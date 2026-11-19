// The fixed cases of the lead-estimate golden vectors (FAH-272, extending FAH-268's harness): the generator writes
// their expected values, and the vitest runner recomputes them from the same cases. The Kotlin port reads the
// committed JSON, never this file. The estimate rounds its own answer (ADR 0031), so the kernel and service views
// are the same object; the route only adds the Dives it may link and the no-water-type count, which this kernel
// does not see.
import { estimateLead } from './estimate.js';
import type { EstimateCylinder, EstimateInput, LeadUnit, PlannedConditions } from './types.js';
import type { Suit, WeightingFeedback } from '../dives/weighting.js';
import type { SiteWaterType } from '../vocabulary.js';
import type { VectorCase, VectorFile, VectorOutcome } from '../planning/test-vectors.js';

export const LEAD_VECTOR_KERNEL = 'lead-estimate';

type EstimateDiveCylinder = EstimateCylinder & { endPressureBar: number | null };

interface LeadVectorDive {
  id: string;
  /** ISO 8601, as the client stores it; the evaluator turns it into a Date. */
  date: string;
  leadKg: number | null;
  feedback: WeightingFeedback | null;
  suit: Suit | null;
  water: SiteWaterType | null;
  cylinders: EstimateDiveCylinder[];
}

interface LeadVectorInput {
  dives: LeadVectorDive[];
  bodyWeightKg: number | null;
  plan: PlannedConditions;
  unit: LeadUnit;
  /** ISO 8601; the day the plan is made on, for the age of the dives. */
  today: string;
}

const TODAY = '2026-10-13T12:00:00Z';
const WETSUIT_5: Suit = { type: 'wetsuit', thicknessMm: 5, hood: false, undergarment: null };
const WETSUIT_7_HOOD: Suit = { type: 'wetsuit', thicknessMm: 7, hood: true, undergarment: null };
const AL80: EstimateCylinder = { volumeL: 11.1, material: 'aluminium' };
const STEEL_12: EstimateCylinder = { volumeL: 12, material: 'steel' };

const yearsAgo = (years: number) => new Date(Date.parse(TODAY) - years * 365.25 * 86_400_000).toISOString();

/** A Dive in the tests' baseline conditions: fresh water, 7 mm with hood, 8 kg carried, a steel 12 L with no end pressure. */
const dive = (id: string, over: Partial<LeadVectorDive> = {}): LeadVectorDive => ({
  id, date: TODAY, leadKg: 8, feedback: null, suit: WETSUIT_7_HOOD, water: 'fresh',
  cylinders: [{ ...STEEL_12, endPressureBar: null }], ...over,
});

const planOf = (over: Partial<PlannedConditions> = {}): PlannedConditions => ({
  water: 'salt', suit: WETSUIT_5, cylinders: [AL80], ...over,
});

const leadInput = (over: Partial<LeadVectorInput> = {}): LeadVectorInput => ({
  dives: [], bodyWeightKg: 82, unit: 'kg', today: TODAY, plan: planOf(), ...over,
});

export const LEAD_CASES: VectorCase[] = [
  {
    id: 'one-dive-same-suit-too-heavy-2',
    input: leadInput({
      dives: [dive('red-sea-1', {
        leadKg: 10, feedback: { felt: 'too_heavy', amountKg: 2 }, suit: WETSUIT_5, water: 'salt',
        cylinders: [{ ...AL80, endPressureBar: null }],
      })],
      plan: planOf({ suit: WETSUIT_5 }),
    }) satisfies LeadVectorInput,
  },
  {
    id: 'one-dive-same-suit-felt-right',
    input: leadInput({
      dives: [dive('felt-right', { feedback: { felt: 'right', amountKg: null } })],
      plan: planOf({ water: 'fresh', suit: WETSUIT_7_HOOD, cylinders: [STEEL_12] }),
    }) satisfies LeadVectorInput,
  },
  {
    id: 'no-dives-rule-5mm-salt-al80',
    input: leadInput() satisfies LeadVectorInput,
  },
  {
    id: 'no-dives-no-body-weight',
    input: leadInput({ bodyWeightKg: null }) satisfies LeadVectorInput,
  },
  {
    id: 'six-7mm-dives-offset-rule-5mm',
    input: leadInput({
      dives: [
        ...[1, 2, 3].map((n) => dive(`tim-right-${n}`, { feedback: { felt: 'right', amountKg: null } })),
        dive('tim-heavy-1', { feedback: { felt: 'too_heavy', amountKg: 1 } }),
        dive('tim-no-feedback-1'), dive('tim-no-feedback-2'),
      ],
    }) satisfies LeadVectorInput,
  },
  {
    id: 'rule-skin-80kg',
    input: leadInput({ bodyWeightKg: 80, plan: planOf({ suit: { type: 'skin', thicknessMm: null, hood: null, undergarment: null } }) }) satisfies LeadVectorInput,
  },
  {
    id: 'rule-3mm-80kg',
    input: leadInput({ bodyWeightKg: 80, plan: planOf({ suit: { type: 'wetsuit', thicknessMm: 3, hood: null, undergarment: null } }) }) satisfies LeadVectorInput,
  },
  {
    id: 'rule-7mm-hood-semidry-80kg',
    input: leadInput({ bodyWeightKg: 80, plan: planOf({ suit: { type: 'semidry', thicknessMm: 7, hood: true, undergarment: null } }) }) satisfies LeadVectorInput,
  },
  {
    id: 'rule-7mm-no-hood-80kg',
    input: leadInput({ bodyWeightKg: 80, plan: planOf({ suit: { type: 'wetsuit', thicknessMm: 7, hood: false, undergarment: null } }) }) satisfies LeadVectorInput,
  },
  {
    id: 'rule-drysuit-heavy-80kg',
    input: leadInput({ bodyWeightKg: 80, plan: planOf({ suit: { type: 'drysuit', thicknessMm: null, hood: null, undergarment: 'heavy' } }) }) satisfies LeadVectorInput,
  },
  {
    id: 'rule-drysuit-undergarment-missing-80kg',
    input: leadInput({ bodyWeightKg: 80, plan: planOf({ suit: { type: 'drysuit', thicknessMm: null, hood: null, undergarment: null } }) }) satisfies LeadVectorInput,
  },
  {
    id: 'rule-wetsuit-thickness-missing-82kg',
    input: leadInput({ plan: planOf({ suit: { type: 'wetsuit', thicknessMm: null, hood: null, undergarment: null } }) }) satisfies LeadVectorInput,
  },
  {
    id: 'plan-brackish-water',
    input: leadInput({
      dives: [dive('fresh-7mm')],
      plan: planOf({ water: 'brackish', suit: WETSUIT_7_HOOD, cylinders: [STEEL_12] }),
    }) satisfies LeadVectorInput,
  },
  {
    id: 'reserve-median-100-bar',
    input: leadInput({
      dives: [90, 100, 110].map((bar) => dive(`end-${bar}`, { cylinders: [{ ...STEEL_12, endPressureBar: bar }] })),
      plan: planOf({ water: 'fresh', suit: WETSUIT_7_HOOD, cylinders: [AL80] }),
    }) satisfies LeadVectorInput,
  },
  {
    id: 'unknown-cylinder-material-planned',
    input: leadInput({
      dives: [dive('same-conditions')],
      plan: planOf({ water: 'fresh', suit: WETSUIT_7_HOOD, cylinders: [{ volumeL: 12, material: null }] }),
    }) satisfies LeadVectorInput,
  },
  {
    id: 'dive-cylinder-material-unknown-and-none',
    input: leadInput({
      dives: [
        dive('rented', { cylinders: [{ volumeL: 12, material: null, endPressureBar: null }] }),
        dive('none', { cylinders: [] }),
      ],
      plan: planOf({ water: 'fresh', suit: WETSUIT_7_HOOD, cylinders: [STEEL_12] }),
    }) satisfies LeadVectorInput,
  },
  {
    id: 'carbon-planned-cylinder',
    input: leadInput({ plan: planOf({ cylinders: [{ volumeL: 12, material: 'carbon' }] }) }) satisfies LeadVectorInput,
  },
  {
    id: 'twin-24l-steel',
    input: leadInput({
      dives: [dive('twin', { cylinders: [{ volumeL: 24, material: 'steel', endPressureBar: null }] })],
      plan: planOf({ water: 'fresh', suit: WETSUIT_7_HOOD, cylinders: [{ volumeL: 24, material: 'steel' }] }),
    }) satisfies LeadVectorInput,
  },
  {
    id: 'age-halves-weight',
    input: leadInput({
      dives: [dive('four-years-ago', { leadKg: 10, date: yearsAgo(4) }), dive('today', { leadKg: 6 })],
      plan: planOf({ water: 'fresh', suit: WETSUIT_7_HOOD, cylinders: [STEEL_12] }),
    }) satisfies LeadVectorInput,
  },
  {
    id: 'felt-right-weighs-twice',
    input: leadInput({
      dives: [
        dive('six', { leadKg: 6 }),
        dive('ten', { leadKg: 10, feedback: { felt: 'right', amountKg: null } }),
      ],
      plan: planOf({ water: 'fresh', suit: WETSUIT_7_HOOD, cylinders: [STEEL_12] }),
    }) satisfies LeadVectorInput,
  },
  {
    id: 'five-dives-spread-high-confidence',
    input: leadInput({
      dives: [4, 6, 8, 10, 12].map((leadKg) => dive(`dive-${leadKg}`, { leadKg })),
      plan: planOf({ water: 'fresh', suit: WETSUIT_7_HOOD, cylinders: [STEEL_12] }),
    }) satisfies LeadVectorInput,
  },
  {
    id: 'lb-unit-rule',
    input: leadInput({ unit: 'lb' }) satisfies LeadVectorInput,
  },
  {
    id: 'metric-rounds-half-kg',
    input: leadInput({
      dives: [dive('ten-point-seven', { leadKg: 10.7 })],
      plan: planOf({ water: 'fresh', suit: WETSUIT_7_HOOD, cylinders: [STEEL_12] }),
    }) satisfies LeadVectorInput,
  },
  {
    id: 'zero-lead-and-unknown-lead',
    input: leadInput({
      dives: [dive('dry', { leadKg: 0 }), dive('unknown', { leadKg: null })],
      plan: planOf({ water: 'fresh', suit: WETSUIT_7_HOOD, cylinders: [STEEL_12] }),
    }) satisfies LeadVectorInput,
  },
  {
    id: 'dive-without-suit-or-water-ignored',
    input: leadInput({
      dives: [
        dive('no-site', { water: null }),
        dive('no-suit', { suit: null }),
        dive('no-hood', { suit: { ...WETSUIT_7_HOOD, hood: false } }),
      ],
      plan: planOf({ water: 'fresh', suit: WETSUIT_7_HOOD, cylinders: [STEEL_12] }),
    }) satisfies LeadVectorInput,
  },
  {
    id: 'feedback-too-light-default-1kg',
    input: leadInput({
      dives: [dive('too-light', { feedback: { felt: 'too_light', amountKg: null } })],
      plan: planOf({ water: 'fresh', suit: WETSUIT_7_HOOD, cylinders: [STEEL_12] }),
    }) satisfies LeadVectorInput,
  },
  {
    id: 'body-weight-missing-with-dives',
    input: leadInput({
      dives: [dive('same-suit')],
      bodyWeightKg: null,
      plan: planOf({ water: 'fresh', suit: WETSUIT_7_HOOD, cylinders: [STEEL_12] }),
    }) satisfies LeadVectorInput,
  },
];

const asEstimateInput = (input: LeadVectorInput): EstimateInput => ({
  dives: input.dives.map((vector) => ({ ...vector, date: new Date(vector.date) })),
  bodyWeightKg: input.bodyWeightKg,
  plan: input.plan,
  unit: input.unit,
  today: new Date(input.today),
});

export function evaluateLeadVectorCase(input: unknown): VectorOutcome {
  const result = estimateLead(asEstimateInput(input as LeadVectorInput));
  const view = {
    unit: result.unit,
    total: result.total,
    range: result.range,
    confidence: result.confidence,
    basis: result.basis,
    adjustments: result.adjustments,
    missing: result.missing,
    reserve: result.reserve,
  };
  // The engine rounds its own answer (ADR 0031), so the route returns the kernel view unchanged.
  return { kernel: view, service: view };
}

export function leadVectorFile(): VectorFile {
  return {
    kernel: LEAD_VECTOR_KERNEL,
    version: 1,
    tolerance: { relative: 1e-9 },
    cases: LEAD_CASES.map((vector) => ({
      id: vector.id,
      input: vector.input,
      expected: evaluateLeadVectorCase(vector.input),
    })),
  };
}
