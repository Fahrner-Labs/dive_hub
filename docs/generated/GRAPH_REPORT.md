# Graph Report - dive_hub  (2026-11-20)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 10736 nodes · 32333 edges · 369 communities (225 shown, 144 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 611 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `f1e3faeb`
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
- Community 242
- Community 243
- Community 244
- Community 245
- Community 246
- Community 247
- Community 248
- Community 249
- Community 252
- Community 254

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
- `askedAt()` --calls--> `DiveError`  [EXTRACTED]
  apps/server/src/dives/continuation/append-service.ts → apps/server/src/dives/dive-service.ts
- `Context` --references--> `Position`  [EXTRACTED]
  apps/server/src/imports/formats/uddf/uddf.ts → apps/server/src/vocabulary.ts

## Import Cycles
- None detected.

## Communities (369 total, 144 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (268): DiveView, cylinderCatalogueQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery() (+260 more)

### Community 1 - "Community 1"
Cohesion: 0.03
Nodes (147): user, cylinder, dive, diver, diverExternalId, duplicateCandidate, importJob, original (+139 more)

### Community 2 - "Community 2"
Cohesion: 0.05
Nodes (176): DeleteUserDialog(), Invite(), UserRow(), CreateAccess(), bodyWeightsQuery(), certificationsQuery(), diverDetailsQuery(), diverSearchQuery() (+168 more)

### Community 3 - "Community 3"
Cohesion: 0.02
Nodes (175): label(), diversQuery(), BuddyView, ConflictChoices, ConflictEntryView, ConflictFieldView, ConflictsOfConnection, connectionsQuery() (+167 more)

### Community 4 - "Community 4"
Cohesion: 0.02
Nodes (139): buddySuggestion, diveAssessment, diveFinding, jointDive, parseHint(), Assessment, FindingView, Row (+131 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (132): BACKUP_EXTENSION, BACKUP_FORMAT_VERSION, BACKUP_SKIPPED_DIRS, BackupArchiveError, BackupDecryptionKey, BackupEncryption, BackupEncryptionMetadata, BackupPart (+124 more)

### Community 6 - "Community 6"
Cohesion: 0.02
Nodes (129): CentreService, CentreRouteDeps, UTC_OFFSET_SOURCES, DiverService, waterMismatch(), CertificationHint, CertificationHintExpiry, DiveCertifications (+121 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (137): ComputerChoice, DiveImportMode, DiveKind, UtcOffsetSource, PartInput, mergeProviderCylinder(), ProviderCylinder, siteRef() (+129 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (124): deletedDivesTotal(), divesQuery(), LogbookPage, PAGE_SIZE, goneDivesQuery(), providerConflictsQuery(), ProblemCode, App() (+116 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (113): AiAccessLogEntry, aiAccessLogQuery(), aiAccessQuery(), AiAccessView, invitationLinkQuery(), invitationsQuery(), InvitationView, passwordResetQuery() (+105 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (114): continuationAppend, OVERRIDABLE_FIELDS, OverridableField, recordingEvent, RecordingSummary, sampleSeries, renumber(), renumberStatement() (+106 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (100): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+92 more)

### Community 12 - "Community 12"
Cohesion: 0.02
Nodes (106): accountListEntry, actorType, aiAccessOutcome, backupRunKind, backupRunStatus, candidateResolution, centreSource, CERTIFICATION_IMAGE_STATES (+98 more)

### Community 13 - "Community 13"
Cohesion: 0.03
Nodes (84): connection, ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS, eachFile(), readEntry(), ADR-0044 (+76 more)

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (90): device, diveSite, equipmentItem, managedDiverIds(), managesDiveDiver(), ADR-0016, CandidateError, CandidateRow (+82 more)

### Community 15 - "Community 15"
Cohesion: 0.04
Nodes (90): DIVE_KINDS, PARTICIPANT_ROLES, DIVE_ONLY_PATTERN, ErrorAnswer, PROBLEM_CODES, ProblemSchema, providerProblem(), storedProblemCode() (+82 more)

### Community 16 - "Community 16"
Cohesion: 0.04
Nodes (93): waitingOf(), open(), close(), createConnectionService(), checkAutomation(), checkFollowNumbers(), expiryOf(), onTurn() (+85 more)

### Community 17 - "Community 17"
Cohesion: 0.04
Nodes (78): createServer(), createAuth(), loadAuthSecret(), acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS (+70 more)

### Community 18 - "Community 18"
Cohesion: 0.04
Nodes (91): Queryable, logbookCheckAnswer, logbookCheckDiveAnswer, keepApart(), keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration() (+83 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (94): allButDive(), AnyLogbookCheck, continuationCandidateQuery(), ContinuationCandidateView, deletedDivesOn(), DeletedDivesPageView, deletedDivesQuery(), DeletedDiveView (+86 more)

### Community 20 - "Community 20"
Cohesion: 0.04
Nodes (17): MainActivity, MainViewModel, UiState, sessionDestination(), LoadingsUiState, LoadingsViewModel, SessionDestinationTest, SignInEmailRequest (+9 more)

### Community 21 - "Community 21"
Cohesion: 0.05
Nodes (80): DisplaySettings(), ReportProblemSettings(), SecuritySettings(), Sessions(), AdminSettings(), ClientDiagnosticsSetting(), Invitations(), LinkToPassOn() (+72 more)

### Community 22 - "Community 22"
Cohesion: 0.04
Nodes (48): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+40 more)

### Community 23 - "Community 23"
Cohesion: 0.03
Nodes (69): hashPassword(), BackupLog, BackupServiceDeps, createServices(), ServiceParts, Services, ADR-0027, ADR-0030 (+61 more)

### Community 24 - "Community 24"
Cohesion: 0.05
Nodes (79): Me, AcceptedBuddySuggestionView, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView, candidatesQuery() (+71 more)

### Community 25 - "Community 25"
Cohesion: 0.04
Nodes (75): diveSiteExternalId, forget(), findOrMake(), createSsiAdapter(), SSI_CAPABILITIES, createSsiClient(), Fetch, changeListenerOrNull() (+67 more)

### Community 26 - "Community 26"
Cohesion: 0.06
Nodes (75): buildApp(), assessmentRoutes(), SessionUser, authHandler(), authHeaders(), fastify, FastifyRequest, forwardCookies() (+67 more)

### Community 27 - "Community 27"
Cohesion: 0.04
Nodes (74): ConnectionRow, WaitingPushOptions, audienceOfConnection(), createScheduledFetch(), blocked(), due(), minimumOf(), OPENING_AFTER_MINUTES (+66 more)

### Community 28 - "Community 28"
Cohesion: 0.05
Nodes (76): AiAccessScope, seesPositions(), VerifiedAccess, diversOf(), SiteRow, checkedByRunTool, createMcpServer(), INSTRUCTIONS (+68 more)

### Community 29 - "Community 29"
Cohesion: 0.05
Nodes (66): invitation, userRole, AttemptLimit, clientKey(), limitAttempts(), ADR-0061, Problem, ProblemCode (+58 more)

### Community 30 - "Community 30"
Cohesion: 0.03
Nodes (61): ParticipantRole, ProviderImportPlan, providerSiteData, ConnectionService, ActionContext, Direction, DiveExport, DiveListing (+53 more)

### Community 31 - "Community 31"
Cohesion: 0.03
Nodes (13): AppContainer, KeystoreTokenStore, PreferencesInstanceUrlStore, UserCacheStore, AndroidDiveStore, DiveHubApplication, SyncWorker, WorkManagerPeriodicSync (+5 more)

### Community 32 - "Community 32"
Cohesion: 0.06
Nodes (63): ChangePassword(), ADR-0080, useDescribeAgent(), authClient, SignedOut(), CodeFigure(), DiveCodes(), showDiveCodes() (+55 more)

### Community 33 - "Community 33"
Cohesion: 0.04
Nodes (67): BottomTime, BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, Conditions, GasMix (+59 more)

### Community 34 - "Community 34"
Cohesion: 0.05
Nodes (22): DiveDetailUiState, DiveDetailViewModel, LogbookViewModel, DiveRepository, neighboursPath(), countOf(), filterChips(), LogbookFilter (+14 more)

### Community 35 - "Community 35"
Cohesion: 0.04
Nodes (18): ApiClientDiagnosticsStatusGet200Response, ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting, ProviderSummary (+10 more)

### Community 36 - "Community 36"
Cohesion: 0.05
Nodes (65): BackupRouteDeps, BACKUP_RUN_TASK, BackupService, backupTask(), ADR-0069, CertificationService, DIVE_IMPORT_MODES, AssessmentRefresh (+57 more)

### Community 37 - "Community 37"
Cohesion: 0.04
Nodes (67): ImportErrorCode, ImportFound, importOriginal, ImportOutcome, reviseDive(), untieLostSeries(), ensureItemForDevice(), FileFormat (+59 more)

### Community 38 - "Community 38"
Cohesion: 0.06
Nodes (67): tidyName(), DiverDetails, participantsOf(), dataIdsOf(), foldName(), nameDiffers(), Buddy, createBuddyService() (+59 more)

### Community 39 - "Community 39"
Cohesion: 0.06
Nodes (46): DiveBody(), DiveDetailRoute(), DiveDetailScreen(), errorText(), FactRow(), Facts(), Loading(), NeighbourButton() (+38 more)

### Community 40 - "Community 40"
Cohesion: 0.04
Nodes (65): AssessmentService, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView(), ADR-0036 (+57 more)

### Community 41 - "Community 41"
Cohesion: 0.04
Nodes (70): LEAD_PLACEMENTS, SUIT_TYPES, SUIT_UNDERGARMENTS, WEIGHTING_FELT, downsampleMinMax(), buddiesOf(), BuddySearch, count() (+62 more)

### Community 42 - "Community 42"
Cohesion: 0.07
Nodes (9): Action(), label(), Section(), SettingsDebugSection(), SettingsRoute(), SettingsScreen(), SyncLine(), LoadingSource (+1 more)

### Community 43 - "Community 43"
Cohesion: 0.08
Nodes (9): LeadEstimateViewModelTest, BodyWeight, Cylinder, Dive, Diver, toStore(), json(), LogbookRepositoryTest (+1 more)

### Community 44 - "Community 44"
Cohesion: 0.04
Nodes (65): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, CertificationView, CertificationViewSchema (+57 more)

### Community 45 - "Community 45"
Cohesion: 0.06
Nodes (14): DiveListView, DiveListViewCounts, DiveView, DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewLeadItem, DiveViewSac, DiveViewSite (+6 more)

### Community 46 - "Community 46"
Cohesion: 0.09
Nodes (48): BottomTime, BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen, BottomTimeLimits (+40 more)

### Community 47 - "Community 47"
Cohesion: 0.06
Nodes (61): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+53 more)

### Community 48 - "Community 48"
Cohesion: 0.05
Nodes (56): ExternalIdView, Position, running(), SiteImportArea, siteImportsQuery(), SiteImportView, siteRevisionsQuery(), SiteRevisionView (+48 more)

### Community 49 - "Community 49"
Cohesion: 0.06
Nodes (46): SsiClient, SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), DiveForSsi, emptyRecord(), writableRecord() (+38 more)

### Community 50 - "Community 50"
Cohesion: 0.08
Nodes (53): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+45 more)

### Community 51 - "Community 51"
Cohesion: 0.07
Nodes (60): Answer, answer(), baseFrom(), BodyWeight, Confidence, high, low, medium (+52 more)

### Community 52 - "Community 52"
Cohesion: 0.06
Nodes (6): DiveStore, toBoolean(), toLong(), toModel(), DiveSite, Recording

### Community 53 - "Community 53"
Cohesion: 0.12
Nodes (24): Body, BrokenBody, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage(), diverJson() (+16 more)

### Community 54 - "Community 54"
Cohesion: 0.07
Nodes (53): DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining(), ssiDiveType(), ssiInteger() (+45 more)

### Community 55 - "Community 55"
Cohesion: 0.08
Nodes (27): Profile(), ProfileChart(), ProfileTable(), DiveViewRecordingsItem, DiveViewRecordingsItemDevice, DiveViewRecordingsItemSummarySurfaceBreaksItem, RecordingSamplesView, RecordingSamplesViewSeriesItem (+19 more)

### Community 56 - "Community 56"
Cohesion: 0.08
Nodes (16): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), Answers (+8 more)

### Community 57 - "Community 57"
Cohesion: 0.06
Nodes (12): ApiClientDiagnosticsPostRequest, ApiClientDiagnosticsPostRequestClient, ApiClientDiagnosticsPostRequestEntriesItem, DiagnosticHygiene, Diagnostics, Failed, DiagnosticEntry, DiagnosticLevel (+4 more)

### Community 58 - "Community 58"
Cohesion: 0.06
Nodes (36): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+28 more)

### Community 59 - "Community 59"
Cohesion: 0.07
Nodes (9): SettingsDiagnosticsState, SettingsUiState, SettingsViewModel, FakeDiagnostics, FakeInstances, SettingsViewModelTest, ReportProblemResult, Sent (+1 more)

### Community 60 - "Community 60"
Cohesion: 0.07
Nodes (45): ImportArea, createSsiSiteSource(), createSiteSources(), ADR-0021, ADR-0027, inArea(), createOverpassSource(), DEFAULT_OVERPASS_URL (+37 more)

### Community 61 - "Community 61"
Cohesion: 0.05
Nodes (45): DiveExportAction, buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open(), saveAndReadBack() (+37 more)

### Community 62 - "Community 62"
Cohesion: 0.09
Nodes (11): ApiDiverBodyWeightsGet200ResponseItem, Complete, SessionEnded, Stopped, SyncEngine, SyncPhase, Details, Meta (+3 more)

### Community 63 - "Community 63"
Cohesion: 0.06
Nodes (14): ApiClient, ApiErrorBody, DecodeException, DiveHubException, NoAnswerException, Connected, Hint, HintFrame (+6 more)

### Community 64 - "Community 64"
Cohesion: 0.11
Nodes (27): Body, Hold, Offline, RefreshData, refreshDeletedJson(), refreshDetailJson(), refreshDetailsPage(), refreshDiveId() (+19 more)

### Community 65 - "Community 65"
Cohesion: 0.07
Nodes (47): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+39 more)

### Community 66 - "Community 66"
Cohesion: 0.05
Nodes (42): providerConflict, waitingPush, kept(), ExportingProvider, DELETE_QUIET_MS, QUIET_MS, DeletedDive, Outcome (+34 more)

### Community 67 - "Community 67"
Cohesion: 0.12
Nodes (44): formatSyncTime(), GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult(), PlanInputs() (+36 more)

### Community 68 - "Community 68"
Cohesion: 0.06
Nodes (18): AvatarCircle(), drawBand(), ProfileSketch(), initials(), mixName(), LogbookPaging, AscentBand, SketchCurve (+10 more)

### Community 69 - "Community 69"
Cohesion: 0.05
Nodes (49): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, conflictForLena() (+41 more)

### Community 70 - "Community 70"
Cohesion: 0.05
Nodes (47): REVISION_CAUSES, PositionSchema, AdoptBody, Country, CreateBody, Description, EditBody, errors (+39 more)

### Community 71 - "Community 71"
Cohesion: 0.05
Nodes (45): CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043, CreateBody (+37 more)

### Community 72 - "Community 72"
Cohesion: 0.08
Nodes (40): bottomTime(), BottomTimeQuery, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033, ADR-0071 (+32 more)

### Community 73 - "Community 73"
Cohesion: 0.06
Nodes (44): DiveValues, OverridableField, revisionsQuery(), RevisionView, UtcOffsetSource, CylinderList(), Entry(), isValue() (+36 more)

### Community 74 - "Community 74"
Cohesion: 0.06
Nodes (42): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+34 more)

### Community 75 - "Community 75"
Cohesion: 0.08
Nodes (43): surfacePressureBar(), AIR, DEFAULT_CONDITIONS, N2_REPLAY_WINDOW_HOURS, N2_STALE_AFTER_HOURS, N2Loadings, N2LoadingsBase, N2UnavailableReason (+35 more)

### Community 76 - "Community 76"
Cohesion: 0.07
Nodes (35): diveWith(), ADR-0028, ADR-0043, buddySite(), Dive, OTHER, resetSource(), ADR-0012 (+27 more)

### Community 77 - "Community 77"
Cohesion: 0.08
Nodes (19): AppNavHost(), AppScreens, clearSavedTabStates(), isShowing(), leaveDiveForLogbook(), MainShell(), navigateToTab(), tabOf() (+11 more)

### Community 78 - "Community 78"
Cohesion: 0.13
Nodes (3): DiveRoute, AppNavHostTest, FakeScreens

### Community 79 - "Community 79"
Cohesion: 0.05
Nodes (39): CYLINDER_MATERIALS, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053, replyDiverError, STATUS (+31 more)

### Community 80 - "Community 80"
Cohesion: 0.11
Nodes (8): FakeCache, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore, UserScopedStore

### Community 81 - "Community 81"
Cohesion: 0.07
Nodes (5): LogbookViewModelTest, SettingsRouteTest, SettingsRepository, FakeInstances, SettingsRepositoryTest

### Community 82 - "Community 82"
Cohesion: 0.07
Nodes (36): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+28 more)

### Community 83 - "Community 83"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 84 - "Community 84"
Cohesion: 0.06
Nodes (35): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+27 more)

### Community 85 - "Community 85"
Cohesion: 0.10
Nodes (7): LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200ResponseLeftOut, ApiDiversIdLeadEstimateGet200ResponseMissingItem, ApiDiversIdLeadEstimateGet200ResponseRange, ApiDiversIdLeadEstimateGet200ResponseWeightCheck

### Community 86 - "Community 86"
Cohesion: 0.12
Nodes (4): DiveDetailScreenshotTest, DiveViewFromRecording, DiveViewFromRecordingStartsAt, DiveViewWaterMismatch

### Community 88 - "Community 88"
Cohesion: 0.07
Nodes (32): WeightingFeedback, WeightingFelt, right, too_heavy, too_light, dayNumber(), enteredDecoFromNdl(), minNdlSeconds() (+24 more)

### Community 89 - "Community 89"
Cohesion: 0.07
Nodes (28): LoadingsView, CurrentLoadings(), CustomTypeOptions, i18next, initI18n(), resources, ADR-0014, Language (+20 more)

### Community 90 - "Community 90"
Cohesion: 0.08
Nodes (37): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+29 more)

### Community 91 - "Community 91"
Cohesion: 0.10
Nodes (22): ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseCns, ApiDiversIdLoadingsGet200ResponseN2, ApiDiversIdLoadingsGet200ResponseOtu, cnsDecayed(), Estimate, isoUtc() (+14 more)

### Community 92 - "Community 92"
Cohesion: 0.07
Nodes (32): SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject(), MAX_CARD_IMAGE_BYTES (+24 more)

### Community 93 - "Community 93"
Cohesion: 0.05
Nodes (37): AREA_OF, areaOf(), areas, capture(), findings, headers, ADR-0020, ADR-0021 (+29 more)

### Community 94 - "Community 94"
Cohesion: 0.08
Nodes (25): captureDiagnostic(), captureError(), cleanContext(), CLIENT_NAME, CLIENT_VERSION, DiagnosticEntry, diagnosticFromError(), DIAGNOSTICS_KEY (+17 more)

### Community 95 - "Community 95"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 96 - "Community 96"
Cohesion: 0.10
Nodes (12): DiveListViewDivesItem, DiveListViewDivesItemGasesItem, DiveListViewDivesItemParticipantsItem, DiveListViewDivesItemSite, DiveListViewMonthsItem, DiveProfileView, localMonthOf(), LogbookMonth (+4 more)

### Community 97 - "Community 97"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 98 - "Community 98"
Cohesion: 0.11
Nodes (17): DiveRow(), Error(), Fact(), Filters(), Loading(), LogbookList(), LogbookRoute(), LogbookScreen() (+9 more)

### Community 99 - "Community 99"
Cohesion: 0.09
Nodes (4): DiveHubApplicationTest, LauncherIconTest, SplashTheme, SplashThemeTest

### Community 100 - "Community 100"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 101 - "Community 101"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 102 - "Community 102"
Cohesion: 0.07
Nodes (28): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError (+20 more)

### Community 103 - "Community 103"
Cohesion: 0.09
Nodes (28): createFileFormats(), createFitAdapter(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf() (+20 more)

### Community 104 - "Community 104"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 105 - "Community 105"
Cohesion: 0.13
Nodes (34): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+26 more)

### Community 106 - "Community 106"
Cohesion: 0.11
Nodes (28): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+20 more)

### Community 107 - "Community 107"
Cohesion: 0.11
Nodes (25): DiveViewRecordingsItemSummary, DiveViewRecordingsItemSummaryGasesItem, planningSac(), PlanningSacDive, PlanningSacSource, default, logbook, jsRound() (+17 more)

### Community 108 - "Community 108"
Cohesion: 0.11
Nodes (25): auth, out, services, createSecretBox(), ADR-0024, SiteSourceAdapter, appWith(), services() (+17 more)

### Community 109 - "Community 109"
Cohesion: 0.11
Nodes (31): diverBodyWeight, lead, LeadPlacement, SuitType, SuitUndergarment, WeightingFelt, DiveRow, LeadEntry (+23 more)

### Community 110 - "Community 110"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 111 - "Community 111"
Cohesion: 0.17
Nodes (8): bottomTime(), otu(), oxygenLimitMinutes(), PlanningSacResult, BottomTimeToolInput, GasNumbersInput, ToolDiverSource, ToolsServiceTest

### Community 112 - "Community 112"
Cohesion: 0.11
Nodes (29): ImportedValues, distanceM(), ExistingSite, has(), LatitudeBands, MATCH_WITHIN_M, matchingSite(), merged() (+21 more)

### Community 113 - "Community 113"
Cohesion: 0.13
Nodes (30): importsQuery(), ImportContext, ImportFilesButton(), ImportHistory(), ImportList(), ImportNotices(), ImportPanel(), ImportProvider() (+22 more)

### Community 114 - "Community 114"
Cohesion: 0.16
Nodes (21): Formats, Adjustment(), amount(), Answer(), Conditions(), detail(), hoodName(), LeadEstimateRoute() (+13 more)

### Community 116 - "Community 116"
Cohesion: 0.09
Nodes (8): ApiDiveSitesGet200Response, SiteView, SiteViewExternalIdsItem, SiteViewExternalIdsItemAttribution, SiteViewExternalIdsItemOffered, SiteViewExternalIdsItemOfferedPosition, SiteViewExternalIdsItemPrivateTo, SiteViewPosition

### Community 118 - "Community 118"
Cohesion: 0.11
Nodes (27): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+19 more)

### Community 119 - "Community 119"
Cohesion: 0.09
Nodes (25): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+17 more)

### Community 120 - "Community 120"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 121 - "Community 121"
Cohesion: 0.13
Nodes (12): DiveHubApp(), DiveHubScreens, ErrorText(), Gate(), InstanceForm(), LoadingScreen(), PasswordField(), PrimaryButton() (+4 more)

### Community 122 - "Community 122"
Cohesion: 0.09
Nodes (27): ENGINE_VERSION, EVIDENCE, Evidence, FindingValues, NOT_COVERED_DIVE_MODES, RULE_IDS, RuleId, RULES (+19 more)

### Community 123 - "Community 123"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 124 - "Community 124"
Cohesion: 0.09
Nodes (27): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+19 more)

### Community 125 - "Community 125"
Cohesion: 0.10
Nodes (23): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearPushRuns(), clearQueue() (+15 more)

### Community 126 - "Community 126"
Cohesion: 0.18
Nodes (4): GasPlanScreenshotTest, GasPlanToolDiver, GasPlanToolInput, GasPlanToolLevel

### Community 127 - "Community 127"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 128 - "Community 128"
Cohesion: 0.08
Nodes (22): headers, ADR-0035, aiAccessReady(), askMcp(), cancelWaitingImports(), createAiAccess(), putAside(), waitingImport() (+14 more)

### Community 130 - "Community 130"
Cohesion: 0.11
Nodes (7): ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh, ApiToolsBottomTimeGet200ResponseUsedSacLpm, ApiToolsBottomTimeGet200ResponseUsedWater

### Community 131 - "Community 131"
Cohesion: 0.28
Nodes (6): EstimateCylinder, PlannedConditions, Suit, assemblePersonalizedSources(), leadEstimate(), PersonalizedToolsTest

### Community 132 - "Community 132"
Cohesion: 0.13
Nodes (25): NoFly, getDiverLoadings, DEFAULT_GF_HIGH, residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows(), DiveRow (+17 more)

### Community 133 - "Community 133"
Cohesion: 0.08
Nodes (24): EMPTY_SITE_IMPORT_COUNTS, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, siteImport, siteImportStatus (+16 more)

### Community 134 - "Community 134"
Cohesion: 0.09
Nodes (22): Cylinder, DiveView, RevisionView, ADR-0045, upload(), Candidate, Check, Outcome (+14 more)

### Community 135 - "Community 135"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 136 - "Community 136"
Cohesion: 0.15
Nodes (7): LeadAnswer, leadAnswer(), LeadEstimateUiState, LeadEstimateViewModel, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, PersonalizedSources

### Community 137 - "Community 137"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 138 - "Community 138"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 139 - "Community 139"
Cohesion: 0.11
Nodes (23): equipmentUse, DiveGear, GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow, ItemView (+15 more)

### Community 140 - "Community 140"
Cohesion: 0.17
Nodes (23): DiveOrigin, SsiLogbook, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef(), MATERIAL_OF_TANK_TYPE (+15 more)

### Community 141 - "Community 141"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 142 - "Community 142"
Cohesion: 0.08
Nodes (25): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+17 more)

### Community 143 - "Community 143"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 144 - "Community 144"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 146 - "Community 146"
Cohesion: 0.13
Nodes (6): DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, WaitingPushView

### Community 147 - "Community 147"
Cohesion: 0.14
Nodes (23): requireManagedDiver(), managementOf(), EquipmentError, checkCost(), checkDates(), checkWindow(), createEquipmentService(), assemble() (+15 more)

### Community 148 - "Community 148"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 149 - "Community 149"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 151 - "Community 151"
Cohesion: 0.13
Nodes (16): compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051, candidatesOf(), ConfirmResult (+8 more)

### Community 154 - "Community 154"
Cohesion: 0.10
Nodes (17): account, accountRelations, apikey, rateLimit, sessionRelations, ADR-0011, ADR-0012, ADR-0035 (+9 more)

### Community 155 - "Community 155"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 156 - "Community 156"
Cohesion: 0.15
Nodes (5): ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits

### Community 157 - "Community 157"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 158 - "Community 158"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource/poppins@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 159 - "Community 159"
Cohesion: 0.11
Nodes (18): BOTTOM_TIME_RULES, PLAN_RULES, PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody (+10 more)

### Community 160 - "Community 160"
Cohesion: 0.27
Nodes (16): ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres(), isHypoxic(), metresPerBar() (+8 more)

### Community 161 - "Community 161"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 162 - "Community 162"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 163 - "Community 163"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 164 - "Community 164"
Cohesion: 0.19
Nodes (11): LeadInputTest, SuitType, drysuit, none, semidry, skin, wetsuit, SuitUndergarment (+3 more)

### Community 166 - "Community 166"
Cohesion: 0.18
Nodes (17): SiteImportFinding, SiteImportProgress, Field, columnsOf(), createSiteImportService(), create(), loadExisting(), update() (+9 more)

### Community 167 - "Community 167"
Cohesion: 0.24
Nodes (8): decode(), ENTITIES, localName(), Open, Reader, ADR-0003, XmlElement, XmlError

### Community 168 - "Community 168"
Cohesion: 0.19
Nodes (15): clientVersionGate(), ADR-0076, ClientVersionRegistry, MIN_CLIENT_VERSIONS, MinClientVersion, ADR-0076, clientRequestSerializer(), ADR-0076 (+7 more)

### Community 169 - "Community 169"
Cohesion: 0.18
Nodes (3): FakeInstances, FakeTokens, SyncAuthTest

### Community 170 - "Community 170"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 172 - "Community 172"
Cohesion: 0.20
Nodes (13): errorMessage(), looksLikeZip(), coordinate(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy(), ADR-0024 (+5 more)

### Community 173 - "Community 173"
Cohesion: 0.23
Nodes (15): prepareEntry(), DENIED_KEY_PARTS, emptyRedactionCounters(), isDeniedKey(), maskString(), redactInner(), redactLogObject(), RedactOptions (+7 more)

### Community 174 - "Community 174"
Cohesion: 0.15
Nodes (10): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, ADR-0068 (+2 more)

### Community 175 - "Community 175"
Cohesion: 0.17
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 176 - "Community 176"
Cohesion: 0.19
Nodes (4): ApiToolsBottomTimeGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseLimits, ApiToolsBottomTimeGet200ResponseResidualN2

### Community 177 - "Community 177"
Cohesion: 0.19
Nodes (4): DiveDetailsListView, DiveDetailsListViewCounts, DiveDetailsListViewMonthsItem, DiveDetailsListViewTotals

### Community 178 - "Community 178"
Cohesion: 0.12
Nodes (13): clientDiagnostic, clientDiagnosticsSettings, ClientDiagnosticsEntryInput, ClientDiagnosticsEntryView, ClientDiagnosticsError, ClientDiagnosticsUpload, KEEP_NEWEST, MAX_UPLOAD_ENTRIES (+5 more)

### Community 179 - "Community 179"
Cohesion: 0.14
Nodes (13): AL80, EstimateDiveCylinder, LEAD_CASES, leadInput(), LeadVectorInput, planOf(), STEEL_12, ADR-0031 (+5 more)

### Community 180 - "Community 180"
Cohesion: 0.19
Nodes (13): asEstimateInput(), evaluateLeadVectorCase(), LEAD_VECTOR_KERNEL, leadVectorFile(), VECTOR_KERNELS, VectorFile, vectorFile(), assertMatch() (+5 more)

### Community 181 - "Community 181"
Cohesion: 0.21
Nodes (11): buildSupportBundle(), buildTarGz(), json(), ndjson(), octal(), SUPPORT_BUNDLE_FORMAT, SUPPORT_BUNDLE_FORMAT_VERSION, SupportBufferManifest (+3 more)

### Community 182 - "Community 182"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 183 - "Community 183"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 184 - "Community 184"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 187 - "Community 187"
Cohesion: 0.16
Nodes (14): serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, DueEntry, ScheduleView, equipmentList(), EquipmentListFilter (+6 more)

### Community 188 - "Community 188"
Cohesion: 0.13
Nodes (10): EGYPT, ExternalId, HOUSE_REEF, Method, Position, Revision, Site, SiteImport (+2 more)

### Community 189 - "Community 189"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 190 - "Community 190"
Cohesion: 0.13
Nodes (14): name, needs, runs-on, timeout-minutes, concurrency, cancel-in-progress, group, env (+6 more)

### Community 191 - "Community 191"
Cohesion: 0.20
Nodes (5): AppTab, Logbook, Settings, Tools, DiveHubBottomBar()

### Community 193 - "Community 193"
Cohesion: 0.20
Nodes (5): LogRingBuffer, RedactionCounters, SupportBundleInput, SupportBundleManifest, SupportServiceDeps

### Community 194 - "Community 194"
Cohesion: 0.22
Nodes (11): fromOsm(), fromSsi(), fromWd(), HOUSE_REEF, osm(), site(), ssi(), ADR-0021 (+3 more)

### Community 195 - "Community 195"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 196 - "Community 196"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 197 - "Community 197"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 198 - "Community 198"
Cohesion: 0.26
Nodes (3): ApiMeGet200Response, ApiMeGet200ResponseOwnDiver, ApiMeGet200ResponsePreferences

### Community 199 - "Community 199"
Cohesion: 0.26
Nodes (3): DiverView, DiverViewCertifications, DiverViewCertificationsNewest

### Community 200 - "Community 200"
Cohesion: 0.26
Nodes (3): DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 201 - "Community 201"
Cohesion: 0.26
Nodes (3): NeighboursView, NeighboursViewNext, NeighboursViewPrevious

### Community 202 - "Community 202"
Cohesion: 0.18
Nodes (10): LogBufferStats, SupportBundle, ClientDiagnosticsProvider, FailedBackup, FailedJob, HealthSection, iso(), JobsSection (+2 more)

### Community 203 - "Community 203"
Cohesion: 0.18
Nodes (9): AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031, WETSUIT_5 (+1 more)

### Community 204 - "Community 204"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 205 - "Community 205"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 206 - "Community 206"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 207 - "Community 207"
Cohesion: 0.20
Nodes (10): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, ADR-0076, MODULE_IMPORTS, moduleRules (+2 more)

### Community 208 - "Community 208"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 209 - "Community 209"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 210 - "Community 210"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 212 - "Community 212"
Cohesion: 0.22
Nodes (9): CandidateRouteDeps, CandidateView, Decided, DiveBrief, OffsetSource, STATUS, ADR-0016, ADR-0030 (+1 more)

### Community 213 - "Community 213"
Cohesion: 0.20
Nodes (9): LeadEstimateAnswer, Confidence, EstimateAdjustment, EstimateBasis, EstimateInput, EstimateMissing, LeadEstimate, ADR-0025 (+1 more)

### Community 214 - "Community 214"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 215 - "Community 215"
Cohesion: 0.20
Nodes (10): verify, prerelease, sha, tag, versionCode, versionName, name, outputs (+2 more)

### Community 216 - "Community 216"
Cohesion: 0.20
Nodes (10): with, cache, distribution, fetch-depth, if-no-files-found, java-version, name, path (+2 more)

### Community 217 - "Community 217"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 218 - "Community 218"
Cohesion: 0.39
Nodes (7): LoadingFact(), LoadingsAnswer(), LoadingsRoute(), LoadingsScreen(), MissingValue(), percent(), SourceLink()

### Community 219 - "Community 219"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 220 - "Community 220"
Cohesion: 0.28
Nodes (8): itemIds(), ADR-0026, ADR-0034, Usage, UsageDive, usageMap(), usageOf(), UsageRow

### Community 221 - "Community 221"
Cohesion: 0.31
Nodes (5): BufferedLog, createLogRingBuffer(), LOG_BUFFER_CAPACITY_BYTES, LOG_BUFFER_CAPACITY_ENTRIES, LogRingBufferOptions

### Community 222 - "Community 222"
Cohesion: 0.22
Nodes (7): AQABA, ATTERSEE, EILAT, HAUSREEF, local(), MID_ATLANTIC, ADR-0030

### Community 223 - "Community 223"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 224 - "Community 224"
Cohesion: 0.22
Nodes (9): version, on, push, workflow_dispatch, tags, description, required, type (+1 more)

### Community 225 - "Community 225"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 226 - "Community 226"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 231 - "Community 231"
Cohesion: 0.29
Nodes (5): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, EstimateCylinder

### Community 232 - "Community 232"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 233 - "Community 233"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 235 - "Community 235"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 236 - "Community 236"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 237 - "Community 237"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 238 - "Community 238"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 239 - "Community 239"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 240 - "Community 240"
Cohesion: 0.40
Nodes (4): TAGS, ADR-0023, { stdout, stderr }, total

### Community 245 - "Community 245"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 246 - "Community 246"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 247 - "Community 247"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1297 isolated node(s):** `Reserve`, `RockBottom`, `Off`, `SuitThickness`, `Sent` (+1292 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3635 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **144 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 1` to `Community 132`, `Community 5`, `Community 6`, `Community 7`, `Community 4`, `Community 10`, `Community 11`, `Community 12`, `Community 139`, `Community 142`, `Community 14`, `Community 16`, `Community 17`, `Community 18`, `Community 13`, `Community 23`, `Community 151`, `Community 25`, `Community 154`, `Community 27`, `Community 28`, `Community 29`, `Community 30`, `Community 37`, `Community 38`, `Community 166`, `Community 40`, `Community 41`, `Community 44`, `Community 178`, `Community 187`, `Community 66`, `Community 70`, `Community 72`, `Community 202`, `Community 220`, `Community 102`, `Community 108`, `Community 109`, `Community 118`, `Community 124`?**
  _High betweenness centrality (0.019) - this node is a cross-community bridge._
- **What connects `Reserve`, `RockBottom`, `Off` to the rest of the system?**
  _1297 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.018313768313768315 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 9` to `Community 32`, `Community 0`, `Community 2`, `Community 3`, `Community 8`, `Community 73`, `Community 48`, `Community 113`, `Community 50`, `Community 19`, `Community 21`, `Community 24`, `Community 123`, `Community 158`, `Community 95`?**
  _High betweenness centrality (0.017) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.031678541331494676 - nodes in this community are weakly interconnected._
- **Why does `@playwright/test` connect `Community 58` to `Community 128`, `Community 69`, `Community 197`, `Community 76`, `Community 205`, `Community 141`, `Community 206`, `Community 93`, `Community 119`, `Community 183`, `Community 123`, `Community 125`?**
  _High betweenness centrality (0.011) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.050813218688490626 - nodes in this community are weakly interconnected._