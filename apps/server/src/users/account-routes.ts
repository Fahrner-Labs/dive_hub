// A User's own account: password reset links, signed-in sessions (ADR 0013).
// Changing one's own password is Better Auth's POST /api/auth/change-password.
import type { FastifyPluginAsyncTypebox, TypeBoxTypeProvider } from '@fastify/type-provider-typebox';
import { Type } from 'typebox';
import { APIError } from 'better-auth/api';
import type { Auth } from '../auth/auth.js';
import { authHeaders, forwardCookies, requireUser, sessionIdOf, userOf } from '../auth/fastify.js';
import { limitAttempts, type AttemptLimit } from '../http/attempt-limit.js';
import { problem } from '../http/problems.js';
import type { PasswordResetService } from './password-reset-service.js';
import type { SessionService } from './session-service.js';
import { Password, Problem, Token, TwoFactorChallengeView, UserView, toUserView } from './views.js';
import { DateTime, IdParams, Nullable } from '../http/schemas.js';

export interface AccountRouteDeps {
  auth: Auth;
  passwordResets: PasswordResetService;
  sessions: SessionService;
  /** Counts attempts at the token routes per client IP (ADR 0061); shared with userRoutes. */
  tokenAttempts: AttemptLimit;
}

const SessionView = Type.Object({
  id: Type.String(),
  createdAt: DateTime,
  lastActiveAt: Nullable(DateTime, { description: 'Last time the session was refreshed (about daily)' }),
  expiresAt: DateTime,
  ipAddress: Nullable(Type.String()),
  userAgent: Nullable(Type.String()),
  current: Type.Boolean({ description: 'The session this request came with' }),
});

export const accountRoutes: FastifyPluginAsyncTypebox<AccountRouteDeps> = async (app, { auth, passwordResets, sessions, tokenAttempts }) => {
  // --- Public: using a password reset link ---------------------------------------------------------
  const limited = limitAttempts(tokenAttempts);

  app.post('/password-resets/lookup', {
    onRequest: limited,
    schema: {
      summary: 'Whose password a reset link is for',
      body: Type.Object({ token: Token }),
      response: { 200: Type.Object({ email: Type.String() }), 404: Problem, 429: Problem },
    },
  }, async (request, reply) => {
    const found = await passwordResets.find(request.body.token);
    return found ? { email: found.email } : reply.code(404).send(problem('reset_link_invalid'));
  });

  app.post('/password-resets/complete', {
    onRequest: limited,
    schema: {
      summary: 'Set a new password with a reset link; signs out everywhere, then signs in here (or asks for the second factor)',
      body: Type.Object({ token: Token, password: Password }),
      response: { 200: Type.Union([UserView, TwoFactorChallengeView]), 404: Problem, 429: Problem },
    },
  }, async (request, reply) => {
    const email = await passwordResets.complete(request.body.token, request.body.password);
    if (!email) return reply.code(404).send(problem('reset_link_invalid'));
    const { headers, response } = await auth.api.signInEmail({
      body: { email, password: request.body.password }, headers: authHeaders(request), returnHeaders: true,
    });
    forwardCookies(headers, reply);
    // A User with 2FA gets Better Auth's challenge instead of a session; the reset page passes it on like sign-in.
    const result = response as { twoFactorRedirect?: true; twoFactorMethods?: string[]; user?: Parameters<typeof toUserView>[0] };
    if (result.twoFactorRedirect) return reply.code(200).send({ twoFactorRedirect: true, twoFactorMethods: result.twoFactorMethods ?? [] });
    if (!result.user) throw new Error('password reset sign-in returned neither a User nor a two-factor challenge');
    return toUserView(result.user);
  });

  // --- Signed in: own sessions ---------------------------------------------------------------------

  await app.register(async (scope) => {
    const signedIn = scope.withTypeProvider<TypeBoxTypeProvider>();
    signedIn.addHook('onRequest', requireUser(auth));

    signedIn.get('/me/sessions', {
      schema: { summary: 'Where the signed-in User is signed in', response: { 200: Type.Array(SessionView) } },
    }, async (request) => sessions.list(userOf(request).id, sessionIdOf(request)));

    signedIn.delete('/me/sessions/:id', {
      schema: { summary: 'Sign out one session (the current one too)', params: IdParams, response: { 204: Type.Null(), 404: Problem } },
    }, async (request, reply) => {
      const deleted = await sessions.end(userOf(request).id, request.params.id);
      return deleted ? reply.code(204).send(null) : reply.code(404).send(problem('session_not_found'));
    });

    signedIn.delete('/me/sessions', {
      schema: { summary: 'Sign out every other session', response: { 204: Type.Null() } },
    }, async (request, reply) => {
      await sessions.endOthers(userOf(request).id, sessionIdOf(request));
      return reply.code(204).send(null);
    });

    // --- Signed in: own two-factor authentication -------------------------------------------------

    signedIn.post('/me/two-factor/disable', {
      schema: {
        summary: 'Turn off two-factor authentication; needs the password and a current code (authenticator or backup)',
        body: Type.Object({
          password: Password,
          code: Type.String({
            minLength: 6, maxLength: 11, pattern: '^(?:[0-9]{6}|[A-Za-z0-9]{5}-[A-Za-z0-9]{5})$',
            description: 'A 6-digit authenticator code, or one backup code in its XXXXX-XXXXX form',
          }),
        }),
        response: { 200: Type.Object({ status: Type.Boolean() }), 400: Problem, 401: Problem, 409: Problem },
      },
    }, async (request, reply) => {
      const headers = authHeaders(request);
      const { password, code } = request.body;
      try {
        await auth.api.verifyPassword({ body: { password }, headers });
      } catch (error) {
        if (error instanceof APIError && error.statusCode === 400) return reply.code(400).send(problem('two_factor_password_invalid'));
        throw error;
      }
      try {
        if (code.includes('-')) await auth.api.verifyBackupCode({ body: { code }, headers });
        else await auth.api.verifyTOTP({ body: { code }, headers });
      } catch (error) {
        if (error instanceof APIError && error.statusCode === 401) return reply.code(401).send(problem('two_factor_code_invalid'));
        if (error instanceof APIError && error.statusCode === 400) return reply.code(409).send(problem('two_factor_not_enabled'));
        throw error;
      }
      const { headers: responseHeaders, response } = await auth.api.disableTwoFactor({
        body: { password }, headers, returnHeaders: true,
      });
      forwardCookies(responseHeaders, reply);
      return reply.code(200).send({ status: response.status });
    });
  });
};
