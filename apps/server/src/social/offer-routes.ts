// Fill offers for a Joint dive's shared facts over HTTP (FAH-411, ADR 0070): list the offers, read one Dive's, and take
// or dismiss them. Deriving the offers at read time keeps the answer current; only a dismissal is written.
import type { FastifyPluginAsyncTypebox } from '@fastify/type-provider-typebox';
import { Type } from 'typebox';
import type { Auth } from '../auth/auth.js';
import { requireUser, userOf } from '../auth/fastify.js';
import { answerErrors, answers, Problem } from '../http/problems.js';
import { IdParams } from '../http/schemas.js';
import { JointOfferAcceptView, JointOfferDeclineView, JointOfferFieldsBody, JointOfferView } from './offer-view.js';
import { OfferError, type OfferService } from './offer-service.js';

export interface OfferRouteDeps {
  auth: Auth;
  offers: OfferService;
}

const STATUS = {
  dive_not_found: 404,
  joint_offer_not_valid: 409,
} as const;

export const offerRoutes: FastifyPluginAsyncTypebox<OfferRouteDeps> = async (app, { auth, offers }) => {
  app.addHook('onRequest', requireUser(auth));
  app.setErrorHandler(answerErrors(answers(OfferError, STATUS)));

  app.get('/joint-dives/offers', {
    schema: {
      summary: 'Fill offers from the other members of the signed-in User\'s Joint dives',
      description: 'One entry per source Dive: the shared facts of the User\'s Dive beside the other member\'s, and which of the '
        + 'User\'s empty fields it can fill. Offers exist only where the User\'s field is empty and another member has a value; '
        + 'differing filled values are never offered. With more than two members the newest filled value is the source. Never a '
        + 'Recording, notes, suit, weighting, cylinders, lead, an overridden value or a provider link.',
      response: { 200: Type.Array(JointOfferView) },
    },
  }, async (request) => offers.list(userOf(request).id));

  app.get('/dives/:id/joint-offers', {
    schema: {
      summary: 'Fill offers for one Dive of a Joint dive',
      description: 'The same entries as GET /joint-dives/offers, for this Dive only. 404 `dive_not_found` unless the signed-in '
        + 'User manages its Diver.',
      params: IdParams,
      response: { 200: Type.Array(JointOfferView), 404: Problem },
    },
  }, async (request) => offers.forDive(userOf(request).id, request.params.id));

  app.post('/joint-dives/offers/accept', {
    schema: {
      summary: 'Fill empty fields of one of the User\'s Dives from another member of its Joint dive',
      description: 'Copies the offering member\'s current values (never a preview) for the named fields, as the User\'s own values, '
        + 'through the normal Revision machinery (actor `system`, cause `joint-dive-fill`). The Dive is locked first: 409 '
        + '`joint_offer_not_valid` when it is no longer in a Joint dive, a requested field is filled meanwhile, or the other member '
        + 'no longer has a value. 404 `dive_not_found` unless the User manages the Dive.',
      body: JointOfferFieldsBody,
      response: { 200: JointOfferAcceptView, 404: Problem, 409: Problem },
    },
  }, async (request) => offers.accept(userOf(request).id, request.body.diveId, request.body.fields));

  app.post('/joint-dives/offers/decline', {
    schema: {
      summary: 'Decline fill offers for one of the User\'s Dives',
      description: 'Stores a dismissal per named field for this Dive and Joint dive; the derivation leaves them out from then on, '
        + 'also after the other members\' values change. 404 `dive_not_found` unless the User manages the Dive; 409 '
        + '`joint_offer_not_valid` when the Dive is not in a Joint dive anymore.',
      body: JointOfferFieldsBody,
      response: { 200: JointOfferDeclineView, 404: Problem, 409: Problem },
    },
  }, async (request) => offers.decline(userOf(request).id, request.body.diveId, request.body.fields));
};
