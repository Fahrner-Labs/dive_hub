# Graph Report - dive_hub  (2026-11-23)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 11619 nodes · 35450 edges · 361 communities (218 shown, 143 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 697 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `6800666e`
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
- Community 236
- Community 243

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 211 edges
2. `Button()` - 204 edges
3. `useErrorText()` - 176 edges
4. `useDisplay()` - 165 edges
5. `drizzle-orm` - 150 edges
6. `unwrap()` - 143 edges
7. `Muted()` - 139 edges
8. `createTestDatabase()` - 111 edges
9. `TestDatabase` - 110 edges
10. `databaseReachable()` - 109 edges

## Surprising Connections (you probably didn't know these)
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `Context` --references--> `Position`  [EXTRACTED]
  apps/server/src/imports/formats/uddf/uddf.ts → apps/server/src/vocabulary.ts
- `RefreshHarness` --calls--> `SyncRefresher`  [INFERRED]
  apps/android/shared/src/jvmTest/kotlin/dev/divehub/shared/sync/SyncRefreshTest.kt → apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/sync/SyncRefresher.kt

## Import Cycles
- None detected.

## Communities (361 total, 143 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.01
Nodes (393): user, buddySuggestion, connection, cylinder, dive, diveLease, diver, duplicateCandidate (+385 more)

### Community 1 - "Community 1"
Cohesion: 0.02
Nodes (267): createServices(), ADR-0027, ADR-0030, ADR-0061, ADR-0063, ADR-0066, ADR-0070, Tx (+259 more)

### Community 2 - "Community 2"
Cohesion: 0.02
Nodes (273): accountListEntry, ConnectionRow, diveFingerprint, providerGoneDive, providerLease, pushRun, pushRunItem, PushRunLeftOut (+265 more)

### Community 3 - "Community 3"
Cohesion: 0.02
Nodes (267): cylinderCatalogueQuery(), ToolsPlan, ToolsPlanDiver, CylinderFields(), CylinderList(), MATERIALS, ADR-0034, ADR-0045 (+259 more)

### Community 4 - "Community 4"
Cohesion: 0.04
Nodes (223): ChangePassword(), DisplaySettings(), Sessions(), ADR-0080, useDescribeAgent(), AdminSettings(), ClientDiagnosticsSetting(), DeleteUserDialog() (+215 more)

### Community 5 - "Community 5"
Cohesion: 0.02
Nodes (192): ServiceParts, Db, Queryable, EMPTY_SITE_IMPORT_COUNTS, ImportArea, ImportedValues, SiteImportCounts, StoredConflictField (+184 more)

### Community 6 - "Community 6"
Cohesion: 0.02
Nodes (172): ClientDiagnosticsSettingsView, clientDiagnosticsStatusQuery(), ClientDiagnosticsStatusView, ADR-0080, CertificationView, DeviceView, diverDetailsQuery(), DiverDetailsView (+164 more)

### Community 7 - "Community 7"
Cohesion: 0.02
Nodes (167): divesQuery(), LogbookPage, PAGE_SIZE, equipmentDueQuery(), ExternalIdView, siteQuery(), App(), BackupsPage (+159 more)

### Community 8 - "Community 8"
Cohesion: 0.02
Nodes (150): ImportErrorCode, ImportFound, importOriginal, ImportOutcome, JOINT_FILL_FIELDS, jointDiveDismissal, JointFillField, logbookCheckAnswer (+142 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (80): FactRow(), NoteText(), StackedNoteText(), Section(), cylinderLine(), cylinderPressures(), Cylinders(), Direction (+72 more)

### Community 10 - "Community 10"
Cohesion: 0.05
Nodes (8): label(), SettingsDebugRoute(), SettingsDebugScreen(), Spacing, SyncLine(), LoadingSource, limitWord(), DiveHubText

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (127): label(), Me, AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery() (+119 more)

### Community 12 - "Community 12"
Cohesion: 0.02
Nodes (125): CentreService, CentreRouteDeps, LEAD_PLACEMENTS, DiverService, downsampleMinMax(), CertificationHint, CertificationHintExpiry, DiveCertifications (+117 more)

### Community 13 - "Community 13"
Cohesion: 0.03
Nodes (135): ComputerChoice, DiveImportMode, DiveKind, ProviderImportPlan, providerSiteData, mergeProviderCylinder(), ProviderCylinder, siteRef() (+127 more)

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (126): importQuery(), buddiesForQuery(), buddiesQuery(), BuddyView, ConflictChoices, ConflictEntryView, ConflictFieldView, ConflictsOfConnection (+118 more)

### Community 15 - "Community 15"
Cohesion: 0.03
Nodes (116): DIVE_IMPORT_MODES, DIVE_KINDS, IMPORT_ERROR_CODES, OUTCOME_REASONS, SUGGESTION_STATUSES, DiveSummaryFields, SummaryRow, DIVE_ONLY_PATTERN (+108 more)

### Community 16 - "Community 16"
Cohesion: 0.02
Nodes (125): CHANNELS, ADR-0036, ENGINE_VERSION, actorType, aiAccessLog, aiAccessOutcome, backupRun, backupRunKind (+117 more)

### Community 17 - "Community 17"
Cohesion: 0.05
Nodes (19): logbookDiveItemIndex(), LogbookUiState, LogbookScreenshotTest, LogbookScreenTest, DiveListView, DiveListViewCounts, DiveListViewDivesItem, DiveListViewDivesItemGasesItem (+11 more)

### Community 18 - "Community 18"
Cohesion: 0.04
Nodes (115): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+107 more)

### Community 19 - "Community 19"
Cohesion: 0.02
Nodes (85): PushLeftOut, createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities() (+77 more)

### Community 20 - "Community 20"
Cohesion: 0.05
Nodes (109): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, assessmentRoutes() (+101 more)

### Community 21 - "Community 21"
Cohesion: 0.04
Nodes (18): AndroidDiveStore, DiveStore, toBoolean(), toLong(), toModel(), BodyWeight, Cylinder, Diver (+10 more)

### Community 22 - "Community 22"
Cohesion: 0.03
Nodes (90): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError (+82 more)

### Community 23 - "Community 23"
Cohesion: 0.06
Nodes (13): FakeCache, RevisionFollowTest, LeadEstimateViewModelTest, ErrorCodes, PlanningSacSource, default, logbook, Dive (+5 more)

### Community 24 - "Community 24"
Cohesion: 0.05
Nodes (94): allButDive(), continuationCandidateQuery(), deletedDivesOn(), deletedDivesQuery(), diveQuery(), DiveView, mergeCandidatesQuery(), nearbyDivesQuery() (+86 more)

### Community 25 - "Community 25"
Cohesion: 0.05
Nodes (24): AppNavHost(), AppScreens, clearSavedTabStates(), isShowing(), leaveDiveForLogbook(), MainShell(), navigateToTab(), tabOf() (+16 more)

### Community 26 - "Community 26"
Cohesion: 0.04
Nodes (48): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+40 more)

### Community 27 - "Community 27"
Cohesion: 0.04
Nodes (71): SessionUser, fastify, FastifyRequest, isAdmin(), publicPaths, sessionIdOf(), ADR-0011, ADR-0074 (+63 more)

### Community 28 - "Community 28"
Cohesion: 0.07
Nodes (34): DecodeException, DiveHubException, NoAnswerException, LiveHint, LiveHints, Body, FeedEntry, Hold (+26 more)

### Community 29 - "Community 29"
Cohesion: 0.05
Nodes (10): DiveDetailViewModelTest, GatedDispatcher, CountingDispatcher, LogbookViewModelTest, ApiClient, LogbookRepository, LogbookRead, StoreLogbook (+2 more)

### Community 30 - "Community 30"
Cohesion: 0.06
Nodes (72): Formats, GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult(), PlanInputs() (+64 more)

### Community 31 - "Community 31"
Cohesion: 0.04
Nodes (21): ApiClientDiagnosticsStatusGet200Response, ApiMeGet200ResponseOwnDiver, DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, ProviderSummaryData (+13 more)

### Community 32 - "Community 32"
Cohesion: 0.04
Nodes (73): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+65 more)

### Community 33 - "Community 33"
Cohesion: 0.08
Nodes (45): BottomTime, bottomTime(), BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen (+37 more)

### Community 34 - "Community 34"
Cohesion: 0.05
Nodes (13): SettingsDiagnosticsState, SettingsUiState, SettingsViewModel, FakeDiagnostics, FakeInstances, SettingsViewModelTest, ReportProblemResult, Sent (+5 more)

### Community 35 - "Community 35"
Cohesion: 0.06
Nodes (33): Profile(), ProfileChart(), recordingName(), Recordings(), DiveViewRecordingsItem, DiveViewRecordingsItemDevice, DiveViewRecordingsItemSummarySurfaceBreaksItem, RecordingSamplesView (+25 more)

### Community 36 - "Community 36"
Cohesion: 0.05
Nodes (57): BACKUP_EXTENSION, BACKUP_SKIPPED_DIRS, BackupDecryptionKey, BackupPart, encryptionKeyFingerprint(), openArchiveStreamingWith(), BackupError, BackupLog (+49 more)

### Community 37 - "Community 37"
Cohesion: 0.06
Nodes (4): SectionTest, LoadingScreenshotTest, SyncLineTest, UpdateRequiredScreenTest

### Community 38 - "Community 38"
Cohesion: 0.05
Nodes (27): Radius, tankLabel(), drawBand(), ProfileSketch(), DiveViewRecordingsItemSummaryGasesItem, durationMinutes(), initials(), mixName() (+19 more)

### Community 39 - "Community 39"
Cohesion: 0.05
Nodes (7): AppContainer, MainActivity, MainViewModel, Unreachable, LoadingsUiState, LoadingsViewModel, ToolsViewModel

### Community 40 - "Community 40"
Cohesion: 0.07
Nodes (56): BottomTime, bottomTime(), Limit, ADR-0032, ADR-0033, ADR-0071, ambientPressureBar(), bestMixPercent() (+48 more)

### Community 41 - "Community 41"
Cohesion: 0.06
Nodes (62): BACKUP_FORMAT_VERSION, BackupArchiveError, BackupEncryption, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32Encode() (+54 more)

### Community 42 - "Community 42"
Cohesion: 0.05
Nodes (42): captureDiagnostic(), captureError(), cleanContext(), clearDiagnostics(), CLIENT_NAME, CLIENT_VERSION, DiagnosticEntry, diagnosticFromError() (+34 more)

### Community 43 - "Community 43"
Cohesion: 0.05
Nodes (58): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, Suit, WeightingFeedback, answer(), baseFrom() (+50 more)

### Community 44 - "Community 44"
Cohesion: 0.04
Nodes (56): AssessmentService, AssessmentView, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView() (+48 more)

### Community 45 - "Community 45"
Cohesion: 0.08
Nodes (11): ChangeFeedEvent, ChangeFeedPage, Complete, SessionEnded, Stopped, SyncEngine, SyncPhase, Details (+3 more)

### Community 46 - "Community 46"
Cohesion: 0.06
Nodes (47): SsiClient, SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), buildUpdateRecord(), emptyRecord(), keysOf() (+39 more)

### Community 47 - "Community 47"
Cohesion: 0.06
Nodes (27): InkButton(), QuietButton(), EmptyState(), rememberReducedMotion(), rememberSkeletonAlpha(), SkeletonBar(), AvatarCircle(), Error() (+19 more)

### Community 48 - "Community 48"
Cohesion: 0.04
Nodes (60): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+52 more)

### Community 49 - "Community 49"
Cohesion: 0.08
Nodes (53): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+45 more)

### Community 50 - "Community 50"
Cohesion: 0.04
Nodes (10): KeystoreTokenStore, PreferencesCachedMeStore, PreferencesInstanceUrlStore, UserCacheStore, ApiDiverBodyWeightsGet200ResponseItem, ApiDiveSitesGet200Response, SiteView, CachedMeStore (+2 more)

### Community 51 - "Community 51"
Cohesion: 0.06
Nodes (18): UiState, sessionDestination(), SessionDestinationTest, ApiMeGet200Response, ApiMeGet200ResponsePreferences, SignInEmailRequest, UserView, InstanceUrl (+10 more)

### Community 52 - "Community 52"
Cohesion: 0.06
Nodes (55): replyCertificationError, STATUS, CertificationService, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams (+47 more)

### Community 53 - "Community 53"
Cohesion: 0.05
Nodes (37): ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS, eachFile(), readEntry(), ADR-0044, walk() (+29 more)

### Community 54 - "Community 54"
Cohesion: 0.05
Nodes (45): BACKUP_RUN_TASK, backupTask(), ADR-0069, EXPIRE_WAITING_IMPORTS_TASK, PUSH_RUN_STEP_TASK, AUTOMATION_TICK_TASK, REVISION_ENTITY_TYPES, RevisionEntityType (+37 more)

### Community 55 - "Community 55"
Cohesion: 0.06
Nodes (36): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+28 more)

### Community 56 - "Community 56"
Cohesion: 0.06
Nodes (17): LogbookViewModel, followStoreWrites(), DiveRepository, neighboursPath(), LogbookFilter, CHANGED_SINCE_SENT, NO_RECORDING, NO_SITE (+9 more)

### Community 57 - "Community 57"
Cohesion: 0.05
Nodes (52): BottomTimeInput, Conditions, GasMix, GasPlanInput, GasRule, LoadingDive, TissuesAfterDivesInput, NdlInput (+44 more)

### Community 58 - "Community 58"
Cohesion: 0.06
Nodes (41): createServer(), loadAuthSecret(), workerDeps(), Config, loadConfig(), ADR-0011, ADR-0021, ADR-0024 (+33 more)

### Community 59 - "Community 59"
Cohesion: 0.07
Nodes (43): ReportProblemSettings(), SecuritySettings(), BackupRunView, backupSettingsQuery(), BackupSettingsView, backupsQuery(), SiteDataView, supportBundlePreviewQuery() (+35 more)

### Community 60 - "Community 60"
Cohesion: 0.13
Nodes (22): Body, BrokenBody, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage(), diverJson() (+14 more)

### Community 61 - "Community 61"
Cohesion: 0.07
Nodes (43): HINT, Input, LoadingsInput, planGasAndBottomTime, ADR-0032, ADR-0035, Used(), BOTTOM_TIME_RULES (+35 more)

### Community 62 - "Community 62"
Cohesion: 0.10
Nodes (9): FakeCache, FakeCachedMe, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore (+1 more)

### Community 63 - "Community 63"
Cohesion: 0.07
Nodes (44): createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open(), saveAndReadBack() (+36 more)

### Community 64 - "Community 64"
Cohesion: 0.09
Nodes (9): LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200ResponseMissingItem, ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseCns, ApiDiversIdLoadingsGet200ResponseN2 (+1 more)

### Community 65 - "Community 65"
Cohesion: 0.09
Nodes (50): Answer, answer(), baseFrom(), Confidence, high, low, medium, Cylinder (+42 more)

### Community 67 - "Community 67"
Cohesion: 0.06
Nodes (42): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+34 more)

### Community 68 - "Community 68"
Cohesion: 0.05
Nodes (37): auth, baseDbUrl, { db, pool }, divers, E2E_USER, fakeSsi, garminDive(), here() (+29 more)

### Community 69 - "Community 69"
Cohesion: 0.07
Nodes (14): Answers, ApiToolsBottomTimeGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseLimits, ApiToolsBottomTimeGet200ResponseResidualN2, ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule (+6 more)

### Community 70 - "Community 70"
Cohesion: 0.06
Nodes (42): diveListFilter(), notAtProvider(), Blocked, classify(), FailureOutcome, failureOutcome(), kindOf(), KINDS (+34 more)

### Community 71 - "Community 71"
Cohesion: 0.08
Nodes (37): createSsiSiteSource(), createSiteSources(), ADR-0021, ADR-0027, createOverpassSource(), DEFAULT_OVERPASS_URL, Element, overpassQuery() (+29 more)

### Community 72 - "Community 72"
Cohesion: 0.05
Nodes (6): DiveHubApplication, SyncWorker, WorkManagerPeriodicSync, PeriodicSyncScheduler, SseLiveHintsEngineTest, TrickleServer

### Community 73 - "Community 73"
Cohesion: 0.08
Nodes (43): NoFly, getDiverLoadings, DEFAULT_GF_HIGH, residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows(), DiveRow (+35 more)

### Community 74 - "Community 74"
Cohesion: 0.06
Nodes (42): ADR-0036, ADR-0063, ADR-0038, ADR-0048, ADR-0062, AiAccess, assessedDive(), AssessedFinding (+34 more)

### Community 75 - "Community 75"
Cohesion: 0.07
Nodes (43): DiveValues, OverridableField, revisionsQuery(), RevisionView, UtcOffsetSource, DiveHistory(), Entry(), isValue() (+35 more)

### Community 76 - "Community 76"
Cohesion: 0.05
Nodes (39): CYLINDER_MATERIALS, SUIT_TYPES, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053, replyDiverError (+31 more)

### Community 77 - "Community 77"
Cohesion: 0.08
Nodes (40): DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining(), ssiDiveType(), ssiInteger() (+32 more)

### Community 78 - "Community 78"
Cohesion: 0.05
Nodes (42): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+34 more)

### Community 79 - "Community 79"
Cohesion: 0.08
Nodes (7): SettingsRouteTest, DiagnosticEntry, DiagnosticLevel, ERROR, WARN, DiagnosticsBuffer, DiagnosticsBufferTest

### Community 80 - "Community 80"
Cohesion: 0.06
Nodes (36): CylinderMaterial, aluminium, carbon, steel, EstimateDiveCylinder, neededKg(), WeightingFeedback, WeightingFelt (+28 more)

### Community 81 - "Community 81"
Cohesion: 0.08
Nodes (28): auth, BackupServiceDeps, PROBLEMS, out, services, createSecretBox(), ADR-0024, BlobStore (+20 more)

### Community 82 - "Community 82"
Cohesion: 0.06
Nodes (38): account, accountRelations, apikey, rateLimit, session, sessionRelations, ADR-0011, ADR-0012 (+30 more)

### Community 83 - "Community 83"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 84 - "Community 84"
Cohesion: 0.06
Nodes (29): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, APP (+21 more)

### Community 85 - "Community 85"
Cohesion: 0.10
Nodes (13): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), number() (+5 more)

### Community 86 - "Community 86"
Cohesion: 0.08
Nodes (29): buildManifest(), encryptFile(), openArchive(), packContainer(), BackupService, ADR-0004, UploadTooLargeError, createScratchDatabase() (+21 more)

### Community 87 - "Community 87"
Cohesion: 0.06
Nodes (41): Change(), Changes(), historyRoute, Made(), REVISION_CAUSES, PositionSchema, AdoptBody, Country (+33 more)

### Community 88 - "Community 88"
Cohesion: 0.07
Nodes (7): ApiClientDiagnosticsPostRequest, ApiClientDiagnosticsPostRequestClient, ApiClientDiagnosticsPostRequestEntriesItem, ApiErrorBody, DiagnosticHygiene, Diagnostics, Failed

### Community 91 - "Community 91"
Cohesion: 0.07
Nodes (34): sampleCount(), SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject() (+26 more)

### Community 92 - "Community 92"
Cohesion: 0.11
Nodes (8): DiveHubTopBar(), titleLineHeight(), TitleSkeleton(), DiveHubColors, DiveHubTheme(), isDarkScheme(), successColor(), DiveHubTopBarTest

### Community 93 - "Community 93"
Cohesion: 0.08
Nodes (14): LeadAnswer, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseLeftOut, ApiDiversIdLeadEstimateGet200ResponseRange, ApiDiversIdLeadEstimateGet200ResponseReserve, ApiDiversIdLeadEstimateGet200ResponseWeightCheck, ApiDiversIdLoadingsGet200ResponseNoFly (+6 more)

### Community 94 - "Community 94"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 95 - "Community 95"
Cohesion: 0.05
Nodes (39): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+31 more)

### Community 96 - "Community 96"
Cohesion: 0.07
Nodes (38): SUIT_UNDERGARMENTS, WEIGHTING_FELT, DiveRow, GROUPS, participantsOf(), recordingsOf(), seriesOf(), minutes() (+30 more)

### Community 97 - "Community 97"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 98 - "Community 98"
Cohesion: 0.08
Nodes (34): @modelcontextprotocol/server@^2.3.1, aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, AiAccessService, KeyRow, LOG_DAYS (+26 more)

### Community 99 - "Community 99"
Cohesion: 0.08
Nodes (32): CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite(), lock() (+24 more)

### Community 100 - "Community 100"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 101 - "Community 101"
Cohesion: 0.07
Nodes (27): LoadingsView, running(), siteImportsQuery(), CustomTypeOptions, i18next, initI18n(), resources, ADR-0014 (+19 more)

### Community 102 - "Community 102"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 103 - "Community 103"
Cohesion: 0.13
Nodes (34): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+26 more)

### Community 104 - "Community 104"
Cohesion: 0.07
Nodes (28): headers, ADR-0030, ADR-0040, conflictForLena(), diveWithoutRecording(), editInSsi(), externalDiver(), importLena() (+20 more)

### Community 105 - "Community 105"
Cohesion: 0.15
Nodes (6): LogbookPhase, Error, List, Loading, Offline, SettingsScreenshotTest

### Community 106 - "Community 106"
Cohesion: 0.11
Nodes (28): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+20 more)

### Community 107 - "Community 107"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 108 - "Community 108"
Cohesion: 0.10
Nodes (26): ADR-0051, Status, ADR-0056, ADR-0024, ADR-0043, clearQueue(), conflictAtSsi(), connectSsi() (+18 more)

### Community 109 - "Community 109"
Cohesion: 0.12
Nodes (31): importsQuery(), ImportView, ImportContext, ImportFilesButton(), ImportHistory(), ImportList(), ImportNotices(), ImportPanel() (+23 more)

### Community 110 - "Community 110"
Cohesion: 0.09
Nodes (25): createFileFormats(), createFitAdapter(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf() (+17 more)

### Community 111 - "Community 111"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 112 - "Community 112"
Cohesion: 0.09
Nodes (27): AiAccessScope, diversOf(), Cursor, cursorAt(), DateOnly(), diversAsked(), inputProblem(), loggable() (+19 more)

### Community 113 - "Community 113"
Cohesion: 0.10
Nodes (29): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), divesAtSite() (+21 more)

### Community 114 - "Community 114"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 115 - "Community 115"
Cohesion: 0.17
Nodes (4): DiveRow(), Figures(), RowFacts(), ComponentsScreenshotTest

### Community 116 - "Community 116"
Cohesion: 0.29
Nodes (8): leadAnswer(), EstimateCylinder, PlannedConditions, Suit, assemblePersonalizedSources(), currentLoadings(), leadEstimate(), PersonalizedToolsTest

### Community 117 - "Community 117"
Cohesion: 0.11
Nodes (4): FakeCachedMe, FakeInstances, FakeTokens, MainViewModelTest

### Community 118 - "Community 118"
Cohesion: 0.11
Nodes (8): InstanceProbeTest, json(), probe(), ProbeCache, ProbeCachedMe, ProbeInstances, probeRepository(), ProbeTokens

### Community 119 - "Community 119"
Cohesion: 0.11
Nodes (26): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DueInput, DueSchedule (+18 more)

### Community 120 - "Community 120"
Cohesion: 0.10
Nodes (24): AL80, asEstimateInput(), EstimateDiveCylinder, evaluateLeadVectorCase(), LEAD_CASES, LEAD_VECTOR_KERNEL, leadInput(), leadVectorFile() (+16 more)

### Community 121 - "Community 121"
Cohesion: 0.07
Nodes (12): buddySite(), Dive, OTHER, PendingOffer, ADR-0012, ADR-0070, Dive, OTHER (+4 more)

### Community 122 - "Community 122"
Cohesion: 0.09
Nodes (22): BOM, Json, num(), obj(), positive(), scaled(), suuntoFitKey(), SuuntoJsonAdapter (+14 more)

### Community 123 - "Community 123"
Cohesion: 0.10
Nodes (26): globalSetup(), ADR-0023, accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks() (+18 more)

### Community 125 - "Community 125"
Cohesion: 0.09
Nodes (26): DUE_STATES, equipmentList(), today(), usageSummary(), SiteRow, Detail, excerpt(), Paging (+18 more)

### Community 126 - "Community 126"
Cohesion: 0.13
Nodes (23): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, Site (+15 more)

### Community 127 - "Community 127"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 128 - "Community 128"
Cohesion: 0.14
Nodes (17): DiveDetailRoute(), DiveHubApp(), DiveHubScreens, ErrorText(), Gate(), InstanceForm(), InstanceStatus(), LoadingScreen() (+9 more)

### Community 129 - "Community 129"
Cohesion: 0.13
Nodes (3): LauncherIconTest, SplashTheme, SplashThemeTest

### Community 130 - "Community 130"
Cohesion: 0.21
Nodes (4): GasPlanScreenshotTest, GasPlanToolDiver, GasPlanToolInput, GasPlanToolLevel

### Community 131 - "Community 131"
Cohesion: 0.11
Nodes (23): AssessmentError, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), requireManagedDive(), isRule() (+15 more)

### Community 132 - "Community 132"
Cohesion: 0.12
Nodes (22): acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), AppLockProbe (+14 more)

### Community 133 - "Community 133"
Cohesion: 0.11
Nodes (23): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+15 more)

### Community 134 - "Community 134"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 136 - "Community 136"
Cohesion: 0.13
Nodes (20): Estimate, isoUtc(), LoadingBasis, LoadingDive, N2Loadings, N2UnavailableReason, no_profile, no_recent_dive (+12 more)

### Community 137 - "Community 137"
Cohesion: 0.17
Nodes (23): DiveOrigin, SsiLogbook, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef(), MATERIAL_OF_TANK_TYPE (+15 more)

### Community 138 - "Community 138"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 139 - "Community 139"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 140 - "Community 140"
Cohesion: 0.10
Nodes (22): CENTRE_SOURCE_NAME, CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+14 more)

### Community 141 - "Community 141"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 142 - "Community 142"
Cohesion: 0.21
Nodes (4): DiveDetailScreen(), NeighboursView, NeighboursViewNext, NeighboursViewPrevious

### Community 143 - "Community 143"
Cohesion: 0.13
Nodes (6): ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting

### Community 144 - "Community 144"
Cohesion: 0.16
Nodes (6): Connected, Hint, HintFrame, LiveEvent, SseLiveHints, LiveHintsTest

### Community 145 - "Community 145"
Cohesion: 0.16
Nodes (3): StreamFailure, SyncRefresher, toStreamFailure()

### Community 146 - "Community 146"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 147 - "Community 147"
Cohesion: 0.14
Nodes (5): DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewCylindersItemSeries, DiveViewLeadItem, DiveViewSac

### Community 148 - "Community 148"
Cohesion: 0.16
Nodes (21): compressibility(), Cubic, minusOne(), pressureForLitres(), surfaceLitres(), jsRound(), powInt(), roundTo() (+13 more)

### Community 149 - "Community 149"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 150 - "Community 150"
Cohesion: 0.16
Nodes (6): AppTab, Logbook, Settings, Tools, DiveHubBottomBar(), BottomBarScreenshotTest

### Community 151 - "Community 151"
Cohesion: 0.18
Nodes (3): LeadEstimateUiState, LeadEstimateViewModel, PersonalizedSources

### Community 152 - "Community 152"
Cohesion: 0.21
Nodes (3): DiveViewRecordingsItemSummary, summaryOf(), StoreLogbookTest

### Community 153 - "Community 153"
Cohesion: 0.14
Nodes (4): FakeCachedMe, FakeInstances, FakeTokens, SyncAuthTest

### Community 155 - "Community 155"
Cohesion: 0.12
Nodes (18): siteImportStatus, PROBLEM_CODES, SiteImportRouteDeps, StartBody, STATUS, ADR-0021, SiteImportError, SiteImportService (+10 more)

### Community 156 - "Community 156"
Cohesion: 0.16
Nodes (16): DecisionTimelineProps, GapKind, ADR-0063, addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, laneFitsCapsule(), relations() (+8 more)

### Community 157 - "Community 157"
Cohesion: 0.15
Nodes (5): ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits

### Community 158 - "Community 158"
Cohesion: 0.15
Nodes (5): SiteViewExternalIdsItem, SiteViewExternalIdsItemAttribution, SiteViewExternalIdsItemOffered, SiteViewExternalIdsItemOfferedPosition, SiteViewExternalIdsItemPrivateTo

### Community 159 - "Community 159"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource/poppins@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 160 - "Community 160"
Cohesion: 0.16
Nodes (16): errorMessage(), looksLikeZip(), failureOf(), placeFile(), placeFiles(), coordinate(), DEFAULT_SSI_SITES_URL, parseSsiSites() (+8 more)

### Community 161 - "Community 161"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 162 - "Community 162"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 163 - "Community 163"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 164 - "Community 164"
Cohesion: 0.14
Nodes (18): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript-eslint@8.72.0, engines (+10 more)

### Community 165 - "Community 165"
Cohesion: 0.19
Nodes (11): LeadInputTest, SuitType, drysuit, none, semidry, skin, wetsuit, SuitUndergarment (+3 more)

### Community 166 - "Community 166"
Cohesion: 0.12
Nodes (18): typescript@7.0.2, typescript@7.0.2, typescript@6.0.3, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0 (+10 more)

### Community 167 - "Community 167"
Cohesion: 0.15
Nodes (15): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+7 more)

### Community 168 - "Community 168"
Cohesion: 0.18
Nodes (7): Action(), ConnectionStatus(), Section(), SettingsRoute(), SettingsScreen(), formatSyncTime(), SyncTimeFormatTest

### Community 169 - "Community 169"
Cohesion: 0.14
Nodes (15): UTC_OFFSET_SOURCES, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053, CandidateRouteDeps, CandidateView, Decided (+7 more)

### Community 170 - "Community 170"
Cohesion: 0.12
Nodes (16): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+8 more)

### Community 171 - "Community 171"
Cohesion: 0.19
Nodes (4): DiveDetailsListView, DiveDetailsListViewCounts, DiveDetailsListViewMonthsItem, DiveDetailsListViewTotals

### Community 172 - "Community 172"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 173 - "Community 173"
Cohesion: 0.21
Nodes (11): buildSupportBundle(), buildTarGz(), json(), ndjson(), octal(), SUPPORT_BUNDLE_FORMAT, SUPPORT_BUNDLE_FORMAT_VERSION, SupportBufferManifest (+3 more)

### Community 174 - "Community 174"
Cohesion: 0.23
Nodes (13): clientVersionGate(), ADR-0076, ClientVersionRegistry, MIN_CLIENT_VERSIONS, MinClientVersion, ADR-0076, compareIdentifier(), compareNumeric() (+5 more)

### Community 175 - "Community 175"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 176 - "Community 176"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 178 - "Community 178"
Cohesion: 0.13
Nodes (12): clientDiagnosticsSettings, ClientDiagnosticsEntryInput, ClientDiagnosticsEntryView, ClientDiagnosticsError, ClientDiagnosticsUpload, KEEP_NEWEST, MAX_UPLOAD_ENTRIES, MESSAGE_MAX_LENGTH (+4 more)

### Community 179 - "Community 179"
Cohesion: 0.19
Nodes (11): decimal(), dive(), makeOceanicPlusUddf(), OCEANIC_DIVES, OceanicDive, site(), waypoint(), exported (+3 more)

### Community 180 - "Community 180"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 181 - "Community 181"
Cohesion: 0.13
Nodes (14): concurrency, cancel-in-progress, group, env, RELEASE_TAG, jobs, publish, name (+6 more)

### Community 183 - "Community 183"
Cohesion: 0.24
Nodes (13): asNumber(), asText(), compareReadBack(), datasetLength(), describe(), isObject(), keyDifferences(), pause() (+5 more)

### Community 184 - "Community 184"
Cohesion: 0.20
Nodes (5): LogRingBuffer, RedactionCounters, SupportBundleInput, SupportBundleManifest, SupportServiceDeps

### Community 185 - "Community 185"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 186 - "Community 186"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 187 - "Community 187"
Cohesion: 0.24
Nodes (3): DiveViewSite, DiveViewValues, DiveViewValuesStartsAt

### Community 188 - "Community 188"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 189 - "Community 189"
Cohesion: 0.26
Nodes (3): DiverView, DiverViewCertifications, DiverViewCertificationsNewest

### Community 190 - "Community 190"
Cohesion: 0.26
Nodes (3): DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 191 - "Community 191"
Cohesion: 0.18
Nodes (10): LogBufferStats, SupportBundle, ClientDiagnosticsProvider, FailedBackup, FailedJob, HealthSection, iso(), JobsSection (+2 more)

### Community 192 - "Community 192"
Cohesion: 0.18
Nodes (11): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, ADR-0076, ADR-0083, MODULE_IMPORTS (+3 more)

### Community 193 - "Community 193"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 194 - "Community 194"
Cohesion: 0.20
Nodes (10): defineTool(), Id(), Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), NOTICE (+2 more)

### Community 195 - "Community 195"
Cohesion: 0.24
Nodes (7): compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051, ADR-0051

### Community 196 - "Community 196"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 197 - "Community 197"
Cohesion: 0.22
Nodes (8): diveRow(), rowOf(), ADR-0026, ADR-0048, diveRows(), goToMore(), expectFirstDiveInView(), rowOf()

### Community 198 - "Community 198"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 199 - "Community 199"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 200 - "Community 200"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 201 - "Community 201"
Cohesion: 0.33
Nodes (8): prepareEntry(), DENIED_KEY_PARTS, isDeniedKey(), maskString(), redactInner(), redactLogObject(), RedactOptions, redactValue()

### Community 202 - "Community 202"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 203 - "Community 203"
Cohesion: 0.20
Nodes (10): verify, prerelease, sha, tag, versionCode, versionName, name, outputs (+2 more)

### Community 204 - "Community 204"
Cohesion: 0.20
Nodes (10): with, cache, distribution, fetch-depth, if-no-files-found, java-version, name, path (+2 more)

### Community 205 - "Community 205"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 209 - "Community 209"
Cohesion: 0.28
Nodes (7): ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, s(), ADR-0035

### Community 211 - "Community 211"
Cohesion: 0.31
Nodes (5): BufferedLog, createLogRingBuffer(), LOG_BUFFER_CAPACITY_BYTES, LOG_BUFFER_CAPACITY_ENTRIES, LogRingBufferOptions

### Community 212 - "Community 212"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 213 - "Community 213"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 214 - "Community 214"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 217 - "Community 217"
Cohesion: 0.46
Nodes (8): appliedMigrationHead(), emptyRedactionCounters(), createSupportService(), assemble(), clientDiagnostics(), configSection(), failedJobs(), healthSection()

### Community 218 - "Community 218"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 219 - "Community 219"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 220 - "Community 220"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 221 - "Community 221"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 222 - "Community 222"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 223 - "Community 223"
Cohesion: 0.40
Nodes (5): stubDive(), ADR-0015, ADR-0023, ADR-0025, guardedRoute()

### Community 224 - "Community 224"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 225 - "Community 225"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 226 - "Community 226"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build

### Community 227 - "Community 227"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 228 - "Community 228"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 232 - "Community 232"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 233 - "Community 233"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 234 - "Community 234"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1339 isolated node(s):** `Reachable`, `Unknown`, `Reserve`, `RockBottom`, `Sent` (+1334 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3856 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **143 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 0` to `Community 1`, `Community 2`, `Community 5`, `Community 8`, `Community 12`, `Community 13`, `Community 15`, `Community 16`, `Community 20`, `Community 22`, `Community 27`, `Community 36`, `Community 48`, `Community 178`, `Community 54`, `Community 58`, `Community 61`, `Community 191`, `Community 68`, `Community 73`, `Community 81`, `Community 82`, `Community 98`, `Community 99`, `Community 112`, `Community 113`?**
  _High betweenness centrality (0.020) - this node is a cross-community bridge._
- **What connects `Reachable`, `Unknown`, `Reserve` to the rest of the system?**
  _1339 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.013225353726298905 - nodes in this community are weakly interconnected._
- **Why does `react` connect `Community 6` to `Community 3`, `Community 4`, `Community 101`, `Community 7`, `Community 42`, `Community 11`, `Community 75`, `Community 109`, `Community 14`, `Community 49`, `Community 114`, `Community 24`, `Community 59`, `Community 156`, `Community 94`, `Community 159`?**
  _High betweenness centrality (0.016) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.015092587217739595 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 6` to `Community 3`, `Community 4`, `Community 101`, `Community 7`, `Community 11`, `Community 75`, `Community 109`, `Community 14`, `Community 49`, `Community 114`, `Community 24`, `Community 59`, `Community 94`, `Community 159`?**
  _High betweenness centrality (0.015) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.015114003913269176 - nodes in this community are weakly interconnected._