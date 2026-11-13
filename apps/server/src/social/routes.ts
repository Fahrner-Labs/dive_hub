// Buddy suggestions over HTTP (ADR 0070): the Review inbox's list, the shared facts a preview shows, and the two answers.
// Raising and cancelling happen with the Participants themselves.
import type { FastifyPluginAsyncTypebox } from '@fastify/type-provider-typebox';
import { Type } from 'typebox';
import type { Auth } from '../auth/auth.js';
import { requireUser, userOf } from '../auth/fastify.js';
import { answerErrors, Problem, answers } from '../http/problems.js';
import { IdParams } from '../http/schemas.js';
import { SuggestionView, SuggestionPreviewView, SuggestionAcceptView, SuggestionDeclineView } from './suggestion-view.js';
import { SuggestionError, type SuggestionService } from './suggestion-service.js';

export interface SuggestionRouteDeps {
  auth: Auth;
  suggestions: SuggestionService;
}

const STATUS = {
  suggestion_not_found: 404,
  suggestion_not_pending: 409,
  suggestion_dive_not_found: 404,
  suggestion_dive_not_overlapping: 409,
  suggestion_link_conflict: 409,
} as const;

const AcceptBody = Type.Object({
  diveId: Type.Optional(Type.String({
    format: 'uuid',
    description: 'One of the preview\'s overlaps: link that existing Dive instead of creating a new one. It must still overlap the suggested Dive.',
  })),
}, { additionalProperties: false });

export const suggestionRoutes: FastifyPluginAsyncTypebox<SuggestionRouteDeps> = async (app, { auth, suggestions }) => {
  app.addHook('onRequest', requireUser(auth));
  app.setErrorHandler(answerErrors(answers(SuggestionError, STATUS)));

  app.get('/buddy-suggestions', {
    schema: {
      summary: 'Pending Buddy suggestions for the Divers the User manages now, newest first',
      description: 'The audience is read from which Users manage the invited Diver at this moment; a Diver no User manages has no audience and appears for none. '
        + 'Another User\'s suggestion is not listed, and its preview answers 404 `suggestion_not_found`.',
      response: { 200: Type.Array(SuggestionView), 404: Problem },
    },
  }, async (request) => suggestions.list(userOf(request).id));

  app.get('/buddy-suggestions/:id', {
    schema: {
      summary: 'The shared facts of a suggested Dive, for the Review inbox',
      description: 'Site, start time with its UTC offset, duration, max and average depth as starting values, the Dive\'s own Diver, its Participants by name and any centre responsible for the site. '
        + 'Never a Recording, notes, suit, weighting, cylinders, lead or an overridden value. 404 `suggestion_not_found` unless the User manages the invited Diver.',
      params: IdParams,
      response: { 200: SuggestionPreviewView, 404: Problem },
    },
  }, async (request) => suggestions.preview(userOf(request).id, request.params.id));

  app.post('/buddy-suggestions/:id/accept', {
    schema: {
      summary: 'Accept a Buddy suggestion: link an existing overlapping Dive, or copy the shared facts into a new one',
      description: 'With `diveId`, link that existing Dive of the invited Diver into one Joint dive with the suggested one: nothing is copied '
        + 'and no Diver\'s data is merged into another\'s. That Dive must be one of the preview\'s `overlaps` and still overlap; a Dive '
        + 'already in a different Joint dive is refused. Without `diveId`, create a Dive for the invited Diver with the site, start time '
        + 'and UTC offset, duration and max/average depth as plain own values, and the people on the source Dive (its own Diver and its '
        + 'Participants) as Divers; no Recording, notes, suit, weighting, cylinders, lead or Override. '
        + 'Both Dives join one Joint dive (an existing one if either already has it). '
        + '404 `suggestion_not_found` unless the User manages the invited Diver; 409 `suggestion_not_pending` once it was accepted, '
        + 'declined or cancelled; 404 `suggestion_dive_not_found` and 409 `suggestion_dive_not_overlapping` for a chosen Dive that is '
        + 'not the invited Diver\'s or no longer overlaps; 409 `suggestion_link_conflict` when both Dives are in different Joint dives.',
      params: IdParams,
      // An absent body means branch A; Fastify validates `undefined` as `null` when a body schema is given.
      body: Type.Union([AcceptBody, Type.Null()]),
      response: { 200: SuggestionAcceptView, 404: Problem, 409: Problem },
    },
  }, async (request) => suggestions.accept(userOf(request).id, request.params.id, request.body?.diveId));

  app.post('/buddy-suggestions/:id/decline', {
    schema: {
      summary: 'Decline a Buddy suggestion',
      description: 'Marks the one suggestion row declined for every User who manages the invited Diver now. Nothing is created and the '
        + 'inviter\'s Participant stays; adding the Participant again raises it as pending once more. '
        + '404 `suggestion_not_found` unless the User manages the invited Diver; 409 `suggestion_not_pending` once it was accepted, declined or cancelled.',
      params: IdParams,
      response: { 200: SuggestionDeclineView, 404: Problem, 409: Problem },
    },
  }, async (request) => suggestions.decline(userOf(request).id, request.params.id));
};
