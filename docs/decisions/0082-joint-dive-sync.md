---
title: "ADR 0082: Joint dive sync — derived fill offers, stored dismissals and Participant union"
summary: A Joint dive keeps its members' shared facts in step without silent copies. Participants union on link and on later adds, removals never propagate, and a member short of a shared fact is offered another member's value; only a decline is stored. Individual facts never cross.
status: proposed
date: 2026-11-22
---

# ADR 0082: Joint dive sync — derived fill offers, stored dismissals and Participant union

## Status
Proposed – 2026-11-22 – by Elena. Requested in [FAH-409](/FAH/issues/FAH-409); accepted plan revision
`9d5dea0c-ca0b-447f-857b-126422e37a05` there; this slice: [FAH-414](/FAH/issues/FAH-414). Builds on
[ADR 0070](0070-buddy-suggestions-and-joint-dives.md), whose accept branches are built and shipped. The owner accepts
this ADR by merging the pull request: `docs/decisions/**` is CODEOWNERS-protected.

## Context
[ADR 0070](0070-buddy-suggestions-and-joint-dives.md) gave a Joint dive its tables and two ways to join it: accepting
without a Dive creates one from the shared facts and copies the source's Participants; accepting an existing
overlapping Dive links it and copies nothing. After that, the members went their own way. Adding a buddy to one Dive
left the other members' lists short, and a member who gained a site, a start time or a depth learned nothing from the
buddy's fuller Dive. [FAH-409](/FAH/issues/FAH-409) asks for both, while keeping a clear line between facts the descent
shares and facts that belong to one Diver.

Constraints that shape the answer:

- **Privacy is unchanged.** ADR 0070 lets one User read only the other member's shared facts; Recording/profile, notes,
  suit, weighting, cylinders, lead, Overrides and provider links never cross.
- **Nothing may go stale.** Who manages a Diver changes through a claim or a merge (ADR 0028), and a member Dive can be
  edited, imported into, merged or deleted at any time.
- **Participants are written from many places.** The edit route, an Import, a merge, a continuation and MCP all write
  them; a rule hung on one route would be forgotten by the next.
- **Never silently overwrite.** Each User's own value on their own Dive wins; sync may fill a gap, not replace a value.

## Decision

### D1 — Participants union automatically; removals never propagate
When Dives join a Joint dive, every member gains every other member's people: the other members' own Divers as
`buddy`, and their Participants with their roles, with the receiving Dive's own Diver excluded. A person already on a
member keeps that member's role. The union runs on link (both accept branches re-check it) and again whenever a
Participant is added to any member, fanned out to every other member in the same writing transaction. A removal stays
on the Dive where it was made: there is no unlink and no removal propagation. Every path that writes Participants
already writes a Revision, so this lives in the existing `withSuggestionChanges` `ChangeListener` seam and not on one
route.

### D2 — One new table for dismissals; offers are derived
`joint_dive_dismissal` (`id`, `joint_dive_id`, `dive_id`, `field`, `created_at`, unique
`(joint_dive_id, dive_id, field)`) is the only state the feature stores. It records "do not offer this field for this
Dive of this Joint dive again". It cascades with the Joint dive and with the Dive, so a deleted member's dismissals go
with it, as do those of a Joint dive left with fewer than two live members.

A **fill offer** is computed at read time, never stored. For each of a User's Dives with a `joint_dive_id`, its shared
fields are compared with the other live members': a field that is empty on the User's Dive and filled on another
member is offered, unless a dismissal covers it. With more than two members the newest filled value is the source.

### D3 — Two classes of shared facts, and the rest stay individual
| Class | Fields | Rule |
|---|---|---|
| Shared — auto-synced | Participants | Union on link and on later adds; roles kept; removals never propagate. |
| Shared — offered to fill | `siteId`, `startsAt` + `utcOffsetSeconds`, `durationSeconds`, `maxDepthM`, `avgDepthM` | Offered only where the receiving Dive's field is empty; accepting copies the source's current value; never overwrites. |
| Individual — never crosses | Recording/profile, notes, suit, weighting, cylinders, lead, `overrides`, provider links | Never read by another User, never offered. |

Two filled values are neither an offer nor a conflict: each Dive keeps its own. The offered set is the same one
ADR 0070's suggestion preview already exposes, so this adds no new visibility class.

### D4 — Accept writes through the Revision machinery; decline stores a dismissal
`POST /api/joint-dives/offers/accept` locks the receiving Dive, re-checks that it is still a Joint member and that the
requested fields are still empty, reads the source members' **current** values and writes them as the Dive's own values
through a system Revision with cause `joint-dive-fill`. A field filled meanwhile, a lost source value or a Dive that
left its Joint dive answers 409 `joint_offer_not_valid`. `POST /api/joint-dives/offers/decline` writes one dismissal
row per field; answering twice is a no-op by the unique key.

The read surface is `GET /api/joint-dives/offers` (every offer for the User's Dives) and
`GET /api/dives/{id}/joint-offers` (one Dive). Both return only the shared fields above; the individual list never
leaves the owning User's Dive.

### D5 — A live hint names the Joint dive
A new `jointDiveOffer` hint is emitted inside the writing transaction for a shared-field edit, a link and a member
leaving, through the same `ChangeListener` seam. Clients refetch the offers and Dives, as with every hint (ADR 0066).

### D6 — One idempotent backfill for Joint dives linked before the union
Migration `0064_union_joint_dive_participants` applies the same union to every Joint dive that already existed when
D1 shipped. It adds only, keeps a member's existing roles, excludes each member's own Diver and is a no-op when run
again. It raises no suggestions and no hints, exactly as the link-time union does.

## Consequences
- A User sees another member's shared fact only as an offer on their own Dive, and a decline is remembered per Dive and
  field without any offer row to clean up. Editing, importing, merging, deleting or a member leaving resolves an offer
  by itself.
- Participant lists converge on every add but may diverge on a removal; re-adding the person propagates again.
- Every new shared field is a visibility decision: it must be added to the offered set deliberately, never as a
  formatting change.
- The dismissal table grows only when a User declines. A Joint dive deleted for having fewer than two live members
  takes its dismissals with it.
- The backfill runs once at upgrade; a database that had no Joint dives pays a single empty query.

## Out of scope
- Unlinking a Dive from a Joint dive, and propagating removals.
- Treating two different filled values as a conflict.
- A Joint-dive page of its own; the shared facts reach another User only through the offers in D4.
- The Android client, which has no Buddy-suggestion surface yet; the API stays client-agnostic.
