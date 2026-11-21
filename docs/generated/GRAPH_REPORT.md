# Graph Report - dive_hub  (2026-11-21)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 11095 nodes · 33569 edges · 372 communities (221 shown, 151 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 628 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `eebd1ccc`
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
- Community 246
- Community 247
- Community 248
- Community 249
- Community 250
- Community 254

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
- `SsiClientOptions` --references--> `Fetch`  [EXTRACTED]
  apps/server/src/providers/ssi/ssi-client.ts → apps/server/src/providers/ssi/ssi-transport.ts
- `RepositoryHarness` --calls--> `LogbookRepository`  [INFERRED]
  apps/android/shared/src/commonTest/kotlin/dev/divehub/shared/logbook/LogbookTest.kt → apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/logbook/LogbookRepository.kt

## Import Cycles
- None detected.

## Communities (372 total, 151 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.03
Nodes (173): user, diveSiteExternalId, lead, createSsiClient(), Fetch, ADR-0013, ADR-0011, dumpToolAvailable() (+165 more)

### Community 1 - "Community 1"
Cohesion: 0.02
Nodes (212): diverSearchQuery(), allButDive(), continuationCandidateQuery(), deletedDivesOn(), deletedDivesQuery(), diveNeighboursQuery(), diveQuery(), DiveView (+204 more)

### Community 2 - "Community 2"
Cohesion: 0.02
Nodes (213): cylinderCatalogueQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas (+205 more)

### Community 3 - "Community 3"
Cohesion: 0.05
Nodes (209): ChangePassword(), DisplaySettings(), ReportProblemSettings(), SecuritySettings(), Sessions(), ADR-0080, useDescribeAgent(), AdminSettings() (+201 more)

### Community 4 - "Community 4"
Cohesion: 0.02
Nodes (168): continuationAppend, CylinderMaterial, equipmentUse, LeadPlacement, OverridableField, SuitType, SuitUndergarment, renumber() (+160 more)

### Community 5 - "Community 5"
Cohesion: 0.02
Nodes (161): APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), AppLockProbe, probeAppLock() (+153 more)

### Community 6 - "Community 6"
Cohesion: 0.02
Nodes (148): @modelcontextprotocol/server@^2.3.1, downsampleMinMax(), AiAccessRouteDeps, AccessRefusal, AiAccessScope, AiAccessService, seesPositions(), VerifiedAccess (+140 more)

### Community 7 - "Community 7"
Cohesion: 0.02
Nodes (126): apikey, buddySuggestion, diveAssessment, participant, recordingEvent, parseHint(), Assessment, FindingView (+118 more)

### Community 8 - "Community 8"
Cohesion: 0.02
Nodes (114): createServer(), createAuth(), auth, hashPassword(), loadAuthSecret(), acquireAppLock(), BackupLog, BackupServiceDeps (+106 more)

### Community 9 - "Community 9"
Cohesion: 0.02
Nodes (130): invitationLinkQuery(), invitationsQuery(), InvitationView, passwordResetQuery(), sessionsQuery(), SessionView, setupQuery(), setupRestoreArchivesQuery() (+122 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (132): label(), Me, AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery() (+124 more)

### Community 11 - "Community 11"
Cohesion: 0.02
Nodes (135): accountListEntry, actorType, aiAccessOutcome, backupRunKind, backupRunStatus, candidateResolution, centreSource, CERTIFICATION_IMAGE_STATES (+127 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (126): Auth, CentreService, CentreRouteDeps, LEAD_PLACEMENTS, OVERRIDABLE_FIELDS, UTC_OFFSET_SOURCES, WEIGHTING_FELT, DiverService (+118 more)

### Community 13 - "Community 13"
Cohesion: 0.04
Nodes (138): ComputerChoice, DiveImportMode, DiveKind, diveSite, providerSiteData, UtcOffsetSource, managedDiverIds(), mergeProviderCylinder() (+130 more)

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (122): divesQuery(), DiveSummary, LogbookPage, PAGE_SIZE, ExternalIdView, App(), BackupsPage, CentrePage (+114 more)

### Community 15 - "Community 15"
Cohesion: 0.03
Nodes (115): AuthOptions, ServiceParts, Db, clientDiagnostic, clientDiagnosticsSettings, ConnectionRow, providerGoneDive, providerLease (+107 more)

### Community 16 - "Community 16"
Cohesion: 0.03
Nodes (105): EMPTY_SITE_IMPORT_COUNTS, ImportArea, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053 (+97 more)

### Community 17 - "Community 17"
Cohesion: 0.04
Nodes (30): DiveRow(), EmptyState(), FactRow(), Section(), AppTab, Logbook, Settings, Tools (+22 more)

### Community 18 - "Community 18"
Cohesion: 0.04
Nodes (102): diveCentre, jointDive, logbookCheckAnswer, logbookCheckDiveAnswer, agreeInDepthAndDuration(), CheckedDive, DeletableDive, findChecks() (+94 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (94): ParticipantRole, waitingOf(), open(), close(), createConnectionService(), checkAutomation(), checkFollowNumbers(), expiryOf() (+86 more)

### Community 20 - "Community 20"
Cohesion: 0.04
Nodes (80): equipmentDueQuery(), cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043 (+72 more)

### Community 21 - "Community 21"
Cohesion: 0.03
Nodes (85): fastify@^5.12.5, adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, SessionUser, ADR-0011 (+77 more)

### Community 22 - "Community 22"
Cohesion: 0.04
Nodes (91): DiveValues, OverridableField, revisionsQuery(), RevisionView, UtcOffsetSource, DiveImportMode, DiveImportPreview, diveImportPreviewQuery() (+83 more)

### Community 23 - "Community 23"
Cohesion: 0.06
Nodes (18): RevisionFollowTest, LeadEstimateViewModelTest, DiveViewRecordingsItemSummary, ErrorCodes, Cylinder, Dive, Diver, DiveSite (+10 more)

### Community 24 - "Community 24"
Cohesion: 0.04
Nodes (90): siteImport, siteImportStatus, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053, CandidateRouteDeps, CandidateView (+82 more)

### Community 25 - "Community 25"
Cohesion: 0.03
Nodes (85): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+77 more)

### Community 26 - "Community 26"
Cohesion: 0.04
Nodes (16): SettingsDiagnosticsState, SettingsUiState, SettingsViewModel, FakeDiagnostics, FakeInstances, SettingsRouteTest, SettingsViewModelTest, ReportProblemResult (+8 more)

### Community 27 - "Community 27"
Cohesion: 0.03
Nodes (74): connection, diveLease, providerConflict, push, pushRun, revision, waitingPush, kept() (+66 more)

### Community 28 - "Community 28"
Cohesion: 0.06
Nodes (84): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, assessmentRoutes() (+76 more)

### Community 29 - "Community 29"
Cohesion: 0.04
Nodes (67): cylinder, device, dive, duplicateCandidate, ImportErrorCode, ImportFound, importJob, importOriginal (+59 more)

### Community 30 - "Community 30"
Cohesion: 0.05
Nodes (42): cylinderLine(), cylinderPressures(), Cylinders(), Direction, Back, Left, Right, DirectionIcon() (+34 more)

### Community 31 - "Community 31"
Cohesion: 0.04
Nodes (48): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+40 more)

### Community 32 - "Community 32"
Cohesion: 0.03
Nodes (68): createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities(), ADR-0062 (+60 more)

### Community 33 - "Community 33"
Cohesion: 0.05
Nodes (28): LogbookUiState, LogbookViewModel, DiveListView, DiveListViewCounts, DiveRepository, neighboursPath(), countOf(), FilterChip (+20 more)

### Community 34 - "Community 34"
Cohesion: 0.06
Nodes (15): LogbookScreenshotTest, DiveListViewDivesItem, DiveListViewDivesItemGasesItem, DiveListViewDivesItemParticipantsItem, DiveListViewDivesItemSite, DiveListViewMonthsItem, DiveListViewTotals, DiveProfileView (+7 more)

### Community 35 - "Community 35"
Cohesion: 0.04
Nodes (74): AssessmentService, AssessmentRouteDeps, BackupRouteDeps, BACKUP_RUN_TASK, BackupService, backupTask(), ADR-0069, CertificationService (+66 more)

### Community 36 - "Community 36"
Cohesion: 0.08
Nodes (60): Formats, formatSyncTime(), GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult() (+52 more)

### Community 37 - "Community 37"
Cohesion: 0.06
Nodes (68): SiteImportFinding, SiteImportProgress, dataIdsOf(), settleSiteVisibility(), outgoingSite(), findOrMake(), SiteForSend, siteForSend() (+60 more)

### Community 38 - "Community 38"
Cohesion: 0.06
Nodes (64): NoFly, Queryable, getDiverLoadings, HINT, Input, LoadingsInput, planGasAndBottomTime, ADR-0032 (+56 more)

### Community 39 - "Community 39"
Cohesion: 0.05
Nodes (10): DiveDetailViewModel, followStoreWrites(), LoadingsUiState, LoadingsViewModel, Answers, number(), ToolsInputs, ToolsUiState (+2 more)

### Community 40 - "Community 40"
Cohesion: 0.07
Nodes (65): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+57 more)

### Community 41 - "Community 41"
Cohesion: 0.05
Nodes (18): MainActivity, MainViewModel, Unreachable, ApiMeGet200Response, ApiMeGet200ResponsePreferences, SignInEmailRequest, UserView, InstanceUrl (+10 more)

### Community 42 - "Community 42"
Cohesion: 0.03
Nodes (49): ProviderImportPlan, MAX_OFFSET_MS, inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), placeLocalTime(), Position (+41 more)

### Community 43 - "Community 43"
Cohesion: 0.06
Nodes (64): ANDROID_UI, AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch() (+56 more)

### Community 44 - "Community 44"
Cohesion: 0.06
Nodes (30): ApiToolsBottomTimeGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseLimits, ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh (+22 more)

### Community 45 - "Community 45"
Cohesion: 0.06
Nodes (8): CountingDispatcher, LogbookViewModelTest, ApiClient, ApiErrorBody, DecodeException, DiveHubException, NoAnswerException, ApiClientTest

### Community 46 - "Community 46"
Cohesion: 0.06
Nodes (44): BufferedLog, LOG_BUFFER_CAPACITY_BYTES, LOG_BUFFER_CAPACITY_ENTRIES, LogBufferStats, LogRingBuffer, LogRingBufferOptions, DENIED_KEY_PARTS, emptyRedactionCounters() (+36 more)

### Community 47 - "Community 47"
Cohesion: 0.05
Nodes (5): DiveStore, toBoolean(), toLong(), toModel(), BodyWeight

### Community 48 - "Community 48"
Cohesion: 0.05
Nodes (54): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+46 more)

### Community 49 - "Community 49"
Cohesion: 0.06
Nodes (46): SsiClient, SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), DiveForSsi, emptyRecord(), writableRecord() (+38 more)

### Community 50 - "Community 50"
Cohesion: 0.07
Nodes (28): recordingName(), Recordings(), DiveViewRecordingsItem, DiveViewRecordingsItemSummarySurfaceBreaksItem, RecordingSamplesViewSeriesItem, computerRecordings(), depthSeries(), nearest() (+20 more)

### Community 51 - "Community 51"
Cohesion: 0.10
Nodes (28): LiveHints, Body, Hold, Offline, RefreshData, refreshDeletedJson(), refreshDetailJson(), refreshDetailsPage() (+20 more)

### Community 52 - "Community 52"
Cohesion: 0.04
Nodes (55): ADR-0025, WaterMismatch, waterMismatch(), EquipmentService, RecordInput, ScheduleInput, DiveGearItemViewSchema, DiveGearViewSchema (+47 more)

### Community 53 - "Community 53"
Cohesion: 0.05
Nodes (51): replyCertificationError, STATUS, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, CertificationRouteDeps (+43 more)

### Community 54 - "Community 54"
Cohesion: 0.06
Nodes (36): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+28 more)

### Community 55 - "Community 55"
Cohesion: 0.09
Nodes (11): ApiDiverBodyWeightsGet200ResponseItem, SyncCheckpoint, Complete, SessionEnded, Stopped, SyncEngine, SyncPhase, Details (+3 more)

### Community 56 - "Community 56"
Cohesion: 0.07
Nodes (48): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+40 more)

### Community 57 - "Community 57"
Cohesion: 0.07
Nodes (53): pushRunActive(), pushRunBody(), PushRunChoices, PushRunPreview, pushRunPreviewQuery(), pushRunQuery(), pushRunsQuery(), startPushRun() (+45 more)

### Community 58 - "Community 58"
Cohesion: 0.05
Nodes (43): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError (+35 more)

### Community 59 - "Community 59"
Cohesion: 0.05
Nodes (49): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, conflictForLena() (+41 more)

### Community 60 - "Community 60"
Cohesion: 0.12
Nodes (3): DiveRoute, AppNavHostTest, FakeScreens

### Community 61 - "Community 61"
Cohesion: 0.10
Nodes (40): ambientPressureBar(), bestMixPercent(), Conditions, depthOfPressure(), eadMetres(), endMetres(), GasMix, isHypoxic() (+32 more)

### Community 62 - "Community 62"
Cohesion: 0.13
Nodes (22): Body, BrokenBody, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage(), diverJson() (+14 more)

### Community 63 - "Community 63"
Cohesion: 0.05
Nodes (46): BottomTime, bottomTime(), NOAA_ROWS, otu(), oxygenLimitMinutes(), ADR-0032, AIR, BOTTOM_TIME_CASES (+38 more)

### Community 64 - "Community 64"
Cohesion: 0.09
Nodes (6): SettingsRepository, SyncState, InstanceUrlStore, FakeInstances, SettingsRepositoryTest, DiveStoreTest

### Community 65 - "Community 65"
Cohesion: 0.07
Nodes (46): AIR, cnsDecayed(), DEFAULT_CONDITIONS, N2_REPLAY_WINDOW_HOURS, N2_STALE_AFTER_HOURS, N2Loadings, N2LoadingsBase, N2UnavailableReason (+38 more)

### Community 66 - "Community 66"
Cohesion: 0.07
Nodes (46): Delivered, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open() (+38 more)

### Community 67 - "Community 67"
Cohesion: 0.06
Nodes (43): diveListFilter(), notAtProvider(), Blocked, classify(), FailureOutcome, failureOutcome(), kindOf(), KINDS (+35 more)

### Community 68 - "Community 68"
Cohesion: 0.07
Nodes (20): AppNavHost(), AppScreens, clearSavedTabStates(), isShowing(), leaveDiveForLogbook(), MainShell(), navigateToTab(), tabOf() (+12 more)

### Community 69 - "Community 69"
Cohesion: 0.09
Nodes (48): Answer, answer(), baseFrom(), Confidence, high, low, medium, Cylinder (+40 more)

### Community 70 - "Community 70"
Cohesion: 0.07
Nodes (35): diveWith(), ADR-0028, ADR-0043, buddySite(), Dive, OTHER, resetSource(), ADR-0012 (+27 more)

### Community 71 - "Community 71"
Cohesion: 0.09
Nodes (20): DiveDetailRoute(), DiveHubApp(), DiveHubScreens, ErrorText(), Gate(), InstanceForm(), InstanceStatus(), LoadingScreen() (+12 more)

### Community 72 - "Community 72"
Cohesion: 0.05
Nodes (39): CYLINDER_MATERIALS, SUIT_TYPES, SUIT_UNDERGARMENTS, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053 (+31 more)

### Community 73 - "Community 73"
Cohesion: 0.08
Nodes (40): DiveField, Leave, asBool(), DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi() (+32 more)

### Community 74 - "Community 74"
Cohesion: 0.10
Nodes (16): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), GasPlanInputTest (+8 more)

### Community 75 - "Community 75"
Cohesion: 0.11
Nodes (8): FakeCache, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore, UserScopedStore

### Community 76 - "Community 76"
Cohesion: 0.19
Nodes (6): DiveDetailScreen(), DiveDetailUiState, DiveHubTheme(), DiveDetailScreenshotTest, NeighboursView, NeighboursViewNext

### Community 77 - "Community 77"
Cohesion: 0.09
Nodes (37): EstimateDive, EstimateDiveCylinder, WeightingFeedback, WeightingFelt, right, too_heavy, too_light, dayNumber() (+29 more)

### Community 78 - "Community 78"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 79 - "Community 79"
Cohesion: 0.07
Nodes (5): AppContainer, KeystoreTokenStore, PreferencesInstanceUrlStore, UserCacheStore, AndroidDiveStore

### Community 81 - "Community 81"
Cohesion: 0.08
Nodes (27): captureDiagnostic(), captureError(), cleanContext(), clearDiagnostics(), CLIENT_NAME, CLIENT_VERSION, DiagnosticEntry, diagnosticFromError() (+19 more)

### Community 82 - "Community 82"
Cohesion: 0.11
Nodes (21): AvatarCircle(), DiveRow(), Error(), Fact(), Filters(), Loading(), LogbookList(), LogbookRoute() (+13 more)

### Community 83 - "Community 83"
Cohesion: 0.08
Nodes (38): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+30 more)

### Community 84 - "Community 84"
Cohesion: 0.06
Nodes (38): pushRunItem, PushRunLeftOut, asksFor(), DIVE_ONLY, DIVE_ONLY_PATTERN, DiveListFilter, DiveOnly, FINDINGS_SHOWN (+30 more)

### Community 85 - "Community 85"
Cohesion: 0.07
Nodes (32): sampleCount(), SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject() (+24 more)

### Community 86 - "Community 86"
Cohesion: 0.05
Nodes (37): AREA_OF, areaOf(), areas, capture(), findings, headers, ADR-0020, ADR-0021 (+29 more)

### Community 87 - "Community 87"
Cohesion: 0.09
Nodes (10): DiveView, DiveViewLeadItem, DiveViewSac, DiveViewSite, DiveViewSuit, DiveViewValues, DiveViewValuesStartsAt, DiveViewWeightingFeedback (+2 more)

### Community 88 - "Community 88"
Cohesion: 0.08
Nodes (35): BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, Conditions, GasMix, ASCENT_RATE_M_PER_MIN (+27 more)

### Community 89 - "Community 89"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 90 - "Community 90"
Cohesion: 0.08
Nodes (31): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+23 more)

### Community 91 - "Community 91"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 92 - "Community 92"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 93 - "Community 93"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 94 - "Community 94"
Cohesion: 0.09
Nodes (21): utcOffsetText(), Deep, DepthReading, depthReading(), deviceName(), DiveValue, AVG_DEPTH_M, DURATION_SECONDS (+13 more)

### Community 95 - "Community 95"
Cohesion: 0.10
Nodes (13): ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseCns, ApiDiversIdLoadingsGet200ResponseN2, ApiDiversIdLoadingsGet200ResponseNoFly, ApiDiversIdLoadingsGet200ResponseNoFlySource, ApiDiversIdLoadingsGet200ResponseOtu, BodyWeight (+5 more)

### Community 96 - "Community 96"
Cohesion: 0.13
Nodes (34): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+26 more)

### Community 97 - "Community 97"
Cohesion: 0.16
Nodes (11): bottomTime(), planningSac(), PlanningSacDive, PlanningSacResult, PlanningSacSource, default, logbook, BottomTimeToolInput (+3 more)

### Community 98 - "Community 98"
Cohesion: 0.07
Nodes (32): fit-file-parser@^6.1.2, geo-tz@8.1.9, yauzl@^3.4.0, devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1 (+24 more)

### Community 99 - "Community 99"
Cohesion: 0.06
Nodes (33): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0, graphile-worker@^0.18.0 (+25 more)

### Community 100 - "Community 100"
Cohesion: 0.07
Nodes (4): DiveViewRecordingsItemDevice, DiveViewRecordingsItemSummaryGasesItem, DiveViewWaterMismatch, NeighboursViewPrevious

### Community 101 - "Community 101"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 102 - "Community 102"
Cohesion: 0.08
Nodes (23): Tx, AppendUndo, ADR-0020, ADR-0056, ADR-0066, withRevisionHints(), ChangeListener, REVISION_CAUSES (+15 more)

### Community 103 - "Community 103"
Cohesion: 0.08
Nodes (24): LoadingsView, CustomTypeOptions, i18next, initI18n(), resources, ADR-0014, Language, LANGUAGES (+16 more)

### Community 104 - "Community 104"
Cohesion: 0.13
Nodes (30): Logbook(), ImportContext, ImportFilesButton(), ImportList(), ImportNotices(), ImportPanel(), ImportProvider(), ImportRow() (+22 more)

### Community 105 - "Community 105"
Cohesion: 0.15
Nodes (5): Missing(), LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200ResponseMissingItem

### Community 107 - "Community 107"
Cohesion: 0.09
Nodes (7): ApiClientDiagnosticsStatusGet200Response, ApiMeGet200ResponseOwnDiver, ApiToolsBottomTimeGet200ResponseResidualN2, DiverView, DiverViewCertifications, DiverViewCertificationsNewest, DiveViewPosition

### Community 108 - "Community 108"
Cohesion: 0.11
Nodes (22): createDb(), appliedMigrationHead(), findChangedMigrations(), migrateDatabase(), ADR-0004, checkClone(), createE2eDatabase(), E2eDatabase (+14 more)

### Community 109 - "Community 109"
Cohesion: 0.09
Nodes (25): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+17 more)

### Community 110 - "Community 110"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 111 - "Community 111"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 112 - "Community 112"
Cohesion: 0.09
Nodes (28): applyHints(), LiveArrivals, Page, useLiveArrivals(), bump(), changedListeners, ChannelMessage, healthListeners (+20 more)

### Community 113 - "Community 113"
Cohesion: 0.25
Nodes (6): leadAnswer(), EstimateCylinder, PlannedConditions, Suit, leadEstimate(), PersonalizedToolsTest

### Community 114 - "Community 114"
Cohesion: 0.09
Nodes (24): computerEventOf(), ADR-0036, computerEventFromFit(), COMPUTER_EVENT, computerEventFromSuunto(), DECO_MODEL, decoModelFromSuunto, DIVE_MODE (+16 more)

### Community 115 - "Community 115"
Cohesion: 0.10
Nodes (25): date(), FitMessage, FitMessages, GARMIN_PRODUCTS, num(), position(), positive(), RECORD_CHANNELS (+17 more)

### Community 116 - "Community 116"
Cohesion: 0.09
Nodes (22): DIVE_FIELDS, FIND_BY, OPERATIONS, OutgoingDive, SignInInput, SSI_CAPABILITIES, CASES, ContractHarness (+14 more)

### Community 117 - "Community 117"
Cohesion: 0.10
Nodes (23): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearPushRuns(), clearQueue() (+15 more)

### Community 118 - "Community 118"
Cohesion: 0.11
Nodes (4): FakeCache, FakeInstances, FakeTokens, MainViewModelTest

### Community 119 - "Community 119"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 120 - "Community 120"
Cohesion: 0.08
Nodes (22): headers, ADR-0035, aiAccessReady(), askMcp(), cancelWaitingImports(), createAiAccess(), putAside(), waitingImport() (+14 more)

### Community 121 - "Community 121"
Cohesion: 0.11
Nodes (6): ApiClientDiagnosticsPostRequest, ApiClientDiagnosticsPostRequestClient, ApiClientDiagnosticsPostRequestEntriesItem, DiagnosticHygiene, Diagnostics, Failed

### Community 122 - "Community 122"
Cohesion: 0.12
Nodes (7): LogbookPaging, sketchScale(), json(), LogbookPagingTest, LogbookRepositoryTest, RepositoryHarness, SketchTest

### Community 123 - "Community 123"
Cohesion: 0.07
Nodes (22): account, accountRelations, rateLimit, session, sessionRelations, ADR-0011, ADR-0012, ADR-0035 (+14 more)

### Community 124 - "Community 124"
Cohesion: 0.16
Nodes (25): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+17 more)

### Community 125 - "Community 125"
Cohesion: 0.15
Nodes (25): DiveOrigin, REFERENCE_PREFIX, Series, SsiLogbook, aboveZero(), cylinderOf(), deviceOf(), deviceOfRef() (+17 more)

### Community 126 - "Community 126"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 128 - "Community 128"
Cohesion: 0.21
Nodes (4): GasPlanScreenshotTest, GasPlanToolDiver, GasPlanToolInput, GasPlanToolLevel

### Community 129 - "Community 129"
Cohesion: 0.16
Nodes (7): InstanceProbeTest, json(), probe(), ProbeCache, ProbeInstances, probeRepository(), ProbeTokens

### Community 130 - "Community 130"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 131 - "Community 131"
Cohesion: 0.11
Nodes (24): DiveGear, ensureItemForDevice(), GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow, ItemView (+16 more)

### Community 132 - "Community 132"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 133 - "Community 133"
Cohesion: 0.09
Nodes (4): DiveHubApplication, SyncWorker, WorkManagerPeriodicSync, PeriodicSyncScheduler

### Community 134 - "Community 134"
Cohesion: 0.13
Nodes (10): drawBand(), ProfileSketch(), AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint, SketchProfile (+2 more)

### Community 136 - "Community 136"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 137 - "Community 137"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 138 - "Community 138"
Cohesion: 0.12
Nodes (7): LeadAnswer, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseLeftOut, ApiDiversIdLeadEstimateGet200ResponseRange, ApiDiversIdLeadEstimateGet200ResponseReserve, ApiDiversIdLeadEstimateGet200ResponseWeightCheck

### Community 140 - "Community 140"
Cohesion: 0.10
Nodes (22): AI_ACCESS_NAME_MAX, aiAccessLog, aiAccessSetting, Access, AccessView, LogEntryView, Scope, STATUS (+14 more)

### Community 141 - "Community 141"
Cohesion: 0.12
Nodes (20): BOM, Json, num(), obj(), positive(), scaled(), suuntoFitKey(), time() (+12 more)

### Community 142 - "Community 142"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 144 - "Community 144"
Cohesion: 0.15
Nodes (15): LeadInputTest, CylinderMaterial, aluminium, carbon, steel, SuitType, drysuit, none (+7 more)

### Community 145 - "Community 145"
Cohesion: 0.13
Nodes (6): ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting

### Community 146 - "Community 146"
Cohesion: 0.13
Nodes (6): DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, WaitingPushView

### Community 147 - "Community 147"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 148 - "Community 148"
Cohesion: 0.16
Nodes (7): Connected, Hint, HintFrame, LiveEvent, LiveHint, SseLiveHints, LiveHintsTest

### Community 149 - "Community 149"
Cohesion: 0.11
Nodes (20): PARTICIPANT_ROLES, SUGGESTION_STATUSES, DiveProfileView, DiveProfileViewSchema, ADR-0041, AcceptBody, STATUS, SuggestionRouteDeps (+12 more)

### Community 150 - "Community 150"
Cohesion: 0.14
Nodes (18): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+10 more)

### Community 151 - "Community 151"
Cohesion: 0.24
Nodes (19): ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres(), isHypoxic(), metresPerBar() (+11 more)

### Community 152 - "Community 152"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 157 - "Community 157"
Cohesion: 0.12
Nodes (18): CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043, CreateBody (+10 more)

### Community 158 - "Community 158"
Cohesion: 0.12
Nodes (16): Cylinder, DiveView, RevisionView, ADR-0045, upload(), FIT_EPOCH_MS, makeSuuntoAppFit(), makeSuuntoJson() (+8 more)

### Community 159 - "Community 159"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 160 - "Community 160"
Cohesion: 0.15
Nodes (5): ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits

### Community 161 - "Community 161"
Cohesion: 0.15
Nodes (5): ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport

### Community 162 - "Community 162"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 163 - "Community 163"
Cohesion: 0.15
Nodes (5): SiteViewExternalIdsItem, SiteViewExternalIdsItemAttribution, SiteViewExternalIdsItemOffered, SiteViewExternalIdsItemOfferedPosition, SiteViewExternalIdsItemPrivateTo

### Community 164 - "Community 164"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource/poppins@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 165 - "Community 165"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 166 - "Community 166"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 167 - "Community 167"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 168 - "Community 168"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 170 - "Community 170"
Cohesion: 0.18
Nodes (3): FakeInstances, FakeTokens, SyncAuthTest

### Community 171 - "Community 171"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 172 - "Community 172"
Cohesion: 0.18
Nodes (15): RecordingSummary, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput, round3() (+7 more)

### Community 173 - "Community 173"
Cohesion: 0.14
Nodes (14): AL80, EstimateDiveCylinder, LEAD_CASES, leadInput(), LeadVectorInput, planOf(), STEEL_12, ADR-0031 (+6 more)

### Community 174 - "Community 174"
Cohesion: 0.20
Nodes (16): asNumber(), asText(), buddyIdsOf(), buildUpdateRecord(), compareReadBack(), datasetLength(), keysOf(), describe() (+8 more)

### Community 175 - "Community 175"
Cohesion: 0.15
Nodes (10): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, ADR-0068 (+2 more)

### Community 176 - "Community 176"
Cohesion: 0.17
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 178 - "Community 178"
Cohesion: 0.19
Nodes (4): DiveDetailsListView, DiveDetailsListViewCounts, DiveDetailsListViewMonthsItem, DiveDetailsListViewTotals

### Community 179 - "Community 179"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 180 - "Community 180"
Cohesion: 0.15
Nodes (15): equipmentItem, serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, DueEntry, ScheduleView, equipmentList() (+7 more)

### Community 181 - "Community 181"
Cohesion: 0.19
Nodes (13): asEstimateInput(), evaluateLeadVectorCase(), LEAD_VECTOR_KERNEL, leadVectorFile(), VECTOR_KERNELS, VectorFile, vectorFile(), assertMatch() (+5 more)

### Community 182 - "Community 182"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 183 - "Community 183"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 184 - "Community 184"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 186 - "Community 186"
Cohesion: 0.26
Nodes (14): jsRound(), powInt(), roundTo(), Sac, SacCylinder, SacDive, SacMissing, cylinder_incomplete (+6 more)

### Community 188 - "Community 188"
Cohesion: 0.22
Nodes (12): ADR-0076, ClientVersionRegistry, MIN_CLIENT_VERSIONS, MinClientVersion, ADR-0076, compareIdentifier(), compareNumeric(), compareSemanticVersions() (+4 more)

### Community 189 - "Community 189"
Cohesion: 0.13
Nodes (10): EGYPT, ExternalId, HOUSE_REEF, Method, Position, Revision, Site, SiteImport (+2 more)

### Community 190 - "Community 190"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 191 - "Community 191"
Cohesion: 0.25
Nodes (11): supportBundlePreviewQuery(), backupDownloadHref(), formatBytes(), isBackupActive(), isBackupDownloadable(), ScheduleProblem, scheduleProblem(), SUPPORT_BUNDLE_DOWNLOAD_HREF (+3 more)

### Community 192 - "Community 192"
Cohesion: 0.13
Nodes (6): FakeBroadcastChannel, FakeEventSource, Listener, locks, start(), ADR-0066

### Community 193 - "Community 193"
Cohesion: 0.13
Nodes (14): concurrency, cancel-in-progress, group, env, RELEASE_TAG, jobs, publish, name (+6 more)

### Community 196 - "Community 196"
Cohesion: 0.29
Nodes (14): managementOf(), createEquipmentService(), assemble(), detailOf(), lastCylinderOf(), managedItem(), managedRecord(), managedSchedule() (+6 more)

### Community 197 - "Community 197"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 198 - "Community 198"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 199 - "Community 199"
Cohesion: 0.17
Nodes (10): EstimateInput, AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031 (+2 more)

### Community 200 - "Community 200"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 203 - "Community 203"
Cohesion: 0.26
Nodes (3): ApiDiveSitesGet200Response, SiteView, SiteViewPosition

### Community 204 - "Community 204"
Cohesion: 0.26
Nodes (3): DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewCylindersItemSeries

### Community 205 - "Community 205"
Cohesion: 0.26
Nodes (3): DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 206 - "Community 206"
Cohesion: 0.18
Nodes (10): EquipmentError, ADR-0034, ADR-0053, checkCost(), checkDates(), checkWindow(), checkResets(), checkSchedule() (+2 more)

### Community 207 - "Community 207"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 209 - "Community 209"
Cohesion: 0.20
Nodes (10): WeightingFelt, WeightingFeedback, LeadVectorDive, Confidence, EstimateAdjustment, EstimateBasis, EstimateDive, EstimateMissing (+2 more)

### Community 210 - "Community 210"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 211 - "Community 211"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 212 - "Community 212"
Cohesion: 0.20
Nodes (10): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, ADR-0076, MODULE_IMPORTS, moduleRules (+2 more)

### Community 213 - "Community 213"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 214 - "Community 214"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 217 - "Community 217"
Cohesion: 0.20
Nodes (5): FileFormat, FitAdapter, SuuntoJsonAdapter, UddfAdapter, ParsedRecording

### Community 218 - "Community 218"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 219 - "Community 219"
Cohesion: 0.20
Nodes (10): verify, prerelease, sha, tag, versionCode, versionName, name, outputs (+2 more)

### Community 220 - "Community 220"
Cohesion: 0.20
Nodes (10): with, cache, distribution, fetch-depth, if-no-files-found, java-version, name, path (+2 more)

### Community 221 - "Community 221"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 223 - "Community 223"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 224 - "Community 224"
Cohesion: 0.31
Nodes (8): diverBodyWeight, weightingFeedbackOf(), createLeadService(), DiveReference, LeadEstimateAnswer, leadEstimateOf(), ADR-0031, LeadEstimate

### Community 225 - "Community 225"
Cohesion: 0.28
Nodes (7): ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, s(), ADR-0035

### Community 226 - "Community 226"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 227 - "Community 227"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 228 - "Community 228"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 232 - "Community 232"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 233 - "Community 233"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 235 - "Community 235"
Cohesion: 0.29
Nodes (6): itemIds(), ADR-0026, ADR-0034, Usage, UsageDive, UsageRow

### Community 236 - "Community 236"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 237 - "Community 237"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 238 - "Community 238"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 239 - "Community 239"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 240 - "Community 240"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 241 - "Community 241"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build

### Community 242 - "Community 242"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 243 - "Community 243"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 247 - "Community 247"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 248 - "Community 248"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 249 - "Community 249"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1303 isolated node(s):** `Sent`, `Reserve`, `RockBottom`, `Reachable`, `Reachable` (+1298 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3689 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **151 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `DiveStore` connect `Community 47` to `Community 64`, `Community 33`, `Community 26`, `Community 39`, `Community 74`, `Community 170`, `Community 45`, `Community 77`, `Community 79`, `Community 51`, `Community 55`, `Community 87`, `Community 23`, `Community 154`, `Community 187`, `Community 62`?**
  _High betweenness centrality (0.019) - this node is a cross-community bridge._
- **What connects `Sent`, `Reserve`, `RockBottom` to the rest of the system?**
  _1303 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.02648605577689243 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 1` to `Community 192`, `Community 2`, `Community 3`, `Community 164`, `Community 103`, `Community 40`, `Community 9`, `Community 10`, `Community 104`, `Community 14`, `Community 111`, `Community 112`, `Community 20`, `Community 22`, `Community 57`, `Community 191`?**
  _High betweenness centrality (0.019) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.02336630854873595 - nodes in this community are weakly interconnected._
- **Why does `drizzle-orm` connect `Community 0` to `Community 131`, `Community 4`, `Community 5`, `Community 6`, `Community 7`, `Community 8`, `Community 11`, `Community 12`, `Community 140`, `Community 13`, `Community 15`, `Community 18`, `Community 19`, `Community 24`, `Community 25`, `Community 27`, `Community 28`, `Community 29`, `Community 32`, `Community 35`, `Community 37`, `Community 38`, `Community 42`, `Community 46`, `Community 48`, `Community 180`, `Community 58`, `Community 84`, `Community 224`, `Community 98`, `Community 99`, `Community 235`, `Community 108`, `Community 123`?**
  _High betweenness centrality (0.014) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.021240225495544645 - nodes in this community are weakly interconnected._