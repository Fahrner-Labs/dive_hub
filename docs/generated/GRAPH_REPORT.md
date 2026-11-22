# Graph Report - dive_hub  (2026-11-22)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 11291 nodes · 34414 edges · 349 communities (202 shown, 147 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 661 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `1c1fcc02`
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
- Community 221
- Community 222
- Community 223
- Community 224
- Community 225
- Community 228
- Community 230

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 209 edges
2. `Button()` - 202 edges
3. `useErrorText()` - 174 edges
4. `useDisplay()` - 163 edges
5. `drizzle-orm` - 143 edges
6. `unwrap()` - 141 edges
7. `Muted()` - 139 edges
8. `announce()` - 107 edges
9. `createTestDatabase()` - 106 edges
10. `TestDatabase` - 105 edges

## Surprising Connections (you probably didn't know these)
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `gasPlan()` --calls--> `ToolsError`  [INFERRED]
  apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/tools/GasPlanService.kt → apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/tools/ToolsService.kt
- `Context` --references--> `Position`  [EXTRACTED]
  apps/server/src/imports/formats/uddf/uddf.ts → apps/server/src/vocabulary.ts

## Import Cycles
- None detected.

## Communities (349 total, 147 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.03
Nodes (265): ChangePassword(), DisplaySettings(), SecuritySettings(), Sessions(), ADR-0080, useDescribeAgent(), AdminSettings(), ClientDiagnosticsSetting() (+257 more)

### Community 1 - "Community 1"
Cohesion: 0.02
Nodes (227): cylinderCatalogueQuery(), LeadEstimate, CylinderFields(), MATERIALS, ADR-0034, ADR-0045, useCatalogueName(), useCylinderProblem() (+219 more)

### Community 2 - "Community 2"
Cohesion: 0.03
Nodes (153): user, cylinder, dive, duplicateCandidate, importJob, original, push, recording (+145 more)

### Community 3 - "Community 3"
Cohesion: 0.02
Nodes (200): ConnectionRow, providerGoneDive, providerLease, WaitingPushOptions, waitingOf(), audienceOfConnection(), open(), close() (+192 more)

### Community 4 - "Community 4"
Cohesion: 0.02
Nodes (184): label(), ClientDiagnosticsSettingsView, clientDiagnosticsStatusQuery(), ClientDiagnosticsStatusView, ADR-0080, importQuery(), ImportView, ADR-0030 (+176 more)

### Community 5 - "Community 5"
Cohesion: 0.02
Nodes (180): continuationAppend, diverBodyWeight, LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, RecordingSummary, SuitType, SuitUndergarment (+172 more)

### Community 6 - "Community 6"
Cohesion: 0.02
Nodes (163): acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), AppLockProbe (+155 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (163): auth, ServiceParts, Db, EMPTY_SITE_IMPORT_COUNTS, ImportArea, ImportedValues, SiteImportCounts, StoredConflictField (+155 more)

### Community 8 - "Community 8"
Cohesion: 0.02
Nodes (136): accountListEntry, lead, forget(), remember(), rememberedEntries(), rememberedFor(), resolvedRows(), ADR-0040 (+128 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (145): AcceptedBuddySuggestionView, allButDive(), AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionView, CandidateView, continuationCandidateQuery(), ContinuationCandidateView (+137 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (54): LeadAnswer, leadAnswer(), LeadCylinderInput, LeadEstimateUiState, LeadEstimateViewModel, LeadInputs, LeadInputTest, PersonalizedScreenshotTest (+46 more)

### Community 11 - "Community 11"
Cohesion: 0.05
Nodes (10): titleLineHeight(), TitleSkeleton(), label(), SettingsDebugRoute(), SettingsDebugScreen(), Spacing, DiveHubColors, LoadingSource (+2 more)

### Community 12 - "Community 12"
Cohesion: 0.04
Nodes (67): Section(), cylinderLine(), cylinderPressures(), Cylinders(), Direction, Back, Left, Right (+59 more)

### Community 13 - "Community 13"
Cohesion: 0.03
Nodes (120): diver, jointDive, logbookCheckAnswer, logbookCheckDiveAnswer, keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration() (+112 more)

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (116): ReportProblemSettings(), divesQuery(), DiveSummary, LogbookPage, PAGE_SIZE, providersQuery(), pushRunActive(), pushRunBody() (+108 more)

### Community 15 - "Community 15"
Cohesion: 0.04
Nodes (124): ComputerChoice, DiveImportMode, DiveKind, providerSiteData, UtcOffsetSource, mergeProviderCylinder(), ProviderCylinder, siteRef() (+116 more)

### Community 16 - "Community 16"
Cohesion: 0.03
Nodes (110): UTC_OFFSET_SOURCES, DiveCertifications, AppendDeps, AppendOutcome, AppendService, Side, ADR-0038, ADR-0063 (+102 more)

### Community 17 - "Community 17"
Cohesion: 0.03
Nodes (93): connection, ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS, eachFile(), readEntry(), ADR-0044 (+85 more)

### Community 18 - "Community 18"
Cohesion: 0.02
Nodes (114): CHANNELS, ADR-0036, Queryable, actorType, aiAccessOutcome, backupRunKind, backupRunStatus, backupSettings (+106 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (108): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, ambientPressureBar() (+100 more)

### Community 20 - "Community 20"
Cohesion: 0.02
Nodes (106): apikey, buddySuggestion, diveFinding, recordingEvent, Assessment, FindingView, Row, ADR-0036 (+98 more)

### Community 21 - "Community 21"
Cohesion: 0.05
Nodes (59): ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh, ApiToolsBottomTimeGet200ResponseUsedSacLpm, ApiToolsBottomTimeGet200ResponseUsedWater, BottomTime (+51 more)

### Community 22 - "Community 22"
Cohesion: 0.03
Nodes (80): createServer(), createAuth(), loadAuthSecret(), workerDeps(), Config, loadConfig(), ADR-0011, ADR-0021 (+72 more)

### Community 23 - "Community 23"
Cohesion: 0.03
Nodes (27): ApiClientDiagnosticsStatusGet200Response, ApiDiveSitesGet200Response, ApiMeGet200ResponseOwnDiver, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseLimits, ApiToolsBottomTimeGet200ResponseResidualN2, ConnectionView, ConnectionViewDiveImport (+19 more)

### Community 24 - "Community 24"
Cohesion: 0.03
Nodes (86): CentreDisplayNames, CentreError, CentreExternalId, CentreService, CentreSource, createCentreService(), holder(), liveSite() (+78 more)

### Community 25 - "Community 25"
Cohesion: 0.04
Nodes (78): equipmentDueQuery(), cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043 (+70 more)

### Community 26 - "Community 26"
Cohesion: 0.03
Nodes (20): SettingsDiagnosticsState, SettingsUiState, SettingsViewModel, FakeDiagnostics, FakeInstances, SettingsViewModelTest, ApiClientDiagnosticsPostRequest, ApiClientDiagnosticsPostRequestClient (+12 more)

### Community 27 - "Community 27"
Cohesion: 0.03
Nodes (88): isUnauthorized(), ExternalIdView, Position, running(), SiteImportArea, siteImportsQuery(), SiteImportView, siteRevisionsQuery() (+80 more)

### Community 28 - "Community 28"
Cohesion: 0.04
Nodes (97): LEAD_PLACEMENTS, WEIGHTING_FELT, downsampleMinMax(), AiAccessScope, seesPositions(), VerifiedAccess, diversOf(), seriesOf() (+89 more)

### Community 29 - "Community 29"
Cohesion: 0.04
Nodes (31): MainActivity, Checking, MainViewModel, Reachability, Reachable, UiState, Unknown, Unreachable (+23 more)

### Community 30 - "Community 30"
Cohesion: 0.05
Nodes (91): Me, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery(), candidatesQuery(), deletedDivesTotal(), isDiveCheck(), isPairCheck() (+83 more)

### Community 31 - "Community 31"
Cohesion: 0.03
Nodes (78): ParticipantRole, ProviderImportPlan, tidyName(), DiverDetails, Caller, foldName(), nameDiffers(), Buddy (+70 more)

### Community 32 - "Community 32"
Cohesion: 0.05
Nodes (10): DiveDetailViewModelTest, GatedDispatcher, CountingDispatcher, LogbookViewModelTest, ApiClient, ApiErrorBody, LogbookRepository, StoredDive (+2 more)

### Community 33 - "Community 33"
Cohesion: 0.04
Nodes (78): sessionIdOf(), hashPassword(), invitation, userRole, AttemptLimit, clientKey(), limitAttempts(), TOKEN_ATTEMPTS (+70 more)

### Community 34 - "Community 34"
Cohesion: 0.04
Nodes (26): DiveDetailViewModel, LogbookViewModel, followStoreWrites(), DiveRepository, neighboursPath(), DecodeException, DiveHubException, NoAnswerException (+18 more)

### Community 35 - "Community 35"
Cohesion: 0.04
Nodes (48): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+40 more)

### Community 36 - "Community 36"
Cohesion: 0.05
Nodes (8): DiveStore, toBoolean(), toLong(), toModel(), BodyWeight, DiveSite, SyncState, DiveStoreTest

### Community 37 - "Community 37"
Cohesion: 0.07
Nodes (9): FakeCache, RevisionFollowTest, LeadEstimateViewModelTest, ErrorCodes, Cylinder, Dive, Diver, Recording (+1 more)

### Community 38 - "Community 38"
Cohesion: 0.06
Nodes (76): Formats, formatSyncTime(), GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult() (+68 more)

### Community 39 - "Community 39"
Cohesion: 0.05
Nodes (18): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), Answers (+10 more)

### Community 40 - "Community 40"
Cohesion: 0.04
Nodes (75): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+67 more)

### Community 41 - "Community 41"
Cohesion: 0.04
Nodes (77): equipmentUse, pushRunItem, PushRunLeftOut, Participant, asksFor(), DIVE_ONLY, DIVE_ONLY_PATTERN, DiveListFilter (+69 more)

### Community 42 - "Community 42"
Cohesion: 0.05
Nodes (75): CertificationService, CYLINDER_MATERIALS, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053, ErrorAnswer, providerProblem() (+67 more)

### Community 43 - "Community 43"
Cohesion: 0.03
Nodes (63): BackupServiceDeps, createServices(), ADR-0027, ADR-0030, ADR-0061, ADR-0063, ADR-0066, ADR-0070 (+55 more)

### Community 44 - "Community 44"
Cohesion: 0.07
Nodes (35): Connected, Hint, HintFrame, LiveEvent, LiveHint, LiveHints, SseLiveHints, LiveHintsTest (+27 more)

### Community 45 - "Community 45"
Cohesion: 0.04
Nodes (10): AppContainer, KeystoreTokenStore, PreferencesInstanceUrlStore, UserCacheStore, AndroidDiveStore, DiveHubApplication, SyncWorker, WorkManagerPeriodicSync (+2 more)

### Community 46 - "Community 46"
Cohesion: 0.06
Nodes (17): LogbookScreenTest, DiveListView, DiveListViewCounts, DiveListViewDivesItem, DiveListViewDivesItemGasesItem, DiveListViewDivesItemSite, DiveListViewMonthsItem, DiveListViewTotals (+9 more)

### Community 47 - "Community 47"
Cohesion: 0.04
Nodes (67): AssessmentService, AssessmentView, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView() (+59 more)

### Community 48 - "Community 48"
Cohesion: 0.07
Nodes (66): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+58 more)

### Community 49 - "Community 49"
Cohesion: 0.05
Nodes (67): equipmentItem, serviceRecord, serviceRecordSchedule, serviceSchedule, managementOf(), ScheduleDue, EquipmentError, ADR-0034 (+59 more)

### Community 50 - "Community 50"
Cohesion: 0.06
Nodes (3): OnboardingScreenTest, SettingsDebugScreenTest, SettingsScreenTest

### Community 51 - "Community 51"
Cohesion: 0.09
Nodes (66): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, assessmentRoutes() (+58 more)

### Community 52 - "Community 52"
Cohesion: 0.05
Nodes (55): CertificationView, CertificationViewSchema, Day, ADR-0050, CERTIFICATION_IMAGE_STATES, CERTIFICATION_VERIFICATIONS, DIVE_IMPORT_MODES, DIVE_KINDS (+47 more)

### Community 53 - "Community 53"
Cohesion: 0.04
Nodes (49): diveLease, providerConflict, pushRun, kept(), LEASE_MS, MAX_WAIT_MS, POLL_MS, Delivered (+41 more)

### Community 54 - "Community 54"
Cohesion: 0.06
Nodes (64): ANDROID_UI, AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch() (+56 more)

### Community 55 - "Community 55"
Cohesion: 0.08
Nodes (33): EstimateCylinder, PlannedConditions, Suit, dayNumber(), enteredDecoFromNdl(), minNdlSeconds(), NoFly, noFlyAfter() (+25 more)

### Community 56 - "Community 56"
Cohesion: 0.06
Nodes (29): InkButton(), QuietButton(), EmptyState(), rememberReducedMotion(), rememberSkeletonAlpha(), SkeletonBar(), AvatarCircle(), Error() (+21 more)

### Community 57 - "Community 57"
Cohesion: 0.06
Nodes (50): looksLikeZip(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy(), ADR-0024 (+42 more)

### Community 58 - "Community 58"
Cohesion: 0.08
Nodes (13): ApiDiverBodyWeightsGet200ResponseItem, DiverView, SyncCheckpoint, Complete, SessionEnded, Stopped, SyncEngine, SyncPhase (+5 more)

### Community 59 - "Community 59"
Cohesion: 0.07
Nodes (62): Answer, answer(), baseFrom(), BodyWeight, Confidence, high, low, medium (+54 more)

### Community 60 - "Community 60"
Cohesion: 0.06
Nodes (52): Id(), Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), getDiverLoadings, HINT (+44 more)

### Community 61 - "Community 61"
Cohesion: 0.12
Nodes (24): Body, BrokenBody, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage(), diverJson() (+16 more)

### Community 62 - "Community 62"
Cohesion: 0.06
Nodes (36): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+28 more)

### Community 63 - "Community 63"
Cohesion: 0.04
Nodes (51): CertificationView, DeviceView, DiverDetailsView, ExternalDiverView, FoundDiverView, LoadingsView, ReadCodeView, ADR-0028 (+43 more)

### Community 64 - "Community 64"
Cohesion: 0.06
Nodes (20): AppNavHost(), AppScreens, clearSavedTabStates(), isShowing(), leaveDiveForLogbook(), MainShell(), navigateToTab(), tabOf() (+12 more)

### Community 65 - "Community 65"
Cohesion: 0.05
Nodes (49): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, conflictForLena() (+41 more)

### Community 66 - "Community 66"
Cohesion: 0.06
Nodes (31): diveLocalTime(), offsetId(), timeZoneNote(), TimeZoneOrigin, DEVICE, NEARBY, POSITION, UNKNOWN (+23 more)

### Community 67 - "Community 67"
Cohesion: 0.04
Nodes (51): EquipmentService, RecordInput, ScheduleInput, DiveGearItemViewSchema, DiveGearViewSchema, DueState, EquipmentDetailViewSchema, EquipmentDueViewSchema (+43 more)

### Community 68 - "Community 68"
Cohesion: 0.15
Nodes (8): otu(), oxygenLimitMinutes(), compressibility(), Cubic, minusOne(), pressureForLitres(), surfaceLitres(), PlanningVectorsTest

### Community 69 - "Community 69"
Cohesion: 0.06
Nodes (42): createFitAdapter(), date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser() (+34 more)

### Community 70 - "Community 70"
Cohesion: 0.05
Nodes (44): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+36 more)

### Community 71 - "Community 71"
Cohesion: 0.07
Nodes (46): read(), NoFly, noFlyAfter(), seriesDiveOf(), residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows() (+38 more)

### Community 72 - "Community 72"
Cohesion: 0.07
Nodes (44): Leave, Series, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+36 more)

### Community 73 - "Community 73"
Cohesion: 0.07
Nodes (35): diveWith(), ADR-0028, ADR-0043, buddySite(), Dive, OTHER, resetSource(), ADR-0012 (+27 more)

### Community 75 - "Community 75"
Cohesion: 0.09
Nodes (11): DiveHubTopBar(), Action(), ConnectionStatus(), Section(), SettingsRoute(), SettingsScreen(), DiveHubTheme(), isDarkScheme() (+3 more)

### Community 76 - "Community 76"
Cohesion: 0.05
Nodes (39): SUIT_TYPES, SUIT_UNDERGARMENTS, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053, replyDiverError (+31 more)

### Community 77 - "Community 77"
Cohesion: 0.11
Nodes (8): FakeCache, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore, UserScopedStore

### Community 78 - "Community 78"
Cohesion: 0.09
Nodes (39): aiAccessSettingQuery(), BackupRunView, backupSettingsQuery(), BackupSettingsView, backupsQuery(), SiteDataView, supportBundlePreviewQuery(), SupportBundlePreviewView (+31 more)

### Community 79 - "Community 79"
Cohesion: 0.06
Nodes (36): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+28 more)

### Community 80 - "Community 80"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 81 - "Community 81"
Cohesion: 0.11
Nodes (6): DiagnosticEntry, DiagnosticLevel, ERROR, WARN, DiagnosticsBufferTest, DiagnosticsTest

### Community 82 - "Community 82"
Cohesion: 0.07
Nodes (11): ProviderSummary, ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport (+3 more)

### Community 83 - "Community 83"
Cohesion: 0.08
Nodes (15): DiveHubApp(), DiveHubScreens, ErrorText(), Gate(), InstanceForm(), InstanceStatus(), LoadingScreen(), PasswordField() (+7 more)

### Community 84 - "Community 84"
Cohesion: 0.13
Nodes (41): withBuddies(), APP_KEYS, appRow(), BuddySaved, byId(), changedKeys(), deleteTestDive(), deleteTestEntries() (+33 more)

### Community 85 - "Community 85"
Cohesion: 0.08
Nodes (27): captureDiagnostic(), captureError(), cleanContext(), clearDiagnostics(), CLIENT_NAME, CLIENT_VERSION, DiagnosticEntry, diagnosticFromError() (+19 more)

### Community 86 - "Community 86"
Cohesion: 0.18
Nodes (3): DiveRoute, AppNavHostTest, FakeScreens

### Community 87 - "Community 87"
Cohesion: 0.06
Nodes (32): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError (+24 more)

### Community 88 - "Community 88"
Cohesion: 0.06
Nodes (39): Change(), Changes(), historyRoute, Made(), REVISION_CAUSES, PositionSchema, AdoptBody, Country (+31 more)

### Community 89 - "Community 89"
Cohesion: 0.08
Nodes (38): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+30 more)

### Community 90 - "Community 90"
Cohesion: 0.09
Nodes (16): LogbookPaging, AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint, SketchProfile, sketchScale() (+8 more)

### Community 91 - "Community 91"
Cohesion: 0.05
Nodes (37): AREA_OF, areaOf(), areas, capture(), findings, headers, ADR-0020, ADR-0021 (+29 more)

### Community 92 - "Community 92"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 93 - "Community 93"
Cohesion: 0.07
Nodes (30): BACKUP_RUN_TASK, BackupService, backupTask(), ADR-0069, PurgeService, EXPIRE_WAITING_IMPORTS_TASK, PROCESS_IMPORT_TASK, SCHEDULED_FETCH_TASK (+22 more)

### Community 94 - "Community 94"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 95 - "Community 95"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 96 - "Community 96"
Cohesion: 0.13
Nodes (21): planningSac(), PlanningSacDive, jsRound(), powInt(), roundTo(), Sac, SacCylinder, SacDive (+13 more)

### Community 97 - "Community 97"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 98 - "Community 98"
Cohesion: 0.13
Nodes (6): LogbookPhase, Error, List, Loading, Offline, SettingsScreenshotTest

### Community 100 - "Community 100"
Cohesion: 0.08
Nodes (32): AI_ACCESS_NAME_MAX, aiAccessLog, aiAccessSetting, Access, AccessView, AiAccessRouteDeps, LogEntryView, Scope (+24 more)

### Community 101 - "Community 101"
Cohesion: 0.14
Nodes (7): DiveRow(), Figures(), RowFacts(), FactRow(), NoteText(), StackedNoteText(), ComponentsScreenshotTest

### Community 102 - "Community 102"
Cohesion: 0.09
Nodes (33): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+25 more)

### Community 103 - "Community 103"
Cohesion: 0.07
Nodes (28): DELETE_QUIET_MS, Cylinder, DiveView, RevisionView, ADR-0045, upload(), Candidate, Deleted (+20 more)

### Community 104 - "Community 104"
Cohesion: 0.11
Nodes (27): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+19 more)

### Community 105 - "Community 105"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 106 - "Community 106"
Cohesion: 0.15
Nodes (32): acceptedFormat(), Answer, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId(), Format (+24 more)

### Community 107 - "Community 107"
Cohesion: 0.13
Nodes (31): importsQuery(), Logbook(), ImportContext, ImportFilesButton(), ImportHistory(), ImportList(), ImportNotices(), ImportPanel() (+23 more)

### Community 108 - "Community 108"
Cohesion: 0.09
Nodes (25): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+17 more)

### Community 109 - "Community 109"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 110 - "Community 110"
Cohesion: 0.14
Nodes (6): DiveViewRecordingsItemSummary, DiveViewValues, DiveViewValuesStartsAt, summaryOf(), summaryOf(), StoreLogbookTest

### Community 111 - "Community 111"
Cohesion: 0.12
Nodes (23): PROBLEM_CODES, ProblemSchema, ADR-0014, ADR-0027, ADR-0076, ClientVersionRegistry, MIN_CLIENT_VERSIONS, MinClientVersion (+15 more)

### Community 112 - "Community 112"
Cohesion: 0.11
Nodes (22): SsiClient, buildCreateRecord(), buildDeleteRecord(), emptyRecord(), writableRecord(), ClearChecks, clearDive(), findIn() (+14 more)

### Community 113 - "Community 113"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 114 - "Community 114"
Cohesion: 0.10
Nodes (23): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearPushRuns(), clearQueue() (+15 more)

### Community 115 - "Community 115"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 116 - "Community 116"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 117 - "Community 117"
Cohesion: 0.11
Nodes (22): SsiRecord, contextOf(), FakeSsi, access(), client, out, pause, save() (+14 more)

### Community 118 - "Community 118"
Cohesion: 0.08
Nodes (22): headers, ADR-0035, aiAccessReady(), askMcp(), cancelWaitingImports(), createAiAccess(), putAside(), waitingImport() (+14 more)

### Community 119 - "Community 119"
Cohesion: 0.11
Nodes (24): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+16 more)

### Community 120 - "Community 120"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 123 - "Community 123"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 124 - "Community 124"
Cohesion: 0.14
Nodes (6): AppTab, Logbook, Settings, Tools, DiveHubBottomBar(), BottomBarScreenshotTest

### Community 125 - "Community 125"
Cohesion: 0.13
Nodes (3): FakeInstances, FakeTokens, MainViewModelTest

### Community 126 - "Community 126"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 127 - "Community 127"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 128 - "Community 128"
Cohesion: 0.08
Nodes (25): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+17 more)

### Community 129 - "Community 129"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 130 - "Community 130"
Cohesion: 0.13
Nodes (6): DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, WaitingPushView

### Community 131 - "Community 131"
Cohesion: 0.09
Nodes (19): account, accountRelations, rateLimit, session, sessionRelations, ADR-0011, ADR-0012, ADR-0035 (+11 more)

### Community 132 - "Community 132"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 134 - "Community 134"
Cohesion: 0.14
Nodes (18): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+10 more)

### Community 135 - "Community 135"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 136 - "Community 136"
Cohesion: 0.15
Nodes (5): DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewLeadItem, DiveViewSac, DiveViewWeightingFeedback

### Community 137 - "Community 137"
Cohesion: 0.12
Nodes (19): CENTRE_SOURCE_NAME, CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+11 more)

### Community 139 - "Community 139"
Cohesion: 0.16
Nodes (16): errorMessage(), formatOf(), createImportService(), cancelImport(), createImport(), endWaiting(), expireWaiting(), openUpload() (+8 more)

### Community 140 - "Community 140"
Cohesion: 0.23
Nodes (20): DiveOrigin, REFERENCE_PREFIX, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef(), MATERIAL_OF_TANK_TYPE (+12 more)

### Community 141 - "Community 141"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 142 - "Community 142"
Cohesion: 0.15
Nodes (5): ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits

### Community 143 - "Community 143"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 144 - "Community 144"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource/poppins@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 145 - "Community 145"
Cohesion: 0.14
Nodes (16): AssessmentError, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), requireManagedDive(), isRule() (+8 more)

### Community 146 - "Community 146"
Cohesion: 0.13
Nodes (18): PARTICIPANT_ROLES, SUGGESTION_STATUSES, DiveProfileView, AcceptBody, STATUS, SuggestionRouteDeps, ADR-0070, SuggestionService (+10 more)

### Community 147 - "Community 147"
Cohesion: 0.13
Nodes (17): siteImportStatus, SiteImportRouteDeps, StartBody, STATUS, ADR-0021, SiteImportError, SiteImportService, Area (+9 more)

### Community 148 - "Community 148"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 149 - "Community 149"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 150 - "Community 150"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 151 - "Community 151"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 153 - "Community 153"
Cohesion: 0.18
Nodes (3): FakeInstances, FakeTokens, SyncAuthTest

### Community 154 - "Community 154"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 155 - "Community 155"
Cohesion: 0.12
Nodes (16): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+8 more)

### Community 156 - "Community 156"
Cohesion: 0.15
Nodes (3): Radius, drawBand(), ProfileSketch()

### Community 157 - "Community 157"
Cohesion: 0.17
Nodes (12): sampleCount(), storedList(), tryParseJson(), APP_PARAMS, createSsiTransport(), exchange(), isObject(), SSI_APP_USER_AGENT (+4 more)

### Community 158 - "Community 158"
Cohesion: 0.23
Nodes (15): prepareEntry(), DENIED_KEY_PARTS, emptyRedactionCounters(), isDeniedKey(), maskString(), redactInner(), redactLogObject(), RedactOptions (+7 more)

### Community 159 - "Community 159"
Cohesion: 0.15
Nodes (10): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, ADR-0068 (+2 more)

### Community 160 - "Community 160"
Cohesion: 0.17
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 162 - "Community 162"
Cohesion: 0.12
Nodes (13): clientDiagnostic, clientDiagnosticsSettings, ClientDiagnosticsEntryInput, ClientDiagnosticsEntryView, ClientDiagnosticsError, ClientDiagnosticsUpload, KEEP_NEWEST, MAX_UPLOAD_ENTRIES (+5 more)

### Community 163 - "Community 163"
Cohesion: 0.14
Nodes (13): AL80, EstimateDiveCylinder, LEAD_CASES, leadInput(), LeadVectorInput, planOf(), STEEL_12, ADR-0031 (+5 more)

### Community 164 - "Community 164"
Cohesion: 0.19
Nodes (13): asEstimateInput(), evaluateLeadVectorCase(), LEAD_VECTOR_KERNEL, leadVectorFile(), VECTOR_KERNELS, VectorFile, vectorFile(), assertMatch() (+5 more)

### Community 165 - "Community 165"
Cohesion: 0.21
Nodes (11): buildSupportBundle(), buildTarGz(), json(), ndjson(), octal(), SUPPORT_BUNDLE_FORMAT, SUPPORT_BUNDLE_FORMAT_VERSION, SupportBufferManifest (+3 more)

### Community 166 - "Community 166"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 167 - "Community 167"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 168 - "Community 168"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 169 - "Community 169"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 170 - "Community 170"
Cohesion: 0.13
Nodes (14): concurrency, cancel-in-progress, group, env, RELEASE_TAG, jobs, publish, name (+6 more)

### Community 172 - "Community 172"
Cohesion: 0.24
Nodes (3): NeighboursView, NeighboursViewNext, NeighboursViewPrevious

### Community 174 - "Community 174"
Cohesion: 0.20
Nodes (5): LogRingBuffer, RedactionCounters, SupportBundleInput, SupportBundleManifest, SupportServiceDeps

### Community 175 - "Community 175"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 176 - "Community 176"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 179 - "Community 179"
Cohesion: 0.27
Nodes (12): asText(), compareReadBack(), datasetLength(), describe(), isObject(), keyDifferences(), pause(), recording() (+4 more)

### Community 180 - "Community 180"
Cohesion: 0.17
Nodes (12): createSecretBox(), services(), services(), credentials(), Numbered, SSI, Status, ADR-0024 (+4 more)

### Community 181 - "Community 181"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 183 - "Community 183"
Cohesion: 0.26
Nodes (3): DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 184 - "Community 184"
Cohesion: 0.18
Nodes (10): LogBufferStats, SupportBundle, ClientDiagnosticsProvider, FailedBackup, FailedJob, HealthSection, iso(), JobsSection (+2 more)

### Community 185 - "Community 185"
Cohesion: 0.18
Nodes (9): AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031, WETSUIT_5 (+1 more)

### Community 186 - "Community 186"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 188 - "Community 188"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 189 - "Community 189"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 190 - "Community 190"
Cohesion: 0.20
Nodes (10): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, ADR-0076, MODULE_IMPORTS, moduleRules (+2 more)

### Community 191 - "Community 191"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 192 - "Community 192"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 195 - "Community 195"
Cohesion: 0.20
Nodes (9): LeadEstimateAnswer, Confidence, EstimateAdjustment, EstimateBasis, EstimateInput, EstimateMissing, LeadEstimate, ADR-0025 (+1 more)

### Community 196 - "Community 196"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 197 - "Community 197"
Cohesion: 0.20
Nodes (10): verify, prerelease, sha, tag, versionCode, versionName, name, outputs (+2 more)

### Community 198 - "Community 198"
Cohesion: 0.20
Nodes (10): with, cache, distribution, fetch-depth, if-no-files-found, java-version, name, path (+2 more)

### Community 199 - "Community 199"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 202 - "Community 202"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 204 - "Community 204"
Cohesion: 0.31
Nodes (5): BufferedLog, createLogRingBuffer(), LOG_BUFFER_CAPACITY_BYTES, LOG_BUFFER_CAPACITY_ENTRIES, LogRingBufferOptions

### Community 205 - "Community 205"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 206 - "Community 206"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 207 - "Community 207"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 208 - "Community 208"
Cohesion: 0.29
Nodes (5): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, EstimateCylinder

### Community 209 - "Community 209"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 211 - "Community 211"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 212 - "Community 212"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 213 - "Community 213"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 214 - "Community 214"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 215 - "Community 215"
Cohesion: 0.40
Nodes (5): DivePhase, Dive, Error, Loading, Offline

### Community 217 - "Community 217"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build

### Community 218 - "Community 218"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 219 - "Community 219"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 222 - "Community 222"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 223 - "Community 223"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 224 - "Community 224"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1310 isolated node(s):** `Sent`, `Reachable`, `Unknown`, `Reachable`, `Reserve` (+1305 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3715 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **147 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 2` to `Community 128`, `Community 131`, `Community 3`, `Community 5`, `Community 6`, `Community 7`, `Community 8`, `Community 13`, `Community 15`, `Community 16`, `Community 17`, `Community 18`, `Community 20`, `Community 22`, `Community 24`, `Community 28`, `Community 31`, `Community 33`, `Community 162`, `Community 41`, `Community 43`, `Community 49`, `Community 51`, `Community 52`, `Community 53`, `Community 180`, `Community 184`, `Community 60`, `Community 71`, `Community 87`, `Community 93`, `Community 100`, `Community 102`, `Community 103`, `Community 115`?**
  _High betweenness centrality (0.020) - this node is a cross-community bridge._
- **What connects `Sent`, `Reachable`, `Unknown` to the rest of the system?**
  _1310 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.033385037585013724 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 4` to `Community 0`, `Community 1`, `Community 9`, `Community 107`, `Community 78`, `Community 14`, `Community 144`, `Community 113`, `Community 48`, `Community 25`, `Community 27`, `Community 92`, `Community 30`, `Community 63`?**
  _High betweenness centrality (0.015) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.021362695275738752 - nodes in this community are weakly interconnected._
- **Why does `DiveStore` connect `Community 36` to `Community 32`, `Community 96`, `Community 58`, `Community 37`, `Community 39`, `Community 10`, `Community 44`, `Community 45`, `Community 46`, `Community 178`, `Community 83`, `Community 55`, `Community 153`, `Community 26`, `Community 61`?**
  _High betweenness centrality (0.014) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.03048310773632662 - nodes in this community are weakly interconnected._