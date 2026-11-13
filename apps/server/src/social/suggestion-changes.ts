// Buddy suggestions follow the Dive's Revisions (ADR 0070): every path that writes Participants writes one, and every
// soft delete does too. The listener runs inside that transaction, so a web edit, an Import, a merge, a continuation and
// an MCP call all raise and cancel the same way, and a rolled-back write leaves no suggestion behind.
import type { ChangeListener } from '../revisions/revisions.js';
import type { Tx } from '../db/client.js';
import { syncSuggestions } from './suggestion-service.js';

/** Wraps the process's existing listener so Participant changes and soft deletes also keep the suggestions current. */
export function withSuggestionChanges(inner: ChangeListener): ChangeListener {
  return {
    async diveChanged(tx: Tx, diveId, cause, at, actor, changes) {
      await inner.diveChanged(tx, diveId, cause, at, actor);
      // `participants` means the list was written; a tombstone means the Dive left every list. A restore (to null)
      // leaves cancelled suggestions cancelled until the Participant is added again.
      const participants = changes?.participants !== undefined;
      const tombstone = changes?.deletedAt;
      const deleted = typeof tombstone === 'object' && tombstone !== null && tombstone.to !== null && tombstone.to !== undefined;
      if (participants || deleted) await syncSuggestions(tx, diveId);
    },
    async siteRevisionWritten(tx: Tx, siteId, cause, at) {
      await inner.siteRevisionWritten(tx, siteId, cause, at);
    },
    async siteChanged(tx: Tx, siteId, at) {
      await inner.siteChanged(tx, siteId, at);
    },
  };
}
