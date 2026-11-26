// Users: who am I, first-admin setup, accepting an invitation (ADR 0011, 0012); admins invite in admin-routes.ts.
import type { FastifyPluginAsyncTypebox, TypeBoxTypeProvider } from '@fastify/type-provider-typebox';
import type { FastifyReply, FastifyRequest } from 'fastify';
import { Type } from 'typebox';
import { APIError } from 'better-auth/api';
import type { Auth } from '../auth/auth.js';
import { authHeaders, forwardCookies, requireUser, userOf } from '../auth/fastify.js';
import type { InvitationService, Role } from './invitation-service.js';
import type { ProfileService } from './profile-service.js';
import type { SetupService } from './setup-service.js';
import { limitAttempts, type AttemptLimit } from '../http/attempt-limit.js';
import { problem } from '../http/problems.js';
import { Email, Password, PreferencesView, Problem, Token, UserView, toUserView } from './views.js';
import { DateTime, Nullable } from '../http/schemas.js';
import type { EntitlementService } from '../entitlement/entitlement.js';

export interface UserRouteDeps {
  auth: Auth;
  profiles: ProfileService;
  setup: SetupService;
  invitations: InvitationService;
  /** Counts attempts at the token routes per client IP (ADR 0061); shared with accountRoutes. */
  tokenAttempts: AttemptLimit;
  /** The User's plan for the Settings' Plan section; with billing off it answers free and entitled (ADR 0087, 0089). */
  entitlements: EntitlementService;
}

const Name = Type.String({ minLength: 1, maxLength: 100, pattern: '\\S' });

const BillingView = Type.Object({
  configured: Type.Boolean({
    description: 'False on an instance without billing: every feature is free and the client shows no Plan section',
  }),
  plan: Type.Enum(['free', 'pro']),
  status: Type.Enum(['none', 'active', 'past_due', 'canceled'], { description: 'none: never subscribed' }),
  currentPeriodEnd: Nullable(DateTime, { description: 'The period that was paid for; canceled keeps it until it ends' }),
  entitled: Type.Boolean({ description: 'Whether the paid features (automation, Push runs) may be used now' }),
}, { description: 'The User\'s plan and status (hosted-DiveHub plan §4.4); shown in Settings and used to grey the paywall' });

const MeView = Type.Object({
  user: UserView,
  ownDiver: Type.Object({ id: Type.String(), name: Type.String() }),
  preferences: PreferencesView,
  billing: BillingView,
});

export const userRoutes: FastifyPluginAsyncTypebox<UserRouteDeps> = async (app, { auth, profiles, setup, invitations, tokenAttempts, entitlements }) => {
  const limited = limitAttempts(tokenAttempts);

  /** Creates the User through Better Auth (which hashes the password), then signs them in. */
  async function createAndSignIn(
    request: FastifyRequest, reply: FastifyReply,
    input: { email: string; name: string; password: string; role: Role },
  ) {
    // The setup token and an admin's invitation vouch for the address, so these Users need no verification mail even
    // when open registration requires it for self-registered ones (hosted plan §6.2).
    const { user: created } = await auth.api.createUser({ body: { ...input, data: { emailVerified: true } } });
    const { headers } = await auth.api.signInEmail({
      body: { email: input.email, password: input.password }, headers: authHeaders(request), returnHeaders: true,
    });
    forwardCookies(headers, reply);
    return created;
  }

  const isEmailTaken = (error: unknown) =>
    error instanceof APIError && error.body?.code === 'USER_ALREADY_EXISTS_USE_ANOTHER_EMAIL';

  // --- Public: first-admin setup and accepting an invitation -------------------------------------

  app.get('/setup', {
    schema: { summary: 'Whether this instance still needs its first admin', response: { 200: Type.Object({ needed: Type.Boolean() }) } },
  }, async () => ({ needed: await setup.isNeeded() }));

  app.post('/setup', {
    onRequest: limited,
    schema: {
      summary: 'Create the first admin with the setup token printed to the server log',
      body: Type.Object({ token: Token, email: Email, name: Name, password: Password }),
      response: { 201: UserView, 403: Problem, 409: Problem, 429: Problem },
    },
  }, async (request, reply) => {
    if (!(await setup.isNeeded())) return reply.code(409).send(problem('setup_done'));
    const restore = setup.consume(request.body.token);
    if (!restore) return reply.code(403).send(problem('setup_token_invalid'));
    try {
      const { token: _, ...input } = request.body;
      const created = await createAndSignIn(request, reply, { ...input, role: 'admin' });
      request.log.info({ userId: created.id }, 'first admin created');
      // The worker was held back while no admin existed (plan FAH-175); starting it now must not undo the creation.
      try {
        await setup.notifyDone();
      } catch (error) {
        request.log.error({ err: error }, 'could not start the worker after the first admin');
      }
      return reply.code(201).send(toUserView(created));
    } catch (error) {
      restore();
      throw error;
    }
  });

  app.post('/invitations/lookup', {
    onRequest: limited,
    schema: {
      summary: 'The open invitation behind an invitation token',
      body: Type.Object({ token: Token }),
      response: { 200: Type.Object({ email: Type.String(), expiresAt: DateTime }), 404: Problem, 429: Problem },
    },
  }, async (request, reply) => {
    const found = await invitations.find(request.body.token);
    return found
      ? { email: found.email, expiresAt: found.expiresAt.toISOString() }
      : reply.code(404).send(problem('invitation_invalid'));
  });

  app.post('/invitations/accept', {
    onRequest: limited,
    schema: {
      summary: 'Accept an invitation: become a User and get signed in',
      body: Type.Object({ token: Token, name: Name, password: Password }),
      response: { 201: UserView, 404: Problem, 409: Problem, 429: Problem },
    },
  }, async (request, reply) => {
    const claimed = await invitations.claim(request.body.token);
    if (!claimed) return reply.code(404).send(problem('invitation_invalid'));
    const { email, role } = claimed.invitation;
    try {
      const created = await createAndSignIn(request, reply, { email, role, name: request.body.name, password: request.body.password });
      await claimed.acceptedBy(created.id);
      return reply.code(201).send(toUserView(created));
    } catch (error) {
      await claimed.reopen();
      if (isEmailTaken(error)) return reply.code(409).send(problem('email_taken'));
      throw error;
    }
  });

  // --- Signed in -----------------------------------------------------------------------------------

  await app.register(async (scope) => {
    const signedIn = scope.withTypeProvider<TypeBoxTypeProvider>();
    signedIn.addHook('onRequest', requireUser(auth));

    signedIn.get('/me', {
      schema: {
        summary: 'The signed-in User, their own Diver, and their plan',
        description: 'Billing reports whether this instance sells the Pro plan at all; without it every feature is free and '
          + 'the client shows no Plan section (the dual-track invariant, ADR 0087).',
        response: { 200: MeView },
      },
    }, async (request) => {
      const me = userOf(request);
      const plan = await entitlements.summary(me.id);
      return {
        user: toUserView(me), ...(await profiles.read(me.id)),
        billing: { configured: entitlements.enforced, ...plan, currentPeriodEnd: plan.currentPeriodEnd?.toISOString() ?? null },
      };
    });

    signedIn.patch('/me/preferences', {
      schema: {
        summary: 'Change display settings (language, units); fields left out stay as they are',
        body: Type.Partial(PreferencesView),
        response: { 200: PreferencesView },
      },
    }, async (request) => {
      return profiles.savePreferences(userOf(request).id, request.body);
    });
  });
};
