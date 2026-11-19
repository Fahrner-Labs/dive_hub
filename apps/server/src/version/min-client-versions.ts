// Per-route minimum Android versions, for hard breaks only (ADR 0076). This stays empty until the first real break
// ships; building it now means that break is a data change, not an infrastructure change.
//
// Grammar:
// - A key is the URL template Fastify matched for the request (`request.routeOptions.url`), not the raw URL:
//   `/api/imports/:id` matches that route whatever id the client sends. A key covers every method registered on the
//   template. There are no prefixes or wildcards; an unlisted route is never gated.
// - `minimum` is a SemVer 2.0.0 version. A recognised Android client older than it gets HTTP 400 `client_too_old`.
// - `reason` says what broke and `since` (YYYY-MM-DD) when the break shipped, for the next reader.
//
// The gate is UX, not access control: the break itself is enforced by the server's own logic for every request, and a
// client without a recognised Android User-Agent is never gated (ADR 0076).

/** One hard break's minimum recognised Android version. */
export interface MinClientVersion {
  /** SemVer 2.0.0: older recognised Android versions are answered `client_too_old`. */
  readonly minimum: string;
  /** What broke, so the next reader knows why this route is gated. */
  readonly reason: string;
  /** The day the break shipped, as YYYY-MM-DD. */
  readonly since: string;
}

/** Route template → its minimum recognised Android version. See the grammar above. */
export type ClientVersionRegistry = Readonly<Record<string, MinClientVersion>>;

/** The production registry: empty until the first hard break ships. */
export const MIN_CLIENT_VERSIONS: ClientVersionRegistry = {};
