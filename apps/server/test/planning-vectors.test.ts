// The TypeScript side of the golden vectors (FAH-268, lead estimate FAH-272): every committed vector is recomputed
// with the reference kernels, so a changed kernel fails until the vectors are deliberately regenerated and reviewed.
import { readFileSync } from 'node:fs';
import { fileURLToPath } from 'node:url';
import { describe, expect, it } from 'vitest';
import { LEAD_VECTOR_KERNEL, evaluateLeadVectorCase, leadVectorFile } from '../src/lead/test-vectors.js';
import { VECTOR_KERNELS, evaluateVectorCase, vectorFile } from '../src/planning/test-vectors.js';

const VECTORS = fileURLToPath(new URL('../../../packages/test-vectors/planning/', import.meta.url));
const DEFAULT_RELATIVE = 1e-9;

interface VectorFile {
  kernel: string;
  version: number;
  tolerance?: { relative?: number };
  cases: { id: string; input: unknown; expected: { kernel: unknown; service: unknown } }[];
}

function readVectors(kernel: string): VectorFile {
  return JSON.parse(readFileSync(`${VECTORS}${kernel}.json`, 'utf8')) as VectorFile;
}

function isRecord(value: unknown): value is Record<string, unknown> {
  return typeof value === 'object' && value !== null && !Array.isArray(value);
}

/** Integer-valued expected numbers are exact; every other number is within the relative tolerance (1e-9 by default). */
function assertMatch(actual: unknown, expected: unknown, path: string, relative: number): void {
  if (expected === null) {
    expect(actual, `${path}: null stands for infinity`).toBeNull();
    return;
  }
  if (Array.isArray(expected)) {
    expect(Array.isArray(actual), `${path}: expected an array`).toBe(true);
    const values = actual as unknown[];
    expect(values.length, `${path}: length`).toBe(expected.length);
    expected.forEach((value, i) => assertMatch(values[i], value, `${path}[${i}]`, relative));
    return;
  }
  if (isRecord(expected)) {
    expect(isRecord(actual), `${path}: expected an object`).toBe(true);
    const value = actual as Record<string, unknown>;
    expect(Object.keys(value).sort(), `${path}: fields`).toEqual(Object.keys(expected).sort());
    for (const [key, child] of Object.entries(expected)) assertMatch(value[key], child, `${path}.${key}`, relative);
    return;
  }
  if (typeof expected === 'number') {
    expect(typeof actual, `${path}: expected a number`).toBe('number');
    const value = actual as number;
    if (Number.isInteger(expected)) {
      expect(value, path).toBe(expected);
      return;
    }
    const difference = Math.abs(value - expected);
    const scale = Math.max(Math.abs(value), Math.abs(expected), 1);
    expect(difference, `${path}: ${value} is not within ${relative} of ${expected}`).toBeLessThanOrEqual(relative * scale);
    return;
  }
  expect(actual, path).toEqual(expected);
}

describe('planning golden vectors', () => {
  function checkVectors(kernel: string, expectedFile: VectorFile, evaluate: (input: unknown) => { kernel: unknown; service: unknown }) {
    const file = readVectors(kernel);
    expect(file, `${kernel}.json is stale; regenerate with src/scripts/write-planning-vectors.ts`).toEqual(expectedFile);
    expect(file.kernel).toBe(kernel);
    expect(file.version).toBe(1);
    expect(file.cases.length).toBeGreaterThan(0);
    const ids = file.cases.map((c) => c.id);
    expect(new Set(ids).size, 'case ids are unique').toBe(ids.length);
    const relative = file.tolerance?.relative ?? DEFAULT_RELATIVE;
    for (const vector of file.cases) {
      const actual = evaluate(vector.input);
      assertMatch(actual.kernel, vector.expected.kernel, `${kernel}/${vector.id}/kernel`, relative);
      assertMatch(actual.service, vector.expected.service, `${kernel}/${vector.id}/service`, relative);
    }
  }

  for (const kernel of VECTOR_KERNELS) {
    it(`${kernel} matches the TypeScript kernels`, () => {
      checkVectors(kernel, vectorFile(kernel), (input) => evaluateVectorCase(kernel, input));
    });
  }

  it(`${LEAD_VECTOR_KERNEL} matches the TypeScript kernel`, () => {
    checkVectors(LEAD_VECTOR_KERNEL, leadVectorFile(), evaluateLeadVectorCase);
  });
});
