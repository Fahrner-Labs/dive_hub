---
title: "ADR 0085: Push notifications for native clients"
summary: Native clients get background wake-ups through FCM data messages carrying hints only ({v, kind, id}); Firebase is configured at runtime from a public endpoint so one binary works with every instance, push is optional per instance with polling as the fallback, and push-device rows are session-scoped.
status: proposed
date: 2026-11-25
---

# ADR 0085: Push notifications for native clients

## Status
Proposed – 2026-11-25. Design locked on [FAH-517](/FAH/issues/FAH-517) (plan document revision 7); the feature branch
implements the push-device table and endpoints, the dispatcher and FCM sender, and the Android client. The ADR stays
proposed until the owner accepts it during the pull request's review ([FAH-521](/FAH/issues/FAH-521)).

## Context
The foreground SSE stream ([ADR 0066](0066-live-updates-via-sse-hints.md)) keeps an open page current, and polling is
the permanent fallback. Neither reaches a backgrounded Android app: the OS ends the stream and suspends the app. The
only sanctioned way to wake it is a high-priority FCM data message over Google Play services.

Dive Hub is self-hosted ([ADR 0004](0004-system-architecture.md)) and its Android app is one binary for every
instance ([ADR 0073](0073-android-app-in-monorepo-kotlin-compose.md), [ADR 0077](0077-android-app-distribution.md)).
An ordinary Firebase Android app is tied at build time to one project's `google-services.json`; shipping ours would
lock every self-hoster out of push. The design therefore has to keep the binary neutral, push optional, and the
server's send credential private.

Forces: no second broker (PostgreSQL is the only service); sessions already revoke
([ADR 0074](0074-bearer-tokens-for-native-clients.md), [ADR 0011](0011-better-auth.md)); hints must stay cheap and
must never leak content; iOS and web push must be addable later without a redesign.

## Decision
- **FCM carries hints only.** A push is a high-priority FCM **data message** with `{ v: 1, kind, id }`, exactly the
  shape and philosophy of the SSE stream in [ADR 0066](0066-live-updates-via-sse-hints.md). It never carries dive
  content, a name, a site or a count; the app fetches through the normal authenticated API and composes the
  notification on device. A wrong or stale hint can cause a needless fetch, never a disclosure.
- **Runtime Firebase configuration, no bundled `google-services.json`.** The server serves its project's public
  client config at **`GET /api/push/config`** (no authentication): `{ android: { projectId, applicationId, apiKey,
  gcmSenderId } }`. The Android app initializes `FirebaseApp` at runtime from it. These values are public by design
  (they ship in every Firebase app); the server sends with the **same project's** service account, so the halves can
  never point at different projects. This is what lets a self-hoster create a free Firebase project and use the
  official app binary.
  - **Not configured is `200 {}`**, not 404: the empty body means "this instance has push off, keep polling", while a
    404 keeps its usual meaning "this server does not have the endpoint" (an older server). The client contract states
    both.
- **Push is optional per instance.** The operator sets `DIVEHUB_FCM_SERVICE_ACCOUNT` and
  `DIVEHUB_FCM_CLIENT_CONFIG` (each JSON inline or a path to a JSON file), both or neither; an error names the missing
  variable. With neither set, `/api/push/config` answers `{}`, no push job is ever enqueued, and clients keep
  foreground SSE plus configurable polling. `POST /api/devices` still accepts registrations while push is off, so
  enabling FCM later lights up existing installs without an app update.
- **The table is `push_device`, not `device`.** `Device` already means the dive computer that recorded a Dive
  ([glossary](../glossary.md), [ADR 0034](0034-equipment-items-and-service-schedules.md)); reusing the word would
  conflate two domain concepts in the schema and the code. The plan's "device table" is this table: `id`,
  `user_id` (FK user, cascade), `session_id` (FK session, cascade), `platform` (`android | ios | web`),
  `push_token` (unique), `created_at`, `last_seen_at`.
  - Rows are **session-scoped**: signing out, ending a session from another device, "sign out everywhere" or a
    password change deletes the session row, and the push token goes with it by cascade. A phone that is no longer
    signed in cannot be woken.
- **Registration over the API.** `POST /api/devices` is session-authenticated (web cookie or native bearer, ADR 0074)
  and upserts `{ platform, pushToken }` for the current User and session, refreshing `lastSeenAt`; registering the
  same token again moves it to the latest User and session (a shared phone signed in as someone else). It answers
  `{ id, platform, createdAt, lastSeenAt }`. `DELETE /api/devices/{id}` removes one of the User's own rows and answers
  404 `push_device_not_found` for anyone else's, so it never reveals another User's registration. The path is
  `/api/devices` as the plan fixed it; the existing `GET /api/devices` and `PATCH /api/devices/{id}` stay Dive-computer
  devices, distinct by method and body.
- **A provider abstraction keeps the other platforms open.** The dispatcher (next slice) calls `sendPush(device,
  hint)`, one implementation per platform. Android over FCM is first. iOS has one hard asymmetry: the last hop is
  APNs with a key bound to the Apple Developer team that owns the App Store binary, so only a credential we issue can
  push to that binary — a later decision (hosted-only, or a hint-only relay). Web Push (VAPID) needs no third-party
  account. The `platform` column exists from day one so both are new providers, not a migration.
- **The service-account key never ships in any artifact.** It is a fleet-wide send credential: a holder can push
  arbitrary messages to tokens in that project, bypass the hint-only rule and burn shared quota. It lives only in the
  server's environment (inline JSON or a file the operator mounts), is read at startup, is never logged, and never
  enters an image, a client, a backup or the repository. The public client config is not that key.

## Consequences
- Push adds freshness in the background without adding a data channel: the API remains the only source of content
  and permissions.
- A self-hoster needs a free Firebase project and two environment values for Android push; without them nothing
  breaks and nothing is enqueued. Their app binary is unchanged.
- Session revocation is also push revocation, for free, through the foreign-key cascade.
- A push token is unique across the instance: one physical install has one row. A shared device that signs in as
  another User moves its row instead of accumulating former Users' pushes.
- `DIVEHUB_FCM_SERVICE_ACCOUNT` and `DIVEHUB_FCM_CLIENT_CONFIG` are both-or-neither, so a half-configured instance
  stops with a clear error instead of advertising push it cannot send.
- The endpoint, the table and the platform enum are the stable part; the dispatcher, the FCM HTTP v1 sender and the
  Android client can change without touching the registration contract.

## Considered options
- **Bundled `google-services.json`.** Rejected: it pins the official binary to our Firebase project and locks
  self-hosters out of push.
- **SSE only.** Rejected: the OS ends the stream in the background, which is exactly when a notification matters.
  SSE stays the foreground path.
- **Polling only.** Rejected as the only path: WorkManager's 15-minute floor is poor for notifications; it stays the
  fallback.
- **UnifiedPush.** Self-hostable and free of Google, but every user would need a distributor app; it can join the
  same provider abstraction later.
- **Web Push (VAPID) now.** Not part of the first slice; it is a later provider behind the same dispatcher and needs
  no third-party account.
