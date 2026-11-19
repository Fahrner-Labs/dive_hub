---
title: "ADR 0077: Android app distribution — self-signed APK via GitHub Releases"
summary: The Android app ships as a release-signed APK attached to GitHub Releases, driven by android-vX.Y.Z(-rcN) tags on main. We hold the release signing key ourselves in a PEPK-compatible form, so a later move to Google Play keeps the same signing identity and forces no reinstalls.
status: proposed
date: 2026-11-19
---

# ADR 0077: Android app distribution

## Status
Proposed – 2026-11-19. Drafted by the CTO from the channel decision on the Paperclip issue
[FAH-284](https://paperclip.privatenode.de/FAH/issues/FAH-284) (CEO: "self-distributed APK via GitHub Releases")
and the key-management research in [FAH-281](https://paperclip.privatenode.de/FAH/issues/FAH-281). The owner accepts
it by merging its pull request: `docs/decisions/**` is CODEOWNERS-protected.

## Context
The Android app ([ADR 0073](0073-android-app-in-monorepo-kotlin-compose.md)) builds CI-green debug APKs signed with
the committed `apps/android/debug.keystore` (FAH-282). Nothing release-shaped exists: no `release` build type, no
release key, no distribution channel, and [ADR 0068](0068-release-pipeline.md)'s release pipeline covers Docker
images only. [FAH-281](https://paperclip.privatenode.de/FAH/issues/FAH-281) established that the signing key *is*
the app's identity — Android refuses updates signed differently — and that the right production-key strategy depends
on the distribution channel. The channel is now decided: self-distributed APKs via GitHub Releases, matching the
self-hosted ethos of the product.

## Decision

### D1 — Channel: APK attached to GitHub Releases
The app is distributed as a release-signed APK attached to its GitHub Release. Users sideload it (Android asks for
an "install unknown apps" grant once). There is no Play Store presence for now; nothing in this ADR precludes adding
it later (D3).

### D2 — One self-managed release key
A single Java keystore (JKS) signs all release builds. It is generated once, **never committed**, and stored as
GitHub Actions secrets (base64-encoded keystore + store/key passwords + alias). An encrypted cold backup is handed
to the owner (company password manager) before the first release-candidate tag; the owner confirms the backup
exists. Losing this key means every installed phone must reinstall — there is no recovery without Play App Signing
(D3), so the backup confirmation gates the first `android-v*` tag.

### D3 — Play Store forward compatibility: same key, no reinstalls
The release key stays a standard exportable JKS so that a future move to Google Play can enroll **our existing key**
into Play App Signing via Google's PEPK tool at first upload. The signing identity is then unchanged: sideloaded
installs update from Play without reinstall. (Only letting Google generate a *new* key would force reinstalls; this
ADR rules that out by keeping our key exportable.) A Play move would additionally require building an AAB
(`bundleRelease`) instead of an APK, a Play developer account, and store review — none of which is built now.

### D4 — App tags: `android-vX.Y.Z(-rcN)` on main
App releases use their own tag grammar `^android-v[0-9]+\.[0-9]+\.[0-9]+(-rc[1-9][0-9]*)?$`, pushed on main after
the owner merges, with the same rc rules as [ADR 0068](0068-release-pipeline.md) D3 (no rc after its final; verify
the tag is an ancestor of `origin/main`). The `android-` prefix keeps the server grammar and Docker lane untouched:
app and server version independently. Independence is safe because the app speaks the additive-only v1 API
([ADR 0076](0076-api-versioning-policy.md)); compatibility is an API concern, not a version-lockstep one.

### D5 — Versioning from the tag
`versionName` is the tag without the `android-v` prefix. `versionCode` is derived:
`major*100000 + minor*1000 + patch*10 + (rc ? rcN : 9)` — monotonic across rc→final within a version, with room for
9 rc and 9 patch digits per component at our 0.x cadence. Both are injected by the release lane
(`ANDROID_VERSION_NAME` / `ANDROID_VERSION_CODE`); local builds default to `0.1.0-dev` / `1`. The hardcoded values
in `apps/android/app/build.gradle.kts` are removed.

### D6 — Debug key stays debug-only
The committed `apps/android/debug.keystore` keeps signing debug builds (stable dev identity, installs over itself).
It never signs a release build. Release and debug signatures differ, so installing the first release APK over a
debug install is a fresh install that wipes the on-device store — acceptable exactly once, now, before any real
use.

### D7 — The Android release lane
A new workflow `.github/workflows/android-release.yml` triggers on `android-v*` tags (plus `workflow_dispatch`
repair runs, mirroring [ADR 0068](0068-release-pipeline.md) D3). It verifies grammar and ancestry, builds
`assembleRelease` signed from the GitHub secrets, verifies the APK signature with `apksigner`, and attaches the APK
to a GitHub Release for the tag (prerelease, non-latest for rc, `--generate-notes`). The full PR gate is not re-run
on the tag, same rationale as ADR 0068.

## Consequences
- Cutting an app release is one `git tag android-vX.Y.Z` + push on main; the APK appears on the Releases page.
- Key custody is ours: the GitHub secrets plus the owner's confirmed cold backup are the only copies. Rotating
  means a new signing identity and reinstalls — do not rotate.
- Users upgrading from any debug install reinstall once (D6); release-to-release upgrades are seamless.
- A later Play move is a new lane (AAB upload), not a new identity (D3); existing users keep their data.
- The Releases page now carries two kinds of artifacts (images for `v*` tags, APK for `android-v*` tags); release
  notes conventions from ADR 0068 D6 apply to both.

## Out of scope
- Play Store listing, AAB builds, fastlane (D3 lists what a Play move needs).
- APK signature scheme v1-only devices (minSdk 26 makes v2+ sufficient).
- Reproducible builds and cosign/SLSA attestation of the APK (phase 2, as in ADR 0068).
- F-Droid distribution (would build from source with its own signing model — revisit if requested).
