# Graph Report - dive_hub  (2026-11-25)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 12909 nodes · 40224 edges · 412 communities (242 shown, 170 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 967 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `d697e27f`
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
- Community 246
- Community 247
- Community 248
- Community 249
- Community 250
- Community 251
- Community 252
- Community 253
- Community 254
- Community 255
- Community 256
- Community 257
- Community 258
- Community 259
- Community 261
- Community 262
- Community 263
- Community 264
- Community 265
- Community 272

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 211 edges
2. `Button()` - 204 edges
3. `useErrorText()` - 176 edges
4. `useDisplay()` - 165 edges
5. `drizzle-orm` - 150 edges
6. `unwrap()` - 143 edges
7. `DiveStore` - 139 edges
8. `Muted()` - 139 edges
9. `createTestDatabase()` - 112 edges
10. `TestDatabase` - 111 edges

## Surprising Connections (you probably didn't know these)
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `askedAt()` --calls--> `DiveError`  [EXTRACTED]
  apps/server/src/dives/continuation/append-service.ts → apps/server/src/dives/dive-service.ts
- `Placement` --references--> `Actor`  [EXTRACTED]
  apps/server/src/imports/placement.ts → apps/server/src/revisions/revisions.ts

## Import Cycles
- None detected.

## Communities (412 total, 170 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (246): Queryable, Tx, dive, diverBodyWeight, diverManagement, LeadPlacement, OVERRIDABLE_FIELDS, OverridableField (+238 more)

### Community 1 - "Community 1"
Cohesion: 0.02
Nodes (203): ConnectionRow, diveFingerprint, providerGoneDive, providerLease, push, PushLeftOut, WaitingPushOptions, waitingOf() (+195 more)

### Community 2 - "Community 2"
Cohesion: 0.04
Nodes (193): ChangePassword(), DisplaySettings(), SecuritySettings(), Sessions(), ADR-0080, useDescribeAgent(), AdminSettings(), ClientDiagnosticsSetting() (+185 more)

### Community 3 - "Community 3"
Cohesion: 0.03
Nodes (127): user, changeEvent, diveSiteExternalId, revision, ADR-0044, ADR-0013, ADR-0011, dumpToolAvailable() (+119 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (179): cylinderCatalogueQuery(), CylinderFields(), MATERIALS, ADR-0034, ADR-0045, useCatalogueName(), useCylinderProblem(), DiveEditForm() (+171 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (98): Section(), cylinderLine(), cylinderPressures(), Cylinders(), Direction, Back, Left, Right (+90 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (145): label(), Me, AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery() (+137 more)

### Community 7 - "Community 7"
Cohesion: 0.02
Nodes (148): jointDiveDismissal, JointFillField, logbookCheckAnswer, logbookCheckDiveAnswer, ParticipantRole, agreeInDepthAndDuration(), CheckedDive, DeletableDive (+140 more)

### Community 8 - "Community 8"
Cohesion: 0.02
Nodes (151): downsampleMinMax(), Nullable(), AiAccessScope, seesPositions(), ProfileSummary, round1(), Series, summariseProfile() (+143 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (37): DiveRow(), Figures(), RowFacts(), EmptyState(), FactRow(), NoteText(), StackedNoteText(), Radius (+29 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (95): DangerButton(), InkButton(), QuietButton(), SecondaryButton(), CylinderFields(), CylinderProblemText(), CylindersFields(), FieldPair() (+87 more)

### Community 11 - "Community 11"
Cohesion: 0.02
Nodes (140): actorType, aiAccessOutcome, backupRunKind, backupRunStatus, backupSettings, candidateResolution, centreSource, certificationImageState (+132 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (37): ScanErrors, ScanUiState, ScanViewModel, ScanViewModelTest, ApiDiveCentresPostRequest, ApiDiveCentresPostRequestExternalIdsItem, ApiVerificationCodesReadPost200Response, ApiVerificationCodesReadPost200ResponseCandidatesItem (+29 more)

### Community 13 - "Community 13"
Cohesion: 0.03
Nodes (5): SectionTest, LoadingScreenshotTest, UpdateRequiredScreenTest, DiveViewFromRecording, DiveViewFromRecordingStartsAt

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (132): replyCertificationError, STATUS, CertificationService, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams (+124 more)

### Community 15 - "Community 15"
Cohesion: 0.03
Nodes (128): BACKUP_EXTENSION, BACKUP_FORMAT_VERSION, BACKUP_SKIPPED_DIRS, BackupArchiveError, BackupDecryptionKey, BackupEncryption, BackupEncryptionMetadata, BackupManifest (+120 more)

### Community 16 - "Community 16"
Cohesion: 0.03
Nodes (139): diverSearchQuery(), importQuery(), keys, buddiesForQuery(), buddiesQuery(), BuddyView, ConflictFieldView, ConnectionView (+131 more)

### Community 17 - "Community 17"
Cohesion: 0.03
Nodes (113): createServer(), createAuth(), auth, hashPassword(), loadAuthSecret(), BackupServiceDeps, createServices(), ServiceParts (+105 more)

### Community 18 - "Community 18"
Cohesion: 0.02
Nodes (122): CentreService, CentreRouteDeps, LEAD_PLACEMENTS, UTC_OFFSET_SOURCES, WEIGHTING_FELT, DiverService, CertificationHint, CertificationHintExpiry (+114 more)

### Community 19 - "Community 19"
Cohesion: 0.03
Nodes (22): EditSource, LeadEstimateViewModelTest, DiveStore, toBoolean(), toLong(), toModel(), BodyWeight, Cylinder (+14 more)

### Community 20 - "Community 20"
Cohesion: 0.04
Nodes (120): ImportedValues, diverExternalId, diveSite, siteImport, SiteImportFinding, SiteImportProgress, ProviderCylinder, providerCylinderOf() (+112 more)

### Community 21 - "Community 21"
Cohesion: 0.06
Nodes (16): DiveDetailScreenshotTest, DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewLeadItem, DiveViewParticipantsItem, DiveViewPosition, DiveViewSac, DiveViewSite (+8 more)

### Community 22 - "Community 22"
Cohesion: 0.05
Nodes (77): Formats, SyncLine(), formatSyncTime(), GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard() (+69 more)

### Community 23 - "Community 23"
Cohesion: 0.04
Nodes (114): divesQuery(), LogbookPage, PAGE_SIZE, ExternalIdView, siteQuery(), sitesQuery(), Buddies(), COUNT_OF (+106 more)

### Community 24 - "Community 24"
Cohesion: 0.04
Nodes (120): ComputerChoice, DiveImportMode, DiveKind, ProviderImportPlan, providerSiteData, UtcOffsetSource, mergeProviderCylinder(), siteRef() (+112 more)

### Community 25 - "Community 25"
Cohesion: 0.03
Nodes (101): EMPTY_SITE_IMPORT_COUNTS, ImportArea, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, errorMessage() (+93 more)

### Community 26 - "Community 26"
Cohesion: 0.03
Nodes (99): ClientDiagnosticsSettingsView, clientDiagnosticsStatusQuery(), ClientDiagnosticsStatusView, ADR-0080, CertificationView, DeviceView, DiverDetailsView, ExternalDiverView (+91 more)

### Community 27 - "Community 27"
Cohesion: 0.03
Nodes (89): createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities(), providerError() (+81 more)

### Community 28 - "Community 28"
Cohesion: 0.07
Nodes (20): FakeCache, ErrorCodes, Dive, PendingWriteStatus, Awaiting, Conflicted, Failed, Pending (+12 more)

### Community 29 - "Community 29"
Cohesion: 0.04
Nodes (110): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, assessmentRoutes() (+102 more)

### Community 30 - "Community 30"
Cohesion: 0.03
Nodes (102): Position, running(), SiteImportArea, siteImportsQuery(), SiteImportView, siteRevisionsQuery(), SiteRevisionView, SiteView (+94 more)

### Community 31 - "Community 31"
Cohesion: 0.04
Nodes (83): ReportProblemSettings(), equipmentDueQuery(), cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL (+75 more)

### Community 32 - "Community 32"
Cohesion: 0.04
Nodes (24): DiveEditViewModel, LoadingsUiState, LoadingsViewModel, RevisionFollowTest, ApiMeGet200Response, ApiMeGet200ResponsePreferences, UserView, DiverSearch (+16 more)

### Community 33 - "Community 33"
Cohesion: 0.03
Nodes (92): buddySuggestion, diveFinding, jointDive, createFitAdapter(), Assessment, FindingView, Row, ADR-0036 (+84 more)

### Community 34 - "Community 34"
Cohesion: 0.04
Nodes (87): AssessmentError, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), requireManagedDive() (+79 more)

### Community 35 - "Community 35"
Cohesion: 0.06
Nodes (33): DiveHubException, LiveHint, LiveHints, SyncRefresher, Body, FeedEntry, Hold, Offline (+25 more)

### Community 36 - "Community 36"
Cohesion: 0.03
Nodes (17): AppContainer, KeystoreTokenStore, PreferencesCachedMeStore, PreferencesInstanceUrlStore, UserCacheStore, AndroidDiveStore, SyncWorker, WorkManagerPeriodicSync (+9 more)

### Community 37 - "Community 37"
Cohesion: 0.10
Nodes (3): DiveEditScreenshotTest, pressureSeriesOptions(), DiveEditDraftTest

### Community 38 - "Community 38"
Cohesion: 0.04
Nodes (89): device, equipmentItem, equipmentUse, serviceRecord, serviceRecordSchedule, serviceSchedule, managementOf(), ADR-0025 (+81 more)

### Community 39 - "Community 39"
Cohesion: 0.03
Nodes (61): before(), ADR-0044, E2eDatabase, migrations, ADR-0023, adapter, crossCheck(), decodeOfficial() (+53 more)

### Community 40 - "Community 40"
Cohesion: 0.04
Nodes (81): adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, AuthOptions, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, SessionUser, ADR-0011 (+73 more)

### Community 41 - "Community 41"
Cohesion: 0.04
Nodes (82): DiveValues, OverridableField, revisionsQuery(), RevisionView, UtcOffsetSource, ConflictChoices, ConflictEntryView, ConflictsOfConnection (+74 more)

### Community 42 - "Community 42"
Cohesion: 0.04
Nodes (48): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+40 more)

### Community 43 - "Community 43"
Cohesion: 0.04
Nodes (17): LogbookViewModel, ApiClient, ApiErrorBody, DiveRepository, DecodeException, NoAnswerException, decodeList(), LogbookRepository (+9 more)

### Community 44 - "Community 44"
Cohesion: 0.05
Nodes (81): importsQuery(), ImportView, pushRunActive(), pushRunBody(), PushRunChoices, PushRunPreview, pushRunPreviewQuery(), pushRunQuery() (+73 more)

### Community 45 - "Community 45"
Cohesion: 0.06
Nodes (78): allButDive(), continuationCandidateQuery(), deletedDivesOn(), deletedDivesQuery(), diveQuery(), DiveView, mergeCandidatesQuery(), nearbyDivesQuery() (+70 more)

### Community 46 - "Community 46"
Cohesion: 0.04
Nodes (16): followStoreWrites(), SettingsDiagnosticsState, SettingsQueueState, SettingsUiState, SettingsViewModel, FakeDiagnostics, FakeInstances, SettingsViewModelTest (+8 more)

### Community 47 - "Community 47"
Cohesion: 0.03
Nodes (62): connection, diveLease, providerConflict, pushRun, waitingPush, kept(), PURGE_AFTER_DAYS, LEASE_MS (+54 more)

### Community 48 - "Community 48"
Cohesion: 0.07
Nodes (62): BottomTime, BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen, BottomTimeLimits (+54 more)

### Community 49 - "Community 49"
Cohesion: 0.05
Nodes (62): SsiClient, SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), buildUpdateRecord(), DiveForSsi, emptyRecord() (+54 more)

### Community 50 - "Community 50"
Cohesion: 0.07
Nodes (66): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+58 more)

### Community 51 - "Community 51"
Cohesion: 0.05
Nodes (26): rememberReducedMotion(), rememberSkeletonAlpha(), SkeletonBar(), AvatarCircle(), Error(), FilterAction(), Filters(), FilterSheet() (+18 more)

### Community 52 - "Community 52"
Cohesion: 0.05
Nodes (21): DiveDetailViewModel, NeighboursRead, ProfileRead, DiveHubApp(), DiveHubScreens, ErrorText(), Gate(), InstanceForm() (+13 more)

### Community 53 - "Community 53"
Cohesion: 0.05
Nodes (59): lead, DIVE_FIELDS, createSsiAdapter(), SSI_CAPABILITIES, createSsiClient(), Fetch, Card, DEEP (+51 more)

### Community 54 - "Community 54"
Cohesion: 0.04
Nodes (65): pushRunItem, PushRunLeftOut, managedDiverIds(), asksFor(), DIVE_ONLY, DIVE_ONLY_PATTERN, DiveListFilter, diveListFilter() (+57 more)

### Community 55 - "Community 55"
Cohesion: 0.04
Nodes (17): ApiClientDiagnosticsStatusGet200Response, ApiDiverBodyWeightsGet200ResponseItem, ApiDiversFromCodePostRequest, ApiDiveSitesGet200Response, ApiMeGet200ResponseOwnDiver, ApiToolsBottomTimeGet200ResponseLimits, ApiVerificationCodesReadPostRequest, DiveViewCylindersItemSeries (+9 more)

### Community 56 - "Community 56"
Cohesion: 0.05
Nodes (10): MainActivity, MainViewModel, Unreachable, IntHolder, MainViewModelPendingWarningTest, WarningCache, WarningCachedMe, WarningInstances (+2 more)

### Community 57 - "Community 57"
Cohesion: 0.08
Nodes (17): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), Answers (+9 more)

### Community 58 - "Community 58"
Cohesion: 0.04
Nodes (52): BACKUP_RUN_TASK, backupTask(), ADR-0069, CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053 (+44 more)

### Community 59 - "Community 59"
Cohesion: 0.06
Nodes (64): ANDROID_UI, AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch() (+56 more)

### Community 60 - "Community 60"
Cohesion: 0.08
Nodes (16): ChangesList(), ConflictChoice(), ConflictEntry(), ConflictsRoute(), ConflictsScreen(), diveTitle(), FieldDecision(), NoticeSection() (+8 more)

### Community 61 - "Community 61"
Cohesion: 0.07
Nodes (65): Answer, answer(), baseFrom(), BodyWeight, Confidence, high, low, medium (+57 more)

### Community 62 - "Community 62"
Cohesion: 0.05
Nodes (41): captureDiagnostic(), captureError(), cleanContext(), CLIENT_NAME, CLIENT_VERSION, DiagnosticEntry, diagnosticFromError(), DIAGNOSTICS_KEY (+33 more)

### Community 63 - "Community 63"
Cohesion: 0.08
Nodes (11): ChangeFeedEvent, ChangeFeedPage, Complete, SessionEnded, Stopped, SyncEngine, SyncPhase, Details (+3 more)

### Community 64 - "Community 64"
Cohesion: 0.08
Nodes (5): DiveDetailViewModelTest, GatedDispatcher, DiveEditViewModelTest, FakeSearch, FoundDiver

### Community 65 - "Community 65"
Cohesion: 0.04
Nodes (55): AssessmentService, AssessmentView, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView() (+47 more)

### Community 66 - "Community 66"
Cohesion: 0.12
Nodes (24): Body, BrokenBody, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage(), diverJson() (+16 more)

### Community 67 - "Community 67"
Cohesion: 0.10
Nodes (10): FakeCache, FakeCachedMe, FakeInstances, FakeQueue, FakeTokens, Harness, json(), SessionRepositoryTest (+2 more)

### Community 68 - "Community 68"
Cohesion: 0.07
Nodes (13): LeadAnswer, LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseBasis, ApiDiversIdLeadEstimateGet200ResponseBasisDivesItem (+5 more)

### Community 69 - "Community 69"
Cohesion: 0.04
Nodes (41): ActionContext, Direction, DiveExport, DiveListing, FIND_BY, FindBy, Operation, OPERATIONS (+33 more)

### Community 70 - "Community 70"
Cohesion: 0.06
Nodes (51): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, answer(), baseFrom(), cylindersAtReserve(), CylinderTerm (+43 more)

### Community 71 - "Community 71"
Cohesion: 0.06
Nodes (36): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+28 more)

### Community 72 - "Community 72"
Cohesion: 0.05
Nodes (52): BottomTimeInput, Conditions, GasMix, GasRule, LoadingDive, TissuesAfterDivesInput, NdlInput, AIR (+44 more)

### Community 73 - "Community 73"
Cohesion: 0.07
Nodes (9): WritesUiState, WritesViewModel, SettingsRouteTest, WritesViewModelTest, ConflictResolution, KeptServer, Requeued, WriteQueueSnapshot (+1 more)

### Community 74 - "Community 74"
Cohesion: 0.09
Nodes (47): accountListEntry, diver, tidyName(), DiverDetails, Caller, foldName(), nameDiffers(), Buddy (+39 more)

### Community 75 - "Community 75"
Cohesion: 0.07
Nodes (27): CameraPermission, Blocked, Denied, cameraPermission(), Granted, ChangeBullet(), changeField(), ChangeLine() (+19 more)

### Community 76 - "Community 76"
Cohesion: 0.06
Nodes (5): QrImageDecoder, LauncherIconTest, SplashTheme, SplashThemeTest, QrImageDecoderTest

### Community 77 - "Community 77"
Cohesion: 0.06
Nodes (38): acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), AppLockProbe (+30 more)

### Community 78 - "Community 78"
Cohesion: 0.05
Nodes (40): CYLINDER_MATERIALS, SUIT_TYPES, SUIT_UNDERGARMENTS, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053 (+32 more)

### Community 79 - "Community 79"
Cohesion: 0.08
Nodes (41): Leave, Series, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+33 more)

### Community 80 - "Community 80"
Cohesion: 0.06
Nodes (42): ADR-0036, ADR-0063, ADR-0038, ADR-0048, ADR-0062, AiAccess, assessedDive(), AssessedFinding (+34 more)

### Community 82 - "Community 82"
Cohesion: 0.05
Nodes (42): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+34 more)

### Community 83 - "Community 83"
Cohesion: 0.05
Nodes (36): Cylinder, DiveView, RevisionView, ADR-0045, upload(), Candidate, Check, Outcome (+28 more)

### Community 84 - "Community 84"
Cohesion: 0.13
Nodes (15): Facts(), Water(), DiveDetailText, conflictCylindersText(), conflictCylinderText(), conflictFeedbackText(), conflictFieldLabel(), conflictFieldValue() (+7 more)

### Community 85 - "Community 85"
Cohesion: 0.10
Nodes (6): DiveHubTopBar(), titleLineHeight(), TitleSkeleton(), DiveHubTheme(), DiveHubTopBarTest, PendingWarningDialogTest

### Community 86 - "Community 86"
Cohesion: 0.20
Nodes (3): TissuePoint, depthPoints(), PlanningVectorsTest

### Community 87 - "Community 87"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 88 - "Community 88"
Cohesion: 0.06
Nodes (41): Change(), Changes(), historyRoute, Made(), REVISION_CAUSES, PositionSchema, AdoptBody, Country (+33 more)

### Community 89 - "Community 89"
Cohesion: 0.07
Nodes (9): findActivity(), CameraFailure, cameraFailure(), Blocked, Busy, Missing, CameraScanner(), CameraScannerFrame() (+1 more)

### Community 90 - "Community 90"
Cohesion: 0.09
Nodes (13): logbookDiveItemIndex(), DiveListViewDivesItem, DiveListViewDivesItemGasesItem, DiveListViewDivesItemParticipantsItem, DiveListViewDivesItemSite, DiveListViewMonthsItem, DiveProfileView, localMonthOf() (+5 more)

### Community 91 - "Community 91"
Cohesion: 0.08
Nodes (8): Connected, Hint, HintFrame, LiveEvent, SseLiveHints, LiveHintsTest, SseLiveHintsEngineTest, TrickleServer

### Community 92 - "Community 92"
Cohesion: 0.05
Nodes (32): account, accountRelations, apikey, rateLimit, sessionRelations, ADR-0011, ADR-0012, ADR-0035 (+24 more)

### Community 93 - "Community 93"
Cohesion: 0.08
Nodes (38): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+30 more)

### Community 94 - "Community 94"
Cohesion: 0.09
Nodes (17): LeadEstimateUiState, LeadEstimateViewModel, SuitUndergarment, heavy, light, medium, channelsOf(), CnsSource (+9 more)

### Community 95 - "Community 95"
Cohesion: 0.07
Nodes (32): SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject(), MAX_CARD_IMAGE_BYTES (+24 more)

### Community 96 - "Community 96"
Cohesion: 0.05
Nodes (39): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+31 more)

### Community 97 - "Community 97"
Cohesion: 0.13
Nodes (13): bottomTime(), otu(), oxygenLimitMinutes(), planningSac(), PlanningSacDive, PlanningSacResult, PlanningSacSource, default (+5 more)

### Community 98 - "Community 98"
Cohesion: 0.08
Nodes (35): Delivered, buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open(), saveAndReadBack() (+27 more)

### Community 99 - "Community 99"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 100 - "Community 100"
Cohesion: 0.11
Nodes (11): ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseCns, ApiDiversIdLoadingsGet200ResponseN2, ApiDiversIdLoadingsGet200ResponseNoFly, ApiDiversIdLoadingsGet200ResponseNoFlySource, ApiDiversIdLoadingsGet200ResponseOtu, cnsDecayed() (+3 more)

### Community 101 - "Community 101"
Cohesion: 0.07
Nodes (34): AI_ACCESS_NAME_MAX, aiAccessLog, aiAccessSetting, Access, AccessView, AiAccessRouteDeps, LogEntryView, Scope (+26 more)

### Community 102 - "Community 102"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 104 - "Community 104"
Cohesion: 0.08
Nodes (31): CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite(), lock() (+23 more)

### Community 105 - "Community 105"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 106 - "Community 106"
Cohesion: 0.10
Nodes (12): neighboursPath(), LogbookFilter, CHANGED_SINCE_SENT, NO_RECORDING, NO_SITE, NOT_AT_PROVIDER, WITH_FINDINGS, onlyParam() (+4 more)

### Community 107 - "Community 107"
Cohesion: 0.11
Nodes (8): AppNavHost(), AppScreens, clearSavedTabStates(), isShowing(), leaveDiveForLogbook(), MainShell(), navigateToTab(), tabOf()

### Community 108 - "Community 108"
Cohesion: 0.13
Nodes (34): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+26 more)

### Community 109 - "Community 109"
Cohesion: 0.07
Nodes (28): headers, ADR-0030, ADR-0040, conflictForLena(), diveWithoutRecording(), editInSsi(), externalDiver(), importLena() (+20 more)

### Community 110 - "Community 110"
Cohesion: 0.15
Nodes (5): LogbookUiState, LogbookScreenTest, DiveListView, DiveListViewCounts, DiveListViewTotals

### Community 111 - "Community 111"
Cohesion: 0.14
Nodes (15): Sac, SacCylinder, SacDive, SacMissing, cylinder_incomplete, no_average_depth, too_short, sacOfDive() (+7 more)

### Community 112 - "Community 112"
Cohesion: 0.08
Nodes (31): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+23 more)

### Community 113 - "Community 113"
Cohesion: 0.11
Nodes (28): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+20 more)

### Community 115 - "Community 115"
Cohesion: 0.10
Nodes (26): ADR-0051, Status, ADR-0056, ADR-0024, ADR-0043, clearQueue(), conflictAtSsi(), connectSsi() (+18 more)

### Community 116 - "Community 116"
Cohesion: 0.23
Nodes (8): leadAnswer(), EstimateCylinder, PlannedConditions, Suit, assemblePersonalizedSources(), gasOf(), leadEstimate(), PersonalizedToolsTest

### Community 117 - "Community 117"
Cohesion: 0.13
Nodes (7): GateCachedMe, GateCall, GateClient, GateInstances, GateServer, GateTokens, WriteExitGateTest

### Community 118 - "Community 118"
Cohesion: 0.08
Nodes (27): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+19 more)

### Community 119 - "Community 119"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 120 - "Community 120"
Cohesion: 0.20
Nodes (4): DiveMergeTest, diveObject(), participant(), participants()

### Community 121 - "Community 121"
Cohesion: 0.13
Nodes (8): InstanceProbeTest, json(), probe(), ProbeCache, ProbeCachedMe, ProbeInstances, probeRepository(), ProbeTokens

### Community 122 - "Community 122"
Cohesion: 0.10
Nodes (27): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+19 more)

### Community 123 - "Community 123"
Cohesion: 0.10
Nodes (25): AL80, asEstimateInput(), EstimateDiveCylinder, evaluateLeadVectorCase(), LEAD_CASES, LEAD_VECTOR_KERNEL, leadInput(), leadVectorFile() (+17 more)

### Community 124 - "Community 124"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 125 - "Community 125"
Cohesion: 0.16
Nodes (3): ToolsViewModel, assembleToolSources(), ToolSourcesStoreTest

### Community 126 - "Community 126"
Cohesion: 0.11
Nodes (4): FakeCachedMe, FakeInstances, FakeTokens, MainViewModelTest

### Community 127 - "Community 127"
Cohesion: 0.07
Nodes (12): buddySite(), Dive, OTHER, PendingOffer, ADR-0012, ADR-0070, Dive, OTHER (+4 more)

### Community 130 - "Community 130"
Cohesion: 0.13
Nodes (11): WriteDrain, Drained, Empty, Stopped, WriteReplayResult, SyncDrainTest, WriteDrain, WriteDrain (+3 more)

### Community 131 - "Community 131"
Cohesion: 0.18
Nodes (10): Body, buddyAnswer(), centreAnswer(), centreView(), diverView(), Offline, ScanCall, ScanHarness (+2 more)

### Community 132 - "Community 132"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 133 - "Community 133"
Cohesion: 0.10
Nodes (26): globalSetup(), ADR-0023, accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks() (+18 more)

### Community 134 - "Community 134"
Cohesion: 0.11
Nodes (7): ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh, ApiToolsBottomTimeGet200ResponseUsedSacLpm, ApiToolsBottomTimeGet200ResponseUsedWater

### Community 135 - "Community 135"
Cohesion: 0.11
Nodes (7): ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport

### Community 136 - "Community 136"
Cohesion: 0.11
Nodes (24): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+16 more)

### Community 137 - "Community 137"
Cohesion: 0.14
Nodes (24): surfacePressureBar(), ndlMinutes(), ADR-0032, ADR-0046, evaluateTissues(), advanceTissues(), schreinerStep(), surfaceSaturatedTissues() (+16 more)

### Community 138 - "Community 138"
Cohesion: 0.13
Nodes (23): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, Site (+15 more)

### Community 139 - "Community 139"
Cohesion: 0.15
Nodes (22): BackupRunView, backupSettingsQuery(), BackupSettingsView, backupsQuery(), SiteDataView, supportBundlePreviewQuery(), SupportBundlePreviewView, ADR-0030 (+14 more)

### Community 140 - "Community 140"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 141 - "Community 141"
Cohesion: 0.18
Nodes (3): ToolLink(), ToolsIndexScreen(), ToolsScreenshotTest

### Community 143 - "Community 143"
Cohesion: 0.10
Nodes (17): ProblemCode, ResponseState, whenResponseGone(), StoredBlob, PlacedArchive, ANSWER, looksLikeRecipientPrivateKey(), PlacedArchive (+9 more)

### Community 144 - "Community 144"
Cohesion: 0.16
Nodes (24): DiveOrigin, REFERENCE_PREFIX, SsiLogbook, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef() (+16 more)

### Community 145 - "Community 145"
Cohesion: 0.12
Nodes (22): holdLeave(), mayLeave(), useLeaveGuard(), typedSiteId(), Draft, draftOf(), ExternalIdField(), positionOf() (+14 more)

### Community 146 - "Community 146"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 147 - "Community 147"
Cohesion: 0.14
Nodes (23): AscentBand, ascentBandsOf(), assessProfile(), ceiling(), ppo2(), Profile, PROFILE_RULES, ProfileInput (+15 more)

### Community 148 - "Community 148"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 149 - "Community 149"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 150 - "Community 150"
Cohesion: 0.10
Nodes (22): CENTRE_SOURCE_NAME, CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+14 more)

### Community 151 - "Community 151"
Cohesion: 0.08
Nodes (10): HINT_CHANNEL, LiveHub, createListener(), connect(), lost(), LiveListener, LiveLog, RETRY_MS (+2 more)

### Community 152 - "Community 152"
Cohesion: 0.11
Nodes (23): AIR, CNS_HALF_LIFE_MINUTES, cnsDecayed(), DEFAULT_CONDITIONS, N2_REPLAY_WINDOW_HOURS, N2_STALE_AFTER_HOURS, N2Loadings, N2LoadingsBase (+15 more)

### Community 153 - "Community 153"
Cohesion: 0.10
Nodes (19): REVISION_ENTITY_TYPES, RevisionEntityType, CHANGE_EVENT_RETENTION_DAYS, ChangeEventView, ChangeFeedPage, ChangeKind, CHANGES_DEFAULT_LIMIT, CHANGES_MAX_LIMIT (+11 more)

### Community 154 - "Community 154"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 155 - "Community 155"
Cohesion: 0.13
Nodes (8): countOf(), FilterChip, filterChips(), toggleFilter(), LogbookPaging, counts(), LogbookFiltersTest, LogbookPagingTest

### Community 156 - "Community 156"
Cohesion: 0.13
Nodes (6): ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting

### Community 157 - "Community 157"
Cohesion: 0.13
Nodes (6): DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, WaitingPushView

### Community 158 - "Community 158"
Cohesion: 0.24
Nodes (6): Body, Call, Harness, Offline, Reply, WriteReplayerTest

### Community 159 - "Community 159"
Cohesion: 0.08
Nodes (24): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+16 more)

### Community 160 - "Community 160"
Cohesion: 0.14
Nodes (19): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), parseXml() (+11 more)

### Community 161 - "Community 161"
Cohesion: 0.21
Nodes (20): ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres(), isHypoxic(), metresPerBar() (+12 more)

### Community 162 - "Community 162"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 164 - "Community 164"
Cohesion: 0.23
Nodes (5): Body, ConflictCall, ConflictHarness, ConflictReply, WriteConflictResolutionTest

### Community 165 - "Community 165"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 166 - "Community 166"
Cohesion: 0.14
Nodes (4): FakeCachedMe, FakeInstances, FakeTokens, SyncAuthTest

### Community 167 - "Community 167"
Cohesion: 0.15
Nodes (17): BottomTime, bottomTime(), Limit, ADR-0032, ADR-0033, ADR-0071, planGas(), NOAA_ROWS (+9 more)

### Community 168 - "Community 168"
Cohesion: 0.12
Nodes (19): ENGINE_VERSION, EVIDENCE, Evidence, NOT_COVERED_DIVE_MODES, RULE_IDS, RULES, SEVERITIES, Severity (+11 more)

### Community 169 - "Community 169"
Cohesion: 0.12
Nodes (18): siteImportStatus, PROBLEM_CODES, SiteImportRouteDeps, StartBody, STATUS, ADR-0021, SiteImportError, SiteImportService (+10 more)

### Community 170 - "Community 170"
Cohesion: 0.13
Nodes (14): LoadingsView, CustomTypeOptions, i18next, initI18n(), resources, ADR-0014, LOADING_SOURCES, loadingNumber() (+6 more)

### Community 171 - "Community 171"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 173 - "Community 173"
Cohesion: 0.15
Nodes (5): DiagnosticEntry, DiagnosticLevel, ERROR, WARN, DiagnosticsBuffer

### Community 174 - "Community 174"
Cohesion: 0.15
Nodes (5): ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits

### Community 175 - "Community 175"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 177 - "Community 177"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource/poppins@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 178 - "Community 178"
Cohesion: 0.13
Nodes (14): BufferedLog, LOG_BUFFER_CAPACITY_BYTES, LOG_BUFFER_CAPACITY_ENTRIES, LogBufferStats, LogRingBufferOptions, SupportBundle, ClientDiagnosticsProvider, FailedBackup (+6 more)

### Community 179 - "Community 179"
Cohesion: 0.11
Nodes (15): dumpToolAvailable(), execFileAsync, migrationsFolder, silent, ADR-0069, credentials(), Numbered, SSI (+7 more)

### Community 180 - "Community 180"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 181 - "Community 181"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 182 - "Community 182"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 184 - "Community 184"
Cohesion: 0.24
Nodes (8): decode(), ENTITIES, localName(), Open, Reader, ADR-0003, XmlElement, XmlError

### Community 185 - "Community 185"
Cohesion: 0.11
Nodes (17): BOTTOM_TIME_RULES, PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView (+9 more)

### Community 186 - "Community 186"
Cohesion: 0.18
Nodes (7): ToolPage, Gas, Index, Lead, Loadings, Plan, FakeScreens

### Community 187 - "Community 187"
Cohesion: 0.23
Nodes (10): AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint, SketchProfile, sketchScale(), toAscentBand() (+2 more)

### Community 188 - "Community 188"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 190 - "Community 190"
Cohesion: 0.15
Nodes (13): Sac, SAC_MISSING, SacMissing, sacOfDive(), SacValues, sacValues(), ADR-0033, ADR-0045 (+5 more)

### Community 191 - "Community 191"
Cohesion: 0.16
Nodes (12): BOM, Json, num(), obj(), positive(), scaled(), SuuntoJsonAdapter, time() (+4 more)

### Community 192 - "Community 192"
Cohesion: 0.15
Nodes (10): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, ADR-0068 (+2 more)

### Community 194 - "Community 194"
Cohesion: 0.19
Nodes (4): CentreView, CentreViewExternalIdsItem, CentreViewSitesItem, CentreViewVerificationCode

### Community 195 - "Community 195"
Cohesion: 0.19
Nodes (4): DiveDetailsListView, DiveDetailsListViewCounts, DiveDetailsListViewMonthsItem, DiveDetailsListViewTotals

### Community 196 - "Community 196"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 197 - "Community 197"
Cohesion: 0.12
Nodes (13): clientDiagnostic, clientDiagnosticsSettings, ClientDiagnosticsEntryInput, ClientDiagnosticsEntryView, ClientDiagnosticsError, ClientDiagnosticsUpload, KEEP_NEWEST, MAX_UPLOAD_ENTRIES (+5 more)

### Community 198 - "Community 198"
Cohesion: 0.15
Nodes (13): AssessmentRefresh, ADR-0036, ADR-0053, CandidateRouteDeps, CandidateView, Decided, DiveBrief, OffsetSource (+5 more)

### Community 199 - "Community 199"
Cohesion: 0.21
Nodes (11): buildSupportBundle(), buildTarGz(), json(), ndjson(), octal(), SUPPORT_BUNDLE_FORMAT, SUPPORT_BUNDLE_FORMAT_VERSION, SupportBufferManifest (+3 more)

### Community 200 - "Community 200"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 202 - "Community 202"
Cohesion: 0.27
Nodes (7): LeadInputTest, SuitType, drysuit, none, semidry, skin, wetsuit

### Community 203 - "Community 203"
Cohesion: 0.20
Nodes (13): dayNumber(), enteredDecoFromNdl(), minNdlSeconds(), NoFly, noFlyAfter(), NoFlyReason, decompression, several (+5 more)

### Community 204 - "Community 204"
Cohesion: 0.19
Nodes (13): flag(), idText(), isObject(), numberOrNull(), readSsiSite(), ssiSiteRow(), SsiSiteValues, visibilityFor() (+5 more)

### Community 205 - "Community 205"
Cohesion: 0.13
Nodes (6): FakeBroadcastChannel, FakeEventSource, Listener, locks, start(), ADR-0066

### Community 206 - "Community 206"
Cohesion: 0.13
Nodes (14): concurrency, cancel-in-progress, group, env, RELEASE_TAG, jobs, publish, name (+6 more)

### Community 207 - "Community 207"
Cohesion: 0.33
Nodes (4): ConflictField, DiveMerge, MergeOutcome, ParticipantsMergeOutcome

### Community 208 - "Community 208"
Cohesion: 0.24
Nodes (13): asNumber(), asText(), compareReadBack(), datasetLength(), describe(), isObject(), keyDifferences(), pause() (+5 more)

### Community 209 - "Community 209"
Cohesion: 0.20
Nodes (5): LogRingBuffer, RedactionCounters, SupportBundleInput, SupportBundleManifest, SupportServiceDeps

### Community 210 - "Community 210"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 211 - "Community 211"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 212 - "Community 212"
Cohesion: 0.17
Nodes (11): FindingValues, RuleId, NoFly, ASSESSMENT_NOTE, FINDING_TEXT, n(), NO_FLY_TEXT, Text (+3 more)

### Community 213 - "Community 213"
Cohesion: 0.23
Nodes (10): ADR-0076, ClientVersionRegistry, MIN_CLIENT_VERSIONS, MinClientVersion, ADR-0076, compareIdentifier(), compareNumeric(), compareSemanticVersions() (+2 more)

### Community 214 - "Community 214"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 215 - "Community 215"
Cohesion: 0.32
Nodes (10): ConflictsRoute, DiveEditRoute, InstanceRoute, LogbookRoute, MainRoute, ScanRoute, SettingsDebugRoute, SettingsRoute (+2 more)

### Community 216 - "Community 216"
Cohesion: 0.26
Nodes (3): DiverDetailsView, DiverDetailsViewAccountsItem, DiverDetailsViewCodesItem

### Community 217 - "Community 217"
Cohesion: 0.26
Nodes (3): DiverView, DiverViewCertifications, DiverViewCertificationsNewest

### Community 218 - "Community 218"
Cohesion: 0.26
Nodes (3): DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 220 - "Community 220"
Cohesion: 0.17
Nodes (7): ndl(), ProfileAssessment, Finding, SeriesDive, CLEAN, samples, ADR-0036

### Community 221 - "Community 221"
Cohesion: 0.20
Nodes (8): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, ADR-0043, ADR-0043, qr

### Community 222 - "Community 222"
Cohesion: 0.18
Nodes (11): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, ADR-0076, ADR-0083, MODULE_IMPORTS (+3 more)

### Community 223 - "Community 223"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 226 - "Community 226"
Cohesion: 0.25
Nodes (11): ascentRate(), descentRate(), judgedParts(), lastMetres(), safetyStop(), stopIn(), stopStability(), worstOf() (+3 more)

### Community 227 - "Community 227"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 228 - "Community 228"
Cohesion: 0.22
Nodes (8): diveRow(), rowOf(), ADR-0026, ADR-0048, diveRows(), goToMore(), expectFirstDiveInView(), rowOf()

### Community 229 - "Community 229"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 230 - "Community 230"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 231 - "Community 231"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 232 - "Community 232"
Cohesion: 0.53
Nodes (3): UiState, sessionDestination(), SessionDestinationTest

### Community 234 - "Community 234"
Cohesion: 0.22
Nodes (8): LIMITS, dayNumber(), deepDays(), SERIES_RULES, SeriesContext, SeriesFinding, SeriesRuleId, ADR-0036

### Community 235 - "Community 235"
Cohesion: 0.33
Nodes (8): prepareEntry(), DENIED_KEY_PARTS, isDeniedKey(), maskString(), redactInner(), redactLogObject(), RedactOptions, redactValue()

### Community 236 - "Community 236"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 237 - "Community 237"
Cohesion: 0.20
Nodes (10): verify, prerelease, sha, tag, versionCode, versionName, name, outputs (+2 more)

### Community 238 - "Community 238"
Cohesion: 0.20
Nodes (10): with, cache, distribution, fetch-depth, if-no-files-found, java-version, name, path (+2 more)

### Community 239 - "Community 239"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 242 - "Community 242"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 244 - "Community 244"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 245 - "Community 245"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 246 - "Community 246"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 247 - "Community 247"
Cohesion: 0.46
Nodes (8): appliedMigrationHead(), emptyRedactionCounters(), createSupportService(), assemble(), clientDiagnostics(), configSection(), failedJobs(), healthSection()

### Community 248 - "Community 248"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 250 - "Community 250"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 251 - "Community 251"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 252 - "Community 252"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 253 - "Community 253"
Cohesion: 0.33
Nodes (6): SuitType, DRYSUIT, NONE, SEMIDRY, SKIN, WETSUIT

### Community 254 - "Community 254"
Cohesion: 0.40
Nodes (5): stubDive(), ADR-0015, ADR-0023, ADR-0025, guardedRoute()

### Community 255 - "Community 255"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 256 - "Community 256"
Cohesion: 0.40
Nodes (5): LogbookPhase, Error, List, Loading, Offline

### Community 257 - "Community 257"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build

### Community 258 - "Community 258"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 259 - "Community 259"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 262 - "Community 262"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 263 - "Community 263"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 264 - "Community 264"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1380 isolated node(s):** `Idle`, `Failed`, `Reachable`, `Reserve`, `RockBottom` (+1375 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 4017 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **170 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `DiveStore` connect `Community 19` to `Community 130`, `Community 131`, `Community 12`, `Community 21`, `Community 28`, `Community 158`, `Community 32`, `Community 35`, `Community 36`, `Community 164`, `Community 166`, `Community 43`, `Community 46`, `Community 176`, `Community 57`, `Community 63`, `Community 64`, `Community 66`, `Community 73`, `Community 94`, `Community 106`, `Community 111`, `Community 116`, `Community 117`, `Community 125`?**
  _High betweenness centrality (0.019) - this node is a cross-community bridge._
- **What connects `Idle`, `Failed`, `Reachable` to the rest of the system?**
  _1380 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.017426517426517428 - nodes in this community are weakly interconnected._
- **Why does `DiveHubTheme()` connect `Community 85` to `Community 128`, `Community 129`, `Community 9`, `Community 13`, `Community 142`, `Community 141`, `Community 21`, `Community 163`, `Community 37`, `Community 172`, `Community 52`, `Community 56`, `Community 57`, `Community 60`, `Community 193`, `Community 68`, `Community 201`, `Community 73`, `Community 81`, `Community 225`, `Community 100`, `Community 110`?**
  _High betweenness centrality (0.013) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.019778699861687413 - nodes in this community are weakly interconnected._
- **Why does `drizzle-orm` connect `Community 20` to `Community 0`, `Community 1`, `Community 3`, `Community 132`, `Community 7`, `Community 8`, `Community 11`, `Community 14`, `Community 15`, `Community 17`, `Community 18`, `Community 24`, `Community 153`, `Community 27`, `Community 29`, `Community 159`, `Community 33`, `Community 34`, `Community 38`, `Community 40`, `Community 47`, `Community 178`, `Community 179`, `Community 53`, `Community 54`, `Community 58`, `Community 197`, `Community 74`, `Community 83`, `Community 92`, `Community 101`, `Community 104`?**
  _High betweenness centrality (0.012) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.04477233331924069 - nodes in this community are weakly interconnected._