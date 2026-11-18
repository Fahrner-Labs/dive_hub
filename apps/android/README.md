# Android app

The native Android client, planned in [ADR 0073](../../docs/decisions/0073-android-app-in-monorepo-kotlin-compose.md).
It lives in this repository with its own Gradle build and is **not** a pnpm workspace package.

```
apps/android/
  app/       Jetpack Compose UI, Material 3, the Android Keystore store
  shared/    Kotlin Multiplatform core, no Android imports: Ktor + kotlinx.serialization, generated API models,
             the sign-in flow and the token/cache abstractions
```

`shared/` currently has a JVM target so the Android app can consume it and the unit tests run on the JVM. iOS targets
join when the iOS app starts; the module's source sets are already common.

## Requirements

- JDK 17 or newer.
- Android SDK with `platforms;android-37.0` and `build-tools;37.0.0`, and `ANDROID_HOME` (or `ANDROID_SDK_ROOT`)
  pointing at it. CI installs both.
- The Gradle wrapper downloads Gradle 9.6.0; the first build also downloads the Android Gradle plugin, Kotlin and
  Compose dependencies.

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
