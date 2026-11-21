---
title: "ADR 0068: Release pipeline — tag as truth, ghcr images, GitHub Releases"
summary: A vX.Y.Z or vX.Y.Z-rcN tag on main is the only release input; a GitHub Actions workflow proves the tag is on main, builds and smoke-tests a multi-arch image, pushes it to ghcr (four tags for a stable release, only the exact tag for an rc) and writes the GitHub Release notes. Signing, SBOM/SLSA and mirrors are phase 2.
status: proposed
date: 2026-11-09
---

# ADR 0068: Release pipeline

## Status
Proposed – 2026-11-09. Drafted by the Release Engineer from the locked plan of the Paperclip issue
[FAH-168](https://paperclip.privatenode.de/FAH/issues/FAH-168) (plan revision
`0cac381e-45d3-4006-bbaa-dceb72801795`). The owner accepts it by merging its pull request: `docs/decisions/**` is
CODEOWNERS-protected.

## Context
Dive Hub is self-hosted ([ADR 0004](0004-system-architecture.md)): operators need an image they can pull and a page
that tells them what changed before they upgrade. Until now there was no release process — no published image, no
version in the running app, and nothing that could become release notes. Slices A–C of FAH-168 build the version
plumbing, this workflow and the self-hosting guide.

The comparable self-hosted apps in [the architecture research](../research/2026-10-02-self-hosted-architecture.md)
converge on versioned container images plus release notes, with operators pinning exact versions in production.
Two existing decisions constrain the shape:

- [ADR 0004](0004-system-architecture.md): one image contains API, web client and in-process worker, and migrations
  run on container start.
- [ADR 0006](0006-license-apache-2-and-fit-parser.md): Garmin's FIT SDK may never be redistributed, so
  `@garmin/fitsdk` is a development dependency and must stay out of the published image.
- [ADR 0023](0023-faster-checks.md): `pnpm check:full` on the pull request is the gate; the release should not run a
  second, differently configured copy of it.

## Decision

### D1 — The git tag is the single source of truth
Releases are SemVer tags `vX.Y.Z`; a release candidate is the same tag with an `-rcN` suffix (`vX.Y.Z-rcN`, N ≥ 1,
no leading zeros), used to test an upgrade before the final release. The accepted grammar is
`^v[0-9]+\.[0-9]+\.[0-9]+(-rc[1-9][0-9]*)?$`; every other form fails `verify`. No `version` field is added to any
`package.json`: nothing consumes it, and bump commits would add churn without authority. The first release is
`v0.1.0`; the project stays on `0.x` while the API and data model still move.

### D2 — ghcr.io only
Images live at `ghcr.io/fahrner-labs/dive_hub`. The repository is public, so the registry is free, needs no extra
account, and the workflow's built-in `GITHUB_TOKEN` covers it. Each stable release publishes four tags:

| Tag | Use |
|---|---|
| `vX.Y.Z` | exact version; what production operators pin |
| `X.Y` | latest patch of a minor line |
| `X` | latest minor and patch of a major line |
| `latest` | newest stable release; convenient, not for production |

A release candidate publishes **only** its exact `vX.Y.Z-rcN` tag. The floating `X.Y`, `X` and `latest` tags, and
the GitHub Release's `latest` flag, are stable-only; an rc is a non-latest prerelease.

### D3 — A tag on main triggers the release
> **Amended by [ADR 0081](0081-release-lane-trigger.md) (2026-11-20):** `workflow_dispatch` with the release tag as
> its `version` input is now the only trigger. A tag pushed by the automation identity starts no workflow run;
> verify and the published artefacts are unchanged, and the procedure is in [releases.md](../releases.md).

Pushing a `v*` tag runs [`.github/workflows/release.yml`](../../.github/workflows/release.yml). The `verify` job
requires the tag to match `^v[0-9]+\.[0-9]+\.[0-9]+(-rc[1-9][0-9]*)?$` and its commit to be an ancestor of
`origin/main`; otherwise it fails before anything is built. Stable tags and release candidates alike are cut only on
main after the owner merges (the PR-per-task convention). An `-rcN` tag whose final `vX.Y.Z` tag already exists
fails verify ("rc after final release") and publishes nothing. Because that ancestor passed CI on main, the full PR
gate is deliberately **not** re-run on the tag. `workflow_dispatch` with a `version` input re-runs or repairs an
existing tag (including an rc, unless its final release already exists) and checks out the tag, not the branch.

The build job builds both `linux/amd64` and `linux/arm64` with GHA layer cache, then loads the amd64 image and
smoke-tests it against `postgres:18.0-alpine`:

- `GET /api/health/ready` → 200, proving the migrations ran on container start (ADR 0004);
- `GET /api/health/live` → `version` equals the tag;
- `/app/node_modules` contains no `@garmin/fitsdk`, so a dependency change cannot silently ship the licence
  problem ADR 0006 forbids.

Only after the smoke test passes does `publish` log in to ghcr, push the tags D2 lists (a re-run overwrites them)
and create the GitHub Release.

### D4 — The version is built into the image
The workflow passes `DIVEHUB_VERSION` (the tag) and `DIVEHUB_REVISION` (the tagged commit) as build args. The
Dockerfile turns them into `ENV` and OCI labels
(`org.opencontainers.image.{version,source,revision,licenses}`), and the server returns `DIVEHUB_VERSION` from
`/api/health/live`. The default remains `dev` for plain `docker build .` and `compose.dev.yaml`.

### D5 — The compose example uses the published image
`compose.yaml` pulls `ghcr.io/fahrner-labs/dive_hub:latest`; `build: .` stays as a comment for hacking. The
self-hosting guide tells operators to pin an exact `vX.Y.Z` in production, following the Mealie/Immich guidance in
the research. Compose tag bumping stays manual.

### D6 — The Releases page is the changelog
GitHub Releases are created with `--generate-notes`, which reads the merged pull requests (our PR-per-task
convention makes them readable), plus a hand-written "Upgrade" section per release when needed. There is no
CHANGELOG file: one channel, the releases page. An existing release is edited rather than recreated, so re-runs are
safe.

### D7 — This ADR
The decisions above are recorded here rather than in the release workflow's comments, because the trigger,
registry, tag scheme and upgrade contract are hard to reverse and constrain future tooling.

## Consequences
- Cutting a release is one `git tag` + `git push` on main, then a dispatch of the lane with that tag
  ([ADR 0081](0081-release-lane-trigger.md)); nothing is published from a branch or a local build.
- A bad version build fails the smoke job and publishes nothing; a crash during `publish` is repaired by
  `workflow_dispatch` with the same tag.
- The first release run is also the live validation of the workflow (about 15–25 minutes because arm64 builds under
  QEMU).
- Operators get two upgrade paths: `latest` for convenience, `vX.Y.Z` for production. Migrations are forward-only,
  so the self-hosting guide requires a backup before upgrading and forbids downgrading across a migration
  (ADR 0004).
- The FIT SDK assertion turns ADR 0006's licence rule into a build failure instead of a convention.
- A failed `gh release` step after a successful push leaves the image tags in place but no release; the guarded
  re-run fixes it.

## Out of scope (phase 2)
Recorded here so the omissions are deliberate, not forgotten:

- **Supply-chain hardening:** cosign keyless signing, SLSA provenance, SBOM attestations.
- **A Docker Hub mirror** for discoverability; ghcr is enough for v1.
- **Automated compose-tag bumping** after a release.
