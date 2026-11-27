# Graph Report - dive_hub  (2026-11-27)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 13601 nodes · 42079 edges · 434 communities (248 shown, 186 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 1001 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `c726f51a`
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
- Community 260
- Community 261
- Community 262
- Community 263
- Community 264
- Community 265
- Community 266
- Community 267
- Community 268
- Community 269
- Community 270
- Community 271
- Community 272
- Community 273
- Community 274
- Community 275
- Community 276
- Community 277
- Community 280
- Community 281
- Community 282
- Community 283
- Community 284
- Community 285
- Community 286
- Community 292

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 218 edges
2. `Button()` - 209 edges
3. `useErrorText()` - 181 edges
4. `useDisplay()` - 168 edges
5. `drizzle-orm` - 162 edges
6. `unwrap()` - 145 edges
7. `Muted()` - 143 edges
8. `DiveStore` - 139 edges
9. `createTestDatabase()` - 121 edges
10. `TestDatabase` - 120 edges

## Surprising Connections (you probably didn't know these)
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `SearchHarness` --calls--> `ApiDiverSearch`  [INFERRED]
  apps/android/shared/src/commonTest/kotlin/dev/divehub/shared/divers/DiverSearchTest.kt → apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/divers/DiverSearch.kt
- `AppNavHost()` --calls--> `DiveRoute`  [INFERRED]
  apps/android/app/src/main/kotlin/dev/divehub/android/ui/navigation/AppNavHost.kt → apps/android/app/src/main/kotlin/dev/divehub/android/ui/navigation/AppRoutes.kt

## Import Cycles
- None detected.

## Communities (434 total, 186 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.01
Nodes (375): user, buddySuggestion, cylinder, dive, diveAssessment, diver, diveSiteExternalId, lead (+367 more)

### Community 1 - "Community 1"
Cohesion: 0.01
Nodes (270): ComputerChoice, connection, ConnectionRow, diveFingerprint, DiveImportMode, DiveKind, diveLease, importOriginal (+262 more)

### Community 2 - "Community 2"
Cohesion: 0.02
Nodes (258): DiveView, cylinderCatalogueQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery() (+250 more)

### Community 3 - "Community 3"
Cohesion: 0.02
Nodes (209): createServer(), createAuth(), auth, loadAuthSecret(), acquireAppLock(), BackupServiceDeps, BillingLog, createBillingService() (+201 more)

### Community 4 - "Community 4"
Cohesion: 0.02
Nodes (198): APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), AppLockProbe, probeAppLock() (+190 more)

### Community 5 - "Community 5"
Cohesion: 0.04
Nodes (227): ChangePassword(), DisplaySettings(), ReportProblemSettings(), SecuritySettings(), Sessions(), ADR-0080, useDescribeAgent(), AdminSettings() (+219 more)

### Community 6 - "Community 6"
Cohesion: 0.02
Nodes (159): pushRun, pushRunItem, PushRunLeftOut, Delivered, Direction, DIVE_FIELDS, DiveExport, DiveListing (+151 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (102): Section(), cylinderLine(), cylinderPressures(), Cylinders(), Direction, Back, Left, Right (+94 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (60): DiveRow(), Figures(), RowFacts(), EmptyState(), FactRow(), NoteText(), StackedNoteText(), Radius (+52 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (135): label(), AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView (+127 more)

### Community 10 - "Community 10"
Cohesion: 0.02
Nodes (26): AppContainer, AndroidDiveStore, MainActivity, DiveEditUiState, DiveEditViewModel, DiverChoice, DiverSearchState, EditSource (+18 more)

### Community 11 - "Community 11"
Cohesion: 0.02
Nodes (131): CentreService, CentreRouteDeps, UTC_OFFSET_SOURCES, DiverService, downsampleMinMax(), ADR-0025, WaterMismatch, waterMismatch() (+123 more)

### Community 12 - "Community 12"
Cohesion: 0.01
Nodes (137): CHANNELS, ADR-0036, ENGINE_VERSION, AcceptedConsent, CONSENT_VERSIONS, ConsentKind, consentOf(), isObject() (+129 more)

### Community 13 - "Community 13"
Cohesion: 0.03
Nodes (125): Me, divesQuery(), DiveSummary, LogbookPage, PAGE_SIZE, App(), BackupsPage, CentrePage (+117 more)

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (124): continuationAppend, LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, renumber(), renumberStatement(), ADR-0048, askedAt() (+116 more)

### Community 15 - "Community 15"
Cohesion: 0.02
Nodes (115): AiAccessLogEntry, aiAccessLogQuery(), aiAccessQuery(), AiAccessView, billingCheckout(), billingPortal(), BillingView, invitationLinkQuery() (+107 more)

### Community 16 - "Community 16"
Cohesion: 0.04
Nodes (127): allButDive(), continuationCandidateQuery(), ContinuationCandidateView, deletedDivesOn(), deletedDivesQuery(), diveQuery(), LogbookCheckView, mergeCandidatesQuery() (+119 more)

### Community 17 - "Community 17"
Cohesion: 0.04
Nodes (84): DangerButton(), FieldButton(), InkButton(), SecondaryButton(), CylinderFields(), CylinderProblemText(), CylindersFields(), FieldPair() (+76 more)

### Community 18 - "Community 18"
Cohesion: 0.07
Nodes (16): RevisionFollowTest, CylinderMaterial, aluminium, carbon, steel, Dive, PendingWriteStatus, Awaiting (+8 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (44): LeadAnswer, LeadCylinderInput, LeadInputs, LeadInputTest, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseBasis (+36 more)

### Community 20 - "Community 20"
Cohesion: 0.06
Nodes (12): DiveDetailScreen(), DiveDetailScreenshotTest, DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewLeadItem, DiveViewPosition, DiveViewRecordingsItemSummaryGasesItem, DiveViewSac (+4 more)

### Community 21 - "Community 21"
Cohesion: 0.03
Nodes (114): LEAD_PLACEMENTS, WEIGHTING_FELT, AiAccessScope, seesPositions(), VerifiedAccess, ProfileSummary, round1(), Series (+106 more)

### Community 22 - "Community 22"
Cohesion: 0.03
Nodes (27): ApiClientDiagnosticsStatusGet200Response, ApiDevicesPost200Response, ApiDevicesPostRequest, ApiDiverBodyWeightsGet200ResponseItem, ApiDiversFromCodePostRequest, ApiDiveSitesGet200Response, ApiMeGet200ResponseBilling, ApiMeGet200ResponseOwnDiver (+19 more)

### Community 23 - "Community 23"
Cohesion: 0.03
Nodes (97): CertificationViewSchema, ADR-0050, DIVE_IMPORT_MODES, JOINT_FILL_FIELDS, PARTICIPANT_ROLES, siteImportStatus, SUGGESTION_STATUSES, DiveProfileView (+89 more)

### Community 24 - "Community 24"
Cohesion: 0.03
Nodes (103): certificationsQuery(), keys, BuddyView, ConflictChoices, ConflictEntryView, ConflictFieldView, ConflictsOfConnection, connectionsQuery() (+95 more)

### Community 25 - "Community 25"
Cohesion: 0.06
Nodes (6): Spacing, SectionTest, LoadingScreenshotTest, DiveViewFromRecording, DiveViewFromRecordingStartsAt, ErrorCodes

### Community 26 - "Community 26"
Cohesion: 0.03
Nodes (93): RequirementView, ExternalIdView, Position, purgeRunning(), running(), SiteImportArea, siteImportsQuery(), SiteImportView (+85 more)

### Community 27 - "Community 27"
Cohesion: 0.04
Nodes (93): replyCertificationError, STATUS, CertificationService, CertificationView, Day, CardParams, CertificationRouteDeps, errors (+85 more)

### Community 28 - "Community 28"
Cohesion: 0.05
Nodes (104): ParticipantRole, sampleSeries, managedDiverIds(), tieToJoined(), cylindersOf(), mergeProviderCylinder(), providerCylinderOf(), participantsOf() (+96 more)

### Community 29 - "Community 29"
Cohesion: 0.04
Nodes (93): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, ambientPressureBar() (+85 more)

### Community 30 - "Community 30"
Cohesion: 0.04
Nodes (13): DiveViewSuitDraftSource, numberOrNull(), toJson(), DiveStore, toBoolean(), toLong(), toModel(), Recording (+5 more)

### Community 31 - "Community 31"
Cohesion: 0.03
Nodes (75): diveModeFromFit, createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities() (+67 more)

### Community 32 - "Community 32"
Cohesion: 0.06
Nodes (58): BottomTime, bottomTime(), BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen (+50 more)

### Community 33 - "Community 33"
Cohesion: 0.04
Nodes (78): EMPTY_SITE_IMPORT_COUNTS, ImportedValues, PurgeReferencedSite, SiteImportCounts, SitePurgeProgress, SitePurgeResult, StoredConflictField, ADR-0021 (+70 more)

### Community 34 - "Community 34"
Cohesion: 0.03
Nodes (74): BACKUP_RUN_TASK, backupTask(), ADR-0069, changeEvent, ImportErrorCode, ImportFound, importJob, ImportOutcome (+66 more)

### Community 35 - "Community 35"
Cohesion: 0.04
Nodes (48): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+40 more)

### Community 36 - "Community 36"
Cohesion: 0.05
Nodes (16): CountingDispatcher, LogbookViewModelTest, DiveListView, DiveListViewDivesItem, DiveListViewDivesItemGasesItem, DiveListViewDivesItemParticipantsItem, DiveListViewDivesItemSite, DiveListViewTotals (+8 more)

### Community 37 - "Community 37"
Cohesion: 0.04
Nodes (74): SessionUser, authHandler(), authHeaders(), fastify, FastifyRequest, forwardCookies(), ADR-0011, ADR-0061 (+66 more)

### Community 38 - "Community 38"
Cohesion: 0.07
Nodes (66): Formats, formatSyncTime(), GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult() (+58 more)

### Community 39 - "Community 39"
Cohesion: 0.05
Nodes (30): DiveHubException, PendingWrite, PendingWriteKind, Action, Patch, ConflictSide, Server, ThisPhone (+22 more)

### Community 40 - "Community 40"
Cohesion: 0.04
Nodes (28): DiveDetailViewModel, NeighboursRead, ProfileRead, LogbookViewModel, followStoreWrites(), DiveRepository, neighboursPath(), countOf() (+20 more)

### Community 41 - "Community 41"
Cohesion: 0.06
Nodes (75): diveSite, dataIdsOf(), SearchInput, ADR-0020, ADR-0035, ADR-0057, settleSiteVisibility(), RemoteSite (+67 more)

### Community 42 - "Community 42"
Cohesion: 0.05
Nodes (64): cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043, Buddies() (+56 more)

### Community 43 - "Community 43"
Cohesion: 0.04
Nodes (73): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+65 more)

### Community 44 - "Community 44"
Cohesion: 0.04
Nodes (75): devicesQuery(), diverLoadingsQuery(), diversQuery(), DiverView, diveNeighboursQuery(), DiveValues, OverridableField, revisionsQuery() (+67 more)

### Community 45 - "Community 45"
Cohesion: 0.05
Nodes (16): SettingsDiagnosticsState, SettingsQueueState, SettingsUiState, SettingsViewModel, FakeDiagnostics, FakeInstances, SettingsViewModelTest, ApiClientDiagnosticsPostRequest (+8 more)

### Community 46 - "Community 46"
Cohesion: 0.07
Nodes (6): DiveEditViewModelTest, FakeSearch, ScanViewModelTest, FoundDiver, ReadCodes, WriteQueue

### Community 47 - "Community 47"
Cohesion: 0.04
Nodes (16): KeystoreTokenStore, PreferencesCachedMeStore, PreferencesInstanceUrlStore, PreferencesPollingIntervalStore, UserCacheStore, FakeCachedMe, SettingsRepository, CachedMeStore (+8 more)

### Community 48 - "Community 48"
Cohesion: 0.07
Nodes (23): QuietButton(), ChangesList(), ConflictChoice(), ConflictEntry(), ConflictsRoute(), ConflictsScreen(), diveTitle(), FieldDecision() (+15 more)

### Community 49 - "Community 49"
Cohesion: 0.04
Nodes (60): Queryable, Tx, jointDive, AppendUndo, audienceOfDiver(), PushHintSink, ADR-0020, ADR-0056 (+52 more)

### Community 50 - "Community 50"
Cohesion: 0.06
Nodes (70): logbookCheckAnswer, logbookCheckDiveAnswer, keepApart(), keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration(), CheckedDive (+62 more)

### Community 51 - "Community 51"
Cohesion: 0.08
Nodes (69): buildApp(), assessmentRoutes(), isAdmin(), requireAdmin(), requireUser(), sessionIdOf(), userOf(), BackupRouteDeps (+61 more)

### Community 52 - "Community 52"
Cohesion: 0.05
Nodes (60): ImportArea, flag(), idText(), isObject(), numberOrNull(), readSsiSite(), visibilityFor(), coordinate() (+52 more)

### Community 53 - "Community 53"
Cohesion: 0.06
Nodes (28): rememberReducedMotion(), rememberSkeletonAlpha(), SkeletonBar(), tankLabel(), AvatarCircle(), Error(), FilterAction(), Filters() (+20 more)

### Community 54 - "Community 54"
Cohesion: 0.06
Nodes (59): NoFly, getDiverLoadings, BottomTimeQuery, DEFAULT_GF_HIGH, diverLogbook(), planBottomTime(), residualSeedOf(), Source (+51 more)

### Community 55 - "Community 55"
Cohesion: 0.05
Nodes (17): MainViewModel, Unreachable, ApiMeGet200Response, ApiMeGet200ResponsePreferences, UserView, InstanceUrl, InstanceProbeResult, Loading (+9 more)

### Community 56 - "Community 56"
Cohesion: 0.07
Nodes (8): ScanScreenshotTest, ScanScreenTest, ApiVerificationCodesReadPost200Response, ApiVerificationCodesReadPost200ResponseCandidatesItem, ScanResult, ReadChange, ReadCode, ReadExisting

### Community 57 - "Community 57"
Cohesion: 0.05
Nodes (66): ANDROID_UI, AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch() (+58 more)

### Community 58 - "Community 58"
Cohesion: 0.06
Nodes (8): SettingsRouteTest, DiagnosticEntry, DiagnosticLevel, ERROR, WARN, DiagnosticsBuffer, DiagnosticsBufferTest, DiagnosticsTest

### Community 59 - "Community 59"
Cohesion: 0.09
Nodes (11): FakeCache, FakeCachedMe, FakeInstances, FakeQueue, FakeTokens, Harness, json(), SessionRepositoryTest (+3 more)

### Community 60 - "Community 60"
Cohesion: 0.07
Nodes (13): LeadEstimateViewModelTest, BodyWeight, Cylinder, Diver, DiveSite, ScanStatus, Created, Existing (+5 more)

### Community 61 - "Community 61"
Cohesion: 0.04
Nodes (63): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, ADR-0089, Services (+55 more)

### Community 62 - "Community 62"
Cohesion: 0.05
Nodes (42): captureDiagnostic(), captureError(), cleanContext(), clearDiagnostics(), CLIENT_NAME, CLIENT_VERSION, DiagnosticEntry, diagnosticFromError() (+34 more)

### Community 63 - "Community 63"
Cohesion: 0.09
Nodes (35): leadAnswer(), EstimateCylinder, PlannedConditions, Suit, dayNumber(), enteredDecoFromNdl(), minNdlSeconds(), NoFly (+27 more)

### Community 64 - "Community 64"
Cohesion: 0.07
Nodes (64): Answer, answer(), baseFrom(), BodyWeight, Confidence, high, low, medium (+56 more)

### Community 65 - "Community 65"
Cohesion: 0.05
Nodes (36): headers, ADR-0031, headers, ADR-0043, ADR-0080, cameraShowing(), headers, ADR-0043 (+28 more)

### Community 66 - "Community 66"
Cohesion: 0.08
Nodes (54): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+46 more)

### Community 67 - "Community 67"
Cohesion: 0.08
Nodes (14): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), GasPlanInputTest (+6 more)

### Community 68 - "Community 68"
Cohesion: 0.05
Nodes (12): findActivity(), CameraFailure, cameraFailure(), Blocked, Busy, Missing, CameraScanner(), CameraScannerFrame() (+4 more)

### Community 69 - "Community 69"
Cohesion: 0.05
Nodes (51): diverBodyWeight, participant, DiverError, ADR-0016, ADR-0053, ACCOUNT_PATTERN, accountKey(), createDiverService() (+43 more)

### Community 70 - "Community 70"
Cohesion: 0.09
Nodes (7): SyncCheckpoint, queuedProfileCheckpoint(), Complete, SessionEnded, Stopped, SyncEngine, SyncResult

### Community 71 - "Community 71"
Cohesion: 0.12
Nodes (24): Body, BrokenBody, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage(), diverJson() (+16 more)

### Community 73 - "Community 73"
Cohesion: 0.07
Nodes (8): ApiClient, ApiErrorBody, DecodeException, NoAnswerException, WriteCapability, ApiClientTest, PushRegistrationTest, WriteCapabilityTest

### Community 74 - "Community 74"
Cohesion: 0.05
Nodes (48): AssessmentService, AssessmentView, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView() (+40 more)

### Community 75 - "Community 75"
Cohesion: 0.05
Nodes (49): adminRole, AI_ACCESS_NAME_MAX, AI_ACCESS_PREFIX, AI_ACCESS_RATE, CLIENT_IP_HEADER, publicAuthPaths(), ADR-0011, ADR-0012 (+41 more)

### Community 76 - "Community 76"
Cohesion: 0.07
Nodes (50): surfacePressureBar(), AIR, cnsDecayed(), DEFAULT_CONDITIONS, LoadingDive, N2_REPLAY_WINDOW_HOURS, N2_STALE_AFTER_HOURS, N2Loadings (+42 more)

### Community 77 - "Community 77"
Cohesion: 0.05
Nodes (48): headers, ADR-0030, ADR-0062, Status, ADR-0056, AiAccess, AssessedFinding, clearPushRuns() (+40 more)

### Community 78 - "Community 78"
Cohesion: 0.08
Nodes (6): DiveHubTopBar(), titleLineHeight(), TitleSkeleton(), DiveHubTheme(), DiveHubTopBarTest, BottomBarScreenshotTest

### Community 79 - "Community 79"
Cohesion: 0.06
Nodes (5): QrImageDecoder, LauncherIconTest, SplashTheme, SplashThemeTest, QrImageDecoderTest

### Community 80 - "Community 80"
Cohesion: 0.09
Nodes (46): accountListEntry, diverExternalId, tidyName(), foldName(), nameDiffers(), Buddy, createBuddyService(), createFrom() (+38 more)

### Community 81 - "Community 81"
Cohesion: 0.07
Nodes (39): AuthOptions, hashPassword(), invitation, passwordReset, userRole, createMailLinks(), MailLinks, ADR-0012 (+31 more)

### Community 82 - "Community 82"
Cohesion: 0.06
Nodes (44): diveListFilter(), notAtProvider(), Blocked, classify(), FailureOutcome, failureOutcome(), kindOf(), KINDS (+36 more)

### Community 83 - "Community 83"
Cohesion: 0.14
Nodes (5): DiveMergeTest, diveObject(), jsonOf(), participants(), WriteQueueTest

### Community 84 - "Community 84"
Cohesion: 0.05
Nodes (41): CYLINDER_MATERIALS, SUIT_TYPES, SUIT_UNDERGARMENTS, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053 (+33 more)

### Community 85 - "Community 85"
Cohesion: 0.04
Nodes (47): depends_on, environment, image, ports, restart, volumes, condition, environment (+39 more)

### Community 86 - "Community 86"
Cohesion: 0.08
Nodes (42): Leave, diveModeOfSsi(), keepTraining(), ssiDiveType(), ssiInteger(), ADR-0057, asNumber(), asText() (+34 more)

### Community 87 - "Community 87"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 88 - "Community 88"
Cohesion: 0.07
Nodes (11): ProviderSummary, ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport (+3 more)

### Community 89 - "Community 89"
Cohesion: 0.10
Nodes (35): aiAccessSettingQuery(), BackupRunView, backupSettingsQuery(), BackupSettingsView, backupsQuery(), SiteDataView, supportBundlePreviewQuery(), SupportBundlePreviewView (+27 more)

### Community 92 - "Community 92"
Cohesion: 0.05
Nodes (42): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+34 more)

### Community 93 - "Community 93"
Cohesion: 0.07
Nodes (39): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+31 more)

### Community 95 - "Community 95"
Cohesion: 0.14
Nodes (6): LogbookPhase, Error, List, Loading, Offline, SettingsScreenshotTest

### Community 96 - "Community 96"
Cohesion: 0.06
Nodes (38): Change(), Changes(), PositionSchema, AdoptBody, Country, CreateBody, Description, EditBody (+30 more)

### Community 97 - "Community 97"
Cohesion: 0.08
Nodes (10): ApiToolsBottomTimeGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseResidualN2, ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh (+2 more)

### Community 98 - "Community 98"
Cohesion: 0.05
Nodes (37): AREA_OF, areaOf(), areas, capture(), findings, headers, ADR-0020, ADR-0021 (+29 more)

### Community 99 - "Community 99"
Cohesion: 0.10
Nodes (7): IntHolder, MainViewModelPendingWarningTest, WarningCache, WarningCachedMe, WarningInstances, WarningQueue, WarningTokens

### Community 100 - "Community 100"
Cohesion: 0.07
Nodes (33): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+25 more)

### Community 101 - "Community 101"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 102 - "Community 102"
Cohesion: 0.07
Nodes (31): billingHeaders, ADR-0087, globalSetup(), ADR-0023, accept(), addCentre(), addCrowd(), dataFile() (+23 more)

### Community 103 - "Community 103"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 106 - "Community 106"
Cohesion: 0.08
Nodes (30): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+22 more)

### Community 107 - "Community 107"
Cohesion: 0.12
Nodes (35): DEFAULT_SSI_URL, acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive() (+27 more)

### Community 108 - "Community 108"
Cohesion: 0.11
Nodes (7): WritesUiState, WritesViewModel, WritesViewModelTest, ConflictResolution, KeptServer, Requeued, WriteQueueSnapshot

### Community 109 - "Community 109"
Cohesion: 0.08
Nodes (34): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+26 more)

### Community 110 - "Community 110"
Cohesion: 0.11
Nodes (29): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0040 (+21 more)

### Community 111 - "Community 111"
Cohesion: 0.11
Nodes (8): AppNavHost(), AppScreens, clearSavedTabStates(), isShowing(), leaveDiveForLogbook(), MainShell(), navigateToTab(), tabOf()

### Community 112 - "Community 112"
Cohesion: 0.09
Nodes (8): Answers, number(), ToolsViewModel, ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits

### Community 113 - "Community 113"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 114 - "Community 114"
Cohesion: 0.12
Nodes (29): DiveOrigin, REFERENCE_PREFIX, Series, sampleCount(), asBool(), DIVE_ROW, SENT_KEYS, SsiValueType (+21 more)

### Community 115 - "Community 115"
Cohesion: 0.13
Nodes (31): importsQuery(), Logbook(), ImportContext, ImportFilesButton(), ImportHistory(), ImportList(), ImportNotices(), ImportPanel() (+23 more)

### Community 116 - "Community 116"
Cohesion: 0.11
Nodes (4): FakeCache, FakeInstances, FakeTokens, MainViewModelTest

### Community 117 - "Community 117"
Cohesion: 0.13
Nodes (7): GateCachedMe, GateCall, GateClient, GateInstances, GateServer, GateTokens, WriteExitGateTest

### Community 118 - "Community 118"
Cohesion: 0.09
Nodes (26): createPaddleClient(), post(), dateOf(), isObject(), ParsedSubscription, parseSubscriptionEvent(), STATUS, stringOf() (+18 more)

### Community 119 - "Community 119"
Cohesion: 0.14
Nodes (22): Body, Hold, Offline, RefreshData, refreshDeletedJson(), refreshDetailJson(), refreshDiveListItem(), refreshDivePage() (+14 more)

### Community 120 - "Community 120"
Cohesion: 0.10
Nodes (30): reviseDive(), untieLostSeries(), refreshFromPrimary(), deletedEarlier(), fullerCopyHere(), IsManaged, knownRecording(), Outcome (+22 more)

### Community 121 - "Community 121"
Cohesion: 0.09
Nodes (29): ScheduleDue, DiveGear, DueEntry, ensureItemForDevice(), GearItem, ItemDetailView, ItemEdit, ItemInput (+21 more)

### Community 122 - "Community 122"
Cohesion: 0.10
Nodes (22): SsiClient, buildCreateRecord(), buildDeleteRecord(), emptyRecord(), writableRecord(), ClearChecks, clearDive(), findIn() (+14 more)

### Community 123 - "Community 123"
Cohesion: 0.09
Nodes (25): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+17 more)

### Community 124 - "Community 124"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 125 - "Community 125"
Cohesion: 0.13
Nodes (4): LiveHints, SyncRefresher, RefreshHarness, refreshPathWithQuery()

### Community 126 - "Community 126"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 127 - "Community 127"
Cohesion: 0.12
Nodes (16): DiveHubApp(), DiveHubScreens, ErrorText(), Gate(), InstanceForm(), InstanceStatus(), LoadingScreen(), PasswordField() (+8 more)

### Community 128 - "Community 128"
Cohesion: 0.11
Nodes (7): LogbookPaging, sketchScale(), counts(), LogbookFiltersTest, LogbookFormatTest, LogbookPagingTest, SketchTest

### Community 129 - "Community 129"
Cohesion: 0.09
Nodes (27): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+19 more)

### Community 130 - "Community 130"
Cohesion: 0.08
Nodes (26): CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043, CentreChanged (+18 more)

### Community 131 - "Community 131"
Cohesion: 0.11
Nodes (23): SsiRecord, contextOf(), FakeSsi, BuddySaved, access(), client, out, pause (+15 more)

### Community 132 - "Community 132"
Cohesion: 0.07
Nodes (12): buddySite(), Dive, OTHER, PendingOffer, ADR-0012, ADR-0070, Dive, OTHER (+4 more)

### Community 133 - "Community 133"
Cohesion: 0.12
Nodes (26): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+18 more)

### Community 134 - "Community 134"
Cohesion: 0.16
Nodes (4): ToolsScreenshotTest, PlanningSacSource, default, logbook

### Community 135 - "Community 135"
Cohesion: 0.18
Nodes (10): Body, buddyAnswer(), centreAnswer(), centreView(), diverView(), Offline, ScanCall, ScanHarness (+2 more)

### Community 136 - "Community 136"
Cohesion: 0.09
Nodes (23): computerEventOf(), ADR-0036, computerEventFromFit(), COMPUTER_EVENT, computerEventFromSuunto(), DECO_MODEL, decoModelFromSuunto, DIVE_MODE (+15 more)

### Community 137 - "Community 137"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 138 - "Community 138"
Cohesion: 0.10
Nodes (7): ToolPage, Gas, Index, Lead, Loadings, Plan, FakeScreens

### Community 139 - "Community 139"
Cohesion: 0.16
Nodes (25): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+17 more)

### Community 140 - "Community 140"
Cohesion: 0.17
Nodes (26): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+18 more)

### Community 141 - "Community 141"
Cohesion: 0.10
Nodes (18): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, APP (+10 more)

### Community 142 - "Community 142"
Cohesion: 0.10
Nodes (6): drawBand(), ProfileSketch(), CameraChrome, DiveHubColors, isDarkScheme(), successColor()

### Community 144 - "Community 144"
Cohesion: 0.11
Nodes (23): AssessmentError, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), requireManagedDive(), isRule() (+15 more)

### Community 145 - "Community 145"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 146 - "Community 146"
Cohesion: 0.08
Nodes (20): headers, ADR-0035, aiAccessReady(), askMcp(), createAiAccess(), putAside(), headers, ADR-0016 (+12 more)

### Community 147 - "Community 147"
Cohesion: 0.14
Nodes (7): InstanceProbeTest, json(), probe(), ProbeCache, ProbeInstances, probeRepository(), ProbeTokens

### Community 148 - "Community 148"
Cohesion: 0.10
Nodes (23): CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError, CertificationExpiry, CertificationInput, CertificationView, createCertificationService(), cleaned() (+15 more)

### Community 149 - "Community 149"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 151 - "Community 151"
Cohesion: 0.08
Nodes (25): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+17 more)

### Community 152 - "Community 152"
Cohesion: 0.13
Nodes (23): managementOf(), EquipmentError, ADR-0034, ADR-0053, checkCost(), checkDates(), checkWindow(), createEquipmentService() (+15 more)

### Community 153 - "Community 153"
Cohesion: 0.11
Nodes (20): BOM, Json, num(), obj(), positive(), scaled(), suuntoFitKey(), time() (+12 more)

### Community 154 - "Community 154"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 155 - "Community 155"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 156 - "Community 156"
Cohesion: 0.17
Nodes (3): FirebaseRuntime, hasPlayServices(), FirebaseRuntimeTest

### Community 158 - "Community 158"
Cohesion: 0.13
Nodes (6): DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, WaitingPushView

### Community 159 - "Community 159"
Cohesion: 0.21
Nodes (7): DiveViewRecordingsItemSummary, assembleToolSources(), epochMs(), recentDiveEnd(), sacOf(), summaryOf(), ToolSourcesStoreTest

### Community 160 - "Community 160"
Cohesion: 0.17
Nodes (7): Connected, Hint, HintFrame, LiveEvent, LiveHint, SseLiveHints, LiveHintsTest

### Community 161 - "Community 161"
Cohesion: 0.24
Nodes (6): Body, Call, Harness, Offline, Reply, WriteReplayerTest

### Community 162 - "Community 162"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 163 - "Community 163"
Cohesion: 0.23
Nodes (5): Body, ConflictCall, ConflictHarness, ConflictReply, WriteConflictResolutionTest

### Community 164 - "Community 164"
Cohesion: 0.14
Nodes (18): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+10 more)

### Community 165 - "Community 165"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 166 - "Community 166"
Cohesion: 0.14
Nodes (4): FakeCachedMe, FakeInstances, FakeTokens, SyncAuthTest

### Community 167 - "Community 167"
Cohesion: 0.10
Nodes (17): account, accountRelations, apikey, rateLimit, sessionRelations, ADR-0011, ADR-0012, ADR-0035 (+9 more)

### Community 168 - "Community 168"
Cohesion: 0.13
Nodes (19): DAY_MS, MAX_OFFSET_MS, inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), PlacedTime, placeLocalTime() (+11 more)

### Community 169 - "Community 169"
Cohesion: 0.12
Nodes (3): SyncWorker, WorkManagerPeriodicSync, PeriodicSyncScheduler

### Community 172 - "Community 172"
Cohesion: 0.18
Nodes (15): ApiToolsBottomTimeGet200ResponseLimits, powInt(), roundTo(), Sac, SacCylinder, SacDive, SacMissing, cylinder_incomplete (+7 more)

### Community 173 - "Community 173"
Cohesion: 0.13
Nodes (16): stubDive(), ADR-0015, ADR-0023, ADR-0025, clearQueue(), fetchState(), guardedRoute(), leaveConflict() (+8 more)

### Community 174 - "Community 174"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 176 - "Community 176"
Cohesion: 0.15
Nodes (5): ApiDiveCentresPostRequest, ApiDiveCentresPostRequestExternalIdsItem, DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 177 - "Community 177"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 178 - "Community 178"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource/poppins@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 179 - "Community 179"
Cohesion: 0.16
Nodes (17): RecordingSummary, UtcOffsetSource, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput (+9 more)

### Community 180 - "Community 180"
Cohesion: 0.14
Nodes (18): serviceRecord, serviceRecordSchedule, serviceSchedule, equipmentList(), EquipmentListFilter, EquipmentRecordRead, today(), ADR-0034 (+10 more)

### Community 181 - "Community 181"
Cohesion: 0.12
Nodes (10): managesDiveDiver(), CandidateError, createCandidateService(), lockMine(), participantsOfDives(), createLogbookService(), deletedDiveIds(), deletedDives() (+2 more)

### Community 182 - "Community 182"
Cohesion: 0.16
Nodes (16): errorMessage(), date(), FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+8 more)

### Community 183 - "Community 183"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 184 - "Community 184"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 185 - "Community 185"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 186 - "Community 186"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 188 - "Community 188"
Cohesion: 0.14
Nodes (13): BufferedLog, LOG_BUFFER_CAPACITY_BYTES, LOG_BUFFER_CAPACITY_ENTRIES, LogBufferStats, LogRingBufferOptions, SupportBundle, ClientDiagnosticsProvider, FailedBackup (+5 more)

### Community 189 - "Community 189"
Cohesion: 0.18
Nodes (15): clientVersionGate(), ADR-0076, ClientVersionRegistry, MIN_CLIENT_VERSIONS, MinClientVersion, ADR-0076, ADR-0076, compareIdentifier() (+7 more)

### Community 190 - "Community 190"
Cohesion: 0.15
Nodes (3): PushPreferences, ApiPushConfigGet200Response, ApiPushConfigGet200ResponseAndroid

### Community 191 - "Community 191"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 192 - "Community 192"
Cohesion: 0.12
Nodes (16): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+8 more)

### Community 193 - "Community 193"
Cohesion: 0.16
Nodes (14): execPnpm(), FORBIDDEN, isPermissive(), licenseProblems(), main(), ADR-0006, ADR-0086, PERMISSIVE (+6 more)

### Community 194 - "Community 194"
Cohesion: 0.43
Nodes (12): conflictCylindersText(), conflictCylinderText(), conflictFeedbackText(), conflictFieldLabel(), conflictFieldValue(), conflictLeadText(), conflictStartText(), conflictSuitText() (+4 more)

### Community 195 - "Community 195"
Cohesion: 0.19
Nodes (6): logbookDiveItemIndex(), DiveListViewMonthsItem, localMonthOf(), LogbookMonth, monthGroups(), MonthGroupsTest

### Community 198 - "Community 198"
Cohesion: 0.14
Nodes (14): AL80, EstimateDiveCylinder, LEAD_CASES, leadInput(), LeadVectorInput, planOf(), STEEL_12, ADR-0031 (+6 more)

### Community 199 - "Community 199"
Cohesion: 0.17
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 204 - "Community 204"
Cohesion: 0.19
Nodes (4): CentreView, CentreViewExternalIdsItem, CentreViewSitesItem, CentreViewVerificationCode

### Community 205 - "Community 205"
Cohesion: 0.19
Nodes (4): DiveDetailsListView, DiveDetailsListViewCounts, DiveDetailsListViewMonthsItem, DiveDetailsListViewTotals

### Community 206 - "Community 206"
Cohesion: 0.12
Nodes (13): clientDiagnostic, clientDiagnosticsSettings, ClientDiagnosticsEntryInput, ClientDiagnosticsEntryView, ClientDiagnosticsError, ClientDiagnosticsUpload, KEEP_NEWEST, MAX_UPLOAD_ENTRIES (+5 more)

### Community 207 - "Community 207"
Cohesion: 0.15
Nodes (15): SuitType, SuitUndergarment, WeightingFelt, Suit, WeightingFeedback, LeadEstimateAnswer, LeadVectorDive, Confidence (+7 more)

### Community 208 - "Community 208"
Cohesion: 0.15
Nodes (13): CIRCUIT, circuitFromFit, DECO_MODEL, decoModelFromFit, DIVE_ALERT, DIVE_MODE, ADR-0015, WATER_TYPE (+5 more)

### Community 209 - "Community 209"
Cohesion: 0.19
Nodes (13): asEstimateInput(), evaluateLeadVectorCase(), LEAD_VECTOR_KERNEL, leadVectorFile(), VECTOR_KERNELS, VectorFile, vectorFile(), assertMatch() (+5 more)

### Community 210 - "Community 210"
Cohesion: 0.21
Nodes (11): buildSupportBundle(), buildTarGz(), json(), ndjson(), octal(), SUPPORT_BUNDLE_FORMAT, SUPPORT_BUNDLE_FORMAT_VERSION, SupportBufferManifest (+3 more)

### Community 211 - "Community 211"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 212 - "Community 212"
Cohesion: 0.13
Nodes (11): Dive, EGYPT, ELSEWHERE, Method, POSITION, runPurge(), Site, SiteImport (+3 more)

### Community 213 - "Community 213"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 216 - "Community 216"
Cohesion: 0.33
Nodes (4): ConflictField, DiveMerge, MergeOutcome, ParticipantsMergeOutcome

### Community 217 - "Community 217"
Cohesion: 0.20
Nodes (11): DIVE_KINDS, ImportView, ImportViewSchema, toImportView(), ADR-0030, ImportRouteDeps, importRoutes(), ADR-0044 (+3 more)

### Community 218 - "Community 218"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 219 - "Community 219"
Cohesion: 0.13
Nodes (6): FakeBroadcastChannel, FakeEventSource, Listener, locks, start(), ADR-0066

### Community 220 - "Community 220"
Cohesion: 0.13
Nodes (14): concurrency, cancel-in-progress, group, env, RELEASE_TAG, jobs, publish, name (+6 more)

### Community 222 - "Community 222"
Cohesion: 0.15
Nodes (8): FitAdapter, SuuntoJsonAdapter, UddfAdapter, ParsedRecording, adapter, crossCheck(), decodeOfficial(), privateDir

### Community 223 - "Community 223"
Cohesion: 0.20
Nodes (5): LogRingBuffer, RedactionCounters, SupportBundleInput, SupportBundleManifest, SupportServiceDeps

### Community 224 - "Community 224"
Cohesion: 0.15
Nodes (10): FIT_EPOCH_MS, makeSuuntoAppFit(), shape(), SuuntoDiveOptions, ADR-0006, ADR-0037, out, ADR-0038 (+2 more)

### Community 225 - "Community 225"
Cohesion: 0.15
Nodes (13): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, ADR-0076, ADR-0083, ADR-0085 (+5 more)

### Community 226 - "Community 226"
Cohesion: 0.14
Nodes (13): if, runs-on, steps, timeout-minutes, jobs, dco-check, name, on (+5 more)

### Community 227 - "Community 227"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 228 - "Community 228"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 229 - "Community 229"
Cohesion: 0.17
Nodes (10): EstimateInput, AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031 (+2 more)

### Community 230 - "Community 230"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 231 - "Community 231"
Cohesion: 0.48
Nodes (3): UiState, sessionDestination(), SessionDestinationTest

### Community 232 - "Community 232"
Cohesion: 0.32
Nodes (10): ConflictsRoute, DiveEditRoute, InstanceRoute, LogbookRoute, MainRoute, ScanRoute, SettingsDebugRoute, SettingsRoute (+2 more)

### Community 235 - "Community 235"
Cohesion: 0.26
Nodes (3): DiverDetailsView, DiverDetailsViewAccountsItem, DiverDetailsViewCodesItem

### Community 236 - "Community 236"
Cohesion: 0.26
Nodes (3): DiverView, DiverViewCertifications, DiverViewCertificationsNewest

### Community 237 - "Community 237"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 238 - "Community 238"
Cohesion: 0.29
Nodes (3): pushFoundNewDive(), PushHint, PushHintTest

### Community 240 - "Community 240"
Cohesion: 0.18
Nodes (5): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053

### Community 241 - "Community 241"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 242 - "Community 242"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 243 - "Community 243"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 245 - "Community 245"
Cohesion: 0.44
Nodes (8): AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint, SketchProfile, toAscentBand(), toSketchProfile()

### Community 247 - "Community 247"
Cohesion: 0.33
Nodes (8): prepareEntry(), DENIED_KEY_PARTS, isDeniedKey(), maskString(), redactInner(), redactLogObject(), RedactOptions, redactValue()

### Community 248 - "Community 248"
Cohesion: 0.36
Nodes (9): describe(), isObject(), keyDifferences(), pause(), recording(), runAnswerChecks(), sampleCount(), testDive() (+1 more)

### Community 249 - "Community 249"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 250 - "Community 250"
Cohesion: 0.20
Nodes (10): verify, prerelease, sha, tag, versionCode, versionName, name, outputs (+2 more)

### Community 251 - "Community 251"
Cohesion: 0.20
Nodes (10): with, cache, distribution, fetch-depth, if-no-files-found, java-version, name, path (+2 more)

### Community 252 - "Community 252"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 255 - "Community 255"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 257 - "Community 257"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 258 - "Community 258"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 259 - "Community 259"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 261 - "Community 261"
Cohesion: 0.43
Nodes (3): DiverSearchTest, json(), SearchHarness

### Community 263 - "Community 263"
Cohesion: 0.29
Nodes (5): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, EstimateCylinder

### Community 264 - "Community 264"
Cohesion: 0.43
Nodes (8): emptyRedactionCounters(), createSupportService(), assemble(), clientDiagnostics(), configSection(), failedJobs(), healthSection(), maskDatabaseUrl()

### Community 265 - "Community 265"
Cohesion: 0.36
Nodes (6): headers, open(), ownId(), ownRow(), sectionButton(), ADR-0050

### Community 266 - "Community 266"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 267 - "Community 267"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 269 - "Community 269"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 270 - "Community 270"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 271 - "Community 271"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 272 - "Community 272"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 273 - "Community 273"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 274 - "Community 274"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build

### Community 275 - "Community 275"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 276 - "Community 276"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 282 - "Community 282"
Cohesion: 0.50
Nodes (4): SyncPhase, Details, Meta, Profiles

### Community 283 - "Community 283"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 284 - "Community 284"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 285 - "Community 285"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1467 isolated node(s):** `Idle`, `KeptServer`, `Requeued`, `Reachable`, `Unknown` (+1462 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 4276 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **186 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `DiveStore` connect `Community 30` to `Community 135`, `Community 10`, `Community 18`, `Community 159`, `Community 161`, `Community 163`, `Community 36`, `Community 166`, `Community 39`, `Community 45`, `Community 46`, `Community 47`, `Community 56`, `Community 58`, `Community 60`, `Community 63`, `Community 67`, `Community 70`, `Community 71`, `Community 201`, `Community 83`, `Community 105`, `Community 112`, `Community 117`, `Community 125`?**
  _High betweenness centrality (0.020) - this node is a cross-community bridge._
- **What connects `Idle`, `KeptServer`, `Requeued` to the rest of the system?**
  _1467 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.013702644689631137 - nodes in this community are weakly interconnected._
- **Why does `drizzle-orm` connect `Community 0` to `Community 129`, `Community 1`, `Community 3`, `Community 4`, `Community 6`, `Community 11`, `Community 12`, `Community 14`, `Community 148`, `Community 21`, `Community 23`, `Community 151`, `Community 28`, `Community 31`, `Community 33`, `Community 34`, `Community 167`, `Community 168`, `Community 41`, `Community 49`, `Community 50`, `Community 180`, `Community 54`, `Community 188`, `Community 61`, `Community 69`, `Community 75`, `Community 206`, `Community 80`, `Community 81`, `Community 100`, `Community 109`, `Community 120`, `Community 121`?**
  _High betweenness centrality (0.013) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.014840386484931453 - nodes in this community are weakly interconnected._
- **Why does `DiveHubTheme()` connect `Community 78` to `Community 134`, `Community 10`, `Community 142`, `Community 19`, `Community 20`, `Community 150`, `Community 25`, `Community 157`, `Community 170`, `Community 171`, `Community 48`, `Community 56`, `Community 58`, `Community 187`, `Community 67`, `Community 196`, `Community 68`, `Community 72`, `Community 202`, `Community 203`, `Community 90`, `Community 95`, `Community 233`, `Community 234`?**
  _High betweenness centrality (0.012) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.01929777539533637 - nodes in this community are weakly interconnected._