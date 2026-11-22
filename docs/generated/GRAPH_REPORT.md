# Graph Report - dive_hub  (2026-11-22)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 11365 nodes · 34670 edges · 360 communities (213 shown, 147 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 663 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `1bf848ff`
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
- Community 230
- Community 231
- Community 232
- Community 233
- Community 234
- Community 235
- Community 236
- Community 237
- Community 241

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
- `RepositoryHarness` --calls--> `LogbookRepository`  [INFERRED]
  apps/android/shared/src/commonTest/kotlin/dev/divehub/shared/logbook/LogbookTest.kt → apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/logbook/LogbookRepository.kt
- `Context` --references--> `Position`  [EXTRACTED]
  apps/server/src/imports/formats/uddf/uddf.ts → apps/server/src/vocabulary.ts

## Import Cycles
- None detected.

## Communities (360 total, 147 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (267): user, buddySuggestion, dive, diveAssessment, diveFinding, jointDive, participant, providerConflict (+259 more)

### Community 1 - "Community 1"
Cohesion: 0.02
Nodes (241): cylinderCatalogueQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas (+233 more)

### Community 2 - "Community 2"
Cohesion: 0.04
Nodes (234): ChangePassword(), DisplaySettings(), ReportProblemSettings(), SecuritySettings(), Sessions(), ADR-0080, useDescribeAgent(), AdminSettings() (+226 more)

### Community 3 - "Community 3"
Cohesion: 0.02
Nodes (187): acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), AppLockProbe (+179 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (162): EMPTY_SITE_IMPORT_COUNTS, ImportArea, ImportedValues, SiteImportCounts, ADR-0021, ADR-0025, ADR-0053, diveSite (+154 more)

### Community 5 - "Community 5"
Cohesion: 0.02
Nodes (139): Me, BackupRunView, BackupSettingsView, SiteDataView, SupportBundlePreviewView, ADR-0030, ClientDiagnosticsSettingsView, clientDiagnosticsStatusQuery() (+131 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (133): createServer(), auth, loadAuthSecret(), BackupServiceDeps, createServices(), ServiceParts, ADR-0027, ADR-0030 (+125 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (146): diversQuery(), diveNeighboursQuery(), DiveSummary, DiveValues, LogbookPage, OverridableField, PAGE_SIZE, revisionsQuery() (+138 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (123): label(), AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView, candidatesQuery() (+115 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (117): CentreService, CentreRouteDeps, LEAD_PLACEMENTS, OVERRIDABLE_FIELDS, UTC_OFFSET_SOURCES, DiverService, DiveCertifications, LogbookCheckService (+109 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (96): connection, recording, diveModeFromFit, createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS (+88 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (33): DiveRow(), Figures(), RowFacts(), EmptyState(), FactRow(), NoteText(), StackedNoteText(), AppTab (+25 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (123): buddiesForQuery(), buddiesQuery(), BuddyView, ConflictChoices, ConflictEntryView, ConflictFieldView, ConflictsOfConnection, connectionsQuery() (+115 more)

### Community 13 - "Community 13"
Cohesion: 0.02
Nodes (119): StoredConflictField, actorType, aiAccessOutcome, backupRunKind, backupRunStatus, candidateResolution, centreSource, certificationImageState (+111 more)

### Community 14 - "Community 14"
Cohesion: 0.04
Nodes (104): renumber(), renumberStatement(), ADR-0048, managedDiverIds(), managesDiveDiver(), CandidateError, createCandidateService(), lockMine() (+96 more)

### Community 15 - "Community 15"
Cohesion: 0.03
Nodes (105): Tx, OverridableField, askedAt(), buildJoined(), partInput(), partsOfJoined(), rebuildJoined(), RecordingRow (+97 more)

### Community 16 - "Community 16"
Cohesion: 0.05
Nodes (65): Section(), cylinderPressures(), Cylinders(), Direction, Back, Left, Right, DirectionIcon() (+57 more)

### Community 17 - "Community 17"
Cohesion: 0.03
Nodes (103): supportBundlePreviewQuery(), divesQuery(), importsQuery(), ImportView, uploadFile(), siteImportsQuery(), App(), BackupsPage (+95 more)

### Community 18 - "Community 18"
Cohesion: 0.03
Nodes (21): SettingsDiagnosticsState, SettingsUiState, SettingsViewModel, FakeDiagnostics, FakeInstances, SettingsViewModelTest, ApiClientDiagnosticsPostRequest, ApiClientDiagnosticsPostRequestClient (+13 more)

### Community 19 - "Community 19"
Cohesion: 0.03
Nodes (14): MainActivity, DiveDetailViewModel, DiveHubApp(), Gate(), LoadingScreen(), UpdateRequiredScreen(), followStoreWrites(), LeadEstimateUiState (+6 more)

### Community 20 - "Community 20"
Cohesion: 0.03
Nodes (85): diveLease, providerLease, pushRun, waitingPush, WaitingPushOptions, kept(), keyLease(), LEASE_MS (+77 more)

### Community 21 - "Community 21"
Cohesion: 0.04
Nodes (10): DiveDetailViewModelTest, GatedDispatcher, CountingDispatcher, LogbookViewModelTest, ApiClient, ApiErrorBody, StoredDive, StoreLogbook (+2 more)

### Community 22 - "Community 22"
Cohesion: 0.03
Nodes (88): ConnectionRow, providerGoneDive, ADR-0016, ADR-0053, externalDiver(), mergeExternalDiver(), participations(), ADR-0028 (+80 more)

### Community 23 - "Community 23"
Cohesion: 0.04
Nodes (91): logbookCheckAnswer, logbookCheckDiveAnswer, keepApart(), keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration(), CheckedDive (+83 more)

### Community 24 - "Community 24"
Cohesion: 0.05
Nodes (92): diveFingerprint, PushLeftOut, waitingOf(), audienceOfConnection(), close(), currentRemote(), PushRow, ADR-0024 (+84 more)

### Community 25 - "Community 25"
Cohesion: 0.04
Nodes (82): DIVE_FIELDS, forSsi(), remoteBuddy(), SSI_CAPABILITIES, SsiCallLog, ADR-0024, ADR-0027, ADR-0029 (+74 more)

### Community 26 - "Community 26"
Cohesion: 0.07
Nodes (14): FakeCache, RevisionFollowTest, LeadEstimateViewModelTest, DiveViewRecordingsItemSummary, ErrorCodes, Cylinder, Dive, Diver (+6 more)

### Community 27 - "Community 27"
Cohesion: 0.07
Nodes (70): Formats, formatSyncTime(), GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult() (+62 more)

### Community 28 - "Community 28"
Cohesion: 0.04
Nodes (26): MainViewModel, UiState, Unreachable, sessionDestination(), SessionDestinationTest, ApiMeGet200Response, ApiMeGet200ResponsePreferences, UserView (+18 more)

### Community 29 - "Community 29"
Cohesion: 0.04
Nodes (84): CertificationService, CertificationRouteDeps, DIVE_IMPORT_MODES, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053, ErrorAnswer (+76 more)

### Community 30 - "Community 30"
Cohesion: 0.04
Nodes (91): ComputerChoice, DiveImportMode, DiveKind, providerSiteData, ProviderCylinder, siteRef(), entryMatches(), kindOfMode() (+83 more)

### Community 31 - "Community 31"
Cohesion: 0.04
Nodes (68): equipmentDueQuery(), CodeFigure(), DiveCodes(), showDiveCodes(), ADR-0043, EquipmentDueNotice(), ADR-0034, avatarTone() (+60 more)

### Community 32 - "Community 32"
Cohesion: 0.04
Nodes (48): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+40 more)

### Community 33 - "Community 33"
Cohesion: 0.04
Nodes (72): createAuth(), hashPassword(), ImportErrorCode, ImportFound, importJob, importOriginal, ImportOutcome, recordingEvent (+64 more)

### Community 34 - "Community 34"
Cohesion: 0.03
Nodes (80): JOINT_FILL_FIELDS, SUGGESTION_STATUSES, DiveProfileView, DiveProfileViewSchema, ADR-0041, Change(), Changes(), historyRoute (+72 more)

### Community 35 - "Community 35"
Cohesion: 0.04
Nodes (84): WEIGHTING_FELT, AiAccessScope, seesPositions(), VerifiedAccess, seriesOf(), checkedByRunTool, createMcpServer(), INSTRUCTIONS (+76 more)

### Community 36 - "Community 36"
Cohesion: 0.04
Nodes (69): errorMessage(), placeFile(), placeFiles(), flag(), idText(), isObject(), numberOrNull(), readSsiSite() (+61 more)

### Community 37 - "Community 37"
Cohesion: 0.03
Nodes (55): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, ParticipantRole, ActionContext, Direction (+47 more)

### Community 38 - "Community 38"
Cohesion: 0.06
Nodes (78): allButDive(), continuationCandidateQuery(), deletedDivesOn(), deletedDivesQuery(), diveQuery(), DiveView, mergeCandidatesQuery(), nearbyDivesQuery() (+70 more)

### Community 39 - "Community 39"
Cohesion: 0.04
Nodes (20): ApiClientDiagnosticsStatusGet200Response, ApiDiverBodyWeightsGet200ResponseItem, ApiMeGet200ResponseOwnDiver, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseResidualN2, ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers (+12 more)

### Community 40 - "Community 40"
Cohesion: 0.07
Nodes (35): Connected, Hint, HintFrame, LiveEvent, LiveHint, LiveHints, SseLiveHints, LiveHintsTest (+27 more)

### Community 41 - "Community 41"
Cohesion: 0.05
Nodes (26): InkButton(), QuietButton(), rememberReducedMotion(), rememberSkeletonAlpha(), SkeletonBar(), AvatarCircle(), Error(), FilterAction() (+18 more)

### Community 42 - "Community 42"
Cohesion: 0.08
Nodes (71): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, assessmentRoutes() (+63 more)

### Community 43 - "Community 43"
Cohesion: 0.04
Nodes (64): accountListEntry, diver, diverBodyWeight, diverExternalId, tidyName(), ACCOUNT_PATTERN, DETAIL_FIELDS, DETAIL_PATTERN (+56 more)

### Community 44 - "Community 44"
Cohesion: 0.06
Nodes (67): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+59 more)

### Community 45 - "Community 45"
Cohesion: 0.04
Nodes (67): downsampleMinMax(), BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071 (+59 more)

### Community 46 - "Community 46"
Cohesion: 0.05
Nodes (28): LogbookUiState, LogbookViewModel, DiveListView, DiveRepository, neighboursPath(), DecodeException, DiveHubException, NoAnswerException (+20 more)

### Community 47 - "Community 47"
Cohesion: 0.05
Nodes (59): sessionIdOf(), AttemptLimit, limitAttempts(), Problem, ResponseState, whenResponseGone(), DateTime, StoredBlob (+51 more)

### Community 48 - "Community 48"
Cohesion: 0.06
Nodes (60): NoFly, Queryable, getDiverLoadings, BottomTimeQuery, DEFAULT_GF_HIGH, diverLogbook(), planBottomTime(), residualSeedOf() (+52 more)

### Community 49 - "Community 49"
Cohesion: 0.05
Nodes (33): cylinderLine(), Facts(), Water(), DiveDetailText, diveLocalTime(), offsetId(), timeZoneNote(), TimeZoneOrigin (+25 more)

### Community 50 - "Community 50"
Cohesion: 0.06
Nodes (16): logbookDiveItemIndex(), DiveListViewDivesItem, DiveListViewDivesItemGasesItem, DiveListViewDivesItemParticipantsItem, DiveListViewDivesItemSite, DiveListViewMonthsItem, DiveProfileView, DiveViewValues (+8 more)

### Community 51 - "Community 51"
Cohesion: 0.05
Nodes (8): DiveStore, toBoolean(), toLong(), toModel(), BodyWeight, DiveSite, Recording, RecordingSeries

### Community 52 - "Community 52"
Cohesion: 0.04
Nodes (58): adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, Auth, AuthOptions, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, ADR-0011 (+50 more)

### Community 53 - "Community 53"
Cohesion: 0.06
Nodes (62): ANDROID_UI, AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch() (+54 more)

### Community 54 - "Community 54"
Cohesion: 0.07
Nodes (64): Answer, answer(), baseFrom(), BodyWeight, Confidence, high, low, medium (+56 more)

### Community 55 - "Community 55"
Cohesion: 0.05
Nodes (7): logShareChooser(), DiveHubApplicationTest, LauncherIconTest, SplashTheme, SplashThemeTest, LogShareTest, UpdateRequiredScreenTest

### Community 56 - "Community 56"
Cohesion: 0.07
Nodes (13): LeadAnswer, LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseBasis, ApiDiversIdLeadEstimateGet200ResponseBasisDivesItem (+5 more)

### Community 57 - "Community 57"
Cohesion: 0.09
Nodes (47): BottomTime, BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen, BottomTimeLimits (+39 more)

### Community 58 - "Community 58"
Cohesion: 0.04
Nodes (55): AssessmentService, AssessmentRouteDeps, BACKUP_RUN_TASK, backupTask(), ADR-0069, pushRunItem, PushRunLeftOut, asksFor() (+47 more)

### Community 59 - "Community 59"
Cohesion: 0.12
Nodes (24): Body, BrokenBody, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage(), diverJson() (+16 more)

### Community 60 - "Community 60"
Cohesion: 0.04
Nodes (58): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+50 more)

### Community 61 - "Community 61"
Cohesion: 0.05
Nodes (52): PARTICIPANT_ROLES, ADR-0053, ADR-0056, WaitingPushViewSchema, PROBLEM_CODES, ProblemSchema, storedProblemCode(), ADR-0014 (+44 more)

### Community 62 - "Community 62"
Cohesion: 0.06
Nodes (51): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, answer(), baseFrom(), cylindersAtReserve(), CylinderTerm (+43 more)

### Community 63 - "Community 63"
Cohesion: 0.06
Nodes (36): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+28 more)

### Community 64 - "Community 64"
Cohesion: 0.07
Nodes (46): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+38 more)

### Community 65 - "Community 65"
Cohesion: 0.08
Nodes (7): DiveHubTopBar(), titleLineHeight(), TitleSkeleton(), DiveHubTheme(), SectionTest, DiveHubTopBarTest, BottomBarScreenshotTest

### Community 67 - "Community 67"
Cohesion: 0.07
Nodes (48): Leave, asBool(), DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+40 more)

### Community 68 - "Community 68"
Cohesion: 0.10
Nodes (10): SyncCheckpoint, Complete, SessionEnded, Stopped, SyncEngine, SyncPhase, Details, Meta (+2 more)

### Community 69 - "Community 69"
Cohesion: 0.12
Nodes (3): DiveRoute, AppNavHostTest, FakeScreens

### Community 71 - "Community 71"
Cohesion: 0.09
Nodes (6): SettingsRepository, SyncState, InstanceUrlStore, FakeInstances, SettingsRepositoryTest, DiveStoreTest

### Community 72 - "Community 72"
Cohesion: 0.06
Nodes (44): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+36 more)

### Community 73 - "Community 73"
Cohesion: 0.07
Nodes (46): surfacePressureBar(), AIR, cnsDecayed(), DEFAULT_CONDITIONS, N2_REPLAY_WINDOW_HOURS, N2_STALE_AFTER_HOURS, N2Loadings, N2LoadingsBase (+38 more)

### Community 74 - "Community 74"
Cohesion: 0.07
Nodes (45): lead, LeadPlacement, ProviderImportPlan, SuitType, SuitUndergarment, WeightingFelt, ADR-0025, WaterMismatch (+37 more)

### Community 75 - "Community 75"
Cohesion: 0.08
Nodes (46): LoadingsView, pushRunActive(), pushRunBody(), PushRunChoices, PushRunPreview, pushRunPreviewQuery(), pushRunQuery(), pushRunsQuery() (+38 more)

### Community 76 - "Community 76"
Cohesion: 0.06
Nodes (42): CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite(), lock() (+34 more)

### Community 77 - "Community 77"
Cohesion: 0.06
Nodes (42): Blocked, classify(), FailureOutcome, failureOutcome(), kindOf(), KINDS, LEFT_OUT_KEY, LEFT_OUT_KEYS (+34 more)

### Community 78 - "Community 78"
Cohesion: 0.08
Nodes (21): AppNavHost(), AppScreens, clearSavedTabStates(), isShowing(), leaveDiveForLogbook(), MainShell(), navigateToTab(), tabOf() (+13 more)

### Community 79 - "Community 79"
Cohesion: 0.05
Nodes (40): CYLINDER_MATERIALS, SUIT_TYPES, SUIT_UNDERGARMENTS, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053 (+32 more)

### Community 80 - "Community 80"
Cohesion: 0.05
Nodes (44): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+36 more)

### Community 81 - "Community 81"
Cohesion: 0.09
Nodes (23): ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseCns, ApiDiversIdLoadingsGet200ResponseN2, ApiDiversIdLoadingsGet200ResponseNoFly, ApiDiversIdLoadingsGet200ResponseNoFlySource, ApiDiversIdLoadingsGet200ResponseOtu, cnsDecayed() (+15 more)

### Community 82 - "Community 82"
Cohesion: 0.06
Nodes (39): replyCertificationError, STATUS, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError, CertificationExpiry, CertificationInput, CertificationView (+31 more)

### Community 83 - "Community 83"
Cohesion: 0.11
Nodes (8): FakeCache, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore, UserScopedStore

### Community 84 - "Community 84"
Cohesion: 0.07
Nodes (37): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+29 more)

### Community 85 - "Community 85"
Cohesion: 0.10
Nodes (14): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), Answers (+6 more)

### Community 86 - "Community 86"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 87 - "Community 87"
Cohesion: 0.06
Nodes (35): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+27 more)

### Community 88 - "Community 88"
Cohesion: 0.06
Nodes (39): ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, connectGarmin(), Connection, DatedDive (+31 more)

### Community 89 - "Community 89"
Cohesion: 0.07
Nodes (5): AppContainer, KeystoreTokenStore, PreferencesInstanceUrlStore, UserCacheStore, AndroidDiveStore

### Community 91 - "Community 91"
Cohesion: 0.07
Nodes (6): DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewLeadItem, DiveViewSac, DiveViewSuit, DiveViewWeightingFeedback

### Community 92 - "Community 92"
Cohesion: 0.08
Nodes (37): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+29 more)

### Community 93 - "Community 93"
Cohesion: 0.13
Nodes (13): bottomTime(), otu(), oxygenLimitMinutes(), planningSac(), PlanningSacDive, PlanningSacResult, PlanningSacSource, default (+5 more)

### Community 94 - "Community 94"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 95 - "Community 95"
Cohesion: 0.12
Nodes (36): DEFAULT_SSI_URL, acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive() (+28 more)

### Community 96 - "Community 96"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 97 - "Community 97"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 98 - "Community 98"
Cohesion: 0.06
Nodes (30): accountRelations, apikey, rateLimit, sessionRelations, ADR-0011, ADR-0012, ADR-0035, twoFactor (+22 more)

### Community 99 - "Community 99"
Cohesion: 0.07
Nodes (35): EquipmentService, RecordInput, ScheduleInput, DiveGearItemViewSchema, DiveGearViewSchema, DueState, EquipmentDetailViewSchema, EquipmentDueViewSchema (+27 more)

### Community 100 - "Community 100"
Cohesion: 0.08
Nodes (35): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+27 more)

### Community 101 - "Community 101"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 103 - "Community 103"
Cohesion: 0.08
Nodes (32): AI_ACCESS_NAME_MAX, aiAccessLog, aiAccessSetting, Access, AccessView, AiAccessRouteDeps, LogEntryView, Scope (+24 more)

### Community 104 - "Community 104"
Cohesion: 0.09
Nodes (26): createFileFormats(), FileFormat, looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf() (+18 more)

### Community 105 - "Community 105"
Cohesion: 0.07
Nodes (28): headers, ADR-0030, ADR-0040, conflictForLena(), diveWithoutRecording(), editInSsi(), externalDiver(), importLena() (+20 more)

### Community 106 - "Community 106"
Cohesion: 0.11
Nodes (28): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0051 (+20 more)

### Community 108 - "Community 108"
Cohesion: 0.10
Nodes (29): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+21 more)

### Community 109 - "Community 109"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 110 - "Community 110"
Cohesion: 0.11
Nodes (12): DiveHubScreens, ErrorText(), InstanceForm(), InstanceStatus(), PasswordField(), PrimaryButton(), SignInForm(), Spinner() (+4 more)

### Community 111 - "Community 111"
Cohesion: 0.09
Nodes (25): dayNumber(), enteredDecoFromNdl(), minNdlSeconds(), NoFly, noFlyAfter(), NoFlyReason, decompression, several (+17 more)

### Community 112 - "Community 112"
Cohesion: 0.25
Nodes (8): leadAnswer(), EstimateCylinder, PlannedConditions, Suit, assemblePersonalizedSources(), suitOf(), leadEstimate(), PersonalizedToolsTest

### Community 113 - "Community 113"
Cohesion: 0.09
Nodes (8): ApiDiveSitesGet200Response, SiteView, SiteViewExternalIdsItem, SiteViewExternalIdsItemAttribution, SiteViewExternalIdsItemOffered, SiteViewExternalIdsItemOfferedPosition, SiteViewExternalIdsItemPrivateTo, SiteViewPosition

### Community 114 - "Community 114"
Cohesion: 0.10
Nodes (8): LogbookPaging, sketchScale(), json(), LogbookFormatTest, LogbookPagingTest, LogbookRepositoryTest, RepositoryHarness, SketchTest

### Community 115 - "Community 115"
Cohesion: 0.12
Nodes (26): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+18 more)

### Community 116 - "Community 116"
Cohesion: 0.10
Nodes (22): SsiClient, buildCreateRecord(), buildDeleteRecord(), emptyRecord(), writableRecord(), ClearChecks, clearDive(), findIn() (+14 more)

### Community 117 - "Community 117"
Cohesion: 0.09
Nodes (29): applyHints(), LiveArrivals, Page, useLiveArrivals(), bump(), changedListeners, ChannelMessage, healthListeners (+21 more)

### Community 118 - "Community 118"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 120 - "Community 120"
Cohesion: 0.09
Nodes (27): EVIDENCE, Evidence, FindingValues, LIMITS, NOT_COVERED_DIVE_MODES, RULE_IDS, RuleId, RULES (+19 more)

### Community 121 - "Community 121"
Cohesion: 0.10
Nodes (24): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearPushRuns(), clearQueue() (+16 more)

### Community 122 - "Community 122"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 123 - "Community 123"
Cohesion: 0.12
Nodes (24): captureDiagnostic(), captureError(), cleanContext(), clearDiagnostics(), CLIENT_NAME, CLIENT_VERSION, DiagnosticEntry, diagnosticFromError() (+16 more)

### Community 124 - "Community 124"
Cohesion: 0.10
Nodes (24): AL80, asEstimateInput(), EstimateDiveCylinder, evaluateLeadVectorCase(), LEAD_CASES, LEAD_VECTOR_KERNEL, leadInput(), leadVectorFile() (+16 more)

### Community 125 - "Community 125"
Cohesion: 0.11
Nodes (23): comparableOf(), SsiRecord, contextOf(), FakeSsi, BuddySaved, access(), client, out (+15 more)

### Community 126 - "Community 126"
Cohesion: 0.12
Nodes (11): Radius, drawBand(), ProfileSketch(), AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint (+3 more)

### Community 129 - "Community 129"
Cohesion: 0.11
Nodes (7): ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh, ApiToolsBottomTimeGet200ResponseUsedSacLpm, ApiToolsBottomTimeGet200ResponseUsedWater

### Community 130 - "Community 130"
Cohesion: 0.11
Nodes (7): ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport

### Community 131 - "Community 131"
Cohesion: 0.10
Nodes (22): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+14 more)

### Community 132 - "Community 132"
Cohesion: 0.10
Nodes (18): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, APP (+10 more)

### Community 133 - "Community 133"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 134 - "Community 134"
Cohesion: 0.21
Nodes (4): GasPlanScreenshotTest, GasPlanToolDiver, GasPlanToolInput, GasPlanToolLevel

### Community 135 - "Community 135"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 136 - "Community 136"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 137 - "Community 137"
Cohesion: 0.09
Nodes (4): DiveHubApplication, SyncWorker, WorkManagerPeriodicSync, PeriodicSyncScheduler

### Community 139 - "Community 139"
Cohesion: 0.13
Nodes (3): FakeInstances, FakeTokens, MainViewModelTest

### Community 140 - "Community 140"
Cohesion: 0.12
Nodes (23): DiveGear, ensureItemForDevice(), GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow, ItemView (+15 more)

### Community 141 - "Community 141"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 142 - "Community 142"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 143 - "Community 143"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 144 - "Community 144"
Cohesion: 0.13
Nodes (6): DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, WaitingPushView

### Community 145 - "Community 145"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 146 - "Community 146"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 147 - "Community 147"
Cohesion: 0.15
Nodes (18): qr@0.7.2, cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043 (+10 more)

### Community 149 - "Community 149"
Cohesion: 0.09
Nodes (21): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource/poppins@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+13 more)

### Community 150 - "Community 150"
Cohesion: 0.12
Nodes (19): CENTRE_SOURCE_NAME, CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+11 more)

### Community 151 - "Community 151"
Cohesion: 0.16
Nodes (21): managementOf(), EquipmentError, checkCost(), checkDates(), checkWindow(), createEquipmentService(), assemble(), checkResets() (+13 more)

### Community 152 - "Community 152"
Cohesion: 0.20
Nodes (21): DiveOrigin, REFERENCE_PREFIX, Series, aboveZero(), cylinderOf(), deviceOf(), deviceOfRef(), MATERIAL_OF_TANK_TYPE (+13 more)

### Community 153 - "Community 153"
Cohesion: 0.17
Nodes (13): LeadInputTest, SuitType, drysuit, none, semidry, skin, wetsuit, SuitUndergarment (+5 more)

### Community 154 - "Community 154"
Cohesion: 0.17
Nodes (16): ApiToolsBottomTimeGet200ResponseLimits, jsRound(), powInt(), roundTo(), Sac, SacCylinder, SacDive, SacMissing (+8 more)

### Community 156 - "Community 156"
Cohesion: 0.14
Nodes (18): geo-tz@8.1.9, inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), placeLocalTime(), Position, ADR-0030 (+10 more)

### Community 157 - "Community 157"
Cohesion: 0.15
Nodes (18): RecordingSummary, UtcOffsetSource, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput (+10 more)

### Community 158 - "Community 158"
Cohesion: 0.26
Nodes (17): ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres(), isHypoxic(), metresPerBar() (+9 more)

### Community 159 - "Community 159"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 160 - "Community 160"
Cohesion: 0.15
Nodes (5): ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits

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
Cohesion: 0.24
Nodes (8): decode(), ENTITIES, localName(), Open, Reader, ADR-0003, XmlElement, XmlError

### Community 165 - "Community 165"
Cohesion: 0.13
Nodes (17): Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), HINT, Input, LoadingsInput (+9 more)

### Community 166 - "Community 166"
Cohesion: 0.14
Nodes (13): BufferedLog, LOG_BUFFER_CAPACITY_BYTES, LOG_BUFFER_CAPACITY_ENTRIES, LogBufferStats, LogRingBufferOptions, SupportBundle, ClientDiagnosticsProvider, FailedBackup (+5 more)

### Community 167 - "Community 167"
Cohesion: 0.12
Nodes (10): react-dom@^19.3.0, isUnauthorized(), initI18n(), resources, ADR-0014, LocalizedApp(), onError(), queryClient (+2 more)

### Community 168 - "Community 168"
Cohesion: 0.18
Nodes (3): FakeInstances, FakeTokens, SyncAuthTest

### Community 169 - "Community 169"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 171 - "Community 171"
Cohesion: 0.17
Nodes (12): sampleCount(), storedList(), tryParseJson(), APP_PARAMS, createSsiTransport(), exchange(), isObject(), SSI_APP_USER_AGENT (+4 more)

### Community 172 - "Community 172"
Cohesion: 0.17
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 174 - "Community 174"
Cohesion: 0.19
Nodes (4): DiveDetailsListView, DiveDetailsListViewCounts, DiveDetailsListViewMonthsItem, DiveDetailsListViewTotals

### Community 175 - "Community 175"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 176 - "Community 176"
Cohesion: 0.12
Nodes (13): clientDiagnostic, clientDiagnosticsSettings, ClientDiagnosticsEntryInput, ClientDiagnosticsEntryView, ClientDiagnosticsError, ClientDiagnosticsUpload, KEEP_NEWEST, MAX_UPLOAD_ENTRIES (+5 more)

### Community 177 - "Community 177"
Cohesion: 0.21
Nodes (11): buildSupportBundle(), buildTarGz(), json(), ndjson(), octal(), SUPPORT_BUNDLE_FORMAT, SUPPORT_BUNDLE_FORMAT_VERSION, SupportBufferManifest (+3 more)

### Community 178 - "Community 178"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 179 - "Community 179"
Cohesion: 0.13
Nodes (7): buddySite(), Dive, OTHER, PendingOffer, ADR-0012, ADR-0070, uniqueWord()

### Community 180 - "Community 180"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 181 - "Community 181"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 183 - "Community 183"
Cohesion: 0.16
Nodes (14): serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, DueEntry, ScheduleView, equipmentList(), EquipmentListFilter (+6 more)

### Community 184 - "Community 184"
Cohesion: 0.13
Nodes (6): FakeBroadcastChannel, FakeEventSource, Listener, locks, start(), ADR-0066

### Community 185 - "Community 185"
Cohesion: 0.13
Nodes (14): concurrency, cancel-in-progress, group, env, RELEASE_TAG, jobs, publish, name (+6 more)

### Community 186 - "Community 186"
Cohesion: 0.20
Nodes (10): DIVE_KINDS, ImportView, ImportViewSchema, toImportView(), ADR-0030, ImportRouteDeps, ADR-0044, ADR-0056 (+2 more)

### Community 187 - "Community 187"
Cohesion: 0.20
Nodes (5): LogRingBuffer, RedactionCounters, SupportBundleInput, SupportBundleManifest, SupportServiceDeps

### Community 188 - "Community 188"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 189 - "Community 189"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 190 - "Community 190"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 192 - "Community 192"
Cohesion: 0.26
Nodes (3): DiverView, DiverViewCertifications, DiverViewCertificationsNewest

### Community 193 - "Community 193"
Cohesion: 0.26
Nodes (3): DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 194 - "Community 194"
Cohesion: 0.26
Nodes (3): NeighboursView, NeighboursViewNext, NeighboursViewPrevious

### Community 195 - "Community 195"
Cohesion: 0.17
Nodes (3): DiagnosticStore, BrokenStore, MemoryStore

### Community 196 - "Community 196"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 198 - "Community 198"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 199 - "Community 199"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 200 - "Community 200"
Cohesion: 0.20
Nodes (10): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, ADR-0076, MODULE_IMPORTS, moduleRules (+2 more)

### Community 201 - "Community 201"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 202 - "Community 202"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 204 - "Community 204"
Cohesion: 0.33
Nodes (8): prepareEntry(), DENIED_KEY_PARTS, isDeniedKey(), maskString(), redactInner(), redactLogObject(), RedactOptions, redactValue()

### Community 205 - "Community 205"
Cohesion: 0.36
Nodes (9): describe(), isObject(), keyDifferences(), pause(), recording(), runAnswerChecks(), sampleCount(), testDive() (+1 more)

### Community 206 - "Community 206"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 207 - "Community 207"
Cohesion: 0.20
Nodes (10): verify, prerelease, sha, tag, versionCode, versionName, name, outputs (+2 more)

### Community 208 - "Community 208"
Cohesion: 0.20
Nodes (10): with, cache, distribution, fetch-depth, if-no-files-found, java-version, name, path (+2 more)

### Community 209 - "Community 209"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 211 - "Community 211"
Cohesion: 0.28
Nodes (8): itemIds(), ADR-0026, ADR-0034, Usage, UsageDive, usageMap(), usageOf(), UsageRow

### Community 212 - "Community 212"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 213 - "Community 213"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 214 - "Community 214"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 215 - "Community 215"
Cohesion: 0.43
Nodes (8): emptyRedactionCounters(), createSupportService(), assemble(), clientDiagnostics(), configSection(), failedJobs(), healthSection(), maskDatabaseUrl()

### Community 216 - "Community 216"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 217 - "Community 217"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 219 - "Community 219"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 220 - "Community 220"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 221 - "Community 221"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 222 - "Community 222"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 223 - "Community 223"
Cohesion: 0.40
Nodes (5): DivePhase, Dive, Error, Loading, Offline

### Community 224 - "Community 224"
Cohesion: 0.40
Nodes (5): LogbookPhase, Error, List, Loading, Offline

### Community 225 - "Community 225"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build

### Community 226 - "Community 226"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 227 - "Community 227"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 228 - "Community 228"
Cohesion: 0.40
Nodes (4): TAGS, ADR-0023, { stdout, stderr }, total

### Community 233 - "Community 233"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 234 - "Community 234"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 235 - "Community 235"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1316 isolated node(s):** `Reachable`, `Unknown`, `Sent`, `Reachable`, `Reserve` (+1311 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3751 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **147 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 0` to `Community 3`, `Community 4`, `Community 6`, `Community 9`, `Community 10`, `Community 140`, `Community 13`, `Community 14`, `Community 15`, `Community 20`, `Community 22`, `Community 23`, `Community 24`, `Community 25`, `Community 156`, `Community 29`, `Community 30`, `Community 33`, `Community 35`, `Community 166`, `Community 42`, `Community 43`, `Community 48`, `Community 176`, `Community 183`, `Community 58`, `Community 60`, `Community 61`, `Community 74`, `Community 76`, `Community 82`, `Community 211`, `Community 98`, `Community 100`, `Community 103`, `Community 108`?**
  _High betweenness centrality (0.025) - this node is a cross-community bridge._
- **What connects `Reachable`, `Unknown`, `Sent` to the rest of the system?**
  _1316 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.01867842773135252 - nodes in this community are weakly interconnected._
- **Why does `typescript` connect `Community 169` to `Community 122`, `Community 60`, `Community 159`?**
  _High betweenness centrality (0.010) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.02017999888199452 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 5` to `Community 1`, `Community 2`, `Community 38`, `Community 7`, `Community 8`, `Community 167`, `Community 75`, `Community 12`, `Community 44`, `Community 17`, `Community 149`, `Community 117`, `Community 184`, `Community 122`, `Community 31`?**
  _High betweenness centrality (0.010) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.03535250921545294 - nodes in this community are weakly interconnected._