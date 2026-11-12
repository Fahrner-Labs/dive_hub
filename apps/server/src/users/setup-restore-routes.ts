// The setup screen's restore path (plan FAH-175 slice 3): while no User exists, the setup token protects replacing
// this fresh instance's database and files with a backup. Uploads stream to disk with no upload cap; an archive the
// operator placed in /data/restore/ can be picked instead. On success the server answers and then exits.
import type { FastifyPluginAsyncTypebox } from '@fastify/type-provider-typebox';
import type { FastifyRequest } from 'fastify';
import { Type } from 'typebox';
import { limitAttempts, type AttemptLimit } from '../http/attempt-limit.js';
import { Problem, problem, type ProblemCode } from '../http/problems.js';
import { DateTime } from '../http/schemas.js';
import type { BlobStore, StoredBlob } from '../storage/blob-store.js';
import type { SetupRestore, SetupRestoreFailure } from './setup-restore.js';
import type { SetupService } from './setup-service.js';

export interface SetupRestoreRouteDeps {
  setup: SetupService;
  setupRestore: SetupRestore;
  blobs: BlobStore;
  /** Counts attempts at the token routes per client IP (ADR 0061); shared with the other token routes. */
  tokenAttempts: AttemptLimit;
  /** Runs after a successful restore's answer is written, so the process can exit and the operator restart it. */
  onRestored?: (() => void) | undefined;
}

const RestoreResult = Type.Object({
  restored: Type.Literal(true),
  createdAt: DateTime,
  migrationHead: Type.Union([Type.String(), Type.Null()]),
});

const PlacedArchive = Type.Object({
  name: Type.String(),
  bytes: Type.Integer(),
});

const ANSWER: Record<SetupRestoreFailure, { code: ProblemCode; status: 400 | 500 }> = {
  backup_key_mismatch: { code: 'setup_restore_key_mismatch', status: 400 },
  backup_schema_newer: { code: 'setup_restore_schema_newer', status: 400 },
  backup_decrypt_failed: { code: 'setup_restore_decrypt_failed', status: 400 },
  backup_restore_failed: { code: 'setup_restore_failed', status: 500 },
  backup_files_failed: { code: 'setup_restore_failed', status: 500 },
};

/** What the multipart reader gathered; `retry` is the token's put-back while nothing has been restored. */
interface RestoreParts {
  retry: (() => void) | null;
  passphrase?: string | undefined;
  placed?: string | undefined;
  stored?: StoredBlob | undefined;
}

/**
 * Reads the form's fields and its one archive. The token is checked as soon as its field arrives, so a wrong one does
 * not let an archive stream to disk. The file is the only part that is stored.
 */
async function readParts(request: FastifyRequest, blobs: BlobStore, consume: (token: string) => (() => void) | null, parts: RestoreParts): Promise<void> {
  let tokenSeen = false;
  // Unlike the app's 512 MB default, a backup may be many gigabytes: stream it to disk with no cap.
  for await (const part of request.parts({ limits: { fileSize: Number.MAX_SAFE_INTEGER } })) {
    if (part.type === 'file') {
      if (part.fieldname === 'file' && parts.retry && !parts.stored) parts.stored = await blobs.putIncoming(part.file, Number.MAX_SAFE_INTEGER);
      else part.file.resume();
    } else if (part.fieldname === 'token' && !tokenSeen) {
      tokenSeen = true;
      parts.retry = consume(String(part.value).trim());
    } else if (part.fieldname === 'passphrase') {
      parts.passphrase = String(part.value);
    } else if (part.fieldname === 'placed') {
      parts.placed = String(part.value);
    }
  }
}

export const setupRestoreRoutes: FastifyPluginAsyncTypebox<SetupRestoreRouteDeps> = async (app, { setup, setupRestore, blobs, tokenAttempts, onRestored }) => {
  const limited = limitAttempts(tokenAttempts);

  app.get('/setup/restore/archives', {
    schema: {
      summary: 'Archives the operator placed in /data/restore/, for the setup screen',
      response: { 200: Type.Object({ archives: Type.Array(PlacedArchive) }) },
    },
  }, async () => ({ archives: (await setup.isNeeded()) ? await setupRestore.placedArchives() : [] }));

  app.post('/setup/restore', {
    onRequest: limited,
    schema: {
      summary: 'Restore a fresh instance from a .divehub-backup archive (setup token required)',
      description: 'Multipart fields: token, passphrase, then either a file in "file" or the name of an archive in '
        + '/data/restore/ in "placed". The upload has no fixed size cap. On success the server exits; restart it.',
      consumes: ['multipart/form-data'],
      response: { 200: RestoreResult, 400: Problem, 403: Problem, 409: Problem, 429: Problem, 500: Problem },
    },
  }, async (request, reply) => {
    // Setup may only have completed since the request arrived; check before parsing any body.
    if (!(await setup.isNeeded())) return reply.code(409).send(problem('setup_done'));
    if (!request.isMultipart()) return reply.code(400).send(problem('invalid_input', 'Expected multipart/form-data'));

    const parts: RestoreParts = { retry: null };
    try {
      await readParts(request, blobs, (token) => setup.consume(token), parts);
    } catch (error) {
      parts.retry?.();
      throw error;
    }

    const fail = async (code: ProblemCode, detail?: string, status: 400 | 403 | 500 = 400) => {
      parts.retry?.();
      if (parts.stored) await blobs.delete(parts.stored.key).catch(() => {});
      return reply.code(status).send(problem(code, detail));
    };

    try {
      if (!parts.retry) return fail('setup_token_invalid', undefined, 403);
      if (!parts.passphrase) return fail('backup_passphrase_missing');
      let archivePath: string | null;
      if (parts.stored) archivePath = blobs.pathOf(parts.stored.key);
      else if (parts.placed) archivePath = await setupRestore.placedArchive(parts.placed);
      else return fail('setup_restore_source_missing');
      if (!archivePath) return fail('setup_restore_archive_missing');

      const outcome = await setupRestore.restore({ archivePath, passphrase: parts.passphrase });
      if (!outcome.ok) {
        request.log.warn({ code: outcome.code, detail: outcome.message }, 'setup restore refused');
        const answer = ANSWER[outcome.code];
        return fail(answer.code, outcome.message, answer.status);
      }
      request.log.info(
        { createdAt: outcome.manifest.createdAt, migrationHead: outcome.manifest.migrationHead },
        'instance restored from a backup during setup; exiting for the operator to restart it',
      );
      // Remove the streamed upload before answering; the process goes away right after the answer.
      if (parts.stored) await blobs.delete(parts.stored.key).catch(() => {});
      reply.raw.once('finish', () => onRestored?.());
      return reply.code(200).send({ restored: true, ...outcome.manifest });
    } catch (error) {
      parts.retry?.();
      throw error;
    } finally {
      if (parts.stored) await blobs.delete(parts.stored.key).catch(() => {});
    }
  });
};
