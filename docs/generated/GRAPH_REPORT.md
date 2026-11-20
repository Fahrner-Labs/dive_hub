# Graph Report - dive_hub  (2026-11-20)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 10071 nodes · 30462 edges · 331 communities (206 shown, 125 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 543 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `63d90f75`
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
- Community 218
- Community 219
- Community 220
- Community 221
- Community 222
- Community 226

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
- `AuthOptions` --references--> `Db`  [EXTRACTED]
  apps/server/src/auth/auth.ts → apps/server/src/db/client.ts

## Import Cycles
- None detected.

## Communities (331 total, 125 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (246): ENGINE_VERSION, apikey, user, buddySuggestion, cylinder, dive, duplicateCandidate, jointDive (+238 more)

### Community 1 - "Community 1"
Cohesion: 0.02
Nodes (176): createServer(), createAuth(), auth, loadAuthSecret(), acquireAppLock(), BACKUP_EXTENSION, BackupManifest, BackupPart (+168 more)

### Community 2 - "Community 2"
Cohesion: 0.04
Nodes (185): DisplaySettings(), DeleteUserDialog(), Invite(), LinkToPassOn(), SiteDataPanel(), UserRow(), Accesses(), AccessLog() (+177 more)

### Community 3 - "Community 3"
Cohesion: 0.03
Nodes (184): cylinderCatalogueQuery(), CylinderFields(), MATERIALS, ADR-0034, ADR-0045, useCatalogueName(), useCylinderProblem(), DiveEditForm() (+176 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (147): EMPTY_SITE_IMPORT_COUNTS, ImportArea, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053 (+139 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (141): AdminSettings(), Invitations(), ProviderSiteData(), ADR-0012, ADR-0013, ADR-0030, Users(), AiAccessSetting() (+133 more)

### Community 6 - "Community 6"
Cohesion: 0.04
Nodes (78): Formats, SyncLine(), formatSyncTime(), GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard() (+70 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (147): Queryable, continuationAppend, OVERRIDABLE_FIELDS, OverridableField, participant, sampleSeries, renumber(), renumberStatement() (+139 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (145): allButDive(), AnyLogbookCheck, CandidateView, continuationCandidateQuery(), ContinuationCandidateView, deletedDivesOn(), DeletedDivesPageView, deletedDivesQuery() (+137 more)

### Community 9 - "Community 9"
Cohesion: 0.02
Nodes (134): CHANNELS, isRule(), read(), ADR-0036, hashPassword(), account, accountRelations, rateLimit (+126 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (118): CentreService, CentreRouteDeps, LEAD_PLACEMENTS, PARTICIPANT_ROLES, WEIGHTING_FELT, DiverService, downsampleMinMax(), waterMismatch() (+110 more)

### Community 11 - "Community 11"
Cohesion: 0.02
Nodes (109): aiAccessSettingQuery(), BackupRunView, backupSettingsQuery(), BackupSettingsView, backupsQuery(), SiteDataView, ADR-0030, CertificationView (+101 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (20): AppContainer, KeystoreTokenStore, PreferencesInstanceUrlStore, UserCacheStore, MainViewModel, UiState, LoadingsUiState, LoadingsViewModel (+12 more)

### Community 13 - "Community 13"
Cohesion: 0.03
Nodes (116): certificationsQuery(), keys, BuddyView, connectionsQuery(), ConnectionView, DiveImportMode, DiveImportPreview, diveImportPreviewQuery() (+108 more)

### Community 14 - "Community 14"
Cohesion: 0.04
Nodes (107): assessmentQuery(), AssessmentView, samplesQuery(), ConflictChoices, ConflictFieldView, AsideItem(), AssessmentContext, AssessmentPanel() (+99 more)

### Community 15 - "Community 15"
Cohesion: 0.03
Nodes (96): ChangePassword(), SecuritySettings(), Sessions(), useDescribeAgent(), AiAccessLogEntry, AiAccessView, authClient, invitationLinkQuery() (+88 more)

### Community 16 - "Community 16"
Cohesion: 0.04
Nodes (105): ComputerChoice, DiveImportMode, DiveKind, importOriginal, UtcOffsetSource, ProviderCylinder, siteRef(), PlacedTime (+97 more)

### Community 17 - "Community 17"
Cohesion: 0.04
Nodes (93): DIVE_IMPORT_MODES, DIVE_KINDS, UTC_OFFSET_SOURCES, DiveSummaryFields, SummaryRow, ADR-0041, ADR-0053, ADR-0056 (+85 more)

### Community 18 - "Community 18"
Cohesion: 0.03
Nodes (88): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+80 more)

### Community 19 - "Community 19"
Cohesion: 0.05
Nodes (91): buildApp(), assessmentRoutes(), SessionUser, authHandler(), authHeaders(), fastify, FastifyRequest, forwardCookies() (+83 more)

### Community 20 - "Community 20"
Cohesion: 0.05
Nodes (91): label(), AcceptedBuddySuggestionView, BuddySuggestionOverlapView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView, candidatesQuery(), deletedDivesTotal() (+83 more)

### Community 21 - "Community 21"
Cohesion: 0.03
Nodes (76): connection, diveLease, importJob, providerConflict, push, pushRun, waitingPush, kept() (+68 more)

### Community 22 - "Community 22"
Cohesion: 0.04
Nodes (86): logbookCheckAnswer, logbookCheckDiveAnswer, agreeInDepthAndDuration(), CheckedDive, DeletableDive, findChecks(), followsSoon(), inChains() (+78 more)

### Community 23 - "Community 23"
Cohesion: 0.03
Nodes (72): createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities(), ADR-0062 (+64 more)

### Community 24 - "Community 24"
Cohesion: 0.04
Nodes (48): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+40 more)

### Community 25 - "Community 25"
Cohesion: 0.04
Nodes (72): providerGoneDive, open(), AutomationSettings, createConnectionService(), checkAutomation(), checkFollowNumbers(), expiryOf(), onTurn() (+64 more)

### Community 26 - "Community 26"
Cohesion: 0.05
Nodes (70): accountListEntry, diver, diverExternalId, providerLease, tidyName(), DiverDetails, Caller, foldName() (+62 more)

### Community 27 - "Community 27"
Cohesion: 0.06
Nodes (45): DiveBody(), DiveDetailRoute(), DiveDetailScreen(), errorText(), FactRow(), Facts(), Loading(), NeighbourButton() (+37 more)

### Community 28 - "Community 28"
Cohesion: 0.04
Nodes (21): ApiDiverBodyWeightsGet200ResponseItem, ApiDiveSitesGet200Response, ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting (+13 more)

### Community 29 - "Community 29"
Cohesion: 0.05
Nodes (70): divesQuery(), DiveSummary, LogbookPage, PAGE_SIZE, ProblemCode, siteQuery(), Buddies(), COUNT_OF (+62 more)

### Community 30 - "Community 30"
Cohesion: 0.06
Nodes (70): diveFingerprint, PushLeftOut, waitingOf(), currentRemote(), PushRow, ADR-0024, isExporting(), Leave (+62 more)

### Community 31 - "Community 31"
Cohesion: 0.05
Nodes (23): LogbookViewModel, ApiDivesGet200Response, ApiDivesGet200ResponseCounts, ApiDivesGet200ResponseTotals, DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewSac, DiveViewSite (+15 more)

### Community 32 - "Community 32"
Cohesion: 0.04
Nodes (64): AssessmentService, AssessmentView, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView() (+56 more)

### Community 33 - "Community 33"
Cohesion: 0.06
Nodes (67): SENT_KEYS, diveModeOfSsi(), keepTraining(), ssiDiveType(), ssiInteger(), ADR-0057, asNumber(), asText() (+59 more)

### Community 34 - "Community 34"
Cohesion: 0.05
Nodes (11): AndroidDiveStore, DiveStore, toBoolean(), toLong(), toModel(), BodyWeight, DiveSite, Recording (+3 more)

### Community 35 - "Community 35"
Cohesion: 0.04
Nodes (64): pushRunItem, PushRunLeftOut, asksFor(), DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN (+56 more)

### Community 36 - "Community 36"
Cohesion: 0.07
Nodes (11): DiveDetailScreenshotTest, DiveViewFromRecording, DiveViewFromRecordingStartsAt, DiveViewParticipantsItem, DiveViewRecordingsItemDevice, DiveViewValues, DiveViewValuesStartsAt, DiveViewWaterMismatch (+3 more)

### Community 37 - "Community 37"
Cohesion: 0.05
Nodes (60): BackupRouteDeps, BACKUP_RUN_TASK, BackupService, backupTask(), ADR-0069, CertificationService, PurgeService, EXPIRE_WAITING_IMPORTS_TASK (+52 more)

### Community 38 - "Community 38"
Cohesion: 0.04
Nodes (56): diverManagement, equipmentItem, managedDiverIds(), managesDiveDiver(), ADR-0016, CandidateError, CandidateRow, createCandidateService() (+48 more)

### Community 39 - "Community 39"
Cohesion: 0.04
Nodes (49): ParticipantRole, Direction, DiveExport, DiveListing, FIND_BY, FindBy, Operation, OPERATIONS (+41 more)

### Community 40 - "Community 40"
Cohesion: 0.04
Nodes (63): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, Services, DiveProfileViewSchema (+55 more)

### Community 41 - "Community 41"
Cohesion: 0.04
Nodes (62): ADR-0025, WaterMismatch, Change(), Changes(), historyRoute, Made(), REVISION_CAUSES, PositionSchema (+54 more)

### Community 42 - "Community 42"
Cohesion: 0.06
Nodes (60): ConnectionRow, WaitingPushOptions, createImport(), startImport(), audienceOfConnection(), audienceOfDive(), audienceOfDiver(), emitHint() (+52 more)

### Community 43 - "Community 43"
Cohesion: 0.06
Nodes (20): drawBand(), ProfileSketch(), DiveProfileView, LogbookPaging, AscentBand, SketchCurve, SketchGeometry, sketchGeometry() (+12 more)

### Community 44 - "Community 44"
Cohesion: 0.06
Nodes (49): errorMessage(), failureOf(), placeFile(), placeFiles(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites() (+41 more)

### Community 45 - "Community 45"
Cohesion: 0.06
Nodes (57): BottomTimeInput, Conditions, GasMix, GasRule, AIR, DEFAULT_CONDITIONS, LoadingDive, N2_REPLAY_WINDOW_HOURS (+49 more)

### Community 46 - "Community 46"
Cohesion: 0.07
Nodes (58): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+50 more)

### Community 47 - "Community 47"
Cohesion: 0.06
Nodes (32): DiveDetailText, diveLocalTime(), offsetId(), timeZoneNote(), TimeZoneOrigin, DEVICE, NEARBY, POSITION (+24 more)

### Community 48 - "Community 48"
Cohesion: 0.07
Nodes (60): Answer, answer(), baseFrom(), BodyWeight, Confidence, high, low, medium (+52 more)

### Community 49 - "Community 49"
Cohesion: 0.09
Nodes (31): ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits, ambientPressureBar(), bestMixPercent(), Conditions (+23 more)

### Community 50 - "Community 50"
Cohesion: 0.06
Nodes (49): APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), AppLockProbe, probeAppLock() (+41 more)

### Community 51 - "Community 51"
Cohesion: 0.06
Nodes (51): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, answer(), baseFrom(), cylindersAtReserve(), CylinderTerm (+43 more)

### Community 52 - "Community 52"
Cohesion: 0.04
Nodes (50): Sac, SAC_MISSING, SacMissing, sacOfDive(), SacValues, sacValues(), ADR-0033, ADR-0045 (+42 more)

### Community 53 - "Community 53"
Cohesion: 0.11
Nodes (27): Body, Hold, Offline, RefreshData, refreshDeletedJson(), refreshDetailJson(), refreshDetailsPage(), refreshDiveId() (+19 more)

### Community 54 - "Community 54"
Cohesion: 0.07
Nodes (48): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+40 more)

### Community 55 - "Community 55"
Cohesion: 0.07
Nodes (53): BACKUP_FORMAT_VERSION, BackupArchiveError, BackupEncryption, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32Encode() (+45 more)

### Community 56 - "Community 56"
Cohesion: 0.05
Nodes (47): diveSiteExternalId, providerSiteData, createSsiClient(), ssiSiteRow(), Card, DEEP, OWD, Report (+39 more)

### Community 57 - "Community 57"
Cohesion: 0.10
Nodes (7): LeadEstimateViewModelTest, Cylinder, Dive, Diver, DiveDetailsPage, toStore(), ApiClientTest

### Community 58 - "Community 58"
Cohesion: 0.05
Nodes (50): ADR-0036, headers, ADR-0030, ADR-0038, ADR-0048, ADR-0062, AiAccess, assessedDive() (+42 more)

### Community 59 - "Community 59"
Cohesion: 0.09
Nodes (27): BottomTime, bottomTime(), BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen (+19 more)

### Community 61 - "Community 61"
Cohesion: 0.06
Nodes (34): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+26 more)

### Community 62 - "Community 62"
Cohesion: 0.07
Nodes (13): ApiToolsBottomTimeGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseLimits, ApiToolsBottomTimeGet200ResponseResidualN2, ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue (+5 more)

### Community 63 - "Community 63"
Cohesion: 0.11
Nodes (11): SyncCheckpoint, Complete, SessionEnded, Stopped, SyncEngine, SyncPhase, Details, Meta (+3 more)

### Community 64 - "Community 64"
Cohesion: 0.14
Nodes (23): Body, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage(), diverJson(), diveStartsAtOf() (+15 more)

### Community 65 - "Community 65"
Cohesion: 0.05
Nodes (40): CYLINDER_MATERIALS, SUIT_TYPES, SUIT_UNDERGARMENTS, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053 (+32 more)

### Community 66 - "Community 66"
Cohesion: 0.07
Nodes (43): diversOf(), SiteRow, Cursor, cursorAt(), DateOnly(), Detail, diversAsked(), excerpt() (+35 more)

### Community 67 - "Community 67"
Cohesion: 0.08
Nodes (23): AvatarCircle(), DiveRow(), Error(), Fact(), Filters(), Loading(), LogbookList(), LogbookRoute() (+15 more)

### Community 68 - "Community 68"
Cohesion: 0.06
Nodes (40): Delivered, buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open(), saveAndReadBack() (+32 more)

### Community 69 - "Community 69"
Cohesion: 0.07
Nodes (37): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+29 more)

### Community 70 - "Community 70"
Cohesion: 0.06
Nodes (40): @modelcontextprotocol/server@^2.3.1, aiAccessLog, aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, AiAccessScope, KeyRow (+32 more)

### Community 71 - "Community 71"
Cohesion: 0.08
Nodes (32): diveWith(), ADR-0028, ADR-0043, buddySite(), Dive, OTHER, resetSource(), ADR-0012 (+24 more)

### Community 72 - "Community 72"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 73 - "Community 73"
Cohesion: 0.08
Nodes (9): ApiClient, ApiErrorBody, DiveRepository, neighboursPath(), decodeList(), LogbookRepository, ProviderDirectory, providerDirectory() (+1 more)

### Community 74 - "Community 74"
Cohesion: 0.12
Nodes (8): FakeCache, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore, UserScopedStore

### Community 75 - "Community 75"
Cohesion: 0.06
Nodes (41): ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, seriesOf(), minute(), minutes() (+33 more)

### Community 76 - "Community 76"
Cohesion: 0.09
Nodes (22): ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseCns, ApiDiversIdLoadingsGet200ResponseN2, ApiDiversIdLoadingsGet200ResponseNoFly, ApiDiversIdLoadingsGet200ResponseNoFlySource, cnsDecayed(), Estimate (+14 more)

### Community 77 - "Community 77"
Cohesion: 0.09
Nodes (33): BottomTimeQuery, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033, ADR-0071, DEFAULT_PPO2 (+25 more)

### Community 78 - "Community 78"
Cohesion: 0.08
Nodes (38): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+30 more)

### Community 79 - "Community 79"
Cohesion: 0.08
Nodes (33): ImportErrorCode, ImportFound, ImportOutcome, ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS, eachFile() (+25 more)

### Community 81 - "Community 81"
Cohesion: 0.06
Nodes (30): headers, ADR-0035, diveRow(), rowOf(), ADR-0026, ADR-0048, aiAccessReady(), askMcp() (+22 more)

### Community 82 - "Community 82"
Cohesion: 0.05
Nodes (36): AREA_OF, areaOf(), areas, capture(), findings, headers, ADR-0020, ADR-0021 (+28 more)

### Community 83 - "Community 83"
Cohesion: 0.12
Nodes (9): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), GasPlanInputTest (+1 more)

### Community 84 - "Community 84"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 85 - "Community 85"
Cohesion: 0.10
Nodes (9): Connected, Hint, HintFrame, LiveEvent, LiveHint, LiveHints, SseLiveHints, SyncRefresher (+1 more)

### Community 86 - "Community 86"
Cohesion: 0.08
Nodes (31): rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject(), MAX_CARD_IMAGE_BYTES, PNG_MAGIC (+23 more)

### Community 87 - "Community 87"
Cohesion: 0.10
Nodes (27): equipmentDueQuery(), EquipmentDueNotice(), ADR-0034, avatarTone(), initials(), dismissDue(), dismissedDueDigest(), dueCounts() (+19 more)

### Community 88 - "Community 88"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 89 - "Community 89"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 90 - "Community 90"
Cohesion: 0.11
Nodes (11): ApiDivesGet200ResponseDivesItem, ApiDivesGet200ResponseDivesItemGasesItem, ApiDivesGet200ResponseDivesItemParticipantsItem, ApiDivesGet200ResponseDivesItemSite, ApiDivesGet200ResponseMonthsItem, localMonthOf(), LogbookMonth, monthGroups() (+3 more)

### Community 91 - "Community 91"
Cohesion: 0.07
Nodes (28): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError (+20 more)

### Community 92 - "Community 92"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 93 - "Community 93"
Cohesion: 0.13
Nodes (6): LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200ResponseMissingItem, ApiDiversIdLoadingsGet200ResponseOtu, missingOf()

### Community 94 - "Community 94"
Cohesion: 0.13
Nodes (34): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+26 more)

### Community 95 - "Community 95"
Cohesion: 0.09
Nodes (33): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+25 more)

### Community 96 - "Community 96"
Cohesion: 0.12
Nodes (30): DiveOrigin, REFERENCE_PREFIX, Series, SsiLogbook, asBool(), DIVE_ROW, SsiValueType, ADR-0027 (+22 more)

### Community 97 - "Community 97"
Cohesion: 0.07
Nodes (26): adapter, crossCheck(), decodeOfficial(), privateDir, ownersDiveParts(), through(), ADR-0063, FIT_EPOCH_MS (+18 more)

### Community 98 - "Community 98"
Cohesion: 0.09
Nodes (27): CylinderMaterial, aluminium, carbon, steel, EstimateDiveCylinder, dayNumber(), enteredDecoFromNdl(), minNdlSeconds() (+19 more)

### Community 99 - "Community 99"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 100 - "Community 100"
Cohesion: 0.13
Nodes (31): importsQuery(), Logbook(), ImportContext, ImportFilesButton(), ImportHistory(), ImportList(), ImportNotices(), ImportPanel() (+23 more)

### Community 101 - "Community 101"
Cohesion: 0.12
Nodes (30): diverBodyWeight, lead, LeadPlacement, SuitType, SuitUndergarment, WeightingFelt, DiveRow, LeadEntry (+22 more)

### Community 102 - "Community 102"
Cohesion: 0.09
Nodes (29): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+21 more)

### Community 103 - "Community 103"
Cohesion: 0.09
Nodes (27): AL80, asEstimateInput(), EstimateDiveCylinder, evaluateLeadVectorCase(), LEAD_CASES, LEAD_VECTOR_KERNEL, leadInput(), leadVectorFile() (+19 more)

### Community 105 - "Community 105"
Cohesion: 0.09
Nodes (30): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+22 more)

### Community 106 - "Community 106"
Cohesion: 0.08
Nodes (28): Position, running(), SiteImportArea, siteImportsQuery(), SiteImportView, siteRevisionsQuery(), SiteRevisionView, ADR-0020 (+20 more)

### Community 107 - "Community 107"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 109 - "Community 109"
Cohesion: 0.10
Nodes (24): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearPushRuns(), clearQueue() (+16 more)

### Community 110 - "Community 110"
Cohesion: 0.09
Nodes (28): applyHints(), LiveArrivals, Page, useLiveArrivals(), bump(), changedListeners, ChannelMessage, healthListeners (+20 more)

### Community 111 - "Community 111"
Cohesion: 0.12
Nodes (11): MainActivity, DiveDetailViewModel, DiveHubApp(), ErrorText(), InstanceForm(), Loading(), PasswordField(), PrimaryButton() (+3 more)

### Community 112 - "Community 112"
Cohesion: 0.14
Nodes (26): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+18 more)

### Community 113 - "Community 113"
Cohesion: 0.11
Nodes (23): SsiRecord, contextOf(), FakeSsi, BuddySaved, access(), client, out, pause (+15 more)

### Community 114 - "Community 114"
Cohesion: 0.27
Nodes (7): leadAnswer(), EstimateCylinder, PlannedConditions, Suit, assemblePersonalizedSources(), leadEstimate(), PersonalizedToolsTest

### Community 115 - "Community 115"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 116 - "Community 116"
Cohesion: 0.10
Nodes (25): EVIDENCE, Evidence, FindingValues, NOT_COVERED_DIVE_MODES, RULE_IDS, RuleId, SEVERITIES, Severity (+17 more)

### Community 117 - "Community 117"
Cohesion: 0.09
Nodes (23): computerEventOf(), ADR-0036, computerEventFromFit(), COMPUTER_EVENT, computerEventFromSuunto(), DECO_MODEL, decoModelFromSuunto, DIVE_MODE (+15 more)

### Community 118 - "Community 118"
Cohesion: 0.10
Nodes (26): globalSetup(), ADR-0023, accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks() (+18 more)

### Community 119 - "Community 119"
Cohesion: 0.19
Nodes (4): GasPlanScreenshotTest, GasPlanToolDiver, GasPlanToolInput, GasPlanToolLevel

### Community 120 - "Community 120"
Cohesion: 0.11
Nodes (7): ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport

### Community 121 - "Community 121"
Cohesion: 0.11
Nodes (19): SsiClient, ClearChecks, TwowayChecks, apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks() (+11 more)

### Community 122 - "Community 122"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 123 - "Community 123"
Cohesion: 0.14
Nodes (24): NoFly, getDiverLoadings, DEFAULT_GF_HIGH, residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows(), DiveRow (+16 more)

### Community 124 - "Community 124"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 125 - "Community 125"
Cohesion: 0.11
Nodes (19): ProblemCode, ResponseState, whenResponseGone(), StoredBlob, PlacedArchive, ANSWER, looksLikeRecipientPrivateKey(), PlacedArchive (+11 more)

### Community 126 - "Community 126"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 127 - "Community 127"
Cohesion: 0.14
Nodes (23): planningSac(), PlanningSacDive, PlanningSacSource, default, logbook, jsRound(), powInt(), roundTo() (+15 more)

### Community 128 - "Community 128"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 129 - "Community 129"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 130 - "Community 130"
Cohesion: 0.09
Nodes (4): DiveHubApplication, SyncWorker, WorkManagerPeriodicSync, PeriodicSyncScheduler

### Community 131 - "Community 131"
Cohesion: 0.12
Nodes (7): LeadAnswer, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseLeftOut, ApiDiversIdLeadEstimateGet200ResponseRange, ApiDiversIdLeadEstimateGet200ResponseReserve, ApiDiversIdLeadEstimateGet200ResponseWeightCheck

### Community 132 - "Community 132"
Cohesion: 0.12
Nodes (21): AssessmentError, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), requireManagedDive(), same() (+13 more)

### Community 133 - "Community 133"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 134 - "Community 134"
Cohesion: 0.13
Nodes (6): DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, WaitingPushView

### Community 135 - "Community 135"
Cohesion: 0.13
Nodes (21): equipmentUse, DiveGear, ensureItemForDevice(), GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow (+13 more)

### Community 136 - "Community 136"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 137 - "Community 137"
Cohesion: 0.09
Nodes (23): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+15 more)

### Community 138 - "Community 138"
Cohesion: 0.15
Nodes (22): managementOf(), EquipmentError, checkCost(), checkDates(), checkWindow(), createEquipmentService(), assemble(), checkResets() (+14 more)

### Community 139 - "Community 139"
Cohesion: 0.14
Nodes (18): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+10 more)

### Community 140 - "Community 140"
Cohesion: 0.09
Nodes (17): parseHint(), call(), createSite(), Method, openStream(), Stream, ADR-0066, diveAt() (+9 more)

### Community 141 - "Community 141"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 142 - "Community 142"
Cohesion: 0.18
Nodes (3): LeadEstimateUiState, LeadEstimateViewModel, PersonalizedSources

### Community 143 - "Community 143"
Cohesion: 0.14
Nodes (17): BottomTime, bottomTime(), Limit, ADR-0032, ADR-0033, ADR-0071, MAX_DEPTH_M, NOAA_ROWS (+9 more)

### Community 144 - "Community 144"
Cohesion: 0.26
Nodes (18): ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres(), isHypoxic(), metresPerBar() (+10 more)

### Community 145 - "Community 145"
Cohesion: 0.12
Nodes (18): CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043, CreateBody (+10 more)

### Community 146 - "Community 146"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 147 - "Community 147"
Cohesion: 0.19
Nodes (11): LeadInputTest, SuitType, drysuit, none, semidry, skin, wetsuit, SuitUndergarment (+3 more)

### Community 148 - "Community 148"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 149 - "Community 149"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 150 - "Community 150"
Cohesion: 0.13
Nodes (17): siteImportStatus, SiteImportRouteDeps, StartBody, STATUS, ADR-0021, SiteImportError, SiteImportService, Area (+9 more)

### Community 151 - "Community 151"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 152 - "Community 152"
Cohesion: 0.13
Nodes (18): defineTool(), Id(), Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), HINT (+10 more)

### Community 153 - "Community 153"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 154 - "Community 154"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 155 - "Community 155"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 156 - "Community 156"
Cohesion: 0.13
Nodes (17): SUGGESTION_STATUSES, DiveProfileView, AcceptBody, STATUS, SuggestionRouteDeps, ADR-0070, SuggestionService, DiverRef (+9 more)

### Community 158 - "Community 158"
Cohesion: 0.17
Nodes (6): Answers, number(), ToolsViewModel, depthFromDisplay(), pressureFromDisplay(), ToolRanges

### Community 159 - "Community 159"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 160 - "Community 160"
Cohesion: 0.18
Nodes (15): RecordingSummary, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput, round3() (+7 more)

### Community 161 - "Community 161"
Cohesion: 0.12
Nodes (16): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+8 more)

### Community 162 - "Community 162"
Cohesion: 0.14
Nodes (13): nearest(), perMinute(), Series, summarize(), ChannelSeries, RecordedGas, Tank, TiedCylinder (+5 more)

### Community 164 - "Community 164"
Cohesion: 0.20
Nodes (3): FakeInstances, FakeTokens, SyncAuthTest

### Community 165 - "Community 165"
Cohesion: 0.15
Nodes (10): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, ADR-0068 (+2 more)

### Community 166 - "Community 166"
Cohesion: 0.17
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 167 - "Community 167"
Cohesion: 0.19
Nodes (4): ApiMeGet200Response, ApiMeGet200ResponseOwnDiver, ApiMeGet200ResponsePreferences, UserView

### Community 168 - "Community 168"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 169 - "Community 169"
Cohesion: 0.16
Nodes (13): CIRCUIT, circuitFromFit, DECO_MODEL, decoModelFromFit, DIVE_ALERT, DIVE_MODE, diveModeFromFit, ADR-0015 (+5 more)

### Community 170 - "Community 170"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 173 - "Community 173"
Cohesion: 0.16
Nodes (14): serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, DueEntry, ScheduleView, equipmentList(), EquipmentListFilter (+6 more)

### Community 174 - "Community 174"
Cohesion: 0.13
Nodes (10): EGYPT, ExternalId, HOUSE_REEF, Method, Position, Revision, Site, SiteImport (+2 more)

### Community 175 - "Community 175"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 176 - "Community 176"
Cohesion: 0.18
Nodes (11): addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, relations(), TimelineBar, TimelineBarGeometry, TimelineGapGeometry, TimelineGeometry (+3 more)

### Community 177 - "Community 177"
Cohesion: 0.13
Nodes (6): FakeBroadcastChannel, FakeEventSource, Listener, locks, start(), ADR-0066

### Community 178 - "Community 178"
Cohesion: 0.13
Nodes (14): name, needs, runs-on, timeout-minutes, concurrency, cancel-in-progress, group, env (+6 more)

### Community 179 - "Community 179"
Cohesion: 0.20
Nodes (12): flag(), idText(), isObject(), numberOrNull(), readSsiSite(), SsiSiteValues, visibilityFor(), WATER_OF_BOW (+4 more)

### Community 180 - "Community 180"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 181 - "Community 181"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 182 - "Community 182"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 183 - "Community 183"
Cohesion: 0.26
Nodes (3): DiverView, DiverViewCertifications, DiverViewCertificationsNewest

### Community 184 - "Community 184"
Cohesion: 0.20
Nodes (8): ADR-0063, ADR-0040, ADR-0047, diveNumber(), expireSsiTokens(), headers, openFilters(), signInSsi()

### Community 185 - "Community 185"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 186 - "Community 186"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 187 - "Community 187"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 188 - "Community 188"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 189 - "Community 189"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 190 - "Community 190"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 191 - "Community 191"
Cohesion: 0.29
Nodes (4): DiveViewRecordingsItemSummary, DiveViewRecordingsItemSummaryGasesItem, summaryOf(), summaryOf()

### Community 192 - "Community 192"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 193 - "Community 193"
Cohesion: 0.22
Nodes (9): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, ADR-0076, MODULE_IMPORTS, moduleRules (+1 more)

### Community 194 - "Community 194"
Cohesion: 0.20
Nodes (10): verify, prerelease, sha, tag, versionCode, versionName, name, outputs (+2 more)

### Community 195 - "Community 195"
Cohesion: 0.20
Nodes (10): with, cache, distribution, fetch-depth, if-no-files-found, java-version, name, path (+2 more)

### Community 196 - "Community 196"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 197 - "Community 197"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 198 - "Community 198"
Cohesion: 0.28
Nodes (8): itemIds(), ADR-0026, ADR-0034, Usage, UsageDive, usageMap(), usageOf(), UsageRow

### Community 200 - "Community 200"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 201 - "Community 201"
Cohesion: 0.22
Nodes (9): version, on, push, workflow_dispatch, tags, description, required, type (+1 more)

### Community 202 - "Community 202"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 203 - "Community 203"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 205 - "Community 205"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 206 - "Community 206"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 209 - "Community 209"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 210 - "Community 210"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 212 - "Community 212"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 213 - "Community 213"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 214 - "Community 214"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 215 - "Community 215"
Cohesion: 0.40
Nodes (4): TAGS, ADR-0023, { stdout, stderr }, total

### Community 218 - "Community 218"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 219 - "Community 219"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 220 - "Community 220"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1277 isolated node(s):** `Reserve`, `RockBottom`, `Off`, `SuitThickness`, `ADR-0072` (+1272 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3507 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **125 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 0` to `Community 1`, `Community 4`, `Community 7`, `Community 135`, `Community 137`, `Community 9`, `Community 10`, `Community 16`, `Community 17`, `Community 18`, `Community 21`, `Community 22`, `Community 23`, `Community 25`, `Community 26`, `Community 30`, `Community 35`, `Community 37`, `Community 38`, `Community 40`, `Community 42`, `Community 173`, `Community 56`, `Community 66`, `Community 198`, `Community 70`, `Community 77`, `Community 79`, `Community 91`, `Community 95`, `Community 97`, `Community 101`, `Community 115`, `Community 123`?**
  _High betweenness centrality (0.019) - this node is a cross-community bridge._
- **What connects `Reserve`, `RockBottom`, `Off` to the rest of the system?**
  _1277 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.01995488460871074 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 15` to `Community 2`, `Community 3`, `Community 100`, `Community 5`, `Community 8`, `Community 105`, `Community 106`, `Community 11`, `Community 13`, `Community 14`, `Community 110`, `Community 177`, `Community 20`, `Community 149`, `Community 87`, `Community 29`?**
  _High betweenness centrality (0.016) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.017569862590705573 - nodes in this community are weakly interconnected._
- **Why does `DiveStore` connect `Community 34` to `Community 64`, `Community 98`, `Community 164`, `Community 104`, `Community 12`, `Community 172`, `Community 142`, `Community 114`, `Community 83`, `Community 53`, `Community 57`, `Community 27`, `Community 127`, `Community 63`, `Community 158`, `Community 31`?**
  _High betweenness centrality (0.012) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.043593911629968965 - nodes in this community are weakly interconnected._