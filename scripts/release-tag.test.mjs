// Tests of the release tag parser: stable `vX.Y.Z` and prerelease `vX.Y.Z-rcN` (ADR 0068, FAH-243).
// Run with: node --test scripts/
import assert from 'node:assert/strict';
import { spawnSync } from 'node:child_process';
import { test } from 'node:test';
import { fileURLToPath } from 'node:url';

const script = fileURLToPath(new URL('release-tag.mjs', import.meta.url));
const run = (tag) => spawnSync(process.execPath, [script, tag], { encoding: 'utf8' });

test('a stable tag reports its version and no prerelease', () => {
  const { status, stdout, stderr } = run('v1.2.3');
  assert.equal(status, 0, stderr);
  assert.deepEqual(stdout.trim().split('\n'), [
    'tag=v1.2.3',
    'version=1.2.3',
    'baseVersion=1.2.3',
    'major=1',
    'minor=2',
    'prerelease=false',
  ]);
});

test('a release candidate reports the base version as a prerelease', () => {
  const { status, stdout, stderr } = run('v1.2.3-rc4');
  assert.equal(status, 0, stderr);
  assert.deepEqual(stdout.trim().split('\n'), [
    'tag=v1.2.3-rc4',
    'version=1.2.3',
    'baseVersion=1.2.3',
    'major=1',
    'minor=2',
    'prerelease=true',
  ]);
});

const rejected = ['v1.2.3-rc0', 'v1.2.3-rc01', 'v1.2.3-rc.1', 'v1.2.3-alpha', '1.2.3', 'v1.2', 'v1.2.3.4', ''];
for (const tag of rejected) {
  test(`rejects '${tag}' and names both accepted forms`, () => {
    const { status, stdout, stderr } = run(tag);
    assert.equal(status, 1);
    assert.equal(stdout, '');
    assert.match(stderr, /::error::/);
    assert.match(stderr, /vX\.Y\.Z/);
    assert.match(stderr, /vX\.Y\.Z-rcN/);
  });
}
