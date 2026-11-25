# Graph Report - dive_hub  (2026-11-25)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 12792 nodes · 39898 edges · 420 communities (241 shown, 179 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 953 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `6be52849`
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
- Community 266
- Community 267
- Community 268
- Community 269
- Community 270
- Community 271
- Community 272
- Community 273
- Community 278
- Community 280

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
- `assembleToolSources()` --calls--> `ToolDiverSource`  [INFERRED]
  apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/tools/ToolSourcesStore.kt → apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/tools/ToolsService.kt
- `Context` --references--> `Position`  [EXTRACTED]
  apps/server/src/imports/formats/uddf/uddf.ts → apps/server/src/vocabulary.ts

## Import Cycles
- None detected.

## Communities (420 total, 179 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (211): apikey, user, buddySuggestion, changeEvent, cylinder, dive, diveFinding, diveSiteExternalId (+203 more)

### Community 1 - "Community 1"
Cohesion: 0.02
Nodes (219): Tx, continuationAppend, diverBodyWeight, LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, participant, recordingEvent (+211 more)

### Community 2 - "Community 2"
Cohesion: 0.02
Nodes (205): clientDiagnosticsStatusQuery(), diversQuery(), cylinderCatalogueQuery(), equipmentOfDiverQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery() (+197 more)

### Community 3 - "Community 3"
Cohesion: 0.04
Nodes (199): DisplaySettings(), AdminSettings(), ClientDiagnosticsSetting(), DeleteUserDialog(), Invitations(), Invite(), LinkToPassOn(), ProviderSiteData() (+191 more)

### Community 4 - "Community 4"
Cohesion: 0.02
Nodes (181): ClientDiagnosticsSettingsView, ClientDiagnosticsStatusView, ADR-0080, importQuery(), ADR-0030, uploadFile(), keys, buddiesForQuery() (+173 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (44): DiveRow(), Figures(), RowFacts(), EmptyState(), FactRow(), NoteText(), StackedNoteText(), rememberReducedMotion() (+36 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (45): AppContainer, MainActivity, DiveEditUiState, DiveEditViewModel, DiverChoice, DiverSearchState, Failed, Found (+37 more)

### Community 7 - "Community 7"
Cohesion: 0.02
Nodes (132): createServer(), createAuth(), loadAuthSecret(), acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS (+124 more)

### Community 8 - "Community 8"
Cohesion: 0.02
Nodes (150): ConnectionRow, diveFingerprint, providerGoneDive, providerLease, DiverDetails, waitingOf(), Caller, Buddy (+142 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (134): BACKUP_EXTENSION, BACKUP_FORMAT_VERSION, BACKUP_SKIPPED_DIRS, BackupArchiveError, BackupDecryptionKey, BackupEncryption, BackupEncryptionMetadata, BackupManifest (+126 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (141): allButDive(), continuationCandidateQuery(), deletedDivesOn(), deletedDivesQuery(), diveNeighboursQuery(), diveQuery(), DiveValues, DiveView (+133 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (87): DangerButton(), InkButton(), SecondaryButton(), CylinderFields(), CylinderProblemText(), CylindersFields(), FieldPair(), GearEntryHeading() (+79 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (128): label(), Me, AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery() (+120 more)

### Community 13 - "Community 13"
Cohesion: 0.03
Nodes (128): EMPTY_SITE_IMPORT_COUNTS, ImportArea, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053 (+120 more)

### Community 14 - "Community 14"
Cohesion: 0.04
Nodes (138): ComputerChoice, DiveImportMode, DiveKind, ProviderImportPlan, providerSiteData, UtcOffsetSource, renumber(), cylindersOf() (+130 more)

### Community 15 - "Community 15"
Cohesion: 0.03
Nodes (119): lead, DIVE_FIELDS, ProviderError, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord() (+111 more)

### Community 16 - "Community 16"
Cohesion: 0.02
Nodes (129): actorType, aiAccessOutcome, backupRunKind, backupRunStatus, candidateResolution, centreSource, CERTIFICATION_IMAGE_STATES, CERTIFICATION_VERIFICATIONS (+121 more)

### Community 17 - "Community 17"
Cohesion: 0.03
Nodes (129): LEAD_PLACEMENTS, SUIT_UNDERGARMENTS, AiAccessScope, seesPositions(), VerifiedAccess, buddiesOf(), BuddySearch, count() (+121 more)

### Community 18 - "Community 18"
Cohesion: 0.03
Nodes (20): AndroidDiveStore, EditSource, ApiDiverBodyWeightsGet200ResponseItem, DiveStore, toBoolean(), toLong(), toModel(), BodyWeight (+12 more)

### Community 19 - "Community 19"
Cohesion: 0.03
Nodes (119): divesQuery(), LogbookPage, PAGE_SIZE, importsQuery(), ImportView, siteQuery(), App(), BackupsPage (+111 more)

### Community 20 - "Community 20"
Cohesion: 0.06
Nodes (16): RevisionFollowTest, LeadEstimateViewModelTest, CylinderMaterial, aluminium, carbon, steel, Dive, PendingWriteStatus (+8 more)

### Community 21 - "Community 21"
Cohesion: 0.05
Nodes (82): Formats, label(), SettingsDebugRoute(), SettingsDebugScreen(), SyncLine(), formatSyncTime(), GasPlanRoute(), GasPlanScreen() (+74 more)

### Community 22 - "Community 22"
Cohesion: 0.03
Nodes (111): backupTask(), ADR-0069, ImportErrorCode, ImportFound, importOriginal, ImportOutcome, push, WaitingPushOptions (+103 more)

### Community 23 - "Community 23"
Cohesion: 0.04
Nodes (112): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+104 more)

### Community 24 - "Community 24"
Cohesion: 0.04
Nodes (29): ScanViewModelTest, ApiDiveCentresPostRequest, ApiDiveCentresPostRequestExternalIdsItem, ApiDiversFromCodePostRequest, ApiVerificationCodesReadPostRequest, DecodeException, DiveHubException, PendingWrite (+21 more)

### Community 25 - "Community 25"
Cohesion: 0.03
Nodes (102): CHANNELS, isRule(), read(), ADR-0036, Queryable, CylinderMaterial, device, diveAssessment (+94 more)

### Community 26 - "Community 26"
Cohesion: 0.04
Nodes (87): auth, BackupServiceDeps, createServices(), ServiceParts, ADR-0027, ADR-0030, ADR-0061, ADR-0063 (+79 more)

### Community 27 - "Community 27"
Cohesion: 0.04
Nodes (83): equipmentDueQuery(), CodeFigure(), DiveCodes(), showDiveCodes(), ADR-0043, EquipmentDueNotice(), ADR-0034, avatarTone() (+75 more)

### Community 28 - "Community 28"
Cohesion: 0.02
Nodes (94): UTC_OFFSET_SOURCES, WEIGHTING_FELT, downsampleMinMax(), CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, withoutPressures(), Copies (+86 more)

### Community 29 - "Community 29"
Cohesion: 0.04
Nodes (5): DiveHubTopBar(), DiveHubTheme(), SectionTest, DiveHubTopBarTest, UpdateRequiredScreenTest

### Community 30 - "Community 30"
Cohesion: 0.03
Nodes (72): CertificationAccount, RemoteCertification, ADR-0050, ADR-0053, ParticipantRole, PushLeftOut, ActionContext, Capabilities (+64 more)

### Community 31 - "Community 31"
Cohesion: 0.04
Nodes (48): Section(), cylinderLine(), cylinderPressures(), Cylinders(), Direction, Back, Left, Right (+40 more)

### Community 32 - "Community 32"
Cohesion: 0.04
Nodes (80): adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, AuthOptions, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, SessionUser, ADR-0011 (+72 more)

### Community 33 - "Community 33"
Cohesion: 0.03
Nodes (71): Check, Deleted, DiveView, Outcome, RecordingView, ADR-0063, Candidate, Deleted (+63 more)

### Community 34 - "Community 34"
Cohesion: 0.04
Nodes (86): CertificationService, CYLINDER_MATERIALS, DIVE_IMPORT_MODES, DIVE_KINDS, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053 (+78 more)

### Community 35 - "Community 35"
Cohesion: 0.05
Nodes (85): BottomTime, bottomTime(), BottomTimeInput, Limit, BottomTimeQuery, ADR-0032, ADR-0033, ADR-0071 (+77 more)

### Community 36 - "Community 36"
Cohesion: 0.04
Nodes (48): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+40 more)

### Community 37 - "Community 37"
Cohesion: 0.04
Nodes (76): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+68 more)

### Community 38 - "Community 38"
Cohesion: 0.04
Nodes (74): CertificationProviders, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError, CertificationExpiry, CertificationInput, CertificationView, createCertificationService() (+66 more)

### Community 39 - "Community 39"
Cohesion: 0.05
Nodes (18): DiveListViewDivesItem, DiveListViewDivesItemGasesItem, DiveListViewDivesItemParticipantsItem, DiveListViewDivesItemSite, DiveListViewMonthsItem, DiveViewValues, DiveViewValuesStartsAt, localMonthOf() (+10 more)

### Community 40 - "Community 40"
Cohesion: 0.06
Nodes (22): QuietButton(), ChangesList(), ConflictChoice(), ConflictEntry(), ConflictsRoute(), ConflictsScreen(), diveTitle(), FieldDecision() (+14 more)

### Community 41 - "Community 41"
Cohesion: 0.05
Nodes (8): CountingDispatcher, LogbookViewModelTest, ApiClient, ApiErrorBody, WriteCapability, ApiClientTest, DiveRepositoryTest, WriteCapabilityTest

### Community 42 - "Community 42"
Cohesion: 0.03
Nodes (75): DiveGearItemView, DiveGearView, EquipmentDetailView, EquipmentDueView, EquipmentItemView, EquipmentRecordView, EquipmentScheduleDue, EquipmentScheduleView (+67 more)

### Community 43 - "Community 43"
Cohesion: 0.03
Nodes (61): diveLease, providerConflict, pushRun, waitingPush, kept(), LEASE_MS, MAX_WAIT_MS, POLL_MS (+53 more)

### Community 44 - "Community 44"
Cohesion: 0.04
Nodes (70): jointDiveDismissal, JointFillField, alignedForMatching(), CandidateDive, DAY_MS, decideMatch(), depthsDisagree(), MatchDecision (+62 more)

### Community 45 - "Community 45"
Cohesion: 0.12
Nodes (4): DiveDetailScreenshotTest, NeighboursView, NeighboursViewNext, NeighboursViewPrevious

### Community 46 - "Community 46"
Cohesion: 0.04
Nodes (18): ApiClientDiagnosticsStatusGet200Response, ApiDiveSitesGet200Response, ApiMeGet200ResponseOwnDiver, DiveDetailsListView, DiveDetailsListViewCounts, DiveDetailsListViewMonthsItem, DiveDetailsListViewTotals, DiverDetailsView (+10 more)

### Community 47 - "Community 47"
Cohesion: 0.05
Nodes (52): connection, diveModeFromFit, createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE (+44 more)

### Community 48 - "Community 48"
Cohesion: 0.05
Nodes (14): SettingsDiagnosticsState, SettingsUiState, SettingsViewModel, FakeDiagnostics, FakeInstances, SettingsViewModelTest, ApiClientDiagnosticsPostRequest, ApiClientDiagnosticsPostRequestClient (+6 more)

### Community 49 - "Community 49"
Cohesion: 0.06
Nodes (55): accountListEntry, diver, diverExternalId, tidyName(), foldName(), nameDiffers(), createBuddyService(), createFrom() (+47 more)

### Community 50 - "Community 50"
Cohesion: 0.04
Nodes (62): pushRunItem, PushRunLeftOut, DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN, NO_RECORDING (+54 more)

### Community 51 - "Community 51"
Cohesion: 0.06
Nodes (25): DiveEditScreenRoute(), DiveHubApp(), DiveHubScreens, ErrorText(), Gate(), InstanceForm(), InstanceStatus(), LoadingScreen() (+17 more)

### Community 52 - "Community 52"
Cohesion: 0.09
Nodes (5): LogbookScreenshotTest, DiveListView, DiveListViewCounts, DiveListViewTotals, DiveProfileView

### Community 53 - "Community 53"
Cohesion: 0.06
Nodes (64): ANDROID_UI, AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch() (+56 more)

### Community 54 - "Community 54"
Cohesion: 0.08
Nodes (7): DiveDetailViewModelTest, GatedDispatcher, DiveEditViewModelTest, FakeSearch, FoundDiver, ApiDiverSearch, DiverSearch

### Community 55 - "Community 55"
Cohesion: 0.05
Nodes (42): captureDiagnostic(), captureError(), cleanContext(), clearDiagnostics(), CLIENT_NAME, CLIENT_VERSION, DiagnosticEntry, diagnosticFromError() (+34 more)

### Community 56 - "Community 56"
Cohesion: 0.08
Nodes (29): Profile(), ProfileChart(), ProfileRead, DiveViewRecordingsItem, DiveViewRecordingsItemSummarySurfaceBreaksItem, RecordingSamplesView, RecordingSamplesViewSeriesItem, computerRecordings() (+21 more)

### Community 57 - "Community 57"
Cohesion: 0.06
Nodes (29): AppNavHost(), AppScreens, clearSavedTabStates(), isShowing(), leaveDiveForLogbook(), MainShell(), navigateToTab(), tabOf() (+21 more)

### Community 58 - "Community 58"
Cohesion: 0.06
Nodes (50): looksLikeZip(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy(), ADR-0024 (+42 more)

### Community 59 - "Community 59"
Cohesion: 0.04
Nodes (12): KeystoreTokenStore, PreferencesCachedMeStore, PreferencesInstanceUrlStore, UserCacheStore, FakeCachedMe, SettingsRepository, CachedMeStore, InstanceUrlStore (+4 more)

### Community 60 - "Community 60"
Cohesion: 0.04
Nodes (48): account, accountRelations, rateLimit, session, sessionRelations, ADR-0011, ADR-0012, ADR-0035 (+40 more)

### Community 61 - "Community 61"
Cohesion: 0.04
Nodes (55): AssessmentService, AssessmentView, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView() (+47 more)

### Community 62 - "Community 62"
Cohesion: 0.10
Nodes (10): FakeCache, FakeCachedMe, FakeInstances, FakeQueue, FakeTokens, Harness, json(), SessionRepositoryTest (+2 more)

### Community 63 - "Community 63"
Cohesion: 0.12
Nodes (57): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, assessmentRoutes() (+49 more)

### Community 64 - "Community 64"
Cohesion: 0.07
Nodes (51): SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), buildUpdateRecord(), DiveForSsi, emptyRecord(), keysOf() (+43 more)

### Community 65 - "Community 65"
Cohesion: 0.11
Nodes (3): DiveRoute, AppNavHostTest, FakeScreens

### Community 66 - "Community 66"
Cohesion: 0.06
Nodes (18): ApiMeGet200Response, ApiMeGet200ResponsePreferences, SignInEmailRequest, UserView, InstanceUrl, HealthView, InstanceProbeResult, Loading (+10 more)

### Community 67 - "Community 67"
Cohesion: 0.07
Nodes (18): LogbookPaging, AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint, SketchProfile, sketchScale() (+10 more)

### Community 68 - "Community 68"
Cohesion: 0.05
Nodes (49): replyCertificationError, STATUS, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, CertificationRouteDeps (+41 more)

### Community 69 - "Community 69"
Cohesion: 0.06
Nodes (36): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+28 more)

### Community 70 - "Community 70"
Cohesion: 0.06
Nodes (9): MainViewModel, Unreachable, IntHolder, MainViewModelPendingWarningTest, WarningCache, WarningCachedMe, WarningInstances, WarningQueue (+1 more)

### Community 71 - "Community 71"
Cohesion: 0.12
Nodes (23): ApiToolsBottomTimeGet200ResponseGas, Body, BrokenBody, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage() (+15 more)

### Community 72 - "Community 72"
Cohesion: 0.07
Nodes (49): NoFly, noFlyAfter(), DEFAULT_GF_HIGH, residualSeedOf(), Source, ADR-0032, ADR-0033, ADR-0071 (+41 more)

### Community 73 - "Community 73"
Cohesion: 0.13
Nodes (10): LiveHints, FeedEntry, RefreshData, refreshDetailJson(), refreshDiveListItem(), refreshDiverJson(), refreshRecordingJson(), refreshSiteJson() (+2 more)

### Community 75 - "Community 75"
Cohesion: 0.11
Nodes (5): Complete, SessionEnded, Stopped, SyncEngine, SyncResult

### Community 76 - "Community 76"
Cohesion: 0.05
Nodes (45): JOINT_FILL_FIELDS, SUGGESTION_STATUSES, DiveProfileView, DiveProfileViewSchema, ADR-0041, DateTime, ImportView, ImportViewSchema (+37 more)

### Community 77 - "Community 77"
Cohesion: 0.07
Nodes (51): ConnectionView, pushRunActive(), pushRunBody(), PushRunChoices, PushRunPreview, pushRunPreviewQuery(), pushRunQuery(), pushRunsQuery() (+43 more)

### Community 78 - "Community 78"
Cohesion: 0.08
Nodes (51): Answer, answer(), baseFrom(), BodyWeight, Confidence, high, low, medium (+43 more)

### Community 79 - "Community 79"
Cohesion: 0.06
Nodes (44): defineTool(), Id(), Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), getDiverLoadings (+36 more)

### Community 80 - "Community 80"
Cohesion: 0.12
Nodes (37): BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen, BottomTimeOutside, depth (+29 more)

### Community 81 - "Community 81"
Cohesion: 0.14
Nodes (5): DiveMergeTest, diveObject(), jsonOf(), participants(), WriteQueueTest

### Community 82 - "Community 82"
Cohesion: 0.07
Nodes (45): serviceRecord, serviceRecordSchedule, serviceSchedule, addMonths(), CountDue, countReason(), dimensionState(), divesLeft() (+37 more)

### Community 83 - "Community 83"
Cohesion: 0.05
Nodes (42): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+34 more)

### Community 84 - "Community 84"
Cohesion: 0.07
Nodes (43): Series, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining(), ssiDiveType() (+35 more)

### Community 85 - "Community 85"
Cohesion: 0.08
Nodes (18): ChangeBullet(), changeField(), ChangeLine(), ChooseRow(), Dismiss(), OutcomeText(), ReadResult(), scanErrorText() (+10 more)

### Community 86 - "Community 86"
Cohesion: 0.06
Nodes (42): ADR-0036, ADR-0063, ADR-0038, ADR-0048, ADR-0062, AiAccess, assessedDive(), AssessedFinding (+34 more)

### Community 87 - "Community 87"
Cohesion: 0.06
Nodes (5): QrImageDecoder, DiveHubApplicationTest, SplashTheme, SplashThemeTest, LogShareTest

### Community 88 - "Community 88"
Cohesion: 0.05
Nodes (42): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+34 more)

### Community 89 - "Community 89"
Cohesion: 0.09
Nodes (38): ReportProblemSettings(), SecuritySettings(), BackupRunView, backupSettingsQuery(), BackupSettingsView, backupsQuery(), SiteDataView, supportBundlePreviewQuery() (+30 more)

### Community 90 - "Community 90"
Cohesion: 0.05
Nodes (38): SUIT_TYPES, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053, replyDiverError, STATUS (+30 more)

### Community 91 - "Community 91"
Cohesion: 0.13
Nodes (17): Facts(), profileSummaryText(), Recordings(), Water(), DiveDetailText, conflictCylindersText(), conflictCylinderText(), conflictFeedbackText() (+9 more)

### Community 92 - "Community 92"
Cohesion: 0.06
Nodes (8): DiveViewFromRecording, DiveViewFromRecordingStartsAt, DiveViewLeadItem, DiveViewPosition, DiveViewRecordingsItemDevice, DiveViewRecordingsItemSummaryGasesItem, DiveViewSac, DiveViewWaterMismatch

### Community 93 - "Community 93"
Cohesion: 0.08
Nodes (34): DiveCertifications, LogbookCheckService, AppendDeps, AppendOutcome, AppendService, Side, ADR-0038, ADR-0063 (+26 more)

### Community 94 - "Community 94"
Cohesion: 0.06
Nodes (41): Change(), Changes(), historyRoute, Made(), REVISION_CAUSES, PositionSchema, AdoptBody, Country (+33 more)

### Community 95 - "Community 95"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 96 - "Community 96"
Cohesion: 0.09
Nodes (38): EstimateDive, EstimateDiveCylinder, WeightingFeedback, WeightingFelt, right, too_heavy, too_light, LoadingDive (+30 more)

### Community 98 - "Community 98"
Cohesion: 0.08
Nodes (10): Radius, drawBand(), ProfileSketch(), PendingChangeWarning, PendingWarningDialog(), SignIn, SignInWarningDialog(), SignOut (+2 more)

### Community 99 - "Community 99"
Cohesion: 0.08
Nodes (38): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+30 more)

### Community 101 - "Community 101"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 102 - "Community 102"
Cohesion: 0.05
Nodes (39): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+31 more)

### Community 104 - "Community 104"
Cohesion: 0.13
Nodes (15): BottomTime, bottomTime(), BottomTimeLimits, Ok, OutsideLimits, planningSac(), PlanningSacDive, PlanningSacResult (+7 more)

### Community 105 - "Community 105"
Cohesion: 0.06
Nodes (37): EquipmentService, RecordInput, ScheduleInput, DiveGearItemViewSchema, DiveGearViewSchema, DueState, EquipmentDetailViewSchema, EquipmentDueViewSchema (+29 more)

### Community 106 - "Community 106"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 107 - "Community 107"
Cohesion: 0.12
Nodes (33): ChangePassword(), Sessions(), ADR-0080, useDescribeAgent(), AiAccessLogEntry, AiAccessView, authClient, invitationLinkQuery() (+25 more)

### Community 108 - "Community 108"
Cohesion: 0.12
Nodes (3): ScanUiState, ScanViewModel, ScanScreenshotTest

### Community 109 - "Community 109"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 110 - "Community 110"
Cohesion: 0.08
Nodes (31): CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite(), lock() (+23 more)

### Community 111 - "Community 111"
Cohesion: 0.12
Nodes (35): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+27 more)

### Community 112 - "Community 112"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 113 - "Community 113"
Cohesion: 0.07
Nodes (28): headers, ADR-0030, ADR-0040, conflictForLena(), diveWithoutRecording(), editInSsi(), externalDiver(), importLena() (+20 more)

### Community 114 - "Community 114"
Cohesion: 0.11
Nodes (12): ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseCns, ApiDiversIdLoadingsGet200ResponseN2, ApiDiversIdLoadingsGet200ResponseNoFly, ApiDiversIdLoadingsGet200ResponseNoFlySource, ApiDiversIdLoadingsGet200ResponseOtu, cnsDecayed() (+4 more)

### Community 115 - "Community 115"
Cohesion: 0.08
Nodes (31): AI_ACCESS_NAME_MAX, aiAccessLog, aiAccessSetting, Access, AccessView, AiAccessRouteDeps, LogEntryView, Scope (+23 more)

### Community 116 - "Community 116"
Cohesion: 0.09
Nodes (29): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+21 more)

### Community 117 - "Community 117"
Cohesion: 0.11
Nodes (27): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+19 more)

### Community 118 - "Community 118"
Cohesion: 0.11
Nodes (28): surfacePressureBar(), ndlMinutes(), ADR-0032, ADR-0046, evaluateTissues(), advanceTissues(), schreinerStep(), surfaceSaturatedTissues() (+20 more)

### Community 119 - "Community 119"
Cohesion: 0.10
Nodes (26): ADR-0051, Status, ADR-0056, ADR-0024, ADR-0043, clearQueue(), conflictAtSsi(), connectSsi() (+18 more)

### Community 120 - "Community 120"
Cohesion: 0.13
Nodes (7): GateCachedMe, GateCall, GateClient, GateInstances, GateServer, GateTokens, WriteExitGateTest

### Community 121 - "Community 121"
Cohesion: 0.08
Nodes (29): CENTRE_SOURCE_NAME, CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+21 more)

### Community 122 - "Community 122"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 123 - "Community 123"
Cohesion: 0.15
Nodes (9): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), GasPlanInputTest (+1 more)

### Community 124 - "Community 124"
Cohesion: 0.23
Nodes (7): leadAnswer(), EstimateCylinder, EstimateInput, PlannedConditions, Suit, leadEstimate(), PersonalizedToolsTest

### Community 125 - "Community 125"
Cohesion: 0.16
Nodes (4): LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200ResponseMissingItem

### Community 126 - "Community 126"
Cohesion: 0.13
Nodes (8): InstanceProbeTest, json(), probe(), ProbeCache, ProbeCachedMe, ProbeInstances, probeRepository(), ProbeTokens

### Community 127 - "Community 127"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 128 - "Community 128"
Cohesion: 0.12
Nodes (12): WriteDrain, Drained, Empty, SessionEnded, Stopped, WriteReplayResult, SyncDrainTest, WriteDrain (+4 more)

### Community 129 - "Community 129"
Cohesion: 0.09
Nodes (19): ProblemCode, ResponseState, whenResponseGone(), StoredBlob, PlacedArchive, ANSWER, looksLikeRecipientPrivateKey(), PlacedArchive (+11 more)

### Community 130 - "Community 130"
Cohesion: 0.07
Nodes (12): buddySite(), Dive, OTHER, PendingOffer, ADR-0012, ADR-0070, Dive, OTHER (+4 more)

### Community 131 - "Community 131"
Cohesion: 0.11
Nodes (4): FakeCache, FakeInstances, FakeTokens, MainViewModelTest

### Community 132 - "Community 132"
Cohesion: 0.18
Nodes (10): Body, buddyAnswer(), centreAnswer(), centreView(), diverView(), Offline, ScanCall, ScanHarness (+2 more)

### Community 133 - "Community 133"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 134 - "Community 134"
Cohesion: 0.11
Nodes (20): SsiClient, ClearChecks, NumberChecks, TwowayChecks, apneaDive(), DATASET_KEYS, datasetSummary(), findIn() (+12 more)

### Community 135 - "Community 135"
Cohesion: 0.10
Nodes (26): globalSetup(), ADR-0023, accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks() (+18 more)

### Community 136 - "Community 136"
Cohesion: 0.11
Nodes (7): ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh, ApiToolsBottomTimeGet200ResponseUsedSacLpm, ApiToolsBottomTimeGet200ResponseUsedWater

### Community 137 - "Community 137"
Cohesion: 0.11
Nodes (7): ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport

### Community 138 - "Community 138"
Cohesion: 0.10
Nodes (21): BOM, Json, num(), obj(), positive(), scaled(), SuuntoJsonAdapter, time() (+13 more)

### Community 139 - "Community 139"
Cohesion: 0.13
Nodes (23): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, Site (+15 more)

### Community 140 - "Community 140"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 143 - "Community 143"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 145 - "Community 145"
Cohesion: 0.22
Nodes (4): GasPlanScreenshotTest, GasPlanToolDiver, GasPlanToolInput, GasPlanToolLevel

### Community 146 - "Community 146"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 147 - "Community 147"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 148 - "Community 148"
Cohesion: 0.12
Nodes (7): LeadAnswer, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseLeftOut, ApiDiversIdLeadEstimateGet200ResponseRange, ApiDiversIdLeadEstimateGet200ResponseReserve, ApiDiversIdLeadEstimateGet200ResponseWeightCheck

### Community 149 - "Community 149"
Cohesion: 0.08
Nodes (25): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+17 more)

### Community 150 - "Community 150"
Cohesion: 0.10
Nodes (19): REVISION_ENTITY_TYPES, RevisionEntityType, CHANGE_EVENT_RETENTION_DAYS, ChangeEventView, ChangeFeedPage, ChangeKind, CHANGES_DEFAULT_LIMIT, CHANGES_MAX_LIMIT (+11 more)

### Community 151 - "Community 151"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 153 - "Community 153"
Cohesion: 0.13
Nodes (6): ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting

### Community 154 - "Community 154"
Cohesion: 0.13
Nodes (6): DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, WaitingPushView

### Community 155 - "Community 155"
Cohesion: 0.21
Nodes (7): DiveViewRecordingsItemSummary, assembleToolSources(), epochMs(), recentDiveEnd(), sacOf(), summaryOf(), ToolSourcesStoreTest

### Community 156 - "Community 156"
Cohesion: 0.17
Nodes (7): Connected, Hint, HintFrame, LiveEvent, LiveHint, SseLiveHints, LiveHintsTest

### Community 157 - "Community 157"
Cohesion: 0.13
Nodes (20): Body, Hold, Offline, refreshDeletedJson(), refreshDetailsPage(), refreshDiveId(), refreshDivePage(), refreshDiveStartsAt() (+12 more)

### Community 158 - "Community 158"
Cohesion: 0.24
Nodes (6): Body, Call, Harness, Offline, Reply, WriteReplayerTest

### Community 159 - "Community 159"
Cohesion: 0.12
Nodes (22): answer(), CylinderTerm, detail(), EMPTY_BUOYANCY_POINTS, inUnit(), roundedTotal(), roundTo(), RULE_CYLINDER (+14 more)

### Community 160 - "Community 160"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 162 - "Community 162"
Cohesion: 0.17
Nodes (3): StreamFailure, SyncRefresher, toStreamFailure()

### Community 163 - "Community 163"
Cohesion: 0.23
Nodes (5): Body, ConflictCall, ConflictHarness, ConflictReply, WriteConflictResolutionTest

### Community 164 - "Community 164"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 165 - "Community 165"
Cohesion: 0.15
Nodes (18): qr@0.7.2, cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043 (+10 more)

### Community 166 - "Community 166"
Cohesion: 0.17
Nodes (16): ApiToolsBottomTimeGet200ResponseLimits, jsRound(), powInt(), roundTo(), Sac, SacCylinder, SacDive, SacMissing (+8 more)

### Community 167 - "Community 167"
Cohesion: 0.15
Nodes (18): Estimate, LoadingBasis, N2Loadings, N2UnavailableReason, no_profile, no_recent_dive, stale, offGasAtSurface() (+10 more)

### Community 168 - "Community 168"
Cohesion: 0.14
Nodes (4): FakeCachedMe, FakeInstances, FakeTokens, SyncAuthTest

### Community 169 - "Community 169"
Cohesion: 0.19
Nodes (6): WriteQueueLineTest, WriteDropReason, Forbidden, Gone, NotFound, Refused

### Community 170 - "Community 170"
Cohesion: 0.14
Nodes (17): AssessmentError, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), requireManagedDive(), same() (+9 more)

### Community 171 - "Community 171"
Cohesion: 0.23
Nodes (20): DiveOrigin, REFERENCE_PREFIX, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef(), MATERIAL_OF_TANK_TYPE (+12 more)

### Community 172 - "Community 172"
Cohesion: 0.16
Nodes (16): DecisionTimelineProps, GapKind, ADR-0063, addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, laneFitsCapsule(), relations() (+8 more)

### Community 173 - "Community 173"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 174 - "Community 174"
Cohesion: 0.15
Nodes (12): timeZoneText(), diveLocalTime(), diveStartAt(), offsetId(), timeZoneNote(), TimeZoneOrigin, DEVICE, NEARBY (+4 more)

### Community 175 - "Community 175"
Cohesion: 0.15
Nodes (5): DiagnosticEntry, DiagnosticLevel, ERROR, WARN, DiagnosticsBuffer

### Community 177 - "Community 177"
Cohesion: 0.15
Nodes (5): ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits

### Community 178 - "Community 178"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 179 - "Community 179"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource/poppins@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 180 - "Community 180"
Cohesion: 0.14
Nodes (16): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), fit (+8 more)

### Community 181 - "Community 181"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 182 - "Community 182"
Cohesion: 0.14
Nodes (17): SiteImportRouteDeps, StartBody, STATUS, ADR-0021, SiteImportError, SiteImportService, Area, Counts (+9 more)

### Community 183 - "Community 183"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 184 - "Community 184"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 185 - "Community 185"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 186 - "Community 186"
Cohesion: 0.12
Nodes (3): SyncWorker, WorkManagerPeriodicSync, PeriodicSyncScheduler

### Community 187 - "Community 187"
Cohesion: 0.19
Nodes (11): LeadInputTest, SuitType, drysuit, none, semidry, skin, wetsuit, SuitUndergarment (+3 more)

### Community 189 - "Community 189"
Cohesion: 0.17
Nodes (15): ADR-0076, ClientVersionRegistry, MIN_CLIENT_VERSIONS, MinClientVersion, ADR-0076, clientRequestSerializer(), ADR-0076, compareIdentifier() (+7 more)

### Community 191 - "Community 191"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 192 - "Community 192"
Cohesion: 0.13
Nodes (14): AL80, asEstimateInput(), EstimateDiveCylinder, LEAD_CASES, leadInput(), LeadVectorInput, planOf(), STEEL_12 (+6 more)

### Community 193 - "Community 193"
Cohesion: 0.17
Nodes (12): sampleCount(), storedList(), tryParseJson(), APP_PARAMS, createSsiTransport(), exchange(), isObject(), SSI_APP_USER_AGENT (+4 more)

### Community 194 - "Community 194"
Cohesion: 0.15
Nodes (10): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, ADR-0068 (+2 more)

### Community 195 - "Community 195"
Cohesion: 0.17
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 199 - "Community 199"
Cohesion: 0.19
Nodes (4): CentreView, CentreViewExternalIdsItem, CentreViewSitesItem, CentreViewVerificationCode

### Community 200 - "Community 200"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 201 - "Community 201"
Cohesion: 0.12
Nodes (13): clientDiagnostic, clientDiagnosticsSettings, ClientDiagnosticsEntryInput, ClientDiagnosticsEntryView, ClientDiagnosticsError, ClientDiagnosticsUpload, KEEP_NEWEST, MAX_UPLOAD_ENTRIES (+5 more)

### Community 202 - "Community 202"
Cohesion: 0.21
Nodes (11): buildSupportBundle(), buildTarGz(), json(), ndjson(), octal(), SUPPORT_BUNDLE_FORMAT, SUPPORT_BUNDLE_FORMAT_VERSION, SupportBufferManifest (+3 more)

### Community 203 - "Community 203"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 204 - "Community 204"
Cohesion: 0.18
Nodes (15): baseFrom(), cylindersAtReserve(), densityRatio(), diveWeight(), emptyBuoyancyKg(), estimateLead(), median(), neededIn() (+7 more)

### Community 205 - "Community 205"
Cohesion: 0.21
Nodes (12): evaluateLeadVectorCase(), LEAD_VECTOR_KERNEL, leadVectorFile(), VECTOR_KERNELS, VectorFile, vectorFile(), assertMatch(), checkVectors() (+4 more)

### Community 206 - "Community 206"
Cohesion: 0.19
Nodes (11): decimal(), dive(), makeOceanicPlusUddf(), OCEANIC_DIVES, OceanicDive, site(), waypoint(), exported (+3 more)

### Community 207 - "Community 207"
Cohesion: 0.13
Nodes (14): concurrency, cancel-in-progress, group, env, RELEASE_TAG, jobs, publish, name (+6 more)

### Community 209 - "Community 209"
Cohesion: 0.33
Nodes (4): ConflictField, DiveMerge, MergeOutcome, ParticipantsMergeOutcome

### Community 210 - "Community 210"
Cohesion: 0.20
Nodes (5): LogRingBuffer, RedactionCounters, SupportBundleInput, SupportBundleManifest, SupportServiceDeps

### Community 211 - "Community 211"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 212 - "Community 212"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 213 - "Community 213"
Cohesion: 0.22
Nodes (10): recordingName(), Deep, DepthReading, depthReading(), deviceName(), manufacturerName(), MarkedValue, markOf() (+2 more)

### Community 215 - "Community 215"
Cohesion: 0.17
Nodes (10): EstimateInput, AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031 (+2 more)

### Community 216 - "Community 216"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 217 - "Community 217"
Cohesion: 0.26
Nodes (3): DiverView, DiverViewCertifications, DiverViewCertificationsNewest

### Community 218 - "Community 218"
Cohesion: 0.26
Nodes (3): DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewCylindersItemSeries

### Community 219 - "Community 219"
Cohesion: 0.26
Nodes (3): DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 221 - "Community 221"
Cohesion: 0.18
Nodes (10): LogBufferStats, SupportBundle, ClientDiagnosticsProvider, FailedBackup, FailedJob, HealthSection, iso(), JobsSection (+2 more)

### Community 222 - "Community 222"
Cohesion: 0.18
Nodes (11): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, ADR-0076, ADR-0083, MODULE_IMPORTS (+3 more)

### Community 223 - "Community 223"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

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

### Community 233 - "Community 233"
Cohesion: 0.33
Nodes (8): prepareEntry(), DENIED_KEY_PARTS, isDeniedKey(), maskString(), redactInner(), redactLogObject(), RedactOptions, redactValue()

### Community 234 - "Community 234"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 235 - "Community 235"
Cohesion: 0.20
Nodes (10): verify, prerelease, sha, tag, versionCode, versionName, name, outputs (+2 more)

### Community 236 - "Community 236"
Cohesion: 0.20
Nodes (10): with, cache, distribution, fetch-depth, if-no-files-found, java-version, name, path (+2 more)

### Community 237 - "Community 237"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 238 - "Community 238"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 239 - "Community 239"
Cohesion: 0.28
Nodes (7): ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, s(), ADR-0035

### Community 241 - "Community 241"
Cohesion: 0.31
Nodes (5): BufferedLog, createLogRingBuffer(), LOG_BUFFER_CAPACITY_BYTES, LOG_BUFFER_CAPACITY_ENTRIES, LogRingBufferOptions

### Community 242 - "Community 242"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 243 - "Community 243"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 244 - "Community 244"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 248 - "Community 248"
Cohesion: 0.43
Nodes (3): DiverSearchTest, json(), SearchHarness

### Community 250 - "Community 250"
Cohesion: 0.46
Nodes (8): appliedMigrationHead(), emptyRedactionCounters(), createSupportService(), assemble(), clientDiagnostics(), configSection(), failedJobs(), healthSection()

### Community 251 - "Community 251"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 252 - "Community 252"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 254 - "Community 254"
Cohesion: 0.43
Nodes (5): createFitAdapter(), ownersParts(), parse(), toPart(), ADR-0063

### Community 255 - "Community 255"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 256 - "Community 256"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 257 - "Community 257"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 258 - "Community 258"
Cohesion: 0.40
Nodes (5): stubDive(), ADR-0015, ADR-0023, ADR-0025, guardedRoute()

### Community 259 - "Community 259"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 260 - "Community 260"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 261 - "Community 261"
Cohesion: 0.40
Nodes (5): LogbookPhase, Error, List, Loading, Offline

### Community 262 - "Community 262"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build

### Community 263 - "Community 263"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 264 - "Community 264"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 269 - "Community 269"
Cohesion: 0.50
Nodes (4): SyncPhase, Details, Meta, Profiles

### Community 270 - "Community 270"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 271 - "Community 271"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 272 - "Community 272"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1369 isolated node(s):** `Off`, `Failed`, `Reserve`, `RockBottom`, `Sent` (+1364 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3984 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **179 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `DiveStore` connect `Community 18` to `Community 128`, `Community 132`, `Community 6`, `Community 20`, `Community 24`, `Community 155`, `Community 157`, `Community 158`, `Community 163`, `Community 39`, `Community 168`, `Community 48`, `Community 51`, `Community 54`, `Community 59`, `Community 190`, `Community 67`, `Community 196`, `Community 71`, `Community 73`, `Community 75`, `Community 81`, `Community 96`, `Community 108`, `Community 120`, `Community 123`?**
  _High betweenness centrality (0.021) - this node is a cross-community bridge._
- **What connects `Off`, `Failed`, `Reserve` to the rest of the system?**
  _1369 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.022525067080920773 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 4` to `Community 2`, `Community 3`, `Community 101`, `Community 42`, `Community 107`, `Community 12`, `Community 10`, `Community 77`, `Community 179`, `Community 19`, `Community 23`, `Community 89`, `Community 27`, `Community 127`?**
  _High betweenness centrality (0.012) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.018237838847219197 - nodes in this community are weakly interconnected._
- **Why does `drizzle-orm` connect `Community 0` to `Community 1`, `Community 133`, `Community 7`, `Community 8`, `Community 9`, `Community 13`, `Community 14`, `Community 15`, `Community 16`, `Community 17`, `Community 149`, `Community 22`, `Community 150`, `Community 25`, `Community 26`, `Community 28`, `Community 30`, `Community 32`, `Community 33`, `Community 38`, `Community 43`, `Community 44`, `Community 47`, `Community 49`, `Community 50`, `Community 60`, `Community 63`, `Community 72`, `Community 201`, `Community 82`, `Community 93`, `Community 221`, `Community 110`, `Community 115`?**
  _High betweenness centrality (0.011) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.022222222222222223 - nodes in this community are weakly interconnected._