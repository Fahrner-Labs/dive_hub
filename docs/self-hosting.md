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
   Without e-mail configured, every invitation is a link you pass on yourself. With Resend configured
   ([below](#transactional-e-mail-optional)) the link also goes out by e-mail; the copy link stays.

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
| `DIVEHUB_SITE_SOURCES` | `osm,wikidata,ssi` | Comma-separated site sources this instance constructs and offers: `osm`, `wikidata`, `ssi`. The default is every source, as before. Leave OSM out (e.g. `wikidata,ssi`) when the site list must not carry ODbL's derivative-database obligation, as on the hosted instance ([ADR 0087](decisions/0087-hosted-instance-configuration.md)). An unknown name stops the app at boot. |
| `DIVEHUB_CONTACT` | unset | Your e-mail address or URL, added to the User-Agent when importing dive sites from Overpass and Wikidata. Identifying yourself is polite and avoids blocks ([site imports](spec/architecture.md#dive-site-imports-and-licenses)). |
| `DIVEHUB_OVERPASS_URL` | `https://overpass-api.de/api/interpreter` | Another Overpass endpoint for site imports, e.g. your own or a paid server for regular or commercial imports. |
| `DIVEHUB_WIKIDATA_SPARQL_URL` | `https://query.wikidata.org/sparql` | Another Wikidata SPARQL endpoint. |
| `DIVEHUB_SSI_SITES_URL` | `https://api.divessi.com/app/APP_CACHE_SITES.zip` | Another address for SSI's site list, normally only for tests ([ADR 0025](decisions/0025-ssi-site-import-and-site-water-type.md)). |
| `DIVEHUB_FCM_SERVICE_ACCOUNT` | unset | Firebase service-account JSON for native push (ADR 0085), inline or a path to a JSON file. **A secret**: the send credential for the whole project; never commit it or put it in an image. Needs `DIVEHUB_FCM_CLIENT_CONFIG` too. |
| `DIVEHUB_FCM_CLIENT_CONFIG` | unset | The matching public Firebase client config (`projectId`, `applicationId`, `apiKey`, `gcmSenderId`), inline JSON or a path. Served by `GET /api/push/config`; without both FCM values push is off and clients keep polling. |
| `DIVEHUB_EMAIL_PROVIDER` | unset | Transactional mail: `resend` today. Needs `DIVEHUB_EMAIL_API_KEY` and `DIVEHUB_EMAIL_FROM` too; with any e-mail value unset, invitations and resets stay copy links ([ADR 0012](decisions/0012-invitations-and-admin-bootstrap.md), [ADR 0013](decisions/0013-account-management.md)). |
| `DIVEHUB_EMAIL_API_KEY` | unset | **A secret**: the Resend API key (`re_…`). Never commit it or put it in an image. |
| `DIVEHUB_EMAIL_FROM` | unset | The sender, e.g. `Dive Hub <noreply@dives.example.com>`; the domain must be verified at Resend. |
| `DIVEHUB_BILLING_PROVIDER` | unset | `paddle` turns on hosted billing and its entitlement gates (provider sync automation and Push runs); it needs the three Paddle values below. Unset keeps every feature free and registers no billing route ([ADR 0089](decisions/0089-entitlements-and-payments.md)). |
| `DIVEHUB_PADDLE_API_KEY` | unset | **A secret**: the Paddle API key. A sandbox key (contains `_sdbx`) uses Paddle's sandbox; a live key uses production. |
| `DIVEHUB_PADDLE_WEBHOOK_SECRET` | unset | **A secret**: the notification destination's secret that signs every webhook. |
| `DIVEHUB_PADDLE_PRICE_IDS` | unset | Comma-separated recurring Paddle price IDs that mean Pro; the first is the one checkout offers. |

`DIVEHUB_GARMIN_URL`, `DIVEHUB_SSI_URL`, `DIVEHUB_HOST`, `DIVEHUB_WORKER`, `DIVEHUB_WEB_DIR` and
`DIVEHUB_DATA_DIR` exist for the image and the tests; the example compose file sets what the
containers need. The full table is in the [architecture spec](spec/architecture.md#authentication).

### Push notifications for the Android app (optional)

The Android app works without push: it keeps the foreground live stream and polls on the
interval chosen in its settings. Push only makes a backgrounded phone notice a new or changed
Dive sooner. Messages are **hints only** ([ADR 0085](decisions/0085-push-notifications-for-native-clients.md)):
a push names a Dive, and the app fetches it through your API before showing anything, so no dive
content ever travels through Google.

The official app binary is the same for every instance and bundles no Firebase project. To have
push, bring your own free Firebase project and set its two halves on the server:

1. Create a project at <https://console.firebase.google.com/> (the free Spark plan is enough) and
   add an **Android** app with the package name `dev.divehub.android`, the official app's
   application ID. If you build the app yourself, use your build's application ID instead.
2. Download the generated `google-services.json`. You only copy values out of it; never commit it
   and never put it in the app or the repository.
3. From that file take the four public client values:

   | Client value | From `google-services.json` |
   |---|---|
   | `projectId` | `project_info.project_id` |
   | `applicationId` | `client[0].client_info.mobilesdk_app_id` — the Google App ID, `1:...:android:...`, **not** the package name |
   | `apiKey` | `client[0].api_key[0].current_key` |
   | `gcmSenderId` | `project_info.project_number` |

4. In the Firebase console open **Project settings → Service accounts → Generate new private
   key**. The downloaded JSON is the send credential; it is a secret for the whole project.
5. Set both variables in `.env`, either as one-line JSON or as paths to JSON files the container
   can read. The two must belong to the same project:

   ```sh
   DIVEHUB_FCM_SERVICE_ACCOUNT={"type":"service_account","project_id":"…","client_email":"…","private_key":"…"}
   DIVEHUB_FCM_CLIENT_CONFIG={"projectId":"…","applicationId":"1:1234567890:android:…","apiKey":"…","gcmSenderId":"…"}
   ```

6. Restart the stack. `curl https://dives.example.com/api/push/config` now answers an `android`
   object; signed-in Android clients pick it up and register their token. An empty `{}` still
   means push is off and clients keep polling.

Set **both or neither**: with only one of them the app refuses to start and names the missing
variable, so a half-configured instance never advertises a push it cannot send. Keep the
service-account JSON out of the repository, images and instance backups; the client values are
public by design (they ship inside every Firebase app). Unsetting both turns push off again, and
clients fall back to polling on their next start.

### Transactional e-mail (optional)

Dive Hub can send the two transactional messages itself: an invitation after an admin creates it,
and a password reset link a User requests from the sign-in page. It uses **Resend**
(<https://resend.com>), whose free tier covers a small instance. Without the three variables below
nothing changes: invitations and reset links are links you copy and pass on, exactly as
[ADR 0012](decisions/0012-invitations-and-admin-bootstrap.md) and
[ADR 0013](decisions/0013-account-management.md) describe.

1. Create a Resend account and verify the domain you send from.
2. Create an API key with sending permission.
3. Set all three values in `.env`:

   ```sh
   DIVEHUB_EMAIL_PROVIDER=resend
   DIVEHUB_EMAIL_API_KEY=re_…
   DIVEHUB_EMAIL_FROM=Dive Hub <noreply@dives.example.com>
   ```

4. Restart the stack. `curl https://dives.example.com/api/email/config` answers
   `{"configured":true}`. New invitations go out by e-mail and the Admin page keeps showing the
   link for copying; the sign-in page offers **Forgot password?**, which mails a reset link.

Set **all three or none**: with only some of them the app refuses to start and names the missing
variable. A failed send is logged at error level; an invitation still answers its link so the admin
can pass it on, and a self-serve reset request answers the same whether or not the address has an
account (it never reveals one). Unsetting the three turns mail off again and the copy-link flows
return.

The mail module lives in `apps/server/src/mail/` behind a `MailProvider` interface. Postmark is
the documented fallback if Resend's deliverability disappoints: implementing it is another provider
behind that interface, not a change at the invitation or reset call sites.

### Billing with Paddle (optional)

Dive Hub can sell a Pro plan that unlocks provider sync automation and Push runs. **Paddle** is the merchant of record:
it is the seller, collects the card and VAT, and hosts checkout and the customer portal (invoices, card updates,
cancellation). Dive Hub stores only the Paddle customer and subscription IDs and the period end; it stores no card,
address or invoice ([ADR 0089](decisions/0089-entitlements-and-payments.md)). Without the four variables below every
feature stays free and no billing route exists.

For hosted Dive Hub on the NAS the beta uses **Paddle sandbox only**; live keys land with the VPS move
([hosted-operations.md](hosted-operations.md)). A sandbox API key contains `_sdbx` and makes the server talk to
`https://sandbox-api.paddle.com`; a live key talks to `https://api.paddle.com`, so the same image serves both.

1. Create the account you need: a [Paddle sandbox account](https://sandbox-vendors.paddle.com/signup) for testing, or a
   live account (Paddle approves the business and product first). Create a product with a **recurring** price and copy its
   price ID (`pri_…`). Set and get approval for a default payment link under **Checkout → Checkout configuration**;
   Paddle appends the transaction to it.
2. In **Developer tools → Notifications**, create a notification destination of type **URL** pointing at
   `https://dives.example.com/api/billing/webhooks`. Subscribe it to `subscription.created`, `subscription.updated` and
   `subscription.canceled`. Copy the destination's secret (`pdl_ntfset_…`); it signs every delivery.
3. Create an API key with the transaction and customer-portal permissions (`pdl_sdbx_…` in sandbox, `pdl_live_…` live).
4. Set all four values in `.env`:

   ```sh
   DIVEHUB_BILLING_PROVIDER=paddle
   DIVEHUB_PADDLE_API_KEY=pdl_sdbx_…
   DIVEHUB_PADDLE_WEBHOOK_SECRET=pdl_ntfset_…
   DIVEHUB_PADDLE_PRICE_IDS=pri_…
   ```

5. Restart the stack. A signed-in user's `GET /api/billing/checkout` now answers a Paddle checkout URL carrying their id
   in the passthrough, and `GET /api/billing/portal` the customer portal (409 `billing_not_subscribed` before the first
   checkout).

Set **all four or none**: with the provider set and a Paddle value missing the app refuses to start and names the missing
variable, so an instance never runs the paywall with no way to pay. The webhook is verified against
`DIVEHUB_PADDLE_WEBHOOK_SECRET` over the raw body before anything is parsed; without a valid signature it answers 401.

Sandbox round-trip before live keys:

1. Sign in on the instance and open the checkout link.
2. Pay with Paddle's sandbox test card `4242 4242 4242 4242` (or `4000 0038 0000 0446` for 3-D Secure).
3. The `subscription.created` webhook should arrive; the user's entitlement is now pro and active, so a Connection's
   automation switches unlock. Replaying the event changes nothing.
4. Open the portal link: invoices and card updates are Paddle's pages.

**Moving an existing instance from sandbox to live.** Paddle customer and subscription IDs belong to one environment
(`sandbox-api.paddle.com` versus `api.paddle.com`). A restored database still carries the sandbox IDs, so after you
swap in live keys `GET /api/billing/checkout` reuses a sandbox customer that live Paddle rejects (the route answers
`billing_unavailable`, 502), `GET /api/billing/portal` fails the same way for those users, and the sandbox `pro` rows
keep their entitlement because the gate only reads `plan`, `status` and `current_period_end`. At the flip, either
restore into a fresh database (live users subscribe again) or clear the sandbox plan state before the live image
starts:

```sh
docker compose exec -T db psql -U divehub -d divehub -c "delete from user_entitlement"
```

No row means the free plan, so that one statement drops the sandbox Paddle IDs and the entitlements they granted. Run
the sandbox round-trip above on the restored instance with real credentials first, then switch the keys.

The webhook endpoint must be reachable at `DIVEHUB_BASE_URL`. If the NAS is behind CGNAT, use a tunnel (or Paddle's CLI
webhook forwarding during testing) as [hosted-operations.md](hosted-operations.md) describes.

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

If a backup fails with `Backups need pg_dump, but it was not found on this host`, the running image
does not contain PostgreSQL's client tools. The official image has shipped them since v0.1.1; check
the `version` in `/api/health/live` and upgrade an older image (or rebuild a local one from the
current `Dockerfile`). The release pipeline refuses to publish an image whose `pg_dump`/`pg_restore`
are not on the runtime user's PATH.

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
   production: a moving tag can jump across a migration without warning. `-rcN` tags exist for
   testing an upgrade before the final release; never pin one in production.
4. Pull and restart:

   ```sh
   docker compose pull app
   docker compose up -d
   ```

**Never downgrade across a migration.** An older image cannot undo a newer schema. If an upgrade
goes wrong, restore the backup from step 1 (database *and* files) and start the previous tag
again.

## A worker job that gave up

The support bundle's `jobs.json` lists jobs with a stored error. A job whose `attempts` reached
`maxAttempts` has stopped retrying and stays in the queue as a tombstone: it runs nothing, and a job
the worker queues at every start (such as `assess-dives`,
[ADR 0036](decisions/0036-dive-assessment.md)) is queued anew on the next start. The row only
clutters the next bundle. To remove the ones that gave up (Graphile Worker keeps them in its private
table; `attempts = max_attempts and locked_at is null` is its own "permanently failed" rule):

```sh
docker compose exec -T db psql -U divehub -d divehub -c \
  "delete from graphile_worker._private_jobs where attempts = max_attempts and locked_at is null"
```

Take the support bundle first if you want to keep their errors. A job that fails again after this
points at a real fault: its stored error names the database's own message.
