# Graph Report - dive_hub  (2026-11-19)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 9868 nodes · 29952 edges · 320 communities (202 shown, 118 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 526 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `e5ba0bd8`
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
- Community 214
- Community 216

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
- `Placement` --references--> `Actor`  [EXTRACTED]
  apps/server/src/imports/placement.ts → apps/server/src/revisions/revisions.ts
- `StoreLogbookTest` --calls--> `StoreLogbook`  [INFERRED]
  apps/android/shared/src/jvmTest/kotlin/dev/divehub/shared/logbook/StoreLogbookTest.kt → apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/logbook/StoreLogbook.kt

## Import Cycles
- None detected.

## Communities (320 total, 118 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (229): cylinderCatalogueQuery(), CylinderFields(), MATERIALS, ADR-0034, ADR-0045, useCatalogueName(), useCylinderProblem(), DiveEditForm() (+221 more)

### Community 1 - "Community 1"
Cohesion: 0.03
Nodes (154): user, diveSiteExternalId, lead, createSsiClient(), ADR-0013, ADR-0011, dumpToolAvailable(), execFileAsync (+146 more)

### Community 2 - "Community 2"
Cohesion: 0.04
Nodes (196): DisplaySettings(), DeleteUserDialog(), Invite(), LinkToPassOn(), SiteDataPanel(), UserRow(), Accesses(), AiAccessSetting() (+188 more)

### Community 3 - "Community 3"
Cohesion: 0.02
Nodes (175): ConnectionRow, diveFingerprint, providerGoneDive, providerLease, WaitingPushOptions, waitingOf(), open(), close() (+167 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (174): EMPTY_SITE_IMPORT_COUNTS, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, diverExternalId (+166 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (133): equipmentItem, OVERRIDABLE_FIELDS, OverridableField, participant, RecordingSummary, renumber(), renumberStatement(), ADR-0048 (+125 more)

### Community 6 - "Community 6"
Cohesion: 0.02
Nodes (118): buddySuggestion, diveFinding, recordingEvent, parseHint(), Assessment, FindingView, Row, ADR-0036 (+110 more)

### Community 7 - "Community 7"
Cohesion: 0.02
Nodes (119): LEAD_PLACEMENTS, UTC_OFFSET_SOURCES, WEIGHTING_FELT, downsampleMinMax(), CertificationHint, CertificationHintExpiry, DiveCertifications, ADR-0050 (+111 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (115): BACKUP_EXTENSION, BACKUP_FORMAT_VERSION, BACKUP_SKIPPED_DIRS, BackupArchiveError, BackupDecryptionKey, BackupEncryption, BackupEncryptionMetadata, BackupPart (+107 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (117): label(), Me, AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery() (+109 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (113): diveAssessment, jointDive, logbookCheckAnswer, logbookCheckDiveAnswer, keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration() (+105 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (98): aiAccessSettingQuery(), BackupRunView, backupSettingsQuery(), BackupSettingsView, backupsQuery(), SiteDataView, ADR-0030, CertificationView (+90 more)

### Community 12 - "Community 12"
Cohesion: 0.02
Nodes (104): actorType, aiAccessOutcome, backupRunKind, backupRunStatus, candidateResolution, centreSource, CERTIFICATION_IMAGE_STATES, CERTIFICATION_VERIFICATIONS (+96 more)

### Community 13 - "Community 13"
Cohesion: 0.04
Nodes (104): ComputerChoice, CylinderMaterial, DiveImportMode, DiveKind, importOriginal, providerSiteData, UtcOffsetSource, ProviderCylinder (+96 more)

### Community 14 - "Community 14"
Cohesion: 0.04
Nodes (80): createServer(), createAuth(), auth, loadAuthSecret(), acquireAppLock(), createServices(), ServiceParts, Services (+72 more)

### Community 15 - "Community 15"
Cohesion: 0.05
Nodes (94): devicesQuery(), diversQuery(), centreQuery(), centresQuery(), CentreView, ExternalIdView, Position, SiteImportArea (+86 more)

### Community 16 - "Community 16"
Cohesion: 0.03
Nodes (85): CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite(), lock() (+77 more)

### Community 17 - "Community 17"
Cohesion: 0.04
Nodes (70): buildManifest(), encryptFile(), openArchive(), packContainer(), createDb(), findChangedMigrations(), migrateDatabase(), ADR-0004 (+62 more)

### Community 18 - "Community 18"
Cohesion: 0.03
Nodes (90): pushRunItem, PushRunLeftOut, managedDiverIds(), DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN (+82 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (71): equipmentDueQuery(), CodeFigure(), DiveCodes(), showDiveCodes(), ADR-0043, EquipmentDueNotice(), ADR-0034, avatarTone() (+63 more)

### Community 20 - "Community 20"
Cohesion: 0.04
Nodes (83): importsQuery(), isUnauthorized(), running(), siteImportsQuery(), App(), BackupsPage, CentrePage, CentresPage (+75 more)

### Community 21 - "Community 21"
Cohesion: 0.03
Nodes (71): diveLease, providerConflict, push, pushRun, revision, waitingPush, kept(), PURGE_AFTER_DAYS (+63 more)

### Community 22 - "Community 22"
Cohesion: 0.04
Nodes (48): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+40 more)

### Community 23 - "Community 23"
Cohesion: 0.05
Nodes (85): allButDive(), continuationCandidateQuery(), deletedDivesOn(), deletedDivesQuery(), diveQuery(), DiveView, mergeCandidatesQuery(), nearbyDivesQuery() (+77 more)

### Community 24 - "Community 24"
Cohesion: 0.03
Nodes (67): connection, createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities() (+59 more)

### Community 25 - "Community 25"
Cohesion: 0.04
Nodes (76): adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, AuthOptions, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, ADR-0011, ADR-0012 (+68 more)

### Community 26 - "Community 26"
Cohesion: 0.04
Nodes (74): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError (+66 more)

### Community 27 - "Community 27"
Cohesion: 0.04
Nodes (16): MainViewModel, UiState, LoadingsUiState, LoadingsViewModel, ApiMeGet200Response, ApiMeGet200ResponsePreferences, SignInEmailRequest, UserView (+8 more)

### Community 28 - "Community 28"
Cohesion: 0.05
Nodes (74): ChangePassword(), SecuritySettings(), Sessions(), useDescribeAgent(), AdminSettings(), Invitations(), ProviderSiteData(), ADR-0012 (+66 more)

### Community 29 - "Community 29"
Cohesion: 0.07
Nodes (76): buildApp(), assessmentRoutes(), AI_ACCESS_NAME_MAX, SessionUser, authHandler(), authHeaders(), fastify, FastifyRequest (+68 more)

### Community 30 - "Community 30"
Cohesion: 0.04
Nodes (21): ApiDiveSitesGet200Response, ApiMeGet200ResponseOwnDiver, DiveViewParticipantsItemCertificationHint, ProviderSummary, ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications (+13 more)

### Community 31 - "Community 31"
Cohesion: 0.05
Nodes (73): CertificationService, AssessmentRefresh, ADR-0036, ADR-0053, ErrorAnswer, PROBLEM_CODES, providerProblem(), AccountRouteDeps (+65 more)

### Community 32 - "Community 32"
Cohesion: 0.04
Nodes (73): BuddyView, ConflictChoices, ConflictEntryView, ConflictFieldView, ConflictsOfConnection, GoneDivesOfConnection, GoneDiveView, LeftOutView (+65 more)

### Community 33 - "Community 33"
Cohesion: 0.05
Nodes (6): syncDateTime(), SyncLine(), dateTime(), segmentName(), LoadingSource, ErrorCodes

### Community 34 - "Community 34"
Cohesion: 0.06
Nodes (67): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+59 more)

### Community 35 - "Community 35"
Cohesion: 0.04
Nodes (52): ParticipantRole, PushLeftOut, ActionContext, Capabilities, Direction, DiveExport, DiveListing, FIND_BY (+44 more)

### Community 36 - "Community 36"
Cohesion: 0.05
Nodes (27): LogbookViewModel, ApiDivesGet200Response, ApiDivesGet200ResponseCounts, DiveRepository, neighboursPath(), countOf(), FilterChip, filterChips() (+19 more)

### Community 37 - "Community 37"
Cohesion: 0.05
Nodes (54): SsiClient, SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), DiveForSsi, emptyRecord(), writableRecord() (+46 more)

### Community 38 - "Community 38"
Cohesion: 0.05
Nodes (9): AndroidDiveStore, DiveStore, toBoolean(), toLong(), toModel(), BodyWeight, DiveSite, Recording (+1 more)

### Community 39 - "Community 39"
Cohesion: 0.05
Nodes (58): divesQuery(), LogbookPage, PAGE_SIZE, Buddies(), COUNT_OF, DiveList(), DiveRow(), MonthHead() (+50 more)

### Community 40 - "Community 40"
Cohesion: 0.05
Nodes (51): AssessmentError, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), requireManagedDive() (+43 more)

### Community 41 - "Community 41"
Cohesion: 0.05
Nodes (8): AppContainer, KeystoreTokenStore, PreferencesInstanceUrlStore, UserCacheStore, InstanceUrlStore, TokenStore, UserScopedStore, SyncRefresher

### Community 42 - "Community 42"
Cohesion: 0.05
Nodes (55): DIVE_IMPORT_MODES, DIVE_KINDS, Change(), Changes(), DateTime, historyRoute, Made(), ImportView (+47 more)

### Community 43 - "Community 43"
Cohesion: 0.06
Nodes (53): Delivered, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open() (+45 more)

### Community 44 - "Community 44"
Cohesion: 0.07
Nodes (58): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+50 more)

### Community 45 - "Community 45"
Cohesion: 0.07
Nodes (60): Answer, answer(), baseFrom(), BodyWeight, Confidence, high, low, medium (+52 more)

### Community 46 - "Community 46"
Cohesion: 0.07
Nodes (51): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+43 more)

### Community 47 - "Community 47"
Cohesion: 0.11
Nodes (51): GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult(), PlanInputs(), PlanRuleExplanation() (+43 more)

### Community 48 - "Community 48"
Cohesion: 0.05
Nodes (51): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, cancelWaitingImports() (+43 more)

### Community 49 - "Community 49"
Cohesion: 0.06
Nodes (50): DiveValues, OverridableField, revisionsQuery(), RevisionView, UtcOffsetSource, CylinderList(), useCylinderText(), DiveHistory() (+42 more)

### Community 50 - "Community 50"
Cohesion: 0.09
Nodes (9): DiveDetailScreenshotTest, DiveViewFromRecording, DiveViewFromRecordingStartsAt, DiveViewRecordingsItem, DiveViewRecordingsItemDevice, DiveViewWaterMismatch, NeighboursView, NeighboursViewNext (+1 more)

### Community 51 - "Community 51"
Cohesion: 0.05
Nodes (52): BottomTimeInput, Conditions, GasMix, GasPlanInput, GasRule, LoadingDive, TissuesAfterDivesInput, NdlInput (+44 more)

### Community 52 - "Community 52"
Cohesion: 0.09
Nodes (35): DiveBody(), DiveDetailRoute(), DiveDetailScreen(), errorText(), Loading(), NeighbourButton(), Neighbours(), Notice() (+27 more)

### Community 53 - "Community 53"
Cohesion: 0.09
Nodes (13): LogbookScreenshotTest, ApiDivesGet200ResponseDivesItem, ApiDivesGet200ResponseDivesItemGasesItem, ApiDivesGet200ResponseDivesItemParticipantsItem, ApiDivesGet200ResponseDivesItemSite, ApiDivesGet200ResponseMonthsItem, DiveProfileView, localMonthOf() (+5 more)

### Community 54 - "Community 54"
Cohesion: 0.10
Nodes (42): BottomTime, BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen, BottomTimeLimits (+34 more)

### Community 55 - "Community 55"
Cohesion: 0.08
Nodes (11): SyncCheckpoint, Complete, SessionEnded, Stopped, SyncEngine, SyncPhase, Details, Meta (+3 more)

### Community 56 - "Community 56"
Cohesion: 0.09
Nodes (10): Cylinder, Dive, Diver, toStore(), divePage(), diverJson(), manySites(), pathWithQuery() (+2 more)

### Community 57 - "Community 57"
Cohesion: 0.06
Nodes (14): DiveView, DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewCylindersItemSeries, DiveViewLeadItem, DiveViewParticipantsItem, DiveViewPosition, DiveViewSac (+6 more)

### Community 58 - "Community 58"
Cohesion: 0.07
Nodes (41): ImportArea, createSsiSiteSource(), DEFAULT_SSI_SITES_URL, createSiteSources(), ADR-0021, ADR-0027, createOverpassSource(), DEFAULT_OVERPASS_URL (+33 more)

### Community 59 - "Community 59"
Cohesion: 0.07
Nodes (47): Leave, Series, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+39 more)

### Community 60 - "Community 60"
Cohesion: 0.09
Nodes (46): accountListEntry, diver, tidyName(), DiverDetails, Caller, foldName(), nameDiffers(), Buddy (+38 more)

### Community 61 - "Community 61"
Cohesion: 0.07
Nodes (47): AiAccessScope, diversOf(), SiteRow, Cursor, cursorAt(), DateOnly(), defineTool(), Detail (+39 more)

### Community 62 - "Community 62"
Cohesion: 0.07
Nodes (47): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+39 more)

### Community 63 - "Community 63"
Cohesion: 0.06
Nodes (34): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+26 more)

### Community 64 - "Community 64"
Cohesion: 0.12
Nodes (24): Body, Hold, Offline, RefreshData, refreshDeletedJson(), refreshDetailJson(), refreshDiveListItem(), refreshDivePage() (+16 more)

### Community 65 - "Community 65"
Cohesion: 0.07
Nodes (45): serviceRecord, serviceRecordSchedule, serviceSchedule, addMonths(), CountDue, countReason(), dimensionState(), divesLeft() (+37 more)

### Community 66 - "Community 66"
Cohesion: 0.06
Nodes (42): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+34 more)

### Community 67 - "Community 67"
Cohesion: 0.06
Nodes (38): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+30 more)

### Community 68 - "Community 68"
Cohesion: 0.08
Nodes (38): BottomTimeQuery, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033, ADR-0071, DEFAULT_PPO2 (+30 more)

### Community 69 - "Community 69"
Cohesion: 0.08
Nodes (43): surfacePressureBar(), AIR, DEFAULT_CONDITIONS, N2_REPLAY_WINDOW_HOURS, N2_STALE_AFTER_HOURS, N2Loadings, N2LoadingsBase, N2UnavailableReason (+35 more)

### Community 70 - "Community 70"
Cohesion: 0.06
Nodes (44): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, DiveProfileView, DiveProfileViewSchema, ADR-0041 (+36 more)

### Community 71 - "Community 71"
Cohesion: 0.09
Nodes (31): cylinder, dive, duplicateCandidate, importJob, original, recording, ImportService, createLocalBlobStore() (+23 more)

### Community 72 - "Community 72"
Cohesion: 0.06
Nodes (6): MainActivity, DiveHubTheme(), Answers, number(), ToolsViewModel, LauncherIconTest

### Community 73 - "Community 73"
Cohesion: 0.05
Nodes (42): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+34 more)

### Community 74 - "Community 74"
Cohesion: 0.06
Nodes (38): AssessmentService, AssessmentView, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView() (+30 more)

### Community 75 - "Community 75"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 76 - "Community 76"
Cohesion: 0.10
Nodes (16): LeadCylinderInput, LeadEstimateUiState, LeadEstimateViewModel, LeadInputs, LeadInputTest, SuitType, drysuit, none (+8 more)

### Community 77 - "Community 77"
Cohesion: 0.12
Nodes (8): FakeCache, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore, UserScopedStore

### Community 78 - "Community 78"
Cohesion: 0.09
Nodes (41): DiveGearItemView, diveGearQuery(), DiveGearView, EquipmentCategory, EquipmentDetailView, EquipmentDueView, equipmentItemQuery(), EquipmentItemView (+33 more)

### Community 80 - "Community 80"
Cohesion: 0.08
Nodes (35): ImportErrorCode, ImportFound, errorMessage(), looksLikeZip(), formatOf(), CHOICE_WAIT_DAYS, createImportService(), cancelImport() (+27 more)

### Community 81 - "Community 81"
Cohesion: 0.10
Nodes (8): PersonalizedScreenshotTest, ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseCns, ApiDiversIdLoadingsGet200ResponseN2, ApiDiversIdLoadingsGet200ResponseNoFly, ApiDiversIdLoadingsGet200ResponseNoFlySource, ApiDiversIdLoadingsGet200ResponseOtu

### Community 82 - "Community 82"
Cohesion: 0.06
Nodes (38): CYLINDER_MATERIALS, SUIT_TYPES, SUIT_UNDERGARMENTS, ADR-0025, WaterMismatch, waterMismatch(), LeadService, Amount (+30 more)

### Community 83 - "Community 83"
Cohesion: 0.08
Nodes (38): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+30 more)

### Community 84 - "Community 84"
Cohesion: 0.07
Nodes (36): @modelcontextprotocol/server@^2.3.1, aiAccessLog, aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, AiAccessService, KeyRow (+28 more)

### Community 85 - "Community 85"
Cohesion: 0.10
Nodes (32): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0051 (+24 more)

### Community 86 - "Community 86"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 87 - "Community 87"
Cohesion: 0.07
Nodes (32): sampleCount(), rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject(), MAX_CARD_IMAGE_BYTES (+24 more)

### Community 88 - "Community 88"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 89 - "Community 89"
Cohesion: 0.10
Nodes (23): FactRow(), Facts(), Water(), DiveDetailText, Deep, DepthReading, depthReading(), deviceName() (+15 more)

### Community 90 - "Community 90"
Cohesion: 0.12
Nodes (9): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), GasPlanInputTest (+1 more)

### Community 91 - "Community 91"
Cohesion: 0.07
Nodes (29): WeightingFelt, right, too_heavy, too_light, dayNumber(), enteredDecoFromNdl(), minNdlSeconds(), NoFly (+21 more)

### Community 92 - "Community 92"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 93 - "Community 93"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 94 - "Community 94"
Cohesion: 0.09
Nodes (12): drawBand(), ProfileSketch(), AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint, SketchProfile (+4 more)

### Community 95 - "Community 95"
Cohesion: 0.07
Nodes (33): Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), HINT, Input, LoadingsInput (+25 more)

### Community 96 - "Community 96"
Cohesion: 0.12
Nodes (35): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+27 more)

### Community 97 - "Community 97"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 98 - "Community 98"
Cohesion: 0.12
Nodes (20): AvatarCircle(), DiveRow(), Error(), Fact(), Filters(), Loading(), LogbookList(), LogbookRoute() (+12 more)

### Community 99 - "Community 99"
Cohesion: 0.06
Nodes (30): account, accountRelations, apikey, rateLimit, session, sessionRelations, ADR-0011, ADR-0012 (+22 more)

### Community 100 - "Community 100"
Cohesion: 0.08
Nodes (31): PARTICIPANT_ROLES, DIVE_ONLY_PATTERN, storedProblemCode(), oneOf(), DiveProviderStatus, DiveProviderStatusSchema, LeftOut, PushView (+23 more)

### Community 101 - "Community 101"
Cohesion: 0.07
Nodes (28): CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053, replyDiverError, STATUS, DiverService (+20 more)

### Community 102 - "Community 102"
Cohesion: 0.09
Nodes (33): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+25 more)

### Community 103 - "Community 103"
Cohesion: 0.08
Nodes (34): seriesOf(), minute(), minutes(), round(), startTimes(), Channel, CHANNEL_UNITS, CHANNELS (+26 more)

### Community 104 - "Community 104"
Cohesion: 0.10
Nodes (6): ApiDivesGet200ResponseTotals, DiveViewRecordingsItemSummary, DiveViewRecordingsItemSummaryGasesItem, StoreLogbook, StoreLogbookContext, summaryOf()

### Community 105 - "Community 105"
Cohesion: 0.10
Nodes (13): diveLocalTime(), offsetId(), timeZoneNote(), TimeZoneOrigin, DEVICE, NEARBY, POSITION, UNKNOWN (+5 more)

### Community 106 - "Community 106"
Cohesion: 0.19
Nodes (13): Body, diveListItem(), Harness, json(), manyDives(), Offline, query(), Reply (+5 more)

### Community 107 - "Community 107"
Cohesion: 0.09
Nodes (23): createFileFormats(), FileFormat, FitAdapter, looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), SuuntoJsonAdapter, ADR-0037 (+15 more)

### Community 108 - "Community 108"
Cohesion: 0.12
Nodes (27): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+19 more)

### Community 109 - "Community 109"
Cohesion: 0.14
Nodes (24): Adjustment(), amount(), Answer(), Conditions(), detail(), hoodName(), levelName(), materialName() (+16 more)

### Community 111 - "Community 111"
Cohesion: 0.09
Nodes (26): AL80, asEstimateInput(), EstimateDiveCylinder, evaluateLeadVectorCase(), LEAD_CASES, LEAD_VECTOR_KERNEL, leadInput(), leadVectorFile() (+18 more)

### Community 112 - "Community 112"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 113 - "Community 113"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 114 - "Community 114"
Cohesion: 0.11
Nodes (5): ApiClient, ApiErrorBody, json(), LogbookRepositoryTest, RepositoryHarness

### Community 115 - "Community 115"
Cohesion: 0.09
Nodes (26): ENGINE_VERSION, EVIDENCE, Evidence, FindingValues, NOT_COVERED_DIVE_MODES, RULE_IDS, RuleId, SEVERITIES (+18 more)

### Community 116 - "Community 116"
Cohesion: 0.09
Nodes (24): BackupRouteDeps, BACKUP_RUN_TASK, BackupService, backupTask(), ADR-0069, PurgeService, EXPIRE_WAITING_IMPORTS_TASK, PROCESS_IMPORT_TASK (+16 more)

### Community 117 - "Community 117"
Cohesion: 0.09
Nodes (24): computerEventOf(), ADR-0036, computerEventFromFit(), COMPUTER_EVENT, computerEventFromSuunto(), DECO_MODEL, decoModelFromSuunto, DIVE_MODE (+16 more)

### Community 118 - "Community 118"
Cohesion: 0.10
Nodes (25): date(), FitMessage, FitMessages, GARMIN_PRODUCTS, num(), position(), positive(), RECORD_CHANNELS (+17 more)

### Community 119 - "Community 119"
Cohesion: 0.07
Nodes (13): HINT_CHANNEL, HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect(), lost() (+5 more)

### Community 120 - "Community 120"
Cohesion: 0.21
Nodes (6): bottomTime(), PlanningSacResult, BottomTimeToolInput, GasNumbersInput, ToolDiverSource, ToolsServiceTest

### Community 121 - "Community 121"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 122 - "Community 122"
Cohesion: 0.10
Nodes (20): clientKey(), limitAttempts(), ADR-0061, ProblemCode, ResponseState, whenResponseGone(), StoredBlob, PlacedArchive (+12 more)

### Community 123 - "Community 123"
Cohesion: 0.09
Nodes (23): createFitAdapter(), Check, Deleted, DiveView, Outcome, RecordingView, ADR-0063, Candidate (+15 more)

### Community 124 - "Community 124"
Cohesion: 0.10
Nodes (22): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearQueue(), conflictAtSsi() (+14 more)

### Community 125 - "Community 125"
Cohesion: 0.11
Nodes (7): ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh, ApiToolsBottomTimeGet200ResponseUsedSacLpm, ApiToolsBottomTimeGet200ResponseUsedWater

### Community 126 - "Community 126"
Cohesion: 0.13
Nodes (25): NoFly, getDiverLoadings, DEFAULT_GF_HIGH, residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows(), DiveRow (+17 more)

### Community 127 - "Community 127"
Cohesion: 0.09
Nodes (22): DELETE_QUIET_MS, Cylinder, DiveView, RevisionView, ADR-0045, upload(), Candidate, Deleted (+14 more)

### Community 128 - "Community 128"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 129 - "Community 129"
Cohesion: 0.31
Nodes (7): leadAnswer(), EstimateCylinder, PlannedConditions, Suit, assemblePersonalizedSources(), leadEstimate(), PersonalizedToolsTest

### Community 130 - "Community 130"
Cohesion: 0.16
Nodes (24): DiveOrigin, REFERENCE_PREFIX, SsiLogbook, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef() (+16 more)

### Community 131 - "Community 131"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 132 - "Community 132"
Cohesion: 0.16
Nodes (17): cnsDecayed(), Estimate, isoUtc(), LoadingBasis, LoadingDive, N2Loadings, N2UnavailableReason, no_profile (+9 more)

### Community 133 - "Community 133"
Cohesion: 0.12
Nodes (21): APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), AppLockProbe, probeAppLock() (+13 more)

### Community 134 - "Community 134"
Cohesion: 0.11
Nodes (21): parseWithFitFileParser(), BOM, Json, num(), obj(), positive(), scaled(), suuntoFitKey() (+13 more)

### Community 135 - "Community 135"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 136 - "Community 136"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 137 - "Community 137"
Cohesion: 0.09
Nodes (4): DiveHubApplication, SyncWorker, WorkManagerPeriodicSync, PeriodicSyncScheduler

### Community 138 - "Community 138"
Cohesion: 0.12
Nodes (7): LeadAnswer, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseLeftOut, ApiDiversIdLeadEstimateGet200ResponseRange, ApiDiversIdLeadEstimateGet200ResponseReserve, ApiDiversIdLeadEstimateGet200ResponseWeightCheck

### Community 139 - "Community 139"
Cohesion: 0.15
Nodes (22): planningSac(), PlanningSacDive, PlanningSacSource, default, logbook, jsRound(), powInt(), roundTo() (+14 more)

### Community 140 - "Community 140"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 141 - "Community 141"
Cohesion: 0.24
Nodes (4): GasPlanScreenshotTest, GasPlanToolDiver, GasPlanToolInput, GasPlanToolLevel

### Community 142 - "Community 142"
Cohesion: 0.13
Nodes (6): ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting

### Community 143 - "Community 143"
Cohesion: 0.13
Nodes (6): DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, WaitingPushView

### Community 144 - "Community 144"
Cohesion: 0.11
Nodes (21): CENTRE_SOURCE_NAME, CENTRE_SOURCES, CentreService, CentreView, CentreViewSchema, Row, Source, toCentreView() (+13 more)

### Community 145 - "Community 145"
Cohesion: 0.09
Nodes (16): SourceSite, call(), createSite(), Method, Stream, ADR-0066, EGYPT, ExternalId (+8 more)

### Community 146 - "Community 146"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 147 - "Community 147"
Cohesion: 0.17
Nodes (3): DiveDetailUiState, DiveDetailViewModel, RecordingSamplesView

### Community 148 - "Community 148"
Cohesion: 0.09
Nodes (23): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+15 more)

### Community 149 - "Community 149"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 150 - "Community 150"
Cohesion: 0.15
Nodes (18): qr@0.7.2, cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043 (+10 more)

### Community 151 - "Community 151"
Cohesion: 0.16
Nodes (8): Connected, Hint, HintFrame, LiveEvent, LiveHint, LiveHints, SseLiveHints, LiveHintsTest

### Community 152 - "Community 152"
Cohesion: 0.14
Nodes (6): Formats, durationMinutes(), depthFromDisplay(), pressureFromDisplay(), pressureIn(), ToolRanges

### Community 154 - "Community 154"
Cohesion: 0.15
Nodes (6): ApiToolsBottomTimeGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseLimits, ApiToolsBottomTimeGet200ResponseResidualN2, CommonAnswer, finite()

### Community 155 - "Community 155"
Cohesion: 0.14
Nodes (16): BottomTime, bottomTime(), Limit, ADR-0032, ADR-0033, ADR-0071, MAX_DEPTH_M, NOAA_ROWS (+8 more)

### Community 156 - "Community 156"
Cohesion: 0.26
Nodes (17): ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres(), isHypoxic(), metresPerBar() (+9 more)

### Community 157 - "Community 157"
Cohesion: 0.16
Nodes (16): DecisionTimelineProps, GapKind, ADR-0063, addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, laneFitsCapsule(), relations() (+8 more)

### Community 158 - "Community 158"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 159 - "Community 159"
Cohesion: 0.15
Nodes (5): ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits

### Community 160 - "Community 160"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 161 - "Community 161"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 162 - "Community 162"
Cohesion: 0.13
Nodes (17): siteImportStatus, SiteImportRouteDeps, StartBody, STATUS, ADR-0021, SiteImportError, SiteImportService, Area (+9 more)

### Community 163 - "Community 163"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 164 - "Community 164"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 165 - "Community 165"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 166 - "Community 166"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 167 - "Community 167"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 168 - "Community 168"
Cohesion: 0.20
Nodes (3): FakeInstances, FakeTokens, SyncAuthTest

### Community 169 - "Community 169"
Cohesion: 0.15
Nodes (10): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, ADR-0068 (+2 more)

### Community 170 - "Community 170"
Cohesion: 0.17
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 171 - "Community 171"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 172 - "Community 172"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 173 - "Community 173"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 176 - "Community 176"
Cohesion: 0.14
Nodes (7): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, headers, uniqueWord()

### Community 177 - "Community 177"
Cohesion: 0.22
Nodes (11): fromOsm(), fromSsi(), fromWd(), HOUSE_REEF, osm(), site(), ssi(), ADR-0021 (+3 more)

### Community 178 - "Community 178"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 179 - "Community 179"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 180 - "Community 180"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 181 - "Community 181"
Cohesion: 0.26
Nodes (3): DiverView, DiverViewCertifications, DiverViewCertificationsNewest

### Community 182 - "Community 182"
Cohesion: 0.26
Nodes (3): DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 183 - "Community 183"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 184 - "Community 184"
Cohesion: 0.18
Nodes (9): Buddy, KAI, MIA, NAMELESS, SAM, Status, ADR-0029, ADR-0043 (+1 more)

### Community 185 - "Community 185"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 186 - "Community 186"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 187 - "Community 187"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 188 - "Community 188"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 189 - "Community 189"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 190 - "Community 190"
Cohesion: 0.29
Nodes (7): otu(), oxygenLimitMinutes(), compressibility(), Cubic, minusOne(), pressureForLitres(), surfaceLitres()

### Community 191 - "Community 191"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 192 - "Community 192"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 193 - "Community 193"
Cohesion: 0.47
Nodes (8): DiveHubApp(), ErrorText(), InstanceForm(), Loading(), PasswordField(), PrimaryButton(), SignInForm(), Spinner()

### Community 194 - "Community 194"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 195 - "Community 195"
Cohesion: 0.28
Nodes (7): ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, s(), ADR-0035

### Community 197 - "Community 197"
Cohesion: 0.25
Nodes (8): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, MODULE_IMPORTS, moduleRules, target()

### Community 198 - "Community 198"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 199 - "Community 199"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 201 - "Community 201"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 202 - "Community 202"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 203 - "Community 203"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 204 - "Community 204"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 205 - "Community 205"
Cohesion: 0.40
Nodes (4): TAGS, ADR-0023, { stdout, stderr }, total

### Community 208 - "Community 208"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 209 - "Community 209"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 210 - "Community 210"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1270 isolated node(s):** `Reserve`, `RockBottom`, `SuitThickness`, `Off`, `ADR-0034` (+1265 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3440 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **118 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 1` to `Community 3`, `Community 4`, `Community 5`, `Community 6`, `Community 7`, `Community 8`, `Community 10`, `Community 12`, `Community 13`, `Community 14`, `Community 16`, `Community 17`, `Community 18`, `Community 148`, `Community 21`, `Community 24`, `Community 25`, `Community 26`, `Community 40`, `Community 184`, `Community 60`, `Community 61`, `Community 65`, `Community 68`, `Community 70`, `Community 71`, `Community 80`, `Community 84`, `Community 99`, `Community 102`, `Community 121`, `Community 123`, `Community 126`, `Community 127`?**
  _High betweenness centrality (0.023) - this node is a cross-community bridge._
- **What connects `Reserve`, `RockBottom`, `SuitThickness` to the rest of the system?**
  _1270 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.021337038752508877 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 28` to `Community 32`, `Community 161`, `Community 34`, `Community 2`, `Community 0`, `Community 39`, `Community 9`, `Community 11`, `Community 78`, `Community 15`, `Community 113`, `Community 49`, `Community 19`, `Community 20`, `Community 86`, `Community 23`?**
  _High betweenness centrality (0.014) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.030363815142576203 - nodes in this community are weakly interconnected._
- **Why does `typescript` connect `Community 167` to `Community 121`, `Community 158`, `Community 113`?**
  _High betweenness centrality (0.011) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.04073027602695066 - nodes in this community are weakly interconnected._