// A Source purge over HTTP (FAH-528): the job, its progress, and the sites it had to leave for the admin.
import { Type, type Static } from 'typebox';
import type { sitePurge } from '../../db/schema.js';
import { siteImportStatus } from '../../db/schema.js';
import { PROBLEM_CODES, storedProblemCode } from '../../http/problems.js';
import { DateTime, Nullable, refTo } from '../../http/schemas.js';
import { Source } from './site-import-view.js';

/** Registered with app.addSchema; routes answer SitePurgeView, the reference. */
export const SitePurgeViewSchema = Type.Object({
  id: Type.String(),
  status: Type.Enum([...siteImportStatus.enumValues]),
  source: Source,
  progress: Type.Object({
    step: Type.Enum(['waiting', 'ids', 'sites']),
    done: Type.Integer(), total: Type.Integer(),
  }),
  result: Nullable(Type.Object({
    sourceIdsDeleted: Type.Integer({ description: 'External IDs of the Source that were removed' }),
    sitesDeleted: Type.Integer({ description: 'Import-created sites no data-providing ID and no Dive held' }),
    referencedSites: Type.Array(Type.Object({
      id: Type.String(), name: Type.String(), dives: Type.Integer({ description: 'Dives that still name the site' }),
    }), { description: 'Sites a Dive still names: left in place for the admin to resolve, then run the purge again' }),
  })),
  failureCode: Nullable(Type.Enum(PROBLEM_CODES), { description: 'Why it failed (a problem code)' }),
  createdAt: DateTime,
  finishedAt: Nullable(DateTime),
}, { $id: 'SitePurgeView' });
export const SitePurgeView = refTo(SitePurgeViewSchema);

export const toSitePurgeView = (r: typeof sitePurge.$inferSelect): Static<typeof SitePurgeView> => ({
  id: r.id, status: r.status, source: r.source, progress: r.progress, result: r.result,
  failureCode: storedProblemCode(r.errorCode), createdAt: r.createdAt.toISOString(), finishedAt: r.finishedAt?.toISOString() ?? null,
});
