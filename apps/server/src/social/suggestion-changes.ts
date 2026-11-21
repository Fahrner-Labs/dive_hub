// Buddy suggestions follow the Dive's Revisions (ADR 0070): every path that writes Participants writes one, and every
// soft delete does too. The listener runs inside that transaction, so a web edit, an Import, a merge, a continuation and
// an MCP call all raise and cancel the same way, and a rolled-back write leaves no suggestion behind. A Diver merged
// away takes its pending suggestions to the surviving Diver the same way, in the merge's transaction.
import { eq } from 'drizzle-orm';
import type { ChangeListener } from '../revisions/revisions.js';
import type { Tx } from '../db/client.js';
import { dive, type ParticipantRole } from '../db/schema.js';
import { detachDeletedJointDive, emitJointOfferHint } from './offer-service.js';
import { followMergedDiver, propagateAddedParticipants, syncSuggestions } from './suggestion-service.js';

type ParticipantChange = {
  from: { diverId: string }[];
  to: { diverId: string; role: ParticipantRole }[];
};

/** The Revisions' keys of the shared facts a Joint dive offers (FAH-411); anything else changes no offer. */
const SHARED_CHANGE_KEYS = ['site', 'startsAt', 'durationSeconds', 'maxDepthM', 'avgDepthM'] as const;
const sharedFieldChanged = (changes: Parameters<ChangeListener['diveChanged']>[5]) =>
  !!changes && SHARED_CHANGE_KEYS.some((key) => key in changes);

/** Wraps the process's existing listener so Participant changes and soft deletes also keep the suggestions current. */
export function withSuggestionChanges(inner: ChangeListener): ChangeListener {
  return {
    async diveChanged(tx: Tx, diveId, cause, at, actor, changes) {
      await inner.diveChanged(tx, diveId, cause, at, actor);
      // A Joint dive keeps its members' people in step (FAH-410): a Participant added to one member joins every other
      // member too. Adds only — a removal never reaches another member — and a row already there stays as it is, so
      // replaying the same change is a no-op. The receiving Dive's own Diver is never a Participant of its own.
      const participantChange = changes?.participants as ParticipantChange | undefined;
      if (participantChange) {
        const before = new Set(participantChange.from.map((person) => person.diverId));
        const added = participantChange.to.filter((person) => !before.has(person.diverId));
        await propagateAddedParticipants(tx, diveId, added);
      }
      // `participants` means the list was written; a tombstone means the Dive left every list; `diverId` means the Dive
      // moved to another Diver, which changes who its own Diver is. Only adding a Participant raises a declined or
      // cancelled row again (ADR 0070), so a move cancels but never revives: it also removes the new own Diver from the
      // Participant list. A restore (to null) leaves cancelled suggestions cancelled until the Participant is added again.
      // Accepting a Buddy suggestion copies the source Dive's people onto the copy, which must not raise suggestions
      // back to them.
      const participants = changes?.participants !== undefined;
      const tombstone = changes?.deletedAt;
      const deleted = typeof tombstone === 'object' && tombstone !== null && tombstone.to !== null && tombstone.to !== undefined;
      const moved = changes?.diverId !== undefined;
      if (cause !== 'buddy-accept' && (participants || deleted || moved)) {
        await syncSuggestions(tx, diveId, { reRaise: cause !== 'move' });
      }
      // A Joint dive's fill offers are derived from its members' shared facts (FAH-411), so a shared-field edit or a
      // member leaving changes them. A soft delete first leaves the Joint dive; a Joint dive left with fewer than two
      // live members is deleted with its dismissals. Either way every remaining member's Users refetch.
      if (deleted) {
        const stillJoint = await detachDeletedJointDive(tx, diveId);
        if (stillJoint) await emitJointOfferHint(tx, stillJoint);
      } else if (sharedFieldChanged(changes)) {
        const [row] = await tx.select({ jointDiveId: dive.jointDiveId }).from(dive).where(eq(dive.id, diveId));
        if (row?.jointDiveId) await emitJointOfferHint(tx, row.jointDiveId);
      }
    },
    async siteRevisionWritten(tx: Tx, siteId, cause, at) {
      await inner.siteRevisionWritten(tx, siteId, cause, at);
    },
    async siteChanged(tx: Tx, siteId, at) {
      await inner.siteChanged(tx, siteId, at);
    },
    async diverMerged(tx: Tx, fromId, intoId) {
      await inner.diverMerged?.(tx, fromId, intoId);
      await followMergedDiver(tx, fromId, intoId);
    },
    async fingerprintInputsChanged(tx: Tx, diverIds) {
      await inner.fingerprintInputsChanged?.(tx, diverIds);
    },
    async fingerprintSiteInputsChanged(tx: Tx, siteId) {
      await inner.fingerprintSiteInputsChanged?.(tx, siteId);
    },
  };
}
