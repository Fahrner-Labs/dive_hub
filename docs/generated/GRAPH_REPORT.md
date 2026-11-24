# Graph Report - dive_hub  (2026-11-24)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 12685 nodes · 39402 edges · 429 communities (252 shown, 177 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 936 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `f8f12ec7`
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
- Community 277
- Community 278
- Community 279
- Community 280
- Community 281
- Community 282
- Community 283
- Community 284
- Community 287
- Community 288
- Community 295

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 211 edges
2. `Button()` - 204 edges
3. `useErrorText()` - 176 edges
4. `useDisplay()` - 165 edges
5. `drizzle-orm` - 150 edges
6. `unwrap()` - 143 edges
7. `DiveStore` - 139 edges
8. `Muted()` - 139 edges
9. `createTestDatabase()` - 112 edges
10. `TestDatabase` - 111 edges

## Surprising Connections (you probably didn't know these)
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `AppNavHost()` --calls--> `DiveRoute`  [INFERRED]
  apps/android/app/src/main/kotlin/dev/divehub/android/ui/navigation/AppNavHost.kt → apps/android/app/src/main/kotlin/dev/divehub/android/ui/navigation/AppRoutes.kt
- `RepositoryHarness` --calls--> `LogbookRepository`  [INFERRED]
  apps/android/shared/src/commonTest/kotlin/dev/divehub/shared/logbook/LogbookTest.kt → apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/logbook/LogbookRepository.kt

## Import Cycles
- None detected.

## Communities (429 total, 177 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (322): buddySuggestion, diveAssessment, diveFinding, diveSiteExternalId, jointDive, kept(), createSsiClient(), DELETE_QUIET_MS (+314 more)

### Community 1 - "Community 1"
Cohesion: 0.01
Nodes (232): user, actorType, aiAccessOutcome, backupRunKind, backupRunStatus, candidateResolution, centreSource, CERTIFICATION_IMAGE_STATES (+224 more)

### Community 2 - "Community 2"
Cohesion: 0.04
Nodes (218): DisplaySettings(), Sessions(), AdminSettings(), ClientDiagnosticsSetting(), DeleteUserDialog(), Invitations(), Invite(), LinkToPassOn() (+210 more)

### Community 3 - "Community 3"
Cohesion: 0.02
Nodes (187): BACKUP_RUN_TASK, backupTask(), ADR-0069, CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053 (+179 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (166): supportBundlePreviewQuery(), bodyWeightsQuery(), diverDetailsQuery(), diversQuery(), DiverView, externalDiversQuery(), RequirementView, centreQuery() (+158 more)

### Community 5 - "Community 5"
Cohesion: 0.02
Nodes (163): APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), AppLockProbe, probeAppLock() (+155 more)

### Community 6 - "Community 6"
Cohesion: 0.02
Nodes (135): createServer(), createAuth(), auth, hashPassword(), loadAuthSecret(), acquireAppLock(), BackupServiceDeps, createServices() (+127 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (85): Section(), rememberReducedMotion(), rememberSkeletonAlpha(), cylinderLine(), cylinderPressures(), Cylinders(), Direction, Back (+77 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (166): cylinderCatalogueQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas (+158 more)

### Community 9 - "Community 9"
Cohesion: 0.02
Nodes (156): Me, diveNeighboursQuery(), diveQuery(), divesQuery(), DiveSummary, DiveValues, LogbookPage, OverridableField (+148 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (146): assessmentQuery(), AssessmentView, samplesQuery(), ToolsPlan, ToolsPlanDiver, AsideItem(), AssessmentContext, AssessmentPanel() (+138 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (28): AppContainer, MainActivity, DiveEditViewModel, followStoreWrites(), ScanErrors, LeadEstimateUiState, LeadEstimateViewModel, LoadingsUiState (+20 more)

### Community 12 - "Community 12"
Cohesion: 0.05
Nodes (10): Radius, DiveEditScreenRoute(), markedName(), label(), SettingsDebugRoute(), SettingsDebugScreen(), Spacing, LoadingSource (+2 more)

### Community 13 - "Community 13"
Cohesion: 0.03
Nodes (132): label(), AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView (+124 more)

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (131): EMPTY_SITE_IMPORT_COUNTS, ImportArea, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053 (+123 more)

### Community 15 - "Community 15"
Cohesion: 0.02
Nodes (106): createDb(), ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS, eachFile(), readEntry(), ADR-0044 (+98 more)

### Community 16 - "Community 16"
Cohesion: 0.03
Nodes (133): continuationAppend, LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, recordingEvent, sampleSeries, renumber(), renumberStatement() (+125 more)

### Community 17 - "Community 17"
Cohesion: 0.02
Nodes (126): CentreService, CentreRouteDeps, LEAD_PLACEMENTS, UTC_OFFSET_SOURCES, WEIGHTING_FELT, DiverService, waterMismatch(), CertificationHint (+118 more)

### Community 18 - "Community 18"
Cohesion: 0.02
Nodes (114): pushRun, pushRunItem, Delivered, DIVE_FIELDS, ExportingProvider, OutgoingDive, ProviderError, RemoteDive (+106 more)

### Community 19 - "Community 19"
Cohesion: 0.06
Nodes (24): RevisionFollowTest, LeadEstimateViewModelTest, ApiClient, CylinderMaterial, aluminium, carbon, steel, SuitType (+16 more)

### Community 20 - "Community 20"
Cohesion: 0.04
Nodes (8): SectionTest, LoadingScreenshotTest, OnboardingScreenTest, PendingWarningDialogTest, SettingsDebugScreenTest, UpdateRequiredScreenTest, WriteQueueLineTest, DiveViewSuit

### Community 21 - "Community 21"
Cohesion: 0.03
Nodes (114): downsampleMinMax(), DUE_STATES, AiAccessScope, seesPositions(), VerifiedAccess, ProfileSummary, round1(), Series (+106 more)

### Community 22 - "Community 22"
Cohesion: 0.04
Nodes (17): DiveStore, toBoolean(), toLong(), toModel(), BodyWeight, Cylinder, Diver, DiveSite (+9 more)

### Community 23 - "Community 23"
Cohesion: 0.02
Nodes (94): BackupRunView, BackupSettingsView, SiteDataView, SupportBundlePreviewView, ADR-0030, ClientDiagnosticsSettingsView, clientDiagnosticsStatusQuery(), ClientDiagnosticsStatusView (+86 more)

### Community 24 - "Community 24"
Cohesion: 0.04
Nodes (97): siteRef(), waitingOf(), open(), close(), createConnectionService(), checkAutomation(), checkFollowNumbers(), expiryOf() (+89 more)

### Community 25 - "Community 25"
Cohesion: 0.04
Nodes (36): ApiDiverBodyWeightsGet200ResponseItem, PendingWrite, PendingWriteKind, Action, Patch, PendingWriteStatus, Awaiting, Conflicted (+28 more)

### Community 26 - "Community 26"
Cohesion: 0.04
Nodes (94): CertificationService, CYLINDER_MATERIALS, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053, CandidateRouteDeps, CandidateView (+86 more)

### Community 27 - "Community 27"
Cohesion: 0.04
Nodes (21): AndroidDiveStore, SettingsDiagnosticsState, SettingsUiState, FakeDiagnostics, FakeInstances, SettingsViewModelTest, ApiClientDiagnosticsPostRequest, ApiClientDiagnosticsPostRequestClient (+13 more)

### Community 28 - "Community 28"
Cohesion: 0.03
Nodes (90): importQuery(), buddiesForQuery(), buddiesQuery(), BuddyView, ConflictChoices, ConflictEntryView, ConflictsOfConnection, connectionsQuery() (+82 more)

### Community 29 - "Community 29"
Cohesion: 0.03
Nodes (86): CertificationView, CertificationViewSchema, Day, ADR-0050, DIVE_IMPORT_MODES, DIVE_KINDS, PARTICIPANT_ROLES, ADR-0053 (+78 more)

### Community 30 - "Community 30"
Cohesion: 0.04
Nodes (12): ScanUiState, ScanViewModel, DiveEditViewModelTest, ScanViewModelTest, ApiDiveCentresPostRequest, ApiDiveCentresPostRequestExternalIdsItem, ApiDiversFromCodePostRequest, ApiVerificationCodesReadPostRequest (+4 more)

### Community 31 - "Community 31"
Cohesion: 0.05
Nodes (62): SecondaryButton(), CylinderFields(), CylinderProblemText(), CylindersFields(), FieldPair(), GearEntryHeading(), GearNumberField(), LeadFields() (+54 more)

### Community 32 - "Community 32"
Cohesion: 0.04
Nodes (48): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+40 more)

### Community 33 - "Community 33"
Cohesion: 0.04
Nodes (38): DangerButton(), InkButton(), QuietButton(), EmptyState(), SkeletonBar(), DiveEditScreen(), SaveBar(), DiveEditUiState (+30 more)

### Community 34 - "Community 34"
Cohesion: 0.07
Nodes (54): Answers, number(), BottomTime, bottomTime(), BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas (+46 more)

### Community 35 - "Community 35"
Cohesion: 0.05
Nodes (81): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, ambientPressureBar() (+73 more)

### Community 36 - "Community 36"
Cohesion: 0.05
Nodes (85): allButDive(), continuationCandidateQuery(), deletedDivesOn(), deletedDivesQuery(), DiveView, mergeCandidatesQuery(), nearbyDivesQuery(), restorePreviewQuery() (+77 more)

### Community 37 - "Community 37"
Cohesion: 0.04
Nodes (73): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+65 more)

### Community 38 - "Community 38"
Cohesion: 0.06
Nodes (19): PendingChangeWarning, PendingWarningDialog(), SignIn, SignInWarningDialog(), SignOut, SignOutWarningDialog(), ConflictsRoute(), ConflictsScreen() (+11 more)

### Community 39 - "Community 39"
Cohesion: 0.07
Nodes (70): Formats, formatSyncTime(), GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult() (+62 more)

### Community 40 - "Community 40"
Cohesion: 0.08
Nodes (72): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, assessmentRoutes() (+64 more)

### Community 41 - "Community 41"
Cohesion: 0.05
Nodes (74): ComputerChoice, DiveImportMode, DiveKind, importOriginal, providerSiteData, UtcOffsetSource, PartInput, ProviderCylinder (+66 more)

### Community 42 - "Community 42"
Cohesion: 0.05
Nodes (63): NoFly, RecordingSummary, RecordingFacts, getDiverLoadings, BOTTOM_TIME_RULES, BottomTimeQuery, DEFAULT_GF_HIGH, diverLogbook() (+55 more)

### Community 43 - "Community 43"
Cohesion: 0.04
Nodes (58): AttemptLimit, clientKey(), limitAttempts(), ADR-0061, ProblemCode, ResponseState, whenResponseGone(), StoredBlob (+50 more)

### Community 44 - "Community 44"
Cohesion: 0.06
Nodes (61): accountListEntry, diveLease, diverExternalId, providerLease, tidyName(), DiverDetails, Caller, foldName() (+53 more)

### Community 45 - "Community 45"
Cohesion: 0.04
Nodes (55): Tx, AppendUndo, alignedForMatching(), overlaps(), ChangeListener, removed(), REVISION_ENTITY_TYPES, ADR-0015 (+47 more)

### Community 46 - "Community 46"
Cohesion: 0.04
Nodes (60): AssessmentService, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView(), ADR-0036 (+52 more)

### Community 47 - "Community 47"
Cohesion: 0.07
Nodes (70): providerCylinderOf(), participantsOf(), columnsOf(), leadOf(), leadTotalKg(), mergeProviderLead(), kindOfMode(), deviceDiver() (+62 more)

### Community 48 - "Community 48"
Cohesion: 0.06
Nodes (63): Queryable, logbookCheckAnswer, logbookCheckDiveAnswer, keepApart(), keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration() (+55 more)

### Community 49 - "Community 49"
Cohesion: 0.03
Nodes (43): ParticipantRole, Direction, DiveExport, DiveExportAction, DiveListing, FIND_BY, FindBy, Operation (+35 more)

### Community 50 - "Community 50"
Cohesion: 0.14
Nodes (3): DiveDetailScreenshotTest, NeighboursView, NeighboursViewNext

### Community 51 - "Community 51"
Cohesion: 0.05
Nodes (42): captureDiagnostic(), captureError(), cleanContext(), clearDiagnostics(), CLIENT_NAME, CLIENT_VERSION, DiagnosticEntry, diagnosticFromError() (+34 more)

### Community 52 - "Community 52"
Cohesion: 0.06
Nodes (62): ANDROID_UI, AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch() (+54 more)

### Community 53 - "Community 53"
Cohesion: 0.07
Nodes (14): Missing(), LeadAnswer, LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseBasis (+6 more)

### Community 54 - "Community 54"
Cohesion: 0.05
Nodes (15): ApiClientDiagnosticsStatusGet200Response, ApiMeGet200ResponseOwnDiver, ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting (+7 more)

### Community 55 - "Community 55"
Cohesion: 0.10
Nodes (10): FakeCache, FakeCachedMe, FakeInstances, FakeQueue, FakeTokens, Harness, json(), SessionRepositoryTest (+2 more)

### Community 56 - "Community 56"
Cohesion: 0.04
Nodes (56): ADR-0025, WaterMismatch, EquipmentService, RecordInput, ScheduleInput, DiveGearItemViewSchema, DiveGearViewSchema, DueState (+48 more)

### Community 57 - "Community 57"
Cohesion: 0.06
Nodes (47): ReportProblemSettings(), devicesQuery(), equipmentDueQuery(), DeviceRow(), Devices(), ADR-0016, Buddies(), EquipmentDueNotice() (+39 more)

### Community 58 - "Community 58"
Cohesion: 0.09
Nodes (13): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), GasPlanInputTest (+5 more)

### Community 59 - "Community 59"
Cohesion: 0.07
Nodes (17): ApiMeGet200Response, ApiMeGet200ResponsePreferences, SignInEmailRequest, UserView, InstanceUrl, HealthView, InstanceProbeResult, Loading (+9 more)

### Community 60 - "Community 60"
Cohesion: 0.09
Nodes (9): Complete, SessionEnded, Stopped, SyncEngine, SyncPhase, Details, Meta, Profiles (+1 more)

### Community 61 - "Community 61"
Cohesion: 0.06
Nodes (36): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+28 more)

### Community 62 - "Community 62"
Cohesion: 0.06
Nodes (9): MainViewModel, Unreachable, IntHolder, MainViewModelPendingWarningTest, WarningCache, WarningCachedMe, WarningInstances, WarningQueue (+1 more)

### Community 63 - "Community 63"
Cohesion: 0.06
Nodes (49): AIR, cnsDecayed(), DEFAULT_CONDITIONS, N2_REPLAY_WINDOW_HOURS, N2_STALE_AFTER_HOURS, N2Loadings, N2LoadingsBase, N2UnavailableReason (+41 more)

### Community 64 - "Community 64"
Cohesion: 0.09
Nodes (45): ChangePassword(), SecuritySettings(), ADR-0080, useDescribeAgent(), AiAccessLogEntry, AiAccessView, authClient, invitationLinkQuery() (+37 more)

### Community 65 - "Community 65"
Cohesion: 0.08
Nodes (52): Answer, answer(), baseFrom(), BodyWeight, Confidence, high, low, medium (+44 more)

### Community 66 - "Community 66"
Cohesion: 0.13
Nodes (22): Body, BrokenBody, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage(), diverJson() (+14 more)

### Community 67 - "Community 67"
Cohesion: 0.07
Nodes (5): ApiErrorBody, DiveViewSuitDraftSource, numberOrNull(), toJson(), DiveLocalEdit

### Community 69 - "Community 69"
Cohesion: 0.05
Nodes (43): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+35 more)

### Community 70 - "Community 70"
Cohesion: 0.12
Nodes (6): LiveHints, SyncRefresher, FeedEntry, RefreshHarness, refreshPathWithQuery(), SyncRefreshTest

### Community 71 - "Community 71"
Cohesion: 0.08
Nodes (41): SsiRecord, buildCreateRecord(), buildDeleteRecord(), buildUpdateRecord(), DiveForSsi, emptyRecord(), keysOf(), writableRecord() (+33 more)

### Community 72 - "Community 72"
Cohesion: 0.07
Nodes (38): createSsiSiteSource(), DEFAULT_SSI_SITES_URL, createSiteSources(), ADR-0021, ADR-0027, createOverpassSource(), DEFAULT_OVERPASS_URL, Element (+30 more)

### Community 73 - "Community 73"
Cohesion: 0.05
Nodes (41): SUIT_TYPES, SUIT_UNDERGARMENTS, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053, replyDiverError (+33 more)

### Community 74 - "Community 74"
Cohesion: 0.06
Nodes (42): asksFor(), Blocked, classify(), FailureOutcome, failureOutcome(), kindOf(), KINDS, LEFT_OUT_KEY (+34 more)

### Community 75 - "Community 75"
Cohesion: 0.09
Nodes (13): DiveHubTopBar(), titleLineHeight(), TitleSkeleton(), Action(), ConnectionStatus(), Section(), SettingsRoute(), SettingsScreen() (+5 more)

### Community 76 - "Community 76"
Cohesion: 0.06
Nodes (42): ADR-0036, ADR-0063, ADR-0038, ADR-0048, ADR-0062, AiAccess, assessedDive(), AssessedFinding (+34 more)

### Community 77 - "Community 77"
Cohesion: 0.09
Nodes (23): ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseCns, ApiDiversIdLoadingsGet200ResponseN2, ApiDiversIdLoadingsGet200ResponseNoFly, ApiDiversIdLoadingsGet200ResponseNoFlySource, ApiDiversIdLoadingsGet200ResponseOtu, cnsDecayed() (+15 more)

### Community 78 - "Community 78"
Cohesion: 0.08
Nodes (38): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+30 more)

### Community 79 - "Community 79"
Cohesion: 0.05
Nodes (42): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+34 more)

### Community 80 - "Community 80"
Cohesion: 0.07
Nodes (6): SettingsViewModel, SettingsRouteTest, SettingsRepository, InstanceUrlStore, FakeInstances, SettingsRepositoryTest

### Community 81 - "Community 81"
Cohesion: 0.08
Nodes (40): Leave, Series, SENT_KEYS, diveModeOfSsi(), keepTraining(), ssiDiveType(), ssiInteger(), ADR-0057 (+32 more)

### Community 82 - "Community 82"
Cohesion: 0.06
Nodes (39): Position, running(), SiteImportArea, siteImportsQuery(), SiteImportView, siteRevisionsQuery(), SiteRevisionView, ADR-0020 (+31 more)

### Community 83 - "Community 83"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 85 - "Community 85"
Cohesion: 0.09
Nodes (37): EstimateDive, EstimateDiveCylinder, WeightingFeedback, WeightingFelt, right, too_heavy, too_light, dayNumber() (+29 more)

### Community 86 - "Community 86"
Cohesion: 0.11
Nodes (7): DiveRow(), Figures(), RowFacts(), FactRow(), NoteText(), StackedNoteText(), ComponentsScreenshotTest

### Community 87 - "Community 87"
Cohesion: 0.10
Nodes (12): ScanScreenTest, ApiVerificationCodesReadPost200Response, ApiVerificationCodesReadPost200ResponseCandidatesItem, ScanStatus, Created, Existing, Failed, Read (+4 more)

### Community 88 - "Community 88"
Cohesion: 0.08
Nodes (37): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+29 more)

### Community 89 - "Community 89"
Cohesion: 0.06
Nodes (7): KeystoreTokenStore, PreferencesCachedMeStore, PreferencesInstanceUrlStore, UserCacheStore, CachedMeStore, TokenStore, UserScopedStore

### Community 90 - "Community 90"
Cohesion: 0.14
Nodes (6): LogbookPhase, Error, List, Loading, Offline, SettingsScreenshotTest

### Community 91 - "Community 91"
Cohesion: 0.09
Nodes (16): LogbookViewModel, neighboursPath(), countOf(), FilterChip, filterChips(), LogbookFilter, CHANGED_SINCE_SENT, NO_RECORDING (+8 more)

### Community 92 - "Community 92"
Cohesion: 0.08
Nodes (4): QrImageDecoder, LauncherIconTest, SplashTheme, SplashThemeTest

### Community 93 - "Community 93"
Cohesion: 0.09
Nodes (34): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+26 more)

### Community 94 - "Community 94"
Cohesion: 0.05
Nodes (39): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+31 more)

### Community 95 - "Community 95"
Cohesion: 0.17
Nodes (37): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestEntries(), entryNamed(), flag() (+29 more)

### Community 96 - "Community 96"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 97 - "Community 97"
Cohesion: 0.06
Nodes (4): DiveHubApplication, SyncWorker, WorkManagerPeriodicSync, PeriodicSyncScheduler

### Community 98 - "Community 98"
Cohesion: 0.08
Nodes (35): participantsOfDives(), buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf() (+27 more)

### Community 99 - "Community 99"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 100 - "Community 100"
Cohesion: 0.11
Nodes (8): AppNavHost(), AppScreens, clearSavedTabStates(), isShowing(), leaveDiveForLogbook(), MainShell(), navigateToTab(), tabOf()

### Community 102 - "Community 102"
Cohesion: 0.07
Nodes (28): headers, ADR-0030, ADR-0040, conflictForLena(), diveWithoutRecording(), editInSsi(), externalDiver(), importLena() (+20 more)

### Community 104 - "Community 104"
Cohesion: 0.11
Nodes (13): LogbookPaging, AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint, SketchProfile, sketchScale() (+5 more)

### Community 105 - "Community 105"
Cohesion: 0.08
Nodes (31): AI_ACCESS_NAME_MAX, aiAccessLog, aiAccessSetting, Access, AccessView, AiAccessRouteDeps, LogEntryView, Scope (+23 more)

### Community 106 - "Community 106"
Cohesion: 0.09
Nodes (33): overlapWindow(), recordingKeySource(), deletedEarlier(), fullerCopyHere(), IsManaged, knownRecording(), matchDecision(), nearbyDives() (+25 more)

### Community 107 - "Community 107"
Cohesion: 0.13
Nodes (34): DEFAULT_SSI_URL, acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findNewId() (+26 more)

### Community 109 - "Community 109"
Cohesion: 0.11
Nodes (5): FakeCache, FakeCachedMe, FakeInstances, FakeTokens, MainViewModelTest

### Community 110 - "Community 110"
Cohesion: 0.11
Nodes (27): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+19 more)

### Community 111 - "Community 111"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 112 - "Community 112"
Cohesion: 0.10
Nodes (26): ADR-0051, Status, ADR-0056, ADR-0024, ADR-0043, clearQueue(), conflictAtSsi(), connectSsi() (+18 more)

### Community 113 - "Community 113"
Cohesion: 0.12
Nodes (31): importsQuery(), ImportView, ImportContext, ImportFilesButton(), ImportHistory(), ImportList(), ImportNotices(), ImportPanel() (+23 more)

### Community 115 - "Community 115"
Cohesion: 0.20
Nodes (5): DiveMergeTest, diveObject(), participant(), participants(), putDive()

### Community 116 - "Community 116"
Cohesion: 0.13
Nodes (7): GateCachedMe, GateCall, GateClient, GateInstances, GateServer, GateTokens, WriteExitGateTest

### Community 117 - "Community 117"
Cohesion: 0.08
Nodes (32): dependencies, better-auth@1.7.7, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1 (+24 more)

### Community 118 - "Community 118"
Cohesion: 0.09
Nodes (29): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+21 more)

### Community 119 - "Community 119"
Cohesion: 0.09
Nodes (8): ApiDiveSitesGet200Response, SiteView, SiteViewExternalIdsItem, SiteViewExternalIdsItemAttribution, SiteViewExternalIdsItemOffered, SiteViewExternalIdsItemOfferedPosition, SiteViewExternalIdsItemPrivateTo, SiteViewPosition

### Community 120 - "Community 120"
Cohesion: 0.14
Nodes (22): Body, Hold, Offline, RefreshData, refreshDeletedJson(), refreshDetailJson(), refreshDiveListItem(), refreshDivePage() (+14 more)

### Community 121 - "Community 121"
Cohesion: 0.08
Nodes (28): CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043, CentreChanged (+20 more)

### Community 122 - "Community 122"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 123 - "Community 123"
Cohesion: 0.13
Nodes (8): InstanceProbeTest, json(), probe(), ProbeCache, ProbeCachedMe, ProbeInstances, probeRepository(), ProbeTokens

### Community 124 - "Community 124"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 125 - "Community 125"
Cohesion: 0.09
Nodes (7): ToolPage, Gas, Index, Lead, Loadings, Plan, FakeScreens

### Community 126 - "Community 126"
Cohesion: 0.25
Nodes (6): leadAnswer(), EstimateCylinder, PlannedConditions, Suit, leadEstimate(), PersonalizedToolsTest

### Community 127 - "Community 127"
Cohesion: 0.07
Nodes (12): buddySite(), Dive, OTHER, PendingOffer, ADR-0012, ADR-0070, Dive, OTHER (+4 more)

### Community 128 - "Community 128"
Cohesion: 0.13
Nodes (16): DiveHubApp(), DiveHubScreens, ErrorText(), Gate(), InstanceForm(), InstanceStatus(), LoadingScreen(), PasswordField() (+8 more)

### Community 129 - "Community 129"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 130 - "Community 130"
Cohesion: 0.10
Nodes (26): globalSetup(), ADR-0023, accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks() (+18 more)

### Community 132 - "Community 132"
Cohesion: 0.11
Nodes (7): ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh, ApiToolsBottomTimeGet200ResponseUsedSacLpm, ApiToolsBottomTimeGet200ResponseUsedWater

### Community 133 - "Community 133"
Cohesion: 0.11
Nodes (7): ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport

### Community 134 - "Community 134"
Cohesion: 0.13
Nodes (23): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, Site (+15 more)

### Community 135 - "Community 135"
Cohesion: 0.10
Nodes (18): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, APP (+10 more)

### Community 136 - "Community 136"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 137 - "Community 137"
Cohesion: 0.18
Nodes (3): ToolLink(), ToolsIndexScreen(), ToolsScreenshotTest

### Community 138 - "Community 138"
Cohesion: 0.22
Nodes (21): ChangesList(), ConflictChoice(), ConflictEntry(), diveTitle(), FieldDecision(), NoticeSection(), noticeText(), QueuedSection() (+13 more)

### Community 140 - "Community 140"
Cohesion: 0.11
Nodes (5): DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewCylindersItemSeries, DiveViewLeadItem, DiveViewSite

### Community 141 - "Community 141"
Cohesion: 0.19
Nodes (10): Body, buddyAnswer(), centreAnswer(), centreView(), diverView(), Offline, ScanCall, ScanHarness (+2 more)

### Community 142 - "Community 142"
Cohesion: 0.13
Nodes (22): errorMessage(), failureOf(), placeFile(), placeFiles(), flag(), idText(), isObject(), numberOrNull() (+14 more)

### Community 143 - "Community 143"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 144 - "Community 144"
Cohesion: 0.14
Nodes (7): DiveListViewDivesItem, DiveListViewDivesItemGasesItem, DiveListViewDivesItemParticipantsItem, DiveListViewDivesItemSite, DiveProfileView, toListRow(), dive()

### Community 145 - "Community 145"
Cohesion: 0.13
Nodes (22): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DueInput, DueSchedule (+14 more)

### Community 146 - "Community 146"
Cohesion: 0.12
Nodes (23): ScheduleDue, DiveGear, DueEntry, GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow (+15 more)

### Community 147 - "Community 147"
Cohesion: 0.12
Nodes (19): createFileFormats(), FileFormat, looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf() (+11 more)

### Community 148 - "Community 148"
Cohesion: 0.16
Nodes (23): DiveOrigin, REFERENCE_PREFIX, asBool(), DIVE_ROW, SsiValueType, ADR-0027, aboveZero(), cylinderOf() (+15 more)

### Community 149 - "Community 149"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 150 - "Community 150"
Cohesion: 0.15
Nodes (3): DiveViewRecordingsItemSummary, StoreLogbook, summaryOf()

### Community 151 - "Community 151"
Cohesion: 0.18
Nodes (3): DiveViewValues, DiveViewValuesStartsAt, StoreLogbookTest

### Community 152 - "Community 152"
Cohesion: 0.08
Nodes (23): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+15 more)

### Community 153 - "Community 153"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 154 - "Community 154"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 155 - "Community 155"
Cohesion: 0.15
Nodes (6): AppTab, Logbook, Settings, Tools, DiveHubBottomBar(), BottomBarScreenshotTest

### Community 156 - "Community 156"
Cohesion: 0.13
Nodes (6): DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, WaitingPushView

### Community 157 - "Community 157"
Cohesion: 0.12
Nodes (11): diveLocalTime(), diveStartAt(), offsetId(), timeZoneNote(), TimeZoneOrigin, DEVICE, NEARBY, POSITION (+3 more)

### Community 158 - "Community 158"
Cohesion: 0.17
Nodes (7): Connected, Hint, HintFrame, LiveEvent, LiveHint, SseLiveHints, LiveHintsTest

### Community 159 - "Community 159"
Cohesion: 0.24
Nodes (6): Body, Call, Harness, Offline, Reply, WriteReplayerTest

### Community 160 - "Community 160"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 161 - "Community 161"
Cohesion: 0.23
Nodes (5): Body, ConflictCall, ConflictHarness, ConflictReply, WriteConflictResolutionTest

### Community 162 - "Community 162"
Cohesion: 0.09
Nodes (18): COMPUTER_EVENT, DECO_MODEL, decoModelFromSuunto, DIVE_MODE, diveModeFromSuunto, FIT_DIVE_MODE, SUUNTO_EVENT_TYPES, ADR-0015 (+10 more)

### Community 163 - "Community 163"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 166 - "Community 166"
Cohesion: 0.14
Nodes (4): FakeCachedMe, FakeInstances, FakeTokens, SyncAuthTest

### Community 167 - "Community 167"
Cohesion: 0.12
Nodes (19): SUGGESTION_STATUSES, DiveProfileView, DiveProfileViewSchema, ADR-0041, AcceptBody, STATUS, SuggestionRouteDeps, ADR-0070 (+11 more)

### Community 168 - "Community 168"
Cohesion: 0.16
Nodes (21): managementOf(), EquipmentError, checkCost(), checkDates(), checkWindow(), createEquipmentService(), assemble(), checkResets() (+13 more)

### Community 169 - "Community 169"
Cohesion: 0.11
Nodes (16): adapter, crossCheck(), decodeOfficial(), privateDir, FIT_EPOCH_MS, makeSuuntoAppFit(), makeSuuntoJson(), shape() (+8 more)

### Community 170 - "Community 170"
Cohesion: 0.18
Nodes (7): LogbookUiState, DiveListView, DiveListViewCounts, DiveListViewMonthsItem, DiveListViewTotals, LogbookRead, StoreLogbookContext

### Community 172 - "Community 172"
Cohesion: 0.10
Nodes (15): changeEvent, RevisionEntityType, CHANGE_EVENT_RETENTION_DAYS, ChangeEventView, ChangeFeedPage, ChangeKind, CHANGES_DEFAULT_LIMIT, CHANGES_MAX_LIMIT (+7 more)

### Community 173 - "Community 173"
Cohesion: 0.12
Nodes (18): siteImportStatus, PROBLEM_CODES, SiteImportRouteDeps, StartBody, STATUS, ADR-0021, SiteImportError, SiteImportService (+10 more)

### Community 174 - "Community 174"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 175 - "Community 175"
Cohesion: 0.19
Nodes (4): DiveDetailViewModel, NeighboursRead, ProfileRead, StoredDive

### Community 177 - "Community 177"
Cohesion: 0.15
Nodes (5): ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits

### Community 178 - "Community 178"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 180 - "Community 180"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 181 - "Community 181"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 182 - "Community 182"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 183 - "Community 183"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 184 - "Community 184"
Cohesion: 0.21
Nodes (16): jsRound(), powInt(), roundTo(), Sac, SacCylinder, SacDive, SacMissing, cylinder_incomplete (+8 more)

### Community 185 - "Community 185"
Cohesion: 0.29
Nodes (5): assembleToolSources(), epochMs(), recentDiveEnd(), summaryOf(), ToolSourcesStoreTest

### Community 186 - "Community 186"
Cohesion: 0.15
Nodes (17): serviceRecord, serviceRecordSchedule, serviceSchedule, equipmentList(), EquipmentRecordRead, today(), ADR-0034, ADR-0035 (+9 more)

### Community 187 - "Community 187"
Cohesion: 0.13
Nodes (9): managesDiveDiver(), CandidateError, createCandidateService(), lockMine(), createLogbookService(), deletedDiveIds(), deletedDives(), findManagedDive() (+1 more)

### Community 188 - "Community 188"
Cohesion: 0.14
Nodes (13): BufferedLog, LOG_BUFFER_CAPACITY_BYTES, LOG_BUFFER_CAPACITY_ENTRIES, LogBufferStats, LogRingBufferOptions, SupportBundle, ClientDiagnosticsProvider, FailedBackup (+5 more)

### Community 189 - "Community 189"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 190 - "Community 190"
Cohesion: 0.14
Nodes (15): JOINT_FILL_FIELDS, OfferRouteDeps, STATUS, ADR-0070, OfferError, OfferService, Facts, Field (+7 more)

### Community 191 - "Community 191"
Cohesion: 0.12
Nodes (16): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+8 more)

### Community 192 - "Community 192"
Cohesion: 0.12
Nodes (8): SsiClient, deleteTestDive(), ClearChecks, NumberChecks, findDive(), runSiteVisibleFinish(), TwowayChecks, ValuesChecks

### Community 193 - "Community 193"
Cohesion: 0.14
Nodes (14): AL80, EstimateDiveCylinder, LEAD_CASES, leadInput(), LeadVectorInput, planOf(), STEEL_12, ADR-0031 (+6 more)

### Community 194 - "Community 194"
Cohesion: 0.12
Nodes (17): dependencies, @dive-hub/api-client@workspace:*, @fontsource/poppins@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0, react@^19.3.0, react-aria-components@^1.21.1 (+9 more)

### Community 195 - "Community 195"
Cohesion: 0.17
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 198 - "Community 198"
Cohesion: 0.19
Nodes (4): ApiToolsBottomTimeGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseLimits, ApiToolsBottomTimeGet200ResponseResidualN2

### Community 199 - "Community 199"
Cohesion: 0.19
Nodes (4): CentreView, CentreViewExternalIdsItem, CentreViewSitesItem, CentreViewVerificationCode

### Community 200 - "Community 200"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 201 - "Community 201"
Cohesion: 0.12
Nodes (13): clientDiagnostic, clientDiagnosticsSettings, ClientDiagnosticsEntryInput, ClientDiagnosticsEntryView, ClientDiagnosticsError, ClientDiagnosticsUpload, KEEP_NEWEST, MAX_UPLOAD_ENTRIES (+5 more)

### Community 202 - "Community 202"
Cohesion: 0.19
Nodes (13): asEstimateInput(), evaluateLeadVectorCase(), LEAD_VECTOR_KERNEL, leadVectorFile(), VECTOR_KERNELS, VectorFile, vectorFile(), assertMatch() (+5 more)

### Community 203 - "Community 203"
Cohesion: 0.21
Nodes (11): buildSupportBundle(), buildTarGz(), json(), ndjson(), octal(), SUPPORT_BUNDLE_FORMAT, SUPPORT_BUNDLE_FORMAT_VERSION, SupportBufferManifest (+3 more)

### Community 204 - "Community 204"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 205 - "Community 205"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 208 - "Community 208"
Cohesion: 0.16
Nodes (12): CIRCUIT, circuitFromFit, DECO_MODEL, decoModelFromFit, DIVE_ALERT, DIVE_MODE, ADR-0015, WATER_TYPE (+4 more)

### Community 209 - "Community 209"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 210 - "Community 210"
Cohesion: 0.13
Nodes (6): FakeBroadcastChannel, FakeEventSource, Listener, locks, start(), ADR-0066

### Community 211 - "Community 211"
Cohesion: 0.13
Nodes (14): concurrency, cancel-in-progress, group, env, RELEASE_TAG, jobs, publish, name (+6 more)

### Community 212 - "Community 212"
Cohesion: 0.33
Nodes (4): ConflictField, DiveMerge, MergeOutcome, ParticipantsMergeOutcome

### Community 213 - "Community 213"
Cohesion: 0.18
Nodes (13): SuitType, SuitUndergarment, WeightingFelt, Suit, WeightingFeedback, LeadVectorDive, Confidence, EstimateAdjustment (+5 more)

### Community 214 - "Community 214"
Cohesion: 0.20
Nodes (5): LogRingBuffer, RedactionCounters, SupportBundleInput, SupportBundleManifest, SupportServiceDeps

### Community 215 - "Community 215"
Cohesion: 0.22
Nodes (11): fromOsm(), fromSsi(), fromWd(), HOUSE_REEF, osm(), site(), ssi(), ADR-0021 (+3 more)

### Community 216 - "Community 216"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 217 - "Community 217"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 218 - "Community 218"
Cohesion: 0.31
Nodes (11): ChangeBullet(), changeField(), ChangeLine(), ChooseRow(), Dismiss(), OutcomeText(), ReadResult(), scanErrorText() (+3 more)

### Community 219 - "Community 219"
Cohesion: 0.33
Nodes (5): LeadInputTest, SuitUndergarment, heavy, light, medium

### Community 221 - "Community 221"
Cohesion: 0.17
Nodes (10): EstimateInput, AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031 (+2 more)

### Community 222 - "Community 222"
Cohesion: 0.23
Nodes (10): ADR-0076, ClientVersionRegistry, MIN_CLIENT_VERSIONS, MinClientVersion, ADR-0076, compareIdentifier(), compareNumeric(), compareSemanticVersions() (+2 more)

### Community 223 - "Community 223"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 224 - "Community 224"
Cohesion: 0.32
Nodes (10): ConflictsRoute, DiveEditRoute, InstanceRoute, LogbookRoute, MainRoute, ScanRoute, SettingsDebugRoute, SettingsRoute (+2 more)

### Community 225 - "Community 225"
Cohesion: 0.26
Nodes (3): DiverView, DiverViewCertifications, DiverViewCertificationsNewest

### Community 226 - "Community 226"
Cohesion: 0.26
Nodes (3): DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 227 - "Community 227"
Cohesion: 0.17
Nodes (11): account, accountRelations, apikey, rateLimit, sessionRelations, ADR-0011, ADR-0012, ADR-0035 (+3 more)

### Community 228 - "Community 228"
Cohesion: 0.18
Nodes (11): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, ADR-0076, ADR-0083, MODULE_IMPORTS (+3 more)

### Community 229 - "Community 229"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 230 - "Community 230"
Cohesion: 0.24
Nodes (7): compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051, ADR-0051

### Community 231 - "Community 231"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 232 - "Community 232"
Cohesion: 0.22
Nodes (8): diveRow(), rowOf(), ADR-0026, ADR-0048, diveRows(), goToMore(), expectFirstDiveInView(), rowOf()

### Community 233 - "Community 233"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 234 - "Community 234"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 235 - "Community 235"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 236 - "Community 236"
Cohesion: 0.53
Nodes (3): UiState, sessionDestination(), SessionDestinationTest

### Community 239 - "Community 239"
Cohesion: 0.29
Nodes (7): otu(), oxygenLimitMinutes(), compressibility(), Cubic, minusOne(), pressureForLitres(), surfaceLitres()

### Community 241 - "Community 241"
Cohesion: 0.33
Nodes (8): prepareEntry(), DENIED_KEY_PARTS, isDeniedKey(), maskString(), redactInner(), redactLogObject(), RedactOptions, redactValue()

### Community 242 - "Community 242"
Cohesion: 0.36
Nodes (9): describe(), isObject(), keyDifferences(), pause(), recording(), runAnswerChecks(), sampleCount(), testDive() (+1 more)

### Community 243 - "Community 243"
Cohesion: 0.20
Nodes (9): credentials(), Numbered, SSI, Status, ADR-0024, ADR-0027, ADR-0056, upload() (+1 more)

### Community 244 - "Community 244"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 245 - "Community 245"
Cohesion: 0.20
Nodes (10): verify, prerelease, sha, tag, versionCode, versionName, name, outputs (+2 more)

### Community 246 - "Community 246"
Cohesion: 0.20
Nodes (10): with, cache, distribution, fetch-depth, if-no-files-found, java-version, name, path (+2 more)

### Community 247 - "Community 247"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 251 - "Community 251"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 252 - "Community 252"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 253 - "Community 253"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 254 - "Community 254"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 257 - "Community 257"
Cohesion: 0.29
Nodes (5): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, EstimateCylinder

### Community 258 - "Community 258"
Cohesion: 0.43
Nodes (7): defined(), Joined, joinedSummary(), joinParts(), median(), round3(), ADR-0063

### Community 259 - "Community 259"
Cohesion: 0.43
Nodes (8): emptyRedactionCounters(), createSupportService(), assemble(), clientDiagnostics(), configSection(), failedJobs(), healthSection(), maskDatabaseUrl()

### Community 260 - "Community 260"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 261 - "Community 261"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 262 - "Community 262"
Cohesion: 0.43
Nodes (5): createFitAdapter(), ownersParts(), parse(), toPart(), ADR-0063

### Community 263 - "Community 263"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 264 - "Community 264"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 265 - "Community 265"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 266 - "Community 266"
Cohesion: 0.33
Nodes (6): SuitType, DRYSUIT, NONE, SEMIDRY, SKIN, WETSUIT

### Community 267 - "Community 267"
Cohesion: 0.40
Nodes (5): stubDive(), ADR-0015, ADR-0023, ADR-0025, guardedRoute()

### Community 268 - "Community 268"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 269 - "Community 269"
Cohesion: 0.40
Nodes (5): DivePhase, Dive, Error, Loading, Offline

### Community 271 - "Community 271"
Cohesion: 0.40
Nodes (5): ensureItemForDevice(), scheduleView(), today(), trimName(), resolveDevice()

### Community 272 - "Community 272"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build

### Community 273 - "Community 273"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 274 - "Community 274"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 275 - "Community 275"
Cohesion: 0.40
Nodes (4): TAGS, ADR-0023, { stdout, stderr }, total

### Community 282 - "Community 282"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 283 - "Community 283"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 284 - "Community 284"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1368 isolated node(s):** `KeptServer`, `Requeued`, `Reachable`, `Unknown`, `Failed` (+1363 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3976 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **177 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `DiveStore` connect `Community 22` to `Community 11`, `Community 141`, `Community 19`, `Community 150`, `Community 25`, `Community 27`, `Community 30`, `Community 159`, `Community 161`, `Community 166`, `Community 170`, `Community 179`, `Community 185`, `Community 58`, `Community 60`, `Community 66`, `Community 67`, `Community 70`, `Community 80`, `Community 85`, `Community 115`, `Community 116`?**
  _High betweenness centrality (0.021) - this node is a cross-community bridge._
- **What connects `KeptServer`, `Requeued`, `Reachable` to the rest of the system?**
  _1368 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.015177880897138438 - nodes in this community are weakly interconnected._
- **Why does `DiveHubTheme()` connect `Community 75` to `Community 131`, `Community 137`, `Community 11`, `Community 140`, `Community 20`, `Community 155`, `Community 164`, `Community 165`, `Community 38`, `Community 50`, `Community 53`, `Community 58`, `Community 68`, `Community 197`, `Community 80`, `Community 86`, `Community 87`, `Community 90`, `Community 114`?**
  _High betweenness centrality (0.014) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.012833731278489255 - nodes in this community are weakly interconnected._
- **Why does `drizzle-orm` connect `Community 0` to `Community 1`, `Community 3`, `Community 5`, `Community 6`, `Community 14`, `Community 15`, `Community 16`, `Community 17`, `Community 146`, `Community 18`, `Community 21`, `Community 24`, `Community 29`, `Community 40`, `Community 41`, `Community 42`, `Community 44`, `Community 45`, `Community 172`, `Community 47`, `Community 48`, `Community 186`, `Community 188`, `Community 201`, `Community 93`, `Community 98`, `Community 227`, `Community 105`, `Community 106`, `Community 243`, `Community 117`, `Community 118`?**
  _High betweenness centrality (0.013) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.03506841275081619 - nodes in this community are weakly interconnected._