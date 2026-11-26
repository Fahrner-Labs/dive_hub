---
title: "ADR 0087: Hosted instance configuration"
summary: One image serves both tracks; opt-in environment variables change which site sources exist, whether registration is open, how mail is sent, whether billing is enabled and how Diver details are seen; with none set it behaves exactly as today.
status: proposed
date: 2026-11-26
---

# ADR 0087: Hosted instance configuration

## Status
Proposed – 2026-11-26. Design locked by the accepted hosted-DiveHub execution plan
([FAH-524](/FAH/issues/FAH-524), revision `ff67973a`), implementation task [FAH-527](/FAH/issues/FAH-527). The
owner reviews and accepts it with the pull request that adds `DIVEHUB_SITE_SOURCES`; the other variables are
documented here now and land in their own tasks.

## Context
Dive Hub is self-hosted: one image, one database, no account with us, invite-only, all Dive site sources on, every
feature free ([ADR 0004](0004-system-architecture.md), [ADR 0012](0012-invitations.md),
[ADR 0021](0021-site-external-ids-and-import.md)). The hosted product is the *same* artifact, so any difference
between the tracks must be deployment configuration, never a fork or a build flag.

Some hosted requirements change instance-wide behaviour: OpenStreetMap data under ODbL would make the hosted site
list a derivative database, open registration needs transactional email, a paid tier needs billing, and a worldwide
instance must not publish every Diver's contact details. Encoding each as a separate ad-hoc switch would let the two
tracks drift and would hide today's behaviour inside conditionals.

## Decision
**Environment variables, read once at boot by `loadConfig` (`apps/server/src/config.ts`), are the only difference
between a self-hosted and a hosted instance.** An empty value counts as unset, as Compose's `VAR=` gives one. The
defaults reproduce today's self-hosted behaviour exactly; the hosted configuration opts in.

| Variable | Default (self-hosted) | Hosted value | Effect |
|---|---|---|---|
| `DIVEHUB_SITE_SOURCES` | `osm,wikidata,ssi` | `wikidata,ssi` | Which site sources `createSiteSources` constructs and the admin UI offers. Unknown names stop the boot. OSM off means no ODbL derivative-database obligation on the hosted instance. |
| `DIVEHUB_REGISTRATION_OPEN` | `false` | `true` | Opens self-registration and email verification. The invitation flow stays available on both tracks. |
| `DIVEHUB_EMAIL_PROVIDER` / `DIVEHUB_EMAIL_API_KEY` / `DIVEHUB_EMAIL_FROM` | unset | `resend` and the key | Unset keeps today's copy-link invitations and admin-issued password resets ([ADR 0012](0012-invitations.md)). |
| `DIVEHUB_BILLING_PROVIDER` / `DIVEHUB_PADDLE_API_KEY` / `DIVEHUB_PADDLE_WEBHOOK_SECRET` / `DIVEHUB_PADDLE_PRICE_IDS` | unset | `paddle` and the keys | Unset means every feature is free, no entitlement gate and no billing UI. |
| `DIVEHUB_DIVER_VISIBILITY` | `instance` | `shared` | `instance` keeps today's rule (every User sees every Diver, [ADR 0043](0043-dive-centres-and-ssi-verification-codes.md)); `shared` limits contact details to the Diver's owners, Users who share a Dive and admins. |

**Dual-track invariant.** With none of these variables set, the image behaves exactly as it does today: invite-only,
copy-link invitations, all site sources, every feature free. Every hosted behaviour is opt-in configuration of the
same artifact.

**Site sources are a deployment decision, not a user toggle.** [ADR 0021](0021-site-external-ids-and-import.md)
deliberately has no per-User switch for sources. This ADR amends that decision for deployment configuration only: an
operator chooses the sources once for the instance; users still cannot enable or disable one. The enabled set is
enforced on the server (a request naming a disabled source answers `400 site_source_disabled`), and the admin UI
offers exactly what `GET /api/admin/site-sources` reports rather than a copy of the list. It is not stored in the
database, so changing it takes effect on restart, and a queued import that names a since-disabled source fails with
the same code instead of fetching it.

The remaining variables are documented here as the model and defaults; they are implemented and enforced by their
own tasks and ADRs (registration, email, billing/entitlements, Diver visibility). Until then they change nothing.

## Consequences
- A self-hoster upgrades without changing anything; the defaults are the old behaviour.
- The hosted instance is configured by environment alone, so backups, migrations, the Android binary and the web
  client stay identical across tracks.
- A typo in `DIVEHUB_SITE_SOURCES` stops the boot with the unknown name and the valid list, rather than silently
  removing a source or leaving the admin with an empty form.
- `DIVEHUB_SITE_SOURCES` cannot be changed per request or per User; doing so needs a new decision, not another
  environment variable.
