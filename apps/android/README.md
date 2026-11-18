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
./gradlew :shared:jvmTest          # the core's JVM unit tests
./gradlew :app:assembleDebug       # the debug APK
```

From the repository root, `pnpm check` runs the Android lane when something under `apps/android/`, the OpenAPI
document or the API generator changed. The lane is `node scripts/check-android.mjs`: it regenerates the API models in
check mode, then runs `:shared:jvmTest` and `:app:assembleDebug`. `pnpm check:full` (CI) always runs it.

## API models

`shared/src/commonMain/kotlin/dev/divehub/api/models/` is generated from
[`packages/api-client/openapi.json`](../../packages/api-client/openapi.json) and committed:

```sh
node scripts/generate-android-api.mjs           # after changing the OpenAPI document
node scripts/generate-android-api.mjs --check   # what the Android lane runs
```

The generator is [`scripts/generate-android-api.mjs`](../../scripts/generate-android-api.mjs). It generates every
component schema, plus the operation request/response schemas the app uses (`OPERATIONS` in the script; slice B adds
sign-in, sign-out, get-session and `/api/me`). Later slices add the operations they use there and regenerate.

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
