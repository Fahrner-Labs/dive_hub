#!/usr/bin/env node
// The repo map (ADR 0067, layer 1): a cheap, always-fresh orientation for agents. It ranks the exported and internal
// symbols of apps/server/src and apps/web/src by how widely the rest of the code references them, then writes the
// most useful ones per module to docs/generated/REPOMAP.md. CI rebuilds and commits it on every merge to main, and
// `pnpm check` fails when the committed map no longer matches the source.
//   node scripts/repomap.mjs           write docs/generated/REPOMAP.md
//   node scripts/repomap.mjs --check   fail when it is missing or stale
// The map is a hint; agents verify by reading the code around an edit.
import { existsSync, mkdirSync, readdirSync, readFileSync, writeFileSync } from 'node:fs';
import { dirname, join } from 'node:path';
import { pathToFileURL } from 'node:url';
import ts from 'typescript';

export const ROOTS = ['apps/server/src', 'apps/web/src'];
export const OUTPUT = 'docs/generated/REPOMAP.md';
/** `--check` exits with this when the map is missing or stale; `check.mjs` excuses only this code on a CI main push. */
export const STALE_EXIT = 2;

/** A module is one app plus its top-level folder; files directly under src belong to `(root)`. */
export const moduleOf = (path) => {
  const match = /^apps\/(server|web)\/src\/(.*)$/.exec(path);
  if (!match) return '(other)';
  const [, app, rest] = match;
  const slash = rest.indexOf('/');
  return `${app}/${slash < 0 ? '(root)' : rest.slice(0, slash)}`;
};

const byText = (left, right) => (left < right ? -1 : left > right ? 1 : 0);

/** Repository-relative source files under the app roots, sorted; test fixtures and declarations are not source. */
export function sourceFiles(root) {
  const files = [];
  const walk = (path) => {
    for (const entry of readdirSync(join(root, path), { withFileTypes: true }).sort((a, b) => byText(a.name, b.name))) {
      const child = `${path}/${entry.name}`;
      if (entry.isDirectory()) walk(child);
      else if (/\.(ts|tsx)$/.test(entry.name) && !/\.(d\.ts|test\.tsx?|spec\.tsx?)$/.test(entry.name)) files.push(child);
    }
  };
  for (const path of ROOTS) if (existsSync(join(root, path))) walk(path);
  return files.sort(byText);
}

const KIND_WEIGHT = { class: 1.25, fn: 1.2, const: 1.05, let: 1, iface: 1.05, type: 1, enum: 0.9, ns: 0.8 };
const EXPORT_BOOST = 1.35;

/** The names a declaration binds: functions, classes, types and enums; variables by each simple name. */
function definitionsOf(source, file) {
  const found = [];
  const add = (name, kind, exported, line) => {
    if (!name) return;
    found.push({ name, kind, exported, file, line });
  };
  for (const statement of source.statements) {
    const exported = Boolean(statement.modifiers?.some((modifier) => modifier.kind === ts.SyntaxKind.ExportKeyword));
    const line = (node) => source.getLineAndCharacterOfPosition(node.getStart(source)).line + 1;
    if (ts.isFunctionDeclaration(statement) && statement.name) add(statement.name.text, 'fn', exported, line(statement.name));
    else if (ts.isClassDeclaration(statement) && statement.name) add(statement.name.text, 'class', exported, line(statement.name));
    else if (ts.isInterfaceDeclaration(statement)) add(statement.name.text, 'iface', exported, line(statement.name));
    else if (ts.isTypeAliasDeclaration(statement)) add(statement.name.text, 'type', exported, line(statement.name));
    else if (ts.isEnumDeclaration(statement)) add(statement.name.text, 'enum', exported, line(statement.name));
    else if (ts.isModuleDeclaration(statement)) add(statement.name.text, 'ns', exported, line(statement.name));
    else if (ts.isVariableStatement(statement)) {
      const kind = statement.declarationList.flags & ts.NodeFlags.Const ? 'const' : 'let';
      for (const declaration of statement.declarationList.declarations) {
        if (ts.isIdentifier(declaration.name)) add(declaration.name.text, kind, exported, line(declaration.name));
      }
    }
  }
  return found;
}

/** The declaration-name positions whose identifier is not a reference (its parent names it). */
const NAME_PARENTS = new Set([
  ts.SyntaxKind.FunctionDeclaration, ts.SyntaxKind.ClassDeclaration, ts.SyntaxKind.InterfaceDeclaration,
  ts.SyntaxKind.TypeAliasDeclaration, ts.SyntaxKind.EnumDeclaration, ts.SyntaxKind.ModuleDeclaration,
  ts.SyntaxKind.MethodDeclaration, ts.SyntaxKind.MethodSignature, ts.SyntaxKind.PropertyDeclaration,
  ts.SyntaxKind.PropertySignature, ts.SyntaxKind.Parameter, ts.SyntaxKind.TypeParameter,
  ts.SyntaxKind.VariableDeclaration, ts.SyntaxKind.BindingElement, ts.SyntaxKind.ImportClause,
  ts.SyntaxKind.ImportSpecifier, ts.SyntaxKind.NamespaceImport, ts.SyntaxKind.PropertyAssignment,
  ts.SyntaxKind.EnumMember, ts.SyntaxKind.GetAccessor, ts.SyntaxKind.SetAccessor, ts.SyntaxKind.JsxAttribute,
]);

const isDeclaredName = (node) => {
  const parent = node.parent;
  if (!parent) return false;
  if (ts.isQualifiedName(parent) && parent.right === node) return true;
  return parent.name === node && NAME_PARENTS.has(parent.kind);
};

/** The local names a file may reference: its own declarations plus what its import clauses bring in. */
function localNamesOf(source, definitions) {
  const names = new Set(definitions.map(({ name }) => name));
  for (const statement of source.statements) {
    if (!ts.isImportDeclaration(statement) || !statement.importClause) continue;
    const clause = statement.importClause;
    if (clause.name) names.add(clause.name.text);
    const bindings = clause.namedBindings;
    if (!bindings) continue;
    if (ts.isNamespaceImport(bindings)) names.add(bindings.name.text);
    else for (const element of bindings.elements) names.add(element.name.text);
  }
  return names;
}

/** Counts, per name, in how many files and how often the rest of each file references it. */
function referencesOf(files) {
  const filesByName = new Map();
  const countsByName = new Map();
  for (const { source, localNames } of files) {
    const seen = new Set();
    const visit = (node) => {
      if (ts.isIdentifier(node) && localNames.has(node.text) && !isDeclaredName(node)) {
        countsByName.set(node.text, (countsByName.get(node.text) ?? 0) + 1);
        seen.add(node.text);
      }
      ts.forEachChild(node, visit);
    };
    visit(source);
    for (const name of seen) {
      if (!filesByName.has(name)) filesByName.set(name, new Set());
      filesByName.get(name).add(source.fileName);
    }
  }
  return { filesByName, countsByName };
}

/** Parse the sources and rank their declarations; `[{ name, kind, file, line, score, references }]`. */
export function rankSymbols(root) {
  const files = sourceFiles(root).map((file) => {
    const text = readFileSync(join(root, file), 'utf8');
    const source = ts.createSourceFile(file, text, ts.ScriptTarget.Latest, true, file.endsWith('.tsx') ? ts.ScriptKind.TSX : ts.ScriptKind.TS);
    const definitions = definitionsOf(source, file);
    return { source, file, definitions, localNames: localNamesOf(source, definitions) };
  });

  const merged = new Map();
  for (const { definitions } of files) {
    for (const symbol of definitions) {
      const key = `${symbol.file}\0${symbol.name}`;
      const seen = merged.get(key);
      if (!seen) merged.set(key, { ...symbol });
      else {
        seen.exported ||= symbol.exported;
        seen.line = Math.min(seen.line, symbol.line);
      }
    }
  }

  const symbols = [...merged.values()];
  const byName = new Map();
  for (const symbol of symbols) {
    if (!byName.has(symbol.name)) byName.set(symbol.name, []);
    byName.get(symbol.name).push(symbol);
  }
  const { filesByName, countsByName } = referencesOf(files);
  for (const symbol of symbols) {
    const shared = byName.get(symbol.name).length;
    const referenceFiles = filesByName.get(symbol.name)?.size ?? 0;
    const references = countsByName.get(symbol.name) ?? 0;
    symbol.references = references;
    symbol.score = ((1 + referenceFiles * 3 + Math.log2(1 + references)) / Math.sqrt(shared))
      * KIND_WEIGHT[symbol.kind] * (symbol.exported ? EXPORT_BOOST : 1);
  }
  return symbols.sort((left, right) => right.score - left.score
    || byText(left.file, right.file) || left.line - right.line || byText(left.name, right.name));
}

/** How many symbols a module shows: small modules keep their few, large ones get a deeper slice. */
export const perModuleLimit = (count) => Math.max(6, Math.min(24, Math.ceil(count / 15)));

/**
 * The module's top symbols. The first pass keeps any file to about a third of the places (at least three), so a large
 * file (api.ts, schema.ts) doesn't drown out every component and route worth finding; a second pass fills the places
 * a small module leaves.
 */
function topOf(items, limit) {
  const perFile = Math.max(3, Math.ceil(limit / 3));
  const chosen = [];
  const counts = new Map();
  for (const symbol of items) {
    const count = counts.get(symbol.file) ?? 0;
    if (count >= perFile || chosen.length >= limit) continue;
    chosen.push(symbol);
    counts.set(symbol.file, count + 1);
  }
  for (const symbol of items) {
    if (chosen.length >= limit) break;
    if (!chosen.includes(symbol)) chosen.push(symbol);
  }
  return chosen.slice(0, limit);
}

/** The map text; `perModule` is a number or a function of the module's symbol count. */
export function renderRepoMap(symbols, { files, perModule = perModuleLimit } = {}) {
  const byModule = new Map();
  for (const symbol of symbols) {
    const module = moduleOf(symbol.file);
    if (!byModule.has(module)) byModule.set(module, []);
    byModule.get(module).push(symbol);
  }
  const modules = [...byModule.keys()].sort(byText);
  const limitOf = (count) => (typeof perModule === 'function' ? perModule(count) : perModule);
  const lines = [
    '# Repo map',
    '',
    '> Generated by `node scripts/repomap.mjs` from `apps/server/src` and `apps/web/src`; do not edit by hand.',
    '> `pnpm check` fails when this map is stale. Symbols are ranked by how widely the code references them;',
    '> each module shows its most-referenced ones (exported symbols rank higher). Search here before walking the tree.',
    '',
  ];
  let shown = 0;
  for (const module of modules) {
    const items = byModule.get(module);
    const limit = limitOf(items.length);
    const top = topOf(items, limit);
    shown += top.length;
    lines.push(`### ${module}`);
    for (const symbol of top) lines.push(`${symbol.file}:${symbol.line} ${symbol.kind} ${symbol.name}`);
    if (items.length > top.length) lines.push(`… ${items.length - top.length} more symbols in this module`);
    lines.push('');
  }
  if (files !== undefined) lines.splice(6, 0, `Files: ${files} · symbols: ${symbols.length} · shown: ${shown}`, '');
  return `${lines.join('\n').replace(/\n+$/, '')}\n`;
}

/** The map and the numbers it was built from; no files are written. */
export function generateRepoMap(root = process.cwd(), options = {}) {
  const files = sourceFiles(root);
  const symbols = rankSymbols(root);
  return { text: renderRepoMap(symbols, { files: files.length, ...options }), files: files.length, symbols };
}

/** Whether the committed map is present and identical to a fresh generation. */
export function checkRepoMap(root = process.cwd()) {
  const file = join(root, OUTPUT);
  if (!existsSync(file)) return { ok: false, reason: `${OUTPUT} is missing; run node scripts/repomap.mjs` };
  const committed = readFileSync(file, 'utf8');
  const expected = generateRepoMap(root).text;
  if (committed === expected) return { ok: true };
  return { ok: false, reason: `${OUTPUT} is stale; run node scripts/repomap.mjs and commit the result` };
}

function main() {
  const root = process.cwd();
  if (process.argv.includes('--check')) {
    const result = checkRepoMap(root);
    if (!result.ok) {
      console.error(`repomap: ${result.reason}`);
      process.exit(STALE_EXIT);
    }
    console.log('repomap: docs/generated/REPOMAP.md is fresh');
    return;
  }
  const { text, files, symbols } = generateRepoMap(root);
  mkdirSync(dirname(join(root, OUTPUT)), { recursive: true });
  writeFileSync(join(root, OUTPUT), text);
  console.log(`repomap: wrote ${OUTPUT} (${files} files, ${symbols.length} symbols)`);
}

if (process.argv[1] && import.meta.url === pathToFileURL(process.argv[1]).href) main();
