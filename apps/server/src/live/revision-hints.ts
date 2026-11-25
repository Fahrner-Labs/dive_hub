// Dive and site Revisions become hints (ADR 0066). The change listener runs inside the writing transaction, so these
// hints are any-origin by construction: a web edit, a worker import, MCP, or another tab all arrive here.
import type { ChangeListener } from '../revisions/revisions.js';
import type { Queryable, Tx } from '../db/client.js';
import { audienceOfDive, emitHint } from './emit.js';

/**
 * Queues a Dive hint for native push in the same transaction (ADR 0085). The dispatcher implements it; push is a
 * sibling of the SSE hint, not content: it carries the same kind and id, and the Users who may refetch the Dive.
 */
export type PushHintSink = (
  q: Queryable,
  hint: { kind: 'dive'; id: string; audience: string[] },
) => Promise<void>;

/** Wraps the process's existing listener (the Waiting push queue) so every Revision also notifies open pages. */
export function withRevisionHints(inner: ChangeListener, push?: PushHintSink): ChangeListener {
  return {
    async diveChanged(tx: Tx, diveId, cause, at, actor, changes) {
      await inner.diveChanged(tx, diveId, cause, at, actor, changes);
      // The same audience feeds the SSE hint and the push jobs (ADR 0085), so both stay in step with the Divers' managers.
      const audience = await audienceOfDive(tx, diveId);
      await emitHint(tx, { kind: 'dive', id: diveId, audience });
      await push?.(tx, { kind: 'dive', id: diveId, audience });
    },
    async siteRevisionWritten(tx: Tx, siteId, cause, at) {
      await inner.siteRevisionWritten(tx, siteId, cause, at);
      // Dive sites are shared and any User may read them (ADR 0020): the wildcard keeps the payload constant-sized.
      await emitHint(tx, { kind: 'site', id: siteId, audience: '*' });
    },
    // The Waiting push queue still hears name/position changes (ADR 0056); the site hint itself comes from the Revision
    // hook above, so every site write announces itself and a caller cannot forget it.
    async siteChanged(tx: Tx, siteId, at) {
      await inner.siteChanged(tx, siteId, at);
    },
    async fingerprintInputsChanged(tx: Tx, diverIds) {
      await inner.fingerprintInputsChanged?.(tx, diverIds);
    },
    async fingerprintSiteInputsChanged(tx: Tx, siteId) {
      await inner.fingerprintSiteInputsChanged?.(tx, siteId);
    },
  };
}
