// Tests of the check's run log (the local log `pnpm check` appends to and `pnpm check:log` reads).
// Run with: node --test scripts/
import assert from 'node:assert/strict';
import { mkdtempSync, readFileSync, rmSync } from 'node:fs';
import { tmpdir } from 'node:os';
import { join } from 'node:path';
import { test } from 'node:test';
import { localTime } from './check-lib.mjs';
import { appendRun, browserStats, failedSummary, flakySummary, summarise } from './check-log.mjs';

const entry = (over = {}) => ({
  at: '2026-10-26T10:00:00.000Z', branch: 'main', head: 'abc1234', by: 'person', args: [], wall: 60, result: 'passed',
  steps: [{ step: 'typecheck', from: 'main', changed: 2, plan: true, seconds: 10, status: 0 }, { step: 'e2e', from: 'main', changed: 2, plan: 'dives', seconds: 50, status: 0 }],
  ...over,
});

test('a run is one JSON line appended', (t) => {
  const dir = mkdtempSync(join(tmpdir(), 'check-log-'));
  t.after(() => rmSync(dir, { recursive: true, force: true }));
  const path = join(dir, 'check-runs.jsonl');
  assert.equal(appendRun(entry({ head: 'one' }), path), true);
  assert.equal(appendRun(entry({ head: 'two' }), path), true);
  const lines = readFileSync(path, 'utf8').trimEnd().split('\n');
  assert.deepEqual(lines.map((line) => JSON.parse(line).head), ['one', 'two']);
});

test('a log that cannot be written leaves the check alone', (t) => {
  const dir = mkdtempSync(join(tmpdir(), 'check-log-'));
  t.after(() => rmSync(dir, { recursive: true, force: true }));
  assert.equal(appendRun(entry(), join(dir, 'missing', 'check-runs.jsonl')), false);
});

test('the browser stats come from the report', () => {
  const spec = (title, ms, status) => ({ title, file: 'a.spec.ts', tests: [{ results: [{ status, duration: ms }] }] });
  const report = { suites: [{ title: 'a.spec.ts', file: 'a.spec.ts', specs: [spec('one', 1000, 'passed'), spec('two', 2500, 'failed'), spec('three', 500, 'passed')] }] };
  const stats = browserStats(report);
  assert.equal(stats.tests, 3);
  assert.equal(stats.workerSeconds, 4);
  assert.deepEqual(stats.failed, ['a.spec.ts › two']);
  assert.equal(stats.slowest[0].seconds, 2.5);
  assert.equal(stats.slowest[0].test, 'a.spec.ts › two');
});

test('the flaky summary is a job-summary table of what passed on a retry', () => {
  const attempt = (status, retry, message) => ({ status, retry, duration: 100, error: message ? { message } : undefined });
  const report = {
    stats: { expected: 1, unexpected: 0, flaky: 1, skipped: 0 },
    suites: [{ title: 'a.spec.ts', file: 'a.spec.ts', specs: [
      { file: 'a.spec.ts', title: 'one | two', tests: [{ status: 'flaky', results: [attempt('failed', 0, 'expect(x)\n\ndetail'), attempt('passed', 1)] }] },
      { file: 'a.spec.ts', title: 'three', tests: [{ status: 'expected', results: [attempt('passed', 0)] }] },
    ] }],
  };
  const text = flakySummary(report);
  assert.match(text, /### Flaky browser tests/);
  assert.match(text, /1 of the 2 browser tests passed only on a retry:/);
  assert.match(text, /\| Browser test \| Passed on attempt \| Earlier attempts \|/);
  assert.match(text, /\| `a.spec.ts › one \\\| two` \| 2 \| failed: expect\(x\) \|/);
  assert.match(flakySummary({ stats: { expected: 1, unexpected: 0, flaky: 0, skipped: 0 }, suites: [] }), /None of the 1 browser test passed only on a retry\./);
  assert.match(flakySummary(null), /No browser test report/);
});

test('the failed summary lists only the tests that failed every attempt', () => {
  const attempt = (status, retry) => ({ status, retry, duration: 100 });
  const report = { suites: [{ title: 'a.spec.ts', file: 'a.spec.ts', specs: [
    { file: 'a.spec.ts', title: 'one', tests: [{ status: 'flaky', results: [attempt('failed', 0), attempt('passed', 1)] }] },
    { file: 'a.spec.ts', title: 'two', tests: [{ status: 'unexpected', results: [attempt('failed', 0), attempt('failed', 1)] }] },
  ] }] };
  const text = failedSummary(report);
  assert.match(text, /### Failed browser tests/);
  assert.match(text, /- `a.spec.ts › two`/);
  assert.doesNotMatch(text, /a.spec.ts › one/);
  assert.match(failedSummary({ suites: [{ title: 'a.spec.ts', file: 'a.spec.ts', specs: [report.suites[0].specs[0]] }] }), /None: no browser test failed every attempt\./);
  assert.match(failedSummary(null), /No browser test report/);
});

test('the summary lists the last runs and sums each branch', () => {
  const entries = [
    entry({ branch: 'main', wall: 30 }),
    entry({ branch: 'feature', wall: 100, result: 'failed', at: '2026-10-26T11:00:00.000Z' }),
    entry({ branch: 'feature', wall: 50, by: 'hook', at: '2026-10-26T12:00:00.000Z' }),
  ];
  const text = summarise(entries);
  assert.equal(text.split('\n').filter((line) => line.includes('e2e')).length, 3); // slowest step per run
  assert.match(text, /feature: 2 runs, 1 failed, 150 s/);
  assert.match(text, /main: 1 run, 0 failed, 30 s/);
  const only = summarise(entries, { branch: 'main' });
  assert.doesNotMatch(only, /feature/);
  assert.equal(summarise(entries, { last: 1 }).split('\n').filter((line) => line.includes('e2e')).length, 1);
});

test('a detached run shows no branch name', () => {
  assert.match(summarise([entry({ branch: null, head: 'deadbee' })]), /deadbee/);
});

test('the time column is local time', () => {
  const at = '2026-10-26T23:30:00.000Z';
  assert.ok(summarise([entry({ at })]).startsWith(localTime(new Date(at))));
});

test('a branch without runs says so', () => {
  assert.equal(summarise([entry({ branch: 'main' })], { branch: 'other' }), 'no check runs on other');
});
