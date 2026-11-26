// Better Auth inside Fastify (ADR 0011): the /api/auth/* handler and a session guard for our routes.
import type { FastifyInstance, FastifyReply, FastifyRequest } from 'fastify';
import { fromNodeHeaders } from 'better-auth/node';
import { clientKey, type AttemptLimit } from '../http/attempt-limit.js';
import { problem } from '../http/problems.js';
import { CLIENT_IP_HEADER, publicAuthPaths, type Auth, type SessionUser } from './auth.js';

declare module 'fastify' {
  interface FastifyRequest {
    /** The signed-in User; set by the session guard on every protected route. */
    user: SessionUser | null;
    /** Id of the session the request came with (set together with `user`). */
    sessionId: string | null;
  }
}

/**
 * Request headers for Better Auth. The client IP comes from Fastify, which only believes
 * X-Forwarded-For from the configured trusted proxies; a client-sent value of our header is replaced.
 */
export function authHeaders(request: FastifyRequest): Headers {
  const headers = fromNodeHeaders(request.headers);
  headers.set(CLIENT_IP_HEADER, request.ip);
  return headers;
}

/** Copies Better Auth's cookies (sign-in, session refresh, sign-out) onto our reply. */
export function forwardCookies(from: Headers, reply: FastifyReply): void {
  const cookies = from.getSetCookie();
  if (cookies.length > 0) reply.header('set-cookie', cookies);
}

/** Mounts the Better Auth endpoints we expose (publicAuthPaths) at /api/auth/*; the rest answer 404. */
export async function authHandler(
  app: FastifyInstance,
  { auth, registrationOpen, tokenAttempts }: { auth: Auth; registrationOpen: boolean; tokenAttempts: AttemptLimit },
) {
  const publicPaths = new Set<string>(publicAuthPaths(registrationOpen));
  // Hand Better Auth the body exactly as sent; it parses and validates it itself.
  app.removeAllContentTypeParsers();
  app.addContentTypeParser('*', { parseAs: 'string' }, (_request, body, done) => done(null, body));

  app.route({
    method: ['GET', 'POST'],
    url: '/api/auth/*',
    schema: { hide: true },
    handler: async (request, reply) => {
      const url = new URL(request.url, auth.options.baseURL);
      const path = url.pathname.slice('/api/auth'.length);
      if (!publicPaths.has(path)) return reply.code(404).send(problem('not_found'));
      // The same count per client IP as the token routes (ADR 0061), so a sign-up flood stops at 30 per 15 minutes.
      // Signing up repeats no token attempt, but without this limit it would be an unlimited public write route.
      if (registrationOpen && path === '/sign-up/email') {
        const wait = tokenAttempts.take(clientKey(request.ip));
        if (wait !== null) return reply.code(429).header('retry-after', String(wait)).send(problem('too_many_attempts'));
      }
      const response = await auth.handler(new Request(url, {
        method: request.method,
        headers: authHeaders(request),
        ...(typeof request.body === 'string' && request.body.length > 0 && { body: request.body }),
      }));
      reply.status(response.status);
      // The bearer plugin adds `set-auth-token` whenever a response sets the session cookie. A browser sign-in must
      // not hand same-origin JavaScript the HttpOnly session token (ADR 0011), so forward it only to a native-shaped
      // request: no cookie and no Origin/Referer (ADR 0074). The browser keeps its cookie; a native client reads this.
      const native = !request.headers.cookie && !request.headers.origin && !request.headers.referer;
      for (const [key, value] of response.headers) {
        if (key === 'set-auth-token' && !native) continue;
        if (key !== 'set-cookie' && key !== 'content-length' && key !== 'content-encoding') reply.header(key, value);
      }
      forwardCookies(response.headers, reply);
      return reply.send(response.body ? await response.text() : null);
    },
  });
}

/** onRequest hook: 401 unless a valid session cookie is present. Refreshed session cookies are passed on. */
export function requireUser(auth: Auth) {
  return async (request: FastifyRequest, reply: FastifyReply) => {
    const { headers, response: session } = await auth.api.getSession({
      headers: authHeaders(request), returnHeaders: true,
    });
    if (!session) return reply.code(401).send(problem('sign_in_required'));
    forwardCookies(headers, reply);
    request.user = session.user;
    request.sessionId = session.session.id;
  };
}

/** The signed-in User of a route behind requireUser; without the hook it is the route's mistake, so it throws. */
export function userOf(request: FastifyRequest): SessionUser {
  if (!request.user) throw new Error('no signed-in User: the route has no requireUser hook');
  return request.user;
}

/** The signed-in session's id of a route behind requireUser; like userOf, it throws without the hook. */
export function sessionIdOf(request: FastifyRequest): string {
  if (!request.sessionId) throw new Error('no session: the route has no requireUser hook');
  return request.sessionId;
}

/** Whether the User has the admin role: the one place that rule is written. */
export const isAdmin = (user: SessionUser | null): boolean => user?.role === 'admin';

/**
 * onRequest hook (after requireUser): 403 unless the signed-in User is an admin. The one admin gate: a plugin of only
 * admin routes adds it for the whole plugin, an admin route in a mixed plugin takes it as `onRequest`; services trust it.
 */
export async function requireAdmin(request: FastifyRequest, reply: FastifyReply) {
  if (!isAdmin(request.user)) return reply.code(403).send(problem('admins_only'));
}
