// Who may see a Diver's contact details (ADR 0088): under `instance` every User of the instance (ADR 0043, the default);
// under `shared` only the Diver's Users, the User who added an external Diver, admins, and Users who share a Dive with
// it. The rule is computational over the existing management and participant rows; nothing about it is stored.
import { sql, type SQL } from 'drizzle-orm';
import type { Queryable } from '../db/client.js';
import { dive, diver, diverManagement, participant } from '../db/schema.js';
import type { DiverVisibility } from '../vocabulary.js';

export interface VisibilityViewer {
  userId: string;
  isAdmin: boolean;
}

/**
 * The predicate as SQL, so a list asks it per row instead of running a query per row. `diverId` is the Diver's id
 * column or a bound id. `instance` and admins answer true without reading anything.
 *
 * Two people share a Dive when they are both on it: as its own Diver, or as a Participant (ADR 0028: a Dive's own Diver
 * is no Participant). A Dive that was deleted still counts: it happened, and soft-deleting it is its owner's private
 * act, not a withdrawal of what the people on it saw. Purging a Dive removes the row, so nothing is shared then.
 *
 * An external Diver has no managing User; the User who added it (`created_by`) is its owner for this rule. Otherwise
 * the User who first typed the name could not read what they entered, and the code they scanned to add the buddy would
 * be the last they saw of it.
 */
export function seesDetailsSql(viewer: VisibilityViewer, diverId: SQL, visibility: DiverVisibility): SQL {
  if (visibility === 'instance' || viewer.isAdmin) return sql`true`;
  return sql`(
    exists (select 1 from ${diverManagement} m where m.diver_id = ${diverId} and m.user_id = ${viewer.userId})
    or exists (
      select 1 from ${dive} d
      where (d.diver_id = ${diverId} or exists (select 1 from ${participant} p where p.dive_id = d.id and p.diver_id = ${diverId}))
        and (
          d.diver_id in (select m2.diver_id from ${diverManagement} m2 where m2.user_id = ${viewer.userId})
          or exists (
            select 1 from ${participant} p2
            where p2.dive_id = d.id
              and p2.diver_id in (select m3.diver_id from ${diverManagement} m3 where m3.user_id = ${viewer.userId})
          )
        )
    )
    or exists (
      select 1 from ${diver} own
      where own.id = ${diverId} and own.created_by = ${viewer.userId}
        and not exists (select 1 from ${diverManagement} dm where dm.diver_id = own.id)
    )
  )`;
}

/** Whether this viewer may see this Diver's contact details under the instance's visibility rule (ADR 0088). */
export async function seesDiverDetails(
  q: Queryable, viewer: VisibilityViewer, diverId: string, visibility: DiverVisibility,
): Promise<boolean> {
  if (visibility === 'instance' || viewer.isAdmin) return true;
  const [row] = (await q.execute(sql`select ${seesDetailsSql(viewer, sql`${diverId}::uuid`, visibility)} as visible`))
    .rows as { visible: boolean }[];
  return row?.visible === true;
}
