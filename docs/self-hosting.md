---
title: Self-hosting guide
summary: Run Dive Hub in production with Docker Compose — first start, environment variables, TLS reverse proxy, backups and upgrades.
status: living
date: 2026-11-09
---

# Self-hosting guide

Dive Hub ships as one application image (API, web client and worker) plus PostgreSQL
([ADR 0004](decisions/0004-system-architecture.md)). The example
[`compose.yaml`](../compose.yaml) runs both containers. The operator supplies the public
hostname, the TLS reverse proxy and the backups.

For a development setup see [development.md](development.md).

## Prerequisites

- Docker Engine with Compose v2 (`docker compose`), on amd64 or arm64.
- A hostname that resolves to the server, e.g. `dives.example.com`.
- A reverse proxy that terminates TLS. [Below](#tls-reverse-proxy) is a Caddy example; Traefik and
  NAS proxies work the same way.
- Somewhere to put backups: both the PostgreSQL dump and the files volume.

The stack is two containers and fits a small VPS or NAS. Give the database and the file volume
room for your dive files and account exports.

## Quick start

1. Download the example compose file into an empty directory:

   ```sh
   curl -fsSLO https://raw.githubusercontent.com/Fahrner-Labs/dive_hub/main/compose.yaml
   ```

2. Create `.env` next to it with the two required settings:

   ```sh
   POSTGRES_PASSWORD=<a long random password>
   DIVEHUB_BASE_URL=https://dives.example.com
   ```

   Generate the password with `openssl rand -base64 24`. `DIVEHUB_BASE_URL` is the exact public
   origin users open (scheme and host, no path); invitation links and secure cookies use it.

3. For production, pin an exact release tag in `compose.yaml` instead of `latest` (see
   [Upgrades](#upgrades)):

   ```yaml
   image: ghcr.io/fahrner-labs/dive_hub:v1.2.3
   ```

4. Start the stack:

   ```sh
   docker compose up -d
   ```

5. On the first start the app logs a **setup token**. Read it from the app log:

   ```sh
   docker compose logs app
   ```

   Look for `No admin yet. Open …/#/setup …`. Open `https://dives.example.com/#/setup`, enter the
   token and create the first admin. The token is valid for 24 hours and until the first admin
   exists; if the log scrolled away, `docker compose restart app` prints a fresh one.

6. Invite the other users from the Admin page ([ADR 0012](decisions/0012-invitations-and-admin-bootstrap.md)).
   There is no mail relay: invitations are links you pass on yourself.

The app port is published on `127.0.0.1` by default, so only the reverse proxy on the same host
can reach it. Put the proxy in front of it before inviting anyone.

## Environment variables

Required (the container refuses to start without them):

| Variable | Meaning |
|---|---|
| `POSTGRES_PASSWORD` | Password of the `divehub` database user. Compose passes it to both containers. |
| `DIVEHUB_BASE_URL` | Public origin users open, e.g. `https://dives.example.com`. Required in production; invitation links point here. With `https`, cookies are `Secure`. |

Settings used by the example compose file:

| Variable | Default | Meaning |
|---|---|---|
| `DIVEHUB_BIND` | `127.0.0.1` | Host address the app port binds to. Keep the loopback default and let the reverse proxy reach it; set `0.0.0.0` only if the proxy runs on another host and you firewall the port. |
| `DIVEHUB_PORT` | `3000` | Host port the app is published on. The container always listens on 3000. |

Application settings (optional unless stated):

| Variable | Default | Meaning |
|---|---|---|
| `DIVEHUB_MAX_UPLOAD_MB` | `512` | Largest upload in MiB. The reverse proxy must allow at least this size. |
| `DIVEHUB_TRUSTED_PROXIES` | empty | Comma-separated IPs or CIDRs of your reverse proxy, e.g. `127.0.0.1/32`. Only their `X-Forwarded-For` counts for the client IP (rate limiting, session records). Without it every request looks like it came from the proxy. |
| `DIVEHUB_AUTH_SECRET` | generated | Secret for signing session cookies. When unset, one is generated into the data volume. Set it if you run more than one app process or want the secret outside the volume. |
| `DIVEHUB_ENCRYPTION_KEY` | unset | 32 bytes as base64 (`openssl rand -base64 32`). Encrypts secrets kept for Targets (e.g. SSI passwords). Without it, users cannot choose "Keep me signed in" for SSI. A wrong length stops the app. A `.divehub-backup` archive stores only its fingerprint, never the key; keep a copy apart from your backups. Losing it means every user signs in to SSI again. |
| `DIVEHUB_LOG_LEVEL` | `info` | Log level (`fatal`, `error`, `warn`, `info`, `debug`, `trace`). |
| `DIVEHUB_CONTACT` | unset | Your e-mail address or URL, added to the User-Agent when importing dive sites from Overpass and Wikidata. Identifying yourself is polite and avoids blocks ([site imports](spec/architecture.md#dive-site-imports-and-licenses)). |
| `DIVEHUB_OVERPASS_URL` | `https://overpass-api.de/api/interpreter` | Another Overpass endpoint for site imports, e.g. your own or a paid server for regular or commercial imports. |
| `DIVEHUB_WIKIDATA_SPARQL_URL` | `https://query.wikidata.org/sparql` | Another Wikidata SPARQL endpoint. |
| `DIVEHUB_SSI_SITES_URL` | `https://api.divessi.com/app/APP_CACHE_SITES.zip` | Another address for SSI's site list, normally only for tests ([ADR 0025](decisions/0025-ssi-site-import-and-site-water-type.md)). |

`DIVEHUB_GARMIN_URL`, `DIVEHUB_SSI_URL`, `DIVEHUB_HOST`, `DIVEHUB_WORKER`, `DIVEHUB_WEB_DIR` and
`DIVEHUB_DATA_DIR` exist for the image and the tests; the example compose file sets what the
containers need. The full table is in the [architecture spec](spec/architecture.md#authentication).

## TLS reverse proxy

The app listens on `127.0.0.1:3000`. A minimal Caddy setup on the same host:

```caddyfile
dives.example.com {
	reverse_proxy 127.0.0.1:3000
}
```

Caddy obtains and renews the certificate itself. Add the proxy to the trusted list in `.env`:

```sh
DIVEHUB_TRUSTED_PROXIES=127.0.0.1/32
```

Two proxy behaviours matter:

- **Upload size.** The proxy must allow at least `DIVEHUB_MAX_UPLOAD_MB` (512 MiB by default).
  Caddy has no body limit unless you add one; if you do, keep it at or above that value.
  In nginx set `client_max_body_size 512m;`.
- **Streaming, not buffering.** Sending a Dive to a Provider can take a couple of minutes, and the
  live hint stream (`GET /api/live`, [ADR 0066](decisions/0066-live-updates-via-sse-hints.md)) is a
  Server-Sent Events stream. Allow at least a 2-minute read timeout, and disable buffering on that
  route. Caddy and Traefik stream by default; nginx needs:

  ```nginx
  location /api/live {
      proxy_pass http://127.0.0.1:3000;
      proxy_buffering off;
      proxy_read_timeout 120s;
  }
  ```

If you use the MCP endpoint, forward `/mcp` like `/api` ([ADR 0035](decisions/0035-mcp-connector.md)).

## Backups

Dive Hub writes whole-instance backups as one encrypted `.divehub-backup` archive: the database and
the data directory together, with a manifest that records the app and schema version and a
fingerprint of `DIVEHUB_ENCRYPTION_KEY` — never the key itself
([ADR 0069](decisions/0069-full-instance-backup-and-restore.md)). Admins take and download them under
**Admin → Backups** (`#/settings/admin/backups`). Restore a fresh instance from the setup screen; the
operator CLI below is the secondary path.

v1 backups assume **one app process** per data volume: the `app.lock` heartbeat the restore CLI checks
and the boot-time reconciliation of interrupted backup runs are per volume, and a manual backup's
passphrase lives only in the app process that accepted it. Run one `app` service against the database
and volume (the compose file's default). A separate worker service can still run scheduled backups.

### Manual backups

A manual backup is encrypted with a passphrase an admin types for that run:

1. Open **Admin → Backups** and choose **New backup**.
2. Enter a passphrase of at least 15 characters. It is used for this run only; the server never stores
   or logs it. Without it the archive cannot be restored, so keep it with the archive — off this
   server.
3. Wait until the run is `ready` and download it. Only one backup runs at a time.

The download is a file handoff: the archive is the whole instance's state as an encrypted file.
Admins still see no other User's Dives in the app, only the archive they can download.

### Scheduled backups and their recipient key

Dive Hub can write a backup to `/data/backups` on its data volume at an interval. Scheduled archives
are encrypted to an X25519 **recipient** you hold the private key for, so the server can create
backups but cannot read them:

```sh
# On your workstation, not the server. age-keygen comes from the age package.
age-keygen -o divehub-backup.key
grep 'public key' divehub-backup.key   # age1… — paste this into the backup settings
```

Keep `divehub-backup.key` (the `AGE-SECRET-KEY-1…` inside) off the server and backed up separately;
without it no scheduled archive can be restored. In **Admin → Backups → Scheduled backups**, switch
the schedule on, set the interval (1–168 hours), the retention count (1–365 scheduled runs) and the
`age1…` recipient. Keep the private key off the server. Retention keeps the newest scheduled runs and
never deletes a manual run or the last successful scheduled one. Download an archive from the list and
copy it off the host: the same volume holds the data it protects.

The archive format and encryption are Dive Hub's own (AES-256-GCM); the recipient and private key use
age's `age1…` / `AGE-SECRET-KEY-1…` bech32 text so `age-keygen` can make them, but `age` itself does
not open a `.divehub-backup` file.

### The instance encryption key

A backup never contains `DIVEHUB_ENCRYPTION_KEY`; its manifest carries only a SHA-256 fingerprint. To
restore an instance that had one, set the same key in `.env` before starting the app — a different or
missing key is refused before anything is written, because the stored Provider credentials would be
undecryptable. Keep a copy of `DIVEHUB_ENCRYPTION_KEY` somewhere other than the server, never inside
an archive. A backup made without the key restores only while the variable is unset and warns that its
Provider credentials are plain text; set the key afterwards and reconnect those accounts to encrypt
them again.

### Restoring a fresh instance from the setup screen

A new machine, or one whose volumes were wiped, is provisioned from a `.divehub-backup` archive on
the setup screen. This is the primary restore path; the setup state guarantees no one is signed in
and no background job runs while the restore replaces the database and the files volume.

1. If the backup was made with `DIVEHUB_ENCRYPTION_KEY`, put that key in `.env` first (see
   [above](#the-instance-encryption-key)); a missing or different key is refused. Then start the
   stack as usual:

   ```sh
   docker compose up -d
   ```

2. Read the setup token from the app log:

   ```sh
   docker compose logs app | grep 'setup token'
   ```

3. Open `https://dives.example.com/#/setup`, choose **Restore from backup**, and enter the setup
   token and the archive's key: a manual backup's passphrase, or a scheduled backup's
   `AGE-SECRET-KEY-1…` private key.

   Upload the archive in the browser, or place it in the app's `/data/restore/` directory first and
   pick it there (better for very large archives; the browser upload is streamed to disk and has no
   fixed size cap):

   ```sh
   docker compose exec app mkdir -p /data/restore
   docker compose cp /path/to/backup.divehub-backup app:/data/restore/
   ```

4. The server answers, then exits so it cannot keep running on a database it just replaced. Restart
   it:

   ```sh
   docker compose restart app
   ```

5. Sign in with an account from the backup. Forward migrations bring an older backup up to the
   image's schema on that restart. An archive from a newer Dive Hub is refused; upgrade the image
   before restoring it.

A refused restore leaves the setup token usable, so a wrong passphrase or a mistyped archive can be
corrected and tried again. A refusal before the database work leaves the fresh database untouched. A
failure once the schema replacement has started leaves it wiped, so retry the same archive; the
token remains usable either way.

### Restoring with the operator CLI

A `.divehub-backup` archive from Dive Hub's backup feature is restored with the operator CLI while
the app is stopped; it is the secondary path for staging resets, scripted recovery and operators who
prefer the shell. The CLI verifies the archive's checksums, the encryption-key fingerprint and the
schema version before it replaces the instance's files or database (it unpacks the archive into the
data volume's `tmp/` directory first), then replaces the database and the files volume.

1. Stop the app, leaving the database container up:

   ```sh
   docker compose stop app
   ```

2. Put the archive's key in the environment (it reaches the one-off container, never the shell
   history or a log): a manual backup's passphrase, or a scheduled backup's private key.

   ```sh
   printf 'Backup passphrase: '; read -rs DIVEHUB_BACKUP_PASSPHRASE; export DIVEHUB_BACKUP_PASSPHRASE
   # For a scheduled archive, instead:
   # read -rs DIVEHUB_BACKUP_RECIPIENT_KEY; export DIVEHUB_BACKUP_RECIPIENT_KEY
   ```

3. Restore the archive from wherever it sits on the host:

   ```sh
   scripts/restore-backup.sh /path/to/backup.divehub-backup
   ```

   The wrapper mounts the archive read-only and runs `docker compose run --rm app node
   dist/main.js restore-backup …`. Without it:

   ```sh
   docker compose run --rm -e DIVEHUB_BACKUP_PASSPHRASE \
     -v "$PWD/backup.divehub-backup:/restore/backup.divehub-backup:ro" \
     app node dist/main.js restore-backup /restore/backup.divehub-backup
   ```

   Add `-v "$PWD/passphrase:/restore/passphrase:ro"` and pass `--passphrase-file
   /restore/passphrase` to read the passphrase from a mounted file instead; delete it after. For a
   scheduled archive, mount the private-key file and pass `--recipient-key-file /restore/key`.

4. Start the app again with `docker compose up -d app`. Forward migrations bring an older
   archive up to the image's schema.

The CLI refuses while the app's heartbeat in the data volume is fresh; after a crash the lock goes
stale within 30 seconds and `--force` overrides it. It also refuses when `DIVEHUB_ENCRYPTION_KEY`
differs from the backup's — the stored provider credentials would be undecryptable — and when the
backup's schema is newer than the image. A backup made without `DIVEHUB_ENCRYPTION_KEY` restores
while the variable is unset and warns that its provider credentials are plain text; set the key
afterwards and reconnect those accounts to encrypt them again.

Restoring replaces the database and the files volume. Keep a copy of the current state first if
you are not restoring onto a fresh instance.

If a restore fails after the database work starts, the database is left wiped: the non-system
schemas are dropped before the dump replays, and `pg_restore` runs in a single transaction, so a
failure rolls back to an empty schema rather than the previous state. The files may already be the
backup's while the database is not. Retry the same archive; the first-start path restores into a
disposable database and the CLI path expects a stopped app and a copy of the current state.

### Database and files without an archive (fallback)

If you use your own tooling instead of `.divehub-backup`, back up **both** parts; either alone cannot
restore the instance:

```sh
# PostgreSQL: users, dives, sites, sessions, everything but files
docker compose exec -T db pg_dump -U divehub -d divehub > divehub-db-$(date +%F).sql

# The files volume: Originals, Media, import archives
docker compose exec -T app tar czf - -C /data . > divehub-files-$(date +%F).tar.gz
```

Run this on a schedule and copy the files off the host. These files have no built-in encryption or
manifest: encrypt them yourself, and keep `DIVEHUB_ENCRYPTION_KEY` apart from them.

To restore, stop the app, load the dump into the database (`docker compose exec -T db psql -U
divehub -d divehub < divehub-db-….sql`), unpack the files archive into the files volume, restore the
same image tag and start the stack.

## Upgrades

Upgrades are `docker compose pull && docker compose up -d` with a new image tag. Migrations run
automatically when the container starts ([ADR 0004](decisions/0004-system-architecture.md)) and
are **forward-only**.

1. **Back up first** — a manual full-instance backup from **Admin → Backups**, or the `pg_dump` and
   files commands above, before every upgrade.
2. **Read the release notes** for the version you are moving to.
3. **Pin an exact `vX.Y.Z` tag** in `compose.yaml`. Do not use `latest`, `X.Y` or `X` in
   production: a moving tag can jump across a migration without warning.
4. Pull and restart:

   ```sh
   docker compose pull app
   docker compose up -d
   ```

**Never downgrade across a migration.** An older image cannot undo a newer schema. If an upgrade
goes wrong, restore the backup from step 1 (database *and* files) and start the previous tag
again.
