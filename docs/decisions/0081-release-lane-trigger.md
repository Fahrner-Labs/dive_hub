---
title: "ADR 0081: Release lanes publish on dispatch, not on a tag push"
summary: The server and Android release workflows are triggered only by workflow_dispatch with an existing release tag as input. The automation identity that cuts tags does not cascade its pushes into workflow runs, so relying on the tag-push event published nothing twice. The tag stays the single source of truth and verify still proves it is on main.
status: proposed
date: 2026-11-20
---

# ADR 0081: Release lanes publish on dispatch

## Status
Proposed – 2026-11-20. Amends the trigger in [ADR 0068](0068-release-pipeline.md) D3 and
[ADR 0077](0077-android-app-distribution.md) D7. Drafted by the Release Engineer on the Paperclip issue
[FAH-329](https://paperclip.privatenode.de/FAH/issues/FAH-329). The owner accepts it by merging its pull request:
`docs/decisions/**` is CODEOWNERS-protected.

## Context
[ADR 0068](0068-release-pipeline.md) D3 and [ADR 0077](0077-android-app-distribution.md) D7 made a `v*` or
`android-v*` tag push on main the release trigger, with `workflow_dispatch` as the repair path. In practice the
automation identity that cuts tags pushed tags whose push events started no workflow run (the GitHub rule that
events from an automation token do not cascade into new runs). The same lanes already proved that the trigger
itself works: `android-v0.1.0-rc1`, whose annotated tag was created and pushed by the owner's user token, produced a
`push` run (run [44498161828](https://github.com/Fahrner-Labs/dive_hub/actions/runs/44498161828)). The difference is
the token, not the workflow.

The failure happened identically twice:

- `v0.3.0-rc2` and `android-v0.1.0-rc2` ([FAH-323](https://paperclip.privatenode.de/FAH/issues/FAH-323)) produced no
  runs and were repaired with `workflow_dispatch` using the same tags.
- `android-v0.1.0-rc3` ([FAH-326](https://paperclip.privatenode.de/FAH/issues/FAH-326)) behaved the same way and was
  dispatched by hand.

Three fixes were on the table: push tags with a PAT or GitHub App token that cascades, make the dispatch the
designed trigger, or add a `repository_dispatch` shim. Minting and storing another credential is an owner/ops
decision and is not available to the release automation; a shim would still need a manual call to start. Dispatch
needs neither and removes the "did the tag fire?" guesswork.

## Decision

### D1 — `workflow_dispatch` is the only trigger
[`.github/workflows/release.yml`](../../.github/workflows/release.yml) and
[`.github/workflows/android-release.yml`](../../.github/workflows/android-release.yml) take exactly one trigger:
`workflow_dispatch` with a required `version` input holding an existing release tag. The `push.tags` and
`release.published` triggers are removed. No PAT, GitHub App installation or other credential is added for
releases.

### D2 — The tag remains the single source of truth
The tag is still the only release input, and `verify` keeps every rule from ADR 0068 D3 / ADR 0077 D7: the grammar,
the tag exists, its commit is an ancestor of `origin/main`, and an rc after its final release fails. Only the event
that starts the run changes; what is published is still determined by the tag.

### D3 — Cutting a release includes the dispatch
The procedure is: cut the tag on main, push it, then dispatch the lane with that tag
(`gh workflow run release.yml -f version=vX.Y.Z` or the Actions UI). [docs/releases.md](../releases.md) is the
runbook. Re-dispatching the same tag is the repair path and stays safe: verify reruns and publish overwrites the
image tags and edits the GitHub Release.

## Consequences
- Every cut has one explicit dispatch step after the tag push. The automation does it as part of the release task,
  so a release no longer depends on a silently missing push event.
- A tag pushed by anyone, including a UI-created release, no longer publishes on its own; the lane must be
  dispatched. That is the same step for every actor, so there is one path to document and test.
- The concurrency group is keyed on the input tag, so re-dispatches still serialise per tag.
- Adopting a cascading credential later and restoring a push trigger is a new decision, not an assumed fallback.

## Out of scope
- Provisioning a PAT or GitHub App token, and the `repository_dispatch` shim.
- A scheduled reconciler that dispatches tags that have no release.
