// The fixed cases of the planning golden vectors (FAH-268): the generator writes their expected values, and the
// vitest runner recomputes them from the same cases. The Kotlin port reads the committed JSON, never this file.
import { assessmentApplies, SOURCES } from '../assessment/rules.js';
import { noFlyAfter, seriesDiveOf } from '../assessment/series-rules.js';
import { downsampleMinMax } from '../dives/calc/downsample.js';
import { compressibility, pressureForLitres, surfaceLitres } from '../dives/calc/real-gas.js';
import { sacOfDive, sacValues, type SacMissing, type SacValues } from '../dives/calc/sac.js';
import {
  ambientPressureBar, bestMixPercent, eadMetres, endMetres, isHypoxic, metresPerBar, modMetres, surfacePressureBar,
  type Conditions, type GasMix, type PlanningWater,
} from './gas.js';
import { bottomTime } from './bottom-time.js';
import { gasNumbers, floorTenth } from './gas-numbers.js';
import { planGas, type GasRule } from './gas-plan.js';
import {
  cnsDecayed, CNS_HALF_LIFE_MINUTES, otuLast24h, tissuesAfterDives, type LoadingDive,
} from './loadings.js';
import { ndlMinutes } from './ndl.js';
import { otu, oxygenLimitMinutes } from './oxygen.js';
import { planningSac } from './planning-sac.js';
import { advanceTissues, surfaceSaturatedTissues, worstCompartmentPercent, type TissuePoint } from './tissues.js';

export const VECTOR_KERNELS = [
  'gas', 'tissues', 'ndl', 'oxygen', 'real-gas', 'sac', 'gas-plan', 'bottom-time', 'planning-sac',
  'loadings', 'no-fly',
] as const;
export type VectorKernel = (typeof VECTOR_KERNELS)[number];

export interface VectorCase {
  id: string;
  input: unknown;
}

export interface VectorOutcome {
  kernel: Record<string, unknown>;
  service: Record<string, unknown>;
}

const round = (n: number, places: number) => Math.round(n * 10 ** places) / 10 ** places;
/** JSON has no Infinity; a null expected number means the kernel answered infinity. */
const finiteOrNull = (n: number): number | null => (Number.isFinite(n) ? n : null);

interface GasVectorInput {
  o2: number;
  he: number;
  depthM: number;
  water: PlanningWater;
  altitudeM: number;
  ppo2Working: number;
  ppo2Contingency: number;
  oxygenNarcotic: boolean;
}

interface TissuesVectorInput {
  gas: GasMix;
  conditions: Conditions;
  gfHigh: number;
  initialTissues: number[] | null;
  points: TissuePoint[];
}

interface NdlVectorInput {
  gas: GasMix;
  depthM: number;
  gfHigh: number;
  conditions: Conditions;
  initialTissues: number[] | null;
}

interface OxygenVectorInput {
  ppO2: number;
  minutes: number;
}

interface RealGasVectorInput {
  gas: GasMix;
  volumeL: number;
  bar: number;
  litres: number;
}

interface SacVectorCylinder {
  volumeL: number | null;
  startPressureBar: number | null;
  endPressureBar: number | null;
  gas: GasMix | null;
}

interface SacVectorInput {
  dive: { durationSeconds: number; avgDepthM: number | null };
  cylinders: SacVectorCylinder[];
}

interface GasPlanVectorInput {
  gas: GasMix;
  conditions: Conditions;
  levels: { depthM: number; minutes: number }[];
  divers: { name: string; volumeL: number; startPressureBar: number; sacLpm: number }[];
  rule: GasRule;
  ppO2Contingency: number;
  descentRateMPerMin?: number;
  ascentRateMPerMin?: number;
}

interface BottomTimeVectorInput {
  gas: GasMix;
  depthM: number;
  gfHigh: number;
  conditions: Conditions;
  cylinder: { volumeL: number; startPressureBar: number } | null;
  sacLpm: number;
  rule: GasRule;
  ppO2: { working: number; contingency: number };
  initialTissues?: number[];
}

interface PlanningSacVectorInput {
  dives: { sacLpm: number | null }[];
}

interface LoadingsVectorDive {
  startMs: number;
  endMs: number;
  samples: TissuePoint[];
  gas: GasMix | null;
  otuStart: number | null;
  otuEnd: number | null;
}

interface LoadingsVectorInput {
  dives: LoadingsVectorDive[];
  nowMs: number;
  gfHigh: number;
  /** The latest Recording's CNS end and its Dive's end; null when no Recording carries CNS. */
  cns: { cnsEnd: number; endedAtMs: number } | null;
}

interface NoFlyVectorDive {
  id: string;
  startMs: number;
  utcOffsetSeconds: number | null;
  durationSeconds: number;
  maxDepthM: number | null;
  /** The Recording's dive mode; apnea, CCR and SCR are not covered by the series rules (ADR 0036). */
  diveMode: string | null;
  /** The computer's no-decompression limit series, seconds; null when it carried none. */
  ndlSeconds: number[] | null;
}

interface NoFlyVectorInput {
  dives: NoFlyVectorDive[];
  id: string;
}

const HOUR_MS = 3_600_000;

const conditionsOf = (input: { water: PlanningWater; altitudeM: number }): Conditions => ({
  water: input.water,
  altitudeM: input.altitudeM,
});

const AIR: GasMix = { o2: 21, he: 0 };
const EAN32: GasMix = { o2: 32, he: 0 };
const TRIMIX_21_35: GasMix = { o2: 21, he: 35 };

/** A profile as the planner sees it: seconds from the start and depth in metres. */
const profile = (...points: [number, number][]): TissuePoint[] => points.map(([tSeconds, depthM]) => ({ tSeconds, depthM }));

export const GAS_CASES: VectorCase[] = [
  { id: 'air-30m-salt', input: { o2: 21, he: 0, depthM: 30, water: 'salt', altitudeM: 0, ppo2Working: 1.4, ppo2Contingency: 1.6, oxygenNarcotic: true } satisfies GasVectorInput },
  { id: 'ean32-33m-salt', input: { o2: 32, he: 0, depthM: 33.4, water: 'salt', altitudeM: 0, ppo2Working: 1.4, ppo2Contingency: 1.6, oxygenNarcotic: true } satisfies GasVectorInput },
  { id: 'trimix-21-35-40m-salt', input: { o2: 21, he: 35, depthM: 40, water: 'salt', altitudeM: 0, ppo2Working: 1.4, ppo2Contingency: 1.6, oxygenNarcotic: true } satisfies GasVectorInput },
  { id: 'ean36-25m-fresh-900', input: { o2: 36, he: 0, depthM: 25, water: 'fresh', altitudeM: 900, ppo2Working: 1.4, ppo2Contingency: 1.6, oxygenNarcotic: false } satisfies GasVectorInput },
  { id: 'hypoxic-17-10m-en13319', input: { o2: 17, he: 0, depthM: 10, water: 'en13319', altitudeM: 0, ppo2Working: 1.4, ppo2Contingency: 1.6, oxygenNarcotic: true } satisfies GasVectorInput },
  { id: 'oxygen-100-5m-salt', input: { o2: 100, he: 0, depthM: 5, water: 'salt', altitudeM: 0, ppo2Working: 1.4, ppo2Contingency: 1.6, oxygenNarcotic: true } satisfies GasVectorInput },
];

export const TISSUES_CASES: VectorCase[] = [
  {
    id: 'clean-surface-air',
    input: { gas: AIR, conditions: { water: 'salt', altitudeM: 0 }, gfHigh: 85, initialTissues: null, points: profile([0, 0], [3600, 0]) } satisfies TissuesVectorInput,
  },
  {
    id: 'air-30m-20min-salt',
    input: {
      gas: AIR, conditions: { water: 'salt', altitudeM: 0 }, gfHigh: 85, initialTissues: null,
      points: profile([0, 0], [100, 30], [1300, 30], [1580, 5], [1760, 5], [1880, 0]),
    } satisfies TissuesVectorInput,
  },
  {
    id: 'ean32-24m-fresh-900',
    input: {
      gas: EAN32, conditions: { water: 'fresh', altitudeM: 900 }, gfHigh: 100, initialTissues: null,
      points: profile([0, 0], [120, 24], [1320, 24], [1500, 5], [1680, 5], [1800, 0]),
    } satisfies TissuesVectorInput,
  },
  {
    id: 'trimix-21-35-40m-salt',
    input: {
      gas: TRIMIX_21_35, conditions: { water: 'salt', altitudeM: 0 }, gfHigh: 70, initialTissues: null,
      points: profile([0, 0], [135, 40], [1215, 40], [1540, 5], [1720, 5], [1840, 0]),
    } satisfies TissuesVectorInput,
  },
  {
    id: 'residual-air-18m',
    input: {
      gas: AIR, conditions: { water: 'salt', altitudeM: 0 }, gfHigh: 85,
      initialTissues: Array.from({ length: 16 }, (_, i) => 0.82 + i * 0.001),
      points: profile([0, 0], [60, 18], [1260, 18]),
    } satisfies TissuesVectorInput,
  },
  {
    id: 'duplicate-timestamp-depth-change',
    input: {
      gas: AIR, conditions: { water: 'salt', altitudeM: 0 }, gfHigh: 85, initialTissues: null,
      points: profile([0, 0], [0, 30], [60, 30]),
    } satisfies TissuesVectorInput,
  },
];

export const NDL_CASES: VectorCase[] = [
  { id: 'air-18m-fresh-gf100', input: { gas: AIR, depthM: 18, gfHigh: 100, conditions: { water: 'fresh', altitudeM: 0 }, initialTissues: null } satisfies NdlVectorInput },
  { id: 'air-30m-salt-gf85', input: { gas: AIR, depthM: 30, gfHigh: 85, conditions: { water: 'salt', altitudeM: 0 }, initialTissues: null } satisfies NdlVectorInput },
  { id: 'ean32-30m-salt-gf85', input: { gas: EAN32, depthM: 30, gfHigh: 85, conditions: { water: 'salt', altitudeM: 0 }, initialTissues: null } satisfies NdlVectorInput },
  { id: 'air-10m-salt-gf100', input: { gas: AIR, depthM: 10, gfHigh: 100, conditions: { water: 'salt', altitudeM: 0 }, initialTissues: null } satisfies NdlVectorInput },
  { id: 'air-40m-salt-gf85', input: { gas: AIR, depthM: 40, gfHigh: 85, conditions: { water: 'salt', altitudeM: 0 }, initialTissues: null } satisfies NdlVectorInput },
  { id: 'trimix-21-35-40m-salt-gf85', input: { gas: TRIMIX_21_35, depthM: 40, gfHigh: 85, conditions: { water: 'salt', altitudeM: 0 }, initialTissues: null } satisfies NdlVectorInput },
  {
    id: 'residual-air-18m-gf85',
    input: {
      gas: AIR, depthM: 18, gfHigh: 85, conditions: { water: 'salt', altitudeM: 0 },
      initialTissues: Array.from({ length: 16 }, (_, i) => 0.82 + i * 0.001),
    } satisfies NdlVectorInput,
  },
  { id: 'shallow-6m-salt-gf100', input: { gas: AIR, depthM: 6, gfHigh: 100, conditions: { water: 'salt', altitudeM: 0 }, initialTissues: null } satisfies NdlVectorInput },
];

export const OXYGEN_CASES: VectorCase[] = [
  { id: 'below-table-0-55', input: { ppO2: 0.55, minutes: 30 } satisfies OxygenVectorInput },
  { id: 'row-0-7', input: { ppO2: 0.7, minutes: 60 } satisfies OxygenVectorInput },
  { id: 'row-1-0', input: { ppO2: 1.0, minutes: 30 } satisfies OxygenVectorInput },
  { id: 'row-1-2', input: { ppO2: 1.2, minutes: 120 } satisfies OxygenVectorInput },
  { id: 'between-1-3-and-1-4', input: { ppO2: 1.35, minutes: 45 } satisfies OxygenVectorInput },
  { id: 'row-1-6', input: { ppO2: 1.6, minutes: 45 } satisfies OxygenVectorInput },
  { id: 'above-table-1-61', input: { ppO2: 1.61, minutes: 10 } satisfies OxygenVectorInput },
];

export const REAL_GAS_CASES: VectorCase[] = [
  { id: 'air-12l-200bar', input: { gas: AIR, volumeL: 12, bar: 200, litres: 2400 } satisfies RealGasVectorInput },
  { id: 'air-12l-300bar', input: { gas: AIR, volumeL: 12, bar: 300, litres: 3000 } satisfies RealGasVectorInput },
  { id: 'trimix-21-35-12l-200bar', input: { gas: TRIMIX_21_35, volumeL: 12, bar: 200, litres: 2400 } satisfies RealGasVectorInput },
  { id: 'oxygen-7l-150bar', input: { gas: { o2: 100, he: 0 }, volumeL: 7, bar: 150, litres: 900 } satisfies RealGasVectorInput },
  { id: 'helium-12l-200bar', input: { gas: { o2: 0, he: 100 }, volumeL: 12, bar: 200, litres: 2400 } satisfies RealGasVectorInput },
  { id: 'above-fit-400bar', input: { gas: AIR, volumeL: 12, bar: 400, litres: 2400 } satisfies RealGasVectorInput },
  { id: 'clamped-at-351bar', input: { gas: AIR, volumeL: 12, bar: 300, litres: 5000 } satisfies RealGasVectorInput },
  { id: 'zero-and-negative', input: { gas: AIR, volumeL: 12, bar: -50, litres: -10 } satisfies RealGasVectorInput },
];

export const SAC_CASES: VectorCase[] = [
  {
    id: 'single-air-40min-10m',
    input: { dive: { durationSeconds: 40 * 60, avgDepthM: 10 }, cylinders: [{ volumeL: 12, startPressureBar: 201, endPressureBar: 51, gas: AIR }] } satisfies SacVectorInput,
  },
  {
    id: 'single-air-300-to-101',
    input: { dive: { durationSeconds: 40 * 60, avgDepthM: 10 }, cylinders: [{ volumeL: 12, startPressureBar: 301, endPressureBar: 101, gas: AIR }] } satisfies SacVectorInput,
  },
  {
    id: 'single-trimix',
    input: { dive: { durationSeconds: 40 * 60, avgDepthM: 10 }, cylinders: [{ volumeL: 12, startPressureBar: 201, endPressureBar: 51, gas: TRIMIX_21_35 }] } satisfies SacVectorInput,
  },
  {
    id: 'two-cylinders',
    input: {
      dive: { durationSeconds: 40 * 60, avgDepthM: 10 },
      cylinders: [
        { volumeL: 12, startPressureBar: 201, endPressureBar: 51, gas: AIR },
        { volumeL: 7, startPressureBar: 201, endPressureBar: 101, gas: { o2: 50, he: 0 } },
      ],
    } satisfies SacVectorInput,
  },
  {
    id: 'untouched-cylinder',
    input: {
      dive: { durationSeconds: 40 * 60, avgDepthM: 10 },
      cylinders: [
        { volumeL: 12, startPressureBar: 201, endPressureBar: 51, gas: AIR },
        { volumeL: 7, startPressureBar: 200, endPressureBar: 200, gas: AIR },
      ],
    } satisfies SacVectorInput,
  },
  {
    id: 'too-short',
    input: { dive: { durationSeconds: 14 * 60 + 59, avgDepthM: 10 }, cylinders: [{ volumeL: 12, startPressureBar: 200, endPressureBar: 50, gas: AIR }] } satisfies SacVectorInput,
  },
  {
    id: 'no-average-depth',
    input: { dive: { durationSeconds: 40 * 60, avgDepthM: null }, cylinders: [{ volumeL: 12, startPressureBar: 200, endPressureBar: 50, gas: AIR }] } satisfies SacVectorInput,
  },
  {
    id: 'cylinder-incomplete',
    input: { dive: { durationSeconds: 40 * 60, avgDepthM: 10 }, cylinders: [{ volumeL: 12, startPressureBar: 200, endPressureBar: null, gas: AIR }] } satisfies SacVectorInput,
  },
  {
    id: 'no-cylinders',
    input: { dive: { durationSeconds: 40 * 60, avgDepthM: 10 }, cylinders: [] } satisfies SacVectorInput,
  },
];

export const GAS_PLAN_CASES: VectorCase[] = [
  {
    id: 'rock-bottom-air-30m-one-diver',
    input: {
      gas: AIR, conditions: { water: 'salt', altitudeM: 0 }, levels: [{ depthM: 30, minutes: 20 }],
      divers: [{ name: 'Ana', volumeL: 12, startPressureBar: 200, sacLpm: 20 }],
      rule: { kind: 'rock_bottom' }, ppO2Contingency: 1.6,
    } satisfies GasPlanVectorInput,
  },
  {
    id: 'halves-ean32-two-divers',
    input: {
      gas: EAN32, conditions: { water: 'salt', altitudeM: 0 },
      levels: [{ depthM: 20, minutes: 15 }, { depthM: 30, minutes: 10 }],
      divers: [
        { name: 'Ana', volumeL: 12, startPressureBar: 200, sacLpm: 18 },
        { name: 'Bo', volumeL: 15, startPressureBar: 220, sacLpm: 22 },
      ],
      rule: { kind: 'halves' }, ppO2Contingency: 1.6,
    } satisfies GasPlanVectorInput,
  },
  {
    id: 'thirds-air-three-divers-fresh-900',
    input: {
      gas: AIR, conditions: { water: 'fresh', altitudeM: 900 }, levels: [{ depthM: 25, minutes: 20 }],
      divers: [
        { name: 'Ana', volumeL: 12, startPressureBar: 200, sacLpm: 18 },
        { name: 'Bo', volumeL: 12, startPressureBar: 210, sacLpm: 20 },
        { name: 'Cy', volumeL: 15, startPressureBar: 220, sacLpm: 16 },
      ],
      rule: { kind: 'thirds' }, ppO2Contingency: 1.6, descentRateMPerMin: 15, ascentRateMPerMin: 6,
    } satisfies GasPlanVectorInput,
  },
  {
    id: 'sixths-air-40m',
    input: {
      gas: AIR, conditions: { water: 'salt', altitudeM: 0 }, levels: [{ depthM: 40, minutes: 15 }],
      divers: [{ name: 'Ana', volumeL: 12, startPressureBar: 220, sacLpm: 20 }],
      rule: { kind: 'sixths' }, ppO2Contingency: 1.6,
    } satisfies GasPlanVectorInput,
  },
  {
    id: 'reserve-ean36-25m',
    input: {
      gas: { o2: 36, he: 0 }, conditions: { water: 'salt', altitudeM: 0 }, levels: [{ depthM: 25, minutes: 25 }],
      divers: [{ name: 'Ana', volumeL: 12, startPressureBar: 200, sacLpm: 18 }],
      rule: { kind: 'reserve', bar: 60 }, ppO2Contingency: 1.6,
    } satisfies GasPlanVectorInput,
  },
  {
    id: 'ascent-pressure-air-18m',
    input: {
      gas: AIR, conditions: { water: 'salt', altitudeM: 0 }, levels: [{ depthM: 18, minutes: 30 }],
      divers: [{ name: 'Ana', volumeL: 12, startPressureBar: 210, sacLpm: 22 }],
      rule: { kind: 'ascent_pressure', bar: 90 }, ppO2Contingency: 1.6,
    } satisfies GasPlanVectorInput,
  },
  {
    id: 'overdraw-fits-false',
    input: {
      gas: AIR, conditions: { water: 'salt', altitudeM: 0 }, levels: [{ depthM: 30, minutes: 60 }],
      divers: [{ name: 'Ana', volumeL: 12, startPressureBar: 200, sacLpm: 20 }],
      rule: { kind: 'rock_bottom' }, ppO2Contingency: 1.6,
    } satisfies GasPlanVectorInput,
  },
  {
    id: 'outside-depth-41m',
    input: {
      gas: AIR, conditions: { water: 'salt', altitudeM: 0 }, levels: [{ depthM: 41, minutes: 10 }],
      divers: [{ name: 'Ana', volumeL: 12, startPressureBar: 200, sacLpm: 20 }],
      rule: { kind: 'rock_bottom' }, ppO2Contingency: 1.6,
    } satisfies GasPlanVectorInput,
  },
  {
    id: 'outside-mod-ean40-32m',
    input: {
      gas: { o2: 40, he: 0 }, conditions: { water: 'salt', altitudeM: 0 }, levels: [{ depthM: 32, minutes: 10 }],
      divers: [{ name: 'Ana', volumeL: 12, startPressureBar: 200, sacLpm: 20 }],
      rule: { kind: 'rock_bottom' }, ppO2Contingency: 1.6,
    } satisfies GasPlanVectorInput,
  },
  {
    id: 'outside-gas-trimix',
    input: {
      gas: TRIMIX_21_35, conditions: { water: 'salt', altitudeM: 0 }, levels: [{ depthM: 30, minutes: 10 }],
      divers: [{ name: 'Ana', volumeL: 12, startPressureBar: 200, sacLpm: 20 }],
      rule: { kind: 'rock_bottom' }, ppO2Contingency: 1.6,
    } satisfies GasPlanVectorInput,
  },
];

export const BOTTOM_TIME_CASES: VectorCase[] = [
  {
    id: 'air-30m-gf85-rock-bottom',
    input: {
      gas: AIR, depthM: 30, gfHigh: 85, conditions: { water: 'salt', altitudeM: 0 },
      cylinder: { volumeL: 12, startPressureBar: 200 }, sacLpm: 20,
      rule: { kind: 'rock_bottom' }, ppO2: { working: 1.4, contingency: 1.6 },
    } satisfies BottomTimeVectorInput,
  },
  {
    id: 'ean32-30m-gf85-reserve',
    input: {
      gas: EAN32, depthM: 30, gfHigh: 85, conditions: { water: 'salt', altitudeM: 0 },
      cylinder: { volumeL: 12, startPressureBar: 220 }, sacLpm: 20,
      rule: { kind: 'reserve', bar: 50 }, ppO2: { working: 1.4, contingency: 1.6 },
    } satisfies BottomTimeVectorInput,
  },
  {
    id: 'air-18m-gf100-no-cylinder',
    input: {
      gas: AIR, depthM: 18, gfHigh: 100, conditions: { water: 'salt', altitudeM: 0 },
      cylinder: null, sacLpm: 20,
      rule: { kind: 'rock_bottom' }, ppO2: { working: 1.4, contingency: 1.6 },
    } satisfies BottomTimeVectorInput,
  },
  {
    id: 'ean36-25m-ascent-pressure',
    input: {
      gas: { o2: 36, he: 0 }, depthM: 25, gfHigh: 85, conditions: { water: 'salt', altitudeM: 0 },
      cylinder: { volumeL: 12, startPressureBar: 200 }, sacLpm: 18,
      rule: { kind: 'ascent_pressure', bar: 80 }, ppO2: { working: 1.4, contingency: 1.6 },
    } satisfies BottomTimeVectorInput,
  },
  {
    id: 'ean36-30m-above-working',
    input: {
      gas: { o2: 36, he: 0 }, depthM: 30, gfHigh: 85, conditions: { water: 'salt', altitudeM: 0 },
      cylinder: { volumeL: 12, startPressureBar: 220 }, sacLpm: 20,
      rule: { kind: 'ascent_pressure', bar: 80 }, ppO2: { working: 1.4, contingency: 1.6 },
    } satisfies BottomTimeVectorInput,
  },
  {
    id: 'air-20m-gf85-ascent-pressure',
    input: {
      gas: AIR, depthM: 20, gfHigh: 85, conditions: { water: 'salt', altitudeM: 0 },
      cylinder: { volumeL: 15, startPressureBar: 220 }, sacLpm: 22,
      rule: { kind: 'ascent_pressure', bar: 100 }, ppO2: { working: 1.4, contingency: 1.6 },
    } satisfies BottomTimeVectorInput,
  },
  {
    id: 'outside-depth-41m',
    input: {
      gas: AIR, depthM: 41, gfHigh: 85, conditions: { water: 'salt', altitudeM: 0 },
      cylinder: { volumeL: 12, startPressureBar: 200 }, sacLpm: 20,
      rule: { kind: 'rock_bottom' }, ppO2: { working: 1.4, contingency: 1.6 },
    } satisfies BottomTimeVectorInput,
  },
  {
    id: 'outside-mod-ean40-34m',
    input: {
      gas: { o2: 40, he: 0 }, depthM: 34, gfHigh: 85, conditions: { water: 'salt', altitudeM: 0 },
      cylinder: { volumeL: 12, startPressureBar: 200 }, sacLpm: 20,
      rule: { kind: 'rock_bottom' }, ppO2: { working: 1.4, contingency: 1.6 },
    } satisfies BottomTimeVectorInput,
  },
  {
    id: 'outside-gas-trimix',
    input: {
      gas: TRIMIX_21_35, depthM: 30, gfHigh: 85, conditions: { water: 'salt', altitudeM: 0 },
      cylinder: { volumeL: 12, startPressureBar: 200 }, sacLpm: 20,
      rule: { kind: 'rock_bottom' }, ppO2: { working: 1.4, contingency: 1.6 },
    } satisfies BottomTimeVectorInput,
  },
  {
    id: 'residual-air-18m-gf85',
    input: {
      gas: AIR, depthM: 18, gfHigh: 85, conditions: { water: 'salt', altitudeM: 0 },
      cylinder: null, sacLpm: 20, rule: { kind: 'rock_bottom' }, ppO2: { working: 1.4, contingency: 1.6 },
      initialTissues: Array.from({ length: 16 }, (_, i) => 0.82 + i * 0.001),
    } satisfies BottomTimeVectorInput,
  },
  {
    id: 'tie-ndl-gas-air-30m',
    input: {
      gas: AIR, depthM: 30, gfHigh: 85, conditions: { water: 'salt', altitudeM: 0 },
      cylinder: { volumeL: 12, startPressureBar: 171.5 }, sacLpm: 20,
      rule: { kind: 'rock_bottom' }, ppO2: { working: 1.4, contingency: 1.6 },
    } satisfies BottomTimeVectorInput,
  },
  {
    id: 'shallow-6m-gas-only',
    input: {
      gas: AIR, depthM: 6, gfHigh: 100, conditions: { water: 'salt', altitudeM: 0 },
      cylinder: { volumeL: 12, startPressureBar: 200 }, sacLpm: 20,
      rule: { kind: 'rock_bottom' }, ppO2: { working: 1.4, contingency: 1.6 },
    } satisfies BottomTimeVectorInput,
  },
];

export const PLANNING_SAC_CASES: VectorCase[] = [
  { id: 'five-dives-inclusive-rank', input: { dives: [22, 18, 20, 16, 24].map((sacLpm) => ({ sacLpm })) } satisfies PlanningSacVectorInput },
  { id: 'four-dives-default', input: { dives: [22, 18, 20, 16].map((sacLpm) => ({ sacLpm })) } satisfies PlanningSacVectorInput },
  {
    id: 'nulls-filtered',
    input: { dives: [20, null, 22, 18, null, 24, 26].map((sacLpm) => ({ sacLpm })) } satisfies PlanningSacVectorInput,
  },
  {
    id: 'twenty-most-recent',
    input: { dives: [30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6].map((sacLpm) => ({ sacLpm })) } satisfies PlanningSacVectorInput,
  },
  { id: 'all-tied', input: { dives: Array.from({ length: 6 }, () => ({ sacLpm: 18 })) } satisfies PlanningSacVectorInput },
  { id: 'rounds-tie-up', input: { dives: [20, 20, 20, 20, 20.125].map((sacLpm) => ({ sacLpm })) } satisfies PlanningSacVectorInput },
  { id: 'single-dive-default', input: { dives: [{ sacLpm: 17 }] } satisfies PlanningSacVectorInput },
  { id: 'all-null-default', input: { dives: Array.from({ length: 6 }, () => ({ sacLpm: null })) } satisfies PlanningSacVectorInput },
  { id: 'unsorted', input: { dives: [24, 16, 22, 18, 20].map((sacLpm) => ({ sacLpm })) } satisfies PlanningSacVectorInput },
];

/** A fixed instant for the loadings and no-fly cases: 2026-11-18T12:00:00Z. */
const T0 = Date.UTC(2026, 10, 18, 12, 0, 0);

const loadingDive = (
  endMs: number,
  durationSeconds: number,
  samples: TissuePoint[],
  extra: Partial<Pick<LoadingsVectorDive, 'gas' | 'otuStart' | 'otuEnd'>> = {},
): LoadingsVectorDive => ({
  startMs: endMs - durationSeconds * 1000, endMs, samples, gas: AIR, otuStart: null, otuEnd: null, ...extra,
});

/** A synthetic 30 m dive at 2 s sampling, reduced by the samples endpoint's min/max downsample (maxPoints 40). */
function syntheticProfile(): TissuePoint[] {
  const points: TissuePoint[] = [];
  const push = (tSeconds: number, depthM: number) => points.push({ tSeconds, depthM });
  for (let s = 0; s <= 120; s += 2) push(s, (s / 120) * 30);
  for (let s = 122; s <= 1320; s += 2) push(s, 30);
  for (let s = 1322; s <= 1560; s += 2) push(s, 30 - ((s - 1320) / 240) * 25);
  for (let s = 1562; s <= 1740; s += 2) push(s, 5);
  for (let s = 1742; s <= 1800; s += 2) push(s, 5 - ((s - 1740) / 60) * 5);
  return points;
}

const DOWNSAMPLED_PROFILE: TissuePoint[] = (() => {
  const full = syntheticProfile();
  const { offsets, values } = downsampleMinMax(
    full.map((point) => point.tSeconds * 1000),
    full.map((point) => point.depthM),
    40,
  );
  return offsets.map((ms, i) => ({ tSeconds: ms / 1000, depthM: values[i]! }));
})();

const PROFILE_31M = profile([0, 0], [120, 20], [1500, 20], [1740, 5], [1800, 5], [1860, 0]);
const PROFILE_30M = profile([0, 0], [60, 18], [1500, 18], [1800, 0]);

export const LOADINGS_CASES: VectorCase[] = [
  {
    id: 'no-dives',
    input: { dives: [], nowMs: T0, gfHigh: 85, cns: null } satisfies LoadingsVectorInput,
  },
  {
    id: 'latest-older-than-window',
    input: {
      dives: [loadingDive(T0 - 80 * HOUR_MS, 3600, [])],
      nowMs: T0, gfHigh: 85, cns: null,
    } satisfies LoadingsVectorInput,
  },
  {
    id: 'stale-with-profile',
    input: {
      dives: [loadingDive(T0 - 50 * HOUR_MS, 1860, PROFILE_31M)],
      nowMs: T0, gfHigh: 85, cns: null,
    } satisfies LoadingsVectorInput,
  },
  {
    id: 'no-profile',
    input: {
      dives: [loadingDive(T0 - 2 * HOUR_MS, 2400, [])],
      nowMs: T0, gfHigh: 85, cns: null,
    } satisfies LoadingsVectorInput,
  },
  {
    id: 'single-air-30m-with-cns-otu',
    input: {
      dives: [loadingDive(T0 - HOUR_MS, 1860, PROFILE_31M, { otuStart: 12, otuEnd: 30 })],
      nowMs: T0, gfHigh: 85, cns: { cnsEnd: 42, endedAtMs: T0 - HOUR_MS },
    } satisfies LoadingsVectorInput,
  },
  {
    id: 'cns-elapsed-zero',
    input: {
      dives: [],
      nowMs: T0, gfHigh: 85, cns: { cnsEnd: 37.5, endedAtMs: T0 },
    } satisfies LoadingsVectorInput,
  },
  {
    id: 'two-dives-with-interval-and-old-dive',
    input: {
      dives: [
        loadingDive(T0 - HOUR_MS, 1860, PROFILE_31M, { otuStart: 20, otuEnd: 35 }),
        loadingDive(T0 - 80 * HOUR_MS, 3600, [], { otuStart: 100, otuEnd: 200 }),
        loadingDive(T0 - 3 * HOUR_MS, 1800, PROFILE_30M, { otuStart: 5, otuEnd: 15 }),
      ],
      nowMs: T0, gfHigh: 85, cns: null,
    } satisfies LoadingsVectorInput,
  },
  {
    id: 'downsampled-series-as-the-api-emits-it',
    input: {
      dives: [loadingDive(T0 - 20 * HOUR_MS, 1800, DOWNSAMPLED_PROFILE)],
      nowMs: T0, gfHigh: 85, cns: null,
    } satisfies LoadingsVectorInput,
  },
];

const seriesDive = (
  id: string,
  startMs: number,
  durationSeconds: number,
  extra: Partial<Pick<NoFlyVectorDive, 'utcOffsetSeconds' | 'maxDepthM' | 'diveMode' | 'ndlSeconds'>> = {},
): NoFlyVectorDive => ({
  id, startMs, durationSeconds, utcOffsetSeconds: null, maxDepthM: 20, diveMode: 'open_circuit', ndlSeconds: null, ...extra,
});

const DAY = Date.UTC(2026, 10, 18, 8, 0, 0);

export const NO_FLY_CASES: VectorCase[] = [
  {
    id: 'single-last-dive',
    input: {
      dives: [seriesDive('a', DAY, 2400, { ndlSeconds: [600, 300, 120] })], id: 'a',
    } satisfies NoFlyVectorInput,
  },
  {
    id: 'first-of-two-is-not-last',
    input: {
      dives: [seriesDive('a', DAY, 2400), seriesDive('b', DAY + 3 * HOUR_MS, 1800)], id: 'a',
    } satisfies NoFlyVectorInput,
  },
  {
    id: 'second-of-two-same-day',
    input: {
      dives: [seriesDive('a', DAY, 2400), seriesDive('b', DAY + 3 * HOUR_MS, 1800)], id: 'b',
    } satisfies NoFlyVectorInput,
  },
  {
    id: 'deco-on-target',
    input: {
      dives: [seriesDive('a', DAY, 2400), seriesDive('b', DAY + 3 * HOUR_MS, 1800, { ndlSeconds: [300, 0, 0] })], id: 'b',
    } satisfies NoFlyVectorInput,
  },
  {
    id: 'deco-on-earlier-dive',
    input: {
      dives: [seriesDive('a', DAY, 2400, { ndlSeconds: [0, 0] }), seriesDive('b', DAY + 3 * HOUR_MS, 1800)], id: 'b',
    } satisfies NoFlyVectorInput,
  },
  {
    id: 'previous-day-diving',
    input: {
      dives: [seriesDive('a', DAY - 20 * HOUR_MS, 2400), seriesDive('b', DAY, 1800)], id: 'b',
    } satisfies NoFlyVectorInput,
  },
  {
    id: 'non-applying-dive-does-not-count',
    input: {
      dives: [seriesDive('a', DAY, 2400, { diveMode: 'apnea' }), seriesDive('b', DAY + 3 * HOUR_MS, 1800)], id: 'b',
    } satisfies NoFlyVectorInput,
  },
  {
    id: 'target-not-covered',
    input: { dives: [seriesDive('a', DAY, 2400, { diveMode: 'ccr' })], id: 'a' } satisfies NoFlyVectorInput,
  },
  {
    id: 'unknown-id',
    input: { dives: [seriesDive('a', DAY, 2400)], id: 'missing' } satisfies NoFlyVectorInput,
  },
  {
    id: 'empty-ndl-is-no-deco',
    input: { dives: [seriesDive('a', DAY, 2400, { ndlSeconds: [] })], id: 'a' } satisfies NoFlyVectorInput,
  },
  {
    id: 'utc-offset-crosses-midnight',
    input: {
      dives: [
        seriesDive('a', Date.UTC(2026, 10, 18, 23, 0, 0), 2400, { utcOffsetSeconds: 3600 }),
        seriesDive('b', Date.UTC(2026, 10, 19, 1, 0, 0), 1800, { utcOffsetSeconds: 3600 }),
      ],
      id: 'a',
    } satisfies NoFlyVectorInput,
  },
  {
    id: 'unknown-offset-uses-utc-wall-clock',
    input: {
      dives: [
        seriesDive('a', Date.UTC(2026, 10, 18, 23, 0, 0), 2400),
        seriesDive('b', Date.UTC(2026, 10, 19, 1, 0, 0), 1800),
      ],
      id: 'a',
    } satisfies NoFlyVectorInput,
  },
];

export const VECTOR_CASES: Record<VectorKernel, VectorCase[]> = {
  gas: GAS_CASES,
  tissues: TISSUES_CASES,
  ndl: NDL_CASES,
  oxygen: OXYGEN_CASES,
  'real-gas': REAL_GAS_CASES,
  sac: SAC_CASES,
  'gas-plan': GAS_PLAN_CASES,
  'bottom-time': BOTTOM_TIME_CASES,
  'planning-sac': PLANNING_SAC_CASES,
  loadings: LOADINGS_CASES,
  'no-fly': NO_FLY_CASES,
};

function evaluateGas(input: GasVectorInput): VectorOutcome {
  const gas = { o2: input.o2, he: input.he };
  const conditions = conditionsOf(input);
  const depthM = input.depthM;
  const shell = gasNumbers({
    o2: gas.o2, he: gas.he, depth: depthM, water: input.water, altitude: input.altitudeM,
    ppo2Working: input.ppo2Working, ppo2Contingency: input.ppo2Contingency, oxygenNarcotic: input.oxygenNarcotic,
  });
  const atDepth = shell.atDepth;
  if (atDepth === null) throw new Error('a gas vector needs a depth');
  return {
    kernel: {
      surfacePressureBar: surfacePressureBar(input.altitudeM),
      metresPerBar: metresPerBar(conditions),
      ambientPressureBar: ambientPressureBar(depthM, conditions),
      modWorkingM: modMetres(gas, input.ppo2Working, conditions),
      modContingencyM: modMetres(gas, input.ppo2Contingency, conditions),
      bestMixPercent: bestMixPercent(depthM, input.ppo2Working, conditions),
      eadM: eadMetres(gas, depthM, conditions),
      endM: endMetres(gas, depthM, conditions, { oxygenNarcotic: input.oxygenNarcotic }),
      hypoxic: isHypoxic(gas),
    },
    service: {
      modWorkingM: shell.modWorkingM,
      modContingencyM: shell.modContingencyM,
      hypoxic: shell.hypoxic,
      atDepth: {
        depthM: atDepth.depthM,
        ppO2: atDepth.ppO2,
        bestMixPercent: atDepth.bestMixPercent,
        eadM: atDepth.eadM,
        endM: atDepth.endM,
      },
    },
  };
}

function evaluateTissues(input: TissuesVectorInput): VectorOutcome {
  const clean = surfaceSaturatedTissues(input.conditions);
  const start = input.initialTissues ?? clean;
  const tissues = advanceTissues(start, input.points, input.gas, input.conditions);
  const worst = worstCompartmentPercent(tissues, input.gfHigh, input.conditions);
  return {
    kernel: {
      surfaceSaturatedTissues: clean,
      tissues,
      worstCompartmentPercent: worst,
    },
    service: {
      worstCompartmentPercent: round(worst, 1),
    },
  };
}

function evaluateNdl(input: NdlVectorInput): VectorOutcome {
  const minutes = ndlMinutes({
    gas: input.gas, depthM: input.depthM, gfHigh: input.gfHigh, conditions: input.conditions,
    ...(input.initialTissues ? { initialTissues: input.initialTissues } : {}),
  });
  return {
    kernel: { minutes: finiteOrNull(minutes) },
    service: { minutes: finiteOrNull(round(minutes, 1)) },
  };
}

function evaluateOxygen(input: OxygenVectorInput): VectorOutcome {
  const limit = oxygenLimitMinutes(input.ppO2);
  const otuValue = otu(input.ppO2, input.minutes);
  return {
    kernel: { limitMinutes: finiteOrNull(limit), otu: otuValue },
    service: { limitMinutes: finiteOrNull(round(limit, 1)), otu: round(otuValue, 1) },
  };
}

function evaluateRealGas(input: RealGasVectorInput): VectorOutcome {
  return {
    kernel: {
      compressibility: compressibility(input.bar, input.gas),
      surfaceLitres: surfaceLitres(input.volumeL, input.bar, input.gas),
      pressureForLitres: pressureForLitres(input.volumeL, input.litres, input.gas),
    },
    service: {
      surfaceLitres: round(surfaceLitres(input.volumeL, input.bar, input.gas), 1),
      pressureForLitres: round(pressureForLitres(input.volumeL, input.litres, input.gas), 1),
    },
  };
}

function evaluateSac(input: SacVectorInput): VectorOutcome {
  const raw: { sac: SacValues | null; missing: SacMissing | null } = sacValues(input.dive, input.cylinders);
  const service = sacOfDive(input.dive, input.cylinders);
  return {
    kernel: {
      litresPerMinute: raw.sac?.litresPerMinute ?? null,
      barPerMinute: raw.sac?.barPerMinute ?? null,
      surfaceMinutes: raw.sac?.surfaceMinutes ?? null,
      missing: raw.missing,
    },
    service: {
      sac: service.sac === null ? null : { litresPerMinute: service.sac.litresPerMinute, barPerMinute: service.sac.barPerMinute },
      missing: service.missing,
    },
  };
}

function evaluateGasPlan(input: GasPlanVectorInput): VectorOutcome {
  const plan = planGas(input);
  if (plan.status === 'outside_limits') {
    const outside = { status: plan.status, reason: plan.reason, levelIndex: plan.levelIndex };
    return { kernel: outside, service: outside };
  }
  const kernel = {
    status: plan.status,
    runtimeMinutes: plan.runtimeMinutes,
    segments: plan.segments.map((s) => ({
      kind: s.kind, fromDepthM: s.fromDepthM, depthM: s.depthM, minutes: s.minutes, ambientBar: s.ambientBar,
    })),
    divers: plan.divers.map((p) => ({
      name: p.name, litres: p.litres, endPressureBar: p.endPressureBar,
      rockBottom: p.rockBottom.map((r) => ({ depthM: r.depthM, pressureBar: r.pressureBar })),
      ascentPressureBar: p.ascentPressureBar, turnPressureBar: p.turnPressureBar,
      belowRockBottom: p.belowRockBottom, atAscentStartBar: p.atAscentStartBar,
      pressuresBar: p.pressuresBar, marginBar: p.marginBar, marginMinutes: p.marginMinutes,
      controlling: p.controlling,
    })),
    controllingIndex: plan.controllingIndex, fits: plan.fits, leaveLastLevelAtMinute: plan.leaveLastLevelAtMinute,
  };
  // The route's view: one decimal for minutes, pressures and litres, three for the mean ambient pressure.
  const service = {
    status: plan.status,
    runtimeMinutes: round(plan.runtimeMinutes, 1),
    segments: plan.segments.map((s) => ({
      kind: s.kind, fromDepthM: s.fromDepthM, depthM: s.depthM, minutes: round(s.minutes, 1), ambientBar: round(s.ambientBar, 3),
    })),
    divers: plan.divers.map((p) => ({
      name: p.name, litres: round(p.litres, 1), endPressureBar: round(p.endPressureBar, 1),
      rockBottom: p.rockBottom.map((r) => ({ depthM: r.depthM, pressureBar: round(r.pressureBar, 1) })),
      ascentPressureBar: round(p.ascentPressureBar, 1),
      turnPressureBar: p.turnPressureBar === null ? null : round(p.turnPressureBar, 1),
      belowRockBottom: p.belowRockBottom, atAscentStartBar: round(p.atAscentStartBar, 1),
      pressuresBar: p.pressuresBar.map((bar) => round(bar, 1)), marginBar: round(p.marginBar, 1),
      marginMinutes: round(p.marginMinutes, 1), controlling: p.controlling,
    })),
    controllingIndex: plan.controllingIndex, fits: plan.fits,
    leaveLastLevelAtMinute: round(plan.leaveLastLevelAtMinute, 1),
  };
  return { kernel, service };
}

function evaluateBottomTime(input: BottomTimeVectorInput): VectorOutcome {
  const result = bottomTime(input);
  if (result.status === 'outside_limits') {
    const kernel = { status: result.status, reason: result.reason, modM: result.modM };
    const service = {
      status: result.status, reason: result.reason,
      modM: result.modM === null ? null : floorTenth(result.modM),
    };
    return { kernel, service };
  }
  const kernel = {
    status: result.status,
    minutes: result.minutes,
    binding: result.binding,
    limits: {
      ndl: finiteOrNull(result.limits.ndl),
      oxygen: finiteOrNull(result.limits.oxygen),
      gas: result.limits.gas,
    },
    otu: result.otu,
    ppO2AtDepth: result.ppO2AtDepth,
    ascentPressureBar: result.ascentPressureBar,
    ppO2AboveWorking: result.ppO2AboveWorking,
  };
  const finiteTenth = (n: number) => (Number.isFinite(n) ? round(n, 1) : null);
  const service = {
    status: result.status,
    minutes: result.minutes,
    binding: result.binding,
    limits: {
      ndl: finiteTenth(result.limits.ndl),
      oxygen: finiteTenth(result.limits.oxygen),
      gas: result.limits.gas === null ? null : finiteTenth(result.limits.gas),
    },
    ascentPressureBar: result.ascentPressureBar === null ? null : round(result.ascentPressureBar, 1),
    otu: round(result.otu, 1),
    ppO2AtDepth: round(result.ppO2AtDepth, 2),
    ppO2AboveWorking: result.ppO2AboveWorking,
  };
  return { kernel, service };
}

function evaluatePlanningSac(input: PlanningSacVectorInput): VectorOutcome {
  const result = planningSac(input.dives);
  const view = { lpm: result.lpm, basedOn: result.basedOn, source: result.source };
  return { kernel: view, service: view };
}

function evaluateLoadings(input: LoadingsVectorInput): VectorOutcome {
  const dives: LoadingDive[] = input.dives.map((dive) => ({
    startMs: dive.startMs,
    endMs: dive.endMs,
    samples: dive.samples,
    ...(dive.gas === null ? {} : { gas: dive.gas }),
    ...(dive.otuStart === null ? {} : { otuStart: dive.otuStart }),
    ...(dive.otuEnd === null ? {} : { otuEnd: dive.otuEnd }),
  }));
  const n2 = tissuesAfterDives({ dives, nowMs: input.nowMs, gfHigh: input.gfHigh });
  const otu = otuLast24h(dives, input.nowMs);
  const cnsPercentNow = input.cns === null
    ? null
    : cnsDecayed(input.cns.cnsEnd, (input.nowMs - input.cns.endedAtMs) / 60_000);
  return {
    kernel: {
      worstCompartmentPercent: n2.worstCompartmentPercent,
      reason: 'reason' in n2 ? n2.reason : null,
      gfHigh: n2.gfHigh,
      basis: n2.basis,
      tissues: n2.tissues,
      cnsPercentNow,
      otuLast24Hours: otu,
    },
    service: {
      n2: n2.worstCompartmentPercent === null ? null : {
        worstCompartmentPercent: round(n2.worstCompartmentPercent, 1), gfHigh: n2.gfHigh,
      },
      cns: cnsPercentNow === null || input.cns === null ? null : {
        percentNow: round(cnsPercentNow, 1),
        percentAtLastDiveEnd: round(input.cns.cnsEnd, 1),
        halfLifeMinutes: CNS_HALF_LIFE_MINUTES,
      },
      otu: otu === null ? null : { last24Hours: round(otu, 1) },
    },
  };
}

function evaluateNoFly(input: NoFlyVectorInput): VectorOutcome {
  const applies = (diveMode: string | null) => assessmentApplies(diveMode ?? undefined);
  const series = input.dives.map((dive) => {
    const minNdlSeconds = dive.ndlSeconds !== null && dive.ndlSeconds.length > 0
      ? Math.min(...dive.ndlSeconds)
      : null;
    return seriesDiveOf(
      {
        id: dive.id,
        startsAt: new Date(dive.startMs),
        utcOffsetSeconds: dive.utcOffsetSeconds,
        durationSeconds: dive.durationSeconds,
        maxDepthM: dive.maxDepthM,
      },
      minNdlSeconds === 0,
    );
  });
  const covered = series.filter((_, i) => applies(input.dives[i]!.diveMode));
  const noFly = noFlyAfter(covered, input.id);
  return {
    kernel: {
      dives: input.dives.map((dive, i) => ({
        id: dive.id,
        day: series[i]!.day,
        startMs: series[i]!.startMs,
        endMs: series[i]!.endMs,
        diveMode: dive.diveMode,
        applies: applies(dive.diveMode),
        minNdlSeconds: dive.ndlSeconds !== null && dive.ndlSeconds.length > 0 ? Math.min(...dive.ndlSeconds) : null,
        enteredDeco: series[i]!.enteredDeco,
      })),
      noFly,
    },
    service: { noFly: noFly === null ? null : { ...noFly, source: SOURCES.dan_flying } },
  };
}

export function evaluateVectorCase(kernel: VectorKernel, input: unknown): VectorOutcome {
  switch (kernel) {
    case 'gas': return evaluateGas(input as GasVectorInput);
    case 'tissues': return evaluateTissues(input as TissuesVectorInput);
    case 'ndl': return evaluateNdl(input as NdlVectorInput);
    case 'oxygen': return evaluateOxygen(input as OxygenVectorInput);
    case 'real-gas': return evaluateRealGas(input as RealGasVectorInput);
    case 'sac': return evaluateSac(input as SacVectorInput);
    case 'gas-plan': return evaluateGasPlan(input as GasPlanVectorInput);
    case 'bottom-time': return evaluateBottomTime(input as BottomTimeVectorInput);
    case 'planning-sac': return evaluatePlanningSac(input as PlanningSacVectorInput);
    case 'loadings': return evaluateLoadings(input as LoadingsVectorInput);
    case 'no-fly': return evaluateNoFly(input as NoFlyVectorInput);
  }
}

export interface VectorFile {
  /** The file's kernel name: one of [VECTOR_KERNELS] here, or another kernel's name (FAH-272). */
  kernel: string;
  version: 1;
  tolerance: { relative: number };
  cases: { id: string; input: unknown; expected: VectorOutcome }[];
}

export function vectorFile(kernel: VectorKernel): VectorFile {
  return {
    kernel,
    version: 1,
    tolerance: { relative: 1e-9 },
    cases: VECTOR_CASES[kernel].map((vector) => ({
      id: vector.id,
      input: vector.input,
      expected: evaluateVectorCase(kernel, vector.input),
    })),
  };
}
