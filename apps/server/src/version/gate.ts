// The client version gate (ADR 0076): an onRequest hook that answers HTTP 400 `client_too_old` only when the request
// names a registry route and its recognised Android version is older than that route's minimum. An unrecognised or
// missing User-Agent is never gated, and the gate never stands in for the server's own enforcement of the break.
import type { FastifyReply, FastifyRequest } from 'fastify';
import { problem } from '../http/problems.js';
import { MIN_CLIENT_VERSIONS, type ClientVersionRegistry } from './min-client-versions.js';
import { compareSemanticVersions, parseAndroidUserAgent, parseSemanticVersion } from './user-agent.js';

/** The onRequest hook for `registry` (default: the production registry, empty until a hard break ships). */
export function clientVersionGate(registry: ClientVersionRegistry = MIN_CLIENT_VERSIONS) {
  return async (request: FastifyRequest, reply: FastifyReply) => {
    const route = request.routeOptions.url;
    const gate = route === undefined ? undefined : registry[route];
    if (!gate) return;
    const client = parseAndroidUserAgent(request.headers['user-agent']);
    if (!client) return;
    const minimum = parseSemanticVersion(gate.minimum);
    // A malformed registry entry is a code error, but it must not block clients: the route falls back to ordinary
    // behaviour until the entry is fixed, and the version tests keep the registry well-formed.
    if (!minimum) return;
    if (compareSemanticVersions(client, minimum) < 0) {
      return reply.code(400).send(problem('client_too_old'));
    }
  };
}
