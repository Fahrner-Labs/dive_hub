---
title: "ADR 0092: Stripe billing: processor model, Checkout and Customer Portal, webhook-written entitlements"
summary: Stripe processes payments for the Pro plan as payment processor; Checkout and the Customer Portal are Stripe-hosted links, Stripe Tax is on for threshold monitoring, and one signed webhook writes the per-User entitlement.
status: proposed
date: 2026-11-27
---

# ADR 0092: Stripe billing: processor model, Checkout and Customer Portal, webhook-written entitlements

## Status
Proposed – 2026-11-27. The owner decided the processor and this scope on the task
([FAH-554](/FAH/issues/FAH-554)); the implementation follows it. This ADR supersedes
[ADR 0089](0089-entitlements-and-payments.md) (Paddle as merchant of record). Owner review is required
(`docs/decisions/**` in [CODEOWNERS](../../.github/CODEOWNERS)) and happens on the pull request that adds the Stripe
integration.

## Context
The hosted plan sells one paid tier, Pro, which unlocks provider sync automation and Push runs; the logbook, manual
sends and exports stay free (plan §4.4, [ADR 0087](0087-hosted-instance-configuration.md)). ADR 0089 chose Paddle as
merchant of record. The owner has since reversed that choice: with a small customer base and an EU + Asia focus, Stripe
fits the existing stack better, and the realistic indirect-tax risk (the EU OSS threshold, US nexus) is covered by
monitoring rather than a merchant-of-record arrangement. Asia (VN/TH) is outside Stripe Tax's coverage and is accepted
as low risk at the current scale.

The self-hosted artifact must stay unchanged when no billing is configured: every feature free, no entitlement check, no
billing route (the dual-track invariant, [ADR 0087](0087-hosted-instance-configuration.md)). The hosted deployment is the
same image with `DIVEHUB_BILLING_PROVIDER=stripe` and Stripe credentials.

Payments touch a trust boundary: a public endpoint that grants paid access. It cannot use a session, because Stripe calls
it, not a browser; it must prove the call is Stripe's.

## Decision
**Stripe is the payment processor, not a merchant of record.** Dive Hub is the seller. Stripe Checkout collects the card
and calculates tax with **Stripe Tax** enabled (`automatic_tax.enabled`); the Stripe dashboard's threshold monitoring is
what we watch for OSS and nexus obligations. We build no tax logic, no tax table and no filing automation.

- **Stripe** ships Checkout and the Customer Portal (invoices, card updates, cancellation), so we build no money UI and
  hold no card, address or invoice. It runs in **test mode** first: a `sk_test_…` secret key charges no money, and live
  keys (`sk_live_…`) come later without a code change.
- **Paddle** is no longer used. Its sandbox never went live because charging waited for the VPS move; the integration is
  replaced rather than kept beside a second provider.

**Configuration is opt-in.** `DIVEHUB_BILLING_PROVIDER=stripe` needs `DIVEHUB_STRIPE_SECRET_KEY`,
`DIVEHUB_STRIPE_WEBHOOK_SECRET` and `DIVEHUB_STRIPE_PRICE_ID`; a half-configured instance stops the boot rather than run
the paywall with no way to pay. Unset means every feature is free, the entitlement gate is inert and no billing route is
registered.

**The webhook is unauthenticated but signed, outside Better Auth.** `POST /api/billing/webhooks` verifies Stripe's
`Stripe-Signature` header (HMAC-SHA256 of `<timestamp>.<raw body>` with the endpoint's signing secret, timing-safe,
tolerant of the extra `v1` values sent during a secret rotation) before parsing the body. It is not a Better Auth
endpoint and takes no session, which amends [ADR 0013](0013-account-management.md)'s "only four Better Auth endpoints are
reachable over HTTP" as an explicit exception: a signed provider webhook is its own authentication. A wrong or missing
signature answers 401; after a valid signature the answer is 2xx even for a replay, a duplicate or an event we do not
handle, so Stripe stops retrying. A database failure still answers 5xx so Stripe retries a write that did not happen.

**The webhook is the only writer of entitlements.** Checkout sets `subscription_data[metadata][userId]`, which Stripe
copies onto the subscription, so `customer.subscription.created` carries the User. A later event without it is resolved
from the stored subscription id. The handler applies `customer.subscription.created`, `customer.subscription.updated`,
`customer.subscription.deleted` and `invoice.payment_failed` and nothing else. It upserts one `user_entitlement` row per
User (already added by [ADR 0087](0087-hosted-instance-configuration.md)'s task), keyed on the User so a replay changes
nothing. `updated_at` stores the provider's event time, and an event older than the stored one is ignored, so a late event
cannot roll back a newer state; the write is one atomic `ON CONFLICT … WHERE` statement, so concurrent deliveries cannot
leave the older one last. A subscription whose items contain none of the configured price is not this instance's Pro plan
and grants nothing; once stored, though, its updates and deletion are still applied, so removing a price from the
configuration never leaves a User entitled. An event for a User deleted since the checkout (its entitlement row cascaded
with it) is acknowledged, not retried forever.

**The state machine** (plan §4.6):

```
        subscribe              charge fails           retry succeeds / update
 none ──────────▶ active ──────────────▶ past_due ──────────────────▶ active
                   │                        │ cancel at period end
                   │ cancel                 ▼
                   └──────────────▶ canceled (period end reached) ──▶ none
```

Stripe's `active` and `trialing` become `active`; `past_due` and `unpaid` become `past_due`, the grace window in which
Stripe retries the card and automation keeps running; `canceled`, `incomplete`, `incomplete_expired` and `paused` become
`canceled`. A subscription marked `cancel_at_period_end` is shown as `canceled` while keeping `current_period_end`, so the
User stays entitled until the period that was paid for ends and the client says "paid until" instead of "renews". There is
no proration logic of our own: Stripe owns the money.

**No money UI and no payment data.** `GET /api/billing/checkout` returns a Stripe Checkout Session URL;
`GET /api/billing/portal` returns a Stripe Customer Portal session URL. Both need a signed-in User (the portal needs a
Stripe customer from an earlier checkout). Dive Hub stores no card, address or invoice; only the Stripe customer and
subscription IDs and the period end, in provider-neutral `billing_customer_id` / `billing_subscription_id` columns.
Checkout refuses a User who is already entitled (`billing_already_subscribed`): with one subscription per User, a second
checkout would charge them twice, so a plan change happens in the portal.

## Consequences
- A self-hoster is unaffected: with no `DIVEHUB_BILLING_*` the routes do not exist and every feature is free.
- Dive Hub is the seller: Stripe Tax calculates and reports, but VAT/OSS registration and filings remain ours. The
  dashboard's threshold monitoring is the early-warning system; Asia is accepted as out of coverage for now.
- Stripe has no merchant-of-record lead time like Paddle's business approval, so test mode can start immediately.
- The webhook endpoint must be reachable at the public URL; a NAS behind CGNAT needs a tunnel or Stripe CLI forwarding
  while testing (hosted-operations runbook).
- The model is one subscription per User. A User who changes plan through Stripe's portal keeps one subscription;
  separate concurrent subscriptions are not represented, and the newest event wins.
- A lost or misconfigured webhook secret stops every entitlement update; the boot refuses a half-configured instance, and a
  test-mode round-trip with the operator's keys proves checkout → webhook → entitlement → automation before live keys.
- Replays are harmless by construction, so the webhook does not need a timestamp window; the signature still makes an
  altered or forged body impossible.
