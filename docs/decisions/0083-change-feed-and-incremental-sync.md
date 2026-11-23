---
title: "ADR 0083: Change feed and incremental sync"
summary: A durable, append-only change_event log written in the revision transaction gives native clients a cursorable change feed (IDs and kinds only, xmin high-water, tombstones, 180-day retention, 410 on an expired cursor); reads gain ETag and writes gain If-Match with 412 + current state beside the web's unchanged body-version 409; installs bootstrap the feed cursor over the existing paged sync, and the app probes the feed endpoint instead of parsing versions.
status: proposed
date: 2026-11-23
---

# ADR 0083: Change feed and incremental sync

## Status
Proposed – 2026-11-23 – by the CTO. Requested in [FAH-457](/FAH/issues/FAH-457) (owner-accepted plan,
2026-11-23); the engineering detail is the locked technical plan on
[FAH-458](/FAH/issues/FAH-458#document-plan) (revision 2); this slice: [FAH-459](/FAH/issues/FAH-459).
Builds on [ADR 0066](0066-live-updates-via-sse-hints.md) (hints), [ADR 0075](0075-android-read-store.md)
(the store this feed fills) and [ADR 0076](0076-api-versioning-policy.md) (every change here is additive).
The owner accepts this ADR by merging the pull request: `docs/decisions/**` is CODEOWNERS-protected.

## Context
The native client syncs the logbook into its on-device store ([ADR 0075](0075-android-read-store.md)) by
re-paging the whole logbook on every refresh, and [ADR 0066](0066-live-updates-via-sse-hints.md) covers
freshness with SSE hints that say "something changed" but are not replayed — a phone that was offline has no
way to learn what it missed except re-reading everything. Making the client *writeable* (the offline write
queue of [ADR 0084](0084-offline-writes-in-the-native-core.md)) raises the stakes: a queued write must replay
against a fresh base version, so the client needs a cheap, reliable way to know exactly what changed since it
last looked.

The research blueprint ([data, sync, upload](../research/2026-10-02-data-sync-upload-auth.md) §3) sketched
this as `change_seq`/`change_xid` columns on every entity table. That sketch fails on contact with the real
schema: `participant` has no timestamps or tombstones, `diver_body_weight` is hard-deleted under a composite
key, `cylinder`/`lead` live inside the Dive's version, and a deleted Dive is purged after 30 days
([ADR 0072](0072-purging-deleted-dives.md)) — a purge would silently amputate a feed that lives on the rows.

Constraints that shape the answer:

- **The feed is a wire protocol installed apps depend on.** Once phones hold cursors against it, its shape is
  expensive to change; it must be simple and additive ([ADR 0076](0076-api-versioning-policy.md)).
- **Every write path must be covered.** Logbook writes come from routes, imports, merges, joint-dive
  propagation ([ADR 0082](0082-joint-dive-sync.md)) and MCP; a feed hung on individual routes would be
  forgotten by the next write path.
- **Permissions stay in the normal API.** The feed must not become a second read model with its own
  visibility rules.
- **A client may be gone for months.** Cursors must survive that, or fail loudly with a defined recovery.

## Decision

### D1 — The change feed is a durable, cursorable hint log, not per-row state
A new append-only table **`change_event`**: `seq` (bigserial, the cursor), `xid` (`xid8`,
`pg_current_xact_id()`), `entity_type`, `entity_id`, `kind` (`changed` | `deleted`), `at`. It is written in
the **same transaction** as every logbook write, through the existing `writeRevision()` / `ChangeListener`
seam (`apps/server/src/revisions/revisions.ts`) — every write path (routes, imports, merges, joint-dive
propagation, MCP) already funnels through it, so no future write path can forget the feed.

`GET /sync/changes?cursor=&limit=` returns the events after the opaque position `cursor` (encoded `<xid>:<seq>`),
filtered by the safe high-water mark `xid < pg_snapshot_xmin(pg_current_snapshot())` and ordered by `(xid, seq)`. The
new cursor is the last position returned. `xid` and `seq` bind at different moments — `xid` at the transaction's
first write, `seq` when its hint row is inserted — so two concurrent writes can commit in the opposite order to their
`seq`s; a `seq`-only cursor let a higher-`seq` transaction hand out a position that a lower-`seq` transaction then
committed behind, and the change was skipped. Ordering by `xid` first makes that impossible: a lower-xid transaction
can never be passed by a higher-xid one, and the xmin filter still holds back a transaction that may yet commit. `xid`
is `xid8`, so the order does not wrap at 2^32. The feed orders by position, not wall-clock; the client refetches, so
order carries no conflict meaning. **Cursor bootstrap:** called without a cursor, the endpoint returns only the current
high-water position — the maximum committed position, or the retention floor when the log is empty — for the install
migration and full sync (D6).

The high-water is read from `pg_snapshot_xmin`, which needs no privilege and is correct across xid epochs. Where the
role may see other backends' `backend_xid`, it is scoped to transactions on the current database (only those can write
this log, so a long transaction in another database must not stall the feed); a role that may not see them falls back to
the conservative cluster-wide xmin rather than `xmax`, which would hold nothing back. The price of the cluster fallback
is that a long transaction in another database holds the feed until it ends — conservative, not unsafe.


The feed carries **IDs and kinds only — no payloads**, exactly the SSE-hint discipline of
[ADR 0066](0066-live-updates-via-sse-hints.md). The client refetches changed entities through the normal API;
permissions, visibility and shapes stay in the normal endpoints. Three resolutions per event:

- refetch 2xx → upsert into the store (idempotent; keyed by server `id`);
- refetch 404/403, or `kind = deleted` → local tombstone (`deletedAt`, never a hard delete — the store
  already works this way). 403 doubles as the scope-shrink signal (diver management revoked): the local
  effect is identical, so there is no separate "remove from scope" entry type in v1;
- an entity gone from a listing-only table → handled per-entity in the sync engine.

**Rejected alternative:** `change_seq`/`change_xid` columns on every entity table (the research sketch) —
rejected for the schema reasons above. The log table decouples feed retention from entity lifecycle and
covers every entity kind uniformly. The blueprint's *properties* (ordered cursor, xmin high-water,
tombstones) are all preserved.

**Retention:** feed entries are kept **180 days** (server setting; one sweep next to the existing purge job
of ADR 0072). A one-row `change_feed_floor` records the position of the last swept event. The sweep deletes only a
feed-order prefix of events older than the retention and moves the floor past it in the same statement, so the retained
log is always the suffix after the floor and a crash cannot leave the floor disagreeing with it. Deleting by `at` alone
could leave a hole above a lower position (`at` is the transaction's start, the position its first write). A cursor
before the floor answers **410 Gone** with the problem code `sync_resync_required`, including when the sweep has emptied
the log; the client resyncs (D6) and rebases its write queue instead of discarding it
([ADR 0084](0084-offline-writes-in-the-native-core.md), 410-rebase). 180 days against the 30-day dive
tombstone purge is safe: the deletion *event* survives in the log after the Dive row is gone.

### D2 — Writes: `If-Match` in, 412 + current state out; the 409 body-version path is untouched
Every write today takes `version` in the body and answers 409 (`dive_changed`, `site_changed`, …) with no
state. That stays for the web client — no breaking change ([ADR 0076](0076-api-versioning-policy.md)).
Additively:

- read endpoints (`GET /dives/:id`, `/dive-sites/:id`, centres, equipment) gain an `ETag: "<version>"`
  header;
- write endpoints accept `If-Match: "<version>"` as an alternative to the body `version`;
- a stale `If-Match` answers **412** with the problem body **plus the current entity view** (e.g.
  `{code: "dive_changed", dive: DiveView}`) — one round trip instead of two, which is what the offline
  queue's 3-way merge needs.

A request carrying both must agree; `If-Match` wins on disagreement (it is the newer contract).

### D6 — Migrating existing installs from paged sync to feed sync
The store (schema v4) adds the feed cursor. On upgrade (and on every fresh install after the initial sync):

1. fetch the bootstrap cursor (D1);
2. run the existing full refresh (the paged phases are unchanged — they double as the 410 recovery path);
3. switch `refresh()` to feed-following from the cursor.

Overlaps are safe because upserts are idempotent: an event already reflected in the re-paged data reapplies
harmlessly, and an event that lands between bootstrap and the last page arrives via the feed. SSE hints
become triggers to drain the feed; the durable cursor takes over ADR 0066's "refetch on connect" duty (the
feed covers everything since the cursor, which hints never guaranteed). Paged sync is deleted from the
refresh path only — it stays as the bootstrap and resync machinery.

### D7 — Capability detection, not version parsing
The first writeable app release requires the server release that ships D1 + D2. The app probes
`GET /sync/changes` once per session: 404 → **read-only mode** (write UI hidden, not merely disabled), any
2xx → writeable. The release notes of that app version state the minimum server version. ADR 0076's
`client_too_old` covers the reverse direction; no URL versioning is introduced.

## Consequences

- Sync traffic drops to delta refetches, and the write path gets the fresh base versions it needs — A-read
  ships on its own, before the queue exists.
- The feed is a hint log, so permissions never leak through it; the cost is one extra refetch round trip per
  changed entity (batched, as with SSE hints).
- Feed write amplification is the accepted cost: an import touching 5,000 Dives writes 5,000 `change_event`
  rows in one transaction. Fine at logbook scale; the sweep bounds growth.
- `diver_body_weight` (composite key, hard-deleted) is **not** feed-synced in v1 and stays paged-synced — a
  documented gap, not papered over. `diver` gains `version` in the same migration so it joins the
  body-version/ETag pattern.
- A server that keeps no feed (older release) degrades the app to read-only by probe, never by crash.
- Once installed apps hold cursors, the feed's shape is effectively frozen: changing it later means an
  overlap window under ADR 0076.
