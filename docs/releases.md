---
title: Release runbook
summary: How to cut and publish a server or Android release — tag on main, then dispatch the lane with that tag (ADR 0081).
status: living
date: 2026-11-20
---

# Release runbook

Releases are **dispatch-triggered** ([ADR 0081](decisions/0081-release-lane-trigger.md)): a tag push alone starts
no workflow run. The tag is still the only release input, and the lane's `verify` job proves it matches the
grammar, exists, and points at a commit on `main` before anything is built or published.

Both lanes are one job graph — `verify` → build (and smoke test for the server) → publish — and every publish step
is idempotent. Re-dispatching the same tag is the repair path.

## Before tagging

- The owner merged the pull request; releases are cut only on `main` ([ADR 0068](decisions/0068-release-pipeline.md)).
- `check` on the merge commit is green.
- The release notes are ready: `--generate-notes` is the changelog, plus the hand-written **Upgrade** section when
  an upgrade needs a backup, a minimum server version or a pairing note ([ADR 0068](decisions/0068-release-pipeline.md)
  D6). For an Android release, state the minimum server version the app requires.

## Server release (container image)

Tags: `vX.Y.Z`, or `vX.Y.Z-rcN` for a release candidate.

```sh
git fetch origin main
git tag v0.3.0-rc4 origin/main        # or v0.3.0 for the final release
git push origin v0.3.0-rc4
gh workflow run release.yml -f version=v0.3.0-rc4
```

In the UI: **Actions → release → Run workflow → version = the tag**.

A stable tag publishes `ghcr.io/fahrner-labs/dive_hub:vX.Y.Z`, `X.Y`, `X` and `latest` plus a latest GitHub
Release. An rc publishes only its exact tag and a non-latest prerelease. The `build-smoke` job loads the amd64
image against PostgreSQL, checks `/api/health/ready`, checks that `/api/health/live` reports the tag and that
`@garmin/fitsdk` is absent before `publish` runs.

## Android release

Tags: `android-vX.Y.Z`, or `android-vX.Y.Z-rcN` for a release candidate.

```sh
git fetch origin main
git tag android-v0.1.0-rc4 origin/main
git push origin android-v0.1.0-rc4
gh workflow run android-release.yml -f version=android-v0.1.0-rc4
```

In the UI: **Actions → android release → Run workflow → version = the tag**.

The lane builds a release-signed APK from the signing secrets ([ADR 0077](decisions/0077-android-app-distribution.md)),
verifies its v2+ signature with `apksigner`, and attaches `DiveHub-<versionName>.apk` to the GitHub Release. An rc
is a non-latest prerelease; a stable tag is latest. The version name and code come from the tag
(`scripts/android-version-code.mjs`).

## Watching and repairing

```sh
gh run list --workflow release.yml --limit 5
gh run list --workflow android-release.yml --limit 5
gh run watch <run-id>
```

- A failed run publishes nothing before `publish`; fix the cause, then dispatch the same tag again.
- A crash during `publish` is repaired the same way: the image tags are overwritten and the GitHub Release is
  edited, not recreated.
- `verify` rejects a tag that is not on `main`, an invalid tag, or an rc whose final release already exists. Those
  failures are expected guard rails, not something to bypass.
- Do not delete and re-push a release tag to "retry": the tag is the release identity. Dispatch it again.

## Publishing is not deploying

Merging, deploying to an instance and announcing a release are the owner's steps. This runbook ends at a green
publish run and a GitHub Release for the tag.
