// Push notifications over HTTP, first slice (ADR 0085): the public FCM client config, and the session-scoped device
// registrations a native client makes and removes. Hints only; no route here carries dive content or sends a push.
import type { FastifyPluginAsyncTypebox } from '@fastify/type-provider-typebox';
import { Type } from 'typebox';
import type { Auth } from '../auth/auth.js';
import { requireUser, sessionIdOf, userOf } from '../auth/fastify.js';
import { problem, Problem } from '../http/problems.js';
import { DateTime, IdParams } from '../http/schemas.js';
import { PUSH_PLATFORMS } from '../vocabulary.js';
import { MAX_PUSH_TOKEN_LENGTH, type NotificationService } from './notification-service.js';

export interface NotificationRouteDeps {
  auth: Auth;
  notifications: NotificationService;
}

const Platform = Type.Enum([...PUSH_PLATFORMS], {
  description: 'The client platform; iOS and web are reserved for their providers, Android is the first (ADR 0085)',
});

const FcmClientView = Type.Object({
  projectId: Type.String(),
  applicationId: Type.String(),
  apiKey: Type.String(),
  gcmSenderId: Type.String(),
}, { additionalProperties: false });

const PushConfigView = Type.Object({
  android: Type.Optional(FcmClientView),
}, {
  additionalProperties: false,
  description: 'The Firebase client config for Android; no `android` member means push is off and the client polls',
});

const DeviceRegistration = Type.Object({
  platform: Platform,
  pushToken: Type.String({ minLength: 1, maxLength: MAX_PUSH_TOKEN_LENGTH }),
}, { additionalProperties: false });

const PushDeviceView = Type.Object({
  id: Type.String({ format: 'uuid' }),
  platform: Platform,
  createdAt: DateTime,
  lastSeenAt: DateTime,
}, {
  additionalProperties: false,
  description: 'The registration to keep for DELETE /api/devices/{id}; the token itself is not echoed',
});

export const notificationRoutes: FastifyPluginAsyncTypebox<NotificationRouteDeps> = async (app, { auth, notifications }) => {
  app.get('/push/config', {
    schema: {
      summary: 'The public Firebase client config for push (ADR 0085)',
      description: 'Unauthenticated on purpose: these values are public in every Firebase app. An empty object means '
        + 'this instance has no FCM credential, so native clients keep polling and the foreground SSE stream.',
      response: { 200: PushConfigView },
    },
  }, async () => notifications.config());

  app.post('/devices', {
    onRequest: requireUser(auth),
    schema: {
      summary: 'Register or refresh this device\'s push token for the signed-in User and session',
      description: 'Upserts by pushToken: the same token registered again moves to the latest User and session and '
        + 'refreshes lastSeenAt. Ending the session deletes the row (ADR 0085).',
      body: DeviceRegistration,
      response: { 200: PushDeviceView, 400: Problem, 401: Problem },
    },
  }, async (request) => notifications.register(userOf(request).id, sessionIdOf(request), request.body));

  app.delete('/devices/:id', {
    onRequest: requireUser(auth),
    schema: {
      summary: 'Remove one of the signed-in User\'s push registrations (e.g. on sign-out)',
      description: 'Owner only; another User\'s registration answers 404, never 403.',
      params: IdParams,
      response: { 204: Type.Null(), 400: Problem, 401: Problem, 404: Problem },
    },
  }, async (request, reply) => {
    const removed = await notifications.remove(userOf(request).id, request.params.id);
    if (!removed) return reply.code(404).send(problem('push_device_not_found'));
    return reply.code(204).send(null);
  });
};
