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

Release builds follow [ADR 0077](../../docs/decisions/0077-android-app-distribution.md): the app ships as a
release-signed APK attached to GitHub Releases, signed with a self-managed key held in GitHub Actions secrets. The
`release` build type reads four environment variables; when any is missing, `:app:assembleRelease` fails with
"release signing env vars not set" instead of falling back to the debug key:

| Variable | Value |
|---|---|
| `ANDROID_RELEASE_KEYSTORE_PATH` | Absolute path of the JKS keystore |
| `ANDROID_RELEASE_KEYSTORE_PASSWORD` | Keystore password |
| `ANDROID_RELEASE_KEY_PASSWORD` | Key password |
| `ANDROID_RELEASE_KEY_ALIAS` | Key alias |

The Android release lane ([`.github/workflows/android-release.yml`](../../.github/workflows/android-release.yml))
holds them as the GitHub Actions secrets `ANDROID_RELEASE_KEYSTORE_BASE64` (the JKS, base64-encoded),
`ANDROID_RELEASE_KEYSTORE_PASSWORD`, `ANDROID_RELEASE_KEY_ALIAS` and `ANDROID_RELEASE_KEY_PASSWORD`; it decodes the
keystore to `$RUNNER_TEMP` and exports the four variables for the `assembleRelease` step. Nothing reads them from a
file in the repository. To check the build locally, use a throwaway keystore outside the tree — never the release key:

```sh
keytool -genkeypair -keystore /tmp/throwaway.jks -storepass throwaway -keypass throwaway \
  -alias throwaway -keyalg RSA -keysize 2048 -validity 1 -dname 'CN=throwaway'
ANDROID_RELEASE_KEYSTORE_PATH=/tmp/throwaway.jks ANDROID_RELEASE_KEYSTORE_PASSWORD=throwaway \
ANDROID_RELEASE_KEY_PASSWORD=throwaway ANDROID_RELEASE_KEY_ALIAS=throwaway \
  ./gradlew :app:assembleRelease
```

`*.jks` and `*.keystore` are gitignored, except the committed debug keystore.

### Versioning

`versionName` is the tag without `android-v` (`android-v0.1.0-rc1` → `0.1.0-rc1`); `versionCode` is derived from it
([ADR 0077](../../docs/decisions/0077-android-app-distribution.md) D5). Local builds default to `0.1.0-dev` / `1`.
The release lane exports `ANDROID_VERSION_NAME` and `ANDROID_VERSION_CODE` from the `versionName=` / `versionCode=`
lines the helper prints:

```sh
node scripts/android-version-code.mjs android-v0.1.0-rc1
# tag=android-v0.1.0-rc1 versionName=0.1.0-rc1 versionCode=1001 prerelease=true
```

### Cutting a release

After the owner merges the release-lane change and confirms the cold backup (ADR 0077 D2), push
`android-vX.Y.Z` or `android-vX.Y.Z-rcN` on main. The lane verifies the tag, builds and signs the APK, checks the
signature and attaches it to the GitHub Release as `DiveHub-<versionName>.apk` — for example
`android-v0.1.0-rc1` publishes `DiveHub-0.1.0-rc1.apk`. Repair a failed run with *Run workflow* and the existing tag as
`version`; the release is edited and the APK replaced, never recreated.

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

### The logbook and dive page read the store (slice D4)

From FAH-277 the screens never fetch logbook data themselves: `GET /api/dives`, `GET /api/dives/{id}` and
`GET /api/recordings/{id}/samples` fill the store during sync. The two Push-backed filters (`changed-since-sent` and
`not-at-provider`) are the exception the client contract names: they are fetched online because the store holds no
Push state, and offline the cached list is shown without them. `shared/logbook/StoreLogbook.kt` reads the stored list
rows and Dive views (the API's own JSON, beside the typed columns) and computes the totals and month figures from the
same rows the server does. The list and the dive page show "Last synced …" from `sync_state.lastFullSyncAt`, in the
device's time zone (`ui/SyncTimeFormat.kt`, FAH-296), only while the live stream is down; while it is connected they
stay quiet, and Settings shows the connection state beside the instance (FAH-342). A Dive whose profile series are
still missing from phase 3 says "Profile syncing"/"still syncing" from its `profiles:<id>` checkpoint.

- Chips: `no-recording`, `no-site` and `with-findings` are answered from the stored rows. Their counts (and the two
  Push-backed ones) come from the last list sync, so a chip keeps its number offline. `changed-since-sent` and
  `not-at-provider` need server-side Push state: they are fetched online and, with no answer, the cached list is shown
  without them and the screen says the filter needs a connection.
- Offline with a cache shows the stored list or Dive with "No connection. Showing … synced as of …"; before the first
  sync there is nothing to show, so the pre-sync "No connection. Check your connection and try again." state remains.
- The open screens follow `SyncRefresher.revision` and reread the store after a hint or refresh.

Schema v2 (`1.sqm`) adds `dive.listJson`, `dive.detailJson` and `sync_state.countsJson`. A store migrated from v1 is
backfilled by the next sync, so it reads the API's payloads too.

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
(`LogbookScreenshotTest`, `DiveDetailScreenshotTest`). They pin the contract-visible states: the logbook's "Show only"
counts, a row without a Recording, a row whose Dive has findings, the sync line, a dive whose profiles are still
syncing, and offline-with-cache versus offline-empty on both screens. An intended visual change rewrites them with
`:app:recordRoborazziDebug` and commits the PNGs; CI fails when the drawing no longer matches them.

## Behaviour in this slice

- **Instance URL first.** A first start asks for the self-hosted instance; its origin is stored in preferences. The
  URL has no path (the API lives under `/api` on the same origin). Before it moves from the address to sign-in, and
  again whenever the sign-in screen is shown, the app probes `GET /api/health/live` (5 s timeout): a typo, an
  offline phone or a host that is not a Dive Hub keeps the address form with the reason, and the sign-in screen shows
  the reachability chip.
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
2. **Details** — pages `GET /api/dives?include=details` (50 a page, the server's cap), each row a whole Dive view:
   Dive, Recordings, Cylinders. The dive page renders offline after this. A checkpoint that misses a Dive (it was
   deleted between the phases, or a page boundary moved) is repaired with the single `GET /api/dives/{id}` fetch,
   which is also what a `dive` hint uses.
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

While the first sync runs, `SyncRefresher.progress` follows the phases, and the logbook's pre-sync loading state shows
how many Dives phase 1 has found and how many details phase 2 has stored ("Syncing your dives… 42 of 130"). The bar is
indeterminate until the first detail lands. Progress never holds back already-synced content: a cached list renders as
soon as the store has one, and the tools keep working from the store while a sync continues.

## The logbook list (slice C1)

Read-only, from the synced store ([ADR 0040](../../docs/decisions/0040-logbook-rows-and-filters.md)); the rows and
counts are the API's own, read from the store (see slice D4 above):

- One continuous list (`LazyColumn`) of the whole stored list, newest first, under sticky month headings. Only the two
  server-answered filters (`changed-since-sent`, `not-at-provider`) keep 25-Dive pages with `limit`/`offset`, the pager
  and the "1–25 of 312" range (ADR 0017).
- The top bar's filter action opens a `ModalBottomSheet` with the "Show only" toggles and their `counts`; a filter
  whose count is 0 is hidden unless it is applied. With filters applied the action carries their count as a badge, so
  the state stays visible while the sheet is closed. Offline, the sheet says a server-only filter needs a connection.
  `not-at-provider` names the connected Providers from `GET /api/providers` and `GET /api/connections` ("Not in SSI").
  No result names the applied filters and offers "Show all dives".
- `totals` above the list and sticky month headings with all of each month's Dives and their time (ADR 0040).
- Rows say the Recordings count ("No recording"), the source Provider, gas, the computer's surface interval and the
  participants as initials in circles, with names and roles as the row's accessible text. Findings are not marked on a
  row; until the Dive page shows assessments, the `with-findings` filter is how they are found.
  The `profile` sketch shares one depth scale for every row (`totals.deepestM`, at least 10 m); fast ascent is thicker
  and coloured. A null profile draws nothing, or an empty dashed box when the Dive has no Recording (ADR 0041).
- Pull-to-refresh re-runs the sync. With a cache, no answer shows the stored list and its age; before the first sync
  it shows the "no connection" state, distinct from the translated error codes.

## The dive page (slice C2)

Read-only, from the synced store: the Dive, its neighbours and the Primary recording's series (client contract,
Dives):

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
  its filters, scrolled to the Dive's row; a server-answered filter pages to it instead (ADR 0042).
- Errors show by `code`; with a cache, no answer shows the stored Dive and its age, and before the first sync the
  "no connection" state. A profile still syncing says so; one that cannot be read leaves the rest of the page standing.

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
  in the device's time zone (FAH-296), with "(profiles still syncing)" while the profiles phase (FAH-273) is
  incomplete. The profiles checkpoints decide once they exist; before that the store's own channels and series do.
- Errors are codes the screen translates (`invalid_input`, `suit_invalid`); amounts are already in the answer's unit
  and are never converted again. The lead screen's Dive links open the existing Dive page.

The contract-visible states are screenshots (`PersonalizedScreenshotTest`): the lead estimate with the staleness
label and profiles marker, the no-number state with the weight check, the loadings with their basis, and a missing
estimate beside a real zero (`loadings_missing_zero.png`).

## Settings and the bottom bar (T3)

The signed-in shell has three bottom-bar places — Logbook, Tools and Settings (`ui/navigation/DiveHubBottomBar.kt`) —
and a Dive is pushed above the place that opened it, never into the bar; the bar stays on that place selected. A switch uses `saveState`/`restoreState`, so
the logbook comes back where the User left it: its saved scroll position, or the page that shows the Dive a
server-answered filter was stepped from. Back from the logbook (the `main` graph's start destination) leaves the app
and never walks back through sign-in.

The settings screen (`ui/settings/`) is a grouped M3 list: **Connection** shows the instance from
`PreferencesInstanceUrlStore`, its live sync state (FAH-342), and "Sync now", which reuses the logbook's `SyncLine`
source (`sync_state.lastFullSyncAt` and the refresher's offline flag) and runs the same guarded `refreshNow` path as the
periodic worker; "Change instance" asks first, then signs out, wipes the local data and forgets the
address so the app asks for an instance again ([settings/SettingsRepository.kt](shared/src/commonMain/kotlin/dev/divehub/shared/settings/SettingsRepository.kt)).
**Account** shows the `/api/me` e-mail and owns sign-out, which used to sit on the logbook app bar; it wipes the
session and the graph pops to sign-in.
**About** shows `versionName (versionCode)` from `BuildConfig` (real values stamped by the release lane, ADR 0077) and
opens the bundled Poppins OFL licence from `assets/licenses/`.

EN and DE strings are in the app resources; the four light/dark frames and the three bottom-bar frames are Roborazzi
records (`SettingsScreenshotTest`, `BottomBarScreenshotTest`).
