# Graph Report - dive_hub  (2026-11-19)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 10040 nodes · 30356 edges · 330 communities (203 shown, 127 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 537 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `48b8ef5b`
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
- Community 215
- Community 216
- Community 217
- Community 218
- Community 219
- Community 220
- Community 221
- Community 225

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
- `CatalogueCylinder` --references--> `CylinderMaterial`  [EXTRACTED]
  apps/server/src/dives/cylinder-catalogue.ts → apps/server/src/db/schema.ts
- `RefreshHarness` --calls--> `SyncRefresher`  [INFERRED]
  apps/android/shared/src/jvmTest/kotlin/dev/divehub/shared/sync/SyncRefreshTest.kt → apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/sync/SyncRefresher.kt

## Import Cycles
- None detected.

## Communities (330 total, 127 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (190): Queryable, continuationAppend, CylinderMaterial, diverManagement, logbookCheckAnswer, logbookCheckDiveAnswer, OVERRIDABLE_FIELDS, OverridableField (+182 more)

### Community 1 - "Community 1"
Cohesion: 0.03
Nodes (129): user, diver, diveSiteExternalId, PROBLEMS, ADR-0013, ADR-0011, dumpToolAvailable(), execFileAsync (+121 more)

### Community 2 - "Community 2"
Cohesion: 0.02
Nodes (152): CentreService, CentreRouteDeps, CYLINDER_MATERIALS, LEAD_PLACEMENTS, SUIT_TYPES, SUIT_UNDERGARMENTS, DiverService, ADR-0025 (+144 more)

### Community 3 - "Community 3"
Cohesion: 0.02
Nodes (137): ConnectionRow, tidyName(), DiverDetails, siteRef(), Caller, foldName(), nameDiffers(), Buddy (+129 more)

### Community 4 - "Community 4"
Cohesion: 0.02
Nodes (151): WEIGHTING_FELT, DUE_STATES, Nullable(), AiAccessScope, seesPositions(), ProfileSummary, round1(), Series (+143 more)

### Community 5 - "Community 5"
Cohesion: 0.05
Nodes (139): DeleteUserDialog(), UserRow(), bodyWeightsQuery(), certificationsQuery(), diverDetailsQuery(), diverSearchQuery(), DiverView, externalDiversQuery() (+131 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (135): label(), Me, AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery() (+127 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (134): cylinderCatalogueQuery(), CylinderFields(), CylinderList(), MATERIALS, ADR-0034, ADR-0045, useCatalogueName(), useCylinderProblem() (+126 more)

### Community 8 - "Community 8"
Cohesion: 0.02
Nodes (117): buddySuggestion, diveAssessment, jointDive, recordingEvent, Assessment, FindingView, Row, ADR-0036 (+109 more)

### Community 9 - "Community 9"
Cohesion: 0.05
Nodes (126): DisplaySettings(), Accesses(), AccessLog(), AiAccess(), CreateAccess(), devicesQuery(), diversQuery(), DiveGearItemView (+118 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (16): AppContainer, KeystoreTokenStore, PreferencesInstanceUrlStore, UserCacheStore, MainActivity, DiveDetailUiState, DiveDetailViewModel, MainViewModel (+8 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (115): replyCertificationError, STATUS, CertificationService, DIVE_IMPORT_MODES, PARTICIPANT_ROLES, UTC_OFFSET_SOURCES, AssessmentRefresh, ADR-0036 (+107 more)

### Community 12 - "Community 12"
Cohesion: 0.04
Nodes (119): WaitingPushOptions, waitingOf(), audienceOfConnection(), audienceOfDive(), audienceOfDiver(), close(), stillWaits(), currentRemote() (+111 more)

### Community 13 - "Community 13"
Cohesion: 0.02
Nodes (110): createAuth(), actorType, aiAccessOutcome, backupRunKind, backupRunStatus, candidateResolution, centreSource, CERTIFICATION_IMAGE_STATES (+102 more)

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (99): adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, Auth, AuthOptions, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, ADR-0011 (+91 more)

### Community 15 - "Community 15"
Cohesion: 0.04
Nodes (116): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, AssessmentService (+108 more)

### Community 16 - "Community 16"
Cohesion: 0.03
Nodes (112): ComputerChoice, DiveImportMode, DiveKind, ImportErrorCode, ImportFound, importOriginal, ImportOutcome, UtcOffsetSource (+104 more)

### Community 17 - "Community 17"
Cohesion: 0.04
Nodes (109): EMPTY_SITE_IMPORT_COUNTS, ImportArea, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053 (+101 more)

### Community 18 - "Community 18"
Cohesion: 0.04
Nodes (30): ApiDivesGet200Response, ApiDivesGet200ResponseCounts, ApiDivesGet200ResponseDivesItem, ApiDivesGet200ResponseDivesItemGasesItem, ApiDivesGet200ResponseDivesItemSite, ApiDivesGet200ResponseMonthsItem, ApiDivesGet200ResponseTotals, DiveView (+22 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (106): diverBodyWeight, diverExternalId, diveSite, lead, LeadPlacement, SuitType, SuitUndergarment, WeightingFelt (+98 more)

### Community 20 - "Community 20"
Cohesion: 0.03
Nodes (82): createServer(), loadAuthSecret(), acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock (+74 more)

### Community 21 - "Community 21"
Cohesion: 0.03
Nodes (93): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+85 more)

### Community 22 - "Community 22"
Cohesion: 0.03
Nodes (84): connection, ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS, eachFile(), readEntry(), ADR-0044 (+76 more)

### Community 23 - "Community 23"
Cohesion: 0.03
Nodes (89): equipmentDueQuery(), isUnauthorized(), SiteImportArea, SiteImportView, App(), BackupsPage, CentrePage, CentresPage (+81 more)

### Community 24 - "Community 24"
Cohesion: 0.04
Nodes (91): DiveValues, OverridableField, revisionsQuery(), RevisionView, UtcOffsetSource, DiveHistory(), Entry(), isValue() (+83 more)

### Community 25 - "Community 25"
Cohesion: 0.04
Nodes (95): bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas, toolsGasQuery() (+87 more)

### Community 26 - "Community 26"
Cohesion: 0.03
Nodes (85): ExternalIdView, Position, running(), siteImportsQuery(), siteRevisionsQuery(), SiteRevisionView, ADR-0020, ADR-0021 (+77 more)

### Community 27 - "Community 27"
Cohesion: 0.04
Nodes (48): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+40 more)

### Community 28 - "Community 28"
Cohesion: 0.05
Nodes (88): allButDive(), continuationCandidateQuery(), ContinuationCandidateView, deletedDivesOn(), deletedDivesQuery(), diveQuery(), mergeCandidatesQuery(), nearbyDivesQuery() (+80 more)

### Community 29 - "Community 29"
Cohesion: 0.05
Nodes (8): AndroidDiveStore, DiveStore, toBoolean(), toLong(), toModel(), DiveSite, SyncState, DiveStoreTest

### Community 30 - "Community 30"
Cohesion: 0.04
Nodes (73): AscentBand, ascentBandsOf(), ascentRate(), ceiling(), descentRate(), judgedParts(), lastMetres(), ndl() (+65 more)

### Community 31 - "Community 31"
Cohesion: 0.03
Nodes (68): BackupRunView, backupSettingsQuery(), BackupSettingsView, SiteDataView, ADR-0030, CertificationView, DeviceView, DiverDetailsView (+60 more)

### Community 32 - "Community 32"
Cohesion: 0.05
Nodes (60): auth, BackupServiceDeps, createServices(), ServiceParts, ADR-0027, ADR-0030, ADR-0061, ADR-0063 (+52 more)

### Community 33 - "Community 33"
Cohesion: 0.06
Nodes (73): ChangePassword(), SecuritySettings(), Sessions(), useDescribeAgent(), AdminSettings(), Invitations(), Invite(), LinkToPassOn() (+65 more)

### Community 34 - "Community 34"
Cohesion: 0.04
Nodes (25): LogbookViewModel, ApiClient, ApiErrorBody, DiveRepository, neighboursPath(), countOf(), FilterChip, filterChips() (+17 more)

### Community 35 - "Community 35"
Cohesion: 0.06
Nodes (8): LogbookPaging, json(), LogbookPagingTest, LogbookRepositoryTest, RepositoryHarness, FakeInstances, FakeTokens, SyncAuthTest

### Community 36 - "Community 36"
Cohesion: 0.04
Nodes (70): CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, errors, Fields, ADR-0050 (+62 more)

### Community 37 - "Community 37"
Cohesion: 0.04
Nodes (70): downsampleMinMax(), BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071 (+62 more)

### Community 38 - "Community 38"
Cohesion: 0.04
Nodes (71): alignedForMatching(), CandidateDive, DAY_MS, decideMatch(), depthsDisagree(), DuplicateReason, MatchDecision, MAX_OFFSET_MS (+63 more)

### Community 39 - "Community 39"
Cohesion: 0.05
Nodes (61): DIVE_FIELDS, createSsiAdapter(), SSI_CAPABILITIES, createSsiClient(), ssiSiteRow(), Fetch, Card, DEEP (+53 more)

### Community 40 - "Community 40"
Cohesion: 0.08
Nodes (66): GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult(), PlanInputs(), PlanRuleExplanation() (+58 more)

### Community 41 - "Community 41"
Cohesion: 0.07
Nodes (63): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+55 more)

### Community 42 - "Community 42"
Cohesion: 0.04
Nodes (66): equipmentUse, pushRunItem, PushRunLeftOut, Participant, DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly (+58 more)

### Community 43 - "Community 43"
Cohesion: 0.05
Nodes (63): divesQuery(), LogbookPage, PAGE_SIZE, siteQuery(), Buddies(), COUNT_OF, DiveList(), DiveRow() (+55 more)

### Community 44 - "Community 44"
Cohesion: 0.05
Nodes (64): buddiesQuery(), BuddyView, DiveImportMode, DiveImportPreview, DiveKind, fetchBuddies(), GoneDivesOfConnection, GoneDiveView (+56 more)

### Community 45 - "Community 45"
Cohesion: 0.05
Nodes (20): ApiDiverBodyWeightsGet200ResponseItem, ApiDiveSitesGet200Response, ApiMeGet200Response, ApiMeGet200ResponseOwnDiver, ApiMeGet200ResponsePreferences, SignInEmailRequest, SiteView, SiteViewPosition (+12 more)

### Community 46 - "Community 46"
Cohesion: 0.06
Nodes (56): NoFly, getDiverLoadings, BottomTimeQuery, DEFAULT_GF_HIGH, diverLogbook(), planBottomTime(), residualSeedOf(), Source (+48 more)

### Community 47 - "Community 47"
Cohesion: 0.06
Nodes (27): recordingName(), DiveViewRecordingsItem, DiveViewRecordingsItemDevice, DiveViewRecordingsItemSummarySurfaceBreaksItem, RecordingSamplesView, RecordingSamplesViewSeriesItem, diveLocalTime(), offsetId() (+19 more)

### Community 48 - "Community 48"
Cohesion: 0.06
Nodes (60): AREAS, baseCandidates(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads(), exists() (+52 more)

### Community 49 - "Community 49"
Cohesion: 0.04
Nodes (49): BACKUP_RUN_TASK, backupTask(), ADR-0069, CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053 (+41 more)

### Community 50 - "Community 50"
Cohesion: 0.06
Nodes (56): Leave, asBool(), DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+48 more)

### Community 51 - "Community 51"
Cohesion: 0.04
Nodes (44): ParticipantRole, ActionContext, Direction, DiveExport, DiveListing, FIND_BY, FindBy, Operation (+36 more)

### Community 53 - "Community 53"
Cohesion: 0.08
Nodes (8): DiveDetailScreenshotTest, DiveViewFromRecording, DiveViewFromRecordingStartsAt, DiveViewParticipantsItem, DiveViewParticipantsItemCertificationHint, DiveViewWaterMismatch, NeighboursView, NeighboursViewNext

### Community 54 - "Community 54"
Cohesion: 0.07
Nodes (46): looksLikeZip(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy(), ADR-0024 (+38 more)

### Community 55 - "Community 55"
Cohesion: 0.05
Nodes (51): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, cancelWaitingImports() (+43 more)

### Community 56 - "Community 56"
Cohesion: 0.09
Nodes (30): BottomTime, bottomTime(), BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen (+22 more)

### Community 57 - "Community 57"
Cohesion: 0.08
Nodes (56): Answer, answer(), baseFrom(), BodyWeight, Confidence, high, low, medium (+48 more)

### Community 58 - "Community 58"
Cohesion: 0.10
Nodes (41): ambientPressureBar(), bestMixPercent(), Conditions, depthOfPressure(), eadMetres(), endMetres(), GasMix, isHypoxic() (+33 more)

### Community 59 - "Community 59"
Cohesion: 0.12
Nodes (27): Body, Hold, Offline, RefreshData, refreshDeletedJson(), refreshDetailJson(), refreshDetailsPage(), refreshDiveId() (+19 more)

### Community 60 - "Community 60"
Cohesion: 0.08
Nodes (11): Missing(), LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseLeftOut, ApiDiversIdLeadEstimateGet200ResponseMissingItem (+3 more)

### Community 61 - "Community 61"
Cohesion: 0.05
Nodes (44): dive, diveFinding, providerConflict, push, recording, revision, waitingPush, kept() (+36 more)

### Community 62 - "Community 62"
Cohesion: 0.11
Nodes (48): withBuddies(), APP_KEYS, appRow(), BuddySaved, byId(), changedKeys(), deleteTestDive(), deleteTestEntries() (+40 more)

### Community 63 - "Community 63"
Cohesion: 0.10
Nodes (27): DiveBody(), DiveDetailRoute(), DiveDetailScreen(), errorText(), FactRow(), Facts(), Loading(), NeighbourButton() (+19 more)

### Community 64 - "Community 64"
Cohesion: 0.07
Nodes (46): surfacePressureBar(), AIR, cnsDecayed(), DEFAULT_CONDITIONS, N2_REPLAY_WINDOW_HOURS, N2_STALE_AFTER_HOURS, N2Loadings, N2LoadingsBase (+38 more)

### Community 65 - "Community 65"
Cohesion: 0.06
Nodes (34): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+26 more)

### Community 66 - "Community 66"
Cohesion: 0.07
Nodes (46): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+38 more)

### Community 67 - "Community 67"
Cohesion: 0.08
Nodes (47): BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32Encode(), bech32HrpExpand(), bech32Polymod() (+39 more)

### Community 68 - "Community 68"
Cohesion: 0.06
Nodes (38): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+30 more)

### Community 69 - "Community 69"
Cohesion: 0.07
Nodes (13): ApiToolsBottomTimeGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseLimits, ApiToolsBottomTimeGet200ResponseResidualN2, ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue (+5 more)

### Community 70 - "Community 70"
Cohesion: 0.14
Nodes (23): Body, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage(), diverJson(), diveStartsAtOf() (+15 more)

### Community 71 - "Community 71"
Cohesion: 0.06
Nodes (32): diveLease, providerLease, pushRun, LEASE_MS, MAX_WAIT_MS, POLL_MS, ExportingProvider, createFakeHandover() (+24 more)

### Community 72 - "Community 72"
Cohesion: 0.06
Nodes (41): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+33 more)

### Community 73 - "Community 73"
Cohesion: 0.06
Nodes (35): Delivered, DiveExportAction, buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open() (+27 more)

### Community 74 - "Community 74"
Cohesion: 0.08
Nodes (34): SsiClient, SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), emptyRecord(), writableRecord(), FakeSsi (+26 more)

### Community 75 - "Community 75"
Cohesion: 0.05
Nodes (42): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+34 more)

### Community 76 - "Community 76"
Cohesion: 0.11
Nodes (16): DiveViewRecordingsItemSummary, DiveViewRecordingsItemSummaryGasesItem, BodyWeight, Cylinder, Dive, Diver, Recording, DiveDetailsPage (+8 more)

### Community 77 - "Community 77"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 78 - "Community 78"
Cohesion: 0.07
Nodes (41): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+33 more)

### Community 79 - "Community 79"
Cohesion: 0.09
Nodes (3): DiveHubTheme(), ToolsScreenshotTest, UpdateRequiredScreenTest

### Community 80 - "Community 80"
Cohesion: 0.08
Nodes (35): EstimateDive, EstimateDiveCylinder, WeightingFeedback, WeightingFelt, right, too_heavy, too_light, dayNumber() (+27 more)

### Community 81 - "Community 81"
Cohesion: 0.08
Nodes (13): Answers, number(), ToolsUiState, ToolsViewModel, ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas (+5 more)

### Community 83 - "Community 83"
Cohesion: 0.13
Nodes (6): SyncCheckpoint, Complete, SessionEnded, Stopped, SyncEngine, SyncResult

### Community 84 - "Community 84"
Cohesion: 0.13
Nodes (8): FakeCache, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore, UserScopedStore

### Community 85 - "Community 85"
Cohesion: 0.07
Nodes (32): SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject(), MAX_CARD_IMAGE_BYTES (+24 more)

### Community 86 - "Community 86"
Cohesion: 0.10
Nodes (32): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0051 (+24 more)

### Community 87 - "Community 87"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 88 - "Community 88"
Cohesion: 0.11
Nodes (33): BACKUP_SKIPPED_DIRS, BackupDecryptionKey, encryptionKeyFingerprint(), openArchiveStreamingWith(), assertPgTool(), execFileAsync, pgCommand(), pgErrorDetail() (+25 more)

### Community 89 - "Community 89"
Cohesion: 0.12
Nodes (9): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), GasPlanInputTest (+1 more)

### Community 90 - "Community 90"
Cohesion: 0.07
Nodes (34): AI_ACCESS_NAME_MAX, aiAccessLog, aiAccessSetting, Access, AccessView, AiAccessRouteDeps, LogEntryView, Scope (+26 more)

### Community 91 - "Community 91"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 92 - "Community 92"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 93 - "Community 93"
Cohesion: 0.12
Nodes (35): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+27 more)

### Community 94 - "Community 94"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 95 - "Community 95"
Cohesion: 0.10
Nodes (30): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+22 more)

### Community 96 - "Community 96"
Cohesion: 0.09
Nodes (15): DiveHubApp(), ErrorText(), InstanceForm(), Loading(), PasswordField(), PrimaryButton(), SignInForm(), Spinner() (+7 more)

### Community 97 - "Community 97"
Cohesion: 0.13
Nodes (3): LogbookScreenshotTest, ApiDivesGet200ResponseDivesItemParticipantsItem, DiveProfileView

### Community 98 - "Community 98"
Cohesion: 0.11
Nodes (12): ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseCns, ApiDiversIdLoadingsGet200ResponseN2, ApiDiversIdLoadingsGet200ResponseNoFly, ApiDiversIdLoadingsGet200ResponseNoFlySource, ApiDiversIdLoadingsGet200ResponseOtu, cnsDecayed() (+4 more)

### Community 99 - "Community 99"
Cohesion: 0.08
Nodes (17): HINT_CHANNEL, parseHint(), createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener() (+9 more)

### Community 100 - "Community 100"
Cohesion: 0.10
Nodes (12): drawBand(), ProfileSketch(), AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint, SketchProfile (+4 more)

### Community 101 - "Community 101"
Cohesion: 0.07
Nodes (27): CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053, replyDiverError, STATUS, CertificationSummary (+19 more)

### Community 102 - "Community 102"
Cohesion: 0.13
Nodes (31): importsQuery(), Logbook(), ImportContext, ImportFilesButton(), ImportHistory(), ImportList(), ImportNotices(), ImportPanel() (+23 more)

### Community 103 - "Community 103"
Cohesion: 0.08
Nodes (21): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, killTree() (+13 more)

### Community 104 - "Community 104"
Cohesion: 0.14
Nodes (17): DiveRow(), Error(), Fact(), Filters(), Loading(), LogbookList(), LogbookRoute(), LogbookScreen() (+9 more)

### Community 105 - "Community 105"
Cohesion: 0.09
Nodes (26): AL80, asEstimateInput(), EstimateDiveCylinder, evaluateLeadVectorCase(), LEAD_CASES, LEAD_VECTOR_KERNEL, leadInput(), leadVectorFile() (+18 more)

### Community 106 - "Community 106"
Cohesion: 0.08
Nodes (24): DELETE_QUIET_MS, Cylinder, DiveView, RevisionView, ADR-0045, upload(), Candidate, Deleted (+16 more)

### Community 107 - "Community 107"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 108 - "Community 108"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 109 - "Community 109"
Cohesion: 0.14
Nodes (26): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+18 more)

### Community 110 - "Community 110"
Cohesion: 0.18
Nodes (4): GasPlanScreenshotTest, GasPlanToolDiver, GasPlanToolInput, GasPlanToolLevel

### Community 111 - "Community 111"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 112 - "Community 112"
Cohesion: 0.10
Nodes (22): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearQueue(), conflictAtSsi() (+14 more)

### Community 113 - "Community 113"
Cohesion: 0.08
Nodes (25): account, accountRelations, apikey, rateLimit, session, sessionRelations, ADR-0011, ADR-0012 (+17 more)

### Community 114 - "Community 114"
Cohesion: 0.15
Nodes (25): DiveOrigin, REFERENCE_PREFIX, Series, SsiLogbook, aboveZero(), cylinderOf(), deviceOf(), deviceOfRef() (+17 more)

### Community 115 - "Community 115"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 116 - "Community 116"
Cohesion: 0.27
Nodes (5): EstimateCylinder, PlannedConditions, Suit, leadEstimate(), PersonalizedToolsTest

### Community 117 - "Community 117"
Cohesion: 0.11
Nodes (23): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), position(), positive() (+15 more)

### Community 118 - "Community 118"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 119 - "Community 119"
Cohesion: 0.10
Nodes (20): accountListEntry, diveFingerprint, forget(), forgetEntry(), remember(), ADR-0040, ADR-0057, changeListenerOrNull() (+12 more)

### Community 120 - "Community 120"
Cohesion: 0.13
Nodes (22): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DueInput, DueSchedule (+14 more)

### Community 121 - "Community 121"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 122 - "Community 122"
Cohesion: 0.09
Nodes (4): DiveHubApplication, SyncWorker, WorkManagerPeriodicSync, PeriodicSyncScheduler

### Community 123 - "Community 123"
Cohesion: 0.08
Nodes (25): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+17 more)

### Community 124 - "Community 124"
Cohesion: 0.12
Nodes (22): ScheduleDue, DiveGear, DueEntry, GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow (+14 more)

### Community 125 - "Community 125"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 126 - "Community 126"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 127 - "Community 127"
Cohesion: 0.13
Nodes (19): Deep, DepthReading, depthReading(), deviceName(), DiveValue, AVG_DEPTH_M, DURATION_SECONDS, MAX_DEPTH_M (+11 more)

### Community 128 - "Community 128"
Cohesion: 0.13
Nodes (6): ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting

### Community 129 - "Community 129"
Cohesion: 0.13
Nodes (6): DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, WaitingPushView

### Community 130 - "Community 130"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 131 - "Community 131"
Cohesion: 0.14
Nodes (18): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+10 more)

### Community 132 - "Community 132"
Cohesion: 0.09
Nodes (18): COMPUTER_EVENT, DECO_MODEL, decoModelFromSuunto, DIVE_MODE, diveModeFromSuunto, FIT_DIVE_MODE, SUUNTO_EVENT_TYPES, ADR-0015 (+10 more)

### Community 133 - "Community 133"
Cohesion: 0.15
Nodes (17): clientVersionGate(), ADR-0076, ClientVersionRegistry, MIN_CLIENT_VERSIONS, MinClientVersion, ADR-0076, clientRequestSerializer(), ADR-0076 (+9 more)

### Community 134 - "Community 134"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 135 - "Community 135"
Cohesion: 0.18
Nodes (5): LeadAnswer, leadAnswer(), LeadEstimateUiState, LeadEstimateViewModel, PersonalizedSources

### Community 136 - "Community 136"
Cohesion: 0.16
Nodes (8): Connected, Hint, HintFrame, LiveEvent, LiveHint, LiveHints, SseLiveHints, LiveHintsTest

### Community 137 - "Community 137"
Cohesion: 0.16
Nodes (21): managementOf(), EquipmentError, checkCost(), checkDates(), checkWindow(), createEquipmentService(), assemble(), checkResets() (+13 more)

### Community 138 - "Community 138"
Cohesion: 0.12
Nodes (18): CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043, CreateBody (+10 more)

### Community 139 - "Community 139"
Cohesion: 0.13
Nodes (19): equipmentItem, serviceRecord, serviceRecordSchedule, serviceSchedule, equipmentList(), EquipmentListFilter, EquipmentRecordRead, today() (+11 more)

### Community 140 - "Community 140"
Cohesion: 0.12
Nodes (18): siteImportStatus, PROBLEM_CODES, SiteImportRouteDeps, StartBody, STATUS, ADR-0021, SiteImportError, SiteImportService (+10 more)

### Community 141 - "Community 141"
Cohesion: 0.26
Nodes (17): ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres(), isHypoxic(), metresPerBar() (+9 more)

### Community 142 - "Community 142"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 143 - "Community 143"
Cohesion: 0.14
Nodes (3): SyncLine(), formatSyncTime(), SyncTimeFormatTest

### Community 144 - "Community 144"
Cohesion: 0.15
Nodes (5): SiteViewExternalIdsItem, SiteViewExternalIdsItemAttribution, SiteViewExternalIdsItemOffered, SiteViewExternalIdsItemOfferedPosition, SiteViewExternalIdsItemPrivateTo

### Community 145 - "Community 145"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 146 - "Community 146"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 147 - "Community 147"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 148 - "Community 148"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 149 - "Community 149"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 150 - "Community 150"
Cohesion: 0.19
Nodes (11): LeadInputTest, SuitType, drysuit, none, semidry, skin, wetsuit, SuitUndergarment (+3 more)

### Community 151 - "Community 151"
Cohesion: 0.11
Nodes (13): BACKUP_EXTENSION, BackupEncryption, BackupPart, BackupLog, BackupRunRow, BackupSettingsInput, BackupSettingsValues, DEFAULT_BACKUP_SETTINGS (+5 more)

### Community 152 - "Community 152"
Cohesion: 0.15
Nodes (17): BACKUP_FORMAT_VERSION, buildManifest(), decryptFile(), decryptFileWith(), decryptStreamWith(), encryptFile(), encryptStream(), fileTreeChecksum() (+9 more)

### Community 153 - "Community 153"
Cohesion: 0.11
Nodes (17): BOTTOM_TIME_RULES, PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView (+9 more)

### Community 154 - "Community 154"
Cohesion: 0.15
Nodes (15): qr@0.7.2, cameraAtStart(), cameraFailure(), cameras(), CameraScan(), browserDetector(), Detector, DetectorClass (+7 more)

### Community 155 - "Community 155"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 157 - "Community 157"
Cohesion: 0.20
Nodes (14): AppLockProbe, BackupManifest, Arguments, credentialOf(), FILE_OPTIONS, fileText(), finishedArguments(), parseArguments() (+6 more)

### Community 158 - "Community 158"
Cohesion: 0.16
Nodes (12): BOM, Json, num(), obj(), positive(), scaled(), SuuntoJsonAdapter, time() (+4 more)

### Community 159 - "Community 159"
Cohesion: 0.23
Nodes (15): jsRound(), powInt(), roundTo(), Sac, SacCylinder, SacDive, SacMissing, cylinder_incomplete (+7 more)

### Community 160 - "Community 160"
Cohesion: 0.16
Nodes (11): execFileAsync, tarDirectory(), BackupError, createBackupService(), checkSettings(), due(), enqueueScheduled(), readSettings() (+3 more)

### Community 161 - "Community 161"
Cohesion: 0.15
Nodes (13): CIRCUIT, circuitFromFit, DECO_MODEL, decoModelFromFit, DIVE_ALERT, DIVE_MODE, ADR-0015, WATER_TYPE (+5 more)

### Community 162 - "Community 162"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 163 - "Community 163"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 164 - "Community 164"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 165 - "Community 165"
Cohesion: 0.18
Nodes (6): Formats, AvatarCircle(), durationMinutes(), initials(), mixName(), LogbookFormatTest

### Community 167 - "Community 167"
Cohesion: 0.13
Nodes (10): EGYPT, ExternalId, HOUSE_REEF, Method, Position, Revision, Site, SiteImport (+2 more)

### Community 168 - "Community 168"
Cohesion: 0.14
Nodes (7): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, headers, uniqueWord()

### Community 169 - "Community 169"
Cohesion: 0.18
Nodes (11): addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, relations(), TimelineBar, TimelineBarGeometry, TimelineGapGeometry, TimelineGeometry (+3 more)

### Community 170 - "Community 170"
Cohesion: 0.13
Nodes (14): name, needs, runs-on, timeout-minutes, concurrency, cancel-in-progress, group, env (+6 more)

### Community 171 - "Community 171"
Cohesion: 0.20
Nodes (10): DIVE_KINDS, ImportView, ImportViewSchema, toImportView(), ADR-0030, ImportRouteDeps, ADR-0044, ADR-0056 (+2 more)

### Community 172 - "Community 172"
Cohesion: 0.20
Nodes (12): flag(), idText(), isObject(), numberOrNull(), readSsiSite(), SsiSiteValues, visibilityFor(), WATER_OF_BOW (+4 more)

### Community 173 - "Community 173"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 174 - "Community 174"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 175 - "Community 175"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 176 - "Community 176"
Cohesion: 0.26
Nodes (3): DiverView, DiverViewCertifications, DiverViewCertificationsNewest

### Community 177 - "Community 177"
Cohesion: 0.26
Nodes (3): DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 178 - "Community 178"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 179 - "Community 179"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 180 - "Community 180"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 181 - "Community 181"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 182 - "Community 182"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 183 - "Community 183"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 184 - "Community 184"
Cohesion: 0.22
Nodes (8): wallClockMs(), AQABA, ATTERSEE, EILAT, HAUSREEF, local(), MID_ATLANTIC, ADR-0030

### Community 185 - "Community 185"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 186 - "Community 186"
Cohesion: 0.22
Nodes (9): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, ADR-0076, MODULE_IMPORTS, moduleRules (+1 more)

### Community 187 - "Community 187"
Cohesion: 0.20
Nodes (10): verify, prerelease, sha, tag, versionCode, versionName, name, outputs (+2 more)

### Community 188 - "Community 188"
Cohesion: 0.20
Nodes (10): with, cache, distribution, fetch-depth, if-no-files-found, java-version, name, path (+2 more)

### Community 189 - "Community 189"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 190 - "Community 190"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 191 - "Community 191"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 192 - "Community 192"
Cohesion: 0.22
Nodes (9): version, on, push, workflow_dispatch, tags, description, required, type (+1 more)

### Community 193 - "Community 193"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 194 - "Community 194"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 203 - "Community 203"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 205 - "Community 205"
Cohesion: 0.43
Nodes (5): createFitAdapter(), ownersParts(), parse(), toPart(), ADR-0063

### Community 206 - "Community 206"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 207 - "Community 207"
Cohesion: 0.33
Nodes (3): CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031

### Community 208 - "Community 208"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 209 - "Community 209"
Cohesion: 0.40
Nodes (5): ensureItemForDevice(), scheduleView(), today(), trimName(), resolveDevice()

### Community 210 - "Community 210"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 211 - "Community 211"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 212 - "Community 212"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 217 - "Community 217"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 218 - "Community 218"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 219 - "Community 219"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1277 isolated node(s):** `Off`, `Reserve`, `RockBottom`, `SuitThickness`, `ADR-0048` (+1272 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3497 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **127 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 1` to `Community 0`, `Community 2`, `Community 3`, `Community 4`, `Community 8`, `Community 139`, `Community 12`, `Community 13`, `Community 14`, `Community 15`, `Community 16`, `Community 17`, `Community 19`, `Community 20`, `Community 21`, `Community 22`, `Community 151`, `Community 32`, `Community 36`, `Community 38`, `Community 39`, `Community 42`, `Community 46`, `Community 49`, `Community 61`, `Community 71`, `Community 90`, `Community 95`, `Community 106`, `Community 111`, `Community 113`, `Community 119`, `Community 123`, `Community 124`?**
  _High betweenness centrality (0.024) - this node is a cross-community bridge._
- **What connects `Off`, `Reserve`, `RockBottom` to the rest of the system?**
  _1277 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.022057932371833715 - nodes in this community are weakly interconnected._
- **Why does `typescript` connect `Community 155` to `Community 108`, `Community 142`, `Community 111`?**
  _High betweenness centrality (0.019) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.03346550356859635 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 33` to `Community 5`, `Community 6`, `Community 7`, `Community 102`, `Community 9`, `Community 41`, `Community 43`, `Community 108`, `Community 44`, `Community 145`, `Community 87`, `Community 23`, `Community 24`, `Community 25`, `Community 26`, `Community 28`, `Community 31`?**
  _High betweenness centrality (0.016) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.020123151145813496 - nodes in this community are weakly interconnected._