---
title: "ADR 0090: Android default instance"
summary: The Android app signs in to Dive Hub Cloud by default: the first screen is the sign-in form against a build-config default, and a secondary action reveals the address field for a self-hosted instance. Other distributions can ship their own default. Amends ADR 0074's "no fixed host".
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

The hosted track is the same artifact, configured rather than forked (the hosted-instance configuration decision,
ADR 0087, landed with FAH-527; dual-track invariant). The Android app needs the same property: one codebase whose
default address is packaging configuration, not a second product. [ADR 0077](0077-android-app-distribution.md)
distributes the official APK, but the source may also be built by a self-hoster or an F-Droid-style distributor, who
need their own sensible default.

Forces:

- A custom instance must stay reachable; the app still has to reach any Dive Hub (ADR 0074).
- The address the User enters is authoritative. Nothing may quietly connect somewhere else.
- The Android build already carries compile-time switches through `buildConfigField` (`SCANNER_ENABLED`,
  `EDIT_DIVE_SCALARS`), so a default address fits the existing mechanism.
- A stored instance and a session must keep working exactly as before. The default only decides first start, and
  asking for an address must not be the first thing a new Dive Hub Cloud User sees.

## Decision
- **`DEFAULT_INSTANCE_URL` is a build configuration field.** `apps/android/app/build.gradle.kts` puts it in
  `BuildConfig`; the official build sets it to the Dive Hub Cloud URL. A build can override it as a whole with the
  `divehub.defaultInstanceUrl` Gradle property, or for one build type with `divehub.defaultInstanceUrl.<buildType>`,
  so self-hosters and F-Droid-style builds can ship their own default without a code fork. A blank override means no
  default.
- **The app starts on the sign-in screen for the default.** When no instance is stored, the sign-in form is the first
  screen, pointed at `DEFAULT_INSTANCE_URL`; its existing health probe runs on show, so a wrong or stale default is
  reported as "Not reachable" instead of failing as a misleading sign-in error. The User signs in without typing an
  address.
- **Self-hosting is a secondary action on the sign-in screen.** "Connect to a self-hosted instance" reveals the
  address form; only then is the URL field shown. The form probes the address with the existing health check before
  asking for credentials (ADR 0074), offers Back to the default sign-in while a default exists, and nothing is stored
  until the User signs in.
- **A build with no default keeps the old fallback.** With a blank default and nothing stored, the address form is
  the first screen, exactly as before; there is nothing to sign in to yet.
- **A stored instance always wins.** Once a User has connected, `InstanceUrlStore` is the source of truth and the
  default is not shown. "Change instance" still ends the session and wipes the local data as today, then opens the
  address form, with the default sign-in one tap back.
- Nothing else changes: no fixed host is baked into the session, sync, push or deep-link paths, and the server-side
  hosted configuration (ADR 0087, landed with FAH-527) is untouched.

## Consequences
- The official app opens on a ready sign-in for Dive Hub Cloud, while a self-hoster opts into the address field.
- The default is packaging, not behaviour: the same source can build the cloud app, a self-hosted app and an
  F-Droid-style app from one branch.
- A wrong or stale default is a non-resolving address that the sign-in screen's health probe rejects with the
  existing "not a Dive Hub" answer; it never becomes a silent redirect, and the self-hosted path stays available.
- The placeholder constant must be replaced with the owner-confirmed cloud URL before the official build ships, and
  a release with the placeholder cannot reach Dive Hub Cloud.
