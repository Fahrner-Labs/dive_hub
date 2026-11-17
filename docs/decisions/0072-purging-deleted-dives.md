---
title: "ADR 0072: Purging deleted Dives that no longer track anything"
summary: A deleted Dive is hard-deleted after 30 days when every Provider copy is gone (deleted there or found absent) and no Continuation still uses it; its Recordings stay as tombstones so a re-import is still skipped as deleted_earlier, and its Originals stay because another Dive can share them. A daily worker job does it.
status: accepted
date: 2026-11-17
---

# ADR 0072: Purging deleted Dives that no longer track anything

## Status
Accepted – 2026-11-17. Discussion and the split of the oversized task: [FAH-59](/FAH/issues/FAH-59). Amends
[ADR 0026](0026-deleting-dives.md) (which kept deleted Dives forever for lack of this decision).

## Context
A deleted Dive is a soft delete: the Dive and its Recordings get a tombstone, and the row keeps working after the User
has stopped seeing it ([ADR 0026](0026-deleting-dives.md)). It keeps three things:

- the **Recording keys**, so re-importing the same file is skipped as `deleted_earlier` instead of making the Dive again;
- the link to each **Provider copy** that is still there, so the list can remind ("Still in SSI") and the copy can be
  deleted later;
- the **history** of merges, moves and Continuations that name it.

Keeping those rows forever was a deliberate simplification, not a requirement. A tombstone whose Provider copies are all
gone and whose grace period has passed tracks nothing a User can still use, and it keeps rows and personal data around
without a reason.

The rule has to respect the two things a tombstone is still needed for: a re-import must still be skipped, and a
Continuation's parts must still make up the kept Dive's joined Recording. Deleting the Recordings with the Dive would do
the first one wrong: an old file imported again would recreate a Dive the User deleted. Deleting a Dive that a
Continuation uses would break the joined Recording.

## Decision
- **Purge rule.** A deleted Dive is purged when **every** Provider copy is gone — a confirmed delete there or a copy
  found absent — **and** `deleted_at` is more than **30 days** old.
- **What "every copy is gone" reads as.** For each Provider, the Dive's current remote dive is gone (the rule of ADR 0027:
  the last confirmed Push with an ID, unless a later delete or a `remoteGone` came after it), and no Waiting delete is
  still queued or held for it. A Dive that was never at a Provider qualifies at once.
- **What is kept.** The Dive's Recordings stay, detached (`dive_id` null) and still tombstoned, so their keys stay taken
  for every User and a re-import is still `deleted_earlier`. Originals and their files stay: an Original belongs to the
  User and can hold other Dives. A Dive that a Continuation uses as a part (`recording.from_dive_id`) or as the kept side
  (`continuation_append.kept_dive_id`) is not purged at all; it still tracks that Continuation.
- **What is deleted.** The Dive row, and with it every dependent row the schema cascades (Participants, Buddy
  suggestions, findings and their dismissals, logbook-check answers, Cylinders and Lead, leases, conflicts, Waiting
  pushes, Push-run items, Provider findings). Its Pushes and its own Revisions are deleted explicitly: they are its own
  history, not another row's.
- **How it runs.** A daily worker task (`purge-deleted-dives`, 03:37 UTC) purges every eligible Dive and logs how many.
  There is no route and no client change: a purged Dive simply stops appearing under "Deleted dives" and can't be
  restored.

## Considered options
- **Delete the Recordings too:** frees the most space, but the next import of an old file would recreate the Dive and
  undo the User's deletion. A separate key tombstone table would be a migration for the same result.
- **Purge at once when the copies are gone:** no grace period for restoring. The 30 days are the window in which
  "Deleted dives" and Undo still mean something.
- **Let the User purge by hand:** another dialog and a destructive action in the client; the tombstones are the app's
  housekeeping, not the User's decision.
- **Purge Continuation tombstones and rewire the joined Recording:** restores a deleted part only by rebuilding the
  append, for rows that no longer track anything the User can act on; leaving them is simpler and keeps the kept Dive's
  profile intact.

## Consequences
- "Deleted dives" is no longer forever: after the rule matches, a Dive disappears without a User action. The client
  contract needs no new request; the list and the reminder simply stop naming it.
- The 30 days are a fixed retention, not configurable per instance. If an owner needs longer, that is its own decision.
- A Continuation's parts and kept side can stay as tombstones indefinitely; they are the price of keeping the joined
  Recording whole.
- The daily job deletes rows in one transaction per Dive, so a failure leaves the rest for the next night.
