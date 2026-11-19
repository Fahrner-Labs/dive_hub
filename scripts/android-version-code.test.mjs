// Tests of the Android release tag parser and the versionCode it derives (ADR 0077 D4/D5).
// Run with: node --test scripts/
import assert from 'node:assert/strict';
import { spawnSync } from 'node:child_process';
import { test } from 'node:test';
import { fileURLToPath } from 'node:url';
import { androidVersionCode, parseAndroidTag } from './android-version-code.mjs';

const script = fileURLToPath(new URL('android-version-code.mjs', import.meta.url));
const run = (tag) => spawnSync(process.execPath, [script, tag], { encoding: 'utf8' });

test('the versionCode adds the components per ADR 0077 D5', () => {
  assert.equal(androidVersionCode(parseAndroidTag('android-v1.2.3')), 1 * 100000 + 2 * 1000 + 3 * 10 + 9);
  assert.equal(androidVersionCode(parseAndroidTag('android-v0.1.0')), 1009);
  assert.equal(androidVersionCode(parseAndroidTag('android-v0.1.0-rc2')), 1002);
});

test('a release candidate rises to its final', () => {
  const codes = ['android-v0.1.0-rc1', 'android-v0.1.0-rc2', 'android-v0.1.0-rc8', 'android-v0.1.0', 'android-v0.1.1-rc1', 'android-v0.1.1']
    .map((tag) => androidVersionCode(parseAndroidTag(tag)));
  assert.deepEqual(codes, [...codes].sort((a, b) => a - b));
});

test('a stable tag reports its versionName and versionCode', () => {
  const { status, stdout, stderr } = run('android-v1.2.3');
  assert.equal(status, 0, stderr);
  assert.deepEqual(stdout.trim().split('\n'), [
    'tag=android-v1.2.3',
    'versionName=1.2.3',
    'versionCode=102039',
    'prerelease=false',
  ]);
});

test('a release candidate keeps its suffix in versionName', () => {
  const { status, stdout, stderr } = run('android-v0.1.0-rc4');
  assert.equal(status, 0, stderr);
  assert.deepEqual(stdout.trim().split('\n'), [
    'tag=android-v0.1.0-rc4',
    'versionName=0.1.0-rc4',
    'versionCode=1004',
    'prerelease=true',
  ]);
});

test('a tag whose components overflow the formula is refused', () => {
  for (const tag of ['android-v0.1.0-rc9', 'android-v0.1.10', 'android-v0.100.0']) {
    const { status, stdout, stderr } = run(tag);
    assert.equal(status, 1, tag);
    assert.equal(stdout, '');
    assert.match(stderr, /::error::.*ADR 0077 D5/, tag);
  }
});

const rejected = ['android-v1.2.3-rc0', 'android-v1.2.3-rc01', 'android-v1.2.3-rc.1', 'android-v1.2.3-alpha', 'android-v1.2', 'android-v1.2.3.4', 'v1.2.3', 'android-1.2.3', ''];
for (const tag of rejected) {
  test(`rejects '${tag}' and names both accepted forms`, () => {
    const { status, stdout, stderr } = run(tag);
    assert.equal(status, 1);
    assert.equal(stdout, '');
    assert.match(stderr, /::error::/);
    assert.match(stderr, /android-vX\.Y\.Z/);
    assert.match(stderr, /android-vX\.Y\.Z-rcN/);
  });
}
