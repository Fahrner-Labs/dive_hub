# Graph Report - dive_hub  (2026-11-23)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 11480 nodes · 35028 edges · 365 communities (226 shown, 139 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 689 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `c748712f`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- Community 0
- Community 1
- Community 2
- Community 3
- Community 4
- Community 5
- Community 6
- Community 7
- Community 8
- Community 9
- Community 10
- Community 11
- Community 12
- Community 13
- Community 14
- Community 15
- Community 16
- Community 17
- Community 18
- Community 19
- Community 20
- Community 21
- Community 22
- Community 23
- Community 24
- Community 25
- Community 26
- Community 27
- Community 28
- Community 29
- Community 30
- Community 31
- Community 32
- Community 33
- Community 34
- Community 35
- Community 36
- Community 37
- Community 38
- Community 39
- Community 40
- Community 41
- Community 42
- Community 43
- Community 44
- Community 45
- Community 46
- Community 47
- Community 48
- Community 49
- Community 50
- Community 51
- Community 52
- Community 53
- Community 54
- Community 55
- Community 56
- Community 57
- Community 58
- Community 59
- Community 60
- Community 61
- Community 62
- Community 63
- Community 64
- Community 65
- Community 66
- Community 67
- Community 68
- Community 69
- Community 70
- Community 71
- Community 72
- Community 73
- Community 74
- Community 75
- Community 76
- Community 77
- Community 78
- Community 79
- Community 80
- Community 81
- Community 82
- Community 83
- Community 84
- Community 85
- Community 86
- Community 87
- Community 88
- Community 89
- Community 90
- Community 91
- Community 92
- Community 93
- Community 94
- Community 95
- Community 96
- Community 97
- Community 98
- Community 99
- Community 100
- Community 101
- Community 102
- Community 103
- Community 104
- Community 105
- Community 106
- Community 107
- Community 108
- Community 109
- Community 110
- Community 111
- Community 112
- Community 113
- Community 114
- Community 115
- Community 116
- Community 117
- Community 118
- Community 119
- Community 120
- Community 121
- Community 122
- Community 123
- Community 124
- Community 125
- Community 126
- Community 127
- Community 128
- Community 129
- Community 130
- Community 131
- Community 132
- Community 133
- Community 134
- Community 135
- Community 136
- Community 137
- Community 138
- Community 139
- Community 140
- Community 141
- Community 142
- Community 143
- Community 144
- Community 145
- Community 146
- Community 147
- Community 148
- Community 149
- Community 150
- Community 151
- Community 152
- Community 153
- Community 154
- Community 155
- Community 156
- Community 157
- Community 158
- Community 159
- Community 160
- Community 161
- Community 162
- Community 163
- Community 164
- Community 165
- Community 166
- Community 167
- Community 168
- Community 169
- Community 170
- Community 171
- Community 172
- Community 173
- Community 174
- Community 175
- Community 176
- Community 177
- Community 178
- Community 179
- Community 180
- Community 181
- Community 182
- Community 183
- Community 184
- Community 185
- Community 186
- Community 187
- Community 188
- Community 189
- Community 190
- Community 191
- Community 192
- Community 193
- Community 194
- Community 195
- Community 196
- Community 197
- Community 198
- Community 199
- Community 200
- Community 201
- Community 202
- Community 203
- Community 204
- Community 205
- Community 206
- Community 207
- Community 208
- Community 209
- Community 210
- Community 211
- Community 212
- Community 213
- Community 214
- Community 215
- Community 216
- Community 217
- Community 218
- Community 219
- Community 220
- Community 221
- Community 222
- Community 223
- Community 224
- Community 225
- Community 226
- Community 227
- Community 228
- Community 229
- Community 230
- Community 231
- Community 232
- Community 233
- Community 234
- Community 235
- Community 236
- Community 237
- Community 239
- Community 240
- Community 241
- Community 242
- Community 243
- Community 247

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 211 edges
2. `Button()` - 204 edges
3. `useErrorText()` - 176 edges
4. `useDisplay()` - 165 edges
5. `drizzle-orm` - 147 edges
6. `unwrap()` - 143 edges
7. `Muted()` - 139 edges
8. `createTestDatabase()` - 108 edges
9. `TestDatabase` - 107 edges
10. `announce()` - 107 edges

## Surprising Connections (you probably didn't know these)
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `Removal` --references--> `ProviderServiceError`  [EXTRACTED]
  apps/server/src/providers/push/push-service.ts → apps/server/src/providers/registry.ts
- `AuthOptions` --references--> `Db`  [EXTRACTED]
  apps/server/src/auth/auth.ts → apps/server/src/db/client.ts

## Import Cycles
- None detected.

## Communities (365 total, 139 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.01
Nodes (282): account, user, createDb(), findChangedMigrations(), migrateDatabase(), ADR-0004, backupRun, buddySuggestion (+274 more)

### Community 1 - "Community 1"
Cohesion: 0.02
Nodes (179): createServer(), createAuth(), auth, hashPassword(), loadAuthSecret(), acquireAppLock(), BackupServiceDeps, createServices() (+171 more)

### Community 2 - "Community 2"
Cohesion: 0.04
Nodes (218): DisplaySettings(), Sessions(), AdminSettings(), ClientDiagnosticsSetting(), DeleteUserDialog(), Invitations(), Invite(), LinkToPassOn() (+210 more)

### Community 3 - "Community 3"
Cohesion: 0.02
Nodes (176): APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), AppLockProbe, probeAppLock() (+168 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (166): supportBundlePreviewQuery(), bodyWeightsQuery(), diverDetailsQuery(), diversQuery(), DiverView, externalDiversQuery(), RequirementView, centreQuery() (+158 more)

### Community 5 - "Community 5"
Cohesion: 0.02
Nodes (164): accountListEntry, cylinder, device, dive, diveLease, diver, diverBodyWeight, diverExternalId (+156 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (156): LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, renumber(), renumberStatement(), ADR-0048, managesDiveDiver(), waterMismatch() (+148 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (166): cylinderCatalogueQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas (+158 more)

### Community 8 - "Community 8"
Cohesion: 0.02
Nodes (156): Me, diveNeighboursQuery(), diveQuery(), divesQuery(), DiveSummary, DiveValues, LogbookPage, OverridableField (+148 more)

### Community 9 - "Community 9"
Cohesion: 0.02
Nodes (135): BACKUP_RUN_TASK, backupTask(), ADR-0069, connection, ConnectionRow, continuationAppend, importJob, providerConflict (+127 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (146): assessmentQuery(), AssessmentView, samplesQuery(), ToolsPlan, ToolsPlanDiver, AsideItem(), AssessmentContext, AssessmentPanel() (+138 more)

### Community 11 - "Community 11"
Cohesion: 0.02
Nodes (150): downsampleMinMax(), Nullable(), AiAccessScope, seesPositions(), ProfileSummary, round1(), Series, summariseProfile() (+142 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (132): label(), AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView (+124 more)

### Community 13 - "Community 13"
Cohesion: 0.02
Nodes (127): CentreService, CentreRouteDeps, LEAD_PLACEMENTS, UTC_OFFSET_SOURCES, WEIGHTING_FELT, DiverService, DiverRouteDeps, CertificationHint (+119 more)

### Community 14 - "Community 14"
Cohesion: 0.02
Nodes (114): ParticipantRole, Direction, DIVE_FIELDS, DiveExport, DiveListing, FIND_BY, FindBy, Operation (+106 more)

### Community 15 - "Community 15"
Cohesion: 0.04
Nodes (123): EMPTY_SITE_IMPORT_COUNTS, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, diveSite (+115 more)

### Community 16 - "Community 16"
Cohesion: 0.02
Nodes (96): ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS, eachFile(), readEntry(), ADR-0044, walk() (+88 more)

### Community 17 - "Community 17"
Cohesion: 0.02
Nodes (94): BackupRunView, BackupSettingsView, SiteDataView, SupportBundlePreviewView, ADR-0030, ClientDiagnosticsSettingsView, clientDiagnosticsStatusQuery(), ClientDiagnosticsStatusView (+86 more)

### Community 18 - "Community 18"
Cohesion: 0.04
Nodes (104): logbookCheckAnswer, logbookCheckDiveAnswer, keepDive(), ADR-0038, agreeInDepthAndDuration(), CheckedDive, DeletableDive, findChecks() (+96 more)

### Community 19 - "Community 19"
Cohesion: 0.02
Nodes (103): actorType, aiAccessOutcome, backupRunKind, backupRunStatus, backupSettings, candidateResolution, centreSource, certificationImageState (+95 more)

### Community 20 - "Community 20"
Cohesion: 0.04
Nodes (16): AppContainer, MainActivity, followStoreWrites(), SettingsViewModel, LeadEstimateUiState, LeadEstimateViewModel, LoadingsUiState, LoadingsViewModel (+8 more)

### Community 21 - "Community 21"
Cohesion: 0.04
Nodes (85): diveFingerprint, PushLeftOut, waitingOf(), currentRemote(), takeChanges(), withRemembered(), DiveExportAction, isExporting() (+77 more)

### Community 22 - "Community 22"
Cohesion: 0.06
Nodes (88): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, assessmentRoutes() (+80 more)

### Community 23 - "Community 23"
Cohesion: 0.03
Nodes (79): adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, AuthOptions, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, ADR-0011, ADR-0012 (+71 more)

### Community 24 - "Community 24"
Cohesion: 0.03
Nodes (90): importQuery(), buddiesForQuery(), buddiesQuery(), BuddyView, ConflictChoices, ConflictEntryView, ConflictsOfConnection, connectionsQuery() (+82 more)

### Community 25 - "Community 25"
Cohesion: 0.05
Nodes (47): Section(), cylinderLine(), cylinderPressures(), Cylinders(), Direction, Back, Left, Right (+39 more)

### Community 26 - "Community 26"
Cohesion: 0.04
Nodes (81): CertificationService, DIVE_IMPORT_MODES, AssessmentRefresh, ADR-0036, ADR-0053, ErrorAnswer, providerProblem(), AccountRouteDeps (+73 more)

### Community 27 - "Community 27"
Cohesion: 0.05
Nodes (85): allButDive(), continuationCandidateQuery(), deletedDivesOn(), deletedDivesQuery(), DiveView, mergeCandidatesQuery(), nearbyDivesQuery(), restorePreviewQuery() (+77 more)

### Community 28 - "Community 28"
Cohesion: 0.05
Nodes (16): DiveStore, toBoolean(), toLong(), toModel(), BodyWeight, Cylinder, Diver, DiveSite (+8 more)

### Community 29 - "Community 29"
Cohesion: 0.07
Nodes (10): FakeCache, RevisionFollowTest, LeadEstimateViewModelTest, PlanningSacSource, default, logbook, Dive, json() (+2 more)

### Community 30 - "Community 30"
Cohesion: 0.04
Nodes (77): CertificationError, createCertificationService(), cleaned(), requireManagedDiver(), rowOrThrow(), isCalendarDate(), managedDiverIds(), asksFor() (+69 more)

### Community 31 - "Community 31"
Cohesion: 0.04
Nodes (17): SettingsDiagnosticsState, SettingsUiState, FakeDiagnostics, FakeInstances, SettingsViewModelTest, DiagnosticHygiene, Diagnostics, Failed (+9 more)

### Community 32 - "Community 32"
Cohesion: 0.07
Nodes (59): Formats, formatSyncTime(), GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult() (+51 more)

### Community 33 - "Community 33"
Cohesion: 0.05
Nodes (25): AppTab, Logbook, Settings, Tools, DiveHubBottomBar(), label(), SettingsDebugRoute(), SettingsDebugScreen() (+17 more)

### Community 34 - "Community 34"
Cohesion: 0.04
Nodes (13): DiveRow(), Figures(), RowFacts(), EmptyState(), FactRow(), NoteText(), StackedNoteText(), rememberReducedMotion() (+5 more)

### Community 35 - "Community 35"
Cohesion: 0.03
Nodes (14): KeystoreTokenStore, PreferencesCachedMeStore, PreferencesInstanceUrlStore, UserCacheStore, AndroidDiveStore, DiveHubApplication, SyncWorker, WorkManagerPeriodicSync (+6 more)

### Community 36 - "Community 36"
Cohesion: 0.05
Nodes (25): InkButton(), QuietButton(), Radius, AvatarCircle(), Error(), FilterAction(), Filters(), FilterSheet() (+17 more)

### Community 37 - "Community 37"
Cohesion: 0.05
Nodes (64): ImportArea, errorMessage(), looksLikeZip(), formatOf(), createImportService(), cancelImport(), createImport(), endWaiting() (+56 more)

### Community 38 - "Community 38"
Cohesion: 0.08
Nodes (29): LiveHints, SyncRefresher, Body, Hold, Offline, RefreshData, refreshDeletedJson(), refreshDetailJson() (+21 more)

### Community 39 - "Community 39"
Cohesion: 0.05
Nodes (73): ComputerChoice, DiveImportMode, DiveKind, providerSiteData, ProviderCylinder, DAY_MS, MAX_OFFSET_MS, inZone() (+65 more)

### Community 40 - "Community 40"
Cohesion: 0.11
Nodes (5): DiveDetailScreen(), DiveDetailScreenshotTest, NeighboursView, NeighboursViewNext, NeighboursViewPrevious

### Community 41 - "Community 41"
Cohesion: 0.04
Nodes (69): replyCertificationError, STATUS, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, CertificationRouteDeps (+61 more)

### Community 42 - "Community 42"
Cohesion: 0.04
Nodes (69): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, Conditions (+61 more)

### Community 43 - "Community 43"
Cohesion: 0.05
Nodes (21): ApiMeGet200Response, ApiMeGet200ResponsePreferences, SignInEmailRequest, UserView, InstanceUrl, HealthView, InstanceProbeResult, Loading (+13 more)

### Community 44 - "Community 44"
Cohesion: 0.06
Nodes (68): ANDROID_UI, AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), clearFailed(), clearPassed() (+60 more)

### Community 45 - "Community 45"
Cohesion: 0.05
Nodes (17): ApiClientDiagnosticsStatusGet200Response, ApiDiveSitesGet200Response, ApiMeGet200ResponseOwnDiver, ApiToolsBottomTimeGet200ResponseResidualN2, DiveDetailsListView, DiveDetailsListViewCounts, DiveDetailsListViewMonthsItem, DiveDetailsListViewTotals (+9 more)

### Community 46 - "Community 46"
Cohesion: 0.06
Nodes (23): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+15 more)

### Community 47 - "Community 47"
Cohesion: 0.06
Nodes (68): mergeProviderCylinder(), providerCylinderOf(), siteRef(), leadOf(), mergeProviderLead(), kindOfMode(), deviceDiver(), closeFields() (+60 more)

### Community 48 - "Community 48"
Cohesion: 0.07
Nodes (13): LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200ResponseBasis, ApiDiversIdLeadEstimateGet200ResponseBasisDivesItem, ApiDiversIdLeadEstimateGet200ResponseMissingItem, ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis (+5 more)

### Community 49 - "Community 49"
Cohesion: 0.08
Nodes (5): DiveDetailViewModelTest, GatedDispatcher, CountingDispatcher, LogbookViewModelTest, StoreLogbook

### Community 50 - "Community 50"
Cohesion: 0.05
Nodes (55): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+47 more)

### Community 51 - "Community 51"
Cohesion: 0.04
Nodes (57): AssessmentService, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView(), ADR-0036 (+49 more)

### Community 52 - "Community 52"
Cohesion: 0.07
Nodes (62): Answer, answer(), baseFrom(), Confidence, high, low, medium, Cylinder (+54 more)

### Community 53 - "Community 53"
Cohesion: 0.12
Nodes (24): Body, BrokenBody, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage(), diverJson() (+16 more)

### Community 54 - "Community 54"
Cohesion: 0.06
Nodes (47): ReportProblemSettings(), devicesQuery(), equipmentDueQuery(), DeviceRow(), Devices(), ADR-0016, Buddies(), EquipmentDueNotice() (+39 more)

### Community 55 - "Community 55"
Cohesion: 0.05
Nodes (11): Spacing, DiveView, DiveViewFromRecording, DiveViewFromRecordingStartsAt, DiveViewLeadItem, DiveViewPosition, DiveViewSac, DiveViewSuit (+3 more)

### Community 56 - "Community 56"
Cohesion: 0.08
Nodes (11): ApiDiverBodyWeightsGet200ResponseItem, SyncCheckpoint, Complete, SessionEnded, Stopped, SyncEngine, SyncPhase, Details (+3 more)

### Community 57 - "Community 57"
Cohesion: 0.05
Nodes (52): PARTICIPANT_ROLES, siteImportStatus, PROBLEM_CODES, ProblemSchema, storedProblemCode(), ADR-0014, ADR-0027, oneOf() (+44 more)

### Community 58 - "Community 58"
Cohesion: 0.06
Nodes (36): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+28 more)

### Community 59 - "Community 59"
Cohesion: 0.07
Nodes (47): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+39 more)

### Community 60 - "Community 60"
Cohesion: 0.11
Nodes (41): BottomTime, bottomTime(), BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen (+33 more)

### Community 61 - "Community 61"
Cohesion: 0.09
Nodes (45): ChangePassword(), SecuritySettings(), ADR-0080, useDescribeAgent(), AiAccessLogEntry, AiAccessView, authClient, invitationLinkQuery() (+37 more)

### Community 62 - "Community 62"
Cohesion: 0.09
Nodes (25): Profile(), ProfileChart(), DiveViewRecordingsItem, DiveViewRecordingsItemDevice, DiveViewRecordingsItemSummarySurfaceBreaksItem, RecordingSamplesView, RecordingSamplesViewSeriesItem, depthSeries() (+17 more)

### Community 63 - "Community 63"
Cohesion: 0.12
Nodes (3): DiveRoute, AppNavHostTest, FakeScreens

### Community 64 - "Community 64"
Cohesion: 0.06
Nodes (32): timeZoneText(), diveLocalTime(), offsetId(), timeZoneNote(), TimeZoneOrigin, DEVICE, NEARBY, POSITION (+24 more)

### Community 65 - "Community 65"
Cohesion: 0.06
Nodes (6): logShareChooser(), DiveHubApplicationTest, LauncherIconTest, SplashTheme, SplashThemeTest, LogShareTest

### Community 66 - "Community 66"
Cohesion: 0.10
Nodes (9): FakeCache, FakeCachedMe, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore (+1 more)

### Community 67 - "Community 67"
Cohesion: 0.08
Nodes (18): DiveHubApp(), DiveHubScreens, ErrorText(), Gate(), InstanceForm(), InstanceStatus(), LoadingScreen(), PasswordField() (+10 more)

### Community 68 - "Community 68"
Cohesion: 0.07
Nodes (21): AppNavHost(), AppScreens, clearSavedTabStates(), isShowing(), leaveDiveForLogbook(), MainShell(), navigateToTab(), tabOf() (+13 more)

### Community 69 - "Community 69"
Cohesion: 0.06
Nodes (42): CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite(), lock() (+34 more)

### Community 70 - "Community 70"
Cohesion: 0.08
Nodes (39): Queryable, BottomTimeQuery, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033, ADR-0071 (+31 more)

### Community 71 - "Community 71"
Cohesion: 0.05
Nodes (42): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+34 more)

### Community 72 - "Community 72"
Cohesion: 0.10
Nodes (41): ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres(), isHypoxic(), metresPerBar() (+33 more)

### Community 73 - "Community 73"
Cohesion: 0.06
Nodes (43): ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, cancelWaitingImports(), connectGarmin(), Connection (+35 more)

### Community 74 - "Community 74"
Cohesion: 0.08
Nodes (42): NoFly, DEFAULT_GF_HIGH, residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows(), DiveRow, endMsOf() (+34 more)

### Community 75 - "Community 75"
Cohesion: 0.05
Nodes (39): CYLINDER_MATERIALS, SUIT_TYPES, SUIT_UNDERGARMENTS, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053 (+31 more)

### Community 76 - "Community 76"
Cohesion: 0.08
Nodes (40): Leave, asBool(), DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+32 more)

### Community 77 - "Community 77"
Cohesion: 0.06
Nodes (41): ProviderImportPlan, SuitType, SuitUndergarment, WeightingFelt, ADR-0025, WaterMismatch, Suit, WeightingFeedback (+33 more)

### Community 78 - "Community 78"
Cohesion: 0.07
Nodes (8): ApiClient, ApiErrorBody, DiveRepository, decodeList(), ProviderDirectory, providerDirectory(), ApiClientTest, DiveRepositoryTest

### Community 79 - "Community 79"
Cohesion: 0.07
Nodes (9): Connected, Hint, HintFrame, LiveEvent, LiveHint, SseLiveHints, LiveHintsTest, SseLiveHintsEngineTest (+1 more)

### Community 80 - "Community 80"
Cohesion: 0.08
Nodes (37): SsiRecord, buildCreateRecord(), buildDeleteRecord(), emptyRecord(), writableRecord(), FakeSsi, BuddySaved, deleteTestDive() (+29 more)

### Community 81 - "Community 81"
Cohesion: 0.06
Nodes (39): Position, running(), SiteImportArea, siteImportsQuery(), SiteImportView, siteRevisionsQuery(), SiteRevisionView, ADR-0020 (+31 more)

### Community 82 - "Community 82"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 83 - "Community 83"
Cohesion: 0.11
Nodes (4): DiveHubTopBar(), DiveHubTheme(), SectionTest, DiveHubTopBarTest

### Community 85 - "Community 85"
Cohesion: 0.05
Nodes (39): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+31 more)

### Community 86 - "Community 86"
Cohesion: 0.08
Nodes (32): stubDive(), ADR-0015, ADR-0023, ADR-0025, ADR-0051, Status, ADR-0056, ADR-0024 (+24 more)

### Community 87 - "Community 87"
Cohesion: 0.08
Nodes (26): captureDiagnostic(), captureError(), cleanContext(), clearDiagnostics(), CLIENT_NAME, CLIENT_VERSION, DiagnosticEntry, diagnosticFromError() (+18 more)

### Community 88 - "Community 88"
Cohesion: 0.13
Nodes (6): LogbookPhase, Error, List, Loading, Offline, SettingsScreenshotTest

### Community 89 - "Community 89"
Cohesion: 0.09
Nodes (16): LogbookViewModel, neighboursPath(), countOf(), FilterChip, filterChips(), LogbookFilter, CHANGED_SINCE_SENT, NO_RECORDING (+8 more)

### Community 90 - "Community 90"
Cohesion: 0.11
Nodes (9): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), GasPlanInputTest (+1 more)

### Community 91 - "Community 91"
Cohesion: 0.08
Nodes (6): SettingsRouteTest, SettingsRepository, InstanceUrlStore, ProbeInstances, FakeInstances, SettingsRepositoryTest

### Community 92 - "Community 92"
Cohesion: 0.07
Nodes (34): AI_ACCESS_NAME_MAX, aiAccessLog, aiAccessSetting, Access, AccessView, AiAccessRouteDeps, LogEntryView, Scope (+26 more)

### Community 93 - "Community 93"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 94 - "Community 94"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 95 - "Community 95"
Cohesion: 0.08
Nodes (34): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+26 more)

### Community 96 - "Community 96"
Cohesion: 0.09
Nodes (6): DiveListViewDivesItem, DiveListViewDivesItemGasesItem, DiveListViewDivesItemParticipantsItem, DiveListViewDivesItemSite, DiveProfileView, toListRow()

### Community 98 - "Community 98"
Cohesion: 0.09
Nodes (11): ApiToolsBottomTimeGet200ResponseLimits, ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh, ApiToolsBottomTimeGet200ResponseUsedSacLpm, ApiToolsBottomTimeGet200ResponseUsedWater (+3 more)

### Community 99 - "Community 99"
Cohesion: 0.08
Nodes (8): DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewCylindersItemSeries, DiveViewRecordingsItemSummary, DiveViewRecordingsItemSummaryGasesItem, DiveViewSite, DiveViewValues, DiveViewValuesStartsAt

### Community 100 - "Community 100"
Cohesion: 0.07
Nodes (35): EquipmentService, RecordInput, ScheduleInput, DiveGearItemViewSchema, DiveGearViewSchema, DueState, EquipmentDetailViewSchema, EquipmentDueViewSchema (+27 more)

### Community 101 - "Community 101"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 103 - "Community 103"
Cohesion: 0.07
Nodes (28): headers, ADR-0030, ADR-0040, conflictForLena(), diveWithoutRecording(), editInSsi(), externalDiver(), importLena() (+20 more)

### Community 104 - "Community 104"
Cohesion: 0.09
Nodes (27): dayNumber(), enteredDecoFromNdl(), minNdlSeconds(), NoFly, noFlyAfter(), NoFlyReason, decompression, several (+19 more)

### Community 105 - "Community 105"
Cohesion: 0.19
Nodes (34): APP_KEYS, appRow(), byId(), changedKeys(), deleteTestEntries(), entryNamed(), flag(), FLAGS (+26 more)

### Community 106 - "Community 106"
Cohesion: 0.11
Nodes (13): LogbookPaging, AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint, SketchProfile, sketchScale() (+5 more)

### Community 107 - "Community 107"
Cohesion: 0.08
Nodes (24): Tx, AppendUndo, audienceOfDive(), ADR-0020, ADR-0056, ADR-0066, withRevisionHints(), ChangeListener (+16 more)

### Community 108 - "Community 108"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 109 - "Community 109"
Cohesion: 0.12
Nodes (31): importsQuery(), ImportView, ImportContext, ImportFilesButton(), ImportHistory(), ImportList(), ImportNotices(), ImportPanel() (+23 more)

### Community 110 - "Community 110"
Cohesion: 0.24
Nodes (9): leadAnswer(), EstimateCylinder, PlannedConditions, Suit, assemblePersonalizedSources(), suitOf(), currentLoadings(), leadEstimate() (+1 more)

### Community 112 - "Community 112"
Cohesion: 0.14
Nodes (32): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findNewId(), Format (+24 more)

### Community 113 - "Community 113"
Cohesion: 0.09
Nodes (30): AscentPressure, DiverPlan, GasPlan, GasPlanLimit, depth, gas, mod, GasRule (+22 more)

### Community 115 - "Community 115"
Cohesion: 0.09
Nodes (30): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+22 more)

### Community 116 - "Community 116"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 117 - "Community 117"
Cohesion: 0.10
Nodes (11): LeadAnswer, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseLeftOut, ApiDiversIdLeadEstimateGet200ResponseRange, ApiDiversIdLeadEstimateGet200ResponseReserve, ApiDiversIdLeadEstimateGet200ResponseWeightCheck, BodyWeight (+3 more)

### Community 119 - "Community 119"
Cohesion: 0.18
Nodes (7): planningSac(), PlanningSacDive, PlanningSacResult, BottomTimeToolInput, GasNumbersInput, ToolDiverSource, ToolsServiceTest

### Community 120 - "Community 120"
Cohesion: 0.07
Nodes (12): buddySite(), Dive, OTHER, PendingOffer, ADR-0012, ADR-0070, Dive, OTHER (+4 more)

### Community 122 - "Community 122"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 123 - "Community 123"
Cohesion: 0.08
Nodes (22): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationExpiry (+14 more)

### Community 124 - "Community 124"
Cohesion: 0.09
Nodes (23): computerEventOf(), ADR-0036, computerEventFromFit(), COMPUTER_EVENT, computerEventFromSuunto(), DECO_MODEL, decoModelFromSuunto, DIVE_MODE (+15 more)

### Community 125 - "Community 125"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 126 - "Community 126"
Cohesion: 0.11
Nodes (7): ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport

### Community 127 - "Community 127"
Cohesion: 0.16
Nodes (25): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+17 more)

### Community 128 - "Community 128"
Cohesion: 0.10
Nodes (22): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+14 more)

### Community 129 - "Community 129"
Cohesion: 0.10
Nodes (18): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, APP (+10 more)

### Community 130 - "Community 130"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 131 - "Community 131"
Cohesion: 0.21
Nodes (4): GasPlanScreenshotTest, GasPlanToolDiver, GasPlanToolInput, GasPlanToolLevel

### Community 133 - "Community 133"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 134 - "Community 134"
Cohesion: 0.11
Nodes (24): DiveGear, ensureItemForDevice(), GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow, ItemView (+16 more)

### Community 135 - "Community 135"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 136 - "Community 136"
Cohesion: 0.13
Nodes (6): LogbookUiState, DiveListView, DiveListViewCounts, DiveListViewTotals, LogbookRead, StoreLogbookContext

### Community 137 - "Community 137"
Cohesion: 0.13
Nodes (3): FakeInstances, FakeTokens, MainViewModelTest

### Community 138 - "Community 138"
Cohesion: 0.12
Nodes (23): date(), FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position(), positive() (+15 more)

### Community 139 - "Community 139"
Cohesion: 0.10
Nodes (18): adapter, crossCheck(), decodeOfficial(), privateDir, FIT_EPOCH_MS, makeSuuntoAppFit(), makeSuuntoJson(), shape() (+10 more)

### Community 140 - "Community 140"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 141 - "Community 141"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 142 - "Community 142"
Cohesion: 0.08
Nodes (25): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+17 more)

### Community 143 - "Community 143"
Cohesion: 0.11
Nodes (15): planSend(), SsiClient, contextOf(), DiveForSsi, ClearChecks, NumberChecks, findIn(), GAS (+7 more)

### Community 144 - "Community 144"
Cohesion: 0.14
Nodes (20): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, Site (+12 more)

### Community 145 - "Community 145"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 146 - "Community 146"
Cohesion: 0.16
Nodes (5): MainViewModel, UiState, Unreachable, sessionDestination(), SessionDestinationTest

### Community 147 - "Community 147"
Cohesion: 0.13
Nodes (6): ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting

### Community 148 - "Community 148"
Cohesion: 0.13
Nodes (6): DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, WaitingPushView

### Community 149 - "Community 149"
Cohesion: 0.12
Nodes (6): DiverView, DiverViewCertifications, DiverViewCertificationsNewest, DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 150 - "Community 150"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 152 - "Community 152"
Cohesion: 0.11
Nodes (21): Participant, DIVE_ONLY, DIVE_ONLY_PATTERN, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN, NO_RECORDING (+13 more)

### Community 153 - "Community 153"
Cohesion: 0.14
Nodes (18): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+10 more)

### Community 154 - "Community 154"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 156 - "Community 156"
Cohesion: 0.14
Nodes (4): FakeCachedMe, FakeInstances, FakeTokens, SyncAuthTest

### Community 157 - "Community 157"
Cohesion: 0.12
Nodes (19): CENTRE_SOURCE_NAME, CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+11 more)

### Community 158 - "Community 158"
Cohesion: 0.20
Nodes (21): DiveOrigin, REFERENCE_PREFIX, Series, aboveZero(), cylinderOf(), deviceOf(), deviceOfRef(), MATERIAL_OF_TANK_TYPE (+13 more)

### Community 159 - "Community 159"
Cohesion: 0.18
Nodes (5): DiveDetailRoute(), DiveDetailViewModel, NeighbourDive, NeighboursRead, ProfileRead

### Community 161 - "Community 161"
Cohesion: 0.10
Nodes (16): accountRelations, apikey, rateLimit, sessionRelations, ADR-0011, ADR-0012, ADR-0035, twoFactor (+8 more)

### Community 162 - "Community 162"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 163 - "Community 163"
Cohesion: 0.15
Nodes (5): ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits

### Community 164 - "Community 164"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 165 - "Community 165"
Cohesion: 0.17
Nodes (17): cnsDecayed(), Estimate, isoUtc(), LoadingBasis, LoadingDive, N2Loadings, N2UnavailableReason, no_profile (+9 more)

### Community 166 - "Community 166"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource/poppins@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 167 - "Community 167"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 168 - "Community 168"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 169 - "Community 169"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 170 - "Community 170"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 171 - "Community 171"
Cohesion: 0.19
Nodes (11): LeadInputTest, SuitType, drysuit, none, semidry, skin, wetsuit, SuitUndergarment (+3 more)

### Community 172 - "Community 172"
Cohesion: 0.18
Nodes (16): RecordingSummary, UtcOffsetSource, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput (+8 more)

### Community 173 - "Community 173"
Cohesion: 0.14
Nodes (13): BufferedLog, LOG_BUFFER_CAPACITY_BYTES, LOG_BUFFER_CAPACITY_ENTRIES, LogBufferStats, LogRingBufferOptions, SupportBundle, ClientDiagnosticsProvider, FailedBackup (+5 more)

### Community 174 - "Community 174"
Cohesion: 0.20
Nodes (8): logbookDiveItemIndex(), LogbookScreen(), DiveListViewMonthsItem, localMonthOf(), LogbookMonth, monthGroups(), dive(), MonthGroupsTest

### Community 175 - "Community 175"
Cohesion: 0.29
Nodes (15): Adjustment(), amount(), Answer(), Conditions(), detail(), hoodName(), levelName(), materialName() (+7 more)

### Community 176 - "Community 176"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 178 - "Community 178"
Cohesion: 0.14
Nodes (14): AL80, EstimateDiveCylinder, LEAD_CASES, leadInput(), LeadVectorInput, planOf(), STEEL_12, ADR-0031 (+6 more)

### Community 179 - "Community 179"
Cohesion: 0.20
Nodes (16): asNumber(), asText(), buddyIdsOf(), buildUpdateRecord(), compareReadBack(), datasetLength(), keysOf(), describe() (+8 more)

### Community 180 - "Community 180"
Cohesion: 0.17
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 181 - "Community 181"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 182 - "Community 182"
Cohesion: 0.12
Nodes (13): clientDiagnostic, clientDiagnosticsSettings, ClientDiagnosticsEntryInput, ClientDiagnosticsEntryView, ClientDiagnosticsError, ClientDiagnosticsUpload, KEEP_NEWEST, MAX_UPLOAD_ENTRIES (+5 more)

### Community 183 - "Community 183"
Cohesion: 0.15
Nodes (15): equipmentItem, serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, DueEntry, ScheduleView, equipmentList() (+7 more)

### Community 184 - "Community 184"
Cohesion: 0.18
Nodes (11): storedList(), tryParseJson(), APP_PARAMS, createSsiTransport(), exchange(), isObject(), SSI_APP_USER_AGENT, SsiTransport (+3 more)

### Community 185 - "Community 185"
Cohesion: 0.21
Nodes (11): buildSupportBundle(), buildTarGz(), json(), ndjson(), octal(), SUPPORT_BUNDLE_FORMAT, SUPPORT_BUNDLE_FORMAT_VERSION, SupportBufferManifest (+3 more)

### Community 186 - "Community 186"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 187 - "Community 187"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 188 - "Community 188"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 190 - "Community 190"
Cohesion: 0.27
Nodes (14): powInt(), roundTo(), Sac, SacCylinder, SacDive, SacMissing, cylinder_incomplete, no_average_depth (+6 more)

### Community 191 - "Community 191"
Cohesion: 0.16
Nodes (12): CIRCUIT, circuitFromFit, DECO_MODEL, decoModelFromFit, DIVE_ALERT, DIVE_MODE, ADR-0015, WATER_TYPE (+4 more)

### Community 192 - "Community 192"
Cohesion: 0.19
Nodes (11): BOM, Json, num(), obj(), positive(), scaled(), time(), toRecording() (+3 more)

### Community 193 - "Community 193"
Cohesion: 0.20
Nodes (12): asEstimateInput(), evaluateLeadVectorCase(), LEAD_VECTOR_KERNEL, leadVectorFile(), VECTOR_KERNELS, VectorFile, assertMatch(), checkVectors() (+4 more)

### Community 194 - "Community 194"
Cohesion: 0.20
Nodes (12): fromOsm(), fromSsi(), fromWd(), HOUSE_REEF, osm(), plan(), site(), ssi() (+4 more)

### Community 195 - "Community 195"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 196 - "Community 196"
Cohesion: 0.13
Nodes (6): FakeBroadcastChannel, FakeEventSource, Listener, locks, start(), ADR-0066

### Community 197 - "Community 197"
Cohesion: 0.13
Nodes (14): concurrency, cancel-in-progress, group, env, RELEASE_TAG, jobs, publish, name (+6 more)

### Community 198 - "Community 198"
Cohesion: 0.29
Nodes (14): managementOf(), createEquipmentService(), assemble(), detailOf(), lastCylinderOf(), managedItem(), managedRecord(), managedSchedule() (+6 more)

### Community 199 - "Community 199"
Cohesion: 0.20
Nodes (5): LogRingBuffer, RedactionCounters, SupportBundleInput, SupportBundleManifest, SupportServiceDeps

### Community 200 - "Community 200"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 201 - "Community 201"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 202 - "Community 202"
Cohesion: 0.23
Nodes (10): ADR-0076, ClientVersionRegistry, MIN_CLIENT_VERSIONS, MinClientVersion, ADR-0076, compareIdentifier(), compareNumeric(), compareSemanticVersions() (+2 more)

### Community 203 - "Community 203"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 204 - "Community 204"
Cohesion: 0.26
Nodes (3): ApiClientDiagnosticsPostRequest, ApiClientDiagnosticsPostRequestClient, ApiClientDiagnosticsPostRequestEntriesItem

### Community 205 - "Community 205"
Cohesion: 0.18
Nodes (10): EquipmentError, ADR-0034, ADR-0053, checkCost(), checkDates(), checkWindow(), checkResets(), checkSchedule() (+2 more)

### Community 206 - "Community 206"
Cohesion: 0.18
Nodes (9): AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031, WETSUIT_5 (+1 more)

### Community 207 - "Community 207"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 208 - "Community 208"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 209 - "Community 209"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 210 - "Community 210"
Cohesion: 0.20
Nodes (10): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, ADR-0076, MODULE_IMPORTS, moduleRules (+2 more)

### Community 211 - "Community 211"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 212 - "Community 212"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 214 - "Community 214"
Cohesion: 0.20
Nodes (5): FileFormat, FitAdapter, SuuntoJsonAdapter, UddfAdapter, ParsedRecording

### Community 215 - "Community 215"
Cohesion: 0.20
Nodes (9): LeadEstimateAnswer, Confidence, EstimateAdjustment, EstimateBasis, EstimateInput, EstimateMissing, LeadEstimate, ADR-0025 (+1 more)

### Community 216 - "Community 216"
Cohesion: 0.33
Nodes (8): prepareEntry(), DENIED_KEY_PARTS, isDeniedKey(), maskString(), redactInner(), redactLogObject(), RedactOptions, redactValue()

### Community 217 - "Community 217"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 218 - "Community 218"
Cohesion: 0.20
Nodes (10): verify, prerelease, sha, tag, versionCode, versionName, name, outputs (+2 more)

### Community 219 - "Community 219"
Cohesion: 0.20
Nodes (10): with, cache, distribution, fetch-depth, if-no-files-found, java-version, name, path (+2 more)

### Community 220 - "Community 220"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 221 - "Community 221"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 222 - "Community 222"
Cohesion: 0.25
Nodes (6): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, CylinderInput, EstimateCylinder

### Community 223 - "Community 223"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 224 - "Community 224"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 225 - "Community 225"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 226 - "Community 226"
Cohesion: 0.43
Nodes (8): emptyRedactionCounters(), createSupportService(), assemble(), clientDiagnostics(), configSection(), failedJobs(), healthSection(), maskDatabaseUrl()

### Community 227 - "Community 227"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 228 - "Community 228"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 229 - "Community 229"
Cohesion: 0.29
Nodes (6): itemIds(), ADR-0026, ADR-0034, Usage, UsageDive, UsageRow

### Community 230 - "Community 230"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 231 - "Community 231"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 232 - "Community 232"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 233 - "Community 233"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 234 - "Community 234"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build

### Community 235 - "Community 235"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 236 - "Community 236"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 240 - "Community 240"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 241 - "Community 241"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 242 - "Community 242"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1320 isolated node(s):** `Reserve`, `RockBottom`, `SuitThickness`, `Sent`, `Reachable` (+1315 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3798 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **139 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `@tanstack/react-query` connect `Community 4` to `Community 2`, `Community 196`, `Community 166`, `Community 7`, `Community 8`, `Community 10`, `Community 12`, `Community 109`, `Community 17`, `Community 81`, `Community 115`, `Community 54`, `Community 24`, `Community 27`, `Community 61`?**
  _High betweenness centrality (0.017) - this node is a cross-community bridge._
- **What connects `Reserve`, `RockBottom`, `SuitThickness` to the rest of the system?**
  _1320 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.01479581485667913 - nodes in this community are weakly interconnected._
- **Why does `drizzle-orm` connect `Community 5` to `Community 0`, `Community 1`, `Community 3`, `Community 6`, `Community 134`, `Community 9`, `Community 11`, `Community 139`, `Community 13`, `Community 142`, `Community 15`, `Community 14`, `Community 16`, `Community 18`, `Community 19`, `Community 21`, `Community 22`, `Community 152`, `Community 161`, `Community 39`, `Community 173`, `Community 50`, `Community 182`, `Community 183`, `Community 69`, `Community 70`, `Community 74`, `Community 92`, `Community 229`, `Community 122`, `Community 123`?**
  _High betweenness centrality (0.017) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.01987017670393076 - nodes in this community are weakly interconnected._
- **Why does `TextField()` connect `Community 4` to `Community 8`, `Community 2`, `Community 10`, `Community 61`?**
  _High betweenness centrality (0.012) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.03506841275081619 - nodes in this community are weakly interconnected._