---
title: "ADR 0074: Bearer tokens for native clients"
summary: Native clients (the Android app first) sign in with e-mail and password and authenticate with the session token as an Authorization bearer token (Better Auth's bearer plugin, as ADR 0011 pre-decided); bearer requests skip the CSRF origin checks; /api/live accepts the bearer token too.
status: proposed
date: 2026-11-18
---

# ADR 0074: Bearer tokens for native clients

## Status
Proposed – 2026-11-18. Discussion: [FAH-255](https://paperclip/FAH/issues/FAH-255). The
[client contract](../spec/clients.md) reserved this ADR ("A native client will use bearer tokens … its ADR comes
with the mobile app"); [ADR 0011](0011-better-auth.md) pre-decided the mechanism ("otherwise the bearer plugin").

## Context
The web client authenticates with an `HttpOnly`, `SameSite=Lax` session cookie on the same origin, protected by
Better Auth's Origin/Fetch-Metadata CSRF checks ([ADR 0011](0011-better-auth.md)). A native app has no cookies
and no same origin: it needs a token it can store (Android Keystore) and send on every request.

[ADR 0011](0011-better-auth.md) pre-decided the fork: "Mobile uses the Expo integration if the app is React
Native, otherwise the bearer plugin." [ADR 0073](0073-android-app-in-monorepo-kotlin-compose.md) chose native
Kotlin, so the bearer plugin it is. What remains to decide is the shape around it.

Forces:

- Sessions are database-backed and revocable today; the account page already lists them and can end them, and a
  password change ends the others ([ADR 0013](0013-account-management.md)). A native sign-in should be nothing
  new under the sun.
- AI-access keys work only at `/mcp`, never for `/api` ([ADR 0035](0035-mcp-connector.md)) — that stays.
- Live hints (`GET /api/live`, [ADR 0066](0066-live-updates-via-sse-hints.md)) authenticate by session cookie
  today ("the browser's EventSource sends it same-origin"); a native SSE client sends an `Authorization` header.

## Decision
- **Native clients authenticate with the session token as a bearer token** (`Authorization: Bearer <token>`),
  via Better Auth's **bearer plugin**. Sign-in is the existing e-mail + password endpoint; the app keeps the
  returned token in the Android Keystore.
- **A native sign-in is an ordinary database session.** No new token type: revocation, the session list,
  sign-out-everywhere and password-change-ends-sessions all apply unchanged. A lost phone is handled by ending
  its session from any other session.
- **Bearer requests skip the Origin/Fetch-Metadata CSRF checks** (there is no browser to protect); cookie
  requests keep the checks exactly as they are.
- **`GET /api/live` accepts the bearer token** as well as the cookie. Polling remains fully valid for clients
  without the stream (ADR 0066).
- **The app asks for the instance URL at first start** (self-hosted instances have no fixed host); deep links or
  QR codes are a later refinement.
- No new tables are expected — the plugin rides on the session table. If the implementation turns out to need a
  migration, it needs the owner's approval like any schema change (CODEOWNERS).
- The client contract is updated in the same change: "will use bearer tokens" becomes "uses bearer tokens".

## Considered options
- **Separate long-lived API tokens per device** (like the AI-access keys for `/api`). Rejected: a second
  credential system beside sessions, with its own expiry/revocation semantics to build and audit, for no gain —
  sessions already are revocable tokens with a listing UI.
- **OIDC/OAuth with PKCE now.** Still planned for later (ADR 0011); it does not remove the need for bearer
  transport on native, and self-hosted OIDC providers vary per operator.

## Consequences
- The sign-in endpoint gets native traffic: its rate limits and "never reveal whether an e-mail has an account"
  rules (client contract) apply unchanged.
- The session list on the account page becomes the revocation point for phones; it should keep naming device and
  last activity so a User can tell their phone from their browser.
- Bearer tokens in `Authorization` headers must stay out of request logs.
- The web client is untouched: cookie path, CSRF checks and SameSite behaviour do not change.
