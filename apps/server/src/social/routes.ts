// Buddy suggestions over HTTP (ADR 0070): the Review inbox's list and a preview of the shared facts. Raising and
// cancelling happen with the Participants themselves; accepting and declining come in a later slice.
import type { FastifyPluginAsyncTypebox } from '@fastify/type-provider-typebox';
import { Type } from 'typebox';
import type { Auth } from '../auth/auth.js';
import { requireUser, userOf } from '../auth/fastify.js';
import { answerErrors, Problem, answers } from '../http/problems.js';
import { IdParams } from '../http/schemas.js';
import { SuggestionView, SuggestionPreviewView } from './suggestion-view.js';
import { SuggestionError, type SuggestionService } from './suggestion-service.js';

export interface SuggestionRouteDeps {
  auth: Auth;
  suggestions: SuggestionService;
}

export const suggestionRoutes: FastifyPluginAsyncTypebox<SuggestionRouteDeps> = async (app, { auth, suggestions }) => {
  app.addHook('onRequest', requireUser(auth));
  app.setErrorHandler(answerErrors(answers(SuggestionError, { suggestion_not_found: 404 })));

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
};
