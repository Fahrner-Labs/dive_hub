// Tests of the Android lane's --review mode, which collects the goldens for the design critique gate (FAH-359).
// Run with: node --test scripts/
import assert from 'node:assert/strict';
import { existsSync, mkdirSync, mkdtempSync, readFileSync, rmSync, writeFileSync } from 'node:fs';
import { tmpdir } from 'node:os';
import { dirname, join } from 'node:path';
import { test } from 'node:test';
import { parseReviewArgs, reviewGoldens } from './check-android.mjs';

/** A folder holding `files` (path → content), removed after the test. */
function fixture(t, files) {
  const root = mkdtempSync(join(tmpdir(), 'check-android-'));
  t.after(() => rmSync(root, { recursive: true, force: true }));
  for (const [path, content] of Object.entries(files)) {
    mkdirSync(dirname(join(root, path)), { recursive: true });
    writeFileSync(join(root, path), content);
  }
  return root;
}

test('--review copies the goldens into the review output, sorted, filtered when asked', (t) => {
  const root = fixture(t, {
    'apps/android/app/src/test/screenshots/logbook_light.png': 'a',
    'apps/android/app/src/test/screenshots/logbook_dark.png': 'b',
    'apps/android/app/src/test/screenshots/dive_detail.png': 'c',
    // Left over from an earlier run: must not survive into this one.
    'apps/android/review-output/stale.png': 'old',
  });

  const all = reviewGoldens({ root });
  assert.deepEqual(all.copied, [
    'apps/android/review-output/dive_detail.png',
    'apps/android/review-output/logbook_dark.png',
    'apps/android/review-output/logbook_light.png',
  ]);
  assert.equal(all.outDir, 'apps/android/review-output');
  assert.equal(readFileSync(join(root, all.copied[2]), 'utf8'), 'a');
  assert.equal(existsSync(join(root, 'apps/android/review-output/stale.png')), false);

  const filtered = reviewGoldens({ root, filter: 'logbook' });
  assert.deepEqual(filtered.copied, ['apps/android/review-output/logbook_dark.png', 'apps/android/review-output/logbook_light.png']);
  // A filtered run clears what the unfiltered one left: nothing stale is critiqued.
  assert.equal(existsSync(join(root, 'apps/android/review-output/dive_detail.png')), false);
});

test('--review with no goldens, or none matching, copies nothing', (t) => {
  const empty = fixture(t, {});
  assert.deepEqual(reviewGoldens({ root: empty }).copied, []);

  const root = fixture(t, { 'apps/android/app/src/test/screenshots/dive_detail.png': 'c' });
  assert.deepEqual(reviewGoldens({ root, filter: 'logbook' }).copied, []);
});

test('--review arguments: nothing or --filter <name part>, everything else an error', () => {
  assert.deepEqual(parseReviewArgs([]), { filter: undefined });
  assert.deepEqual(parseReviewArgs(['--filter', 'logbook']), { filter: 'logbook' });

  for (const args of [
    ['--bogus'],
    ['logbook'],
    ['--filter'],
    // An empty value, as from an unset shell variable, is not a name part either.
    ['--filter', ''],
    ['--filter', '  '],
    // A `--`-prefixed value is a mistyped option, not a name part.
    ['--filter', '--bogus'],
    // Leftovers never pass silently.
    ['--filter', 'logbook', 'junk'],
    ['--review'],
  ]) {
    assert.match(parseReviewArgs(args).error, /.+/u, JSON.stringify(args));
  }
});
