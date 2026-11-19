# Graph Report - dive_hub  (2026-11-19)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 10045 nodes · 30384 edges · 328 communities (205 shown, 123 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 537 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `f41db851`
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
- Community 221
- Community 223

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
- `SsiClientOptions` --references--> `Fetch`  [EXTRACTED]
  apps/server/src/providers/ssi/ssi-client.ts → apps/server/src/providers/ssi/ssi-transport.ts
- `SsiLogbookSite` --references--> `SiteWaterType`  [EXTRACTED]
  apps/server/src/providers/ssi/ssi-client.ts → apps/server/src/vocabulary.ts

## Import Cycles
- None detected.

## Communities (328 total, 123 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.03
Nodes (189): user, accountListEntry, cylinder, dive, diver, diverExternalId, diveSiteExternalId, PROBLEMS (+181 more)

### Community 1 - "Community 1"
Cohesion: 0.05
Nodes (214): ChangePassword(), DisplaySettings(), Sessions(), useDescribeAgent(), AdminSettings(), DeleteUserDialog(), Invitations(), Invite() (+206 more)

### Community 2 - "Community 2"
Cohesion: 0.02
Nodes (200): cylinderCatalogueQuery(), equipmentOfDiverQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery() (+192 more)

### Community 3 - "Community 3"
Cohesion: 0.02
Nodes (138): createServer(), createAuth(), loadAuthSecret(), acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS (+130 more)

### Community 4 - "Community 4"
Cohesion: 0.02
Nodes (142): AiAccessLogEntry, AiAccessView, InvitationView, sessionsQuery(), SessionView, setupQuery(), ADR-0035, UserView (+134 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (149): continuationAppend, diverBodyWeight, diveSite, lead, LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, participant (+141 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (151): ComputerChoice, CylinderMaterial, DiveImportMode, DiveKind, importOriginal, ImportOutcome, ProviderImportPlan, UtcOffsetSource (+143 more)

### Community 7 - "Community 7"
Cohesion: 0.02
Nodes (128): buddySuggestion, jointDive, parseHint(), Assessment, FindingView, Row, ADR-0036, upload() (+120 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (127): Me, AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView (+119 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (121): Auth, CentreService, CentreRouteDeps, UTC_OFFSET_SOURCES, WEIGHTING_FELT, DiverService, DiverRouteDeps, downsampleMinMax() (+113 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (118): diveNeighboursQuery(), divesQuery(), LogbookPage, OverridableField, PAGE_SIZE, revisionsQuery(), siteQuery(), CylinderList() (+110 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (108): BACKUP_EXTENSION, BACKUP_FORMAT_VERSION, BACKUP_SKIPPED_DIRS, BackupArchiveError, BackupDecryptionKey, BackupEncryption, BackupEncryptionMetadata, BackupPart (+100 more)

### Community 12 - "Community 12"
Cohesion: 0.02
Nodes (100): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreExternalId, CentreSource, live, ADR-0029, ADR-0043, typedId() (+92 more)

### Community 13 - "Community 13"
Cohesion: 0.03
Nodes (105): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError (+97 more)

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (104): SecuritySettings(), backupsQuery(), ExternalIdView, App(), BackupsPage, CentrePage, CentresPage, currentRoute() (+96 more)

### Community 15 - "Community 15"
Cohesion: 0.03
Nodes (105): AssessmentService, AssessmentRouteDeps, BackupRouteDeps, BACKUP_RUN_TASK, BackupService, backupTask(), ADR-0069, CertificationService (+97 more)

### Community 16 - "Community 16"
Cohesion: 0.03
Nodes (106): buddiesQuery(), BuddyView, ConnectionView, DiveImportMode, DiveImportPreview, diveImportPreviewQuery(), DiveKind, diveTimesQuery() (+98 more)

### Community 17 - "Community 17"
Cohesion: 0.04
Nodes (98): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+90 more)

### Community 18 - "Community 18"
Cohesion: 0.04
Nodes (100): diveAssessment, logbookCheckAnswer, logbookCheckDiveAnswer, recordingEvent, keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration() (+92 more)

### Community 19 - "Community 19"
Cohesion: 0.03
Nodes (97): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+89 more)

### Community 20 - "Community 20"
Cohesion: 0.04
Nodes (73): equipmentDueQuery(), showDiveCodes(), ADR-0043, DiverRow(), EquipmentDueNotice(), ADR-0034, avatarTone(), initials() (+65 more)

### Community 21 - "Community 21"
Cohesion: 0.03
Nodes (76): connection, diveLease, providerConflict, push, pushRun, revision, waitingPush, kept() (+68 more)

### Community 22 - "Community 22"
Cohesion: 0.05
Nodes (90): diveFingerprint, PushLeftOut, waitingOf(), open(), createConnectionService(), checkAutomation(), checkFollowNumbers(), expiryOf() (+82 more)

### Community 23 - "Community 23"
Cohesion: 0.04
Nodes (93): LEAD_PLACEMENTS, AiAccessScope, seesPositions(), diversOf(), seriesOf(), SiteRow, checkedByRunTool, createMcpServer() (+85 more)

### Community 24 - "Community 24"
Cohesion: 0.05
Nodes (89): allButDive(), continuationCandidateQuery(), deletedDivesOn(), deletedDivesQuery(), diveQuery(), DiveView, mergeCandidatesQuery(), nearbyDivesQuery() (+81 more)

### Community 25 - "Community 25"
Cohesion: 0.02
Nodes (90): actorType, aiAccessOutcome, backupRunKind, backupRunStatus, candidateResolution, centreSource, CERTIFICATION_IMAGE_STATES, CERTIFICATION_VERIFICATIONS (+82 more)

### Community 26 - "Community 26"
Cohesion: 0.03
Nodes (71): createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities(), ADR-0062 (+63 more)

### Community 27 - "Community 27"
Cohesion: 0.04
Nodes (48): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+40 more)

### Community 28 - "Community 28"
Cohesion: 0.04
Nodes (17): MainActivity, MainViewModel, UiState, LoadingsUiState, LoadingsViewModel, ApiMeGet200Response, ApiMeGet200ResponseOwnDiver, ApiMeGet200ResponsePreferences (+9 more)

### Community 29 - "Community 29"
Cohesion: 0.05
Nodes (62): AuthOptions, auth, BackupServiceDeps, createServices(), ServiceParts, Services, ADR-0027, ADR-0030 (+54 more)

### Community 30 - "Community 30"
Cohesion: 0.05
Nodes (18): LogbookScreenshotTest, ApiDiverBodyWeightsGet200ResponseItem, ApiDivesGet200Response, ApiDivesGet200ResponseDivesItem, ApiDivesGet200ResponseDivesItemGasesItem, ApiDivesGet200ResponseDivesItemParticipantsItem, ApiDivesGet200ResponseDivesItemSite, ApiDivesGet200ResponseMonthsItem (+10 more)

### Community 31 - "Community 31"
Cohesion: 0.05
Nodes (71): ConflictChoices, ConflictEntryView, ConflictFieldView, ConflictsOfConnection, RequirementView, useCylinderText(), ADR-0014, ADR-0020 (+63 more)

### Community 32 - "Community 32"
Cohesion: 0.04
Nodes (10): AppContainer, KeystoreTokenStore, PreferencesInstanceUrlStore, UserCacheStore, DiveHubApplication, SyncWorker, WorkManagerPeriodicSync, InstanceUrlStore (+2 more)

### Community 33 - "Community 33"
Cohesion: 0.05
Nodes (28): DiveDetailUiState, DiveDetailViewModel, LogbookViewModel, ApiDivesGet200ResponseCounts, DiveRepository, neighboursPath(), DiveHubException, NoAnswerException (+20 more)

### Community 34 - "Community 34"
Cohesion: 0.07
Nodes (74): buildApp(), assessmentRoutes(), AssessmentViewSchema, FindingView, Rule, STATUS, toView(), ADR-0036 (+66 more)

### Community 35 - "Community 35"
Cohesion: 0.07
Nodes (68): GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult(), PlanInputs(), PlanRuleExplanation() (+60 more)

### Community 36 - "Community 36"
Cohesion: 0.05
Nodes (13): AndroidDiveStore, DiveStore, toBoolean(), toLong(), toModel(), BodyWeight, Cylinder, DiveSite (+5 more)

### Community 37 - "Community 37"
Cohesion: 0.07
Nodes (65): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+57 more)

### Community 39 - "Community 39"
Cohesion: 0.05
Nodes (68): equipmentItem, serviceRecord, serviceRecordSchedule, serviceSchedule, managementOf(), ScheduleDue, EquipmentError, ADR-0034 (+60 more)

### Community 40 - "Community 40"
Cohesion: 0.04
Nodes (64): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, DIVE_IMPORT_MODES, DIVE_KINDS (+56 more)

### Community 41 - "Community 41"
Cohesion: 0.06
Nodes (60): SessionUser, authHandler(), authHeaders(), fastify, FastifyRequest, forwardCookies(), isAdmin(), publicPaths (+52 more)

### Community 42 - "Community 42"
Cohesion: 0.07
Nodes (38): DiveBody(), DiveDetailRoute(), DiveDetailScreen(), errorText(), Loading(), NeighbourButton(), Neighbours(), Notice() (+30 more)

### Community 43 - "Community 43"
Cohesion: 0.05
Nodes (17): ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting, DeletedDivesPage, DeletedDivesPageDivesItem (+9 more)

### Community 44 - "Community 44"
Cohesion: 0.05
Nodes (16): DiveView, DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewLeadItem, DiveViewRecordingsItemSummary, DiveViewSac, DiveViewSite, DiveViewSuit (+8 more)

### Community 45 - "Community 45"
Cohesion: 0.07
Nodes (56): dataIdsOf(), settleSiteVisibility(), outgoingSite(), findOrMake(), SiteForSend, siteForSend(), ADR-0057, createSitePickService() (+48 more)

### Community 46 - "Community 46"
Cohesion: 0.06
Nodes (51): ImportArea, errorMessage(), looksLikeZip(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson() (+43 more)

### Community 47 - "Community 47"
Cohesion: 0.07
Nodes (64): Answer, answer(), baseFrom(), BodyWeight, Confidence, high, low, medium (+56 more)

### Community 48 - "Community 48"
Cohesion: 0.06
Nodes (60): AREAS, baseCandidates(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads(), exists() (+52 more)

### Community 49 - "Community 49"
Cohesion: 0.05
Nodes (56): pushRunItem, PushRunLeftOut, asksFor(), RemoteDive, Blocked, classify(), FailureOutcome, failureOutcome() (+48 more)

### Community 50 - "Community 50"
Cohesion: 0.07
Nodes (54): SsiRecord, contextOf(), asNumber(), buildCreateRecord(), buildDeleteRecord(), buildUpdateRecord(), compareReadBack(), DiveForSsi (+46 more)

### Community 51 - "Community 51"
Cohesion: 0.06
Nodes (51): defineTool(), Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), HINT, Input (+43 more)

### Community 52 - "Community 52"
Cohesion: 0.04
Nodes (59): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+51 more)

### Community 53 - "Community 53"
Cohesion: 0.04
Nodes (54): @node-rs/argon2@^2.2.1, adminRole, AI_ACCESS_NAME_MAX, AI_ACCESS_PREFIX, AI_ACCESS_RATE, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, ADR-0011 (+46 more)

### Community 54 - "Community 54"
Cohesion: 0.09
Nodes (13): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), GasPlanInputTest (+5 more)

### Community 55 - "Community 55"
Cohesion: 0.05
Nodes (51): Delivered, forSsi(), remoteBuddy(), ADR-0024, ADR-0027, ADR-0029, ADR-0030, ADR-0048 (+43 more)

### Community 56 - "Community 56"
Cohesion: 0.11
Nodes (27): Body, Hold, Offline, RefreshData, refreshDeletedJson(), refreshDetailJson(), refreshDetailsPage(), refreshDiveId() (+19 more)

### Community 57 - "Community 57"
Cohesion: 0.05
Nodes (51): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, cancelWaitingImports() (+43 more)

### Community 58 - "Community 58"
Cohesion: 0.09
Nodes (30): BottomTime, bottomTime(), BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen (+22 more)

### Community 59 - "Community 59"
Cohesion: 0.09
Nodes (4): DiveRepositoryTest, FakeInstances, FakeTokens, SyncAuthTest

### Community 60 - "Community 60"
Cohesion: 0.06
Nodes (48): EMPTY_SITE_IMPORT_COUNTS, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, ExistingSite (+40 more)

### Community 61 - "Community 61"
Cohesion: 0.04
Nodes (32): ParticipantRole, Direction, DiveExport, DiveExportAction, DiveListing, FindBy, Operation, OutgoingParticipant (+24 more)

### Community 62 - "Community 62"
Cohesion: 0.07
Nodes (48): providerLease, tidyName(), DiverDetails, Caller, foldName(), nameDiffers(), Buddy, createBuddyService() (+40 more)

### Community 63 - "Community 63"
Cohesion: 0.10
Nodes (10): SyncCheckpoint, Complete, SessionEnded, Stopped, SyncEngine, SyncPhase, Details, Meta (+2 more)

### Community 64 - "Community 64"
Cohesion: 0.06
Nodes (34): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+26 more)

### Community 66 - "Community 66"
Cohesion: 0.14
Nodes (24): Dive, Body, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage(), diverJson() (+16 more)

### Community 67 - "Community 67"
Cohesion: 0.10
Nodes (42): ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres(), isHypoxic(), metresPerBar() (+34 more)

### Community 68 - "Community 68"
Cohesion: 0.06
Nodes (38): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+30 more)

### Community 69 - "Community 69"
Cohesion: 0.08
Nodes (25): ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseCns, ApiDiversIdLoadingsGet200ResponseN2, ApiDiversIdLoadingsGet200ResponseNoFly, ApiDiversIdLoadingsGet200ResponseNoFlySource, ApiDiversIdLoadingsGet200ResponseOtu, cnsDecayed() (+17 more)

### Community 70 - "Community 70"
Cohesion: 0.07
Nodes (13): ApiToolsBottomTimeGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseLimits, ApiToolsBottomTimeGet200ResponseResidualN2, ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue (+5 more)

### Community 71 - "Community 71"
Cohesion: 0.07
Nodes (11): ApiClient, ApiErrorBody, Connected, Hint, HintFrame, LiveEvent, LiveHint, LiveHints (+3 more)

### Community 72 - "Community 72"
Cohesion: 0.07
Nodes (44): NoFly, getDiverLoadings, DEFAULT_GF_HIGH, residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows(), DiveRow (+36 more)

### Community 73 - "Community 73"
Cohesion: 0.12
Nodes (29): ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponsePpO2Limits, ambientPressureBar(), bestMixPercent(), Conditions, depthOfPressure(), eadMetres(), endMetres() (+21 more)

### Community 74 - "Community 74"
Cohesion: 0.08
Nodes (41): Leave, asBool(), DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+33 more)

### Community 75 - "Community 75"
Cohesion: 0.05
Nodes (39): CYLINDER_MATERIALS, SUIT_TYPES, SUIT_UNDERGARMENTS, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053 (+31 more)

### Community 76 - "Community 76"
Cohesion: 0.05
Nodes (42): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+34 more)

### Community 77 - "Community 77"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 78 - "Community 78"
Cohesion: 0.07
Nodes (41): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+33 more)

### Community 79 - "Community 79"
Cohesion: 0.06
Nodes (40): Change(), Changes(), historyRoute, Made(), REVISION_CAUSES, PositionSchema, AdoptBody, Country (+32 more)

### Community 81 - "Community 81"
Cohesion: 0.13
Nodes (8): FakeCache, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore, UserScopedStore

### Community 82 - "Community 82"
Cohesion: 0.10
Nodes (24): FactRow(), Facts(), Water(), DiveDetailText, utcOffsetText(), Deep, DepthReading, depthReading() (+16 more)

### Community 83 - "Community 83"
Cohesion: 0.10
Nodes (32): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0051 (+24 more)

### Community 84 - "Community 84"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 85 - "Community 85"
Cohesion: 0.07
Nodes (29): equipmentUse, managesDiveDiver(), Participant, participantsOfDives(), DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly (+21 more)

### Community 86 - "Community 86"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 87 - "Community 87"
Cohesion: 0.11
Nodes (17): DiveRow(), Error(), Fact(), Filters(), Loading(), LogbookList(), LogbookRoute(), LogbookScreen() (+9 more)

### Community 88 - "Community 88"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 89 - "Community 89"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 90 - "Community 90"
Cohesion: 0.14
Nodes (4): DiveDetailScreenshotTest, DiveViewFromRecording, DiveViewFromRecordingStartsAt, DiveViewWaterMismatch

### Community 91 - "Community 91"
Cohesion: 0.07
Nodes (30): CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043, CreateBody (+22 more)

### Community 92 - "Community 92"
Cohesion: 0.06
Nodes (28): account, accountRelations, apikey, rateLimit, sessionRelations, ADR-0011, ADR-0012, ADR-0035 (+20 more)

### Community 93 - "Community 93"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 94 - "Community 94"
Cohesion: 0.13
Nodes (34): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+26 more)

### Community 95 - "Community 95"
Cohesion: 0.08
Nodes (31): PARTICIPANT_ROLES, DIVE_ONLY_PATTERN, storedProblemCode(), oneOf(), DiveProviderStatus, DiveProviderStatusSchema, LeftOut, PushView (+23 more)

### Community 96 - "Community 96"
Cohesion: 0.11
Nodes (28): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+20 more)

### Community 97 - "Community 97"
Cohesion: 0.09
Nodes (33): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+25 more)

### Community 98 - "Community 98"
Cohesion: 0.08
Nodes (28): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+20 more)

### Community 99 - "Community 99"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 100 - "Community 100"
Cohesion: 0.13
Nodes (31): importsQuery(), Logbook(), ImportContext, ImportFilesButton(), ImportHistory(), ImportList(), ImportNotices(), ImportPanel() (+23 more)

### Community 101 - "Community 101"
Cohesion: 0.08
Nodes (21): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, killTree() (+13 more)

### Community 102 - "Community 102"
Cohesion: 0.11
Nodes (9): LeadAnswer, LeadEstimateUiState, LeadEstimateViewModel, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseRange, ApiDiversIdLeadEstimateGet200ResponseWeightCheck, LeadDiveSource (+1 more)

### Community 103 - "Community 103"
Cohesion: 0.11
Nodes (9): Answers, number(), ToolsViewModel, ApiToolsGasGet200Response, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, depthFromDisplay(), pressureFromDisplay() (+1 more)

### Community 104 - "Community 104"
Cohesion: 0.11
Nodes (6): Missing(), LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200ResponseMissingItem, ApiDiversIdLeadEstimateGet200ResponseReserve

### Community 105 - "Community 105"
Cohesion: 0.09
Nodes (8): ApiDiveSitesGet200Response, SiteView, SiteViewExternalIdsItem, SiteViewExternalIdsItemAttribution, SiteViewExternalIdsItemOffered, SiteViewExternalIdsItemOfferedPosition, SiteViewExternalIdsItemPrivateTo, SiteViewPosition

### Community 106 - "Community 106"
Cohesion: 0.09
Nodes (20): enteredDecoFromNdl(), minNdlSeconds(), NoFly, NoFlyReason, decompression, several, single, SeriesDive (+12 more)

### Community 107 - "Community 107"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 108 - "Community 108"
Cohesion: 0.26
Nodes (8): leadAnswer(), EstimateCylinder, PlannedConditions, Suit, assemblePersonalizedSources(), suitOf(), leadEstimate(), PersonalizedToolsTest

### Community 110 - "Community 110"
Cohesion: 0.08
Nodes (25): ADR-0025, WaterMismatch, ProviderAdapter, RemoteSite, ProviderSite, SiteInput, DECO_MODELS, DIVE_MODES (+17 more)

### Community 111 - "Community 111"
Cohesion: 0.12
Nodes (26): CentreError, createCentreService(), holder(), liveSite(), lock(), setExternal(), displayNameOf(), SiteImportFinding (+18 more)

### Community 112 - "Community 112"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 113 - "Community 113"
Cohesion: 0.14
Nodes (26): DiveOrigin, REFERENCE_PREFIX, Series, SsiLogbook, aboveZero(), cylinderOf(), deviceOf(), deviceOfRef() (+18 more)

### Community 114 - "Community 114"
Cohesion: 0.10
Nodes (22): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearQueue(), conflictAtSsi() (+14 more)

### Community 115 - "Community 115"
Cohesion: 0.09
Nodes (27): devDependencies, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2, @testing-library/react@^16.3.3 (+19 more)

### Community 116 - "Community 116"
Cohesion: 0.11
Nodes (7): ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport

### Community 117 - "Community 117"
Cohesion: 0.12
Nodes (7): LogbookPaging, sketchScale(), json(), LogbookPagingTest, LogbookRepositoryTest, RepositoryHarness, SketchTest

### Community 118 - "Community 118"
Cohesion: 0.11
Nodes (19): SsiClient, NumberChecks, TwowayChecks, apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks() (+11 more)

### Community 119 - "Community 119"
Cohesion: 0.09
Nodes (21): Cylinder, DiveView, RevisionView, ADR-0045, upload(), adapter, crossCheck(), decodeOfficial() (+13 more)

### Community 120 - "Community 120"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 121 - "Community 121"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 122 - "Community 122"
Cohesion: 0.11
Nodes (19): ProblemCode, ResponseState, whenResponseGone(), StoredBlob, PlacedArchive, ANSWER, looksLikeRecipientPrivateKey(), PlacedArchive (+11 more)

### Community 123 - "Community 123"
Cohesion: 0.11
Nodes (23): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+15 more)

### Community 124 - "Community 124"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 125 - "Community 125"
Cohesion: 0.13
Nodes (10): drawBand(), ProfileSketch(), AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint, SketchProfile (+2 more)

### Community 126 - "Community 126"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 127 - "Community 127"
Cohesion: 0.14
Nodes (11): diveLocalTime(), offsetId(), timeZoneNote(), TimeZoneOrigin, DEVICE, NEARBY, POSITION, UNKNOWN (+3 more)

### Community 128 - "Community 128"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 129 - "Community 129"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 130 - "Community 130"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 131 - "Community 131"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 132 - "Community 132"
Cohesion: 0.20
Nodes (18): planningSac(), PlanningSacDive, jsRound(), powInt(), roundTo(), Sac, SacCylinder, SacDive (+10 more)

### Community 133 - "Community 133"
Cohesion: 0.17
Nodes (15): hashPassword(), createInvitationService(), EmailTakenError, InvitationRow, InvitationStatus, ADR-0012, createPasswordResetService(), find() (+7 more)

### Community 134 - "Community 134"
Cohesion: 0.14
Nodes (16): providerGoneDive, compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051, candidatesOf() (+8 more)

### Community 135 - "Community 135"
Cohesion: 0.14
Nodes (17): createFileFormats(), createFitAdapter(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf() (+9 more)

### Community 136 - "Community 136"
Cohesion: 0.13
Nodes (15): sampleCount(), SsiCallLog, storedList(), tryParseJson(), datasetLength(), APP_PARAMS, createSsiTransport(), exchange() (+7 more)

### Community 137 - "Community 137"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 139 - "Community 139"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 140 - "Community 140"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 141 - "Community 141"
Cohesion: 0.15
Nodes (17): DAY_MS, inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), placeLocalTime(), Position, ADR-0030 (+9 more)

### Community 142 - "Community 142"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 143 - "Community 143"
Cohesion: 0.13
Nodes (15): FIND_BY, OPERATIONS, SignInInput, CASES, ContractHarness, declared(), ELSEWHERE, later() (+7 more)

### Community 144 - "Community 144"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 145 - "Community 145"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 146 - "Community 146"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 147 - "Community 147"
Cohesion: 0.19
Nodes (11): LeadInputTest, SuitType, drysuit, none, semidry, skin, wetsuit, SuitUndergarment (+3 more)

### Community 148 - "Community 148"
Cohesion: 0.19
Nodes (15): clientVersionGate(), ADR-0076, ClientVersionRegistry, MIN_CLIENT_VERSIONS, MinClientVersion, ADR-0076, clientRequestSerializer(), ADR-0076 (+7 more)

### Community 149 - "Community 149"
Cohesion: 0.30
Nodes (5): Diver, assembleToolSources(), epochMs(), recentDiveEnd(), ToolSourcesStoreTest

### Community 150 - "Community 150"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 151 - "Community 151"
Cohesion: 0.14
Nodes (16): SUGGESTION_STATUSES, AcceptBody, STATUS, SuggestionRouteDeps, ADR-0070, SuggestionService, DiverRef, Offset (+8 more)

### Community 152 - "Community 152"
Cohesion: 0.16
Nodes (13): BOM, Json, num(), obj(), positive(), scaled(), suuntoFitKey(), SuuntoJsonAdapter (+5 more)

### Community 153 - "Community 153"
Cohesion: 0.13
Nodes (15): AL80, asEstimateInput(), EstimateDiveCylinder, LEAD_CASES, leadInput(), LeadVectorInput, planOf(), STEEL_12 (+7 more)

### Community 154 - "Community 154"
Cohesion: 0.12
Nodes (16): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+8 more)

### Community 155 - "Community 155"
Cohesion: 0.15
Nodes (7): Formats, AvatarCircle(), durationMinutes(), initials(), mixName(), pressureIn(), LogbookFormatTest

### Community 157 - "Community 157"
Cohesion: 0.14
Nodes (16): SuitType, SuitUndergarment, WeightingFelt, Suit, WeightingFeedback, LeadEstimateAnswer, LeadVectorDive, Confidence (+8 more)

### Community 158 - "Community 158"
Cohesion: 0.15
Nodes (13): Sac, SAC_MISSING, SacMissing, sacOfDive(), SacValues, sacValues(), ADR-0033, ADR-0045 (+5 more)

### Community 159 - "Community 159"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 160 - "Community 160"
Cohesion: 0.20
Nodes (13): formatOf(), createImportService(), cancelImport(), createImport(), endWaiting(), expireWaiting(), openUpload(), processFile() (+5 more)

### Community 161 - "Community 161"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 162 - "Community 162"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 163 - "Community 163"
Cohesion: 0.16
Nodes (13): certification, createUserAdminService(), keepAnAdmin(), lock(), deleteUserData(), ADR-0013, ADR-0024, ADR-0034 (+5 more)

### Community 164 - "Community 164"
Cohesion: 0.21
Nodes (12): evaluateLeadVectorCase(), LEAD_VECTOR_KERNEL, leadVectorFile(), VECTOR_KERNELS, VectorFile, vectorFile(), assertMatch(), checkVectors() (+4 more)

### Community 165 - "Community 165"
Cohesion: 0.19
Nodes (11): decimal(), dive(), makeOceanicPlusUddf(), OCEANIC_DIVES, OceanicDive, site(), waypoint(), exported (+3 more)

### Community 166 - "Community 166"
Cohesion: 0.14
Nodes (7): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, headers, uniqueWord()

### Community 167 - "Community 167"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 168 - "Community 168"
Cohesion: 0.18
Nodes (11): addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, relations(), TimelineBar, TimelineBarGeometry, TimelineGapGeometry, TimelineGeometry (+3 more)

### Community 169 - "Community 169"
Cohesion: 0.13
Nodes (14): name, needs, runs-on, timeout-minutes, concurrency, cancel-in-progress, group, env (+6 more)

### Community 170 - "Community 170"
Cohesion: 0.21
Nodes (3): SyncLine(), formatSyncTime(), SyncTimeFormatTest

### Community 171 - "Community 171"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 172 - "Community 172"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 173 - "Community 173"
Cohesion: 0.17
Nodes (10): EstimateInput, AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031 (+2 more)

### Community 174 - "Community 174"
Cohesion: 0.26
Nodes (13): buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open(), saveAndReadBack(), saveBuddy() (+5 more)

### Community 175 - "Community 175"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 177 - "Community 177"
Cohesion: 0.26
Nodes (3): DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 178 - "Community 178"
Cohesion: 0.26
Nodes (3): NeighboursView, NeighboursViewNext, NeighboursViewPrevious

### Community 179 - "Community 179"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 180 - "Community 180"
Cohesion: 0.36
Nodes (9): DiveHubApp(), ErrorText(), InstanceForm(), Loading(), PasswordField(), PrimaryButton(), SignInForm(), Spinner() (+1 more)

### Community 181 - "Community 181"
Cohesion: 0.29
Nodes (10): RecordingSummary, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput, round3() (+2 more)

### Community 182 - "Community 182"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

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
Cohesion: 0.28
Nodes (7): ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, s(), ADR-0035

### Community 193 - "Community 193"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 194 - "Community 194"
Cohesion: 0.22
Nodes (9): version, on, push, workflow_dispatch, tags, description, required, type (+1 more)

### Community 195 - "Community 195"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 196 - "Community 196"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 199 - "Community 199"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 200 - "Community 200"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 202 - "Community 202"
Cohesion: 0.33
Nodes (4): Sketch, SKETCH_POINTS, ADR-0041, ADR-0041

### Community 203 - "Community 203"
Cohesion: 0.29
Nodes (6): label(), axe-core@^4.13.0, CompareSubject, facts, subjects, axe-core

### Community 204 - "Community 204"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 205 - "Community 205"
Cohesion: 0.47
Nodes (4): ownersParts(), parse(), toPart(), ADR-0063

### Community 206 - "Community 206"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 207 - "Community 207"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 208 - "Community 208"
Cohesion: 0.40
Nodes (5): ToolPage, Gas, Lead, Loadings, Plan

### Community 209 - "Community 209"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 210 - "Community 210"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 211 - "Community 211"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 215 - "Community 215"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 216 - "Community 216"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 217 - "Community 217"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1277 isolated node(s):** `Reserve`, `RockBottom`, `SuitThickness`, `Off`, `ADR-0040` (+1272 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3499 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **123 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 0` to `Community 3`, `Community 5`, `Community 6`, `Community 134`, `Community 133`, `Community 9`, `Community 7`, `Community 11`, `Community 12`, `Community 13`, `Community 141`, `Community 15`, `Community 17`, `Community 18`, `Community 21`, `Community 22`, `Community 23`, `Community 25`, `Community 26`, `Community 29`, `Community 163`, `Community 39`, `Community 40`, `Community 45`, `Community 49`, `Community 51`, `Community 52`, `Community 53`, `Community 62`, `Community 72`, `Community 85`, `Community 92`, `Community 97`, `Community 111`?**
  _High betweenness centrality (0.021) - this node is a cross-community bridge._
- **What connects `Reserve`, `RockBottom`, `SuitThickness` to the rest of the system?**
  _1277 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.025166690310682367 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 4` to `Community 1`, `Community 2`, `Community 100`, `Community 37`, `Community 8`, `Community 10`, `Community 140`, `Community 14`, `Community 16`, `Community 115`, `Community 20`, `Community 84`, `Community 24`, `Community 31`?**
  _High betweenness centrality (0.017) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.04558235903507474 - nodes in this community are weakly interconnected._
- **Why does `DiveStore` connect `Community 36` to `Community 32`, `Community 66`, `Community 132`, `Community 102`, `Community 103`, `Community 106`, `Community 44`, `Community 108`, `Community 109`, `Community 149`, `Community 54`, `Community 56`, `Community 59`, `Community 28`, `Community 63`?**
  _High betweenness centrality (0.013) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.023083752589523526 - nodes in this community are weakly interconnected._