---
title: "ADR 0069: Full-instance backup and restore"
summary: One encrypted `.divehub-backup` archive holds the database and the data directory; manual archives use a passphrase through scrypt, scheduled ones an operator-held X25519 recipient; DIVEHUB_ENCRYPTION_KEY is excluded and only its fingerprint is recorded. Restore is primarily the first-start setup screen, with the operator CLI as the secondary path; maintenance mode is deliberately not built.
status: proposed
date: 2026-11-12
---

# ADR 0069: Full-instance backup and restore

## Status
Proposed – 2026-11-12. Drafted by the software engineer from the locked plan of the Paperclip issue
[FAH-175](https://paperclip.privatenode.de/FAH/issues/FAH-175) (plan v1.1, approved 2026-11-12, including the
CODEOWNERS touches). The owner accepts it by merging its pull request: `docs/decisions/**` is CODEOWNERS-protected.

## Context
Dive Hub is self-hosted ([ADR 0004](0004-system-architecture.md)): one application image contains the API, the web
client and the worker; the state lives in two places, PostgreSQL and the data directory on a volume. An operator who
loses the host, the volumes or the database has to be able to come back to the exact instance — dives and their
samples, Originals, sites, equipment, certifications, users and sessions, settings and Provider connections with their
encrypted credentials. The self-hosting guide previously described only `pg_dump` plus a `tar` of the files volume:
two artefacts, no manifest, and whatever protection the operator happened to add.

Four forces shape the answer:

- **The archive must be safe to store wherever the operator puts it.** A Dive Hub instance contains personal data,
  and the scheduled case writes archives to the server itself. Encryption had to be a property of the format, not an
  optional operator step.
- **`DIVEHUB_ENCRYPTION_KEY` is deliberately outside the database** ([ADR 0024](0024-ssi-target-via-app-api.md)):
  it decrypts what Dive Hub keeps for Targets, so it must not travel inside an archive that also holds the ciphertext.
- **Restoring onto a running instance is a different problem from disaster recovery.** It needs writes drained, the
  worker stopped and sessions invalidated; a half-restored live instance is worse than a stopped one.
- **First start is already a safe window.** With zero rows in `user`, no session can exist, the only write path is the
  setup-token-protected first-admin route, and the worker is held until setup completes. That is exactly the state a
  restore needs.

The original plan for v1 was an operator CLI only. It was superseded during planning: the common recovery is a new
machine with the archive on a disk, where a browser and the setup token are available but a shell and the matching
image are not necessarily. The CLI stays for staging resets and scripted recovery.

## Decision

### D1 — Instance state, in one container
An archive is a tar container with three members:

| Member | Content |
|---|---|
| `manifest.json` | Format and app version, migration journal head, creation time, row counts per public table, a checksum of the file tree, SHA-256 and size of both parts, the `DIVEHUB_ENCRYPTION_KEY` fingerprint, and the encryption metadata |
| `database.dump` | The whole database in `pg_dump` custom format (`-Fc`), including the `graphile_worker` schema |
| `files.tar.gz` | The contents of `DIVEHUB_DATA_DIR`: Originals, media, `incoming/`, the generated auth secret |

The file tar leaves out `backups/`, `tmp/`, `restore/` and `run/`: the backup must not contain earlier archives, its
own temporary files, a restore upload or the app's ephemeral lock/heartbeat files. The dump is one MVCC snapshot; the
files are copied afterwards, and the only mutable part, a transient upload in `incoming/`, is harmless. The manifest's
row counts and the file-tree checksum are recorded for diagnostics and spot checks; the restore itself verifies the
part checksums, the key fingerprint and the schema head.

The format has its own magic and version byte. A reader refuses a version it does not know; changing the container in a
way an older reader cannot take bumps `BACKUP_FORMAT_VERSION` and needs a reader that understands both.

### D2 — One encrypted stream, no unencrypted path
The container is encrypted as a single AES-256-GCM stream. Nothing inside — not the manifest, the table names, the
file names — is readable without the key. The file begins with a small cleartext header that carries only what the
reader needs to derive the key: magic, format version, KDF id, and that KDF's public parameters (salt and IV, plus the
ephemeral public key for a recipient). The 16-byte GCM tag authenticates the whole archive: a truncated, modified or
wrong-key archive fails before `pg_restore` or `tar` runs.

Encryption is mandatory. There is no unencrypted archive variant and no "skip encryption" switch, so an archive cannot
become plaintext by accident or by convenience.

### D3 — Two envelopes: passphrase and X25519 recipient
**Manual backups** are encrypted with a passphrase an admin enters for that run. It is at least 15 characters, the
same minimum the app applies to passwords: an archive is stored anywhere, so the passphrase must resist an offline
crack. The passphrase is normalised (NFKC) and stretched with scrypt (`N = 16384`, `r = 8`, `p = 1`, a 16-byte salt
and a 12-byte IV). The passphrase is used for that
run only: it is never stored, logged, put in the address or sent to a client again. It travels only in the memory of
the app process that accepted the request to the in-process worker; a deployment that runs the optional separate worker
service, or routes the job to another app process, fails a manual run instead of writing the passphrase to a table or a
job payload. Scheduled runs are unaffected. The manifest records the KDF parameters so a later reader can explain and
verify the archive.

**Scheduled backups** are encrypted to an X25519 **recipient** the operator holds the private key for. The server
generates an ephemeral keypair per archive, derives a shared secret with the recipient's public key and turns it into
the AES key with HKDF-SHA256. The private key never reaches the server: the server can write backups but cannot read
them back. Recipients and private keys use age's bech32 text (`age1…`, `AGE-SECRET-KEY-1…`) so `age-keygen` can create
them, but the container is Dive Hub's own and `age` itself cannot open it. Borrowing the key text avoids inventing a
key-generation story for operators; borrowing age's container would add a binary dependency and hide the manifest from
our own restore.

### D4 — `DIVEHUB_ENCRYPTION_KEY` never enters the archive
The key is excluded on purpose. It is already outside the database, and putting it into the same archive as the
encrypted Provider credentials would defeat that separation. The manifest carries only its SHA-256 fingerprint and a
`plaintextCredentials` flag.

Before the instance is touched — before its files or database are replaced — a restore compares the fingerprint with
the key in the target's environment. The archive is unpacked to `dataDir/tmp/restore-*` first; a refusal or a damaged
archive leaves the instance as it was:

- both present and equal: proceed;
- both absent: proceed, because the backup was made without a key and its credentials are plain text;
- one absent or the fingerprints differ: refuse, because the stored credentials would be undecryptable.

A backup made without the key restores only while the variable is unset, and the restore warns that its Provider
credentials are plain text. The key remains the operator's secret, kept apart from every archive; losing it loses the
Provider credentials (users sign in again), not the dives.

### D5 — First-start restore is the primary path; the CLI is secondary
A fresh instance shows the setup screen with two paths: **Start fresh** (today's first-admin form) and **Restore from
backup**. The restore path is protected by the same setup token, takes the archive's key, and accepts either a browser
upload (streamed to disk, no fixed size cap) or an archive the operator placed in `/data/restore/`. The restore core
decrypts and verifies the archive, checks the key fingerprint and the migration head, replaces the managed files and
then replaces the database's schema state: it drops and recreates the non-system schemas (`public`, `drizzle` and the
worker's schema) and replays the dump with `pg_restore --no-owner --no-privileges --single-transaction`. Cleaning
individual objects instead cannot work, because `--clean` only drops what the archive holds and a newer image's
surviving tables and foreign keys then block the restore. With the schema replaced, a backup whose head the image knows
restores and the forward migrations settle it on the next boot. A failed run leaves the database wiped; on the
first-start path it is disposable and the setup token stays usable for a retry. On success it answers, then the
process exits so it cannot keep running on a database it just replaced; the operator runs `docker compose restart app`. On the next boot,
forward migrations bring an older-schema backup up to the image's schema and sign-in works with the backed-up accounts.

This is safe because it happens in the setup window described above: no session exists, no authenticated route is open,
and the worker is held until setup completes. A refused restore leaves the setup token usable, so a wrong passphrase or
a damaged archive can be retried; the database is still the fresh, empty one. Restoring an archive from a newer Dive
Hub is refused by the migration-head check.

The **operator CLI** (`restore-backup`, wrapped by `scripts/restore-backup.sh`) runs the same core while the app is
stopped. It takes the key from the environment or a mounted file, refuses while the app's heartbeat lock is fresh
(`--force` overrides after a crash), and tells the operator to start the app again. It exists for staging resets,
scripted recovery and operators who prefer the shell; it is not the documented first choice.

Maintenance mode is deliberately not built in v1. It would have to drain writes, stop the worker, invalidate sessions
and keep the app serving while its database is replaced — a feature with its own failure modes — and the first-start
window already covers migration and disaster recovery. Restoring onto a live, in-use instance through the admin UI is
out of scope.

### D6 — Scheduled archives stay local
Scheduled backups write to `/data/backups` on the data volume. Retention keeps the newest N scheduled runs (default 7),
never deletes a manual run and never deletes the last successful scheduled run. Off-site destinations (S3 and similar)
are a later feature; until then the operator copies archives off the host. A backup is downloaded only as a file
handoff, so an admin still never sees another User's Dives in the app.

## Alternatives considered
- **Operator CLI only (the original v1 plan).** Rejected as the primary path: disaster recovery commonly starts on a
  new machine with the archive at hand but no shell, no matching image tag and no database. The setup-screen path uses
  the empty setup window and is safer; the CLI remains for the cases that suit it.
- **Maintenance mode / restore onto a running instance.** Rejected for v1 for the reasons in D5: more moving parts,
  and a partial restore on live data is worse than an explicit stop.
- **Plain `pg_dump` + `tar` as the supported format.** The commands stay documented as an operator's own fallback, but
  they are two artefacts with no manifest, no schema/key check and no encryption envelope. The built-in archive makes
  those checks part of the product.
- **Use age as the container format.** Rejected: it adds an external binary and keeps our manifest opaque. Only the
  key text is borrowed, so `age-keygen` remains useful.

## Consequences
- One file restores the whole instance; there is no selective restore of one table, Diver or Dive in v1.
- Manual backups need the in-process worker: the passphrase is never persisted, so the optional separate worker service
  fails a manual run instead of executing it. Scheduled backups are unaffected.
- Losing the passphrase or the recipient private key makes an archive unrecoverable; losing `DIVEHUB_ENCRYPTION_KEY`
  makes the restored Provider credentials unusable. Operators keep copies of both somewhere other than the server and
  never inside an archive.
- The files are replaced before the database transaction. If the database restore then fails, the files are already the
  backup's while the database is unchanged: on a fresh instance a retry is safe; an operator restoring over a used
  instance keeps a copy first.
- The archive includes sessions, stored API keys and the worker queue along with the domain data. A restored instance
  resumes from that state; stale queue jobs are deduplicated by their job keys.
- Backups and restores stream end to end and are disk-bound; an archive larger than a browser upload can comfortably
  carry goes through `/data/restore/`. The app image gains PostgreSQL's client tools to run `pg_dump`/`pg_restore`.
- Scheduled archives live on the same volume as the data they protect until copied off. Retention is a convenience, not
  a substitute for an off-site copy.
- Restore accepts a backup whose migration head the image knows and refuses an unknown one; a backup is never
  downgraded to an older image across a migration.

## Out of scope (v1)
Recorded here so the omissions are deliberate:

- off-site targets (S3 and similar) and remote retention;
- a per-Diver data export (the account export is a different feature, [ADR 0044](0044-account-export-import-and-kinds.md));
- point-in-time recovery;
- restoring onto a live instance or a maintenance mode;
- selective restore of parts of the instance.
