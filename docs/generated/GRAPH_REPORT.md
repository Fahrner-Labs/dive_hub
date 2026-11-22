# Graph Report - dive_hub  (2026-11-22)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 11398 nodes · 34772 edges · 372 communities (236 shown, 136 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 668 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `b4a775e6`
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
- Community 247
- Community 248
- Community 249
- Community 254

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 211 edges
2. `Button()` - 204 edges
3. `useErrorText()` - 176 edges
4. `useDisplay()` - 165 edges
5. `drizzle-orm` - 147 edges
6. `unwrap()` - 143 edges
7. `Muted()` - 139 edges
8. `createTestDatabase()` - 108 edges
9. `TestDatabase` - 107 edges
10. `announce()` - 107 edges

## Surprising Connections (you probably didn't know these)
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `assembleToolSources()` --calls--> `ToolDiverSource`  [INFERRED]
  apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/tools/ToolSourcesStore.kt → apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/tools/ToolsService.kt
- `Harness` --calls--> `SessionRepository`  [INFERRED]
  apps/android/shared/src/commonTest/kotlin/dev/divehub/shared/SessionRepositoryTest.kt → apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/SessionRepository.kt

## Import Cycles
- None detected.

## Communities (372 total, 136 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (232): buddySuggestion, dive, diveFinding, diveSiteExternalId, jointDiveDismissal, revision, TOKEN_ATTEMPTS, PROBLEMS (+224 more)

### Community 1 - "Community 1"
Cohesion: 0.04
Nodes (226): ChangePassword(), DisplaySettings(), SecuritySettings(), Sessions(), ADR-0080, useDescribeAgent(), AdminSettings(), ClientDiagnosticsSetting() (+218 more)

### Community 2 - "Community 2"
Cohesion: 0.02
Nodes (215): diverSearchQuery(), DiveView, cylinderCatalogueQuery(), ToolsBottomTime, ToolsGas, ToolsPlan, ToolsPlanDiver, CylinderFields() (+207 more)

### Community 3 - "Community 3"
Cohesion: 0.02
Nodes (155): label(), Me, AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery() (+147 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (32): InkButton(), QuietButton(), DiveRow(), Figures(), RowFacts(), EmptyState(), FactRow(), NoteText() (+24 more)

### Community 5 - "Community 5"
Cohesion: 0.02
Nodes (142): CentreService, CentreRouteDeps, LEAD_PLACEMENTS, UTC_OFFSET_SOURCES, WEIGHTING_FELT, DiverService, downsampleMinMax(), CertificationHint (+134 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (139): ConnectionRow, diveFingerprint, WaitingPushOptions, waitingOf(), audienceOfConnection(), open(), close(), createConnectionService() (+131 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (105): AuthOptions, hashPassword(), createServices(), Services, ADR-0027, ADR-0030, ADR-0061, ADR-0063 (+97 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (116): LoadingsView, divesQuery(), importsQuery(), ImportView, isUnauthorized(), App(), BackupsPage, CentrePage (+108 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (125): certificationsQuery(), LogbookPage, PAGE_SIZE, buddiesForQuery(), buddiesQuery(), BuddyView, connectionsQuery(), ConnectionView (+117 more)

### Community 10 - "Community 10"
Cohesion: 0.02
Nodes (118): accountRelations, apikey, rateLimit, sessionRelations, ADR-0011, ADR-0012, ADR-0035, twoFactor (+110 more)

### Community 11 - "Community 11"
Cohesion: 0.04
Nodes (119): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+111 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (105): AssessmentViewSchema, FindingView, Rule, STATUS, toView(), ADR-0036, BackupRunView, BackupSettingsInput (+97 more)

### Community 13 - "Community 13"
Cohesion: 0.05
Nodes (77): Formats, label(), SettingsDebugRoute(), SettingsDebugScreen(), formatSyncTime(), GasPlanRoute(), GasPlanScreen(), PlanAnswer() (+69 more)

### Community 14 - "Community 14"
Cohesion: 0.04
Nodes (104): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, ambientPressureBar() (+96 more)

### Community 15 - "Community 15"
Cohesion: 0.03
Nodes (99): ReportProblemSettings(), ClientDiagnosticsSettingsView, clientDiagnosticsStatusQuery(), ClientDiagnosticsStatusView, ADR-0080, CertificationView, DeviceView, DiverDetailsView (+91 more)

### Community 16 - "Community 16"
Cohesion: 0.04
Nodes (100): OVERRIDABLE_FIELDS, OverridableField, renumber(), renumberStatement(), ADR-0048, accountKey(), applyExternalId(), insertExternal() (+92 more)

### Community 17 - "Community 17"
Cohesion: 0.04
Nodes (97): diveAssessment, jointDive, logbookCheckAnswer, logbookCheckDiveAnswer, keepApart(), keepDive(), keepRestored(), ADR-0038 (+89 more)

### Community 18 - "Community 18"
Cohesion: 0.03
Nodes (82): createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities(), providerError() (+74 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (78): equipmentDueQuery(), CodeFigure(), DiveCodes(), showDiveCodes(), ADR-0043, EquipmentDueNotice(), ADR-0034, avatarTone() (+70 more)

### Community 20 - "Community 20"
Cohesion: 0.03
Nodes (77): createServer(), createAuth(), loadAuthSecret(), acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS (+69 more)

### Community 21 - "Community 21"
Cohesion: 0.03
Nodes (86): continuationAppend, pushRunItem, PushRunLeftOut, managedDiverIds(), managesDiveDiver(), Participant, participantsOfDives(), DIVE_ONLY (+78 more)

### Community 22 - "Community 22"
Cohesion: 0.04
Nodes (86): NoFly, noFlyAfter(), getDiverLoadings, HINT, Input, LoadingsInput, planGasAndBottomTime, ADR-0032 (+78 more)

### Community 23 - "Community 23"
Cohesion: 0.05
Nodes (24): AppNavHost(), AppScreens, clearSavedTabStates(), isShowing(), leaveDiveForLogbook(), MainShell(), navigateToTab(), tabOf() (+16 more)

### Community 24 - "Community 24"
Cohesion: 0.06
Nodes (88): buildApp(), assessmentRoutes(), SessionUser, authHandler(), authHeaders(), fastify, FastifyRequest, forwardCookies() (+80 more)

### Community 25 - "Community 25"
Cohesion: 0.04
Nodes (80): keys, ProviderView, RequirementView, api, centreQuery(), centresQuery(), CentreView, Position (+72 more)

### Community 26 - "Community 26"
Cohesion: 0.04
Nodes (48): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+40 more)

### Community 27 - "Community 27"
Cohesion: 0.04
Nodes (75): DIVE_FIELDS, SSI_CAPABILITIES, SsiCallLog, rowOf(), createSsiClient(), DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText() (+67 more)

### Community 28 - "Community 28"
Cohesion: 0.04
Nodes (76): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+68 more)

### Community 29 - "Community 29"
Cohesion: 0.05
Nodes (81): Queryable, ComputerChoice, DiveImportMode, DiveKind, importOriginal, providerSiteData, RecordingSummary, UtcOffsetSource (+73 more)

### Community 30 - "Community 30"
Cohesion: 0.06
Nodes (77): diveSite, Context, dataIdsOf(), settleSiteVisibility(), outgoingSite(), findOrMake(), SiteForSend, siteForSend() (+69 more)

### Community 31 - "Community 31"
Cohesion: 0.04
Nodes (64): duplicateCandidate, ImportErrorCode, ImportFound, importJob, ImportOutcome, recording, ArchiveEntry, ArchiveLimitError (+56 more)

### Community 32 - "Community 32"
Cohesion: 0.04
Nodes (78): @modelcontextprotocol/server@^2.3.1, AccessRefusal, AiAccessScope, AiAccessService, seesPositions(), VerifiedAccess, mcpEndpoint(), McpEndpointDeps (+70 more)

### Community 33 - "Community 33"
Cohesion: 0.04
Nodes (80): importQuery(), ConflictFieldView, diveImportPreviewQuery(), providerSitesQuery(), providersQuery(), providerStatusQuery(), useCylinderText(), focusFirstIn() (+72 more)

### Community 34 - "Community 34"
Cohesion: 0.04
Nodes (11): AppContainer, KeystoreTokenStore, PreferencesInstanceUrlStore, UserCacheStore, SettingsRepository, InstanceUrlStore, TokenStore, UserScopedStore (+3 more)

### Community 35 - "Community 35"
Cohesion: 0.03
Nodes (55): ParticipantRole, ProviderImportPlan, PushLeftOut, Direction, DiveExport, DiveExportAction, DiveListing, FIND_BY (+47 more)

### Community 36 - "Community 36"
Cohesion: 0.04
Nodes (76): CylinderMaterial, sampleSeries, defined(), Joined, joinedSummary(), joinParts(), median(), round3() (+68 more)

### Community 37 - "Community 37"
Cohesion: 0.08
Nodes (7): FakeCache, RevisionFollowTest, LeadEstimateViewModelTest, Dive, json(), LogbookRepositoryTest, RepositoryHarness

### Community 38 - "Community 38"
Cohesion: 0.05
Nodes (14): AndroidDiveStore, DiveStore, toBoolean(), toLong(), toModel(), BodyWeight, Cylinder, Diver (+6 more)

### Community 39 - "Community 39"
Cohesion: 0.04
Nodes (61): Tx, device, diverBodyWeight, diverManagement, equipmentItem, participant, DiverError, ADR-0016 (+53 more)

### Community 40 - "Community 40"
Cohesion: 0.06
Nodes (73): BACKUP_FORMAT_VERSION, BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32Encode(), bech32HrpExpand() (+65 more)

### Community 41 - "Community 41"
Cohesion: 0.06
Nodes (74): allButDive(), continuationCandidateQuery(), ContinuationCandidateView, deletedDivesOn(), deletedDivesQuery(), diveQuery(), mergeCandidatesQuery(), nearbyDivesQuery() (+66 more)

### Community 42 - "Community 42"
Cohesion: 0.06
Nodes (64): accountListEntry, diver, diverExternalId, tidyName(), DiverDetails, Caller, foldName(), nameDiffers() (+56 more)

### Community 43 - "Community 43"
Cohesion: 0.04
Nodes (74): ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, buddiesOf(), BuddySearch, count() (+66 more)

### Community 44 - "Community 44"
Cohesion: 0.05
Nodes (52): auth, BackupServiceDeps, ServiceParts, siteSource, ProviderAdapter, out, services, createSecretBox() (+44 more)

### Community 45 - "Community 45"
Cohesion: 0.07
Nodes (64): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+56 more)

### Community 46 - "Community 46"
Cohesion: 0.07
Nodes (8): DiveDetailViewModelTest, GatedDispatcher, CountingDispatcher, LogbookViewModelTest, ApiClient, StoreLogbook, StoreLogbookContext, ApiClientTest

### Community 47 - "Community 47"
Cohesion: 0.04
Nodes (54): providerConflict, push, pushRun, waitingPush, kept(), createPurgeService(), PURGE_AFTER_DAYS, ADR-0072 (+46 more)

### Community 48 - "Community 48"
Cohesion: 0.06
Nodes (66): CertificationService, CYLINDER_MATERIALS, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053, ErrorAnswer, providerProblem() (+58 more)

### Community 49 - "Community 49"
Cohesion: 0.06
Nodes (73): cylindersOf(), mergeProviderCylinder(), providerCylinderOf(), participantsOf(), siteRef(), columnsOf(), partsOf(), entryMatches() (+65 more)

### Community 50 - "Community 50"
Cohesion: 0.08
Nodes (5): LogbookScreenshotTest, DiveListView, DiveListViewCounts, DiveListViewTotals, LogbookRead

### Community 51 - "Community 51"
Cohesion: 0.13
Nodes (4): DiveDetailScreenshotTest, NeighboursView, NeighboursViewNext, NeighboursViewPrevious

### Community 52 - "Community 52"
Cohesion: 0.05
Nodes (55): BACKUP_EXTENSION, BACKUP_SKIPPED_DIRS, BackupDecryptionKey, BackupEncryption, BackupPart, fileTreeChecksum(), openArchiveStreamingWith(), BACKUP_RUN_TASK (+47 more)

### Community 53 - "Community 53"
Cohesion: 0.06
Nodes (37): Section(), cylinderLine(), cylinderPressures(), Cylinders(), Direction, Back, Left, Right (+29 more)

### Community 54 - "Community 54"
Cohesion: 0.05
Nodes (13): DiveView, DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewLeadItem, DiveViewPosition, DiveViewSac, DiveViewSite, DiveViewSuit (+5 more)

### Community 55 - "Community 55"
Cohesion: 0.05
Nodes (57): DiveValues, OverridableField, revisionsQuery(), RevisionView, UtcOffsetSource, DiveHistory(), Entry(), isValue() (+49 more)

### Community 56 - "Community 56"
Cohesion: 0.06
Nodes (21): LogbookViewModel, countOf(), FilterChip, filterChips(), LogbookFilter, CHANGED_SINCE_SENT, NO_RECORDING, NO_SITE (+13 more)

### Community 57 - "Community 57"
Cohesion: 0.05
Nodes (15): ApiClientDiagnosticsStatusGet200Response, ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting, DiveDetailsListView (+7 more)

### Community 58 - "Community 58"
Cohesion: 0.07
Nodes (26): SkeletonBar(), AvatarCircle(), Error(), FilterAction(), Filters(), FilterSheet(), Loading(), LogbookDiveRow() (+18 more)

### Community 59 - "Community 59"
Cohesion: 0.06
Nodes (45): SsiClient, SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), emptyRecord(), writableRecord(), FakeSsi (+37 more)

### Community 60 - "Community 60"
Cohesion: 0.06
Nodes (21): timeZoneText(), DiveViewFromRecording, DiveViewFromRecordingStartsAt, DiveViewRecordingsItem, DiveViewRecordingsItemDevice, DiveViewRecordingsItemSummary, DiveViewRecordingsItemSummaryGasesItem, diveLocalTime() (+13 more)

### Community 61 - "Community 61"
Cohesion: 0.07
Nodes (4): LoadingScreenshotTest, OnboardingScreenTest, SettingsDebugScreenTest, ErrorCodes

### Community 62 - "Community 62"
Cohesion: 0.06
Nodes (14): MainActivity, LoadingsUiState, LoadingsViewModel, Answers, number(), ToolsInputs, ToolsUiState, ToolsViewModel (+6 more)

### Community 63 - "Community 63"
Cohesion: 0.08
Nodes (11): ApiDiverBodyWeightsGet200ResponseItem, DiverView, Complete, SessionEnded, Stopped, SyncEngine, SyncPhase, Details (+3 more)

### Community 64 - "Community 64"
Cohesion: 0.12
Nodes (24): Body, BrokenBody, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage(), diverJson() (+16 more)

### Community 65 - "Community 65"
Cohesion: 0.10
Nodes (27): Body, Hold, Offline, RefreshData, refreshDeletedJson(), refreshDetailJson(), refreshDetailsPage(), refreshDiveId() (+19 more)

### Community 66 - "Community 66"
Cohesion: 0.05
Nodes (35): out, ADR-0036, out, out, ADR-0070, { first, second }, ADR-0063, out (+27 more)

### Community 67 - "Community 67"
Cohesion: 0.07
Nodes (13): logbookDiveItemIndex(), LogbookScreenTest, DiveListViewDivesItem, DiveListViewDivesItemGasesItem, DiveListViewDivesItemParticipantsItem, DiveListViewDivesItemSite, DiveListViewMonthsItem, localMonthOf() (+5 more)

### Community 68 - "Community 68"
Cohesion: 0.07
Nodes (41): appliedMigrationHead(), LogBufferStats, LogRingBuffer, DENIED_KEY_PARTS, emptyRedactionCounters(), isDeniedKey(), maskString(), redactInner() (+33 more)

### Community 69 - "Community 69"
Cohesion: 0.07
Nodes (3): SettingsScreenTest, SyncLineTest, UpdateRequiredScreenTest

### Community 70 - "Community 70"
Cohesion: 0.10
Nodes (43): BottomTime, BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen, BottomTimeLimits (+35 more)

### Community 71 - "Community 71"
Cohesion: 0.06
Nodes (36): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+28 more)

### Community 72 - "Community 72"
Cohesion: 0.08
Nodes (56): Answer, answer(), baseFrom(), Confidence, high, low, medium, Cylinder (+48 more)

### Community 73 - "Community 73"
Cohesion: 0.07
Nodes (49): Delivered, ProviderError, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf() (+41 more)

### Community 74 - "Community 74"
Cohesion: 0.07
Nodes (9): SettingsDiagnosticsState, SettingsUiState, SettingsViewModel, FakeDiagnostics, FakeInstances, SettingsViewModelTest, ReportProblemResult, Sent (+1 more)

### Community 75 - "Community 75"
Cohesion: 0.07
Nodes (48): Leave, diveModeOfSsi(), keepTraining(), ssiDiveType(), ssiInteger(), ADR-0057, asNumber(), asText() (+40 more)

### Community 76 - "Community 76"
Cohesion: 0.05
Nodes (45): AssessmentService, AssessmentRouteDeps, Auth, BackupRouteDeps, BackupService, backupTask(), ADR-0069, siteImport (+37 more)

### Community 77 - "Community 77"
Cohesion: 0.07
Nodes (6): logShareChooser(), DiveHubApplicationTest, LauncherIconTest, SplashTheme, SplashThemeTest, LogShareTest

### Community 78 - "Community 78"
Cohesion: 0.05
Nodes (29): Hint, HINT_CHANNEL, HINT_KINDS, HintAudience, HintKind, kinds, parseHint(), ADR-0020 (+21 more)

### Community 79 - "Community 79"
Cohesion: 0.05
Nodes (42): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+34 more)

### Community 81 - "Community 81"
Cohesion: 0.06
Nodes (44): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, EquipmentService, DiveGearItemViewSchema (+36 more)

### Community 82 - "Community 82"
Cohesion: 0.08
Nodes (7): SettingsRouteTest, DiagnosticEntry, DiagnosticLevel, ERROR, WARN, DiagnosticsBuffer, DiagnosticsBufferTest

### Community 83 - "Community 83"
Cohesion: 0.06
Nodes (42): ADR-0036, ADR-0063, ADR-0038, ADR-0048, ADR-0062, AiAccess, assessedDive(), AssessedFinding (+34 more)

### Community 84 - "Community 84"
Cohesion: 0.09
Nodes (16): DiveBody(), DivePager(), DragPages, NeighbourPage(), settle(), DiveDetailUiState, DiveDetailViewModel, NeighbourDive (+8 more)

### Community 85 - "Community 85"
Cohesion: 0.08
Nodes (17): DiveHubApp(), DiveHubScreens, ErrorText(), Gate(), InstanceForm(), InstanceStatus(), LoadingScreen(), PasswordField() (+9 more)

### Community 86 - "Community 86"
Cohesion: 0.05
Nodes (39): SUIT_TYPES, SUIT_UNDERGARMENTS, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053, replyDiverError (+31 more)

### Community 87 - "Community 87"
Cohesion: 0.05
Nodes (42): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+34 more)

### Community 88 - "Community 88"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 89 - "Community 89"
Cohesion: 0.09
Nodes (23): ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseCns, ApiDiversIdLoadingsGet200ResponseN2, ApiDiversIdLoadingsGet200ResponseNoFly, ApiDiversIdLoadingsGet200ResponseNoFlySource, cnsDecayed(), Estimate (+15 more)

### Community 90 - "Community 90"
Cohesion: 0.11
Nodes (21): Profile(), ProfileChart(), DiveViewRecordingsItemSummarySurfaceBreaksItem, RecordingSamplesView, RecordingSamplesViewSeriesItem, depthSeries(), nearest(), nearestIndex() (+13 more)

### Community 91 - "Community 91"
Cohesion: 0.06
Nodes (40): Change(), Changes(), historyRoute, Made(), REVISION_CAUSES, PositionSchema, AdoptBody, Country (+32 more)

### Community 92 - "Community 92"
Cohesion: 0.09
Nodes (36): lead, LeadPlacement, SuitType, SuitUndergarment, WeightingFelt, ADR-0025, WaterMismatch, waterMismatch() (+28 more)

### Community 93 - "Community 93"
Cohesion: 0.08
Nodes (25): captureDiagnostic(), captureError(), cleanContext(), CLIENT_NAME, CLIENT_VERSION, DiagnosticEntry, diagnosticFromError(), DIAGNOSTICS_KEY (+17 more)

### Community 94 - "Community 94"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 95 - "Community 95"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 96 - "Community 96"
Cohesion: 0.12
Nodes (9): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), GasPlanInputTest (+1 more)

### Community 97 - "Community 97"
Cohesion: 0.06
Nodes (33): adminRole, AI_ACCESS_NAME_MAX, AI_ACCESS_PREFIX, AI_ACCESS_RATE, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, ADR-0011, ADR-0012 (+25 more)

### Community 98 - "Community 98"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 99 - "Community 99"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 100 - "Community 100"
Cohesion: 0.13
Nodes (6): LogbookPhase, Error, List, Loading, Offline, SettingsScreenshotTest

### Community 101 - "Community 101"
Cohesion: 0.07
Nodes (28): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError (+20 more)

### Community 102 - "Community 102"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 103 - "Community 103"
Cohesion: 0.13
Nodes (6): DiveHubTopBar(), titleLineHeight(), TitleSkeleton(), DiveHubTheme(), DiveHubTopBarTest, BottomBarScreenshotTest

### Community 104 - "Community 104"
Cohesion: 0.13
Nodes (34): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+26 more)

### Community 105 - "Community 105"
Cohesion: 0.07
Nodes (28): headers, ADR-0030, ADR-0040, conflictForLena(), diveWithoutRecording(), editInSsi(), externalDiver(), importLena() (+20 more)

### Community 106 - "Community 106"
Cohesion: 0.14
Nodes (5): LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200ResponseMissingItem, ApiDiversIdLoadingsGet200ResponseOtu

### Community 107 - "Community 107"
Cohesion: 0.08
Nodes (31): PARTICIPANT_ROLES, DIVE_ONLY_PATTERN, storedProblemCode(), oneOf(), DiveProviderStatus, DiveProviderStatusSchema, LeftOut, PushView (+23 more)

### Community 108 - "Community 108"
Cohesion: 0.10
Nodes (12): Radius, drawBand(), ProfileSketch(), DiveProfileView, AscentBand, SketchCurve, SketchGeometry, sketchGeometry() (+4 more)

### Community 110 - "Community 110"
Cohesion: 0.08
Nodes (28): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+20 more)

### Community 111 - "Community 111"
Cohesion: 0.10
Nodes (26): ADR-0051, Status, ADR-0056, ADR-0024, ADR-0043, clearQueue(), conflictAtSsi(), connectSsi() (+18 more)

### Community 112 - "Community 112"
Cohesion: 0.17
Nodes (8): bottomTime(), otu(), oxygenLimitMinutes(), PlanningSacResult, BottomTimeToolInput, GasNumbersInput, ToolDiverSource, ToolsServiceTest

### Community 113 - "Community 113"
Cohesion: 0.18
Nodes (3): SyncCheckpoint, SyncState, DiveStoreTest

### Community 114 - "Community 114"
Cohesion: 0.12
Nodes (26): bar(), celsius(), contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf(), firstOf() (+18 more)

### Community 115 - "Community 115"
Cohesion: 0.09
Nodes (8): ApiDiveSitesGet200Response, SiteView, SiteViewExternalIdsItem, SiteViewExternalIdsItemAttribution, SiteViewExternalIdsItemOffered, SiteViewExternalIdsItemOfferedPosition, SiteViewExternalIdsItemPrivateTo, SiteViewPosition

### Community 116 - "Community 116"
Cohesion: 0.11
Nodes (25): ApiToolsBottomTimeGet200ResponseLimits, planningSac(), PlanningSacDive, PlanningSacSource, default, logbook, jsRound(), powInt() (+17 more)

### Community 117 - "Community 117"
Cohesion: 0.09
Nodes (28): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+20 more)

### Community 118 - "Community 118"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 119 - "Community 119"
Cohesion: 0.13
Nodes (28): DiveOrigin, REFERENCE_PREFIX, Series, asBool(), DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027 (+20 more)

### Community 120 - "Community 120"
Cohesion: 0.11
Nodes (23): createSsiSiteSource(), DEFAULT_SSI_SITES_URL, createSiteSources(), ADR-0021, ADR-0027, createOverpassSource(), createPoliteHttp(), Fetch (+15 more)

### Community 121 - "Community 121"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 122 - "Community 122"
Cohesion: 0.28
Nodes (8): leadAnswer(), EstimateCylinder, PlannedConditions, Suit, assemblePersonalizedSources(), suitOf(), leadEstimate(), PersonalizedToolsTest

### Community 123 - "Community 123"
Cohesion: 0.22
Nodes (4): FakeTokens, Harness, json(), SessionRepositoryTest

### Community 124 - "Community 124"
Cohesion: 0.09
Nodes (27): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+19 more)

### Community 125 - "Community 125"
Cohesion: 0.11
Nodes (25): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+17 more)

### Community 126 - "Community 126"
Cohesion: 0.07
Nodes (12): buddySite(), Dive, OTHER, PendingOffer, ADR-0012, ADR-0070, Dive, OTHER (+4 more)

### Community 127 - "Community 127"
Cohesion: 0.10
Nodes (22): dayNumber(), enteredDecoFromNdl(), minNdlSeconds(), NoFly, NoFlyReason, decompression, several, single (+14 more)

### Community 128 - "Community 128"
Cohesion: 0.11
Nodes (25): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+17 more)

### Community 129 - "Community 129"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 130 - "Community 130"
Cohesion: 0.10
Nodes (26): globalSetup(), ADR-0023, accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks() (+18 more)

### Community 131 - "Community 131"
Cohesion: 0.11
Nodes (6): ApiClientDiagnosticsPostRequest, ApiClientDiagnosticsPostRequestClient, ApiClientDiagnosticsPostRequestEntriesItem, DiagnosticHygiene, Diagnostics, Failed

### Community 132 - "Community 132"
Cohesion: 0.11
Nodes (7): ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh, ApiToolsBottomTimeGet200ResponseUsedSacLpm, ApiToolsBottomTimeGet200ResponseUsedWater

### Community 133 - "Community 133"
Cohesion: 0.11
Nodes (7): ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport

### Community 134 - "Community 134"
Cohesion: 0.13
Nodes (23): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, Site (+15 more)

### Community 135 - "Community 135"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 136 - "Community 136"
Cohesion: 0.21
Nodes (4): GasPlanScreenshotTest, GasPlanToolDiver, GasPlanToolInput, GasPlanToolLevel

### Community 138 - "Community 138"
Cohesion: 0.10
Nodes (4): ApiErrorBody, DecodeException, DiveHubException, NoAnswerException

### Community 139 - "Community 139"
Cohesion: 0.11
Nodes (23): SiteImportCounts, has(), inArea(), LatitudeBands, MATCH_WITHIN_M, merged(), NEAR_WITHIN_M, normalise() (+15 more)

### Community 140 - "Community 140"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 141 - "Community 141"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 142 - "Community 142"
Cohesion: 0.09
Nodes (4): DiveHubApplication, SyncWorker, WorkManagerPeriodicSync, PeriodicSyncScheduler

### Community 143 - "Community 143"
Cohesion: 0.13
Nodes (3): FakeInstances, FakeTokens, MainViewModelTest

### Community 144 - "Community 144"
Cohesion: 0.14
Nodes (7): InstanceProbeTest, json(), probe(), ProbeCache, ProbeInstances, probeRepository(), ProbeTokens

### Community 145 - "Community 145"
Cohesion: 0.11
Nodes (23): equipmentUse, DiveGear, GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow, ItemView (+15 more)

### Community 146 - "Community 146"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 147 - "Community 147"
Cohesion: 0.16
Nodes (20): BackupRunView, backupSettingsQuery(), BackupSettingsView, backupsQuery(), SiteDataView, supportBundlePreviewQuery(), SupportBundlePreviewView, ADR-0030 (+12 more)

### Community 148 - "Community 148"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 149 - "Community 149"
Cohesion: 0.12
Nodes (7): LeadAnswer, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseLeftOut, ApiDiversIdLeadEstimateGet200ResponseRange, ApiDiversIdLeadEstimateGet200ResponseReserve, ApiDiversIdLeadEstimateGet200ResponseWeightCheck

### Community 150 - "Community 150"
Cohesion: 0.11
Nodes (17): ProblemCode, ResponseState, whenResponseGone(), StoredBlob, PlacedArchive, ANSWER, looksLikeRecipientPrivateKey(), PlacedArchive (+9 more)

### Community 151 - "Community 151"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 152 - "Community 152"
Cohesion: 0.12
Nodes (19): Deep, DepthReading, depthReading(), deviceName(), DiveValue, AVG_DEPTH_M, DURATION_SECONDS, MAX_DEPTH_M (+11 more)

### Community 153 - "Community 153"
Cohesion: 0.13
Nodes (6): DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, WaitingPushView

### Community 154 - "Community 154"
Cohesion: 0.08
Nodes (24): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+16 more)

### Community 155 - "Community 155"
Cohesion: 0.11
Nodes (14): migrations, ADR-0029, ADR-0048, killTree(), ADR-0023, runLanes(), runStep(), takeLock() (+6 more)

### Community 156 - "Community 156"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 157 - "Community 157"
Cohesion: 0.17
Nodes (8): Connected, Hint, HintFrame, LiveEvent, LiveHint, LiveHints, SseLiveHints, LiveHintsTest

### Community 158 - "Community 158"
Cohesion: 0.12
Nodes (18): DEFAULT_OVERPASS_URL, Element, overpassQuery(), parseMaxDepth(), parseOverpass(), text(), ADR-0021, PoliteHttp (+10 more)

### Community 159 - "Community 159"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 160 - "Community 160"
Cohesion: 0.18
Nodes (3): LeadEstimateUiState, LeadEstimateViewModel, PersonalizedSources

### Community 162 - "Community 162"
Cohesion: 0.12
Nodes (18): CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043, CreateBody (+10 more)

### Community 163 - "Community 163"
Cohesion: 0.14
Nodes (16): compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051, candidatesOf(), ConfirmResult (+8 more)

### Community 164 - "Community 164"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 165 - "Community 165"
Cohesion: 0.15
Nodes (5): ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits

### Community 166 - "Community 166"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 167 - "Community 167"
Cohesion: 0.17
Nodes (6): Loading, SessionRepository, SessionState, SignedIn, SignedOut, UpdateRequired

### Community 168 - "Community 168"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource/poppins@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 169 - "Community 169"
Cohesion: 0.14
Nodes (16): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), fit (+8 more)

### Community 170 - "Community 170"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 171 - "Community 171"
Cohesion: 0.13
Nodes (17): AL80, asEstimateInput(), EstimateDiveCylinder, evaluateLeadVectorCase(), LEAD_CASES, leadInput(), leadVectorFile(), LeadVectorInput (+9 more)

### Community 172 - "Community 172"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 173 - "Community 173"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 175 - "Community 175"
Cohesion: 0.18
Nodes (3): FakeInstances, FakeTokens, SyncAuthTest

### Community 176 - "Community 176"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 177 - "Community 177"
Cohesion: 0.14
Nodes (15): JOINT_FILL_FIELDS, OfferRouteDeps, STATUS, ADR-0070, OfferError, OfferService, Facts, Field (+7 more)

### Community 178 - "Community 178"
Cohesion: 0.12
Nodes (16): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+8 more)

### Community 179 - "Community 179"
Cohesion: 0.16
Nodes (13): sampleCount(), storedList(), tryParseJson(), datasetLength(), APP_PARAMS, createSsiTransport(), exchange(), isObject() (+5 more)

### Community 181 - "Community 181"
Cohesion: 0.16
Nodes (12): BOM, Json, num(), obj(), positive(), scaled(), SuuntoJsonAdapter, time() (+4 more)

### Community 182 - "Community 182"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 183 - "Community 183"
Cohesion: 0.23
Nodes (16): managementOf(), createEquipmentService(), assemble(), detailOf(), lastCylinderOf(), managedItem(), managedRecord(), managedSchedule() (+8 more)

### Community 184 - "Community 184"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 185 - "Community 185"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 186 - "Community 186"
Cohesion: 0.27
Nodes (7): LeadInputTest, SuitType, drysuit, none, semidry, skin, wetsuit

### Community 188 - "Community 188"
Cohesion: 0.13
Nodes (4): FakeCache, FakeInstances, TokenStore, UserScopedStore

### Community 189 - "Community 189"
Cohesion: 0.16
Nodes (14): serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, DueEntry, ScheduleView, equipmentList(), EquipmentListFilter (+6 more)

### Community 190 - "Community 190"
Cohesion: 0.19
Nodes (11): decimal(), dive(), makeOceanicPlusUddf(), OCEANIC_DIVES, OceanicDive, site(), waypoint(), exported (+3 more)

### Community 191 - "Community 191"
Cohesion: 0.13
Nodes (10): EGYPT, ExternalId, HOUSE_REEF, Method, Position, Revision, Site, SiteImport (+2 more)

### Community 192 - "Community 192"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 193 - "Community 193"
Cohesion: 0.13
Nodes (14): concurrency, cancel-in-progress, group, env, RELEASE_TAG, jobs, publish, name (+6 more)

### Community 194 - "Community 194"
Cohesion: 0.35
Nodes (4): UiState, sessionDestination(), SessionDestinationTest, UserView

### Community 195 - "Community 195"
Cohesion: 0.19
Nodes (12): ImportedValues, SiteImportFinding, SiteImportProgress, ExistingSite, Field, PlannedCreate, PlannedExternalId, PlannedUpdate (+4 more)

### Community 196 - "Community 196"
Cohesion: 0.22
Nodes (11): fromOsm(), fromSsi(), fromWd(), HOUSE_REEF, osm(), site(), ssi(), ADR-0021 (+3 more)

### Community 197 - "Community 197"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 198 - "Community 198"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 199 - "Community 199"
Cohesion: 0.15
Nodes (13): BodyWeight, CylinderMaterial, aluminium, carbon, steel, EstimateMissing, SuitThickness, SuitUndergarment (+5 more)

### Community 200 - "Community 200"
Cohesion: 0.21
Nodes (9): createFitAdapter(), adapter, crossCheck(), decodeOfficial(), privateDir, ownersParts(), parse(), toPart() (+1 more)

### Community 201 - "Community 201"
Cohesion: 0.17
Nodes (10): EstimateInput, AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031 (+2 more)

### Community 202 - "Community 202"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 203 - "Community 203"
Cohesion: 0.26
Nodes (3): ApiMeGet200Response, ApiMeGet200ResponseOwnDiver, ApiMeGet200ResponsePreferences

### Community 204 - "Community 204"
Cohesion: 0.18
Nodes (10): EquipmentError, ADR-0034, ADR-0053, checkCost(), checkDates(), checkWindow(), checkResets(), checkSchedule() (+2 more)

### Community 205 - "Community 205"
Cohesion: 0.32
Nodes (9): errorMessage(), coordinate(), parseSsiSites(), sitesJson(), truthy(), ADR-0024, unavailable(), alpha2Of() (+1 more)

### Community 206 - "Community 206"
Cohesion: 0.24
Nodes (9): LEAD_VECTOR_KERNEL, VECTOR_KERNELS, VectorFile, assertMatch(), checkVectors(), isRecord(), readVectors(), VectorFile (+1 more)

### Community 207 - "Community 207"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 209 - "Community 209"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 210 - "Community 210"
Cohesion: 0.22
Nodes (8): diveRow(), rowOf(), ADR-0026, ADR-0048, diveRows(), goToMore(), expectFirstDiveInView(), rowOf()

### Community 211 - "Community 211"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 212 - "Community 212"
Cohesion: 0.20
Nodes (10): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, ADR-0076, MODULE_IMPORTS, moduleRules (+2 more)

### Community 213 - "Community 213"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 214 - "Community 214"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 216 - "Community 216"
Cohesion: 0.20
Nodes (8): EMPTY_SITE_IMPORT_COUNTS, ImportArea, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, SiteImportRequest, openStream()

### Community 217 - "Community 217"
Cohesion: 0.22
Nodes (8): wallClockMs(), AQABA, ATTERSEE, EILAT, HAUSREEF, local(), MID_ATLANTIC, ADR-0030

### Community 218 - "Community 218"
Cohesion: 0.20
Nodes (9): LeadEstimateAnswer, Confidence, EstimateAdjustment, EstimateBasis, EstimateCylinder, EstimateMissing, LeadEstimate, ADR-0025 (+1 more)

### Community 219 - "Community 219"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 220 - "Community 220"
Cohesion: 0.20
Nodes (10): verify, prerelease, sha, tag, versionCode, versionName, name, outputs (+2 more)

### Community 221 - "Community 221"
Cohesion: 0.20
Nodes (10): with, cache, distribution, fetch-depth, if-no-files-found, java-version, name, path (+2 more)

### Community 222 - "Community 222"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 223 - "Community 223"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 224 - "Community 224"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 225 - "Community 225"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 226 - "Community 226"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 230 - "Community 230"
Cohesion: 0.29
Nodes (4): InstanceUrl, InstanceProbeResult, Reachable, Unreachable

### Community 231 - "Community 231"
Cohesion: 0.25
Nodes (7): AIR, expectWithinReference(), GF100_FRESH, GF100_SALT, GF85_FRESH, ADR-0032, ADR-0046

### Community 232 - "Community 232"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 233 - "Community 233"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 234 - "Community 234"
Cohesion: 0.29
Nodes (6): itemIds(), ADR-0026, ADR-0034, Usage, UsageDive, UsageRow

### Community 235 - "Community 235"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 236 - "Community 236"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 237 - "Community 237"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 239 - "Community 239"
Cohesion: 0.60
Nodes (5): compressibility(), Cubic, minusOne(), pressureForLitres(), surfaceLitres()

### Community 240 - "Community 240"
Cohesion: 0.40
Nodes (5): stubDive(), ADR-0015, ADR-0023, ADR-0025, guardedRoute()

### Community 241 - "Community 241"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 242 - "Community 242"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 243 - "Community 243"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build

### Community 244 - "Community 244"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 245 - "Community 245"
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
- **1320 isolated node(s):** `Off`, `SuitThickness`, `Reachable`, `Reserve`, `RockBottom` (+1315 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3767 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **136 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 7` to `Community 0`, `Community 5`, `Community 6`, `Community 10`, `Community 16`, `Community 17`, `Community 145`, `Community 18`, `Community 20`, `Community 21`, `Community 22`, `Community 154`, `Community 27`, `Community 29`, `Community 30`, `Community 31`, `Community 32`, `Community 163`, `Community 36`, `Community 39`, `Community 42`, `Community 43`, `Community 44`, `Community 47`, `Community 49`, `Community 52`, `Community 189`, `Community 195`, `Community 68`, `Community 78`, `Community 81`, `Community 92`, `Community 97`, `Community 101`, `Community 234`, `Community 117`, `Community 124`, `Community 125`?**
  _High betweenness centrality (0.017) - this node is a cross-community bridge._
- **What connects `Off`, `SuitThickness`, `Reachable` to the rest of the system?**
  _1320 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.018324717353843566 - nodes in this community are weakly interconnected._
- **Why does `DiveStore` connect `Community 38` to `Community 96`, `Community 160`, `Community 34`, `Community 64`, `Community 65`, `Community 37`, `Community 74`, `Community 46`, `Community 175`, `Community 113`, `Community 82`, `Community 51`, `Community 116`, `Community 122`, `Community 187`, `Community 127`, `Community 62`, `Community 63`?**
  _High betweenness centrality (0.009) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.04105023296942489 - nodes in this community are weakly interconnected._
- **Why does `ApiClient` connect `Community 46` to `Community 64`, `Community 161`, `Community 65`, `Community 131`, `Community 37`, `Community 167`, `Community 138`, `Community 80`, `Community 84`, `Community 56`, `Community 60`, `Community 157`, `Community 63`?**
  _High betweenness centrality (0.009) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.02277765858542516 - nodes in this community are weakly interconnected._