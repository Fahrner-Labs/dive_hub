// Offers to move an account's dives from a private site ID to the site's public counterpart (FAH-121). The offer itself
// is worked out from `dive_site_external_id` (a private row and a public row of the same site and Source); only the
// User's answer is stored, so a public ID that disappears stops the offer without cleanup and a `keep` answer silences
// it for that site and account. Nothing here calls a Provider.
import { and, eq, inArray, isNull } from 'drizzle-orm';
import type { Db } from '../db/client.js';
import { diveSite, diver, diverExternalId, diverManagement, siteIdDecision } from '../db/schema.js';
import { SiteError } from './site-error.js';
import { accountOf, accountsOfUser, clearId, lockPrivateIdWithPublic, privateIdsWithPublicIds } from './site-ids.js';
import { SOURCE_INFO, type SiteSource } from './sources.js';

export type SiteIdDecisionChoice = 'move' | 'keep';

/** One offer: the site, the account (one Diver) and both IDs, so a client may show them to that Diver's Users. */
export interface SiteIdDecisionView {
  siteId: string;
  siteName: string;
  source: SiteSource;
  providerName: string;
  diverId: string;
  diverName: string;
  privateExternalId: string;
  publicExternalId: string;
}

const keyOf = (siteId: string, source: string, accountId: string) => `${siteId} ${source} ${accountId}`;

export function createSiteIdDecisionService(db: Db) {
  /** Whether the User manages the live Diver. */
  async function manages(userId: string, diverId: string): Promise<boolean> {
    const [row] = await db.select({ isOwn: diverManagement.isOwn }).from(diverManagement)
      .innerJoin(diver, eq(diver.id, diverManagement.diverId))
      .where(and(eq(diverManagement.userId, userId), eq(diverManagement.diverId, diverId), isNull(diver.deletedAt)));
    return !!row;
  }

  return {
    /**
     * The offers that wait for the User: one per site and account, for the accounts of the Divers the User manages.
     * A `keep` answer leaves its offer out; an account whose private ID or site's public ID is gone has none.
     */
    async waiting(userId: string): Promise<SiteIdDecisionView[]> {
      const accounts = await accountsOfUser(db, userId);
      const pairs = await privateIdsWithPublicIds(db, accounts);
      if (pairs.length === 0) return [];
      const [sites, divers, kept] = await Promise.all([
        db.select({ id: diveSite.id, name: diveSite.name }).from(diveSite)
          .where(inArray(diveSite.id, [...new Set(pairs.map((p) => p.siteId))])),
        db.select({ accountId: diverExternalId.id, diverId: diverExternalId.diverId, diverName: diver.name })
          .from(diverExternalId).innerJoin(diver, eq(diver.id, diverExternalId.diverId))
          .where(and(inArray(diverExternalId.id, accounts), isNull(diver.deletedAt))),
        db.select({ siteId: siteIdDecision.siteId, source: siteIdDecision.source, accountId: siteIdDecision.accountId })
          .from(siteIdDecision)
          .where(and(inArray(siteIdDecision.accountId, accounts), eq(siteIdDecision.choice, 'keep'))),
      ]);
      const siteName = new Map(sites.map((s) => [s.id, s.name]));
      const diverOf = new Map(divers.map((d) => [d.accountId, d]));
      const keptOffers = new Set(kept.map((k) => keyOf(k.siteId, k.source, k.accountId)));
      return pairs.flatMap((p) => {
        const d = diverOf.get(p.accountId);
        const name = siteName.get(p.siteId);
        if (!d || name === undefined || keptOffers.has(keyOf(p.siteId, p.source, p.accountId))) return [];
        return [{
          siteId: p.siteId, siteName: name, source: p.source, providerName: SOURCE_INFO[p.source].name,
          diverId: d.diverId, diverName: d.diverName, privateExternalId: p.privateExternalId, publicExternalId: p.publicExternalId,
        }];
      }).sort((a, b) => a.siteName.localeCompare(b.siteName)
        || a.providerName.localeCompare(b.providerName) || a.diverName.localeCompare(b.diverName));
    },

    /**
     * Answers an offer. `move` removes the account's private ID, so its Dives use the public ID from the next send on
     * (a change the send sees as "changed since sent"); `keep` leaves the private ID and stops the offer for this site
     * and account. The offer must still wait, with the private ID the User was shown.
     */
    async decide(
      userId: string, siteId: string, source: SiteSource, diverId: string, privateExternalId: string, choice: SiteIdDecisionChoice,
    ): Promise<void> {
      // The same answer for a Diver the User doesn't manage as for one without this offer: neither names what others keep.
      if (!(await manages(userId, diverId))) throw new SiteError('site_id_decision_not_waiting');
      const account = await accountOf(db, diverId, source);
      if (!account) throw new SiteError('site_id_decision_not_waiting');
      await db.transaction(async (tx) => {
        // Locked: another tab may have moved it, or the public counterpart may be gone since the page was read.
        const offered = await lockPrivateIdWithPublic(tx, siteId, source, account);
        if (offered === null || offered !== privateExternalId) throw new SiteError('site_id_decision_not_waiting');
        await tx.insert(siteIdDecision)
          .values({ siteId, source, accountId: account, choice, decidedBy: userId })
          .onConflictDoUpdate({
            target: [siteIdDecision.siteId, siteIdDecision.source, siteIdDecision.accountId],
            set: { choice, decidedBy: userId, decidedAt: new Date() },
          });
        if (choice === 'move') await clearId(tx, siteId, source, account, null);
      });
    },
  };
}

export type SiteIdDecisionService = ReturnType<typeof createSiteIdDecisionService>;
