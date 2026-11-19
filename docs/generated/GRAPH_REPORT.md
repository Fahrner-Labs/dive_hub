# Graph Report - dive_hub  (2026-11-19)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 9592 nodes · 29114 edges · 295 communities (186 shown, 109 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 515 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `a128110f`
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
- Community 190
- Community 191
- Community 192
- Community 193
- Community 194
- Community 195
- Community 198

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 204 edges
2. `Button()` - 198 edges
3. `useErrorText()` - 169 edges
4. `useDisplay()` - 161 edges
5. `Muted()` - 139 edges
6. `drizzle-orm` - 139 edges
7. `unwrap()` - 138 edges
8. `announce()` - 106 edges
9. `createTestDatabase()` - 103 edges
10. `TestDatabase` - 102 edges

## Surprising Connections (you probably didn't know these)
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `AuthOptions` --references--> `Db`  [EXTRACTED]
  apps/server/src/auth/auth.ts → apps/server/src/db/client.ts
- `askedAt()` --calls--> `DiveError`  [EXTRACTED]
  apps/server/src/dives/continuation/append-service.ts → apps/server/src/dives/dive-service.ts

## Import Cycles
- None detected.

## Communities (295 total, 109 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (217): user, buddySuggestion, dive, diveFinding, diveLease, diveSiteExternalId, LEASE_MS, MAX_WAIT_MS (+209 more)

### Community 1 - "Community 1"
Cohesion: 0.04
Nodes (207): DisplaySettings(), AdminSettings(), DeleteUserDialog(), Invitations(), Invite(), LinkToPassOn(), ProviderSiteData(), SiteDataPanel() (+199 more)

### Community 2 - "Community 2"
Cohesion: 0.03
Nodes (170): Queryable, continuationAppend, equipmentUse, LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, participant, sampleSeries (+162 more)

### Community 3 - "Community 3"
Cohesion: 0.03
Nodes (182): cylinderCatalogueQuery(), equipmentOfDiverQuery(), CylinderFields(), MATERIALS, ADR-0034, ADR-0045, useCatalogueName(), useCylinderProblem() (+174 more)

### Community 4 - "Community 4"
Cohesion: 0.02
Nodes (162): @modelcontextprotocol/server@^2.3.1, aiAccessLog, aiAccessSetting, LEAD_PLACEMENTS, WEIGHTING_FELT, downsampleMinMax(), AiAccessRouteDeps, AccessRefusal (+154 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (152): AcceptedBuddySuggestionView, allButDive(), AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionView, CandidateView, continuationCandidateQuery(), ContinuationCandidateView (+144 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (142): ConnectionRow, WaitingPushOptions, waitingOf(), audienceOfConnection(), audienceOfDive(), audienceOfDiver(), open(), close() (+134 more)

### Community 7 - "Community 7"
Cohesion: 0.02
Nodes (129): auth, lead, DIVE_FIELDS, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord() (+121 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (132): ImportedValues, diveSite, ProviderImportPlan, siteImport, SiteImportFinding, SiteImportProgress, PlannedConditions, dataIdsOf() (+124 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (116): diverSearchQuery(), keys, ConnectionView, ProviderView, api, centreQuery(), centresQuery(), CentreView (+108 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (126): BACKUP_EXTENSION, BACKUP_FORMAT_VERSION, BACKUP_SKIPPED_DIRS, BackupArchiveError, BackupDecryptionKey, BackupEncryption, BackupEncryptionMetadata, BackupPart (+118 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (109): ServiceParts, Db, accountListEntry, diver, diverExternalId, providerGoneDive, tidyName(), foldName() (+101 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (114): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+106 more)

### Community 13 - "Community 13"
Cohesion: 0.04
Nodes (129): ComputerChoice, DiveImportMode, DiveKind, providerSiteData, UtcOffsetSource, mergeProviderCylinder(), ProviderCylinder, providerCylinderOf() (+121 more)

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (94): connection, cylinder, duplicateCandidate, importJob, original, providerConflict, push, pushRun (+86 more)

### Community 15 - "Community 15"
Cohesion: 0.03
Nodes (110): UTC_OFFSET_SOURCES, DiveCertifications, LogbookCheckService, AppendDeps, AppendService, ContinuationService, withoutPressures(), createDeletionService() (+102 more)

### Community 16 - "Community 16"
Cohesion: 0.04
Nodes (105): label(), Me, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery(), candidatesQuery(), deletedDivesTotal(), DiveSummary (+97 more)

### Community 17 - "Community 17"
Cohesion: 0.03
Nodes (103): aiAccessSettingQuery(), BackupRunView, backupSettingsQuery(), BackupSettingsView, backupsQuery(), SiteDataView, ADR-0030, bodyWeightsQuery() (+95 more)

### Community 18 - "Community 18"
Cohesion: 0.03
Nodes (94): ImportArea, errorMessage(), looksLikeZip(), parseHint(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites() (+86 more)

### Community 19 - "Community 19"
Cohesion: 0.03
Nodes (108): DIVE_IMPORT_MODES, PARTICIPANT_ROLES, AssessmentRefresh, ADR-0036, ADR-0053, ErrorAnswer, Problem, PROBLEM_CODES (+100 more)

### Community 20 - "Community 20"
Cohesion: 0.03
Nodes (99): setupQuery(), equipmentDueQuery(), App(), BackupsPage, CentrePage, CentresPage, currentRoute(), DivePage (+91 more)

### Community 21 - "Community 21"
Cohesion: 0.03
Nodes (85): createServer(), createAuth(), loadAuthSecret(), acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS (+77 more)

### Community 22 - "Community 22"
Cohesion: 0.03
Nodes (93): adminRole, AI_ACCESS_NAME_MAX, AI_ACCESS_PREFIX, AI_ACCESS_RATE, AuthOptions, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, ADR-0011 (+85 more)

### Community 23 - "Community 23"
Cohesion: 0.03
Nodes (82): ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS, eachFile(), readEntry(), ADR-0044, walk() (+74 more)

### Community 24 - "Community 24"
Cohesion: 0.03
Nodes (25): MainActivity, DiveDetailUiState, DiveDetailViewModel, DiveHubApp(), ErrorText(), InstanceForm(), Loading(), PasswordField() (+17 more)

### Community 25 - "Community 25"
Cohesion: 0.02
Nodes (102): CHANNELS, ADR-0036, actorType, aiAccessOutcome, backupRunKind, backupRunStatus, candidateResolution, centreSource (+94 more)

### Community 26 - "Community 26"
Cohesion: 0.04
Nodes (101): ConflictFieldView, DiveImportMode, diveImportPreviewQuery(), DiveKind, providerSitesQuery(), providersQuery(), providerStatusQuery(), choice() (+93 more)

### Community 27 - "Community 27"
Cohesion: 0.04
Nodes (93): jointDive, logbookCheckAnswer, logbookCheckDiveAnswer, agreeInDepthAndDuration(), CheckedDive, DeletableDive, findChecks(), followsSoon() (+85 more)

### Community 28 - "Community 28"
Cohesion: 0.03
Nodes (75): BackupServiceDeps, CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK (+67 more)

### Community 29 - "Community 29"
Cohesion: 0.04
Nodes (49): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+41 more)

### Community 30 - "Community 30"
Cohesion: 0.06
Nodes (41): DiveBody(), DiveDetailRoute(), DiveDetailScreen(), errorText(), FactRow(), Facts(), Loading(), NeighbourButton() (+33 more)

### Community 31 - "Community 31"
Cohesion: 0.07
Nodes (79): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, assessmentRoutes(), SessionUser (+71 more)

### Community 32 - "Community 32"
Cohesion: 0.04
Nodes (76): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+68 more)

### Community 33 - "Community 33"
Cohesion: 0.07
Nodes (9): Formats, LoadingSource, limitWord(), ToolPage, Gas, Lead, Loadings, Plan (+1 more)

### Community 34 - "Community 34"
Cohesion: 0.05
Nodes (20): DiveHubTheme(), LeadAnswer, LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseBasis (+12 more)

### Community 35 - "Community 35"
Cohesion: 0.05
Nodes (16): DiveDetailScreenshotTest, DiveView, DiveViewFromRecording, DiveViewFromRecordingStartsAt, DiveViewParticipantsItem, DiveViewParticipantsItemCertificationHint, DiveViewPosition, DiveViewRecordingsItemDevice (+8 more)

### Community 36 - "Community 36"
Cohesion: 0.04
Nodes (73): replyCertificationError, STATUS, CertificationService, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams (+65 more)

### Community 37 - "Community 37"
Cohesion: 0.03
Nodes (63): Assessment, FindingView, Row, ADR-0036, upload(), Check, Deleted, DiveView (+55 more)

### Community 38 - "Community 38"
Cohesion: 0.07
Nodes (69): dateTime(), GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult(), PlanInputs() (+61 more)

### Community 39 - "Community 39"
Cohesion: 0.03
Nodes (58): ParticipantRole, DAY_MS, inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), placeLocalTime(), Position (+50 more)

### Community 40 - "Community 40"
Cohesion: 0.07
Nodes (66): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+58 more)

### Community 41 - "Community 41"
Cohesion: 0.05
Nodes (17): LogbookScreenshotTest, ApiDivesGet200Response, ApiDivesGet200ResponseDivesItem, ApiDivesGet200ResponseDivesItemGasesItem, ApiDivesGet200ResponseDivesItemParticipantsItem, ApiDivesGet200ResponseDivesItemSite, ApiDivesGet200ResponseMonthsItem, ApiDivesGet200ResponseTotals (+9 more)

### Community 42 - "Community 42"
Cohesion: 0.05
Nodes (72): buddiesForQuery(), buddiesQuery(), BuddyView, ConflictChoices, ConflictEntryView, ConflictsOfConnection, connectionsQuery(), DiveImportPreview (+64 more)

### Community 43 - "Community 43"
Cohesion: 0.05
Nodes (62): RequirementView, ADR-0014, ADR-0020, ADR-0027, ADR-0030, useFocusBackWhenClosed(), countryCodes(), countryName() (+54 more)

### Community 44 - "Community 44"
Cohesion: 0.05
Nodes (64): equipmentItem, serviceRecord, serviceRecordSchedule, serviceSchedule, managementOf(), EquipmentError, ADR-0034, ADR-0053 (+56 more)

### Community 45 - "Community 45"
Cohesion: 0.04
Nodes (62): pushRunItem, PushRunLeftOut, DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN, NO_RECORDING (+54 more)

### Community 46 - "Community 46"
Cohesion: 0.05
Nodes (62): LEAD_VECTOR_KERNEL, BottomTimeInput, Conditions, GasMix, GasPlanInput, GasRule, LoadingDive, TissuesAfterDivesInput (+54 more)

### Community 47 - "Community 47"
Cohesion: 0.09
Nodes (48): BottomTime, bottomTime(), BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen (+40 more)

### Community 48 - "Community 48"
Cohesion: 0.06
Nodes (16): ApiDiverBodyWeightsGet200ResponseItem, ApiClient, ApiErrorBody, DiveHubException, NoAnswerException, decodeList(), providerDirectory(), Complete (+8 more)

### Community 49 - "Community 49"
Cohesion: 0.07
Nodes (60): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+52 more)

### Community 50 - "Community 50"
Cohesion: 0.06
Nodes (8): AndroidDiveStore, DiveStore, toBoolean(), toLong(), toModel(), DiveSite, Recording, RecordingSeries

### Community 51 - "Community 51"
Cohesion: 0.04
Nodes (56): ADR-0025, WaterMismatch, waterMismatch(), EquipmentService, RecordInput, ScheduleInput, DiveGearItemViewSchema, DiveGearViewSchema (+48 more)

### Community 52 - "Community 52"
Cohesion: 0.08
Nodes (58): Answer, answer(), baseFrom(), BodyWeight, Confidence, high, low, medium (+50 more)

### Community 53 - "Community 53"
Cohesion: 0.08
Nodes (16): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), Answers (+8 more)

### Community 54 - "Community 54"
Cohesion: 0.05
Nodes (51): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, cancelWaitingImports() (+43 more)

### Community 55 - "Community 55"
Cohesion: 0.05
Nodes (45): AssessmentService, AssessmentView, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView() (+37 more)

### Community 56 - "Community 56"
Cohesion: 0.06
Nodes (34): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+26 more)

### Community 57 - "Community 57"
Cohesion: 0.07
Nodes (46): read(), NoFly, noFlyAfter(), seriesDiveOf(), residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows() (+38 more)

### Community 58 - "Community 58"
Cohesion: 0.05
Nodes (43): CENTRE_SOURCES, CentreService, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+35 more)

### Community 59 - "Community 59"
Cohesion: 0.07
Nodes (44): Leave, Series, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+36 more)

### Community 60 - "Community 60"
Cohesion: 0.11
Nodes (11): ToolsScreenshotTest, planningSac(), PlanningSacDive, PlanningSacResult, PlanningSacSource, default, logbook, BottomTimeToolInput (+3 more)

### Community 61 - "Community 61"
Cohesion: 0.06
Nodes (42): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+34 more)

### Community 62 - "Community 62"
Cohesion: 0.06
Nodes (38): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+30 more)

### Community 63 - "Community 63"
Cohesion: 0.10
Nodes (3): json(), LogbookRepositoryTest, RepositoryHarness

### Community 64 - "Community 64"
Cohesion: 0.07
Nodes (12): ApiMeGet200Response, ApiMeGet200ResponseOwnDiver, ApiMeGet200ResponsePreferences, ApiToolsBottomTimeGet200ResponseResidualN2, ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch (+4 more)

### Community 65 - "Community 65"
Cohesion: 0.08
Nodes (38): BottomTimeQuery, DEFAULT_GF_HIGH, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033, ADR-0071 (+30 more)

### Community 66 - "Community 66"
Cohesion: 0.08
Nodes (36): ChangePassword(), SecuritySettings(), Sessions(), useDescribeAgent(), AiAccessLogEntry, AiAccessView, authClient, invitationLinkQuery() (+28 more)

### Community 67 - "Community 67"
Cohesion: 0.07
Nodes (9): AppContainer, KeystoreTokenStore, PreferencesInstanceUrlStore, UserCacheStore, DiveHubApplication, LogbookRepository, InstanceUrlStore, TokenStore (+1 more)

### Community 68 - "Community 68"
Cohesion: 0.08
Nodes (13): ApiToolsBottomTimeGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseLimits, ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh (+5 more)

### Community 69 - "Community 69"
Cohesion: 0.05
Nodes (42): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+34 more)

### Community 70 - "Community 70"
Cohesion: 0.09
Nodes (38): EstimateDiveCylinder, WeightingFeedback, WeightingFelt, right, too_heavy, too_light, LoadingDive, dayNumber() (+30 more)

### Community 71 - "Community 71"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 73 - "Community 73"
Cohesion: 0.13
Nodes (41): withBuddies(), APP_KEYS, appRow(), BuddySaved, byId(), changedKeys(), deleteTestDive(), deleteTestEntries() (+33 more)

### Community 74 - "Community 74"
Cohesion: 0.10
Nodes (39): divesQuery(), importQuery(), importsQuery(), ImportView, ADR-0030, uploadFile(), noAnswerError(), Logbook() (+31 more)

### Community 75 - "Community 75"
Cohesion: 0.09
Nodes (22): AvatarCircle(), DiveRow(), Error(), Fact(), Filters(), Loading(), LogbookList(), LogbookRoute() (+14 more)

### Community 76 - "Community 76"
Cohesion: 0.15
Nodes (19): Body, diveListItem(), divePage(), diverJson(), Harness, json(), manyDives(), manySites() (+11 more)

### Community 77 - "Community 77"
Cohesion: 0.10
Nodes (32): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0051 (+24 more)

### Community 78 - "Community 78"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 79 - "Community 79"
Cohesion: 0.08
Nodes (36): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+28 more)

### Community 80 - "Community 80"
Cohesion: 0.13
Nodes (8): FakeCache, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore, UserScopedStore

### Community 81 - "Community 81"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 82 - "Community 82"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 83 - "Community 83"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 84 - "Community 84"
Cohesion: 0.10
Nodes (12): drawBand(), ProfileSketch(), AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint, SketchProfile (+4 more)

### Community 85 - "Community 85"
Cohesion: 0.10
Nodes (19): ApiDivesGet200ResponseCounts, DiveRepository, neighboursPath(), samplesPath(), countOf(), FilterChip, filterChips(), LogbookFilter (+11 more)

### Community 86 - "Community 86"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 87 - "Community 87"
Cohesion: 0.15
Nodes (32): acceptedFormat(), Answer, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId(), Format (+24 more)

### Community 88 - "Community 88"
Cohesion: 0.18
Nodes (3): SyncCheckpoint, SyncState, DiveStoreTest

### Community 89 - "Community 89"
Cohesion: 0.12
Nodes (27): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+19 more)

### Community 90 - "Community 90"
Cohesion: 0.14
Nodes (27): getDiverLoadings, HINT, Input, LoadingsInput, planGasAndBottomTime, ADR-0032, ADR-0035, Used() (+19 more)

### Community 91 - "Community 91"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 92 - "Community 92"
Cohesion: 0.08
Nodes (21): Tx, AppendUndo, ADR-0020, ADR-0056, ADR-0066, ChangeListener, ADR-0015, ADR-0016 (+13 more)

### Community 93 - "Community 93"
Cohesion: 0.10
Nodes (27): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+19 more)

### Community 94 - "Community 94"
Cohesion: 0.11
Nodes (22): SsiClient, buildCreateRecord(), buildDeleteRecord(), emptyRecord(), writableRecord(), ClearChecks, clearDive(), findIn() (+14 more)

### Community 95 - "Community 95"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 96 - "Community 96"
Cohesion: 0.11
Nodes (12): diveLocalTime(), offsetId(), timeZoneNote(), TimeZoneOrigin, DEVICE, NEARBY, POSITION, UNKNOWN (+4 more)

### Community 97 - "Community 97"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 98 - "Community 98"
Cohesion: 0.09
Nodes (23): computerEventOf(), ADR-0036, computerEventFromFit(), COMPUTER_EVENT, computerEventFromSuunto(), DECO_MODEL, decoModelFromSuunto, DIVE_MODE (+15 more)

### Community 99 - "Community 99"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 100 - "Community 100"
Cohesion: 0.11
Nodes (22): SsiRecord, contextOf(), FakeSsi, access(), client, out, pause, save() (+14 more)

### Community 101 - "Community 101"
Cohesion: 0.10
Nodes (22): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearQueue(), conflictAtSsi() (+14 more)

### Community 102 - "Community 102"
Cohesion: 0.19
Nodes (4): GasPlanScreenshotTest, GasPlanToolDiver, GasPlanToolInput, GasPlanToolLevel

### Community 103 - "Community 103"
Cohesion: 0.11
Nodes (7): ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport

### Community 104 - "Community 104"
Cohesion: 0.10
Nodes (22): BackupRouteDeps, BackupService, backupTask(), ADR-0069, PurgeService, PROCESS_IMPORT_TASK, SCHEDULED_FETCH_TASK, PUSH_RUN_STEP_TASK (+14 more)

### Community 105 - "Community 105"
Cohesion: 0.14
Nodes (24): surfacePressureBar(), ndlMinutes(), ADR-0032, ADR-0046, evaluateTissues(), advanceTissues(), schreinerStep(), surfaceSaturatedTissues() (+16 more)

### Community 106 - "Community 106"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 107 - "Community 107"
Cohesion: 0.28
Nodes (7): leadAnswer(), EstimateCylinder, PlannedConditions, Suit, currentLoadings(), leadEstimate(), PersonalizedToolsTest

### Community 108 - "Community 108"
Cohesion: 0.15
Nodes (20): powInt(), Sac, SacCylinder, SacDive, SacMissing, cylinder_incomplete, no_average_depth, too_short (+12 more)

### Community 109 - "Community 109"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 110 - "Community 110"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 111 - "Community 111"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 112 - "Community 112"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 113 - "Community 113"
Cohesion: 0.13
Nodes (21): date(), FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position(), positive() (+13 more)

### Community 114 - "Community 114"
Cohesion: 0.11
Nodes (14): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+6 more)

### Community 115 - "Community 115"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 116 - "Community 116"
Cohesion: 0.09
Nodes (23): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+15 more)

### Community 117 - "Community 117"
Cohesion: 0.10
Nodes (19): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+11 more)

### Community 118 - "Community 118"
Cohesion: 0.14
Nodes (18): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+10 more)

### Community 119 - "Community 119"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 120 - "Community 120"
Cohesion: 0.14
Nodes (19): Deep, DepthReading, depthReading(), deviceName(), DiveValue, AVG_DEPTH_M, DURATION_SECONDS, MAX_DEPTH_M (+11 more)

### Community 121 - "Community 121"
Cohesion: 0.14
Nodes (17): BottomTime, bottomTime(), Limit, ADR-0032, ADR-0033, ADR-0071, MAX_DEPTH_M, NOAA_ROWS (+9 more)

### Community 122 - "Community 122"
Cohesion: 0.23
Nodes (20): DiveOrigin, REFERENCE_PREFIX, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef(), MATERIAL_OF_TANK_TYPE (+12 more)

### Community 123 - "Community 123"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 124 - "Community 124"
Cohesion: 0.15
Nodes (5): ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits

### Community 125 - "Community 125"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 126 - "Community 126"
Cohesion: 0.15
Nodes (5): SiteViewExternalIdsItem, SiteViewExternalIdsItemAttribution, SiteViewExternalIdsItemOffered, SiteViewExternalIdsItemOfferedPosition, SiteViewExternalIdsItemPrivateTo

### Community 127 - "Community 127"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 128 - "Community 128"
Cohesion: 0.14
Nodes (16): AssessmentError, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), requireManagedDive(), isRule() (+8 more)

### Community 129 - "Community 129"
Cohesion: 0.12
Nodes (18): EMPTY_SITE_IMPORT_COUNTS, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, siteImportStatus, SiteImportPlan (+10 more)

### Community 130 - "Community 130"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 131 - "Community 131"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 132 - "Community 132"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 133 - "Community 133"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 134 - "Community 134"
Cohesion: 0.19
Nodes (11): LeadInputTest, SuitType, drysuit, none, semidry, skin, wetsuit, SuitUndergarment (+3 more)

### Community 135 - "Community 135"
Cohesion: 0.13
Nodes (16): AL80, asEstimateInput(), EstimateDiveCylinder, evaluateLeadVectorCase(), LEAD_CASES, leadInput(), leadVectorFile(), LeadVectorInput (+8 more)

### Community 136 - "Community 136"
Cohesion: 0.18
Nodes (15): RecordingSummary, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput, round3() (+7 more)

### Community 138 - "Community 138"
Cohesion: 0.20
Nodes (3): FakeInstances, FakeTokens, SyncAuthTest

### Community 139 - "Community 139"
Cohesion: 0.13
Nodes (15): CYLINDER_MATERIALS, SUIT_TYPES, SUIT_UNDERGARMENTS, replyDiverError, STATUS, LeadService, Amount, EstimateQuery (+7 more)

### Community 140 - "Community 140"
Cohesion: 0.15
Nodes (14): CIRCUIT, circuitFromFit, DECO_MODEL, decoModelFromFit, DIVE_ALERT, DIVE_MODE, diveModeFromFit, ADR-0015 (+6 more)

### Community 141 - "Community 141"
Cohesion: 0.17
Nodes (13): BOM, Json, num(), obj(), positive(), scaled(), suuntoFitKey(), time() (+5 more)

### Community 142 - "Community 142"
Cohesion: 0.17
Nodes (12): sampleCount(), storedList(), tryParseJson(), APP_PARAMS, createSsiTransport(), exchange(), isObject(), SSI_APP_USER_AGENT (+4 more)

### Community 143 - "Community 143"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 144 - "Community 144"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 145 - "Community 145"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 146 - "Community 146"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 148 - "Community 148"
Cohesion: 0.14
Nodes (7): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, headers, uniqueWord()

### Community 149 - "Community 149"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 150 - "Community 150"
Cohesion: 0.18
Nodes (11): addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, relations(), TimelineBar, TimelineBarGeometry, TimelineGapGeometry, TimelineGeometry (+3 more)

### Community 151 - "Community 151"
Cohesion: 0.24
Nodes (13): cnsDecayed(), Estimate, LoadingBasis, N2Loadings, N2UnavailableReason, no_profile, no_recent_dive, stale (+5 more)

### Community 152 - "Community 152"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 153 - "Community 153"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 154 - "Community 154"
Cohesion: 0.15
Nodes (12): account, accountRelations, apikey, rateLimit, session, sessionRelations, ADR-0011, ADR-0012 (+4 more)

### Community 155 - "Community 155"
Cohesion: 0.27
Nodes (12): asText(), compareReadBack(), datasetLength(), describe(), isObject(), keyDifferences(), pause(), recording() (+4 more)

### Community 156 - "Community 156"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 157 - "Community 157"
Cohesion: 0.26
Nodes (3): ApiDiveSitesGet200Response, SiteView, SiteViewPosition

### Community 158 - "Community 158"
Cohesion: 0.26
Nodes (3): DiverView, DiverViewCertifications, DiverViewCertificationsNewest

### Community 159 - "Community 159"
Cohesion: 0.26
Nodes (3): DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewCylindersItemSeries

### Community 160 - "Community 160"
Cohesion: 0.26
Nodes (3): DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 161 - "Community 161"
Cohesion: 0.18
Nodes (9): AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031, WETSUIT_5 (+1 more)

### Community 162 - "Community 162"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 163 - "Community 163"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 164 - "Community 164"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 165 - "Community 165"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 166 - "Community 166"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 167 - "Community 167"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 168 - "Community 168"
Cohesion: 0.20
Nodes (5): FileFormat, FitAdapter, SuuntoJsonAdapter, UddfAdapter, ParsedRecording

### Community 169 - "Community 169"
Cohesion: 0.20
Nodes (9): LeadEstimateAnswer, Confidence, EstimateAdjustment, EstimateBasis, EstimateInput, EstimateMissing, LeadEstimate, ADR-0025 (+1 more)

### Community 170 - "Community 170"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 171 - "Community 171"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 172 - "Community 172"
Cohesion: 0.33
Nodes (3): DiveViewRecordingsItemSummary, DiveViewRecordingsItemSummaryGasesItem, summaryOf()

### Community 173 - "Community 173"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 174 - "Community 174"
Cohesion: 0.28
Nodes (7): ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, s(), ADR-0035

### Community 176 - "Community 176"
Cohesion: 0.25
Nodes (8): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, MODULE_IMPORTS, moduleRules, target()

### Community 177 - "Community 177"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 178 - "Community 178"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 180 - "Community 180"
Cohesion: 0.29
Nodes (5): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, EstimateCylinder

### Community 181 - "Community 181"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 182 - "Community 182"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 183 - "Community 183"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 184 - "Community 184"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 186 - "Community 186"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 187 - "Community 187"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 188 - "Community 188"
Cohesion: 0.40
Nodes (4): TAGS, ADR-0023, { stdout, stderr }, total

### Community 192 - "Community 192"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 193 - "Community 193"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 194 - "Community 194"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1267 isolated node(s):** `Off`, `Reserve`, `RockBottom`, `SuitThickness`, `ADR-0013` (+1262 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3392 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **109 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 0` to `Community 2`, `Community 4`, `Community 6`, `Community 7`, `Community 8`, `Community 10`, `Community 11`, `Community 12`, `Community 13`, `Community 14`, `Community 15`, `Community 21`, `Community 22`, `Community 23`, `Community 25`, `Community 154`, `Community 27`, `Community 28`, `Community 31`, `Community 37`, `Community 39`, `Community 44`, `Community 45`, `Community 57`, `Community 65`, `Community 97`, `Community 116`?**
  _High betweenness centrality (0.024) - this node is a cross-community bridge._
- **What connects `Off`, `Reserve`, `RockBottom` to the rest of the system?**
  _1267 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.021061316657981586 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 9` to `Community 1`, `Community 66`, `Community 3`, `Community 5`, `Community 40`, `Community 74`, `Community 42`, `Community 43`, `Community 78`, `Community 16`, `Community 17`, `Community 20`, `Community 26`, `Community 95`, `Community 127`?**
  _High betweenness centrality (0.020) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.04159993593850096 - nodes in this community are weakly interconnected._
- **Why does `@playwright/test` connect `Community 56` to `Community 163`, `Community 164`, `Community 69`, `Community 101`, `Community 77`, `Community 110`, `Community 145`, `Community 148`, `Community 54`, `Community 156`, `Community 62`, `Community 95`?**
  _High betweenness centrality (0.011) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.02512562814070352 - nodes in this community are weakly interconnected._