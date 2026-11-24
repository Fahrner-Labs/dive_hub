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

/**
 * The operation schemas slice B needs. Later slices add the operations they use here, then regenerate. Only the
 * transitive closure of these operations and MODEL_SEEDS is generated, so a server-only schema does not churn the
 * Android tree.
 */
export const OPERATIONS = [
  { name: 'SignInEmailRequest', path: '/api/auth/sign-in/email', method: 'post', kind: 'request' },
  { name: 'ApiMeGet200Response', path: '/api/me', method: 'get', kind: 'response', status: '200' },
  { name: 'ApiDivesGet200Response', path: '/api/dives', method: 'get', kind: 'response', status: '200' },
  // The offline sync's phase 1 (slice D2): the Divers the User keeps, their body weights, and the paged Dive sites.
  { name: 'DiverView', path: '/api/divers', method: 'get', kind: 'response', status: '200' },
  // The Participants add sheet (FAH-488): the live search over every Diver of the instance, by name.
  { name: 'FoundDiver', path: '/api/divers/search', method: 'get', kind: 'response', status: '200' },
  { name: 'ApiDiverBodyWeightsGet200ResponseItem', path: '/api/divers/{id}/body-weights', method: 'get', kind: 'response', status: '200' },
  { name: 'ApiDiveSitesGet200Response', path: '/api/dive-sites', method: 'get', kind: 'response', status: '200' },
  // The refresh slice (D3): deleted Dives, paged by nextCursor, become tombstones in the store.
  { name: 'DeletedDivesPage', path: '/api/dives/deleted', method: 'get', kind: 'response', status: '200' },
  // The dive page (slice C2): the Dive itself, its neighbours in the list it was opened from (ADR 0042), and the
  // Primary recording's samples for the depth profile.
  { name: 'DiveView', path: '/api/dives/{id}', method: 'get', kind: 'response', status: '200' },
  { name: 'NeighboursView', path: '/api/dives/{id}/neighbours', method: 'get', kind: 'response', status: '200' },
  { name: 'RecordingSamplesView', path: '/api/recordings/{id}/samples', method: 'get', kind: 'response', status: '200' },
  // The logbook names the Providers it is connected to in `not-at-provider` (ADR 0040); only these two fields are used.
  { name: 'ProviderSummary', path: '/api/providers', method: 'get', kind: 'response', status: '200' },
  { name: 'ConnectionView', path: '/api/connections', method: 'get', kind: 'response', status: '200' },
  // The offline tools (slice U1): their answers are the contract the phone's service layer shapes to.
  { name: 'ApiToolsGasGet200Response', path: '/api/tools/gas', method: 'get', kind: 'response', status: '200' },
  { name: 'ApiToolsBottomTimeGet200Response', path: '/api/tools/bottom-time', method: 'get', kind: 'response', status: '200' },
  // The gas plan (slice U2): the route's answer, both of its shapes in one class (see registerUnionClass).
  { name: 'ApiToolsGasPlanPost200Response', path: '/api/tools/gas-plan', method: 'post', kind: 'response', status: '200' },
  // The personalized tools (slice U3): the answers the phone shapes from the synced store.
  { name: 'ApiDiversIdLeadEstimateGet200Response', path: '/api/divers/{id}/lead-estimate', method: 'get', kind: 'response', status: '200' },
  { name: 'ApiDiversIdLoadingsGet200Response', path: '/api/divers/{id}/loadings', method: 'get', kind: 'response', status: '200' },
  // Client diagnostics (FAH-325): the status the app reads and the upload it sends, so contract drift here is gated too.
  { name: 'ApiClientDiagnosticsStatusGet200Response', path: '/api/client-diagnostics/status', method: 'get', kind: 'response', status: '200' },
  { name: 'ApiClientDiagnosticsPostRequest', path: '/api/client-diagnostics', method: 'post', kind: 'request' },
  // The scanner (T11): read a code, take a person's code, and create a centre from a centre's code with a client id.
  { name: 'ApiVerificationCodesReadPostRequest', path: '/api/verification-codes/read', method: 'post', kind: 'request' },
  { name: 'ApiVerificationCodesReadPost200Response', path: '/api/verification-codes/read', method: 'post', kind: 'response', status: '200' },
  { name: 'ApiDiversFromCodePostRequest', path: '/api/divers/from-code', method: 'post', kind: 'request' },
  { name: 'DiverDetailsView', path: '/api/divers/from-code', method: 'post', kind: 'response', status: '200' },
  { name: 'ApiDiveCentresPostRequest', path: '/api/dive-centres', method: 'post', kind: 'request' },
  { name: 'CentreView', path: '/api/dive-centres', method: 'post', kind: 'response', status: '200' },
];

/** Named component schemas the app uses outside an operation body. Refusals decode through the tolerant ApiErrorBody. */
export const MODEL_SEEDS = [];

const KEYWORDS = new Set([
  'as', 'break', 'class', 'continue', 'do', 'else', 'false', 'for', 'fun', 'if', 'in', 'interface', 'is',
  'null', 'object', 'package', 'return', 'super', 'this', 'throw', 'true', 'try', 'typealias', 'typeof',
  'val', 'var', 'when', 'while', 'field', 'it',
]);

export const pascal = (name) => {
  const parts = String(name).split(/[^A-Za-z0-9]+/).filter(Boolean);
  const text = parts.map((part) => part[0].toUpperCase() + part.slice(1)).join('') || 'Value';
  return /^[0-9]/.test(text) ? `N${text}` : text;
};

export const className = (name) => (KEYWORDS.has(name) ? `${pascal(name)}Model` : pascal(name));

export const identifier = (name) => {
  if (/^[A-Za-z_][A-Za-z0-9_]*$/.test(name) && !KEYWORDS.has(name)) return name;
  return `\`${name.replace(/`/g, '')}\``;
};

const escape = (text) => String(text ?? '').replace(/\*\//g, '* /').replace(/\s+/g, ' ').trim();

const nullableOf = (schema) => schema.nullable === true || schema.type === 'null';

const refSchema = (document, schema) => (schema.$ref ? document.components.schemas[schema.$ref.split('/').pop()] : schema);

/** `allOf` as one object: a Problem plus its extras becomes one data class. */
export function flattenSchema(document, rawSchema) {
  if (!rawSchema.allOf) return rawSchema;
  const merged = { ...rawSchema, type: 'object', properties: {}, required: [] };
  for (const part of rawSchema.allOf) {
    const flat = flattenSchema(document, refSchema(document, part));
    Object.assign(merged.properties, flat.properties ?? {});
    merged.required.push(...(flat.required ?? []));
    if (!merged.description && flat.description) merged.description = flat.description;
  }
  delete merged.allOf;
  return merged;
}

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
    return lines.join('\n');
  }
  lines.push('@Serializable', `data class ${entry.name}(`);
  lines.push(entry.properties.map((property, index) => {
    const suffix = index === entry.properties.length - 1 ? '' : ',';
    const type = `${property.type}${property.nullable ? '?' : ''}`;
    const value = property.optional ? ' = null' : '';
    const note = property.description ? `    /** ${property.description} */\n` : '';
    return `${note}    @SerialName(${JSON.stringify(property.jsonName)}) val ${property.name}: ${type}${value}${suffix}`;
  }).join('\n'));
  lines.push(')', '');
  return lines.join('\n');
};

const schemaAt = (document, operation) => {
  const method = document.paths[operation.path]?.[operation.method];
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

/** The model files of a document: `{ filename → Kotlin source }`, sorted, with references pulled in transitively. */
export function generateModels(document, operations = OPERATIONS, seeds = MODEL_SEEDS) {
  const classes = new Map();
  const usedNames = new Set();

  const refName = (ref) => {
    const name = ref.split('/').pop();
    if (!name || !document.components.schemas[name]) throw new Error(`unknown $ref ${ref}`);
    return className(name);
  };

  function registerClass(hint, rawSchema) {
    if (rawSchema.$ref) return typeOf(rawSchema, hint).type;
    if (rawSchema.anyOf) {
      const shapes = rawSchema.anyOf
        .filter((variant) => !nullableOf(variant))
        .map((variant) => flattenSchema(document, refSchema(document, variant)));
      if (shapes.length > 0 && shapes.every((shape) => shape.type === 'object' && Object.keys(shape.properties ?? {}).length > 0)) {
        return registerUnionClass(hint, shapes);
      }
    }
    let name = className(hint);
    if (usedNames.has(name)) {
      for (let index = 2; ; index += 1) {
        if (!usedNames.has(`${name}${index}`)) {
          name = `${name}${index}`;
          break;
        }
      }
    }
    usedNames.add(name);
    const entry = { name, properties: [], description: '' };
    classes.set(name, entry);
    const schema = flattenSchema(document, rawSchema);
    entry.description = escape(schema.description);
    const required = new Set(schema.required ?? []);
    for (const [property, propertySchema] of Object.entries(schema.properties ?? {})) {
      const type = typeOf(propertySchema, `${name}${pascal(property)}`);
      const isRequired = required.has(property);
      entry.properties.push({
        jsonName: property,
        name: identifier(property),
        type: type.type,
        nullable: !isRequired || nullableOf(propertySchema) || type.nullable,
        optional: !isRequired,
        description: escape(propertySchema.description ?? refSchema(document, propertySchema).description),
      });
    }
    return name;
  }

  /** What a schema looks like, for telling the same shape apart from two that only differ in an enum's values. */
  function shapeKey(rawSchema) {
    if (!rawSchema) return 'any';
    if (rawSchema.$ref) return `ref:${rawSchema.$ref}`;
    if (rawSchema.enum) {
      const values = rawSchema.enum;
      if (values.every((value) => typeof value === 'boolean')) return 'boolean';
      if (values.every((value) => Number.isInteger(value))) return 'integer';
      if (values.every((value) => typeof value === 'number')) return 'number';
      return 'string';
    }
    if (rawSchema.anyOf) return `anyOf:${rawSchema.anyOf.map(shapeKey).join('|')}`;
    if (rawSchema.allOf) return `allOf:${JSON.stringify(flattenSchema(document, rawSchema))}`;
    if (rawSchema.type === 'array') return `array:${shapeKey(rawSchema.items)}`;
    if (rawSchema.type === 'object' || rawSchema.properties) return `object:${JSON.stringify(rawSchema)}`;
    return rawSchema.type ?? 'any';
  }

  /**
   * An `anyOf` of object shapes (the gas plan's ok and outside_limits answers) is one class: the union of their fields.
   * A field is required only when every variant requires it; a variant's own fields stay optional and nullable.
   */
  function registerUnionClass(hint, schemas) {
    let name = className(hint);
    if (usedNames.has(name)) {
      for (let index = 2; ; index += 1) {
        if (!usedNames.has(`${name}${index}`)) {
          name = `${name}${index}`;
          break;
        }
      }
    }
    usedNames.add(name);
    const entry = { name, properties: [], description: '' };
    classes.set(name, entry);
    entry.description = escape(schemas.find((schema) => schema.description)?.description ?? '');
    const requiredEverywhere = schemas
      .map((schema) => new Set(schema.required ?? []))
      .reduce((left, right) => new Set([...left].filter((property) => right.has(property))));
    const fields = new Map();
    for (const schema of schemas) {
      for (const [property, propertySchema] of Object.entries(schema.properties ?? {})) {
        fields.set(property, [...(fields.get(property) ?? []), propertySchema]);
      }
    }
    for (const [property, propertySchemas] of fields) {
      // A field every variant shares with one shape (the status, a string enum in each) keeps that type; a real clash
      // between shapes stays a JsonElement. The shape, not the class name, decides: the same inline object registered
      // twice would otherwise look like two types.
      const shapes = propertySchemas.map((propertySchema) => shapeKey(propertySchema));
      const type = shapes.every((shape) => shape === shapes[0])
        ? typeOf(propertySchemas[0], `${name}${pascal(property)}`)
        : { type: 'JsonElement', nullable: true };
      const isRequired = requiredEverywhere.has(property);
      entry.properties.push({
        jsonName: property,
        name: identifier(property),
        type: type.type,
        nullable: !isRequired || nullableOf(propertySchemas[0]) || type.nullable,
        optional: !isRequired,
        description: escape(propertySchemas[0].description ?? refSchema(document, propertySchemas[0]).description),
      });
    }
    return name;
  }

  function typeOf(rawSchema, hint) {
    if (!rawSchema) return { type: 'JsonElement', nullable: true };
    // `allOf` is one object also when it sits in a property (the tools' `sacLpm`): merge before typing it.
    if (rawSchema.allOf) return typeOf(flattenSchema(document, rawSchema), hint);
    if (rawSchema.$ref) {
      const referenced = rawSchema.$ref.split('/').pop();
      if (!usedNames.has(className(referenced))) registerClass(referenced, document.components.schemas[referenced]);
      return { type: refName(rawSchema.$ref), nullable: false };
    }

    if (rawSchema.anyOf) {
      const variants = rawSchema.anyOf;
      const nonNull = variants.filter((variant) => !nullableOf(variant));
      const hadNull = nonNull.length !== variants.length;
      if (nonNull.length === 0) return { type: 'JsonElement', nullable: true };
      if (nonNull.length === 1) {
        const type = typeOf(nonNull[0], hint);
        return { type: type.type, nullable: hadNull || type.nullable };
      }
      const shapes = nonNull.map((variant) => flattenSchema(document, refSchema(document, variant)));
      if (shapes.every((shape) => shape.type === 'object' && Object.keys(shape.properties ?? {}).length > 0)) {
        return { type: registerUnionClass(hint, shapes), nullable: hadNull };
      }
      const types = nonNull.map((variant) => typeOf(variant, hint));
      if (types.every((type) => type.type === types[0].type)) {
        return { type: types[0].type, nullable: hadNull || types.some((type) => type.nullable) };
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

  /**
   * A response `anyOf` whose variants are all named object schemas (the logbook's summary and details pages): the
   * caller knows which it asked for, so each variant becomes its own class instead of one merged class whose clashing
   * field (`dives`) would collapse to `JsonElement`.
   */
  const namedVariants = (rawSchema) => Array.isArray(rawSchema?.anyOf) && rawSchema.anyOf.length > 1
    && rawSchema.anyOf.every((variant) => variant.$ref && document.components.schemas[variant.$ref.split('/').pop()]);

  for (const name of seeds) {
    if (!document.components.schemas[name]) throw new Error(`unknown model seed ${name}`);
    registerClass(name, document.components.schemas[name]);
  }
  for (const operation of operations) {
    const schema = schemaAt(document, operation);
    // A list operation (ADR 0060's bare arrays) is its item's model: the caller decodes List<operation.name>.
    if (schema.type === 'array') typeOf(schema.items, operation.name);
    else if (namedVariants(schema)) schema.anyOf.forEach((variant) => typeOf(variant, operation.name));
    else registerClass(operation.name, schema);
  }

  return new Map([...classes.keys()].sort().map((name) => [`${name}.kt`, fileFor(classes.get(name))]));
}

function main() {
  const document = JSON.parse(readFileSync(OPENAPI, 'utf8'));
  const files = generateModels(document);

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
    return;
  }

  rmSync(OUT, { recursive: true, force: true });
  mkdirSync(OUT, { recursive: true });
  for (const [file, content] of files) writeFileSync(join(OUT, file), content);
  console.log(`android-api: wrote ${files.size} model file(s) to ${OUT}`);
}

if (process.argv[1] && resolve(process.argv[1]) === fileURLToPath(import.meta.url)) main();
