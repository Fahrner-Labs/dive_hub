# Android app

The native Android client, planned in [ADR 0073](../../docs/decisions/0073-android-app-in-monorepo-kotlin-compose.md).
It lives in this repository with its own Gradle build and is **not** a pnpm workspace package.

```
apps/android/
  app/       Jetpack Compose UI, Material 3, the Android Keystore store
  shared/    Kotlin Multiplatform core, no Android imports: Ktor + kotlinx.serialization, generated API models,
             the sign-in flow, the token/cache abstractions and the SQLDelight offline read store
```

`shared/` currently has a JVM target so the Android app can consume it and the unit tests run on the JVM. iOS targets
join when the iOS app starts; the module's source sets are already common.

## Requirements

- JDK 17 or newer.
- Android SDK with `platforms;android-37.0` and `build-tools;37.0.0`, and `ANDROID_HOME` (or `ANDROID_SDK_ROOT`)
  pointing at it. CI installs both.
- The Gradle wrapper downloads Gradle 9.6.0; the first build also downloads the Android Gradle plugin, Kotlin and
  Compose dependencies.

## Signing

Debug builds are signed with the committed [`debug.keystore`](debug.keystore) and the conventional
`android` / `androiddebugkey` credentials, wired in [`app/build.gradle.kts`](app/build.gradle.kts). Because every
machine and CI use the same key, debug APKs install over each other without uninstalling and wiping the SQLDelight
store or the Android Keystore-wrapped session token. A debug key is not a secret and is never used for release.

Release signing does not exist yet; it follows an ADR once the distribution channel is decided ([FAH-281](https://paperclip/FAH/issues/FAH-281)).

## Commands

```sh
cd apps/android
./gradlew :shared:jvmTest            # the core's JVM unit tests
./gradlew :app:assembleDebug         # the debug APK
./gradlew :app:recordRoborazziDebug  # rewrite the Compose screenshots after an intended visual change
./gradlew :app:verifyRoborazziDebug  # compare them (the lane runs this)
```

From the repository root, `pnpm check` runs the Android lane when something under `apps/android/`, the OpenAPI
document or the API generator changed. The lane is `node scripts/check-android.mjs`: it regenerates the API models in
check mode, then runs `:shared:jvmTest`, `:app:assembleDebug` and `:app:verifyRoborazziDebug`. `pnpm check:full` (CI) always runs it.

## Offline store

The logbook's offline read store is SQLDelight ([ADR 0075](../../docs/decisions/0075-android-read-store.md)): the schema
is `shared/src/commonMain/sqldelight/`, the typed facade is `dev.divehub.shared.store.DiveStore`, and the app opens it
with the Android driver in `app/src/main/kotlin/dev/divehub/android/database/`. The JVM unit tests use the in-memory SQLite
driver, so `./gradlew :shared:jvmTest` needs no emulator. Schema changes are numbered `.sqm` migrations.

## API models

`shared/src/commonMain/kotlin/dev/divehub/api/models/` is generated from
[`packages/api-client/openapi.json`](../../packages/api-client/openapi.json) and committed:

```sh
node scripts/generate-android-api.mjs           # after changing the OpenAPI document
node scripts/generate-android-api.mjs --check   # what the Android lane runs
```

The generator is [`scripts/generate-android-api.mjs`](../../scripts/generate-android-api.mjs), with its tests in
`scripts/generate-android-api.test.mjs`. It generates the **transitive closure** of the operation request/response
schemas the app uses (`OPERATIONS` in the script; slice B adds the sign-in request and `/api/me`) and the named models
in `MODEL_SEEDS`. A server-only schema therefore does not churn the Android tree; a later slice adds the operations it
uses and regenerates. Refusals are read through `ApiErrorBody`, which accepts both our Problem body and Better Auth's
`message` body (and keeps an unknown code as it is).

### Why not OpenAPI Generator

OpenAPI Generator 7.x's Kotlin targets were evaluated before writing the script:

- the JVM targets emit `java.time` types, which cannot live in a KMP `commonMain`;
- the `multiplatform` library emits `@Required` annotations it does not define, `@Serializable` twice on some
  classes, and model KDoc copied onto the wrong properties;
- every string enum becomes a Kotlin enum, so a client fails to deserialize a new server value — exactly the failure
  the client contract's "translate the code" rule must survive.

A tailored generator is small, deterministic, has no JVM toolchain of its own, and lets the models keep enum-shaped
values as `String`, which is what a client needs when the server adds a code. Its rules:

- `$ref` becomes a class; inline objects become `ParentProperty` classes; `allOf` is flattened;
- strings, including all string enums, become `String`; `date-time`/`date` stay `String`;
- `anyOf` of one non-null type becomes that type (nullable if the union had `null`); wider unions become
  `JsonElement`;
- optional properties are nullable with `= null`; required properties have no default.

## Screenshot tests

`app/src/test/screenshots/` holds the pictures Roborazzi's Compose test rule renders with Robolectric on the JVM
(`LogbookScreenshotTest`). They pin the contract-visible states of the logbook list: the "Show only" counts, a row
without a Recording, and the findings mark. An intended visual change rewrites them with `:app:recordRoborazziDebug`
and commits the PNGs; CI fails when the drawing no longer matches them.

## Behaviour in this slice

- **Instance URL first.** A first start asks for the self-hosted instance; its origin is stored in preferences. The
  URL has no path (the API lives under `/api` on the same origin).
- **Sign-in** is the ordinary `POST /api/auth/sign-in/email`; the session token comes from `set-auth-token` and is
  stored encrypted with an AES-GCM key in the Android Keystore. Every request sends `Authorization: Bearer`.
- **No previous User.** Sign-in, sign-out and a 401 anywhere clear the token, the cached preferences and the cache
  directory through `UserScopedStore` (client contract §1 Security).
- **Errors by code.** The UI translates the code (`invalid_credentials`, `too_many_attempts`, `user_disabled`, …)
  and never shows the server's English text. A request with no answer uses `error_no_answer`; unknown codes use a
  generic translation.
- **One retry** for a network failure or a 5xx only; 4xx is never retried. A 401 ends the session and shows sign-in.
- **Cleartext HTTP** is allowed only for `localhost`, `127.0.0.1` and the emulator's `10.0.2.2`
  (`res/xml/network_security_config.xml`). A self-hosted instance reachable over the network must use HTTPS.

## Sync and refresh (slice D3)

The sync engine ([`shared/sync/SyncEngine.kt`](shared/src/commonMain/kotlin/dev/divehub/shared/sync/SyncEngine.kt)) fills
the read store in three phases, in order:

1. **Meta** — Divers, body weights, Dive sites and the paged Dive list. The logbook list renders offline after this.
2. **Details** — one `GET /api/dives/{id}` per Dive (Dive, Recordings, Cylinders). The dive page renders offline after this.
3. **Profiles** — one `GET /api/recordings/{id}/samples` per Recording with all its listed channels and the explicit
   `maxPoints=20000` cap the server verified (FAH-269). This runs in the background, is cancellable, and records
   completion per Dive in `sync_checkpoint` (`profiles:<diveId>`), so `SyncEngine.profileSynced(diveId)` and
   `SyncProgress.divesAwaitingProfiles` let a screen mark profiles "not yet synced" for one Dive only.

While online the app opens `GET /api/live` with the bearer token. A `dive` hint refetches list page 1 and that Dive's
detail, then marks its profiles pending; a `site` hint refetches that site. On every (re)connect the list sync runs
again, because hints are not replayed. When the stream is down the foreground poll re-runs phase 1 every minute, and
WorkManager runs the same refresh every 15 minutes; the scheduler is the `PeriodicSyncScheduler` interface in
`shared/`, implemented in `app/` so `shared/` stays Android-free. Deleted Dives come from `GET /api/dives/deleted`,
paged by `nextCursor`, and become local tombstones (`deletedAt`); nothing is hard-deleted, and a Dive the server
restores clears its tombstone on the next list or detail read. `sync_state` keeps
`lastFullSyncAt` and `lastHintAt` for the staleness label and the settings sync line.

The sync engine is serialized with a mutex, so the initial sync, a connect resync and a hint cannot write interleaved
pages. `startSync()` runs when a User signs in; `stopSync()` runs first on sign-out and waits for an in-flight sync
before the store is cleared, so it cannot write the previous User's rows back. A 401 only clears the token and leaves
the store readable (client contract, Offline reads), so it does not wipe.

## The logbook list (slice C1)

Read-only, from `GET /api/dives` ([ADR 0040](../../docs/decisions/0040-logbook-rows-and-filters.md)):

- 25 Dives a page with `limit`/`offset` and `total` (ADR 0017); the pager and the "1–25 of 312" range sit on the list.
- "Show only" toggles with their `counts`; a filter whose count is 0 is hidden unless it is applied. `not-at-provider`
  names the connected Providers from `GET /api/providers` and `GET /api/connections` ("Not in SSI"). No result names
  the applied filters and offers "Show all dives".
- `totals` above the list and month headings with all of each month's Dives and their time (ADR 0040).
- Rows say the Recordings count ("No recording"), the source Provider, gas, the computer's surface interval and the
  participants as initials in circles, with names and roles as the row's accessible text. The findings mark is text.
  The `profile` sketch shares one depth scale for every row (`totals.deepestM`, at least 10 m); fast ascent is thicker
  and coloured. A null profile draws nothing, or an empty dashed box when the Dive has no Recording (ADR 0041).
- Pull-to-refresh; no answer shows a "no connection" state, distinct from the translated error codes.

## The dive page (slice C2)

Read-only, from `GET /api/dives/{id}`, `GET /api/dives/{id}/neighbours` and `GET /api/recordings/{id}/samples`
(client contract, Dives):

- **Values with overrides.** A value set by hand (`overrides`) says "edited" and what the recording has, as visible
  text (ADR 0015). The number is display only: read-only, never a field (ADR 0048).
- **Time** is the local time at the dive; `utcOffsetSource: unknown` shows it in UTC without an offset, as logged
  (ADR 0030). Where the offset came from (`position`, `nearby`, `unknown`) is said in words, unless the start was set
  by hand. The timezone words, override marking and the profile's text live in `shared/dive` with JVM unit tests.
- **Recordings** are named by their Device ("Garmin Descent Mk3 (777)") and the Primary one is marked (ADR 0016). A
  joined Recording's parts sit under it, named by device and time (ADR 0063). A Dive without a Recording names its
  `fromProvider` and says the computer's file adds the recording later; there is no profile then.
- **Depth profile** with a text alternative: a summary (deepest point and when, time, temperature range) and the
  samples per minute as a table (WCAG 1.1.1). Surface breaks are a band at the surface, each with its length
  (ADR 0063). The profile is the Primary recording's; the samples are read once the Dive is.
- **Water:** the Dive's water type is its site's; the Recording's `summary.waterType` is labelled as the computer's
  water setting. `waterMismatch` names both waters and how the depths read ("about 2 % shallow").
- **Participants** with roles, and the Dive's own position only when the API sends it. A Participant's
  `certificationHint` and its link to the Diver's certifications are left to the Divers slice; this page shows names
  and roles only.
- **Previous/next** follow the logbook's filters (ADR 0042): "previous" is the row above, the position reads
  "Dive 12 of 312", and a control at either end stays but says it is unavailable. Back returns to the logbook with
  its filters, on the page the Dive is on (ADR 0042).
- Errors show by `code`; no answer shows the "no connection" state. A profile that cannot be read leaves the rest of
  the page standing.

The dive page's contract-visible states are screenshots (`DiveDetailScreenshotTest`): an edited value, the timezone
note, a Dive without a Recording, the water mismatch, and Surface breaks in the profile.

## The tools (slice U1)

Gas numbers and a bottom time, typed in and computed on the device from the ported kernels (client contract, Tools;
ADR 0032). `shared/tools/` assembles the inputs, calls `shared/planning/` and shapes the answer exactly like the API's
service shells: the same defaults, `used`/`source`, binding limits, `outside_limits` and rounding. The generated
OpenAPI models for `GET /api/tools/gas` and `GET /api/tools/bottom-time` are the contract.

- The notice is above the inputs and every answer repeats it: an estimate, not advice.
- The User's own Diver comes from the synced store: the GF high from their latest Recording, the planning SAC from
  their Dives (with `basedOn`), and a Dive within the last 48 hours. A personalized answer says "Based on dives synced
  as of …"; without a sync the screen says so as an empty state and the numbers use only what is typed.
- Residual nitrogen is **not** seeded offline yet: `residualN2` is null and the recent-Dive notice says the limit
  starts from clean tissues. `recentDiveEndedAt` and the rest of the shape match the API.
- `outside_limits` shows the reason and the MOD, never a number; MODs are rounded down to 0.1 m, never up.
- Errors are codes the screen translates; units follow the User's metric/imperial setting (depths and pressures are
  converted for display and back before the kernels see them).

The contract-visible states are screenshots (`ToolsScreenshotTest`): the notice, assumptions with sources and
`basedOn`, the binding limit, `outside_limits`, and the empty store.

## The gas plan (slice U2)

Plan the gas of up to eight levels for up to eight Divers, offline, from `shared/tools` and the ported kernel
(client contract, Gas plans; ADR 0033). The answer is the generated `POST /api/tools/gas-plan` model;
nothing is stored.

- The User's synced Divers are offered by name. One whose Dives are on the device brings their planning SAC (with
  `basedOn`) and a Dive within 48 hours; a typed SAC wins, and a guest or a Diver with no synced Dives plans with the
  stated 20 L/min. The screen says where each SAC came from.
- Every rule is explained in words (rock bottom, fixed reserve, fixed ascent pressure, halves, thirds, sixths); rock
  bottom is always shown per level, whichever rule is chosen, and a rule that leaves less than rock bottom says so.
- The answer marks the controlling Diver, gives each Diver's gas, end, rock bottom, turn (under a turn rule), ascent
  pressure and margin, the briefing ("everyone follows …"), and when the plan does not fit: the run time by which to
  leave the last level.
- The stretches between the levels are shown in words in the order they are dived (descent, level, ascent, safety
  stop, with minutes and run time), because the plan does not draw a profile.
- The plan says it does not check the no-decompression limit over several levels; the gas, the MOD and 40 m are what
  it checks. Errors are codes the screen translates, and the notice sits above every input.
- The mix, levels, Divers, water, rule and rates are typed in the User's units (metres and bar are converted before the
  kernel), and the assumptions list the mix, water, rule, rates and each SAC with its source.

The contract-visible states are screenshots (`GasPlanScreenshotTest`): the notice with an empty plan, a plan that
fits, one that does not, a rule below rock bottom, `outside_limits`, and a turn rule's briefing.

## The personalized tools (slice U3)

The lead estimate and the current loadings, computed offline from the synced store (client contract, Offline reads
and Tools; ADR 0031, ADR 0071). `shared/tools/PersonalizedSourcesStore.kt` assembles the Diver's own Dives,
body weight, sites, Recordings and series in one store transaction; `PersonalizedTools.kt` shapes the answers exactly
like `GET /api/divers/{id}/lead-estimate` and `GET /api/divers/{id}/loadings`, whose generated models are the
contract. Nothing is fetched from the API for these results.

- **Lead estimate:** the conditions (water, suit with thickness, hood and undergarment, one to four Cylinders, unit)
  are typed; the answer always carries the total with its range, the confidence in words, the Dives it rests on
  (each linked to its Dive page), each adjustment with its reason, what is missing and where to add it, the Dives
  left out for a site without a water type, and the weight check in every state.
- **Current loadings:** residual N₂, the oxygen clock and OTU, and DAN's no-fly time as information with its source.
  A missing estimate stays missing (with the reason the shape allows); a returned zero is shown as a real zero. The
  N₂ line says the synced profiles are downsampled.
- **Staleness:** every personalized result says "Based on dives synced as of …" from `sync_state.lastFullSyncAt`,
  with "(profiles still syncing)" while the profiles phase (FAH-273) is incomplete. The profiles checkpoints decide
  once they exist; before that the store's own channels and series do.
- Errors are codes the screen translates (`invalid_input`, `suit_invalid`); amounts are already in the answer's unit
  and are never converted again. The lead screen's Dive links open the existing Dive page.

The contract-visible states are screenshots (`PersonalizedScreenshotTest`): the lead estimate with the staleness
label and profiles marker, the no-number state with the weight check, the loadings with their basis, and a missing
estimate beside a real zero (`loadings_missing_zero.png`).
