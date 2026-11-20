# Graph Report - dive_hub  (2026-11-20)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 10738 nodes · 32342 edges · 362 communities (212 shown, 150 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 610 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `44b6cd3e`
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
- Community 238
- Community 239
- Community 240
- Community 241
- Community 242
- Community 243
- Community 247

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
- `AuthOptions` --references--> `Db`  [EXTRACTED]
  apps/server/src/auth/auth.ts → apps/server/src/db/client.ts
- `CatalogueCylinder` --references--> `CylinderMaterial`  [EXTRACTED]
  apps/server/src/dives/cylinder-catalogue.ts → apps/server/src/db/schema.ts

## Import Cycles
- None detected.

## Communities (362 total, 150 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.01
Nodes (349): user, buddySuggestion, diveFinding, diveLease, diver, jointDive, providerConflict, push (+341 more)

### Community 1 - "Community 1"
Cohesion: 0.01
Nodes (373): CHANNELS, ADR-0036, ENGINE_VERSION, Db, Queryable, Tx, actorType, aiAccessLog (+365 more)

### Community 2 - "Community 2"
Cohesion: 0.01
Nodes (319): ComputerChoice, diveFingerprint, DiveImportMode, DiveKind, importOriginal, ParticipantRole, providerGoneDive, ProviderImportPlan (+311 more)

### Community 3 - "Community 3"
Cohesion: 0.04
Nodes (254): ChangePassword(), DisplaySettings(), SecuritySettings(), Sessions(), ADR-0080, useDescribeAgent(), AdminSettings(), ClientDiagnosticsSetting() (+246 more)

### Community 4 - "Community 4"
Cohesion: 0.02
Nodes (254): diversQuery(), DiveView, cylinderCatalogueQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime (+246 more)

### Community 5 - "Community 5"
Cohesion: 0.02
Nodes (175): createServer(), createAuth(), auth, hashPassword(), loadAuthSecret(), encryptFile(), execFileAsync, openArchive() (+167 more)

### Community 6 - "Community 6"
Cohesion: 0.02
Nodes (211): label(), Me, AcceptedBuddySuggestionView, allButDive(), AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery() (+203 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (161): EMPTY_SITE_IMPORT_COUNTS, ImportArea, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053 (+153 more)

### Community 8 - "Community 8"
Cohesion: 0.02
Nodes (142): CentreService, CentreRouteDeps, LEAD_PLACEMENTS, UTC_OFFSET_SOURCES, WEIGHTING_FELT, DiverService, downsampleMinMax(), waterMismatch() (+134 more)

### Community 9 - "Community 9"
Cohesion: 0.02
Nodes (138): AiAccessLogEntry, aiAccessLogQuery(), aiAccessQuery(), AiAccessView, invitationLinkQuery(), invitationsQuery(), InvitationView, passwordResetQuery() (+130 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (109): divesQuery(), LogbookPage, PAGE_SIZE, isUnauthorized(), ExternalIdView, siteQuery(), App(), BackupsPage (+101 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (14): AppContainer, KeystoreTokenStore, PreferencesInstanceUrlStore, UserCacheStore, AndroidDiveStore, MainActivity, MainViewModel, SettingsDiagnosticsState (+6 more)

### Community 12 - "Community 12"
Cohesion: 0.04
Nodes (108): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, ambientPressureBar() (+100 more)

### Community 13 - "Community 13"
Cohesion: 0.03
Nodes (29): recordingName(), ApiToolsBottomTimeGet200ResponseResidualN2, DiveListView, DiveListViewCounts, DiveListViewTotals, DiveView, DiveViewCylindersItem, DiveViewCylindersItemGas (+21 more)

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (100): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError (+92 more)

### Community 15 - "Community 15"
Cohesion: 0.03
Nodes (101): ConflictChoices, ConflictEntryView, ConflictFieldView, ConflictsOfConnection, providerSitesQuery(), providersQuery(), providerStatusQuery(), ProviderView (+93 more)

### Community 16 - "Community 16"
Cohesion: 0.04
Nodes (29): DiveDetailUiState, DiveDetailViewModel, LogbookViewModel, ApiClient, ApiErrorBody, DiveRepository, neighboursPath(), DecodeException (+21 more)

### Community 17 - "Community 17"
Cohesion: 0.04
Nodes (19): FakeDiagnostics, FakeInstances, SettingsViewModelTest, ApiClientDiagnosticsPostRequest, ApiClientDiagnosticsPostRequestClient, ApiClientDiagnosticsPostRequestEntriesItem, ApiClientDiagnosticsStatusGet200Response, DiagnosticHygiene (+11 more)

### Community 18 - "Community 18"
Cohesion: 0.03
Nodes (92): ReportProblemSettings(), buddiesForQuery(), buddiesQuery(), BuddyView, DiveImportMode, DiveImportPreview, diveImportPreviewQuery(), DiveKind (+84 more)

### Community 19 - "Community 19"
Cohesion: 0.03
Nodes (73): Delivered, ExportingProvider, FIND_BY, OPERATIONS, ProviderError, RemoteDive, SignInInput, createSsiAdapter() (+65 more)

### Community 20 - "Community 20"
Cohesion: 0.04
Nodes (48): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+40 more)

### Community 21 - "Community 21"
Cohesion: 0.03
Nodes (81): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+73 more)

### Community 22 - "Community 22"
Cohesion: 0.05
Nodes (18): DiveStore, toBoolean(), toLong(), toModel(), BodyWeight, Cylinder, Dive, Diver (+10 more)

### Community 23 - "Community 23"
Cohesion: 0.04
Nodes (77): replyCertificationError, STATUS, CertificationService, DIVE_IMPORT_MODES, DIVE_KINDS, IMPORT_ERROR_CODES, MATCHING_WINDOWS, OUTCOME_REASONS (+69 more)

### Community 24 - "Community 24"
Cohesion: 0.04
Nodes (80): DUE_STATES, AiAccessScope, seesPositions(), VerifiedAccess, diversOf(), SiteRow, checkedByRunTool, createMcpServer() (+72 more)

### Community 25 - "Community 25"
Cohesion: 0.04
Nodes (76): AssessmentService, AssessmentView, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView() (+68 more)

### Community 26 - "Community 26"
Cohesion: 0.04
Nodes (73): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+65 more)

### Community 27 - "Community 27"
Cohesion: 0.08
Nodes (4): SyncLine(), LoadingSource, limitWord(), ErrorCodes

### Community 28 - "Community 28"
Cohesion: 0.04
Nodes (70): diveNeighboursQuery(), diveQuery(), DiveValues, OverridableField, revisionsQuery(), RevisionView, UtcOffsetSource, CylinderList() (+62 more)

### Community 29 - "Community 29"
Cohesion: 0.07
Nodes (70): formatSyncTime(), GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult(), PlanInputs() (+62 more)

### Community 30 - "Community 30"
Cohesion: 0.07
Nodes (35): Connected, Hint, HintFrame, LiveEvent, LiveHint, LiveHints, SseLiveHints, LiveHintsTest (+27 more)

### Community 31 - "Community 31"
Cohesion: 0.04
Nodes (54): connection, ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS, eachFile(), readEntry(), ADR-0044 (+46 more)

### Community 32 - "Community 32"
Cohesion: 0.04
Nodes (74): ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, buddiesOf(), BuddySearch, count() (+66 more)

### Community 33 - "Community 33"
Cohesion: 0.07
Nodes (38): DiveBody(), DiveDetailRoute(), DiveDetailScreen(), errorText(), FactRow(), Facts(), Loading(), NeighbourButton() (+30 more)

### Community 34 - "Community 34"
Cohesion: 0.07
Nodes (13): LogbookScreenshotTest, DiveListViewDivesItem, DiveListViewDivesItemGasesItem, DiveListViewDivesItemParticipantsItem, DiveListViewDivesItemSite, DiveListViewMonthsItem, localMonthOf(), LogbookMonth (+5 more)

### Community 35 - "Community 35"
Cohesion: 0.08
Nodes (66): buildApp(), assessmentRoutes(), SessionUser, authHandler(), authHeaders(), fastify, FastifyRequest, forwardCookies() (+58 more)

### Community 36 - "Community 36"
Cohesion: 0.04
Nodes (65): CYLINDER_MATERIALS, PARTICIPANT_ROLES, CodePart(), AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053, CandidateRouteDeps (+57 more)

### Community 38 - "Community 38"
Cohesion: 0.04
Nodes (62): ADR-0025, WaterMismatch, Change(), Changes(), historyRoute, Made(), REVISION_CAUSES, PositionSchema (+54 more)

### Community 39 - "Community 39"
Cohesion: 0.06
Nodes (55): accountListEntry, providerLease, tidyName(), DiverDetails, Caller, foldName(), nameDiffers(), Buddy (+47 more)

### Community 40 - "Community 40"
Cohesion: 0.07
Nodes (64): Answer, answer(), baseFrom(), BodyWeight, Confidence, high, low, medium (+56 more)

### Community 41 - "Community 41"
Cohesion: 0.06
Nodes (61): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+53 more)

### Community 42 - "Community 42"
Cohesion: 0.08
Nodes (54): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+46 more)

### Community 43 - "Community 43"
Cohesion: 0.06
Nodes (46): SsiClient, SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), DiveForSsi, emptyRecord(), writableRecord() (+38 more)

### Community 44 - "Community 44"
Cohesion: 0.12
Nodes (24): Body, BrokenBody, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage(), diverJson() (+16 more)

### Community 45 - "Community 45"
Cohesion: 0.05
Nodes (54): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, Services, EquipmentService (+46 more)

### Community 46 - "Community 46"
Cohesion: 0.06
Nodes (55): agreeInDepthAndDuration(), CheckedDive, DeletableDive, findChecks(), followsSoon(), inChains(), LOGBOOK_CHECK_RULES, LOGBOOK_CHECKS_VERSION (+47 more)

### Community 47 - "Community 47"
Cohesion: 0.06
Nodes (47): looksLikeZip(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy(), ADR-0024 (+39 more)

### Community 48 - "Community 48"
Cohesion: 0.06
Nodes (36): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+28 more)

### Community 49 - "Community 49"
Cohesion: 0.09
Nodes (12): ApiDiverBodyWeightsGet200ResponseItem, SyncCheckpoint, Complete, SessionEnded, Stopped, SyncEngine, SyncPhase, Details (+4 more)

### Community 50 - "Community 50"
Cohesion: 0.06
Nodes (24): AppNavHost(), AppScreens, clearSavedTabStates(), isShowing(), leaveDiveForLogbook(), MainShell(), navigateToTab(), tabOf() (+16 more)

### Community 51 - "Community 51"
Cohesion: 0.09
Nodes (8): DiveDetailScreenshotTest, DiveViewFromRecording, DiveViewFromRecordingStartsAt, DiveViewParticipantsItem, DiveViewParticipantsItemCertificationHint, DiveViewWaterMismatch, NeighboursView, NeighboursViewNext

### Community 52 - "Community 52"
Cohesion: 0.06
Nodes (44): RecordingSummary, RecordingFacts, date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num() (+36 more)

### Community 53 - "Community 53"
Cohesion: 0.05
Nodes (49): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, conflictForLena() (+41 more)

### Community 54 - "Community 54"
Cohesion: 0.10
Nodes (40): BottomTime, BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen, BottomTimeLimits (+32 more)

### Community 55 - "Community 55"
Cohesion: 0.08
Nodes (14): UiState, sessionDestination(), SessionDestinationTest, ApiMeGet200Response, ApiMeGet200ResponseOwnDiver, ApiMeGet200ResponsePreferences, SignInEmailRequest, UserView (+6 more)

### Community 56 - "Community 56"
Cohesion: 0.09
Nodes (6): SettingsRepository, SyncState, InstanceUrlStore, FakeInstances, SettingsRepositoryTest, DiveStoreTest

### Community 57 - "Community 57"
Cohesion: 0.08
Nodes (48): BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32Encode(), bech32HrpExpand(), bech32Polymod() (+40 more)

### Community 58 - "Community 58"
Cohesion: 0.05
Nodes (43): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+35 more)

### Community 59 - "Community 59"
Cohesion: 0.06
Nodes (42): diveListFilter(), notAtProvider(), Blocked, classify(), FailureOutcome, failureOutcome(), kindOf(), KINDS (+34 more)

### Community 60 - "Community 60"
Cohesion: 0.08
Nodes (38): BottomTimeQuery, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033, ADR-0071, DEFAULT_PPO2 (+30 more)

### Community 61 - "Community 61"
Cohesion: 0.08
Nodes (42): asBool(), DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining(), ssiDiveType() (+34 more)

### Community 62 - "Community 62"
Cohesion: 0.07
Nodes (35): diveWith(), ADR-0028, ADR-0043, buddySite(), Dive, OTHER, resetSource(), ADR-0012 (+27 more)

### Community 63 - "Community 63"
Cohesion: 0.09
Nodes (23): ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseCns, ApiDiversIdLoadingsGet200ResponseN2, ApiDiversIdLoadingsGet200ResponseNoFly, ApiDiversIdLoadingsGet200ResponseNoFlySource, ApiDiversIdLoadingsGet200ResponseOtu, cnsDecayed() (+15 more)

### Community 64 - "Community 64"
Cohesion: 0.08
Nodes (42): NoFly, DEFAULT_GF_HIGH, residualSeedOf(), depthSamples(), diverDiveRows(), DiveRow, endMsOf(), loadingDivesForReplay() (+34 more)

### Community 65 - "Community 65"
Cohesion: 0.11
Nodes (8): FakeCache, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore, UserScopedStore

### Community 66 - "Community 66"
Cohesion: 0.05
Nodes (38): SUIT_TYPES, SUIT_UNDERGARMENTS, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053, replyDiverError (+30 more)

### Community 67 - "Community 67"
Cohesion: 0.06
Nodes (36): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+28 more)

### Community 68 - "Community 68"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 69 - "Community 69"
Cohesion: 0.06
Nodes (38): CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043, CreateBody (+30 more)

### Community 70 - "Community 70"
Cohesion: 0.09
Nodes (16): DiveProfileView, LogbookPaging, AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint, SketchProfile (+8 more)

### Community 72 - "Community 72"
Cohesion: 0.07
Nodes (33): diverLoadingsQuery(), LoadingsView, running(), siteImportsQuery(), useLanguage(), CurrentLoadings(), CustomTypeOptions, i18next (+25 more)

### Community 73 - "Community 73"
Cohesion: 0.08
Nodes (26): captureDiagnostic(), captureError(), cleanContext(), clearDiagnostics(), CLIENT_NAME, CLIENT_VERSION, DiagnosticEntry, diagnosticFromError() (+18 more)

### Community 74 - "Community 74"
Cohesion: 0.08
Nodes (37): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+29 more)

### Community 75 - "Community 75"
Cohesion: 0.10
Nodes (21): AvatarCircle(), DiveRow(), Error(), Fact(), Filters(), Loading(), LogbookList(), LogbookRoute() (+13 more)

### Community 76 - "Community 76"
Cohesion: 0.05
Nodes (37): AREA_OF, areaOf(), areas, capture(), findings, headers, ADR-0020, ADR-0021 (+29 more)

### Community 77 - "Community 77"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 78 - "Community 78"
Cohesion: 0.08
Nodes (21): Formats, Deep, DepthReading, depthReading(), deviceName(), DiveValue, AVG_DEPTH_M, DURATION_SECONDS (+13 more)

### Community 79 - "Community 79"
Cohesion: 0.12
Nodes (6): LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200ResponseBasis, ApiDiversIdLeadEstimateGet200ResponseBasisDivesItem, ApiDiversIdLeadEstimateGet200ResponseMissingItem

### Community 80 - "Community 80"
Cohesion: 0.11
Nodes (33): BACKUP_SKIPPED_DIRS, BackupDecryptionKey, encryptionKeyFingerprint(), openArchiveStreamingWith(), assertPgTool(), execFileAsync, pgCommand(), pgErrorDetail() (+25 more)

### Community 81 - "Community 81"
Cohesion: 0.07
Nodes (31): BackupRouteDeps, BackupRunView, BackupSettingsInput, BackupSettingsView, STATUS, toView(), ADR-0069, BACKUP_RUN_TASK (+23 more)

### Community 82 - "Community 82"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 83 - "Community 83"
Cohesion: 0.12
Nodes (9): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), GasPlanInputTest (+1 more)

### Community 84 - "Community 84"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 85 - "Community 85"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 86 - "Community 86"
Cohesion: 0.10
Nodes (29): dayNumber(), enteredDecoFromNdl(), minNdlSeconds(), NoFly, noFlyAfter(), NoFlyReason, decompression, several (+21 more)

### Community 87 - "Community 87"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 88 - "Community 88"
Cohesion: 0.13
Nodes (34): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+26 more)

### Community 89 - "Community 89"
Cohesion: 0.10
Nodes (10): Answers, number(), ToolsViewModel, ApiToolsBottomTimeGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseLimits, depthFromDisplay(), pressureFromDisplay() (+2 more)

### Community 90 - "Community 90"
Cohesion: 0.16
Nodes (11): bottomTime(), planningSac(), PlanningSacDive, PlanningSacResult, PlanningSacSource, default, logbook, BottomTimeToolInput (+3 more)

### Community 91 - "Community 91"
Cohesion: 0.08
Nodes (30): accountNumber(), accountOnlyRow(), BUDDY_KEYS, FLAGS, newEntryRow(), rowOf(), SsiBuddyRow, withAccountRow() (+22 more)

### Community 92 - "Community 92"
Cohesion: 0.11
Nodes (27): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+19 more)

### Community 93 - "Community 93"
Cohesion: 0.13
Nodes (31): importsQuery(), Logbook(), ImportContext, ImportFilesButton(), ImportHistory(), ImportList(), ImportNotices(), ImportPanel() (+23 more)

### Community 94 - "Community 94"
Cohesion: 0.10
Nodes (10): ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh, ApiToolsBottomTimeGet200ResponseUsedSacLpm, ApiToolsBottomTimeGet200ResponseUsedWater, bottomTime() (+2 more)

### Community 96 - "Community 96"
Cohesion: 0.08
Nodes (22): AttemptLimit, ProblemCode, ResponseState, whenResponseGone(), StoredBlob, InvitationService, ProfileService, UserRouteDeps (+14 more)

### Community 97 - "Community 97"
Cohesion: 0.09
Nodes (25): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+17 more)

### Community 98 - "Community 98"
Cohesion: 0.10
Nodes (25): avatarTone(), initials(), useEquipmentDueCount(), NavSection, navSection(), Avatar(), DiverAvatar(), ICONS (+17 more)

### Community 99 - "Community 99"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 100 - "Community 100"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 101 - "Community 101"
Cohesion: 0.11
Nodes (12): diveLocalTime(), offsetId(), timeZoneNote(), TimeZoneOrigin, DEVICE, NEARBY, POSITION, UNKNOWN (+4 more)

### Community 102 - "Community 102"
Cohesion: 0.09
Nodes (27): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+19 more)

### Community 103 - "Community 103"
Cohesion: 0.10
Nodes (23): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearPushRuns(), clearQueue() (+15 more)

### Community 104 - "Community 104"
Cohesion: 0.08
Nodes (20): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, APP (+12 more)

### Community 106 - "Community 106"
Cohesion: 0.11
Nodes (26): ScheduleDue, DiveGear, DueEntry, ensureItemForDevice(), GearItem, ItemDetailView, ItemEdit, ItemInput (+18 more)

### Community 107 - "Community 107"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 108 - "Community 108"
Cohesion: 0.08
Nodes (22): headers, ADR-0035, aiAccessReady(), askMcp(), cancelWaitingImports(), createAiAccess(), putAside(), waitingImport() (+14 more)

### Community 109 - "Community 109"
Cohesion: 0.26
Nodes (6): leadAnswer(), EstimateCylinder, PlannedConditions, Suit, leadEstimate(), PersonalizedToolsTest

### Community 110 - "Community 110"
Cohesion: 0.12
Nodes (3): LauncherIconTest, SplashTheme, SplashThemeTest

### Community 111 - "Community 111"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 112 - "Community 112"
Cohesion: 0.21
Nodes (4): GasPlanScreenshotTest, GasPlanToolDiver, GasPlanToolInput, GasPlanToolLevel

### Community 113 - "Community 113"
Cohesion: 0.11
Nodes (23): AssessmentError, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), requireManagedDive(), isRule() (+15 more)

### Community 114 - "Community 114"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 115 - "Community 115"
Cohesion: 0.09
Nodes (4): DiveHubApplication, SyncWorker, WorkManagerPeriodicSync, PeriodicSyncScheduler

### Community 116 - "Community 116"
Cohesion: 0.08
Nodes (26): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+18 more)

### Community 117 - "Community 117"
Cohesion: 0.13
Nodes (21): acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), AppLockProbe (+13 more)

### Community 118 - "Community 118"
Cohesion: 0.13
Nodes (22): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DueInput, DueSchedule (+14 more)

### Community 119 - "Community 119"
Cohesion: 0.12
Nodes (19): createFileFormats(), FileFormat, looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf() (+11 more)

### Community 120 - "Community 120"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 121 - "Community 121"
Cohesion: 0.12
Nodes (7): LeadAnswer, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseLeftOut, ApiDiversIdLeadEstimateGet200ResponseRange, ApiDiversIdLeadEstimateGet200ResponseReserve, ApiDiversIdLeadEstimateGet200ResponseWeightCheck

### Community 123 - "Community 123"
Cohesion: 0.08
Nodes (10): HINT_CHANNEL, LiveHub, createListener(), connect(), lost(), LiveListener, LiveLog, RETRY_MS (+2 more)

### Community 124 - "Community 124"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 125 - "Community 125"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 127 - "Community 127"
Cohesion: 0.13
Nodes (6): ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting

### Community 128 - "Community 128"
Cohesion: 0.13
Nodes (6): DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, WaitingPushView

### Community 129 - "Community 129"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 130 - "Community 130"
Cohesion: 0.11
Nodes (20): aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, KeyRow, LOG_DAYS, LOG_PAGE_MAX, LoggedCall (+12 more)

### Community 131 - "Community 131"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 132 - "Community 132"
Cohesion: 0.18
Nodes (3): LeadEstimateUiState, LeadEstimateViewModel, PersonalizedSources

### Community 135 - "Community 135"
Cohesion: 0.12
Nodes (18): siteImportStatus, PROBLEM_CODES, SiteImportRouteDeps, StartBody, STATUS, ADR-0021, SiteImportError, SiteImportService (+10 more)

### Community 136 - "Community 136"
Cohesion: 0.16
Nodes (16): errorMessage(), formatOf(), createImportService(), cancelImport(), createImport(), endWaiting(), expireWaiting(), openUpload() (+8 more)

### Community 137 - "Community 137"
Cohesion: 0.22
Nodes (20): DiveOrigin, aboveZero(), cylinderOf(), deviceOf(), deviceOfRef(), MATERIAL_OF_TANK_TYPE, nonZero(), numberOf() (+12 more)

### Community 138 - "Community 138"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 139 - "Community 139"
Cohesion: 0.15
Nodes (5): ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits

### Community 140 - "Community 140"
Cohesion: 0.15
Nodes (5): SiteViewExternalIdsItem, SiteViewExternalIdsItemAttribution, SiteViewExternalIdsItemOffered, SiteViewExternalIdsItemOfferedPosition, SiteViewExternalIdsItemPrivateTo

### Community 141 - "Community 141"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource/poppins@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 142 - "Community 142"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 143 - "Community 143"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 144 - "Community 144"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 145 - "Community 145"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 146 - "Community 146"
Cohesion: 0.19
Nodes (11): LeadInputTest, SuitType, drysuit, none, semidry, skin, wetsuit, SuitUndergarment (+3 more)

### Community 147 - "Community 147"
Cohesion: 0.22
Nodes (11): DiveHubApp(), DiveHubScreens, ErrorText(), Gate(), InstanceForm(), LoadingScreen(), PasswordField(), PrimaryButton() (+3 more)

### Community 148 - "Community 148"
Cohesion: 0.18
Nodes (3): FakeInstances, FakeTokens, SyncAuthTest

### Community 149 - "Community 149"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 150 - "Community 150"
Cohesion: 0.11
Nodes (12): BACKUP_EXTENSION, BackupEncryption, BackupPart, BackupLog, BackupRunRow, BackupSettingsInput, BackupSettingsValues, DEFAULT_BACKUP_SETTINGS (+4 more)

### Community 151 - "Community 151"
Cohesion: 0.18
Nodes (15): UtcOffsetSource, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput, round3() (+7 more)

### Community 152 - "Community 152"
Cohesion: 0.12
Nodes (16): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+8 more)

### Community 154 - "Community 154"
Cohesion: 0.14
Nodes (15): serviceRecordSchedule, serviceSchedule, equipmentList(), EquipmentListFilter, EquipmentRecordRead, today(), ADR-0034, ADR-0035 (+7 more)

### Community 155 - "Community 155"
Cohesion: 0.14
Nodes (14): AL80, EstimateDiveCylinder, LEAD_CASES, leadInput(), LeadVectorInput, planOf(), STEEL_12, ADR-0031 (+6 more)

### Community 156 - "Community 156"
Cohesion: 0.17
Nodes (12): storedList(), tryParseJson(), datasetLength(), APP_PARAMS, createSsiTransport(), exchange(), isObject(), SSI_APP_USER_AGENT (+4 more)

### Community 157 - "Community 157"
Cohesion: 0.17
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 159 - "Community 159"
Cohesion: 0.19
Nodes (4): DiveDetailsListView, DiveDetailsListViewCounts, DiveDetailsListViewMonthsItem, DiveDetailsListViewTotals

### Community 160 - "Community 160"
Cohesion: 0.19
Nodes (13): asEstimateInput(), evaluateLeadVectorCase(), LEAD_VECTOR_KERNEL, leadVectorFile(), VECTOR_KERNELS, VectorFile, vectorFile(), assertMatch() (+5 more)

### Community 161 - "Community 161"
Cohesion: 0.21
Nodes (11): buildSupportBundle(), buildTarGz(), json(), ndjson(), octal(), SUPPORT_BUNDLE_FORMAT, SUPPORT_BUNDLE_FORMAT_VERSION, SupportBufferManifest (+3 more)

### Community 162 - "Community 162"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 163 - "Community 163"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 164 - "Community 164"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 166 - "Community 166"
Cohesion: 0.26
Nodes (14): jsRound(), powInt(), roundTo(), Sac, SacCylinder, SacDive, SacMissing, cylinder_incomplete (+6 more)

### Community 167 - "Community 167"
Cohesion: 0.13
Nodes (12): clientDiagnosticsSettings, ClientDiagnosticsEntryInput, ClientDiagnosticsEntryView, ClientDiagnosticsError, ClientDiagnosticsUpload, KEEP_NEWEST, MAX_UPLOAD_ENTRIES, MESSAGE_MAX_LENGTH (+4 more)

### Community 168 - "Community 168"
Cohesion: 0.13
Nodes (14): name, needs, runs-on, timeout-minutes, concurrency, cancel-in-progress, group, env (+6 more)

### Community 171 - "Community 171"
Cohesion: 0.16
Nodes (11): CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, CertificationRouteDeps, errors, Fields (+3 more)

### Community 172 - "Community 172"
Cohesion: 0.18
Nodes (13): Suit, WeightingFeedback, LeadEstimateAnswer, LeadVectorDive, Confidence, EstimateAdjustment, EstimateBasis, EstimateCylinder (+5 more)

### Community 173 - "Community 173"
Cohesion: 0.15
Nodes (12): EquipmentError, ADR-0034, ADR-0053, checkCost(), checkDates(), checkWindow(), checkResets(), checkSchedule() (+4 more)

### Community 174 - "Community 174"
Cohesion: 0.20
Nodes (5): LogRingBuffer, RedactionCounters, SupportBundleInput, SupportBundleManifest, SupportServiceDeps

### Community 175 - "Community 175"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 176 - "Community 176"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 178 - "Community 178"
Cohesion: 0.19
Nodes (11): BACKUP_FORMAT_VERSION, BackupManifest, buildManifest(), decryptFile(), decryptFileWith(), decryptStreamWith(), fileTreeChecksum(), manifestFor() (+3 more)

### Community 179 - "Community 179"
Cohesion: 0.22
Nodes (10): isRecipient(), tarDirectory(), createBackupService(), checkSettings(), due(), enqueueScheduled(), readSettings(), saveSettings() (+2 more)

### Community 180 - "Community 180"
Cohesion: 0.17
Nodes (10): EstimateInput, AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031 (+2 more)

### Community 181 - "Community 181"
Cohesion: 0.22
Nodes (11): flag(), idText(), isObject(), numberOrNull(), readSsiSite(), SsiSiteValues, WATER_OF_BOW, ALPHA3_TO_ALPHA2 (+3 more)

### Community 182 - "Community 182"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 183 - "Community 183"
Cohesion: 0.21
Nodes (9): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, readQr(), ADR-0043, ADR-0043 (+1 more)

### Community 184 - "Community 184"
Cohesion: 0.26
Nodes (3): ApiDiveSitesGet200Response, SiteView, SiteViewPosition

### Community 185 - "Community 185"
Cohesion: 0.26
Nodes (3): DiverView, DiverViewCertifications, DiverViewCertificationsNewest

### Community 186 - "Community 186"
Cohesion: 0.21
Nodes (9): Config, loadConfig(), ADR-0011, ADR-0021, ADR-0024, ADR-0025, config, parseEncryptionKey() (+1 more)

### Community 187 - "Community 187"
Cohesion: 0.17
Nodes (11): account, accountRelations, apikey, rateLimit, sessionRelations, ADR-0011, ADR-0012, ADR-0035 (+3 more)

### Community 188 - "Community 188"
Cohesion: 0.30
Nodes (11): asText(), compareReadBack(), describe(), isObject(), keyDifferences(), pause(), recording(), runAnswerChecks() (+3 more)

### Community 189 - "Community 189"
Cohesion: 0.18
Nodes (10): LogBufferStats, SupportBundle, ClientDiagnosticsProvider, FailedBackup, FailedJob, HealthSection, iso(), JobsSection (+2 more)

### Community 190 - "Community 190"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 191 - "Community 191"
Cohesion: 0.36
Nodes (11): createEquipmentService(), assemble(), detailOf(), lastCylinderOf(), managedItem(), recordsOf(), schedulesFor(), schedulesOf() (+3 more)

### Community 192 - "Community 192"
Cohesion: 0.24
Nodes (7): compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051, ADR-0051

### Community 193 - "Community 193"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 194 - "Community 194"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 195 - "Community 195"
Cohesion: 0.20
Nodes (10): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, ADR-0076, MODULE_IMPORTS, moduleRules (+2 more)

### Community 196 - "Community 196"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 197 - "Community 197"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 198 - "Community 198"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 201 - "Community 201"
Cohesion: 0.29
Nodes (7): otu(), oxygenLimitMinutes(), compressibility(), Cubic, minusOne(), pressureForLitres(), surfaceLitres()

### Community 202 - "Community 202"
Cohesion: 0.33
Nodes (8): prepareEntry(), DENIED_KEY_PARTS, isDeniedKey(), maskString(), redactInner(), redactLogObject(), RedactOptions, redactValue()

### Community 203 - "Community 203"
Cohesion: 0.33
Nodes (9): cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043, pictureOf() (+1 more)

### Community 204 - "Community 204"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 205 - "Community 205"
Cohesion: 0.20
Nodes (10): verify, prerelease, sha, tag, versionCode, versionName, name, outputs (+2 more)

### Community 206 - "Community 206"
Cohesion: 0.20
Nodes (10): with, cache, distribution, fetch-depth, if-no-files-found, java-version, name, path (+2 more)

### Community 207 - "Community 207"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 208 - "Community 208"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 210 - "Community 210"
Cohesion: 0.31
Nodes (5): BufferedLog, createLogRingBuffer(), LOG_BUFFER_CAPACITY_BYTES, LOG_BUFFER_CAPACITY_ENTRIES, LogRingBufferOptions

### Community 211 - "Community 211"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 212 - "Community 212"
Cohesion: 0.22
Nodes (9): version, on, push, workflow_dispatch, tags, description, required, type (+1 more)

### Community 213 - "Community 213"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 214 - "Community 214"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 215 - "Community 215"
Cohesion: 0.46
Nodes (6): Action(), label(), Section(), SettingsDebugSection(), SettingsRoute(), SettingsScreen()

### Community 224 - "Community 224"
Cohesion: 0.46
Nodes (8): appliedMigrationHead(), emptyRedactionCounters(), createSupportService(), assemble(), clientDiagnostics(), configSection(), failedJobs(), healthSection()

### Community 225 - "Community 225"
Cohesion: 0.29
Nodes (6): FIT_EPOCH_MS, makeSuuntoAppFit(), shape(), SuuntoDiveOptions, ADR-0006, ADR-0037

### Community 226 - "Community 226"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 227 - "Community 227"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 229 - "Community 229"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 231 - "Community 231"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 232 - "Community 232"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 234 - "Community 234"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 235 - "Community 235"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 236 - "Community 236"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 240 - "Community 240"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 241 - "Community 241"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 242 - "Community 242"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1297 isolated node(s):** `Sent`, `Reserve`, `RockBottom`, `SuitThickness`, `Off` (+1292 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3635 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **150 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 0` to `Community 1`, `Community 2`, `Community 130`, `Community 5`, `Community 7`, `Community 8`, `Community 14`, `Community 21`, `Community 150`, `Community 24`, `Community 154`, `Community 31`, `Community 32`, `Community 36`, `Community 39`, `Community 167`, `Community 45`, `Community 187`, `Community 60`, `Community 189`, `Community 64`, `Community 102`, `Community 106`, `Community 116`?**
  _High betweenness centrality (0.025) - this node is a cross-community bridge._
- **What connects `Sent`, `Reserve`, `RockBottom` to the rest of the system?**
  _1297 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.014293659621802002 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 9` to `Community 3`, `Community 4`, `Community 100`, `Community 6`, `Community 72`, `Community 10`, `Community 42`, `Community 141`, `Community 77`, `Community 15`, `Community 18`, `Community 28`, `Community 93`?**
  _High betweenness centrality (0.019) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.01057799372406114 - nodes in this community are weakly interconnected._
- **Why does `@playwright/test` connect `Community 48` to `Community 97`, `Community 193`, `Community 163`, `Community 194`, `Community 100`, `Community 103`, `Community 108`, `Community 76`, `Community 53`, `Community 182`, `Community 120`, `Community 62`?**
  _High betweenness centrality (0.016) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.014255607406292338 - nodes in this community are weakly interconnected._