# Graph Report - dive_hub  (2026-11-26)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 13500 nodes · 41776 edges · 437 communities (252 shown, 185 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 1000 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `35f1e44d`
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
- Community 284
- Community 285
- Community 286
- Community 287
- Community 288
- Community 289
- Community 290
- Community 291
- Community 295

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 213 edges
2. `Button()` - 204 edges
3. `useErrorText()` - 179 edges
4. `useDisplay()` - 166 edges
5. `drizzle-orm` - 160 edges
6. `unwrap()` - 145 edges
7. `Muted()` - 141 edges
8. `DiveStore` - 139 edges
9. `createTestDatabase()` - 119 edges
10. `TestDatabase` - 118 edges

## Surprising Connections (you probably didn't know these)
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `AuthOptions` --references--> `Db`  [EXTRACTED]
  apps/server/src/auth/auth.ts → apps/server/src/db/client.ts
- `AppNavHost()` --calls--> `DiveRoute`  [INFERRED]
  apps/android/app/src/main/kotlin/dev/divehub/android/ui/navigation/AppNavHost.kt → apps/android/app/src/main/kotlin/dev/divehub/android/ui/navigation/AppRoutes.kt

## Import Cycles
- None detected.

## Communities (437 total, 185 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (213): user, buddySuggestion, changeEvent, diver, jointDive, participant, revision, userEntitlement (+205 more)

### Community 1 - "Community 1"
Cohesion: 0.03
Nodes (250): AdminSettings(), ClientDiagnosticsSetting(), DeleteUserDialog(), Invitations(), Invite(), LinkToPassOn(), ProviderSiteData(), SiteDataPanel() (+242 more)

### Community 2 - "Community 2"
Cohesion: 0.02
Nodes (184): acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), AppLockProbe (+176 more)

### Community 3 - "Community 3"
Cohesion: 0.02
Nodes (194): Tx, device, diverManagement, jointDiveDismissal, JointFillField, OVERRIDABLE_FIELDS, OverridableField, recordingEvent (+186 more)

### Community 4 - "Community 4"
Cohesion: 0.04
Nodes (20): Radius, Formats, LogbookRoute(), label(), SettingsDebugRoute(), SettingsDebugScreen(), Spacing, SyncLine() (+12 more)

### Community 5 - "Community 5"
Cohesion: 0.02
Nodes (103): Section(), cylinderLine(), cylinderPressures(), Cylinders(), Direction, Back, Left, Right (+95 more)

### Community 6 - "Community 6"
Cohesion: 0.02
Nodes (40): AppContainer, PreferencesPollingIntervalStore, MainActivity, DiveEditUiState, DiveEditViewModel, DiverChoice, DiverSearchState, Failed (+32 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (173): ComputerChoice, DiveImportMode, DiveKind, importOriginal, providerSiteData, ProviderCylinder, siteRef(), entryMatches() (+165 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (153): label(), Me, AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery() (+145 more)

### Community 9 - "Community 9"
Cohesion: 0.02
Nodes (147): account, accountRelations, apikey, rateLimit, session, sessionRelations, ADR-0011, ADR-0012 (+139 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (124): auth, BackupServiceDeps, PaddleFetch, createServices(), ServiceParts, ADR-0012, ADR-0027, ADR-0030 (+116 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (146): DiveValues, OverridableField, cylinderCatalogueQuery(), CylinderFields(), MATERIALS, ADR-0034, ADR-0045, useCatalogueName() (+138 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (25): AndroidDiveStore, EditSource, ApiDiverBodyWeightsGet200ResponseItem, DiveStore, toBoolean(), toLong(), toModel(), BodyWeight (+17 more)

### Community 13 - "Community 13"
Cohesion: 0.02
Nodes (126): createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open(), saveAndReadBack() (+118 more)

### Community 14 - "Community 14"
Cohesion: 0.02
Nodes (126): CentreService, CentreRouteDeps, LEAD_PLACEMENTS, UTC_OFFSET_SOURCES, WEIGHTING_FELT, DiverService, downsampleMinMax(), CertificationHint (+118 more)

### Community 15 - "Community 15"
Cohesion: 0.03
Nodes (95): DangerButton(), FieldButton(), InkButton(), QuietButton(), SecondaryButton(), CylinderFields(), CylinderProblemText(), CylindersFields() (+87 more)

### Community 16 - "Community 16"
Cohesion: 0.02
Nodes (109): createServer(), createAuth(), loadAuthSecret(), workerDeps(), Config, createDb(), findChangedMigrations(), migrateDatabase() (+101 more)

### Community 17 - "Community 17"
Cohesion: 0.03
Nodes (128): Queryable, logbookCheckAnswer, logbookCheckDiveAnswer, managementOf(), keepApart(), keepDive(), keepRestored(), ADR-0038 (+120 more)

### Community 18 - "Community 18"
Cohesion: 0.03
Nodes (111): ConnectionRow, WaitingPushOptions, createEntitlementService(), EntitlementService, isEntitled(), SubscriptionState, ADR-0087, ADR-0089 (+103 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (8): DiveHubTopBar(), DiveHubTheme(), SectionTest, DiveHubTopBarTest, LoadingScreenshotTest, DiveViewFromRecording, DiveViewFromRecordingStartsAt, DiveViewRecordingsItemDevice

### Community 20 - "Community 20"
Cohesion: 0.06
Nodes (15): DiveDetailScreenshotTest, DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewLeadItem, DiveViewParticipantsItem, DiveViewPosition, DiveViewSac, DiveViewSite (+7 more)

### Community 21 - "Community 21"
Cohesion: 0.04
Nodes (109): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, ambientPressureBar() (+101 more)

### Community 22 - "Community 22"
Cohesion: 0.03
Nodes (115): aiAccessLog, aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, AiAccessScope, AiAccessService, KeyRow (+107 more)

### Community 23 - "Community 23"
Cohesion: 0.02
Nodes (91): diveAssessment, Assessment, FindingView, Row, ADR-0036, upload(), DiveView, halves() (+83 more)

### Community 24 - "Community 24"
Cohesion: 0.07
Nodes (18): LeadEstimateViewModelTest, PlanningSacSource, default, logbook, Dive, PendingWriteStatus, Awaiting, Conflicted (+10 more)

### Community 25 - "Community 25"
Cohesion: 0.03
Nodes (91): ClientDiagnosticsSettingsView, clientDiagnosticsStatusQuery(), ClientDiagnosticsStatusView, ADR-0080, CertificationView, DeviceView, DiverDetailsView, ExternalDiverView (+83 more)

### Community 26 - "Community 26"
Cohesion: 0.04
Nodes (22): DiveEditViewModelTest, FakeSearch, ScanViewModelTest, ApiDiveCentresPostRequest, ApiDiveCentresPostRequestExternalIdsItem, ApiDiversFromCodePostRequest, ApiVerificationCodesReadPostRequest, FoundDiver (+14 more)

### Community 27 - "Community 27"
Cohesion: 0.04
Nodes (97): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+89 more)

### Community 28 - "Community 28"
Cohesion: 0.04
Nodes (90): authHeaders(), forwardCookies(), sessionIdOf(), hashPassword(), invitation, passwordReset, userRole, AttemptLimit (+82 more)

### Community 29 - "Community 29"
Cohesion: 0.06
Nodes (32): LiveHint, LiveHints, SyncRefresher, Body, FeedEntry, Hold, Offline, RefreshData (+24 more)

### Community 30 - "Community 30"
Cohesion: 0.04
Nodes (101): bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas, toolsGasQuery() (+93 more)

### Community 31 - "Community 31"
Cohesion: 0.03
Nodes (94): pushRunItem, PushRunLeftOut, managedDiverIds(), DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN (+86 more)

### Community 32 - "Community 32"
Cohesion: 0.03
Nodes (90): AssessmentService, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView(), ADR-0036 (+82 more)

### Community 33 - "Community 33"
Cohesion: 0.10
Nodes (4): DiveEditScreenshotTest, DiveViewCylindersItemSeries, pressureSeriesOptions(), DiveEditDraftTest

### Community 34 - "Community 34"
Cohesion: 0.03
Nodes (93): buddiesForQuery(), buddiesQuery(), BuddyView, ConflictChoices, ConflictEntryView, ConflictFieldView, ConflictsOfConnection, DiveImportMode (+85 more)

### Community 35 - "Community 35"
Cohesion: 0.04
Nodes (91): divesQuery(), LogbookPage, PAGE_SIZE, ProblemCode, BackupsPage, CentrePage, CentresPage, currentRoute() (+83 more)

### Community 36 - "Community 36"
Cohesion: 0.05
Nodes (80): ChangePassword(), DisplaySettings(), ReportProblemSettings(), SecuritySettings(), Sessions(), ADR-0080, useDescribeAgent(), AiAccessLogEntry (+72 more)

### Community 37 - "Community 37"
Cohesion: 0.04
Nodes (69): App(), useFetchOnOpening(), cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL (+61 more)

### Community 38 - "Community 38"
Cohesion: 0.04
Nodes (48): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+40 more)

### Community 39 - "Community 39"
Cohesion: 0.03
Nodes (16): KeystoreTokenStore, PreferencesCachedMeStore, PreferencesInstanceUrlStore, UserCacheStore, FakeCachedMe, FakeInstances, SettingsRouteTest, SettingsRepository (+8 more)

### Community 40 - "Community 40"
Cohesion: 0.05
Nodes (33): PendingWrite, PendingWriteKind, Action, Patch, ConflictSide, Server, ThisPhone, WriteDrain (+25 more)

### Community 41 - "Community 41"
Cohesion: 0.05
Nodes (78): diverExternalId, diveSite, dataIdsOf(), findOrMake(), siteForSend(), ADR-0057, createSitePickService(), ADR-0057 (+70 more)

### Community 42 - "Community 42"
Cohesion: 0.07
Nodes (51): BottomTime, bottomTime(), BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen (+43 more)

### Community 43 - "Community 43"
Cohesion: 0.04
Nodes (77): CertificationService, CYLINDER_MATERIALS, DIVE_IMPORT_MODES, DIVE_KINDS, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053 (+69 more)

### Community 44 - "Community 44"
Cohesion: 0.07
Nodes (81): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, ADR-0089 (+73 more)

### Community 45 - "Community 45"
Cohesion: 0.05
Nodes (73): NoFly, Id(), Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), getDiverLoadings (+65 more)

### Community 46 - "Community 46"
Cohesion: 0.06
Nodes (27): ChangesList(), ConflictChoice(), ConflictEntry(), ConflictsScreen(), diveTitle(), FieldDecision(), NoticeSection(), noticeText() (+19 more)

### Community 47 - "Community 47"
Cohesion: 0.04
Nodes (20): ApiClientDiagnosticsStatusGet200Response, ApiDevicesPost200Response, ApiMeGet200ResponseOwnDiver, DiveViewParticipantsItemCertificationHint, ProviderSummary, ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport (+12 more)

### Community 48 - "Community 48"
Cohesion: 0.06
Nodes (78): allButDive(), continuationCandidateQuery(), deletedDivesOn(), deletedDivesQuery(), diveQuery(), DiveView, mergeCandidatesQuery(), nearbyDivesQuery() (+70 more)

### Community 49 - "Community 49"
Cohesion: 0.07
Nodes (70): formatSyncTime(), GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult(), PlanInputs() (+62 more)

### Community 50 - "Community 50"
Cohesion: 0.04
Nodes (62): FcmClientConfig, FcmConfig, FcmServiceAccount, loadConfig(), readBillingConfig(), readEmailConfig(), readFcmConfig(), readJsonObject() (+54 more)

### Community 51 - "Community 51"
Cohesion: 0.04
Nodes (69): PARTICIPANT_ROLES, siteImportStatus, DIVE_ONLY_PATTERN, ErrorAnswer, PROBLEM_CODES, ProblemCode, ProblemSchema, providerProblem() (+61 more)

### Community 52 - "Community 52"
Cohesion: 0.06
Nodes (4): ScanUiState, ScanViewModel, ScanScreenshotTest, ScanScreenTest

### Community 53 - "Community 53"
Cohesion: 0.07
Nodes (65): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+57 more)

### Community 54 - "Community 54"
Cohesion: 0.03
Nodes (48): ParticipantRole, ActionContext, Capabilities, Delivered, Direction, DiveExport, DiveExportAction, DiveListing (+40 more)

### Community 55 - "Community 55"
Cohesion: 0.05
Nodes (14): SettingsDiagnosticsState, SettingsQueueState, SettingsUiState, SettingsViewModel, FakeDiagnostics, SettingsViewModelTest, ApiClientDiagnosticsPostRequest, ApiClientDiagnosticsPostRequestClient (+6 more)

### Community 56 - "Community 56"
Cohesion: 0.04
Nodes (51): diveLease, providerConflict, providerLease, pushRun, kept(), LEASE_MS, MAX_WAIT_MS, POLL_MS (+43 more)

### Community 57 - "Community 57"
Cohesion: 0.05
Nodes (62): continuationAppend, sampleSeries, tieToJoined(), cylindersOf(), mergeProviderCylinder(), providerCylinderOf(), Participant, participantsOf() (+54 more)

### Community 58 - "Community 58"
Cohesion: 0.06
Nodes (67): ANDROID_UI, AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch() (+59 more)

### Community 59 - "Community 59"
Cohesion: 0.08
Nodes (17): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), Answers (+9 more)

### Community 60 - "Community 60"
Cohesion: 0.09
Nodes (11): FakeCache, FakeCachedMe, FakeInstances, FakeQueue, FakeTokens, Harness, json(), SessionRepositoryTest (+3 more)

### Community 61 - "Community 61"
Cohesion: 0.06
Nodes (53): errorMessage(), looksLikeZip(), parseWithFitFileParser(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson() (+45 more)

### Community 62 - "Community 62"
Cohesion: 0.05
Nodes (8): FirebaseRuntime, hasPlayServices(), resultOrNull(), PushController, PushPreferences, FirebaseRuntimeTest, ApiPushConfigGet200Response, ApiPushConfigGet200ResponseAndroid

### Community 63 - "Community 63"
Cohesion: 0.06
Nodes (7): ApiClient, ApiErrorBody, DiveLocalEdit, WriteCapability, ApiClientTest, DiveRepositoryTest, WriteCapabilityTest

### Community 64 - "Community 64"
Cohesion: 0.05
Nodes (42): captureDiagnostic(), captureError(), cleanContext(), clearDiagnostics(), CLIENT_NAME, CLIENT_VERSION, DiagnosticEntry, diagnosticFromError() (+34 more)

### Community 65 - "Community 65"
Cohesion: 0.05
Nodes (57): devicesQuery(), diveNeighboursQuery(), revisionsQuery(), RevisionView, UtcOffsetSource, useCylinderText(), DeviceRow(), Devices() (+49 more)

### Community 66 - "Community 66"
Cohesion: 0.06
Nodes (30): EmptyState(), rememberReducedMotion(), rememberSkeletonAlpha(), SkeletonBar(), titleLineHeight(), TitleSkeleton(), AvatarCircle(), Error() (+22 more)

### Community 67 - "Community 67"
Cohesion: 0.09
Nodes (11): ChangeFeedEvent, ChangeFeedPage, Complete, SessionEnded, Stopped, SyncEngine, SyncPhase, Details (+3 more)

### Community 68 - "Community 68"
Cohesion: 0.05
Nodes (53): Position, purgeRunning(), running(), SiteImportArea, siteImportsQuery(), SiteImportView, sitePurgesQuery(), SitePurgeView (+45 more)

### Community 69 - "Community 69"
Cohesion: 0.07
Nodes (60): Answer, answer(), baseFrom(), BodyWeight, Confidence, high, low, medium (+52 more)

### Community 70 - "Community 70"
Cohesion: 0.04
Nodes (53): replyCertificationError, STATUS, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, CertificationRouteDeps (+45 more)

### Community 71 - "Community 71"
Cohesion: 0.07
Nodes (12): LeadAnswer, LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseBasis, ApiDiversIdLeadEstimateGet200ResponseLeftOut (+4 more)

### Community 72 - "Community 72"
Cohesion: 0.12
Nodes (24): Body, BrokenBody, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage(), diverJson() (+16 more)

### Community 73 - "Community 73"
Cohesion: 0.04
Nodes (44): BACKUP_RUN_TASK, backupTask(), ADR-0069, PurgeService, PushDispatcher, WAITING_PUSHES_TASK, AUTOMATION_TICK_TASK, RevisionEntityType (+36 more)

### Community 74 - "Community 74"
Cohesion: 0.06
Nodes (9): MainViewModel, Unreachable, IntHolder, MainViewModelPendingWarningTest, WarningCache, WarningCachedMe, WarningInstances, WarningQueue (+1 more)

### Community 75 - "Community 75"
Cohesion: 0.05
Nodes (51): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, cancelWaitingImports() (+43 more)

### Community 76 - "Community 76"
Cohesion: 0.04
Nodes (47): SUIT_TYPES, SUIT_UNDERGARMENTS, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053, replyDiverError (+39 more)

### Community 77 - "Community 77"
Cohesion: 0.04
Nodes (54): EquipmentService, RecordInput, ScheduleInput, DiveGearItemViewSchema, DiveGearViewSchema, DueState, EquipmentDetailViewSchema, EquipmentDueViewSchema (+46 more)

### Community 78 - "Community 78"
Cohesion: 0.05
Nodes (47): ImportErrorCode, ImportFound, ImportOutcome, FileFormat, formatOf(), CHOICE_WAIT_DAYS, createImportService(), cancelImport() (+39 more)

### Community 79 - "Community 79"
Cohesion: 0.09
Nodes (5): CountingDispatcher, LogbookViewModelTest, LogbookRead, StoreLogbook, StoreLogbookContext

### Community 80 - "Community 80"
Cohesion: 0.07
Nodes (16): ApiMeGet200Response, ApiMeGet200ResponsePreferences, SignInEmailRequest, UserView, InstanceUrl, InstanceProbeResult, Loading, PendingChanges (+8 more)

### Community 81 - "Community 81"
Cohesion: 0.06
Nodes (5): QrImageDecoder, LauncherIconTest, SplashTheme, SplashThemeTest, QrImageDecoderTest

### Community 82 - "Community 82"
Cohesion: 0.05
Nodes (44): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+36 more)

### Community 83 - "Community 83"
Cohesion: 0.08
Nodes (42): SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), buildUpdateRecord(), emptyRecord(), keysOf(), writableRecord() (+34 more)

### Community 84 - "Community 84"
Cohesion: 0.09
Nodes (44): tidyName(), DiverDetails, Caller, foldName(), nameDiffers(), Buddy, createBuddyService(), createFrom() (+36 more)

### Community 85 - "Community 85"
Cohesion: 0.05
Nodes (42): CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043, CentreChanged (+34 more)

### Community 86 - "Community 86"
Cohesion: 0.07
Nodes (36): ADR-0051, ADR-0047, clearPushRuns(), clearQueue(), connectSsi(), deletableDive(), expireSsiTokens(), fetchState() (+28 more)

### Community 88 - "Community 88"
Cohesion: 0.09
Nodes (23): ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseCns, ApiDiversIdLoadingsGet200ResponseN2, ApiDiversIdLoadingsGet200ResponseNoFly, ApiDiversIdLoadingsGet200ResponseNoFlySource, ApiDiversIdLoadingsGet200ResponseOtu, cnsDecayed() (+15 more)

### Community 89 - "Community 89"
Cohesion: 0.04
Nodes (46): depends_on, environment, image, ports, restart, volumes, condition, environment (+38 more)

### Community 90 - "Community 90"
Cohesion: 0.08
Nodes (39): DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining(), ssiDiveType(), ssiInteger() (+31 more)

### Community 91 - "Community 91"
Cohesion: 0.08
Nodes (26): headers, ADR-0031, headers, ADR-0043, ADR-0080, ADR-0016, ADR-0071, ADR-0042 (+18 more)

### Community 92 - "Community 92"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 93 - "Community 93"
Cohesion: 0.05
Nodes (40): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+32 more)

### Community 94 - "Community 94"
Cohesion: 0.09
Nodes (12): DiveListViewDivesItem, DiveListViewDivesItemGasesItem, DiveListViewDivesItemParticipantsItem, DiveListViewDivesItemSite, DiveListViewMonthsItem, DiveProfileView, localMonthOf(), LogbookMonth (+4 more)

### Community 96 - "Community 96"
Cohesion: 0.08
Nodes (17): LogbookViewModel, DiveListViewCounts, neighboursPath(), countOf(), FilterChip, filterChips(), LogbookFilter, CHANGED_SINCE_SENT (+9 more)

### Community 97 - "Community 97"
Cohesion: 0.08
Nodes (8): Connected, Hint, HintFrame, LiveEvent, SseLiveHints, LiveHintsTest, SseLiveHintsEngineTest, TrickleServer

### Community 98 - "Community 98"
Cohesion: 0.08
Nodes (31): BillingLog, createBillingService(), ADR-0013, ADR-0089, createPaddleClient(), post(), dateOf(), isObject() (+23 more)

### Community 99 - "Community 99"
Cohesion: 0.09
Nodes (37): diverBodyWeight, lead, LeadPlacement, ProviderImportPlan, SuitType, SuitUndergarment, WeightingFelt, ADR-0025 (+29 more)

### Community 100 - "Community 100"
Cohesion: 0.05
Nodes (41): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+33 more)

### Community 101 - "Community 101"
Cohesion: 0.14
Nodes (6): LogbookPhase, Error, List, Loading, Offline, SettingsScreenshotTest

### Community 102 - "Community 102"
Cohesion: 0.09
Nodes (34): date(), FitMessage, FitMessages, GARMIN_PRODUCTS, num(), position(), positive(), RECORD_CHANNELS (+26 more)

### Community 103 - "Community 103"
Cohesion: 0.10
Nodes (37): importQuery(), importsQuery(), ImportView, ADR-0030, uploadFile(), noAnswerError(), Logbook(), ImportContext (+29 more)

### Community 104 - "Community 104"
Cohesion: 0.10
Nodes (39): pushRunActive(), pushRunBody(), PushRunChoices, PushRunPreview, pushRunPreviewQuery(), pushRunQuery(), pushRunsQuery(), startPushRun() (+31 more)

### Community 105 - "Community 105"
Cohesion: 0.08
Nodes (37): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+29 more)

### Community 106 - "Community 106"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 107 - "Community 107"
Cohesion: 0.07
Nodes (33): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+25 more)

### Community 108 - "Community 108"
Cohesion: 0.08
Nodes (32): WeightingFelt, right, too_heavy, too_light, dayNumber(), enteredDecoFromNdl(), minNdlSeconds(), NoFly (+24 more)

### Community 109 - "Community 109"
Cohesion: 0.17
Nodes (37): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestEntries(), entryNamed(), flag() (+29 more)

### Community 110 - "Community 110"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 112 - "Community 112"
Cohesion: 0.07
Nodes (28): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError (+20 more)

### Community 113 - "Community 113"
Cohesion: 0.09
Nodes (30): diveWith(), ADR-0028, ADR-0043, resetSource(), stubDive(), ADR-0015, ADR-0023, ADR-0025 (+22 more)

### Community 114 - "Community 114"
Cohesion: 0.14
Nodes (7): DiveRow(), Figures(), RowFacts(), FactRow(), NoteText(), StackedNoteText(), ComponentsScreenshotTest

### Community 115 - "Community 115"
Cohesion: 0.11
Nodes (8): AppNavHost(), AppScreens, clearSavedTabStates(), isShowing(), leaveDiveForLogbook(), MainShell(), navigateToTab(), tabOf()

### Community 116 - "Community 116"
Cohesion: 0.09
Nodes (33): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+25 more)

### Community 117 - "Community 117"
Cohesion: 0.09
Nodes (11): CameraFailure, cameraFailure(), Blocked, Busy, Missing, CameraScanner(), CameraScannerFrame(), TorchState (+3 more)

### Community 118 - "Community 118"
Cohesion: 0.11
Nodes (13): LogbookPaging, AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint, SketchProfile, sketchScale() (+5 more)

### Community 119 - "Community 119"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 120 - "Community 120"
Cohesion: 0.13
Nodes (33): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findNewId(), Format (+25 more)

### Community 121 - "Community 121"
Cohesion: 0.11
Nodes (4): FakeCache, FakeInstances, FakeTokens, MainViewModelTest

### Community 122 - "Community 122"
Cohesion: 0.13
Nodes (7): GateCachedMe, GateCall, GateClient, GateInstances, GateServer, GateTokens, WriteExitGateTest

### Community 123 - "Community 123"
Cohesion: 0.25
Nodes (8): leadAnswer(), EstimateCylinder, EstimateInput, PlannedConditions, Suit, assemblePersonalizedSources(), leadEstimate(), PersonalizedToolsTest

### Community 124 - "Community 124"
Cohesion: 0.09
Nodes (8): ApiDiveSitesGet200Response, SiteView, SiteViewExternalIdsItem, SiteViewExternalIdsItemAttribution, SiteViewExternalIdsItemOffered, SiteViewExternalIdsItemOfferedPosition, SiteViewExternalIdsItemPrivateTo, SiteViewPosition

### Community 125 - "Community 125"
Cohesion: 0.12
Nodes (26): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+18 more)

### Community 126 - "Community 126"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 127 - "Community 127"
Cohesion: 0.20
Nodes (4): DiveMergeTest, diveObject(), participant(), participants()

### Community 128 - "Community 128"
Cohesion: 0.13
Nodes (8): InstanceProbeTest, json(), probe(), ProbeCache, ProbeCachedMe, ProbeInstances, probeRepository(), ProbeTokens

### Community 129 - "Community 129"
Cohesion: 0.07
Nodes (24): diveSiteExternalId, parseHint(), ids(), call(), createSite(), Method, openStream(), Stream (+16 more)

### Community 130 - "Community 130"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 131 - "Community 131"
Cohesion: 0.12
Nodes (16): DiveHubApp(), DiveHubScreens, ErrorText(), Gate(), InstanceForm(), InstanceStatus(), LoadingScreen(), PasswordField() (+8 more)

### Community 132 - "Community 132"
Cohesion: 0.07
Nodes (12): buddySite(), Dive, OTHER, PendingOffer, ADR-0012, ADR-0070, Dive, OTHER (+4 more)

### Community 133 - "Community 133"
Cohesion: 0.12
Nodes (26): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+18 more)

### Community 134 - "Community 134"
Cohesion: 0.18
Nodes (10): Body, buddyAnswer(), centreAnswer(), centreView(), diverView(), Offline, ScanCall, ScanHarness (+2 more)

### Community 135 - "Community 135"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 136 - "Community 136"
Cohesion: 0.09
Nodes (23): computerEventOf(), ADR-0036, computerEventFromFit(), COMPUTER_EVENT, computerEventFromSuunto(), DECO_MODEL, decoModelFromSuunto, DIVE_MODE (+15 more)

### Community 137 - "Community 137"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 138 - "Community 138"
Cohesion: 0.10
Nodes (7): ToolPage, Gas, Index, Lead, Loadings, Plan, FakeScreens

### Community 139 - "Community 139"
Cohesion: 0.11
Nodes (7): ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh, ApiToolsBottomTimeGet200ResponseUsedSacLpm, ApiToolsBottomTimeGet200ResponseUsedWater

### Community 140 - "Community 140"
Cohesion: 0.10
Nodes (22): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+14 more)

### Community 141 - "Community 141"
Cohesion: 0.17
Nodes (26): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+18 more)

### Community 143 - "Community 143"
Cohesion: 0.14
Nodes (20): createMail(), createMailLinks(), ADR-0012, MailMessage, MailProvider, ADR-0053, day(), invitationMail() (+12 more)

### Community 144 - "Community 144"
Cohesion: 0.11
Nodes (21): RecordingSummary, UtcOffsetSource, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput (+13 more)

### Community 145 - "Community 145"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 148 - "Community 148"
Cohesion: 0.08
Nodes (25): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+17 more)

### Community 149 - "Community 149"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 150 - "Community 150"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 151 - "Community 151"
Cohesion: 0.13
Nodes (6): ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting

### Community 152 - "Community 152"
Cohesion: 0.13
Nodes (6): DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, WaitingPushView

### Community 153 - "Community 153"
Cohesion: 0.24
Nodes (6): Body, Call, Harness, Offline, Reply, WriteReplayerTest

### Community 154 - "Community 154"
Cohesion: 0.12
Nodes (21): equipmentUse, DiveGear, GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow, ItemView (+13 more)

### Community 155 - "Community 155"
Cohesion: 0.12
Nodes (22): AIR, cnsDecayed(), DEFAULT_CONDITIONS, N2_REPLAY_WINDOW_HOURS, N2_STALE_AFTER_HOURS, N2Loadings, N2LoadingsBase, N2UnavailableReason (+14 more)

### Community 156 - "Community 156"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 157 - "Community 157"
Cohesion: 0.23
Nodes (5): Body, ConflictCall, ConflictHarness, ConflictReply, WriteConflictResolutionTest

### Community 158 - "Community 158"
Cohesion: 0.14
Nodes (18): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+10 more)

### Community 159 - "Community 159"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 160 - "Community 160"
Cohesion: 0.18
Nodes (3): LeadEstimateUiState, LeadEstimateViewModel, PersonalizedSources

### Community 161 - "Community 161"
Cohesion: 0.18
Nodes (3): WriteQueueLine(), WritesActions, WriteQueueLineTest

### Community 163 - "Community 163"
Cohesion: 0.14
Nodes (4): FakeCachedMe, FakeInstances, FakeTokens, SyncAuthTest

### Community 164 - "Community 164"
Cohesion: 0.12
Nodes (19): SUGGESTION_STATUSES, DiveProfileView, DiveProfileViewSchema, ADR-0041, AcceptBody, STATUS, SuggestionRouteDeps, ADR-0070 (+11 more)

### Community 165 - "Community 165"
Cohesion: 0.15
Nodes (21): EquipmentError, checkCost(), checkDates(), checkWindow(), createEquipmentService(), assemble(), checkResets(), checkSchedule() (+13 more)

### Community 166 - "Community 166"
Cohesion: 0.12
Nodes (3): SyncWorker, WorkManagerPeriodicSync, PeriodicSyncScheduler

### Community 167 - "Community 167"
Cohesion: 0.17
Nodes (6): AppTab, Logbook, Settings, Tools, DiveHubBottomBar(), BottomBarScreenshotTest

### Community 169 - "Community 169"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 171 - "Community 171"
Cohesion: 0.15
Nodes (5): DiagnosticEntry, DiagnosticLevel, ERROR, WARN, DiagnosticsBuffer

### Community 173 - "Community 173"
Cohesion: 0.15
Nodes (5): ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits

### Community 175 - "Community 175"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource/poppins@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 176 - "Community 176"
Cohesion: 0.14
Nodes (18): equipmentItem, serviceRecord, serviceRecordSchedule, serviceSchedule, equipmentList(), EquipmentRecordRead, today(), ADR-0034 (+10 more)

### Community 177 - "Community 177"
Cohesion: 0.16
Nodes (18): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+10 more)

### Community 178 - "Community 178"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 179 - "Community 179"
Cohesion: 0.25
Nodes (19): DiveOrigin, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef(), MATERIAL_OF_TANK_TYPE, nonZero() (+11 more)

### Community 180 - "Community 180"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 181 - "Community 181"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 182 - "Community 182"
Cohesion: 0.20
Nodes (7): PendingChangeWarning, PendingWarningDialog(), SignIn, SignInWarningDialog(), SignOut, SignOutWarningDialog(), PendingWarningDialogTest

### Community 184 - "Community 184"
Cohesion: 0.19
Nodes (11): LeadInputTest, SuitType, drysuit, none, semidry, skin, wetsuit, SuitUndergarment (+3 more)

### Community 186 - "Community 186"
Cohesion: 0.17
Nodes (15): ADR-0076, ClientVersionRegistry, MIN_CLIENT_VERSIONS, MinClientVersion, ADR-0076, clientRequestSerializer(), ADR-0076, compareIdentifier() (+7 more)

### Community 187 - "Community 187"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 188 - "Community 188"
Cohesion: 0.13
Nodes (15): AL80, asEstimateInput(), EstimateDiveCylinder, LEAD_CASES, leadInput(), LeadVectorInput, planOf(), STEEL_12 (+7 more)

### Community 189 - "Community 189"
Cohesion: 0.16
Nodes (14): execPnpm(), FORBIDDEN, isPermissive(), licenseProblems(), main(), ADR-0006, ADR-0086, PERMISSIVE (+6 more)

### Community 191 - "Community 191"
Cohesion: 0.18
Nodes (8): Action(), ConnectionStatus(), Section(), SettingsRoute(), SettingsScreen(), CameraChrome, isDarkScheme(), successColor()

### Community 194 - "Community 194"
Cohesion: 0.13
Nodes (13): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, LeadEstimateAnswer, Confidence, EstimateAdjustment, EstimateBasis (+5 more)

### Community 195 - "Community 195"
Cohesion: 0.17
Nodes (12): sampleCount(), storedList(), tryParseJson(), APP_PARAMS, createSsiTransport(), exchange(), isObject(), SSI_APP_USER_AGENT (+4 more)

### Community 196 - "Community 196"
Cohesion: 0.23
Nodes (15): prepareEntry(), DENIED_KEY_PARTS, emptyRedactionCounters(), isDeniedKey(), maskString(), redactInner(), redactLogObject(), RedactOptions (+7 more)

### Community 197 - "Community 197"
Cohesion: 0.12
Nodes (12): Dive, EGYPT, ELSEWHERE, Method, POSITION, runPurge(), Site, SiteImport (+4 more)

### Community 198 - "Community 198"
Cohesion: 0.15
Nodes (10): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, ADR-0068 (+2 more)

### Community 199 - "Community 199"
Cohesion: 0.24
Nodes (14): git(), failedTestsFromReport(), localTime(), REPORT, appendRun(), browserStats(), failedSummary(), flakySummary() (+6 more)

### Community 200 - "Community 200"
Cohesion: 0.17
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 203 - "Community 203"
Cohesion: 0.19
Nodes (4): ApiToolsBottomTimeGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseLimits, ApiToolsBottomTimeGet200ResponseResidualN2

### Community 204 - "Community 204"
Cohesion: 0.19
Nodes (4): CentreView, CentreViewExternalIdsItem, CentreViewSitesItem, CentreViewVerificationCode

### Community 205 - "Community 205"
Cohesion: 0.19
Nodes (4): DiveDetailsListView, DiveDetailsListViewCounts, DiveDetailsListViewMonthsItem, DiveDetailsListViewTotals

### Community 206 - "Community 206"
Cohesion: 0.26
Nodes (14): powInt(), roundTo(), Sac, SacCylinder, SacDive, SacMissing, cylinder_incomplete, no_average_depth (+6 more)

### Community 207 - "Community 207"
Cohesion: 0.15
Nodes (14): certification, PROBLEMS, createUserAdminService(), keepAnAdmin(), lock(), deleteUserData(), ADR-0013, ADR-0024 (+6 more)

### Community 208 - "Community 208"
Cohesion: 0.12
Nodes (13): clientDiagnostic, clientDiagnosticsSettings, ClientDiagnosticsEntryInput, ClientDiagnosticsEntryView, ClientDiagnosticsError, ClientDiagnosticsUpload, KEEP_NEWEST, MAX_UPLOAD_ENTRIES (+5 more)

### Community 209 - "Community 209"
Cohesion: 0.16
Nodes (13): CIRCUIT, circuitFromFit, DECO_MODEL, decoModelFromFit, DIVE_ALERT, DIVE_MODE, diveModeFromFit, ADR-0015 (+5 more)

### Community 210 - "Community 210"
Cohesion: 0.13
Nodes (7): SsiClient, deleteTestDive(), NumberChecks, findDive(), runSiteVisibleFinish(), TwowayChecks, ValuesChecks

### Community 211 - "Community 211"
Cohesion: 0.21
Nodes (11): buildSupportBundle(), buildTarGz(), json(), ndjson(), octal(), SUPPORT_BUNDLE_FORMAT, SUPPORT_BUNDLE_FORMAT_VERSION, SupportBufferManifest (+3 more)

### Community 212 - "Community 212"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 213 - "Community 213"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 214 - "Community 214"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 216 - "Community 216"
Cohesion: 0.21
Nodes (12): evaluateLeadVectorCase(), LEAD_VECTOR_KERNEL, leadVectorFile(), VECTOR_KERNELS, VectorFile, vectorFile(), assertMatch(), checkVectors() (+4 more)

### Community 217 - "Community 217"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 218 - "Community 218"
Cohesion: 0.13
Nodes (14): concurrency, cancel-in-progress, group, env, RELEASE_TAG, jobs, publish, name (+6 more)

### Community 221 - "Community 221"
Cohesion: 0.33
Nodes (4): ConflictField, DiveMerge, MergeOutcome, ParticipantsMergeOutcome

### Community 222 - "Community 222"
Cohesion: 0.24
Nodes (13): asNumber(), asText(), compareReadBack(), datasetLength(), describe(), isObject(), keyDifferences(), pause() (+5 more)

### Community 223 - "Community 223"
Cohesion: 0.20
Nodes (5): LogRingBuffer, RedactionCounters, SupportBundleInput, SupportBundleManifest, SupportServiceDeps

### Community 224 - "Community 224"
Cohesion: 0.20
Nodes (11): created, headers, importFixture(), ADR-0066, uploadFixture(), Status, ADR-0056, conflictAtSsi() (+3 more)

### Community 225 - "Community 225"
Cohesion: 0.15
Nodes (13): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, ADR-0076, ADR-0083, ADR-0085 (+5 more)

### Community 226 - "Community 226"
Cohesion: 0.14
Nodes (13): if, runs-on, steps, timeout-minutes, jobs, dco-check, name, on (+5 more)

### Community 227 - "Community 227"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 228 - "Community 228"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 229 - "Community 229"
Cohesion: 0.17
Nodes (10): EstimateInput, AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031 (+2 more)

### Community 230 - "Community 230"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 231 - "Community 231"
Cohesion: 0.48
Nodes (3): UiState, sessionDestination(), SessionDestinationTest

### Community 232 - "Community 232"
Cohesion: 0.32
Nodes (10): ConflictsRoute, DiveEditRoute, InstanceRoute, LogbookRoute, MainRoute, ScanRoute, SettingsDebugRoute, SettingsRoute (+2 more)

### Community 234 - "Community 234"
Cohesion: 0.26
Nodes (3): DiverDetailsView, DiverDetailsViewAccountsItem, DiverDetailsViewCodesItem

### Community 235 - "Community 235"
Cohesion: 0.26
Nodes (3): DiverView, DiverViewCertifications, DiverViewCertificationsNewest

### Community 236 - "Community 236"
Cohesion: 0.26
Nodes (3): DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 238 - "Community 238"
Cohesion: 0.18
Nodes (10): LogBufferStats, SupportBundle, ClientDiagnosticsProvider, FailedBackup, FailedJob, HealthSection, iso(), JobsSection (+2 more)

### Community 239 - "Community 239"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 240 - "Community 240"
Cohesion: 0.29
Nodes (3): pushFoundNewDive(), PushHint, PushHintTest

### Community 241 - "Community 241"
Cohesion: 0.20
Nodes (9): inZone(), offsetIn(), AQABA, ATTERSEE, EILAT, HAUSREEF, local(), MID_ATLANTIC (+1 more)

### Community 242 - "Community 242"
Cohesion: 0.20
Nodes (9): DueSchedule, ScheduleDue, UsageDive, DueEntry, ScheduleView, EquipmentScheduleRead, run(), schedule() (+1 more)

### Community 243 - "Community 243"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 244 - "Community 244"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 245 - "Community 245"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 246 - "Community 246"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 248 - "Community 248"
Cohesion: 0.29
Nodes (7): otu(), oxygenLimitMinutes(), compressibility(), Cubic, minusOne(), pressureForLitres(), surfaceLitres()

### Community 250 - "Community 250"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 251 - "Community 251"
Cohesion: 0.20
Nodes (10): verify, prerelease, sha, tag, versionCode, versionName, name, outputs (+2 more)

### Community 252 - "Community 252"
Cohesion: 0.20
Nodes (10): with, cache, distribution, fetch-depth, if-no-files-found, java-version, name, path (+2 more)

### Community 253 - "Community 253"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 259 - "Community 259"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 260 - "Community 260"
Cohesion: 0.31
Nodes (5): BufferedLog, createLogRingBuffer(), LOG_BUFFER_CAPACITY_BYTES, LOG_BUFFER_CAPACITY_ENTRIES, LogRingBufferOptions

### Community 261 - "Community 261"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 262 - "Community 262"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 263 - "Community 263"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 265 - "Community 265"
Cohesion: 0.36
Nodes (6): headers, open(), ownId(), ownRow(), sectionButton(), ADR-0050

### Community 266 - "Community 266"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 267 - "Community 267"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 269 - "Community 269"
Cohesion: 0.33
Nodes (4): Sketch, SKETCH_POINTS, ADR-0041, ADR-0041

### Community 270 - "Community 270"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 271 - "Community 271"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 272 - "Community 272"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 273 - "Community 273"
Cohesion: 0.33
Nodes (6): SuitType, DRYSUIT, NONE, SEMIDRY, SKIN, WETSUIT

### Community 274 - "Community 274"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 275 - "Community 275"
Cohesion: 0.40
Nodes (5): DivePhase, Dive, Error, Loading, Offline

### Community 276 - "Community 276"
Cohesion: 0.40
Nodes (4): offer, osm, ssi, wikidata

### Community 277 - "Community 277"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build

### Community 278 - "Community 278"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 279 - "Community 279"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 280 - "Community 280"
Cohesion: 0.40
Nodes (4): TAGS, ADR-0023, { stdout, stderr }, total

### Community 287 - "Community 287"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 288 - "Community 288"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 289 - "Community 289"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1438 isolated node(s):** `Reachable`, `Unknown`, `SignIn`, `SignOut`, `Reserve` (+1433 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 4218 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **185 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 0` to `Community 129`, `Community 2`, `Community 3`, `Community 135`, `Community 7`, `Community 9`, `Community 10`, `Community 13`, `Community 14`, `Community 16`, `Community 17`, `Community 18`, `Community 148`, `Community 22`, `Community 23`, `Community 154`, `Community 27`, `Community 28`, `Community 31`, `Community 41`, `Community 44`, `Community 45`, `Community 176`, `Community 50`, `Community 56`, `Community 57`, `Community 73`, `Community 78`, `Community 207`, `Community 208`, `Community 84`, `Community 99`, `Community 107`, `Community 238`, `Community 112`, `Community 116`?**
  _High betweenness centrality (0.024) - this node is a cross-community bridge._
- **What connects `Reachable`, `Unknown`, `SignIn` to the rest of the system?**
  _1438 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.02131102169423687 - nodes in this community are weakly interconnected._
- **Why does `typescript` connect `Community 187` to `Community 169`, `Community 130`, `Community 135`?**
  _High betweenness centrality (0.015) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.03278564397667903 - nodes in this community are weakly interconnected._
- **Why does `DiveStore` connect `Community 12` to `Community 6`, `Community 134`, `Community 20`, `Community 24`, `Community 153`, `Community 26`, `Community 29`, `Community 157`, `Community 160`, `Community 163`, `Community 39`, `Community 40`, `Community 174`, `Community 52`, `Community 55`, `Community 59`, `Community 62`, `Community 63`, `Community 67`, `Community 72`, `Community 79`, `Community 108`, `Community 122`, `Community 123`?**
  _High betweenness centrality (0.011) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.020663541291020555 - nodes in this community are weakly interconnected._