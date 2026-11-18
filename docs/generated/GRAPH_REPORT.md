# Graph Report - dive_hub  (2026-11-18)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 8177 nodes · 24712 edges · 213 communities (180 shown, 33 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 285 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `82cfb60e`
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
- Community 180
- Community 181
- Community 182
- Community 183
- Community 184
- Community 187

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
- `ClearChecks` --references--> `SsiClient`  [EXTRACTED]
  apps/server/test/fixtures/ssi/clear-checks.ts → apps/server/src/providers/ssi/ssi-client.ts
- `AuthOptions` --references--> `Db`  [EXTRACTED]
  apps/server/src/auth/auth.ts → apps/server/src/db/client.ts

## Import Cycles
- None detected.

## Communities (213 total, 33 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.05
Nodes (176): ChangePassword(), DisplaySettings(), Sessions(), useDescribeAgent(), AdminSettings(), DeleteUserDialog(), Invitations(), Invite() (+168 more)

### Community 1 - "Community 1"
Cohesion: 0.02
Nodes (154): createCertificationService(), requireManagedDiver(), device, diveAssessment, equipmentUse, jointDive, logbookCheckAnswer, logbookCheckDiveAnswer (+146 more)

### Community 2 - "Community 2"
Cohesion: 0.03
Nodes (155): importQuery(), keys, buddiesForQuery(), buddiesQuery(), BuddyView, ConflictChoices, ConflictEntryView, ConflictFieldView (+147 more)

### Community 3 - "Community 3"
Cohesion: 0.03
Nodes (144): continuationAppend, diverBodyWeight, equipmentItem, LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, RecordingSummary, sampleSeries (+136 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (145): diversQuery(), diveNeighboursQuery(), DiveValues, OverridableField, revisionsQuery(), cylinderCatalogueQuery(), CylinderFields(), CylinderList() (+137 more)

### Community 5 - "Community 5"
Cohesion: 0.04
Nodes (98): account, user, cylinder, diveFinding, lead, original, revision, LEASE_MS (+90 more)

### Community 6 - "Community 6"
Cohesion: 0.02
Nodes (121): CentreService, CentreRouteDeps, UTC_OFFSET_SOURCES, DiverService, downsampleMinMax(), DiveCertifications, LogbookCheckService, AppendDeps (+113 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (116): ServiceParts, ConnectionRow, providerGoneDive, providerLease, WaitingPushOptions, audienceOfConnection(), audienceOfDive(), audienceOfDiver() (+108 more)

### Community 8 - "Community 8"
Cohesion: 0.04
Nodes (135): ComputerChoice, DiveImportMode, DiveKind, UtcOffsetSource, tieToJoined(), PartInput, cylindersOf(), mergeProviderCylinder() (+127 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (119): EMPTY_SITE_IMPORT_COUNTS, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, diveSite (+111 more)

### Community 10 - "Community 10"
Cohesion: 0.04
Nodes (112): Me, AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView (+104 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (90): createServer(), loadAuthSecret(), acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock (+82 more)

### Community 12 - "Community 12"
Cohesion: 0.04
Nodes (110): divesQuery(), LogbookPage, PAGE_SIZE, pushRunActive(), pushRunBody(), PushRunChoices, PushRunPreview, pushRunPreviewQuery() (+102 more)

### Community 13 - "Community 13"
Cohesion: 0.03
Nodes (91): buddySuggestion, parseHint(), Accepted, Candidate, Check, DiveView, Outcome, RevisionView (+83 more)

### Community 14 - "Community 14"
Cohesion: 0.04
Nodes (106): diverSearchQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas (+98 more)

### Community 15 - "Community 15"
Cohesion: 0.03
Nodes (28): DiveHubApp(), ErrorText(), InstanceForm(), Loading(), PasswordField(), PrimaryButton(), SignInForm(), Spinner() (+20 more)

### Community 16 - "Community 16"
Cohesion: 0.03
Nodes (88): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+80 more)

### Community 17 - "Community 17"
Cohesion: 0.02
Nodes (100): actorType, aiAccessOutcome, backupRunKind, backupRunStatus, candidateResolution, centreSource, CERTIFICATION_IMAGE_STATES, CERTIFICATION_VERIFICATIONS (+92 more)

### Community 18 - "Community 18"
Cohesion: 0.03
Nodes (78): createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities(), providerError() (+70 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (91): CertificationService, CYLINDER_MATERIALS, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053, ErrorAnswer, PROBLEM_CODES (+83 more)

### Community 20 - "Community 20"
Cohesion: 0.04
Nodes (71): equipmentDueQuery(), EquipmentDueNotice(), ADR-0034, avatarTone(), initials(), dismissDue(), dismissedDueDigest(), dueCounts() (+63 more)

### Community 21 - "Community 21"
Cohesion: 0.05
Nodes (89): buildApp(), AssessmentRouteDeps, assessmentRoutes(), AssessmentViewSchema, FindingView, Rule, STATUS, toView() (+81 more)

### Community 22 - "Community 22"
Cohesion: 0.03
Nodes (77): invitationLinkQuery(), invitationsQuery(), InvitationView, passwordResetQuery(), sessionsQuery(), SessionView, setupQuery(), setupRestoreArchivesQuery() (+69 more)

### Community 23 - "Community 23"
Cohesion: 0.05
Nodes (88): diveFingerprint, waitingOf(), open(), close(), createConnectionService(), checkAutomation(), checkFollowNumbers(), expiryOf() (+80 more)

### Community 24 - "Community 24"
Cohesion: 0.04
Nodes (70): diveSiteExternalId, createSsiClient(), Sent, View, Dive, Status, ADR-0038, ADR-0043 (+62 more)

### Community 25 - "Community 25"
Cohesion: 0.03
Nodes (68): connection, diveLease, providerConflict, push, pushRun, waitingPush, kept(), PURGE_AFTER_DAYS (+60 more)

### Community 26 - "Community 26"
Cohesion: 0.03
Nodes (76): CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043, CreateBody (+68 more)

### Community 27 - "Community 27"
Cohesion: 0.06
Nodes (45): ambientPressureBar(), bestMixPercent(), Conditions, depthOfPressure(), eadMetres(), endMetres(), GasMix, isHypoxic() (+37 more)

### Community 28 - "Community 28"
Cohesion: 0.04
Nodes (77): AiAccessScope, seesPositions(), VerifiedAccess, diversOf(), SiteRow, checkedByRunTool, createMcpServer(), INSTRUCTIONS (+69 more)

### Community 29 - "Community 29"
Cohesion: 0.04
Nodes (62): invitation, userRole, AttemptLimit, clientKey(), limitAttempts(), TOKEN_ATTEMPTS, ADR-0061, ProblemCode (+54 more)

### Community 30 - "Community 30"
Cohesion: 0.04
Nodes (71): diverLoadingsQuery(), DiverView, ExternalIdView, Position, running(), SiteImportArea, siteImportsQuery(), SiteImportView (+63 more)

### Community 31 - "Community 31"
Cohesion: 0.05
Nodes (69): SecuritySettings(), backupsQuery(), App(), BackupsPage, CentrePage, CentresPage, currentRoute(), DivePage (+61 more)

### Community 32 - "Community 32"
Cohesion: 0.07
Nodes (74): allButDive(), continuationCandidateQuery(), deletedDivesOn(), deletedDivesQuery(), diveQuery(), DiveView, mergeCandidatesQuery(), nearbyDivesQuery() (+66 more)

### Community 33 - "Community 33"
Cohesion: 0.04
Nodes (19): ApiMeGet200ResponseOwnDiver, ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting, ProviderSummary (+11 more)

### Community 34 - "Community 34"
Cohesion: 0.05
Nodes (58): auth, BackupServiceDeps, createServices(), ADR-0027, ADR-0030, ADR-0061, ADR-0063, ADR-0066 (+50 more)

### Community 35 - "Community 35"
Cohesion: 0.05
Nodes (58): ImportArea, createSsiSiteSource(), DEFAULT_SSI_SITES_URL, createSiteSources(), ADR-0021, ADR-0027, createOverpassSource(), DEFAULT_OVERPASS_URL (+50 more)

### Community 36 - "Community 36"
Cohesion: 0.06
Nodes (14): LogbookScreenshotTest, ApiDivesGet200Response, ApiDivesGet200ResponseDivesItem, ApiDivesGet200ResponseDivesItemGasesItem, ApiDivesGet200ResponseDivesItemParticipantsItem, ApiDivesGet200ResponseDivesItemSite, ApiDivesGet200ResponseMonthsItem, ApiDivesGet200ResponseTotals (+6 more)

### Community 37 - "Community 37"
Cohesion: 0.04
Nodes (54): ADR-0025, WaterMismatch, waterMismatch(), computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit() (+46 more)

### Community 38 - "Community 38"
Cohesion: 0.07
Nodes (55): SsiRecord, contextOf(), asNumber(), asText(), buildCreateRecord(), buildDeleteRecord(), buildUpdateRecord(), compareReadBack() (+47 more)

### Community 39 - "Community 39"
Cohesion: 0.05
Nodes (55): CylinderMaterial, WeightingFelt, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, WeightingFeedback, answer(), baseFrom() (+47 more)

### Community 40 - "Community 40"
Cohesion: 0.07
Nodes (58): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+50 more)

### Community 41 - "Community 41"
Cohesion: 0.06
Nodes (55): Conditions, GasMix, GasPlanInput, AIR, CNS_HALF_LIFE_MINUTES, DEFAULT_CONDITIONS, LoadingDive, N2_REPLAY_WINDOW_HOURS (+47 more)

### Community 42 - "Community 42"
Cohesion: 0.06
Nodes (52): Delivered, DIVE_FIELDS, ProviderError, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord() (+44 more)

### Community 43 - "Community 43"
Cohesion: 0.07
Nodes (48): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+40 more)

### Community 44 - "Community 44"
Cohesion: 0.05
Nodes (51): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, cancelWaitingImports() (+43 more)

### Community 45 - "Community 45"
Cohesion: 0.09
Nodes (49): accountListEntry, diver, diverExternalId, tidyName(), DiverDetails, foldName(), nameDiffers(), Buddy (+41 more)

### Community 46 - "Community 46"
Cohesion: 0.04
Nodes (38): ParticipantRole, PushLeftOut, Capabilities, Direction, DiveExport, DiveListing, FindBy, Operation (+30 more)

### Community 47 - "Community 47"
Cohesion: 0.08
Nodes (50): BACKUP_FORMAT_VERSION, BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32Encode(), bech32HrpExpand() (+42 more)

### Community 48 - "Community 48"
Cohesion: 0.06
Nodes (34): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+26 more)

### Community 49 - "Community 49"
Cohesion: 0.06
Nodes (38): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+30 more)

### Community 50 - "Community 50"
Cohesion: 0.06
Nodes (9): AppContainer, KeystoreTokenStore, PreferencesInstanceUrlStore, UserCacheStore, DiveHubApplication, LogbookRepository, InstanceUrlStore, TokenStore (+1 more)

### Community 51 - "Community 51"
Cohesion: 0.07
Nodes (6): LogbookViewModel, MainViewModel, DiveHubException, NoAnswerException, InstanceUrl, ProviderDirectory

### Community 52 - "Community 52"
Cohesion: 0.08
Nodes (5): ApiClient, ApiErrorBody, json(), LogbookRepositoryTest, RepositoryHarness

### Community 53 - "Community 53"
Cohesion: 0.06
Nodes (43): LEAD_PLACEMENTS, WEIGHTING_FELT, ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, seriesOf() (+35 more)

### Community 54 - "Community 54"
Cohesion: 0.07
Nodes (37): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+29 more)

### Community 55 - "Community 55"
Cohesion: 0.08
Nodes (36): BottomTimeQuery, DEFAULT_GF_HIGH, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033, ADR-0071 (+28 more)

### Community 56 - "Community 56"
Cohesion: 0.08
Nodes (39): Leave, asBool(), DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+31 more)

### Community 57 - "Community 57"
Cohesion: 0.05
Nodes (42): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+34 more)

### Community 58 - "Community 58"
Cohesion: 0.09
Nodes (41): certificationsQuery(), importsQuery(), Logbook(), CertificationItem(), CertificationsPanel(), CertificationsSection(), ImportReport(), ImportResult (+33 more)

### Community 59 - "Community 59"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 60 - "Community 60"
Cohesion: 0.08
Nodes (15): drawBand(), ProfileSketch(), durationMinutes(), mixName(), AscentBand, SketchCurve, SketchGeometry, sketchGeometry() (+7 more)

### Community 61 - "Community 61"
Cohesion: 0.06
Nodes (41): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, Services, EquipmentService, RecordInput (+33 more)

### Community 62 - "Community 62"
Cohesion: 0.05
Nodes (34): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError (+26 more)

### Community 63 - "Community 63"
Cohesion: 0.07
Nodes (40): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+32 more)

### Community 64 - "Community 64"
Cohesion: 0.06
Nodes (36): SUIT_TYPES, SUIT_UNDERGARMENTS, DiverCertifications, replyDiverError, STATUS, CertificationSummary, DiverDetailsView, DiverDetailsViewSchema (+28 more)

### Community 65 - "Community 65"
Cohesion: 0.07
Nodes (35): sampleCount(), SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject() (+27 more)

### Community 66 - "Community 66"
Cohesion: 0.07
Nodes (40): cylinderOf(), cylinderText(), diverOf(), diverText(), finite(), kept, LEAD_MATERIALS, LEAD_WATERS (+32 more)

### Community 67 - "Community 67"
Cohesion: 0.07
Nodes (37): pushRunItem, PushRunLeftOut, DIVE_ONLY, DIVE_ONLY_PATTERN, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN (+29 more)

### Community 68 - "Community 68"
Cohesion: 0.06
Nodes (37): Change(), Changes(), REVISION_CAUSES, PositionSchema, AdoptBody, Country, CreateBody, Description (+29 more)

### Community 69 - "Community 69"
Cohesion: 0.10
Nodes (32): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0051 (+24 more)

### Community 70 - "Community 70"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 71 - "Community 71"
Cohesion: 0.13
Nodes (8): FakeCache, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore, UserScopedStore

### Community 72 - "Community 72"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 73 - "Community 73"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 74 - "Community 74"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 75 - "Community 75"
Cohesion: 0.09
Nodes (10): UiState, ApiMeGet200Response, ApiMeGet200ResponsePreferences, SignInEmailRequest, UserView, Loading, SessionRepository, SessionState (+2 more)

### Community 76 - "Community 76"
Cohesion: 0.12
Nodes (35): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+27 more)

### Community 77 - "Community 77"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 78 - "Community 78"
Cohesion: 0.08
Nodes (34): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+26 more)

### Community 79 - "Community 79"
Cohesion: 0.08
Nodes (31): AI_ACCESS_NAME_MAX, aiAccessLog, aiAccessSetting, Access, AccessView, AiAccessRouteDeps, LogEntryView, Scope (+23 more)

### Community 80 - "Community 80"
Cohesion: 0.11
Nodes (28): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+20 more)

### Community 81 - "Community 81"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 82 - "Community 82"
Cohesion: 0.07
Nodes (27): Cylinder, DiveView, RevisionView, ADR-0045, upload(), Candidate, Check, Outcome (+19 more)

### Community 83 - "Community 83"
Cohesion: 0.10
Nodes (28): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+20 more)

### Community 84 - "Community 84"
Cohesion: 0.11
Nodes (19): dive, duplicateCandidate, ImportService, ADR-0004, ADR-0044, Neighbours, ADR-0042, noAppends (+11 more)

### Community 85 - "Community 85"
Cohesion: 0.09
Nodes (27): AIR, conditionsOf(), EAN32, evaluateNdl(), evaluateOxygen(), evaluateVectorCase(), finiteOrNull(), GAS_CASES (+19 more)

### Community 86 - "Community 86"
Cohesion: 0.10
Nodes (16): ApiDivesGet200ResponseCounts, countOf(), FilterChip, filterChips(), LogbookFilter, CHANGED_SINCE_SENT, NO_RECORDING, NO_SITE (+8 more)

### Community 87 - "Community 87"
Cohesion: 0.09
Nodes (26): AssessmentService, BACKUP_RUN_TASK, backupTask(), ADR-0069, PurgeService, EXPIRE_WAITING_IMPORTS_TASK, SCHEDULED_FETCH_TASK, PUSH_RUN_STEP_TASK (+18 more)

### Community 88 - "Community 88"
Cohesion: 0.09
Nodes (28): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+20 more)

### Community 89 - "Community 89"
Cohesion: 0.16
Nodes (28): assessmentQuery(), AssessmentView, AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState, FindingItem() (+20 more)

### Community 90 - "Community 90"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 91 - "Community 91"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 92 - "Community 92"
Cohesion: 0.09
Nodes (27): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+19 more)

### Community 93 - "Community 93"
Cohesion: 0.10
Nodes (26): EVIDENCE, Evidence, FindingValues, RULE_IDS, RuleId, RULES, SEVERITIES, SourceId (+18 more)

### Community 94 - "Community 94"
Cohesion: 0.09
Nodes (26): Blocked, classify(), FailureOutcome, kindOf(), KINDS, LEFT_OUT_KEY, LEFT_OUT_KEYS, LeftOutReason (+18 more)

### Community 95 - "Community 95"
Cohesion: 0.08
Nodes (24): recordingEvent, Assessment, FindingView, Row, ADR-0036, upload(), Check, Deleted (+16 more)

### Community 96 - "Community 96"
Cohesion: 0.14
Nodes (26): DiveOrigin, REFERENCE_PREFIX, Series, SsiLogbook, aboveZero(), cylinderOf(), deviceOf(), deviceOfRef() (+18 more)

### Community 97 - "Community 97"
Cohesion: 0.10
Nodes (22): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearQueue(), conflictAtSsi() (+14 more)

### Community 98 - "Community 98"
Cohesion: 0.13
Nodes (23): samplesQuery(), FindingLane(), useAssessment(), DepthProfile(), NO_POINTS, SurfaceBreakLane(), ADR-0036, ADR-0063 (+15 more)

### Community 99 - "Community 99"
Cohesion: 0.13
Nodes (22): errorMessage(), looksLikeZip(), formatOf(), createImportService(), cancelImport(), createImport(), endWaiting(), expireWaiting() (+14 more)

### Community 100 - "Community 100"
Cohesion: 0.11
Nodes (19): SsiClient, NumberChecks, TwowayChecks, apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks() (+11 more)

### Community 101 - "Community 101"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 102 - "Community 102"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 103 - "Community 103"
Cohesion: 0.08
Nodes (25): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+17 more)

### Community 104 - "Community 104"
Cohesion: 0.10
Nodes (20): Config, loadConfig(), ADR-0011, ADR-0021, ADR-0024, ADR-0025, config, createSecretBox() (+12 more)

### Community 105 - "Community 105"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 106 - "Community 106"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 107 - "Community 107"
Cohesion: 0.13
Nodes (20): BACKUP_SKIPPED_DIRS, BackupDecryptionKey, restore(), decryptionKeyOf(), execFileAsync, imageMigrationHead(), Journal, KEPT_ON_RESTORE (+12 more)

### Community 108 - "Community 108"
Cohesion: 0.16
Nodes (22): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), typeOf() (+14 more)

### Community 109 - "Community 109"
Cohesion: 0.11
Nodes (14): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+6 more)

### Community 110 - "Community 110"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 111 - "Community 111"
Cohesion: 0.13
Nodes (22): serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, DueEntry, GearItem, ItemDetailView, ItemEdit (+14 more)

### Community 112 - "Community 112"
Cohesion: 0.15
Nodes (22): EquipmentError, checkCost(), checkDates(), checkWindow(), createEquipmentService(), assemble(), checkResets(), checkSchedule() (+14 more)

### Community 113 - "Community 113"
Cohesion: 0.14
Nodes (18): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+10 more)

### Community 114 - "Community 114"
Cohesion: 0.13
Nodes (18): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, GasRule (+10 more)

### Community 115 - "Community 115"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 116 - "Community 116"
Cohesion: 0.26
Nodes (18): ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres(), isHypoxic(), metresPerBar() (+10 more)

### Community 117 - "Community 117"
Cohesion: 0.19
Nodes (18): Queryable, residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows(), DiveRow, endMsOf(), loadingDivesForReplay() (+10 more)

### Community 118 - "Community 118"
Cohesion: 0.14
Nodes (18): DAY_MS, MAX_OFFSET_MS, inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), placeLocalTime(), Position (+10 more)

### Community 119 - "Community 119"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 120 - "Community 120"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 121 - "Community 121"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 122 - "Community 122"
Cohesion: 0.16
Nodes (18): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+10 more)

### Community 123 - "Community 123"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 124 - "Community 124"
Cohesion: 0.11
Nodes (18): BOTTOM_TIME_RULES, PLAN_RULES, PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody (+10 more)

### Community 125 - "Community 125"
Cohesion: 0.13
Nodes (15): FIND_BY, OPERATIONS, SignInInput, CASES, ContractHarness, declared(), ELSEWHERE, later() (+7 more)

### Community 126 - "Community 126"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 127 - "Community 127"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 128 - "Community 128"
Cohesion: 0.11
Nodes (13): BACKUP_EXTENSION, BackupEncryption, BackupPart, BackupLog, BackupRunRow, BackupSettingsInput, BackupSettingsValues, DEFAULT_BACKUP_SETTINGS (+5 more)

### Community 129 - "Community 129"
Cohesion: 0.11
Nodes (15): accountRelations, apikey, rateLimit, sessionRelations, ADR-0011, ADR-0012, ADR-0035, twoFactor (+7 more)

### Community 130 - "Community 130"
Cohesion: 0.22
Nodes (16): describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun(), browserStats() (+8 more)

### Community 131 - "Community 131"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 132 - "Community 132"
Cohesion: 0.14
Nodes (15): siteImportStatus, StartBody, STATUS, ADR-0021, SiteImportError, Area, Counts, countsOf() (+7 more)

### Community 133 - "Community 133"
Cohesion: 0.15
Nodes (13): Sac, SAC_MISSING, SacMissing, sacOfDive(), SacValues, sacValues(), ADR-0033, ADR-0045 (+5 more)

### Community 134 - "Community 134"
Cohesion: 0.12
Nodes (16): ADR-0013, ADR-0026, ADR-0028, ADR-0030, ADR-0031, ADR-0034, ADR-0035, ADR-0036 (+8 more)

### Community 135 - "Community 135"
Cohesion: 0.16
Nodes (13): DecisionTimelineProps, addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, relations(), TimelineBar, TimelineBarGeometry, TimelineGapGeometry (+5 more)

### Community 137 - "Community 137"
Cohesion: 0.22
Nodes (13): AppLockProbe, Arguments, credentialOf(), FILE_OPTIONS, fileText(), finishedArguments(), parseArguments(), RestoreCliDeps (+5 more)

### Community 138 - "Community 138"
Cohesion: 0.17
Nodes (12): decodeRecipient(), fileTreeChecksum(), isRecipient(), tarDirectory(), createBackupService(), checkSettings(), due(), enqueueScheduled() (+4 more)

### Community 139 - "Community 139"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 140 - "Community 140"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 141 - "Community 141"
Cohesion: 0.16
Nodes (11): replyCertificationError, STATUS, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, CertificationRouteDeps (+3 more)

### Community 142 - "Community 142"
Cohesion: 0.22
Nodes (12): defined(), Joined, joinedSummary(), joinParts(), median(), round3(), ADR-0063, createFitAdapter() (+4 more)

### Community 143 - "Community 143"
Cohesion: 0.14
Nodes (7): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, headers, uniqueWord()

### Community 144 - "Community 144"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 145 - "Community 145"
Cohesion: 0.29
Nodes (13): temperatureIn(), DepthPlot(), LINE_DASHES, plotFont(), PressureStrip(), RIGHT_AXIS, token(), ADR-0045 (+5 more)

### Community 146 - "Community 146"
Cohesion: 0.22
Nodes (11): fromOsm(), fromSsi(), fromWd(), HOUSE_REEF, osm(), site(), ssi(), ADR-0021 (+3 more)

### Community 147 - "Community 147"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 148 - "Community 148"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 149 - "Community 149"
Cohesion: 0.35
Nodes (11): assertPgTool(), execFileAsync, pgCommand(), pgErrorDetail(), pgFailureMessage(), PgTool, pgToolFailure(), redactPassword() (+3 more)

### Community 150 - "Community 150"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 151 - "Community 151"
Cohesion: 0.17
Nodes (10): Dive, HOUSE_REEF, LIGHTHOUSE, Method, Page, Position, Revision, Site (+2 more)

### Community 152 - "Community 152"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 153 - "Community 153"
Cohesion: 0.20
Nodes (10): CandidateRouteDeps, CandidateView, Decided, DiveBrief, OffsetSource, STATUS, ADR-0016, ADR-0030 (+2 more)

### Community 154 - "Community 154"
Cohesion: 0.29
Nodes (9): Doc, isObjectSchema(), mediaSchemas(), placesOf(), placesOfOperation(), Schema, sharedInlineSchemas(), sizable() (+1 more)

### Community 155 - "Community 155"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 156 - "Community 156"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 157 - "Community 157"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 158 - "Community 158"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 159 - "Community 159"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 160 - "Community 160"
Cohesion: 0.27
Nodes (6): Profile, SKETCH_HEIGHT, SKETCH_WIDTH, sketchPaths(), ADR-0041, ADR-0041

### Community 161 - "Community 161"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 162 - "Community 162"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 163 - "Community 163"
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
Cohesion: 0.29
Nodes (4): initI18n(), resources, ADR-0014, ADR-0014

### Community 169 - "Community 169"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 170 - "Community 170"
Cohesion: 0.33
Nodes (5): DueSchedule, UsageDive, run(), schedule(), ADR-0034

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
Cohesion: 0.40
Nodes (4): label(), CompareSubject, facts, subjects

### Community 176 - "Community 176"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 177 - "Community 177"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 178 - "Community 178"
Cohesion: 0.40
Nodes (4): TAGS, ADR-0023, { stdout, stderr }, total

### Community 181 - "Community 181"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 182 - "Community 182"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 183 - "Community 183"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1199 isolated node(s):** `ADR-0012`, `ADR-0013`, `ADR-0030`, `ADR-0035`, `ADR-0031` (+1194 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3226 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **33 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 5` to `Community 128`, `Community 129`, `Community 1`, `Community 3`, `Community 6`, `Community 7`, `Community 8`, `Community 9`, `Community 11`, `Community 13`, `Community 16`, `Community 17`, `Community 18`, `Community 23`, `Community 24`, `Community 25`, `Community 26`, `Community 28`, `Community 29`, `Community 34`, `Community 171`, `Community 45`, `Community 55`, `Community 61`, `Community 62`, `Community 67`, `Community 78`, `Community 79`, `Community 82`, `Community 83`, `Community 84`, `Community 92`, `Community 95`, `Community 103`, `Community 104`, `Community 111`, `Community 117`, `Community 118`?**
  _High betweenness centrality (0.026) - this node is a cross-community bridge._
- **What connects `ADR-0012`, `ADR-0013`, `ADR-0030` to the rest of the system?**
  _1199 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.05224079910261204 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 2` to `Community 0`, `Community 32`, `Community 89`, `Community 98`, `Community 4`, `Community 70`, `Community 10`, `Community 12`, `Community 14`, `Community 20`, `Community 22`, `Community 121`, `Community 58`, `Community 91`, `Community 30`, `Community 31`?**
  _High betweenness centrality (0.024) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.022209165687426556 - nodes in this community are weakly interconnected._
- **Why does `react` connect `Community 20` to `Community 0`, `Community 32`, `Community 89`, `Community 2`, `Community 98`, `Community 4`, `Community 70`, `Community 10`, `Community 12`, `Community 14`, `Community 145`, `Community 22`, `Community 121`, `Community 58`, `Community 91`, `Community 30`, `Community 31`?**
  _High betweenness centrality (0.016) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.02733578131374949 - nodes in this community are weakly interconnected._