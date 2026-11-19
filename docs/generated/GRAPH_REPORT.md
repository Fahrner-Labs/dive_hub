# Graph Report - dive_hub  (2026-11-19)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 9990 nodes · 30195 edges · 319 communities (199 shown, 120 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 532 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `5d79bf6b`
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
- Community 206
- Community 207
- Community 208
- Community 209
- Community 210
- Community 214

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 204 edges
2. `Button()` - 198 edges
3. `useErrorText()` - 169 edges
4. `useDisplay()` - 161 edges
5. `Muted()` - 139 edges
6. `drizzle-orm` - 139 edges
7. `unwrap()` - 138 edges
8. `announce()` - 106 edges
9. `createTestDatabase()` - 104 edges
10. `TestDatabase` - 103 edges

## Surprising Connections (you probably didn't know these)
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `AuthOptions` --references--> `Db`  [EXTRACTED]
  apps/server/src/auth/auth.ts → apps/server/src/db/client.ts
- `StoreLogbookTest` --calls--> `StoreLogbook`  [INFERRED]
  apps/android/shared/src/jvmTest/kotlin/dev/divehub/shared/logbook/StoreLogbookTest.kt → apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/logbook/StoreLogbook.kt

## Import Cycles
- None detected.

## Communities (319 total, 120 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.01
Nodes (385): user, buddySuggestion, dive, diveAssessment, diveFinding, diveLease, diveSiteExternalId, duplicateCandidate (+377 more)

### Community 1 - "Community 1"
Cohesion: 0.02
Nodes (295): ServiceParts, Db, ComputerChoice, connection, ConnectionRow, DiveImportMode, DiveKind, importJob (+287 more)

### Community 2 - "Community 2"
Cohesion: 0.04
Nodes (237): ChangePassword(), DisplaySettings(), SecuritySettings(), Sessions(), useDescribeAgent(), AdminSettings(), DeleteUserDialog(), Invitations() (+229 more)

### Community 3 - "Community 3"
Cohesion: 0.02
Nodes (172): createServer(), createAuth(), auth, hashPassword(), loadAuthSecret(), acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS (+164 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (213): Queryable, EMPTY_SITE_IMPORT_COUNTS, ImportArea, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025 (+205 more)

### Community 5 - "Community 5"
Cohesion: 0.02
Nodes (190): diverBodyWeight, lead, LeadPlacement, logbookCheckAnswer, logbookCheckDiveAnswer, OVERRIDABLE_FIELDS, OverridableField, ParticipantRole (+182 more)

### Community 6 - "Community 6"
Cohesion: 0.02
Nodes (198): DiveView, cylinderCatalogueQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery() (+190 more)

### Community 7 - "Community 7"
Cohesion: 0.02
Nodes (190): importQuery(), keys, buddiesForQuery(), BuddyView, ConflictChoices, ConflictEntryView, ConflictFieldView, ConflictsOfConnection (+182 more)

### Community 8 - "Community 8"
Cohesion: 0.02
Nodes (143): CentreService, CentreRouteDeps, LEAD_PLACEMENTS, WEIGHTING_FELT, DiverService, ADR-0025, WaterMismatch, waterMismatch() (+135 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (142): divesQuery(), DiveSummary, DiveValues, LogbookPage, OverridableField, PAGE_SIZE, revisionsQuery(), RevisionView (+134 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (150): SOURCES, downsampleMinMax(), Nullable(), AiAccessScope, seesPositions(), buddiesOf(), BuddySearch, count() (+142 more)

### Community 11 - "Community 11"
Cohesion: 0.02
Nodes (122): account, accountRelations, apikey, rateLimit, session, sessionRelations, ADR-0011, ADR-0012 (+114 more)

### Community 12 - "Community 12"
Cohesion: 0.04
Nodes (112): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+104 more)

### Community 13 - "Community 13"
Cohesion: 0.02
Nodes (104): CertificationView, DeviceView, DiverDetailsView, ExternalDiverView, FoundDiverView, LoadingsView, ReadCodeView, ADR-0028 (+96 more)

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (102): Tx, accountListEntry, diveCentre, diveCentreSite, diver, participant, DiverError, ADR-0016 (+94 more)

### Community 15 - "Community 15"
Cohesion: 0.04
Nodes (114): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, AssessmentService, AssessmentView (+106 more)

### Community 16 - "Community 16"
Cohesion: 0.04
Nodes (111): label(), Me, AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery() (+103 more)

### Community 17 - "Community 17"
Cohesion: 0.03
Nodes (96): dependencies, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0, qr@0.7.2, react@^19.3.0 (+88 more)

### Community 18 - "Community 18"
Cohesion: 0.04
Nodes (98): aiAccessSettingQuery(), BackupRunView, backupSettingsQuery(), BackupSettingsView, backupsQuery(), SiteDataView, ADR-0030, importsQuery() (+90 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (92): CertificationService, CYLINDER_MATERIALS, DIVE_IMPORT_MODES, DIVE_KINDS, IMPORT_ERROR_CODES, MATCHING_WINDOWS, OUTCOME_REASONS, UTC_OFFSET_SOURCES (+84 more)

### Community 20 - "Community 20"
Cohesion: 0.04
Nodes (14): AndroidDiveStore, DiveStore, toBoolean(), toLong(), toModel(), BodyWeight, DiveSite, Recording (+6 more)

### Community 21 - "Community 21"
Cohesion: 0.03
Nodes (66): ProviderImportPlan, Delivered, Direction, DiveExport, DiveListing, ExportingProvider, FIND_BY, FindBy (+58 more)

### Community 22 - "Community 22"
Cohesion: 0.04
Nodes (17): MainActivity, MainViewModel, UiState, LoadingsUiState, LoadingsViewModel, ApiMeGet200Response, ApiMeGet200ResponsePreferences, SignInEmailRequest (+9 more)

### Community 23 - "Community 23"
Cohesion: 0.04
Nodes (48): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+40 more)

### Community 24 - "Community 24"
Cohesion: 0.04
Nodes (76): adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, Auth, AuthOptions, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, ADR-0011 (+68 more)

### Community 25 - "Community 25"
Cohesion: 0.06
Nodes (79): allButDive(), continuationCandidateQuery(), ContinuationCandidateView, deletedDivesOn(), deletedDivesQuery(), diveQuery(), mergeCandidatesQuery(), nearbyDivesQuery() (+71 more)

### Community 26 - "Community 26"
Cohesion: 0.05
Nodes (25): DiveDetailUiState, DiveDetailViewModel, LogbookViewModel, ApiDivesGet200ResponseCounts, DiveRepository, neighboursPath(), countOf(), filterChips() (+17 more)

### Community 27 - "Community 27"
Cohesion: 0.07
Nodes (64): Formats, GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult(), PlanInputs() (+56 more)

### Community 28 - "Community 28"
Cohesion: 0.05
Nodes (65): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+57 more)

### Community 30 - "Community 30"
Cohesion: 0.04
Nodes (66): SUGGESTION_STATUSES, DiveProfileView, DiveProfileViewSchema, ADR-0041, Change(), Changes(), historyRoute, Made() (+58 more)

### Community 31 - "Community 31"
Cohesion: 0.04
Nodes (58): BACKUP_RUN_TASK, ImportErrorCode, ImportFound, importOriginal, ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS (+50 more)

### Community 32 - "Community 32"
Cohesion: 0.05
Nodes (64): PARTICIPANT_ROLES, siteImportStatus, clientKey(), ADR-0061, ErrorAnswer, Problem, PROBLEM_CODES, providerProblem() (+56 more)

### Community 33 - "Community 33"
Cohesion: 0.07
Nodes (15): LogbookScreenshotTest, ApiDivesGet200Response, ApiDivesGet200ResponseDivesItem, ApiDivesGet200ResponseDivesItemGasesItem, ApiDivesGet200ResponseDivesItemParticipantsItem, ApiDivesGet200ResponseDivesItemSite, ApiDivesGet200ResponseMonthsItem, ApiDivesGet200ResponseTotals (+7 more)

### Community 34 - "Community 34"
Cohesion: 0.07
Nodes (41): DiveBody(), DiveDetailRoute(), DiveDetailScreen(), errorText(), FactRow(), Facts(), Loading(), NeighbourButton() (+33 more)

### Community 35 - "Community 35"
Cohesion: 0.08
Nodes (13): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), GasPlanInputTest (+5 more)

### Community 36 - "Community 36"
Cohesion: 0.08
Nodes (49): BottomTime, BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen, BottomTimeLimits (+41 more)

### Community 37 - "Community 37"
Cohesion: 0.04
Nodes (45): createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities(), providerError() (+37 more)

### Community 38 - "Community 38"
Cohesion: 0.05
Nodes (20): drawBand(), ProfileSketch(), DiveProfileView, initials(), mixName(), LogbookPaging, AscentBand, SketchCurve (+12 more)

### Community 39 - "Community 39"
Cohesion: 0.07
Nodes (9): DiveDetailScreenshotTest, DiveViewFromRecording, DiveViewFromRecordingStartsAt, DiveViewParticipantsItem, DiveViewParticipantsItemCertificationHint, DiveViewWaterMismatch, NeighboursView, NeighboursViewNext (+1 more)

### Community 40 - "Community 40"
Cohesion: 0.05
Nodes (16): ApiDiverBodyWeightsGet200ResponseItem, ApiDiveSitesGet200Response, ApiMeGet200ResponseOwnDiver, ApiToolsBottomTimeGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseLimits, ApiToolsBottomTimeGet200ResponseResidualN2, DiveViewPosition (+8 more)

### Community 41 - "Community 41"
Cohesion: 0.06
Nodes (56): Leave, Series, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+48 more)

### Community 42 - "Community 42"
Cohesion: 0.06
Nodes (45): SsiClient, SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), emptyRecord(), writableRecord(), FakeSsi (+37 more)

### Community 43 - "Community 43"
Cohesion: 0.07
Nodes (58): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+50 more)

### Community 44 - "Community 44"
Cohesion: 0.07
Nodes (26): ProfileTable(), DiveViewRecordingsItem, DiveViewRecordingsItemDevice, DiveViewRecordingsItemSummary, DiveViewRecordingsItemSummarySurfaceBreaksItem, RecordingSamplesView, RecordingSamplesViewSeriesItem, depthSeries() (+18 more)

### Community 45 - "Community 45"
Cohesion: 0.09
Nodes (10): Cylinder, Dive, Diver, toStore(), json(), LogbookRepositoryTest, RepositoryHarness, FakeInstances (+2 more)

### Community 46 - "Community 46"
Cohesion: 0.05
Nodes (51): asksFor(), DIVE_ONLY, DIVE_ONLY_PATTERN, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN, NO_RECORDING (+43 more)

### Community 47 - "Community 47"
Cohesion: 0.05
Nodes (51): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, cancelWaitingImports() (+43 more)

### Community 48 - "Community 48"
Cohesion: 0.06
Nodes (6): AppContainer, KeystoreTokenStore, PreferencesInstanceUrlStore, UserCacheStore, InstanceUrlStore, SyncRefresher

### Community 49 - "Community 49"
Cohesion: 0.05
Nodes (48): CENTRE_SOURCE_NAME, CENTRE_SOURCES, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder() (+40 more)

### Community 50 - "Community 50"
Cohesion: 0.07
Nodes (51): agreeInDepthAndDuration(), CheckedDive, DeletableDive, findChecks(), followsSoon(), LOGBOOK_CHECK_RULES, LOGBOOK_CHECKS_VERSION, LogbookCheck (+43 more)

### Community 51 - "Community 51"
Cohesion: 0.12
Nodes (27): Body, Hold, Offline, RefreshData, refreshDeletedJson(), refreshDetailJson(), refreshDetailsPage(), refreshDiveId() (+19 more)

### Community 52 - "Community 52"
Cohesion: 0.08
Nodes (51): BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32Encode(), bech32HrpExpand(), bech32Polymod() (+43 more)

### Community 53 - "Community 53"
Cohesion: 0.09
Nodes (10): Complete, SessionEnded, Stopped, SyncEngine, SyncPhase, Details, Meta, Profiles (+2 more)

### Community 54 - "Community 54"
Cohesion: 0.06
Nodes (34): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+26 more)

### Community 56 - "Community 56"
Cohesion: 0.07
Nodes (11): ApiClient, ApiErrorBody, Connected, Hint, HintFrame, LiveEvent, LiveHint, LiveHints (+3 more)

### Community 57 - "Community 57"
Cohesion: 0.14
Nodes (23): Body, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage(), diverJson(), diveStartsAtOf() (+15 more)

### Community 58 - "Community 58"
Cohesion: 0.07
Nodes (38): createSsiSiteSource(), DEFAULT_SSI_SITES_URL, createSiteSources(), ADR-0021, ADR-0027, createOverpassSource(), DEFAULT_OVERPASS_URL, Element (+30 more)

### Community 59 - "Community 59"
Cohesion: 0.06
Nodes (38): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+30 more)

### Community 60 - "Community 60"
Cohesion: 0.09
Nodes (48): Answer, answer(), baseFrom(), Confidence, high, low, medium, Cylinder (+40 more)

### Community 61 - "Community 61"
Cohesion: 0.10
Nodes (41): BottomTime, ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres(), isHypoxic() (+33 more)

### Community 62 - "Community 62"
Cohesion: 0.08
Nodes (38): bottomTime(), BottomTimeQuery, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033, ADR-0071 (+30 more)

### Community 63 - "Community 63"
Cohesion: 0.05
Nodes (41): NOAA_ROWS, otu(), oxygenLimitMinutes(), ADR-0032, AIR, BOTTOM_TIME_CASES, conditionsOf(), DAY (+33 more)

### Community 64 - "Community 64"
Cohesion: 0.09
Nodes (9): DiveHubTheme(), LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseCns, ApiDiversIdLoadingsGet200ResponseN2 (+1 more)

### Community 65 - "Community 65"
Cohesion: 0.08
Nodes (42): NoFly, DEFAULT_GF_HIGH, residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows(), DiveRow, endMsOf() (+34 more)

### Community 66 - "Community 66"
Cohesion: 0.07
Nodes (38): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+30 more)

### Community 67 - "Community 67"
Cohesion: 0.05
Nodes (42): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+34 more)

### Community 68 - "Community 68"
Cohesion: 0.06
Nodes (33): applyHints(), LiveArrivals, Page, useLiveArrivals(), bump(), changedListeners, ChannelMessage, healthListeners (+25 more)

### Community 69 - "Community 69"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 70 - "Community 70"
Cohesion: 0.09
Nodes (37): CylinderMaterial, aluminium, carbon, steel, EstimateDive, EstimateDiveCylinder, WeightingFeedback, WeightingFelt (+29 more)

### Community 71 - "Community 71"
Cohesion: 0.08
Nodes (12): Answers, number(), ToolsViewModel, ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits (+4 more)

### Community 72 - "Community 72"
Cohesion: 0.12
Nodes (8): FakeCache, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore, UserScopedStore

### Community 73 - "Community 73"
Cohesion: 0.06
Nodes (35): SUIT_TYPES, SUIT_UNDERGARMENTS, DiverCertifications, replyDiverError, STATUS, CertificationSummary, DiverDetailsView, DiverDetailsViewSchema (+27 more)

### Community 74 - "Community 74"
Cohesion: 0.08
Nodes (38): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+30 more)

### Community 75 - "Community 75"
Cohesion: 0.07
Nodes (37): BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, Conditions, GasMix, ASCENT_RATE_M_PER_MIN (+29 more)

### Community 76 - "Community 76"
Cohesion: 0.07
Nodes (33): SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject(), MAX_CARD_IMAGE_BYTES (+25 more)

### Community 77 - "Community 77"
Cohesion: 0.10
Nodes (32): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0051 (+24 more)

### Community 78 - "Community 78"
Cohesion: 0.11
Nodes (32): BackupDecryptionKey, encryptionKeyFingerprint(), openArchiveStreamingWith(), assertPgTool(), execFileAsync, pgCommand(), pgErrorDetail(), pgFailureMessage() (+24 more)

### Community 79 - "Community 79"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 80 - "Community 80"
Cohesion: 0.08
Nodes (31): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+23 more)

### Community 81 - "Community 81"
Cohesion: 0.08
Nodes (34): buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open(), saveAndReadBack(), saveBuddy() (+26 more)

### Community 82 - "Community 82"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 83 - "Community 83"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 84 - "Community 84"
Cohesion: 0.08
Nodes (9): DiveView, DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewCylindersItemSeries, DiveViewLeadItem, DiveViewSac, DiveViewSite, DiveViewSuit (+1 more)

### Community 85 - "Community 85"
Cohesion: 0.08
Nodes (33): AI_ACCESS_NAME_MAX, aiAccessSetting, Access, AccessView, AiAccessRouteDeps, LogEntryView, Scope, STATUS (+25 more)

### Community 86 - "Community 86"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 87 - "Community 87"
Cohesion: 0.09
Nodes (13): LeadAnswer, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseLeftOut, ApiDiversIdLeadEstimateGet200ResponseMissingItem, ApiDiversIdLeadEstimateGet200ResponseRange, ApiDiversIdLeadEstimateGet200ResponseReserve, ApiDiversIdLeadEstimateGet200ResponseWeightCheck (+5 more)

### Community 88 - "Community 88"
Cohesion: 0.13
Nodes (34): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+26 more)

### Community 89 - "Community 89"
Cohesion: 0.10
Nodes (29): AssessmentError, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), requireManagedDive() (+21 more)

### Community 90 - "Community 90"
Cohesion: 0.11
Nodes (27): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+19 more)

### Community 91 - "Community 91"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 92 - "Community 92"
Cohesion: 0.13
Nodes (18): AvatarCircle(), DiveRow(), Error(), Fact(), Filters(), Loading(), LogbookList(), LogbookRoute() (+10 more)

### Community 93 - "Community 93"
Cohesion: 0.17
Nodes (8): bottomTime(), otu(), oxygenLimitMinutes(), PlanningSacResult, BottomTimeToolInput, GasNumbersInput, ToolDiverSource, ToolsServiceTest

### Community 94 - "Community 94"
Cohesion: 0.07
Nodes (6): DiveHubApplication, SyncWorker, WorkManagerPeriodicSync, TokenStore, UserScopedStore, PeriodicSyncScheduler

### Community 95 - "Community 95"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 96 - "Community 96"
Cohesion: 0.09
Nodes (27): CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError, CertificationExpiry, CertificationInput, CertificationView, createCertificationService(), cleaned() (+19 more)

### Community 97 - "Community 97"
Cohesion: 0.10
Nodes (28): ScheduleDue, DiveGear, DueEntry, ensureItemForDevice(), GearItem, ItemDetailView, ItemEdit, ItemInput (+20 more)

### Community 98 - "Community 98"
Cohesion: 0.09
Nodes (27): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+19 more)

### Community 99 - "Community 99"
Cohesion: 0.25
Nodes (7): leadAnswer(), EstimateCylinder, PlannedConditions, Suit, currentLoadings(), leadEstimate(), PersonalizedToolsTest

### Community 100 - "Community 100"
Cohesion: 0.07
Nodes (29): dependencies, better-auth@1.7.7, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1 (+21 more)

### Community 101 - "Community 101"
Cohesion: 0.09
Nodes (23): computerEventOf(), ADR-0036, computerEventFromFit(), COMPUTER_EVENT, computerEventFromSuunto(), DECO_MODEL, decoModelFromSuunto, DIVE_MODE (+15 more)

### Community 102 - "Community 102"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 103 - "Community 103"
Cohesion: 0.10
Nodes (22): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearQueue(), conflictAtSsi() (+14 more)

### Community 104 - "Community 104"
Cohesion: 0.11
Nodes (7): ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh, ApiToolsBottomTimeGet200ResponseUsedSacLpm, ApiToolsBottomTimeGet200ResponseUsedWater

### Community 105 - "Community 105"
Cohesion: 0.11
Nodes (7): ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport

### Community 106 - "Community 106"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 107 - "Community 107"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 108 - "Community 108"
Cohesion: 0.16
Nodes (24): DiveOrigin, REFERENCE_PREFIX, SsiLogbook, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef() (+16 more)

### Community 109 - "Community 109"
Cohesion: 0.11
Nodes (21): has(), inArea(), LatitudeBands, merged(), planSiteImport(), same(), targetNames(), targetPosition() (+13 more)

### Community 110 - "Community 110"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 112 - "Community 112"
Cohesion: 0.11
Nodes (22): UtcOffsetSource, DAY_MS, MAX_OFFSET_MS, inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), PlacedTime (+14 more)

### Community 113 - "Community 113"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 114 - "Community 114"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 115 - "Community 115"
Cohesion: 0.18
Nodes (3): DiveViewValues, DiveViewValuesStartsAt, StoreLogbookTest

### Community 116 - "Community 116"
Cohesion: 0.15
Nodes (22): planningSac(), PlanningSacDive, PlanningSacSource, default, logbook, jsRound(), powInt(), roundTo() (+14 more)

### Community 117 - "Community 117"
Cohesion: 0.10
Nodes (16): ProblemCode, ResponseState, whenResponseGone(), StoredBlob, PlacedArchive, ANSWER, looksLikeRecipientPrivateKey(), PlacedArchive (+8 more)

### Community 118 - "Community 118"
Cohesion: 0.08
Nodes (24): devDependencies, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2, @testing-library/react@^16.3.3 (+16 more)

### Community 119 - "Community 119"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 120 - "Community 120"
Cohesion: 0.13
Nodes (6): ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting

### Community 121 - "Community 121"
Cohesion: 0.13
Nodes (6): DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, WaitingPushView

### Community 122 - "Community 122"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 123 - "Community 123"
Cohesion: 0.09
Nodes (16): BACKUP_EXTENSION, BACKUP_SKIPPED_DIRS, BackupEncryption, BackupPart, BackupLog, BackupRunRow, BackupSettingsInput, BackupSettingsValues (+8 more)

### Community 124 - "Community 124"
Cohesion: 0.16
Nodes (22): managementOf(), EquipmentError, checkCost(), checkDates(), checkWindow(), createEquipmentService(), assemble(), checkResets() (+14 more)

### Community 125 - "Community 125"
Cohesion: 0.14
Nodes (18): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+10 more)

### Community 126 - "Community 126"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 127 - "Community 127"
Cohesion: 0.17
Nodes (18): errorMessage(), looksLikeZip(), formatOf(), openUpload(), processFile(), processImport(), failureOf(), kindOf() (+10 more)

### Community 128 - "Community 128"
Cohesion: 0.16
Nodes (16): cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043, browserDetector() (+8 more)

### Community 129 - "Community 129"
Cohesion: 0.16
Nodes (16): DecisionTimelineProps, GapKind, ADR-0063, addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, laneFitsCapsule(), relations() (+8 more)

### Community 130 - "Community 130"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 131 - "Community 131"
Cohesion: 0.19
Nodes (3): LeadEstimateUiState, LeadEstimateViewModel, PersonalizedSources

### Community 132 - "Community 132"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 133 - "Community 133"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 134 - "Community 134"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 135 - "Community 135"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 137 - "Community 137"
Cohesion: 0.19
Nodes (11): LeadInputTest, SuitType, drysuit, none, semidry, skin, wetsuit, SuitUndergarment (+3 more)

### Community 138 - "Community 138"
Cohesion: 0.11
Nodes (17): BOTTOM_TIME_RULES, PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView (+9 more)

### Community 139 - "Community 139"
Cohesion: 0.16
Nodes (9): diveLocalTime(), offsetId(), timeZoneNote(), TimeZoneOrigin, DEVICE, NEARBY, POSITION, UNKNOWN (+1 more)

### Community 140 - "Community 140"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 141 - "Community 141"
Cohesion: 0.18
Nodes (15): RecordingSummary, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput, round3() (+7 more)

### Community 142 - "Community 142"
Cohesion: 0.16
Nodes (3): SyncLine(), formatSyncTime(), SyncTimeFormatTest

### Community 143 - "Community 143"
Cohesion: 0.20
Nodes (14): AppLockProbe, BackupManifest, Arguments, credentialOf(), FILE_OPTIONS, fileText(), finishedArguments(), parseArguments() (+6 more)

### Community 144 - "Community 144"
Cohesion: 0.14
Nodes (15): serviceRecordSchedule, serviceSchedule, equipmentList(), EquipmentListFilter, EquipmentRecordRead, today(), ADR-0034, ADR-0035 (+7 more)

### Community 145 - "Community 145"
Cohesion: 0.14
Nodes (14): AL80, EstimateDiveCylinder, LEAD_CASES, leadInput(), LeadVectorInput, planOf(), STEEL_12, ADR-0031 (+6 more)

### Community 146 - "Community 146"
Cohesion: 0.15
Nodes (10): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, ADR-0068 (+2 more)

### Community 147 - "Community 147"
Cohesion: 0.17
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 148 - "Community 148"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 149 - "Community 149"
Cohesion: 0.16
Nodes (13): CIRCUIT, circuitFromFit, DECO_MODEL, decoModelFromFit, DIVE_ALERT, DIVE_MODE, diveModeFromFit, ADR-0015 (+5 more)

### Community 150 - "Community 150"
Cohesion: 0.19
Nodes (13): asEstimateInput(), evaluateLeadVectorCase(), LEAD_VECTOR_KERNEL, leadVectorFile(), VECTOR_KERNELS, VectorFile, vectorFile(), assertMatch() (+5 more)

### Community 151 - "Community 151"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 152 - "Community 152"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 153 - "Community 153"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 154 - "Community 154"
Cohesion: 0.26
Nodes (14): Estimate, isoUtc(), LoadingBasis, LoadingDive, N2Loadings, N2UnavailableReason, no_profile, no_recent_dive (+6 more)

### Community 156 - "Community 156"
Cohesion: 0.19
Nodes (13): flag(), idText(), isObject(), numberOrNull(), readSsiSite(), ssiSiteRow(), SsiSiteValues, visibilityFor() (+5 more)

### Community 157 - "Community 157"
Cohesion: 0.14
Nodes (7): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, headers, uniqueWord()

### Community 158 - "Community 158"
Cohesion: 0.13
Nodes (14): name, needs, runs-on, timeout-minutes, concurrency, cancel-in-progress, group, env (+6 more)

### Community 159 - "Community 159"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 160 - "Community 160"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 161 - "Community 161"
Cohesion: 0.21
Nodes (9): fileTreeChecksum(), createBackupService(), checkSettings(), due(), enqueueScheduled(), readSettings(), saveSettings(), sweepRetention() (+1 more)

### Community 162 - "Community 162"
Cohesion: 0.19
Nodes (12): Suit, WeightingFeedback, LeadEstimateAnswer, LeadVectorDive, Confidence, EstimateAdjustment, EstimateBasis, EstimateDive (+4 more)

### Community 163 - "Community 163"
Cohesion: 0.17
Nodes (10): EstimateInput, AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031 (+2 more)

### Community 164 - "Community 164"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 165 - "Community 165"
Cohesion: 0.26
Nodes (3): DiverView, DiverViewCertifications, DiverViewCertificationsNewest

### Community 166 - "Community 166"
Cohesion: 0.26
Nodes (3): DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 167 - "Community 167"
Cohesion: 0.21
Nodes (10): BACKUP_FORMAT_VERSION, buildManifest(), decryptFile(), decryptFileWith(), decryptStreamWith(), encryptFileToRecipient(), manifestFor(), partOf() (+2 more)

### Community 168 - "Community 168"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 169 - "Community 169"
Cohesion: 0.18
Nodes (5): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053

### Community 170 - "Community 170"
Cohesion: 0.24
Nodes (7): compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051, ADR-0051

### Community 171 - "Community 171"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 172 - "Community 172"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 173 - "Community 173"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 174 - "Community 174"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 175 - "Community 175"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 176 - "Community 176"
Cohesion: 0.33
Nodes (8): LoadingFact(), LoadingsAnswer(), LoadingsRoute(), LoadingsScreen(), MissingValue(), percent(), SourceLink(), dateTime()

### Community 177 - "Community 177"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 178 - "Community 178"
Cohesion: 0.20
Nodes (10): verify, prerelease, sha, tag, versionCode, versionName, name, outputs (+2 more)

### Community 179 - "Community 179"
Cohesion: 0.20
Nodes (10): with, cache, distribution, fetch-depth, if-no-files-found, java-version, name, path (+2 more)

### Community 180 - "Community 180"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 181 - "Community 181"
Cohesion: 0.47
Nodes (8): DiveHubApp(), ErrorText(), InstanceForm(), Loading(), PasswordField(), PrimaryButton(), SignInForm(), Spinner()

### Community 182 - "Community 182"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 184 - "Community 184"
Cohesion: 0.25
Nodes (8): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, MODULE_IMPORTS, moduleRules, target()

### Community 185 - "Community 185"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 186 - "Community 186"
Cohesion: 0.22
Nodes (9): version, on, push, workflow_dispatch, tags, description, required, type (+1 more)

### Community 187 - "Community 187"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 188 - "Community 188"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 191 - "Community 191"
Cohesion: 0.29
Nodes (5): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, EstimateCylinder

### Community 192 - "Community 192"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 193 - "Community 193"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 194 - "Community 194"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 195 - "Community 195"
Cohesion: 0.29
Nodes (6): APP, build, generated, javaVersion, ADR-0073, ROOT

### Community 196 - "Community 196"
Cohesion: 0.40
Nodes (4): adapter, crossCheck(), decodeOfficial(), privateDir

### Community 197 - "Community 197"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 198 - "Community 198"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 199 - "Community 199"
Cohesion: 0.40
Nodes (5): ToolPage, Gas, Lead, Loadings, Plan

### Community 200 - "Community 200"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 201 - "Community 201"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 202 - "Community 202"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 204 - "Community 204"
Cohesion: 0.40
Nodes (4): TAGS, ADR-0023, { stdout, stderr }, total

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
- **1270 isolated node(s):** `Reserve`, `RockBottom`, `Off`, `SuitThickness`, `ADR-0044` (+1265 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3488 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **120 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 0` to `Community 1`, `Community 3`, `Community 4`, `Community 5`, `Community 8`, `Community 10`, `Community 11`, `Community 14`, `Community 15`, `Community 144`, `Community 24`, `Community 31`, `Community 46`, `Community 49`, `Community 62`, `Community 65`, `Community 85`, `Community 89`, `Community 96`, `Community 97`, `Community 98`, `Community 100`, `Community 112`, `Community 123`?**
  _High betweenness centrality (0.022) - this node is a cross-community bridge._
- **What connects `Reserve`, `RockBottom`, `Off` to the rest of the system?**
  _1270 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.013078313656348338 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 2` to `Community 68`, `Community 6`, `Community 7`, `Community 9`, `Community 12`, `Community 13`, `Community 16`, `Community 17`, `Community 18`, `Community 25`?**
  _High betweenness centrality (0.015) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.01566892243623113 - nodes in this community are weakly interconnected._
- **Why does `react` connect `Community 17` to `Community 128`, `Community 129`, `Community 2`, `Community 68`, `Community 6`, `Community 7`, `Community 9`, `Community 12`, `Community 13`, `Community 16`, `Community 18`, `Community 25`?**
  _High betweenness centrality (0.013) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.03973416687786883 - nodes in this community are weakly interconnected._