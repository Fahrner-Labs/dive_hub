// A failed job's stored error is only the error's message (graphile_worker.jobs.last_error); a Drizzle query error
// keeps the database's own words in its cause. The wrapper folds that cause into the message, so the next stuck job
// says which query failed and why.
import { describe, expect, it } from 'vitest';
import type { JobHelpers } from 'graphile-worker';
import { failureMessage, withFailureCause } from '../src/worker.js';

const drizzleFailure = (cause: string) => new Error('Failed query: select ... from "dive_assessment"\nparams: ...', { cause: new Error(cause) });

describe('a failed worker task', () => {
  it('stores the database error Drizzle keeps in the cause', () => {
    expect(failureMessage(drizzleFailure('relation "dive_assessment" does not exist')))
      .toBe('Failed query: select ... from "dive_assessment"\nparams: ...\nCaused by: relation "dive_assessment" does not exist');
  });

  it('does not repeat a cause the message already names', () => {
    expect(failureMessage(new Error('the connection failed: timeout', { cause: new Error('timeout') })))
      .toBe('the connection failed: timeout');
  });

  it('takes a thrown value that is not an Error', () => {
    expect(failureMessage('boom')).toBe('boom');
  });

  it('rethrows with the cause in the message and the original error as its cause', async () => {
    const original = drizzleFailure('column "profile" does not exist');
    const tasks = withFailureCause({ 'assess-dives': async () => { throw original; } });
    const failure = await tasks['assess-dives']!(undefined, {} as JobHelpers).catch((error: unknown) => error);
    expect(failure).toBeInstanceOf(Error);
    expect((failure as Error).message).toContain('Caused by: column "profile" does not exist');
    expect((failure as Error).cause).toBe(original);
  });
});
