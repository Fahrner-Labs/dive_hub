// Buddy suggestions follow the Dive's Revisions (ADR 0070): every path that writes Participants writes one, and every
// soft delete does too. The listener runs inside that transaction, so a web edit, an Import, a merge, a continuation and
// an MCP call all raise and cancel the same way, and a rolled-back write leaves no suggestion behind. A Diver merged
// away takes its pending suggestions to the surviving Diver the same way, in the merge's transaction.
import type { ChangeListener } from '../revisions/revisions.js';
import type { Tx } from '../db/client.js';
import { followMergedDiver, syncSuggestions } from './suggestion-service.js';

/** Wraps the process's existing listener so Participant changes and soft deletes also keep the suggestions current. */
export function withSuggestionChanges(inner: ChangeListener): ChangeListener {
  return {
    async diveChanged(tx: Tx, diveId, cause, at, actor, changes) {
      await inner.diveChanged(tx, diveId, cause, at, actor);
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
  };
}
