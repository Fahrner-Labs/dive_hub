// The request log line (ADR 0076): Fastify's usual fields plus the recognised Android client, and never any request
// header. The auth headers (Authorization, cookies) therefore cannot reach the log through this serializer.
import type { FastifyRequest } from 'fastify';
import { parseAndroidUserAgent } from './user-agent.js';

/** What one request logs at info level; the same fields Fastify logs by default, plus `client` for the Android app. */
export function clientRequestSerializer(request: FastifyRequest) {
  const client = parseAndroidUserAgent(request.headers['user-agent']);
  const acceptVersion = request.headers['accept-version'];
  return {
    method: request.method,
    url: request.url,
    ...(typeof acceptVersion === 'string' && { version: acceptVersion }),
    host: request.host,
    remoteAddress: request.ip,
    ...(request.socket?.remotePort !== undefined && { remotePort: request.socket.remotePort }),
    ...(client && { client: { kind: 'android', version: client.text } }),
  };
}
