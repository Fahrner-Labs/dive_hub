---
title: "ADR 0076: API versioning policy for native clients"
summary: The v1 API grows additively; a field or endpoint is removed or re-typed only after an overlap window of at least six months in which both forms are served, announced in the release notes; the Android app identifies itself with User-Agent divehub-android/<version>, and a hard break is answered with the client_too_old problem code instead of URL versioning until one actually ships.
status: proposed
date: 2026-11-18
---

# ADR 0076: API versioning policy for native clients

## Status
Proposed – 2026-11-18. Discussion: [FAH-266](/FAH/issues/FAH-266), from the accepted plan
[FAH-265](/FAH/issues/FAH-265) (§9). The owner decides this ADR on the pull request (CODEOWNERS).

## Context
The web client ships with the server and is always current. The [MCP endpoint](../spec/clients.md#the-mcp-endpoint)
negotiates its protocol revision ([ADR 0035](0035-mcp-connector.md)). The Android app does neither: it is installed
through a store and stays on a phone for months, while a self-hosted server updates on its own schedule
([ADR 0068](0068-release-pipeline.md)). A server release must not break an app that was current when it was installed.

Two habits from the pre-mobile API cannot continue unchanged:

- **Replacing fields in one step.** [ADR 0029](0029-push-requirements-and-buddies.md) removed the site API's
  `ssiSiteId` and the export's `needsSiteIdFrom` at once; that was fine while every client updated with the
  server, but an installed app would lose them silently.
- **Treating a new enum value as harmless.** The Android models ignore unknown JSON fields (`ignoreUnknownKeys`),
  so a new response field is safe today, but a strict enum fails the whole answer on a value it does not know.

At the same time, compatibility cannot be unbounded: every old form is code, tests and an OpenAPI document that
must keep working while it is served.

## Decision

### v1 grows additively
- Adding an endpoint, a response field, or an optional request field does not need a version or an overlap window.
  Clients ignore response fields and endpoints they do not know ([client contract](../spec/clients.md#2-talking-to-the-api)).
- A new value of a closed enum is a break for a strict client and has no second form to overlap. It is added
  only when every supported client already tolerates it, which within v1 means the field's contract says an
  unknown value is shown as recorded (the device vocabulary is the pattern); otherwise the value goes in a new
  optional field, which is additive, while the old field stays until it can be removed under the overlap rule.
- A field's meaning, units and accepted values do not change within v1. A request that was valid stays valid: a
  new request field is optional, or has a default that matches the old behaviour.

### Removing or re-typing has an overlap window
- An existing field or endpoint is never removed or re-typed in one step. **Both forms are served for at least six
  months.** For a rename or re-type, the old field stays populated beside the new one; a removed endpoint keeps
  answering. The old form is marked `deprecated` in the OpenAPI document and the change is announced in the release
  notes of the server version that starts the window.
- The release notes say what changes, which two forms exist meanwhile, and the earliest release that may end the
  window. Six months is a floor, not a schedule: the old form is removed only in a later release, and only after
  the announcement.
- The clock starts with a server release, not with a commit on `main`: operators upgrade at their own pace.

### The app identifies itself
- Every Android request sends `User-Agent: divehub-android/<version>`, where `<version>` is the app's release
  version (`versionName`). It is for diagnostics and the hard-break gate only — never authentication, and never a
  trust signal. The server logs it with the request.
- Other clients keep their own User-Agent; a request without a recognised Android one is never gated by this
  policy.

### A hard break gets a problem code
- A change that genuinely cannot be overlapped (for example an authentication or security fix) may refuse requests
  from older app versions. The answer carries the problem code **`client_too_old`** (HTTP 400, not 401) in the
  usual `{ code, error }` body, and the client says that the app must be updated. It must not retry and must not
  show sign-in: the session is fine, the app is old.
- The User-Agent only chooses that message. **The break itself is enforced by the server's own logic for every
  request, whatever the client sends**; the gate is best-effort UX, not access control. The server sends
  `client_too_old` only to the versions it recognises as affected; an unrecognised client gets the ordinary
  refusal, never a bypass.
- The message is scoped to what the break affects. A hard break is a last resort, announced in the release notes
  like an overlap.

### No URL versioning until a break ships
- Everything stays under `/api`; there is no `/api/v1` and no `/api/v2` now.
- If a hard break actually ships, that release may introduce `/api/v2` alongside v1. v1 then keeps serving
  installed apps for the overlap above; where even that is impossible for a specific route, `client_too_old` is
  the answer. Introducing `/api/v2` before a break ships is not allowed.
- `/mcp` is outside this policy: it negotiates its own revision ([ADR 0035](0035-mcp-connector.md)).

## Considered options
- **Version the URL from the start (`/api/v1`).** Rejected: it gives every client a choice with one right answer,
  changes every path and bookmark, and creates an empty v2 before there is anything to put in it.
- **Keep replacing fields in one step, as before.** Rejected: an installed app that misses a field shows wrong or
  missing data with no error, and the first such change would strand phones until the store update arrives.
- **A custom `X-Client-Version` header.** Rejected: the User-Agent is the standard place for this and is already
  logged; a second header would be forgotten by clients and proxies more easily.
- **Serve every version forever.** Rejected: each old form costs code and tests, and the OpenAPI document stops
  describing what a current client should use. The six-month floor bounds the debt.
- **A hard break always means `/api/v2`.** Rejected as the first answer: a problem code tells the User what to do
  (update) without duplicating the whole API; v2 waits until a break is big enough to need it.

## Consequences
- A server change that removes or re-types something carries compatibility code, its tests and a release note.
  The OpenAPI document gains deprecations rather than losing entries.
- Generated clients keep working across additive releases; the Android core must keep ignoring unknown fields and
  must send the User-Agent on every request.
- `client_too_old` joins the error vocabulary and needs a translated string in every client; the client contract
  states the duty ([§2](../spec/clients.md#2-talking-to-the-api)).
- A hard break is still possible, but it is deliberate, announced and visible to the User instead of a silent
  failure.
- The policy covers `/api` only. The MCP endpoint and the AI-access keys keep their own compatibility rules
  ([ADR 0035](0035-mcp-connector.md)).
