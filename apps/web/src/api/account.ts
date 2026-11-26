import { keepPreviousData, queryOptions } from '@tanstack/react-query';
import { createAuthClient } from 'better-auth/client';
import { keys } from './keys.ts';
import { api, request, unwrap, type Ok } from './request.ts';

/** Better Auth's own endpoints (sign-in, sign-out) at /api/auth on this origin; the session is a cookie. */
export const authClient = createAuthClient();

export type Me = Ok<'/api/me'>;
export type InvitationView = Ok<'/api/invitations'>[number];
export type UserView = Ok<'/api/users'>[number];
export type SessionView = Ok<'/api/me/sessions'>[number];
export type AiAccessView = Ok<'/api/me/ai-access'>['accesses'][number];
export type AiAccessLogEntry = Ok<'/api/me/ai-access-log'>['entries'][number];

/** The signed-in User, or null when nobody is signed in. */
export const meQuery = () => queryOptions({
  queryKey: keys.me,
  queryFn: async () => {
    const result = await api.GET('/api/me');
    return result.response.status === 401 ? null : unwrap(result);
  },
  staleTime: 5 * 60_000,
});
export const setupQuery = () =>
  queryOptions({ queryKey: keys.setup, queryFn: async () => request(api.GET('/api/setup')) });
/** Whether this instance mails invitation and reset links; without it both stay copy links (ADR 0012, 0013). */
export const emailConfigQuery = () =>
  queryOptions({ queryKey: keys.emailConfig, queryFn: async () => request(api.GET('/api/email/config')), staleTime: 5 * 60_000 });
/** Backups the operator put in /data/restore/ on the server, offered instead of a browser upload (plan FAH-175). */
export const setupRestoreArchivesQuery = () =>
  queryOptions({
    queryKey: keys.setupRestoreArchives,
    queryFn: async () => request(api.GET('/api/setup/restore/archives')),
  });
export const usersQuery = () => queryOptions({ queryKey: keys.users, queryFn: async () => request(api.GET('/api/users')) });
export const sessionsQuery = () => queryOptions({ queryKey: keys.sessions, queryFn: async () => request(api.GET('/api/me/sessions')) });
export const invitationsQuery = () => queryOptions({ queryKey: keys.invitations, queryFn: async () => request(api.GET('/api/invitations')) });
/** A link that is used up or expired stays an error: asking again won't change it. */
export const invitationLinkQuery = (token: string) => queryOptions({
  queryKey: keys.invitationLink(token), queryFn: async () => request(api.POST('/api/invitations/lookup', { body: { token } })), retry: false,
});
export const passwordResetQuery = (token: string) => queryOptions({
  queryKey: keys.passwordReset(token), queryFn: async () => request(api.POST('/api/password-resets/lookup', { body: { token } })), retry: false,
});

/** The User's AI accesses and whether this instance offers the MCP endpoint (ADR 0035). */
export const aiAccessQuery = () => queryOptions({ queryKey: keys.aiAccess, queryFn: async () => request(api.GET('/api/me/ai-access')) });

/** What the User's AI accesses read, newest first; `limit` grows with "show older". */
export const aiAccessLogQuery = (limit: number) =>
  queryOptions({
    queryKey: [...keys.aiAccessLog, limit] as const,
    queryFn: async () => request(api.GET('/api/me/ai-access-log', { params: { query: { limit } } })),
    placeholderData: keepPreviousData,
  });
