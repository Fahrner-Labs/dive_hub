// Types of the Site import that the schema stores as JSON (ADR 0021, 0025). They live here, below the domain
// modules, so db/ imports nothing of sites/ (ADR 0053); sites/ re-exports them under their old homes.
import type { Position, SiteWaterType } from '../vocabulary.js';

/** Where to import: one country (ISO 3166-1 alpha-2), a box in degrees, or everywhere. */
export type ImportArea =
  | { kind: 'country'; country: string }
  | { kind: 'box'; south: number; west: number; north: number; east: number }
  | { kind: 'world' };

/** The site fields a Source can fill; null where it has nothing. */
export interface ImportedValues {
  name: string | null;
  position: Position | null;
  country: string | null;
  waterBody: string | null;
  description: string | null;
  maxDepthM: number | null;
  /** Only SSI knows it (ADR 0025). Missing in values stored before it existed: read as null. */
  waterType: SiteWaterType | null;
}

export interface SiteImportCounts {
  created: number;
  updated: number;
  unchanged: number;
  kept: number;
  linked: number;
  skippedNoName: number;
  skippedDeleted: number;
  skippedMerged: number;
  /** New places left out because the admin chose not to create sites (ADR 0025). */
  skippedNew: number;
  /** Hand-made sites whose page now offers a Source's data. */
  offered: number;
  gone: number;
}

/** Every counter at zero; also fills the counters a row written by an older release does not have. */
export const EMPTY_SITE_IMPORT_COUNTS: Readonly<SiteImportCounts> = Object.freeze({
  created: 0, updated: 0, unchanged: 0, kept: 0, linked: 0,
  skippedNoName: 0, skippedDeleted: 0, skippedMerged: 0, skippedNew: 0, offered: 0, gone: 0,
});

/** Where a Source purge is: deleting its External IDs, then the import-created sites they leave empty. */
export interface SitePurgeProgress {
  step: 'waiting' | 'ids' | 'sites';
  done: number;
  total: number;
}

/** A site a Source purge would have deleted, but a Dive still names it; the admin resolves it and runs the purge again. */
export interface PurgeReferencedSite {
  id: string;
  name: string;
  /** The Dives that name it, deleted ones too. */
  dives: number;
}

/** What a finished Source purge did, kept for the admin who started it. */
export interface SitePurgeResult {
  sourceIdsDeleted: number;
  sitesDeleted: number;
  /** Sites left because Dives are there (data integrity beats silent deletion). */
  referencedSites: PurgeReferencedSite[];
}

/**
 * One field of a Conflict as stored (src/providers/conflict-service.ts says which fields and values exist): the field and both
 * sides' values as the import preview shows them.
 */
export interface StoredConflictField {
  field: string;
  hub: unknown;
  provider: unknown;
}
