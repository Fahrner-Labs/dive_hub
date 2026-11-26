#!/usr/bin/env node
// The license scan (ADR 0086, ADR 0006): every production dependency must be permissively licensed, and Garmin's
// FIT SDK must never be one of them. Reads `pnpm -r licenses list --prod --json`; `--report <file>` reads a report
// from a file instead, which is how the failing case is tested.
import { spawnSync } from 'node:child_process';
import { readFileSync } from 'node:fs';
import { pathToFileURL } from 'node:url';

/**
 * The SPDX identifiers we accept for production dependencies: permissive licenses that let a self-hoster run,
 * modify and redistribute the image. Anything else must be looked at before it ships; a new one is added here on
 * purpose (ADR 0086).
 */
export const PERMISSIVE = new Set([
  '0BSD',
  'Apache-2.0',
  'BSD-2-Clause',
  'BSD-3-Clause',
  'BlueOak-1.0.0',
  'CC0-1.0',
  'ISC',
  'MIT',
  'MIT-0',
  'OFL-1.1',
  'Python-2.0',
  'Unlicense',
  'WTFPL',
  'Zlib',
]);

/** A license expression's terms, with the outer parentheses and `+` suffixes of SPDX expressions removed. */
const terms = (expression) => expression.trim().replace(/^\((.*)\)$/, '$1');

/**
 * Whether an SPDX license expression is permissive. An `OR` expression is fine when one of its branches is (the
 * consumer may choose it); an `AND` expression only when every term is. Unknown identifiers are not permissive.
 */
export function isPermissive(expression) {
  if (typeof expression !== 'string') return false;
  const text = terms(expression);
  const or = text.split(/\s+OR\s+/);
  if (or.length > 1) return or.some(isPermissive);
  const and = text.split(/\s+AND\s+/);
  if (and.length > 1) return and.every(isPermissive);
  return PERMISSIVE.has(text.replace(/\+$/, ''));
}

/** Garmin's FIT SDK is a dev dependency only: its license forbids redistributing it in the image (ADR 0006). */
export const FORBIDDEN = '@garmin/fitsdk';

/**
 * What is wrong with a `pnpm -r licenses list --prod --json` report: one line per non-permissive license with the
 * packages under it, the forbidden FIT SDK if it appears, and a warning when the report has no packages at all
 * (dependencies not installed), which would otherwise pass silently.
 */
export function licenseProblems(report) {
  const problems = [];
  let packages = 0;
  for (const [license, entries] of Object.entries(report)) {
    packages += entries.length;
    const names = entries.map(({ name, versions }) => (versions?.length ? `${name}@${versions.join(',')}` : name));
    if (entries.some(({ name }) => name === FORBIDDEN)) {
      problems.push(`${FORBIDDEN} is a production dependency; its license forbids redistributing it (ADR 0006)`);
    }
    if (entries.length > 0 && !isPermissive(license)) {
      problems.push(`non-permissive license ${license}: ${names.join(', ')}`);
    }
  }
  if (packages === 0) {
    problems.push('the report lists no production dependencies; run pnpm install before the license scan');
  }
  return problems;
}

/** Reads the production license report from pnpm (or from `--report <file>`). */
export function readReport(args) {
  const at = args.indexOf('--report');
  if (at !== -1) {
    const file = args[at + 1];
    if (!file) throw new Error('--report needs a file');
    return JSON.parse(readFileSync(file, 'utf8'));
  }
  return JSON.parse(execPnpm());
}

/** `pnpm -r licenses list --prod --json`, through the shell so it also works where pnpm is a `.cmd` (Windows). */
function execPnpm() {
  const { stdout, stderr, status } = spawnSync('pnpm -r licenses list --prod --json', { encoding: 'utf8', shell: true, maxBuffer: 64 * 1024 * 1024 });
  if (status !== 0) throw new Error((stderr || stdout || `pnpm exited with ${status}`).trim());
  return stdout;
}

/** The CLI: exit 0 when every production dependency is permissive and the FIT SDK is absent, else 1. */
function main(args) {
  let report;
  try {
    report = readReport(args);
  } catch (error) {
    console.error(`::error::license-scan: could not read the production license report: ${error.message}`);
    process.exit(1);
  }
  const problems = licenseProblems(report);
  if (problems.length > 0) {
    for (const problem of problems) console.error(`::error::license-scan: ${problem}`);
    process.exit(1);
  }
  const count = Object.values(report).reduce((sum, entries) => sum + entries.length, 0);
  console.log(`license-scan: ${count} production dependencies, all permissive; ${FORBIDDEN} absent`);
}

if (process.argv[1] && import.meta.url === pathToFileURL(process.argv[1]).href) main(process.argv.slice(2));
