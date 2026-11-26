import { and, eq, gt, isNull, ne, sql, TransactionRollbackError } from 'drizzle-orm';
import type { Db } from '../db/client.js';
import { account, passwordReset, session, user } from '../db/schema.js';
import { hashPassword } from '../auth/password.js';
import type { MailLinks } from '../mail/links.js';
import { attemptDelivery, type MailDelivery } from '../mail/mail.js';
import { newToken, sha256 } from './tokens.js';

const RESET_TTL_MS = 24 * 3600_000;

/** What a self-serve request did; the route answers the same either way, so an unknown address learns nothing. */
export interface PasswordResetRequest {
  email: string;
  delivery: MailDelivery;
}

/**
 * Password reset links (ADR 0013): an admin issues one for a User and passes it on, like an
 * Invitation. Single-use, expires after 24 h; only the token's hash is stored. Using it sets the
 * new password and ends every session of that User.
 *
 * With mail configured (hosted plan §6.1) a User can request the link themselves; it replaces the
 * admin copy-link flow. Without mail everything stays as ADR 0013 describes.
 */
export function createPasswordResetService(db: Db, mail?: MailLinks) {
  const open = (tokenSha256: string) => and(
    eq(passwordReset.tokenSha256, tokenSha256), isNull(passwordReset.usedAt), isNull(passwordReset.revokedAt),
    gt(passwordReset.expiresAt, sql`now()`),
  );

  /** The User an open, usable link belongs to; null if unknown, used, revoked, expired or the User is disabled. */
  async function find(token: string) {
    const [row] = await db.select({ id: user.id, email: user.email, banned: user.banned }).from(passwordReset)
      .innerJoin(user, eq(user.id, passwordReset.userId)).where(open(sha256(token)));
    return row && !row.banned ? { userId: row.id, email: row.email } : null;
  }

  /**
   * A new link for the User. By default any earlier open link stops working; the self-serve request keeps them open
   * until its mail went out, so a failed send never costs the User the link they already had.
   */
  async function issue(createdBy: string, userId: string, { revokeEarlier = true } = {}) {
    const token = newToken();
    const row = await db.transaction(async (tx) => {
      if (revokeEarlier) {
        await tx.update(passwordReset).set({ revokedAt: new Date() })
          .where(and(eq(passwordReset.userId, userId), isNull(passwordReset.usedAt), isNull(passwordReset.revokedAt)));
      }
      const [created] = await tx.insert(passwordReset).values({
        tokenSha256: sha256(token), userId, createdBy, expiresAt: new Date(Date.now() + RESET_TTL_MS),
      }).returning();
      return created!;
    });
    return { reset: row, token };
  }

  return {
    find,
    issue,

    /** Whether this instance mails reset links and offers the self-serve request instead of admin copy links. */
    get selfServe() {
      return mail !== undefined;
    },

    /**
     * Self-serve: mail a new link to an existing, enabled User. Unknown or disabled addresses do nothing; the caller
     * answers the same for every address (no account enumeration). A send failure is returned for the route to log.
     */
    async request(email: string): Promise<PasswordResetRequest | null> {
      if (!mail) return null;
      const [target] = await db.select({ id: user.id, email: user.email, banned: user.banned }).from(user)
        .where(eq(user.email, email.trim().toLowerCase()));
      if (!target || target.banned) return null;
      // Self-issued: there is no admin behind the request, so the User is their own `createdBy` (ADR 0013's column).
      const { reset, token } = await issue(target.id, target.id, { revokeEarlier: false });
      const delivery = await attemptDelivery(() => mail.passwordReset({ to: target.email, token, expiresAt: reset.expiresAt }));
      if (delivery.sent) {
        // Only now does the new link replace the User's earlier one; until here an old link stayed usable.
        await db.update(passwordReset).set({ revokedAt: new Date() })
          .where(and(
            eq(passwordReset.userId, target.id), ne(passwordReset.id, reset.id),
            isNull(passwordReset.usedAt), isNull(passwordReset.revokedAt),
          ));
      } else {
        // Nobody received this token; an open row would be a link only the database knows.
        await db.delete(passwordReset).where(eq(passwordReset.id, reset.id));
      }
      return { email: target.email, delivery };
    },

    /**
     * Uses the link: sets the password and ends all of the User's sessions, in one transaction,
     * so a failure leaves the link usable. Returns the User's e-mail, or null if the link isn't usable.
     */
    async complete(token: string, password: string): Promise<string | null> {
      if (!(await find(token))) return null; // before hashing, so bad tokens cost no argon2 time
      const hash = await hashPassword(password);
      return db.transaction(async (tx) => {
        const [used] = await tx.update(passwordReset).set({ usedAt: new Date() }).where(open(sha256(token)))
          .returning({ userId: passwordReset.userId });
        if (!used) return null;
        const [target] = await tx.select({ email: user.email, banned: user.banned }).from(user).where(eq(user.id, used.userId));
        if (!target || target.banned) {
          tx.rollback();
          return null;
        }
        const updated = await tx.update(account).set({ password: hash, updatedAt: new Date() })
          .where(and(eq(account.userId, used.userId), eq(account.providerId, 'credential')))
          .returning({ id: account.id });
        if (updated.length === 0) {
          // No password yet (e.g. a future OIDC-only User): add one, the way Better Auth stores it.
          await tx.insert(account).values({
            userId: used.userId, accountId: used.userId, providerId: 'credential', password: hash, updatedAt: new Date(),
          });
        }
        await tx.delete(session).where(eq(session.userId, used.userId));
        return target.email;
      }).catch((error: unknown) => {
        if (error instanceof TransactionRollbackError) return null;
        throw error;
      });
    },
  };
}

export type PasswordResetService = ReturnType<typeof createPasswordResetService>;
