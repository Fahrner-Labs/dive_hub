# Graph Report - dive_hub  (2026-11-19)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 9203 nodes · 27842 edges · 277 communities (198 shown, 79 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 456 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `2db23205`
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
- Community 203
- Community 204
- Community 206
- Community 207
- Community 208
- Community 209
- Community 212

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
- `NumberChecks` --references--> `SsiClient`  [EXTRACTED]
  apps/server/test/fixtures/ssi/number-checks.ts → apps/server/src/providers/ssi/ssi-client.ts
- `RepositoryHarness` --calls--> `LogbookRepository`  [INFERRED]
  apps/android/shared/src/commonTest/kotlin/dev/divehub/shared/logbook/LogbookTest.kt → apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/logbook/LogbookRepository.kt

## Import Cycles
- None detected.

## Communities (277 total, 79 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.01
Nodes (367): user, buddySuggestion, dive, diveSiteExternalId, jointDive, push, pushRun, revision (+359 more)

### Community 1 - "Community 1"
Cohesion: 0.02
Nodes (286): ComputerChoice, ConnectionRow, DiveImportMode, DiveKind, providerConflict, providerGoneDive, ProviderImportPlan, providerSiteData (+278 more)

### Community 2 - "Community 2"
Cohesion: 0.02
Nodes (256): CHANNELS, ADR-0036, ENGINE_VERSION, Tx, continuationAppend, device, diveAssessment, diveFinding (+248 more)

### Community 3 - "Community 3"
Cohesion: 0.02
Nodes (166): createServer(), auth, loadAuthSecret(), BackupServiceDeps, createServices(), ServiceParts, ADR-0027, ADR-0030 (+158 more)

### Community 4 - "Community 4"
Cohesion: 0.04
Nodes (196): DeleteUserDialog(), Invite(), LinkToPassOn(), SiteDataPanel(), UserRow(), Accesses(), AiAccessSetting(), CreateAccess() (+188 more)

### Community 5 - "Community 5"
Cohesion: 0.02
Nodes (199): diversQuery(), cylinderCatalogueQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery() (+191 more)

### Community 6 - "Community 6"
Cohesion: 0.02
Nodes (159): importQuery(), BuddyView, ConflictChoices, ConflictEntryView, ConflictFieldView, ConflictsOfConnection, connectionsQuery(), ConnectionView (+151 more)

### Community 7 - "Community 7"
Cohesion: 0.04
Nodes (109): name, private, type, ChangePassword(), DisplaySettings(), SecuritySettings(), Sessions(), useDescribeAgent() (+101 more)

### Community 8 - "Community 8"
Cohesion: 0.04
Nodes (115): label(), axe-core@^4.13.0, AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery() (+107 more)

### Community 9 - "Community 9"
Cohesion: 0.04
Nodes (112): Queryable, diverExternalId, diveSite, mergeProviderCylinder(), providerCylinderOf(), columnsOf(), leadOf(), leadTotalKg() (+104 more)

### Community 10 - "Community 10"
Cohesion: 0.05
Nodes (21): AvatarCircle(), DiveRow(), Error(), Fact(), Filters(), Loading(), LogbookList(), LogbookRoute() (+13 more)

### Community 11 - "Community 11"
Cohesion: 0.05
Nodes (101): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+93 more)

### Community 12 - "Community 12"
Cohesion: 0.04
Nodes (96): diveCentre, diveCentreSite, logbookCheckAnswer, logbookCheckDiveAnswer, UtcOffsetSource, keepApart(), keepDive(), keepRestored() (+88 more)

### Community 13 - "Community 13"
Cohesion: 0.02
Nodes (102): account, accountRelations, apikey, rateLimit, session, sessionRelations, ADR-0011, ADR-0012 (+94 more)

### Community 14 - "Community 14"
Cohesion: 0.04
Nodes (24): AppContainer, PreferencesInstanceUrlStore, AndroidDiveStore, DiveDetailUiState, DiveDetailViewModel, LogbookViewModel, MainViewModel, UiState (+16 more)

### Community 15 - "Community 15"
Cohesion: 0.04
Nodes (97): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+89 more)

### Community 16 - "Community 16"
Cohesion: 0.03
Nodes (71): ParticipantRole, createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities() (+63 more)

### Community 17 - "Community 17"
Cohesion: 0.04
Nodes (88): RequirementView, ExternalIdView, Position, running(), SiteImportArea, siteImportsQuery(), SiteImportView, siteRevisionsQuery() (+80 more)

### Community 18 - "Community 18"
Cohesion: 0.04
Nodes (48): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+40 more)

### Community 19 - "Community 19"
Cohesion: 0.03
Nodes (86): LEAD_PLACEMENTS, UTC_OFFSET_SOURCES, WEIGHTING_FELT, ADR-0025, WaterMismatch, waterMismatch(), withoutPressures(), Copies (+78 more)

### Community 20 - "Community 20"
Cohesion: 0.03
Nodes (74): ADR-0030, CertificationView, DeviceView, DiverDetailsView, ExternalDiverView, FoundDiverView, LoadingsView, ReadCodeView (+66 more)

### Community 21 - "Community 21"
Cohesion: 0.04
Nodes (82): CertificationService, DIVE_IMPORT_MODES, MATCHING_WINDOWS, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053, ErrorAnswer (+74 more)

### Community 22 - "Community 22"
Cohesion: 0.04
Nodes (76): Me, divesQuery(), DiveSummary, LogbookPage, PAGE_SIZE, siteQuery(), Buddies(), COUNT_OF (+68 more)

### Community 23 - "Community 23"
Cohesion: 0.04
Nodes (71): PARTICIPANT_ROLES, SUGGESTION_STATUSES, downsampleMinMax(), diveSummaryOf(), asksFor(), DIVE_ONLY_PATTERN, DiveCounts, DiveListQuery (+63 more)

### Community 24 - "Community 24"
Cohesion: 0.05
Nodes (69): importsQuery(), ImportView, uploadFile(), App(), BackupsPage, CentrePage, CentresPage, currentRoute() (+61 more)

### Community 25 - "Community 25"
Cohesion: 0.06
Nodes (35): timeZoneText(), DiveViewRecordingsItem, DiveViewRecordingsItemDevice, DiveViewRecordingsItemSummarySurfaceBreaksItem, RecordingSamplesView, RecordingSamplesViewSeriesItem, diveLocalTime(), offsetId() (+27 more)

### Community 26 - "Community 26"
Cohesion: 0.06
Nodes (63): authHandler(), authHeaders(), forwardCookies(), sessionIdOf(), userRole, AttemptLimit, clientKey(), limitAttempts() (+55 more)

### Community 27 - "Community 27"
Cohesion: 0.06
Nodes (69): BACKUP_FORMAT_VERSION, BackupArchiveError, BackupEncryptionMetadata, BackupManifest, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32Encode() (+61 more)

### Community 28 - "Community 28"
Cohesion: 0.05
Nodes (56): BACKUP_EXTENSION, BACKUP_SKIPPED_DIRS, BackupDecryptionKey, BackupEncryption, BackupPart, encryptionKeyFingerprint(), fileTreeChecksum(), openArchiveStreamingWith() (+48 more)

### Community 29 - "Community 29"
Cohesion: 0.06
Nodes (58): accountListEntry, connection, diveLease, diver, providerLease, tidyName(), DiverDetails, Caller (+50 more)

### Community 30 - "Community 30"
Cohesion: 0.06
Nodes (60): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+52 more)

### Community 31 - "Community 31"
Cohesion: 0.06
Nodes (62): Series, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining(), ssiDiveType() (+54 more)

### Community 32 - "Community 32"
Cohesion: 0.05
Nodes (16): ProviderSummary, ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDives, ProviderSummaryDataDivesExport (+8 more)

### Community 33 - "Community 33"
Cohesion: 0.09
Nodes (50): BottomTime, bottomTime(), BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen (+42 more)

### Community 34 - "Community 34"
Cohesion: 0.06
Nodes (52): HINT, Input, LoadingsInput, planGasAndBottomTime, ADR-0032, ADR-0035, Used(), BOTTOM_TIME_RULES (+44 more)

### Community 35 - "Community 35"
Cohesion: 0.05
Nodes (49): createSsiSiteSource(), DEFAULT_SSI_SITES_URL, createSiteSources(), ADR-0021, ADR-0027, createOverpassSource(), DEFAULT_OVERPASS_URL, Element (+41 more)

### Community 36 - "Community 36"
Cohesion: 0.06
Nodes (53): DIVE_FIELDS, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open() (+45 more)

### Community 37 - "Community 37"
Cohesion: 0.07
Nodes (11): DiveDetailScreenshotTest, DiveViewFromRecording, DiveViewFromRecordingStartsAt, DiveViewParticipantsItem, DiveViewParticipantsItemCertificationHint, DiveViewValues, DiveViewValuesStartsAt, DiveViewWaterMismatch (+3 more)

### Community 38 - "Community 38"
Cohesion: 0.07
Nodes (39): DiveBody(), DiveDetailRoute(), DiveDetailScreen(), errorText(), FactRow(), Facts(), Loading(), NeighbourButton() (+31 more)

### Community 39 - "Community 39"
Cohesion: 0.09
Nodes (46): Formats, dateTime(), GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult() (+38 more)

### Community 40 - "Community 40"
Cohesion: 0.05
Nodes (49): DiveValues, OverridableField, revisionsQuery(), RevisionView, UtcOffsetSource, CodeFigure(), DiveCodes(), showDiveCodes() (+41 more)

### Community 41 - "Community 41"
Cohesion: 0.05
Nodes (50): DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN, NO_RECORDING, notAtProvider(), ADR-0017 (+42 more)

### Community 42 - "Community 42"
Cohesion: 0.07
Nodes (43): avatarTone(), initials(), useEquipmentDueCount(), subscribeNarrow, subscribeRoomy, subscribeWide, useWide(), NavSection (+35 more)

### Community 43 - "Community 43"
Cohesion: 0.05
Nodes (52): BottomTimeInput, Conditions, GasMix, GasRule, LoadingDive, TissuesAfterDivesInput, NdlInput, AIR (+44 more)

### Community 44 - "Community 44"
Cohesion: 0.10
Nodes (50): buildApp(), assessmentRoutes(), AI_ACCESS_NAME_MAX, isAdmin(), requireAdmin(), requireUser(), userOf(), backupRoutes() (+42 more)

### Community 45 - "Community 45"
Cohesion: 0.06
Nodes (46): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+38 more)

### Community 46 - "Community 46"
Cohesion: 0.09
Nodes (6): DiveHubTheme(), GasPlanScreenshotTest, ToolsScreenshotTest, GasPlanToolDiver, GasPlanToolInput, GasPlanToolLevel

### Community 47 - "Community 47"
Cohesion: 0.05
Nodes (44): AssessmentService, AssessmentView, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView() (+36 more)

### Community 48 - "Community 48"
Cohesion: 0.07
Nodes (23): neighboursPath(), samplesPath(), durationMinutes(), mixName(), countOf(), FilterChip, filterChips(), LogbookFilter (+15 more)

### Community 49 - "Community 49"
Cohesion: 0.11
Nodes (48): withBuddies(), APP_KEYS, appRow(), BuddySaved, byId(), changedKeys(), deleteTestDive(), deleteTestEntries() (+40 more)

### Community 50 - "Community 50"
Cohesion: 0.07
Nodes (34): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+26 more)

### Community 51 - "Community 51"
Cohesion: 0.08
Nodes (5): ApiClient, ApiErrorBody, json(), LogbookRepositoryTest, RepositoryHarness

### Community 52 - "Community 52"
Cohesion: 0.06
Nodes (43): date(), FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position(), positive() (+35 more)

### Community 54 - "Community 54"
Cohesion: 0.10
Nodes (26): DiveViewRecordingsItemSummary, DiveViewRecordingsItemSummaryGasesItem, jsRound(), powInt(), roundTo(), Sac, SacCylinder, SacDive (+18 more)

### Community 55 - "Community 55"
Cohesion: 0.07
Nodes (42): EMPTY_SITE_IMPORT_COUNTS, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, distanceM(), Field (+34 more)

### Community 56 - "Community 56"
Cohesion: 0.06
Nodes (42): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, Services, DiveProviderAnswers, EquipmentService (+34 more)

### Community 57 - "Community 57"
Cohesion: 0.06
Nodes (42): ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, connectGarmin(), Connection, DatedDive (+34 more)

### Community 58 - "Community 58"
Cohesion: 0.08
Nodes (41): NoFly, getDiverLoadings, DEFAULT_GF_HIGH, depthSamples(), DiverDiveRows, DiveRow, endMsOf(), loadingDivesForReplay() (+33 more)

### Community 59 - "Community 59"
Cohesion: 0.09
Nodes (43): ProviderCylinder, stillDiffering(), shownFields(), baseDiveOf(), providerChangesOf(), ConflictView, Choice, Comparable (+35 more)

### Community 60 - "Community 60"
Cohesion: 0.06
Nodes (41): Change(), Changes(), historyRoute, Made(), REVISION_CAUSES, PositionSchema, AdoptBody, Country (+33 more)

### Community 61 - "Community 61"
Cohesion: 0.06
Nodes (29): ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS, eachFile(), readEntry(), ADR-0044, walk() (+21 more)

### Community 62 - "Community 62"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 63 - "Community 63"
Cohesion: 0.12
Nodes (43): Answer, answer(), CylinderMaterial, aluminium, carbon, steel, cylindersAtReserve(), CylinderTerm (+35 more)

### Community 64 - "Community 64"
Cohesion: 0.05
Nodes (40): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+32 more)

### Community 65 - "Community 65"
Cohesion: 0.06
Nodes (36): CYLINDER_MATERIALS, SUIT_UNDERGARMENTS, DiverCertifications, replyDiverError, STATUS, CertificationSummary, DiverDetailsView, DiverDetailsViewSchema (+28 more)

### Community 66 - "Community 66"
Cohesion: 0.09
Nodes (33): LogbookCheckService, AppendDeps, AppendOutcome, AppendService, Side, ADR-0038, ADR-0063, ContinuationService (+25 more)

### Community 67 - "Community 67"
Cohesion: 0.10
Nodes (37): ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres(), metresPerBar(), PLANNING_WATERS (+29 more)

### Community 68 - "Community 68"
Cohesion: 0.07
Nodes (35): sampleCount(), rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject(), MAX_CARD_IMAGE_BYTES (+27 more)

### Community 69 - "Community 69"
Cohesion: 0.11
Nodes (40): deletedDivesOn(), deletedDivesQuery(), diveQuery(), restorePreviewQuery(), ProviderStatusView, DeletedDives(), DeleteDialog(), DeletedNotices() (+32 more)

### Community 70 - "Community 70"
Cohesion: 0.09
Nodes (34): diveWith(), ADR-0028, ADR-0043, resetSource(), stubDive(), ADR-0015, ADR-0023, ADR-0025 (+26 more)

### Community 71 - "Community 71"
Cohesion: 0.08
Nodes (29): acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), AppLockProbe (+21 more)

### Community 72 - "Community 72"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 73 - "Community 73"
Cohesion: 0.13
Nodes (8): FakeCache, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore, UserScopedStore

### Community 74 - "Community 74"
Cohesion: 0.08
Nodes (34): @modelcontextprotocol/server@^2.3.1, aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, AiAccessService, KeyRow, LOG_DAYS (+26 more)

### Community 75 - "Community 75"
Cohesion: 0.07
Nodes (32): CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError, CertificationExpiry, CertificationInput, CertificationView, createCertificationService(), cleaned() (+24 more)

### Community 76 - "Community 76"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 77 - "Community 77"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 78 - "Community 78"
Cohesion: 0.13
Nodes (9): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), GasPlanInputTest (+1 more)

### Community 79 - "Community 79"
Cohesion: 0.12
Nodes (35): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+27 more)

### Community 80 - "Community 80"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 81 - "Community 81"
Cohesion: 0.09
Nodes (9): Answers, number(), ToolsViewModel, ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits (+1 more)

### Community 82 - "Community 82"
Cohesion: 0.08
Nodes (35): SUIT_TYPES, seriesOf(), minute(), minutes(), round(), startTimes(), Channel, CHANNEL_UNITS (+27 more)

### Community 83 - "Community 83"
Cohesion: 0.08
Nodes (28): adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, AuthOptions, CLIENT_IP_HEADER, createAuth(), PUBLIC_AUTH_PATHS, SessionUser (+20 more)

### Community 84 - "Community 84"
Cohesion: 0.08
Nodes (17): HINT_CHANNEL, parseHint(), createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener() (+9 more)

### Community 85 - "Community 85"
Cohesion: 0.09
Nodes (33): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+25 more)

### Community 86 - "Community 86"
Cohesion: 0.07
Nodes (27): headers, ADR-0030, cancelWaitingImports(), conflictForLena(), diveWithoutRecording(), editInSsi(), importLena(), leaveLena() (+19 more)

### Community 87 - "Community 87"
Cohesion: 0.09
Nodes (28): CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite(), lock() (+20 more)

### Community 88 - "Community 88"
Cohesion: 0.15
Nodes (10): planningSac(), PlanningSacDive, PlanningSacResult, PlanningSacSource, default, logbook, BottomTimeToolInput, GasNumbersInput (+2 more)

### Community 89 - "Community 89"
Cohesion: 0.12
Nodes (27): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+19 more)

### Community 90 - "Community 90"
Cohesion: 0.06
Nodes (24): Series, StrayOverride, ChannelSeries, RecordedGas, Tank, TiedCylinder, ADR-0045, readings (+16 more)

### Community 91 - "Community 91"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 92 - "Community 92"
Cohesion: 0.11
Nodes (23): ADR-0051, Status, ADR-0056, clearPushRuns(), clearQueue(), conflictAtSsi(), connectSsi(), editAtSsi() (+15 more)

### Community 93 - "Community 93"
Cohesion: 0.14
Nodes (5): LogbookScreenshotTest, ApiDivesGet200Response, ApiDivesGet200ResponseCounts, ApiDivesGet200ResponseDivesItemParticipantsItem, ApiDivesGet200ResponseTotals

### Community 94 - "Community 94"
Cohesion: 0.10
Nodes (7): DiveView, DiveViewLeadItem, DiveViewPosition, DiveViewSac, DiveViewSite, DiveViewSuit, DiveViewWeightingFeedback

### Community 95 - "Community 95"
Cohesion: 0.09
Nodes (27): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+19 more)

### Community 96 - "Community 96"
Cohesion: 0.09
Nodes (26): Suit, WeightingFeedback, LeadEstimateAnswer, AL80, EstimateDiveCylinder, LEAD_CASES, leadInput(), LeadVectorDive (+18 more)

### Community 97 - "Community 97"
Cohesion: 0.10
Nodes (27): ScheduleDue, DiveGear, DueEntry, ensureItemForDevice(), GearItem, ItemDetailView, ItemEdit, ItemInput (+19 more)

### Community 98 - "Community 98"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 99 - "Community 99"
Cohesion: 0.10
Nodes (25): AiAccessScope, diversOf(), Cursor, cursorAt(), DateOnly(), diversAsked(), inputProblem(), loggable() (+17 more)

### Community 100 - "Community 100"
Cohesion: 0.11
Nodes (7): ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh, ApiToolsBottomTimeGet200ResponseUsedSacLpm, ApiToolsBottomTimeGet200ResponseUsedWater

### Community 102 - "Community 102"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 103 - "Community 103"
Cohesion: 0.13
Nodes (10): drawBand(), ProfileSketch(), AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint, SketchProfile (+2 more)

### Community 104 - "Community 104"
Cohesion: 0.11
Nodes (23): AssessmentError, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), requireManagedDive(), isRule() (+15 more)

### Community 105 - "Community 105"
Cohesion: 0.11
Nodes (19): RecordingSummary, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput, round3() (+11 more)

### Community 106 - "Community 106"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 107 - "Community 107"
Cohesion: 0.11
Nodes (21): createFileFormats(), createFitAdapter(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf() (+13 more)

### Community 108 - "Community 108"
Cohesion: 0.16
Nodes (24): DiveOrigin, REFERENCE_PREFIX, SsiLogbook, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef() (+16 more)

### Community 109 - "Community 109"
Cohesion: 0.10
Nodes (19): diverLoadingsQuery(), CurrentLoadings(), CustomTypeOptions, i18next, initI18n(), resources, ADR-0014, Language (+11 more)

### Community 110 - "Community 110"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 111 - "Community 111"
Cohesion: 0.14
Nodes (24): managementOf(), EquipmentError, ADR-0034, ADR-0053, checkCost(), checkDates(), checkWindow(), createEquipmentService() (+16 more)

### Community 112 - "Community 112"
Cohesion: 0.10
Nodes (24): SiteRow, Detail, excerpt(), Paging, SharedText(), ToolContext, buddies, DueCount (+16 more)

### Community 113 - "Community 113"
Cohesion: 0.11
Nodes (20): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+12 more)

### Community 114 - "Community 114"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 115 - "Community 115"
Cohesion: 0.12
Nodes (19): BackupRouteDeps, BackupService, backupTask(), ADR-0069, PurgeService, EXPIRE_WAITING_IMPORTS_TASK, AUTOMATION_TICK_TASK, IMPORT_DIVE_SITES_TASK (+11 more)

### Community 116 - "Community 116"
Cohesion: 0.11
Nodes (22): CENTRE_SOURCE_NAME, CENTRE_SOURCES, CentreService, CentreView, CentreViewSchema, Row, Source, toCentreView() (+14 more)

### Community 117 - "Community 117"
Cohesion: 0.11
Nodes (17): ProblemCode, ResponseState, whenResponseGone(), StoredBlob, PlacedArchive, ANSWER, looksLikeRecipientPrivateKey(), PlacedArchive (+9 more)

### Community 118 - "Community 118"
Cohesion: 0.10
Nodes (15): SsiClient, ClearChecks, clearDive(), findIn(), finishClearChecks(), GAS, isSet(), KEYS (+7 more)

### Community 119 - "Community 119"
Cohesion: 0.08
Nodes (24): devDependencies, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2, @testing-library/react@^16.3.3 (+16 more)

### Community 120 - "Community 120"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 121 - "Community 121"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 122 - "Community 122"
Cohesion: 0.12
Nodes (3): DiveStore, BodyWeight, RecordingSeries

### Community 123 - "Community 123"
Cohesion: 0.13
Nodes (6): ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting

### Community 124 - "Community 124"
Cohesion: 0.08
Nodes (24): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+16 more)

### Community 125 - "Community 125"
Cohesion: 0.11
Nodes (14): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+6 more)

### Community 126 - "Community 126"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 128 - "Community 128"
Cohesion: 0.10
Nodes (19): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+11 more)

### Community 129 - "Community 129"
Cohesion: 0.12
Nodes (19): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+11 more)

### Community 130 - "Community 130"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 131 - "Community 131"
Cohesion: 0.15
Nodes (18): qr@0.7.2, cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043 (+10 more)

### Community 132 - "Community 132"
Cohesion: 0.15
Nodes (5): ApiDivesGet200ResponseDivesItem, ApiDivesGet200ResponseDivesItemGasesItem, ApiDivesGet200ResponseDivesItemSite, DiveProfileView, WaitingPushView

### Community 133 - "Community 133"
Cohesion: 0.13
Nodes (4): toBoolean(), toLong(), toModel(), Recording

### Community 134 - "Community 134"
Cohesion: 0.17
Nodes (18): errorMessage(), looksLikeZip(), formatOf(), openUpload(), processFile(), processImport(), failureOf(), kindOf() (+10 more)

### Community 135 - "Community 135"
Cohesion: 0.09
Nodes (13): out, ADR-0036, out, ADR-0070, { first, second }, ADR-0063, out, ADR-0026 (+5 more)

### Community 136 - "Community 136"
Cohesion: 0.15
Nodes (9): MainActivity, DiveHubApp(), ErrorText(), InstanceForm(), Loading(), PasswordField(), PrimaryButton(), SignInForm() (+1 more)

### Community 137 - "Community 137"
Cohesion: 0.13
Nodes (18): siteImportStatus, SiteImportRouteDeps, StartBody, STATUS, ADR-0021, SiteImportError, SiteImportService, Area (+10 more)

### Community 138 - "Community 138"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 139 - "Community 139"
Cohesion: 0.16
Nodes (5): ApiToolsBottomTimeGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseLimits, ApiToolsBottomTimeGet200ResponseResidualN2, CommonAnswer

### Community 140 - "Community 140"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 141 - "Community 141"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 142 - "Community 142"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 143 - "Community 143"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 144 - "Community 144"
Cohesion: 0.21
Nodes (14): findChangedMigrations(), checkClone(), createE2eDatabase(), E2eDatabase, ensureTemplate(), identifier(), migrateTemplate(), migrationKey() (+6 more)

### Community 145 - "Community 145"
Cohesion: 0.12
Nodes (16): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+8 more)

### Community 146 - "Community 146"
Cohesion: 0.20
Nodes (6): ApiDivesGet200ResponseMonthsItem, localMonthOf(), LogbookMonth, monthGroups(), dive(), MonthGroupsTest

### Community 147 - "Community 147"
Cohesion: 0.15
Nodes (14): FindingValues, ASSESSMENT_NOTE, FINDING_TEXT, n(), NO_FLY_TEXT, Text, ADR-0035, ownDive() (+6 more)

### Community 148 - "Community 148"
Cohesion: 0.14
Nodes (15): serviceRecordSchedule, serviceSchedule, equipmentList(), EquipmentListFilter, EquipmentRecordRead, today(), ADR-0034, ADR-0035 (+7 more)

### Community 149 - "Community 149"
Cohesion: 0.15
Nodes (13): Sac, SAC_MISSING, SacMissing, sacOfDive(), SacValues, sacValues(), ADR-0033, ADR-0045 (+5 more)

### Community 150 - "Community 150"
Cohesion: 0.23
Nodes (15): cnsDecayed(), Estimate, isoUtc(), LoadingBasis, LoadingDive, N2Loadings, N2UnavailableReason, no_profile (+7 more)

### Community 151 - "Community 151"
Cohesion: 0.19
Nodes (13): asEstimateInput(), evaluateLeadVectorCase(), LEAD_VECTOR_KERNEL, leadVectorFile(), VECTOR_KERNELS, VectorFile, vectorFile(), assertMatch() (+5 more)

### Community 152 - "Community 152"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 153 - "Community 153"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 154 - "Community 154"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 155 - "Community 155"
Cohesion: 0.20
Nodes (13): dayNumber(), enteredDecoFromNdl(), minNdlSeconds(), NoFly, noFlyAfter(), NoFlyReason, decompression, several (+5 more)

### Community 156 - "Community 156"
Cohesion: 0.19
Nodes (11): decimal(), dive(), makeOceanicPlusUddf(), OCEANIC_DIVES, OceanicDive, site(), waypoint(), exported (+3 more)

### Community 157 - "Community 157"
Cohesion: 0.18
Nodes (11): addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, relations(), TimelineBar, TimelineBarGeometry, TimelineGapGeometry, TimelineGeometry (+3 more)

### Community 158 - "Community 158"
Cohesion: 0.15
Nodes (10): FIT_EPOCH_MS, makeSuuntoAppFit(), shape(), SuuntoDiveOptions, ADR-0006, ADR-0037, out, ADR-0038 (+2 more)

### Community 159 - "Community 159"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 160 - "Community 160"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 161 - "Community 161"
Cohesion: 0.17
Nodes (10): EstimateInput, AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031 (+2 more)

### Community 162 - "Community 162"
Cohesion: 0.17
Nodes (6): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, uniqueWord()

### Community 163 - "Community 163"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 164 - "Community 164"
Cohesion: 0.26
Nodes (3): ApiMeGet200Response, ApiMeGet200ResponseOwnDiver, ApiMeGet200ResponsePreferences

### Community 165 - "Community 165"
Cohesion: 0.26
Nodes (3): DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewCylindersItemSeries

### Community 166 - "Community 166"
Cohesion: 0.26
Nodes (3): DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 167 - "Community 167"
Cohesion: 0.21
Nodes (9): Config, loadConfig(), ADR-0011, ADR-0021, ADR-0024, ADR-0025, config, parseEncryptionKey() (+1 more)

### Community 168 - "Community 168"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 169 - "Community 169"
Cohesion: 0.18
Nodes (11): BodyWeight, Cylinder, EstimateAdjustment, EstimateMissing, PersonalOffset, SuitThickness, SuitUndergarment, heavy (+3 more)

### Community 170 - "Community 170"
Cohesion: 0.18
Nodes (5): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053

### Community 171 - "Community 171"
Cohesion: 0.29
Nodes (9): Doc, isObjectSchema(), mediaSchemas(), placesOf(), placesOfOperation(), Schema, sharedInlineSchemas(), sizable() (+1 more)

### Community 172 - "Community 172"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 173 - "Community 173"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 174 - "Community 174"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 175 - "Community 175"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 176 - "Community 176"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 177 - "Community 177"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 178 - "Community 178"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 179 - "Community 179"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 180 - "Community 180"
Cohesion: 0.28
Nodes (7): ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, s(), ADR-0035

### Community 181 - "Community 181"
Cohesion: 0.25
Nodes (8): defineTool(), Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), NOTICE, PLAN_RULES

### Community 182 - "Community 182"
Cohesion: 0.25
Nodes (5): GONE_WARNING_MIN, GoneCandidate, GoneComparison, ADR-0051, ADR-0051

### Community 184 - "Community 184"
Cohesion: 0.25
Nodes (5): dumpToolAvailable(), execFileAsync, migrationsFolder, silent, ADR-0069

### Community 185 - "Community 185"
Cohesion: 0.25
Nodes (8): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, MODULE_IMPORTS, moduleRules, target()

### Community 186 - "Community 186"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 187 - "Community 187"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 188 - "Community 188"
Cohesion: 0.29
Nodes (5): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, EstimateCylinder

### Community 189 - "Community 189"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 190 - "Community 190"
Cohesion: 0.38
Nodes (7): baseFrom(), densityRatio(), neededIn(), SiteWaterType, brackish, fresh, salt

### Community 193 - "Community 193"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 194 - "Community 194"
Cohesion: 0.33
Nodes (6): SuitType, drysuit, none, semidry, skin, wetsuit

### Community 196 - "Community 196"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 198 - "Community 198"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 199 - "Community 199"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 200 - "Community 200"
Cohesion: 0.40
Nodes (4): TAGS, ADR-0023, { stdout, stderr }, total

### Community 203 - "Community 203"
Cohesion: 0.50
Nodes (4): Confidence, high, low, medium

### Community 204 - "Community 204"
Cohesion: 0.50
Nodes (4): WeightingFelt, right, too_heavy, too_light

### Community 206 - "Community 206"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 207 - "Community 207"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 208 - "Community 208"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1261 isolated node(s):** `SuitThickness`, `Reserve`, `RockBottom`, `Off`, `ADR-0013` (+1256 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3334 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **79 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 0` to `Community 1`, `Community 2`, `Community 3`, `Community 9`, `Community 12`, `Community 13`, `Community 144`, `Community 148`, `Community 23`, `Community 26`, `Community 28`, `Community 29`, `Community 34`, `Community 41`, `Community 56`, `Community 58`, `Community 66`, `Community 74`, `Community 75`, `Community 85`, `Community 87`, `Community 95`, `Community 97`, `Community 99`, `Community 124`?**
  _High betweenness centrality (0.025) - this node is a cross-community bridge._
- **What connects `SuitThickness`, `Reserve`, `RockBottom` to the rest of the system?**
  _1261 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.013401387911093231 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 20` to `Community 4`, `Community 5`, `Community 6`, `Community 7`, `Community 8`, `Community 69`, `Community 40`, `Community 11`, `Community 140`, `Community 72`, `Community 17`, `Community 22`, `Community 24`?**
  _High betweenness centrality (0.021) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.016395418927064496 - nodes in this community are weakly interconnected._
- **Why does `react` connect `Community 7` to `Community 131`, `Community 4`, `Community 69`, `Community 6`, `Community 5`, `Community 8`, `Community 40`, `Community 72`, `Community 11`, `Community 140`, `Community 42`, `Community 17`, `Community 20`, `Community 22`, `Community 24`?**
  _High betweenness centrality (0.017) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.015343405121611486 - nodes in this community are weakly interconnected._