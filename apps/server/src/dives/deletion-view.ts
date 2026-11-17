// What the API says about deleted Dives: where a page of them continues, each Provider copy's fate, and the refusal that
// names the copies.
import { Type } from 'typebox';
import { Problem } from '../http/problems.js';

/** Where a page of deleted Dives continues: the last Dive of the page, in the list's order (deletedAt, then id). */
export interface DeletedDiveCursor {
  deletedAt: Date;
  id: string;
}

/** The cursor for a page's last Dive: opaque, so a client passes it back without reading it. */
export function encodeDeletedCursor({ deletedAt, id }: DeletedDiveCursor): string {
  return Buffer.from(JSON.stringify({ at: deletedAt.toISOString(), id }), 'utf8').toString('base64url');
}

/** The cursor in its opaque form; null when it isn't one (the route answers 400 invalid_input). */
export function decodeDeletedCursor(raw: string): DeletedDiveCursor | null {
  try {
    const value: unknown = JSON.parse(Buffer.from(raw, 'base64url').toString('utf8'));
    if (typeof value !== 'object' || value === null) return null;
    const { at, id } = value as { at?: unknown; id?: unknown };
    if (typeof at !== 'string' || typeof id !== 'string') return null;
    const deletedAt = new Date(at);
    if (Number.isNaN(deletedAt.getTime())) return null;
    return { deletedAt, id };
  } catch {
    return null;
  }
}

export const Copies = Type.Array(Type.Object({
  provider: Type.String(),
  copy: Type.Enum(['deleted', 'kept'], { description: 'What happened to the copy there' }),
}), { description: 'Each Provider the Dive was at' });

/** A refusal while deleting; after a provider_* code, which copies at Providers are gone already. Registered with app.addSchema, like Problem. */
export const DeleteProblemSchema = Type.Intersect([Problem, Type.Object({
  providers: Type.Optional(Type.Array(Copies.items, {
    description: 'With a provider_* code: each Provider the Dive is at, and whether its copy there was deleted before the refusal (the Dive stays here)',
  })),
})], { $id: 'DeleteProblem', description: 'A refusal while deleting a Dive: a Problem, with the copies at Providers' });
