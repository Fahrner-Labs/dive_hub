#!/usr/bin/env node
// The local run log of `pnpm check` (ADR 0023, amendment of 2026-10-26): one JSON line per run that ran steps, in
// `check-runs.jsonl` in git's common directory. `pnpm check:log [--last N] [--branch <name>]` reads it.
// `node scripts/check-log.mjs --flaky|--failed` writes CI's job-summary section from Playwright's JSON report.
import { spawnSync } from 'node:child_process';
import { appendFileSync, existsSync, readFileSync } from 'node:fs';
import { resolve } from 'node:path';
import { fileURLToPath } from 'node:url';
import { REPORT, failedTestsFromReport, flakyTestsFromReport, localTime } from './check-lib.mjs';

/** The log's path: `check-runs.jsonl` in git's common directory. */
export const logPath = () => resolve(spawnSync('git rev-parse --git-common-dir', { encoding: 'utf8', shell: true }).stdout.trim(), 'check-runs.jsonl');

/** Appends `entry` as one line to the log at `path`; false (never a throw) when it cannot be written. */
export function appendRun(entry, path) {
  try {
    appendFileSync(path, `${JSON.stringify(entry)}\n`);
    return true;
  } catch {
    return false;
  }
}

/** The tests of a Playwright JSON report: count, summed run time, the failed ones (`--test-list` lines), the 5 slowest. */
export function browserStats(report) {
  const timed = [];
  const walk = (suite, describes) => {
    for (const spec of suite.specs ?? []) {
      const ms = (spec.tests ?? []).flatMap((t) => t.results ?? []).reduce((sum, r) => sum + (r.duration ?? 0), 0);
      timed.push({ test: [spec.file, ...describes, spec.title].join(' › '), seconds: ms / 1000 });
    }
    for (const child of suite.suites ?? []) walk(child, [...describes, child.title]);
  };
  for (const fileSuite of report?.suites ?? []) walk(fileSuite, []);
  const round = (n) => Math.round(n * 10) / 10;
  return {
    tests: timed.length,
    workerSeconds: round(timed.reduce((sum, t) => sum + t.seconds, 0)),
    failed: failedTestsFromReport(report),
    slowest: timed.sort((a, b) => b.seconds - a.seconds).slice(0, 5).map(({ test, seconds }) => ({ test, seconds: round(seconds) })),
  };
}

const where = (run) => run.branch ?? `(detached ${run.head})`;

/**
 * The job summary's flaky-test table (ADR 0023, amendment 2026-11-12): Playwright knows which cases failed and then
 * passed on a retry. `report` is Playwright's JSON report; `null` means none was written, as when an earlier step
 * failed. Markdown, so CI appends it to `$GITHUB_STEP_SUMMARY`.
 */
export function flakySummary(report) {
  if (!report) return '### Flaky browser tests\n\nNo browser test report: an earlier step failed or the browser step did not run.';
  const flaky = flakyTestsFromReport(report);
  const stats = report.stats;
  const total = stats ? stats.expected + stats.unexpected + stats.flaky + stats.skipped : browserStats(report).tests;
  if (flaky.length === 0) return `### Flaky browser tests\n\nNone of the ${total} browser test${total === 1 ? '' : 's'} passed only on a retry.`;
  const cell = (text) => String(text).replaceAll('|', '\\|');
  return [
    '### Flaky browser tests',
    '',
    `${flaky.length} of the ${total} browser test${total === 1 ? '' : 's'} passed only on a retry:`,
    '',
    '| Browser test | Passed on attempt | Earlier attempts |',
    '|---|---:|---|',
    ...flaky.map((test) => `| \`${cell(test.line)}\` | ${test.passedOnAttempt} | ${cell(test.failures.join('; ')) || '-'} |`),
  ].join('\n');
}

/**
 * The job summary's failed-test list: the cases that failed every attempt (a retry that passes makes a case flaky,
 * not failed). Playwright's report, not its output folders, which also hold the failed first attempt of a flaky case.
 * `null` means no report was written, as when an earlier step failed.
 */
export function failedSummary(report) {
  if (!report) return '### Failed browser tests\n\nNo browser test report: another step failed.';
  const failed = failedTestsFromReport(report);
  if (failed.length === 0) return '### Failed browser tests\n\nNone: no browser test failed every attempt.';
  return ['### Failed browser tests', '', ...failed.map((line) => `- \`${line}\``)].join('\n');
}

/** The last runs, one line each, then per branch the runs, the failed ones and the total wall time. */
export function summarise(entries, { last = 20, branch } = {}) {
  const runs = branch ? entries.filter((run) => run.branch === branch) : entries;
  if (branch && runs.length === 0) return `no check runs on ${branch}`;
  const lines = runs.slice(-last).map((run) => {
    const slowest = [...(run.steps ?? [])].sort((a, b) => b.seconds - a.seconds)[0];
    const time = localTime(new Date(run.at));
    return `${time}  ${where(run)}  ${run.by}  ${run.result}  ${Math.round(run.wall)} s  slowest: ${slowest ? `${slowest.step} ${Math.round(slowest.seconds)} s` : '-'}`;
  });
  const byBranch = new Map();
  for (const run of runs) {
    const sum = byBranch.get(where(run)) ?? { runs: 0, failed: 0, wall: 0 };
    byBranch.set(where(run), { runs: sum.runs + 1, failed: sum.failed + (run.result === 'failed' ? 1 : 0), wall: sum.wall + run.wall });
  }
  const totals = [...byBranch].map(([name, s]) => `${name}: ${s.runs} run${s.runs === 1 ? '' : 's'}, ${s.failed} failed, ${Math.round(s.wall)} s`);
  return [...lines, '', ...totals].join('\n');
}

function main() {
  const args = process.argv.slice(2);
  if (args.includes('--flaky') || args.includes('--failed')) {
    // A report that exists but cannot be read should fail the step, not be reported as a missing one.
    const report = existsSync(REPORT) ? JSON.parse(readFileSync(REPORT, 'utf8')) : null;
    return console.log(args.includes('--flaky') ? flakySummary(report) : failedSummary(report));
  }
  const value = (flag) => (args.includes(flag) ? args[args.indexOf(flag) + 1] : undefined);
  const path = logPath();
  if (!existsSync(path)) return console.log('no check runs logged yet');
  const entries = readFileSync(path, 'utf8').split('\n').filter(Boolean).flatMap((line) => {
    try { return [JSON.parse(line)]; } catch { return []; }
  });
  const last = value('--last') ? Number(value('--last')) : 20;
  if (entries.length === 0) return console.log('no check runs logged yet');
  console.log(summarise(entries, { last, branch: value('--branch') }));
}

if (process.argv[1] && resolve(process.argv[1]) === fileURLToPath(import.meta.url)) main();
