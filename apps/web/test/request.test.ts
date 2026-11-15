// The shared request helper keeps unwrap's answer handling for every domain query (ADR 0014).
import { describe, expect, it } from 'vitest';
import { ApiError, request } from '../src/api.ts';

describe('request', () => {
  it('resolves the data of an answer', async () => {
    await expect(request(Promise.resolve({ data: { id: 'a' }, response: new Response(null, { status: 200 }) }))).resolves.toEqual({ id: 'a' });
  });

  it('rejects a refusal as an ApiError, as unwrap does', async () => {
    const answer = { error: { code: 'not_found', error: 'No Dive' }, response: new Response(null, { status: 404 }) };
    await expect(request(Promise.resolve(answer))).rejects.toBeInstanceOf(ApiError);
    await expect(request(Promise.resolve(answer))).rejects.toMatchObject({ message: 'No Dive', status: 404, code: 'not_found' });
  });
});
