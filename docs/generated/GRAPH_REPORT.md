# Graph Report - dive_hub  (2026-11-19)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 9768 nodes · 29568 edges · 323 communities (207 shown, 116 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 522 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `731099dd`
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
- Community 213
- Community 214
- Community 215
- Community 216
- Community 217
- Community 218
- Community 219
- Community 222

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
- `Removal` --references--> `ProviderServiceError`  [EXTRACTED]
  apps/server/src/providers/push/push-service.ts → apps/server/src/providers/registry.ts
- `AuthOptions` --references--> `Db`  [EXTRACTED]
  apps/server/src/auth/auth.ts → apps/server/src/db/client.ts

## Import Cycles
- None detected.

## Communities (323 total, 116 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (205): user, diver, diveSiteExternalId, createSsiAdapter(), createSsiClient(), ssiSiteRow(), ADR-0013, ADR-0011 (+197 more)

### Community 1 - "Community 1"
Cohesion: 0.05
Nodes (195): DisplaySettings(), AdminSettings(), DeleteUserDialog(), Invitations(), Invite(), LinkToPassOn(), ProviderSiteData(), SiteDataPanel() (+187 more)

### Community 2 - "Community 2"
Cohesion: 0.02
Nodes (192): cylinderCatalogueQuery(), equipmentOfDiverQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery() (+184 more)

### Community 3 - "Community 3"
Cohesion: 0.02
Nodes (139): ChangePassword(), SecuritySettings(), Sessions(), useDescribeAgent(), AiAccessLogEntry, AiAccessView, authClient, invitationLinkQuery() (+131 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (144): allButDive(), AnyLogbookCheck, CandidateView, continuationCandidateQuery(), ContinuationCandidateView, deletedDivesOn(), DeletedDivesPageView, deletedDivesQuery() (+136 more)

### Community 5 - "Community 5"
Cohesion: 0.02
Nodes (110): createServer(), createAuth(), loadAuthSecret(), acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS (+102 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (132): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+124 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (108): LEAD_PLACEMENTS, WEIGHTING_FELT, downsampleMinMax(), DiveCertifications, LogbookCheckService, AppendDeps, AppendOutcome, AppendService (+100 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (102): LoadingsView, isUnauthorized(), ExternalIdView, App(), BackupsPage, CentrePage, CentresPage, currentRoute() (+94 more)

### Community 9 - "Community 9"
Cohesion: 0.02
Nodes (117): hashPassword(), account, accountRelations, apikey, rateLimit, session, sessionRelations, ADR-0011 (+109 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (91): auth, BackupServiceDeps, createServices(), ServiceParts, ADR-0027, ADR-0030, ADR-0061, ADR-0063 (+83 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (93): equipmentDueQuery(), cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043 (+85 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (115): buddiesForQuery(), buddiesQuery(), BuddyView, ConflictChoices, ConflictEntryView, ConflictFieldView, ConflictsOfConnection, ConnectionView (+107 more)

### Community 13 - "Community 13"
Cohesion: 0.02
Nodes (99): buddySuggestion, diveAssessment, jointDive, parseHint(), Assessment, FindingView, Row, ADR-0036 (+91 more)

### Community 14 - "Community 14"
Cohesion: 0.04
Nodes (103): CertificationService, CYLINDER_MATERIALS, DIVE_IMPORT_MODES, DIVE_KINDS, UTC_OFFSET_SOURCES, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036 (+95 more)

### Community 15 - "Community 15"
Cohesion: 0.04
Nodes (99): label(), Me, AcceptedBuddySuggestionView, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView (+91 more)

### Community 16 - "Community 16"
Cohesion: 0.04
Nodes (101): divesQuery(), LogbookPage, PAGE_SIZE, connectionsQuery(), pushRunActive(), pushRunBody(), PushRunChoices, PushRunPreview (+93 more)

### Community 17 - "Community 17"
Cohesion: 0.03
Nodes (91): BACKUP_RUN_TASK, backupTask(), ADR-0069, connection, ConnectionRow, providerConflict, push, waitingPush (+83 more)

### Community 18 - "Community 18"
Cohesion: 0.04
Nodes (20): ApiDiverBodyWeightsGet200ResponseItem, PlanningSacSource, default, logbook, DiveStore, toBoolean(), toLong(), toModel() (+12 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (89): equipmentItem, serviceRecord, serviceRecordSchedule, serviceSchedule, managementOf(), addMonths(), CountDue, countReason() (+81 more)

### Community 20 - "Community 20"
Cohesion: 0.04
Nodes (48): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+40 more)

### Community 21 - "Community 21"
Cohesion: 0.05
Nodes (75): accountListEntry, diverExternalId, providerLease, tidyName(), ADR-0028, ADR-0043, ADR-0070, foldName() (+67 more)

### Community 22 - "Community 22"
Cohesion: 0.05
Nodes (72): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+64 more)

### Community 23 - "Community 23"
Cohesion: 0.08
Nodes (3): LoadingSource, limitWord(), ErrorCodes

### Community 24 - "Community 24"
Cohesion: 0.07
Nodes (69): Formats, dateTime(), GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult() (+61 more)

### Community 25 - "Community 25"
Cohesion: 0.04
Nodes (67): adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, AuthOptions, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, SessionUser, ADR-0011 (+59 more)

### Community 26 - "Community 26"
Cohesion: 0.06
Nodes (70): accountKey(), applyExternalId(), insertExternal(), askedAt(), AppendRow, channelsOf(), createContinuationService(), takeIn() (+62 more)

### Community 27 - "Community 27"
Cohesion: 0.04
Nodes (14): AppContainer, KeystoreTokenStore, PreferencesInstanceUrlStore, UserCacheStore, AndroidDiveStore, DiveHubApplication, SyncWorker, WorkManagerPeriodicSync (+6 more)

### Community 28 - "Community 28"
Cohesion: 0.06
Nodes (69): waitingOf(), close(), currentRemote(), PushRow, ADR-0024, Choice, isExporting(), referenceOf() (+61 more)

### Community 29 - "Community 29"
Cohesion: 0.04
Nodes (19): ApiMeGet200ResponseOwnDiver, ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting, ProviderSummaryData (+11 more)

### Community 30 - "Community 30"
Cohesion: 0.04
Nodes (64): CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite(), lock() (+56 more)

### Community 31 - "Community 31"
Cohesion: 0.05
Nodes (53): cylinder, device, dive, duplicateCandidate, ImportErrorCode, ImportFound, importJob, ImportOutcome (+45 more)

### Community 32 - "Community 32"
Cohesion: 0.05
Nodes (14): MainViewModel, UiState, LoadingsUiState, LoadingsViewModel, ApiMeGet200Response, ApiMeGet200ResponsePreferences, SignInEmailRequest, UserView (+6 more)

### Community 33 - "Community 33"
Cohesion: 0.05
Nodes (21): MainActivity, DiveDetailUiState, DiveDetailViewModel, LogbookUiState, LogbookViewModel, neighboursPath(), samplesPath(), DiveHubException (+13 more)

### Community 34 - "Community 34"
Cohesion: 0.04
Nodes (63): pushRunItem, PushRunLeftOut, DIVE_ONLY, DIVE_ONLY_PATTERN, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN (+55 more)

### Community 35 - "Community 35"
Cohesion: 0.05
Nodes (66): continuationAppend, recordingEvent, buildJoined(), partInput(), partsOfJoined(), rebuildJoined(), RecordingRow, ADR-0063 (+58 more)

### Community 36 - "Community 36"
Cohesion: 0.07
Nodes (58): diveSite, dataIdsOf(), settleSiteVisibility(), outgoingSite(), findOrMake(), SiteForSend, siteForSend(), ADR-0057 (+50 more)

### Community 37 - "Community 37"
Cohesion: 0.04
Nodes (48): ParticipantRole, Direction, DiveExport, DiveListing, FIND_BY, FindBy, Operation, OPERATIONS (+40 more)

### Community 38 - "Community 38"
Cohesion: 0.06
Nodes (40): DiveBody(), DiveDetailRoute(), DiveDetailScreen(), errorText(), FactRow(), Facts(), Loading(), NeighbourButton() (+32 more)

### Community 39 - "Community 39"
Cohesion: 0.06
Nodes (54): defineTool(), Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), getDiverLoadings, HINT (+46 more)

### Community 40 - "Community 40"
Cohesion: 0.08
Nodes (47): BottomTime, BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen, BottomTimeLimits (+39 more)

### Community 41 - "Community 41"
Cohesion: 0.07
Nodes (15): LogbookScreenshotTest, ApiDivesGet200Response, ApiDivesGet200ResponseCounts, ApiDivesGet200ResponseDivesItem, ApiDivesGet200ResponseDivesItemGasesItem, ApiDivesGet200ResponseDivesItemParticipantsItem, ApiDivesGet200ResponseDivesItemSite, ApiDivesGet200ResponseMonthsItem (+7 more)

### Community 42 - "Community 42"
Cohesion: 0.07
Nodes (63): Answer, answer(), baseFrom(), BodyWeight, Confidence, high, low, medium (+55 more)

### Community 43 - "Community 43"
Cohesion: 0.04
Nodes (53): AssessmentService, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView(), ADR-0036 (+45 more)

### Community 44 - "Community 44"
Cohesion: 0.06
Nodes (57): AiAccessScope, seesPositions(), checkedByRunTool, createMcpServer(), INSTRUCTIONS, MCP_SERVER_INFO, TOOLS, toolsFor() (+49 more)

### Community 45 - "Community 45"
Cohesion: 0.07
Nodes (60): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+52 more)

### Community 46 - "Community 46"
Cohesion: 0.07
Nodes (47): errorMessage(), looksLikeZip(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy() (+39 more)

### Community 47 - "Community 47"
Cohesion: 0.08
Nodes (17): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), number() (+9 more)

### Community 48 - "Community 48"
Cohesion: 0.09
Nodes (56): buildApp(), assessmentRoutes(), AI_ACCESS_NAME_MAX, authHandler(), authHeaders(), forwardCookies(), isAdmin(), requireAdmin() (+48 more)

### Community 49 - "Community 49"
Cohesion: 0.06
Nodes (44): SsiClient, SsiRecord, contextOf(), buildDeleteRecord(), buildUpdateRecord(), DiveForSsi, writableRecord(), FakeSsi (+36 more)

### Community 50 - "Community 50"
Cohesion: 0.07
Nodes (58): ComputerChoice, DiveImportMode, DiveKind, importOriginal, providerSiteData, siteRef(), kindOfMode(), deviceDiver() (+50 more)

### Community 51 - "Community 51"
Cohesion: 0.05
Nodes (54): LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, CylinderError, CylinderInput, fillCylindersFromRecording(), sameSeries(), setCylinders() (+46 more)

### Community 52 - "Community 52"
Cohesion: 0.07
Nodes (10): DiveDetailScreenshotTest, DiveViewFromRecording, DiveViewFromRecordingStartsAt, DiveViewRecordingsItemDevice, DiveViewValues, DiveViewValuesStartsAt, DiveViewWaterMismatch, NeighboursView (+2 more)

### Community 53 - "Community 53"
Cohesion: 0.08
Nodes (6): DiveRepositoryTest, counts(), LogbookFiltersTest, FakeInstances, FakeTokens, SyncAuthTest

### Community 54 - "Community 54"
Cohesion: 0.06
Nodes (50): EMPTY_SITE_IMPORT_COUNTS, ImportArea, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053 (+42 more)

### Community 55 - "Community 55"
Cohesion: 0.08
Nodes (26): Profile(), ProfileChart(), ProfileTable(), DiveViewRecordingsItem, DiveViewRecordingsItemSummarySurfaceBreaksItem, RecordingSamplesView, RecordingSamplesViewSeriesItem, computerRecordings() (+18 more)

### Community 56 - "Community 56"
Cohesion: 0.07
Nodes (12): DiveHubTheme(), LeadAnswer, LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseLeftOut (+4 more)

### Community 57 - "Community 57"
Cohesion: 0.05
Nodes (50): ADR-0036, headers, ADR-0030, ADR-0038, ADR-0048, ADR-0062, AiAccess, assessedDive() (+42 more)

### Community 58 - "Community 58"
Cohesion: 0.05
Nodes (50): BottomTimeInput, Conditions, GasMix, GasRule, LoadingDive, TissuesAfterDivesInput, NdlInput, AIR (+42 more)

### Community 59 - "Community 59"
Cohesion: 0.09
Nodes (10): Complete, SessionEnded, Stopped, SyncEngine, SyncPhase, Details, Meta, Profiles (+2 more)

### Community 60 - "Community 60"
Cohesion: 0.09
Nodes (48): Queryable, logbookCheckAnswer, logbookCheckDiveAnswer, keepApart(), keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration() (+40 more)

### Community 61 - "Community 61"
Cohesion: 0.06
Nodes (32): diveFingerprint, diveLease, pushRun, ExportingProvider, ProviderError, List, ADR-0040, createFakeHandover() (+24 more)

### Community 62 - "Community 62"
Cohesion: 0.06
Nodes (34): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+26 more)

### Community 63 - "Community 63"
Cohesion: 0.08
Nodes (48): BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32Encode(), bech32HrpExpand(), bech32Polymod() (+40 more)

### Community 64 - "Community 64"
Cohesion: 0.05
Nodes (43): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+35 more)

### Community 65 - "Community 65"
Cohesion: 0.07
Nodes (45): AIR, CNS_HALF_LIFE_MINUTES, cnsDecayed(), DEFAULT_CONDITIONS, N2_REPLAY_WINDOW_HOURS, N2_STALE_AFTER_HOURS, N2Loadings, N2LoadingsBase (+37 more)

### Community 66 - "Community 66"
Cohesion: 0.07
Nodes (43): PARTICIPANT_ROLES, siteImportStatus, PROBLEM_CODES, storedProblemCode(), ADR-0014, ADR-0027, oneOf(), refTo() (+35 more)

### Community 67 - "Community 67"
Cohesion: 0.07
Nodes (41): RecordingSummary, RecordingFacts, date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num() (+33 more)

### Community 68 - "Community 68"
Cohesion: 0.07
Nodes (43): Leave, Series, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+35 more)

### Community 69 - "Community 69"
Cohesion: 0.13
Nodes (24): Body, Hold, Offline, RefreshData, refreshDeletedJson(), refreshDetailJson(), refreshDiveListItem(), refreshDivePage() (+16 more)

### Community 70 - "Community 70"
Cohesion: 0.05
Nodes (40): SUIT_TYPES, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053, replyDiverError, STATUS (+32 more)

### Community 71 - "Community 71"
Cohesion: 0.07
Nodes (35): createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities(), providerError() (+27 more)

### Community 72 - "Community 72"
Cohesion: 0.06
Nodes (41): Delivered, DIVE_FIELDS, buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open() (+33 more)

### Community 73 - "Community 73"
Cohesion: 0.06
Nodes (44): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, Services, EquipmentService, RecordInput (+36 more)

### Community 74 - "Community 74"
Cohesion: 0.08
Nodes (44): WaitingPushOptions, audienceOfConnection(), audienceOfDive(), audienceOfDiver(), emitHint(), Hint, HINT_KINDS, HintAudience (+36 more)

### Community 75 - "Community 75"
Cohesion: 0.10
Nodes (38): BottomTime, bottomTime(), Limit, ADR-0032, ADR-0033, ADR-0071, ambientPressureBar(), bestMixPercent() (+30 more)

### Community 76 - "Community 76"
Cohesion: 0.11
Nodes (5): GasPlanScreenshotTest, ToolsScreenshotTest, GasPlanToolDiver, GasPlanToolInput, GasPlanToolLevel

### Community 77 - "Community 77"
Cohesion: 0.06
Nodes (36): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+28 more)

### Community 78 - "Community 78"
Cohesion: 0.08
Nodes (32): diveWith(), ADR-0028, ADR-0043, buddySite(), Dive, OTHER, resetSource(), ADR-0012 (+24 more)

### Community 79 - "Community 79"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 80 - "Community 80"
Cohesion: 0.06
Nodes (41): SUIT_UNDERGARMENTS, ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, seriesOf(), minute() (+33 more)

### Community 81 - "Community 81"
Cohesion: 0.09
Nodes (22): AvatarCircle(), DiveRow(), Error(), Fact(), Filters(), Loading(), LogbookList(), LogbookRoute() (+14 more)

### Community 83 - "Community 83"
Cohesion: 0.09
Nodes (16): LogbookPaging, AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint, SketchProfile, sketchScale() (+8 more)

### Community 84 - "Community 84"
Cohesion: 0.12
Nodes (8): FakeCache, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore, UserScopedStore

### Community 85 - "Community 85"
Cohesion: 0.15
Nodes (19): Body, diveListItem(), divePage(), diverJson(), Harness, json(), manyDives(), manySites() (+11 more)

### Community 86 - "Community 86"
Cohesion: 0.06
Nodes (32): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError (+24 more)

### Community 87 - "Community 87"
Cohesion: 0.07
Nodes (29): diverManagement, equipmentUse, sampleSeries, managesDiveDiver(), ADR-0016, CandidateError, CandidateRow, createCandidateService() (+21 more)

### Community 88 - "Community 88"
Cohesion: 0.09
Nodes (37): externalDiver(), mergeExternalDiver(), participations(), open(), AutomationSettings, createConnectionService(), checkAutomation(), checkFollowNumbers() (+29 more)

### Community 89 - "Community 89"
Cohesion: 0.06
Nodes (38): Change(), Changes(), historyRoute, Made(), PositionSchema, AdoptBody, Country, CreateBody (+30 more)

### Community 90 - "Community 90"
Cohesion: 0.06
Nodes (30): headers, ADR-0035, diveRow(), rowOf(), ADR-0026, ADR-0048, aiAccessReady(), askMcp() (+22 more)

### Community 91 - "Community 91"
Cohesion: 0.05
Nodes (36): AREA_OF, areaOf(), areas, capture(), findings, headers, ADR-0020, ADR-0021 (+28 more)

### Community 92 - "Community 92"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 93 - "Community 93"
Cohesion: 0.08
Nodes (36): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+28 more)

### Community 94 - "Community 94"
Cohesion: 0.11
Nodes (33): BACKUP_SKIPPED_DIRS, BackupDecryptionKey, encryptionKeyFingerprint(), openArchiveStreamingWith(), assertPgTool(), execFileAsync, pgCommand(), pgErrorDetail() (+25 more)

### Community 95 - "Community 95"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 96 - "Community 96"
Cohesion: 0.09
Nodes (35): ProviderCylinder, createConflictService(), loadWaiting(), stillDiffering(), fieldsOf(), isExists(), shownFields(), stillWaits() (+27 more)

### Community 97 - "Community 97"
Cohesion: 0.08
Nodes (36): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+28 more)

### Community 98 - "Community 98"
Cohesion: 0.12
Nodes (36): DEFAULT_SSI_URL, acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive() (+28 more)

### Community 99 - "Community 99"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 100 - "Community 100"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 101 - "Community 101"
Cohesion: 0.10
Nodes (29): dayNumber(), enteredDecoFromNdl(), minNdlSeconds(), NoFly, NoFlyReason, decompression, several, single (+21 more)

### Community 102 - "Community 102"
Cohesion: 0.09
Nodes (23): Tx, AppendUndo, alignedForMatching(), decideMatch(), depthsDisagree(), overlaps(), overlapWindow(), ChangeListener (+15 more)

### Community 103 - "Community 103"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 104 - "Community 104"
Cohesion: 0.08
Nodes (29): rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, idText(), isObject(), MAX_CARD_IMAGE_BYTES, PNG_MAGIC, readBuddy() (+21 more)

### Community 105 - "Community 105"
Cohesion: 0.15
Nodes (34): managedDiverIds(), cylindersOf(), mergeProviderCylinder(), providerCylinderOf(), participantsOf(), columnsOf(), partsOf(), leadOf() (+26 more)

### Community 106 - "Community 106"
Cohesion: 0.12
Nodes (32): importsQuery(), ImportView, ImportContext, ImportFilesButton(), ImportHistory(), ImportList(), ImportNotices(), ImportPanel() (+24 more)

### Community 107 - "Community 107"
Cohesion: 0.11
Nodes (11): ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseCns, ApiDiversIdLoadingsGet200ResponseN2, ApiDiversIdLoadingsGet200ResponseNoFly, ApiDiversIdLoadingsGet200ResponseNoFlySource, ApiDiversIdLoadingsGet200ResponseOtu, cnsDecayed() (+3 more)

### Community 108 - "Community 108"
Cohesion: 0.10
Nodes (29): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+21 more)

### Community 109 - "Community 109"
Cohesion: 0.12
Nodes (27): bar(), celsius(), contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf(), firstOf() (+19 more)

### Community 110 - "Community 110"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 111 - "Community 111"
Cohesion: 0.17
Nodes (8): bottomTime(), otu(), oxygenLimitMinutes(), PlanningSacResult, BottomTimeToolInput, GasNumbersInput, ToolDiverSource, ToolsServiceTest

### Community 112 - "Community 112"
Cohesion: 0.18
Nodes (3): SyncCheckpoint, SyncState, DiveStoreTest

### Community 113 - "Community 113"
Cohesion: 0.12
Nodes (9): Connected, Hint, HintFrame, LiveEvent, LiveHint, LiveHints, SseLiveHints, SyncRefresher (+1 more)

### Community 114 - "Community 114"
Cohesion: 0.09
Nodes (8): ApiDiveSitesGet200Response, SiteView, SiteViewExternalIdsItem, SiteViewExternalIdsItemAttribution, SiteViewExternalIdsItemOffered, SiteViewExternalIdsItemOfferedPosition, SiteViewExternalIdsItemPrivateTo, SiteViewPosition

### Community 115 - "Community 115"
Cohesion: 0.09
Nodes (30): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+22 more)

### Community 116 - "Community 116"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 117 - "Community 117"
Cohesion: 0.10
Nodes (24): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearPushRuns(), clearQueue() (+16 more)

### Community 118 - "Community 118"
Cohesion: 0.09
Nodes (27): @modelcontextprotocol/server@^2.3.1, aiAccessLog, aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, AiAccessService, KeyRow (+19 more)

### Community 119 - "Community 119"
Cohesion: 0.14
Nodes (20): DiveViewRecordingsItemSummary, DiveViewRecordingsItemSummaryGasesItem, planningSac(), PlanningSacDive, jsRound(), powInt(), roundTo(), Sac (+12 more)

### Community 120 - "Community 120"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 121 - "Community 121"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 122 - "Community 122"
Cohesion: 0.10
Nodes (26): globalSetup(), ADR-0023, accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks() (+18 more)

### Community 123 - "Community 123"
Cohesion: 0.11
Nodes (7): ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh, ApiToolsBottomTimeGet200ResponseUsedSacLpm, ApiToolsBottomTimeGet200ResponseUsedWater

### Community 124 - "Community 124"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 125 - "Community 125"
Cohesion: 0.09
Nodes (24): ProviderImportPlan, ADR-0025, WaterMismatch, waterMismatch(), RemoteSite, SsiLogbookSite, SiteInput, DECO_MODELS (+16 more)

### Community 126 - "Community 126"
Cohesion: 0.11
Nodes (19): ProblemCode, ResponseState, whenResponseGone(), StoredBlob, PlacedArchive, ANSWER, looksLikeRecipientPrivateKey(), PlacedArchive (+11 more)

### Community 127 - "Community 127"
Cohesion: 0.16
Nodes (24): DiveOrigin, REFERENCE_PREFIX, SsiLogbook, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef() (+16 more)

### Community 128 - "Community 128"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 129 - "Community 129"
Cohesion: 0.30
Nodes (6): leadAnswer(), EstimateCylinder, PlannedConditions, Suit, leadEstimate(), PersonalizedToolsTest

### Community 130 - "Community 130"
Cohesion: 0.12
Nodes (19): createFileFormats(), FileFormat, looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf() (+11 more)

### Community 131 - "Community 131"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 132 - "Community 132"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 133 - "Community 133"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 134 - "Community 134"
Cohesion: 0.13
Nodes (6): DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, WaitingPushView

### Community 135 - "Community 135"
Cohesion: 0.12
Nodes (22): BACKUP_FORMAT_VERSION, BackupManifest, buildManifest(), decryptFile(), decryptFileWith(), decryptStreamWith(), encryptFile(), encryptStream() (+14 more)

### Community 136 - "Community 136"
Cohesion: 0.11
Nodes (21): CENTRE_SOURCE_NAME, CENTRE_SOURCES, CentreService, CentreView, CentreViewSchema, Row, Source, toCentreView() (+13 more)

### Community 137 - "Community 137"
Cohesion: 0.11
Nodes (14): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+6 more)

### Community 138 - "Community 138"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 139 - "Community 139"
Cohesion: 0.09
Nodes (23): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+15 more)

### Community 140 - "Community 140"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 142 - "Community 142"
Cohesion: 0.17
Nodes (19): NoFly, DEFAULT_GF_HIGH, residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows(), DiveRow, endMsOf() (+11 more)

### Community 143 - "Community 143"
Cohesion: 0.13
Nodes (17): providerGoneDive, compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051, candidatesOf() (+9 more)

### Community 144 - "Community 144"
Cohesion: 0.12
Nodes (19): SUGGESTION_STATUSES, DiveProfileView, DiveProfileViewSchema, ADR-0041, AcceptBody, STATUS, SuggestionRouteDeps, ADR-0070 (+11 more)

### Community 145 - "Community 145"
Cohesion: 0.14
Nodes (6): Answers, ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits

### Community 146 - "Community 146"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 147 - "Community 147"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 148 - "Community 148"
Cohesion: 0.15
Nodes (17): inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), PlacedTime, placeLocalTime(), Position, ADR-0030 (+9 more)

### Community 149 - "Community 149"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 150 - "Community 150"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 151 - "Community 151"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 152 - "Community 152"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 154 - "Community 154"
Cohesion: 0.19
Nodes (11): LeadInputTest, SuitType, drysuit, none, semidry, skin, wetsuit, SuitUndergarment (+3 more)

### Community 155 - "Community 155"
Cohesion: 0.11
Nodes (13): BACKUP_EXTENSION, BackupEncryption, BackupPart, BackupLog, BackupRunRow, BackupSettingsInput, BackupSettingsValues, DEFAULT_BACKUP_SETTINGS (+5 more)

### Community 156 - "Community 156"
Cohesion: 0.18
Nodes (17): siteImport, Field, columnsOf(), createSiteImportService(), create(), loadExisting(), update(), same() (+9 more)

### Community 157 - "Community 157"
Cohesion: 0.16
Nodes (9): diveLocalTime(), offsetId(), timeZoneNote(), TimeZoneOrigin, DEVICE, NEARBY, POSITION, UNKNOWN (+1 more)

### Community 158 - "Community 158"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 159 - "Community 159"
Cohesion: 0.20
Nodes (14): UtcOffsetSource, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput, round3() (+6 more)

### Community 160 - "Community 160"
Cohesion: 0.14
Nodes (14): AL80, EstimateDiveCylinder, LEAD_CASES, leadInput(), LeadVectorInput, planOf(), STEEL_12, ADR-0031 (+6 more)

### Community 162 - "Community 162"
Cohesion: 0.19
Nodes (4): ApiToolsBottomTimeGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseLimits, ApiToolsBottomTimeGet200ResponseResidualN2

### Community 163 - "Community 163"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 164 - "Community 164"
Cohesion: 0.24
Nodes (15): Estimate, LoadingBasis, LoadingDive, N2Loadings, N2UnavailableReason, no_profile, no_recent_dive, stale (+7 more)

### Community 165 - "Community 165"
Cohesion: 0.22
Nodes (13): AppLockProbe, Arguments, credentialOf(), FILE_OPTIONS, fileText(), finishedArguments(), parseArguments(), RestoreCliDeps (+5 more)

### Community 166 - "Community 166"
Cohesion: 0.15
Nodes (15): SuitType, SuitUndergarment, WeightingFelt, Suit, WeightingFeedback, LeadEstimateAnswer, LeadVectorDive, Confidence (+7 more)

### Community 167 - "Community 167"
Cohesion: 0.20
Nodes (13): formatOf(), createImportService(), cancelImport(), createImport(), endWaiting(), expireWaiting(), openUpload(), processFile() (+5 more)

### Community 168 - "Community 168"
Cohesion: 0.19
Nodes (13): asEstimateInput(), evaluateLeadVectorCase(), LEAD_VECTOR_KERNEL, leadVectorFile(), VECTOR_KERNELS, VectorFile, vectorFile(), assertMatch() (+5 more)

### Community 169 - "Community 169"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 170 - "Community 170"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 171 - "Community 171"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 172 - "Community 172"
Cohesion: 0.18
Nodes (11): addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, relations(), TimelineBar, TimelineBarGeometry, TimelineGapGeometry, TimelineGeometry (+3 more)

### Community 173 - "Community 173"
Cohesion: 0.24
Nodes (13): asNumber(), asText(), compareReadBack(), datasetLength(), describe(), isObject(), keyDifferences(), pause() (+5 more)

### Community 174 - "Community 174"
Cohesion: 0.20
Nodes (12): flag(), idText(), isObject(), numberOrNull(), readSsiSite(), SsiSiteValues, visibilityFor(), WATER_OF_BOW (+4 more)

### Community 175 - "Community 175"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 176 - "Community 176"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 177 - "Community 177"
Cohesion: 0.17
Nodes (10): EstimateInput, AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031 (+2 more)

### Community 178 - "Community 178"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 179 - "Community 179"
Cohesion: 0.26
Nodes (3): DiverView, DiverViewCertifications, DiverViewCertificationsNewest

### Community 180 - "Community 180"
Cohesion: 0.26
Nodes (3): DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewCylindersItemSeries

### Community 181 - "Community 181"
Cohesion: 0.26
Nodes (3): DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 182 - "Community 182"
Cohesion: 0.23
Nodes (8): createBackupService(), checkSettings(), due(), enqueueScheduled(), readSettings(), saveSettings(), sweepRetention(), isUniqueViolation()

### Community 183 - "Community 183"
Cohesion: 0.20
Nodes (8): ADR-0063, ADR-0040, ADR-0047, diveNumber(), expireSsiTokens(), headers, openFilters(), signInSsi()

### Community 184 - "Community 184"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 185 - "Community 185"
Cohesion: 0.22
Nodes (6): call(), createSite(), Method, openStream(), Stream, ADR-0066

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

### Community 196 - "Community 196"
Cohesion: 0.25
Nodes (7): IMPORT_SOURCES, SITE_SOURCES, SOURCE_INFO, SourceInfo, ADR-0021, IMPORT_SOURCES, ADR-0021

### Community 197 - "Community 197"
Cohesion: 0.25
Nodes (8): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, MODULE_IMPORTS, moduleRules, target()

### Community 198 - "Community 198"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 199 - "Community 199"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 202 - "Community 202"
Cohesion: 0.29
Nodes (5): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, EstimateCylinder

### Community 203 - "Community 203"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 204 - "Community 204"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 205 - "Community 205"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 206 - "Community 206"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 207 - "Community 207"
Cohesion: 0.40
Nodes (5): ToolPage, Gas, Lead, Loadings, Plan

### Community 209 - "Community 209"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 210 - "Community 210"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 211 - "Community 211"
Cohesion: 0.40
Nodes (4): TAGS, ADR-0023, { stdout, stderr }, total

### Community 216 - "Community 216"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 217 - "Community 217"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 218 - "Community 218"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1268 isolated node(s):** `Reserve`, `RockBottom`, `Off`, `SuitThickness`, `ADR-0013` (+1263 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3425 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **116 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 0` to `Community 5`, `Community 7`, `Community 9`, `Community 10`, `Community 139`, `Community 13`, `Community 142`, `Community 143`, `Community 17`, `Community 19`, `Community 148`, `Community 21`, `Community 26`, `Community 155`, `Community 28`, `Community 156`, `Community 30`, `Community 31`, `Community 34`, `Community 35`, `Community 36`, `Community 39`, `Community 44`, `Community 50`, `Community 51`, `Community 60`, `Community 61`, `Community 71`, `Community 73`, `Community 74`, `Community 86`, `Community 87`, `Community 88`, `Community 97`, `Community 102`, `Community 105`, `Community 108`, `Community 118`, `Community 120`?**
  _High betweenness centrality (0.023) - this node is a cross-community bridge._
- **What connects `Reserve`, `RockBottom`, `Off` to the rest of the system?**
  _1268 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.023078823820113663 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 3` to `Community 1`, `Community 2`, `Community 4`, `Community 6`, `Community 8`, `Community 106`, `Community 11`, `Community 12`, `Community 15`, `Community 16`, `Community 115`, `Community 147`, `Community 92`?**
  _High betweenness centrality (0.014) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.050172265288544356 - nodes in this community are weakly interconnected._
- **Why does `Button()` connect `Community 1` to `Community 2`, `Community 3`, `Community 4`, `Community 6`, `Community 8`, `Community 106`, `Community 11`, `Community 12`, `Community 15`, `Community 16`?**
  _High betweenness centrality (0.013) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.02328548644338118 - nodes in this community are weakly interconnected._