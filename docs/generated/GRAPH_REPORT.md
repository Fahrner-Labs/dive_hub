# Graph Report - dive_hub  (2026-11-24)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 11889 nodes · 36372 edges · 392 communities (237 shown, 155 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 815 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `8ad0f8a4`
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
- Community 255
- Community 256
- Community 257
- Community 258
- Community 259
- Community 260
- Community 265
- Community 267

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 211 edges
2. `Button()` - 204 edges
3. `useErrorText()` - 176 edges
4. `useDisplay()` - 165 edges
5. `drizzle-orm` - 150 edges
6. `unwrap()` - 143 edges
7. `Muted()` - 139 edges
8. `DiveStore` - 124 edges
9. `createTestDatabase()` - 111 edges
10. `TestDatabase` - 110 edges

## Surprising Connections (you probably didn't know these)
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `Harness` --calls--> `SessionRepository`  [INFERRED]
  apps/android/shared/src/commonTest/kotlin/dev/divehub/shared/SessionRepositoryTest.kt → apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/SessionRepository.kt
- `EquipmentListFilter` --references--> `EquipmentCategory`  [EXTRACTED]
  apps/server/src/equipment/read.ts → apps/server/src/vocabulary.ts

## Import Cycles
- None detected.

## Communities (392 total, 155 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (270): user, buddySuggestion, changeEvent, dive, diveAssessment, diveFinding, diveLease, diver (+262 more)

### Community 1 - "Community 1"
Cohesion: 0.02
Nodes (241): DiveView, cylinderCatalogueQuery(), equipmentOfDiverQuery(), CylinderFields(), MATERIALS, ADR-0034, ADR-0045, useCatalogueName() (+233 more)

### Community 2 - "Community 2"
Cohesion: 0.05
Nodes (186): DisplaySettings(), AdminSettings(), ClientDiagnosticsSetting(), DeleteUserDialog(), Invitations(), Invite(), LinkToPassOn(), ProviderSiteData() (+178 more)

### Community 3 - "Community 3"
Cohesion: 0.02
Nodes (161): APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), AppLockProbe, probeAppLock() (+153 more)

### Community 4 - "Community 4"
Cohesion: 0.02
Nodes (151): ClientDiagnosticsSettingsView, clientDiagnosticsStatusQuery(), ClientDiagnosticsStatusView, ADR-0080, CertificationView, DeviceView, DiverDetailsView, ExternalDiverView (+143 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (145): label(), Me, AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery() (+137 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (122): createServer(), auth, loadAuthSecret(), acquireAppLock(), BackupServiceDeps, createServices(), ServiceParts, Services (+114 more)

### Community 7 - "Community 7"
Cohesion: 0.02
Nodes (125): pushRun, Capabilities, Delivered, DIVE_FIELDS, ExportingProvider, ProviderError, createSsiAdapter(), buddyListOf() (+117 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (146): ComputerChoice, DiveImportMode, DiveKind, importOriginal, ImportOutcome, providerSiteData, UtcOffsetSource, cylindersOf() (+138 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (139): Nullable(), AiAccessScope, seesPositions(), VerifiedAccess, ProfileSummary, round1(), Series, summariseProfile() (+131 more)

### Community 10 - "Community 10"
Cohesion: 0.02
Nodes (136): actorType, aiAccessOutcome, backupRunKind, backupRunStatus, candidateResolution, centreSource, CERTIFICATION_IMAGE_STATES, CERTIFICATION_VERIFICATIONS (+128 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (129): buddiesForQuery(), buddiesQuery(), BuddyView, ConflictChoices, ConflictEntryView, ConflictFieldView, ConflictsOfConnection, ConnectionView (+121 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (71): cylinderPressures(), Cylinders(), Direction, Back, Left, Right, DirectionIcon(), DiveBody() (+63 more)

### Community 13 - "Community 13"
Cohesion: 0.03
Nodes (118): CentreService, CentreRouteDeps, LEAD_PLACEMENTS, UTC_OFFSET_SOURCES, WEIGHTING_FELT, DiverService, downsampleMinMax(), DiveCertifications (+110 more)

### Community 14 - "Community 14"
Cohesion: 0.02
Nodes (98): ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS, eachFile(), readEntry(), ADR-0044, walk() (+90 more)

### Community 15 - "Community 15"
Cohesion: 0.04
Nodes (21): AndroidDiveStore, DiveStore, toBoolean(), toLong(), toModel(), BodyWeight, Cylinder, Diver (+13 more)

### Community 16 - "Community 16"
Cohesion: 0.04
Nodes (31): DiveRow(), Figures(), RowFacts(), EmptyState(), FactRow(), NoteText(), StackedNoteText(), Radius (+23 more)

### Community 17 - "Community 17"
Cohesion: 0.03
Nodes (99): accountListEntry, device, diverExternalId, providerLease, tidyName(), ADR-0028, ADR-0043, ADR-0070 (+91 more)

### Community 18 - "Community 18"
Cohesion: 0.04
Nodes (103): LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, renumber(), renumberStatement(), ADR-0048, LogbookCheckService, AppendRow (+95 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (33): DiveDetailViewModel, NeighboursRead, ProfileRead, LogbookUiState, LogbookViewModel, followStoreWrites(), DiveListView, DiveListViewDivesItem (+25 more)

### Community 20 - "Community 20"
Cohesion: 0.04
Nodes (103): Queryable, logbookCheckAnswer, logbookCheckDiveAnswer, keepApart(), keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration() (+95 more)

### Community 21 - "Community 21"
Cohesion: 0.04
Nodes (106): LogbookPage, PAGE_SIZE, connectionsQuery(), pushRunActive(), pushRunBody(), PushRunChoices, PushRunPreview, pushRunPreviewQuery() (+98 more)

### Community 22 - "Community 22"
Cohesion: 0.06
Nodes (12): FakeCache, RevisionFollowTest, LeadEstimateViewModelTest, ErrorCodes, Dive, PendingWriteStatus, Conflicted, Failed (+4 more)

### Community 23 - "Community 23"
Cohesion: 0.03
Nodes (94): replyCertificationError, STATUS, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, CertificationRouteDeps (+86 more)

### Community 24 - "Community 24"
Cohesion: 0.04
Nodes (96): waitingOf(), open(), close(), createConnectionService(), checkAutomation(), checkFollowNumbers(), expiryOf(), onTurn() (+88 more)

### Community 25 - "Community 25"
Cohesion: 0.04
Nodes (74): equipmentDueQuery(), cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043 (+66 more)

### Community 26 - "Community 26"
Cohesion: 0.03
Nodes (16): AppContainer, KeystoreTokenStore, PreferencesCachedMeStore, PreferencesInstanceUrlStore, UserCacheStore, FakeCachedMe, FakeInstances, FakeTokens (+8 more)

### Community 27 - "Community 27"
Cohesion: 0.06
Nodes (86): buildApp(), AssessmentService, AssessmentRouteDeps, assessmentRoutes(), AssessmentViewSchema, FindingView, Rule, STATUS (+78 more)

### Community 28 - "Community 28"
Cohesion: 0.04
Nodes (48): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+40 more)

### Community 29 - "Community 29"
Cohesion: 0.05
Nodes (77): ChangePassword(), ReportProblemSettings(), SecuritySettings(), Sessions(), ADR-0080, useDescribeAgent(), AiAccessLogEntry, AiAccessView (+69 more)

### Community 30 - "Community 30"
Cohesion: 0.04
Nodes (76): Auth, CandidateRouteDeps, CandidateView, Decided, DiveBrief, OffsetSource, STATUS, ADR-0016 (+68 more)

### Community 31 - "Community 31"
Cohesion: 0.04
Nodes (81): CertificationService, CYLINDER_MATERIALS, DIVE_IMPORT_MODES, AssessmentRefresh, ADR-0036, ADR-0053, ErrorAnswer, providerProblem() (+73 more)

### Community 32 - "Community 32"
Cohesion: 0.05
Nodes (21): MainActivity, gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number() (+13 more)

### Community 33 - "Community 33"
Cohesion: 0.08
Nodes (32): DiveHubException, LiveHint, LiveHints, Body, FeedEntry, Hold, Offline, RefreshData (+24 more)

### Community 34 - "Community 34"
Cohesion: 0.05
Nodes (30): InkButton(), QuietButton(), rememberReducedMotion(), rememberSkeletonAlpha(), SkeletonBar(), AvatarCircle(), Error(), FilterAction() (+22 more)

### Community 35 - "Community 35"
Cohesion: 0.04
Nodes (26): DiveHubApp(), DiveHubScreens, ErrorText(), Gate(), InstanceForm(), InstanceStatus(), LoadingScreen(), PasswordField() (+18 more)

### Community 36 - "Community 36"
Cohesion: 0.04
Nodes (72): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+64 more)

### Community 37 - "Community 37"
Cohesion: 0.05
Nodes (36): cylinderLine(), Facts(), leadText(), profileSummaryText(), timeZoneText(), Water(), DiveDetailText, diveLocalTime() (+28 more)

### Community 38 - "Community 38"
Cohesion: 0.05
Nodes (70): divesQuery(), isUnauthorized(), App(), BackupsPage, CentrePage, CentresPage, currentRoute(), DivePage (+62 more)

### Community 39 - "Community 39"
Cohesion: 0.08
Nodes (65): Formats, formatSyncTime(), GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult() (+57 more)

### Community 40 - "Community 40"
Cohesion: 0.05
Nodes (67): NoFly, getDiverLoadings, HINT, Input, LoadingsInput, planGasAndBottomTime, ADR-0032, ADR-0035 (+59 more)

### Community 41 - "Community 41"
Cohesion: 0.05
Nodes (63): ImportArea, looksLikeZip(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy() (+55 more)

### Community 42 - "Community 42"
Cohesion: 0.04
Nodes (15): SettingsDiagnosticsState, SettingsUiState, SettingsViewModel, FakeDiagnostics, FakeInstances, SettingsViewModelTest, ApiClientDiagnosticsPostRequest, ApiClientDiagnosticsPostRequestClient (+7 more)

### Community 43 - "Community 43"
Cohesion: 0.06
Nodes (72): allButDive(), continuationCandidateQuery(), ContinuationCandidateView, deletedDivesOn(), deletedDivesQuery(), diveQuery(), LogbookCheckView, mergeCandidatesQuery() (+64 more)

### Community 44 - "Community 44"
Cohesion: 0.06
Nodes (7): DiveHubTopBar(), titleLineHeight(), TitleSkeleton(), DiveHubTheme(), SectionTest, DiveHubTopBarTest, SettingsRouteTest

### Community 45 - "Community 45"
Cohesion: 0.04
Nodes (67): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, Conditions (+59 more)

### Community 46 - "Community 46"
Cohesion: 0.08
Nodes (7): LogbookScreenshotTest, DiveListViewCounts, DiveListViewDivesItemGasesItem, DiveListViewDivesItemParticipantsItem, DiveListViewTotals, DiveProfileView, toListRow()

### Community 47 - "Community 47"
Cohesion: 0.04
Nodes (64): diveSite, pushRunItem, PushRunLeftOut, DIVE_ONLY, DIVE_ONLY_PATTERN, DiveListFilter, diveListFilter(), DiveOnly (+56 more)

### Community 48 - "Community 48"
Cohesion: 0.06
Nodes (68): ANDROID_UI, AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), clearFailed(), clearPassed() (+60 more)

### Community 49 - "Community 49"
Cohesion: 0.05
Nodes (65): continuationAppend, recordingEvent, RecordingSummary, sampleSeries, defined(), Joined, joinedSummary(), joinParts() (+57 more)

### Community 50 - "Community 50"
Cohesion: 0.04
Nodes (63): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, WaitingPushViewSchema, EquipmentService (+55 more)

### Community 51 - "Community 51"
Cohesion: 0.03
Nodes (42): ParticipantRole, Direction, DiveExport, DiveExportAction, DiveListing, FIND_BY, FindBy, Operation (+34 more)

### Community 52 - "Community 52"
Cohesion: 0.06
Nodes (62): ConnectionRow, WaitingPushOptions, audienceOfConnection(), audienceOfDive(), audienceOfDiver(), createScheduledFetch(), blocked(), due() (+54 more)

### Community 53 - "Community 53"
Cohesion: 0.05
Nodes (42): captureDiagnostic(), captureError(), cleanContext(), clearDiagnostics(), CLIENT_NAME, CLIENT_VERSION, DiagnosticEntry, diagnosticFromError() (+34 more)

### Community 54 - "Community 54"
Cohesion: 0.05
Nodes (56): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+48 more)

### Community 55 - "Community 55"
Cohesion: 0.16
Nodes (3): DiveDetailScreenshotTest, NeighboursView, NeighboursViewNext

### Community 56 - "Community 56"
Cohesion: 0.05
Nodes (45): diverBodyWeight, diverManagement, equipmentItem, lead, managedDiverIds(), managesDiveDiver(), ADR-0016, CandidateError (+37 more)

### Community 57 - "Community 57"
Cohesion: 0.08
Nodes (4): DiveDetailViewModelTest, GatedDispatcher, CountingDispatcher, LogbookViewModelTest

### Community 58 - "Community 58"
Cohesion: 0.09
Nodes (11): ChangeFeedEvent, ChangeFeedPage, Complete, SessionEnded, Stopped, SyncEngine, SyncPhase, Details (+3 more)

### Community 59 - "Community 59"
Cohesion: 0.06
Nodes (54): EMPTY_SITE_IMPORT_COUNTS, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, Context (+46 more)

### Community 60 - "Community 60"
Cohesion: 0.12
Nodes (24): Body, BrokenBody, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage(), diverJson() (+16 more)

### Community 61 - "Community 61"
Cohesion: 0.08
Nodes (51): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+43 more)

### Community 62 - "Community 62"
Cohesion: 0.05
Nodes (53): adminRole, AI_ACCESS_NAME_MAX, AI_ACCESS_PREFIX, AI_ACCESS_RATE, AuthOptions, CLIENT_IP_HEADER, createAuth(), PUBLIC_AUTH_PATHS (+45 more)

### Community 63 - "Community 63"
Cohesion: 0.05
Nodes (45): BackupRouteDeps, BACKUP_RUN_TASK, BackupService, backupTask(), ADR-0069, PurgeService, PROCESS_IMPORT_TASK, PUSH_RUN_STEP_TASK (+37 more)

### Community 64 - "Community 64"
Cohesion: 0.05
Nodes (46): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError (+38 more)

### Community 65 - "Community 65"
Cohesion: 0.08
Nodes (3): LoadingScreenshotTest, OnboardingScreenTest, SettingsDebugScreenTest

### Community 66 - "Community 66"
Cohesion: 0.06
Nodes (21): DiveListViewMonthsItem, LogbookPaging, localMonthOf(), monthGroups(), AscentBand, SketchCurve, SketchGeometry, sketchGeometry() (+13 more)

### Community 67 - "Community 67"
Cohesion: 0.07
Nodes (48): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+40 more)

### Community 68 - "Community 68"
Cohesion: 0.06
Nodes (36): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+28 more)

### Community 69 - "Community 69"
Cohesion: 0.09
Nodes (46): diveSiteExternalId, dataIdsOf(), settleSiteVisibility(), outgoingSite(), findOrMake(), SiteForSend, siteForSend(), ADR-0057 (+38 more)

### Community 70 - "Community 70"
Cohesion: 0.06
Nodes (47): RequirementView, ExternalIdView, useFocusBackWhenClosed(), holdLeave(), mayLeave(), useLeaveGuard(), needsOsmAttribution(), siteOrigin() (+39 more)

### Community 71 - "Community 71"
Cohesion: 0.10
Nodes (41): ambientPressureBar(), bestMixPercent(), Conditions, depthOfPressure(), eadMetres(), endMetres(), GasMix, isHypoxic() (+33 more)

### Community 72 - "Community 72"
Cohesion: 0.07
Nodes (26): ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh, ApiToolsBottomTimeGet200ResponseUsedSacLpm, ApiToolsBottomTimeGet200ResponseUsedWater, BottomTime (+18 more)

### Community 73 - "Community 73"
Cohesion: 0.06
Nodes (6): logShareChooser(), DiveHubApplicationTest, LauncherIconTest, SplashTheme, SplashThemeTest, LogShareTest

### Community 74 - "Community 74"
Cohesion: 0.06
Nodes (44): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+36 more)

### Community 75 - "Community 75"
Cohesion: 0.13
Nodes (10): DiveRoute, InstanceRoute, LogbookRoute, MainRoute, SettingsDebugRoute, SettingsRoute, SignInRoute, ToolsRoute (+2 more)

### Community 76 - "Community 76"
Cohesion: 0.05
Nodes (44): ProviderImportPlan, ADR-0025, WaterMismatch, waterMismatch(), RemoteSite, SsiLogbookSite, SsiSiteValues, ExternalIdRow (+36 more)

### Community 77 - "Community 77"
Cohesion: 0.06
Nodes (43): DiveValues, OverridableField, revisionsQuery(), RevisionView, UtcOffsetSource, DiveHistory(), Entry(), isValue() (+35 more)

### Community 78 - "Community 78"
Cohesion: 0.09
Nodes (8): LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseCns, ApiDiversIdLoadingsGet200ResponseN2, ApiDiversIdLoadingsGet200ResponseOtu

### Community 79 - "Community 79"
Cohesion: 0.09
Nodes (47): Answer, answer(), baseFrom(), Confidence, high, low, medium, Cylinder (+39 more)

### Community 80 - "Community 80"
Cohesion: 0.07
Nodes (37): errorMessage(), date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser() (+29 more)

### Community 81 - "Community 81"
Cohesion: 0.10
Nodes (40): ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres(), isHypoxic(), metresPerBar() (+32 more)

### Community 82 - "Community 82"
Cohesion: 0.08
Nodes (41): Leave, asBool(), DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+33 more)

### Community 83 - "Community 83"
Cohesion: 0.06
Nodes (42): ADR-0036, ADR-0063, ADR-0038, ADR-0048, ADR-0062, AiAccess, assessedDive(), AssessedFinding (+34 more)

### Community 84 - "Community 84"
Cohesion: 0.08
Nodes (40): CylinderMaterial, aluminium, carbon, steel, EstimateDive, EstimateDiveCylinder, WeightingFeedback, WeightingFelt (+32 more)

### Community 85 - "Community 85"
Cohesion: 0.05
Nodes (39): SUIT_TYPES, SUIT_UNDERGARMENTS, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053, replyDiverError (+31 more)

### Community 86 - "Community 86"
Cohesion: 0.05
Nodes (42): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+34 more)

### Community 87 - "Community 87"
Cohesion: 0.08
Nodes (6): ApiClient, ApiErrorBody, DecodeException, NoAnswerException, DiveLocalEdit, ApiClientTest

### Community 88 - "Community 88"
Cohesion: 0.06
Nodes (37): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+29 more)

### Community 89 - "Community 89"
Cohesion: 0.08
Nodes (5): ApiDiverBodyWeightsGet200ResponseItem, DiverView, StreamFailure, SyncRefresher, toStreamFailure()

### Community 90 - "Community 90"
Cohesion: 0.16
Nodes (6): FakeCachedMe, FakeQueue, FakeTokens, Harness, json(), SessionRepositoryTest

### Community 91 - "Community 91"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 93 - "Community 93"
Cohesion: 0.09
Nodes (18): ConflictFieldView, ConflictResolution, ConflictSide, Server, ThisPhone, KeptServer, Requeued, WriteConflictView (+10 more)

### Community 94 - "Community 94"
Cohesion: 0.08
Nodes (32): Config, loadConfig(), ADR-0011, ADR-0021, ADR-0024, ADR-0025, createDb(), appliedMigrationHead() (+24 more)

### Community 95 - "Community 95"
Cohesion: 0.06
Nodes (40): Change(), Changes(), historyRoute, Made(), REVISION_CAUSES, PositionSchema, AdoptBody, Country (+32 more)

### Community 96 - "Community 96"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 97 - "Community 97"
Cohesion: 0.05
Nodes (39): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+31 more)

### Community 98 - "Community 98"
Cohesion: 0.13
Nodes (15): bottomTime(), PlanningWater, en13319, fresh, salt, planningSac(), PlanningSacDive, PlanningSacResult (+7 more)

### Community 99 - "Community 99"
Cohesion: 0.06
Nodes (5): SyncWorker, WorkManagerPeriodicSync, PeriodicSyncScheduler, SseLiveHintsEngineTest, TrickleServer

### Community 100 - "Community 100"
Cohesion: 0.10
Nodes (9): DiveView, DiveViewLeadItem, DiveViewSac, DiveViewSite, DiveViewSuit, DiveViewValues, DiveViewValuesStartsAt, DiveViewWeightingFeedback (+1 more)

### Community 101 - "Community 101"
Cohesion: 0.17
Nodes (37): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestEntries(), entryNamed(), flag() (+29 more)

### Community 102 - "Community 102"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 103 - "Community 103"
Cohesion: 0.08
Nodes (34): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+26 more)

### Community 104 - "Community 104"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 105 - "Community 105"
Cohesion: 0.09
Nodes (13): LeadAnswer, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseLeftOut, ApiDiversIdLeadEstimateGet200ResponseMissingItem, ApiDiversIdLeadEstimateGet200ResponseRange, ApiDiversIdLeadEstimateGet200ResponseReserve, ApiDiversIdLeadEstimateGet200ResponseWeightCheck (+5 more)

### Community 106 - "Community 106"
Cohesion: 0.09
Nodes (25): SsiClient, buildCreateRecord(), buildDeleteRecord(), emptyRecord(), writableRecord(), deleteTestDive(), ClearChecks, clearDive() (+17 more)

### Community 107 - "Community 107"
Cohesion: 0.07
Nodes (28): headers, ADR-0030, ADR-0040, conflictForLena(), diveWithoutRecording(), editInSsi(), externalDiver(), importLena() (+20 more)

### Community 108 - "Community 108"
Cohesion: 0.07
Nodes (25): Tx, AppendUndo, ADR-0020, ADR-0056, ADR-0066, withRevisionHints(), ChangeListener, removed() (+17 more)

### Community 109 - "Community 109"
Cohesion: 0.13
Nodes (34): DEFAULT_SSI_URL, acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findNewId() (+26 more)

### Community 110 - "Community 110"
Cohesion: 0.08
Nodes (5): DiveViewFromRecording, DiveViewFromRecordingStartsAt, DiveViewPosition, DiveViewWaterMismatch, NeighboursViewPrevious

### Community 111 - "Community 111"
Cohesion: 0.08
Nodes (6): ApiClientDiagnosticsStatusGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseResidualN2, DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 112 - "Community 112"
Cohesion: 0.12
Nodes (27): bar(), celsius(), contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf(), firstOf() (+19 more)

### Community 113 - "Community 113"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 114 - "Community 114"
Cohesion: 0.10
Nodes (26): ADR-0051, Status, ADR-0056, ADR-0024, ADR-0043, clearQueue(), conflictAtSsi(), connectSsi() (+18 more)

### Community 116 - "Community 116"
Cohesion: 0.08
Nodes (24): DELETE_QUIET_MS, Candidate, Deleted, DiveView, Outcome, ADR-0038, adapter, crossCheck() (+16 more)

### Community 117 - "Community 117"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 118 - "Community 118"
Cohesion: 0.13
Nodes (8): InstanceProbeTest, json(), probe(), ProbeCache, ProbeCachedMe, ProbeInstances, probeRepository(), ProbeTokens

### Community 119 - "Community 119"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 120 - "Community 120"
Cohesion: 0.10
Nodes (10): Checking, MainViewModel, Reachability, Reachable, Unknown, Unreachable, InstanceUrl, InstanceProbeResult (+2 more)

### Community 121 - "Community 121"
Cohesion: 0.26
Nodes (8): leadAnswer(), EstimateCylinder, EstimateInput, PlannedConditions, Suit, currentLoadings(), leadEstimate(), PersonalizedToolsTest

### Community 124 - "Community 124"
Cohesion: 0.21
Nodes (4): DiveMergeTest, diveObject(), participant(), participants()

### Community 125 - "Community 125"
Cohesion: 0.11
Nodes (23): createFileFormats(), createFitAdapter(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf() (+15 more)

### Community 126 - "Community 126"
Cohesion: 0.07
Nodes (12): buddySite(), Dive, OTHER, PendingOffer, ADR-0012, ADR-0070, Dive, OTHER (+4 more)

### Community 127 - "Community 127"
Cohesion: 0.12
Nodes (6): AppTab, Logbook, Settings, Tools, DiveHubBottomBar(), BottomBarScreenshotTest

### Community 128 - "Community 128"
Cohesion: 0.14
Nodes (9): Loading, PendingChanges, PendingWritesException, SessionRepository, SessionState, SignedIn, SignedOut, SignOutOutcome (+1 more)

### Community 129 - "Community 129"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 130 - "Community 130"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 131 - "Community 131"
Cohesion: 0.11
Nodes (22): SsiRecord, contextOf(), FakeSsi, BuddySaved, access(), client, out, pause (+14 more)

### Community 132 - "Community 132"
Cohesion: 0.10
Nodes (26): globalSetup(), ADR-0023, accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks() (+18 more)

### Community 133 - "Community 133"
Cohesion: 0.11
Nodes (7): ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport

### Community 134 - "Community 134"
Cohesion: 0.13
Nodes (23): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, Site (+15 more)

### Community 135 - "Community 135"
Cohesion: 0.10
Nodes (18): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, APP (+10 more)

### Community 136 - "Community 136"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 137 - "Community 137"
Cohesion: 0.21
Nodes (4): GasPlanScreenshotTest, GasPlanToolDiver, GasPlanToolInput, GasPlanToolLevel

### Community 139 - "Community 139"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 140 - "Community 140"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 142 - "Community 142"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 143 - "Community 143"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 144 - "Community 144"
Cohesion: 0.08
Nodes (25): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+17 more)

### Community 145 - "Community 145"
Cohesion: 0.11
Nodes (23): AIR, CNS_HALF_LIFE_MINUTES, cnsDecayed(), DEFAULT_CONDITIONS, N2_REPLAY_WINDOW_HOURS, N2_STALE_AFTER_HOURS, N2Loadings, N2LoadingsBase (+15 more)

### Community 146 - "Community 146"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 147 - "Community 147"
Cohesion: 0.13
Nodes (6): ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting

### Community 148 - "Community 148"
Cohesion: 0.13
Nodes (6): DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, WaitingPushView

### Community 149 - "Community 149"
Cohesion: 0.11
Nodes (21): CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043, CentreChanged (+13 more)

### Community 150 - "Community 150"
Cohesion: 0.13
Nodes (21): equipmentUse, DiveGear, ensureItemForDevice(), GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow (+13 more)

### Community 151 - "Community 151"
Cohesion: 0.18
Nodes (23): wallClockMs(), DiveOrigin, REFERENCE_PREFIX, Series, aboveZero(), cylinderOf(), deviceOf(), deviceOfRef() (+15 more)

### Community 152 - "Community 152"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 153 - "Community 153"
Cohesion: 0.17
Nodes (6): Connected, Hint, HintFrame, LiveEvent, SseLiveHints, LiveHintsTest

### Community 154 - "Community 154"
Cohesion: 0.15
Nodes (9): WriteDrain, Drained, Empty, Stopped, WriteReplayResult, SyncDrainTest, WriteDrain, WriteDrain (+1 more)

### Community 155 - "Community 155"
Cohesion: 0.15
Nodes (22): managementOf(), EquipmentError, checkCost(), checkDates(), checkWindow(), createEquipmentService(), assemble(), checkResets() (+14 more)

### Community 156 - "Community 156"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 157 - "Community 157"
Cohesion: 0.17
Nodes (17): ApiToolsBottomTimeGet200ResponseLimits, jsRound(), powInt(), roundTo(), Sac, SacCylinder, SacDive, SacMissing (+9 more)

### Community 158 - "Community 158"
Cohesion: 0.14
Nodes (4): FakeCachedMe, FakeInstances, FakeTokens, SyncAuthTest

### Community 159 - "Community 159"
Cohesion: 0.23
Nodes (5): Body, ConflictCall, ConflictHarness, ConflictReply, WriteConflictResolutionTest

### Community 160 - "Community 160"
Cohesion: 0.25
Nodes (6): Body, Call, Harness, Offline, Reply, WriteReplayerTest

### Community 161 - "Community 161"
Cohesion: 0.13
Nodes (17): providerGoneDive, compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051, candidatesOf() (+9 more)

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
Cohesion: 0.15
Nodes (5): SiteViewExternalIdsItem, SiteViewExternalIdsItemAttribution, SiteViewExternalIdsItemOffered, SiteViewExternalIdsItemOfferedPosition, SiteViewExternalIdsItemPrivateTo

### Community 168 - "Community 168"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource/poppins@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 169 - "Community 169"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 170 - "Community 170"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 171 - "Community 171"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 172 - "Community 172"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 173 - "Community 173"
Cohesion: 0.19
Nodes (11): LeadInputTest, SuitType, drysuit, none, semidry, skin, wetsuit, SuitUndergarment (+3 more)

### Community 174 - "Community 174"
Cohesion: 0.16
Nodes (5): DiagnosticEntry, DiagnosticLevel, ERROR, WARN, DiagnosticsBuffer

### Community 175 - "Community 175"
Cohesion: 0.29
Nodes (5): assembleToolSources(), epochMs(), recentDiveEnd(), summaryOf(), ToolSourcesStoreTest

### Community 178 - "Community 178"
Cohesion: 0.14
Nodes (13): BufferedLog, LOG_BUFFER_CAPACITY_BYTES, LOG_BUFFER_CAPACITY_ENTRIES, LogBufferStats, LogRingBufferOptions, SupportBundle, ClientDiagnosticsProvider, FailedBackup (+5 more)

### Community 179 - "Community 179"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 180 - "Community 180"
Cohesion: 0.12
Nodes (16): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+8 more)

### Community 182 - "Community 182"
Cohesion: 0.14
Nodes (14): AL80, EstimateDiveCylinder, LEAD_CASES, leadInput(), LeadVectorInput, planOf(), STEEL_12, ADR-0031 (+6 more)

### Community 183 - "Community 183"
Cohesion: 0.17
Nodes (12): sampleCount(), storedList(), tryParseJson(), APP_PARAMS, createSsiTransport(), exchange(), isObject(), SSI_APP_USER_AGENT (+4 more)

### Community 184 - "Community 184"
Cohesion: 0.20
Nodes (16): asNumber(), asText(), buddyIdsOf(), buildUpdateRecord(), compareReadBack(), datasetLength(), keysOf(), describe() (+8 more)

### Community 185 - "Community 185"
Cohesion: 0.17
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 186 - "Community 186"
Cohesion: 0.19
Nodes (4): DiveDetailsListView, DiveDetailsListViewCounts, DiveDetailsListViewMonthsItem, DiveDetailsListViewTotals

### Community 187 - "Community 187"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 189 - "Community 189"
Cohesion: 0.12
Nodes (13): clientDiagnostic, clientDiagnosticsSettings, ClientDiagnosticsEntryInput, ClientDiagnosticsEntryView, ClientDiagnosticsError, ClientDiagnosticsUpload, KEEP_NEWEST, MAX_UPLOAD_ENTRIES (+5 more)

### Community 190 - "Community 190"
Cohesion: 0.17
Nodes (14): JOINT_FILL_FIELDS, OfferRouteDeps, STATUS, ADR-0070, OfferService, Facts, Field, JointOfferAcceptView (+6 more)

### Community 191 - "Community 191"
Cohesion: 0.15
Nodes (15): SuitType, SuitUndergarment, WeightingFelt, Suit, WeightingFeedback, LeadEstimateAnswer, LeadVectorDive, Confidence (+7 more)

### Community 192 - "Community 192"
Cohesion: 0.19
Nodes (13): asEstimateInput(), evaluateLeadVectorCase(), LEAD_VECTOR_KERNEL, leadVectorFile(), VECTOR_KERNELS, VectorFile, vectorFile(), assertMatch() (+5 more)

### Community 193 - "Community 193"
Cohesion: 0.21
Nodes (11): buildSupportBundle(), buildTarGz(), json(), ndjson(), octal(), SUPPORT_BUNDLE_FORMAT, SUPPORT_BUNDLE_FORMAT_VERSION, SupportBufferManifest (+3 more)

### Community 194 - "Community 194"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 195 - "Community 195"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 196 - "Community 196"
Cohesion: 0.20
Nodes (4): DiveViewRecordingsItemDevice, DiveViewRecordingsItemSummary, DiveViewRecordingsItemSummaryGasesItem, summaryOf()

### Community 198 - "Community 198"
Cohesion: 0.33
Nodes (4): ConflictField, DiveMerge, MergeOutcome, ParticipantsMergeOutcome

### Community 199 - "Community 199"
Cohesion: 0.13
Nodes (4): FakeCache, FakeInstances, TokenStore, UserScopedStore

### Community 200 - "Community 200"
Cohesion: 0.16
Nodes (14): serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, DueEntry, ScheduleView, equipmentList(), EquipmentListFilter (+6 more)

### Community 201 - "Community 201"
Cohesion: 0.19
Nodes (13): flag(), idText(), isObject(), numberOrNull(), readSsiSite(), ssiSiteRow(), visibilityFor(), WATER_OF_BOW (+5 more)

### Community 202 - "Community 202"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 203 - "Community 203"
Cohesion: 0.13
Nodes (14): concurrency, cancel-in-progress, group, env, RELEASE_TAG, jobs, publish, name (+6 more)

### Community 204 - "Community 204"
Cohesion: 0.35
Nodes (4): UiState, sessionDestination(), SessionDestinationTest, UserView

### Community 205 - "Community 205"
Cohesion: 0.20
Nodes (5): LogRingBuffer, RedactionCounters, SupportBundleInput, SupportBundleManifest, SupportServiceDeps

### Community 206 - "Community 206"
Cohesion: 0.22
Nodes (11): fromOsm(), fromSsi(), fromWd(), HOUSE_REEF, osm(), site(), ssi(), ADR-0021 (+3 more)

### Community 207 - "Community 207"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 208 - "Community 208"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 209 - "Community 209"
Cohesion: 0.23
Nodes (3): ApiMeGet200Response, ApiMeGet200ResponseOwnDiver, ApiMeGet200ResponsePreferences

### Community 210 - "Community 210"
Cohesion: 0.17
Nodes (10): EstimateInput, AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031 (+2 more)

### Community 211 - "Community 211"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 212 - "Community 212"
Cohesion: 0.26
Nodes (3): ApiDiveSitesGet200Response, SiteView, SiteViewPosition

### Community 213 - "Community 213"
Cohesion: 0.26
Nodes (3): DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewCylindersItemSeries

### Community 216 - "Community 216"
Cohesion: 0.17
Nodes (11): account, accountRelations, apikey, rateLimit, sessionRelations, ADR-0011, ADR-0012, ADR-0035 (+3 more)

### Community 217 - "Community 217"
Cohesion: 0.18
Nodes (11): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, ADR-0076, ADR-0083, MODULE_IMPORTS (+3 more)

### Community 218 - "Community 218"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 219 - "Community 219"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 220 - "Community 220"
Cohesion: 0.22
Nodes (8): diveRow(), rowOf(), ADR-0026, ADR-0048, diveRows(), goToMore(), expectFirstDiveInView(), rowOf()

### Community 221 - "Community 221"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 222 - "Community 222"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 223 - "Community 223"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 225 - "Community 225"
Cohesion: 0.33
Nodes (8): prepareEntry(), DENIED_KEY_PARTS, isDeniedKey(), maskString(), redactInner(), redactLogObject(), RedactOptions, redactValue()

### Community 226 - "Community 226"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 227 - "Community 227"
Cohesion: 0.20
Nodes (10): verify, prerelease, sha, tag, versionCode, versionName, name, outputs (+2 more)

### Community 228 - "Community 228"
Cohesion: 0.20
Nodes (10): with, cache, distribution, fetch-depth, if-no-files-found, java-version, name, path (+2 more)

### Community 229 - "Community 229"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 230 - "Community 230"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 231 - "Community 231"
Cohesion: 0.28
Nodes (8): itemIds(), ADR-0026, ADR-0034, Usage, UsageDive, usageMap(), usageOf(), UsageRow

### Community 232 - "Community 232"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 233 - "Community 233"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 234 - "Community 234"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 239 - "Community 239"
Cohesion: 0.29
Nodes (5): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, EstimateCylinder

### Community 240 - "Community 240"
Cohesion: 0.43
Nodes (8): emptyRedactionCounters(), createSupportService(), assemble(), clientDiagnostics(), configSection(), failedJobs(), healthSection(), maskDatabaseUrl()

### Community 241 - "Community 241"
Cohesion: 0.25
Nodes (6): AQABA, ATTERSEE, EILAT, HAUSREEF, MID_ATLANTIC, ADR-0030

### Community 242 - "Community 242"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 243 - "Community 243"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 245 - "Community 245"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 246 - "Community 246"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 247 - "Community 247"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 248 - "Community 248"
Cohesion: 0.40
Nodes (5): stubDive(), ADR-0015, ADR-0023, ADR-0025, guardedRoute()

### Community 249 - "Community 249"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 250 - "Community 250"
Cohesion: 0.40
Nodes (5): LogbookPhase, Error, List, Loading, Offline

### Community 251 - "Community 251"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build

### Community 252 - "Community 252"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 253 - "Community 253"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 255 - "Community 255"
Cohesion: 0.50
Nodes (4): WriteDropReason, Forbidden, Gone, NotFound

### Community 256 - "Community 256"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 257 - "Community 257"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 258 - "Community 258"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1352 isolated node(s):** `SuitThickness`, `Reachable`, `Unknown`, `Reachable`, `Reserve` (+1347 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3885 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **155 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `DiveStore` connect `Community 15` to `Community 19`, `Community 22`, `Community 154`, `Community 26`, `Community 158`, `Community 159`, `Community 32`, `Community 33`, `Community 162`, `Community 160`, `Community 42`, `Community 44`, `Community 175`, `Community 177`, `Community 57`, `Community 58`, `Community 60`, `Community 188`, `Community 84`, `Community 87`, `Community 89`, `Community 93`, `Community 100`?**
  _High betweenness centrality (0.021) - this node is a cross-community bridge._
- **What connects `SuitThickness`, `Reachable`, `Unknown` to the rest of the system?**
  _1352 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.018064257143266763 - nodes in this community are weakly interconnected._
- **Why does `drizzle-orm` connect `Community 0` to `Community 129`, `Community 3`, `Community 6`, `Community 7`, `Community 8`, `Community 9`, `Community 10`, `Community 13`, `Community 14`, `Community 144`, `Community 17`, `Community 18`, `Community 20`, `Community 150`, `Community 23`, `Community 24`, `Community 161`, `Community 36`, `Community 40`, `Community 47`, `Community 49`, `Community 50`, `Community 178`, `Community 52`, `Community 54`, `Community 56`, `Community 59`, `Community 189`, `Community 62`, `Community 63`, `Community 64`, `Community 69`, `Community 200`, `Community 76`, `Community 216`, `Community 94`, `Community 231`, `Community 116`?**
  _High betweenness centrality (0.017) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.020168673361815458 - nodes in this community are weakly interconnected._
- **Why does `AppContainer` connect `Community 26` to `Community 128`, `Community 32`, `Community 153`, `Community 99`, `Community 35`, `Community 162`, `Community 42`, `Community 174`, `Community 15`, `Community 19`, `Community 244`, `Community 87`, `Community 120`, `Community 89`, `Community 58`, `Community 188`, `Community 93`?**
  _High betweenness centrality (0.008) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.048308457711442786 - nodes in this community are weakly interconnected._