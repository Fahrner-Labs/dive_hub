---
title: "ADR 0073: Native Android app in the monorepo; Kotlin, Jetpack Compose, KMP core"
summary: The Android app lives at apps/android in this repository with its own Gradle build; it is written in Kotlin with Jetpack Compose, and all non-UI code sits in a Kotlin Multiplatform core module so the later iOS app reuses it.
status: proposed
date: 2026-11-18
---

# ADR 0073: Native Android app in the monorepo; Kotlin, Jetpack Compose, KMP core

## Status
Proposed – 2026-11-18. Discussion and the owner's approval of the approach: [FAH-255](https://paperclip/FAH/issues/FAH-255).

## Context
[ADR 0004](0004-system-architecture.md) always planned a mobile app as a client of the same API, and the
[client contract](../spec/clients.md) was written so that "a new client (the planned native mobile app) misses
none" of its duties. Android comes first; iOS is planned later. Two questions needed an answer: where the code
lives, and which stack builds it.

[ADR 0005](0005-typescript-stack.md) picked React for the web client partly for "a path to React Native/Expo for
mobile". That path is not taken (see Considered options); this ADR supersedes that remark.

What shapes the decision:

- The API is the contract: the OpenAPI document (`packages/api-client/openapi.json`) is generated from the
  server, and the client contract pins dozens of client duties (paging, version conflicts, import polling,
  privacy texts) that every client must fulfil.
- The expensive part of a mobile client here is contract behaviour, not widgets — and it is exactly the part iOS
  would otherwise reimplement.
- Governance ([ADR 0023](0023-faster-checks.md) checks, [ADR 0054](0054-unattended-implementation.md) one branch
  one PR, CODEOWNERS) exists once, in this repository.

## Decision
- **The app lives in this repository at `apps/android/`.** It has its own Gradle build (Kotlin DSL) and wrapper
  and is **not** a pnpm workspace package (the workspace globs only JS packages). `pnpm check`/`check:full` gains
  an Android lane (Gradle build + unit tests) so the existing gate covers it; the same branch/PR rules apply.
- **Stack: Kotlin + Jetpack Compose** (Material 3, which maps onto the tonal-surfaces design system,
  [ADR 0019](0019-tonal-surfaces.md)). All user-facing text comes from string resources; errors are shown by
  their `code`, as for every client ([ADR 0014](0014-design-system-and-localization.md)).
- **All non-UI code sits in a Kotlin Multiplatform core module** (`apps/android/shared/`) from day one: API
  models generated from the OpenAPI document (regeneration checked in CI, so contract drift fails the build),
  the HTTP layer (Ktor client + kotlinx.serialization), paging, version-conflict handling, import polling,
  live-hint handling and units. The module has no Android imports and is unit-tested on the JVM.
- **The Android UI** (`apps/android/app/`) is Compose on top of that core. **iOS later gets a SwiftUI shell on
  the same core**; whether Compose Multiplatform replaces SwiftUI is decided when iOS starts, not now.
- Authentication uses bearer tokens ([ADR 0074](0074-bearer-tokens-for-native-clients.md)).

## Considered options
- **A separate repository.** Isolation, and the Gradle build stays out of this repo's CI. Rejected: the client
  contract and the OpenAPI document would become cross-repo references that drift; every API change would need
  coordination across two repositories; governance would have to be built a second time.
- **React Native / Expo** (the ADR 0005 remark). Would reuse some TypeScript (units, gas math). Rejected: the ask
  is a native app; the shareable TS is small next to the contract behaviour, which RN does not share; a second UI
  paradigm and weaker platform fit.
- **Flutter.** Same objections as React Native, plus a third language in the project.
- **Compose Multiplatform for iOS UI from the start.** Deferred: one UI toolkit decision is enough for now; the
  KMP core keeps the option open either way.

## Consequences
- CI needs an Android SDK lane (Gradle build and JVM unit tests; emulator tests optional at first), and the repo
  carries two build systems (pnpm + Gradle) with two dependency-update tracks.
- API changes that affect clients touch server, web and Android in one PR — visible in one diff, reviewed once.
- A change that gives clients a new duty updates the client contract in the same change, as AGENTS.md already
  requires; the Android app makes that rule real for a second consumer.
- ADR 0005's "path to React Native/Expo" consequence is void; its other consequences (one language for server,
  worker and web) stand.
