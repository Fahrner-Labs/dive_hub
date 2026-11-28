# Graph Report - dive_hub  (2026-11-28)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 13744 nodes · 42464 edges · 454 communities (247 shown, 207 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 1001 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `4aeda260`
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
- Community 238
- Community 239
- Community 240
- Community 241
- Community 242
- Community 243
- Community 244
- Community 245
- Community 246
- Community 247
- Community 248
- Community 249
- Community 250
- Community 251
- Community 252
- Community 253
- Community 254
- Community 255
- Community 256
- Community 257
- Community 258
- Community 259
- Community 260
- Community 261
- Community 262
- Community 263
- Community 264
- Community 265
- Community 266
- Community 267
- Community 268
- Community 269
- Community 270
- Community 271
- Community 272
- Community 273
- Community 274
- Community 275
- Community 276
- Community 277
- Community 278
- Community 279
- Community 280
- Community 281
- Community 282
- Community 283
- Community 284
- Community 285
- Community 286
- Community 288
- Community 289
- Community 291
- Community 292
- Community 293
- Community 294
- Community 295
- Community 296
- Community 297
- Community 302

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 218 edges
2. `Button()` - 209 edges
3. `useErrorText()` - 181 edges
4. `useDisplay()` - 168 edges
5. `drizzle-orm` - 162 edges
6. `unwrap()` - 145 edges
7. `Muted()` - 143 edges
8. `DiveStore` - 139 edges
9. `createTestDatabase()` - 121 edges
10. `TestDatabase` - 120 edges

## Surprising Connections (you probably didn't know these)
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `ScanReplayTest` --calls--> `WriteQueue`  [INFERRED]
  apps/android/shared/src/jvmTest/kotlin/dev/divehub/shared/write/ScanReplayTest.kt → apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/write/WriteQueue.kt
- `WriteConflictResolutionTest` --calls--> `WriteQueue`  [INFERRED]
  apps/android/shared/src/jvmTest/kotlin/dev/divehub/shared/write/WriteConflictResolutionTest.kt → apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/write/WriteQueue.kt

## Import Cycles
- None detected.

## Communities (454 total, 207 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.01
Nodes (380): user, buddySuggestion, changeEvent, connection, dive, diveAssessment, diveLease, diver (+372 more)

### Community 1 - "Community 1"
Cohesion: 0.02
Nodes (277): ComputerChoice, ConnectionRow, diveFingerprint, DiveImportMode, DiveKind, providerGoneDive, ProviderImportPlan, providerLease (+269 more)

### Community 2 - "Community 2"
Cohesion: 0.02
Nodes (207): createServer(), AuthOptions, createAuth(), auth, hashPassword(), loadAuthSecret(), acquireAppLock(), BackupServiceDeps (+199 more)

### Community 3 - "Community 3"
Cohesion: 0.04
Nodes (246): ChangePassword(), DisplaySettings(), Sessions(), ADR-0080, useDescribeAgent(), AdminSettings(), ClientDiagnosticsSetting(), DeleteUserDialog() (+238 more)

### Community 4 - "Community 4"
Cohesion: 0.02
Nodes (194): cylinderCatalogueQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas (+186 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (109): cylinderLine(), cylinderPressures(), Cylinders(), Direction, Back, Left, Right, DirectionIcon() (+101 more)

### Community 6 - "Community 6"
Cohesion: 0.02
Nodes (163): APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), AppLockProbe, probeAppLock() (+155 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (55): DiveRow(), Figures(), RowFacts(), EmptyState(), FactRow(), NoteText(), StackedNoteText(), Radius (+47 more)

### Community 8 - "Community 8"
Cohesion: 0.02
Nodes (138): createDb(), ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS, eachFile(), readEntry(), ADR-0044 (+130 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (145): label(), @testing-library/react@^16.3.3, Me, AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery() (+137 more)

### Community 10 - "Community 10"
Cohesion: 0.02
Nodes (156): @modelcontextprotocol/server@^2.3.1, AI_ACCESS_NAME_MAX, aiAccessLog, aiAccessSetting, WEIGHTING_FELT, Access, AccessView, AiAccessRouteDeps (+148 more)

### Community 11 - "Community 11"
Cohesion: 0.01
Nodes (135): CHANNELS, isRule(), read(), ADR-0036, ENGINE_VERSION, AcceptedConsent, CONSENT_VERSIONS, ConsentKind (+127 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (91): DangerButton(), FieldButton(), InkButton(), SecondaryButton(), CylinderFields(), CylinderProblemText(), CylindersFields(), FieldPair() (+83 more)

### Community 13 - "Community 13"
Cohesion: 0.03
Nodes (127): BackupRunView, backupSettingsQuery(), BackupSettingsView, backupsQuery(), SiteDataView, supportBundlePreviewQuery(), SupportBundlePreviewView, ADR-0030 (+119 more)

### Community 14 - "Community 14"
Cohesion: 0.02
Nodes (123): CentreService, CentreRouteDeps, LEAD_PLACEMENTS, UTC_OFFSET_SOURCES, DiverService, CertificationHint, CertificationHintExpiry, DiveCertifications (+115 more)

### Community 15 - "Community 15"
Cohesion: 0.02
Nodes (118): ClientDiagnosticsSettingsView, clientDiagnosticsStatusQuery(), ClientDiagnosticsStatusView, ADR-0080, CertificationView, devicesQuery(), DeviceView, DiverDetailsView (+110 more)

### Community 16 - "Community 16"
Cohesion: 0.03
Nodes (124): DiveSummary, LogbookPage, PAGE_SIZE, buddiesForQuery(), buddiesQuery(), BuddyView, ConflictEntryView, ConflictsOfConnection (+116 more)

### Community 17 - "Community 17"
Cohesion: 0.04
Nodes (124): continuationAppend, OVERRIDABLE_FIELDS, OverridableField, renumber(), renumberStatement(), ADR-0048, accountKey(), applyExternalId() (+116 more)

### Community 18 - "Community 18"
Cohesion: 0.03
Nodes (23): AppContainer, PreferencesInstanceUrlStore, PreferencesPollingIntervalStore, AndroidDiveStore, PushController, DiveEditViewModel, DiverChoice, DiverSearchState (+15 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (110): ImportedValues, diveSite, siteImport, sitePurge, Placement, dataIdsOf(), SearchInput, ADR-0020 (+102 more)

### Community 20 - "Community 20"
Cohesion: 0.04
Nodes (93): ReportProblemSettings(), SecuritySettings(), billingCheckout(), billingPortal(), BillingView, equipmentDueQuery(), Connections(), CodeFigure() (+85 more)

### Community 21 - "Community 21"
Cohesion: 0.03
Nodes (111): diversQuery(), DiveValues, OverridableField, revisionsQuery(), RequirementView, CylinderList(), useCylinderText(), DiveHistory() (+103 more)

### Community 22 - "Community 22"
Cohesion: 0.06
Nodes (6): Spacing, SectionTest, LoadingScreenshotTest, DiveViewFromRecording, DiveViewFromRecordingStartsAt, ErrorCodes

### Community 23 - "Community 23"
Cohesion: 0.03
Nodes (94): providerSiteData, Delivered, ProviderError, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord() (+86 more)

### Community 24 - "Community 24"
Cohesion: 0.06
Nodes (32): LiveHint, LiveHints, SyncRefresher, Body, FeedEntry, Hold, Offline, RefreshData (+24 more)

### Community 25 - "Community 25"
Cohesion: 0.06
Nodes (66): Formats, formatSyncTime(), GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult() (+58 more)

### Community 26 - "Community 26"
Cohesion: 0.09
Nodes (5): DiveEditScreenshotTest, DiveViewCylindersItemSeries, DiveEditSuit, pressureSeriesOptions(), DiveEditDraftTest

### Community 27 - "Community 27"
Cohesion: 0.06
Nodes (92): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, ADR-0089 (+84 more)

### Community 28 - "Community 28"
Cohesion: 0.04
Nodes (93): CertificationService, CertificationRouteDeps, DIVE_IMPORT_MODES, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053, CandidateRouteDeps (+85 more)

### Community 29 - "Community 29"
Cohesion: 0.05
Nodes (31): QuietButton(), ChangesList(), ConflictChoice(), ConflictEntry(), ConflictsRoute(), ConflictsScreen(), diveTitle(), FieldDecision() (+23 more)

### Community 30 - "Community 30"
Cohesion: 0.10
Nodes (9): Dive, PendingWriteStatus, Awaiting, Conflicted, Failed, Pending, ScanEntities, WriteEntities (+1 more)

### Community 31 - "Community 31"
Cohesion: 0.04
Nodes (88): equipmentItem, serviceRecord, serviceRecordSchedule, serviceSchedule, managementOf(), addMonths(), CountDue, countReason() (+80 more)

### Community 32 - "Community 32"
Cohesion: 0.05
Nodes (79): i18next@^26.4.2, LoadingsView, assessmentQuery(), AssessmentView, samplesQuery(), AssessmentContext, AssessmentPanel(), AssessmentProvider() (+71 more)

### Community 33 - "Community 33"
Cohesion: 0.04
Nodes (84): Queryable, jointDiveDismissal, JointFillField, ADR-0016, alignedForMatching(), CandidateDive, decideMatch(), depthsDisagree() (+76 more)

### Community 34 - "Community 34"
Cohesion: 0.04
Nodes (79): replyCertificationError, STATUS, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, errors (+71 more)

### Community 35 - "Community 35"
Cohesion: 0.03
Nodes (73): BACKUP_RUN_TASK, backupTask(), ADR-0069, FcmServiceAccount, account, accountRelations, apikey, rateLimit (+65 more)

### Community 36 - "Community 36"
Cohesion: 0.03
Nodes (68): ParticipantRole, DAY_MS, inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), placeLocalTime(), Position (+60 more)

### Community 37 - "Community 37"
Cohesion: 0.03
Nodes (77): AssessmentService, AssessmentView, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView() (+69 more)

### Community 38 - "Community 38"
Cohesion: 0.04
Nodes (73): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+65 more)

### Community 39 - "Community 39"
Cohesion: 0.05
Nodes (29): rememberReducedMotion(), rememberSkeletonAlpha(), SkeletonBar(), AvatarCircle(), Error(), FilterAction(), Filters(), FilterSheet() (+21 more)

### Community 40 - "Community 40"
Cohesion: 0.04
Nodes (72): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+64 more)

### Community 41 - "Community 41"
Cohesion: 0.04
Nodes (64): EntitlementService, AttemptLimit, clientKey(), limitAttempts(), SIGNUP_ATTEMPTS, ADR-0061, Problem, ProblemCode (+56 more)

### Community 42 - "Community 42"
Cohesion: 0.07
Nodes (75): allButDive(), continuationCandidateQuery(), deletedDivesOn(), deletedDivesQuery(), diveQuery(), DiveView, mergeCandidatesQuery(), nearbyDivesQuery() (+67 more)

### Community 43 - "Community 43"
Cohesion: 0.04
Nodes (69): downsampleMinMax(), BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071 (+61 more)

### Community 44 - "Community 44"
Cohesion: 0.05
Nodes (67): accountListEntry, device, diverExternalId, tidyName(), foldName(), nameDiffers(), Buddy, createBuddyService() (+59 more)

### Community 45 - "Community 45"
Cohesion: 0.04
Nodes (13): DiveStore, toBoolean(), toLong(), toModel(), BodyWeight, Recording, ScanStatus, Created (+5 more)

### Community 46 - "Community 46"
Cohesion: 0.05
Nodes (59): SsiClient, SsiRecord, contextOf(), asNumber(), asText(), buildDeleteRecord(), buildUpdateRecord(), compareReadBack() (+51 more)

### Community 47 - "Community 47"
Cohesion: 0.05
Nodes (15): ApiClient, ApiErrorBody, DiveRepository, decodeList(), providerDirectory(), WriteCapability, ApiClientTest, DiveRepositoryTest (+7 more)

### Community 48 - "Community 48"
Cohesion: 0.07
Nodes (11): ScanViewModelTest, ScanResult, ReadCodes, Applied, Conflicted, Dropped, EntryOutcome, Failed (+3 more)

### Community 49 - "Community 49"
Cohesion: 0.06
Nodes (13): EditSource, LeadEstimateViewModelTest, ApiDiverBodyWeightsGet200ResponseItem, DiverView, Cylinder, Diver, DiveSite, PendingWriteKind (+5 more)

### Community 50 - "Community 50"
Cohesion: 0.06
Nodes (20): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+12 more)

### Community 51 - "Community 51"
Cohesion: 0.04
Nodes (63): dependencies, @dive-hub/api-client@workspace:*, @fontsource/poppins@^5.3.0, @internationalized/date@^3.12.4, lucide-react@^1.51.0, qr@0.7.2, react@^19.3.0, react-aria-components@^1.21.1 (+55 more)

### Community 52 - "Community 52"
Cohesion: 0.04
Nodes (13): KeystoreTokenStore, PreferencesCachedMeStore, UserCacheStore, FakeCachedMe, SettingsRepository, CachedMeStore, InstanceUrlStore, PollingIntervalStore (+5 more)

### Community 53 - "Community 53"
Cohesion: 0.09
Nodes (11): FakeCache, FakeCachedMe, FakeInstances, FakeQueue, FakeTokens, Harness, json(), SessionRepositoryTest (+3 more)

### Community 54 - "Community 54"
Cohesion: 0.06
Nodes (19): DiveHubApp(), DiveHubScreens, ErrorText(), Gate(), InstanceForm(), InstanceStatus(), LoadingScreen(), PasswordField() (+11 more)

### Community 55 - "Community 55"
Cohesion: 0.06
Nodes (67): Answer, answer(), baseFrom(), BodyWeight, Confidence, high, low, medium (+59 more)

### Community 56 - "Community 56"
Cohesion: 0.04
Nodes (64): DUE_STATES, EquipmentService, RecordInput, ScheduleInput, DiveGearItemViewSchema, DiveGearViewSchema, DueState, EquipmentDetailViewSchema (+56 more)

### Community 57 - "Community 57"
Cohesion: 0.06
Nodes (55): defineTool(), Id(), Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), HINT (+47 more)

### Community 58 - "Community 58"
Cohesion: 0.08
Nodes (52): BottomTime, BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen, BottomTimeLimits (+44 more)

### Community 59 - "Community 59"
Cohesion: 0.08
Nodes (66): managedDiverIds(), mergeProviderCylinder(), providerCylinderOf(), participantsOf(), columnsOf(), leadOf(), mergeProviderLead(), kindOfMode() (+58 more)

### Community 60 - "Community 60"
Cohesion: 0.05
Nodes (62): ConflictChoices, ConflictFieldView, providersQuery(), connectableProviders(), diveTargets(), Overrides, Paths, PluralBase (+54 more)

### Community 61 - "Community 61"
Cohesion: 0.05
Nodes (42): captureDiagnostic(), captureError(), cleanContext(), clearDiagnostics(), CLIENT_NAME, CLIENT_VERSION, DiagnosticEntry, diagnosticFromError() (+34 more)

### Community 62 - "Community 62"
Cohesion: 0.06
Nodes (62): ANDROID_UI, AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch() (+54 more)

### Community 63 - "Community 63"
Cohesion: 0.08
Nodes (13): SyncCheckpoint, ChangeFeedEvent, ChangeFeedPage, queuedProfileCheckpoint(), Complete, SessionEnded, Stopped, SyncEngine (+5 more)

### Community 64 - "Community 64"
Cohesion: 0.04
Nodes (56): billingHeaders, ADR-0087, headers, ADR-0030, globalSetup(), ADR-0023, ADR-0088, AiAccess (+48 more)

### Community 65 - "Community 65"
Cohesion: 0.08
Nodes (13): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), GasPlanInputTest (+5 more)

### Community 66 - "Community 66"
Cohesion: 0.04
Nodes (61): dependencies, better-auth@1.7.7, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1 (+53 more)

### Community 67 - "Community 67"
Cohesion: 0.05
Nodes (51): EMPTY_SITE_IMPORT_COUNTS, ImportArea, PurgeReferencedSite, SiteImportCounts, SitePurgeProgress, SitePurgeResult, StoredConflictField, ADR-0021 (+43 more)

### Community 68 - "Community 68"
Cohesion: 0.06
Nodes (48): looksLikeZip(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy(), ADR-0024 (+40 more)

### Community 69 - "Community 69"
Cohesion: 0.08
Nodes (10): DiveHubTopBar(), titleLineHeight(), TitleSkeleton(), CameraChrome, DiveHubColors, DiveHubTheme(), isDarkScheme(), successColor() (+2 more)

### Community 70 - "Community 70"
Cohesion: 0.07
Nodes (13): Missing(), LeadAnswer, LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseBasis (+5 more)

### Community 71 - "Community 71"
Cohesion: 0.05
Nodes (12): findActivity(), CameraFailure, cameraFailure(), Blocked, Busy, Missing, CameraScanner(), CameraScannerFrame() (+4 more)

### Community 72 - "Community 72"
Cohesion: 0.07
Nodes (18): ApiMeGet200Response, ApiMeGet200ResponsePreferences, SignInEmailRequest, UserView, InstanceUrl, HealthView, InstanceProbeResult, Loading (+10 more)

### Community 73 - "Community 73"
Cohesion: 0.12
Nodes (24): Body, BrokenBody, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage(), diverJson() (+16 more)

### Community 74 - "Community 74"
Cohesion: 0.06
Nodes (51): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, answer(), baseFrom(), cylindersAtReserve(), CylinderTerm (+43 more)

### Community 75 - "Community 75"
Cohesion: 0.06
Nodes (9): MainViewModel, Unreachable, IntHolder, MainViewModelPendingWarningTest, WarningCache, WarningCachedMe, WarningInstances, WarningQueue (+1 more)

### Community 76 - "Community 76"
Cohesion: 0.05
Nodes (49): ImportErrorCode, ImportFound, importJob, importOriginal, ImportOutcome, errorMessage(), parseWithFitFileParser(), formatOf() (+41 more)

### Community 78 - "Community 78"
Cohesion: 0.06
Nodes (5): QrImageDecoder, LauncherIconTest, SplashTheme, SplashThemeTest, QrImageDecoderTest

### Community 79 - "Community 79"
Cohesion: 0.13
Nodes (7): DiveMergeTest, diveObject(), jsonOf(), participant(), participants(), putDive(), WriteQueueTest

### Community 80 - "Community 80"
Cohesion: 0.06
Nodes (44): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+36 more)

### Community 81 - "Community 81"
Cohesion: 0.07
Nodes (46): surfacePressureBar(), AIR, cnsDecayed(), DEFAULT_CONDITIONS, N2_REPLAY_WINDOW_HOURS, N2_STALE_AFTER_HOURS, N2Loadings, N2LoadingsBase (+38 more)

### Community 82 - "Community 82"
Cohesion: 0.08
Nodes (20): LogbookUiState, LogbookViewModel, neighboursPath(), countOf(), FilterChip, filterChips(), LogbookFilter, CHANGED_SINCE_SENT (+12 more)

### Community 83 - "Community 83"
Cohesion: 0.07
Nodes (37): BillingError, BillingLog, createBillingService(), ADR-0013, ADR-0092, createStripeClient(), post(), invoiceMetadataOwner() (+29 more)

### Community 84 - "Community 84"
Cohesion: 0.05
Nodes (44): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+36 more)

### Community 85 - "Community 85"
Cohesion: 0.06
Nodes (37): ADR-0051, ADR-0047, clearPushRuns(), clearQueue(), connectSsi(), deletableDive(), expireSsiTokens(), fetchState() (+29 more)

### Community 86 - "Community 86"
Cohesion: 0.04
Nodes (48): depends_on, environment, image, ports, restart, volumes, condition, environment (+40 more)

### Community 87 - "Community 87"
Cohesion: 0.06
Nodes (6): DiveHubApplication, DiveHubMessagingService, PushWorker, SyncWorker, WorkManagerPeriodicSync, PeriodicSyncScheduler

### Community 89 - "Community 89"
Cohesion: 0.07
Nodes (13): ApiToolsBottomTimeGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseLimits, ApiToolsBottomTimeGet200ResponseResidualN2, ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue (+5 more)

### Community 90 - "Community 90"
Cohesion: 0.05
Nodes (40): CYLINDER_MATERIALS, SUIT_TYPES, SUIT_UNDERGARMENTS, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053 (+32 more)

### Community 91 - "Community 91"
Cohesion: 0.07
Nodes (26): headers, ADR-0043, ADR-0080, ADR-0016, ADR-0071, ADR-0062, ADR-0042, ADR-0021 (+18 more)

### Community 92 - "Community 92"
Cohesion: 0.08
Nodes (40): Leave, Series, asBool(), DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi() (+32 more)

### Community 93 - "Community 93"
Cohesion: 0.07
Nodes (40): Blocked, classify(), FailureOutcome, failureOutcome(), kindOf(), KINDS, LEFT_OUT_KEY, LEFT_OUT_KEYS (+32 more)

### Community 94 - "Community 94"
Cohesion: 0.06
Nodes (42): IfMatchHeaders, ifMatchVersion(), missingVersion(), ADR-0083, USED, usesIfMatch(), versionOf(), REVISION_CAUSES (+34 more)

### Community 95 - "Community 95"
Cohesion: 0.07
Nodes (31): headers, ADR-0031, diveWith(), ADR-0028, ADR-0043, buddySite(), Dive, OTHER (+23 more)

### Community 96 - "Community 96"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 97 - "Community 97"
Cohesion: 0.08
Nodes (15): Answers, number(), ToolsInputs, ToolsUiState, ToolsViewModel, ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions (+7 more)

### Community 98 - "Community 98"
Cohesion: 0.09
Nodes (41): DiveGearItemView, diveGearQuery(), DiveGearView, EquipmentCategory, EquipmentDetailView, EquipmentDueView, equipmentItemQuery(), EquipmentItemView (+33 more)

### Community 100 - "Community 100"
Cohesion: 0.10
Nodes (40): logbookCheckAnswer, logbookCheckDiveAnswer, agreeInDepthAndDuration(), CheckedDive, DeletableDive, findChecks(), followsSoon(), inChains() (+32 more)

### Community 101 - "Community 101"
Cohesion: 0.05
Nodes (42): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+34 more)

### Community 102 - "Community 102"
Cohesion: 0.07
Nodes (39): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+31 more)

### Community 103 - "Community 103"
Cohesion: 0.11
Nodes (6): ApiDiveCentresPostRequest, ApiDiveCentresPostRequestExternalIdsItem, ApiDiversFromCodePostRequest, ApiVerificationCodesReadPostRequest, PendingWrite, WriteQueue

### Community 104 - "Community 104"
Cohesion: 0.06
Nodes (28): Tx, CandidateError, CandidateRow, createCandidateService(), lockMine(), RecordingRow, ADR-0016, ADR-0038 (+20 more)

### Community 105 - "Community 105"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 106 - "Community 106"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 108 - "Community 108"
Cohesion: 0.10
Nodes (12): ApiDiversIdLeadEstimateGet200ResponseBasisDivesItem, ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseCns, ApiDiversIdLoadingsGet200ResponseN2, ApiDiversIdLoadingsGet200ResponseNoFly, ApiDiversIdLoadingsGet200ResponseNoFlySource, ApiDiversIdLoadingsGet200ResponseOtu (+4 more)

### Community 109 - "Community 109"
Cohesion: 0.07
Nodes (29): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError (+21 more)

### Community 110 - "Community 110"
Cohesion: 0.07
Nodes (28): equipmentUse, managesDiveDiver(), Participant, participantsOfDives(), DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly (+20 more)

### Community 111 - "Community 111"
Cohesion: 0.10
Nodes (33): LeadPlacement, SuitType, SuitUndergarment, WeightingFelt, ADR-0025, WaterMismatch, waterMismatch(), valuesFromRecording() (+25 more)

### Community 112 - "Community 112"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 114 - "Community 114"
Cohesion: 0.16
Nodes (3): DiveEditViewModelTest, FakeSearch, FoundDiver

### Community 115 - "Community 115"
Cohesion: 0.12
Nodes (35): DEFAULT_SSI_URL, acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive() (+27 more)

### Community 116 - "Community 116"
Cohesion: 0.09
Nodes (33): AscentPressure, DiverPlan, GasPlan, GasPlanDiver, GasPlanInput, GasPlanLevel, GasPlanLimit, depth (+25 more)

### Community 117 - "Community 117"
Cohesion: 0.11
Nodes (8): AppNavHost(), AppScreens, clearSavedTabStates(), isShowing(), leaveDiveForLogbook(), MainShell(), navigateToTab(), tabOf()

### Community 118 - "Community 118"
Cohesion: 0.08
Nodes (9): followStoreWrites(), SettingsDiagnosticsState, SettingsQueueState, SettingsUiState, SettingsViewModel, FakeInstances, ReportProblemResult, Sent (+1 more)

### Community 119 - "Community 119"
Cohesion: 0.11
Nodes (11): DiveListViewDivesItem, DiveListViewDivesItemGasesItem, DiveListViewDivesItemSite, DiveListViewMonthsItem, DiveProfileView, localMonthOf(), LogbookMonth, monthGroups() (+3 more)

### Community 120 - "Community 120"
Cohesion: 0.14
Nodes (4): LogbookScreenTest, DiveListView, DiveListViewCounts, DiveListViewTotals

### Community 121 - "Community 121"
Cohesion: 0.11
Nodes (14): LogbookPaging, logbookPath(), AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint, SketchProfile (+6 more)

### Community 123 - "Community 123"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 124 - "Community 124"
Cohesion: 0.11
Nodes (7): DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewLeadItem, DiveViewSac, DiveViewSite, DiveViewSuit, DiveViewWeightingFeedback

### Community 125 - "Community 125"
Cohesion: 0.11
Nodes (4): FakeCache, FakeInstances, FakeTokens, MainViewModelTest

### Community 126 - "Community 126"
Cohesion: 0.17
Nodes (8): bottomTime(), otu(), oxygenLimitMinutes(), PlanningSacResult, BottomTimeToolInput, GasNumbersInput, ToolDiverSource, ToolsServiceTest

### Community 127 - "Community 127"
Cohesion: 0.13
Nodes (7): GateCachedMe, GateCall, GateClient, GateInstances, GateServer, GateTokens, WriteExitGateTest

### Community 128 - "Community 128"
Cohesion: 0.12
Nodes (27): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+19 more)

### Community 129 - "Community 129"
Cohesion: 0.25
Nodes (8): leadAnswer(), EstimateCylinder, PlannedConditions, Suit, assemblePersonalizedSources(), suitOf(), leadEstimate(), PersonalizedToolsTest

### Community 130 - "Community 130"
Cohesion: 0.09
Nodes (8): ApiDiveSitesGet200Response, SiteView, SiteViewExternalIdsItem, SiteViewExternalIdsItemAttribution, SiteViewExternalIdsItemOffered, SiteViewExternalIdsItemOfferedPosition, SiteViewExternalIdsItemPrivateTo, SiteViewPosition

### Community 131 - "Community 131"
Cohesion: 0.10
Nodes (23): dayNumber(), enteredDecoFromNdl(), minNdlSeconds(), NoFly, noFlyAfter(), NoFlyReason, decompression, several (+15 more)

### Community 132 - "Community 132"
Cohesion: 0.09
Nodes (26): AL80, asEstimateInput(), EstimateDiveCylinder, evaluateLeadVectorCase(), LEAD_CASES, LEAD_VECTOR_KERNEL, leadInput(), leadVectorFile() (+18 more)

### Community 133 - "Community 133"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 134 - "Community 134"
Cohesion: 0.18
Nodes (3): FakeFirebase, PushControllerTest, RecordingDiagnostics

### Community 135 - "Community 135"
Cohesion: 0.12
Nodes (12): WriteDrain, Drained, Empty, SessionEnded, Stopped, WriteReplayResult, SyncDrainTest, WriteDrain (+4 more)

### Community 136 - "Community 136"
Cohesion: 0.13
Nodes (8): InstanceProbeTest, json(), probe(), ProbeCache, ProbeCachedMe, ProbeInstances, probeRepository(), ProbeTokens

### Community 137 - "Community 137"
Cohesion: 0.09
Nodes (26): date(), FitMessage, FitMessages, GARMIN_PRODUCTS, num(), position(), positive(), RECORD_CHANNELS (+18 more)

### Community 139 - "Community 139"
Cohesion: 0.13
Nodes (23): DiveViewRecordingsItemSummary, planningSac(), PlanningSacDive, jsRound(), powInt(), roundTo(), Sac, SacCylinder (+15 more)

### Community 140 - "Community 140"
Cohesion: 0.12
Nodes (26): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+18 more)

### Community 141 - "Community 141"
Cohesion: 0.10
Nodes (7): ToolPage, Gas, Index, Lead, Loadings, Plan, FakeScreens

### Community 144 - "Community 144"
Cohesion: 0.18
Nodes (10): Body, buddyAnswer(), centreAnswer(), centreView(), diverView(), Offline, ScanCall, ScanHarness (+2 more)

### Community 145 - "Community 145"
Cohesion: 0.09
Nodes (23): computerEventOf(), ADR-0036, computerEventFromFit(), COMPUTER_EVENT, computerEventFromSuunto(), DECO_MODEL, decoModelFromSuunto, DIVE_MODE (+15 more)

### Community 146 - "Community 146"
Cohesion: 0.16
Nodes (4): DiveDetailScreen(), NeighboursView, NeighboursViewNext, NeighboursViewPrevious

### Community 147 - "Community 147"
Cohesion: 0.10
Nodes (7): ApiClientDiagnosticsStatusGet200Response, ApiMeGet200ResponseBilling, ApiMeGet200ResponseOwnDiver, DiveDetailsListView, DiveDetailsListViewCounts, DiveDetailsListViewMonthsItem, DiveDetailsListViewTotals

### Community 148 - "Community 148"
Cohesion: 0.11
Nodes (7): ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewOffersOffer, ConnectionViewWaiting

### Community 149 - "Community 149"
Cohesion: 0.11
Nodes (7): ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport

### Community 150 - "Community 150"
Cohesion: 0.10
Nodes (22): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+14 more)

### Community 151 - "Community 151"
Cohesion: 0.17
Nodes (26): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+18 more)

### Community 153 - "Community 153"
Cohesion: 0.12
Nodes (3): MainActivity, LoadingsUiState, LoadingsViewModel

### Community 154 - "Community 154"
Cohesion: 0.16
Nodes (3): FirebaseRuntime, hasPlayServices(), FirebaseRuntimeTest

### Community 155 - "Community 155"
Cohesion: 0.12
Nodes (6): DiagnosticHygiene, DiagnosticEntry, DiagnosticLevel, ERROR, WARN, DiagnosticsBuffer

### Community 157 - "Community 157"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 159 - "Community 159"
Cohesion: 0.12
Nodes (21): AssessmentError, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), requireManagedDive(), same() (+13 more)

### Community 160 - "Community 160"
Cohesion: 0.12
Nodes (20): BOM, Json, num(), obj(), positive(), scaled(), suuntoFitKey(), time() (+12 more)

### Community 161 - "Community 161"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 162 - "Community 162"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 163 - "Community 163"
Cohesion: 0.12
Nodes (10): Acquired, asTokenResult(), Failed, FirebaseStart, NoConfig, NoPlayServices, NotReady, PushFirebase (+2 more)

### Community 165 - "Community 165"
Cohesion: 0.13
Nodes (6): DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, WaitingPushView

### Community 166 - "Community 166"
Cohesion: 0.24
Nodes (6): Body, Call, Harness, Offline, Reply, WriteReplayerTest

### Community 167 - "Community 167"
Cohesion: 0.15
Nodes (21): NoFly, getDiverLoadings, DEFAULT_GF_HIGH, residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows(), DiveRow (+13 more)

### Community 168 - "Community 168"
Cohesion: 0.11
Nodes (21): CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043, CentreChanged (+13 more)

### Community 169 - "Community 169"
Cohesion: 0.11
Nodes (18): RevisionEntityType, CHANGE_EVENT_RETENTION_DAYS, ChangeEventView, ChangeFeedPage, ChangeKind, CHANGES_DEFAULT_LIMIT, CHANGES_MAX_LIMIT, FeedPosition (+10 more)

### Community 170 - "Community 170"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 172 - "Community 172"
Cohesion: 0.17
Nodes (6): Connected, Hint, HintFrame, LiveEvent, SseLiveHints, LiveHintsTest

### Community 173 - "Community 173"
Cohesion: 0.23
Nodes (5): Body, ConflictCall, ConflictHarness, ConflictReply, WriteConflictResolutionTest

### Community 174 - "Community 174"
Cohesion: 0.14
Nodes (18): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+10 more)

### Community 175 - "Community 175"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 176 - "Community 176"
Cohesion: 0.18
Nodes (3): LeadEstimateUiState, LeadEstimateViewModel, PersonalizedSources

### Community 177 - "Community 177"
Cohesion: 0.14
Nodes (4): FakeCachedMe, FakeInstances, FakeTokens, SyncAuthTest

### Community 178 - "Community 178"
Cohesion: 0.14
Nodes (5): ApiClientDiagnosticsPostRequest, ApiClientDiagnosticsPostRequestClient, ApiClientDiagnosticsPostRequestEntriesItem, Diagnostics, Failed

### Community 180 - "Community 180"
Cohesion: 0.15
Nodes (18): RecordingSummary, UtcOffsetSource, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput (+10 more)

### Community 181 - "Community 181"
Cohesion: 0.26
Nodes (17): ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres(), isHypoxic(), metresPerBar() (+9 more)

### Community 183 - "Community 183"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 184 - "Community 184"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 185 - "Community 185"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 186 - "Community 186"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 187 - "Community 187"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 188 - "Community 188"
Cohesion: 0.20
Nodes (7): PendingChangeWarning, PendingWarningDialog(), SignIn, SignInWarningDialog(), SignOut, SignOutWarningDialog(), PendingWarningDialogTest

### Community 191 - "Community 191"
Cohesion: 0.19
Nodes (11): LeadInputTest, SuitType, drysuit, none, semidry, skin, wetsuit, SuitUndergarment (+3 more)

### Community 193 - "Community 193"
Cohesion: 0.24
Nodes (18): DiveOrigin, REFERENCE_PREFIX, aboveZero(), cylinderOf(), deviceOf(), deviceOfRef(), MATERIAL_OF_TANK_TYPE, nonZero() (+10 more)

### Community 194 - "Community 194"
Cohesion: 0.14
Nodes (13): BufferedLog, LOG_BUFFER_CAPACITY_BYTES, LOG_BUFFER_CAPACITY_ENTRIES, LogBufferStats, LogRingBufferOptions, SupportBundle, ClientDiagnosticsProvider, FailedBackup (+5 more)

### Community 195 - "Community 195"
Cohesion: 0.16
Nodes (13): sampleCount(), storedList(), tryParseJson(), datasetLength(), APP_PARAMS, createSsiTransport(), exchange(), isObject() (+5 more)

### Community 196 - "Community 196"
Cohesion: 0.16
Nodes (14): execPnpm(), FORBIDDEN, isPermissive(), licenseProblems(), main(), ADR-0006, ADR-0086, PERMISSIVE (+6 more)

### Community 197 - "Community 197"
Cohesion: 0.17
Nodes (3): PushPreferences, ApiPushConfigGet200Response, ApiPushConfigGet200ResponseAndroid

### Community 199 - "Community 199"
Cohesion: 0.21
Nodes (14): clientVersionGate(), ADR-0076, ClientVersionRegistry, MIN_CLIENT_VERSIONS, MinClientVersion, ADR-0076, compareIdentifier(), compareNumeric() (+6 more)

### Community 200 - "Community 200"
Cohesion: 0.15
Nodes (10): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, ADR-0068 (+2 more)

### Community 201 - "Community 201"
Cohesion: 0.17
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 203 - "Community 203"
Cohesion: 0.27
Nodes (4): DiveDetailViewModel, NeighboursRead, ProfileRead, StoredDive

### Community 206 - "Community 206"
Cohesion: 0.19
Nodes (4): CentreView, CentreViewExternalIdsItem, CentreViewSitesItem, CentreViewVerificationCode

### Community 207 - "Community 207"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 209 - "Community 209"
Cohesion: 0.12
Nodes (13): clientDiagnostic, clientDiagnosticsSettings, ClientDiagnosticsEntryInput, ClientDiagnosticsEntryView, ClientDiagnosticsError, ClientDiagnosticsUpload, KEEP_NEWEST, MAX_UPLOAD_ENTRIES (+5 more)

### Community 210 - "Community 210"
Cohesion: 0.17
Nodes (14): JOINT_FILL_FIELDS, OfferRouteDeps, STATUS, ADR-0070, OfferService, Facts, Field, JointOfferAcceptView (+6 more)

### Community 211 - "Community 211"
Cohesion: 0.13
Nodes (9): FileFormat, FitAdapter, SuuntoJsonAdapter, UddfAdapter, ParsedRecording, adapter, crossCheck(), decodeOfficial() (+1 more)

### Community 212 - "Community 212"
Cohesion: 0.21
Nodes (11): buildSupportBundle(), buildTarGz(), json(), ndjson(), octal(), SUPPORT_BUNDLE_FORMAT, SUPPORT_BUNDLE_FORMAT_VERSION, SupportBufferManifest (+3 more)

### Community 213 - "Community 213"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 214 - "Community 214"
Cohesion: 0.13
Nodes (11): Dive, EGYPT, ELSEWHERE, Method, POSITION, runPurge(), Site, SiteImport (+3 more)

### Community 215 - "Community 215"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 216 - "Community 216"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 221 - "Community 221"
Cohesion: 0.13
Nodes (14): concurrency, cancel-in-progress, group, env, RELEASE_TAG, jobs, publish, name (+6 more)

### Community 222 - "Community 222"
Cohesion: 0.27
Nodes (5): ApiVerificationCodesReadPost200Response, ApiVerificationCodesReadPost200ResponseCandidatesItem, ReadChange, ReadCode, ReadExisting

### Community 223 - "Community 223"
Cohesion: 0.33
Nodes (4): ConflictField, DiveMerge, MergeOutcome, ParticipantsMergeOutcome

### Community 224 - "Community 224"
Cohesion: 0.20
Nodes (5): LogRingBuffer, RedactionCounters, SupportBundleInput, SupportBundleManifest, SupportServiceDeps

### Community 225 - "Community 225"
Cohesion: 0.14
Nodes (5): Dive, OTHER, ADR-0012, ADR-0023, ADR-0070

### Community 226 - "Community 226"
Cohesion: 0.20
Nodes (11): created, headers, importFixture(), ADR-0066, uploadFixture(), Status, ADR-0056, conflictAtSsi() (+3 more)

### Community 227 - "Community 227"
Cohesion: 0.15
Nodes (13): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, ADR-0076, ADR-0083, ADR-0085 (+5 more)

### Community 228 - "Community 228"
Cohesion: 0.14
Nodes (13): if, runs-on, steps, timeout-minutes, jobs, dco-check, name, on (+5 more)

### Community 229 - "Community 229"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 230 - "Community 230"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 231 - "Community 231"
Cohesion: 0.22
Nodes (11): flag(), idText(), isObject(), numberOrNull(), readSsiSite(), SsiSiteValues, WATER_OF_BOW, ALPHA3_TO_ALPHA2 (+3 more)

### Community 232 - "Community 232"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 233 - "Community 233"
Cohesion: 0.17
Nodes (11): PushState, ConfigFailed, InitFailed, NoPlayServices, NotSignedIn, Off, PermissionMissing, Registered (+3 more)

### Community 234 - "Community 234"
Cohesion: 0.48
Nodes (3): UiState, sessionDestination(), SessionDestinationTest

### Community 235 - "Community 235"
Cohesion: 0.32
Nodes (10): ConflictsRoute, DiveEditRoute, InstanceRoute, LogbookRoute, MainRoute, ScanRoute, SettingsDebugRoute, SettingsRoute (+2 more)

### Community 237 - "Community 237"
Cohesion: 0.26
Nodes (3): DiverDetailsView, DiverDetailsViewAccountsItem, DiverDetailsViewCodesItem

### Community 238 - "Community 238"
Cohesion: 0.26
Nodes (3): DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 240 - "Community 240"
Cohesion: 0.18
Nodes (10): BUDDY, headers, ownDiver(), Person, personSession(), STRANGER, ADR-0012, ADR-0028 (+2 more)

### Community 241 - "Community 241"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 242 - "Community 242"
Cohesion: 0.29
Nodes (3): pushFoundNewDive(), PushHint, PushHintTest

### Community 243 - "Community 243"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 244 - "Community 244"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 245 - "Community 245"
Cohesion: 0.22
Nodes (10): ADR-0023, devDependencies, dependency-cruiser@18.7.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript-eslint@8.72.0, dependency-cruiser, @eslint/js (+2 more)

### Community 246 - "Community 246"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 247 - "Community 247"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 248 - "Community 248"
Cohesion: 0.20
Nodes (10): dependencies, openapi-fetch@^0.17.0, exports, name, private, scripts, generate, typecheck (+2 more)

### Community 251 - "Community 251"
Cohesion: 0.33
Nodes (8): prepareEntry(), DENIED_KEY_PARTS, isDeniedKey(), maskString(), redactInner(), redactLogObject(), RedactOptions, redactValue()

### Community 252 - "Community 252"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 253 - "Community 253"
Cohesion: 0.20
Nodes (10): verify, prerelease, sha, tag, versionCode, versionName, name, outputs (+2 more)

### Community 254 - "Community 254"
Cohesion: 0.20
Nodes (10): with, cache, distribution, fetch-depth, if-no-files-found, java-version, name, path (+2 more)

### Community 255 - "Community 255"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 260 - "Community 260"
Cohesion: 0.25
Nodes (7): stubDive(), ADR-0015, ADR-0023, ADR-0025, ADR-0040, guardedRoute(), openFilters()

### Community 261 - "Community 261"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 262 - "Community 262"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 263 - "Community 263"
Cohesion: 0.22
Nodes (8): eslint@10.13.0, engines, node, license, name, packageManager, private, eslint

### Community 264 - "Community 264"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 270 - "Community 270"
Cohesion: 0.25
Nodes (8): typescript@7.0.2, typescript@7.0.2, typescript@6.0.3, typescript, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3, openapi-typescript

### Community 271 - "Community 271"
Cohesion: 0.43
Nodes (8): emptyRedactionCounters(), createSupportService(), assemble(), clientDiagnostics(), configSection(), failedJobs(), healthSection(), maskDatabaseUrl()

### Community 272 - "Community 272"
Cohesion: 0.36
Nodes (6): headers, open(), ownId(), ownRow(), sectionButton(), ADR-0050

### Community 273 - "Community 273"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 274 - "Community 274"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 275 - "Community 275"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 276 - "Community 276"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 277 - "Community 277"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 279 - "Community 279"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 280 - "Community 280"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 281 - "Community 281"
Cohesion: 0.40
Nodes (5): LogbookPhase, Error, List, Loading, Offline

### Community 283 - "Community 283"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build

### Community 284 - "Community 284"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 285 - "Community 285"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 286 - "Community 286"
Cohesion: 0.40
Nodes (4): TAGS, ADR-0023, { stdout, stderr }, total

### Community 293 - "Community 293"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 294 - "Community 294"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 295 - "Community 295"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1488 isolated node(s):** `Reserve`, `RockBottom`, `Sent`, `NoConfig`, `NoPlayServices` (+1483 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 4328 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **207 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `DiveStore` connect `Community 45` to `Community 129`, `Community 131`, `Community 135`, `Community 139`, `Community 143`, `Community 144`, `Community 18`, `Community 24`, `Community 153`, `Community 30`, `Community 166`, `Community 171`, `Community 173`, `Community 176`, `Community 48`, `Community 49`, `Community 177`, `Community 52`, `Community 189`, `Community 63`, `Community 65`, `Community 73`, `Community 204`, `Community 79`, `Community 208`, `Community 220`, `Community 97`, `Community 103`, `Community 114`, `Community 124`, `Community 127`?**
  _High betweenness centrality (0.017) - this node is a cross-community bridge._
- **What connects `Reserve`, `RockBottom`, `Sent` to the rest of the system?**
  _1488 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.013110881441015798 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 3` to `Community 32`, `Community 98`, `Community 4`, `Community 9`, `Community 42`, `Community 105`, `Community 13`, `Community 15`, `Community 16`, `Community 51`, `Community 20`, `Community 21`, `Community 60`?**
  _High betweenness centrality (0.013) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.015575402693164568 - nodes in this community are weakly interconnected._
- **Why does `AppContainer` connect `Community 18` to `Community 7`, `Community 138`, `Community 24`, `Community 153`, `Community 154`, `Community 155`, `Community 29`, `Community 171`, `Community 172`, `Community 45`, `Community 47`, `Community 48`, `Community 176`, `Community 178`, `Community 52`, `Community 54`, `Community 63`, `Community 65`, `Community 197`, `Community 72`, `Community 202`, `Community 208`, `Community 82`, `Community 87`, `Community 97`, `Community 103`, `Community 242`, `Community 118`?**
  _High betweenness centrality (0.012) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.016036205723852875 - nodes in this community are weakly interconnected._