// Classifying what a Source purge deletes (FAH-528): after every External ID of one Source is gone, which sites
// are left with no data-providing ID and no Dive, which such sites a Dive still names (reported), and which stay
// because their creator is a User or because another Source's data holds them.
import type { PurgeReferencedSite } from '../../db/import-types.js';
import type { ImportSource } from '../sources.js';

/** One Dive site as the purge sees it: its creator, its External IDs, the Dives that name it, its merge tombstone. */
export interface PurgeSite {
  id: string;
  name: string;
  /** Null: a Site import created it; set: a User did, so the purge never deletes it. */
  createdBy: string | null;
  /** Set when this site was merged into another and is only a tombstone pointing there. */
  mergedInto: string | null;
  /** Every Dive that names it, deleted ones too (a tombstone still needs the place to come back to). */
  dives: number;
  externalIds: { source: ImportSource; providesData: boolean }[];
}

export interface PurgeClassification {
  /** Import-created sites whose last data-providing ID goes and that no Dive names, tombstones before their targets. */
  deleteIds: string[];
  /** They would go too, but Dives are there: the admin resolves them and runs the purge again. */
  referenced: PurgeReferencedSite[];
}

/** A site the purge may delete: import-created and no other Source's data is left here. */
const eligible = (site: PurgeSite, source: ImportSource) =>
  site.createdBy === null && !site.externalIds.some((id) => id.source !== source && id.providesData);

/**
 * What a purge of `source` may delete, and what it must report instead. The Source's own IDs are already gone.
 *
 * A merge target waits while a tombstone outside the delete set still points at it. Tombstones that go are listed
 * first, so their targets can follow in the same run. Pass every site for the plan, and the remaining sites for a
 * batch re-check after earlier tombstones are gone.
 */
export function classifyPurge(source: ImportSource, sites: readonly PurgeSite[]): PurgeClassification {
  const unreferenced = sites.filter((site) => eligible(site, source) && site.dives === 0);
  const going = new Set(unreferenced.map((site) => site.id));
  // A tombstone that stays keeps its target (deleting the target would strand the pointer). One that goes is in
  // `going`, so its target is free once the tombstone is deleted first.
  const blocked = new Set<string>();
  for (const site of sites) if (site.mergedInto && !going.has(site.id)) blocked.add(site.mergedInto);

  const deletable = unreferenced.filter((site) => !blocked.has(site.id));
  const ordered = [...deletable.filter((site) => site.mergedInto !== null), ...deletable.filter((site) => site.mergedInto === null)];
  const referenced = sites
    .filter((site) => eligible(site, source) && site.dives > 0)
    .map((site) => ({ id: site.id, name: site.name, dives: site.dives }));
  return { deleteIds: ordered.map((site) => site.id), referenced };
}
