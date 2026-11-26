// The admin's Source purge over HTTP (FAH-528): start one, follow it, see the latest. It is a worker job like a
// Site import; the result names the sites a Dive still holds so the admin can resolve them and run it again.
import type { FastifyPluginAsyncTypebox } from '@fastify/type-provider-typebox';
import { Type } from 'typebox';
import type { Auth } from '../../auth/auth.js';
import { requireAdmin, requireUser, userOf } from '../../auth/fastify.js';
import { answerErrors, answers, Problem } from '../../http/problems.js';
import { IdParams } from '../../http/schemas.js';
import { SITE_SOURCES } from '../sources.js';
import { SitePurgeError, type SitePurgeService } from './site-purge-service.js';
import { SitePurgeView, toSitePurgeView } from './site-purge-view.js';

export interface SitePurgeRouteDeps {
  auth: Auth;
  sitePurges: SitePurgeService;
}

const PurgeBody = Type.Object({
  source: Type.Enum([...SITE_SOURCES], { description: 'The Source whose External IDs and now-empty sites go' }),
}, { additionalProperties: false });

const STATUS: Record<SitePurgeError['code'], number> = {
  site_import_running: 409, site_purge_running: 409, site_purge_not_found: 404, site_source_disabled: 400,
};

export const sitePurgeRoutes: FastifyPluginAsyncTypebox<SitePurgeRouteDeps> = async (app, { auth, sitePurges }) => {
  app.addHook('onRequest', requireUser(auth));
  app.addHook('onRequest', requireAdmin);
  app.setErrorHandler(answerErrors(answers(SitePurgeError, STATUS)));

  app.post('/admin/site-imports/purge', {
    schema: {
      summary: 'Remove every External ID of one Source and the sites it leaves empty (admins)',
      description: 'Runs in the background; follow it with GET. Deletes the Source\'s External IDs, then import-created sites with no remaining data-providing ID and no Dive; sites a Dive still names are reported, not deleted. One Site import or purge at a time (409).',
      body: PurgeBody, response: { 202: SitePurgeView, 400: Problem, 403: Problem, 409: Problem },
    },
  }, async (request, reply) => reply.code(202).send(toSitePurgeView(await sitePurges.start(userOf(request).id, request.body.source))));

  app.get('/admin/site-imports/purges', {
    schema: { summary: 'The latest Source purges, newest first (admins)', response: { 200: Type.Array(SitePurgeView), 403: Problem } },
  }, async () => (await sitePurges.list()).map(toSitePurgeView));

  app.get('/admin/site-imports/purges/:id', {
    schema: {
      summary: 'One Source purge with its progress and result (admins)',
      params: IdParams, response: { 200: SitePurgeView, 403: Problem, 404: Problem },
    },
  }, async (request) => toSitePurgeView(await sitePurges.get(request.params.id)));
};
