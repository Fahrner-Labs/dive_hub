# Graph Report - dive_hub  (2026-11-19)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 8933 nodes · 26961 edges · 250 communities (186 shown, 64 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 401 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `152df9dd`
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
- Community 188
- Community 189
- Community 190
- Community 191
- Community 192
- Community 193
- Community 194
- Community 197

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 204 edges
2. `Button()` - 198 edges
3. `useErrorText()` - 169 edges
4. `useDisplay()` - 161 edges
5. `Muted()` - 139 edges
6. `drizzle-orm` - 139 edges
7. `unwrap()` - 138 edges
8. `announce()` - 106 edges
9. `createTestDatabase()` - 103 edges
10. `TestDatabase` - 102 edges

## Surprising Connections (you probably didn't know these)
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `SsiClientOptions` --references--> `Fetch`  [EXTRACTED]
  apps/server/src/providers/ssi/ssi-client.ts → apps/server/src/providers/ssi/ssi-transport.ts
- `EquipmentListFilter` --references--> `EquipmentCategory`  [EXTRACTED]
  apps/server/src/equipment/read.ts → apps/server/src/vocabulary.ts

## Import Cycles
- None detected.

## Communities (250 total, 64 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.03
Nodes (172): user, diver, diverExternalId, diveSiteExternalId, revision, listAccountOf(), DIVE_FIELDS, SSI_CAPABILITIES (+164 more)

### Community 1 - "Community 1"
Cohesion: 0.02
Nodes (188): aiAccessSettingQuery(), backupSettingsQuery(), siteDataQuery(), SiteDataView, ADR-0030, bodyWeightsQuery(), certificationsQuery(), CertificationView (+180 more)

### Community 2 - "Community 2"
Cohesion: 0.05
Nodes (182): DeleteUserDialog(), Invite(), LinkToPassOn(), SiteDataPanel(), UserRow(), CreateAccess(), diversQuery(), DiverView (+174 more)

### Community 3 - "Community 3"
Cohesion: 0.02
Nodes (182): allButDive(), AnyLogbookCheck, continuationCandidateQuery(), ContinuationCandidateView, deletedDivesOn(), DeletedDivesPageView, deletedDivesQuery(), DeletedDiveView (+174 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (162): Queryable, continuationAppend, CylinderMaterial, diveAssessment, diverManagement, equipmentItem, LeadPlacement, OVERRIDABLE_FIELDS (+154 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (161): cylinderCatalogueQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas (+153 more)

### Community 6 - "Community 6"
Cohesion: 0.02
Nodes (137): buddySuggestion, jointDive, parseHint(), Assessment, FindingView, Row, ADR-0036, upload() (+129 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (147): EMPTY_SITE_IMPORT_COUNTS, ImportArea, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053 (+139 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (132): BACKUP_EXTENSION, BACKUP_FORMAT_VERSION, BACKUP_SKIPPED_DIRS, BackupArchiveError, BackupDecryptionKey, BackupEncryption, BackupEncryptionMetadata, BackupPart (+124 more)

### Community 9 - "Community 9"
Cohesion: 0.02
Nodes (132): createAuth(), actorType, aiAccessOutcome, backupRunKind, backupRunStatus, candidateResolution, centreSource, CERTIFICATION_IMAGE_STATES (+124 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (129): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+121 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (143): ComputerChoice, DiveImportMode, DiveKind, importOriginal, providerSiteData, UtcOffsetSource, PartInput, mergeProviderCylinder() (+135 more)

### Community 12 - "Community 12"
Cohesion: 0.02
Nodes (126): ADR-0043, VerificationCodeView, LEAD_PLACEMENTS, WEIGHTING_FELT, ADR-0025, WaterMismatch, waterMismatch(), CatalogueCylinder (+118 more)

### Community 13 - "Community 13"
Cohesion: 0.04
Nodes (40): DiveDetailRoute(), DiveHubApp(), ErrorText(), InstanceForm(), Loading(), PasswordField(), PrimaryButton(), SignInForm() (+32 more)

### Community 14 - "Community 14"
Cohesion: 0.04
Nodes (113): diveFingerprint, PushLeftOut, waitingOf(), audienceOfConnection(), audienceOfDive(), audienceOfDiver(), open(), close() (+105 more)

### Community 15 - "Community 15"
Cohesion: 0.02
Nodes (102): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+94 more)

### Community 16 - "Community 16"
Cohesion: 0.03
Nodes (99): accountListEntry, ConnectionRow, providerLease, WaitingPushOptions, tidyName(), DiverDetails, Caller, foldName() (+91 more)

### Community 17 - "Community 17"
Cohesion: 0.03
Nodes (83): ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS, eachFile(), readEntry(), ADR-0044, walk() (+75 more)

### Community 18 - "Community 18"
Cohesion: 0.04
Nodes (97): divesQuery(), DiveSummary, LogbookPage, PAGE_SIZE, pushRunActive(), pushRunBody(), PushRunChoices, PushRunPreview (+89 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (98): logbookCheckAnswer, logbookCheckDiveAnswer, recordingEvent, keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration(), CheckedDive (+90 more)

### Community 20 - "Community 20"
Cohesion: 0.04
Nodes (76): createServer(), loadAuthSecret(), acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock (+68 more)

### Community 21 - "Community 21"
Cohesion: 0.05
Nodes (88): label(), AcceptedBuddySuggestionView, BuddySuggestionOverlapView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView, candidatesQuery(), CandidateView (+80 more)

### Community 22 - "Community 22"
Cohesion: 0.05
Nodes (86): ChangePassword(), DisplaySettings(), SecuritySettings(), Sessions(), useDescribeAgent(), AdminSettings(), Invitations(), ProviderSiteData() (+78 more)

### Community 23 - "Community 23"
Cohesion: 0.04
Nodes (88): CertificationService, CYLINDER_MATERIALS, DIVE_IMPORT_MODES, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053, DiveSummaryFields (+80 more)

### Community 24 - "Community 24"
Cohesion: 0.05
Nodes (70): equipmentDueQuery(), EquipmentDueView, CodeFigure(), DiveCodes(), showDiveCodes(), ADR-0043, Buddies(), EquipmentDueNotice() (+62 more)

### Community 25 - "Community 25"
Cohesion: 0.04
Nodes (22): ApiToolsBottomTimeGet200ResponseResidualN2, ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting, DiveView (+14 more)

### Community 26 - "Community 26"
Cohesion: 0.04
Nodes (73): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+65 more)

### Community 27 - "Community 27"
Cohesion: 0.04
Nodes (15): MainActivity, LogbookViewModel, MainViewModel, UiState, ApiMeGet200Response, ApiMeGet200ResponseOwnDiver, ApiMeGet200ResponsePreferences, SignInEmailRequest (+7 more)

### Community 28 - "Community 28"
Cohesion: 0.03
Nodes (53): ParticipantRole, providerGoneDive, compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051 (+45 more)

### Community 29 - "Community 29"
Cohesion: 0.04
Nodes (74): pushRunItem, PushRunLeftOut, DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN, NO_RECORDING (+66 more)

### Community 30 - "Community 30"
Cohesion: 0.04
Nodes (68): Me, BuddySuggestionPreviewView, siteImportsQuery(), App(), BackupsPage, CentrePage, CentresPage, currentRoute() (+60 more)

### Community 31 - "Community 31"
Cohesion: 0.05
Nodes (49): DiveBody(), DiveDetailScreen(), errorText(), FactRow(), Facts(), Loading(), NeighbourButton(), Neighbours() (+41 more)

### Community 32 - "Community 32"
Cohesion: 0.05
Nodes (54): auth, BackupLog, BackupServiceDeps, createServices(), ServiceParts, Services, ADR-0027, ADR-0030 (+46 more)

### Community 33 - "Community 33"
Cohesion: 0.03
Nodes (59): connection, diveLease, providerConflict, pushRun, kept(), record(), SCHEDULED_FETCH_TASK, ExportingProvider (+51 more)

### Community 34 - "Community 34"
Cohesion: 0.08
Nodes (66): buildApp(), assessmentRoutes(), SessionUser, authHandler(), authHeaders(), fastify, FastifyRequest, forwardCookies() (+58 more)

### Community 35 - "Community 35"
Cohesion: 0.05
Nodes (56): downsampleMinMax(), DiveCertifications, LogbookCheckService, AppendDeps, AppendOutcome, AppendService, Side, ADR-0038 (+48 more)

### Community 36 - "Community 36"
Cohesion: 0.05
Nodes (54): SsiClient, SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), DiveForSsi, emptyRecord(), writableRecord() (+46 more)

### Community 37 - "Community 37"
Cohesion: 0.04
Nodes (59): Sac, SAC_MISSING, SacMissing, sacOfDive(), SacValues, sacValues(), ADR-0033, ADR-0045 (+51 more)

### Community 38 - "Community 38"
Cohesion: 0.06
Nodes (27): DiveDetailUiState, DiveDetailViewModel, ApiDivesGet200ResponseCounts, ApiClient, DiveRepository, neighboursPath(), samplesPath(), DiveHubException (+19 more)

### Community 39 - "Community 39"
Cohesion: 0.06
Nodes (60): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+52 more)

### Community 40 - "Community 40"
Cohesion: 0.07
Nodes (26): DiveViewRecordingsItem, DiveViewRecordingsItemDevice, DiveViewRecordingsItemSummary, DiveViewRecordingsItemSummaryGasesItem, DiveViewRecordingsItemSummarySurfaceBreaksItem, RecordingSamplesView, RecordingSamplesViewSeriesItem, computerRecordings() (+18 more)

### Community 41 - "Community 41"
Cohesion: 0.07
Nodes (15): LogbookScreenshotTest, ApiDivesGet200Response, ApiDivesGet200ResponseDivesItem, ApiDivesGet200ResponseDivesItemGasesItem, ApiDivesGet200ResponseDivesItemParticipantsItem, ApiDivesGet200ResponseDivesItemSite, ApiDivesGet200ResponseMonthsItem, ApiDivesGet200ResponseTotals (+7 more)

### Community 42 - "Community 42"
Cohesion: 0.04
Nodes (56): AssessmentService, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView(), ADR-0036 (+48 more)

### Community 43 - "Community 43"
Cohesion: 0.07
Nodes (10): DiveHubTheme(), DiveDetailScreenshotTest, DiveViewFromRecording, DiveViewFromRecordingStartsAt, DiveViewValues, DiveViewValuesStartsAt, DiveViewWaterMismatch, NeighboursView (+2 more)

### Community 44 - "Community 44"
Cohesion: 0.06
Nodes (57): BottomTimeInput, Conditions, GasMix, GasPlanInput, GasRule, AIR, DEFAULT_CONDITIONS, LoadingDive (+49 more)

### Community 45 - "Community 45"
Cohesion: 0.06
Nodes (52): hashPassword(), invitation, passwordReset, userRole, AttemptLimit, Problem, DateTime, AccountRouteDeps (+44 more)

### Community 46 - "Community 46"
Cohesion: 0.06
Nodes (55): SuitType, SuitUndergarment, WeightingFelt, Suit, WeightingFeedback, answer(), baseFrom(), cylindersAtReserve() (+47 more)

### Community 47 - "Community 47"
Cohesion: 0.07
Nodes (4): ApiErrorBody, json(), LogbookRepositoryTest, RepositoryHarness

### Community 48 - "Community 48"
Cohesion: 0.07
Nodes (49): Delivered, ProviderError, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf() (+41 more)

### Community 49 - "Community 49"
Cohesion: 0.05
Nodes (50): ADR-0036, headers, ADR-0030, ADR-0038, ADR-0048, ADR-0062, AiAccess, assessedDive() (+42 more)

### Community 50 - "Community 50"
Cohesion: 0.06
Nodes (45): PARTICIPANT_ROLES, siteImport, siteImportStatus, DIVE_ONLY_PATTERN, PROBLEM_CODES, storedProblemCode(), ADR-0014, ADR-0027 (+37 more)

### Community 51 - "Community 51"
Cohesion: 0.07
Nodes (46): Leave, asBool(), DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+38 more)

### Community 52 - "Community 52"
Cohesion: 0.06
Nodes (34): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+26 more)

### Community 53 - "Community 53"
Cohesion: 0.07
Nodes (38): createSsiSiteSource(), DEFAULT_SSI_SITES_URL, createSiteSources(), ADR-0021, ADR-0027, createOverpassSource(), DEFAULT_OVERPASS_URL, Element (+30 more)

### Community 54 - "Community 54"
Cohesion: 0.12
Nodes (37): BottomTime, BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen, BottomTimeLimits (+29 more)

### Community 55 - "Community 55"
Cohesion: 0.06
Nodes (41): CENTRE_SOURCES, CentreService, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+33 more)

### Community 56 - "Community 56"
Cohesion: 0.06
Nodes (10): AppContainer, KeystoreTokenStore, PreferencesInstanceUrlStore, UserCacheStore, AndroidDiveStore, DiveHubApplication, LogbookRepository, InstanceUrlStore (+2 more)

### Community 57 - "Community 57"
Cohesion: 0.07
Nodes (14): drawBand(), ProfileSketch(), LogbookPaging, AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint (+6 more)

### Community 58 - "Community 58"
Cohesion: 0.05
Nodes (36): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError (+28 more)

### Community 59 - "Community 59"
Cohesion: 0.11
Nodes (10): ToolsScreenshotTest, bottomTime(), PlanningSacResult, PlanningSacSource, default, logbook, BottomTimeToolInput, GasNumbersInput (+2 more)

### Community 60 - "Community 60"
Cohesion: 0.06
Nodes (42): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, DUE_STATES, EquipmentService, RecordInput (+34 more)

### Community 61 - "Community 61"
Cohesion: 0.08
Nodes (32): diveWith(), ADR-0028, ADR-0043, buddySite(), Dive, OTHER, resetSource(), ADR-0012 (+24 more)

### Community 62 - "Community 62"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 63 - "Community 63"
Cohesion: 0.07
Nodes (40): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+32 more)

### Community 64 - "Community 64"
Cohesion: 0.08
Nodes (35): BottomTimeQuery, DEFAULT_GF_HIGH, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033, ADR-0071 (+27 more)

### Community 65 - "Community 65"
Cohesion: 0.10
Nodes (22): AvatarCircle(), DiveRow(), Error(), Fact(), Filters(), Loading(), LogbookList(), LogbookRoute() (+14 more)

### Community 66 - "Community 66"
Cohesion: 0.09
Nodes (34): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+26 more)

### Community 67 - "Community 67"
Cohesion: 0.06
Nodes (33): account, accountRelations, apikey, rateLimit, session, sessionRelations, ADR-0011, ADR-0012 (+25 more)

### Community 68 - "Community 68"
Cohesion: 0.07
Nodes (32): sampleCount(), SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject() (+24 more)

### Community 69 - "Community 69"
Cohesion: 0.06
Nodes (30): headers, ADR-0035, diveRow(), rowOf(), ADR-0026, ADR-0048, aiAccessReady(), askMcp() (+22 more)

### Community 70 - "Community 70"
Cohesion: 0.05
Nodes (36): AREA_OF, areaOf(), areas, capture(), findings, headers, ADR-0020, ADR-0021 (+28 more)

### Community 71 - "Community 71"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 72 - "Community 72"
Cohesion: 0.13
Nodes (8): FakeCache, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore, UserScopedStore

### Community 73 - "Community 73"
Cohesion: 0.07
Nodes (30): BOM, Json, num(), obj(), positive(), scaled(), suuntoFitKey(), time() (+22 more)

### Community 74 - "Community 74"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 75 - "Community 75"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 76 - "Community 76"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 77 - "Community 77"
Cohesion: 0.14
Nodes (24): planningSac(), PlanningSacDive, jsRound(), powInt(), roundTo(), Sac, SacCylinder, SacDive (+16 more)

### Community 78 - "Community 78"
Cohesion: 0.08
Nodes (29): RecordingSummary, RecordingFacts, FileFormat, date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS (+21 more)

### Community 79 - "Community 79"
Cohesion: 0.12
Nodes (35): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+27 more)

### Community 80 - "Community 80"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 82 - "Community 82"
Cohesion: 0.07
Nodes (32): diversOf(), Cursor, DateOnly(), defineTool(), diversAsked(), excerpt(), Id(), offsetOf() (+24 more)

### Community 83 - "Community 83"
Cohesion: 0.09
Nodes (33): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+25 more)

### Community 84 - "Community 84"
Cohesion: 0.12
Nodes (32): importsQuery(), ImportView, Logbook(), ImportContext, ImportFilesButton(), ImportHistory(), ImportList(), ImportNotices() (+24 more)

### Community 85 - "Community 85"
Cohesion: 0.08
Nodes (33): SUIT_UNDERGARMENTS, seriesOf(), minutes(), round(), Channel, CHANNEL_UNITS, CHANNELS, Computer (+25 more)

### Community 86 - "Community 86"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 87 - "Community 87"
Cohesion: 0.11
Nodes (9): Answers, number(), ToolsViewModel, ApiToolsBottomTimeGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseLimits, depthFromDisplay(), pressureFromDisplay() (+1 more)

### Community 88 - "Community 88"
Cohesion: 0.11
Nodes (9): ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh, ApiToolsBottomTimeGet200ResponseUsedSacLpm, ApiToolsBottomTimeGet200ResponseUsedWater, bottomTime() (+1 more)

### Community 89 - "Community 89"
Cohesion: 0.09
Nodes (29): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+21 more)

### Community 90 - "Community 90"
Cohesion: 0.12
Nodes (27): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+19 more)

### Community 91 - "Community 91"
Cohesion: 0.09
Nodes (30): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+22 more)

### Community 92 - "Community 92"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 93 - "Community 93"
Cohesion: 0.09
Nodes (22): backupTask(), ADR-0069, PurgeService, PUSH_RUN_STEP_TASK, WAITING_PUSHES_TASK, IMPORT_DIVE_SITES_TASK, idOf(), processImportTask() (+14 more)

### Community 94 - "Community 94"
Cohesion: 0.15
Nodes (26): HINT, Input, LoadingsInput, planGasAndBottomTime, ADR-0032, ADR-0035, Used(), BOTTOM_TIME_RULES (+18 more)

### Community 95 - "Community 95"
Cohesion: 0.10
Nodes (24): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearPushRuns(), clearQueue() (+16 more)

### Community 96 - "Community 96"
Cohesion: 0.12
Nodes (24): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+16 more)

### Community 97 - "Community 97"
Cohesion: 0.11
Nodes (28): AscentPressure, DiverPlan, GasPlan, GasPlanDiver, GasPlanInput, GasPlanLevel, GasPlanLimit, depth (+20 more)

### Community 98 - "Community 98"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 99 - "Community 99"
Cohesion: 0.10
Nodes (26): globalSetup(), ADR-0023, accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks() (+18 more)

### Community 100 - "Community 100"
Cohesion: 0.11
Nodes (7): ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport

### Community 101 - "Community 101"
Cohesion: 0.15
Nodes (25): DiveOrigin, REFERENCE_PREFIX, Series, SsiLogbook, aboveZero(), cylinderOf(), deviceOf(), deviceOfRef() (+17 more)

### Community 102 - "Community 102"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 104 - "Community 104"
Cohesion: 0.11
Nodes (19): ProblemCode, ResponseState, whenResponseGone(), StoredBlob, PlacedArchive, ANSWER, looksLikeRecipientPrivateKey(), PlacedArchive (+11 more)

### Community 106 - "Community 106"
Cohesion: 0.14
Nodes (23): NoFly, getDiverLoadings, residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows(), DiveRow, endMsOf() (+15 more)

### Community 107 - "Community 107"
Cohesion: 0.13
Nodes (22): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DueInput, DueSchedule (+14 more)

### Community 108 - "Community 108"
Cohesion: 0.15
Nodes (20): errorMessage(), looksLikeZip(), formatOf(), createImportService(), cancelImport(), endWaiting(), expireWaiting(), openUpload() (+12 more)

### Community 109 - "Community 109"
Cohesion: 0.10
Nodes (19): Cylinder, DiveView, RevisionView, ADR-0045, upload(), FIT_EPOCH_MS, makeSuuntoAppFit(), makeSuuntoJson() (+11 more)

### Community 110 - "Community 110"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 111 - "Community 111"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 112 - "Community 112"
Cohesion: 0.12
Nodes (22): equipmentUse, DiveGear, GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow, ItemView (+14 more)

### Community 113 - "Community 113"
Cohesion: 0.16
Nodes (23): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), typeOf() (+15 more)

### Community 114 - "Community 114"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 115 - "Community 115"
Cohesion: 0.15
Nodes (19): cnsDecayed(), Estimate, isoUtc(), LoadingBasis, LoadingDive, N2Loadings, N2UnavailableReason, no_profile (+11 more)

### Community 116 - "Community 116"
Cohesion: 0.09
Nodes (23): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+15 more)

### Community 117 - "Community 117"
Cohesion: 0.13
Nodes (21): @modelcontextprotocol/server@^2.3.1, AccessRefusal, AiAccessService, seesPositions(), VerifiedAccess, mcpEndpoint(), McpEndpointDeps, REFUSALS (+13 more)

### Community 118 - "Community 118"
Cohesion: 0.15
Nodes (22): EquipmentError, checkCost(), checkDates(), checkWindow(), createEquipmentService(), assemble(), checkResets(), checkSchedule() (+14 more)

### Community 119 - "Community 119"
Cohesion: 0.14
Nodes (18): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+10 more)

### Community 120 - "Community 120"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 121 - "Community 121"
Cohesion: 0.13
Nodes (4): toBoolean(), toLong(), toModel(), Recording

### Community 122 - "Community 122"
Cohesion: 0.09
Nodes (21): kept, LEAD_MATERIALS, LEAD_WATERS, LeadWater, NUMBERS, OWN, PlanDiver, PlanLevel (+13 more)

### Community 123 - "Community 123"
Cohesion: 0.14
Nodes (16): BottomTime, bottomTime(), Limit, ADR-0032, ADR-0033, ADR-0071, MAX_DEPTH_M, NOAA_ROWS (+8 more)

### Community 124 - "Community 124"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 125 - "Community 125"
Cohesion: 0.15
Nodes (5): ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits

### Community 126 - "Community 126"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 127 - "Community 127"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 128 - "Community 128"
Cohesion: 0.15
Nodes (17): DAY_MS, inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), placeLocalTime(), Position, ADR-0030 (+9 more)

### Community 129 - "Community 129"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 130 - "Community 130"
Cohesion: 0.13
Nodes (15): FIND_BY, OPERATIONS, SignInInput, CASES, ContractHarness, declared(), ELSEWHERE, later() (+7 more)

### Community 131 - "Community 131"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 132 - "Community 132"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 133 - "Community 133"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 134 - "Community 134"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 135 - "Community 135"
Cohesion: 0.16
Nodes (16): AiAccessScope, cursorAt(), inputProblem(), loggable(), MAX_RESULT_CHARS, minute(), page(), pgCode() (+8 more)

### Community 136 - "Community 136"
Cohesion: 0.12
Nodes (16): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+8 more)

### Community 137 - "Community 137"
Cohesion: 0.15
Nodes (14): CIRCUIT, circuitFromFit, DECO_MODEL, decoModelFromFit, DIVE_ALERT, DIVE_MODE, diveModeFromFit, ADR-0015 (+6 more)

### Community 138 - "Community 138"
Cohesion: 0.17
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 139 - "Community 139"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 140 - "Community 140"
Cohesion: 0.15
Nodes (14): aiAccessLog, aiAccessSetting, AI_ACCESS_SCOPES, AiAccessError, KeyRow, LOG_DAYS, LOG_PAGE_MAX, LoggedCall (+6 more)

### Community 141 - "Community 141"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 142 - "Community 142"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 143 - "Community 143"
Cohesion: 0.20
Nodes (13): dayNumber(), enteredDecoFromNdl(), minNdlSeconds(), NoFly, noFlyAfter(), NoFlyReason, decompression, several (+5 more)

### Community 144 - "Community 144"
Cohesion: 0.16
Nodes (11): replyCertificationError, STATUS, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, CertificationRouteDeps (+3 more)

### Community 145 - "Community 145"
Cohesion: 0.16
Nodes (14): serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, DueEntry, ScheduleView, equipmentList(), EquipmentListFilter (+6 more)

### Community 146 - "Community 146"
Cohesion: 0.15
Nodes (13): SUIT_TYPES, replyDiverError, STATUS, LeadService, Amount, EstimateQuery, EstimateView, LeadRouteDeps (+5 more)

### Community 147 - "Community 147"
Cohesion: 0.22
Nodes (12): defined(), Joined, joinedSummary(), joinParts(), median(), round3(), ADR-0063, createFitAdapter() (+4 more)

### Community 148 - "Community 148"
Cohesion: 0.16
Nodes (14): SiteRow, Detail, SharedText(), ToolContext, buddies, dataIdsOf(), getSite, SearchInput (+6 more)

### Community 149 - "Community 149"
Cohesion: 0.13
Nodes (10): EGYPT, ExternalId, HOUSE_REEF, Method, Position, Revision, Site, SiteImport (+2 more)

### Community 150 - "Community 150"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 151 - "Community 151"
Cohesion: 0.18
Nodes (11): addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, relations(), TimelineBar, TimelineBarGeometry, TimelineGapGeometry, TimelineGeometry (+3 more)

### Community 152 - "Community 152"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 153 - "Community 153"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 154 - "Community 154"
Cohesion: 0.19
Nodes (9): DIVE_KINDS, ImportView, ImportViewSchema, ADR-0030, ImportRouteDeps, ADR-0044, ADR-0056, UploadTooLargeError (+1 more)

### Community 155 - "Community 155"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 156 - "Community 156"
Cohesion: 0.18
Nodes (11): UTC_OFFSET_SOURCES, CandidateRouteDeps, CandidateView, Decided, DiveBrief, OffsetSource, STATUS, ADR-0016 (+3 more)

### Community 157 - "Community 157"
Cohesion: 0.20
Nodes (8): ADR-0063, ADR-0040, ADR-0047, diveNumber(), expireSsiTokens(), headers, openFilters(), signInSsi()

### Community 158 - "Community 158"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 159 - "Community 159"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 160 - "Community 160"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 161 - "Community 161"
Cohesion: 0.29
Nodes (9): LoadingsView, CurrentLoadings(), LOADING_SOURCES, loadingNumber(), loadingPercent(), LoadingValues, showsLoadings(), ADR-0071 (+1 more)

### Community 162 - "Community 162"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 163 - "Community 163"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 164 - "Community 164"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 165 - "Community 165"
Cohesion: 0.29
Nodes (7): otu(), oxygenLimitMinutes(), compressibility(), Cubic, minusOne(), pressureForLitres(), surfaceLitres()

### Community 166 - "Community 166"
Cohesion: 0.20
Nodes (3): BodyWeight, Diver, RecordingSeries

### Community 167 - "Community 167"
Cohesion: 0.22
Nodes (9): cylinderText(), diverText(), levelsText(), pick(), sharedOf(), toolsHref(), toolsTabHref(), of() (+1 more)

### Community 168 - "Community 168"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 169 - "Community 169"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 170 - "Community 170"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 171 - "Community 171"
Cohesion: 0.28
Nodes (7): ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, s(), ADR-0035

### Community 172 - "Community 172"
Cohesion: 0.25
Nodes (7): ExternalIdView, siteOrigin(), ADR-0021, offer, osm, ssi, wikidata

### Community 173 - "Community 173"
Cohesion: 0.25
Nodes (8): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, MODULE_IMPORTS, moduleRules, target()

### Community 174 - "Community 174"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 175 - "Community 175"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 177 - "Community 177"
Cohesion: 0.29
Nodes (4): initI18n(), resources, ADR-0014, ADR-0014

### Community 178 - "Community 178"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 179 - "Community 179"
Cohesion: 0.29
Nodes (6): itemIds(), ADR-0026, ADR-0034, Usage, UsageDive, UsageRow

### Community 180 - "Community 180"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 181 - "Community 181"
Cohesion: 0.48
Nodes (7): cylinderOf(), diverOf(), finite(), levelsOf(), oneOf(), pickDefined(), toolsParams()

### Community 182 - "Community 182"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 183 - "Community 183"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 185 - "Community 185"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 186 - "Community 186"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 188 - "Community 188"
Cohesion: 0.67
Nodes (3): planSketch(), shape(), ADR-0033

### Community 190 - "Community 190"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 191 - "Community 191"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 192 - "Community 192"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1237 isolated node(s):** `Off`, `Reserve`, `RockBottom`, `ADR-0013`, `ADR-0011` (+1232 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3291 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **64 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `@tanstack/react-query` connect `Community 1` to `Community 2`, `Community 3`, `Community 5`, `Community 71`, `Community 10`, `Community 18`, `Community 84`, `Community 21`, `Community 22`, `Community 24`, `Community 91`, `Community 30`, `Community 127`?**
  _High betweenness centrality (0.021) - this node is a cross-community bridge._
- **What connects `Off`, `Reserve`, `RockBottom` to the rest of the system?**
  _1237 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.02702788844621514 - nodes in this community are weakly interconnected._
- **Why does `drizzle-orm` connect `Community 0` to `Community 128`, `Community 4`, `Community 6`, `Community 135`, `Community 8`, `Community 9`, `Community 7`, `Community 11`, `Community 140`, `Community 14`, `Community 15`, `Community 16`, `Community 145`, `Community 17`, `Community 19`, `Community 20`, `Community 28`, `Community 29`, `Community 32`, `Community 33`, `Community 35`, `Community 45`, `Community 179`, `Community 58`, `Community 60`, `Community 64`, `Community 66`, `Community 67`, `Community 83`, `Community 93`, `Community 98`, `Community 106`, `Community 109`, `Community 112`, `Community 116`?**
  _High betweenness centrality (0.018) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.016904761904761905 - nodes in this community are weakly interconnected._
- **Why does `react` connect `Community 24` to `Community 1`, `Community 2`, `Community 3`, `Community 5`, `Community 71`, `Community 10`, `Community 18`, `Community 84`, `Community 21`, `Community 22`, `Community 91`, `Community 30`, `Community 127`?**
  _High betweenness centrality (0.014) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.05105527638190955 - nodes in this community are weakly interconnected._