// Tests of the repo map's parsing, ranking and freshness check (ADR 0067, layer 1).
// Run with: node --test scripts/repomap.test.mjs
import assert from 'node:assert/strict';
import { spawnSync } from 'node:child_process';
import { mkdtempSync, mkdirSync, readFileSync, rmSync, writeFileSync } from 'node:fs';
import { tmpdir } from 'node:os';
import { dirname, join } from 'node:path';
import { test } from 'node:test';
import { fileURLToPath } from 'node:url';
import { REPO_MAP_STALE } from './check-lib.mjs';
import { STALE_EXIT, checkRepoMap, generateRepoMap, moduleOf, sourceFiles } from './repomap.mjs';

const SCRIPT = fileURLToPath(new URL('./repomap.mjs', import.meta.url));
test('the producer and the gate agree on the stale exit code', () => {
  assert.equal(STALE_EXIT, REPO_MAP_STALE);
});

const FILES = {
  'apps/server/src/dives/dive-service.ts': `export function lockManagedDive() {}
export function usedOnce() {}
function localHelper() {}
export interface Dive { id: string }
export const DIVE_LIMIT = 3;
`,
  'apps/server/src/dives/continuation.ts': `import { lockManagedDive, Dive } from './dive-service';
export function createContinuationService(dive: Dive) { lockManagedDive(); lockManagedDive(); return dive; }
`,
  'apps/server/src/db/schema.ts': `export function participantsOf() {}
export type DiveRow = { id: string };
`,
  'apps/web/src/App.tsx': `import { createContinuationService } from '../../server/src/dives/continuation';
export function App() { return <main>{createContinuationService}</main>; }
`,
  'apps/server/src/dives/global.d.ts': 'declare const fromDeclaration: string;\n',
  'apps/server/src/dives/dive-service.test.ts': 'export function testOnly() {}\n',
  'apps/web/src/Thing.spec.tsx': 'export function specOnly() {}\n',
  'packages/api-client/src/index.ts': 'export function generatedClient() {}\n',
};

/** A throwaway checkout with the fixture sources; `t.after` removes it. */
function fixture(t, files = FILES) {
  const root = mkdtempSync(join(tmpdir(), 'repomap-'));
  t.after(() => rmSync(root, { recursive: true, force: true }));
  for (const [path, text] of Object.entries(files)) {
    mkdirSync(dirname(join(root, path)), { recursive: true });
    writeFileSync(join(root, path), text);
  }
  return root;
}

test('sourceFiles reads the two app roots, skipping generated packages, declarations and tests', (t) => {
  const root = fixture(t);
  assert.deepEqual(sourceFiles(root), [
    'apps/server/src/db/schema.ts',
    'apps/server/src/dives/continuation.ts',
    'apps/server/src/dives/dive-service.ts',
    'apps/web/src/App.tsx',
  ]);
});

test('moduleOf groups a file under its top-level module folder', () => {
  assert.equal(moduleOf('apps/server/src/dives/dive-service.ts'), 'server/dives');
  assert.equal(moduleOf('apps/server/src/app.ts'), 'server/(root)');
  assert.equal(moduleOf('apps/web/src/lib/live.ts'), 'web/lib');
});

test('the map lists each module and ranks widely referenced symbols first', (t) => {
  const root = fixture(t);
  const { text } = generateRepoMap(root, { perModule: 20 });
  assert.match(text, /^# Repo map/);
  assert.match(text, /^### server\/dives$/m);
  const dives = text.split('\n### ').find((section) => section.startsWith('server/dives\n')) ?? '';
  assert.match(dives, /fn lockManagedDive/);
  assert.match(dives, /fn usedOnce/);
  assert.match(dives, /iface Dive/);
  assert.doesNotMatch(dives, /generatedClient/);
  // lockManagedDive is imported and called from another file; usedOnce never leaves its own file.
  assert.ok(dives.indexOf('lockManagedDive') < dives.indexOf('usedOnce'));
  // The web root shows its own module, not a merged one.
  assert.match(text, /^### web\/\(root\)$/m);
});

test('the render is deterministic', (t) => {
  const root = fixture(t);
  assert.equal(generateRepoMap(root).text, generateRepoMap(root).text);
});

test('the freshness check passes on the generated map and fails when a source changes', (t) => {
  const root = fixture(t);
  assert.equal(checkRepoMap(root).ok, false);
  const { text } = generateRepoMap(root);
  mkdirSync(join(root, 'docs/generated'), { recursive: true });
  writeFileSync(join(root, 'docs/generated/REPOMAP.md'), text);
  assert.equal(readFileSync(join(root, 'docs/generated/REPOMAP.md'), 'utf8'), text);
  assert.equal(checkRepoMap(root).ok, true);
  writeFileSync(join(root, 'apps/server/src/db/schema.ts'), 'export function participantsOfDives() {}\n');
  const stale = checkRepoMap(root);
  assert.equal(stale.ok, false);
  assert.match(stale.reason, /stale/);
});

test('--check exits 2 for a missing or stale map and 0 for a fresh one', (t) => {
  const root = fixture(t);
  const run = () => spawnSync(process.execPath, [SCRIPT, '--check'], { cwd: root, encoding: 'utf8' });
  assert.equal(run().status, REPO_MAP_STALE, 'missing map');
  const { text } = generateRepoMap(root);
  mkdirSync(join(root, 'docs/generated'), { recursive: true });
  writeFileSync(join(root, 'docs/generated/REPOMAP.md'), text);
  assert.equal(run().status, 0, 'fresh map');
  writeFileSync(join(root, 'apps/server/src/db/schema.ts'), 'export function changed() {}\n');
  assert.equal(run().status, REPO_MAP_STALE, 'stale map');
});

test('a module with more symbols than the per-module limit says how many are left out', (t) => {
  const many = Object.fromEntries(
    Array.from({ length: 6 }, (_, index) => [`apps/server/src/dives/symbol-${index}.ts`, `export function symbol${index}() {}\n`]),
  );
  const root = fixture(t, many);
  const { text } = generateRepoMap(root, { perModule: 2 });
  const dives = text.split('\n### ').find((section) => section.startsWith('server/dives\n')) ?? '';
  assert.equal(dives.match(/fn symbol/g).length, 2);
  assert.match(dives, /4 more symbols/);
});

test('the real map stays inside the ADR 0067 budget of about 4k tokens', () => {
  const root = fileURLToPath(new URL('..', import.meta.url));
  const { text } = generateRepoMap(root);
  // About four characters per token, so ~16k characters is the 4k-token ceiling the ADR asks for.
  assert.ok(text.length <= 16_000, `the map is ${text.length} characters, over the ~4k-token budget`);
});

test('the whole map is a snapshot: format, order and the module headings', (t) => {
  const root = fixture(t);
  assert.equal(generateRepoMap(root, { perModule: 20 }).text, `# Repo map

> Generated by \`node scripts/repomap.mjs\` from \`apps/server/src\` and \`apps/web/src\`; do not edit by hand.
> \`pnpm check\` fails when this map is stale. Symbols are ranked by how widely the code references them;
> each module shows its most-referenced ones (exported symbols rank higher). Search here before walking the tree.

Files: 4 · symbols: 9 · shown: 9

### server/db
apps/server/src/db/schema.ts:1 fn participantsOf
apps/server/src/db/schema.ts:2 type DiveRow

### server/dives
apps/server/src/dives/dive-service.ts:1 fn lockManagedDive
apps/server/src/dives/continuation.ts:2 fn createContinuationService
apps/server/src/dives/dive-service.ts:4 iface Dive
apps/server/src/dives/dive-service.ts:2 fn usedOnce
apps/server/src/dives/dive-service.ts:5 const DIVE_LIMIT
apps/server/src/dives/dive-service.ts:3 fn localHelper

### web/(root)
apps/web/src/App.tsx:2 fn App
`);
});
