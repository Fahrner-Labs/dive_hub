---
title: "ADR 0070: Buddy suggestions and Joint dives"
summary: A Participant whose Diver is not the Dive's own raises a Buddy suggestion for whoever manages that Diver, computed at read time; accepting it later creates or links a Dive in a Joint dive. This is Dive Hub's first cross-user visibility of a Dive, and it is limited to shared facts.
status: proposed
date: 2026-11-13
---

# ADR 0070: Buddy suggestions and Joint dives

## Status
Proposed – 2026-11-13 – by Elena. Discussion and the accepted plan:
[FAH-193](/FAH/issues/FAH-193); this slice: [FAH-194](/FAH/issues/FAH-194). The owner reviews and merges the pull
request, as the schema, migrations and decisions need their approval ([CODEOWNERS](../../.github/CODEOWNERS)).

## Context
A Dive lists its Participants (ADR 0028) by Diver, and every User sees every Diver by name. Dive Hub knows when a
Participant is another User's Diver, but nothing reaches that User: their logbook never learns that they were on the
Dive, and the Dive's owner cannot offer the shared facts of the descent. The product spec already promises the
opposite ("if the Diver is managed by another User, a Buddy suggestion is raised";
[data model](../spec/data-model.md#2-buddy-who-is-a-user-buddy-who-isnt)), and the social slice is where this first
becomes real.

The design must keep three constraints:
- Who manages a Diver changes: a User claims an external Diver through a Provider account, and an admin merges two
  Divers (ADR 0028). Anything stored about "the other User" goes stale exactly then.
- A suggestion must not leak the Dive's private data. Its owner's Recording, notes, suit, weighting, cylinders, lead and
  anything under `overrides` stay with the owner.
- Participants are written from many places: the dive edit route, a Provider import, a merge, a continuation and MCP.
  A rule hung on one route would be forgotten by the next.

## Decision
- **New tables.** `buddy_suggestion` (`id`, `dive_id`, `diver_id`, `status` in `pending | accepted | declined |
  cancelled`, `created_at`, `resolved_at`, unique `(dive_id, diver_id)`), `joint_dive` (`id`, `created_at`) and a
  nullable `dive.joint_dive_id` (a Dive belongs to at most one Joint dive). There is one migration for the three.
- **The trigger is managing-user-agnostic and deliberately small:** a `participant` row is added whose Diver is not the
  Dive's own Diver. Nothing else raises a suggestion: not adding a Dive, not an import creating one, not a Diver being
  claimed. A pending row waits even when no User manages the invited Diver (an external buddy); it gets an audience the
  moment someone claims or merges that Diver.
- **The audience is computed, never stored.** Every list, preview and live hint asks `diver_management` who manages the
  invited Diver at that moment. Claims and merges therefore re-target suggestions for free, and a suggestion never
  names a User in the database or on the wire.
- **The lifecycle follows the Participants.** Adding a Participant with no suggestion inserts `pending`; a `declined`
  or `cancelled` one becomes `pending` again (and `resolved_at` is cleared); removing the Participant cancels a
  `pending` one; soft-deleting the Dive cancels its `pending` ones. `accepted` and `declined` rows are never
  overwritten by a later Participant change. This lives in `social/`, and the `ChangeListener` seam already owned by
  `revisions/` calls it inside the writing transaction (`changes.participants`, or a new tombstone), so every write
  path is covered without each module remembering to call it.
- **First cross-user visibility, limited to shared facts.** The preview route shows the invited Diver the site, start
  time with its UTC offset, duration, max and average depth as starting values, the Dive's own Diver by name, its
  Participants by name and any Dive centre responsible for the site. It shows nothing of the Recording, notes, suit,
  weighting, cylinders, lead or `overrides`. Accepting (a later slice) may copy only those shared facts or link an
  existing overlapping Dive into the Joint dive; `merging-service.merge` stays same-Diver-only, so a Joint dive never
  merges one Diver's data into another's.
- **A new live hint kind, `buddySuggestion`,** is emitted in the same transaction to the Users managing the invited
  Diver (the actor may be among them, or there may be nobody). It names the suggestion id only; the client refetches
  through the API, as every hint does (ADR 0066).

## Consequences
- Suggestions are cheap to re-target and need no backfill when a Diver is claimed or merged; a pending row with no
  audience costs nothing and becomes visible later.
- Every Participant write must keep writing a Revision: that is how the suggestion rule reaches it. A future write path
  that changes Participants without a Revision would not raise or cancel anything.
- A User who manages several Divers sees each suggestion once; a Dive with several managed Participants raises one
  suggestion per invited Diver, each with its own lifecycle.
- The preview is the first place where one User reads facts of another User's Dive. Any later field added there is a
  visibility decision, not a formatting one.
- `joint_dive` is created empty in this slice. Accepting, declining, the Joint dive's own visibility and the copy/merge
  behaviour come in the following slices; this ADR fixes only the tables, the trigger and the audience rule they build
  on.
