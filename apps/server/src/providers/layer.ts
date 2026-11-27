// The provider layer of one app (ADR 0027): the registry, Connections, Pushes and their leases, the account's list
// of people (ADR 0029), importing its dives (ADR 0030), also on a schedule, and the Waiting pushes the worker sends (ADR 0056), built from
// the adapters this instance has. main.ts passes the production adapters; tests pass the fake SSI and a test-only adapter.
import type { Db, Tx } from '../db/client.js';
import type { EntitlementService } from '../entitlement/entitlement.js';
import { createDiverService } from '../divers/diver-service.js';
import type { SecretBox } from '../secrets/secret-box.js';
import type { BlobStore } from '../storage/blob-store.js';
import type { DiverVisibility } from '../vocabulary.js';
import { createCertificationService } from '../certifications/certification-service.js';
import type { CertificationProviders } from '../certifications/certification-providers.js';
import { createBuddyService } from './buddies/buddy-service.js';
import { createConflictService } from './conflicts/conflict-service.js';
import { createConnectionService } from './connection-service.js';
import { createDiveImportService } from './dive-import/dive-import.js';
import { createGoneDiveService } from './dive-import/gone-dives.js';
import { createLeaseService, type Clock } from './lease-service.js';
import { ProviderError, type ProviderAdapter } from './provider.js';
import { createPushRunService } from './push/push-run-service.js';
import { createPushService } from './push/push-service.js';
import { createProviderRegistry, named, ProviderServiceError } from './registry.js';
import { createScheduledFetch } from './dive-import/scheduled-fetch.js';
import { createSitePickService } from './site-pick.js';
import { createWaitingPushService, type ProviderLog } from './waiting-push/waiting-push-service.js';
import { consoleLog, noteDiveChange, noteSiteChange } from './waiting-push/waiting-push-queue.js';
import { listenToChanges, type Actor, type RevisionCause } from '../revisions/revisions.js';

export function createProviderLayer(deps: {
  db: Db; secrets: SecretBox; blobs: BlobStore; adapters: ProviderAdapter[]; entitlements: EntitlementService; clock?: Clock;
  /** Where the worker's own faults go (the app's logger); without one, the console (consoleLog). */
  log?: ProviderLog;
  /** Who may see a Diver's contact details (ADR 0088); instance when not set. */
  diverVisibility?: DiverVisibility | undefined;
}) {
  // Changes to Dives and sites reach the Waiting push queue (ADR 0056) and refresh the stored fingerprints the
  // logbook's chip compares (ADR 0040, amended) through the listener dives/ owns. Every site Revision also announces
  // itself to open pages (ADR 0066), which compose.ts adds.
  const providers = createProviderRegistry(deps.adapters);
  const clock = deps.clock && { clock: deps.clock };
  // One fallback for the whole layer: every service that logs is handed the same logger.
  const log = deps.log ?? consoleLog;
  const leases = createLeaseService({ db: deps.db, ...clock, log });
  const { entitlements } = deps;
  const connections = createConnectionService({ db: deps.db, registry: providers, secrets: deps.secrets, leases, entitlements, ...clock });
  const conflicts = createConflictService({ db: deps.db, connections, registry: providers, blobs: deps.blobs });
  const pushes = createPushService({ db: deps.db, blobs: deps.blobs, registry: providers, connections, leases, conflicts, ...clock, log });
  // A Dive's Revision queues what goes to Providers by itself (ADR 0056) and refreshes the stored fingerprint the
  // logbook's chip compares (ADR 0040, amended), in the change's own transaction.
  const noteChanges = {
    async diveChanged(tx: Tx, diveId: string, cause: RevisionCause, at: Date, actor?: Actor) {
      await noteDiveChange(tx, diveId, cause, at, actor);
      await pushes.refreshFingerprints(tx, diveId);
    },
    async siteRevisionWritten(tx: Tx, siteId: string) {
      await pushes.refreshSiteFingerprints(tx, siteId);
    },
    siteChanged: noteSiteChange,
    async fingerprintInputsChanged(tx: Tx, diverIds: string[]) {
      await pushes.markFingerprintsStale(tx, diverIds);
    },
    async fingerprintSiteInputsChanged(tx: Tx, siteId: string) {
      await pushes.markSiteFingerprintsStale(tx, siteId);
    },
  };
  listenToChanges(noteChanges);
  const pushRuns = createPushRunService({ db: deps.db, registry: providers, pushes, conflicts, log });
  const waitingPushes = createWaitingPushService({ db: deps.db, registry: providers, pushes, entitlements, ...clock, log });
  const buddies = createBuddyService({ db: deps.db, registry: providers, connections, divers: createDiverService(deps.db, { visibility: deps.diverVisibility }), log });
  const sitePicks = createSitePickService({ db: deps.db, registry: providers, connections });
  const diveImports = createDiveImportService({ db: deps.db, blobs: deps.blobs, registry: providers, connections, ...clock });
  const scheduledFetch = createScheduledFetch({ db: deps.db, registry: providers, diveImports, entitlements, ...clock, log });
  const goneDives = createGoneDiveService({ db: deps.db, registry: providers, connections, leases });
  // certifications owns the port it needs (ADR 0053's seam for certifications and providers); this layer wires the
  // real registry and Connections to it.
  const certificationProviders: CertificationProviders = {
    async onConnection(userId, connectionId) {
      const connection = await connections.own(userId, connectionId);
      const adapter = providers.get(connection.provider);
      if (!adapter.certifications) throw new ProviderServiceError('provider_unsupported', named(adapter));
      return {
        provider: connection.provider,
        diverId: connection.diverId,
        cards: () => connections.withAccess(connection, (context) => adapter.certifications!.find(context)),
      };
    },
    supports: (provider) => !!providers.get(provider).certifications,
    image(provider, url) {
      const adapter = providers.get(provider);
      if (!adapter.certifications) throw new ProviderServiceError('provider_unsupported', named(adapter));
      return adapter.certifications.image(url);
    },
    isProviderRefusal: (error) => error instanceof ProviderError || error instanceof ProviderServiceError,
  };
  const certifications = createCertificationService({ db: deps.db, blobs: deps.blobs, providers: certificationProviders });
  return {
    providers, connections, pushes, pushRuns, waitingPushes, buddies, sitePicks, diveImports, scheduledFetch, goneDives, conflicts, certifications,
    // What the layer does when a Revision or a site changes; compose.ts wraps it with the live hints (ADR 0066).
    noteChanges,
  };
}

export type ProviderLayer = ReturnType<typeof createProviderLayer>;
