# Graph Report - dive_hub  (2026-11-26)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 13249 nodes · 41065 edges · 441 communities (247 shown, 194 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 989 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `054b926b`
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
- Community 278
- Community 280
- Community 281
- Community 282
- Community 283
- Community 284
- Community 285
- Community 286
- Community 287
- Community 288
- Community 289
- Community 290
- Community 291
- Community 297
- Community 299

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 211 edges
2. `Button()` - 204 edges
3. `useErrorText()` - 176 edges
4. `useDisplay()` - 165 edges
5. `drizzle-orm` - 155 edges
6. `unwrap()` - 143 edges
7. `DiveStore` - 139 edges
8. `Muted()` - 139 edges
9. `createTestDatabase()` - 115 edges
10. `TestDatabase` - 114 edges

## Surprising Connections (you probably didn't know these)
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `RepositoryHarness` --calls--> `LogbookRepository`  [INFERRED]
  apps/android/shared/src/commonTest/kotlin/dev/divehub/shared/logbook/LogbookTest.kt → apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/logbook/LogbookRepository.kt
- `AppNavHost()` --calls--> `DiveRoute`  [INFERRED]
  apps/android/app/src/main/kotlin/dev/divehub/android/ui/navigation/AppNavHost.kt → apps/android/app/src/main/kotlin/dev/divehub/android/ui/navigation/AppRoutes.kt

## Import Cycles
- None detected.

## Communities (441 total, 194 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (287): user, accountListEntry, buddySuggestion, changeEvent, connection, cylinder, dive, diveLease (+279 more)

### Community 1 - "Community 1"
Cohesion: 0.04
Nodes (226): ChangePassword(), DisplaySettings(), ReportProblemSettings(), SecuritySettings(), Sessions(), ADR-0080, useDescribeAgent(), DeleteUserDialog() (+218 more)

### Community 2 - "Community 2"
Cohesion: 0.02
Nodes (170): acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), AppLockProbe (+162 more)

### Community 3 - "Community 3"
Cohesion: 0.02
Nodes (172): ClientDiagnosticsSettingsView, clientDiagnosticsStatusQuery(), ClientDiagnosticsStatusView, ADR-0080, DiveView, importQuery(), ADR-0030, keys (+164 more)

### Community 4 - "Community 4"
Cohesion: 0.02
Nodes (196): cylinderCatalogueQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas (+188 more)

### Community 5 - "Community 5"
Cohesion: 0.01
Nodes (171): actorType, aiAccessOutcome, backupRun, backupRunKind, backupRunStatus, backupSettings, candidateResolution, centreSource (+163 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (90): cylinderLine(), cylinderPressures(), Cylinders(), Direction, Back, Left, Right, DirectionIcon() (+82 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (158): replyCertificationError, STATUS, CertificationService, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams (+150 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (156): Queryable, LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, renumber(), renumberStatement(), ADR-0048, accountKey() (+148 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (147): label(), @testing-library/react@^16.3.3, Me, AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery() (+139 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (144): externalDiver(), mergeExternalDiver(), participations(), waitingOf(), open(), close(), AutomationSettings, createConnectionService() (+136 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (144): EMPTY_SITE_IMPORT_COUNTS, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, diveSite (+136 more)

### Community 12 - "Community 12"
Cohesion: 0.02
Nodes (22): AppContainer, KeystoreTokenStore, PreferencesCachedMeStore, PreferencesInstanceUrlStore, PreferencesPollingIntervalStore, UserCacheStore, PushController, PushPreferences (+14 more)

### Community 13 - "Community 13"
Cohesion: 0.03
Nodes (51): DangerButton(), InkButton(), SecondaryButton(), DiveRow(), Figures(), RowFacts(), EmptyState(), FactRow() (+43 more)

### Community 14 - "Community 14"
Cohesion: 0.02
Nodes (121): lead, forSsi(), remoteBuddy(), SSI_CAPABILITIES, SsiCallLog, ADR-0024, ADR-0027, ADR-0029 (+113 more)

### Community 15 - "Community 15"
Cohesion: 0.03
Nodes (137): LEAD_PLACEMENTS, PARTICIPANT_ROLES, downsampleMinMax(), DUE_STATES, AiAccessScope, seesPositions(), buddiesOf(), BuddySearch (+129 more)

### Community 16 - "Community 16"
Cohesion: 0.03
Nodes (121): Auth, CentreService, CentreRouteDeps, WEIGHTING_FELT, DiverService, DiverRouteDeps, CertificationHint, CertificationHintExpiry (+113 more)

### Community 17 - "Community 17"
Cohesion: 0.03
Nodes (107): auth, BackupLog, BackupServiceDeps, createServices(), ServiceParts, ADR-0027, ADR-0030, ADR-0061 (+99 more)

### Community 18 - "Community 18"
Cohesion: 0.03
Nodes (130): ComputerChoice, DiveImportMode, DiveKind, importOriginal, ProviderImportPlan, UtcOffsetSource, mergeProviderCylinder(), ProviderCylinder (+122 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (117): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+109 more)

### Community 20 - "Community 20"
Cohesion: 0.03
Nodes (122): logbookCheckAnswer, logbookCheckDiveAnswer, agreeInDepthAndDuration(), CheckedDive, DeletableDive, findChecks(), followsSoon(), inChains() (+114 more)

### Community 21 - "Community 21"
Cohesion: 0.06
Nodes (20): ErrorCodes, CylinderMaterial, aluminium, carbon, steel, Dive, PendingWriteKind, Action (+12 more)

### Community 22 - "Community 22"
Cohesion: 0.03
Nodes (24): AndroidDiveStore, SettingsDiagnosticsState, SettingsQueueState, SettingsUiState, SettingsViewModel, FakeDiagnostics, FakeInstances, SettingsRouteTest (+16 more)

### Community 23 - "Community 23"
Cohesion: 0.04
Nodes (82): FieldButton(), CylinderFields(), CylinderProblemText(), CylindersFields(), FieldPair(), GearEntryHeading(), GearNumberField(), LeadFields() (+74 more)

### Community 24 - "Community 24"
Cohesion: 0.05
Nodes (77): Formats, SyncLine(), formatSyncTime(), GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard() (+69 more)

### Community 25 - "Community 25"
Cohesion: 0.04
Nodes (113): allButDive(), continuationCandidateQuery(), ContinuationCandidateView, deletedDivesOn(), deletedDivesQuery(), diveQuery(), DiveValues, LogbookCheckView (+105 more)

### Community 26 - "Community 26"
Cohesion: 0.03
Nodes (31): DiveEditUiState, DiveEditViewModel, DiverChoice, DiverSearchState, EditSource, Failed, Found, Idle (+23 more)

### Community 27 - "Community 27"
Cohesion: 0.03
Nodes (93): createServer(), createAuth(), loadAuthSecret(), workerDeps(), Config, createDb(), findChangedMigrations(), migrateDatabase() (+85 more)

### Community 28 - "Community 28"
Cohesion: 0.04
Nodes (105): divesQuery(), LogbookPage, PAGE_SIZE, providersQuery(), pushRunActive(), pushRunBody(), PushRunChoices, PushRunPreview (+97 more)

### Community 29 - "Community 29"
Cohesion: 0.04
Nodes (26): LogbookViewModel, CountingDispatcher, LogbookViewModelTest, DiveListView, DiveRepository, neighboursPath(), countOf(), FilterChip (+18 more)

### Community 30 - "Community 30"
Cohesion: 0.06
Nodes (98): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, AssessmentService (+90 more)

### Community 31 - "Community 31"
Cohesion: 0.03
Nodes (95): AdminSettings(), ClientDiagnosticsSetting(), Invitations(), ProviderSiteData(), ADR-0012, ADR-0013, ADR-0030, ADR-0080 (+87 more)

### Community 32 - "Community 32"
Cohesion: 0.06
Nodes (3): Spacing, SectionTest, LoadingScreenshotTest

### Community 33 - "Community 33"
Cohesion: 0.03
Nodes (76): hashPassword(), invitation, userRole, AttemptLimit, clientKey(), ADR-0061, Problem, ProblemCode (+68 more)

### Community 34 - "Community 34"
Cohesion: 0.04
Nodes (48): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+40 more)

### Community 35 - "Community 35"
Cohesion: 0.03
Nodes (83): continuationAppend, equipmentUse, pushRunItem, PushRunLeftOut, managedDiverIds(), Participant, asksFor(), DIVE_ONLY (+75 more)

### Community 36 - "Community 36"
Cohesion: 0.04
Nodes (26): notificationsAllowed(), DiveDetailRoute(), DiveHubApp(), DiveHubScreens, ErrorText(), Gate(), InstanceForm(), InstanceStatus() (+18 more)

### Community 37 - "Community 37"
Cohesion: 0.04
Nodes (12): MainActivity, followStoreWrites(), ScanErrors, LoadingsUiState, LoadingsViewModel, ToolsViewModel, SignInEmailRequest, HealthView (+4 more)

### Community 38 - "Community 38"
Cohesion: 0.04
Nodes (31): rememberReducedMotion(), rememberSkeletonAlpha(), SkeletonBar(), tankLabel(), AvatarCircle(), Error(), FilterAction(), Filters() (+23 more)

### Community 39 - "Community 39"
Cohesion: 0.04
Nodes (67): ImportArea, looksLikeZip(), parseHint(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson() (+59 more)

### Community 40 - "Community 40"
Cohesion: 0.05
Nodes (65): equipmentDueQuery(), Buddies(), Neighbours(), EquipmentDueNotice(), ADR-0034, avatarTone(), initials(), dismissDue() (+57 more)

### Community 41 - "Community 41"
Cohesion: 0.04
Nodes (74): centreQuery(), Position, running(), SiteImportArea, siteImportsQuery(), SiteImportView, siteRevisionsQuery(), SiteRevisionView (+66 more)

### Community 42 - "Community 42"
Cohesion: 0.05
Nodes (71): NoFly, noFlyAfter(), getDiverLoadings, HINT, Input, LoadingsInput, planGasAndBottomTime, ADR-0032 (+63 more)

### Community 43 - "Community 43"
Cohesion: 0.03
Nodes (71): dependencies, @dive-hub/api-client@workspace:*, @fontsource/poppins@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0, qr@0.7.2, react@^19.3.0 (+63 more)

### Community 44 - "Community 44"
Cohesion: 0.08
Nodes (5): LogbookScreenshotTest, LogbookScreenTest, DiveListViewCounts, DiveListViewTotals, DiveProfileView

### Community 45 - "Community 45"
Cohesion: 0.03
Nodes (52): ParticipantRole, ActionContext, Direction, DiveExport, DiveExportAction, DiveListing, FIND_BY, FindBy (+44 more)

### Community 46 - "Community 46"
Cohesion: 0.03
Nodes (51): adapter, crossCheck(), decodeOfficial(), privateDir, out, ADR-0044, out, ADR-0036 (+43 more)

### Community 47 - "Community 47"
Cohesion: 0.07
Nodes (24): QuietButton(), ChangesList(), ConflictChoice(), ConflictEntry(), ConflictsRoute(), ConflictsScreen(), diveTitle(), FieldDecision() (+16 more)

### Community 48 - "Community 48"
Cohesion: 0.04
Nodes (61): FcmServiceAccount, loadConfig(), readFcmConfig(), readJsonObject(), requiredString(), ADR-0011, ADR-0021, ADR-0024 (+53 more)

### Community 49 - "Community 49"
Cohesion: 0.04
Nodes (65): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, Conditions (+57 more)

### Community 50 - "Community 50"
Cohesion: 0.03
Nodes (68): ADR-0025, WaterMismatch, waterMismatch(), EquipmentService, RecordInput, ScheduleInput, DiveGearItemViewSchema, DiveGearViewSchema (+60 more)

### Community 51 - "Community 51"
Cohesion: 0.04
Nodes (61): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+53 more)

### Community 52 - "Community 52"
Cohesion: 0.05
Nodes (17): MainViewModel, Unreachable, ApiMeGet200Response, ApiMeGet200ResponsePreferences, UserView, InstanceUrl, InstanceProbeResult, Loading (+9 more)

### Community 53 - "Community 53"
Cohesion: 0.08
Nodes (52): BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen, PpO2Limits, ambientPressureBar() (+44 more)

### Community 54 - "Community 54"
Cohesion: 0.05
Nodes (64): ConnectionRow, WaitingPushOptions, formatOf(), createImportService(), cancelImport(), createImport(), endWaiting(), expireWaiting() (+56 more)

### Community 55 - "Community 55"
Cohesion: 0.05
Nodes (62): CylinderMaterial, SuitType, SuitUndergarment, WeightingFelt, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, CylinderInput (+54 more)

### Community 56 - "Community 56"
Cohesion: 0.04
Nodes (50): providerConflict, pushRun, waitingPush, kept(), providerError(), ExportingProvider, ProviderError, DELETE_QUIET_MS (+42 more)

### Community 57 - "Community 57"
Cohesion: 0.07
Nodes (18): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), GasPlanInputTest (+10 more)

### Community 58 - "Community 58"
Cohesion: 0.09
Nodes (11): FakeCache, FakeCachedMe, FakeInstances, FakeQueue, FakeTokens, Harness, json(), SessionRepositoryTest (+3 more)

### Community 59 - "Community 59"
Cohesion: 0.05
Nodes (42): captureDiagnostic(), captureError(), cleanContext(), clearDiagnostics(), CLIENT_NAME, CLIENT_VERSION, DiagnosticEntry, diagnosticFromError() (+34 more)

### Community 60 - "Community 60"
Cohesion: 0.06
Nodes (52): providerLease, tidyName(), DiverDetails, Caller, foldName(), nameDiffers(), Buddy, createBuddyService() (+44 more)

### Community 62 - "Community 62"
Cohesion: 0.05
Nodes (15): ApiClientDiagnosticsStatusGet200Response, ApiDevicesPost200Response, ApiDiveSitesGet200Response, ApiMeGet200ResponseOwnDiver, CentreView, CentreViewExternalIdsItem, CentreViewSitesItem, CentreViewVerificationCode (+7 more)

### Community 63 - "Community 63"
Cohesion: 0.08
Nodes (11): SyncCheckpoint, queuedProfileCheckpoint(), Complete, SessionEnded, Stopped, SyncEngine, SyncPhase, Details (+3 more)

### Community 64 - "Community 64"
Cohesion: 0.06
Nodes (60): ANDROID_UI, AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch() (+52 more)

### Community 65 - "Community 65"
Cohesion: 0.07
Nodes (9): DiveHubTopBar(), titleLineHeight(), TitleSkeleton(), CameraChrome, DiveHubColors, DiveHubTheme(), isDarkScheme(), DiveHubTopBarTest (+1 more)

### Community 66 - "Community 66"
Cohesion: 0.08
Nodes (5): DiveDetailViewModelTest, GatedDispatcher, DiveEditViewModelTest, FakeSearch, FoundDiver

### Community 67 - "Community 67"
Cohesion: 0.05
Nodes (12): findActivity(), CameraFailure, cameraFailure(), Blocked, Busy, Missing, CameraScanner(), CameraScannerFrame() (+4 more)

### Community 68 - "Community 68"
Cohesion: 0.09
Nodes (7): LogbookPhase, Error, List, Loading, Offline, SettingsDebugScreenTest, SettingsScreenshotTest

### Community 69 - "Community 69"
Cohesion: 0.06
Nodes (51): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+43 more)

### Community 71 - "Community 71"
Cohesion: 0.07
Nodes (49): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+41 more)

### Community 72 - "Community 72"
Cohesion: 0.05
Nodes (52): adminRole, AI_ACCESS_NAME_MAX, AI_ACCESS_PREFIX, AI_ACCESS_RATE, AuthOptions, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, ADR-0011 (+44 more)

### Community 73 - "Community 73"
Cohesion: 0.06
Nodes (36): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+28 more)

### Community 74 - "Community 74"
Cohesion: 0.06
Nodes (5): QrImageDecoder, LauncherIconTest, SplashTheme, SplashThemeTest, QrImageDecoderTest

### Community 75 - "Community 75"
Cohesion: 0.08
Nodes (52): Answer, answer(), baseFrom(), BodyWeight, Confidence, high, low, medium (+44 more)

### Community 76 - "Community 76"
Cohesion: 0.13
Nodes (22): Body, BrokenBody, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage(), diverJson() (+14 more)

### Community 77 - "Community 77"
Cohesion: 0.05
Nodes (36): BACKUP_RUN_TASK, backupTask(), ADR-0069, PurgeService, PushDispatcher, SCHEDULED_FETCH_TASK, AUTOMATION_TICK_TASK, RevisionEntityType (+28 more)

### Community 78 - "Community 78"
Cohesion: 0.14
Nodes (5): DiveMergeTest, diveObject(), jsonOf(), participants(), WriteQueueTest

### Community 79 - "Community 79"
Cohesion: 0.05
Nodes (37): Tx, jointDiveDismissal, JointFillField, AppendUndo, PushHintSink, ADR-0020, ADR-0056, ADR-0066 (+29 more)

### Community 80 - "Community 80"
Cohesion: 0.06
Nodes (42): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+34 more)

### Community 81 - "Community 81"
Cohesion: 0.07
Nodes (43): AIR, CNS_HALF_LIFE_MINUTES, DEFAULT_CONDITIONS, N2_REPLAY_WINDOW_HOURS, N2_STALE_AFTER_HOURS, N2Loadings, N2LoadingsBase, N2UnavailableReason (+35 more)

### Community 82 - "Community 82"
Cohesion: 0.07
Nodes (45): diveModeOfSsi(), keepTraining(), ssiDiveType(), ssiInteger(), ADR-0057, asNumber(), asText(), buildUpdateRecord() (+37 more)

### Community 83 - "Community 83"
Cohesion: 0.15
Nodes (9): FeedEntry, RefreshData, refreshDetailJson(), refreshDiveListItem(), refreshDiverJson(), refreshRecordingJson(), refreshSiteJson(), resetRefreshData() (+1 more)

### Community 84 - "Community 84"
Cohesion: 0.06
Nodes (42): ADR-0036, ADR-0063, ADR-0038, ADR-0048, ADR-0062, AiAccess, assessedDive(), AssessedFinding (+34 more)

### Community 85 - "Community 85"
Cohesion: 0.07
Nodes (10): ApiClient, ApiErrorBody, DecodeException, DiveHubException, NoAnswerException, decodeList(), ProviderDirectory, providerDirectory() (+2 more)

### Community 86 - "Community 86"
Cohesion: 0.10
Nodes (11): ConflictSide, Server, ThisPhone, Applied, Conflicted, Dropped, EntryOutcome, Failed (+3 more)

### Community 87 - "Community 87"
Cohesion: 0.05
Nodes (39): CYLINDER_MATERIALS, SUIT_TYPES, SUIT_UNDERGARMENTS, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053 (+31 more)

### Community 88 - "Community 88"
Cohesion: 0.05
Nodes (42): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+34 more)

### Community 89 - "Community 89"
Cohesion: 0.09
Nodes (39): EstimateDive, EstimateDiveCylinder, WeightingFeedback, WeightingFelt, right, too_heavy, too_light, LoadingDive (+31 more)

### Community 90 - "Community 90"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 91 - "Community 91"
Cohesion: 0.11
Nodes (18): Answers, number(), BottomTime, bottomTime(), BottomTimeLimits, BottomTimeOutside, depth, gas (+10 more)

### Community 92 - "Community 92"
Cohesion: 0.09
Nodes (10): DiveListViewDivesItem, DiveListViewDivesItemGasesItem, DiveListViewDivesItemParticipantsItem, DiveListViewDivesItemSite, DiveListViewMonthsItem, localMonthOf(), toListRow(), dive() (+2 more)

### Community 95 - "Community 95"
Cohesion: 0.06
Nodes (40): Change(), Changes(), historyRoute, Made(), PositionSchema, AdoptBody, Country, CreateBody (+32 more)

### Community 97 - "Community 97"
Cohesion: 0.05
Nodes (40): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+32 more)

### Community 98 - "Community 98"
Cohesion: 0.08
Nodes (37): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+29 more)

### Community 99 - "Community 99"
Cohesion: 0.09
Nodes (10): ProfileRead, DiveView, DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewLeadItem, DiveViewSac, DiveViewSite, DiveViewSuit (+2 more)

### Community 100 - "Community 100"
Cohesion: 0.10
Nodes (7): IntHolder, MainViewModelPendingWarningTest, WarningCache, WarningCachedMe, WarningInstances, WarningQueue, WarningTokens

### Community 101 - "Community 101"
Cohesion: 0.05
Nodes (38): depends_on, environment, image, ports, restart, volumes, condition, environment (+30 more)

### Community 102 - "Community 102"
Cohesion: 0.10
Nodes (15): LogbookPaging, AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint, SketchProfile, sketchScale() (+7 more)

### Community 103 - "Community 103"
Cohesion: 0.17
Nodes (37): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestEntries(), entryNamed(), flag() (+29 more)

### Community 104 - "Community 104"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 105 - "Community 105"
Cohesion: 0.11
Nodes (6): WritesViewModel, WritesViewModelTest, ConflictResolution, KeptServer, Requeued, WriteQueueSnapshot

### Community 107 - "Community 107"
Cohesion: 0.11
Nodes (10): ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseCns, ApiDiversIdLoadingsGet200ResponseN2, ApiDiversIdLoadingsGet200ResponseNoFly, ApiDiversIdLoadingsGet200ResponseNoFlySource, ApiDiversIdLoadingsGet200ResponseOtu, cnsDecayed() (+2 more)

### Community 108 - "Community 108"
Cohesion: 0.07
Nodes (28): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError (+20 more)

### Community 109 - "Community 109"
Cohesion: 0.09
Nodes (24): SsiClient, buildCreateRecord(), buildDeleteRecord(), emptyRecord(), writableRecord(), deleteTestDive(), ClearChecks, clearDive() (+16 more)

### Community 110 - "Community 110"
Cohesion: 0.07
Nodes (28): headers, ADR-0030, ADR-0040, conflictForLena(), diveWithoutRecording(), editInSsi(), externalDiver(), importLena() (+20 more)

### Community 111 - "Community 111"
Cohesion: 0.12
Nodes (33): importsQuery(), ImportView, uploadFile(), Logbook(), ImportContext, ImportFilesButton(), ImportHistory(), ImportList() (+25 more)

### Community 112 - "Community 112"
Cohesion: 0.11
Nodes (8): AppNavHost(), AppScreens, clearSavedTabStates(), isShowing(), leaveDiveForLogbook(), MainShell(), navigateToTab(), tabOf()

### Community 113 - "Community 113"
Cohesion: 0.13
Nodes (6): LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200ResponseBasis, ApiDiversIdLeadEstimateGet200ResponseBasisDivesItem, ApiDiversIdLeadEstimateGet200ResponseMissingItem

### Community 114 - "Community 114"
Cohesion: 0.11
Nodes (28): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+20 more)

### Community 115 - "Community 115"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 116 - "Community 116"
Cohesion: 0.13
Nodes (33): DEFAULT_SSI_URL, acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findNewId() (+25 more)

### Community 117 - "Community 117"
Cohesion: 0.10
Nodes (26): ADR-0051, Status, ADR-0056, ADR-0024, ADR-0043, clearQueue(), conflictAtSsi(), connectSsi() (+18 more)

### Community 118 - "Community 118"
Cohesion: 0.09
Nodes (5): FakeCache, FakeCachedMe, FakeInstances, FakeTokens, MainViewModelTest

### Community 119 - "Community 119"
Cohesion: 0.13
Nodes (7): GateCachedMe, GateCall, GateClient, GateInstances, GateServer, GateTokens, WriteExitGateTest

### Community 120 - "Community 120"
Cohesion: 0.13
Nodes (28): DiveOrigin, sampleCount(), asBool(), DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, aboveZero() (+20 more)

### Community 121 - "Community 121"
Cohesion: 0.12
Nodes (6): DiveEditParticipant, DiveViewSuitDraftSource, numberOrNull(), toJson(), DiveLocalEdit, participant()

### Community 123 - "Community 123"
Cohesion: 0.08
Nodes (26): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+18 more)

### Community 124 - "Community 124"
Cohesion: 0.09
Nodes (26): AL80, asEstimateInput(), EstimateDiveCylinder, evaluateLeadVectorCase(), LEAD_CASES, LEAD_VECTOR_KERNEL, leadInput(), leadVectorFile() (+18 more)

### Community 125 - "Community 125"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 126 - "Community 126"
Cohesion: 0.23
Nodes (7): leadAnswer(), EstimateCylinder, EstimateInput, PlannedConditions, Suit, leadEstimate(), PersonalizedToolsTest

### Community 127 - "Community 127"
Cohesion: 0.15
Nodes (3): ScanViewModelTest, ScanResult, ReadCodes

### Community 128 - "Community 128"
Cohesion: 0.13
Nodes (8): InstanceProbeTest, json(), probe(), ProbeCache, ProbeCachedMe, ProbeInstances, probeRepository(), ProbeTokens

### Community 129 - "Community 129"
Cohesion: 0.10
Nodes (24): SsiRecord, DiveForSsi, FakeSsi, BuddySaved, access(), client, out, pause (+16 more)

### Community 130 - "Community 130"
Cohesion: 0.12
Nodes (12): WriteDrain, Drained, Empty, SessionEnded, Stopped, WriteReplayResult, SyncDrainTest, WriteDrain (+4 more)

### Community 131 - "Community 131"
Cohesion: 0.08
Nodes (26): CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043, CentreChanged (+18 more)

### Community 132 - "Community 132"
Cohesion: 0.07
Nodes (12): buddySite(), Dive, OTHER, PendingOffer, ADR-0012, ADR-0070, Dive, OTHER (+4 more)

### Community 133 - "Community 133"
Cohesion: 0.10
Nodes (27): applyHints(), LiveArrivals, Page, useLiveArrivals(), bump(), changedListeners, ChannelMessage, healthListeners (+19 more)

### Community 134 - "Community 134"
Cohesion: 0.10
Nodes (7): ToolPage, Gas, Index, Lead, Loadings, Plan, FakeScreens

### Community 136 - "Community 136"
Cohesion: 0.16
Nodes (4): ToolsScreenshotTest, PlanningSacSource, default, logbook

### Community 137 - "Community 137"
Cohesion: 0.18
Nodes (10): Body, buddyAnswer(), centreAnswer(), centreView(), diverView(), Offline, ScanCall, ScanHarness (+2 more)

### Community 138 - "Community 138"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 139 - "Community 139"
Cohesion: 0.10
Nodes (26): globalSetup(), ADR-0023, accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks() (+18 more)

### Community 140 - "Community 140"
Cohesion: 0.11
Nodes (7): ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh, ApiToolsBottomTimeGet200ResponseUsedSacLpm, ApiToolsBottomTimeGet200ResponseUsedWater

### Community 141 - "Community 141"
Cohesion: 0.11
Nodes (7): ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport

### Community 142 - "Community 142"
Cohesion: 0.13
Nodes (23): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, Site (+15 more)

### Community 143 - "Community 143"
Cohesion: 0.17
Nodes (26): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+18 more)

### Community 144 - "Community 144"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 145 - "Community 145"
Cohesion: 0.13
Nodes (22): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DueInput, DueSchedule (+14 more)

### Community 146 - "Community 146"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 147 - "Community 147"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 148 - "Community 148"
Cohesion: 0.12
Nodes (7): LeadAnswer, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseLeftOut, ApiDiversIdLeadEstimateGet200ResponseRange, ApiDiversIdLeadEstimateGet200ResponseReserve, ApiDiversIdLeadEstimateGet200ResponseWeightCheck

### Community 151 - "Community 151"
Cohesion: 0.08
Nodes (25): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+17 more)

### Community 152 - "Community 152"
Cohesion: 0.12
Nodes (22): ScheduleDue, DiveGear, DueEntry, GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow (+14 more)

### Community 153 - "Community 153"
Cohesion: 0.11
Nodes (19): BOM, Json, num(), obj(), positive(), scaled(), SuuntoJsonAdapter, time() (+11 more)

### Community 154 - "Community 154"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 157 - "Community 157"
Cohesion: 0.13
Nodes (6): ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting

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
Cohesion: 0.17
Nodes (5): DiveDetailScreen(), title(), NeighboursView, NeighboursViewNext, NeighboursViewPrevious

### Community 165 - "Community 165"
Cohesion: 0.14
Nodes (20): Body, Hold, Offline, refreshDeletedJson(), refreshDetailsPage(), refreshDiveId(), refreshDivePage(), refreshDiveStartsAt() (+12 more)

### Community 166 - "Community 166"
Cohesion: 0.23
Nodes (5): Body, ConflictCall, ConflictHarness, ConflictReply, WriteConflictResolutionTest

### Community 167 - "Community 167"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 168 - "Community 168"
Cohesion: 0.14
Nodes (4): FakeCachedMe, FakeInstances, FakeTokens, SyncAuthTest

### Community 169 - "Community 169"
Cohesion: 0.12
Nodes (19): siteImportStatus, PROBLEM_CODES, SiteImportRouteDeps, StartBody, STATUS, ADR-0021, SiteImportError, SiteImportService (+11 more)

### Community 170 - "Community 170"
Cohesion: 0.16
Nodes (21): managementOf(), EquipmentError, checkCost(), checkDates(), checkWindow(), createEquipmentService(), assemble(), checkResets() (+13 more)

### Community 171 - "Community 171"
Cohesion: 0.26
Nodes (18): ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres(), isHypoxic(), metresPerBar() (+10 more)

### Community 172 - "Community 172"
Cohesion: 0.18
Nodes (19): describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), lastStatuses(), localTime(), passedToRecord(), REPORT (+11 more)

### Community 173 - "Community 173"
Cohesion: 0.12
Nodes (3): SyncWorker, WorkManagerPeriodicSync, PeriodicSyncScheduler

### Community 175 - "Community 175"
Cohesion: 0.13
Nodes (19): equipmentItem, serviceRecord, serviceRecordSchedule, serviceSchedule, equipmentList(), EquipmentListFilter, EquipmentRecordRead, today() (+11 more)

### Community 176 - "Community 176"
Cohesion: 0.14
Nodes (17): createFileFormats(), createFitAdapter(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf() (+9 more)

### Community 177 - "Community 177"
Cohesion: 0.12
Nodes (19): DiveGearItemView, diveGearQuery(), DiveGearView, EquipmentCategory, EquipmentDetailView, EquipmentDueView, EquipmentItemView, equipmentOfDiverQuery() (+11 more)

### Community 178 - "Community 178"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 180 - "Community 180"
Cohesion: 0.15
Nodes (5): ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits

### Community 181 - "Community 181"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 182 - "Community 182"
Cohesion: 0.15
Nodes (17): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+9 more)

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
Cohesion: 0.22
Nodes (4): DiveDetailUiState, DiveDetailViewModel, NeighbourDive, NeighboursRead

### Community 188 - "Community 188"
Cohesion: 0.19
Nodes (11): LeadInputTest, SuitType, drysuit, none, semidry, skin, wetsuit, SuitUndergarment (+3 more)

### Community 189 - "Community 189"
Cohesion: 0.13
Nodes (17): SUGGESTION_STATUSES, DiveProfileView, AcceptBody, STATUS, SuggestionRouteDeps, ADR-0070, SuggestionService, DiverRef (+9 more)

### Community 190 - "Community 190"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 191 - "Community 191"
Cohesion: 0.14
Nodes (15): JOINT_FILL_FIELDS, OfferRouteDeps, STATUS, ADR-0070, OfferError, OfferService, Facts, Field (+7 more)

### Community 192 - "Community 192"
Cohesion: 0.12
Nodes (16): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+8 more)

### Community 193 - "Community 193"
Cohesion: 0.19
Nodes (14): RecordingSummary, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput, round3() (+6 more)

### Community 194 - "Community 194"
Cohesion: 0.17
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 195 - "Community 195"
Cohesion: 0.17
Nodes (3): FirebaseRuntime, hasPlayServices(), resultOrNull()

### Community 197 - "Community 197"
Cohesion: 0.47
Nodes (12): conflictCylindersText(), conflictCylinderText(), conflictFeedbackText(), conflictFieldLabel(), conflictFieldValue(), conflictLeadText(), conflictStartText(), conflictSuitText() (+4 more)

### Community 200 - "Community 200"
Cohesion: 0.19
Nodes (4): ApiToolsBottomTimeGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseLimits, ApiToolsBottomTimeGet200ResponseResidualN2

### Community 201 - "Community 201"
Cohesion: 0.19
Nodes (4): DiveDetailsListView, DiveDetailsListViewCounts, DiveDetailsListViewMonthsItem, DiveDetailsListViewTotals

### Community 202 - "Community 202"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 203 - "Community 203"
Cohesion: 0.12
Nodes (13): clientDiagnostic, clientDiagnosticsSettings, ClientDiagnosticsEntryInput, ClientDiagnosticsEntryView, ClientDiagnosticsError, ClientDiagnosticsUpload, KEEP_NEWEST, MAX_UPLOAD_ENTRIES (+5 more)

### Community 204 - "Community 204"
Cohesion: 0.21
Nodes (11): buildSupportBundle(), buildTarGz(), json(), ndjson(), octal(), SUPPORT_BUNDLE_FORMAT, SUPPORT_BUNDLE_FORMAT_VERSION, SupportBufferManifest (+3 more)

### Community 205 - "Community 205"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 206 - "Community 206"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 208 - "Community 208"
Cohesion: 0.19
Nodes (11): decimal(), dive(), makeOceanicPlusUddf(), OCEANIC_DIVES, OceanicDive, site(), waypoint(), exported (+3 more)

### Community 209 - "Community 209"
Cohesion: 0.13
Nodes (6): FakeBroadcastChannel, FakeEventSource, Listener, locks, start(), ADR-0066

### Community 210 - "Community 210"
Cohesion: 0.13
Nodes (14): concurrency, cancel-in-progress, group, env, RELEASE_TAG, jobs, publish, name (+6 more)

### Community 211 - "Community 211"
Cohesion: 0.27
Nodes (5): ApiVerificationCodesReadPost200Response, ApiVerificationCodesReadPost200ResponseCandidatesItem, ReadChange, ReadCode, ReadExisting

### Community 212 - "Community 212"
Cohesion: 0.33
Nodes (4): ConflictField, DiveMerge, MergeOutcome, ParticipantsMergeOutcome

### Community 213 - "Community 213"
Cohesion: 0.20
Nodes (5): LogRingBuffer, RedactionCounters, SupportBundleInput, SupportBundleManifest, SupportServiceDeps

### Community 214 - "Community 214"
Cohesion: 0.22
Nodes (11): fromOsm(), fromSsi(), fromWd(), HOUSE_REEF, osm(), site(), ssi(), ADR-0021 (+3 more)

### Community 215 - "Community 215"
Cohesion: 0.14
Nodes (13): if, runs-on, steps, timeout-minutes, jobs, dco-check, name, on (+5 more)

### Community 216 - "Community 216"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 217 - "Community 217"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 218 - "Community 218"
Cohesion: 0.23
Nodes (4): DiveEditCylinder, DiveEditLead, DiveEditSeries, toEditDraft()

### Community 219 - "Community 219"
Cohesion: 0.31
Nodes (11): Sac, SacCylinder, SacDive, SacMissing, cylinder_incomplete, no_average_depth, too_short, sacOfDive() (+3 more)

### Community 220 - "Community 220"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 221 - "Community 221"
Cohesion: 0.17
Nodes (12): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, ADR-0076, ADR-0083, ADR-0085 (+4 more)

### Community 222 - "Community 222"
Cohesion: 0.32
Nodes (10): ConflictsRoute, DiveEditRoute, InstanceRoute, LogbookRoute, MainRoute, ScanRoute, SettingsDebugRoute, SettingsRoute (+2 more)

### Community 225 - "Community 225"
Cohesion: 0.26
Nodes (3): DiverDetailsView, DiverDetailsViewAccountsItem, DiverDetailsViewCodesItem

### Community 226 - "Community 226"
Cohesion: 0.26
Nodes (3): DiverView, DiverViewCertifications, DiverViewCertificationsNewest

### Community 227 - "Community 227"
Cohesion: 0.26
Nodes (3): DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 228 - "Community 228"
Cohesion: 0.18
Nodes (10): LogBufferStats, SupportBundle, ClientDiagnosticsProvider, FailedBackup, FailedJob, HealthSection, iso(), JobsSection (+2 more)

### Community 229 - "Community 229"
Cohesion: 0.17
Nodes (10): Dive, HOUSE_REEF, LIGHTHOUSE, Method, Page, Position, Revision, Site (+2 more)

### Community 230 - "Community 230"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 231 - "Community 231"
Cohesion: 0.29
Nodes (3): pushFoundNewDive(), PushHint, PushHintTest

### Community 233 - "Community 233"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 234 - "Community 234"
Cohesion: 0.22
Nodes (8): diveRow(), rowOf(), ADR-0026, ADR-0048, diveRows(), goToMore(), expectFirstDiveInView(), rowOf()

### Community 235 - "Community 235"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 236 - "Community 236"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 237 - "Community 237"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 238 - "Community 238"
Cohesion: 0.53
Nodes (3): UiState, sessionDestination(), SessionDestinationTest

### Community 240 - "Community 240"
Cohesion: 0.22
Nodes (9): REVISION_ENTITY_TYPES, CHANGES_MAX_LIMIT, SyncService, ChangeEventView, ChangesQuery, ChangesView, STATUS, SyncRouteDeps (+1 more)

### Community 241 - "Community 241"
Cohesion: 0.33
Nodes (8): prepareEntry(), DENIED_KEY_PARTS, isDeniedKey(), maskString(), redactInner(), redactLogObject(), RedactOptions, redactValue()

### Community 242 - "Community 242"
Cohesion: 0.24
Nodes (8): ExternalIdView, needsOsmAttribution(), siteOrigin(), ADR-0021, offer, osm, ssi, wikidata

### Community 243 - "Community 243"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 244 - "Community 244"
Cohesion: 0.20
Nodes (10): verify, prerelease, sha, tag, versionCode, versionName, name, outputs (+2 more)

### Community 245 - "Community 245"
Cohesion: 0.20
Nodes (10): with, cache, distribution, fetch-depth, if-no-files-found, java-version, name, path (+2 more)

### Community 246 - "Community 246"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 250 - "Community 250"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 251 - "Community 251"
Cohesion: 0.28
Nodes (7): ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, s(), ADR-0035

### Community 252 - "Community 252"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 253 - "Community 253"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 254 - "Community 254"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 260 - "Community 260"
Cohesion: 0.43
Nodes (3): DiverSearchTest, json(), SearchHarness

### Community 263 - "Community 263"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 264 - "Community 264"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 266 - "Community 266"
Cohesion: 0.52
Nodes (7): emptyRedactionCounters(), createSupportService(), assemble(), clientDiagnostics(), configSection(), failedJobs(), healthSection()

### Community 267 - "Community 267"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 268 - "Community 268"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 269 - "Community 269"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 271 - "Community 271"
Cohesion: 0.40
Nodes (5): stubDive(), ADR-0015, ADR-0023, ADR-0025, guardedRoute()

### Community 272 - "Community 272"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 273 - "Community 273"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 274 - "Community 274"
Cohesion: 0.40
Nodes (5): ensureItemForDevice(), scheduleView(), today(), trimName(), resolveDevice()

### Community 275 - "Community 275"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build

### Community 276 - "Community 276"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 277 - "Community 277"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 278 - "Community 278"
Cohesion: 0.40
Nodes (4): TAGS, ADR-0023, { stdout, stderr }, total

### Community 286 - "Community 286"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 287 - "Community 287"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 288 - "Community 288"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

### Community 289 - "Community 289"
Cohesion: 0.67
Nodes (3): better-auth@1.7.7, better-auth@1.7.7, better-auth

## Knowledge Gaps
- **1402 isolated node(s):** `KeptServer`, `Requeued`, `SignIn`, `SignOut`, `Sent` (+1397 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 4117 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **194 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `DiveStore` connect `Community 26` to `Community 130`, `Community 137`, `Community 12`, `Community 21`, `Community 22`, `Community 156`, `Community 29`, `Community 159`, `Community 161`, `Community 164`, `Community 37`, `Community 165`, `Community 166`, `Community 168`, `Community 57`, `Community 63`, `Community 66`, `Community 196`, `Community 76`, `Community 78`, `Community 83`, `Community 86`, `Community 89`, `Community 94`, `Community 99`, `Community 119`, `Community 121`, `Community 127`?**
  _High betweenness centrality (0.015) - this node is a cross-community bridge._
- **What connects `KeptServer`, `Requeued`, `SignIn` to the rest of the system?**
  _1402 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.01773277414642133 - nodes in this community are weakly interconnected._
- **Why does `Button()` connect `Community 1` to `Community 3`, `Community 4`, `Community 40`, `Community 9`, `Community 41`, `Community 111`, `Community 177`, `Community 19`, `Community 25`, `Community 28`, `Community 31`?**
  _High betweenness centrality (0.011) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.043437862950058075 - nodes in this community are weakly interconnected._
- **Why does `AppContainer` connect `Community 12` to `Community 265`, `Community 22`, `Community 26`, `Community 156`, `Community 29`, `Community 160`, `Community 36`, `Community 37`, `Community 173`, `Community 52`, `Community 57`, `Community 63`, `Community 195`, `Community 86`, `Community 231`, `Community 105`, `Community 121`, `Community 122`, `Community 255`?**
  _High betweenness centrality (0.011) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.022486772486772486 - nodes in this community are weakly interconnected._