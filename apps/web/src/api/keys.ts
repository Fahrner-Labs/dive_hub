/** Query keys in one place (hierarchical, so invalidating ['dives'] covers every dive query). */
export const keys = {
  me: ['me'] as const,
  setup: ['setup'] as const,
  setupRestoreArchives: ['setup', 'restore', 'archives'] as const,
  users: ['users'] as const,
  invitations: ['invitations'] as const,
  /** Whether this instance mails invitations and self-serve password reset links (hosted plan §6.1). */
  emailConfig: ['email-config'] as const,
  /** What an invitation or password reset link opens (ADR 0013); not under ['invitations'], the admin's list. */
  invitationLink: (token: string) => ['invitation', token] as const,
  passwordReset: (token: string) => ['password-reset', token] as const,
  sessions: ['me', 'sessions'] as const,
  imports: ['imports'] as const,
  dives: ['dives'] as const,
  dive: (id: string) => ['dives', id] as const,
  /** Under ['dives'], so whatever changes the logbook refreshes it too (ADR 0026). */
  deletedDives: ['dives', 'deleted'] as const,
  revisions: (id: string) => ['dives', id, 'revisions'] as const,
  /** Logbook checks (ADR 0038); under ['dives'], so whatever changes the logbook computes them again. */
  logbookChecks: (status: 'open' | 'answered') => ['dives', 'checks', status] as const,
  /** Dives this one may be merged with (ADR 0038); under the Dive, so whatever changes it asks again. */
  mergeCandidates: (id: string) => ['dives', id, 'merge-candidates'] as const,
  /** The Dive before this one it may be appended to (ADR 0063); under the Dive, so whatever changes it asks again. */
  continuationCandidate: (id: string) => ['dives', id, 'continuation-candidate'] as const,
  /** The Dives restoring this deleted one brings back (ADR 0063); under ['dives'], so a change to the logbook asks again. */
  restorePreview: (id: string) => ['dives', id, 'restore-preview'] as const,
  /** The Dive's assessment (ADR 0036); under the Dive, so whatever changes it reads the findings again. */
  assessment: (id: string) => ['dives', id, 'assessment'] as const,
  divers: ['divers'] as const,
  devices: ['devices'] as const,
  /** Equipment items and their service schedules (ADR 0034). A Dive's gear sits under the Dive, so it follows it. */
  equipment: ['equipment'] as const,
  equipmentDue: ['equipment', 'due'] as const,
  equipmentOfDiver: (diverId: string) => ['equipment', 'diver', diverId] as const,
  equipmentItem: (id: string) => ['equipment', id] as const,
  diveGear: (diveId: string) => ['dives', diveId, 'equipment'] as const,
  candidates: (status: 'open' | 'discarded') => ['candidates', status] as const,
  /** Buddy suggestions (ADR 0070); a preview sits under the list, so accepting or declining refreshes both. */
  buddySuggestions: ['buddy-suggestions'] as const,
  buddySuggestion: (id: string) => ['buddy-suggestions', id] as const,
  /** Joint-dive fill offers (FAH-411, ADR 0070): the Review list, and one Dive's inline offers under it. */
  jointOffers: ['joint-offers'] as const,
  jointOffersOf: (diveId: string) => ['joint-offers', 'dive', diveId] as const,
  samples: (recordingId: string) => ['recordings', recordingId, 'samples'] as const,
  sites: ['sites'] as const,
  site: (id: string) => ['sites', id] as const,
  siteRevisions: (id: string) => ['sites', id, 'revisions'] as const,
  /** Dive centres (ADR 0043). A Dive's codes follow them: whatever changes a centre also refreshes ['dives']. */
  centres: ['centres'] as const,
  centre: (id: string) => ['centres', id] as const,
  siteImports: ['site-imports'] as const,
  /** Admin backups (plan FAH-175): the run list, and the scheduled-backup setting under it. */
  backups: ['backups'] as const,
  backupSettings: ['backups', 'settings'] as const,
  /** The on-demand support bundle's preview (FAH-291); built fresh each time an admin asks. */
  supportBundlePreview: ['support-bundle', 'preview'] as const,
  /** Whether this instance accepts client diagnostics (ADR 0080), re-read before every upload. */
  clientDiagnosticsStatus: ['client-diagnostics', 'status'] as const,
  /** Admins: the instance's client-diagnostics switch. */
  clientDiagnosticsSetting: ['admin', 'client-diagnostics'] as const,
  providers: ['providers'] as const,
  connections: ['connections'] as const,
  /** Dives gone at a Provider, waiting for the User's choice (ADR 0051). */
  goneDives: ['gone-dives'] as const,
  /** Conflicts with a Provider waiting for the User's choice (ADR 0056); a send from the Dive's page closes them. */
  providerConflicts: ['provider-conflicts'] as const,
  /** Push runs (ADR 0047): the User's list, one run with its items, and what a start would do. */
  pushRuns: ['push-runs'] as const,
  pushRun: (id: string) => ['push-runs', id] as const,
  pushRunPreview: (connectionId: string, body: unknown) => ['push-run-preview', connectionId, body] as const,
  /** The Dive at every Provider; one Provider's state sits under it, so refreshing this refreshes both. */
  diveProviders: (diveId: string) => ['dives', diveId, 'providers'] as const,
  providerStatus: (diveId: string, provider: string) => ['dives', diveId, 'providers', provider] as const,
  providerSites: (diveId: string, provider: string) => ['dives', diveId, 'providers', provider, 'sites'] as const,
  /** Every Diver of the instance by name (ADR 0028); under ['divers'], so changing a Diver refreshes searches. */
  diverSearch: (q: string) => ['divers', 'search', q] as const,
  externalDivers: (q: string) => ['divers', 'external', q] as const,
  /** A Diver's details and codes (ADR 0043); under ['divers']. */
  diverDetails: (id: string) => ['divers', id, 'details'] as const,
  /** A Diver's current loadings (ADR 0071); under ['dives'], so a change to the logbook asks again. */
  loadings: (diverId: string) => ['dives', 'loadings', diverId] as const,
  /** A Diver's body weights (ADR 0031); only for the Users who keep the Diver. */
  bodyWeights: (id: string) => ['divers', id, 'body-weights'] as const,
  certifications: (id: string) => ['divers', id, 'certifications'] as const,
  /** The account's list of people at a Provider; under ['connections']. */
  buddies: (connectionId: string) => ['connections', connectionId, 'buddies'] as const,
  buddiesFor: (connectionId: string, diverId: string) => ['connections', connectionId, 'buddies', diverId] as const,
  /** What importing the account's dives would do (ADR 0030); under ['connections']. */
  diveImport: (connectionId: string) => ['connections', connectionId, 'dive-import'] as const,
  diveTimes: (connectionId: string) => ['connections', connectionId, 'dive-times'] as const,
  importOf: (id: string) => ['imports', id] as const,
  /** Admins: whether Dive sites may be made from a Provider's site data (ADR 0030). */
  siteData: ['admin', 'provider-site-data'] as const,
  /** The User's AI accesses to the MCP endpoint (ADR 0035), and what they read. */
  aiAccess: ['me', 'ai-access'] as const,
  aiAccessLog: ['me', 'ai-access', 'log'] as const,
  /** Admins: the instance's switch for the MCP endpoint. */
  aiAccessSetting: ['admin', 'ai-access'] as const,
};
