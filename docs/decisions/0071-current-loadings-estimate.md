---
title: "ADR 0071: Current loadings from the Diver's logged dives"
summary: N₂ is replayed through the shared ZHL-16C compartments from the dives that ended within 72 h, off-gassed on air to now and shown as the worst compartment's percent of its tolerated pressure at the Diver's GF high; CNS decays with a fixed 90-minute half-life and OTU sums a hard 24 h window. After 48 h the N₂ estimate is null and stale. Provenance is part of the value, and only its visibility is configurable.
status: proposed
date: 2026-11-16
---

# ADR 0071: Current loadings from the Diver's logged dives

## Status
Proposed – 2026-11-16 – by Elena. Pre-discussed and decided with the owner on
[FAH-237](/FAH/issues/FAH-237); this slice is [FAH-238](/FAH/issues/FAH-238). The owner reviews and merges the pull
request, as `docs/decisions/**` needs their approval ([CODEOWNERS](../../.github/CODEOWNERS)).

## Context
A Diver wants to know what their previous dives leave in them right now: residual nitrogen, CNS oxygen exposure and
OTU, beside the no-fly time Dive Hub already shows. The question came from [FAH-237](/FAH/issues/FAH-237), which also
locked the placement and the rules below.

Three forces shape the answer:

- **The logbook is incomplete.** A dive on a rental computer that never reached Dive Hub contributes nothing. A
  number that hides that would be misleading; a number that names the dives it used is an honest estimate. This is
  the same stance as [ADR 0032](0032-mod-and-no-decompression-limits.md) and [ADR 0036](0036-dive-assessment.md):
  computed from the Diver's own data, never advice.
- **The computer's scalars are not portable.** Imports carry `n2Start`/`n2End`, `cnsStart`/`cnsEnd` and
  `otuStart`/`otuEnd`. They are produced by the computer's own model over its own view of the surface interval, which
  may include dives Dive Hub does not have, and their N₂ percentages are not interchangeable between vendors.
- **The decay rules are conventions, not agency guidance.** CNS decay and OTU expiry have no official cross-dive
  model; exposing them as preferences would dress a research gap up as a setting and let a safety number be tuned
  downward without evidence.

## Decision

### Where it lives
`apps/server/src/planning/`: pure functions in `loadings.ts`, stepping the compartments in `tissues.ts`, which is the
ZHL-16C core extracted from `ndl.ts` without changing `ndlMinutes`' behaviour. It is computed on request and nothing
is stored — no schema change, no migration, no new module ([ADR 0053](0053-module-boundaries.md)).

### N₂ — replay the profiles, not the stored scalars
- Replay every non-deleted Dive that **ended within 72 h** (more than six half-times of the slowest 635-minute
  compartment) through the ZHL-16C compartments, using the Recording's depth samples and the Dive's first gas when
  there is no switch information. Surface intervals and the time up to now are spent on air at the surface.
- **Salt water at sea level** is the default for this slice, as in the planner. The Recording's water and the site's
  altitude are not read yet; a later slice can carry per-Dive conditions without changing these rules.
- The displayed value is the **worst compartment as a percent of its tolerated tissue pressure** at the surface and
  the Diver's GF high: 100 % is the gradient-factor limit at which the NDL model would call for a stop. The GF high
  comes from the Diver's latest Recording that has one, else 85, as in the planner.
- **After 48 h the result is `null` with reason `stale`**: the tissues are back at surface saturation within noise.
  This matches the planner's existing 48 h recent-dive window in `logbook-inputs.ts`.
- A Dive without depth samples is skipped and counted in the provenance. If no usable Dive is in the window the
  result is `null` with the matching reason; there is no square-profile fallback in this slice.

### CNS — the stored end value, decayed
Use the latest Recording's `summary.cnsEnd` and decay it to now with a **90-minute elimination half-life**. The
constant is fixed in code and sourced, not configurable: Shearwater documents that CNS never decreases during a dive
and that most computers use a 90-minute half-life on the surface ("The CNS oxygen clock"; the source is already cited
as `SOURCES.noaa_oxygen` in `apps/server/src/assessment/rules.ts`).

### OTU — a hard 24 h window
Sum `otuEnd − otuStart` over the dives that **ended within 24 h**. OTU expire after 24 hours rather than following a
decay curve (Garmin's documented behaviour; NOAA's REPEX is the accumulation model behind `oxygen.ts`).

### Provenance and configurability
- Provenance is part of the value, always returned and shown: dives used, dives skipped for having no profile, and
  when the last dive ended. "Estimate from your logged dives", never "safe" or "unsafe".
- **Only visibility is configurable** — whether the section is shown at all, following the assessment's per-rule
  muting ([ADR 0036](0036-dive-assessment.md)). GF high is inherited from the Diver's settings as a planning input;
  the decay constants are not settings.

## Consequences
- The number follows the Diver's real profiles and our own model, so it stays explainable and testable; the same
  compartment core now serves the NDL and, later, the planner's residual seeding. Existing NDL tests pin the
  extraction.
- An unlogged dive still leaves the estimate wrong, not merely imprecise. The provenance line is the mitigation, and
  the 48 h cutoff stops an old logbook from pretending to know.
- No storage means no invalidation and no migration, at the cost of replaying up to 72 h of samples per request for
  one Diver. That is bounded and acceptable for a Diver page and a read-only MCP tool.
- Changing a decay constant or the staleness window is an ADR-level, reviewed code change, not a silent per-user
  divergence.

Sources: [Shearwater, "Shearwater and the CNS oxygen clock"](https://shearwater.com/blogs/community/shearwater-and-the-cns-oxygen-clock);
[Garmin Descent manual, Surface Interval](https://www8.garmin.com/manuals/webhelp/GUID-D6BA1847-E20D-4241-B08C-DF0D8B052B28/EN-US/GUID-8179D8C5-35C9-450E-9E61-49180CD82701.html);
the Bühlmann and Baker references in `apps/server/src/planning/tissues.ts` and
[ADR 0032](0032-mod-and-no-decompression-limits.md).
