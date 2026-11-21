// A User's own Diver and display preferences (ADR 0053): the routes pass the User's id and hold no query.
import { and, eq } from 'drizzle-orm';
import type { Db } from '../db/client.js';
import { diver, diverManagement, user, userPreference } from '../db/schema.js';

type DisplayPreferences = Partial<Pick<typeof userPreference.$inferInsert, 'language' | 'units'>>;
type Preferences = DisplayPreferences & { mailOptOut?: boolean };

export function createProfileService(db: Db) {
  const readPreferences = async (userId: string) => {
    const [prefs] = await db.select().from(userPreference).where(eq(userPreference.userId, userId));
    const [account] = await db.select({ mailOptOut: user.mailOptOut }).from(user).where(eq(user.id, userId));
    return { language: prefs?.language ?? null, units: prefs?.units ?? null, mailOptOut: account?.mailOptOut ?? false };
  };

  return {
    read: async (userId: string) => {
      const [own] = await db.select({ id: diver.id, name: diver.name }).from(diverManagement)
        .innerJoin(diver, eq(diver.id, diverManagement.diverId))
        .where(and(eq(diverManagement.userId, userId), eq(diverManagement.isOwn, true)));
      return { ownDiver: own!, preferences: await readPreferences(userId) };
    },
    savePreferences: async (userId: string, input: Preferences) => {
      const { mailOptOut, ...display } = input;
      await db.transaction(async (tx) => {
        if (Object.keys(display).length > 0) {
          const changes = { ...display, updatedAt: new Date() };
          await tx.insert(userPreference).values({ userId, ...changes })
            .onConflictDoUpdate({ target: userPreference.userId, set: changes });
        }
        if (mailOptOut !== undefined) await tx.update(user).set({ mailOptOut }).where(eq(user.id, userId));
      });
      return readPreferences(userId);
    },
  };
}

export type ProfileService = ReturnType<typeof createProfileService>;
