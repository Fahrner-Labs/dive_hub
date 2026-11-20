---
title: "ADR 0080: Client diagnostics capture and ingest"
summary: Web and Android clients keep a small always-on local error ring buffer; a user-initiated "report a problem" uploads it to the own instance — only when an admin has enabled the instance-level client-diagnostics setting (default off) — where entries are redacted again, capped at 500 rows, purged after 90 days, and join the next support bundle as `client-diagnostics.ndjson` (formatVersion 2).
status: proposed
date: 2026-11-20
---

# ADR 0080: Client diagnostics capture and ingest

## Status
Proposed – 2026-11-20. Drafted by the CTO as part of the locked plan of the Paperclip issue
[FAH-292](https://paperclip.privatenode.de/FAH/issues/FAH-292#document-plan) (locked 2026-11-20), implementing
Option 3 of the log collection analysis approved by the CEO on [FAH-285](https://paperclip.privatenode.de/FAH/issues/FAH-285)
(2026-11-19). The ingest contract was reserved in [ADR 0078](0078-on-demand-support-bundle.md) / the
[FAH-291](https://paperclip.privatenode.de/FAH/issues/FAH-291) plan §8 D6. The owner accepts it by merging its
pull request: `docs/decisions/**` is CODEOWNERS-protected.

## Context
[ADR 0078](0078-on-demand-support-bundle.md) made server failures inspectable through the on-demand support
bundle and explicitly left client-side capture out: web and Android errors were invisible. The CEO asked for
client error capture "especially for Android/native clients", under the same privacy posture — nothing phones
home, off by default, data lands only on the customer's own instance.

The forces:

- **"Nothing phones home" stays absolute.** No third-party crash SDK, no Sentry DSN, no automatic upload.
  Egress remains a human act (ADR 0078's support-bundle model), now with one additional human act: a user
  pressing *Report a problem*.
- **Useful diagnostics need local persistence.** A crash handler that only holds errors in memory loses the
  crash it exists to record. A small on-device ring buffer is the minimum that makes *Report a problem*
  useful — and on Android it also powers the always-available *Share local logs* export the CEO asked for
  ([FAH-312](https://paperclip.privatenode.de/FAH/issues/FAH-312) §5.5).
- **The server bundle model said "nothing persisted server-side".** Client diagnostics break that rule
  deliberately and in one bounded place: uploaded entries must survive until the admin's next bundle, so they
  are stored — capped, redacted, expiring, and cascade-deleted with their user.

## Decision

### D1 — Local capture is always on; upload is doubly gated
Both clients keep a capped local ring buffer of errors at all times (web: localStorage, 50 entries / 64 KiB;
Android: sqldelight table in `:shared`, 200 entries). *Collection* — data leaving the device — requires both
the instance-level admin setting (default off) and an explicit user action (*Report a problem*). Android's
*Share local logs* (share sheet) is always available regardless of the setting. Clients discover the setting
through `GET /api/client-diagnostics/status`; a refusal at upload time (`403 client_diagnostics_disabled`) is
treated as "turned off meanwhile" and never loses the local buffer.

### D2 — One authenticated ingest endpoint, server re-redacts
`POST /api/client-diagnostics` (session cookie or bearer, ADR 0074) accepts at most 50 entries of
`{ occurredAt, level, code?, message ≤ 1000 chars, stack? ≤ 4000 chars, context? }`. The server truncates
over-long fields and runs every entry through the ADR 0078 redactor (R1–R3) before persisting — client-side
hygiene rules (no dive contents, no tokens, no URLs with query strings) are the first line, the server never
trusts them. Rate limit: 5 uploads per user per hour (`429 client_diagnostics_limited`), enforced as a count
query at insert — no new limiter infrastructure.

### D3 — Bounded server-side storage with expiry
A new `client_diagnostic` table holds at most the **500 newest** rows (enforced in the insert transaction) and
a daily worker task purges rows older than **90 days** (existing purge-cron pattern). Rows cascade-delete
with their user. A one-row settings table (the `backup_settings` pattern) holds the admin toggle.
Uploads are the only writer; there is no read-back API in v1 — the only reader is the bundle builder.

### D4 — Bundle formatVersion 2
The support-bundle builder's reserved `ClientDiagnosticsProvider` hook is wired unconditionally; bundles then
always carry `client-diagnostics.ndjson` (empty when the setting is off or no rows) and the manifest's
`formatVersion` becomes 2. Readers must tolerate absent members (ADR 0078).

## Consequences
- Easier: web and Android failures become debuggable from a support bundle; the privacy story stays
  one-paragraph simple ("nothing leaves unless a person sends it"); FAH-318's settings Debug section gets a
  stable contract.
- Harder: the server now persists user-generated diagnostic text — bounded (500 rows, 90 days), redacted
  twice, cascade-deleted, but persisted. The "nothing persisted" claim in ADR 0078 gains this one exception.
- Accepted: no per-user opt-out beyond not pressing *Report a problem*; no automatic crash upload after a
  restart (a later ADR could add an operator-gated one, default off); duplicate entries when a user reports
  twice before the buffer clears are tolerated (upload clears the buffer on success).
