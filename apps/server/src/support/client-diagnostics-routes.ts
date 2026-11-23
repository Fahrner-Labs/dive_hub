// Client diagnostics over the API (ADR 0080): every signed-in User asks whether this instance collects them and
// uploads a bounded report; admins read and flip the instance setting. Entries are re-redacted by the service.
import type { FastifyPluginAsyncTypebox, TypeBoxTypeProvider } from '@fastify/type-provider-typebox';
import { Type } from 'typebox';
import type { Auth } from '../auth/auth.js';
import { requireAdmin, requireUser, userOf } from '../auth/fastify.js';
import { answerErrors, answers, Problem } from '../http/problems.js';
import { DateTime } from '../http/schemas.js';
import {
  ClientDiagnosticsError, MAX_UPLOAD_ENTRIES, type ClientDiagnosticsService,
} from './client-diagnostics-service.js';

export interface ClientDiagnosticsRouteDeps {
  auth: Auth;
  clientDiagnostics: ClientDiagnosticsService;
}

const STATUS: Record<ClientDiagnosticsError['code'], number> = {
  client_diagnostics_disabled: 403,
  client_diagnostics_limited: 429,
};

const SettingsView = Type.Object({ enabled: Type.Boolean() });

const EntryInput = Type.Object({
  occurredAt: DateTime,
  level: Type.Union([Type.Literal('error'), Type.Literal('warn')]),
  code: Type.Optional(Type.String({ maxLength: 200, description: 'A stable client code; clients translate what they know' })),
  clientVersion: Type.Optional(Type.String({
    maxLength: 100,
    description: 'The build the entry was captured in; older clients omit it and the upload\'s client.version is used',
  })),
  message: Type.String(),
  stack: Type.Optional(Type.String()),
  context: Type.Optional(Type.Record(Type.String(), Type.String(), {
    description: 'Client context; the server re-redacts keys such as token and strings such as e-mail addresses',
  })),
}, { additionalProperties: false });

const UploadBody = Type.Object({
  client: Type.Object({
    name: Type.Union([Type.Literal('web'), Type.Literal('android')]),
    version: Type.String({ minLength: 1, maxLength: 100 }),
  }, { additionalProperties: false }),
  capturedAt: DateTime,
  entries: Type.Array(EntryInput, { maxItems: MAX_UPLOAD_ENTRIES }),
}, { additionalProperties: false });

export const clientDiagnosticsRoutes: FastifyPluginAsyncTypebox<ClientDiagnosticsRouteDeps> = async (
  scope, { auth, clientDiagnostics },
) => {
  const app = scope.withTypeProvider<TypeBoxTypeProvider>();
  app.addHook('onRequest', requireUser(auth));
  app.setErrorHandler(answerErrors(answers(ClientDiagnosticsError, STATUS)));

  app.get('/client-diagnostics/status', {
    schema: {
      summary: 'Whether this instance accepts client diagnostics (off by default)',
      response: { 200: SettingsView },
    },
  }, async () => clientDiagnostics.settings());

  app.post('/client-diagnostics', {
    schema: {
      summary: 'Upload a client\'s local error report (at most 50 entries)',
      description: 'Refused with 403 client_diagnostics_disabled while an admin has collection off, and with '
        + '429 client_diagnostics_limited after five uploads in an hour. The server truncates and re-redacts every '
        + 'entry before storing it.',
      body: UploadBody,
      response: { 204: Type.Null(), 400: Problem, 403: Problem, 429: Problem },
    },
  }, async (request, reply) => {
    await clientDiagnostics.ingest(userOf(request).id, request.body);
    return reply.code(204).send(null);
  });

  app.get('/admin/client-diagnostics', {
    onRequest: requireAdmin,
    schema: { summary: 'Admins: whether this instance collects client diagnostics', response: { 200: SettingsView, 403: Problem } },
  }, async () => clientDiagnostics.settings());

  app.patch('/admin/client-diagnostics', {
    onRequest: requireAdmin,
    schema: {
      summary: 'Admins: switch client-diagnostics collection on or off for the instance (off by default)',
      description: 'Off refuses every new upload at once; rows already stored stay until their 90-day purge or the next support bundle.',
      body: Type.Object({ enabled: Type.Boolean() }, { additionalProperties: false }),
      response: { 200: SettingsView, 403: Problem },
    },
  }, async (request) => {
    const settings = await clientDiagnostics.setEnabled(request.body.enabled);
    request.log.info({ enabled: settings.enabled, by: userOf(request).id }, 'client diagnostics switched');
    return settings;
  });
};
