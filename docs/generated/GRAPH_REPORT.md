# Graph Report - dive_hub  (2026-11-21)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 11119 nodes · 33680 edges · 373 communities (221 shown, 152 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 630 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `7d357664`
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
- Community 242
- Community 243
- Community 244
- Community 245
- Community 246
- Community 247
- Community 248
- Community 249
- Community 250
- Community 255

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
- `Removal` --references--> `ProviderServiceError`  [EXTRACTED]
  apps/server/src/providers/push/push-service.ts → apps/server/src/providers/registry.ts
- `SsiLogbookSite` --references--> `SiteWaterType`  [EXTRACTED]
  apps/server/src/providers/ssi/ssi-client.ts → apps/server/src/vocabulary.ts

## Import Cycles
- None detected.

## Communities (373 total, 152 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.04
Nodes (221): ChangePassword(), DisplaySettings(), SecuritySettings(), Sessions(), ADR-0080, useDescribeAgent(), AdminSettings(), ClientDiagnosticsSetting() (+213 more)

### Community 1 - "Community 1"
Cohesion: 0.03
Nodes (161): user, diver, diveSiteExternalId, lead, revision, createSsiAdapter(), createSsiClient(), ssiSiteRow() (+153 more)

### Community 2 - "Community 2"
Cohesion: 0.02
Nodes (200): cylinderCatalogueQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas (+192 more)

### Community 3 - "Community 3"
Cohesion: 0.02
Nodes (167): Db, ComputerChoice, DiveImportMode, DiveKind, diveSite, importOriginal, ParticipantRole, ProviderCylinder (+159 more)

### Community 4 - "Community 4"
Cohesion: 0.02
Nodes (152): divesQuery(), DiveSummary, LogbookPage, PAGE_SIZE, equipmentDueQuery(), siteQuery(), cameraAtStart(), cameraFailure() (+144 more)

### Community 5 - "Community 5"
Cohesion: 0.02
Nodes (152): aiAccessSettingQuery(), BackupRunView, BackupSettingsView, SiteDataView, SupportBundlePreviewView, ADR-0030, clientDiagnosticsSettingQuery(), ClientDiagnosticsSettingsView (+144 more)

### Community 6 - "Community 6"
Cohesion: 0.02
Nodes (130): connection, cylinder, device, dive, duplicateCandidate, ImportErrorCode, ImportFound, importJob (+122 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (146): continuationAppend, LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, renumber(), renumberStatement(), ADR-0048, AppendRow (+138 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (134): BACKUP_EXTENSION, BACKUP_FORMAT_VERSION, BACKUP_SKIPPED_DIRS, BackupArchiveError, BackupDecryptionKey, BackupEncryption, BackupEncryptionMetadata, BackupPart (+126 more)

### Community 9 - "Community 9"
Cohesion: 0.02
Nodes (127): diveAssessment, recordingEvent, Assessment, FindingView, Row, ADR-0036, upload(), Accepted (+119 more)

### Community 10 - "Community 10"
Cohesion: 0.02
Nodes (129): CentreService, CentreRouteDeps, LEAD_PLACEMENTS, WEIGHTING_FELT, DiverService, ADR-0025, WaterMismatch, waterMismatch() (+121 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (140): allButDive(), AnyLogbookCheck, CandidateView, continuationCandidateQuery(), ContinuationCandidateView, deletedDivesOn(), DeletedDivesPageView, deletedDivesQuery() (+132 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (126): ReportProblemSettings(), Me, backupSettingsQuery(), backupsQuery(), supportBundlePreviewQuery(), devicesQuery(), diversQuery(), BuddySuggestionPreviewView (+118 more)

### Community 13 - "Community 13"
Cohesion: 0.02
Nodes (133): accountListEntry, actorType, aiAccessOutcome, backupRunKind, backupRunStatus, buddySuggestion, candidateResolution, centreSource (+125 more)

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (130): ConnectionRow, WaitingPushOptions, externalDiver(), mergeExternalDiver(), participations(), waitingOf(), open(), close() (+122 more)

### Community 15 - "Community 15"
Cohesion: 0.04
Nodes (114): uplot@^1.6.32, assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider() (+106 more)

### Community 16 - "Community 16"
Cohesion: 0.03
Nodes (119): BuddyView, ConflictChoices, ConflictFieldView, DiveImportMode, DiveImportPreview, diveImportPreviewQuery(), DiveKind, diveTimesQuery() (+111 more)

### Community 17 - "Community 17"
Cohesion: 0.04
Nodes (101): AcceptedBuddySuggestionView, BuddySuggestionOverlapView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView, candidatesQuery(), deletedDivesTotal(), isDiveCheck() (+93 more)

### Community 18 - "Community 18"
Cohesion: 0.04
Nodes (13): AppContainer, PreferencesInstanceUrlStore, MainActivity, MainViewModel, Unreachable, followStoreWrites(), SettingsViewModel, LoadingsUiState (+5 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (93): Tx, ProviderImportPlan, providerSiteData, SiteImportFinding, SiteImportProgress, AppendUndo, RemoteSite, findOrMake() (+85 more)

### Community 20 - "Community 20"
Cohesion: 0.04
Nodes (22): DiveViewRecordingsItem, DiveViewRecordingsItemSummary, profileRecording(), DiveStore, toBoolean(), toLong(), toModel(), BodyWeight (+14 more)

### Community 21 - "Community 21"
Cohesion: 0.04
Nodes (89): Queryable, logbookCheckAnswer, logbookCheckDiveAnswer, ADR-0016, keepApart(), keepDive(), keepRestored(), ADR-0038 (+81 more)

### Community 22 - "Community 22"
Cohesion: 0.05
Nodes (24): DiveRow(), EmptyState(), FactRow(), Section(), label(), SettingsDebugRoute(), SettingsDebugScreen(), Action() (+16 more)

### Community 23 - "Community 23"
Cohesion: 0.03
Nodes (83): equipmentUse, managedDiverIds(), managesDiveDiver(), CandidateError, createCandidateService(), lockMine(), Participant, asksFor() (+75 more)

### Community 24 - "Community 24"
Cohesion: 0.04
Nodes (91): @modelcontextprotocol/server@^2.3.1, AiAccessRouteDeps, AccessRefusal, AiAccessScope, AiAccessService, seesPositions(), VerifiedAccess, mcpEndpoint() (+83 more)

### Community 25 - "Community 25"
Cohesion: 0.04
Nodes (87): replyCertificationError, STATUS, CertificationService, CYLINDER_MATERIALS, DIVE_IMPORT_MODES, UTC_OFFSET_SOURCES, AssessmentRefresh, refreshAssessmentsAfterChange() (+79 more)

### Community 26 - "Community 26"
Cohesion: 0.04
Nodes (78): hashPassword(), invitation, passwordReset, userRole, AttemptLimit, clientKey(), limitAttempts(), ADR-0061 (+70 more)

### Community 27 - "Community 27"
Cohesion: 0.05
Nodes (43): cylinderLine(), cylinderPressures(), Cylinders(), Direction, Back, Left, Right, DirectionIcon() (+35 more)

### Community 28 - "Community 28"
Cohesion: 0.05
Nodes (22): DiveListView, DiveListViewCounts, DiveListViewDivesItem, DiveListViewDivesItemGasesItem, DiveListViewDivesItemParticipantsItem, DiveListViewDivesItemSite, DiveListViewMonthsItem, DiveListViewTotals (+14 more)

### Community 29 - "Community 29"
Cohesion: 0.04
Nodes (71): createServer(), createAuth(), loadAuthSecret(), acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS (+63 more)

### Community 30 - "Community 30"
Cohesion: 0.07
Nodes (66): Formats, formatSyncTime(), GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult() (+58 more)

### Community 31 - "Community 31"
Cohesion: 0.04
Nodes (16): AndroidDiveStore, SettingsDiagnosticsState, SettingsUiState, FakeDiagnostics, FakeInstances, SettingsRouteTest, SettingsViewModelTest, ReportProblemResult (+8 more)

### Community 32 - "Community 32"
Cohesion: 0.04
Nodes (48): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+40 more)

### Community 33 - "Community 33"
Cohesion: 0.03
Nodes (83): AssessmentService, AssessmentView, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView() (+75 more)

### Community 34 - "Community 34"
Cohesion: 0.03
Nodes (65): diveLease, providerConflict, providerLease, push, pushRun, pushRunItem, waitingPush, kept() (+57 more)

### Community 35 - "Community 35"
Cohesion: 0.08
Nodes (8): FakeCache, RevisionFollowTest, LeadEstimateViewModelTest, ErrorCodes, Dive, SyncState, manySites(), siteJson()

### Community 36 - "Community 36"
Cohesion: 0.07
Nodes (78): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, assessmentRoutes() (+70 more)

### Community 37 - "Community 37"
Cohesion: 0.03
Nodes (74): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+66 more)

### Community 38 - "Community 38"
Cohesion: 0.06
Nodes (84): cylindersOf(), mergeProviderCylinder(), providerCylinderOf(), participantsOf(), columnsOf(), leadTotalKg(), kindOfMode(), deviceDiver() (+76 more)

### Community 39 - "Community 39"
Cohesion: 0.04
Nodes (20): DiveDetailViewModel, LogbookViewModel, DiveRepository, neighboursPath(), countOf(), filterChips(), LogbookFilter, CHANGED_SINCE_SENT (+12 more)

### Community 40 - "Community 40"
Cohesion: 0.04
Nodes (65): CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite(), lock() (+57 more)

### Community 41 - "Community 41"
Cohesion: 0.06
Nodes (68): ANDROID_UI, AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), clearFailed(), clearPassed() (+60 more)

### Community 42 - "Community 42"
Cohesion: 0.05
Nodes (21): ApiMeGet200Response, ApiMeGet200ResponsePreferences, SignInEmailRequest, UserView, InstanceUrl, HealthView, InstanceProbeResult, Loading (+13 more)

### Community 43 - "Community 43"
Cohesion: 0.05
Nodes (49): auth, BackupServiceDeps, createServices(), ServiceParts, Services, ADR-0027, ADR-0030, ADR-0061 (+41 more)

### Community 44 - "Community 44"
Cohesion: 0.06
Nodes (54): ImportArea, looksLikeZip(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy() (+46 more)

### Community 45 - "Community 45"
Cohesion: 0.06
Nodes (45): appliedMigrationHead(), BufferedLog, LOG_BUFFER_CAPACITY_BYTES, LOG_BUFFER_CAPACITY_ENTRIES, LogBufferStats, LogRingBuffer, LogRingBufferOptions, DENIED_KEY_PARTS (+37 more)

### Community 46 - "Community 46"
Cohesion: 0.05
Nodes (15): ApiClientDiagnosticsStatusGet200Response, ApiMeGet200ResponseOwnDiver, DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, DiveDetailsListView (+7 more)

### Community 47 - "Community 47"
Cohesion: 0.05
Nodes (56): PARTICIPANT_ROLES, SUGGESTION_STATUSES, DIVE_ONLY_PATTERN, DiveProfileView, DiveProfileViewSchema, ADR-0041, ADR-0053, ADR-0056 (+48 more)

### Community 48 - "Community 48"
Cohesion: 0.09
Nodes (47): BottomTime, BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen, BottomTimeLimits (+39 more)

### Community 49 - "Community 49"
Cohesion: 0.07
Nodes (5): CountingDispatcher, LogbookViewModelTest, ApiClient, ApiErrorBody, ApiClientTest

### Community 50 - "Community 50"
Cohesion: 0.07
Nodes (61): Answer, answer(), baseFrom(), BodyWeight, Confidence, high, low, medium (+53 more)

### Community 51 - "Community 51"
Cohesion: 0.10
Nodes (28): LiveHints, Body, Hold, Offline, RefreshData, refreshDeletedJson(), refreshDetailJson(), refreshDetailsPage() (+20 more)

### Community 52 - "Community 52"
Cohesion: 0.06
Nodes (50): RecordingSummary, RecordingFacts, date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num() (+42 more)

### Community 53 - "Community 53"
Cohesion: 0.07
Nodes (13): LeadAnswer, LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseBasis, ApiDiversIdLeadEstimateGet200ResponseBasisDivesItem (+5 more)

### Community 54 - "Community 54"
Cohesion: 0.07
Nodes (23): UiState, AppNavHost(), AppScreens, clearSavedTabStates(), isShowing(), leaveDiveForLogbook(), MainShell(), navigateToTab() (+15 more)

### Community 55 - "Community 55"
Cohesion: 0.09
Nodes (12): ApiDiverBodyWeightsGet200ResponseItem, SyncCheckpoint, Complete, SessionEnded, Stopped, SyncEngine, SyncPhase, Details (+4 more)

### Community 56 - "Community 56"
Cohesion: 0.07
Nodes (49): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+41 more)

### Community 57 - "Community 57"
Cohesion: 0.06
Nodes (36): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+28 more)

### Community 58 - "Community 58"
Cohesion: 0.06
Nodes (49): EMPTY_SITE_IMPORT_COUNTS, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, distanceM() (+41 more)

### Community 59 - "Community 59"
Cohesion: 0.05
Nodes (45): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError (+37 more)

### Community 60 - "Community 60"
Cohesion: 0.08
Nodes (23): AvatarCircle(), DiveRow(), Error(), Fact(), FilterAction(), Filters(), FilterSheet(), Loading() (+15 more)

### Community 61 - "Community 61"
Cohesion: 0.05
Nodes (49): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, conflictForLena() (+41 more)

### Community 62 - "Community 62"
Cohesion: 0.12
Nodes (3): DiveRoute, AppNavHostTest, FakeScreens

### Community 63 - "Community 63"
Cohesion: 0.13
Nodes (22): Body, BrokenBody, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage(), diverJson() (+14 more)

### Community 64 - "Community 64"
Cohesion: 0.05
Nodes (49): EquipmentService, RecordInput, ScheduleInput, DiveGearItemViewSchema, DiveGearViewSchema, DueState, EquipmentDetailViewSchema, EquipmentDueViewSchema (+41 more)

### Community 65 - "Community 65"
Cohesion: 0.09
Nodes (23): DiveViewRecordingsItemSummarySurfaceBreaksItem, RecordingSamplesView, RecordingSamplesViewSeriesItem, computerRecordings(), depthSeries(), nearest(), nearestIndex(), partsOf() (+15 more)

### Community 66 - "Community 66"
Cohesion: 0.08
Nodes (42): SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), buildUpdateRecord(), DiveForSsi, emptyRecord(), writableRecord() (+34 more)

### Community 67 - "Community 67"
Cohesion: 0.10
Nodes (42): BottomTime, bottomTime(), ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres() (+34 more)

### Community 68 - "Community 68"
Cohesion: 0.06
Nodes (37): DiveExportAction, buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open(), saveAndReadBack() (+29 more)

### Community 69 - "Community 69"
Cohesion: 0.08
Nodes (17): DiveHubApp(), DiveHubScreens, ErrorText(), Gate(), InstanceForm(), InstanceStatus(), LoadingScreen(), PasswordField() (+9 more)

### Community 70 - "Community 70"
Cohesion: 0.07
Nodes (35): diveWith(), ADR-0028, ADR-0043, buddySite(), Dive, OTHER, resetSource(), ADR-0012 (+27 more)

### Community 71 - "Community 71"
Cohesion: 0.08
Nodes (43): SOURCES, NoFly, DEFAULT_GF_HIGH, residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows(), DiveRow (+35 more)

### Community 72 - "Community 72"
Cohesion: 0.05
Nodes (40): SUIT_TYPES, SUIT_UNDERGARMENTS, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053, replyDiverError (+32 more)

### Community 73 - "Community 73"
Cohesion: 0.09
Nodes (36): BottomTimeQuery, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033, ADR-0071, DEFAULT_PPO2 (+28 more)

### Community 74 - "Community 74"
Cohesion: 0.18
Nodes (3): DiveDetailScreenshotTest, NeighboursView, NeighboursViewNext

### Community 75 - "Community 75"
Cohesion: 0.11
Nodes (8): FakeCache, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore, UserScopedStore

### Community 76 - "Community 76"
Cohesion: 0.06
Nodes (42): downsampleMinMax(), ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, seriesOf(), minute() (+34 more)

### Community 77 - "Community 77"
Cohesion: 0.08
Nodes (39): DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining(), ssiDiveType(), ssiInteger() (+31 more)

### Community 78 - "Community 78"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 79 - "Community 79"
Cohesion: 0.11
Nodes (14): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), Answers (+6 more)

### Community 81 - "Community 81"
Cohesion: 0.10
Nodes (38): tidyName(), DiverDetails, Caller, foldName(), nameDiffers(), Buddy, createBuddyService(), createFrom() (+30 more)

### Community 82 - "Community 82"
Cohesion: 0.07
Nodes (37): Change(), Changes(), historyRoute, Made(), PositionSchema, AdoptBody, Country, CreateBody (+29 more)

### Community 83 - "Community 83"
Cohesion: 0.07
Nodes (37): BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, Conditions, GasMix, ASCENT_RATE_M_PER_MIN (+29 more)

### Community 84 - "Community 84"
Cohesion: 0.08
Nodes (26): captureDiagnostic(), captureError(), cleanContext(), clearDiagnostics(), CLIENT_NAME, CLIENT_VERSION, DiagnosticEntry, diagnosticFromError() (+18 more)

### Community 85 - "Community 85"
Cohesion: 0.08
Nodes (28): timeZoneText(), diveLocalTime(), offsetId(), timeZoneNote(), TimeZoneOrigin, DEVICE, NEARBY, POSITION (+20 more)

### Community 86 - "Community 86"
Cohesion: 0.05
Nodes (37): AREA_OF, areaOf(), areas, capture(), findings, headers, ADR-0020, ADR-0021 (+29 more)

### Community 87 - "Community 87"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 88 - "Community 88"
Cohesion: 0.08
Nodes (5): logShareChooser(), DiveHubApplicationTest, SplashTheme, SplashThemeTest, LogShareTest

### Community 89 - "Community 89"
Cohesion: 0.13
Nodes (13): bottomTime(), otu(), oxygenLimitMinutes(), planningSac(), PlanningSacDive, PlanningSacResult, PlanningSacSource, default (+5 more)

### Community 90 - "Community 90"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 91 - "Community 91"
Cohesion: 0.07
Nodes (5): DiveViewFromRecording, DiveViewFromRecordingStartsAt, DiveViewRecordingsItemDevice, DiveViewSac, DiveViewWaterMismatch

### Community 93 - "Community 93"
Cohesion: 0.11
Nodes (11): ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseCns, ApiDiversIdLoadingsGet200ResponseN2, ApiDiversIdLoadingsGet200ResponseNoFly, ApiDiversIdLoadingsGet200ResponseNoFlySource, ApiDiversIdLoadingsGet200ResponseOtu, cnsDecayed() (+3 more)

### Community 94 - "Community 94"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 95 - "Community 95"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 96 - "Community 96"
Cohesion: 0.08
Nodes (34): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+26 more)

### Community 97 - "Community 97"
Cohesion: 0.08
Nodes (29): WeightingFelt, right, too_heavy, too_light, dayNumber(), enteredDecoFromNdl(), minNdlSeconds(), NoFly (+21 more)

### Community 98 - "Community 98"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 99 - "Community 99"
Cohesion: 0.08
Nodes (34): participantsOfDives(), buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf() (+26 more)

### Community 100 - "Community 100"
Cohesion: 0.13
Nodes (34): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+26 more)

### Community 101 - "Community 101"
Cohesion: 0.09
Nodes (27): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+19 more)

### Community 102 - "Community 102"
Cohesion: 0.10
Nodes (12): drawBand(), ProfileSketch(), AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint, SketchProfile (+4 more)

### Community 104 - "Community 104"
Cohesion: 0.12
Nodes (27): bar(), celsius(), contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf(), firstOf() (+19 more)

### Community 105 - "Community 105"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 106 - "Community 106"
Cohesion: 0.10
Nodes (23): diverLoadingsQuery(), DiverView, CurrentLoadings(), ADR-0036, ADR-0048, ADR-0071, CustomTypeOptions, i18next (+15 more)

### Community 107 - "Community 107"
Cohesion: 0.09
Nodes (8): ApiDiveSitesGet200Response, SiteView, SiteViewExternalIdsItem, SiteViewExternalIdsItemAttribution, SiteViewExternalIdsItemOffered, SiteViewExternalIdsItemOfferedPosition, SiteViewExternalIdsItemPrivateTo, SiteViewPosition

### Community 108 - "Community 108"
Cohesion: 0.09
Nodes (23): backupTask(), ADR-0069, PUSH_RUN_STEP_TASK, WAITING_PUSHES_TASK, AUTOMATION_TICK_TASK, IMPORT_DIVE_SITES_TASK, idOf(), processImportTask() (+15 more)

### Community 109 - "Community 109"
Cohesion: 0.09
Nodes (25): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+17 more)

### Community 110 - "Community 110"
Cohesion: 0.13
Nodes (29): Logbook(), ImportContext, ImportFilesButton(), ImportList(), ImportNotices(), ImportPanel(), ImportProvider(), ImportRow() (+21 more)

### Community 111 - "Community 111"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 113 - "Community 113"
Cohesion: 0.07
Nodes (25): account, accountRelations, apikey, rateLimit, session, sessionRelations, ADR-0011, ADR-0012 (+17 more)

### Community 114 - "Community 114"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 115 - "Community 115"
Cohesion: 0.28
Nodes (8): leadAnswer(), EstimateCylinder, PlannedConditions, Suit, assemblePersonalizedSources(), suitOf(), leadEstimate(), PersonalizedToolsTest

### Community 116 - "Community 116"
Cohesion: 0.12
Nodes (25): AssessmentError, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), requireManagedDive() (+17 more)

### Community 117 - "Community 117"
Cohesion: 0.09
Nodes (25): SSI_CAPABILITIES, rowOf(), DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject(), MAX_CARD_IMAGE_BYTES, PNG_MAGIC (+17 more)

### Community 118 - "Community 118"
Cohesion: 0.10
Nodes (23): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearPushRuns(), clearQueue() (+15 more)

### Community 119 - "Community 119"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 120 - "Community 120"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 121 - "Community 121"
Cohesion: 0.11
Nodes (20): SsiClient, ClearChecks, NumberChecks, TwowayChecks, apneaDive(), DATASET_KEYS, datasetSummary(), findIn() (+12 more)

### Community 122 - "Community 122"
Cohesion: 0.08
Nodes (22): headers, ADR-0035, aiAccessReady(), askMcp(), cancelWaitingImports(), createAiAccess(), putAside(), waitingImport() (+14 more)

### Community 123 - "Community 123"
Cohesion: 0.11
Nodes (7): ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh, ApiToolsBottomTimeGet200ResponseUsedSacLpm, ApiToolsBottomTimeGet200ResponseUsedWater

### Community 124 - "Community 124"
Cohesion: 0.11
Nodes (7): ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport

### Community 125 - "Community 125"
Cohesion: 0.11
Nodes (8): LogbookPaging, counts(), json(), LogbookFiltersTest, LogbookFormatTest, LogbookPagingTest, LogbookRepositoryTest, RepositoryHarness

### Community 126 - "Community 126"
Cohesion: 0.10
Nodes (24): ENGINE_VERSION, Evidence, FindingValues, NOT_COVERED_DIVE_MODES, RULE_IDS, RuleId, RULES, SEVERITIES (+16 more)

### Community 127 - "Community 127"
Cohesion: 0.10
Nodes (18): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, APP (+10 more)

### Community 128 - "Community 128"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 129 - "Community 129"
Cohesion: 0.21
Nodes (4): GasPlanScreenshotTest, GasPlanToolDiver, GasPlanToolInput, GasPlanToolLevel

### Community 130 - "Community 130"
Cohesion: 0.12
Nodes (6): ApiClientDiagnosticsPostRequest, ApiClientDiagnosticsPostRequestClient, ApiClientDiagnosticsPostRequestEntriesItem, DiagnosticHygiene, Diagnostics, Failed

### Community 131 - "Community 131"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 132 - "Community 132"
Cohesion: 0.11
Nodes (24): DiveGear, ensureItemForDevice(), GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow, ItemView (+16 more)

### Community 133 - "Community 133"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 134 - "Community 134"
Cohesion: 0.09
Nodes (4): DiveHubApplication, SyncWorker, WorkManagerPeriodicSync, PeriodicSyncScheduler

### Community 137 - "Community 137"
Cohesion: 0.13
Nodes (3): FakeInstances, FakeTokens, MainViewModelTest

### Community 138 - "Community 138"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 139 - "Community 139"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 141 - "Community 141"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 143 - "Community 143"
Cohesion: 0.13
Nodes (6): ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting

### Community 144 - "Community 144"
Cohesion: 0.13
Nodes (19): UtcOffsetSource, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput, round3() (+11 more)

### Community 145 - "Community 145"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 146 - "Community 146"
Cohesion: 0.12
Nodes (5): SettingsRepository, InstanceUrlStore, ProbeInstances, FakeInstances, SettingsRepositoryTest

### Community 147 - "Community 147"
Cohesion: 0.16
Nodes (7): Connected, Hint, HintFrame, LiveEvent, LiveHint, SseLiveHints, LiveHintsTest

### Community 148 - "Community 148"
Cohesion: 0.09
Nodes (23): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+15 more)

### Community 149 - "Community 149"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 151 - "Community 151"
Cohesion: 0.12
Nodes (19): CENTRE_SOURCE_NAME, CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+11 more)

### Community 152 - "Community 152"
Cohesion: 0.12
Nodes (19): siteImport, siteImportStatus, SiteImportRouteDeps, StartBody, STATUS, ADR-0021, SiteImportError, SiteImportService (+11 more)

### Community 153 - "Community 153"
Cohesion: 0.13
Nodes (18): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+10 more)

### Community 154 - "Community 154"
Cohesion: 0.13
Nodes (16): compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051, candidatesOf(), ConfirmResult (+8 more)

### Community 155 - "Community 155"
Cohesion: 0.11
Nodes (17): Cylinder, DiveView, RevisionView, ADR-0045, upload(), FIT_EPOCH_MS, makeSuuntoAppFit(), makeSuuntoJson() (+9 more)

### Community 158 - "Community 158"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 160 - "Community 160"
Cohesion: 0.15
Nodes (5): ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits

### Community 161 - "Community 161"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 162 - "Community 162"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 163 - "Community 163"
Cohesion: 0.25
Nodes (19): DiveOrigin, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef(), MATERIAL_OF_TANK_TYPE, nonZero() (+11 more)

### Community 164 - "Community 164"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 165 - "Community 165"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 166 - "Community 166"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 167 - "Community 167"
Cohesion: 0.19
Nodes (11): LeadInputTest, SuitType, drysuit, none, semidry, skin, wetsuit, SuitUndergarment (+3 more)

### Community 168 - "Community 168"
Cohesion: 0.11
Nodes (19): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource/poppins@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+11 more)

### Community 169 - "Community 169"
Cohesion: 0.18
Nodes (14): errorMessage(), formatOf(), createImportService(), cancelImport(), createImport(), endWaiting(), expireWaiting(), openUpload() (+6 more)

### Community 170 - "Community 170"
Cohesion: 0.15
Nodes (13): SsiCallLog, storedList(), tryParseJson(), APP_PARAMS, createSsiTransport(), exchange(), isObject(), SSI_APP_USER_AGENT (+5 more)

### Community 171 - "Community 171"
Cohesion: 0.18
Nodes (3): FakeInstances, FakeTokens, SyncAuthTest

### Community 172 - "Community 172"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 173 - "Community 173"
Cohesion: 0.22
Nodes (15): jsRound(), powInt(), roundTo(), Sac, SacCylinder, SacDive, SacMissing, cylinder_incomplete (+7 more)

### Community 174 - "Community 174"
Cohesion: 0.12
Nodes (14): clientDiagnostic, clientDiagnosticsSettings, ClientDiagnosticsEntryInput, ClientDiagnosticsEntryView, ClientDiagnosticsError, ClientDiagnosticsUpload, KEEP_NEWEST, MAX_UPLOAD_ENTRIES (+6 more)

### Community 175 - "Community 175"
Cohesion: 0.13
Nodes (14): AL80, asEstimateInput(), EstimateDiveCylinder, LEAD_CASES, leadInput(), LeadVectorInput, planOf(), STEEL_12 (+6 more)

### Community 176 - "Community 176"
Cohesion: 0.17
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 180 - "Community 180"
Cohesion: 0.19
Nodes (4): ApiToolsBottomTimeGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseLimits, ApiToolsBottomTimeGet200ResponseResidualN2

### Community 181 - "Community 181"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 182 - "Community 182"
Cohesion: 0.15
Nodes (14): certification, PROBLEMS, createUserAdminService(), keepAnAdmin(), lock(), deleteUserData(), ADR-0013, ADR-0024 (+6 more)

### Community 183 - "Community 183"
Cohesion: 0.15
Nodes (15): equipmentItem, serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, DueEntry, ScheduleView, equipmentList() (+7 more)

### Community 184 - "Community 184"
Cohesion: 0.15
Nodes (15): SuitType, SuitUndergarment, WeightingFelt, Suit, WeightingFeedback, LeadEstimateAnswer, LeadVectorDive, Confidence (+7 more)

### Community 185 - "Community 185"
Cohesion: 0.23
Nodes (13): clientRequestSerializer(), ADR-0076, compareIdentifier(), compareNumeric(), compareSemanticVersions(), parseAndroidUserAgent(), parseSemanticVersion(), SemanticVersion (+5 more)

### Community 186 - "Community 186"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 187 - "Community 187"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 188 - "Community 188"
Cohesion: 0.18
Nodes (5): AppTab, Logbook, Settings, Tools, DiveHubBottomBar()

### Community 190 - "Community 190"
Cohesion: 0.21
Nodes (12): evaluateLeadVectorCase(), LEAD_VECTOR_KERNEL, leadVectorFile(), VECTOR_KERNELS, VectorFile, vectorFile(), assertMatch(), checkVectors() (+4 more)

### Community 191 - "Community 191"
Cohesion: 0.13
Nodes (10): EGYPT, ExternalId, HOUSE_REEF, Method, Position, Revision, Site, SiteImport (+2 more)

### Community 192 - "Community 192"
Cohesion: 0.13
Nodes (14): concurrency, cancel-in-progress, group, env, RELEASE_TAG, jobs, publish, name (+6 more)

### Community 193 - "Community 193"
Cohesion: 0.27
Nodes (13): Estimate, LoadingBasis, LoadingDive, N2Loadings, N2UnavailableReason, no_profile, no_recent_dive, stale (+5 more)

### Community 194 - "Community 194"
Cohesion: 0.18
Nodes (12): aiAccessLog, aiAccessSetting, AiAccessError, KeyRow, LOG_PAGE_MAX, LoggedCall, permissionOf(), toAccess() (+4 more)

### Community 195 - "Community 195"
Cohesion: 0.20
Nodes (10): DIVE_KINDS, ImportView, ImportViewSchema, toImportView(), ADR-0030, ImportRouteDeps, ADR-0044, ADR-0056 (+2 more)

### Community 196 - "Community 196"
Cohesion: 0.29
Nodes (14): managementOf(), createEquipmentService(), assemble(), detailOf(), lastCylinderOf(), managedItem(), managedRecord(), managedSchedule() (+6 more)

### Community 197 - "Community 197"
Cohesion: 0.24
Nodes (13): asNumber(), asText(), compareReadBack(), datasetLength(), describe(), isObject(), keyDifferences(), pause() (+5 more)

### Community 198 - "Community 198"
Cohesion: 0.20
Nodes (12): flag(), idText(), isObject(), numberOrNull(), readSsiSite(), SsiSiteValues, visibilityFor(), WATER_OF_BOW (+4 more)

### Community 199 - "Community 199"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 200 - "Community 200"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 202 - "Community 202"
Cohesion: 0.17
Nodes (10): EstimateInput, AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031 (+2 more)

### Community 203 - "Community 203"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 204 - "Community 204"
Cohesion: 0.21
Nodes (9): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, readQr(), ADR-0043, ADR-0043 (+1 more)

### Community 206 - "Community 206"
Cohesion: 0.26
Nodes (3): DiverView, DiverViewCertifications, DiverViewCertificationsNewest

### Community 207 - "Community 207"
Cohesion: 0.26
Nodes (3): DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 208 - "Community 208"
Cohesion: 0.18
Nodes (10): EquipmentError, ADR-0034, ADR-0053, checkCost(), checkDates(), checkWindow(), checkResets(), checkSchedule() (+2 more)

### Community 209 - "Community 209"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 211 - "Community 211"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 212 - "Community 212"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 213 - "Community 213"
Cohesion: 0.20
Nodes (10): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, ADR-0076, MODULE_IMPORTS, moduleRules (+2 more)

### Community 214 - "Community 214"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 215 - "Community 215"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

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
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 223 - "Community 223"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 224 - "Community 224"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 226 - "Community 226"
Cohesion: 0.29
Nodes (5): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, EstimateCylinder

### Community 227 - "Community 227"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 228 - "Community 228"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 230 - "Community 230"
Cohesion: 0.29
Nodes (6): itemIds(), ADR-0026, ADR-0034, Usage, UsageDive, UsageRow

### Community 231 - "Community 231"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 232 - "Community 232"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 233 - "Community 233"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 234 - "Community 234"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 235 - "Community 235"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 237 - "Community 237"
Cohesion: 0.40
Nodes (4): label(), CompareSubject, facts, subjects

### Community 238 - "Community 238"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build

### Community 239 - "Community 239"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 240 - "Community 240"
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
- **1303 isolated node(s):** `Sent`, `Reserve`, `RockBottom`, `Reachable`, `SuitThickness` (+1298 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3695 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **152 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 1` to `Community 3`, `Community 132`, `Community 6`, `Community 7`, `Community 8`, `Community 9`, `Community 10`, `Community 13`, `Community 14`, `Community 19`, `Community 148`, `Community 21`, `Community 23`, `Community 24`, `Community 154`, `Community 26`, `Community 29`, `Community 34`, `Community 36`, `Community 38`, `Community 40`, `Community 43`, `Community 45`, `Community 174`, `Community 47`, `Community 182`, `Community 183`, `Community 59`, `Community 194`, `Community 71`, `Community 73`, `Community 81`, `Community 99`, `Community 230`, `Community 108`, `Community 113`, `Community 116`, `Community 119`?**
  _High betweenness centrality (0.016) - this node is a cross-community bridge._
- **What connects `Sent`, `Reserve`, `RockBottom` to the rest of the system?**
  _1303 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.040376569037656906 - nodes in this community are weakly interconnected._
- **Why does `DiveStore` connect `Community 20` to `Community 97`, `Community 35`, `Community 69`, `Community 171`, `Community 79`, `Community 112`, `Community 177`, `Community 18`, `Community 146`, `Community 49`, `Community 115`, `Community 51`, `Community 55`, `Community 28`, `Community 63`, `Community 31`?**
  _High betweenness centrality (0.013) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.029042579374846174 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 5` to `Community 0`, `Community 2`, `Community 4`, `Community 168`, `Community 106`, `Community 11`, `Community 12`, `Community 110`, `Community 15`, `Community 16`, `Community 17`, `Community 114`, `Community 87`?**
  _High betweenness centrality (0.011) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.02333742020039741 - nodes in this community are weakly interconnected._