#!/usr/bin/env node
// The generated code-navigation artefacts (ADR 0067): Graphify's symbol/call graph and dependency-cruiser's
// module graph. CI runs `build` on every merge to main and commits `docs/generated/`; `verify` runs the three
// canned queries and fails loudly when the index stops answering them.
//   node scripts/code-graph.mjs build
//   node scripts/code-graph.mjs verify [--graph <path>] [--boundaries <path>] [--expect-sha <sha>]
// The graph is a hint; agents verify by reading the code.
import { spawnSync } from 'node:child_process';
import { copyFileSync, mkdirSync, readFileSync, writeFileSync } from 'node:fs';
import { join } from 'node:path';
import { pathToFileURL } from 'node:url';

export const OUTPUT = 'docs/generated';
export const GRAPH = join(OUTPUT, 'graph.json');

/** The Graphify relation names that mean one node may use another. */
const USE = new Set(['calls', 'indirect_call', 'references', 'imports', 'imports_from', 'dynamic_import', 're_exports']);
/** File-level imports only: the module graph agents ask "what may import what" about. */
const IMPORT = 'imports_from';

/** Graphify's `norm_label`: the exact symbol's letters and digits, lowercased. */
export const normalizeName = (value) => value.toLowerCase().replace(/[^a-z0-9]/g, '');

const byId = (graph) => new Map(graph.nodes.map((node) => [node.id, node]));

/** The code nodes whose exact name matches; more than one means a name collision the graph cannot resolve. */
export function symbolNodes(graph, symbol) {
  const wanted = normalizeName(symbol);
  return graph.nodes.filter((node) => node.file_type === 'code' && normalizeName(node.norm_label ?? node.label ?? '') === wanted);
}

/** The file node (`L1`) of a repository-relative path, or null. */
export function fileNode(graph, path) {
  return graph.nodes.find((node) => node.file_type === 'code' && node.source_file === path && node.source_location === 'L1') ?? null;
}

/** The code nodes that call or reference `node`; a file-level import or a prose mention is not a caller. */
export function callersOf(graph, node) {
  const nodes = byId(graph);
  const found = graph.links
    .filter((link) => link.target === node.id && USE.has(link.relation))
    .map((link) => nodes.get(link.source))
    .filter((caller) => caller?.file_type === 'code' && caller.source_location !== 'L1');
  return [...new Map(found.map((caller) => [caller.id, caller])).values()];
}

/** The file nodes that import `path` (a file-level reverse edge). */
export function dependentsOf(graph, path) {
  const node = fileNode(graph, path);
  if (!node) return [];
  const nodes = byId(graph);
  const found = graph.links
    .filter((link) => link.target === node.id && link.relation === IMPORT)
    .map((link) => nodes.get(link.source))
    .filter((source) => source?.file_type === 'code' && source.source_location === 'L1');
  return [...new Map(found.map((source) => [source.id, source])).values()];
}

/** The shortest directed path between two file nodes, as the file nodes along it; null when there is none. */
export function pathBetween(graph, fromPath, toPath, maxDepth = 6) {
  const from = fileNode(graph, fromPath);
  const to = fileNode(graph, toPath);
  if (!from || !to) return null;
  const nodes = byId(graph);
  const next = new Map();
  for (const link of graph.links) {
    if (!next.has(link.source)) next.set(link.source, []);
    next.get(link.source).push(link.target);
  }
  const parent = new Map([[from.id, null]]);
  const queue = [[from.id, 0]];
  for (let index = 0; index < queue.length; index += 1) {
    const [id, depth] = queue[index];
    if (id === to.id) {
      const path = [];
      for (let step = to.id; step !== null; step = parent.get(step)) path.unshift(nodes.get(step));
      return path;
    }
    if (depth === maxDepth) continue;
    for (const target of next.get(id) ?? []) {
      if (parent.has(target) || !nodes.has(target)) continue;
      parent.set(target, id);
      queue.push([target, depth + 1]);
    }
  }
  return null;
}

/** The three canned queries plus the staleness check: `{ queries, problems }`, problems for a failed query. */
export function verifyGraph(graph, { expectSha } = {}) {
  const queries = [];
  const problems = [];
  const record = (name, detail, ok = true) => {
    queries.push({ name, detail, ok });
    if (!ok) problems.push(`${name}: ${detail}`);
  };

  const commit = graph.built_at_commit;
  if (expectSha && commit !== expectSha) record('staleness metadata', `built_at_commit is ${commit ?? 'missing'}, expected ${expectSha}`, false);
  else if (!/^[0-9a-f]{40}$/.test(commit ?? '')) record('staleness metadata', `built_at_commit is ${commit ?? 'missing'}, not a full source SHA`, false);
  else record('staleness metadata', `built at ${commit}`);

  const symbols = symbolNodes(graph, 'lockManagedDive');
  if (symbols.length !== 1) record('callers of lockManagedDive', `expected one code node, found ${symbols.length}`, false);
  else if (symbols[0].source_file !== 'apps/server/src/dives/dive-service.ts') record('callers of lockManagedDive', `node is in ${symbols[0].source_file}, not dives/dive-service.ts`, false);
  else {
    const callers = callersOf(graph, symbols[0]).map((caller) => caller.label);
    const missing = ['createContinuationService()', 'createMergingService()'].filter((label) => !callers.includes(label));
    if (missing.length > 0) record('callers of lockManagedDive', `missing ${missing.join(', ')}; found ${callers.join(', ') || 'none'}`, false);
    else record('callers of lockManagedDive', `${callers.length} callers incl. both services`);
  }

  const path = pathBetween(graph, 'apps/server/src/dives/logbook-routes.ts', 'apps/server/src/db/schema.ts');
  if (!path) record('path logbook route to db schema', 'no directed path from logbook-routes.ts to schema.ts', false);
  else record('path logbook route to db schema', path.map((node) => node.source_file).join(' -> '));

  const dependents = dependentsOf(graph, 'apps/server/src/db/schema.ts').map((node) => node.source_file);
  if (dependents.length === 0) record('dependents of db/schema.ts', 'no file imports apps/server/src/db/schema.ts', false);
  else {
    const missing = ['apps/server/src/dives/dive-service.ts', 'apps/server/src/dives/logbook-routes.ts', 'apps/server/src/mcp/read-service.ts'].filter((file) => !dependents.includes(file));
    if (missing.length > 0) record('dependents of db/schema.ts', `missing ${missing.join(', ')}`, false);
    else record('dependents of db/schema.ts', `${dependents.length} files incl. dive-service, logbook-routes and read-service`);
  }

  return { queries, problems };
}

/** The module-boundary artefact: enough modules cruised, and no new dependency violation (ADR 0053). */
export function verifyBoundaries(boundaries) {
  const queries = [];
  const problems = [];
  const record = (name, detail, ok = true) => {
    queries.push({ name, detail, ok });
    if (!ok) problems.push(`${name}: ${detail}`);
  };

  const modules = Array.isArray(boundaries.modules) ? boundaries.modules.map((module) => module.source) : [];
  if (modules.length < 200) record('module boundary graph', `only ${modules.length} modules cruised`, false);
  else if (!modules.includes('apps/server/src/db/schema.ts')) record('module boundary graph', 'apps/server/src/db/schema.ts is missing from the cruised modules', false);
  else record('module boundary graph', `${modules.length} modules incl. db/schema.ts`);

  const advised = boundaries.summary?.advisedExitCode;
  if (advised !== 0) record('module boundary violations', `advisedExitCode is ${advised ?? 'missing'}, not 0`, false);
  else record('module boundary violations', 'none against the known-violations baseline');

  return { queries, problems };
}

/** The small sidecar agents read before loading the 7 MB graph: which source commit it was built from. */
export const metaFor = ({ tool, commit, branch, committedAt }) => ({ tool, commit, branch, committedAt, graph: 'graph.json', report: 'GRAPH_REPORT.md', boundaries: 'dependency-graph.json' });

function run(command, args) {
  const result = spawnSync(command, args, { stdio: 'inherit' });
  if (result.error) throw new Error(`${command} could not run: ${result.error.message}`);
  if (result.status !== 0) throw new Error(`${command} exited with ${result.status}`);
}

function git(...args) {
  const result = spawnSync('git', args, { encoding: 'utf8' });
  if (result.status !== 0) throw new Error(`git ${args.join(' ')} failed: ${(result.stderr ?? '').trim()}`);
  return result.stdout.trim();
}

function version() {
  const result = spawnSync('graphify', ['--version'], { encoding: 'utf8' });
  return result.status === 0 ? result.stdout.trim() : 'graphify (unknown version)';
}

function build() {
  run('graphify', ['.', '--code-only', '--no-viz', '--no-label']);
  run('graphify', ['cluster-only', '.', '--no-viz', '--no-label']);
  mkdirSync(OUTPUT, { recursive: true });
  copyFileSync(join('graphify-out', 'graph.json'), GRAPH);
  copyFileSync(join('graphify-out', 'GRAPH_REPORT.md'), join(OUTPUT, 'GRAPH_REPORT.md'));
  const meta = metaFor({ tool: version(), commit: git('rev-parse', 'HEAD'), branch: git('rev-parse', '--abbrev-ref', 'HEAD'), committedAt: git('show', '-s', '--format=%cI', 'HEAD') });
  writeFileSync(join(OUTPUT, 'graph-meta.json'), `${JSON.stringify(meta, null, 2)}\n`);
  run(process.execPath, ['node_modules/dependency-cruiser/bin/dependency-cruiser.mjs', '--config', '.dependency-cruiser.cjs', '--output-type', 'json', '--ignore-known', '.dependency-cruiser-known-violations.json', '--output-to', join(OUTPUT, 'dependency-graph.json'), 'apps/server/src', 'apps/web/src']);
  console.log(`code-graph: wrote ${GRAPH}, ${join(OUTPUT, 'GRAPH_REPORT.md')}, ${join(OUTPUT, 'graph-meta.json')} and ${join(OUTPUT, 'dependency-graph.json')}`);
}

function verify(args) {
  const option = (name, fallback) => {
    const index = args.indexOf(name);
    return index >= 0 ? args[index + 1] : fallback;
  };
  const graphFile = option('--graph', GRAPH);
  const boundariesFile = option('--boundaries', join(OUTPUT, 'dependency-graph.json'));
  const graph = JSON.parse(readFileSync(graphFile, 'utf8'));
  const boundaries = JSON.parse(readFileSync(boundariesFile, 'utf8'));
  const graphReport = verifyGraph(graph, { expectSha: option('--expect-sha') });
  const boundaryReport = verifyBoundaries(boundaries);
  const queries = [...graphReport.queries, ...boundaryReport.queries];
  const problems = [...graphReport.problems, ...boundaryReport.problems];
  for (const query of queries) console.log(`${query.ok ? '✓' : '✗'} ${query.name}: ${query.detail}`);
  if (problems.length > 0) {
    console.error(`code-graph: ${problems.length} canned query(ies) failed; the committed graph no longer navigates the code`);
    process.exit(1);
  }
  console.log(`code-graph: ${queries.length} canned queries answered from ${graphFile} and ${boundariesFile}`);
}

if (process.argv[1] && import.meta.url === pathToFileURL(process.argv[1]).href) {
  const [command, ...args] = process.argv.slice(2);
  if (command === 'build') build();
  else if (command === 'verify') verify(args);
  else {
    console.error('usage: node scripts/code-graph.mjs build|verify [--graph <path>] [--boundaries <path>] [--expect-sha <sha>]');
    process.exit(2);
  }
}
