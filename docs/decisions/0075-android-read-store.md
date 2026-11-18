---
title: "ADR 0075: SQLDelight for the Android read store"
summary: The shared Kotlin core keeps the offline read store in SQLDelight; the schema is versioned .sq files with SQL migrations, the Android app supplies the Android driver and JVM tests use the SQLite driver.
status: proposed
date: 2026-11-18
---

# ADR 0075: SQLDelight for the Android read store

## Status
Proposed – 2026-11-18. Discussion and the accepted plan: [FAH-265](https://paperclip/FAH/issues/FAH-265)
(plan rev `32a49ddc`, task D1). The owner approves this ADR on the pull request
([CODEOWNERS](../../.github/CODEOWNERS)).

## Context
The Android client reads the logbook offline from an on-device store. The Android V1 offline plan (FAH-265) puts
that store in `shared/`, the Kotlin Multiplatform core from
[ADR 0073](0073-android-app-in-monorepo-kotlin-compose.md): the core has no Android imports, is unit-tested on
the JVM, and serves a later iOS app. The store is the single source for UI reads from day one and the substrate for
a later write path, so its schema must be versioned, migratable and typed rather than a bag of JSON blobs.

Two candidates fit: Room, whose Kotlin Multiplatform support is newer and brings `androidx.room` into
`commonMain`, and SQLDelight, which is multiplatform-first and compiles SQL to typed Kotlin. The schema also has to
survive API changes: a synced row carries the server `id`, its `version` where the API exposes one, and `deletedAt`,
and a later change feed must reuse the same tables.

## Decision
- **Use SQLDelight** for the read store. The schema lives as `.sq` files in
  `apps/android/shared/src/commonMain/sqldelight/`, is generated into typed Kotlin queries and tables, and is
  versioned with numbered `.sqm` migrations. The Android app supplies `android-driver`; the JVM unit tests use
  `sqlite-driver` against an in-memory database.
- **The store lives in `shared/`** behind a small facade in `dev.divehub.shared.store`. It accepts a
  `SqlDriver`, so `shared/` stays free of Android imports; the driver choice belongs to `app/` (Android) or to a
  test (JVM).
- **Schema v1 mirrors the offline plan's tables**: `dive`, `recording`, `recording_series`, `cylinder`,
  `dive_site`, `diver`, `body_weight`, plus the internal `sync_checkpoint` and `sync_state`. Complex API values
  (device, suit, lead, summaries, channel arrays) stay in `TEXT` JSON columns; identity, version, deletion and
  ordering fields are real columns so a later change feed and conflict path can query them.
- **No foreign keys in v1.** Sync may write pages out of order and keeps tombstones; the store owns consistency
  rather than SQLite cascades.
- **Dependencies:** the SQLDelight Gradle plugin and runtime `2.4.1`, `sqlite-driver` for shared JVM tests and
  `android-driver` for the app. They are part of this decision and are listed in the pull request.

## Considered options
- **Room.** It has Android-first ergonomics and is familiar, but its KMP support is younger and `androidx.room`
  would enter `commonMain` of a module that must stay Android-free. It offers no advantage for a read-only store
  whose schema is written once and migrated deliberately.
- **Raw `android.database.sqlite` in the app.** Smallest dependency, but it breaks ADR 0073's split (the store must
  be shared and JVM-testable), loses typed queries and migrations, and cannot serve iOS.
- **SQLDelight in `app/` only.** The driver is Android-only there; the store would not be unit-tested on the JVM and
  iOS would need a second implementation.

## Consequences
- The Android lane gains a Gradle plugin and code generation; generated Kotlin is build output, not committed.
- Schema changes are `.sqm` migrations and are reviewed with the same care as server migrations; the store's
  version is the schema version in `sync_state`.
- `shared/` stays platform-free: only `app/` names an Android driver, and replacing the store later is a
  store-layer rewrite, not a UI rewrite.
- The store facade is synchronous. The sync engine and UI call it off the main thread; transactions are the
  boundary, not individual statements.
