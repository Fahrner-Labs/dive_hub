---
title: "ADR 0090: Android default instance"
summary: The Android app prefills the first-start instance address from a build-config default (Dive Hub Cloud in the official build); the field stays editable, so custom instances remain one tap away and other distributions can ship their own default. Amends ADR 0074's "no fixed host".
status: proposed
date: 2026-11-26
---

# ADR 0090: Android default instance

## Status
Proposed – 2026-11-26. Amends [ADR 0074](0074-bearer-tokens-for-native-clients.md)'s rule that "the app asks for the
instance URL at first start (self-hosted instances have no fixed host)". Comes from §12 of the accepted hosted-Dive
Hub execution plan ([FAH-524](https://paperclip.privatenode.de/FAH/issues/FAH-524), revision `ff67973a`),
implementation task [FAH-537](https://paperclip.privatenode.de/FAH/issues/FAH-537). The owner accepts it by merging
its pull request: `docs/decisions/**` is CODEOWNERS-protected.

**Open value.** The cloud URL itself is not decided yet. This ADR and the build use a clearly marked placeholder
until the owner names the address; the constant must be replaced before the official app is released.

## Context
[ADR 0074](0074-bearer-tokens-for-native-clients.md) makes every first start ask for an instance address because a
self-hosted Dive Hub has no fixed host. That is right for self-hosters and for a client that must work against any
instance, but it puts the hosted product behind a typing task: a new User of Dive Hub Cloud has to know and enter an
address before they can sign in.

The hosted track is the same artifact, configured rather than forked ([ADR 0087](0087-hosted-instance-configuration.md),
dual-track invariant). The Android app needs the same property: one codebase whose default address is packaging
configuration, not a second product. [ADR 0077](0077-android-app-distribution.md) distributes the official APK, but
the source may also be built by a self-hoster or an F-Droid-style distributor, who need their own sensible default.

Forces:

- A custom instance must stay one edit away; the app still has to reach any Dive Hub (ADR 0074).
- The address the User types is authoritative. Nothing may quietly connect somewhere else.
- The Android build already carries compile-time switches through `buildConfigField` (`SCANNER_ENABLED`,
  `EDIT_DIVE_SCALARS`), so a default address fits the existing mechanism.
- A stored instance and a session must keep working exactly as before; the default is only the starting text of the
  first-start form.

## Decision
- **`DEFAULT_INSTANCE_URL` is a build configuration field.** `apps/android/app/build.gradle.kts` puts it in
  `BuildConfig`; the official build sets it to the Dive Hub Cloud URL. A build can override it as a whole with the
  `divehub.defaultInstanceUrl` Gradle property, or for one build type with `divehub.defaultInstanceUrl.<buildType>`,
  so self-hosters and F-Droid-style builds can ship their own default without a code fork. A blank override means no
  prefill.
- **The first-start instance form is prefilled with `BuildConfig.DEFAULT_INSTANCE_URL`.** *Continue* submits the
  prefilled value unchanged; the field remains editable, so a custom instance is one edit away. The form still
  probes the address with the existing health check before asking for credentials (ADR 0074), and nothing is stored
  until the User signs in.
- **A stored instance always wins.** Once a User has connected, `InstanceUrlStore` is the source of truth; the
  default only fills an empty first-start field. "Change instance" behaves as today.
- Nothing else changes: no fixed host is baked into the session, sync, push or deep-link paths, and the server-side
  hosted configuration (ADR 0087) is untouched.

## Consequences
- The official app is one tap from sign-in on first start, while a self-hoster still types their address once.
- The default is packaging, not behaviour: the same source can build the cloud app, a self-hosted app and an
  F-Droid-style app from one branch.
- A wrong or stale default is a non-resolving address that the health probe rejects with the existing
  "not a Dive Hub" answer; it never becomes a silent redirect.
- The placeholder constant must be replaced with the owner-confirmed cloud URL before the official build ships, and
  a release with the placeholder cannot reach Dive Hub Cloud.
