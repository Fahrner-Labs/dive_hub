// Billing over HTTP (hosted-DiveHub plan §4.5, ADR 0089): the signed Paddle webhook (unauthenticated but HMAC-gated) and
// the two links a signed-in User opens on Paddle. No money UI here: Paddle owns checkout and the customer portal.
import type { FastifyPluginAsyncTypebox } from '@fastify/type-provider-typebox';
import { Type } from 'typebox';
import type { Auth } from '../auth/auth.js';
import { requireUser, userOf } from '../auth/fastify.js';
import { problem, Problem } from '../http/problems.js';
import { BillingError, type BillingService } from './billing-service.js';

export interface BillingRouteDeps {
  auth: Auth;
  billing: BillingService;
}

const LinkView = Type.Object({
  url: Type.String({ format: 'uri', description: 'An authenticated Paddle URL; open it rather than building one' }),
}, { additionalProperties: false });

/** The raw bytes a content type parser kept; the signature is over exactly these, not over re-serialised JSON. */
const rawBodyOf = (body: unknown): Buffer =>
  Buffer.isBuffer(body) ? body : Buffer.from(typeof body === 'string' ? body : '');

export const billingRoutes: FastifyPluginAsyncTypebox<BillingRouteDeps> = async (app, { auth, billing }) => {
  // Paddle sends JSON, but its signature covers the bytes as sent. Keep them here, inside this plugin only, so nothing
  // else in the app sees a buffer body.
  app.addContentTypeParser('application/json', { parseAs: 'buffer' }, (_request, body, done) => done(null, body));

  app.post('/billing/webhooks', {
    // A signed webhook is small; a large body is an attack, not a Paddle event.
    bodyLimit: 256 * 1024,
    schema: { hide: true },
  }, async (request, reply) => {
    const header = request.headers['paddle-signature'];
    const rawBody = rawBodyOf(request.body);
    const accepted = await billing.receive(rawBody, Array.isArray(header) ? undefined : header);
    if (!accepted) return reply.code(401).send(problem('invalid_signature'));
    // Always 2xx after the signature check: a replay, a duplicate, or an event we do not use must not make Paddle retry.
    return reply.code(200).send({ received: true });
  });

  app.get('/billing/checkout', {
    onRequest: requireUser(auth),
    schema: {
      summary: 'A Paddle checkout link for the Pro plan',
      description: 'Creates a Paddle transaction whose custom data carries the signed-in User, so the subscription '
        + 'webhook can find them. Open the returned URL; Paddle collects the card and VAT. A User who already has the '
        + 'Pro plan answers 409 `billing_already_subscribed`: manage or cancel in the portal, so no second subscription '
        + 'starts.',
      response: { 200: LinkView, 401: Problem, 409: Problem, 502: Problem },
    },
  }, async (request, reply) => {
    try {
      return await billing.checkout(userOf(request).id);
    } catch (error) {
      if (error instanceof BillingError) return reply.code(409).send(problem(error.code));
      request.log.error({ err: error }, 'the Paddle checkout link could not be created');
      return reply.code(502).send(problem('billing_unavailable'));
    }
  });

  app.get('/billing/portal', {
    onRequest: requireUser(auth),
    schema: {
      summary: 'The Paddle customer portal link (invoices, card updates, cancellation)',
      description: 'Needs a Paddle customer from an earlier checkout; without one the answer is 409 '
        + '`billing_not_subscribed`. The portal is Paddle-hosted; Dive Hub stores no payment data.',
      response: { 200: LinkView, 401: Problem, 409: Problem, 502: Problem },
    },
  }, async (request, reply) => {
    try {
      return await billing.portal(userOf(request).id);
    } catch (error) {
      if (error instanceof BillingError) return reply.code(409).send(problem(error.code));
      request.log.error({ err: error }, 'the Paddle customer-portal link could not be created');
      return reply.code(502).send(problem('billing_unavailable'));
    }
  });
};
