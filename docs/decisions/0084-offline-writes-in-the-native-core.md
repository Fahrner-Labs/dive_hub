---
title: "ADR 0084: Offline writes in the native core"
summary: Native clients write offline through a durable, ordered write queue in the KMP shared core — writes apply to the store at once and replay with their base version (If-Match); a 412 is answered by a client-side field-level three-way merge, fields changed on both sides park as user-decidable conflicts (the user's own fresh edit preselected), a 410 resync rebases the queue instead of discarding it, and signing out with pending writes blocks behind a warning.
status: proposed
date: 2026-11-23
---

# ADR 0084: Offline writes in the native core

## Status
Proposed – 2026-11-23 – by the CTO. Requested in [FAH-457](/FAH/issues/FAH-457) (owner-accepted plan,
2026-11-23), whose product principles bind every clause below; the engineering detail is the locked technical
plan on [FAH-458](/FAH/issues/FAH-458#document-plan) (revision 2); this slice: [FAH-459](/FAH/issues/FAH-459).
Builds on [ADR 0083](0083-change-feed-and-incremental-sync.md) (feed, ETag/If-Match/412),
[ADR 0075](0075-android-read-store.md) (the store the queue lives in),
[ADR 0073](0073-android-app-in-monorepo-kotlin-compose.md) (the KMP core),
[ADR 0015](0015-overrides-vocabulary-and-browser-tests.md) (optimistic locking),
[ADR 0030](0030-importing-dives-from-providers.md) and [ADR 0056](0056-two-way-exchange-and-automation.md)
(the three-way conflict pattern this extends from hub-vs-Provider to hub-vs-device), and
[ADR 0082](0082-joint-dive-sync.md) ("never silently overwrite").
The owner accepts this ADR by merging the pull request: `docs/decisions/**` is CODEOWNERS-protected.

## Context
A logbook lives where the diving happens: boats, centres, quarries — places with no signal. Today the Android
app reads offline ([ADR 0075](0075-android-read-store.md)) but every write needs the server, so an edit made
on the boat simply dies. The product principles ([FAH-457](/FAH/issues/FAH-457)) are non-negotiable: never
silently overwrite; write as if online; offline write beats no write; the API stays the source of truth;
queued writes are the user's data; destructive verbs stay online.

The house pattern already exists: optimistic locking everywhere ([ADR 0015](0015-overrides-vocabulary-and-browser-tests.md)),
and a field-level three-way merge with user-decided conflicts against Providers
([ADR 0030](0030-importing-dives-from-providers.md), [ADR 0056](0056-two-way-exchange-and-automation.md)).
This ADR extends that pattern from "hub vs Provider" to "hub vs your own phone".

Constraints that shape the answer:

- **The queue holds user data.** Losing it is losing a dive the user remembers logging. Durability and the
  sign-out guard are the product, not polish.
- **One merge engine, not two.** The web's conflicts, Provider conflicts and device conflicts must agree on
  what "changed on both sides" means.
- **The server stays "serves state, clients decide".** It already has the per-field base in its Revision
  history, but moving merge onto the server would couple merge semantics to the server release cadence.
- **Two kinds of offline write exist.** Field edits to existing entities merge; deferred actions (a scanned
  code, a create) replay verbatim and never merge.

## Decision

### D3 — The three-way merge is client-side, in the shared core
The client holds everything the merge needs: the **base** is the stored payload JSON the edit was queued
against (the store keeps verbatim API JSON per ADR 0075) plus its `version`. Replay flow per queue entry:

1. `PATCH`/`PUT` with `If-Match: <baseVersion>` ([ADR 0083](0083-change-feed-and-incremental-sync.md) D2).
2. 2xx → write the response into the store, drop the entry, bump the UI revision.
3. 412 → three-way merge (base vs. response state vs. queued payload), per field (merge semantics below):
   fields changed on one side only merge quietly and are re-sent with the fresh version; fields changed on
   both sides become **conflict entries** — the queue entry parks in state `conflicted` and surfaces in the
   UI. The user's resolution re-queues the entry with the resolved values and the fresh version.
4. 404/410 → the entity was deleted or merged meanwhile: drop the entry, surface a notice naming what
   happened (`mergedInto` pointer included when known). No automatic re-application.
5. 401 → the session ended: stop replay, keep the queue, show sign-in (D8).

Server-side merge was considered (the server's Revision history has the per-field base) and rejected: a
second merge implementation to maintain, merge semantics coupled to the server release cadence, and a break
in the uniform "API serves state, clients decide" contract. **One merge engine, in `shared/`** (the KMP core,
ADR 0073), pure Kotlin, JVM-tested, reusable by iOS later.

### Per-field merge semantics
Base = the store's payload + version the edit was queued against. Three field classes:

- **Scalars** (notes, `siteId`, each override scalar including reset-to-null, start time + UTC offset,
  duration, depths, temperature): classic three-way. Changed on one side → take it. Both sides differently →
  conflict. Same value both sides → no-op.
- **Small nested objects** (`suit`, `weightingFeedback`): treated as one field each; splitting inside them
  buys nothing the user can act on.
- **`cylinders` / `lead`: the whole list as one field.** The API sets them as whole ordered lists and items
  have no stable identity (composite key `(dive_id, position)`; position is not identity). Item-level merge
  would invent identity the server doesn't have. Accepted: these change rarely and almost never concurrently.
- **`participants`: element-wise by `diverId`.** The one list that must merge finely, because
  [ADR 0082](0082-joint-dive-sync.md)'s joint-dive union adds participants server-side as a matter of course —
  whole-list conflicts would fire constantly. Rules (mirroring ADR 0056's removal semantics): added on one
  side → merged in; removed on one side, untouched on the other → removed; removed on one side, role-changed
  on the other → conflict; role changed both ways differently → conflict; added on both sides with different
  roles → conflict.
- **`number`**: never editable ([ADR 0048](0048-dive-numbers.md)); the server already refuses it.
- **Joint-dive fill offers** (ADR 0082) are unaffected: they fill empty fields only and never overwrite; a
  queued local value simply wins the race to fill, and the offer evaporates.

### The conflict is user-decidable, own edit preselected
A both-sides-changed field is a decision for the user, shown with both values — the same UX as Provider
conflicts, worded **"this phone" vs "the server"** (the glossary's widened **Conflict**). The preselected
side is **the user's own fresh edit** — the intent they just expressed — not the server's value. Nothing is
ever silently overwritten, and nothing is ever silently lost.

### D4 — Queue model: two entry kinds, per-entity coalescing, durable in SQLDelight
A new store table **`pending_write`** (store schema v5): `id`, `kind` (`patch` | `action`), `entity_type`,
`entity_id`, `payloadJson` (field→value patch, or an opaque action payload + target endpoint),
`base_version` (null for actions), `status` (`pending` | `conflicted` | `failed`), `enqueued_at`,
`last_error`. Rules:

- **Coalescing:** repeated edits to the same `(entity_type, entity_id)` merge into one `patch` entry —
  latest payload, *original* base version. The user sees "1 change waiting", not seven.
- **Ordering:** `patch` entries replay FIFO per entity; `action` entries replay in global enqueue order
  (a queued create must precede anything referencing it). A dependent entry stays `pending` while its create
  is unplayed, and a failed create fails its dependents loudly, never silently.
- **Durability:** entries survive process death and app upgrades (store migrations must migrate the table).
  Replay is idempotent by construction (If-Match + 412-merge; client-supplied IDs for creates, D5).
- **Visibility:** a count + summary feed the "2 changes waiting to sync" surface; `conflicted` entries
  surface as "needs your decision". The queue is invisible when it works and honest when it doesn't: pending
  changes are visible, and their failure is a state, not a toast.

A write applies to the store at once (write as if online) and enqueues; no writeable feature ships
online-only, and no edit is a direct POST that bypasses the queue.

### D5 — Client-generated IDs: optional client-supplied uuidv7 on POST
Server create endpoints that native clients may call offline (dive-centre creation first, dive creation if
Phase D is approved) accept an optional client-supplied uuidv7 `id`. Additive per
[ADR 0076](0076-api-versioning-policy.md); replay-safe: a re-POST with the same ID answers 200/409-already-exists
with the entity, never a duplicate. The ID doubles as the replay idempotency key. Server IDs are already
uuidv7, so no format split exists.

### The 410 resync rebases the queue
When the feed answers 410 `sync_resync_required` ([ADR 0083](0083-change-feed-and-incremental-sync.md)) the
client rebuilds the store — and must NOT discard pending writes (queued writes are the user's data). After
the rebuild, each queued `patch` entry whose entity still exists re-runs the three-way merge against the
fresh state as if it had received a 412; entries whose entity is gone drop with a notice.

### D8 — Queue-aware session lifecycle
- **Explicit sign-out or user switch with a non-empty queue:** a blocking warning — "N changes haven't
  synced" — offering *Sync now* (stays signed in, drains) or *Discard and sign out*. No silent wipe.
- **401 / revoked session:** replay stops, the queue persists, the store stays readable (the approved
  offline-reads trade-off stands), sign-in is shown; on sign-in as the **same** user, replay resumes. Signing
  in as a **different** user is an explicit user switch → the warning above applies before the wipe.
- **Destructive verbs stay online.** Delete, merge, move, append-continuation and Provider-deletes have side
  effects across the instance and at Providers; offline they are shown disabled with the standard online-only
  wording ("no answer at all" family of the client contract), never queued. Possibly permanent — revisited
  after the dive-editing phase.

## Consequences

- The write path trusts the store: every offline-capable screen reads and writes locally, and the queue is
  the only bridge to the server. UI work is behind a feature flag until the conflict surface exists.
- Conflicts are rare (per-entity coalescing, fresh base versions from the feed) but possible by construction;
  the UX cost is one decision screen shared with the Provider-conflict pattern.
- The merge engine is the single most-tested component in this design: the field matrix above is pure
  functions, JVM-tested in the shared core, and the exit gate replays two-client races end to end.
- A phone can hold unsynced user data for months; the queue schema is as migration-critical as the read
  store's, and the sign-out guard is the one place the app may refuse a user action.
- Deletes and merges staying online means the native editor hides or disables them offline — a spec'd
  behaviour of the client contract, not an absence of a button.
- Once phones queue user data against this protocol, the queue semantics (base versions, coalescing, the
  conflict states) are as hard to reverse as the feed itself.
