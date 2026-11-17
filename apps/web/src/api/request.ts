import { createApiClient, type components, type paths } from '@dive-hub/api-client';

export const api = createApiClient();

/** A refusal's body (the server's Problem schema, ADR 0014). */
type Problem = components['schemas']['Problem'];
/** The server's error codes (one list for every route). */
export type ProblemCode = Problem['code'];
/** As openapi-fetch reads an answer (its `Readable`): a tuple is an array of its members. */
export type Read<T> = T extends readonly (infer E)[] ? Read<E>[] : T extends object ? { [K in keyof T]: Read<T[K]> } : T;
/** The body of a route's 200 answer, as `api` returns it: every view type below is one of these, or part of one. */
export type Ok<P extends keyof paths, M extends 'get' | 'post' = 'get'> =
  paths[P][M] extends { responses: { 200: { content: { 'application/json': infer B } } } } ? Read<B> : never;

/** A Provider requirement a Dive doesn't meet, as a refusal about it names (ADR 0029). */
export type UnmetView = Ok<'/api/dives/{id}/providers/{provider}'>['unmet'][number];
/**
 * What else a refusal says besides its code: the Problem schema's extras, and the Provider routes'
 * own `unmet` with provider_requirements_unmet (ADR 0029).
 */
export type ProblemDetails = Pick<Problem, 'diver' | 'centre' | 'existing' | 'diveId' | 'runId'> & { unmet?: UnmetView[] };
const DETAILS = ['unmet', 'diver', 'centre', 'existing', 'diveId', 'runId'] as const satisfies readonly (keyof ProblemDetails)[];

/**
 * An API error with its HTTP status (a 401 anywhere sends the user back to sign-in) and the
 * server's error code, which the UI translates (ADR 0014).
 */
export class ApiError extends Error {
  constructor(
    message: string, readonly status: number, readonly code?: ProblemCode,
    /** The Provider a provider_* code is about, named for the translated text (ADR 0027). */
    readonly provider?: { id: string; name: string },
    readonly details: ProblemDetails = {},
  ) {
    super(message);
  }
}
export const isUnauthorized = (error: unknown) => error instanceof ApiError && error.status === 401;

/** A request that got no answer: offline, or the server can't be reached. A cancelled one stays an AbortError. */
export class NetworkError extends Error {}
export const noAnswerError = (error: unknown) =>
  error instanceof DOMException && error.name === 'AbortError' ? error : new NetworkError('No answer from the server', { cause: error });
/** The client's middleware that turns fetch's TypeError for a request without an answer into a NetworkError. */
export const noAnswer: Parameters<typeof api.use>[0] = { onError: ({ error }) => noAnswerError(error) };
api.use(noAnswer);

/**
 * Retry a failed query once, but only when trying again can help: a network error or a server error.
 * A 4xx (not found, not allowed, signed out) stays the same, and retrying only delays the message.
 */
export const shouldRetry = (failures: number, error: unknown) =>
  failures < 1 && !(error instanceof ApiError && error.status >= 400 && error.status < 500);

/**
 * The response body, or an ApiError. Success is the HTTP status: a 204 has no body, so openapi-fetch
 * gives `data: undefined` for it.
 */
export function unwrap<T>(result: { data?: T; error?: unknown; response: Response }): T {
  if (!result.response.ok) {
    // Our routes answer { error }; Fastify's validation errors carry the useful text in `message`.
    const body = (result.error ?? {}) as Partial<Problem> & ProblemDetails & { message?: string };
    const message = body.error ?? body.message ?? `Request failed (${result.response.status})`;
    const provider = body.provider ? { id: body.provider, name: body.providerName ?? body.provider } : undefined;
    const details = Object.fromEntries(DETAILS.filter((k) => body[k]).map((k) => [k, body[k]])) as ProblemDetails;
    throw new ApiError(message, result.response.status, body.code, provider, details);
  }
  return result.data as T;
}

/** One call through the shared client: the answer's body, or the same ApiError `unwrap` raises. */
export const request = <T>(answer: Promise<{ data?: T; error?: unknown; response: Response }>): Promise<T> =>
  answer.then((result) => unwrap(result));
