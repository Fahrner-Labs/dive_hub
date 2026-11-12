---
title: SSI External API (MySSI developer program)
summary: SSI's official partner API (v1, HMAC-signed), granted to Dive Hub through the SSI developer program - endpoints granted to our application (divelogs read/batch upsert, user signup handshake, self-documentation), the auth model, what it replaces and what it cannot do compared with the private app API, and the open questions for SSI. Credentials are never recorded here.
status: living
date: 2026-11-12
url: https://api.divessi.com/rst/extern/v1
---

# SSI External API (MySSI developer program)

## What it is

SSI's official, supported partner API, accepted for Dive Hub on 2026-11-12 through the SSI developer program.
The authoritative reference is the document SSI issued for our application, kept as the Paperclip document
`ssi-api-dive-hub-persnliches-tauchlogbuch-mit-ssi-anbindung` on issue FAH-191; the comparison against our
implementation lives in the `ssi-external-api-review` document on the same issue. This page records what
Dive Hub may rely on.

- **Base URLs:** production `https://api.divessi.com/rst/extern/v1`; test `https://api.divessi-test.com/rst/extern/v1`.
  The test environment is basic-auth gated and its database is **wiped and re-copied from production every Sunday**;
  test fixtures must be re-creatable.
- **Auth model:** an application key (`x-ssi-key`) plus an HMAC-SHA256 signature (`x-ssi-signature`) over
  `METHOD\nURI\npayload`, where the payload is the auth headers merged with the JSON body (keys lowercased,
  nulls skipped, sorted, PHP-style query string, URL-decoded). `x-ssi-timestamp` must be within 5 minutes of the
  server clock; each signature is accepted exactly once (`AUTH_REPLAY_DETECTED` on reuse), so retries re-sign
  with a fresh timestamp. User-scoped routes add `x-ssi-user-id` and `x-ssi-user-token`.
- **Credentials:** the application key, HMAC secret and test basic-auth password are secrets. They live in the
  operator's secret store (proposed as `integrations/ssi/hmac-secret` and
  `integrations/ssi/test-basic-auth-password`), never in this repository. Server-side only.
- **Envelope:** success `{ "ok": true, "data": ... }`; every failure
  `{ "error": { code, http_status, message, detail } }`. Clients switch on `error.code`; the catalogue is
  machine-readable at `GET /documentation/errors`.
- **User types:** routes are scoped to USER (a diver), CENTER (a dive centre) or SERVICECENTER, granted per
  application. Ungranted routes answer `NO_ACCESS` / `NO_ACCESS_USER_TYPE`; access is requested from SSI with a
  reason and tracked by the `pending_request` flag.

## Endpoints granted to Dive Hub's application

From SSI's reference (only these exist for our application; `GET /documentation/available` is the live source of
truth):

| Route | What it does |
|---|---|
| `GET /divelogs` | A user's complete logbook, or one dive by id. Canonical UDDF-aligned vocabulary, metric only. |
| `POST /divelogs` | Create **one or more** dives in one request. **Upsert** keyed by `partner_dive_id` (required, unique per application, ≤ 255 chars, UUID v4 recommended); re-posting updates. Soft delete via `deleted`. Strict validation: unknown keys are rejected (`PARAM_INVALID`), imperial twin keys are internal. |
| `POST /requestsignup` | Diver sign-up / token handshake between the two APIs; `callback_url` required, extra body params are appended to it. Details ("contact the SSI IT Team") are not in the reference. |
| `GET /center/getVarSets` | CENTER-scoped system variable lists; not relevant to the personal-logbook scope. |
| `GET /documentation/{errors,available,endpoints,general}` | Error catalogue, per-application route grants, OpenAPI 3.0 export, signing examples. |

Notable canonical fields: `datetime` (ISO 8601 **UTC**, required), `duration` (**seconds**, converted server-side),
`maxdepth` (required), `divetype` (scuba/xr/scr/ccr/freedive/snorkel), `samples` (flat array of signed depths at a
partner-chosen interval, stored verbatim), `events`, `buddy_ssi_mids` (SSI account IDs directly), `log_number`
(partner-assigned), `rating`, `visibility`, `weather`, `waterbody`, `watercurrent`, `watersurface`, `airtemperature`,
`decomodel`, `gf_low/high`, `cns_start/end`, `otu_start/end`, tank/gas fields, alarms, and extensive
freedive/snorkel/XR/SCR/CCR sections. `divesite_id` references a `GET /divesites` route that is **not** granted to
our application; otherwise name + lat + lon are sent.

## What we take from it

- A supported replacement for the riskiest parts of the app-API integration ([ssi-app-api.md](ssi-app-api.md)):
  no stored passwords (signup handshake with user tokens), no app User-Agent impersonation
  ([ADR 0049](../decisions/0049-ssi-app-user-agent.md)), a stable upsert key instead of the `divehub-<id>`
  reference smuggled through `odin_user_log_divecomputer_dive_ref`, batch saves instead of one paced call per dive
  ([ADR 0047](../decisions/0047-push-runs.md)), UTC timestamps and second-resolution durations, and a test
  environment for contract checks.
- A partner dive number (`log_number`) as a supported form of [ADR 0048](../decisions/0048-dive-numbers.md)'s
  number following.
- Buddy references as plain SSI account IDs on pushed dives, instead of buddy-list entry IDs
  ([ADR 0029](../decisions/0029-push-requirements-and-buddies.md)).

## Notes

- **Losses vs the app API:** profiles are depth-only (no temperature, tank-pressure, heart-rate, CNS or
  per-sample NDL/GF series; we send all of those today); no site management or private sites
  (`save_divesite`/`get_divesite`); no buddy-list management (`get_buddies`/`save_buddy`); no certifications
  (`get_ccards`, [ADR 0050](../decisions/0050-certifications.md)); no entry/exit positions; dives stay
  unconfirmed either way (verification remains the QR flow, [ADR 0043](../decisions/0043-dive-centres-and-ssi-verification-codes.md)).
  The app API remains the integration for those until SSI grants more routes.
- **Access:** the key belongs to the *application*, not to individual divers or operators. Divers connect through
  `requestsignup` and need no developer-program account. A self-hosted instance cannot share our secret and would
  need its own developer-program application — whether SSI covers all DiveHub deployments under one registration
  is an open question for them.
- **Open questions for SSI** (full list in the FAH-191 review document): the `requestsignup` handshake details
  and token lifetime; deployment model for self-hosted instances; site resolution (`divesite_id` vs
  name+coordinates); sample-interval contract, payload and rate limits; adopting dives previously pushed via the
  app API under a `partner_dive_id`; which further routes are grantable; scripted fixture creation around the
  weekly test-environment reset.
- **In Dive Hub:** nothing implements this API yet (checked 2026-11-12). The existing app-API integration is
  documented in [ssi-app-api.md](ssi-app-api.md); any official-API work goes through the Provider layer
  ([ADR 0027](../decisions/0027-providers-as-adapters.md)) and needs its own ADR first.
