# Graph Report - dive_hub  (2026-11-20)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 10868 nodes · 32700 edges · 376 communities (233 shown, 143 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 616 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `e31b42be`
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
- Community 249
- Community 250
- Community 251
- Community 252
- Community 253
- Community 254
- Community 255
- Community 256
- Community 257
- Community 261

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
- `AuthOptions` --references--> `Db`  [EXTRACTED]
  apps/server/src/auth/auth.ts → apps/server/src/db/client.ts

## Import Cycles
- None detected.

## Communities (376 total, 143 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.04
Nodes (214): DisplaySettings(), AdminSettings(), ClientDiagnosticsSetting(), DeleteUserDialog(), Invitations(), Invite(), LinkToPassOn(), ProviderSiteData() (+206 more)

### Community 1 - "Community 1"
Cohesion: 0.03
Nodes (173): assessmentQuery(), AssessmentView, samplesQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime (+165 more)

### Community 2 - "Community 2"
Cohesion: 0.02
Nodes (158): ChangePassword(), SecuritySettings(), Sessions(), ADR-0080, useDescribeAgent(), AiAccessLogEntry, AiAccessView, authClient (+150 more)

### Community 3 - "Community 3"
Cohesion: 0.03
Nodes (154): Queryable, continuationAppend, device, OVERRIDABLE_FIELDS, OverridableField, recordingEvent, sampleSeries, renumber() (+146 more)

### Community 4 - "Community 4"
Cohesion: 0.04
Nodes (111): user, accountListEntry, diver, diverExternalId, PROBLEMS, forget(), remember(), rememberedEntries() (+103 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (157): AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionView, CandidateView, DeletedDivesPageView, DeletedDiveView, DiveCheckView (+149 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (133): diverSearchQuery(), api, centreQuery(), centresQuery(), CentreView, ExternalIdView, sitesQuery(), SiteView (+125 more)

### Community 7 - "Community 7"
Cohesion: 0.02
Nodes (131): ConnectionRow, pushRunItem, PushRunLeftOut, open(), AutomationSettings, ConnectionService, createConnectionService(), checkAutomation() (+123 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (137): diversQuery(), DiveView, cylinderCatalogueQuery(), CylinderFields(), CylinderList(), MATERIALS, ADR-0034, ADR-0045 (+129 more)

### Community 9 - "Community 9"
Cohesion: 0.02
Nodes (124): CentreService, CentreRouteDeps, LEAD_PLACEMENTS, WEIGHTING_FELT, DiverService, CertificationHint, CertificationHintExpiry, DiveCertifications (+116 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (110): ReportProblemSettings(), backupSettingsQuery(), backupsQuery(), supportBundlePreviewQuery(), isUnauthorized(), App(), BackupsPage, CentrePage (+102 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (107): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+99 more)

### Community 12 - "Community 12"
Cohesion: 0.04
Nodes (81): generateRecipientKeyPair(), createDb(), findChangedMigrations(), migrateDatabase(), ADR-0004, cylinder, dive, duplicateCandidate (+73 more)

### Community 13 - "Community 13"
Cohesion: 0.05
Nodes (102): label(), Me, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery(), candidatesQuery(), deletedDivesTotal(), DiveSummary (+94 more)

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (110): AiAccessScope, seesPositions(), diversOf(), seriesOf(), SiteRow, checkedByRunTool, createMcpServer(), INSTRUCTIONS (+102 more)

### Community 15 - "Community 15"
Cohesion: 0.03
Nodes (89): ParticipantRole, tidyName(), DiverDetails, Caller, foldName(), nameDiffers(), Buddy, createBuddyService() (+81 more)

### Community 16 - "Community 16"
Cohesion: 0.03
Nodes (13): AppContainer, KeystoreTokenStore, PreferencesInstanceUrlStore, UserCacheStore, AndroidDiveStore, MainActivity, SettingsDiagnosticsState, SettingsUiState (+5 more)

### Community 17 - "Community 17"
Cohesion: 0.04
Nodes (105): ComputerChoice, DiveImportMode, DiveKind, ProviderImportPlan, UtcOffsetSource, ProviderCylinder, siteRef(), PlacedTime (+97 more)

### Community 18 - "Community 18"
Cohesion: 0.04
Nodes (95): diveSite, equipmentItem, lead, LeadPlacement, managedDiverIds(), managesDiveDiver(), ADR-0016, tieToJoined() (+87 more)

### Community 19 - "Community 19"
Cohesion: 0.02
Nodes (101): StoredConflictField, actorType, aiAccessOutcome, backupRunKind, backupRunStatus, buddySuggestion, candidateResolution, centreSource (+93 more)

### Community 20 - "Community 20"
Cohesion: 0.03
Nodes (75): diveLease, providerConflict, providerLease, push, pushRun, revision, waitingPush, kept() (+67 more)

### Community 21 - "Community 21"
Cohesion: 0.03
Nodes (75): createFitAdapter(), Assessment, FindingView, Row, ADR-0036, upload(), Hint, SuggestionView (+67 more)

### Community 22 - "Community 22"
Cohesion: 0.04
Nodes (74): connection, createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities() (+66 more)

### Community 23 - "Community 23"
Cohesion: 0.04
Nodes (82): adminRole, AI_ACCESS_NAME_MAX, AI_ACCESS_PREFIX, AI_ACCESS_RATE, AuthOptions, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, SessionUser (+74 more)

### Community 24 - "Community 24"
Cohesion: 0.04
Nodes (87): CertificationService, CYLINDER_MATERIALS, UTC_OFFSET_SOURCES, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053, CandidateRouteDeps (+79 more)

### Community 25 - "Community 25"
Cohesion: 0.04
Nodes (78): replyCertificationError, STATUS, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, CertificationRouteDeps (+70 more)

### Community 26 - "Community 26"
Cohesion: 0.04
Nodes (48): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+40 more)

### Community 27 - "Community 27"
Cohesion: 0.05
Nodes (83): diveAssessment, logbookCheckAnswer, logbookCheckDiveAnswer, keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration(), CheckedDive (+75 more)

### Community 28 - "Community 28"
Cohesion: 0.03
Nodes (78): downsampleMinMax(), AIR, compressibility(), Cubic, HE, minusOne(), N2, O2 (+70 more)

### Community 29 - "Community 29"
Cohesion: 0.05
Nodes (80): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, ambientPressureBar() (+72 more)

### Community 30 - "Community 30"
Cohesion: 0.05
Nodes (50): DiveBody(), DiveDetailScreen(), errorText(), FactRow(), Facts(), Loading(), NeighbourButton(), Neighbours() (+42 more)

### Community 31 - "Community 31"
Cohesion: 0.07
Nodes (83): withBuddies(), APP_KEYS, appRow(), BuddySaved, byId(), changedKeys(), deleteTestDive(), deleteTestEntries() (+75 more)

### Community 32 - "Community 32"
Cohesion: 0.04
Nodes (60): createAuth(), auth, hashPassword(), createServices(), ServiceParts, Services, ADR-0027, ADR-0030 (+52 more)

### Community 33 - "Community 33"
Cohesion: 0.05
Nodes (26): AppTab, Logbook, Settings, Tools, DiveHubBottomBar(), Action(), label(), Section() (+18 more)

### Community 34 - "Community 34"
Cohesion: 0.06
Nodes (70): siteImport, dataIdsOf(), settleSiteVisibility(), outgoingSite(), findOrMake(), SiteForSend, siteForSend(), ADR-0057 (+62 more)

### Community 35 - "Community 35"
Cohesion: 0.08
Nodes (59): Formats, GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult(), PlanInputs() (+51 more)

### Community 36 - "Community 36"
Cohesion: 0.05
Nodes (61): createFileFormats(), date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, looksLikeFit(), num() (+53 more)

### Community 37 - "Community 37"
Cohesion: 0.06
Nodes (71): allButDive(), continuationCandidateQuery(), ContinuationCandidateView, deletedDivesOn(), deletedDivesQuery(), diveQuery(), LogbookCheckView, mergeCandidatesQuery() (+63 more)

### Community 38 - "Community 38"
Cohesion: 0.04
Nodes (58): Tx, diverBodyWeight, participant, DiverError, ADR-0016, ADR-0053, ACCOUNT_PATTERN, accountKey() (+50 more)

### Community 39 - "Community 39"
Cohesion: 0.06
Nodes (66): waitingOf(), close(), currentRemote(), PushRow, ADR-0024, isExporting(), referenceOf(), backfillFingerprints() (+58 more)

### Community 40 - "Community 40"
Cohesion: 0.06
Nodes (65): Leave, diveModeOfSsi(), keepTraining(), ssiDiveType(), ssiInteger(), ADR-0057, asNumber(), asText() (+57 more)

### Community 41 - "Community 41"
Cohesion: 0.08
Nodes (63): buildApp(), AssessmentService, AssessmentRouteDeps, assessmentRoutes(), AssessmentViewSchema, FindingView, Rule, STATUS (+55 more)

### Community 42 - "Community 42"
Cohesion: 0.06
Nodes (52): errorMessage(), looksLikeZip(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy() (+44 more)

### Community 43 - "Community 43"
Cohesion: 0.05
Nodes (17): MainViewModel, Unreachable, ApiMeGet200Response, ApiMeGet200ResponseOwnDiver, ApiMeGet200ResponsePreferences, SignInEmailRequest, InstanceUrl, HealthView (+9 more)

### Community 44 - "Community 44"
Cohesion: 0.05
Nodes (8): DiveStore, toBoolean(), toLong(), toModel(), BodyWeight, DiveSite, Recording, RecordingSeries

### Community 45 - "Community 45"
Cohesion: 0.06
Nodes (55): EMPTY_SITE_IMPORT_COUNTS, ImportArea, ImportedValues, SiteImportCounts, ADR-0021, ADR-0025, ADR-0053, Context (+47 more)

### Community 46 - "Community 46"
Cohesion: 0.06
Nodes (61): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+53 more)

### Community 47 - "Community 47"
Cohesion: 0.04
Nodes (56): buddiesForQuery(), buddiesQuery(), BuddyView, ConflictChoices, ConflictEntryView, ConflictFieldView, ConflictsOfConnection, DiveImportMode (+48 more)

### Community 48 - "Community 48"
Cohesion: 0.07
Nodes (27): ProfileChart(), DiveViewRecordingsItem, DiveViewRecordingsItemDevice, DiveViewRecordingsItemSummarySurfaceBreaksItem, RecordingSamplesView, RecordingSamplesViewSeriesItem, computerRecordings(), depthSeries() (+19 more)

### Community 49 - "Community 49"
Cohesion: 0.10
Nodes (8): LeadEstimateViewModelTest, Cylinder, Dive, Diver, toStore(), json(), LogbookRepositoryTest, RepositoryHarness

### Community 50 - "Community 50"
Cohesion: 0.08
Nodes (35): ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits, ambientPressureBar(), bestMixPercent(), Conditions (+27 more)

### Community 51 - "Community 51"
Cohesion: 0.07
Nodes (22): DiveDetailUiState, DiveDetailViewModel, LogbookViewModel, DiveRepository, neighboursPath(), profileRecording(), countOf(), FilterChip (+14 more)

### Community 52 - "Community 52"
Cohesion: 0.12
Nodes (24): Body, BrokenBody, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage(), diverJson() (+16 more)

### Community 53 - "Community 53"
Cohesion: 0.06
Nodes (12): ApiClient, ApiErrorBody, DecodeException, DiveHubException, NoAnswerException, decodeList(), SyncPhase, Details (+4 more)

### Community 54 - "Community 54"
Cohesion: 0.07
Nodes (55): diverManagement, WaitingPushOptions, audienceOfConnection(), audienceOfDive(), emitHint(), Hint, HINT_KINDS, HintAudience (+47 more)

### Community 55 - "Community 55"
Cohesion: 0.06
Nodes (36): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+28 more)

### Community 56 - "Community 56"
Cohesion: 0.04
Nodes (50): Accepted, Candidate, Check, DiveView, Outcome, RevisionView, START, ADR-0070 (+42 more)

### Community 57 - "Community 57"
Cohesion: 0.06
Nodes (12): ApiClientDiagnosticsPostRequest, ApiClientDiagnosticsPostRequestClient, ApiClientDiagnosticsPostRequestEntriesItem, DiagnosticHygiene, Diagnostics, Failed, DiagnosticEntry, DiagnosticLevel (+4 more)

### Community 58 - "Community 58"
Cohesion: 0.08
Nodes (56): Answer, answer(), baseFrom(), BodyWeight, Confidence, high, low, medium (+48 more)

### Community 59 - "Community 59"
Cohesion: 0.11
Nodes (27): Body, Hold, Offline, RefreshData, refreshDeletedJson(), refreshDetailJson(), refreshDetailsPage(), refreshDiveId() (+19 more)

### Community 60 - "Community 60"
Cohesion: 0.07
Nodes (12): DiveListView, DiveListViewCounts, DiveListViewTotals, DiveViewSac, DiveViewSite, NeighboursView, NeighboursViewNext, NeighboursViewPrevious (+4 more)

### Community 61 - "Community 61"
Cohesion: 0.06
Nodes (44): providerSiteData, SSI_CAPABILITIES, createSsiClient(), Card, DEEP, OWD, Report, Summary (+36 more)

### Community 62 - "Community 62"
Cohesion: 0.10
Nodes (4): DiveDetailScreenshotTest, DiveViewFromRecording, DiveViewFromRecordingStartsAt, DiveViewWaterMismatch

### Community 63 - "Community 63"
Cohesion: 0.05
Nodes (49): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, conflictForLena() (+41 more)

### Community 64 - "Community 64"
Cohesion: 0.05
Nodes (49): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, EquipmentService, RecordInput (+41 more)

### Community 65 - "Community 65"
Cohesion: 0.06
Nodes (15): drawBand(), ProfileSketch(), LogbookPaging, AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint (+7 more)

### Community 66 - "Community 66"
Cohesion: 0.06
Nodes (46): CENTRE_SOURCE_NAME, CENTRE_SOURCES, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder() (+38 more)

### Community 67 - "Community 67"
Cohesion: 0.07
Nodes (47): Delivered, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open() (+39 more)

### Community 68 - "Community 68"
Cohesion: 0.07
Nodes (47): NoFly, getDiverLoadings, DEFAULT_GF_HIGH, residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows(), DiveRow (+39 more)

### Community 69 - "Community 69"
Cohesion: 0.13
Nodes (3): DiveRoute, AppNavHostTest, FakeScreens

### Community 70 - "Community 70"
Cohesion: 0.08
Nodes (47): BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32Encode(), bech32HrpExpand(), bech32Polymod() (+39 more)

### Community 71 - "Community 71"
Cohesion: 0.07
Nodes (24): ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh, ApiToolsBottomTimeGet200ResponseUsedSacLpm, ApiToolsBottomTimeGet200ResponseUsedWater, BottomTime (+16 more)

### Community 73 - "Community 73"
Cohesion: 0.08
Nodes (38): BottomTimeQuery, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033, ADR-0071, DEFAULT_PPO2 (+30 more)

### Community 74 - "Community 74"
Cohesion: 0.07
Nodes (35): diveWith(), ADR-0028, ADR-0043, buddySite(), Dive, OTHER, resetSource(), ADR-0012 (+27 more)

### Community 75 - "Community 75"
Cohesion: 0.07
Nodes (19): AppNavHost(), AppScreens, clearSavedTabStates(), isShowing(), leaveDiveForLogbook(), MainShell(), navigateToTab(), tabOf() (+11 more)

### Community 76 - "Community 76"
Cohesion: 0.05
Nodes (40): SUIT_TYPES, SUIT_UNDERGARMENTS, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053, replyDiverError (+32 more)

### Community 77 - "Community 77"
Cohesion: 0.10
Nodes (20): AvatarCircle(), DiveRow(), Error(), Fact(), Filters(), Loading(), LogbookList(), LogbookRoute() (+12 more)

### Community 78 - "Community 78"
Cohesion: 0.08
Nodes (37): EstimateDive, EstimateDiveCylinder, WeightingFeedback, WeightingFelt, right, too_heavy, too_light, LoadingDive (+29 more)

### Community 79 - "Community 79"
Cohesion: 0.11
Nodes (8): FakeCache, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore, UserScopedStore

### Community 80 - "Community 80"
Cohesion: 0.08
Nodes (5): LogbookViewModelTest, FakeCache, FakeInstances, FakeTokens, MainViewModelTest

### Community 81 - "Community 81"
Cohesion: 0.07
Nodes (7): SettingsRouteTest, SettingsRepository, InstanceUrlStore, TokenStore, UserScopedStore, FakeInstances, SettingsRepositoryTest

### Community 82 - "Community 82"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 83 - "Community 83"
Cohesion: 0.13
Nodes (6): SyncCheckpoint, Complete, SessionEnded, Stopped, SyncEngine, SyncResult

### Community 84 - "Community 84"
Cohesion: 0.07
Nodes (39): Auth, aiAccessLog, aiAccessSetting, Access, AccessView, AiAccessRouteDeps, LogEntryView, Scope (+31 more)

### Community 85 - "Community 85"
Cohesion: 0.06
Nodes (38): ADR-0025, WaterMismatch, waterMismatch(), CIRCUIT, circuitFromFit, DECO_MODEL, decoModelFromFit, DIVE_ALERT (+30 more)

### Community 86 - "Community 86"
Cohesion: 0.08
Nodes (18): DiveDetailRoute(), DiveHubApp(), DiveHubScreens, ErrorText(), Gate(), InstanceForm(), InstanceStatus(), LoadingScreen() (+10 more)

### Community 87 - "Community 87"
Cohesion: 0.09
Nodes (13): ApiDiverBodyWeightsGet200ResponseItem, DiveListViewDivesItem, DiveListViewDivesItemGasesItem, DiveListViewDivesItemParticipantsItem, DiveListViewDivesItemSite, DiveListViewMonthsItem, DiveProfileView, localMonthOf() (+5 more)

### Community 88 - "Community 88"
Cohesion: 0.09
Nodes (22): ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseCns, ApiDiversIdLoadingsGet200ResponseN2, ApiDiversIdLoadingsGet200ResponseNoFly, ApiDiversIdLoadingsGet200ResponseNoFlySource, cnsDecayed(), Estimate (+14 more)

### Community 89 - "Community 89"
Cohesion: 0.07
Nodes (35): sampleCount(), SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject() (+27 more)

### Community 90 - "Community 90"
Cohesion: 0.10
Nodes (6): FakeDiagnostics, FakeInstances, SettingsViewModelTest, ReportProblemResult, Sent, DiagnosticsAccess

### Community 91 - "Community 91"
Cohesion: 0.06
Nodes (38): Change(), Changes(), historyRoute, Made(), REVISION_CAUSES, PositionSchema, AdoptBody, Country (+30 more)

### Community 92 - "Community 92"
Cohesion: 0.08
Nodes (37): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+29 more)

### Community 93 - "Community 93"
Cohesion: 0.05
Nodes (37): AREA_OF, areaOf(), areas, capture(), findings, headers, ADR-0020, ADR-0021 (+29 more)

### Community 94 - "Community 94"
Cohesion: 0.08
Nodes (25): captureDiagnostic(), captureError(), cleanContext(), CLIENT_NAME, CLIENT_VERSION, DiagnosticEntry, diagnosticFromError(), DIAGNOSTICS_KEY (+17 more)

### Community 95 - "Community 95"
Cohesion: 0.12
Nodes (6): LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200ResponseLeftOut, ApiDiversIdLeadEstimateGet200ResponseMissingItem, ApiDiversIdLoadingsGet200ResponseOtu

### Community 96 - "Community 96"
Cohesion: 0.11
Nodes (33): BACKUP_SKIPPED_DIRS, BackupDecryptionKey, encryptionKeyFingerprint(), openArchiveStreamingWith(), assertPgTool(), execFileAsync, pgCommand(), pgErrorDetail() (+25 more)

### Community 97 - "Community 97"
Cohesion: 0.10
Nodes (34): wallClockMs(), DiveOrigin, REFERENCE_PREFIX, Series, SsiLogbook, asBool(), DIVE_ROW, SENT_KEYS (+26 more)

### Community 98 - "Community 98"
Cohesion: 0.12
Nodes (9): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), GasPlanInputTest (+1 more)

### Community 99 - "Community 99"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 100 - "Community 100"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 101 - "Community 101"
Cohesion: 0.07
Nodes (28): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError (+20 more)

### Community 102 - "Community 102"
Cohesion: 0.08
Nodes (30): Db, ImportErrorCode, ImportFound, importOriginal, ImportOutcome, FileFormat, formatOf(), CHOICE_WAIT_DAYS (+22 more)

### Community 103 - "Community 103"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 104 - "Community 104"
Cohesion: 0.06
Nodes (31): account, accountRelations, apikey, rateLimit, session, sessionRelations, ADR-0011, ADR-0012 (+23 more)

### Community 105 - "Community 105"
Cohesion: 0.11
Nodes (20): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+12 more)

### Community 106 - "Community 106"
Cohesion: 0.09
Nodes (33): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+25 more)

### Community 107 - "Community 107"
Cohesion: 0.12
Nodes (27): bar(), celsius(), contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf(), firstOf() (+19 more)

### Community 108 - "Community 108"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 109 - "Community 109"
Cohesion: 0.07
Nodes (27): Cylinder, DiveView, RevisionView, ADR-0045, upload(), Candidate, Check, Outcome (+19 more)

### Community 110 - "Community 110"
Cohesion: 0.10
Nodes (8): ApiClientDiagnosticsStatusGet200Response, ApiToolsBottomTimeGet200ResponseResidualN2, ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting

### Community 111 - "Community 111"
Cohesion: 0.17
Nodes (8): bottomTime(), otu(), oxygenLimitMinutes(), PlanningSacResult, BottomTimeToolInput, GasNumbersInput, ToolDiverSource, ToolsServiceTest

### Community 112 - "Community 112"
Cohesion: 0.09
Nodes (27): createServer(), loadAuthSecret(), acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, AppLock, appLockPath() (+19 more)

### Community 113 - "Community 113"
Cohesion: 0.11
Nodes (10): Answers, number(), ToolsViewModel, ApiToolsBottomTimeGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseLimits, depthFromDisplay(), pressureFromDisplay() (+2 more)

### Community 115 - "Community 115"
Cohesion: 0.09
Nodes (25): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+17 more)

### Community 116 - "Community 116"
Cohesion: 0.13
Nodes (23): equipmentDueQuery(), EquipmentDueNotice(), ADR-0034, avatarTone(), initials(), dismissDue(), dismissedDueDigest(), dueCounts() (+15 more)

### Community 117 - "Community 117"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 118 - "Community 118"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 119 - "Community 119"
Cohesion: 0.09
Nodes (28): applyHints(), LiveArrivals, Page, useLiveArrivals(), bump(), changedListeners, ChannelMessage, healthListeners (+20 more)

### Community 120 - "Community 120"
Cohesion: 0.10
Nodes (20): SsiClient, ClearChecks, NumberChecks, TwowayChecks, apneaDive(), DATASET_KEYS, datasetSummary(), findIn() (+12 more)

### Community 121 - "Community 121"
Cohesion: 0.10
Nodes (23): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearPushRuns(), clearQueue() (+15 more)

### Community 123 - "Community 123"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 124 - "Community 124"
Cohesion: 0.10
Nodes (23): BACKUP_RUN_TASK, backupTask(), ADR-0069, PurgeService, EXPIRE_WAITING_IMPORTS_TASK, PROCESS_IMPORT_TASK, SCHEDULED_FETCH_TASK, PUSH_RUN_STEP_TASK (+15 more)

### Community 125 - "Community 125"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 126 - "Community 126"
Cohesion: 0.08
Nodes (22): headers, ADR-0035, aiAccessReady(), askMcp(), cancelWaitingImports(), createAiAccess(), putAside(), waitingImport() (+14 more)

### Community 127 - "Community 127"
Cohesion: 0.26
Nodes (6): leadAnswer(), EstimateCylinder, PlannedConditions, Suit, leadEstimate(), PersonalizedToolsTest

### Community 128 - "Community 128"
Cohesion: 0.11
Nodes (7): ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport

### Community 129 - "Community 129"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 130 - "Community 130"
Cohesion: 0.13
Nodes (3): LauncherIconTest, SplashTheme, SplashThemeTest

### Community 131 - "Community 131"
Cohesion: 0.21
Nodes (4): GasPlanScreenshotTest, GasPlanToolDiver, GasPlanToolInput, GasPlanToolLevel

### Community 132 - "Community 132"
Cohesion: 0.14
Nodes (23): planningSac(), PlanningSacDive, PlanningSacSource, default, logbook, jsRound(), powInt(), roundTo() (+15 more)

### Community 133 - "Community 133"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 134 - "Community 134"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 135 - "Community 135"
Cohesion: 0.09
Nodes (4): DiveHubApplication, SyncWorker, WorkManagerPeriodicSync, PeriodicSyncScheduler

### Community 136 - "Community 136"
Cohesion: 0.15
Nodes (6): LeadAnswer, LeadEstimateUiState, LeadEstimateViewModel, ApiDiversIdLeadEstimateGet200Response, SyncProgress, PersonalizedSources

### Community 138 - "Community 138"
Cohesion: 0.14
Nodes (7): InstanceProbeTest, json(), probe(), ProbeCache, ProbeInstances, probeRepository(), ProbeTokens

### Community 139 - "Community 139"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 141 - "Community 141"
Cohesion: 0.18
Nodes (3): DiveViewValues, DiveViewValuesStartsAt, StoreLogbookTest

### Community 142 - "Community 142"
Cohesion: 0.08
Nodes (25): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+17 more)

### Community 143 - "Community 143"
Cohesion: 0.11
Nodes (17): ProblemCode, ResponseState, whenResponseGone(), StoredBlob, PlacedArchive, ANSWER, looksLikeRecipientPrivateKey(), PlacedArchive (+9 more)

### Community 144 - "Community 144"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 145 - "Community 145"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 147 - "Community 147"
Cohesion: 0.13
Nodes (6): DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, WaitingPushView

### Community 148 - "Community 148"
Cohesion: 0.13
Nodes (21): equipmentUse, DiveGear, ensureItemForDevice(), GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow (+13 more)

### Community 149 - "Community 149"
Cohesion: 0.09
Nodes (17): IMPORT_SOURCES, SITE_SOURCES, SOURCE_INFO, SourceInfo, ADR-0021, IMPORT_SOURCES, EGYPT, ExternalId (+9 more)

### Community 150 - "Community 150"
Cohesion: 0.13
Nodes (17): diverLoadingsQuery(), CurrentLoadings(), ADR-0036, ADR-0048, ADR-0071, CustomTypeOptions, i18next, resources (+9 more)

### Community 151 - "Community 151"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 153 - "Community 153"
Cohesion: 0.15
Nodes (22): managementOf(), EquipmentError, checkCost(), checkDates(), checkWindow(), createEquipmentService(), assemble(), checkResets() (+14 more)

### Community 154 - "Community 154"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 155 - "Community 155"
Cohesion: 0.16
Nodes (8): Connected, Hint, HintFrame, LiveEvent, LiveHint, LiveHints, SseLiveHints, LiveHintsTest

### Community 156 - "Community 156"
Cohesion: 0.14
Nodes (20): BACKUP_FORMAT_VERSION, buildManifest(), decryptFile(), decryptFileWith(), decryptStreamWith(), encryptFile(), encryptStream(), execFileAsync (+12 more)

### Community 157 - "Community 157"
Cohesion: 0.12
Nodes (19): SUGGESTION_STATUSES, DiveProfileView, DiveProfileViewSchema, ADR-0041, AcceptBody, STATUS, SuggestionRouteDeps, ADR-0070 (+11 more)

### Community 159 - "Community 159"
Cohesion: 0.15
Nodes (5): DiveView, DiveViewLeadItem, DiveViewPosition, DiveViewSuit, DiveViewWeightingFeedback

### Community 161 - "Community 161"
Cohesion: 0.16
Nodes (16): DecisionTimelineProps, GapKind, ADR-0063, addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, laneFitsCapsule(), relations() (+8 more)

### Community 162 - "Community 162"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 163 - "Community 163"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 164 - "Community 164"
Cohesion: 0.15
Nodes (5): SiteViewExternalIdsItem, SiteViewExternalIdsItemAttribution, SiteViewExternalIdsItemOffered, SiteViewExternalIdsItemOfferedPosition, SiteViewExternalIdsItemPrivateTo

### Community 165 - "Community 165"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource/poppins@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 166 - "Community 166"
Cohesion: 0.10
Nodes (14): BACKUP_EXTENSION, BackupEncryption, BackupPart, BackupLog, BackupRunRow, BackupServiceDeps, BackupSettingsInput, BackupSettingsValues (+6 more)

### Community 167 - "Community 167"
Cohesion: 0.13
Nodes (17): AL80, asEstimateInput(), EstimateDiveCylinder, evaluateLeadVectorCase(), LEAD_CASES, leadInput(), leadVectorFile(), LeadVectorInput (+9 more)

### Community 168 - "Community 168"
Cohesion: 0.13
Nodes (14): BufferedLog, LOG_BUFFER_CAPACITY_BYTES, LOG_BUFFER_CAPACITY_ENTRIES, LogBufferStats, LogRingBufferOptions, SupportBundle, ClientDiagnosticsProvider, FailedBackup (+6 more)

### Community 169 - "Community 169"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 170 - "Community 170"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 171 - "Community 171"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 173 - "Community 173"
Cohesion: 0.19
Nodes (11): LeadInputTest, SuitType, drysuit, none, semidry, skin, wetsuit, SuitUndergarment (+3 more)

### Community 174 - "Community 174"
Cohesion: 0.18
Nodes (3): FakeInstances, FakeTokens, SyncAuthTest

### Community 175 - "Community 175"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 176 - "Community 176"
Cohesion: 0.18
Nodes (15): APP_LOCK_STALE_MS, AppLockProbe, BackupManifest, Arguments, credentialOf(), FILE_OPTIONS, fileText(), finishedArguments() (+7 more)

### Community 177 - "Community 177"
Cohesion: 0.13
Nodes (13): diveSiteExternalId, ids(), call(), createSite(), Method, openStream(), Stream, ADR-0066 (+5 more)

### Community 178 - "Community 178"
Cohesion: 0.12
Nodes (16): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+8 more)

### Community 179 - "Community 179"
Cohesion: 0.15
Nodes (10): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, ADR-0068 (+2 more)

### Community 180 - "Community 180"
Cohesion: 0.17
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 181 - "Community 181"
Cohesion: 0.19
Nodes (4): DiveDetailsListView, DiveDetailsListViewCounts, DiveDetailsListViewMonthsItem, DiveDetailsListViewTotals

### Community 182 - "Community 182"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 183 - "Community 183"
Cohesion: 0.12
Nodes (13): clientDiagnostic, clientDiagnosticsSettings, ClientDiagnosticsEntryInput, ClientDiagnosticsEntryView, ClientDiagnosticsError, ClientDiagnosticsUpload, KEEP_NEWEST, MAX_UPLOAD_ENTRIES (+5 more)

### Community 184 - "Community 184"
Cohesion: 0.15
Nodes (15): SuitType, SuitUndergarment, WeightingFelt, Suit, WeightingFeedback, LeadEstimateAnswer, LeadVectorDive, Confidence (+7 more)

### Community 185 - "Community 185"
Cohesion: 0.21
Nodes (11): buildSupportBundle(), buildTarGz(), json(), ndjson(), octal(), SUPPORT_BUNDLE_FORMAT, SUPPORT_BUNDLE_FORMAT_VERSION, SupportBufferManifest (+3 more)

### Community 186 - "Community 186"
Cohesion: 0.23
Nodes (13): clientRequestSerializer(), ADR-0076, compareIdentifier(), compareNumeric(), compareSemanticVersions(), parseAndroidUserAgent(), parseSemanticVersion(), SemanticVersion (+5 more)

### Community 187 - "Community 187"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 188 - "Community 188"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 191 - "Community 191"
Cohesion: 0.16
Nodes (14): serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, DueEntry, ScheduleView, equipmentList(), EquipmentListFilter (+6 more)

### Community 192 - "Community 192"
Cohesion: 0.24
Nodes (12): SsiRecord, contextOf(), FakeSsi, findIn(), finishTwoway(), GAS, sendTwoway(), show() (+4 more)

### Community 193 - "Community 193"
Cohesion: 0.13
Nodes (6): FakeBroadcastChannel, FakeEventSource, Listener, locks, start(), ADR-0066

### Community 194 - "Community 194"
Cohesion: 0.13
Nodes (14): name, needs, runs-on, timeout-minutes, concurrency, cancel-in-progress, group, env (+6 more)

### Community 195 - "Community 195"
Cohesion: 0.35
Nodes (4): UiState, sessionDestination(), SessionDestinationTest, UserView

### Community 198 - "Community 198"
Cohesion: 0.20
Nodes (5): LogRingBuffer, RedactionCounters, SupportBundleInput, SupportBundleManifest, SupportServiceDeps

### Community 199 - "Community 199"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 200 - "Community 200"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 201 - "Community 201"
Cohesion: 0.17
Nodes (10): EstimateInput, AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031 (+2 more)

### Community 202 - "Community 202"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 203 - "Community 203"
Cohesion: 0.21
Nodes (9): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, readQr(), ADR-0043, ADR-0043 (+1 more)

### Community 204 - "Community 204"
Cohesion: 0.26
Nodes (3): ApiDiveSitesGet200Response, SiteView, SiteViewPosition

### Community 205 - "Community 205"
Cohesion: 0.26
Nodes (3): DiverView, DiverViewCertifications, DiverViewCertificationsNewest

### Community 206 - "Community 206"
Cohesion: 0.26
Nodes (3): DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewCylindersItemSeries

### Community 207 - "Community 207"
Cohesion: 0.26
Nodes (3): DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 208 - "Community 208"
Cohesion: 0.23
Nodes (9): fileTreeChecksum(), createBackupService(), checkSettings(), due(), enqueueScheduled(), readSettings(), saveSettings(), sweepRetention() (+1 more)

### Community 209 - "Community 209"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 210 - "Community 210"
Cohesion: 0.29
Nodes (10): RecordingSummary, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput, round3() (+2 more)

### Community 211 - "Community 211"
Cohesion: 0.18
Nodes (9): Buddy, KAI, MIA, NAMELESS, SAM, Status, ADR-0029, ADR-0043 (+1 more)

### Community 212 - "Community 212"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 213 - "Community 213"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 214 - "Community 214"
Cohesion: 0.20
Nodes (10): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, ADR-0076, MODULE_IMPORTS, moduleRules (+2 more)

### Community 215 - "Community 215"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 216 - "Community 216"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 217 - "Community 217"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 219 - "Community 219"
Cohesion: 0.33
Nodes (8): prepareEntry(), DENIED_KEY_PARTS, isDeniedKey(), maskString(), redactInner(), redactLogObject(), RedactOptions, redactValue()

### Community 220 - "Community 220"
Cohesion: 0.33
Nodes (9): cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043, pictureOf() (+1 more)

### Community 221 - "Community 221"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 222 - "Community 222"
Cohesion: 0.20
Nodes (10): verify, prerelease, sha, tag, versionCode, versionName, name, outputs (+2 more)

### Community 223 - "Community 223"
Cohesion: 0.20
Nodes (10): with, cache, distribution, fetch-depth, if-no-files-found, java-version, name, path (+2 more)

### Community 224 - "Community 224"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 225 - "Community 225"
Cohesion: 0.33
Nodes (3): DiveViewRecordingsItemSummary, DiveViewRecordingsItemSummaryGasesItem, summaryOf()

### Community 226 - "Community 226"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 227 - "Community 227"
Cohesion: 0.28
Nodes (8): itemIds(), ADR-0026, ADR-0034, Usage, UsageDive, usageMap(), usageOf(), UsageRow

### Community 228 - "Community 228"
Cohesion: 0.28
Nodes (7): ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, s(), ADR-0035

### Community 230 - "Community 230"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 231 - "Community 231"
Cohesion: 0.22
Nodes (9): version, on, push, workflow_dispatch, tags, description, required, type (+1 more)

### Community 232 - "Community 232"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 233 - "Community 233"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 236 - "Community 236"
Cohesion: 0.46
Nodes (8): appliedMigrationHead(), emptyRedactionCounters(), createSupportService(), assemble(), clientDiagnostics(), configSection(), failedJobs(), healthSection()

### Community 237 - "Community 237"
Cohesion: 0.29
Nodes (5): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, EstimateCylinder

### Community 238 - "Community 238"
Cohesion: 0.25
Nodes (6): AQABA, ATTERSEE, EILAT, HAUSREEF, MID_ATLANTIC, ADR-0030

### Community 239 - "Community 239"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 240 - "Community 240"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 241 - "Community 241"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 242 - "Community 242"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 243 - "Community 243"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 244 - "Community 244"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 245 - "Community 245"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 246 - "Community 246"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 247 - "Community 247"
Cohesion: 0.40
Nodes (4): TAGS, ADR-0023, { stdout, stderr }, total

### Community 253 - "Community 253"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 254 - "Community 254"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 255 - "Community 255"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1300 isolated node(s):** `Reserve`, `RockBottom`, `Off`, `Reachable`, `SuitThickness` (+1295 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3672 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **143 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Button()` connect `Community 0` to `Community 1`, `Community 2`, `Community 37`, `Community 6`, `Community 5`, `Community 8`, `Community 10`, `Community 13`, `Community 47`, `Community 116`, `Community 150`, `Community 220`?**
  _High betweenness centrality (0.019) - this node is a cross-community bridge._
- **What connects `Reserve`, `RockBottom`, `Off` to the rest of the system?**
  _1300 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.03828345567476002 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 2` to `Community 0`, `Community 1`, `Community 193`, `Community 5`, `Community 165`, `Community 37`, `Community 6`, `Community 8`, `Community 10`, `Community 13`, `Community 47`, `Community 116`, `Community 119`, `Community 118`, `Community 150`?**
  _High betweenness centrality (0.017) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.02697020971132646 - nodes in this community are weakly interconnected._
- **Why does `drizzle-orm` connect `Community 4` to `Community 3`, `Community 7`, `Community 9`, `Community 11`, `Community 12`, `Community 142`, `Community 14`, `Community 15`, `Community 17`, `Community 18`, `Community 19`, `Community 20`, `Community 148`, `Community 21`, `Community 23`, `Community 22`, `Community 25`, `Community 27`, `Community 32`, `Community 34`, `Community 166`, `Community 38`, `Community 39`, `Community 168`, `Community 45`, `Community 177`, `Community 54`, `Community 183`, `Community 56`, `Community 61`, `Community 191`, `Community 64`, `Community 66`, `Community 68`, `Community 73`, `Community 211`, `Community 84`, `Community 227`, `Community 101`, `Community 102`, `Community 104`, `Community 106`, `Community 109`, `Community 123`?**
  _High betweenness centrality (0.015) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.021563498982853823 - nodes in this community are weakly interconnected._