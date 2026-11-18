# Graph Report - dive_hub  (2026-11-18)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 8037 nodes · 24336 edges · 207 communities (180 shown, 27 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 248 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `e87f82d4`
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
- Community 181
- Community 182
- Community 183
- Community 184
- Community 185
- Community 186
- Community 189

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
- `Harness` --calls--> `SessionRepository`  [INFERRED]
  apps/android/shared/src/commonTest/kotlin/dev/divehub/shared/SessionRepositoryTest.kt → apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/SessionRepository.kt

## Import Cycles
- None detected.

## Communities (207 total, 27 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (181): apikey, user, buddySuggestion, diveLease, revision, ADR-0013, ADR-0011, dumpToolAvailable() (+173 more)

### Community 1 - "Community 1"
Cohesion: 0.02
Nodes (222): cylinderCatalogueQuery(), CylinderFields(), useCatalogueName(), useCylinderProblem(), DiveEditForm(), Draft, draftOf(), FIELD_OF (+214 more)

### Community 2 - "Community 2"
Cohesion: 0.05
Nodes (171): DeleteUserDialog(), Invite(), LinkToPassOn(), UserRow(), CreateAccess(), bodyWeightsQuery(), certificationsQuery(), devicesQuery() (+163 more)

### Community 3 - "Community 3"
Cohesion: 0.02
Nodes (117): dive, duplicateCandidate, ImportErrorCode, ImportFound, importOriginal, original, recording, recordingEvent (+109 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (133): Queryable, CylinderMaterial, lead, LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, renumber(), renumberStatement() (+125 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (115): divesQuery(), LogbookPage, PAGE_SIZE, CodeFigure(), DiveCodes(), showDiveCodes(), ADR-0043, Buddies() (+107 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (124): Me, AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView (+116 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (124): importsQuery(), ImportView, ExternalIdView, Position, running(), SiteImportArea, siteImportsQuery(), SiteImportView (+116 more)

### Community 8 - "Community 8"
Cohesion: 0.02
Nodes (121): account, accountRelations, rateLimit, session, sessionRelations, ADR-0011, ADR-0012, ADR-0035 (+113 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (121): jointDive, logbookCheckAnswer, logbookCheckDiveAnswer, providerSiteData, agreeInDepthAndDuration(), CheckedDive, DeletableDive, findChecks() (+113 more)

### Community 10 - "Community 10"
Cohesion: 0.04
Nodes (114): diveFingerprint, PushLeftOut, WaitingPushOptions, waitingOf(), audienceOfConnection(), audienceOfDive(), audienceOfDiver(), Hint (+106 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (87): auth, BackupServiceDeps, createServices(), ServiceParts, ADR-0027, ADR-0030, ADR-0061, ADR-0063 (+79 more)

### Community 12 - "Community 12"
Cohesion: 0.04
Nodes (105): diveSite, SiteImportFinding, SiteImportProgress, siteSource, settleSiteVisibility(), keyLease(), outgoingSite(), findOrMake() (+97 more)

### Community 13 - "Community 13"
Cohesion: 0.03
Nodes (90): BACKUP_RUN_TASK, backupTask(), ADR-0069, connection, ConnectionRow, importJob, ImportOutcome, providerConflict (+82 more)

### Community 14 - "Community 14"
Cohesion: 0.04
Nodes (110): ComputerChoice, DiveImportMode, DiveKind, providerCylinderOf(), participantsOf(), siteRef(), columnsOf(), leadOf() (+102 more)

### Community 15 - "Community 15"
Cohesion: 0.03
Nodes (27): DiveHubApp(), ErrorText(), InstanceForm(), Loading(), PasswordField(), PrimaryButton(), SignInForm(), Spinner() (+19 more)

### Community 16 - "Community 16"
Cohesion: 0.04
Nodes (84): setupRestoreArchivesQuery(), SiteDataView, ADR-0030, CertificationView, DeviceView, DiverDetailsView, ExternalDiverView, FoundDiverView (+76 more)

### Community 17 - "Community 17"
Cohesion: 0.04
Nodes (84): CertificationView, CertificationViewSchema, Day, ADR-0050, DIVE_IMPORT_MODES, DIVE_KINDS, UTC_OFFSET_SOURCES, AssessmentRefresh (+76 more)

### Community 18 - "Community 18"
Cohesion: 0.04
Nodes (94): importQuery(), ConflictChoices, ConflictFieldView, ConnectionView, diveImportPreviewQuery(), diveTimesQuery(), providerStatusQuery(), ProviderView (+86 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (80): providerGoneDive, open(), automationOffers(), AutomationSettings, createConnectionService(), checkAutomation(), checkFollowNumbers(), expiryOf() (+72 more)

### Community 20 - "Community 20"
Cohesion: 0.03
Nodes (67): createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities(), ADR-0062 (+59 more)

### Community 21 - "Community 21"
Cohesion: 0.04
Nodes (81): DUE_STATES, AiAccessScope, seesPositions(), VerifiedAccess, diversOf(), SiteRow, checkedByRunTool, createMcpServer() (+73 more)

### Community 22 - "Community 22"
Cohesion: 0.06
Nodes (79): ChangePassword(), DisplaySettings(), SecuritySettings(), Sessions(), useDescribeAgent(), AdminSettings(), Invitations(), ProviderSiteData() (+71 more)

### Community 23 - "Community 23"
Cohesion: 0.06
Nodes (67): LoadingsView, assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider() (+59 more)

### Community 24 - "Community 24"
Cohesion: 0.05
Nodes (75): continuationCandidateQuery(), ContinuationCandidateView, deletedDivesOn(), deletedDivesQuery(), diveQuery(), LogbookCheckView, mergeCandidatesQuery(), MergeCandidateView (+67 more)

### Community 25 - "Community 25"
Cohesion: 0.03
Nodes (75): LEAD_PLACEMENTS, WEIGHTING_FELT, ADR-0025, WaterMismatch, waterMismatch(), DiveCertifications, withoutPressures(), CylinderInput (+67 more)

### Community 26 - "Community 26"
Cohesion: 0.07
Nodes (71): buildApp(), assessmentRoutes(), AI_ACCESS_NAME_MAX, SessionUser, authHandler(), authHeaders(), fastify, FastifyRequest (+63 more)

### Community 27 - "Community 27"
Cohesion: 0.04
Nodes (61): sessionIdOf(), AttemptLimit, clientKey(), limitAttempts(), ADR-0061, ProblemCode, ResponseState, whenResponseGone() (+53 more)

### Community 28 - "Community 28"
Cohesion: 0.04
Nodes (59): Tx, diverBodyWeight, diverManagement, participant, DiverError, ADR-0016, ADR-0053, ACCOUNT_PATTERN (+51 more)

### Community 29 - "Community 29"
Cohesion: 0.03
Nodes (57): ParticipantRole, ActionContext, Direction, DiveExport, DiveListing, FIND_BY, FindBy, Operation (+49 more)

### Community 30 - "Community 30"
Cohesion: 0.05
Nodes (56): createServer(), createAuth(), loadAuthSecret(), acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS (+48 more)

### Community 31 - "Community 31"
Cohesion: 0.06
Nodes (66): BottomTimeInput, ambientPressureBar(), bestMixPercent(), Conditions, DENSITY, depthOfPressure(), eadMetres(), endMetres() (+58 more)

### Community 32 - "Community 32"
Cohesion: 0.06
Nodes (68): diversQuery(), allButDive(), diveNeighboursQuery(), DiveValues, DiveView, OverridableField, revisionsQuery(), diveProvidersQuery() (+60 more)

### Community 33 - "Community 33"
Cohesion: 0.05
Nodes (67): BuddyView, DiveImportMode, DiveImportPreview, DiveKind, LeftOutView, providerSitesQuery(), pushRunActive(), pushRunBody() (+59 more)

### Community 34 - "Community 34"
Cohesion: 0.05
Nodes (54): downsampleMinMax(), AppendDeps, AppendOutcome, AppendService, Side, ADR-0038, ADR-0063, ContinuationService (+46 more)

### Community 35 - "Community 35"
Cohesion: 0.06
Nodes (15): LogbookScreenshotTest, ApiDivesGet200Response, ApiDivesGet200ResponseDivesItem, ApiDivesGet200ResponseDivesItemGasesItem, ApiDivesGet200ResponseDivesItemParticipantsItem, ApiDivesGet200ResponseDivesItemSite, ApiDivesGet200ResponseMonthsItem, ApiDivesGet200ResponseTotals (+7 more)

### Community 36 - "Community 36"
Cohesion: 0.05
Nodes (56): createSsiAdapter(), createSsiClient(), ssiSiteRow(), Card, DEEP, OWD, Report, Summary (+48 more)

### Community 37 - "Community 37"
Cohesion: 0.04
Nodes (53): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+45 more)

### Community 38 - "Community 38"
Cohesion: 0.07
Nodes (53): SsiRecord, contextOf(), asNumber(), asText(), buildCreateRecord(), buildDeleteRecord(), buildUpdateRecord(), compareReadBack() (+45 more)

### Community 39 - "Community 39"
Cohesion: 0.07
Nodes (58): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+50 more)

### Community 40 - "Community 40"
Cohesion: 0.04
Nodes (52): AssessmentService, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView(), ADR-0036 (+44 more)

### Community 41 - "Community 41"
Cohesion: 0.07
Nodes (52): replyCertificationError, STATUS, CertificationService, PARTICIPANT_ROLES, Problem, ProblemSchema, storedProblemCode(), ADR-0014 (+44 more)

### Community 42 - "Community 42"
Cohesion: 0.05
Nodes (50): ADR-0036, headers, ADR-0030, ADR-0038, ADR-0048, ADR-0062, AiAccess, assessedDive() (+42 more)

### Community 43 - "Community 43"
Cohesion: 0.06
Nodes (12): MainViewModel, UiState, ApiMeGet200Response, ApiMeGet200ResponsePreferences, SignInEmailRequest, UserView, InstanceUrl, Loading (+4 more)

### Community 44 - "Community 44"
Cohesion: 0.08
Nodes (50): BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32Encode(), bech32HrpExpand(), bech32Polymod() (+42 more)

### Community 45 - "Community 45"
Cohesion: 0.05
Nodes (39): Delivered, DiveExportAction, buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open() (+31 more)

### Community 46 - "Community 46"
Cohesion: 0.07
Nodes (41): ImportArea, createSsiSiteSource(), DEFAULT_SSI_SITES_URL, createSiteSources(), ADR-0021, ADR-0027, createOverpassSource(), DEFAULT_OVERPASS_URL (+33 more)

### Community 47 - "Community 47"
Cohesion: 0.06
Nodes (34): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+26 more)

### Community 48 - "Community 48"
Cohesion: 0.06
Nodes (9): AppContainer, PreferencesInstanceUrlStore, DiveHubApplication, LogbookViewModel, DiveHubException, NoAnswerException, LogbookRepository, ProviderDirectory (+1 more)

### Community 49 - "Community 49"
Cohesion: 0.07
Nodes (22): ApiDivesGet200ResponseCounts, durationMinutes(), initials(), mixName(), countOf(), FilterChip, filterChips(), LogbookFilter (+14 more)

### Community 50 - "Community 50"
Cohesion: 0.06
Nodes (45): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, Services, DiveProfileViewSchema, WaitingPushViewSchema (+37 more)

### Community 51 - "Community 51"
Cohesion: 0.07
Nodes (43): EMPTY_SITE_IMPORT_COUNTS, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, Context (+35 more)

### Community 52 - "Community 52"
Cohesion: 0.08
Nodes (41): DiveField, Leave, asBool(), DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi() (+33 more)

### Community 53 - "Community 53"
Cohesion: 0.07
Nodes (5): ApiClient, ApiErrorBody, json(), LogbookRepositoryTest, RepositoryHarness

### Community 54 - "Community 54"
Cohesion: 0.08
Nodes (45): SuitType, SuitUndergarment, WeightingFelt, Suit, WeightingFeedback, answer(), baseFrom(), cylindersAtReserve() (+37 more)

### Community 55 - "Community 55"
Cohesion: 0.08
Nodes (42): tidyName(), DiverDetails, Caller, foldName(), nameDiffers(), Buddy, createBuddyService(), createFrom() (+34 more)

### Community 56 - "Community 56"
Cohesion: 0.08
Nodes (36): sacOfDive(), BottomTimeQuery, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033, ADR-0071 (+28 more)

### Community 57 - "Community 57"
Cohesion: 0.06
Nodes (42): SUIT_UNDERGARMENTS, ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, seriesOf(), minute() (+34 more)

### Community 58 - "Community 58"
Cohesion: 0.08
Nodes (32): diveWith(), ADR-0028, ADR-0043, buddySite(), Dive, OTHER, resetSource(), ADR-0012 (+24 more)

### Community 59 - "Community 59"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 60 - "Community 60"
Cohesion: 0.06
Nodes (30): pushRun, providerError(), ExportingProvider, ProviderError, Candidate, Deleted, DiveView, Outcome (+22 more)

### Community 61 - "Community 61"
Cohesion: 0.07
Nodes (40): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+32 more)

### Community 62 - "Community 62"
Cohesion: 0.06
Nodes (36): CYLINDER_MATERIALS, SUIT_TYPES, DiverCertifications, replyDiverError, STATUS, DiverService, CertificationSummary, DiverDetailsView (+28 more)

### Community 63 - "Community 63"
Cohesion: 0.06
Nodes (37): Change(), Changes(), historyRoute, Made(), PositionSchema, AdoptBody, Country, CreateBody (+29 more)

### Community 64 - "Community 64"
Cohesion: 0.06
Nodes (30): headers, ADR-0035, diveRow(), rowOf(), ADR-0026, ADR-0048, aiAccessReady(), askMcp() (+22 more)

### Community 65 - "Community 65"
Cohesion: 0.05
Nodes (36): AREA_OF, areaOf(), areas, capture(), findings, headers, ADR-0020, ADR-0021 (+28 more)

### Community 66 - "Community 66"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 67 - "Community 67"
Cohesion: 0.13
Nodes (8): FakeCache, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore, UserScopedStore

### Community 68 - "Community 68"
Cohesion: 0.07
Nodes (32): sampleCount(), rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject(), MAX_CARD_IMAGE_BYTES (+24 more)

### Community 69 - "Community 69"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 70 - "Community 70"
Cohesion: 0.11
Nodes (35): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+27 more)

### Community 71 - "Community 71"
Cohesion: 0.08
Nodes (32): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+24 more)

### Community 72 - "Community 72"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 73 - "Community 73"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 74 - "Community 74"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 75 - "Community 75"
Cohesion: 0.07
Nodes (31): CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError, CertificationExpiry, CertificationInput, CertificationView, createCertificationService(), cleaned() (+23 more)

### Community 76 - "Community 76"
Cohesion: 0.08
Nodes (34): participantsOfDives(), buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf() (+26 more)

### Community 77 - "Community 77"
Cohesion: 0.13
Nodes (34): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+26 more)

### Community 78 - "Community 78"
Cohesion: 0.11
Nodes (30): BACKUP_SKIPPED_DIRS, BackupDecryptionKey, encryptionKeyFingerprint(), assertPgTool(), execFileAsync, pgCommand(), pgErrorDetail(), pgFailureMessage() (+22 more)

### Community 79 - "Community 79"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 80 - "Community 80"
Cohesion: 0.13
Nodes (26): bar(), celsius(), contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf(), firstOf() (+18 more)

### Community 81 - "Community 81"
Cohesion: 0.09
Nodes (30): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+22 more)

### Community 82 - "Community 82"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 83 - "Community 83"
Cohesion: 0.13
Nodes (28): wallClockMs(), DiveOrigin, REFERENCE_PREFIX, Series, SsiLogbook, aboveZero(), cylinderOf(), deviceOf() (+20 more)

### Community 84 - "Community 84"
Cohesion: 0.10
Nodes (24): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearPushRuns(), clearQueue() (+16 more)

### Community 85 - "Community 85"
Cohesion: 0.11
Nodes (25): ProviderImportPlan, errorMessage(), looksLikeZip(), RemoteSite, SsiLogbookSite, flag(), idText(), isObject() (+17 more)

### Community 86 - "Community 86"
Cohesion: 0.11
Nodes (10): drawBand(), ProfileSketch(), AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint, SketchProfile (+2 more)

### Community 87 - "Community 87"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 88 - "Community 88"
Cohesion: 0.09
Nodes (23): computerEventOf(), ADR-0036, computerEventFromFit(), COMPUTER_EVENT, computerEventFromSuunto(), DECO_MODEL, decoModelFromSuunto, DIVE_MODE (+15 more)

### Community 89 - "Community 89"
Cohesion: 0.09
Nodes (25): Blocked, classify(), kindOf(), KINDS, LEFT_OUT_KEY, LEFT_OUT_KEYS, LeftOutReason, numberOnlyPlan() (+17 more)

### Community 90 - "Community 90"
Cohesion: 0.11
Nodes (20): SsiClient, ClearChecks, NumberChecks, TwowayChecks, apneaDive(), DATASET_KEYS, datasetSummary(), findIn() (+12 more)

### Community 91 - "Community 91"
Cohesion: 0.10
Nodes (26): globalSetup(), ADR-0023, accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks() (+18 more)

### Community 92 - "Community 92"
Cohesion: 0.11
Nodes (7): ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport

### Community 93 - "Community 93"
Cohesion: 0.11
Nodes (24): EVIDENCE, Evidence, FindingValues, RULE_IDS, RuleId, RULES, SEVERITIES, SourceId (+16 more)

### Community 94 - "Community 94"
Cohesion: 0.09
Nodes (19): continuationAppend, equipmentUse, managesDiveDiver(), Participant, createLogbookService(), deletedDiveIds(), deletedDives(), findManagedDive() (+11 more)

### Community 95 - "Community 95"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 96 - "Community 96"
Cohesion: 0.11
Nodes (24): DiveGear, ensureItemForDevice(), GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow, ItemView (+16 more)

### Community 97 - "Community 97"
Cohesion: 0.09
Nodes (23): @modelcontextprotocol/server@^2.3.1, aiAccessLog, aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, KeyRow, LOG_DAYS (+15 more)

### Community 98 - "Community 98"
Cohesion: 0.14
Nodes (23): NoFly, getDiverLoadings, DEFAULT_GF_HIGH, residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows(), DiveRow (+15 more)

### Community 99 - "Community 99"
Cohesion: 0.12
Nodes (21): RecordingSummary, UtcOffsetSource, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput (+13 more)

### Community 100 - "Community 100"
Cohesion: 0.13
Nodes (22): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DueInput, DueSchedule (+14 more)

### Community 101 - "Community 101"
Cohesion: 0.12
Nodes (19): createFileFormats(), FileFormat, looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf() (+11 more)

### Community 102 - "Community 102"
Cohesion: 0.12
Nodes (23): date(), FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position(), positive() (+15 more)

### Community 103 - "Community 103"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 104 - "Community 104"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 105 - "Community 105"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 106 - "Community 106"
Cohesion: 0.13
Nodes (6): ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting

### Community 107 - "Community 107"
Cohesion: 0.14
Nodes (22): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+14 more)

### Community 108 - "Community 108"
Cohesion: 0.09
Nodes (22): pushRunItem, PushRunLeftOut, DIVE_ONLY, DiveListFilter, DiveOnly, FINDINGS_SHOWN, NO_RECORDING, ADR-0017 (+14 more)

### Community 109 - "Community 109"
Cohesion: 0.16
Nodes (22): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), typeOf() (+14 more)

### Community 110 - "Community 110"
Cohesion: 0.11
Nodes (14): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+6 more)

### Community 111 - "Community 111"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 113 - "Community 113"
Cohesion: 0.09
Nodes (23): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+15 more)

### Community 114 - "Community 114"
Cohesion: 0.11
Nodes (20): adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, AuthOptions, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, ADR-0011, ADR-0012 (+12 more)

### Community 115 - "Community 115"
Cohesion: 0.12
Nodes (20): CENTRE_SOURCES, CentreService, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+12 more)

### Community 116 - "Community 116"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 117 - "Community 117"
Cohesion: 0.11
Nodes (15): ndl(), ProfileAssessment, Finding, LIMITS, dayNumber(), deepDays(), SERIES_RULES, SeriesContext (+7 more)

### Community 118 - "Community 118"
Cohesion: 0.15
Nodes (20): BACKUP_FORMAT_VERSION, buildManifest(), decryptFile(), decryptFileWith(), encryptFile(), encryptStream(), execFileAsync, fileTreeChecksum() (+12 more)

### Community 119 - "Community 119"
Cohesion: 0.16
Nodes (16): DecisionTimelineProps, GapKind, ADR-0063, addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, laneFitsCapsule(), relations() (+8 more)

### Community 120 - "Community 120"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 121 - "Community 121"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 122 - "Community 122"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 123 - "Community 123"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 124 - "Community 124"
Cohesion: 0.14
Nodes (16): RequirementView, holdLeave(), mayLeave(), useLeaveGuard(), Draft, draftOf(), positionOf(), privateIdOf() (+8 more)

### Community 125 - "Community 125"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 126 - "Community 126"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 127 - "Community 127"
Cohesion: 0.11
Nodes (13): BACKUP_EXTENSION, BackupEncryption, BackupPart, BackupLog, BackupRunRow, BackupSettingsInput, BackupSettingsValues, DEFAULT_BACKUP_SETTINGS (+5 more)

### Community 128 - "Community 128"
Cohesion: 0.13
Nodes (17): SUGGESTION_STATUSES, DiveProfileView, AcceptBody, STATUS, SuggestionRouteDeps, ADR-0070, SuggestionService, DiverRef (+9 more)

### Community 129 - "Community 129"
Cohesion: 0.22
Nodes (16): describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun(), browserStats() (+8 more)

### Community 130 - "Community 130"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 131 - "Community 131"
Cohesion: 0.17
Nodes (15): diveListFilter(), notAtProvider(), failureOutcome(), numberPlanOf(), orderItems(), createPushRunService(), analyse(), numbersHere() (+7 more)

### Community 132 - "Community 132"
Cohesion: 0.20
Nodes (15): formatOf(), createImportService(), cancelImport(), createImport(), endWaiting(), expireWaiting(), openUpload(), processFile() (+7 more)

### Community 133 - "Community 133"
Cohesion: 0.20
Nodes (14): AppLockProbe, BackupManifest, Arguments, credentialOf(), FILE_OPTIONS, fileText(), finishedArguments(), parseArguments() (+6 more)

### Community 134 - "Community 134"
Cohesion: 0.16
Nodes (12): BOM, Json, num(), obj(), positive(), scaled(), SuuntoJsonAdapter, time() (+4 more)

### Community 135 - "Community 135"
Cohesion: 0.12
Nodes (16): ADR-0013, ADR-0026, ADR-0028, ADR-0030, ADR-0031, ADR-0034, ADR-0035, ADR-0036 (+8 more)

### Community 137 - "Community 137"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 138 - "Community 138"
Cohesion: 0.14
Nodes (11): diveAssessment, parseHint(), PreviewView, SuggestionView, ADR-0070, call(), createSite(), Method (+3 more)

### Community 139 - "Community 139"
Cohesion: 0.16
Nodes (13): CIRCUIT, circuitFromFit, DECO_MODEL, decoModelFromFit, DIVE_ALERT, DIVE_MODE, diveModeFromFit, ADR-0015 (+5 more)

### Community 140 - "Community 140"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 141 - "Community 141"
Cohesion: 0.16
Nodes (14): equipmentItem, serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, DueEntry, ScheduleView, equipmentList() (+6 more)

### Community 142 - "Community 142"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 143 - "Community 143"
Cohesion: 0.15
Nodes (12): EquipmentError, ADR-0034, ADR-0053, checkCost(), checkDates(), checkWindow(), checkResets(), checkSchedule() (+4 more)

### Community 144 - "Community 144"
Cohesion: 0.22
Nodes (11): fromOsm(), fromSsi(), fromWd(), HOUSE_REEF, osm(), site(), ssi(), ADR-0021 (+3 more)

### Community 145 - "Community 145"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 146 - "Community 146"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 147 - "Community 147"
Cohesion: 0.17
Nodes (10): EstimateInput, AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031 (+2 more)

### Community 148 - "Community 148"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 149 - "Community 149"
Cohesion: 0.23
Nodes (8): createBackupService(), checkSettings(), due(), enqueueScheduled(), readSettings(), saveSettings(), sweepRetention(), isUniqueViolation()

### Community 150 - "Community 150"
Cohesion: 0.32
Nodes (12): createEquipmentService(), assemble(), detailOf(), lastCylinderOf(), managedItem(), recordsOf(), schedulesFor(), schedulesOf() (+4 more)

### Community 151 - "Community 151"
Cohesion: 0.17
Nodes (10): Dive, HOUSE_REEF, LIGHTHOUSE, Method, Page, Position, Revision, Site (+2 more)

### Community 152 - "Community 152"
Cohesion: 0.20
Nodes (8): ADR-0063, ADR-0040, ADR-0047, diveNumber(), expireSsiTokens(), headers, openFilters(), signInSsi()

### Community 153 - "Community 153"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 154 - "Community 154"
Cohesion: 0.18
Nodes (5): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053

### Community 155 - "Community 155"
Cohesion: 0.18
Nodes (9): diveAt(), HOUSE_REEF, Method, Page, Position, Revision, Site, ADR-0022 (+1 more)

### Community 156 - "Community 156"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 157 - "Community 157"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 158 - "Community 158"
Cohesion: 0.20
Nodes (8): Series, ChannelSeries, RecordedGas, Tank, TiedCylinder, ADR-0045, readings, ADR-0045

### Community 159 - "Community 159"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 160 - "Community 160"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 161 - "Community 161"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 162 - "Community 162"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 163 - "Community 163"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 164 - "Community 164"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 165 - "Community 165"
Cohesion: 0.25
Nodes (8): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, MODULE_IMPORTS, moduleRules, target()

### Community 166 - "Community 166"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 167 - "Community 167"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 168 - "Community 168"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 170 - "Community 170"
Cohesion: 0.33
Nodes (4): Sketch, SKETCH_POINTS, ADR-0041, ADR-0041

### Community 171 - "Community 171"
Cohesion: 0.29
Nodes (6): itemIds(), ADR-0026, ADR-0034, Usage, UsageDive, UsageRow

### Community 172 - "Community 172"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 173 - "Community 173"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 174 - "Community 174"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 175 - "Community 175"
Cohesion: 0.33
Nodes (3): ApiClient, createApiClient(), ADR-0004

### Community 176 - "Community 176"
Cohesion: 0.40
Nodes (4): label(), CompareSubject, facts, subjects

### Community 177 - "Community 177"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 178 - "Community 178"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 179 - "Community 179"
Cohesion: 0.40
Nodes (4): TAGS, ADR-0023, { stdout, stderr }, total

### Community 183 - "Community 183"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 184 - "Community 184"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 185 - "Community 185"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1193 isolated node(s):** `ADR-0013`, `ADR-0011`, `ADR-0069`, `ADR-0074`, `ADR-0070` (+1188 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3187 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **27 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 13` to `Community 0`, `Community 3`, `Community 4`, `Community 8`, `Community 9`, `Community 10`, `Community 11`, `Community 12`, `Community 141`, `Community 14`, `Community 138`, `Community 17`, `Community 19`, `Community 20`, `Community 21`, `Community 28`, `Community 29`, `Community 30`, `Community 34`, `Community 36`, `Community 171`, `Community 50`, `Community 55`, `Community 56`, `Community 60`, `Community 71`, `Community 75`, `Community 76`, `Community 87`, `Community 94`, `Community 96`, `Community 97`, `Community 98`, `Community 107`, `Community 108`, `Community 113`, `Community 127`?**
  _High betweenness centrality (0.036) - this node is a cross-community bridge._
- **What connects `ADR-0013`, `ADR-0011`, `ADR-0069` to the rest of the system?**
  _1193 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.023741488326848248 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 16` to `Community 32`, `Community 33`, `Community 2`, `Community 1`, `Community 66`, `Community 5`, `Community 6`, `Community 7`, `Community 81`, `Community 18`, `Community 22`, `Community 23`, `Community 24`, `Community 122`, `Community 124`?**
  _High betweenness centrality (0.031) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.021197458938152135 - nodes in this community are weakly interconnected._
- **Why does `@playwright/test` connect `Community 47` to `Community 64`, `Community 65`, `Community 103`, `Community 42`, `Community 81`, `Community 84`, `Community 148`, `Community 152`, `Community 58`, `Community 91`, `Community 156`, `Community 157`?**
  _High betweenness centrality (0.022) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.0512214342001576 - nodes in this community are weakly interconnected._