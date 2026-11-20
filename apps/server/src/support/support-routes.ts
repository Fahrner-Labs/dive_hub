import type { FastifyPluginAsyncTypebox, TypeBoxTypeProvider } from '@fastify/type-provider-typebox';
import { Type } from 'typebox';
import type { Auth } from '../auth/auth.js';
import { requireAdmin, requireUser } from '../auth/fastify.js';
import { answerErrors } from '../http/problems.js';
import { DateTime, Nullable } from '../http/schemas.js';
import type { SupportService } from './support-service.js';

export interface SupportRouteDeps {
  auth: Auth;
  support: SupportService;
}

const LogsView = Type.Object({
  entries: Type.Integer(),
  droppedEntries: Type.Integer(),
  oldestAt: Nullable(DateTime),
  newestAt: Nullable(DateTime),
  bytes: Type.Integer(),
});

const RedactionView = Type.Object({
  keysCensored: Type.Integer(),
  emailsMasked: Type.Integer(),
  ipsMasked: Type.Integer(),
});

const PreviewView = Type.Object({
  generatedAt: DateTime,
  appVersion: Type.String(),
  schemaHead: Nullable(Type.String()),
  logs: LogsView,
  redaction: RedactionView,
  jobs: Type.Object({ failed: Type.Integer() }),
  health: Type.Object({
    uptimeSec: Type.Integer(),
    dbLatencyMs: Nullable(Type.Integer()),
    workerRunning: Type.Boolean(),
    queueDepth: Nullable(Type.Integer()),
  }),
  estimatedBytes: Type.Integer(),
});

export const supportRoutes: FastifyPluginAsyncTypebox<SupportRouteDeps> = async (scope, { auth, support }) => {
  const app = scope.withTypeProvider<TypeBoxTypeProvider>();
  app.addHook('onRequest', requireUser(auth));
  app.addHook('onRequest', requireAdmin);
  app.setErrorHandler(answerErrors());

  app.get('/admin/support-bundle/preview', {
    schema: {
      summary: 'Preview what an on-demand support bundle contains (admins only)',
      response: { 200: PreviewView },
    },
  }, async () => support.buildPreview());

  app.get('/admin/support-bundle/download', {
    schema: {
      summary: 'Download a redacted on-demand support bundle (admins only)',
      description: 'The gzip archive is built in memory from the current logs, redacted configuration, failed jobs and health stats.',
    },
  }, async (_request, reply) => {
    const { filename, bytes } = await support.buildBundle();
    return reply
      .header('content-type', 'application/gzip')
      .header('content-length', bytes.length)
      .header('content-disposition', `attachment; filename="${filename}"`)
      .send(bytes);
  });
};
