// The one composition root: every service the server and the worker use (assessments, Providers, imports, logbook,
// Divers, centres, Dives, sites, planning and the rest), built in the order they use each other. start.ts, the server
// tests' createTestApp, the browser tests' server and write-openapi.ts all build them here, and differ only in the parts
// they pass in (adapters, sources, clock); buildApp in app.ts only registers the routes with them.
import { fileURLToPath } from 'node:url';
import type { Auth } from './auth/auth.js';
import type { Db } from './db/client.js';
import type { BillingConfig, EmailConfig, FcmConfig } from './config.js';
import { createBillingService } from './billing/billing-service.js';
import type { StripeFetch } from './billing/stripe.js';
import { createEntitlementService, type EntitlementService } from './entitlement/entitlement.js';
import { createMailLinks } from './mail/links.js';
import { createMailProvider, type MailFetch } from './mail/resend.js';
import type { DiverVisibility } from './vocabulary.js';
import type { FastifyBaseLogger } from 'fastify';
import { createAssessmentService } from './assessment/assessment-service.js';
import { createBackupService, type BackupLog } from './backups/backup-service.js';
import { createCentreService } from './centres/centre-service.js';
import { createCandidateService } from './dives/candidates/candidate-service.js';
import { createLogbookCheckService } from './dives/checks/logbook-check-service.js';
import { createAppendService } from './dives/continuation/append-service.js';
import { createContinuationService } from './dives/continuation/continuation-service.js';
import { createDeletionService } from './dives/deletion-service.js';
import { createDiveService } from './dives/dive-service.js';
import { createLogbookService } from './dives/logbook-service.js';
import { createMergeService } from './dives/merge-service.js';
import { createMergingService } from './dives/merging-service.js';
import { createPurgeService } from './dives/purge-service.js';
import { createDiverService } from './divers/diver-service.js';
import { createEquipmentService } from './equipment/equipment-service.js';
import { createImportService } from './imports/import-service.js';
import { listenToChanges } from './revisions/revisions.js';
import { createLive } from './live/service.js';
import { createNotificationService } from './notifications/notification-service.js';
import { createPushDispatcher } from './notifications/push-dispatcher.js';
import { withRevisionHints } from './live/revision-hints.js';
import { createAttemptLimit, SIGNUP_ATTEMPTS, TOKEN_ATTEMPTS } from './http/attempt-limit.js';
import { createLeadService } from './lead/lead-service.js';
import { createAiAccessService } from './mcp/access-service.js';
import { createPlanningService } from './planning/planning-service.js';
import type { Clock } from './providers/lease-service.js';
import { createProviderLayer } from './providers/layer.js';
import type { ProviderAdapter } from './providers/provider.js';
import { currentRemote } from './providers/remote-state.js';
import { ssiCentreDisplayName } from './providers/ssi/ssi-centre.js';
import type { ProviderLog } from './providers/waiting-push/waiting-push-queue.js';
import type { SecretBox } from './secrets/secret-box.js';
import { createSiteImportService } from './sites/import/site-import-service.js';
import { createSitePurgeService } from './sites/import/site-purge-service.js';
import type { SiteSourceAdapter } from './sites/import/site-source.js';
import { createSiteService } from './sites/site-service.js';
import type { ImportSource } from './sites/sources.js';
import { createOfferService } from './social/offer-service.js';
import { createSuggestionService } from './social/suggestion-service.js';
import { withSuggestionChanges } from './social/suggestion-changes.js';
import type { BlobStore } from './storage/blob-store.js';
import { createLogRingBuffer, type LogRingBuffer } from './support/log-ring-buffer.js';
import { createClientDiagnosticsService } from './support/client-diagnostics-service.js';
import { createSupportService } from './support/support-service.js';
import { createSyncService } from './sync/changes-service.js';
import { createInvitationService } from './users/invitation-service.js';
import { createPasswordResetService } from './users/password-reset-service.js';
import { createProfileService } from './users/profile-service.js';
import { createSessionService } from './users/session-service.js';
import { createSetupService } from './users/setup-service.js';
import { createUserAdminService } from './users/user-admin-service.js';
import type { WorkerDeps } from './worker.js';

export interface ServiceParts {
  db: Db;
  blobs: BlobStore;
  auth: Auth;
  secrets: SecretBox;
  /** Where the data volume is mounted; backups read it and use its `tmp/`. */
  dataDir: string;
  /** The database a backup dumps; production passes DIVEHUB's `loadConfig().databaseUrl`. */
  databaseUrl: string;
  /** DIVEHUB_ENCRYPTION_KEY, whose fingerprint a backup manifest records (never the key itself). */
  encryptionKey?: Buffer | undefined;
  /** The Providers of this instance (ADR 0027): the production SSI, or the fake and test-only ones. */
  adapters: ProviderAdapter[];
  /** The enabled Site Sources (DIVEHUB_SITE_SOURCES, ADR 0087); a missing key is a Source this instance does not offer. */
  siteSources: Partial<Record<ImportSource, SiteSourceAdapter>>;
  /** The clock of leases and attempt limits (default: real time). */
  clock?: Clock;
  /** Where the provider layer's own faults go (the app's logger). */
  log?: ProviderLog;
  /** Where the live layer's LISTEN connection comes from (ADR 0066); without it, the live route is not mounted. */
  live?: { connectionString: string };
  /** The log stream the support bundle captures; start.ts passes the one it gives Fastify. */
  logBuffer?: LogRingBuffer;
  /** Whether this process runs the worker, for the support bundle's health section. */
  workerRunning?: () => boolean;
  /** FCM push config (ADR 0085); undefined turns push off and native clients keep polling. */
  fcm?: FcmConfig | undefined;
  /** Transactional mail (hosted plan §6.1); undefined keeps invitations and resets as copy links (ADR 0012). */
  email?: EmailConfig | undefined;
  /** DIVEHUB_REGISTRATION_OPEN (hosted plan §6.2); the auth handler exposes sign-up only when true. */
  registrationOpen?: boolean;
  /** Public origin the mailed links point to; required when `email` is set. */
  baseUrl?: string;
  /** Where the Resend HTTP goes; tests pass a stand-in, production the global fetch. */
  mailFetch?: MailFetch;
  /** Resend's endpoint; only tests point it elsewhere. */
  mailUrl?: string;
  /** Billing (hosted-DiveHub plan §4, ADR 0087); undefined makes every feature free and the entitlement gates inert. */
  billing?: BillingConfig | undefined;
  /** Where Stripe's HTTP goes; tests pass a stand-in, production the global fetch. */
  stripeFetch?: StripeFetch;
  /** Stripe's API base; only tests point it elsewhere (test and live keys share one endpoint). */
  stripeUrl?: string;
  /** Who may see a Diver's contact details (ADR 0088); `instance` when not set, today's self-hosted behaviour. */
  diverVisibility?: DiverVisibility | undefined;
}

/** Transactional mail for this instance, or nothing when `DIVEHUB_EMAIL_*` is unset (hosted plan §6.1). */
function createMail(options: {
  email?: EmailConfig | undefined; baseUrl?: string | undefined; mailFetch?: MailFetch | undefined; mailUrl?: string | undefined;
}) {
  if (!options.email) return undefined;
  if (!options.baseUrl) throw new Error('email is configured but createServices got no baseUrl for its links');
  const mailer = createMailProvider(options.email, {
    ...(options.mailFetch && { fetch: options.mailFetch }), ...(options.mailUrl && { url: options.mailUrl }),
  });
  return createMailLinks({ mailer, baseUrl: options.baseUrl });
}

/** Billing for this instance, or nothing when `DIVEHUB_BILLING_PROVIDER` is unset (hosted plan §4, ADR 0092). */
function createBilling(
  config: BillingConfig | undefined, entitlements: EntitlementService, log: ProviderLog | undefined,
  fetch: StripeFetch | undefined, url: string | undefined, baseUrl: string | undefined,
) {
  if (!config) return undefined;
  if (!baseUrl) throw new Error('billing is configured but createServices got no baseUrl for its return links');
  return createBillingService({
    config, entitlements, baseUrl,
    ...(log ? { log } : {}), ...(fetch ? { fetch } : {}), ...(url ? { url } : {}),
  });
}

export function createServices({
  db, blobs, auth, secrets, adapters, siteSources, clock, log, live: liveSettings, dataDir, databaseUrl, encryptionKey,
  logBuffer = createLogRingBuffer({ stdout: { write: () => true } }), workerRunning, fcm, email, registrationOpen = false,
  baseUrl, mailFetch, mailUrl, billing, stripeFetch, stripeUrl, diverVisibility,
}: ServiceParts) {
  const now = clock ? () => clock.now() : undefined;
  const mail = createMail({ email, baseUrl, mailFetch, mailUrl });
  const assessments = createAssessmentService(db);
  const backupLog: BackupLog = {
    info: () => {},
    warn: (detail, message) => log?.warn(detail, message),
    error: (detail, message) => log?.error(detail, message),
  };
  const backups = createBackupService({
    db, blobs, dataDir, databaseUrl,
    migrationsFolder: fileURLToPath(new URL('../drizzle', import.meta.url)), appVersion: process.env.DIVEHUB_VERSION ?? 'dev',
    encryptionKey, log: backupLog, now,
  });
  const clientDiagnostics = createClientDiagnosticsService(db);
  const sync = createSyncService(db);
  const support = createSupportService({
    db, buffer: logBuffer, migrationsFolder: fileURLToPath(new URL('../drizzle', import.meta.url)),
    appVersion: process.env.DIVEHUB_VERSION ?? 'dev', databaseUrl, dataDir, encryptionKey, clientDiagnostics,
    ...(workerRunning && { workerRunning }),
  });
  // The paywall (hosted-DiveHub plan §4): with no billing configured it answers entitled for everything (ADR 0087).
  const entitlements = createEntitlementService({ db, enforced: billing !== undefined, ...(clock && { clock }) });
  // Stripe's checkout/portal links and signed webhook (hosted-DiveHub plan §4.5, ADR 0092); absent with no billing config,
  // which also leaves the billing routes unregistered.
  const billingService = createBilling(billing, entitlements, log, stripeFetch, stripeUrl, baseUrl);
  const providers = createProviderLayer({
    db, blobs, secrets, adapters, entitlements, ...(clock && { clock }), ...(log && { log }), diverVisibility,
  });
  // Push hints queue in the same writing transactions as the SSE hints (ADR 0085); without FCM the dispatcher queues
  // nothing and the job is a no-op.
  const push = createPushDispatcher({ db, fcm });
  // Dive and site Revisions also tell open pages (ADR 0066), through the same listener seam as the Waiting pushes.
  // Buddy suggestions hang off the same seam (ADR 0070): the listener runs inside every Participant write's transaction.
  listenToChanges(withSuggestionChanges(withRevisionHints(providers.noteChanges, (q, hint) => push.enqueue(q, hint))));
  const live = createLive({ ...(liveSettings && { connectionString: liveSettings.connectionString }), ...(log && { log }) });
  const notifications = createNotificationService(db, fcm);
  // Imports of a Provider's dives run in the worker like uploads (ADR 0030).
  const imports = createImportService({ db, blobs, providerImports: providers.diveImports, afterImport: (userId) => assessments.refreshUser(userId) });
  const { pushes, waitingPushes } = providers;
  const logbook = createLogbookService(db);
  const checks = createLogbookCheckService(db);
  const merging = createMergingService(db, checks, { currentRemote, deletingItself: waitingPushes.deletingItself });
  const continuation = createContinuationService(db, { currentRemote, deletingItself: waitingPushes.deletingItself });
  // Restoring a Dive an append took undoes the append (ADR 0063).
  const dives = createDiveService(db, continuation);
  return {
    db, blobs, auth, assessments, backups, support, clientDiagnostics, sync, entitlements, billing: billingService, providers, imports, logBuffer, registrationOpen,
    siteImports: createSiteImportService({ db, sources: siteSources }),
    sitePurges: createSitePurgeService({ db, sources: siteSources }),
    aiAccesses: createAiAccessService(db, auth),
    setup: createSetupService(db, now),
    invitations: createInvitationService(db, mail),
    passwordResets: createPasswordResetService(db, mail),
    // One count per client IP across the public token routes (ADR 0061).
    tokenAttempts: createAttemptLimit(TOKEN_ATTEMPTS, now),
    // Its own count, so a signup flood cannot exhaust the token routes' budget (hosted plan §6.2).
    signupAttempts: createAttemptLimit(SIGNUP_ATTEMPTS, now),
    profiles: createProfileService(db),
    sessions: createSessionService(db),
    userAdmin: createUserAdminService(db, blobs),
    logbook, dives, checks, merging,
    suggestions: createSuggestionService(db),
    offers: createOfferService(db),
    divers: createDiverService(db, { visibility: diverVisibility }),
    equipment: createEquipmentService(db),
    centres: createCentreService(db, { ssi: ssiCentreDisplayName }),
    deletion: createDeletionService({ dives, logbook, pushes, waitingPushes }),
    purge: createPurgeService(db, { pushes }),
    merge: createMergeService({ merging, logbook, pushes }),
    continuation,
    append: createAppendService({ continuation, logbook, pushes, waitingPushes }),
    candidates: createCandidateService(db),
    planning: createPlanningService(db),
    lead: createLeadService(db),
    sites: createSiteService(db),
    live, notifications, push,
  };
}

export type Services = ReturnType<typeof createServices>;

/** What the worker's tasks use, from the services. */
export const workerDeps = (s: Services, log: FastifyBaseLogger): WorkerDeps => ({
  backups: s.backups, imports: s.imports, siteImports: s.siteImports, sitePurges: s.sitePurges, aiAccesses: s.aiAccesses, assessments: s.assessments, pushRuns: s.providers.pushRuns,
  waitingPushes: s.providers.waitingPushes, scheduledFetch: s.providers.scheduledFetch, certifications: s.providers.certifications,
  purges: s.purge, clientDiagnostics: s.clientDiagnostics, sync: s.sync, push: s.push, log,
});
