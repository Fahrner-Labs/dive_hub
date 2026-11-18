#!/usr/bin/env node
// The Android core's API models are generated from the server's OpenAPI document. The generator is deliberately
// small and in-repo (apps/android/README.md explains why OpenAPI Generator's Kotlin targets were not used):
//   node scripts/generate-android-api.mjs           write the models
//   node scripts/generate-android-api.mjs --check   fail when the committed models are stale
// The output is committed, so a changed OpenAPI document without regenerated models fails `pnpm check`.
import { existsSync, mkdirSync, readFileSync, readdirSync, rmSync, writeFileSync } from 'node:fs';
import { dirname, join, resolve } from 'node:path';
import { fileURLToPath } from 'node:url';

const ROOT = resolve(dirname(fileURLToPath(import.meta.url)), '..');
const OPENAPI = join(ROOT, 'packages/api-client/openapi.json');
const OUT = join(ROOT, 'apps/android/shared/src/commonMain/kotlin/dev/divehub/api/models');
const PACKAGE = 'dev.divehub.api.models';

/** The operation schemas slice B needs. Later slices add the operations they use here, then regenerate. */
const OPERATIONS = [
  { name: 'SignInEmailRequest', path: '/api/auth/sign-in/email', method: 'post', kind: 'request' },
  { name: 'SignInEmail200Response', path: '/api/auth/sign-in/email', method: 'post', kind: 'response', status: '200' },
  { name: 'SignOut200Response', path: '/api/auth/sign-out', method: 'post', kind: 'response', status: '200' },
  { name: 'GetSession200Response', path: '/api/auth/get-session', method: 'get', kind: 'response', status: '200' },
  { name: 'ApiMeGet200Response', path: '/api/me', method: 'get', kind: 'response', status: '200' },
];

const KEYWORDS = new Set([
  'as', 'break', 'class', 'continue', 'do', 'else', 'false', 'for', 'fun', 'if', 'in', 'interface', 'is',
  'null', 'object', 'package', 'return', 'super', 'this', 'throw', 'true', 'try', 'typealias', 'typeof',
  'val', 'var', 'when', 'while', 'field', 'it',
]);

const doc = JSON.parse(readFileSync(OPENAPI, 'utf8'));
const classes = new Map();
const usedNames = new Set();

const pascal = (name) => {
  const parts = String(name).split(/[^A-Za-z0-9]+/).filter(Boolean);
  const text = parts.map((part) => part[0].toUpperCase() + part.slice(1)).join('') || 'Value';
  return /^[0-9]/.test(text) ? `N${text}` : text;
};

const className = (name) => (KEYWORDS.has(name) ? `${pascal(name)}Model` : pascal(name));

const identifier = (name) => {
  if (/^[A-Za-z_][A-Za-z0-9_]*$/.test(name) && !KEYWORDS.has(name)) return name;
  return `\`${name.replace(/`/g, '')}\``;
};

const escape = (text) => String(text ?? '').replace(/\*\//g, '* /').replace(/\s+/g, ' ').trim();

const refName = (ref) => {
  const name = ref.split('/').pop();
  if (!name || !doc.components.schemas[name]) throw new Error(`unknown $ref ${ref}`);
  return className(name);
};

const refSchema = (schema) => (schema.$ref ? doc.components.schemas[schema.$ref.split('/').pop()] : schema);

const flatten = (schema) => {
  if (!schema.allOf) return schema;
  const merged = { ...schema, type: 'object', properties: {}, required: [] };
  for (const raw of schema.allOf) {
    const part = flatten(refSchema(raw));
    Object.assign(merged.properties, part.properties ?? {});
    merged.required.push(...(part.required ?? []));
    if (!merged.description && part.description) merged.description = part.description;
  }
  delete merged.allOf;
  return merged;
};

const nullableOf = (schema) => schema.nullable === true || schema.type === 'null';

const uniqueClass = (wanted) => {
  if (!usedNames.has(wanted)) return wanted;
  for (let index = 2; ; index += 1) {
    const candidate = `${wanted}${index}`;
    if (!usedNames.has(candidate)) return candidate;
  }
};

function registerClass(hint, rawSchema) {
  const name = uniqueClass(className(hint));
  usedNames.add(name);
  const entry = { name, schema: rawSchema, properties: [], description: '' };
  classes.set(name, entry);
  const schema = flatten(rawSchema);
  entry.description = escape(schema.description);
  const required = new Set(schema.required ?? []);
  for (const [property, propertySchema] of Object.entries(schema.properties ?? {})) {
    const type = typeOf(propertySchema, `${name}${pascal(property)}`);
    const isRequired = required.has(property);
    const nullable = nullableOf(propertySchema) || type.nullable;
    entry.properties.push({
      jsonName: property,
      name: identifier(property),
      type: type.type,
      nullable: !isRequired || nullable,
      optional: !isRequired,
      description: escape(propertySchema.description ?? refSchema(propertySchema).description),
    });
  }
  return name;
}

function typeOf(rawSchema, hint) {
  if (!rawSchema) return { type: 'JsonElement', nullable: true };
  if (rawSchema.$ref) return { type: refName(rawSchema.$ref), nullable: false };

  if (rawSchema.anyOf) {
    const variants = rawSchema.anyOf;
    const nonNull = variants.filter((variant) => !nullableOf(variant));
    const hadNull = nonNull.length !== variants.length;
    if (nonNull.length === 0) return { type: 'JsonElement', nullable: true };
    if (nonNull.length === 1) {
      const type = typeOf(nonNull[0], hint);
      return { type: type.type, nullable: hadNull || type.nullable };
    }
    return { type: 'JsonElement', nullable: hadNull };
  }
  if (rawSchema.oneOf) return { type: 'JsonElement', nullable: nullableOf(rawSchema) };

  if (rawSchema.enum) {
    const values = rawSchema.enum;
    if (values.every((value) => typeof value === 'boolean')) return { type: 'Boolean', nullable: nullableOf(rawSchema) };
    if (values.every((value) => Number.isInteger(value))) return { type: 'Int', nullable: nullableOf(rawSchema) };
    if (values.every((value) => typeof value === 'number')) return { type: 'Double', nullable: nullableOf(rawSchema) };
    // String, not an enum: a client must keep working when the server adds a value (errors, device vocabulary).
    return { type: 'String', nullable: nullableOf(rawSchema) };
  }

  switch (rawSchema.type) {
    case 'string':
      return { type: 'String', nullable: nullableOf(rawSchema) };
    case 'integer':
      return { type: rawSchema.format === 'int64' ? 'Long' : 'Int', nullable: nullableOf(rawSchema) };
    case 'number':
      return { type: 'Double', nullable: nullableOf(rawSchema) };
    case 'boolean':
      return { type: 'Boolean', nullable: nullableOf(rawSchema) };
    case 'array': {
      if (Array.isArray(rawSchema.items)) return { type: 'List<JsonElement>', nullable: nullableOf(rawSchema) };
      const item = typeOf(rawSchema.items, `${hint}Item`);
      return { type: `List<${item.type}${item.nullable ? '?' : ''}>`, nullable: nullableOf(rawSchema) };
    }
    case 'object':
      if (Object.keys(rawSchema.properties ?? {}).length > 0) {
        return { type: registerClass(hint, rawSchema), nullable: nullableOf(rawSchema) };
      }
      if (rawSchema.additionalProperties === true || rawSchema.additionalProperties === undefined) {
        return { type: 'JsonElement', nullable: nullableOf(rawSchema) };
      }
      if (typeof rawSchema.additionalProperties === 'object') {
        const value = typeOf(rawSchema.additionalProperties, `${hint}Value`);
        return { type: `Map<String, ${value.type}${value.nullable ? '?' : ''}>`, nullable: nullableOf(rawSchema) };
      }
      return { type: 'JsonElement', nullable: nullableOf(rawSchema) };
    default:
      if (rawSchema.properties) return { type: registerClass(hint, rawSchema), nullable: nullableOf(rawSchema) };
      if (rawSchema.additionalProperties) return typeOf({ ...rawSchema, type: 'object' }, hint);
      return { type: 'JsonElement', nullable: true };
  }
}

const schemaAt = (operation) => {
  const method = doc.paths[operation.path]?.[operation.method];
  if (!method) throw new Error(`missing ${operation.method.toUpperCase()} ${operation.path}`);
  if (operation.kind === 'request') {
    const schema = method.requestBody?.content?.['application/json']?.schema;
    if (!schema) throw new Error(`missing request body for ${operation.path}`);
    return schema;
  }
  const schema = method.responses?.[operation.status]?.content?.['application/json']?.schema;
  if (!schema) throw new Error(`missing ${operation.status} response for ${operation.path}`);
  return schema;
};

const fileFor = (entry) => {
  const lines = [
    '// Generated from packages/api-client/openapi.json by scripts/generate-android-api.mjs.',
    '// Do not edit by hand: change the OpenAPI document and run the generator.',
    `package ${PACKAGE}`,
    '',
    'import kotlinx.serialization.SerialName',
    'import kotlinx.serialization.Serializable',
    'import kotlinx.serialization.json.JsonElement',
    '',
  ];
  if (entry.description) lines.push('/**', ` * ${entry.description}`, ' */');
  if (entry.properties.length === 0) {
    lines.push('@Serializable', `class ${entry.name}`, '');
    return `${lines.join('\n')}`;
  }
  lines.push('@Serializable', `data class ${entry.name}(`);
  lines.push(entry.properties.map((property, index) => {
    const suffix = index === entry.properties.length - 1 ? '' : ',';
    const type = `${property.type}${property.nullable ? '?' : ''}`;
    const value = `${property.optional ? ' = null' : ''}`;
    const note = property.description ? `    /** ${property.description} */\n` : '';
    return `${note}    @SerialName(${JSON.stringify(property.jsonName)}) val ${property.name}: ${type}${value}${suffix}`;
  }).join('\n'));
  lines.push(')', '');
  return `${lines.join('\n')}`;
};

// The component schemas first (sorted for a stable diff), then the operation schemas slice B uses.
for (const name of Object.keys(doc.components.schemas).sort()) {
  registerClass(name, doc.components.schemas[name]);
}
for (const operation of OPERATIONS) registerClass(operation.name, schemaAt(operation));

const files = new Map([...classes.keys()].sort().map((name) => [`${name}.kt`, fileFor(classes.get(name))]));

if (process.argv.includes('--check')) {
  const stale = [];
  for (const [file, content] of files) {
    const path = join(OUT, file);
    if (!existsSync(path) || readFileSync(path, 'utf8') !== content) stale.push(file);
  }
  if (existsSync(OUT)) {
    for (const file of readdirSync(OUT)) {
      if (file.endsWith('.kt') && !files.has(file)) stale.push(file);
    }
  }
  if (stale.length > 0) {
    console.error(`android-api: ${stale.length} generated model file(s) are stale: ${stale.slice(0, 12).join(', ')}${stale.length > 12 ? ', …' : ''}`);
    console.error('android-api: run `node scripts/generate-android-api.mjs` and commit the result');
    process.exit(1);
  }
  console.log(`android-api: ${files.size} generated model files match packages/api-client/openapi.json`);
  process.exit(0);
}

rmSync(OUT, { recursive: true, force: true });
mkdirSync(OUT, { recursive: true });
for (const [file, content] of files) writeFileSync(join(OUT, file), content);
console.log(`android-api: wrote ${files.size} model file(s) to ${OUT}`);
