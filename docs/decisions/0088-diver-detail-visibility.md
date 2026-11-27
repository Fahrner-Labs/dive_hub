---
title: "ADR 0088: Diver contact details are visible only to the people who share a Dive"
summary: A Diver's e-mail, leader number, account IDs and codes are contact details of someone who may never have signed up. DIVEHUB_DIVER_VISIBILITY defaults to instance (every User, ADR 0043, self-hosted unchanged); a hosted instance sets shared, where only the Diver's Users, the User who added an external Diver, admins, and Users who share a Dive with it see them. Everyone else sees the name parts only. Amends ADR 0043 slice 3 and, through it, ADR 0028.
status: proposed
date: 2026-11-26
---

# ADR 0088: Diver contact details are visible only to the people who share a Dive

## Status
Proposed – 2026-11-26. This ADR changes `docs/decisions/` and needs the owner's approval on its pull
request ([CODEOWNERS](../../.github/CODEOWNERS), [development.md](../development.md)). It amends
[ADR 0043](0043-dive-centres-and-ssi-verification-codes.md) slice 3 and, by consequence,
[ADR 0028](0028-shared-divers-and-participants.md). It was asked for by the hosted-DiveHub plan (FAH-524,
plan §7); the discussion stays in its Paperclip issue.

## Context
ADR 0043 slice 3 decided that **every User sees every Diver's** first and last name, e-mail, SSI leader
number, account IDs and the SSI codes built from them. That suits an instance of people who dive together:
one club, one family. A worldwide hosted instance is different. A buddy imported from one User's SSI list is
in that list because they dived together, not because they agreed to publish the e-mail and SSI account of a
person who may never have signed up. Every User of the instance could read them.

The hosted plan therefore asks for the same rule ADR 0043 wrote down as a possibility: "hiding the e-mail can
come later". This ADR is that later step, and it covers the whole group of fields that are one person's
contact details, not only the e-mail, because the codes carry all of them.

The self-hosted image must stay exactly as it is. The behaviour is opt-in environment configuration of the
same artifact (the hosted configuration model ADR 0087 introduces in its own pull request), never a fork or
a build flag.

## Decision

### The setting
- One new environment variable, `DIVEHUB_DIVER_VISIBILITY`: `instance` (the default) or `shared`. Any other
  value stops the app where the configuration is read.
- `instance` is ADR 0043 as built: every User of the instance sees every Diver's details.
- `shared` is the hosted rule below.
- It is read in `apps/server/src/config.ts`, passed through `compose.ts` to the one `DiverService`, and the
  same artifact serves both modes.

### The rule under `shared`
A Diver's contact details are visible to:
1. the Users who manage that Diver (their own Diver, a child's);
2. the User who added an external Diver (`created_by`) — it has no managing User, and otherwise the person
   who typed the details in could not read them;
3. admins, for support;
4. Users who share a Dive with that Diver: a User manages a Diver that is on the same Dive, as its own Diver
   or as a Participant. The relation is symmetric: it does not matter which side owns the Dive's logbook.

Everyone else sees the name parts only. A Dive that was deleted still counts: the Dive happened, and
soft-deleting it is the owner's private act, not a withdrawal of what the people on it saw. Purging a Dive
removes the row, so it no longer shares anything.

### What is hidden
The contact details are:
- the e-mail and the SSI leader number;
- the account IDs at services (the SSI user master ID, a PADI account once there is one);
- the buddy and professional codes, because their text is built from the fields above;
- the account sources in the external-Diver list, which say at which services a person has an account.

Everything else stays as ADR 0028 and ADR 0043 decided: every User still sees every Diver by name and may
put them on a Dive; the Diver's name parts, its Dives, devices and Connections keep their existing rules;
the centre directory and the centres' codes stay public.

### Where the rule is applied
- **One predicate**, `apps/server/src/divers/diver-visibility.ts`, as SQL so a list can ask it per row
  without a query per row. It is not stored: it reads `diver_management`, `diver.created_by`, `dive` and
  `participant` as they already are. No schema change.
- `GET /api/divers/{id}/details` redacts e-mail, leader number, accounts and codes for a User outside the
  circle (the name parts stay).
- `GET /api/external-divers` redacts the account sources for such a User.
- A Dive's `verificationCodes` are gated by the same predicate. A caller who may see a Dive manages its own
  Diver, so they share it with every Participant and see all their codes; a future caller who does not gets
  none.
- `POST /api/verification-codes/read` passes the viewer into the details it says the Diver has, so the
  "what would change" answer never prints a stored e-mail or leader number the viewer may not see. The
  code's own text is what the scanner already holds, and taking the code is a write path (below).
- The MCP tools read only names of other Divers (`divers_list`, `divers_buddies`); none of their columns is
  a contact detail, so `shared` needs no further gate there. The predicate is the one place for any future
  read that does.

### What stays as it was
- **Write paths do not change.** `PATCH /api/divers/{id}/details`, `PUT …/external-ids/{source}` and
  `POST /api/divers/from-code` follow ADR 0043's rules: an external Diver may be changed by any User, a
  managed one by its Users. Writing was never the exposure. A User may therefore set details they then
  cannot read back under `shared`; that is the rule, not an oversight.
- No new client duty for the fields: they were already nullable and the arrays could already be empty, so
  clients keep rendering what the API answers. The client contract's shape is unchanged (its sentence about
  who sees the details is the known follow-up under Consequences).

## Considered options
- **One setting only (`shared`)**: breaks every self-hosted instance's pickers and code sharing overnight;
  the dual-track invariant says no.
- **Hide only the e-mail**: the codes still carry it, and the account ID is the identity it is looked up by.
  The plan's "contact details" is the honest unit.
- **Hide the name too, except for Dive partners**: names are what every picker and Participant list needs;
  ADR 0028's sharing stays.
- **A per-Diver "private" flag**: more choices, a migration and UI for a problem the instance-wide rule
  solves; a Diver could also hide from their own Dive partners by mistake.
- **Only the managing Users, not the User who added an external Diver**: a scanned buddy's code would be
  the last the adding User ever sees, and the person who typed the details could not correct them.
- **Ask a live relation at every request with a new table**: no schema change is needed, and a table would
  need backfilling and invalidation whenever participants change.

## Consequences
- A hosted instance no longer publishes the e-mail, leader number, account IDs or codes of people who never
  signed up to every User. A self-hosted instance without `DIVEHUB_DIVER_VISIBILITY` behaves exactly as
  before.
- A User outside a Diver's circle sees the name parts, which is what ADR 0028 already made public, and can
  still put the Diver on a Dive.
- The rule is computational, so changing a Dive's Participants or a Diver's managers changes what is visible
  at the next request; there is nothing to invalidate.
- The predicate costs one indexed lookup per details request; the lists ask it in the same query. On an
  instance with `instance` (the default) it is the literal `true` and costs nothing.
- The external-Diver list no longer says at which services a stranger has an account. That is a small loss
  for the admin screens of a hosted instance; admins still see it.
- Hiding a field now has a second meaning (a value the viewer may not see versus one that is not set).
  Clients render both as "not shown"; the form only appears where the API already said `canEdit`.
- The `readPersonCode` answer may say a field would be set from `null` when the stored value is merely
  hidden; the code's own text is what the scanner already has, so nothing new is revealed.
- **Known follow-up:** the web client still says "everyone on this Dive Hub sees these details and codes"
  where it shows them. That sentence is true under the default; on a hosted instance it is not. Fixing it
  needs the API to say which mode is on (a new public setting field) and the client to pick its sentence, or
  a neutral reword. It is left out here because the dual-track invariant says a self-hosted instance must
  keep today's behaviour, and the server-side rule is the exposure this task closes.
- **Scope difference from the plan:** the plan named `apps/server/src/mcp/read-service.ts` and
  `tools-divers.ts` as places to thread the predicate. Their tools answer names only and select no contact
  detail, so there is nothing to gate; the test pins that. The one predicate is in
  `divers/diver-visibility.ts` for any read that does carry a detail.
- The plan also said the config model was documented in ADR 0087. That ADR is in a separate pull request
  that was not merged when this branch was cut; this branch adds the variable itself and the model in this
  ADR, and the two will need a small merge of `config.ts`/`compose.ts` when both land.
