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
| `DIVEHUB_ENCRYPTION_KEY` | unset | 32 bytes as base64 (`openssl rand -base64 32`). Encrypts secrets kept for Targets (e.g. SSI passwords). Without it, users cannot choose "Keep me signed in" for SSI. A wrong length stops the app. Keep it **apart** from database backups: losing it means every user signs in to SSI again. |
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

Back up **both** parts; either one alone cannot restore the instance:

```sh
# PostgreSQL: users, dives, sites, sessions, everything but files
docker compose exec -T db pg_dump -U divehub -d divehub > divehub-db-$(date +%F).sql

# The files volume: Originals, Media, import archives
docker compose exec -T app tar czf - -C /data . > divehub-files-$(date +%F).tar.gz
```

Run this on a schedule and copy the files off the host. Store `.env` with your secrets as well,
but keep `DIVEHUB_ENCRYPTION_KEY` apart from the database dump: the dump plus the key decrypts
what it protects.

To restore, stop the app, load the dump into the database (`docker compose exec -T db psql -U
divehub -d divehub < divehub-db-….sql`), unpack the archive into the files volume, restore the
same image tag and start the stack.

## Upgrades

Upgrades are `docker compose pull && docker compose up -d` with a new image tag. Migrations run
automatically when the container starts ([ADR 0004](decisions/0004-system-architecture.md)) and
are **forward-only**.

1. **Back up first** — `pg_dump` and the files volume, as above. Do this before every upgrade.
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
