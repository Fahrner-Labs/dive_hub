# Graph Report - dive_hub  (2026-11-20)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 10123 nodes · 30580 edges · 343 communities (217 shown, 126 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 545 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `ec6d9593`
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
- Community 227
- Community 228
- Community 229
- Community 230
- Community 234

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
- `RefreshHarness` --calls--> `SyncRefresher`  [INFERRED]
  apps/android/shared/src/jvmTest/kotlin/dev/divehub/shared/sync/SyncRefreshTest.kt → apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/sync/SyncRefresher.kt
- `EquipmentListFilter` --references--> `EquipmentCategory`  [EXTRACTED]
  apps/server/src/equipment/read.ts → apps/server/src/vocabulary.ts

## Import Cycles
- None detected.

## Communities (343 total, 126 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.01
Nodes (341): user, migrateDatabase(), ADR-0004, buddySuggestion, cylinder, dive, diveAssessment, diveLease (+333 more)

### Community 1 - "Community 1"
Cohesion: 0.02
Nodes (239): RemoteCertification, createCertificationService(), requireManagedDiver(), connection, ConnectionRow, diveFingerprint, importJob, providerGoneDive (+231 more)

### Community 2 - "Community 2"
Cohesion: 0.05
Nodes (203): ChangePassword(), DisplaySettings(), Sessions(), useDescribeAgent(), AdminSettings(), DeleteUserDialog(), Invitations(), Invite() (+195 more)

### Community 3 - "Community 3"
Cohesion: 0.02
Nodes (195): cylinderCatalogueQuery(), equipmentOfDiverQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery() (+187 more)

### Community 4 - "Community 4"
Cohesion: 0.02
Nodes (150): auth, hashPassword(), BackupServiceDeps, createServices(), ServiceParts, ADR-0027, ADR-0030, ADR-0061 (+142 more)

### Community 5 - "Community 5"
Cohesion: 0.02
Nodes (157): diveGearQuery(), EquipmentCategory, keys, ProviderView, RequirementView, api, centreQuery(), centresQuery() (+149 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (154): Tx, EMPTY_SITE_IMPORT_COUNTS, ImportArea, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025 (+146 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (163): allButDive(), AnyLogbookCheck, CandidateView, continuationCandidateQuery(), ContinuationCandidateView, deletedDivesOn(), DeletedDivesPageView, deletedDivesQuery() (+155 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (135): SecuritySettings(), Me, divesQuery(), DiveSummary, LogbookPage, PAGE_SIZE, equipmentDueQuery(), siteQuery() (+127 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (123): Auth, CentreService, CentreRouteDeps, UTC_OFFSET_SOURCES, WEIGHTING_FELT, DiverService, DiverRouteDeps, waterMismatch() (+115 more)

### Community 10 - "Community 10"
Cohesion: 0.04
Nodes (115): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+107 more)

### Community 11 - "Community 11"
Cohesion: 0.04
Nodes (108): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, ambientPressureBar() (+100 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (98): AiAccessLogEntry, aiAccessLogQuery(), aiAccessQuery(), AiAccessView, invitationLinkQuery(), invitationsQuery(), InvitationView, passwordResetQuery() (+90 more)

### Community 13 - "Community 13"
Cohesion: 0.02
Nodes (81): ParticipantRole, providerConflict, pushRunItem, record(), providerError(), Delivered, Direction, DiveExport (+73 more)

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (95): accountListEntry, diver, diverExternalId, participant, DiverError, ADR-0016, ADR-0053, tidyName() (+87 more)

### Community 15 - "Community 15"
Cohesion: 0.05
Nodes (26): SyncLine(), segmentName(), Adjustment(), Conditions(), hoodName(), levelName(), materialName(), Missing() (+18 more)

### Community 16 - "Community 16"
Cohesion: 0.03
Nodes (79): createServer(), createAuth(), loadAuthSecret(), acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS (+71 more)

### Community 17 - "Community 17"
Cohesion: 0.05
Nodes (91): label(), AcceptedBuddySuggestionView, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView, candidatesQuery() (+83 more)

### Community 18 - "Community 18"
Cohesion: 0.04
Nodes (93): Queryable, ComputerChoice, DiveImportMode, DiveKind, importOriginal, UtcOffsetSource, ProviderCylinder, siteRef() (+85 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (91): CertificationService, CertificationRouteDeps, CYLINDER_MATERIALS, DIVE_IMPORT_MODES, MATCHING_WINDOWS, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036 (+83 more)

### Community 20 - "Community 20"
Cohesion: 0.03
Nodes (80): DIVE_FIELDS, SSI_CAPABILITIES, createSsiClient(), DELETE_QUIET_MS, Candidate, Deleted, DiveView, Outcome (+72 more)

### Community 21 - "Community 21"
Cohesion: 0.04
Nodes (88): @modelcontextprotocol/server@^2.3.1, DUE_STATES, AccessRefusal, AiAccessScope, seesPositions(), VerifiedAccess, mcpEndpoint(), McpEndpointDeps (+80 more)

### Community 22 - "Community 22"
Cohesion: 0.04
Nodes (47): ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem, ApiToolsGasPlanPost200ResponseUsed (+39 more)

### Community 23 - "Community 23"
Cohesion: 0.04
Nodes (84): buddiesForQuery(), buddiesQuery(), BuddyView, ConflictChoices, ConflictEntryView, ConflictFieldView, ConflictsOfConnection, connectionsQuery() (+76 more)

### Community 24 - "Community 24"
Cohesion: 0.02
Nodes (83): CHANNELS, ADR-0036, ENGINE_VERSION, actorType, aiAccessLog, aiAccessOutcome, backupRunKind, backupRunStatus (+75 more)

### Community 25 - "Community 25"
Cohesion: 0.07
Nodes (44): DiveBody(), DiveDetailRoute(), DiveDetailScreen(), errorText(), FactRow(), Facts(), Loading(), NeighbourButton() (+36 more)

### Community 26 - "Community 26"
Cohesion: 0.07
Nodes (76): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, AssessmentService (+68 more)

### Community 27 - "Community 27"
Cohesion: 0.05
Nodes (72): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+64 more)

### Community 28 - "Community 28"
Cohesion: 0.05
Nodes (65): adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, AuthOptions, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, ADR-0011, ADR-0012 (+57 more)

### Community 29 - "Community 29"
Cohesion: 0.04
Nodes (67): SUGGESTION_STATUSES, Participant, RowExtras, DiveProfile, DiveProfileView, DiveProfileViewSchema, ADR-0041, Change() (+59 more)

### Community 30 - "Community 30"
Cohesion: 0.05
Nodes (57): SsiClient, SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), buildUpdateRecord(), DiveForSsi, emptyRecord() (+49 more)

### Community 31 - "Community 31"
Cohesion: 0.04
Nodes (69): LEAD_PLACEMENTS, downsampleMinMax(), buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter (+61 more)

### Community 32 - "Community 32"
Cohesion: 0.09
Nodes (57): Formats, formatSyncTime(), GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult() (+49 more)

### Community 33 - "Community 33"
Cohesion: 0.07
Nodes (61): OVERRIDABLE_FIELDS, renumber(), renumberStatement(), ADR-0048, keepApart(), askedAt(), AppendRow, channelsOf() (+53 more)

### Community 34 - "Community 34"
Cohesion: 0.06
Nodes (22): GasPlanAnswer, gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number() (+14 more)

### Community 35 - "Community 35"
Cohesion: 0.05
Nodes (8): AndroidDiveStore, DiveStore, toBoolean(), toLong(), toModel(), BodyWeight, DiveSite, Recording

### Community 36 - "Community 36"
Cohesion: 0.08
Nodes (9): DiveDetailScreenshotTest, DiveViewFromRecording, DiveViewFromRecordingStartsAt, DiveViewParticipantsItem, DiveViewParticipantsItemCertificationHint, DiveViewRecordingsItemDevice, DiveViewWaterMismatch, NeighboursView (+1 more)

### Community 37 - "Community 37"
Cohesion: 0.06
Nodes (31): diveLocalTime(), offsetId(), timeZoneNote(), TimeZoneOrigin, DEVICE, NEARBY, POSITION, UNKNOWN (+23 more)

### Community 38 - "Community 38"
Cohesion: 0.07
Nodes (58): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+50 more)

### Community 39 - "Community 39"
Cohesion: 0.06
Nodes (8): AppContainer, MainActivity, MainViewModel, UiState, LoadingsUiState, LoadingsViewModel, UserView, InstanceUrl

### Community 40 - "Community 40"
Cohesion: 0.04
Nodes (54): ADR-0025, WaterMismatch, EquipmentService, RecordInput, ScheduleInput, DiveGearItemViewSchema, DiveGearViewSchema, DueState (+46 more)

### Community 41 - "Community 41"
Cohesion: 0.07
Nodes (46): looksLikeZip(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy(), ADR-0024 (+38 more)

### Community 42 - "Community 42"
Cohesion: 0.10
Nodes (8): LeadEstimateViewModelTest, Cylinder, Dive, Diver, toStore(), json(), LogbookRepositoryTest, RepositoryHarness

### Community 43 - "Community 43"
Cohesion: 0.11
Nodes (27): Body, Hold, Offline, RefreshData, refreshDeletedJson(), refreshDetailJson(), refreshDetailsPage(), refreshDiveId() (+19 more)

### Community 44 - "Community 44"
Cohesion: 0.05
Nodes (49): AI_ACCESS_NAME_MAX, BackupRouteDeps, BACKUP_RUN_TASK, BackupService, backupTask(), ADR-0069, aiAccessSetting, PurgeService (+41 more)

### Community 45 - "Community 45"
Cohesion: 0.04
Nodes (42): replyCertificationError, STATUS, CertificationAccount, CertificationProviders, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK (+34 more)

### Community 46 - "Community 46"
Cohesion: 0.05
Nodes (51): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, cancelWaitingImports() (+43 more)

### Community 47 - "Community 47"
Cohesion: 0.06
Nodes (12): DiveListView, DiveListViewCounts, DiveListViewTotals, DiveViewLeadItem, DiveViewSac, DiveViewSite, DiveViewWeightingFeedback, NeighboursViewPrevious (+4 more)

### Community 48 - "Community 48"
Cohesion: 0.07
Nodes (51): agreeInDepthAndDuration(), CheckedDive, DeletableDive, findChecks(), followsSoon(), LOGBOOK_CHECK_RULES, LOGBOOK_CHECKS_VERSION, LogbookCheck (+43 more)

### Community 49 - "Community 49"
Cohesion: 0.06
Nodes (15): drawBand(), ProfileSketch(), DiveProfileView, LogbookPaging, AscentBand, SketchCurve, SketchGeometry, sketchGeometry() (+7 more)

### Community 50 - "Community 50"
Cohesion: 0.05
Nodes (48): PARTICIPANT_ROLES, siteImportStatus, asksFor(), DIVE_ONLY, DIVE_ONLY_PATTERN, DiveOnly, FINDINGS_SHOWN, NO_RECORDING (+40 more)

### Community 51 - "Community 51"
Cohesion: 0.07
Nodes (48): providerStatusQuery(), ProblemCode, focusFirstIn(), useFocusBackWhenClosed(), holdLeave(), mayLeave(), useLeaveGuard(), useFieldNames() (+40 more)

### Community 52 - "Community 52"
Cohesion: 0.11
Nodes (39): ambientPressureBar(), bestMixPercent(), Conditions, depthOfPressure(), eadMetres(), endMetres(), GasMix, isHypoxic() (+31 more)

### Community 53 - "Community 53"
Cohesion: 0.08
Nodes (52): Answer, answer(), baseFrom(), BodyWeight, Confidence, high, low, medium (+44 more)

### Community 54 - "Community 54"
Cohesion: 0.10
Nodes (26): BottomTime, bottomTime(), BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen (+18 more)

### Community 55 - "Community 55"
Cohesion: 0.06
Nodes (34): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+26 more)

### Community 57 - "Community 57"
Cohesion: 0.08
Nodes (48): BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32Encode(), bech32HrpExpand(), bech32Polymod() (+40 more)

### Community 58 - "Community 58"
Cohesion: 0.05
Nodes (42): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+34 more)

### Community 59 - "Community 59"
Cohesion: 0.06
Nodes (43): diveListFilter(), notAtProvider(), Blocked, classify(), FailureOutcome, failureOutcome(), kindOf(), KINDS (+35 more)

### Community 60 - "Community 60"
Cohesion: 0.06
Nodes (38): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+30 more)

### Community 61 - "Community 61"
Cohesion: 0.14
Nodes (23): Body, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage(), diverJson(), diveStartsAtOf() (+15 more)

### Community 62 - "Community 62"
Cohesion: 0.08
Nodes (42): DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining(), ssiDiveType(), ssiInteger() (+34 more)

### Community 63 - "Community 63"
Cohesion: 0.07
Nodes (38): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+30 more)

### Community 64 - "Community 64"
Cohesion: 0.06
Nodes (35): DiveExportAction, buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open(), saveAndReadBack() (+27 more)

### Community 65 - "Community 65"
Cohesion: 0.08
Nodes (36): EstimateDive, EstimateDiveCylinder, WeightingFeedback, WeightingFelt, right, too_heavy, too_light, dayNumber() (+28 more)

### Community 66 - "Community 66"
Cohesion: 0.06
Nodes (31): accountRelations, apikey, rateLimit, sessionRelations, ADR-0011, ADR-0012, ADR-0035, twoFactor (+23 more)

### Community 67 - "Community 67"
Cohesion: 0.05
Nodes (42): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+34 more)

### Community 68 - "Community 68"
Cohesion: 0.08
Nodes (12): ApiToolsBottomTimeGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseLimits, ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh (+4 more)

### Community 69 - "Community 69"
Cohesion: 0.07
Nodes (9): ApiClient, ApiErrorBody, DiveHubException, NoAnswerException, decodeList(), LogbookRepository, providerDirectory(), ApiClientTest (+1 more)

### Community 70 - "Community 70"
Cohesion: 0.07
Nodes (41): NoFly, getDiverLoadings, DEFAULT_GF_HIGH, residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows(), DiveRow (+33 more)

### Community 71 - "Community 71"
Cohesion: 0.07
Nodes (36): ImportErrorCode, ImportFound, errorMessage(), ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS, eachFile() (+28 more)

### Community 72 - "Community 72"
Cohesion: 0.06
Nodes (36): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+28 more)

### Community 73 - "Community 73"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 74 - "Community 74"
Cohesion: 0.06
Nodes (6): KeystoreTokenStore, PreferencesInstanceUrlStore, UserCacheStore, InstanceUrlStore, TokenStore, UserScopedStore

### Community 75 - "Community 75"
Cohesion: 0.13
Nodes (6): Complete, SessionEnded, Stopped, SyncEngine, SyncProgress, SyncResult

### Community 76 - "Community 76"
Cohesion: 0.12
Nodes (8): FakeCache, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore, UserScopedStore

### Community 77 - "Community 77"
Cohesion: 0.08
Nodes (35): BottomTimeQuery, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033, ADR-0071, DEFAULT_PPO2 (+27 more)

### Community 78 - "Community 78"
Cohesion: 0.09
Nodes (12): ApiDiverBodyWeightsGet200ResponseItem, DiveListViewDivesItem, DiveListViewDivesItemGasesItem, DiveListViewDivesItemParticipantsItem, DiveListViewDivesItemSite, DiveListViewMonthsItem, localMonthOf(), LogbookMonth (+4 more)

### Community 79 - "Community 79"
Cohesion: 0.10
Nodes (40): LeadPlacement, mergeProviderCylinder(), providerCylinderOf(), participantsOf(), columnsOf(), DiveRow, LeadEntry, leadOf() (+32 more)

### Community 80 - "Community 80"
Cohesion: 0.08
Nodes (38): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+30 more)

### Community 81 - "Community 81"
Cohesion: 0.10
Nodes (33): BACKUP_SKIPPED_DIRS, BackupDecryptionKey, encryptionKeyFingerprint(), openArchiveStreamingWith(), assertPgTool(), execFileAsync, pgCommand(), pgErrorDetail() (+25 more)

### Community 82 - "Community 82"
Cohesion: 0.07
Nodes (33): SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject(), MAX_CARD_IMAGE_BYTES (+25 more)

### Community 83 - "Community 83"
Cohesion: 0.10
Nodes (17): DiveDetailViewModel, DiveRepository, neighboursPath(), countOf(), FilterChip, filterChips(), LogbookFilter, CHANGED_SINCE_SENT (+9 more)

### Community 84 - "Community 84"
Cohesion: 0.10
Nodes (22): AvatarCircle(), DiveRow(), Error(), Fact(), Filters(), Loading(), LogbookList(), LogbookRoute() (+14 more)

### Community 85 - "Community 85"
Cohesion: 0.10
Nodes (32): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0051 (+24 more)

### Community 86 - "Community 86"
Cohesion: 0.11
Nodes (36): importQuery(), importsQuery(), ImportView, ADR-0030, uploadFile(), noAnswerError(), Logbook(), ImportContext (+28 more)

### Community 87 - "Community 87"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 88 - "Community 88"
Cohesion: 0.12
Nodes (6): LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200ResponseBasis, ApiDiversIdLeadEstimateGet200ResponseBasisDivesItem, ApiDiversIdLeadEstimateGet200ResponseMissingItem

### Community 89 - "Community 89"
Cohesion: 0.06
Nodes (35): SUIT_TYPES, SUIT_UNDERGARMENTS, replyDiverError, STATUS, CertificationSummary, DiverDetailsView, DiverDetailsViewSchema, DiverSource (+27 more)

### Community 90 - "Community 90"
Cohesion: 0.09
Nodes (37): createDiveFromRecording(), overlapWindow(), recordingKeySource(), applyDecision(), deletedEarlier(), fullerCopyHere(), IsManaged, knownRecording() (+29 more)

### Community 91 - "Community 91"
Cohesion: 0.10
Nodes (32): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+24 more)

### Community 92 - "Community 92"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 93 - "Community 93"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 94 - "Community 94"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 95 - "Community 95"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 96 - "Community 96"
Cohesion: 0.09
Nodes (10): ApiMeGet200Response, ApiMeGet200ResponseOwnDiver, ApiMeGet200ResponsePreferences, SignInEmailRequest, Loading, SessionRepository, SessionState, SignedIn (+2 more)

### Community 97 - "Community 97"
Cohesion: 0.13
Nodes (34): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+26 more)

### Community 98 - "Community 98"
Cohesion: 0.09
Nodes (4): DiveHubTheme(), LauncherIconTest, SplashTheme, SplashThemeTest

### Community 99 - "Community 99"
Cohesion: 0.08
Nodes (29): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+21 more)

### Community 100 - "Community 100"
Cohesion: 0.11
Nodes (11): ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseCns, ApiDiversIdLoadingsGet200ResponseN2, ApiDiversIdLoadingsGet200ResponseNoFly, ApiDiversIdLoadingsGet200ResponseNoFlySource, ApiDiversIdLoadingsGet200ResponseOtu, cnsDecayed() (+3 more)

### Community 101 - "Community 101"
Cohesion: 0.10
Nodes (5): SyncPhase, Details, Meta, Profiles, SyncRefresher

### Community 102 - "Community 102"
Cohesion: 0.18
Nodes (3): SyncCheckpoint, SyncState, DiveStoreTest

### Community 103 - "Community 103"
Cohesion: 0.08
Nodes (22): LoadingsView, useLanguage(), CustomTypeOptions, i18next, initI18n(), resources, ADR-0014, Language (+14 more)

### Community 104 - "Community 104"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 105 - "Community 105"
Cohesion: 0.09
Nodes (27): OverridableField, CylinderInput, AppendUndo, DiveEdit, DiveRow, isProviderCopy(), merge(), primaryOf() (+19 more)

### Community 106 - "Community 106"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 107 - "Community 107"
Cohesion: 0.17
Nodes (4): GasPlanScreenshotTest, GasPlanToolDiver, GasPlanToolInput, GasPlanToolLevel

### Community 108 - "Community 108"
Cohesion: 0.09
Nodes (27): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+19 more)

### Community 109 - "Community 109"
Cohesion: 0.12
Nodes (7): ApiToolsBottomTimeGet200ResponseResidualN2, ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting

### Community 110 - "Community 110"
Cohesion: 0.10
Nodes (19): ProblemCode, ResponseState, whenResponseGone(), StoredBlob, PlacedArchive, ANSWER, looksLikeRecipientPrivateKey(), PlacedArchive (+11 more)

### Community 111 - "Community 111"
Cohesion: 0.15
Nodes (29): kindOfMode(), deviceDiver(), importsField(), createDiveImportService(), asRecording(), assess(), computersOf(), createDive() (+21 more)

### Community 112 - "Community 112"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 113 - "Community 113"
Cohesion: 0.10
Nodes (22): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearQueue(), conflictAtSsi() (+14 more)

### Community 114 - "Community 114"
Cohesion: 0.26
Nodes (6): leadAnswer(), EstimateCylinder, PlannedConditions, Suit, leadEstimate(), PersonalizedToolsTest

### Community 115 - "Community 115"
Cohesion: 0.10
Nodes (24): tieToJoined(), partInput(), RecordingRow, ADR-0063, CylinderError, cylindersOf(), fillCylindersFromRecording(), reviseDive() (+16 more)

### Community 116 - "Community 116"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 117 - "Community 117"
Cohesion: 0.11
Nodes (23): AssessmentError, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), requireManagedDive(), isRule() (+15 more)

### Community 118 - "Community 118"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 119 - "Community 119"
Cohesion: 0.08
Nodes (21): Copies, decodeDeletedCursor(), DeleteProblemSchema, Problem, PlanningService, BottomTimeQuery, BottomTimeView, Depth (+13 more)

### Community 120 - "Community 120"
Cohesion: 0.13
Nodes (22): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DueInput, DueSchedule (+14 more)

### Community 121 - "Community 121"
Cohesion: 0.12
Nodes (23): ScheduleDue, DiveGear, DueEntry, GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow (+15 more)

### Community 122 - "Community 122"
Cohesion: 0.12
Nodes (19): createFileFormats(), FileFormat, looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf() (+11 more)

### Community 123 - "Community 123"
Cohesion: 0.17
Nodes (23): DiveOrigin, SsiLogbook, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef(), MATERIAL_OF_TANK_TYPE (+15 more)

### Community 124 - "Community 124"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 125 - "Community 125"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 126 - "Community 126"
Cohesion: 0.09
Nodes (4): DiveHubApplication, SyncWorker, WorkManagerPeriodicSync, PeriodicSyncScheduler

### Community 127 - "Community 127"
Cohesion: 0.12
Nodes (7): LeadAnswer, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseLeftOut, ApiDiversIdLeadEstimateGet200ResponseRange, ApiDiversIdLeadEstimateGet200ResponseReserve, ApiDiversIdLeadEstimateGet200ResponseWeightCheck

### Community 128 - "Community 128"
Cohesion: 0.18
Nodes (3): DiveViewValues, DiveViewValuesStartsAt, StoreLogbookTest

### Community 129 - "Community 129"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 131 - "Community 131"
Cohesion: 0.13
Nodes (6): DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, WaitingPushView

### Community 132 - "Community 132"
Cohesion: 0.08
Nodes (24): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+16 more)

### Community 133 - "Community 133"
Cohesion: 0.12
Nodes (22): BACKUP_FORMAT_VERSION, BackupManifest, buildManifest(), decryptFile(), decryptFileWith(), decryptStreamWith(), encryptFile(), encryptStream() (+14 more)

### Community 134 - "Community 134"
Cohesion: 0.13
Nodes (19): RecordingSummary, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput, round3() (+11 more)

### Community 135 - "Community 135"
Cohesion: 0.11
Nodes (14): managesDiveDiver(), CandidateError, createCandidateService(), lockMine(), keepRestored(), createDiveService(), participantsOfDives(), createLogbookService() (+6 more)

### Community 136 - "Community 136"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 138 - "Community 138"
Cohesion: 0.21
Nodes (7): DiveViewRecordingsItemSummary, summaryOf(), assembleToolSources(), epochMs(), recentDiveEnd(), summaryOf(), ToolSourcesStoreTest

### Community 139 - "Community 139"
Cohesion: 0.09
Nodes (22): accountKey(), applyDetails(), applyExternalId(), editable(), insertExternal(), REVISION_CAUSES, ADR-0015, ADR-0016 (+14 more)

### Community 140 - "Community 140"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 141 - "Community 141"
Cohesion: 0.18
Nodes (10): DiveHubApp(), ErrorText(), InstanceForm(), LoadingScreen(), PasswordField(), PrimaryButton(), SignInForm(), Spinner() (+2 more)

### Community 142 - "Community 142"
Cohesion: 0.16
Nodes (8): Connected, Hint, HintFrame, LiveEvent, LiveHint, LiveHints, SseLiveHints, LiveHintsTest

### Community 144 - "Community 144"
Cohesion: 0.20
Nodes (18): planningSac(), PlanningSacDive, jsRound(), powInt(), roundTo(), Sac, SacCylinder, SacDive (+10 more)

### Community 145 - "Community 145"
Cohesion: 0.12
Nodes (18): CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043, CreateBody (+10 more)

### Community 146 - "Community 146"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 147 - "Community 147"
Cohesion: 0.19
Nodes (11): LeadInputTest, CylinderMaterial, aluminium, carbon, steel, SuitType, drysuit, none (+3 more)

### Community 148 - "Community 148"
Cohesion: 0.15
Nodes (5): ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits

### Community 149 - "Community 149"
Cohesion: 0.15
Nodes (5): ProviderSummaryData, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport

### Community 150 - "Community 150"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 151 - "Community 151"
Cohesion: 0.15
Nodes (5): SiteViewExternalIdsItem, SiteViewExternalIdsItemAttribution, SiteViewExternalIdsItemOffered, SiteViewExternalIdsItemOfferedPosition, SiteViewExternalIdsItemPrivateTo

### Community 152 - "Community 152"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource/poppins@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 153 - "Community 153"
Cohesion: 0.13
Nodes (17): AL80, asEstimateInput(), EstimateDiveCylinder, evaluateLeadVectorCase(), LEAD_CASES, leadInput(), leadVectorFile(), LeadVectorInput (+9 more)

### Community 154 - "Community 154"
Cohesion: 0.17
Nodes (16): clientVersionGate(), ADR-0076, ClientVersionRegistry, MIN_CLIENT_VERSIONS, MinClientVersion, ADR-0076, clientRequestSerializer(), ADR-0076 (+8 more)

### Community 155 - "Community 155"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 156 - "Community 156"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 157 - "Community 157"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 158 - "Community 158"
Cohesion: 0.11
Nodes (13): BACKUP_EXTENSION, BackupEncryption, BackupPart, BackupLog, BackupRunRow, BackupSettingsInput, BackupSettingsValues, DEFAULT_BACKUP_SETTINGS (+5 more)

### Community 159 - "Community 159"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 161 - "Community 161"
Cohesion: 0.20
Nodes (3): FakeInstances, FakeTokens, SyncAuthTest

### Community 162 - "Community 162"
Cohesion: 0.14
Nodes (15): serviceRecordSchedule, serviceSchedule, equipmentList(), EquipmentListFilter, EquipmentRecordRead, today(), ADR-0034, ADR-0035 (+7 more)

### Community 163 - "Community 163"
Cohesion: 0.15
Nodes (10): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, ADR-0068 (+2 more)

### Community 164 - "Community 164"
Cohesion: 0.17
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 165 - "Community 165"
Cohesion: 0.19
Nodes (4): DiveDetailsListView, DiveDetailsListViewCounts, DiveDetailsListViewMonthsItem, DiveDetailsListViewTotals

### Community 166 - "Community 166"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 167 - "Community 167"
Cohesion: 0.22
Nodes (13): AppLockProbe, Arguments, credentialOf(), FILE_OPTIONS, fileText(), finishedArguments(), parseArguments(), RestoreCliDeps (+5 more)

### Community 168 - "Community 168"
Cohesion: 0.17
Nodes (12): DIVE_KINDS, IMPORT_ERROR_CODES, OUTCOME_REASONS, ImportView, ImportViewSchema, toImportView(), ADR-0030, ImportRouteDeps (+4 more)

### Community 169 - "Community 169"
Cohesion: 0.15
Nodes (15): SuitType, SuitUndergarment, WeightingFelt, Suit, WeightingFeedback, LeadEstimateAnswer, LeadVectorDive, Confidence (+7 more)

### Community 170 - "Community 170"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 171 - "Community 171"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 172 - "Community 172"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 173 - "Community 173"
Cohesion: 0.19
Nodes (13): flag(), idText(), isObject(), numberOrNull(), readSsiSite(), ssiSiteRow(), SsiSiteValues, visibilityFor() (+5 more)

### Community 174 - "Community 174"
Cohesion: 0.14
Nodes (7): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, headers, uniqueWord()

### Community 175 - "Community 175"
Cohesion: 0.18
Nodes (11): addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, relations(), TimelineBar, TimelineBarGeometry, TimelineGapGeometry, TimelineGeometry (+3 more)

### Community 176 - "Community 176"
Cohesion: 0.13
Nodes (14): name, needs, runs-on, timeout-minutes, concurrency, cancel-in-progress, group, env (+6 more)

### Community 177 - "Community 177"
Cohesion: 0.19
Nodes (9): BackupError, createBackupService(), checkSettings(), due(), enqueueScheduled(), readSettings(), saveSettings(), sweepRetention() (+1 more)

### Community 178 - "Community 178"
Cohesion: 0.15
Nodes (12): EquipmentError, ADR-0034, ADR-0053, checkCost(), checkDates(), checkWindow(), checkResets(), checkSchedule() (+4 more)

### Community 179 - "Community 179"
Cohesion: 0.36
Nodes (4): decode(), localName(), Reader, XmlError

### Community 180 - "Community 180"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 181 - "Community 181"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 182 - "Community 182"
Cohesion: 0.23
Nodes (10): LEAD_VECTOR_KERNEL, VECTOR_KERNELS, VectorFile, vectorFile(), assertMatch(), checkVectors(), isRecord(), readVectors() (+2 more)

### Community 183 - "Community 183"
Cohesion: 0.17
Nodes (10): EstimateInput, AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031 (+2 more)

### Community 184 - "Community 184"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 185 - "Community 185"
Cohesion: 0.21
Nodes (9): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, readQr(), ADR-0043, ADR-0043 (+1 more)

### Community 187 - "Community 187"
Cohesion: 0.26
Nodes (3): ApiDiveSitesGet200Response, SiteView, SiteViewPosition

### Community 188 - "Community 188"
Cohesion: 0.26
Nodes (3): DiverView, DiverViewCertifications, DiverViewCertificationsNewest

### Community 189 - "Community 189"
Cohesion: 0.26
Nodes (3): DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewCylindersItemSeries

### Community 190 - "Community 190"
Cohesion: 0.26
Nodes (3): DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 191 - "Community 191"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 192 - "Community 192"
Cohesion: 0.36
Nodes (11): createEquipmentService(), assemble(), detailOf(), lastCylinderOf(), managedItem(), recordsOf(), schedulesFor(), schedulesOf() (+3 more)

### Community 194 - "Community 194"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 195 - "Community 195"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 196 - "Community 196"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 197 - "Community 197"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 198 - "Community 198"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 200 - "Community 200"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 201 - "Community 201"
Cohesion: 0.22
Nodes (9): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, ADR-0076, MODULE_IMPORTS, moduleRules (+1 more)

### Community 202 - "Community 202"
Cohesion: 0.20
Nodes (10): verify, prerelease, sha, tag, versionCode, versionName, name, outputs (+2 more)

### Community 203 - "Community 203"
Cohesion: 0.20
Nodes (10): with, cache, distribution, fetch-depth, if-no-files-found, java-version, name, path (+2 more)

### Community 204 - "Community 204"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 205 - "Community 205"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 206 - "Community 206"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 207 - "Community 207"
Cohesion: 0.22
Nodes (9): version, on, push, workflow_dispatch, tags, description, required, type (+1 more)

### Community 208 - "Community 208"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 209 - "Community 209"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 212 - "Community 212"
Cohesion: 0.29
Nodes (5): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, EstimateCylinder

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
Cohesion: 0.29
Nodes (6): APP, build, generated, javaVersion, ADR-0073, ROOT

### Community 218 - "Community 218"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 219 - "Community 219"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 221 - "Community 221"
Cohesion: 0.40
Nodes (5): ensureItemForDevice(), scheduleView(), today(), trimName(), resolveDevice()

### Community 222 - "Community 222"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 223 - "Community 223"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 224 - "Community 224"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 225 - "Community 225"
Cohesion: 0.40
Nodes (4): TAGS, ADR-0023, { stdout, stderr }, total

### Community 227 - "Community 227"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 228 - "Community 228"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 229 - "Community 229"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1277 isolated node(s):** `Reserve`, `RockBottom`, `Off`, `SuitThickness`, `ADR-0004` (+1272 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3511 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **126 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 0` to `Community 1`, `Community 132`, `Community 4`, `Community 6`, `Community 9`, `Community 13`, `Community 14`, `Community 16`, `Community 18`, `Community 20`, `Community 21`, `Community 24`, `Community 26`, `Community 158`, `Community 31`, `Community 33`, `Community 162`, `Community 44`, `Community 45`, `Community 50`, `Community 66`, `Community 70`, `Community 71`, `Community 77`, `Community 79`, `Community 90`, `Community 99`, `Community 105`, `Community 108`, `Community 115`, `Community 121`?**
  _High betweenness centrality (0.024) - this node is a cross-community bridge._
- **What connects `Reserve`, `RockBottom`, `Off` to the rest of the system?**
  _1277 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.013683223992502344 - nodes in this community are weakly interconnected._
- **Why does `createPushRunService()` connect `Community 59` to `Community 1`, `Community 4`, `Community 6`, `Community 13`, `Community 50`?**
  _High betweenness centrality (0.015) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.017716333845366104 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 5` to `Community 2`, `Community 3`, `Community 7`, `Community 8`, `Community 106`, `Community 10`, `Community 12`, `Community 17`, `Community 51`, `Community 86`, `Community 23`, `Community 152`, `Community 87`?**
  _High betweenness centrality (0.015) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.049852543126085724 - nodes in this community are weakly interconnected._