# Graph Report - dive_hub  (2026-11-21)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 11264 nodes · 34288 edges · 353 communities (210 shown, 143 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 650 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `0368ed54`
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
- Community 226
- Community 227
- Community 228
- Community 229
- Community 230
- Community 231
- Community 235

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
- `assembleToolSources()` --calls--> `ToolDiverSource`  [INFERRED]
  apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/tools/ToolSourcesStore.kt → apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/tools/ToolsService.kt

## Import Cycles
- None detected.

## Communities (353 total, 143 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (216): user, diveFingerprint, diveSiteExternalId, revision, createSsiClient(), ADR-0013, ADR-0011, dumpToolAvailable() (+208 more)

### Community 1 - "Community 1"
Cohesion: 0.02
Nodes (195): label(), ADR-0080, useDescribeAgent(), AiAccessLogEntry, aiAccessLogQuery(), aiAccessQuery(), AiAccessView, authClient (+187 more)

### Community 2 - "Community 2"
Cohesion: 0.02
Nodes (203): diversQuery(), cylinderCatalogueQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery() (+195 more)

### Community 3 - "Community 3"
Cohesion: 0.05
Nodes (201): ChangePassword(), DisplaySettings(), Sessions(), AdminSettings(), ClientDiagnosticsSetting(), DeleteUserDialog(), Invitations(), Invite() (+193 more)

### Community 4 - "Community 4"
Cohesion: 0.02
Nodes (184): Tx, continuationAppend, LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, RecordingSummary, SuitType, SuitUndergarment (+176 more)

### Community 5 - "Community 5"
Cohesion: 0.02
Nodes (177): divesQuery(), LogbookPage, PAGE_SIZE, buddiesForQuery(), buddiesQuery(), BuddyView, ConflictChoices, ConflictEntryView (+169 more)

### Community 6 - "Community 6"
Cohesion: 0.02
Nodes (131): acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), probeAppLock() (+123 more)

### Community 7 - "Community 7"
Cohesion: 0.01
Nodes (154): CHANNELS, isRule(), read(), ADR-0036, ENGINE_VERSION, accountRelations, apikey, rateLimit (+146 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (126): EMPTY_SITE_IMPORT_COUNTS, ImportArea, ImportedValues, SiteImportCounts, ADR-0021, ADR-0025, ADR-0053, errorMessage() (+118 more)

### Community 9 - "Community 9"
Cohesion: 0.02
Nodes (133): CentreService, CentreRouteDeps, LEAD_PLACEMENTS, UTC_OFFSET_SOURCES, WEIGHTING_FELT, DiverService, downsampleMinMax(), waterMismatch() (+125 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (127): assessmentQuery(), AssessmentView, samplesQuery(), RequirementView, AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider() (+119 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (132): AcceptedBuddySuggestionView, allButDive(), AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionView, CandidateView, continuationCandidateQuery(), ContinuationCandidateView (+124 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (119): ReportProblemSettings(), SecuritySettings(), backupsQuery(), devicesQuery(), ExternalIdView, App(), BackupsPage, CentrePage (+111 more)

### Community 13 - "Community 13"
Cohesion: 0.04
Nodes (118): diverExternalId, diveSite, siteImport, cylindersOf(), mergeProviderCylinder(), providerCylinderOf(), participantsOf(), partsOf() (+110 more)

### Community 14 - "Community 14"
Cohesion: 0.04
Nodes (65): rememberReducedMotion(), rememberSkeletonAlpha(), Direction, Back, Left, Right, DirectionIcon(), DiveBody() (+57 more)

### Community 15 - "Community 15"
Cohesion: 0.03
Nodes (110): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+102 more)

### Community 16 - "Community 16"
Cohesion: 0.03
Nodes (94): createAuth(), auth, hashPassword(), BackupServiceDeps, createServices(), ServiceParts, Services, ADR-0027 (+86 more)

### Community 17 - "Community 17"
Cohesion: 0.03
Nodes (100): BACKUP_RUN_TASK, backupTask(), ADR-0069, connection, importJob, providerConflict, push, waitingPush (+92 more)

### Community 18 - "Community 18"
Cohesion: 0.02
Nodes (89): diveAssessment, Assessment, FindingView, Row, ADR-0036, upload(), Check, Deleted (+81 more)

### Community 19 - "Community 19"
Cohesion: 0.05
Nodes (100): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, assessmentRoutes() (+92 more)

### Community 20 - "Community 20"
Cohesion: 0.03
Nodes (103): @modelcontextprotocol/server@^2.3.1, SUIT_TYPES, AiAccessScope, AiAccessService, seesPositions(), VerifiedAccess, mcpEndpoint(), McpEndpointDeps (+95 more)

### Community 21 - "Community 21"
Cohesion: 0.04
Nodes (105): ComputerChoice, DiveImportMode, DiveKind, providerSiteData, ProviderCylinder, siteRef(), entryMatches(), kindOfMode() (+97 more)

### Community 22 - "Community 22"
Cohesion: 0.03
Nodes (89): equipmentItem, equipmentUse, pushRunItem, PushRunLeftOut, managedDiverIds(), managesDiveDiver(), CandidateError, createCandidateService() (+81 more)

### Community 23 - "Community 23"
Cohesion: 0.03
Nodes (12): AppContainer, KeystoreTokenStore, PreferencesInstanceUrlStore, UserCacheStore, AndroidDiveStore, DiveStore, toBoolean(), toLong() (+4 more)

### Community 24 - "Community 24"
Cohesion: 0.04
Nodes (9): DiveDetailViewModelTest, GatedDispatcher, CountingDispatcher, LogbookViewModelTest, ApiClient, ApiErrorBody, StoreLogbook, StoreLogbookContext (+1 more)

### Community 25 - "Community 25"
Cohesion: 0.04
Nodes (36): InkButton(), QuietButton(), SkeletonBar(), AvatarCircle(), Error(), FilterAction(), Filters(), FilterSheet() (+28 more)

### Community 26 - "Community 26"
Cohesion: 0.04
Nodes (90): CertificationService, CertificationRouteDeps, CYLINDER_MATERIALS, DIVE_IMPORT_MODES, MATCHING_WINDOWS, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036 (+82 more)

### Community 27 - "Community 27"
Cohesion: 0.06
Nodes (12): FakeCache, RevisionFollowTest, LeadEstimateViewModelTest, ErrorCodes, LogbookPaging, PlanningSacSource, default, logbook (+4 more)

### Community 28 - "Community 28"
Cohesion: 0.07
Nodes (70): cylinderLine(), cylinderPressures(), Cylinders(), sacMissingText(), Formats, formatSyncTime(), GasPlanRoute(), GasPlanScreen() (+62 more)

### Community 29 - "Community 29"
Cohesion: 0.04
Nodes (83): NoFly, noFlyAfter(), Queryable, BottomTimeQuery, DEFAULT_GF_HIGH, diverLogbook(), planBottomTime(), residualSeedOf() (+75 more)

### Community 30 - "Community 30"
Cohesion: 0.04
Nodes (30): DiveDetailViewModel, LogbookViewModel, followStoreWrites(), DiveListView, DiveRepository, neighboursPath(), DecodeException, DiveHubException (+22 more)

### Community 31 - "Community 31"
Cohesion: 0.03
Nodes (62): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, ParticipantRole, ProviderImportPlan, PushLeftOut (+54 more)

### Community 32 - "Community 32"
Cohesion: 0.04
Nodes (48): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+40 more)

### Community 33 - "Community 33"
Cohesion: 0.04
Nodes (68): createServer(), loadAuthSecret(), workerDeps(), Config, loadConfig(), ADR-0011, ADR-0021, ADR-0024 (+60 more)

### Community 34 - "Community 34"
Cohesion: 0.03
Nodes (81): AssessmentService, AssessmentView, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView() (+73 more)

### Community 35 - "Community 35"
Cohesion: 0.04
Nodes (75): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+67 more)

### Community 36 - "Community 36"
Cohesion: 0.04
Nodes (75): ConnectionRow, providerGoneDive, externalDiver(), mergeExternalDiver(), participations(), open(), automationOffers(), AutomationSettings (+67 more)

### Community 37 - "Community 37"
Cohesion: 0.03
Nodes (74): CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError, CertificationExpiry, CertificationInput, CertificationView, createCertificationService(), cleaned() (+66 more)

### Community 38 - "Community 38"
Cohesion: 0.06
Nodes (78): buddySuggestionsQuery(), candidatesQuery(), deletedDivesQuery(), deletedDivesTotal(), isDiveCheck(), isPairCheck(), logbookChecksQuery(), goneDivesQuery() (+70 more)

### Community 39 - "Community 39"
Cohesion: 0.04
Nodes (12): MainActivity, MainViewModel, Unreachable, SettingsViewModel, LeadEstimateUiState, LeadEstimateViewModel, LoadingsUiState, LoadingsViewModel (+4 more)

### Community 40 - "Community 40"
Cohesion: 0.06
Nodes (78): waitingOf(), audienceOfConnection(), close(), currentRemote(), PushRow, ADR-0024, isExporting(), referenceOf() (+70 more)

### Community 41 - "Community 41"
Cohesion: 0.06
Nodes (10): DiveRow(), Figures(), RowFacts(), EmptyState(), FactRow(), NoteText(), StackedNoteText(), Section() (+2 more)

### Community 42 - "Community 42"
Cohesion: 0.05
Nodes (67): accountListEntry, diveLease, diver, providerLease, tidyName(), foldName(), nameDiffers(), Buddy (+59 more)

### Community 43 - "Community 43"
Cohesion: 0.04
Nodes (19): ApiClientDiagnosticsStatusGet200Response, ApiMeGet200ResponseOwnDiver, ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits, ConnectionView (+11 more)

### Community 44 - "Community 44"
Cohesion: 0.06
Nodes (71): logbookCheckAnswer, logbookCheckDiveAnswer, keepApart(), keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration(), CheckedDive (+63 more)

### Community 45 - "Community 45"
Cohesion: 0.06
Nodes (63): centreQuery(), centresQuery(), CentreView, sitesQuery(), SiteView, AddSiteDialog(), CentrePage(), CentresPage() (+55 more)

### Community 46 - "Community 46"
Cohesion: 0.04
Nodes (65): replyCertificationError, STATUS, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, errors (+57 more)

### Community 47 - "Community 47"
Cohesion: 0.05
Nodes (25): AppTab, Logbook, Settings, Tools, DiveHubBottomBar(), label(), SettingsDebugRoute(), SettingsDebugScreen() (+17 more)

### Community 48 - "Community 48"
Cohesion: 0.05
Nodes (67): serviceRecord, serviceRecordSchedule, serviceSchedule, managementOf(), ScheduleDue, EquipmentError, ADR-0034, ADR-0053 (+59 more)

### Community 49 - "Community 49"
Cohesion: 0.08
Nodes (54): BottomTime, BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen, BottomTimeLimits (+46 more)

### Community 50 - "Community 50"
Cohesion: 0.06
Nodes (66): ANDROID_UI, AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch() (+58 more)

### Community 51 - "Community 51"
Cohesion: 0.05
Nodes (12): FakeDiagnostics, FakeInstances, SettingsViewModelTest, ApiClientDiagnosticsPostRequest, ApiClientDiagnosticsPostRequestClient, ApiClientDiagnosticsPostRequestEntriesItem, DiagnosticHygiene, Diagnostics (+4 more)

### Community 52 - "Community 52"
Cohesion: 0.05
Nodes (52): pushRun, DIVE_FIELDS, ExportingProvider, ProviderError, createSsiAdapter(), buddyListOf(), called(), confirmed() (+44 more)

### Community 53 - "Community 53"
Cohesion: 0.06
Nodes (67): Answer, answer(), baseFrom(), BodyWeight, Confidence, high, low, medium (+59 more)

### Community 54 - "Community 54"
Cohesion: 0.06
Nodes (46): appliedMigrationHead(), BufferedLog, createLogRingBuffer(), LOG_BUFFER_CAPACITY_BYTES, LOG_BUFFER_CAPACITY_ENTRIES, LogBufferStats, LogRingBuffer, LogRingBufferOptions (+38 more)

### Community 55 - "Community 55"
Cohesion: 0.09
Nodes (15): DiveRoute, InstanceRoute, LogbookRoute, MainRoute, SettingsDebugRoute, SettingsRoute, SignInRoute, ToolsRoute (+7 more)

### Community 56 - "Community 56"
Cohesion: 0.07
Nodes (13): LeadAnswer, LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseBasis, ApiDiversIdLeadEstimateGet200ResponseBasisDivesItem (+5 more)

### Community 57 - "Community 57"
Cohesion: 0.08
Nodes (12): ApiDiveSitesGet200Response, SiteView, SyncCheckpoint, Complete, SessionEnded, Stopped, SyncEngine, SyncPhase (+4 more)

### Community 58 - "Community 58"
Cohesion: 0.06
Nodes (45): SsiClient, SsiRecord, buildCreateRecord(), buildDeleteRecord(), DiveForSsi, emptyRecord(), writableRecord(), FakeSsi (+37 more)

### Community 59 - "Community 59"
Cohesion: 0.10
Nodes (28): LiveHints, Body, Hold, Offline, RefreshData, refreshDeletedJson(), refreshDetailJson(), refreshDetailsPage() (+20 more)

### Community 60 - "Community 60"
Cohesion: 0.12
Nodes (24): Body, BrokenBody, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage(), diverJson() (+16 more)

### Community 61 - "Community 61"
Cohesion: 0.06
Nodes (36): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+28 more)

### Community 62 - "Community 62"
Cohesion: 0.05
Nodes (49): CENTRE_SOURCE_NAME, CENTRE_SOURCES, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder() (+41 more)

### Community 63 - "Community 63"
Cohesion: 0.05
Nodes (49): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, conflictForLena() (+41 more)

### Community 64 - "Community 64"
Cohesion: 0.09
Nodes (7): DiagnosticEntry, DiagnosticLevel, ERROR, WARN, DiagnosticsBuffer, DiagnosticsBufferTest, DiagnosticsTest

### Community 65 - "Community 65"
Cohesion: 0.07
Nodes (14): UiState, sessionDestination(), SessionDestinationTest, ApiMeGet200Response, ApiMeGet200ResponsePreferences, SignInEmailRequest, UserView, HealthView (+6 more)

### Community 66 - "Community 66"
Cohesion: 0.06
Nodes (6): logShareChooser(), DiveHubApplicationTest, LauncherIconTest, SplashTheme, SplashThemeTest, LogShareTest

### Community 67 - "Community 67"
Cohesion: 0.06
Nodes (43): ADR-0025, WaterMismatch, computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL (+35 more)

### Community 68 - "Community 68"
Cohesion: 0.08
Nodes (48): BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32Encode(), bech32HrpExpand(), bech32Polymod() (+40 more)

### Community 70 - "Community 70"
Cohesion: 0.10
Nodes (6): SettingsRepository, SyncState, InstanceUrlStore, FakeInstances, SettingsRepositoryTest, DiveStoreTest

### Community 71 - "Community 71"
Cohesion: 0.10
Nodes (41): ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres(), isHypoxic(), metresPerBar() (+33 more)

### Community 72 - "Community 72"
Cohesion: 0.07
Nodes (35): diveWith(), ADR-0028, ADR-0043, buddySite(), Dive, OTHER, resetSource(), ADR-0012 (+27 more)

### Community 73 - "Community 73"
Cohesion: 0.11
Nodes (8): FakeCache, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore, UserScopedStore

### Community 74 - "Community 74"
Cohesion: 0.05
Nodes (43): Change(), Changes(), ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, PositionSchema (+35 more)

### Community 75 - "Community 75"
Cohesion: 0.08
Nodes (11): DiveView, DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewLeadItem, DiveViewSac, DiveViewSite, DiveViewSuit, DiveViewValues (+3 more)

### Community 76 - "Community 76"
Cohesion: 0.05
Nodes (39): SUIT_UNDERGARMENTS, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053, replyDiverError, STATUS (+31 more)

### Community 78 - "Community 78"
Cohesion: 0.08
Nodes (41): DiveField, Leave, diveModeOfSsi(), keepTraining(), ssiDiveType(), ssiInteger(), ADR-0057, asNumber() (+33 more)

### Community 79 - "Community 79"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 80 - "Community 80"
Cohesion: 0.10
Nodes (12): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), GasPlanInputTest (+4 more)

### Community 82 - "Community 82"
Cohesion: 0.09
Nodes (8): Connected, Hint, HintFrame, LiveEvent, LiveHint, SseLiveHints, SyncRefresher, LiveHintsTest

### Community 83 - "Community 83"
Cohesion: 0.08
Nodes (29): diveLocalTime(), offsetId(), timeZoneNote(), TimeZoneOrigin, DEVICE, NEARBY, POSITION, UNKNOWN (+21 more)

### Community 84 - "Community 84"
Cohesion: 0.07
Nodes (34): sampleCount(), SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject() (+26 more)

### Community 85 - "Community 85"
Cohesion: 0.12
Nodes (4): SettingsDiagnosticsState, SettingsUiState, SettingsScreenshotTest, UpdateRequiredScreenTest

### Community 86 - "Community 86"
Cohesion: 0.10
Nodes (33): BACKUP_SKIPPED_DIRS, BackupDecryptionKey, encryptionKeyFingerprint(), openArchiveStreamingWith(), assertPgTool(), execFileAsync, pgCommand(), pgErrorDetail() (+25 more)

### Community 87 - "Community 87"
Cohesion: 0.07
Nodes (30): createFileFormats(), FitAdapter, looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), SuuntoJsonAdapter, ADR-0037, createUddfAdapter() (+22 more)

### Community 88 - "Community 88"
Cohesion: 0.08
Nodes (34): date(), FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position(), positive() (+26 more)

### Community 89 - "Community 89"
Cohesion: 0.08
Nodes (37): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+29 more)

### Community 90 - "Community 90"
Cohesion: 0.05
Nodes (37): AREA_OF, areaOf(), areas, capture(), findings, headers, ADR-0020, ADR-0021 (+29 more)

### Community 91 - "Community 91"
Cohesion: 0.08
Nodes (25): captureDiagnostic(), captureError(), cleanContext(), CLIENT_NAME, CLIENT_VERSION, DiagnosticEntry, diagnosticFromError(), DIAGNOSTICS_KEY (+17 more)

### Community 92 - "Community 92"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 93 - "Community 93"
Cohesion: 0.06
Nodes (37): EquipmentService, RecordInput, ScheduleInput, DiveGearItemViewSchema, DiveGearViewSchema, DueState, EquipmentDetailViewSchema, EquipmentDueViewSchema (+29 more)

### Community 94 - "Community 94"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 95 - "Community 95"
Cohesion: 0.10
Nodes (30): equipmentDueQuery(), EquipmentDueNotice(), ADR-0034, avatarTone(), initials(), dismissDue(), dismissedDueDigest(), dueCounts() (+22 more)

### Community 96 - "Community 96"
Cohesion: 0.11
Nodes (18): DiveDetailRoute(), DiveHubApp(), DiveHubScreens, ErrorText(), Gate(), InstanceForm(), InstanceStatus(), LoadingScreen() (+10 more)

### Community 97 - "Community 97"
Cohesion: 0.06
Nodes (34): defineTool(), Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), getDiverLoadings, HINT (+26 more)

### Community 98 - "Community 98"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 99 - "Community 99"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 100 - "Community 100"
Cohesion: 0.12
Nodes (35): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+27 more)

### Community 101 - "Community 101"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 102 - "Community 102"
Cohesion: 0.11
Nodes (5): DiveHubTopBar(), TitleSkeleton(), DiveHubTheme(), DiveHubTopBarTest, BottomBarScreenshotTest

### Community 105 - "Community 105"
Cohesion: 0.13
Nodes (11): ApiDiverBodyWeightsGet200ResponseItem, BodyWeight, Cylinder, Dive, Diver, toStore(), assembleToolSources(), epochMs() (+3 more)

### Community 106 - "Community 106"
Cohesion: 0.15
Nodes (10): bottomTime(), otu(), oxygenLimitMinutes(), planningSac(), PlanningSacDive, PlanningSacResult, BottomTimeToolInput, GasNumbersInput (+2 more)

### Community 107 - "Community 107"
Cohesion: 0.11
Nodes (31): DiveOrigin, REFERENCE_PREFIX, Series, SsiLogbook, asBool(), DIVE_ROW, SENT_KEYS, SsiValueType (+23 more)

### Community 108 - "Community 108"
Cohesion: 0.12
Nodes (32): importsQuery(), Logbook(), ImportContext, ImportFilesButton(), ImportHistory(), ImportList(), ImportNotices(), ImportPanel() (+24 more)

### Community 109 - "Community 109"
Cohesion: 0.11
Nodes (12): ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseCns, ApiDiversIdLoadingsGet200ResponseN2, ApiDiversIdLoadingsGet200ResponseNoFly, ApiDiversIdLoadingsGet200ResponseNoFlySource, ApiDiversIdLoadingsGet200ResponseOtu, cnsDecayed() (+4 more)

### Community 110 - "Community 110"
Cohesion: 0.11
Nodes (28): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+20 more)

### Community 111 - "Community 111"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 112 - "Community 112"
Cohesion: 0.09
Nodes (32): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+24 more)

### Community 113 - "Community 113"
Cohesion: 0.08
Nodes (24): LoadingsView, CustomTypeOptions, i18next, initI18n(), resources, ADR-0014, Language, LANGUAGES (+16 more)

### Community 114 - "Community 114"
Cohesion: 0.12
Nodes (8): AppNavHost(), AppScreens, clearSavedTabStates(), isShowing(), leaveDiveForLogbook(), MainShell(), navigateToTab(), tabOf()

### Community 115 - "Community 115"
Cohesion: 0.25
Nodes (8): leadAnswer(), EstimateCylinder, PlannedConditions, Suit, assemblePersonalizedSources(), suitOf(), leadEstimate(), PersonalizedToolsTest

### Community 116 - "Community 116"
Cohesion: 0.10
Nodes (9): Answers, number(), ToolsInputs, ToolsUiState, ToolsViewModel, ApiToolsBottomTimeGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseLimits (+1 more)

### Community 117 - "Community 117"
Cohesion: 0.09
Nodes (24): LoadingDive, dayNumber(), enteredDecoFromNdl(), minNdlSeconds(), NoFly, NoFlyReason, decompression, several (+16 more)

### Community 118 - "Community 118"
Cohesion: 0.09
Nodes (25): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+17 more)

### Community 119 - "Community 119"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 120 - "Community 120"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 121 - "Community 121"
Cohesion: 0.10
Nodes (23): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearPushRuns(), clearQueue() (+15 more)

### Community 123 - "Community 123"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 124 - "Community 124"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 125 - "Community 125"
Cohesion: 0.08
Nodes (22): headers, ADR-0035, aiAccessReady(), askMcp(), cancelWaitingImports(), createAiAccess(), putAside(), waitingImport() (+14 more)

### Community 126 - "Community 126"
Cohesion: 0.11
Nodes (7): ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh, ApiToolsBottomTimeGet200ResponseUsedSacLpm, ApiToolsBottomTimeGet200ResponseUsedWater

### Community 127 - "Community 127"
Cohesion: 0.11
Nodes (7): ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport

### Community 128 - "Community 128"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 129 - "Community 129"
Cohesion: 0.21
Nodes (4): GasPlanScreenshotTest, GasPlanToolDiver, GasPlanToolInput, GasPlanToolLevel

### Community 130 - "Community 130"
Cohesion: 0.16
Nodes (7): InstanceProbeTest, json(), probe(), ProbeCache, ProbeInstances, probeRepository(), ProbeTokens

### Community 131 - "Community 131"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 132 - "Community 132"
Cohesion: 0.11
Nodes (19): ProblemCode, ResponseState, whenResponseGone(), StoredBlob, PlacedArchive, ANSWER, looksLikeRecipientPrivateKey(), PlacedArchive (+11 more)

### Community 133 - "Community 133"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 134 - "Community 134"
Cohesion: 0.09
Nodes (4): DiveHubApplication, SyncWorker, WorkManagerPeriodicSync, PeriodicSyncScheduler

### Community 135 - "Community 135"
Cohesion: 0.13
Nodes (3): FakeInstances, FakeTokens, MainViewModelTest

### Community 137 - "Community 137"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 138 - "Community 138"
Cohesion: 0.14
Nodes (6): DiveViewRecordingsItem, DiveViewRecordingsItemDevice, DiveViewRecordingsItemSummary, DiveViewRecordingsItemSummaryGasesItem, DiveViewRecordingsItemSummarySurfaceBreaksItem, recording()

### Community 139 - "Community 139"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 140 - "Community 140"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 141 - "Community 141"
Cohesion: 0.15
Nodes (6): DiveListViewDivesItem, DiveListViewDivesItemGasesItem, DiveListViewDivesItemParticipantsItem, DiveListViewDivesItemSite, DiveProfileView, toListRow()

### Community 142 - "Community 142"
Cohesion: 0.13
Nodes (6): DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, WaitingPushView

### Community 143 - "Community 143"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 144 - "Community 144"
Cohesion: 0.09
Nodes (23): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+15 more)

### Community 145 - "Community 145"
Cohesion: 0.13
Nodes (21): BACKUP_FORMAT_VERSION, buildManifest(), decryptFile(), decryptFileWith(), decryptStreamWith(), encryptFile(), encryptStream(), execFileAsync (+13 more)

### Community 146 - "Community 146"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 148 - "Community 148"
Cohesion: 0.17
Nodes (20): compressibility(), Cubic, minusOne(), pressureForLitres(), surfaceLitres(), jsRound(), powInt(), roundTo() (+12 more)

### Community 150 - "Community 150"
Cohesion: 0.14
Nodes (17): AssessmentError, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), requireManagedDive(), same() (+9 more)

### Community 151 - "Community 151"
Cohesion: 0.14
Nodes (18): DAY_MS, MAX_OFFSET_MS, inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), placeLocalTime(), Position (+10 more)

### Community 152 - "Community 152"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 153 - "Community 153"
Cohesion: 0.18
Nodes (12): LeadInputTest, SuitType, drysuit, none, semidry, skin, wetsuit, SuitUndergarment (+4 more)

### Community 154 - "Community 154"
Cohesion: 0.15
Nodes (5): SiteViewExternalIdsItem, SiteViewExternalIdsItemAttribution, SiteViewExternalIdsItemOffered, SiteViewExternalIdsItemOfferedPosition, SiteViewExternalIdsItemPrivateTo

### Community 155 - "Community 155"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource/poppins@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 156 - "Community 156"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 157 - "Community 157"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 158 - "Community 158"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 159 - "Community 159"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 160 - "Community 160"
Cohesion: 0.11
Nodes (13): BACKUP_EXTENSION, BackupEncryption, BackupPart, BackupLog, BackupRunRow, BackupSettingsInput, BackupSettingsValues, DEFAULT_BACKUP_SETTINGS (+5 more)

### Community 161 - "Community 161"
Cohesion: 0.17
Nodes (16): UtcOffsetSource, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput, round3() (+8 more)

### Community 162 - "Community 162"
Cohesion: 0.19
Nodes (15): clientVersionGate(), ADR-0076, ClientVersionRegistry, MIN_CLIENT_VERSIONS, MinClientVersion, ADR-0076, clientRequestSerializer(), ADR-0076 (+7 more)

### Community 163 - "Community 163"
Cohesion: 0.18
Nodes (7): logbookDiveItemIndex(), DiveListViewMonthsItem, localMonthOf(), LogbookMonth, monthGroups(), dive(), MonthGroupsTest

### Community 164 - "Community 164"
Cohesion: 0.23
Nodes (10): AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint, SketchProfile, sketchScale(), toAscentBand() (+2 more)

### Community 165 - "Community 165"
Cohesion: 0.18
Nodes (3): FakeInstances, FakeTokens, SyncAuthTest

### Community 166 - "Community 166"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 167 - "Community 167"
Cohesion: 0.13
Nodes (15): AL80, asEstimateInput(), EstimateDiveCylinder, LEAD_CASES, leadInput(), LeadVectorInput, planOf(), STEEL_12 (+7 more)

### Community 171 - "Community 171"
Cohesion: 0.20
Nodes (14): AppLockProbe, BackupManifest, Arguments, credentialOf(), FILE_OPTIONS, fileText(), finishedArguments(), parseArguments() (+6 more)

### Community 172 - "Community 172"
Cohesion: 0.14
Nodes (15): aiAccessLog, aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, KeyRow, LOG_DAYS, LOG_PAGE_MAX (+7 more)

### Community 173 - "Community 173"
Cohesion: 0.15
Nodes (10): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, ADR-0068 (+2 more)

### Community 174 - "Community 174"
Cohesion: 0.17
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 175 - "Community 175"
Cohesion: 0.19
Nodes (4): DiveDetailsListView, DiveDetailsListViewCounts, DiveDetailsListViewMonthsItem, DiveDetailsListViewTotals

### Community 176 - "Community 176"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 177 - "Community 177"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 178 - "Community 178"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 179 - "Community 179"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 181 - "Community 181"
Cohesion: 0.21
Nodes (12): evaluateLeadVectorCase(), LEAD_VECTOR_KERNEL, leadVectorFile(), VECTOR_KERNELS, VectorFile, vectorFile(), assertMatch(), checkVectors() (+4 more)

### Community 182 - "Community 182"
Cohesion: 0.13
Nodes (14): concurrency, cancel-in-progress, group, env, RELEASE_TAG, jobs, publish, name (+6 more)

### Community 183 - "Community 183"
Cohesion: 0.20
Nodes (10): DIVE_KINDS, ImportView, ImportViewSchema, toImportView(), ADR-0030, ImportRouteDeps, ADR-0044, ADR-0056 (+2 more)

### Community 184 - "Community 184"
Cohesion: 0.27
Nodes (12): DiverError, accountKey(), createDiverService(), applyDetails(), applyExternalId(), detailsOf(), editable(), insertExternal() (+4 more)

### Community 185 - "Community 185"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 186 - "Community 186"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 187 - "Community 187"
Cohesion: 0.26
Nodes (3): NeighboursView, NeighboursViewNext, NeighboursViewPrevious

### Community 188 - "Community 188"
Cohesion: 0.17
Nodes (10): EstimateInput, AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031 (+2 more)

### Community 189 - "Community 189"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 190 - "Community 190"
Cohesion: 0.21
Nodes (9): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, readQr(), ADR-0043, ADR-0043 (+1 more)

### Community 191 - "Community 191"
Cohesion: 0.26
Nodes (3): DiverView, DiverViewCertifications, DiverViewCertificationsNewest

### Community 192 - "Community 192"
Cohesion: 0.26
Nodes (3): DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 193 - "Community 193"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 194 - "Community 194"
Cohesion: 0.25
Nodes (8): createBackupService(), checkSettings(), due(), enqueueScheduled(), readSettings(), saveSettings(), sweepRetention(), isUniqueViolation()

### Community 195 - "Community 195"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 196 - "Community 196"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 197 - "Community 197"
Cohesion: 0.20
Nodes (10): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, ADR-0076, MODULE_IMPORTS, moduleRules (+2 more)

### Community 198 - "Community 198"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 199 - "Community 199"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 201 - "Community 201"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 202 - "Community 202"
Cohesion: 0.20
Nodes (10): verify, prerelease, sha, tag, versionCode, versionName, name, outputs (+2 more)

### Community 203 - "Community 203"
Cohesion: 0.20
Nodes (10): with, cache, distribution, fetch-depth, if-no-files-found, java-version, name, path (+2 more)

### Community 204 - "Community 204"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 206 - "Community 206"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 207 - "Community 207"
Cohesion: 0.25
Nodes (6): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, CylinderInput, EstimateCylinder

### Community 208 - "Community 208"
Cohesion: 0.22
Nodes (8): LeadEstimateAnswer, Confidence, EstimateAdjustment, EstimateBasis, EstimateMissing, LeadEstimate, ADR-0025, ADR-0031

### Community 209 - "Community 209"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 210 - "Community 210"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 211 - "Community 211"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 213 - "Community 213"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 214 - "Community 214"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 217 - "Community 217"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 218 - "Community 218"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 219 - "Community 219"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 220 - "Community 220"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 221 - "Community 221"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 222 - "Community 222"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build

### Community 223 - "Community 223"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 224 - "Community 224"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 228 - "Community 228"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 229 - "Community 229"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 230 - "Community 230"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1311 isolated node(s):** `Reserve`, `RockBottom`, `Reachable`, `Sent`, `SuitThickness` (+1306 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3709 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **143 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 0` to `Community 4`, `Community 6`, `Community 7`, `Community 8`, `Community 9`, `Community 13`, `Community 144`, `Community 16`, `Community 17`, `Community 19`, `Community 20`, `Community 21`, `Community 22`, `Community 151`, `Community 18`, `Community 26`, `Community 29`, `Community 160`, `Community 33`, `Community 36`, `Community 37`, `Community 40`, `Community 42`, `Community 44`, `Community 172`, `Community 48`, `Community 52`, `Community 54`, `Community 62`, `Community 112`, `Community 123`?**
  _High betweenness centrality (0.025) - this node is a cross-community bridge._
- **What connects `Reserve`, `RockBottom`, `Reachable` to the rest of the system?**
  _1311 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.021279331404693024 - nodes in this community are weakly interconnected._
- **Why does `request()` connect `Community 1` to `Community 2`, `Community 3`, `Community 5`, `Community 38`, `Community 10`, `Community 11`, `Community 12`, `Community 108`, `Community 45`, `Community 95`?**
  _High betweenness centrality (0.016) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.016637450199203187 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 1` to `Community 2`, `Community 3`, `Community 5`, `Community 38`, `Community 10`, `Community 11`, `Community 12`, `Community 45`, `Community 108`, `Community 113`, `Community 120`, `Community 155`, `Community 92`, `Community 95`?**
  _High betweenness centrality (0.016) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.02242152466367713 - nodes in this community are weakly interconnected._