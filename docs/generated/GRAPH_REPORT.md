# Graph Report - dive_hub  (2026-11-19)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 10016 nodes · 30286 edges · 322 communities (207 shown, 115 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 533 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `1eebd96a`
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
- Community 209
- Community 210
- Community 211
- Community 212
- Community 217

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 204 edges
2. `Button()` - 198 edges
3. `useErrorText()` - 169 edges
4. `useDisplay()` - 161 edges
5. `Muted()` - 139 edges
6. `drizzle-orm` - 139 edges
7. `unwrap()` - 138 edges
8. `announce()` - 106 edges
9. `createTestDatabase()` - 104 edges
10. `TestDatabase` - 103 edges

## Surprising Connections (you probably didn't know these)
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `Removal` --references--> `ProviderServiceError`  [EXTRACTED]
  apps/server/src/providers/push/push-service.ts → apps/server/src/providers/registry.ts
- `SsiClientOptions` --references--> `Fetch`  [EXTRACTED]
  apps/server/src/providers/ssi/ssi-client.ts → apps/server/src/providers/ssi/ssi-transport.ts

## Import Cycles
- None detected.

## Communities (322 total, 115 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.03
Nodes (173): user, cylinder, device, diver, diveSiteExternalId, lead, DIVE_FIELDS, SSI_CAPABILITIES (+165 more)

### Community 1 - "Community 1"
Cohesion: 0.05
Nodes (204): ChangePassword(), DisplaySettings(), Sessions(), useDescribeAgent(), AdminSettings(), DeleteUserDialog(), Invitations(), Invite() (+196 more)

### Community 2 - "Community 2"
Cohesion: 0.02
Nodes (198): cylinderCatalogueQuery(), equipmentOfDiverQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery() (+190 more)

### Community 3 - "Community 3"
Cohesion: 0.02
Nodes (162): AI_ACCESS_NAME_MAX, aiAccessLog, aiAccessSetting, downsampleMinMax(), Nullable(), Access, AccessView, AiAccessRouteDeps (+154 more)

### Community 4 - "Community 4"
Cohesion: 0.02
Nodes (146): aiAccessSettingQuery(), BackupRunView, backupSettingsQuery(), BackupSettingsView, backupsQuery(), siteDataQuery(), SiteDataView, ADR-0030 (+138 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (137): EMPTY_SITE_IMPORT_COUNTS, ImportArea, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053 (+129 more)

### Community 6 - "Community 6"
Cohesion: 0.02
Nodes (133): replyCertificationError, STATUS, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, errors (+125 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (142): allButDive(), AnyLogbookCheck, CandidateView, continuationCandidateQuery(), ContinuationCandidateView, deletedDivesOn(), DeletedDivesPageView, deletedDivesQuery() (+134 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (101): APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), probeAppLock(), buildManifest() (+93 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (127): AppLockProbe, BACKUP_EXTENSION, BACKUP_FORMAT_VERSION, BACKUP_SKIPPED_DIRS, BackupArchiveError, BackupDecryptionKey, BackupEncryption, BackupEncryptionMetadata (+119 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (123): CentreService, CentreRouteDeps, LEAD_PLACEMENTS, WEIGHTING_FELT, DiverService, CertificationHint, CertificationHintExpiry, DiveCertifications (+115 more)

### Community 11 - "Community 11"
Cohesion: 0.02
Nodes (115): apikey, recordingEvent, Assessment, FindingView, Row, ADR-0036, upload(), Accepted (+107 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (129): buddiesForQuery(), buddiesQuery(), BuddyView, ConflictChoices, ConflictEntryView, ConflictFieldView, ConflictsOfConnection, ConnectionView (+121 more)

### Community 13 - "Community 13"
Cohesion: 0.03
Nodes (116): divesQuery(), DiveSummary, LogbookPage, PAGE_SIZE, connectionsQuery(), pushRunActive(), pushRunBody(), PushRunChoices (+108 more)

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (28): LogbookScreenshotTest, ApiDivesGet200Response, ApiDivesGet200ResponseCounts, ApiDivesGet200ResponseDivesItem, ApiDivesGet200ResponseDivesItemGasesItem, ApiDivesGet200ResponseDivesItemParticipantsItem, ApiDivesGet200ResponseDivesItemSite, ApiDivesGet200ResponseMonthsItem (+20 more)

### Community 15 - "Community 15"
Cohesion: 0.04
Nodes (112): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+104 more)

### Community 16 - "Community 16"
Cohesion: 0.04
Nodes (107): LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, ParticipantRole, RecordingSummary, SuitType, SuitUndergarment, WeightingFelt (+99 more)

### Community 17 - "Community 17"
Cohesion: 0.03
Nodes (95): adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, PUBLIC_AUTH_PATHS, ADR-0011, ADR-0012, ADR-0013, ADR-0035 (+87 more)

### Community 18 - "Community 18"
Cohesion: 0.04
Nodes (93): SecuritySettings(), importsQuery(), isUnauthorized(), App(), BackupsPage, CentrePage, CentresPage, currentRoute() (+85 more)

### Community 19 - "Community 19"
Cohesion: 0.03
Nodes (22): MainActivity, MainViewModel, UiState, LoadingsUiState, LoadingsViewModel, Answers, number(), ToolsViewModel (+14 more)

### Community 20 - "Community 20"
Cohesion: 0.03
Nodes (82): createServer(), createAuth(), auth, loadAuthSecret(), acquireAppLock(), BackupLog, createServices(), ServiceParts (+74 more)

### Community 21 - "Community 21"
Cohesion: 0.04
Nodes (78): equipmentDueQuery(), cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043 (+70 more)

### Community 22 - "Community 22"
Cohesion: 0.03
Nodes (76): connection, dive, diveLease, providerConflict, providerLease, push, pushRun, revision (+68 more)

### Community 23 - "Community 23"
Cohesion: 0.02
Nodes (96): actorType, aiAccessOutcome, backupRunKind, backupRunStatus, buddySuggestion, candidateResolution, centreSource, CERTIFICATION_IMAGE_STATES (+88 more)

### Community 24 - "Community 24"
Cohesion: 0.05
Nodes (89): label(), AcceptedBuddySuggestionView, BuddySuggestionOverlapView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView, candidatesQuery(), deletedDivesTotal() (+81 more)

### Community 25 - "Community 25"
Cohesion: 0.03
Nodes (74): createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities(), providerError() (+66 more)

### Community 26 - "Community 26"
Cohesion: 0.07
Nodes (79): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, assessmentRoutes() (+71 more)

### Community 27 - "Community 27"
Cohesion: 0.03
Nodes (12): AppContainer, KeystoreTokenStore, PreferencesInstanceUrlStore, UserCacheStore, DiveHubApplication, SyncWorker, WorkManagerPeriodicSync, InstanceUrlStore (+4 more)

### Community 28 - "Community 28"
Cohesion: 0.05
Nodes (72): AuthOptions, Db, jointDive, logbookCheckAnswer, logbookCheckDiveAnswer, ADR-0016, agreeInDepthAndDuration(), CheckedDive (+64 more)

### Community 29 - "Community 29"
Cohesion: 0.04
Nodes (70): providerGoneDive, open(), AutomationSettings, createConnectionService(), checkAutomation(), checkFollowNumbers(), expiryOf(), onTurn() (+62 more)

### Community 30 - "Community 30"
Cohesion: 0.05
Nodes (72): diveFingerprint, PushLeftOut, waitingOf(), Choice, isExporting(), Leave, referenceOf(), Requirement (+64 more)

### Community 31 - "Community 31"
Cohesion: 0.04
Nodes (76): buildJoined(), partInput(), partsOfJoined(), rebuildJoined(), RecordingRow, ADR-0063, writeJoined(), CylinderError (+68 more)

### Community 32 - "Community 32"
Cohesion: 0.05
Nodes (15): AndroidDiveStore, DiveStore, toBoolean(), toLong(), toModel(), BodyWeight, Cylinder, Diver (+7 more)

### Community 33 - "Community 33"
Cohesion: 0.07
Nodes (68): GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult(), PlanInputs(), PlanRuleExplanation() (+60 more)

### Community 34 - "Community 34"
Cohesion: 0.06
Nodes (12): LogbookPaging, Dive, counts(), json(), LogbookFiltersTest, LogbookFormatTest, LogbookPagingTest, LogbookRepositoryTest (+4 more)

### Community 35 - "Community 35"
Cohesion: 0.06
Nodes (23): DiveDetailUiState, DiveDetailViewModel, LogbookViewModel, DiveRepository, neighboursPath(), DiveHubException, NoAnswerException, countOf() (+15 more)

### Community 36 - "Community 36"
Cohesion: 0.09
Nodes (3): LoadingSource, limitWord(), ErrorCodes

### Community 37 - "Community 37"
Cohesion: 0.05
Nodes (56): looksLikeZip(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy(), ADR-0024 (+48 more)

### Community 38 - "Community 38"
Cohesion: 0.08
Nodes (54): BottomTime, bottomTime(), BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen (+46 more)

### Community 39 - "Community 39"
Cohesion: 0.05
Nodes (62): AssessmentService, AssessmentRouteDeps, Auth, BackupRouteDeps, BACKUP_RUN_TASK, BackupService, backupTask(), ADR-0069 (+54 more)

### Community 40 - "Community 40"
Cohesion: 0.06
Nodes (21): GasPlanAnswer, ApiDiverBodyWeightsGet200ResponseItem, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule (+13 more)

### Community 41 - "Community 41"
Cohesion: 0.07
Nodes (56): BottomTime, bottomTime(), Limit, ADR-0032, ADR-0033, ADR-0071, ambientPressureBar(), bestMixPercent() (+48 more)

### Community 42 - "Community 42"
Cohesion: 0.06
Nodes (61): ComputerChoice, DiveImportMode, DiveKind, ProviderImportPlan, providerSiteData, ProviderCylinder, siteRef(), close() (+53 more)

### Community 43 - "Community 43"
Cohesion: 0.06
Nodes (61): ConnectionRow, WaitingPushOptions, createImport(), startImport(), audienceOfConnection(), audienceOfDive(), audienceOfDiver(), emitHint() (+53 more)

### Community 44 - "Community 44"
Cohesion: 0.08
Nodes (13): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), GasPlanInputTest (+5 more)

### Community 45 - "Community 45"
Cohesion: 0.06
Nodes (57): accountListEntry, diverExternalId, tidyName(), DiverDetails, Caller, foldName(), nameDiffers(), Buddy (+49 more)

### Community 46 - "Community 46"
Cohesion: 0.05
Nodes (50): ADR-0036, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048, assessedDive() (+42 more)

### Community 47 - "Community 47"
Cohesion: 0.06
Nodes (60): AREAS, baseCandidates(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads(), exists() (+52 more)

### Community 48 - "Community 48"
Cohesion: 0.08
Nodes (9): DiveDetailScreenshotTest, DiveViewFromRecording, DiveViewFromRecordingStartsAt, DiveViewParticipantsItem, DiveViewParticipantsItemCertificationHint, DiveViewWaterMismatch, NeighboursView, NeighboursViewNext (+1 more)

### Community 49 - "Community 49"
Cohesion: 0.05
Nodes (46): Tx, diverBodyWeight, DiverError, ADR-0016, ADR-0053, ACCOUNT_PATTERN, accountKey(), createDiverService() (+38 more)

### Community 50 - "Community 50"
Cohesion: 0.04
Nodes (46): continuationAppend, equipmentUse, managesDiveDiver(), CandidateError, CandidateRow, createCandidateService(), lockMine(), RecordingRow (+38 more)

### Community 51 - "Community 51"
Cohesion: 0.04
Nodes (42): Direction, DiveExport, DiveListing, FIND_BY, FindBy, Operation, OPERATIONS, OutgoingDive (+34 more)

### Community 52 - "Community 52"
Cohesion: 0.06
Nodes (52): ADR-0063, headers, ADR-0030, ADR-0040, Status, ADR-0056, ADR-0047, AiAccess (+44 more)

### Community 53 - "Community 53"
Cohesion: 0.04
Nodes (57): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+49 more)

### Community 54 - "Community 54"
Cohesion: 0.08
Nodes (26): ProfileTable(), Recordings(), DiveViewRecordingsItem, DiveViewRecordingsItemDevice, DiveViewRecordingsItemSummarySurfaceBreaksItem, RecordingSamplesView, RecordingSamplesViewSeriesItem, computerRecordings() (+18 more)

### Community 55 - "Community 55"
Cohesion: 0.07
Nodes (48): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+40 more)

### Community 56 - "Community 56"
Cohesion: 0.08
Nodes (56): Answer, answer(), baseFrom(), Confidence, high, low, medium, Cylinder (+48 more)

### Community 57 - "Community 57"
Cohesion: 0.09
Nodes (55): managedDiverIds(), tieToJoined(), cylindersOf(), mergeProviderCylinder(), providerCylinderOf(), participantsOf(), columnsOf(), partsOf() (+47 more)

### Community 58 - "Community 58"
Cohesion: 0.05
Nodes (52): BottomTimeInput, Conditions, GasMix, floorTenth(), GasRule, LoadingDive, TissuesAfterDivesInput, NdlInput (+44 more)

### Community 59 - "Community 59"
Cohesion: 0.09
Nodes (52): withBuddies(), APP_KEYS, appRow(), BuddySaved, byId(), changedKeys(), deleteTestEntries(), entryNamed() (+44 more)

### Community 60 - "Community 60"
Cohesion: 0.06
Nodes (37): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+29 more)

### Community 61 - "Community 61"
Cohesion: 0.07
Nodes (49): Delivered, ProviderError, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf() (+41 more)

### Community 62 - "Community 62"
Cohesion: 0.09
Nodes (11): SyncCheckpoint, Complete, SessionEnded, Stopped, SyncEngine, SyncPhase, Details, Meta (+3 more)

### Community 63 - "Community 63"
Cohesion: 0.08
Nodes (11): LeadAnswer, LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseLeftOut, ApiDiversIdLeadEstimateGet200ResponseMissingItem (+3 more)

### Community 64 - "Community 64"
Cohesion: 0.12
Nodes (27): Body, Hold, Offline, RefreshData, refreshDeletedJson(), refreshDetailJson(), refreshDetailsPage(), refreshDiveId() (+19 more)

### Community 65 - "Community 65"
Cohesion: 0.08
Nodes (28): DiveBody(), DiveDetailRoute(), DiveDetailScreen(), errorText(), FactRow(), Facts(), Loading(), NeighbourButton() (+20 more)

### Community 66 - "Community 66"
Cohesion: 0.07
Nodes (41): SsiClient, asNumber(), asText(), buildCreateRecord(), buildDeleteRecord(), buildUpdateRecord(), compareReadBack(), datasetLength() (+33 more)

### Community 68 - "Community 68"
Cohesion: 0.06
Nodes (39): Me, BuddySuggestionPreviewView, ExternalIdView, useFocusBackWhenClosed(), holdLeave(), mayLeave(), useLeaveGuard(), needsOsmAttribution() (+31 more)

### Community 69 - "Community 69"
Cohesion: 0.05
Nodes (43): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+35 more)

### Community 70 - "Community 70"
Cohesion: 0.08
Nodes (39): Queryable, BottomTimeQuery, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033, ADR-0071 (+31 more)

### Community 71 - "Community 71"
Cohesion: 0.06
Nodes (45): pushRunItem, PushRunLeftOut, RemoteDive, Blocked, classify(), FailureOutcome, kindOf(), KINDS (+37 more)

### Community 72 - "Community 72"
Cohesion: 0.14
Nodes (23): Body, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage(), diverJson(), diveStartsAtOf() (+15 more)

### Community 73 - "Community 73"
Cohesion: 0.09
Nodes (20): AvatarCircle(), DiveRow(), Error(), Fact(), Filters(), Loading(), LogbookList(), LogbookRoute() (+12 more)

### Community 74 - "Community 74"
Cohesion: 0.08
Nodes (3): DiveHubTheme(), LauncherIconTest, ToolsScreenshotTest

### Community 75 - "Community 75"
Cohesion: 0.08
Nodes (42): NoFly, DEFAULT_GF_HIGH, residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows(), DiveRow, endMsOf() (+34 more)

### Community 76 - "Community 76"
Cohesion: 0.07
Nodes (10): ApiClient, ApiErrorBody, Connected, Hint, HintFrame, LiveEvent, LiveHint, LiveHints (+2 more)

### Community 77 - "Community 77"
Cohesion: 0.08
Nodes (39): Series, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining(), ssiDiveType() (+31 more)

### Community 78 - "Community 78"
Cohesion: 0.05
Nodes (41): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+33 more)

### Community 79 - "Community 79"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 80 - "Community 80"
Cohesion: 0.07
Nodes (41): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+33 more)

### Community 81 - "Community 81"
Cohesion: 0.12
Nodes (8): FakeCache, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore, UserScopedStore

### Community 82 - "Community 82"
Cohesion: 0.06
Nodes (32): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError (+24 more)

### Community 83 - "Community 83"
Cohesion: 0.06
Nodes (35): SUIT_TYPES, SUIT_UNDERGARMENTS, DiverCertifications, replyDiverError, STATUS, CertificationSummary, DiverDetailsView, DiverDetailsViewSchema (+27 more)

### Community 84 - "Community 84"
Cohesion: 0.08
Nodes (34): date(), FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position(), positive() (+26 more)

### Community 85 - "Community 85"
Cohesion: 0.07
Nodes (33): sampleCount(), SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject() (+25 more)

### Community 86 - "Community 86"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 87 - "Community 87"
Cohesion: 0.06
Nodes (36): Change(), Changes(), PositionSchema, AdoptBody, Country, CreateBody, Description, EditBody (+28 more)

### Community 88 - "Community 88"
Cohesion: 0.08
Nodes (32): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+24 more)

### Community 89 - "Community 89"
Cohesion: 0.08
Nodes (23): diveWith(), ADR-0028, ADR-0043, buddySite(), Dive, OTHER, resetSource(), ADR-0012 (+15 more)

### Community 90 - "Community 90"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 91 - "Community 91"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 92 - "Community 92"
Cohesion: 0.07
Nodes (35): EquipmentService, RecordInput, ScheduleInput, DiveGearItemViewSchema, DiveGearViewSchema, DueState, EquipmentDetailViewSchema, EquipmentDueViewSchema (+27 more)

### Community 93 - "Community 93"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 94 - "Community 94"
Cohesion: 0.10
Nodes (13): ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseCns, ApiDiversIdLoadingsGet200ResponseN2, ApiDiversIdLoadingsGet200ResponseNoFly, ApiDiversIdLoadingsGet200ResponseNoFlySource, ApiDiversIdLoadingsGet200ResponseOtu, BodyWeight (+5 more)

### Community 95 - "Community 95"
Cohesion: 0.10
Nodes (12): drawBand(), ProfileSketch(), AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint, SketchProfile (+4 more)

### Community 96 - "Community 96"
Cohesion: 0.08
Nodes (17): HINT_CHANNEL, parseHint(), createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener() (+9 more)

### Community 97 - "Community 97"
Cohesion: 0.09
Nodes (27): CylinderMaterial, aluminium, carbon, steel, dayNumber(), enteredDecoFromNdl(), minNdlSeconds(), NoFly (+19 more)

### Community 98 - "Community 98"
Cohesion: 0.10
Nodes (29): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+21 more)

### Community 99 - "Community 99"
Cohesion: 0.08
Nodes (21): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, killTree() (+13 more)

### Community 100 - "Community 100"
Cohesion: 0.10
Nodes (26): ImportErrorCode, ImportFound, importOriginal, errorMessage(), formatOf(), CHOICE_WAIT_DAYS, createImportService(), cancelImport() (+18 more)

### Community 101 - "Community 101"
Cohesion: 0.13
Nodes (33): entryMatches(), kindOfMode(), deviceDiver(), importsField(), createDiveImportService(), asRecording(), assess(), computersOf() (+25 more)

### Community 102 - "Community 102"
Cohesion: 0.12
Nodes (27): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+19 more)

### Community 103 - "Community 103"
Cohesion: 0.09
Nodes (8): ApiDiveSitesGet200Response, SiteView, SiteViewExternalIdsItem, SiteViewExternalIdsItemAttribution, SiteViewExternalIdsItemOffered, SiteViewExternalIdsItemOfferedPosition, SiteViewExternalIdsItemPrivateTo, SiteViewPosition

### Community 105 - "Community 105"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 106 - "Community 106"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 107 - "Community 107"
Cohesion: 0.10
Nodes (25): EVIDENCE, Evidence, FindingValues, NOT_COVERED_DIVE_MODES, RULE_IDS, RuleId, SEVERITIES, Severity (+17 more)

### Community 108 - "Community 108"
Cohesion: 0.09
Nodes (23): computerEventOf(), ADR-0036, computerEventFromFit(), COMPUTER_EVENT, computerEventFromSuunto(), DECO_MODEL, decoModelFromSuunto, DIVE_MODE (+15 more)

### Community 109 - "Community 109"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 110 - "Community 110"
Cohesion: 0.18
Nodes (28): acceptedFormat(), Answer, dateTime(), deleteAndCheck(), describe(), findNewId(), Format, FORMATS (+20 more)

### Community 111 - "Community 111"
Cohesion: 0.10
Nodes (26): globalSetup(), ADR-0023, accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks() (+18 more)

### Community 112 - "Community 112"
Cohesion: 0.29
Nodes (8): leadAnswer(), EstimateCylinder, PlannedConditions, Suit, assemblePersonalizedSources(), suitOf(), leadEstimate(), PersonalizedToolsTest

### Community 113 - "Community 113"
Cohesion: 0.11
Nodes (7): ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh, ApiToolsBottomTimeGet200ResponseUsedSacLpm, ApiToolsBottomTimeGet200ResponseUsedWater

### Community 114 - "Community 114"
Cohesion: 0.11
Nodes (7): ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport

### Community 115 - "Community 115"
Cohesion: 0.11
Nodes (26): DiverPlan, GasPlan, GasPlanLimit, depth, gas, mod, Ok, OutsideLimits (+18 more)

### Community 116 - "Community 116"
Cohesion: 0.09
Nodes (22): DELETE_QUIET_MS, Cylinder, DiveView, RevisionView, ADR-0045, upload(), Candidate, Deleted (+14 more)

### Community 117 - "Community 117"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 118 - "Community 118"
Cohesion: 0.21
Nodes (5): PlanningSacResult, BottomTimeToolInput, GasNumbersInput, ToolDiverSource, ToolsServiceTest

### Community 119 - "Community 119"
Cohesion: 0.08
Nodes (22): account, accountRelations, rateLimit, session, sessionRelations, ADR-0011, ADR-0012, ADR-0035 (+14 more)

### Community 120 - "Community 120"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 121 - "Community 121"
Cohesion: 0.11
Nodes (24): DiveGear, ensureItemForDevice(), GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow, ItemView (+16 more)

### Community 122 - "Community 122"
Cohesion: 0.16
Nodes (24): DiveOrigin, REFERENCE_PREFIX, SsiLogbook, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef() (+16 more)

### Community 123 - "Community 123"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 124 - "Community 124"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 125 - "Community 125"
Cohesion: 0.18
Nodes (3): DiveViewValues, DiveViewValuesStartsAt, StoreLogbookTest

### Community 126 - "Community 126"
Cohesion: 0.15
Nodes (22): planningSac(), PlanningSacDive, PlanningSacSource, default, logbook, jsRound(), powInt(), roundTo() (+14 more)

### Community 127 - "Community 127"
Cohesion: 0.08
Nodes (25): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+17 more)

### Community 128 - "Community 128"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 129 - "Community 129"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 130 - "Community 130"
Cohesion: 0.13
Nodes (6): ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting

### Community 131 - "Community 131"
Cohesion: 0.13
Nodes (6): DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, WaitingPushView

### Community 132 - "Community 132"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 133 - "Community 133"
Cohesion: 0.11
Nodes (20): ADR-0025, WaterMismatch, waterMismatch(), DECO_MODELS, DIVE_MODES, DIVER_SOURCES, GAS_CIRCUITS, IMPORT_SOURCES (+12 more)

### Community 134 - "Community 134"
Cohesion: 0.14
Nodes (18): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+10 more)

### Community 135 - "Community 135"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 136 - "Community 136"
Cohesion: 0.14
Nodes (19): Deep, DepthReading, depthReading(), deviceName(), DiveValue, AVG_DEPTH_M, DURATION_SECONDS, MAX_DEPTH_M (+11 more)

### Community 137 - "Community 137"
Cohesion: 0.12
Nodes (19): SUGGESTION_STATUSES, DiveProfileView, DiveProfileViewSchema, ADR-0041, AcceptBody, STATUS, SuggestionRouteDeps, ADR-0070 (+11 more)

### Community 138 - "Community 138"
Cohesion: 0.24
Nodes (5): DiveViewRecordingsItemSummary, summaryOf(), assembleToolSources(), summaryOf(), ToolSourcesStoreTest

### Community 139 - "Community 139"
Cohesion: 0.12
Nodes (18): CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043, CreateBody (+10 more)

### Community 140 - "Community 140"
Cohesion: 0.10
Nodes (8): ImportOutcome, FileFormat, FitAdapter, SuuntoJsonAdapter, UddfAdapter, ImportServiceDeps, ParsedRecording, BlobStore

### Community 141 - "Community 141"
Cohesion: 0.14
Nodes (18): DAY_MS, MAX_OFFSET_MS, inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), placeLocalTime(), Position (+10 more)

### Community 142 - "Community 142"
Cohesion: 0.12
Nodes (16): stubDive(), ADR-0015, ADR-0023, ADR-0025, ADR-0051, Site, ADR-0057, ADR-0024 (+8 more)

### Community 143 - "Community 143"
Cohesion: 0.19
Nodes (3): LeadEstimateUiState, LeadEstimateViewModel, PersonalizedSources

### Community 144 - "Community 144"
Cohesion: 0.15
Nodes (5): ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits

### Community 145 - "Community 145"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 146 - "Community 146"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 147 - "Community 147"
Cohesion: 0.11
Nodes (18): BOTTOM_TIME_RULES, PLAN_RULES, PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody (+10 more)

### Community 148 - "Community 148"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 149 - "Community 149"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 150 - "Community 150"
Cohesion: 0.14
Nodes (18): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript-eslint@8.72.0, engines (+10 more)

### Community 151 - "Community 151"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 152 - "Community 152"
Cohesion: 0.19
Nodes (11): LeadInputTest, SuitType, drysuit, none, semidry, skin, wetsuit, SuitUndergarment (+3 more)

### Community 153 - "Community 153"
Cohesion: 0.12
Nodes (18): typescript@7.0.2, typescript@7.0.2, typescript@6.0.3, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0 (+10 more)

### Community 154 - "Community 154"
Cohesion: 0.17
Nodes (16): UtcOffsetSource, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput, round3() (+8 more)

### Community 155 - "Community 155"
Cohesion: 0.15
Nodes (17): defineTool(), Id(), Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), HINT (+9 more)

### Community 156 - "Community 156"
Cohesion: 0.14
Nodes (14): AL80, EstimateDiveCylinder, LEAD_CASES, leadInput(), LeadVectorInput, planOf(), STEEL_12, ADR-0031 (+6 more)

### Community 157 - "Community 157"
Cohesion: 0.17
Nodes (3): Formats, durationMinutes(), depthIn()

### Community 158 - "Community 158"
Cohesion: 0.17
Nodes (4): ApiMeGet200ResponseOwnDiver, DiveViewCylindersItemSeries, DiveViewPosition, DiveViewRecordingsItemSummaryGasesItem

### Community 159 - "Community 159"
Cohesion: 0.19
Nodes (4): ApiToolsBottomTimeGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseLimits, ApiToolsBottomTimeGet200ResponseResidualN2

### Community 160 - "Community 160"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 161 - "Community 161"
Cohesion: 0.15
Nodes (14): certification, PROBLEMS, createUserAdminService(), keepAnAdmin(), lock(), deleteUserData(), ADR-0013, ADR-0024 (+6 more)

### Community 162 - "Community 162"
Cohesion: 0.15
Nodes (15): equipmentItem, serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, DueEntry, ScheduleView, equipmentList() (+7 more)

### Community 163 - "Community 163"
Cohesion: 0.16
Nodes (13): CIRCUIT, circuitFromFit, DECO_MODEL, decoModelFromFit, DIVE_ALERT, DIVE_MODE, diveModeFromFit, ADR-0015 (+5 more)

### Community 164 - "Community 164"
Cohesion: 0.19
Nodes (13): asEstimateInput(), evaluateLeadVectorCase(), LEAD_VECTOR_KERNEL, leadVectorFile(), VECTOR_KERNELS, VectorFile, vectorFile(), assertMatch() (+5 more)

### Community 165 - "Community 165"
Cohesion: 0.23
Nodes (13): comparableOf(), SsiRecord, contextOf(), FakeSsi, findIn(), finishTwoway(), GAS, sendTwoway() (+5 more)

### Community 166 - "Community 166"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 167 - "Community 167"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 168 - "Community 168"
Cohesion: 0.26
Nodes (14): Estimate, isoUtc(), LoadingBasis, LoadingDive, N2Loadings, N2UnavailableReason, no_profile, no_recent_dive (+6 more)

### Community 169 - "Community 169"
Cohesion: 0.18
Nodes (11): addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, relations(), TimelineBar, TimelineBarGeometry, TimelineGapGeometry, TimelineGeometry (+3 more)

### Community 170 - "Community 170"
Cohesion: 0.13
Nodes (14): name, needs, runs-on, timeout-minutes, concurrency, cancel-in-progress, group, env (+6 more)

### Community 171 - "Community 171"
Cohesion: 0.23
Nodes (13): DiveHubApp(), ErrorText(), InstanceForm(), Loading(), PasswordField(), PrimaryButton(), SignInForm(), Spinner() (+5 more)

### Community 172 - "Community 172"
Cohesion: 0.21
Nodes (3): SyncLine(), formatSyncTime(), SyncTimeFormatTest

### Community 173 - "Community 173"
Cohesion: 0.29
Nodes (14): managementOf(), createEquipmentService(), assemble(), detailOf(), lastCylinderOf(), managedItem(), managedRecord(), managedSchedule() (+6 more)

### Community 174 - "Community 174"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 175 - "Community 175"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 176 - "Community 176"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 177 - "Community 177"
Cohesion: 0.21
Nodes (9): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, readQr(), ADR-0043, ADR-0043 (+1 more)

### Community 178 - "Community 178"
Cohesion: 0.26
Nodes (3): DiverView, DiverViewCertifications, DiverViewCertificationsNewest

### Community 179 - "Community 179"
Cohesion: 0.26
Nodes (3): DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 180 - "Community 180"
Cohesion: 0.18
Nodes (10): EquipmentError, ADR-0034, ADR-0053, checkCost(), checkDates(), checkWindow(), checkResets(), checkSchedule() (+2 more)

### Community 181 - "Community 181"
Cohesion: 0.18
Nodes (9): AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031, WETSUIT_5 (+1 more)

### Community 182 - "Community 182"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 183 - "Community 183"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 184 - "Community 184"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 185 - "Community 185"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 186 - "Community 186"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 187 - "Community 187"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 188 - "Community 188"
Cohesion: 0.22
Nodes (9): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, ADR-0076, MODULE_IMPORTS, moduleRules (+1 more)

### Community 189 - "Community 189"
Cohesion: 0.20
Nodes (10): verify, prerelease, sha, tag, versionCode, versionName, name, outputs (+2 more)

### Community 190 - "Community 190"
Cohesion: 0.20
Nodes (10): with, cache, distribution, fetch-depth, if-no-files-found, java-version, name, path (+2 more)

### Community 191 - "Community 191"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 192 - "Community 192"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 193 - "Community 193"
Cohesion: 0.25
Nodes (6): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, CylinderInput, EstimateCylinder

### Community 195 - "Community 195"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 196 - "Community 196"
Cohesion: 0.22
Nodes (9): version, on, push, workflow_dispatch, tags, description, required, type (+1 more)

### Community 197 - "Community 197"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 198 - "Community 198"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 200 - "Community 200"
Cohesion: 0.25
Nodes (7): Confidence, EstimateAdjustment, EstimateBasis, EstimateInput, EstimateMissing, ADR-0025, ADR-0031

### Community 201 - "Community 201"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 202 - "Community 202"
Cohesion: 0.29
Nodes (6): itemIds(), ADR-0026, ADR-0034, Usage, UsageDive, UsageRow

### Community 203 - "Community 203"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 204 - "Community 204"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 205 - "Community 205"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 206 - "Community 206"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 207 - "Community 207"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 209 - "Community 209"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 210 - "Community 210"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 211 - "Community 211"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1277 isolated node(s):** `Off`, `Reserve`, `RockBottom`, `SuitThickness`, `ADR-0013` (+1272 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3497 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **115 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 0` to `Community 3`, `Community 5`, `Community 6`, `Community 8`, `Community 9`, `Community 10`, `Community 11`, `Community 141`, `Community 16`, `Community 17`, `Community 20`, `Community 22`, `Community 23`, `Community 25`, `Community 26`, `Community 28`, `Community 29`, `Community 30`, `Community 31`, `Community 161`, `Community 162`, `Community 39`, `Community 42`, `Community 43`, `Community 45`, `Community 49`, `Community 50`, `Community 57`, `Community 70`, `Community 71`, `Community 202`, `Community 75`, `Community 82`, `Community 88`, `Community 98`, `Community 100`, `Community 106`, `Community 116`, `Community 119`, `Community 121`, `Community 127`?**
  _High betweenness centrality (0.023) - this node is a cross-community bridge._
- **What connects `Off`, `Reserve`, `RockBottom` to the rest of the system?**
  _1277 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.026698795180722892 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 4` to `Community 1`, `Community 2`, `Community 68`, `Community 7`, `Community 12`, `Community 13`, `Community 15`, `Community 18`, `Community 53`, `Community 21`, `Community 86`, `Community 24`?**
  _High betweenness centrality (0.018) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.045289633524927646 - nodes in this community are weakly interconnected._
- **Why does `Tx` connect `Community 49` to `Community 0`, `Community 161`, `Community 98`, `Community 3`, `Community 5`, `Community 9`, `Community 42`, `Community 43`, `Community 57`, `Community 16`, `Community 50`, `Community 20`, `Community 88`, `Community 121`, `Community 28`, `Community 29`, `Community 31`?**
  _High betweenness centrality (0.015) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.022739018087855296 - nodes in this community are weakly interconnected._