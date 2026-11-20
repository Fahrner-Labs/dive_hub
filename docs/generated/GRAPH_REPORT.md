# Graph Report - dive_hub  (2026-11-20)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 10211 nodes · 30837 edges · 340 communities (218 shown, 122 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 547 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `87de9c27`
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
- Community 223
- Community 224
- Community 225
- Community 226
- Community 231

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 206 edges
2. `Button()` - 200 edges
3. `useErrorText()` - 171 edges
4. `useDisplay()` - 163 edges
5. `drizzle-orm` - 141 edges
6. `Muted()` - 139 edges
7. `unwrap()` - 138 edges
8. `announce()` - 106 edges
9. `createTestDatabase()` - 105 edges
10. `TestDatabase` - 104 edges

## Surprising Connections (you probably didn't know these)
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `SsiClientOptions` --references--> `Fetch`  [EXTRACTED]
  apps/server/src/providers/ssi/ssi-client.ts → apps/server/src/providers/ssi/ssi-transport.ts
- `StoreLogbookTest` --calls--> `StoreLogbook`  [INFERRED]
  apps/android/shared/src/jvmTest/kotlin/dev/divehub/shared/logbook/StoreLogbookTest.kt → apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/logbook/StoreLogbook.kt

## Import Cycles
- None detected.

## Communities (340 total, 122 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (260): account, buddySuggestion, diverExternalId, diveSiteExternalId, invitation, jointDive, passwordReset, sampleSeries (+252 more)

### Community 1 - "Community 1"
Cohesion: 0.04
Nodes (216): DisplaySettings(), AdminSettings(), DeleteUserDialog(), Invitations(), Invite(), LinkToPassOn(), ProviderSiteData(), SiteDataPanel() (+208 more)

### Community 2 - "Community 2"
Cohesion: 0.02
Nodes (143): CENTRE_SOURCE_NAME, CENTRE_SOURCES, CentreService, CentreView, Row, Source, toCentreView(), ADR-0043 (+135 more)

### Community 3 - "Community 3"
Cohesion: 0.03
Nodes (134): label(), Me, AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery() (+126 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (135): participant, providerGoneDive, ADR-0016, ADR-0053, externalDiver(), mergeExternalDiver(), participations(), ADR-0028 (+127 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (119): ChangePassword(), SecuritySettings(), Sessions(), useDescribeAgent(), AiAccessLogEntry, AiAccessView, authClient, invitationLinkQuery() (+111 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (134): importQuery(), buddiesForQuery(), BuddyView, ConflictChoices, ConflictEntryView, ConflictFieldView, ConflictsOfConnection, ConnectionView (+126 more)

### Community 7 - "Community 7"
Cohesion: 0.04
Nodes (124): DiveValues, DiveView, OverridableField, revisionsQuery(), RevisionView, UtcOffsetSource, cylinderCatalogueQuery(), CylinderFields() (+116 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (120): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, ambientPressureBar() (+112 more)

### Community 9 - "Community 9"
Cohesion: 0.04
Nodes (111): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+103 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (28): LogbookScreenshotTest, DiveListView, DiveListViewCounts, DiveListViewDivesItem, DiveListViewDivesItemGasesItem, DiveListViewDivesItemParticipantsItem, DiveListViewDivesItemSite, DiveListViewMonthsItem (+20 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (119): divesQuery(), LogbookPage, PAGE_SIZE, pushRunActive(), pushRunBody(), PushRunChoices, PushRunPreview, pushRunPreviewQuery() (+111 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (87): ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS, eachFile(), readEntry(), ADR-0044, walk() (+79 more)

### Community 13 - "Community 13"
Cohesion: 0.03
Nodes (21): AppContainer, MainActivity, MainViewModel, UiState, LoadingsUiState, LoadingsViewModel, Answers, number() (+13 more)

### Community 14 - "Community 14"
Cohesion: 0.04
Nodes (102): importsQuery(), ExternalIdView, App(), BackupsPage, CentrePage, CentresPage, currentRoute(), DivePage (+94 more)

### Community 15 - "Community 15"
Cohesion: 0.02
Nodes (104): CHANNELS, isRule(), read(), ADR-0036, ENGINE_VERSION, StoredConflictField, actorType, aiAccessOutcome (+96 more)

### Community 16 - "Community 16"
Cohesion: 0.04
Nodes (79): ParticipantRole, ParticipantView, keys, cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState (+71 more)

### Community 17 - "Community 17"
Cohesion: 0.03
Nodes (80): accountRelations, apikey, rateLimit, session, sessionRelations, ADR-0011, ADR-0012, ADR-0035 (+72 more)

### Community 18 - "Community 18"
Cohesion: 0.04
Nodes (102): diveNeighboursQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas (+94 more)

### Community 19 - "Community 19"
Cohesion: 0.05
Nodes (97): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, assessmentRoutes() (+89 more)

### Community 20 - "Community 20"
Cohesion: 0.04
Nodes (68): createServer(), loadAuthSecret(), acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock (+60 more)

### Community 21 - "Community 21"
Cohesion: 0.03
Nodes (79): cylinder, device, dive, diveFingerprint, lead, original, providerConflict, push (+71 more)

### Community 22 - "Community 22"
Cohesion: 0.04
Nodes (77): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+69 more)

### Community 23 - "Community 23"
Cohesion: 0.04
Nodes (72): SessionUser, fastify, FastifyRequest, publicPaths, sessionIdOf(), ADR-0011, ADR-0074, AttemptLimit (+64 more)

### Community 24 - "Community 24"
Cohesion: 0.06
Nodes (15): LeadEstimateViewModelTest, DiveViewRecordingsItemSummary, PlanningSacSource, default, logbook, Cylinder, Dive, Diver (+7 more)

### Community 25 - "Community 25"
Cohesion: 0.04
Nodes (43): ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem, ApiToolsGasPlanPost200ResponseUsed (+35 more)

### Community 26 - "Community 26"
Cohesion: 0.05
Nodes (80): allButDive(), continuationCandidateQuery(), deletedDivesOn(), DeletedDiveView, diveQuery(), mergeCandidatesQuery(), nearbyDivesQuery(), restorePreviewQuery() (+72 more)

### Community 27 - "Community 27"
Cohesion: 0.07
Nodes (71): GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult(), PlanInputs(), PlanRuleExplanation() (+63 more)

### Community 28 - "Community 28"
Cohesion: 0.04
Nodes (80): AiAccessScope, seesPositions(), VerifiedAccess, diversOf(), SiteRow, checkedByRunTool, createMcpServer(), INSTRUCTIONS (+72 more)

### Community 29 - "Community 29"
Cohesion: 0.05
Nodes (78): CertificationService, CYLINDER_MATERIALS, DIVE_IMPORT_MODES, ErrorAnswer, providerProblem(), IdParams, AccountRouteDeps, BuddyView (+70 more)

### Community 30 - "Community 30"
Cohesion: 0.03
Nodes (70): AssessmentService, AssessmentView, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView() (+62 more)

### Community 31 - "Community 31"
Cohesion: 0.04
Nodes (77): downsampleMinMax(), ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, buddiesOf(), BuddySearch (+69 more)

### Community 32 - "Community 32"
Cohesion: 0.08
Nodes (3): SyncLine(), LoadingSource, ErrorCodes

### Community 33 - "Community 33"
Cohesion: 0.03
Nodes (68): LoadingsView, Position, running(), SiteImportArea, siteImportsQuery(), SiteImportView, siteRevisionsQuery(), SiteRevisionView (+60 more)

### Community 34 - "Community 34"
Cohesion: 0.06
Nodes (67): siteImport, dataIdsOf(), findOrMake(), ADR-0057, createSitePickService(), ADR-0057, consoleLog, changeListenerOrNull() (+59 more)

### Community 35 - "Community 35"
Cohesion: 0.06
Nodes (63): NoFly, RecordingSummary, RecordingFacts, BottomTimeQuery, DEFAULT_GF_HIGH, diverLogbook(), planBottomTime(), residualSeedOf() (+55 more)

### Community 36 - "Community 36"
Cohesion: 0.04
Nodes (54): garminDive(), adapter, crossCheck(), decodeOfficial(), privateDir, ownersDiveParts(), through(), ADR-0063 (+46 more)

### Community 37 - "Community 37"
Cohesion: 0.04
Nodes (67): pushRunItem, PushRunLeftOut, asksFor(), DIVE_ONLY, DIVE_ONLY_PATTERN, DiveListFilter, diveListFilter(), DiveOnly (+59 more)

### Community 38 - "Community 38"
Cohesion: 0.06
Nodes (62): accountListEntry, diver, ParticipantRole, userPreference, tidyName(), DiverDetails, providerCylinderOf(), Caller (+54 more)

### Community 39 - "Community 39"
Cohesion: 0.06
Nodes (32): DiveViewRecordingsItem, DiveViewRecordingsItemDevice, DiveViewRecordingsItemSummarySurfaceBreaksItem, RecordingSamplesView, RecordingSamplesViewSeriesItem, diveLocalTime(), offsetId(), timeZoneNote() (+24 more)

### Community 40 - "Community 40"
Cohesion: 0.04
Nodes (11): KeystoreTokenStore, PreferencesInstanceUrlStore, UserCacheStore, InstanceUrlStore, TokenStore, UserScopedStore, SyncPhase, Details (+3 more)

### Community 41 - "Community 41"
Cohesion: 0.05
Nodes (65): ConnectionRow, WaitingPushOptions, createImport(), startImport(), audienceOfConnection(), audienceOfDive(), audienceOfDiver(), emitHint() (+57 more)

### Community 42 - "Community 42"
Cohesion: 0.06
Nodes (55): SsiClient, SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), buildUpdateRecord(), DiveForSsi, emptyRecord() (+47 more)

### Community 43 - "Community 43"
Cohesion: 0.06
Nodes (54): ImportArea, errorMessage(), looksLikeZip(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson() (+46 more)

### Community 44 - "Community 44"
Cohesion: 0.06
Nodes (67): ComputerChoice, DiveImportMode, DiveKind, ProviderImportPlan, providerSiteData, siteRef(), entryMatches(), kindOfMode() (+59 more)

### Community 45 - "Community 45"
Cohesion: 0.05
Nodes (7): AndroidDiveStore, DiveStore, toBoolean(), toLong(), toModel(), BodyWeight, DiveSite

### Community 46 - "Community 46"
Cohesion: 0.06
Nodes (46): appliedMigrationHead(), BufferedLog, createLogRingBuffer(), LOG_BUFFER_CAPACITY_BYTES, LOG_BUFFER_CAPACITY_ENTRIES, LogBufferStats, LogRingBuffer, LogRingBufferOptions (+38 more)

### Community 47 - "Community 47"
Cohesion: 0.06
Nodes (49): auth, BackupServiceDeps, createServices(), Services, ADR-0027, ADR-0030, ADR-0061, ADR-0063 (+41 more)

### Community 48 - "Community 48"
Cohesion: 0.09
Nodes (48): BottomTime, bottomTime(), BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen (+40 more)

### Community 49 - "Community 49"
Cohesion: 0.07
Nodes (56): OVERRIDABLE_FIELDS, renumber(), renumberStatement(), ADR-0048, AppendRow, channelsOf(), createContinuationService(), takeIn() (+48 more)

### Community 50 - "Community 50"
Cohesion: 0.06
Nodes (13): DiveDetailUiState, DiveDetailViewModel, ApiClient, ApiErrorBody, DiveRepository, DiveHubException, NoAnswerException, decodeList() (+5 more)

### Community 51 - "Community 51"
Cohesion: 0.07
Nodes (14): Missing(), LeadAnswer, LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseBasis (+6 more)

### Community 52 - "Community 52"
Cohesion: 0.09
Nodes (38): leadAnswer(), EstimateCylinder, EstimateDive, EstimateDiveCylinder, EstimateInput, PlannedConditions, SiteWaterType, brackish (+30 more)

### Community 53 - "Community 53"
Cohesion: 0.06
Nodes (60): AREAS, baseCandidates(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads(), exists() (+52 more)

### Community 54 - "Community 54"
Cohesion: 0.07
Nodes (60): managedDiverIds(), mergeProviderCylinder(), ProviderCylinder, participantsOf(), columnsOf(), leadOf(), mergeProviderLead(), ConflictField (+52 more)

### Community 55 - "Community 55"
Cohesion: 0.08
Nodes (57): Queryable, logbookCheckAnswer, logbookCheckDiveAnswer, keepApart(), keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration() (+49 more)

### Community 56 - "Community 56"
Cohesion: 0.05
Nodes (53): PARTICIPANT_ROLES, ADR-0053, ADR-0056, WaitingPushViewSchema, PROBLEM_CODES, storedProblemCode(), historyRoute, Made() (+45 more)

### Community 57 - "Community 57"
Cohesion: 0.07
Nodes (41): DiveBody(), DiveDetailRoute(), DiveDetailScreen(), errorText(), FactRow(), Facts(), Loading(), NeighbourButton() (+33 more)

### Community 58 - "Community 58"
Cohesion: 0.06
Nodes (22): LogbookViewModel, neighboursPath(), countOf(), FilterChip, filterChips(), LogbookFilter, CHANGED_SINCE_SENT, NO_RECORDING (+14 more)

### Community 59 - "Community 59"
Cohesion: 0.06
Nodes (51): EMPTY_SITE_IMPORT_COUNTS, ImportedValues, SiteImportCounts, ADR-0021, ADR-0025, ADR-0053, ExistingSite, Field (+43 more)

### Community 60 - "Community 60"
Cohesion: 0.05
Nodes (44): continuationAppend, diverManagement, equipmentItem, managesDiveDiver(), ADR-0016, CandidateError, CandidateRow, createCandidateService() (+36 more)

### Community 61 - "Community 61"
Cohesion: 0.11
Nodes (27): Body, Hold, Offline, RefreshData, refreshDeletedJson(), refreshDetailJson(), refreshDetailsPage(), refreshDiveId() (+19 more)

### Community 62 - "Community 62"
Cohesion: 0.07
Nodes (56): Answer, answer(), baseFrom(), BodyWeight, Confidence, high, low, medium (+48 more)

### Community 63 - "Community 63"
Cohesion: 0.05
Nodes (46): BACKUP_RUN_TASK, backupTask(), ADR-0069, replyCertificationError, STATUS, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError (+38 more)

### Community 64 - "Community 64"
Cohesion: 0.06
Nodes (35): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+27 more)

### Community 65 - "Community 65"
Cohesion: 0.05
Nodes (49): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, conflictForLena() (+41 more)

### Community 66 - "Community 66"
Cohesion: 0.10
Nodes (6): DiveDetailScreenshotTest, DiveViewFromRecording, DiveViewFromRecordingStartsAt, DiveViewParticipantsItem, DiveViewParticipantsItemCertificationHint, DiveViewWaterMismatch

### Community 67 - "Community 67"
Cohesion: 0.08
Nodes (48): BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32Encode(), bech32HrpExpand(), bech32Polymod() (+40 more)

### Community 68 - "Community 68"
Cohesion: 0.05
Nodes (42): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+34 more)

### Community 69 - "Community 69"
Cohesion: 0.09
Nodes (20): waterName(), GasPlanAnswer, gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel (+12 more)

### Community 70 - "Community 70"
Cohesion: 0.14
Nodes (23): Body, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage(), diverJson(), diveStartsAtOf() (+15 more)

### Community 71 - "Community 71"
Cohesion: 0.07
Nodes (45): diveSite, buildJoined(), partInput(), partsOfJoined(), rebuildJoined(), RecordingRow, ADR-0063, writeJoined() (+37 more)

### Community 72 - "Community 72"
Cohesion: 0.07
Nodes (29): connection, diveLease, providerLease, pushRun, OPENING_AFTER_MINUTES, SCHEDULED_FETCH_TASK, ADR-0056, keyLease() (+21 more)

### Community 73 - "Community 73"
Cohesion: 0.05
Nodes (39): SUIT_TYPES, SUIT_UNDERGARMENTS, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053, replyDiverError (+31 more)

### Community 74 - "Community 74"
Cohesion: 0.08
Nodes (13): ApiToolsBottomTimeGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseLimits, ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh (+5 more)

### Community 75 - "Community 75"
Cohesion: 0.06
Nodes (32): Tx, AppendUndo, OVERLAP_TOLERANCE_SECONDS, ChangeListener, REVISION_CAUSES, RevisionCause, ADR-0015, ADR-0016 (+24 more)

### Community 76 - "Community 76"
Cohesion: 0.12
Nodes (6): Complete, SessionEnded, Stopped, SyncEngine, SyncProgress, SyncResult

### Community 77 - "Community 77"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 78 - "Community 78"
Cohesion: 0.07
Nodes (41): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+33 more)

### Community 79 - "Community 79"
Cohesion: 0.07
Nodes (10): ApiDiverBodyWeightsGet200ResponseItem, ApiToolsBottomTimeGet200ResponseResidualN2, ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting (+2 more)

### Community 80 - "Community 80"
Cohesion: 0.12
Nodes (8): FakeCache, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore, UserScopedStore

### Community 81 - "Community 81"
Cohesion: 0.07
Nodes (40): LeadPlacement, OverridableField, CylinderInput, createDiveFromRecording(), DiveEdit, DiveRow, isProviderCopy(), merge() (+32 more)

### Community 82 - "Community 82"
Cohesion: 0.09
Nodes (39): Leave, SENT_KEYS, diveModeOfSsi(), keepTraining(), ssiDiveType(), ssiInteger(), ADR-0057, asNumber() (+31 more)

### Community 84 - "Community 84"
Cohesion: 0.07
Nodes (33): sampleCount(), SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject() (+25 more)

### Community 85 - "Community 85"
Cohesion: 0.09
Nodes (17): ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseCns, ApiDiversIdLoadingsGet200ResponseN2, ApiDiversIdLoadingsGet200ResponseNoFly, ApiDiversIdLoadingsGet200ResponseNoFlySource, ApiDiversIdLoadingsGet200ResponseOtu, cnsDecayed() (+9 more)

### Community 86 - "Community 86"
Cohesion: 0.07
Nodes (35): ADR-0025, WaterMismatch, waterMismatch(), RemoteSite, flag(), idText(), isObject(), numberOrNull() (+27 more)

### Community 87 - "Community 87"
Cohesion: 0.05
Nodes (26): Direction, DiveExport, DiveListing, FindBy, Operation, ProviderDive, ProviderErrorReason, ProviderId (+18 more)

### Community 88 - "Community 88"
Cohesion: 0.10
Nodes (35): Delivered, ProviderError, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf() (+27 more)

### Community 89 - "Community 89"
Cohesion: 0.05
Nodes (37): AREA_OF, areaOf(), areas, capture(), findings, headers, ADR-0020, ADR-0021 (+29 more)

### Community 90 - "Community 90"
Cohesion: 0.11
Nodes (33): BACKUP_SKIPPED_DIRS, BackupDecryptionKey, encryptionKeyFingerprint(), openArchiveStreamingWith(), assertPgTool(), execFileAsync, pgCommand(), pgErrorDetail() (+25 more)

### Community 91 - "Community 91"
Cohesion: 0.06
Nodes (36): Change(), Changes(), PositionSchema, AdoptBody, Country, CreateBody, Description, EditBody (+28 more)

### Community 92 - "Community 92"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 93 - "Community 93"
Cohesion: 0.10
Nodes (31): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0051 (+23 more)

### Community 94 - "Community 94"
Cohesion: 0.10
Nodes (13): drawBand(), ProfileSketch(), DiveProfileView, AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint (+5 more)

### Community 95 - "Community 95"
Cohesion: 0.11
Nodes (6): DiveView, DiveViewLeadItem, DiveViewPosition, DiveViewSuit, DiveViewWeightingFeedback, StoreLogbookTest

### Community 96 - "Community 96"
Cohesion: 0.06
Nodes (21): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, ServiceParts, ProviderLayer, ADR-0027 (+13 more)

### Community 97 - "Community 97"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 98 - "Community 98"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 99 - "Community 99"
Cohesion: 0.11
Nodes (20): AvatarCircle(), DiveRow(), Error(), Fact(), Filters(), Loading(), LogbookList(), LogbookRoute() (+12 more)

### Community 100 - "Community 100"
Cohesion: 0.12
Nodes (35): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+27 more)

### Community 101 - "Community 101"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 102 - "Community 102"
Cohesion: 0.08
Nodes (30): CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite(), lock() (+22 more)

### Community 103 - "Community 103"
Cohesion: 0.08
Nodes (31): AI_ACCESS_NAME_MAX, aiAccessLog, aiAccessSetting, Access, AccessView, AiAccessRouteDeps, LogEntryView, Scope (+23 more)

### Community 104 - "Community 104"
Cohesion: 0.09
Nodes (30): diverBodyWeight, DiverError, ACCOUNT_PATTERN, accountKey(), createDiverService(), applyDetails(), applyExternalId(), detailsOf() (+22 more)

### Community 105 - "Community 105"
Cohesion: 0.11
Nodes (30): DiveOrigin, REFERENCE_PREFIX, Series, SsiLogbook, asBool(), DIVE_ROW, SsiValueType, ADR-0027 (+22 more)

### Community 106 - "Community 106"
Cohesion: 0.08
Nodes (29): CandidateDive, DAY_MS, DuplicateReason, MatchDecision, MAX_OFFSET_MS, TimeSpan, ADR-0030, ADR-0037 (+21 more)

### Community 107 - "Community 107"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 108 - "Community 108"
Cohesion: 0.08
Nodes (21): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, killTree() (+13 more)

### Community 109 - "Community 109"
Cohesion: 0.18
Nodes (3): SyncCheckpoint, SyncState, DiveStoreTest

### Community 110 - "Community 110"
Cohesion: 0.08
Nodes (27): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+19 more)

### Community 111 - "Community 111"
Cohesion: 0.12
Nodes (27): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+19 more)

### Community 112 - "Community 112"
Cohesion: 0.09
Nodes (8): ApiDiveSitesGet200Response, SiteView, SiteViewExternalIdsItem, SiteViewExternalIdsItemAttribution, SiteViewExternalIdsItemOffered, SiteViewExternalIdsItemOfferedPosition, SiteViewExternalIdsItemPrivateTo, SiteViewPosition

### Community 113 - "Community 113"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 114 - "Community 114"
Cohesion: 0.10
Nodes (24): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearPushRuns(), clearQueue() (+16 more)

### Community 115 - "Community 115"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 117 - "Community 117"
Cohesion: 0.18
Nodes (7): planningSac(), PlanningSacDive, PlanningSacResult, BottomTimeToolInput, GasNumbersInput, ToolDiverSource, ToolsServiceTest

### Community 118 - "Community 118"
Cohesion: 0.12
Nodes (3): LauncherIconTest, SplashTheme, SplashThemeTest

### Community 119 - "Community 119"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 120 - "Community 120"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 121 - "Community 121"
Cohesion: 0.08
Nodes (22): headers, ADR-0035, aiAccessReady(), askMcp(), cancelWaitingImports(), createAiAccess(), putAside(), waitingImport() (+14 more)

### Community 123 - "Community 123"
Cohesion: 0.11
Nodes (7): ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport

### Community 124 - "Community 124"
Cohesion: 0.10
Nodes (22): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+14 more)

### Community 125 - "Community 125"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 126 - "Community 126"
Cohesion: 0.20
Nodes (3): GasPlanScreenshotTest, GasPlanToolDiver, GasPlanToolLevel

### Community 127 - "Community 127"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 128 - "Community 128"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 129 - "Community 129"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 130 - "Community 130"
Cohesion: 0.09
Nodes (4): DiveHubApplication, SyncWorker, WorkManagerPeriodicSync, PeriodicSyncScheduler

### Community 131 - "Community 131"
Cohesion: 0.08
Nodes (25): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+17 more)

### Community 132 - "Community 132"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 133 - "Community 133"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 134 - "Community 134"
Cohesion: 0.13
Nodes (6): DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, WaitingPushView

### Community 135 - "Community 135"
Cohesion: 0.12
Nodes (21): equipmentUse, DiveGear, GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow, ItemView (+13 more)

### Community 136 - "Community 136"
Cohesion: 0.09
Nodes (17): IMPORT_SOURCES, SITE_SOURCES, SOURCE_INFO, SourceInfo, ADR-0021, IMPORT_SOURCES, EGYPT, ExternalId (+9 more)

### Community 137 - "Community 137"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 138 - "Community 138"
Cohesion: 0.15
Nodes (8): Connected, Hint, HintFrame, LiveEvent, LiveHint, LiveHints, SseLiveHints, LiveHintsTest

### Community 139 - "Community 139"
Cohesion: 0.13
Nodes (21): BACKUP_FORMAT_VERSION, buildManifest(), decryptFile(), decryptFileWith(), decryptStreamWith(), encryptFile(), encryptStream(), execFileAsync (+13 more)

### Community 140 - "Community 140"
Cohesion: 0.15
Nodes (22): managementOf(), EquipmentError, checkCost(), checkDates(), checkWindow(), createEquipmentService(), assemble(), checkResets() (+14 more)

### Community 141 - "Community 141"
Cohesion: 0.12
Nodes (18): BOM, Json, num(), obj(), positive(), scaled(), time(), toRecording() (+10 more)

### Community 142 - "Community 142"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 144 - "Community 144"
Cohesion: 0.14
Nodes (17): createFileFormats(), createFitAdapter(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf() (+9 more)

### Community 145 - "Community 145"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 146 - "Community 146"
Cohesion: 0.15
Nodes (5): ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits

### Community 147 - "Community 147"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 148 - "Community 148"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource/poppins@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 149 - "Community 149"
Cohesion: 0.15
Nodes (16): AssessmentError, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), requireManagedDive(), same() (+8 more)

### Community 150 - "Community 150"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 151 - "Community 151"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 152 - "Community 152"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 153 - "Community 153"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 154 - "Community 154"
Cohesion: 0.19
Nodes (11): LeadInputTest, SuitType, drysuit, none, semidry, skin, wetsuit, SuitUndergarment (+3 more)

### Community 155 - "Community 155"
Cohesion: 0.11
Nodes (13): BACKUP_EXTENSION, BackupEncryption, BackupPart, BackupLog, BackupRunRow, BackupSettingsInput, BackupSettingsValues, DEFAULT_BACKUP_SETTINGS (+5 more)

### Community 156 - "Community 156"
Cohesion: 0.14
Nodes (14): FIND_BY, OPERATIONS, SignInInput, CASES, ContractHarness, declared(), ELSEWHERE, later() (+6 more)

### Community 157 - "Community 157"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 158 - "Community 158"
Cohesion: 0.14
Nodes (16): SUGGESTION_STATUSES, AcceptBody, STATUS, SuggestionRouteDeps, ADR-0070, SuggestionService, DiverRef, Offset (+8 more)

### Community 159 - "Community 159"
Cohesion: 0.18
Nodes (16): date(), FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position(), positive() (+8 more)

### Community 160 - "Community 160"
Cohesion: 0.20
Nodes (3): FakeInstances, FakeTokens, SyncAuthTest

### Community 161 - "Community 161"
Cohesion: 0.20
Nodes (14): AppLockProbe, BackupManifest, Arguments, credentialOf(), FILE_OPTIONS, fileText(), finishedArguments(), parseArguments() (+6 more)

### Community 162 - "Community 162"
Cohesion: 0.14
Nodes (14): AL80, EstimateDiveCylinder, LEAD_CASES, leadInput(), LeadVectorInput, planOf(), STEEL_12, ADR-0031 (+6 more)

### Community 163 - "Community 163"
Cohesion: 0.17
Nodes (3): Formats, durationMinutes(), depthIn()

### Community 164 - "Community 164"
Cohesion: 0.19
Nodes (4): DiveDetailsListView, DiveDetailsListViewCounts, DiveDetailsListViewMonthsItem, DiveDetailsListViewTotals

### Community 165 - "Community 165"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 166 - "Community 166"
Cohesion: 0.25
Nodes (15): jsRound(), powInt(), roundTo(), Sac, SacCylinder, SacDive, SacMissing, cylinder_incomplete (+7 more)

### Community 167 - "Community 167"
Cohesion: 0.15
Nodes (15): SuitType, SuitUndergarment, WeightingFelt, Suit, WeightingFeedback, LeadEstimateAnswer, LeadVectorDive, Confidence (+7 more)

### Community 168 - "Community 168"
Cohesion: 0.14
Nodes (9): FileFormat, FitAdapter, SuuntoJsonAdapter, UddfAdapter, ParsedRecording, ownersParts(), parse(), toPart() (+1 more)

### Community 169 - "Community 169"
Cohesion: 0.19
Nodes (13): asEstimateInput(), evaluateLeadVectorCase(), LEAD_VECTOR_KERNEL, leadVectorFile(), VECTOR_KERNELS, VectorFile, vectorFile(), assertMatch() (+5 more)

### Community 170 - "Community 170"
Cohesion: 0.23
Nodes (13): clientVersionGate(), ADR-0076, ClientVersionRegistry, MIN_CLIENT_VERSIONS, MinClientVersion, ADR-0076, compareIdentifier(), compareNumeric() (+5 more)

### Community 171 - "Community 171"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 172 - "Community 172"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 173 - "Community 173"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 174 - "Community 174"
Cohesion: 0.16
Nodes (14): serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, DueEntry, ScheduleView, equipmentList(), EquipmentListFilter (+6 more)

### Community 175 - "Community 175"
Cohesion: 0.16
Nodes (12): AssessmentRefresh, ADR-0036, ADR-0053, CandidateRouteDeps, CandidateView, Decided, DiveBrief, OffsetSource (+4 more)

### Community 176 - "Community 176"
Cohesion: 0.19
Nodes (11): decimal(), dive(), makeOceanicPlusUddf(), OCEANIC_DIVES, OceanicDive, site(), waypoint(), exported (+3 more)

### Community 177 - "Community 177"
Cohesion: 0.13
Nodes (6): FakeBroadcastChannel, FakeEventSource, Listener, locks, start(), ADR-0066

### Community 178 - "Community 178"
Cohesion: 0.13
Nodes (14): name, needs, runs-on, timeout-minutes, concurrency, cancel-in-progress, group, env (+6 more)

### Community 179 - "Community 179"
Cohesion: 0.27
Nodes (13): Estimate, LoadingBasis, LoadingDive, N2Loadings, N2UnavailableReason, no_profile, no_recent_dive, stale (+5 more)

### Community 180 - "Community 180"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 181 - "Community 181"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 182 - "Community 182"
Cohesion: 0.17
Nodes (10): EstimateInput, AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031 (+2 more)

### Community 183 - "Community 183"
Cohesion: 0.17
Nodes (6): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, uniqueWord()

### Community 184 - "Community 184"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 185 - "Community 185"
Cohesion: 0.21
Nodes (9): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, readQr(), ADR-0043, ADR-0043 (+1 more)

### Community 186 - "Community 186"
Cohesion: 0.26
Nodes (3): DiverView, DiverViewCertifications, DiverViewCertificationsNewest

### Community 187 - "Community 187"
Cohesion: 0.26
Nodes (3): DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 188 - "Community 188"
Cohesion: 0.21
Nodes (9): Config, loadConfig(), ADR-0011, ADR-0021, ADR-0024, ADR-0025, config, parseEncryptionKey() (+1 more)

### Community 189 - "Community 189"
Cohesion: 0.26
Nodes (11): UtcOffsetSource, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput, round3() (+3 more)

### Community 190 - "Community 190"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 191 - "Community 191"
Cohesion: 0.38
Nodes (9): DiveHubApp(), ErrorText(), InstanceForm(), LoadingScreen(), PasswordField(), PrimaryButton(), SignInForm(), Spinner() (+1 more)

### Community 193 - "Community 193"
Cohesion: 0.25
Nodes (8): createBackupService(), checkSettings(), due(), enqueueScheduled(), readSettings(), saveSettings(), sweepRetention(), isUniqueViolation()

### Community 194 - "Community 194"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 195 - "Community 195"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 196 - "Community 196"
Cohesion: 0.20
Nodes (10): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, ADR-0076, MODULE_IMPORTS, moduleRules (+2 more)

### Community 197 - "Community 197"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 198 - "Community 198"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 199 - "Community 199"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 200 - "Community 200"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 201 - "Community 201"
Cohesion: 0.20
Nodes (10): verify, prerelease, sha, tag, versionCode, versionName, name, outputs (+2 more)

### Community 202 - "Community 202"
Cohesion: 0.20
Nodes (10): with, cache, distribution, fetch-depth, if-no-files-found, java-version, name, path (+2 more)

### Community 203 - "Community 203"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 204 - "Community 204"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 205 - "Community 205"
Cohesion: 0.28
Nodes (8): itemIds(), ADR-0026, ADR-0034, Usage, UsageDive, usageMap(), usageOf(), UsageRow

### Community 207 - "Community 207"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 208 - "Community 208"
Cohesion: 0.22
Nodes (9): version, on, push, workflow_dispatch, tags, description, required, type (+1 more)

### Community 209 - "Community 209"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 210 - "Community 210"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 211 - "Community 211"
Cohesion: 0.29
Nodes (5): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, EstimateCylinder

### Community 212 - "Community 212"
Cohesion: 0.25
Nodes (7): AIR, expectWithinReference(), GF100_FRESH, GF100_SALT, GF85_FRESH, ADR-0032, ADR-0046

### Community 213 - "Community 213"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 214 - "Community 214"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 216 - "Community 216"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 217 - "Community 217"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 218 - "Community 218"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 219 - "Community 219"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 220 - "Community 220"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 221 - "Community 221"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 223 - "Community 223"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 224 - "Community 224"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 225 - "Community 225"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1278 isolated node(s):** `Reserve`, `RockBottom`, `Off`, `SuitThickness`, `ADR-0013` (+1273 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3533 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **122 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 21` to `Community 0`, `Community 2`, `Community 131`, `Community 4`, `Community 135`, `Community 12`, `Community 15`, `Community 17`, `Community 19`, `Community 20`, `Community 155`, `Community 28`, `Community 31`, `Community 34`, `Community 35`, `Community 36`, `Community 37`, `Community 38`, `Community 41`, `Community 44`, `Community 174`, `Community 47`, `Community 46`, `Community 49`, `Community 54`, `Community 55`, `Community 56`, `Community 60`, `Community 63`, `Community 71`, `Community 72`, `Community 75`, `Community 205`, `Community 81`, `Community 102`, `Community 103`, `Community 104`, `Community 106`, `Community 119`?**
  _High betweenness centrality (0.021) - this node is a cross-community bridge._
- **What connects `Reserve`, `RockBottom`, `Off` to the rest of the system?**
  _1278 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.01600632348582156 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 5` to `Community 1`, `Community 33`, `Community 3`, `Community 6`, `Community 7`, `Community 9`, `Community 11`, `Community 14`, `Community 16`, `Community 177`, `Community 18`, `Community 115`, `Community 148`, `Community 26`?**
  _High betweenness centrality (0.017) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.04343509555721023 - nodes in this community are weakly interconnected._
- **Why does `Button()` connect `Community 1` to `Community 33`, `Community 3`, `Community 5`, `Community 6`, `Community 7`, `Community 9`, `Community 11`, `Community 14`, `Community 16`, `Community 18`, `Community 26`?**
  _High betweenness centrality (0.014) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.02206752523494605 - nodes in this community are weakly interconnected._