---
title: "ADR 0091: Source purge hard-deletes a Source's site data, with a report-first flow"
summary: An admin can remove every External ID of one Source and hard-delete the import-created sites it leaves without data or a Dive; sites a Dive still names are reported, never deleted, and Sites a User made stay. The purge record is the audit, so deleted sites get no Site Revision.
status: proposed
date: 2026-11-26
---

# ADR 0091: Source purge hard-deletes a Source's site data, with a report-first flow

## Status
Proposed – 2026-11-26. The pre-agreed remedy in the accepted hosted-DiveHub execution plan
([FAH-524](/FAH/issues/FAH-524), revision `ff67973a`, §8), implemented in [FAH-528](/FAH/issues/FAH-528). The owner
reviews and accepts it with that pull request. It amends [ADR 0020](0020-dive-sites.md) and
[ADR 0021](0021-site-external-ids-and-import.md) **only** for this admin remedy: a Source's site data is removed, not
soft-deleted like a User's site deletion.

## Context
Hosted Dive Hub imports SSI's site list at the operator's risk ([ADR 0025](0025-ssi-site-import-and-site-water-type.md)).
The plan accepted the remedy if SSI objects: remove the data again. Provenance lives in
`dive_site_external_id` (the `site_source` enum and `providesData`), not in a site column, so a purge can find every
SSI ID and every site it created. A soft delete ([ADR 0020](0020-dive-sites.md)) leaves the SSI-derived name, position
and values in `dive_site`; the remedy has to remove them.

## Decision
- **Admin capability:** `POST /api/admin/site-imports/purge {source}` starts a queued worker job, like a Site import.
  `source` is any Source this deployment offers (`DIVEHUB_SITE_SOURCES`, [ADR 0087](0087-hosted-instance-configuration.md)).
- **The job:** delete every `dive_site_external_id` row of that Source (public and private); then hard-delete sites
  that a Site import created (`createdBy IS NULL`), have no remaining data-providing External ID, and are not named by
  any Dive. A site a Dive still names is reported with its Dive count, never deleted; the admin moves the Dives or
  merges the site, then runs the purge again. A site a User created is never deleted; its External IDs of that Source
  still go, as the remedy requires.
- **No Site Revision for a hard-deleted site.** A Revision belongs to an entity a client can still read
  ([ADR 0020](0020-dive-sites.md)); the `site_purge` row records who purged which Source, when, and which sites stayed
  for a Dive. The job also writes the normal site hints, so open lists refetch.
- **One site job at a time.** A Site import and a Source purge take one advisory lock and refuse each other
  (`site_import_running`), so a purge never runs against a list an import is writing.
- **Merge tombstones:** a merge target waits while a tombstone outside the purge still points at it; a tombstone the
  purge deletes goes before its target in the same run.

## Consequences
- SSI's site data can be removed from a live instance without a database restore; sites Users made and Dives keep
  working, with the Source's ID gone.
- The purge cannot finish while a Dive still names an import-created site. That is deliberate: data integrity beats
  silent deletion, and the report names the sites to resolve.
- A hard-deleted site has no Site history page and no restore. That is the remedy, not a User-facing delete.
- Re-importing the Source after a purge creates the sites again; the admin must keep OSM off and stop the SSI import
  if the Source objected.
