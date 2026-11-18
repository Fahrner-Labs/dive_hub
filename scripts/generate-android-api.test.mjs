// Tests of the Kotlin model generator (scripts/generate-android-api.mjs): the naming and type rules the Android
// core depends on, and that modules only reachable from the app's operations are generated.
import assert from 'node:assert/strict';
import { test } from 'node:test';
import { className, generateModels, identifier, pascal } from './generate-android-api.mjs';

const document = (schemas, paths = {}) => ({ components: { schemas }, paths });

test('Kotlin names are safe for properties and classes', () => {
  assert.equal(pascal('utcOffsetSource'), 'UtcOffsetSource');
  assert.equal(pascal('api/dives/{id}'), 'ApiDivesId');
  assert.equal(pascal('123'), 'N123');
  assert.equal(className('class'), 'ClassModel');
  assert.equal(identifier('email'), 'email');
  assert.equal(identifier('field'), '`field`');
  assert.equal(identifier('not-a-name'), '`not-a-name`');
});

test('string enums stay strings, so a new server code or device value still decodes', () => {
  const files = generateModels(document({
    Status: {
      type: 'object',
      required: ['role', 'code'],
      properties: { role: { enum: ['user', 'admin'] }, code: { enum: ['a', 'b'] } },
    },
  }), [], ['Status']);

  const source = files.get('Status.kt');
  assert.match(source, /val role: String/);
  assert.match(source, /val code: String/);
  assert.doesNotMatch(source, /enum class/);
});

test('an anyOf with null is one nullable type; a wider union is JsonElement', () => {
  const files = generateModels(document({
    Thing: {
      type: 'object',
      required: ['name', 'mixed'],
      properties: {
        name: { anyOf: [{ type: 'null' }, { type: 'string' }] },
        mixed: { anyOf: [{ type: 'string' }, { type: 'integer' }] },
        units: { anyOf: [{ type: 'null' }, { enum: ['metric'] }, { enum: ['imperial'] }] },
        optional: { type: 'string' },
      },
    },
  }), [], ['Thing']);

  const source = files.get('Thing.kt');
  assert.match(source, /val name: String\?/);
  assert.match(source, /val mixed: JsonElement/);
  assert.match(source, /val units: String\? = null/);
  assert.match(source, /val optional: String\? = null/);
});

test('allOf flattens to one data class', () => {
  const files = generateModels(document({
    Base: { type: 'object', required: ['id'], properties: { id: { type: 'string' } } },
    Child: {
      allOf: [
        { $ref: '#/components/schemas/Base' },
        { type: 'object', required: ['extra'], properties: { extra: { type: 'integer' } } },
      ],
    },
  }), [], ['Child']);

  const source = files.get('Child.kt');
  assert.match(source, /val id: String/);
  assert.match(source, /val extra: Int/);
  assert.equal(files.has('Base.kt'), false, 'a flattened reference is not a separate class');
});

test('only what the operations reach is generated', () => {
  const files = generateModels(document({
    Used: { type: 'object', required: ['id'], properties: { id: { type: 'string' } } },
    Unused: { type: 'object', required: ['id'], properties: { id: { type: 'string' } } },
  }, {
    '/api/thing': {
      get: { responses: { 200: { content: { 'application/json': { schema: { $ref: '#/components/schemas/Used' } } } } } },
    },
  }), [{ name: 'ThingResponse', path: '/api/thing', method: 'get', kind: 'response', status: '200' }], []);

  assert.equal(files.has('Used.kt'), true);
  assert.equal(files.has('ThingResponse.kt'), false, 'a $ref response is the referenced model itself');
  assert.equal(files.has('Unused.kt'), false);
});

test('inline objects and arrays become named classes with the parent prefix', () => {
  const files = generateModels(document({
    Parent: {
      type: 'object',
      required: ['child', 'list'],
      properties: {
        child: { type: 'object', required: ['name'], properties: { name: { type: 'string' } } },
        list: { type: 'array', items: { type: 'object', required: ['id'], properties: { id: { type: 'string' } } } },
      },
    },
  }), [], ['Parent']);

  assert.match(files.get('Parent.kt'), /val child: ParentChild/);
  assert.match(files.get('Parent.kt'), /val list: List<ParentListItem>/);
  assert.equal(files.has('ParentChild.kt'), true);
  assert.equal(files.has('ParentListItem.kt'), true);
});
