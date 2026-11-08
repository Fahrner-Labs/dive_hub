// Tests of the generated code graph's canned queries and staleness metadata (ADR 0067).
// Run with: node --test scripts/code-graph.test.mjs
import assert from 'node:assert/strict';
import { test } from 'node:test';
import { callersOf, dependentsOf, fileNode, metaFor, normalizeName, pathBetween, symbolNodes, verifyBoundaries, verifyGraph } from './code-graph.mjs';

const COMMIT = '0123456789abcdef0123456789abcdef01234567';

/** A tiny graph with the shape Graphify writes: nodes by id, links by source/target/relation. */
function example() {
  return {
    directed: false,
    built_at_commit: COMMIT,
    nodes: [
      { id: 'lock', label: 'lockManagedDive()', norm_label: 'lockmanageddive', file_type: 'code', source_file: 'apps/server/src/dives/dive-service.ts', source_location: 'L56' },
      { id: 'continuation', label: 'createContinuationService()', file_type: 'code', source_file: 'apps/server/src/dives/continuation/continuation-service.ts', source_location: 'L267' },
      { id: 'merging', label: 'createMergingService()', file_type: 'code', source_file: 'apps/server/src/dives/merging-service.ts', source_location: 'L166' },
      { id: 'comment', label: 'lockManagedDive()', file_type: 'concept', source_file: 'apps/server/src/dives/logbook-service.ts', source_location: 'L5' },
      { id: 'dive', label: 'dive-service.ts', file_type: 'code', source_file: 'apps/server/src/dives/dive-service.ts', source_location: 'L1' },
      { id: 'route', label: 'logbook-routes.ts', file_type: 'code', source_file: 'apps/server/src/dives/logbook-routes.ts', source_location: 'L1' },
      { id: 'read', label: 'read-service.ts', file_type: 'code', source_file: 'apps/server/src/mcp/read-service.ts', source_location: 'L1' },
      { id: 'schema', label: 'schema.ts', file_type: 'code', source_file: 'apps/server/src/db/schema.ts', source_location: 'L1' },
    ],
    links: [
      { source: 'continuation', target: 'lock', relation: 'calls' },
      { source: 'merging', target: 'lock', relation: 'calls' },
      { source: 'comment', target: 'lock', relation: 'references' },
      { source: 'dive', target: 'schema', relation: 'imports_from' },
      { source: 'route', target: 'schema', relation: 'imports_from' },
      { source: 'read', target: 'schema', relation: 'imports_from' },
      { source: 'dive', target: 'lock', relation: 'contains' },
    ],
  };
}

test('normalizeName resolves the exact-symbol spelling to Graphify’s norm_label', () => {
  assert.equal(normalizeName('lockManagedDive'), 'lockmanageddive');
  assert.equal(normalizeName('lockManagedDive()'), 'lockmanageddive');
  assert.equal(normalizeName('apps/server/src/db/schema.ts'), 'appsserversrcdbschemats');
});

test('symbolNodes finds a code node by exact name and ignores a concept that mentions it', () => {
  const found = symbolNodes(example(), 'lockManagedDive');
  assert.equal(found.length, 1);
  assert.equal(found[0].id, 'lock');
});

test('callersOf returns the call edges and ignores contains', () => {
  const graph = example();
  const [symbol] = symbolNodes(graph, 'lockManagedDive');
  const callers = callersOf(graph, symbol).map((node) => node.label);
  assert.deepEqual(callers.sort(), ['createContinuationService()', 'createMergingService()']);
});

test('dependentsOf returns the files that import the schema file, not the other way around', () => {
  const files = dependentsOf(example(), 'apps/server/src/db/schema.ts').map((node) => node.source_file);
  assert.deepEqual(files.sort(), [
    'apps/server/src/dives/dive-service.ts',
    'apps/server/src/dives/logbook-routes.ts',
    'apps/server/src/mcp/read-service.ts',
  ]);
});

test('pathBetween follows the import direction and has no path back', () => {
  const graph = example();
  const forward = pathBetween(graph, 'apps/server/src/dives/logbook-routes.ts', 'apps/server/src/db/schema.ts');
  assert.deepEqual(forward.map((node) => node.id), ['route', 'schema']);
  assert.equal(pathBetween(graph, 'apps/server/src/db/schema.ts', 'apps/server/src/dives/logbook-routes.ts'), null);
});

test('fileNode only takes the file node (L1), not a symbol in that file', () => {
  assert.equal(fileNode(example(), 'apps/server/src/dives/dive-service.ts').id, 'dive');
  assert.equal(fileNode(example(), 'apps/server/src/nope.ts'), null);
});

test('verifyGraph answers the three canned queries and checks the source commit', () => {
  const report = verifyGraph(example(), { expectSha: COMMIT });
  assert.deepEqual(report.problems, []);
  assert.equal(report.queries.filter((query) => query.ok).length, 4);
});

test('verifyGraph fails loudly when the source commit is not the one built', () => {
  const report = verifyGraph(example(), { expectSha: 'f'.repeat(40) });
  assert.equal(report.problems.length, 1);
  assert.match(report.problems[0], /built_at_commit/);
});

test('verifyGraph fails when a caller or a dependent disappears', () => {
  const graph = example();
  graph.links = graph.links.filter((link) => link.source !== 'merging' && link.source !== 'read');
  const report = verifyGraph(graph, { expectSha: COMMIT });
  assert.equal(report.problems.length, 2);
  assert.ok(report.problems.some((problem) => problem.includes('createMergingService()')));
  assert.ok(report.problems.some((problem) => problem.includes('read-service.ts')));
});

test('verifyBoundaries accepts a full boundary graph with no new violations', () => {
  const modules = Array.from({ length: 250 }, (_, index) => ({ source: `apps/server/src/module-${index}.ts` }));
  modules.push({ source: 'apps/server/src/db/schema.ts' });
  const report = verifyBoundaries({ modules, summary: { advisedExitCode: 0 } });
  assert.deepEqual(report.problems, []);
  assert.equal(report.queries.length, 2);
});

test('verifyBoundaries fails on new violations, an empty graph or a missing db schema', () => {
  const modules = Array.from({ length: 250 }, (_, index) => ({ source: `apps/server/src/module-${index}.ts` }));
  const withSchema = [...modules, { source: 'apps/server/src/db/schema.ts' }];
  assert.equal(verifyBoundaries({ modules: withSchema, summary: { advisedExitCode: 1 } }).problems.length, 1);
  assert.equal(verifyBoundaries({ modules: [], summary: { advisedExitCode: 0 } }).problems.length, 1);
  assert.equal(verifyBoundaries({ modules, summary: {} }).problems.length, 2);
});

test('metaFor names the artefact set and the source commit', () => {
  const meta = metaFor({ tool: 'graphify 0.9.103', commit: COMMIT, branch: 'main', committedAt: '2026-11-07T00:00:00+00:00' });
  assert.deepEqual(meta, {
    tool: 'graphify 0.9.103',
    commit: COMMIT,
    branch: 'main',
    committedAt: '2026-11-07T00:00:00+00:00',
    graph: 'graph.json',
    report: 'GRAPH_REPORT.md',
    boundaries: 'dependency-graph.json',
  });
});
