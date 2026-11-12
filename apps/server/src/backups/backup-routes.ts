// Admin backups (ADR 0069, plan FAH-175): start a manual passphrase-encrypted backup, list runs, download and delete.
import { createReadStream } from 'node:fs';
import { stat } from 'node:fs/promises';
import type { FastifyPluginAsyncTypebox, TypeBoxTypeProvider } from '@fastify/type-provider-typebox';
import { Type, type Static } from 'typebox';
import type { Auth } from '../auth/auth.js';
import { requireAdmin, requireUser, userOf } from '../auth/fastify.js';
import { answerErrors, answers, Problem } from '../http/problems.js';
import { DateTime, IdParams } from '../http/schemas.js';
import { BACKUP_EXTENSION } from './backup-archive.js';
import { BackupError, type BackupRunRow, type BackupService } from './backup-service.js';

export interface BackupRouteDeps {
  auth: Auth;
  backups: BackupService;
}

const STATUS: Record<BackupError['code'], number> = {
  backup_not_found: 404, backup_file_missing: 404,
  backup_in_progress: 409, backup_running: 409, backup_not_ready: 409,
  backup_passphrase_missing: 400,
  backup_settings_invalid: 400, backup_recipient_missing: 400, backup_recipient_invalid: 400,
};

const BackupSettingsView = Type.Object({
  enabled: Type.Boolean(),
  intervalHours: Type.Integer(),
  retentionCount: Type.Integer(),
  recipientPublicKey: Type.Union([Type.String(), Type.Null()]),
});

const BackupSettingsInput = Type.Object({
  enabled: Type.Boolean(),
  intervalHours: Type.Integer({ minimum: 1, maximum: 168, description: 'How often a scheduled backup runs, in hours' }),
  retentionCount: Type.Integer({ minimum: 1, maximum: 365, description: 'How many scheduled runs to keep; manual runs are never deleted' }),
  recipientPublicKey: Type.Optional(Type.String({ maxLength: 128, description: 'An age-style X25519 recipient (age1…) that scheduled backups encrypt to; required while enabled. Omit or send an empty string to clear.' })),
});

const BackupRunView = Type.Object({
  id: Type.String(),
  kind: Type.Union([Type.Literal('manual'), Type.Literal('scheduled')]),
  status: Type.Union([Type.Literal('queued'), Type.Literal('running'), Type.Literal('ready'), Type.Literal('failed')]),
  bytes: Type.Union([Type.Integer(), Type.Null()]),
  sha256: Type.Union([Type.String(), Type.Null()]),
  error: Type.Union([Type.String(), Type.Null()]),
  createdAt: DateTime,
  startedAt: Type.Union([DateTime, Type.Null()]),
  finishedAt: Type.Union([DateTime, Type.Null()]),
});

const toView = (run: BackupRunRow): Static<typeof BackupRunView> => ({
  id: run.id, kind: run.kind, status: run.status, bytes: run.bytes, sha256: run.sha256, error: run.error,
  createdAt: run.createdAt.toISOString(),
  startedAt: run.startedAt?.toISOString() ?? null,
  finishedAt: run.finishedAt?.toISOString() ?? null,
});

export const backupRoutes: FastifyPluginAsyncTypebox<BackupRouteDeps> = async (scope, { auth, backups }) => {
  const app = scope.withTypeProvider<TypeBoxTypeProvider>();
  app.addHook('onRequest', requireUser(auth));
  app.addHook('onRequest', requireAdmin);
  app.setErrorHandler(answerErrors(answers(BackupError, STATUS)));
  const errors = { 400: Problem, 404: Problem, 409: Problem };

  app.get('/admin/backups/settings', {
    schema: {
      summary: 'The scheduled-backup setting (admins only)',
      response: { 200: BackupSettingsView },
    },
  }, async () => backups.settings());

  app.put('/admin/backups/settings', {
    schema: {
      summary: 'Schedule backups and set their retention (admins only)',
      description: 'Scheduled backups encrypt to the operator-held X25519 recipient; the private key never reaches the server. '
        + 'Retention keeps the newest scheduled runs and never deletes a manual run or the last successful run.',
      body: BackupSettingsInput,
      response: { 200: BackupSettingsView, ...errors },
    },
  }, async (request) => backups.saveSettings(request.body));

  app.post('/admin/backups', {
    schema: {
      summary: 'Start a manual backup, encrypted with this passphrase (admins only)',
      description: 'The passphrase is used for this run only; it is never stored or logged. A backup already queued or running answers 409.',
      body: Type.Object({
        passphrase: Type.String({ minLength: 15, maxLength: 1024, description: 'At least 15 characters; needed again to restore' }),
      }),
      response: { 201: BackupRunView, ...errors },
    },
  }, async (request, reply) => {
    const run = await backups.create({ kind: 'manual', createdBy: userOf(request).id, passphrase: request.body.passphrase });
    return reply.code(201).send(toView(run));
  });

  app.get('/admin/backups', {
    schema: {
      summary: 'All backup runs, newest first (admins only)',
      response: { 200: Type.Array(BackupRunView) },
    },
  }, async () => (await backups.list()).map(toView));

  app.get('/admin/backups/:id/download', {
    schema: { summary: 'Download a ready backup archive (admins only)', params: IdParams },
  }, async (request, reply) => {
    const run = await backups.find(request.params.id);
    if (!run) throw new BackupError('backup_not_found');
    if (run.status !== 'ready') throw new BackupError(run.status === 'failed' ? 'backup_not_ready' : 'backup_running');
    const path = backups.archivePath(run.id);
    const info = await stat(path).catch(() => null);
    if (!info) throw new BackupError('backup_file_missing');
    return reply
      .header('content-type', 'application/octet-stream')
      .header('content-length', info.size)
      .header('content-disposition', `attachment; filename="divehub-${run.createdAt.toISOString().slice(0, 10)}-${run.id}${BACKUP_EXTENSION}"`)
      .send(createReadStream(path));
  });

  app.delete('/admin/backups/:id', {
    schema: { summary: 'Delete a finished backup, or cancel a queued one, and its archive (admins only)', params: IdParams, response: { 204: Type.Null(), ...errors } },
  }, async (request, reply) => {
    await backups.remove(request.params.id);
    return reply.code(204).send(null);
  });
};
