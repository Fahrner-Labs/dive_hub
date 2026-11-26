---
title: "ADR 0086: BUSL 1.1 with Apache-2.0 as the Change License"
summary: Dive Hub moves from Apache-2.0 to the Business Source License 1.1. Self-hosting for your own logbook, including dive clubs and centres running it for their members, is always allowed; offering DiveHub as a paid hosted service to third parties is not. Each version becomes Apache-2.0 four years after its first public distribution.
status: accepted
date: 2026-11-26
---

# ADR 0086: BUSL 1.1 with Apache-2.0 as the Change License

## Status
Accepted – 2026-11-26. Supersedes [ADR 0006](0006-license-apache-2-and-fit-parser.md)'s **license** decision only;
its FIT-parser decision (an MIT parser in the image, Garmin's SDK only in tests) stands. Decided as part of the
hosted-DiveHub direction ([FAH-523](/FAH/issues/FAH-523), decision 7), executed in [FAH-525](/FAH/issues/FAH-525).

## Context
Dive Hub is Apache-2.0 ([ADR 0006](0006-license-apache-2-and-fit-parser.md)), chosen for a self-hosted app whose
operators include dive centres and clubs. The accepted product direction ([FAH-523](/FAH/issues/FAH-523)) takes the
same image to a paid hosted service. A permissive license lets anyone take the code, run a competing hosted service,
and give nothing back, which undercuts the paid tier the product now depends on.

The license must keep the promise the self-hosted product made:

- Anyone may run Dive Hub for their own logbook, including a dive club or centre running it for its members, with
  no fee and no permission needed.
- Nobody may offer DiveHub as a paid hosted service to third parties while the Licensor's hosted service competes.
- The code must eventually become open source again, not stay source-available forever.

## Decision
- **The whole repository is licensed under [BUSL 1.1](https://mariadb.com/bsl11/)** (`LICENSE`). The parameters are:
  - **Licensor:** Tim Fahrner.
  - **Licensed Work:** DiveHub, the source code in this repository.
  - **Additional Use Grant:** self-hosting for your own logbook — including dive clubs and centres running it for
    their members — is always allowed; offering DiveHub as a paid hosted service to third parties is not.
  - **Change Date:** the fourth anniversary of the first publicly available distribution of each version of the
    Licensed Work (BUSL applies per version, so each release converts four years after that release).
  - **Change License:** Apache License, Version 2.0.
- **`NOTICE` keeps the holder (Tim Fahrner) and the third-party attributions.** There are no external human
  contributors, so no consent roundup was needed.
- **The package metadata and image label match:** `package.json` says `BUSL-1.1`, and the image's
  `org.opencontainers.image.licenses` label says `BUSL-1.1`.
- **CI scans production dependency licenses** (`scripts/license-scan.mjs`, run by `pnpm check` and `pnpm check:full`):
  a non-permissive production dependency fails the check, and Garmin's FIT SDK may never be one. The release
  smoke test keeps its existing image assertion.
- **Contributions use the DCO, not a CLA.** `CONTRIBUTING.md` and the `dco-check` pull-request workflow require a
  `Signed-off-by:` line from outside contributors.
- **The FIT-parser decision in [ADR 0006](0006-license-apache-2-and-fit-parser.md) is unchanged.** The image still
  contains only the MIT `fit-file-parser`; `@garmin/fitsdk` stays a test-only dev dependency and never ships.

## Considered options
- **Stay Apache-2.0.** Rejected: it permits a competitor to run a paid hosted copy of the same code, which is the
  business the hosted tier is meant to fund.
- **AGPL-3.0.** Protects against a closed hosted fork, but it does not stop a paid hosted service and it would
  change the self-hosting bargain; the owner chose BUSL instead.
- **SSPL or the Elastic License.** Rejected: their restrictions are broader than needed and they are not on the path
  to an open-source license the way BUSL's Change License is.
- **A separate proprietary hosted edition.** Rejected by the dual-track invariant: the hosted product is the same
  source-available artifact, configured by environment, never a fork.
- **A CLA.** Rejected: a DCO is enough for a single-holder project and asks less of contributors.

## Consequences
- Dive Hub is **source-available, not open source** until each version's Change Date. Docs and package metadata say
  so; "open source" must not be used for current releases.
- **Earlier releases stay Apache-2.0.** A license cannot be revoked for copies already distributed, so versions
  released before this change keep their Apache-2.0 rights. BUSL applies from the first version released under it.
- Every new version needs its own Change Date bookkeeping; BUSL's text already fixes it at four years after that
  version's first public distribution, so no per-release file edit is required.
- External contributions need a DCO sign-off; the workflow is skipped for the owner's and members' pull requests.
- Adding a production dependency now also means passing the license scan. A new non-permissive dependency is a
  deliberate decision, recorded by extending the allowlist in `scripts/license-scan.mjs`.
