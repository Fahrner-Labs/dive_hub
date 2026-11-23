// The change feed over HTTP (ADR 0083): a native client follows the append-only log by seq, bootstraps a cursor
// without one, and refetches each changed entity through the normal API. The feed carries IDs and kinds only.
import type { FastifyPluginAsyncTypebox, TypeBoxTypeProvider } from '@fastify/type-provider-typebox';
import { Type } from 'typebox';
import type { Auth } from '../auth/auth.js';
import { requireUser } from '../auth/fastify.js';
import { answerErrors, answers, Problem } from '../http/problems.js';
import { REVISION_ENTITY_TYPES } from '../revisions/revisions.js';
import {
  CHANGES_DEFAULT_LIMIT, CHANGES_MAX_LIMIT, SyncError, type SyncService,
} from './changes-service.js';

export interface SyncRouteDeps {
  auth: Auth;
  sync: SyncService;
}

const ChangesQuery = Type.Object({
  cursor: Type.Optional(Type.Integer({
    minimum: 0,
    description: 'The seq of the last change the client applied. Omit it to bootstrap: the answer then carries only the cursor.',
  })),
  limit: Type.Optional(Type.Integer({ minimum: 1, maximum: CHANGES_MAX_LIMIT, default: CHANGES_DEFAULT_LIMIT })),
}, { additionalProperties: false });

const ChangeEventView = Type.Object({
  seq: Type.Integer({ description: 'The change\'s place in the feed; the answer\'s cursor is the last one' }),
  entityType: Type.Enum([...REVISION_ENTITY_TYPES], { description: 'Which kind of thing changed' }),
  entityId: Type.String({ format: 'uuid' }),
  kind: Type.Enum(['changed', 'deleted'], {
    description: 'deleted: it is gone or merged away; a refetch answers 404, and a 403 is a tombstone too (ADR 0083)',
  }),
});

const ChangesView = Type.Object({
  cursor: Type.Integer({ description: 'Send this back as cursor to continue where this answer stopped' }),
  events: Type.Array(ChangeEventView, { description: 'In seq order; empty for a bootstrap' }),
});

const STATUS: Record<SyncError['code'], number> = { sync_resync_required: 410 };

export const syncRoutes: FastifyPluginAsyncTypebox<SyncRouteDeps> = async (scope, { auth, sync }) => {
  const app = scope.withTypeProvider<TypeBoxTypeProvider>();
  app.addHook('onRequest', requireUser(auth));
  app.setErrorHandler(answerErrors(answers(SyncError, STATUS)));

  app.get('/sync/changes', {
    schema: {
      summary: 'The logbook change feed: what changed since a cursor (ADR 0083)',
      description: 'Append-only and ordered by seq. Events carry IDs and kinds only: refetch each entity through its '
        + 'normal API and tombstone it locally on 404/403 or kind=deleted. Without a cursor it answers only the '
        + 'bootstrap cursor for a full sync. A cursor older than the feed\'s 180-day retention answers 410 '
        + 'sync_resync_required; the client then resyncs and rebases its queued writes.',
      querystring: ChangesQuery,
      response: { 200: ChangesView, 400: Problem, 410: Problem },
    },
  }, async (request) => sync.changes(request.query));
};
