---
title: "ADR 0078: On-demand support bundle"
summary: An admin-triggered, redacted support bundle — a bounded in-memory log ring buffer feeding a tar.gz of version, redacted configuration, recent logs, failed background jobs and health stats — is generated in memory on demand and leaves the instance only as a human download; a client-diagnostics ingest contract is reserved for FAH-292.
status: proposed
date: 2026-11-20
---

# ADR 0078: On-demand support bundle

## Status
Proposed – 2026-11-20. Drafted by the CTO as part of the locked plan of the Paperclip issue
[FAH-291](https://paperclip.privatenode.de/FAH/issues/FAH-291#document-plan) (locked 2026-11-20), implementing
Option 2 of the log collection analysis approved by the CEO on [FAH-285](https://paperclip.privatenode.de/FAH/issues/FAH-285)
(2026-11-19). The owner accepts it by merging its pull request: `docs/decisions/**` is CODEOWNERS-protected.

## Context
When a self-hosted instance misbehaves, Fahrner Labs today asks the operator for `docker compose logs` output:
clumsy for non-technical operators, and raw logs can carry other users' e-mails and IP addresses. Web and
Android client failures are invisible entirely. The rejected alternative was a Sentry-style local sink
container: always-on collection of every user's data on a machine we don't control, plus operational weight
that contradicts [ADR 0004](0004-system-architecture.md)'s resource budget (2 GB NAS boxes, deliberately two
containers).

The forces:

- **"Nothing phones home" is a product property.** State leaves an instance in exactly one way today:
  encrypted, user-initiated `.divehub-backup` archives ([ADR 0069](0069-full-instance-backup-and-restore.md)).
  Diagnostics must follow the same pattern: collection is capped and local; egress is a human act.
- **One process per data volume.** No new container, no disk-persisted logs, no separate collection process.
- **A bundle an admin sends us must not leak the other users of that instance.** E-mail addresses, IPs and
  tokens have to be masked before the archive is built — and ideally before the data rests anywhere at all.

## Decision

### D1 — A bounded in-memory ring buffer at the pino stream seam
The server keeps a bounded in-memory ring buffer of recent structured logs alongside the existing stdout
pino logger. The seam is the Fastify `logger` option in `apps/server/src/start.ts`: a single custom
`Writable` stream forwards every line to `process.stdout` unchanged and best-effort captures it —
`JSON.parse`, redact, push into the buffer. No pino import (pino is not a declared dependency), no new
process, no disk writes. Capture failures are swallowed; the logging path can never throw because of the
buffer. The worker and all services log through the same `app.log`, so the default in-process deployment is
captured whole.

Capacity is **2048 entries and 2 MiB** of serialized payload, whichever bound hits first, evicting oldest;
both are module constants, not new environment variables. Worst case is a few MB of RAM. There is no time
window; the manifest and preview report `entries`, `droppedEntries`, `oldestAt` and `newestAt` so the admin
sees the real coverage window. The buffer captures whatever `DIVEHUB_LOG_LEVEL` lets through.

### D2 — Redaction at capture, whitelist sourcing
One pure redactor (`apps/server/src/support/redact.ts`) is applied at buffer capture — the buffer never
holds raw PII — and again over the configuration and job sections at bundle build. Rules: a key-name
denylist (`token`, `secret`, `password`, `authorization`, `cookie`, `passphrase`, `apikey`, `credential`,
matched on normalized keys at any depth) censors values to `‹redacted›`; bounded e-mail and IPv4/IPv6
patterns mask to `‹email›`/`‹ip›`. Redaction counters land in the manifest. The regexes are bounded so an
adversarial log line cannot cause backtracking cost.

Bundle content comes only from enumerated sections. The service never touches the secrets store and never
queries dive or user content: Provider credentials and dive contents are excluded by construction, not by
filtering. The configuration section enumerates the known `DIVEHUB_*` variables with a per-key policy
(shown verbatim / credentials masked / presence only / fingerprint only for `DIVEHUB_ENCRYPTION_KEY`,
following ADR 0069 D4), so a future variable cannot leak by accident.

The deliberate boot-time setup-token warning (ADR 0012) is plain text and survives redaction. Accepted: the
token is single-use, valid 24 h and dead once the first admin exists, and only an admin can download a
bundle.

### D3 — One in-memory tar.gz, generated on demand, never stored
The bundle is `divehub-support-bundle-<timestamp>.tar.gz`, built in memory by a minimal USTAR writer plus
`node:zlib` (no shelling to `tar`, no new dependency, no disk write). Members: `manifest.json`
(`format: 'divehub-support-bundle'`, `formatVersion: 1`, app and schema versions, buffer and redaction
counters, section sizes), `logs.ndjson`, `config.json`, `jobs.json`, `health.json`.

Failed background jobs come from `graphile_worker.jobs` (`last_error is not null`, newest 50; the `payload`
column is never included) and failed `backup_run` rows (newest 20), both redacted. Health stats cover uptime,
memory, DB latency, worker state and queue depth; a failed DB probe degrades the section to
`{ available: false }` instead of failing the bundle. Generation is stateless: preview and download each
build fresh, nothing is persisted, so there is no retention or cleanup story.

### D4 — Two admin routes behind the existing guard
`GET /api/admin/support-bundle/preview` returns the summary the admin reviews in the UI;
`GET /api/admin/support-bundle/download` returns the archive. Both sit in a new leaf module
`apps/server/src/support/` behind `requireUser` + `requireAdmin`, registered like `backupRoutes`, with
inline TypeBox schemas so the OpenAPI document (and the regenerated api-client) covers them. The web client
gets one page next to Admin → Backups: create → review summary → download.

### D5 — Client-diagnostics ingest contract reserved for FAH-292
The bundle format reserves `client-diagnostics.ndjson` (formatVersion 2) and the builder takes an optional
diagnostics provider. The contract for [FAH-292](https://paperclip.privatenode.de/FAH/issues/FAH-292) is
fixed in the plan: an instance-level admin setting (default off), `POST /api/client-diagnostics` with a
bounded, code-based payload, server-side re-redaction with the same redactor, a capped and purged table, and
rate limiting. Native clients are designed against this contract; FAH-292 owns its server slice (setting,
migration, endpoint, purge, bundle member).

## Alternatives considered
- **Sentry-style sink container (or GlitchTip).** Rejected in the FAH-285 analysis: always-on collection,
  operational weight against ADR 0004, and it still doesn't solve consent/redaction on egress.
- **Persist the generated bundle** (history, audit). Rejected: storage brings retention and access questions;
  the bundle is regenerable on demand, and "nothing rests" is the stronger privacy property.
- **`pino.multistream` with an explicit pino dependency.** Rejected: a single custom `Writable` keeps stdout
  byte-identical and adds no dependency; multistream would be justified only if we needed more than one extra
  destination.
- **Dump `process.env` for the configuration section.** Rejected: any future secret would leak silently.
  The enumerated whitelist makes including a new variable a conscious act.
- **Server-side storage of client diagnostics inside the ring buffer** instead of a table (FAH-292). Rejected:
  client reports must survive restarts to be useful, and a capped, purged table is the honest bound.

## Consequences
- Operators get a one-click diagnostic that is safe to hand over; support stops asking for raw `docker
  compose logs` output.
- Log redaction is best-effort pattern matching: it covers e-mails, IPs and keyed secrets, not arbitrary
  personal data someone logs into a free-text message. The review step in the UI exists because of this.
- With the optional separate-worker deployment the buffer lacks worker-only lines (the same reduced-behaviour
  trade ADR 0069 D3 accepts for that deployment).
- `formatVersion` and the reserved member give FAH-292 a stable seam; readers must tolerate absent members.
- The redactor's rules and the configuration whitelist are the two places future features must remember to
  extend; both are covered by tests.

## Out of scope (v1)
Recorded here so the omissions are deliberate:

- client-side capture (web/Android) — [FAH-292](https://paperclip.privatenode.de/FAH/issues/FAH-292);
- a "share with Fahrner Labs" upload endpoint — a later, explicit, logged option;
- stored bundle history, scheduled bundle creation, or any automatic egress;
- disk-persisted logs or log rotation.
