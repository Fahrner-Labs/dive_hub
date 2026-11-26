---
title: Hosted operations runbook
summary: Running hosted Dive Hub on the owner's NAS — Phase 0 bring-up (deploy, TLS, backups, monitoring, restore and upgrade drills) and Phase 1a hosted configuration, with no fixed VPS date.
status: living
date: 2026-11-26
---

# Hosted operations runbook

This is the runbook the owner executes on the NAS for hosted Dive Hub. Phase 0 brings the stock instance up with
backups, monitoring and rehearsed recovery; Phase 1a turns on the hosted behaviour as those pull requests merge.
The self-hosting guide ([self-hosting.md](self-hosting.md)) is the reference for every variable and command; this
page is the ordered operating habit on top of it.

**Dual-track invariant.** The image is one artifact. With no hosted environment variables set it behaves exactly as
today: invite-only, copy-link invitations, all site sources, every feature free. Every hosted behaviour is opt-in
environment configuration of the same image — never a fork and never a build flag.

**No fixed VPS date.** Hosted beta runs on the NAS, invite-only. The move to a VPS is trigger-based, not scheduled
(owner's direction, 2026-11-26); the drills below are the rehearsal for it.

## Phase 0 — NAS bring-up

### 1. Deploy the stock compose stack

1. Put the stock [`compose.yaml`](../compose.yaml) in a directory on the NAS and create `.env` beside it.
2. Generate and record the required secrets:

   ```sh
   openssl rand -base64 24        # POSTGRES_PASSWORD
   openssl rand -base64 32        # DIVEHUB_ENCRYPTION_KEY
   ```

3. Set the required values; `DIVEHUB_BASE_URL` is the exact public origin (scheme and host, no path):

   ```sh
   POSTGRES_PASSWORD=<generated>
   DIVEHUB_BASE_URL=https://dives.example.com
   DIVEHUB_ENCRYPTION_KEY=<generated 32-byte base64>
   ```

   Keep a copy of `DIVEHUB_ENCRYPTION_KEY` in the owner's password manager. Without the same key, a restored
   instance cannot decrypt stored Provider credentials ([ADR 0069](decisions/0069-full-instance-backup-and-restore.md)).
4. Pin the app image to an exact release tag in `compose.yaml`; never `latest`, `X.Y` or `X`
   ([self-hosting.md](self-hosting.md#upgrades), [releases.md](releases.md)):

   ```yaml
   image: ghcr.io/fahrner-labs/dive_hub:vX.Y.Z
   ```

5. Start the stack and create the first admin from the setup token in the log:

   ```sh
   docker compose up -d
   docker compose logs app        # look for "No admin yet. Open …/#/setup …"
   ```

6. Open `https://dives.example.com/#/setup`, enter the token and create the admin. Registration stays invite-only
   (see [7](#7-invite-only-registration)); nothing is charged in Phase 0.

### 2. TLS in front, app on loopback

Put Caddy (or the NAS's own proxy) in front of the app and let it obtain and renew Let's Encrypt certificates. The
app keeps the default `DIVEHUB_BIND=127.0.0.1`, so only the local proxy can reach port 3000:

```caddyfile
dives.example.com {
	reverse_proxy 127.0.0.1:3000
}
```

Tell the app which proxy to trust, so rate limiting sees real client IPs ([ADR 0061](decisions/0061-attempt-limit-on-token-routes.md)):

```sh
DIVEHUB_TRUSTED_PROXIES=127.0.0.1/32
```

Keep the proxy's body limit at or above `DIVEHUB_MAX_UPLOAD_MB` (512 MiB by default) and let it stream
`/api/live` without buffering; the details are in [self-hosting.md](self-hosting.md#tls-reverse-proxy).

### 3. Backups (ADR 0069)

Backups are the point of this phase; the rest of the runbook assumes they run.

1. **Create the recipient key on a workstation, not the NAS:**

   ```sh
   age-keygen -o divehub-backup.key
   grep 'public key' divehub-backup.key      # age1… — paste into the backup settings
   ```

   Keep the `AGE-SECRET-KEY-1…` private key in the owner's password manager, never on the NAS. The server writes
   backups to it but cannot read them; without the private key no scheduled archive can be restored.

2. **Turn on the schedule** in **Admin → Backups → Scheduled backups**:
   - **Back up on a schedule**: on
   - **Every (hours)**: `12`
   - **Runs to keep**: `30`
   - **Recipient public key**: the `age1…` value

   Scheduled runs are due on the worker's minute tick; no external cron or timer is involved. Archives land in
   `/data/backups` on the `files` volume. Retention keeps the newest 30 scheduled runs, never deletes a manual run
   and never deletes the last successful scheduled run.

3. **Copy the archives off the box nightly.** The archives sit on the same volume as the data they protect; the
   off-box copy is the operator's job ([ADR 0069](decisions/0069-full-instance-backup-and-restore.md) D6). Run a
   nightly `rsync` of the `files` volume's `backups/` directory (the container's `/data/backups`) to a second
   machine, and record it in the NAS's scheduler.

4. **Keep the two secrets apart from the archives.** The backup private key, and a recoverable copy of
   `DIVEHUB_ENCRYPTION_KEY`, live in the owner's password manager. The running stack reads `DIVEHUB_ENCRYPTION_KEY`
   from `.env`; that copy is the instance's, and the password-manager copy is the disaster-recovery one. The backup
   private key never goes on the NAS, and neither secret goes inside an archive or on the off-box backup medium.

### 4. Monitoring habit

No metrics endpoint is built. Use three cheap habits instead:

- **External readiness probe.** Run an external probe of `GET /api/health/ready` every 5 minutes that pings a
  healthchecks.io check (free tier) with an email alert. The route answers `200` only after a successful `select 1`,
  so it covers the app and the database.
- **Dead-man's switch.** Have the nightly `rsync` ping a second healthchecks.io check. Its absence of a ping is the
  alert: the off-box copies stopped.
- **Weekly 10-minute ritual.** Glance at a fresh support bundle (**Admin → Support bundle**), check disk usage on
  the data and backup volumes, and review the `docker compose pull` policy: pulls are deliberate and pinned, never
  automatic.

### 5. Monthly restore drill

Restore the latest scheduled archive into a scratch compose project — never over the live instance. The operator
CLI is `scripts/restore-backup.sh` and takes a manual backup's passphrase or a scheduled backup's private key; it
refuses while the app's heartbeat is fresh, so a running instance is protected by construction:

```sh
scripts/restore-backup.sh /path/to/backup.divehub-backup
```

Do the drill in a separate project directory with its own volumes, then sign in with an account from the archive.
This is the disaster-recovery rehearsal the later VPS move performs for real. The first-start **Restore from
backup** path on `#/setup` is the primary restore for a lost host and is documented in
[self-hosting.md](self-hosting.md#restoring-a-fresh-instance-from-the-setup-screen).

### 6. Upgrade drill

For each release ([releases.md](releases.md)):

1. Take a manual backup from **Admin → Backups** first.
2. Pin the new exact tag in `compose.yaml`.
3. Pull and restart the app:

   ```sh
   docker compose pull app && docker compose up -d
   ```

Migrations run on start and are forward-only. Never downgrade across a migration: the recovery is a restore of the
pre-upgrade archive with the previous image tag, not an older image on a migrated database.

### 7. Invite-only registration

Create and pass invitation links yourself under **Admin → Users**; there is no mail relay in Phase 0. Registration
is not open and nothing is charged.

### Phase 0 exit criteria

Phase 0 is done when all four hold:

- **2+ weeks of green backups** — scheduled runs complete and the nightly off-box copy pings every night.
- **One successful restore drill** — an archive restored into the scratch project and signed in.
- **One upgrade performed** — a pinned tag change survived with a pre-upgrade backup.
- **The alert path deliberately fired and seen** — stop the prober or skip the rsync and confirm the email arrives.

## Phase 1a — hosted configuration on the NAS

Turn these on as the hosted pull requests merge, on the same deployed artifact. Set them in `.env` and
`docker compose up -d`; unsetting them returns the instance to the Phase 0 behaviour.

```sh
DIVEHUB_SITE_SOURCES=wikidata,ssi     # hosted site import sources
DIVEHUB_DIVER_VISIBILITY=shared       # hosted Diver visibility default
DIVEHUB_REGISTRATION_OPEN=false       # keep registration closed; invitations still work
```

Also configure, with credentials from the owner's password manager:

- **Resend live** — transactional mail for verification links and invitations.
- **Paddle sandbox** — billing in sandbox only; no live charges.

### NAS caveats

- **Inbound reachability at the public URL.** Verification links and Paddle sandbox webhooks must reach
  `DIVEHUB_BASE_URL`. If CGNAT blocks inbound connections, put a Cloudflare Tunnel (or equivalent) in front so the
  public origin reaches the NAS; do not open the app to the LAN with `DIVEHUB_BIND=0.0.0.0` as a workaround.
- **No charging on residential hardware.** Paddle stays in sandbox while hosted runs on the NAS. Live billing
  waits for the trigger-based VPS move.

**No fixed VPS date.** The move is trigger-based (owner's direction, 2026-11-26). Until it happens, hosted beta is
the NAS deployment above, invite-only.

## Related

- [self-hosting.md](self-hosting.md): variables, TLS proxy details, backup and restore reference.
- [ADR 0069](decisions/0069-full-instance-backup-and-restore.md): the archive format and restore rules.
- [ADR 0061](decisions/0061-attempt-limit-on-token-routes.md): why `DIVEHUB_TRUSTED_PROXIES` matters.
- [releases.md](releases.md): cutting the `vX.Y.Z` tags this runbook pins.
