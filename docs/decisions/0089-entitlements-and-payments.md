---
title: "ADR 0089: Entitlements and payments: Paddle as merchant of record, a signed webhook outside Better Auth"
summary: Paddle sells the Pro plan as merchant of record; one HMAC-signed webhook writes the per-User entitlement; checkout and the customer portal are Paddle-hosted links, and we store no payment data.
status: proposed
date: 2026-11-26
---

# ADR 0089: Entitlements and payments: Paddle as merchant of record, a signed webhook outside Better Auth

## Status
Proposed – 2026-11-26. The design is fixed by the accepted hosted-DiveHub execution plan
([FAH-524](/FAH/issues/FAH-524), revision `ff67973a`, §4.1, §4.5 and §4.6); this ADR records the decision and its
consequences for the code. Owner review is required (`docs/decisions/**` in [CODEOWNERS](../../.github/CODEOWNERS)) and
happens on the pull request that adds the Paddle integration ([FAH-535](/FAH/issues/FAH-535)).

## Context
The hosted plan sells exactly one paid tier, Pro, which unlocks provider sync automation and Push runs; the logbook,
manual sends and exports stay free (plan §4.4, [ADR 0087](0087-hosted-instance-configuration.md)). Dive Hub is based in
the EU and has no finance team. Selling to consumers across the EU means VAT/OSS registration and filings if we are the
seller. A merchant of record (MoR) removes that: the MoR is the seller, charges and remits the tax, and issues the
invoice.

The self-hosted artifact must stay unchanged when no billing is configured: every feature free, no entitlement check, no
billing route (the dual-track invariant, [ADR 0087](0087-hosted-instance-configuration.md)). The hosted deployment is the
same image with `DIVEHUB_BILLING_PROVIDER=paddle` and Paddle credentials.

Payments touch a trust boundary: a public endpoint that grants paid access. It cannot use a session, because Paddle calls
it, not a browser; it must prove the call is Paddle's.

## Decision
**Paddle is the merchant of record.** It was chosen over Lemon Squeezy and Stripe + Stripe Tax:

- **Paddle** is the seller for EU VAT, so there is no VAT/OSS registration, no filing, and no tax logic in Dive Hub. It
  ships the hosted checkout and the customer portal (invoices, card updates, cancellation), which is the whole billing UI
  we do not build. It requires business/product approval before going live, an external lead time to start early.
- **Lemon Squeezy** has the same MoR shape, but its roadmap and pricing after the Stripe acquisition are uncertain and its
  subscription tooling is thinner. It is a poor foundation for the only revenue path.
- **Stripe + Stripe Tax** leaves the VAT registration and OSS filings with us. At the plan's small subscription prices the
  compliance overhead dominates the fee difference.

**Configuration is opt-in.** `DIVEHUB_BILLING_PROVIDER=paddle` needs `DIVEHUB_PADDLE_API_KEY`,
`DIVEHUB_PADDLE_WEBHOOK_SECRET` and `DIVEHUB_PADDLE_PRICE_IDS`; a half-configured instance stops the boot rather than run
the paywall with no way to pay. Unset means every feature is free, the entitlement gate is inert and no billing route is
registered. A sandbox API key contains `_sdbx` and selects `https://sandbox-api.paddle.com`; a live key selects
`https://api.paddle.com`, so the NAS beta runs in sandbox on the same image that later takes live keys on the VPS.

**The webhook is unauthenticated but signed, outside Better Auth.** `POST /api/billing/webhooks` verifies Paddle's
`Paddle-Signature` header (HMAC-SHA256 of `<timestamp>:<raw body>` with the destination secret, timing-safe, tolerant of
the extra `h1` values sent during a secret rotation) before parsing the body. It is not a Better Auth endpoint and takes no
session, which amends [ADR 0013](0013-account-management.md)'s "only four Better Auth endpoints are reachable over HTTP"
as an explicit exception: a signed provider webhook is its own authentication. A wrong or missing signature answers 401;
after a valid signature the answer is 2xx even for a replay, a duplicate or an event we do not handle, so Paddle stops
retrying. A database failure still answers 5xx so Paddle retries a write that did not happen.

**The webhook is the only writer of entitlements.** The checkout creates a Paddle transaction with the User's id in
`custom_data`; Paddle copies it to the subscription, so `subscription.created` carries the User. A later event without it
is resolved from the stored subscription/customer id. The handler applies `subscription.created`, `subscription.updated`
and `subscription.canceled` and nothing else. It upserts one `user_entitlement` row per User (already added by
[ADR 0087](0087-hosted-instance-configuration.md)'s task), keyed on the User so a replay changes nothing. `updated_at`
stores the provider's event time, and an event older than the stored one is ignored, so a late event cannot roll back a
newer state; the write is one atomic `ON CONFLICT … WHERE` statement, so concurrent deliveries cannot leave the older one
last. A subscription whose items contain none of the configured price IDs is not this instance's Pro plan and grants
nothing; once stored, though, its updates and cancellation are still applied, so removing a price from the configuration
never leaves a User entitled. An event for a User deleted since the checkout (its entitlement row cascaded with it) is
acknowledged, not retried forever.

**The state machine** (plan §4.6):

```
        subscribe              charge fails           retry succeeds / update
 none ──────────▶ active ──────────────▶ past_due ──────────────────▶ active
                   │                        │ cancel at period end
                   │ cancel                 ▼
                   └──────────────▶ canceled (period end reached) ──▶ none
```

Paddle's `active` and `trialing` become `active`; `past_due` stays `past_due`, the grace window in which Paddle retries
the card and automation keeps running; `canceled` keeps `current_period_end`, so the User stays entitled until the period
that was paid for ends; `paused` is treated like `canceled` (keep the paid period, grant nothing after it). There is no
proration logic of our own: Paddle owns the money.

**No money UI and no payment data.** `GET /api/billing/checkout` returns a Paddle-hosted checkout link;
`GET /api/billing/portal` returns a Paddle customer-portal session link. Both need a signed-in User (the portal needs a
Paddle customer from an earlier checkout). Dive Hub stores no card, address or invoice; only the Paddle customer and
subscription IDs and the period end. Checkout refuses a User who is already entitled (`billing_already_subscribed`):
with one subscription per User, a second checkout would charge them twice, so a plan change happens in the portal.

## Consequences
- A self-hoster is unaffected: with no `DIVEHUB_BILLING_*` the routes do not exist and every feature is free.
- EU VAT, invoices and card updates are Paddle's; Dive Hub never holds payment data, which keeps the instance out of PCI
  scope.
- Paddle's business approval is an external dependency before charging; sandbox keys let the beta run meanwhile.
- The webhook endpoint must be reachable at the public URL; a NAS behind CGNAT needs a tunnel or Paddle's CLI forwarding
  while testing (hosted-operations runbook).
- The model is one subscription per User. A User who changes plan through Paddle's upgrade path keeps one subscription;
  separate concurrent subscriptions are not represented, and the newest event wins.
- A lost or misconfigured webhook secret stops every entitlement update; the boot refuses a half-configured instance, and
  the sandbox round-trip in the pull request proves checkout → webhook → entitlement → automation before live keys.
- Replays are harmless by construction, so the webhook does not need a timestamp window; the signature still makes an
  altered or forged body impossible.
