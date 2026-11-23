// Conditional writes (ADR 0083): a native client sends the version an edit started from as `If-Match`,
// and a stale one answers 412 with the current entity instead of the web's 409 body-version refusal.
// The body version keeps working exactly as before; `If-Match` wins when both are sent.
import type { FastifyReply, FastifyRequest } from 'fastify';
import { Type } from 'typebox';

/**
 * The headers a route with a versioned write accepts. The value is the ETag's, quotes included: `"3"`.
 * Fastify refuses anything else with 400 invalid_input before the route runs.
 */
export const IfMatchHeaders = Type.Object({
  'if-match': Type.Optional(Type.String({
    pattern: '^"\\d+"$',
    description: 'The version the change started from, as a read\'s ETag carried it (e.g. "3"); it overrides the body\'s version',
  })),
}, { additionalProperties: true });

const USED = Symbol('if-match-used');

/** The version `If-Match` names; undefined without the header. A request that sends one takes the 412 contract. */
export const ifMatchVersion = (request: FastifyRequest): number | undefined => {
  const value = request.headers['if-match'];
  if (typeof value !== 'string') return undefined;
  (request as FastifyRequest & { [USED]?: boolean })[USED] = true;
  return Number(value.slice(1, -1));
};

/** Whether this request sent `If-Match`: only then is a stale version answered with 412 and the current entity. */
export const usesIfMatch = (request: FastifyRequest): boolean => (request as FastifyRequest & { [USED]?: boolean })[USED] === true;

const missingVersion = () => Object.assign(new Error('Send the version in the body or in If-Match'), { statusCode: 400 });

/**
 * The version a versioned write should check: `If-Match` when sent (it wins over a differing body version, ADR 0083),
 * else the body's. A request with neither is 400 invalid_input, as the body schema alone would have made it.
 */
export const versionOf = (request: FastifyRequest, bodyVersion: number | undefined): number => {
  const version = ifMatchVersion(request) ?? bodyVersion;
  if (version === undefined) throw missingVersion();
  return version;
};

/** Sets the ETag a read or a successful write answers with: `"<version>"`, as `If-Match` takes it. */
export const setETag = (reply: FastifyReply, version: number) => reply.header('etag', `"${version}"`);

