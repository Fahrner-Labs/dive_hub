# Planning golden vectors

Plain JSON, committed, one file per kernel. The TypeScript kernels generate them and the Kotlin port reads the same
files, so a change on one side fails until both agree (FAH-268). The lead estimate joins the harness from its own
module (FAH-272): it rounds its own answer (ADR 0031), so `lead-estimate.json`'s `expected.kernel` and
`expected.service` are the same object.

## Format

```json
{
  "kernel": "ndl",
  "version": 1,
  "tolerance": { "relative": 1e-9 },
  "cases": [
    {
      "id": "air-30m-salt-gf85",
      "input": { "...": "the fixed case's input" },
      "expected": {
        "kernel": { "...": "full-precision doubles" },
        "service": { "...": "the route's rounded view" }
      }
    }
  ]
}
```

- `expected.kernel` holds the kernel's doubles as computed, never rounded. Where a kernel rounds its own answer
  (the lead estimate, ADR 0031) it holds that answer.
- `expected.service` holds the view the server's service shell returns (MOD floored to 0.1 m, ppO2 to 2 places,
  depths and pressures to 1 place, SAC to 2 places, and so on). It is reference data for the clients' presentation
  parity; the TypeScript runner checks it for every kernel, and the Kotlin runner too where the port ships that
  view (SAC, lead estimate).
- Integer-valued expected numbers (minute counts, best-mix percent, the NDL) assert exact equality. Other numbers
  assert `|actual − expected| <= relative × max(|actual|, |expected|, 1)`, with `relative` 1e-9 unless a case or file
  documents a looser value.
- A `null` where a number is expected means infinity: JSON cannot carry `Infinity`, and the NDL and oxygen limit use
  it for "no limit".
- Two other `null`s mean "absent", not infinity: `turnPressureBar` on a rule that does not turn, and a gas-plan or
  bottom-time field that needs a Cylinder the case leaves out (`limits.gas`, `ascentPressureBar`).

The kernels compute the gas they are given; the service and UI restrictions (air and nitrox 21–40 %, no helium,
at most 40 m, the MOD) are not part of the kernel and must be reproduced at the service layer. The trimix and
altitude cases pin the kernel math, not a promise that a route accepts that input.

The `loadings` cases include a profile reduced by the same `downsampleMinMax` rule `GET /api/recordings/{id}/samples`
uses (each bucket's minimum and maximum, peaks kept), generated with `maxPoints` 40 so the fixture stays readable.
The shape is the endpoint's; the server and the phone replay the identical input. The `no-fly` cases pin
`seriesDiveOf`, the local-day grouping, the covered-Dive filter and the `minNdl === 0` derivation of `enteredDeco`.

## Regenerating

From `apps/server`:

```sh
pnpm exec tsx src/scripts/write-planning-vectors.ts
```

Review every changed number in the diff. CI never runs the generator; the server vitest runner and the Android JVM
runner only read the committed files.
