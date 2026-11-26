// Tests of the production license scan (ADR 0086): a non-permissive dependency fails the scan, and so does Garmin's
// FIT SDK if it ever becomes a production dependency (ADR 0006).
// Run with: node --test scripts/
import assert from 'node:assert/strict';
import { spawnSync } from 'node:child_process';
import { mkdtempSync, rmSync, writeFileSync } from 'node:fs';
import { tmpdir } from 'node:os';
import { join } from 'node:path';
import { test } from 'node:test';
import { fileURLToPath } from 'node:url';
import { FORBIDDEN, isPermissive, licenseProblems } from './license-scan.mjs';

const script = fileURLToPath(new URL('license-scan.mjs', import.meta.url));
const dir = mkdtempSync(join(tmpdir(), 'license-scan-'));
process.on('exit', () => rmSync(dir, { recursive: true, force: true }));

/** Runs the CLI against a report written to a file. */
function run(report) {
  const file = join(dir, `${Math.random().toString(36).slice(2)}.json`);
  writeFileSync(file, JSON.stringify(report));
  return spawnSync(process.execPath, [script, '--report', file], { encoding: 'utf8' });
}

const pkg = (name, ...versions) => ({ name, versions: versions.length > 0 ? versions : ['1.0.0'] });

test('a report of permissive licenses passes', () => {
  const { status, stdout, stderr } = run({
    MIT: [pkg('react')],
    'Apache-2.0': [pkg('fastify')],
    '(MIT OR Apache-2.0)': [pkg('qr')],
    'OFL-1.1': [pkg('@fontsource/poppins')],
  });
  assert.equal(status, 0, stderr);
  assert.match(stdout, /4 production dependencies, all permissive/);
});

test('a non-permissive dependency fails the scan', () => {
  const { status, stdout, stderr } = run({
    MIT: [pkg('react')],
    'GPL-3.0-only': [pkg('copyleft-lib', '2.0.0')],
  });
  assert.equal(status, 1);
  assert.equal(stdout, '');
  assert.match(stderr, /::error::license-scan: non-permissive license GPL-3\.0-only: copyleft-lib@2\.0\.0/);
});

test('an unknown license fails the scan', () => {
  assert.equal(run({ Unknown: [pkg('mystery')] }).status, 1);
});

test("Garmin's FIT SDK fails the scan when it is a production dependency", () => {
  const { status, stderr } = run({ MIT: [pkg(FORBIDDEN, '21.217.0')] });
  assert.equal(status, 1);
  assert.match(stderr, /@garmin\/fitsdk is a production dependency/);
});

test('an empty report fails instead of passing silently', () => {
  const { status, stderr } = run({});
  assert.equal(status, 1);
  assert.match(stderr, /no production dependencies/);
});

test('an OR expression passes when a branch is permissive, an AND only when every term is', () => {
  assert.equal(isPermissive('(MIT OR GPL-3.0-only)'), true);
  assert.equal(isPermissive('MIT AND GPL-3.0-only'), false);
  assert.equal(isPermissive('Apache-2.0 OR MIT'), true);
  assert.equal(isPermissive('(MIT OR Apache-2.0)'), true);
  assert.equal(isPermissive('SEE LICENSE IN LICENSE.md'), false);
});

test('licenseProblems names each offending package once', () => {
  assert.deepEqual(
    licenseProblems({ MIT: [pkg('a')], 'GPL-2.0-only': [pkg('b'), pkg('c')] }),
    ['non-permissive license GPL-2.0-only: b@1.0.0, c@1.0.0'],
  );
});
