// The fixed cases of the planning golden vectors (FAH-268): the generator writes their expected values, and the
// vitest runner recomputes them from the same cases. The Kotlin port reads the committed JSON, never this file.
import { compressibility, pressureForLitres, surfaceLitres } from '../dives/calc/real-gas.js';
import { sacOfDive, sacValues, type SacMissing, type SacValues } from '../dives/calc/sac.js';
import {
  ambientPressureBar, bestMixPercent, eadMetres, endMetres, isHypoxic, metresPerBar, modMetres, surfacePressureBar,
  type Conditions, type GasMix, type PlanningWater,
} from './gas.js';
import { gasNumbers } from './gas-numbers.js';
import { ndlMinutes } from './ndl.js';
import { otu, oxygenLimitMinutes } from './oxygen.js';
import { advanceTissues, surfaceSaturatedTissues, worstCompartmentPercent, type TissuePoint } from './tissues.js';

export const VECTOR_KERNELS = ['gas', 'tissues', 'ndl', 'oxygen', 'real-gas', 'sac'] as const;
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

export const VECTOR_CASES: Record<VectorKernel, VectorCase[]> = {
  gas: GAS_CASES,
  tissues: TISSUES_CASES,
  ndl: NDL_CASES,
  oxygen: OXYGEN_CASES,
  'real-gas': REAL_GAS_CASES,
  sac: SAC_CASES,
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

export function evaluateVectorCase(kernel: VectorKernel, input: unknown): VectorOutcome {
  switch (kernel) {
    case 'gas': return evaluateGas(input as GasVectorInput);
    case 'tissues': return evaluateTissues(input as TissuesVectorInput);
    case 'ndl': return evaluateNdl(input as NdlVectorInput);
    case 'oxygen': return evaluateOxygen(input as OxygenVectorInput);
    case 'real-gas': return evaluateRealGas(input as RealGasVectorInput);
    case 'sac': return evaluateSac(input as SacVectorInput);
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
