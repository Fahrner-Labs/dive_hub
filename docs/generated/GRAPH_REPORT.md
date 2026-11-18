# Graph Report - dive_hub  (2026-11-18)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 8674 nodes · 26070 edges · 251 communities (192 shown, 59 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 352 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `0ef7206d`
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
- Community 193
- Community 194
- Community 195
- Community 197
- Community 198
- Community 199
- Community 200
- Community 201
- Community 202
- Community 203
- Community 206

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
- `Filters()` --calls--> `FilterChip`  [INFERRED]
  apps/android/app/src/main/kotlin/dev/divehub/android/ui/logbook/LogbookScreen.kt → apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/logbook/LogbookFilter.kt

## Import Cycles
- None detected.

## Communities (251 total, 59 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (222): diversQuery(), cylinderCatalogueQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery() (+214 more)

### Community 1 - "Community 1"
Cohesion: 0.03
Nodes (135): user, accountListEntry, diver, diverExternalId, diveSiteExternalId, original, revision, forget() (+127 more)

### Community 2 - "Community 2"
Cohesion: 0.02
Nodes (167): diveNeighboursQuery(), LogbookPage, PAGE_SIZE, ExternalIdView, siteQuery(), sitesQuery(), CylinderList(), Buddies() (+159 more)

### Community 3 - "Community 3"
Cohesion: 0.04
Nodes (157): DisplaySettings(), DeleteUserDialog(), Invite(), LinkToPassOn(), SiteDataPanel(), UserRow(), CreateAccess(), certificationsQuery() (+149 more)

### Community 4 - "Community 4"
Cohesion: 0.02
Nodes (131): apikey, buddySuggestion, diveAssessment, parseHint(), Assessment, FindingView, Row, ADR-0036 (+123 more)

### Community 5 - "Community 5"
Cohesion: 0.04
Nodes (134): ChangePassword(), SecuritySettings(), Sessions(), useDescribeAgent(), AdminSettings(), Invitations(), ProviderSiteData(), ADR-0012 (+126 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (98): createServer(), createAuth(), loadAuthSecret(), acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS (+90 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (117): Me, AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView (+109 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (113): BACKUP_EXTENSION, BACKUP_FORMAT_VERSION, BACKUP_SKIPPED_DIRS, BackupArchiveError, BackupDecryptionKey, BackupEncryption, BackupEncryptionMetadata, BackupPart (+105 more)

### Community 9 - "Community 9"
Cohesion: 0.04
Nodes (96): replyCertificationError, STATUS, CertificationService, CertificationView, Day, ADR-0050, CardParams, CertificationRouteDeps (+88 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (35): AppContainer, PreferencesInstanceUrlStore, AndroidDiveStore, DiveHubApplication, MainActivity, DiveDetailUiState, DiveDetailViewModel, LogbookViewModel (+27 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (89): CertificationView, DeviceView, diverDetailsQuery(), DiverDetailsView, ExternalDiverView, FoundDiverView, ReadCodeView, ADR-0028 (+81 more)

### Community 12 - "Community 12"
Cohesion: 0.04
Nodes (98): divesQuery(), equipmentDueQuery(), importQuery(), importsQuery(), ImportView, ADR-0030, uploadFile(), noAnswerError() (+90 more)

### Community 13 - "Community 13"
Cohesion: 0.03
Nodes (80): connection, createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities() (+72 more)

### Community 14 - "Community 14"
Cohesion: 0.02
Nodes (99): account, accountRelations, rateLimit, session, sessionRelations, ADR-0011, ADR-0012, ADR-0035 (+91 more)

### Community 15 - "Community 15"
Cohesion: 0.04
Nodes (17): DiveDetailRoute(), DiveDetailScreen(), errorText(), Loading(), Notice(), title(), DiveHubApp(), ErrorText() (+9 more)

### Community 16 - "Community 16"
Cohesion: 0.03
Nodes (24): ApiMeGet200ResponseOwnDiver, ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting, DiveView (+16 more)

### Community 17 - "Community 17"
Cohesion: 0.03
Nodes (85): LEAD_PLACEMENTS, WEIGHTING_FELT, ADR-0025, WaterMismatch, waterMismatch(), DiveCertifications, CatalogueCylinder, CYLINDER_CATALOGUE (+77 more)

### Community 18 - "Community 18"
Cohesion: 0.05
Nodes (88): allButDive(), continuationCandidateQuery(), deletedDivesOn(), deletedDivesQuery(), diveQuery(), DiveView, mergeCandidatesQuery(), nearbyDivesQuery() (+80 more)

### Community 19 - "Community 19"
Cohesion: 0.03
Nodes (69): diveLease, providerConflict, push, pushRun, waitingPush, kept(), record(), ExportingProvider (+61 more)

### Community 20 - "Community 20"
Cohesion: 0.05
Nodes (78): OVERRIDABLE_FIELDS, OverridableField, AppendRow, channelsOf(), createContinuationService(), takeIn(), cylindersBack(), DiveRow (+70 more)

### Community 21 - "Community 21"
Cohesion: 0.05
Nodes (44): DiveBody(), FactRow(), Facts(), NeighbourButton(), Neighbours(), Profile(), ProfileChart(), profileSummaryText() (+36 more)

### Community 22 - "Community 22"
Cohesion: 0.05
Nodes (68): Auth, invitation, userRole, AttemptLimit, clientKey(), limitAttempts(), ADR-0061, Problem (+60 more)

### Community 23 - "Community 23"
Cohesion: 0.07
Nodes (68): buildApp(), assessmentRoutes(), AI_ACCESS_NAME_MAX, SessionUser, authHandler(), authHeaders(), fastify, FastifyRequest (+60 more)

### Community 24 - "Community 24"
Cohesion: 0.05
Nodes (70): ComputerChoice, DiveImportMode, DiveKind, ImportErrorCode, ImportFound, importOriginal, ImportOutcome, providerSiteData (+62 more)

### Community 25 - "Community 25"
Cohesion: 0.05
Nodes (59): errorMessage(), looksLikeZip(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy() (+51 more)

### Community 26 - "Community 26"
Cohesion: 0.06
Nodes (65): diveSite, settleSiteVisibility(), outgoingSite(), findOrMake(), SiteForSend, siteForSend(), ADR-0057, createSitePickService() (+57 more)

### Community 27 - "Community 27"
Cohesion: 0.05
Nodes (54): Queryable, Tx, device, diverBodyWeight, diverManagement, participant, renumberStatement(), ADR-0048 (+46 more)

### Community 28 - "Community 28"
Cohesion: 0.04
Nodes (51): ProviderImportPlan, PushLeftOut, Capabilities, Direction, DiveExport, DiveListing, FIND_BY, FindBy (+43 more)

### Community 29 - "Community 29"
Cohesion: 0.04
Nodes (64): pushRunItem, PushRunLeftOut, DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN, NO_RECORDING (+56 more)

### Community 30 - "Community 30"
Cohesion: 0.06
Nodes (15): DiveHubTheme(), LogbookScreenshotTest, ApiDivesGet200Response, ApiDivesGet200ResponseDivesItem, ApiDivesGet200ResponseDivesItemGasesItem, ApiDivesGet200ResponseDivesItemParticipantsItem, ApiDivesGet200ResponseDivesItemSite, ApiDivesGet200ResponseMonthsItem (+7 more)

### Community 31 - "Community 31"
Cohesion: 0.07
Nodes (61): diveFingerprint, waitingOf(), close(), currentRemote(), PushRow, ADR-0024, Choice, isExporting() (+53 more)

### Community 32 - "Community 32"
Cohesion: 0.05
Nodes (50): UTC_OFFSET_SOURCES, downsampleMinMax(), LogbookCheckService, AppendDeps, AppendOutcome, AppendService, Side, ADR-0038 (+42 more)

### Community 33 - "Community 33"
Cohesion: 0.05
Nodes (60): AiAccessScope, diversOf(), SiteRow, Cursor, cursorAt(), DateOnly(), defineTool(), Detail (+52 more)

### Community 34 - "Community 34"
Cohesion: 0.05
Nodes (63): buddiesForQuery(), buddiesQuery(), BuddyView, DiveImportMode, DiveImportPreview, DiveKind, fetchBuddies(), GoneDivesOfConnection (+55 more)

### Community 35 - "Community 35"
Cohesion: 0.05
Nodes (62): RecordingSummary, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput, round3() (+54 more)

### Community 36 - "Community 36"
Cohesion: 0.06
Nodes (60): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+52 more)

### Community 37 - "Community 37"
Cohesion: 0.07
Nodes (10): DiveDetailScreenshotTest, DiveViewFromRecording, DiveViewFromRecordingStartsAt, DiveViewRecordingsItemDevice, DiveViewValues, DiveViewValuesStartsAt, DiveViewWaterMismatch, NeighboursView (+2 more)

### Community 38 - "Community 38"
Cohesion: 0.08
Nodes (63): renumber(), providerCylinderOf(), participantsOf(), columnsOf(), kindOfMode(), deviceDiver(), closeFields(), entryOf() (+55 more)

### Community 39 - "Community 39"
Cohesion: 0.06
Nodes (55): audienceOfDiver(), open(), automationOffers(), AutomationSettings, ConnectionService, createConnectionService(), checkAutomation(), checkFollowNumbers() (+47 more)

### Community 40 - "Community 40"
Cohesion: 0.06
Nodes (53): Delivered, DIVE_FIELDS, ProviderError, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord() (+45 more)

### Community 41 - "Community 41"
Cohesion: 0.06
Nodes (51): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+43 more)

### Community 42 - "Community 42"
Cohesion: 0.06
Nodes (56): SuitType, SuitUndergarment, WeightingFelt, Suit, WeightingFeedback, answer(), baseFrom(), cylindersAtReserve() (+48 more)

### Community 43 - "Community 43"
Cohesion: 0.06
Nodes (46): providerLease, tidyName(), DiverDetails, Caller, foldName(), nameDiffers(), Buddy, createBuddyService() (+38 more)

### Community 44 - "Community 44"
Cohesion: 0.05
Nodes (49): createSsiClient(), Card, DEEP, OWD, Report, Summary, ADR-0050, WRECK (+41 more)

### Community 45 - "Community 45"
Cohesion: 0.07
Nodes (5): ApiClient, ApiErrorBody, json(), LogbookRepositoryTest, RepositoryHarness

### Community 46 - "Community 46"
Cohesion: 0.08
Nodes (52): withBuddies(), APP_KEYS, appRow(), BuddySaved, byId(), changedKeys(), deleteTestDive(), deleteTestEntries() (+44 more)

### Community 47 - "Community 47"
Cohesion: 0.07
Nodes (45): SsiClient, SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), buildUpdateRecord(), DiveForSsi, emptyRecord() (+37 more)

### Community 48 - "Community 48"
Cohesion: 0.05
Nodes (51): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, cancelWaitingImports() (+43 more)

### Community 49 - "Community 49"
Cohesion: 0.05
Nodes (43): continuationAppend, CylinderMaterial, equipmentItem, managesDiveDiver(), CandidateError, createCandidateService(), lockMine(), tieToJoined() (+35 more)

### Community 50 - "Community 50"
Cohesion: 0.06
Nodes (46): jointDive, alignedForMatching(), CandidateDive, decideMatch(), depthsDisagree(), DuplicateReason, MatchDecision, MAX_OFFSET_MS (+38 more)

### Community 51 - "Community 51"
Cohesion: 0.08
Nodes (23): DiveViewRecordingsItem, DiveViewRecordingsItemSummary, DiveViewRecordingsItemSummarySurfaceBreaksItem, RecordingSamplesView, RecordingSamplesViewSeriesItem, computerRecordings(), depthSeries(), nearest() (+15 more)

### Community 52 - "Community 52"
Cohesion: 0.07
Nodes (47): EMPTY_SITE_IMPORT_COUNTS, ImportArea, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053 (+39 more)

### Community 53 - "Community 53"
Cohesion: 0.09
Nodes (50): logbookCheckAnswer, logbookCheckDiveAnswer, recording, keepApart(), keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration() (+42 more)

### Community 54 - "Community 54"
Cohesion: 0.05
Nodes (51): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, CertificationViewSchema, DiveProfileViewSchema, WaitingPushViewSchema (+43 more)

### Community 55 - "Community 55"
Cohesion: 0.06
Nodes (36): cylinder, dive, duplicateCandidate, importJob, ImportService, ADR-0044, Cylinder, DiveView (+28 more)

### Community 56 - "Community 56"
Cohesion: 0.07
Nodes (45): Leave, Series, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+37 more)

### Community 57 - "Community 57"
Cohesion: 0.06
Nodes (34): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+26 more)

### Community 58 - "Community 58"
Cohesion: 0.08
Nodes (45): NoFly, noFlyAfter(), residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows(), DiveRow, endMsOf() (+37 more)

### Community 59 - "Community 59"
Cohesion: 0.08
Nodes (39): BottomTimeQuery, DEFAULT_GF_HIGH, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033, ADR-0071 (+31 more)

### Community 60 - "Community 60"
Cohesion: 0.06
Nodes (38): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+30 more)

### Community 61 - "Community 61"
Cohesion: 0.06
Nodes (12): MainViewModel, UiState, ApiMeGet200Response, ApiMeGet200ResponsePreferences, SignInEmailRequest, UserView, InstanceUrl, Loading (+4 more)

### Community 62 - "Community 62"
Cohesion: 0.07
Nodes (39): hashPassword(), createServices(), Services, ADR-0027, ADR-0030, ADR-0061, ADR-0063, ADR-0066 (+31 more)

### Community 63 - "Community 63"
Cohesion: 0.08
Nodes (46): ConnectionRow, WaitingPushOptions, audienceOfConnection(), checkAll(), removeAt(), clearIfUnchanged(), decide(), IMPORT_CAUSES (+38 more)

### Community 64 - "Community 64"
Cohesion: 0.07
Nodes (40): BottomTime, bottomTime(), NOAA_ROWS, otu(), oxygenLimitMinutes(), ADR-0032, AIR, BOTTOM_TIME_CASES (+32 more)

### Community 65 - "Community 65"
Cohesion: 0.05
Nodes (36): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError (+28 more)

### Community 66 - "Community 66"
Cohesion: 0.07
Nodes (40): MATCHING_WINDOWS, ConflictEntry, ConflictService, createConflictService(), loadWaiting(), stillDiffering(), fieldsOf(), isExists() (+32 more)

### Community 67 - "Community 67"
Cohesion: 0.09
Nodes (44): DiveGearItemView, diveGearQuery(), DiveGearView, EquipmentCategory, EquipmentDetailView, EquipmentDueView, equipmentItemQuery(), EquipmentItemView (+36 more)

### Community 68 - "Community 68"
Cohesion: 0.05
Nodes (42): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+34 more)

### Community 69 - "Community 69"
Cohesion: 0.08
Nodes (27): AuthOptions, auth, BackupServiceDeps, ServiceParts, Db, out, services, createSecretBox() (+19 more)

### Community 70 - "Community 70"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 71 - "Community 71"
Cohesion: 0.07
Nodes (40): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+32 more)

### Community 72 - "Community 72"
Cohesion: 0.06
Nodes (40): SUIT_UNDERGARMENTS, ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, seriesOf(), minute() (+32 more)

### Community 73 - "Community 73"
Cohesion: 0.06
Nodes (35): CYLINDER_MATERIALS, SUIT_TYPES, DiverCertifications, replyDiverError, STATUS, CertificationSummary, DiverDetailsView, DiverDetailsViewSchema (+27 more)

### Community 74 - "Community 74"
Cohesion: 0.06
Nodes (23): Hint, HINT_CHANNEL, HINT_KINDS, HintAudience, HintKind, kinds, ADR-0020, ADR-0066 (+15 more)

### Community 75 - "Community 75"
Cohesion: 0.06
Nodes (38): Change(), Changes(), historyRoute, Made(), REVISION_CAUSES, PositionSchema, AdoptBody, Country (+30 more)

### Community 76 - "Community 76"
Cohesion: 0.07
Nodes (33): sampleCount(), SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject() (+25 more)

### Community 77 - "Community 77"
Cohesion: 0.08
Nodes (36): assessmentQuery(), DiveValues, OverridableField, revisionsQuery(), RevisionView, UtcOffsetSource, AsideItem(), AssessmentContext (+28 more)

### Community 78 - "Community 78"
Cohesion: 0.09
Nodes (16): durationMinutes(), initials(), mixName(), LogbookPaging, AscentBand, SketchCurve, SketchGeometry, sketchGeometry() (+8 more)

### Community 79 - "Community 79"
Cohesion: 0.07
Nodes (33): AssessmentService, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView(), ADR-0036 (+25 more)

### Community 80 - "Community 80"
Cohesion: 0.10
Nodes (32): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0051 (+24 more)

### Community 81 - "Community 81"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 82 - "Community 82"
Cohesion: 0.13
Nodes (8): FakeCache, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore, UserScopedStore

### Community 83 - "Community 83"
Cohesion: 0.06
Nodes (36): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+28 more)

### Community 84 - "Community 84"
Cohesion: 0.08
Nodes (35): @modelcontextprotocol/server@^2.3.1, aiAccessLog, aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, AiAccessService, KeyRow (+27 more)

### Community 85 - "Community 85"
Cohesion: 0.08
Nodes (37): cylinderOf(), cylinderText(), diverOf(), diverText(), finite(), kept, LEAD_MATERIALS, LEAD_WATERS (+29 more)

### Community 86 - "Community 86"
Cohesion: 0.08
Nodes (31): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+23 more)

### Community 87 - "Community 87"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 88 - "Community 88"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 89 - "Community 89"
Cohesion: 0.10
Nodes (33): lead, LeadPlacement, ParticipantRole, managedDiverIds(), DiveRow, LeadEntry, leadOf(), leadTotalKg() (+25 more)

### Community 90 - "Community 90"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 91 - "Community 91"
Cohesion: 0.08
Nodes (32): BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, Conditions, GasMix, DiverPlan (+24 more)

### Community 92 - "Community 92"
Cohesion: 0.07
Nodes (32): fit-file-parser@^6.1.2, geo-tz@8.1.9, yauzl@^3.4.0, devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1 (+24 more)

### Community 93 - "Community 93"
Cohesion: 0.11
Nodes (28): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+20 more)

### Community 94 - "Community 94"
Cohesion: 0.09
Nodes (32): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+24 more)

### Community 95 - "Community 95"
Cohesion: 0.08
Nodes (27): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+19 more)

### Community 96 - "Community 96"
Cohesion: 0.16
Nodes (32): createSsiTransport(), acceptedFormat(), Answer, dateTime(), deleteAndCheck(), describe(), findNewId(), Format (+24 more)

### Community 97 - "Community 97"
Cohesion: 0.14
Nodes (18): AvatarCircle(), DiveRow(), Error(), Fact(), Filters(), Loading(), LogbookList(), LogbookRoute() (+10 more)

### Community 98 - "Community 98"
Cohesion: 0.12
Nodes (4): DiveStore, toModel(), DiveSite, Recording

### Community 99 - "Community 99"
Cohesion: 0.09
Nodes (28): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+20 more)

### Community 100 - "Community 100"
Cohesion: 0.14
Nodes (27): getDiverLoadings, HINT, Input, LoadingsInput, planGasAndBottomTime, ADR-0032, ADR-0035, Used() (+19 more)

### Community 101 - "Community 101"
Cohesion: 0.11
Nodes (26): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), typeOf() (+18 more)

### Community 102 - "Community 102"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 103 - "Community 103"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 104 - "Community 104"
Cohesion: 0.12
Nodes (25): samplesQuery(), FindingLane(), useAssessment(), useWhen(), DepthProfile(), NO_POINTS, SurfaceBreakLane(), ADR-0036 (+17 more)

### Community 105 - "Community 105"
Cohesion: 0.09
Nodes (26): ENGINE_VERSION, EVIDENCE, Evidence, FindingValues, RuleId, SEVERITIES, Severity, SourceId (+18 more)

### Community 106 - "Community 106"
Cohesion: 0.11
Nodes (28): AscentPressure, DiverPlan, GasPlan, GasPlanDiver, GasPlanInput, GasPlanLevel, GasPlanLimit, depth (+20 more)

### Community 109 - "Community 109"
Cohesion: 0.10
Nodes (22): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearQueue(), conflictAtSsi() (+14 more)

### Community 110 - "Community 110"
Cohesion: 0.11
Nodes (7): ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport

### Community 111 - "Community 111"
Cohesion: 0.19
Nodes (23): ambientPressureBar(), bestMixPercent(), Conditions, depthOfPressure(), eadMetres(), endMetres(), GasMix, isHypoxic() (+15 more)

### Community 112 - "Community 112"
Cohesion: 0.12
Nodes (23): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+15 more)

### Community 113 - "Community 113"
Cohesion: 0.11
Nodes (24): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+16 more)

### Community 114 - "Community 114"
Cohesion: 0.14
Nodes (24): surfacePressureBar(), ndlMinutes(), ADR-0032, ADR-0046, evaluateTissues(), advanceTissues(), schreinerStep(), surfaceSaturatedTissues() (+16 more)

### Community 115 - "Community 115"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 116 - "Community 116"
Cohesion: 0.11
Nodes (24): equipmentUse, DiveGear, ensureItemForDevice(), GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow (+16 more)

### Community 117 - "Community 117"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 118 - "Community 118"
Cohesion: 0.16
Nodes (24): DiveOrigin, REFERENCE_PREFIX, SsiLogbook, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef() (+16 more)

### Community 119 - "Community 119"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 120 - "Community 120"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 121 - "Community 121"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 122 - "Community 122"
Cohesion: 0.12
Nodes (21): CENTRE_SOURCES, CentreService, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+13 more)

### Community 123 - "Community 123"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 125 - "Community 125"
Cohesion: 0.15
Nodes (19): formatOf(), createImportService(), cancelImport(), createImport(), endWaiting(), expireWaiting(), openUpload(), processFile() (+11 more)

### Community 126 - "Community 126"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 127 - "Community 127"
Cohesion: 0.13
Nodes (17): providerGoneDive, compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051, candidatesOf() (+9 more)

### Community 128 - "Community 128"
Cohesion: 0.12
Nodes (14): LoadingsView, CustomTypeOptions, i18next, initI18n(), resources, ADR-0014, LOADING_SOURCES, loadingNumber() (+6 more)

### Community 129 - "Community 129"
Cohesion: 0.15
Nodes (18): BottomTime, bottomTime(), BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen (+10 more)

### Community 130 - "Community 130"
Cohesion: 0.14
Nodes (17): createFileFormats(), createFitAdapter(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf() (+9 more)

### Community 131 - "Community 131"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 132 - "Community 132"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 133 - "Community 133"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 134 - "Community 134"
Cohesion: 0.13
Nodes (18): PARTICIPANT_ROLES, SUGGESTION_STATUSES, DiveProfileView, AcceptBody, STATUS, SuggestionRouteDeps, ADR-0070, SuggestionService (+10 more)

### Community 135 - "Community 135"
Cohesion: 0.13
Nodes (18): siteImport, siteImportStatus, PROBLEM_CODES, SiteImportRouteDeps, StartBody, STATUS, ADR-0021, SiteImportService (+10 more)

### Community 136 - "Community 136"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 137 - "Community 137"
Cohesion: 0.19
Nodes (17): AssessmentView, AssessmentState, clock(), counts(), differing(), Finding, formatRate(), hidden() (+9 more)

### Community 138 - "Community 138"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 139 - "Community 139"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 140 - "Community 140"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 141 - "Community 141"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 142 - "Community 142"
Cohesion: 0.16
Nodes (13): BOM, Json, num(), obj(), positive(), scaled(), suuntoFitKey(), SuuntoJsonAdapter (+5 more)

### Community 143 - "Community 143"
Cohesion: 0.12
Nodes (16): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+8 more)

### Community 144 - "Community 144"
Cohesion: 0.18
Nodes (11): planningSac(), PlanningSacDive, PlanningSacResult, PlanningSacSource, default, logbook, compressibility(), Cubic (+3 more)

### Community 145 - "Community 145"
Cohesion: 0.12
Nodes (16): ADR-0013, ADR-0026, ADR-0028, ADR-0030, ADR-0031, ADR-0034, ADR-0035, ADR-0036 (+8 more)

### Community 146 - "Community 146"
Cohesion: 0.17
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 147 - "Community 147"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 148 - "Community 148"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 149 - "Community 149"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 150 - "Community 150"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 151 - "Community 151"
Cohesion: 0.26
Nodes (14): jsRound(), powInt(), roundTo(), Sac, SacCylinder, SacDive, SacMissing, cylinder_incomplete (+6 more)

### Community 152 - "Community 152"
Cohesion: 0.16
Nodes (13): certification, createUserAdminService(), keepAnAdmin(), lock(), deleteUserData(), ADR-0013, ADR-0024, ADR-0034 (+5 more)

### Community 153 - "Community 153"
Cohesion: 0.16
Nodes (14): serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, DueEntry, ScheduleView, equipmentList(), EquipmentListFilter (+6 more)

### Community 154 - "Community 154"
Cohesion: 0.16
Nodes (12): AssessmentRefresh, ADR-0036, ADR-0053, CandidateRouteDeps, CandidateView, Decided, DiveBrief, OffsetSource (+4 more)

### Community 155 - "Community 155"
Cohesion: 0.19
Nodes (11): decimal(), dive(), makeOceanicPlusUddf(), OCEANIC_DIVES, OceanicDive, site(), waypoint(), exported (+3 more)

### Community 156 - "Community 156"
Cohesion: 0.14
Nodes (7): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, headers, uniqueWord()

### Community 157 - "Community 157"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 158 - "Community 158"
Cohesion: 0.18
Nodes (11): addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, relations(), TimelineBar, TimelineBarGeometry, TimelineGapGeometry, TimelineGeometry (+3 more)

### Community 159 - "Community 159"
Cohesion: 0.15
Nodes (12): EquipmentError, ADR-0034, ADR-0053, checkCost(), checkDates(), checkWindow(), checkResets(), checkSchedule() (+4 more)

### Community 160 - "Community 160"
Cohesion: 0.22
Nodes (11): fromOsm(), fromSsi(), fromWd(), HOUSE_REEF, osm(), site(), ssi(), ADR-0021 (+3 more)

### Community 161 - "Community 161"
Cohesion: 0.31
Nodes (12): DepthPlot(), LINE_DASHES, plotFont(), PressureStrip(), RIGHT_AXIS, token(), ADR-0045, useColorScheme() (+4 more)

### Community 162 - "Community 162"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 163 - "Community 163"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 164 - "Community 164"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 166 - "Community 166"
Cohesion: 0.17
Nodes (5): BodyWeight, Cylinder, Dive, Diver, RecordingSeries

### Community 167 - "Community 167"
Cohesion: 0.32
Nodes (12): createEquipmentService(), assemble(), detailOf(), lastCylinderOf(), managedItem(), recordsOf(), schedulesFor(), schedulesOf() (+4 more)

### Community 168 - "Community 168"
Cohesion: 0.29
Nodes (12): columnsOf(), createSiteImportService(), create(), loadExisting(), update(), same(), valuesOf(), allPublicIds() (+4 more)

### Community 169 - "Community 169"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 170 - "Community 170"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 171 - "Community 171"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 172 - "Community 172"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 173 - "Community 173"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 174 - "Community 174"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 176 - "Community 176"
Cohesion: 0.24
Nodes (8): Config, loadConfig(), ADR-0011, ADR-0021, ADR-0024, ADR-0025, parseEncryptionKey(), base

### Community 177 - "Community 177"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 178 - "Community 178"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 179 - "Community 179"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 181 - "Community 181"
Cohesion: 0.25
Nodes (8): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, MODULE_IMPORTS, moduleRules, target()

### Community 182 - "Community 182"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 183 - "Community 183"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 184 - "Community 184"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 186 - "Community 186"
Cohesion: 0.29
Nodes (6): itemIds(), ADR-0026, ADR-0034, Usage, UsageDive, UsageRow

### Community 187 - "Community 187"
Cohesion: 0.33
Nodes (5): IMPORT_SOURCES, SOURCE_INFO, SourceInfo, ADR-0021, ADR-0021

### Community 188 - "Community 188"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 189 - "Community 189"
Cohesion: 0.47
Nodes (4): ownersParts(), parse(), toPart(), ADR-0063

### Community 190 - "Community 190"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 191 - "Community 191"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 193 - "Community 193"
Cohesion: 0.40
Nodes (4): label(), CompareSubject, facts, subjects

### Community 194 - "Community 194"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 195 - "Community 195"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 198 - "Community 198"
Cohesion: 0.67
Nodes (3): planSketch(), shape(), ADR-0033

### Community 200 - "Community 200"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 201 - "Community 201"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 202 - "Community 202"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1230 isolated node(s):** `Reserve`, `RockBottom`, `Off`, `ADR-0031`, `ADR-0032` (+1225 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3274 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **59 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 1` to `Community 4`, `Community 6`, `Community 8`, `Community 9`, `Community 13`, `Community 14`, `Community 19`, `Community 20`, `Community 22`, `Community 24`, `Community 153`, `Community 26`, `Community 27`, `Community 152`, `Community 29`, `Community 31`, `Community 32`, `Community 33`, `Community 35`, `Community 39`, `Community 43`, `Community 44`, `Community 49`, `Community 50`, `Community 52`, `Community 53`, `Community 54`, `Community 55`, `Community 186`, `Community 59`, `Community 58`, `Community 62`, `Community 63`, `Community 65`, `Community 66`, `Community 69`, `Community 74`, `Community 83`, `Community 84`, `Community 89`, `Community 92`, `Community 94`, `Community 99`, `Community 112`, `Community 116`, `Community 127`?**
  _High betweenness centrality (0.031) - this node is a cross-community bridge._
- **What connects `Reserve`, `RockBottom`, `Off` to the rest of the system?**
  _1230 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.021815261044176706 - nodes in this community are weakly interconnected._
- **Why does `typescript` connect `Community 141` to `Community 131`, `Community 92`, `Community 103`?**
  _High betweenness centrality (0.022) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.033305462653288744 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 11` to `Community 0`, `Community 34`, `Community 67`, `Community 3`, `Community 5`, `Community 133`, `Community 7`, `Community 103`, `Community 104`, `Community 2`, `Community 12`, `Community 77`, `Community 81`, `Community 18`?**
  _High betweenness centrality (0.020) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.02458224669178016 - nodes in this community are weakly interconnected._