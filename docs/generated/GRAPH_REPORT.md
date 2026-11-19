# Graph Report - dive_hub  (2026-11-19)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 8733 nodes · 26230 edges · 256 communities (197 shown, 59 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 371 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `d98485b9`
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
- Community 198
- Community 199
- Community 201
- Community 202
- Community 203
- Community 204
- Community 205
- Community 208

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
- `bottomTime()` --calls--> `NdlInput`  [INFERRED]
  apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/planning/BottomTime.kt → apps/android/shared/src/commonMain/kotlin/dev/divehub/shared/planning/Ndl.kt
- `AuthOptions` --references--> `Db`  [EXTRACTED]
  apps/server/src/auth/auth.ts → apps/server/src/db/client.ts

## Import Cycles
- None detected.

## Communities (256 total, 59 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (201): cylinderCatalogueQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas (+193 more)

### Community 1 - "Community 1"
Cohesion: 0.05
Nodes (203): ChangePassword(), DisplaySettings(), Sessions(), useDescribeAgent(), AdminSettings(), DeleteUserDialog(), Invitations(), Invite() (+195 more)

### Community 2 - "Community 2"
Cohesion: 0.04
Nodes (121): user, connection, providerConflict, push, revision, LEASE_MS, MAX_WAIT_MS, POLL_MS (+113 more)

### Community 3 - "Community 3"
Cohesion: 0.03
Nodes (138): EMPTY_SITE_IMPORT_COUNTS, ImportArea, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053 (+130 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (107): diverSearchQuery(), DiveView, keys, ConnectionView, ProviderView, api, ApiError, isUnauthorized() (+99 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (115): LEAD_PLACEMENTS, UTC_OFFSET_SOURCES, WEIGHTING_FELT, DiveCertifications, LogbookCheckService, AppendDeps, AppendOutcome, AppendService (+107 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (96): createServer(), loadAuthSecret(), acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock (+88 more)

### Community 7 - "Community 7"
Cohesion: 0.04
Nodes (104): label(), Me, AcceptedBuddySuggestionView, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView (+96 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (103): SecuritySettings(), centreQuery(), ExternalIdView, Position, running(), SiteImportArea, siteImportsQuery(), SiteImportView (+95 more)

### Community 9 - "Community 9"
Cohesion: 0.04
Nodes (102): diverBodyWeight, lead, LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, RecordingSummary, SuitType, SuitUndergarment (+94 more)

### Community 10 - "Community 10"
Cohesion: 0.04
Nodes (97): divesQuery(), LogbookPage, PAGE_SIZE, equipmentDueQuery(), connectionsQuery(), siteQuery(), Buddies(), COUNT_OF (+89 more)

### Community 11 - "Community 11"
Cohesion: 0.04
Nodes (24): DiveBody(), DiveDetailRoute(), DiveDetailScreen(), errorText(), Loading(), NeighbourButton(), Neighbours(), Notice() (+16 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (90): CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite(), lock() (+82 more)

### Community 13 - "Community 13"
Cohesion: 0.04
Nodes (93): CYLINDER_MATERIALS, DIVE_IMPORT_MODES, PARTICIPANT_ROLES, DIVE_ONLY_PATTERN, TOKEN_ATTEMPTS, ADR-0061, ErrorAnswer, Problem (+85 more)

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (79): createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities(), providerError() (+71 more)

### Community 15 - "Community 15"
Cohesion: 0.04
Nodes (76): auth, BackupServiceDeps, createServices(), ServiceParts, ADR-0027, ADR-0030, ADR-0061, ADR-0063 (+68 more)

### Community 16 - "Community 16"
Cohesion: 0.04
Nodes (19): MainActivity, DiveDetailUiState, DiveDetailViewModel, LogbookViewModel, MainViewModel, UiState, ApiMeGet200Response, ApiMeGet200ResponsePreferences (+11 more)

### Community 17 - "Community 17"
Cohesion: 0.02
Nodes (92): actorType, aiAccessOutcome, backupRunKind, backupRunStatus, candidateResolution, centreSource, certificationImageState, certificationVerification (+84 more)

### Community 18 - "Community 18"
Cohesion: 0.04
Nodes (92): allButDive(), AnyLogbookCheck, CandidateView, continuationCandidateQuery(), ContinuationCandidateView, deletedDivesOn(), DeletedDivesPageView, deletedDivesQuery() (+84 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (86): equipmentItem, serviceRecord, serviceRecordSchedule, serviceSchedule, managementOf(), ADR-0025, WaterMismatch, waterMismatch() (+78 more)

### Community 20 - "Community 20"
Cohesion: 0.04
Nodes (23): ApiMeGet200ResponseOwnDiver, ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting, DiveView (+15 more)

### Community 21 - "Community 21"
Cohesion: 0.03
Nodes (77): invitationLinkQuery(), invitationsQuery(), InvitationView, passwordResetQuery(), sessionsQuery(), SessionView, setupQuery(), ADR-0035 (+69 more)

### Community 22 - "Community 22"
Cohesion: 0.04
Nodes (81): duplicateCandidate, recordingEvent, sampleSeries, UtcOffsetSource, CandidateError, CandidateRow, createCandidateService(), lockMine() (+73 more)

### Community 23 - "Community 23"
Cohesion: 0.04
Nodes (64): diveFinding, Assessment, FindingView, Row, ADR-0036, upload(), Hint, SuggestionView (+56 more)

### Community 24 - "Community 24"
Cohesion: 0.05
Nodes (70): diveAssessment, equipmentUse, logbookCheckAnswer, logbookCheckDiveAnswer, managedDiverIds(), managesDiveDiver(), ADR-0016, keepApart() (+62 more)

### Community 25 - "Community 25"
Cohesion: 0.05
Nodes (72): ConflictFieldView, providerSitesQuery(), providersQuery(), providerStatusQuery(), RequirementView, showDiveCodes(), useFocusBackWhenClosed(), holdLeave() (+64 more)

### Community 26 - "Community 26"
Cohesion: 0.04
Nodes (73): importQuery(), buddiesForQuery(), buddiesQuery(), BuddyView, DiveImportMode, DiveImportPreview, diveImportPreviewQuery(), DiveKind (+65 more)

### Community 27 - "Community 27"
Cohesion: 0.05
Nodes (22): LogbookScreenshotTest, ApiDivesGet200Response, ApiDivesGet200ResponseDivesItem, ApiDivesGet200ResponseDivesItemGasesItem, ApiDivesGet200ResponseDivesItemParticipantsItem, ApiDivesGet200ResponseDivesItemSite, ApiDivesGet200ResponseMonthsItem, ApiDivesGet200ResponseTotals (+14 more)

### Community 28 - "Community 28"
Cohesion: 0.04
Nodes (66): pushRunItem, PushRunLeftOut, asksFor(), DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN (+58 more)

### Community 29 - "Community 29"
Cohesion: 0.03
Nodes (63): buddySuggestion, parseHint(), Accepted, Candidate, Check, DiveView, Outcome, RevisionView (+55 more)

### Community 30 - "Community 30"
Cohesion: 0.07
Nodes (66): diveFingerprint, waitingOf(), close(), currentRemote(), PushRow, ADR-0024, takeChanges(), Choice (+58 more)

### Community 31 - "Community 31"
Cohesion: 0.05
Nodes (57): diveSiteExternalId, createSsiClient(), Card, DEEP, OWD, Report, Summary, ADR-0050 (+49 more)

### Community 32 - "Community 32"
Cohesion: 0.04
Nodes (48): PushLeftOut, Capabilities, Direction, DiveExport, DiveListing, FIND_BY, FindBy, Operation (+40 more)

### Community 33 - "Community 33"
Cohesion: 0.04
Nodes (54): ConnectionRow, providerLease, DiverDetails, externalDiver(), participations(), Buddy, ADR-0028, ADR-0029 (+46 more)

### Community 34 - "Community 34"
Cohesion: 0.04
Nodes (55): adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, Auth, AuthOptions, CLIENT_IP_HEADER, createAuth(), PUBLIC_AUTH_PATHS (+47 more)

### Community 35 - "Community 35"
Cohesion: 0.06
Nodes (26): DiveViewRecordingsItem, DiveViewRecordingsItemDevice, DiveViewRecordingsItemSummary, DiveViewRecordingsItemSummaryGasesItem, DiveViewRecordingsItemSummarySurfaceBreaksItem, RecordingSamplesView, RecordingSamplesViewSeriesItem, computerRecordings() (+18 more)

### Community 36 - "Community 36"
Cohesion: 0.06
Nodes (10): DiveHubTheme(), DiveDetailScreenshotTest, DiveViewFromRecording, DiveViewFromRecordingStartsAt, DiveViewValues, DiveViewValuesStartsAt, DiveViewWaterMismatch, NeighboursView (+2 more)

### Community 37 - "Community 37"
Cohesion: 0.04
Nodes (59): SUGGESTION_STATUSES, DiveProfileView, DiveProfileViewSchema, ADR-0041, Change(), Changes(), historyRoute, Made() (+51 more)

### Community 38 - "Community 38"
Cohesion: 0.06
Nodes (60): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+52 more)

### Community 39 - "Community 39"
Cohesion: 0.04
Nodes (59): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+51 more)

### Community 40 - "Community 40"
Cohesion: 0.06
Nodes (53): Delivered, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open() (+45 more)

### Community 41 - "Community 41"
Cohesion: 0.06
Nodes (54): Series, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining(), ssiDiveType() (+46 more)

### Community 42 - "Community 42"
Cohesion: 0.06
Nodes (45): SsiClient, SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), DiveForSsi, emptyRecord(), writableRecord() (+37 more)

### Community 43 - "Community 43"
Cohesion: 0.06
Nodes (30): ApiDivesGet200ResponseCounts, durationMinutes(), initials(), mixName(), countOf(), FilterChip, filterChips(), LogbookFilter (+22 more)

### Community 44 - "Community 44"
Cohesion: 0.06
Nodes (55): assessmentQuery(), DiveValues, OverridableField, revisionsQuery(), RevisionView, UtcOffsetSource, AsideItem(), AssessmentContext (+47 more)

### Community 45 - "Community 45"
Cohesion: 0.10
Nodes (55): buildApp(), assessmentRoutes(), AI_ACCESS_NAME_MAX, authHandler(), authHeaders(), forwardCookies(), isAdmin(), requireAdmin() (+47 more)

### Community 46 - "Community 46"
Cohesion: 0.06
Nodes (52): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, answer(), baseFrom(), cylindersAtReserve(), CylinderTerm (+44 more)

### Community 47 - "Community 47"
Cohesion: 0.04
Nodes (44): diveLease, pushRun, waitingPush, kept(), PURGE_AFTER_DAYS, ExportingProvider, DELETE_QUIET_MS, QUIET_MS (+36 more)

### Community 48 - "Community 48"
Cohesion: 0.07
Nodes (28): FactRow(), Facts(), profileSummaryText(), recordingName(), Recordings(), Water(), DiveDetailText, Formats (+20 more)

### Community 49 - "Community 49"
Cohesion: 0.07
Nodes (57): ComputerChoice, DiveImportMode, siteRef(), entryMatches(), kindOfMode(), deviceDiver(), closeFields(), entryOf() (+49 more)

### Community 50 - "Community 50"
Cohesion: 0.07
Nodes (46): looksLikeZip(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy(), ADR-0024 (+38 more)

### Community 51 - "Community 51"
Cohesion: 0.06
Nodes (47): jointDive, alignedForMatching(), CandidateDive, DAY_MS, decideMatch(), depthsDisagree(), MatchDecision, MAX_OFFSET_MS (+39 more)

### Community 52 - "Community 52"
Cohesion: 0.07
Nodes (53): WaitingPushOptions, audienceOfConnection(), audienceOfDive(), audienceOfDiver(), emitHint(), Hint, HINT_KINDS, HintAudience (+45 more)

### Community 53 - "Community 53"
Cohesion: 0.05
Nodes (50): ADR-0036, headers, ADR-0030, ADR-0038, ADR-0048, ADR-0062, AiAccess, assessedDive() (+42 more)

### Community 54 - "Community 54"
Cohesion: 0.05
Nodes (45): downsampleMinMax(), AIR, BOTTOM_TIME_CASES, conditionsOf(), DAY, DOWNSAMPLED_PROFILE, EAN32, evaluateGasPlan() (+37 more)

### Community 55 - "Community 55"
Cohesion: 0.07
Nodes (4): ApiErrorBody, json(), LogbookRepositoryTest, RepositoryHarness

### Community 56 - "Community 56"
Cohesion: 0.07
Nodes (45): invitation, userRole, AttemptLimit, clientKey(), limitAttempts(), AccountRouteDeps, SessionView, ADR-0013 (+37 more)

### Community 57 - "Community 57"
Cohesion: 0.06
Nodes (34): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+26 more)

### Community 58 - "Community 58"
Cohesion: 0.06
Nodes (46): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, Services, EquipmentService, RecordInput (+38 more)

### Community 59 - "Community 59"
Cohesion: 0.08
Nodes (45): BACKUP_FORMAT_VERSION, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32Encode(), bech32HrpExpand(), bech32Polymod() (+37 more)

### Community 60 - "Community 60"
Cohesion: 0.10
Nodes (39): Queryable, cylinder, recording, BottomTimeQuery, DEFAULT_GF_HIGH, diverLogbook(), planBottomTime(), residualSeedOf() (+31 more)

### Community 61 - "Community 61"
Cohesion: 0.06
Nodes (10): AppContainer, KeystoreTokenStore, PreferencesInstanceUrlStore, UserCacheStore, AndroidDiveStore, DiveHubApplication, LogbookRepository, InstanceUrlStore (+2 more)

### Community 62 - "Community 62"
Cohesion: 0.06
Nodes (35): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CertificationError, CertificationExpiry (+27 more)

### Community 63 - "Community 63"
Cohesion: 0.10
Nodes (44): ParticipantRole, cylindersOf(), mergeProviderCylinder(), providerCylinderOf(), participantsOf(), columnsOf(), partsOf(), leadOf() (+36 more)

### Community 64 - "Community 64"
Cohesion: 0.06
Nodes (37): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+29 more)

### Community 65 - "Community 65"
Cohesion: 0.08
Nodes (32): diveWith(), ADR-0028, ADR-0043, buddySite(), Dive, OTHER, resetSource(), ADR-0012 (+24 more)

### Community 66 - "Community 66"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 67 - "Community 67"
Cohesion: 0.11
Nodes (38): accountListEntry, diver, diverExternalId, tidyName(), foldName(), nameDiffers(), createBuddyService(), createFrom() (+30 more)

### Community 68 - "Community 68"
Cohesion: 0.08
Nodes (40): ProviderCylinder, ConflictEntry, ConflictField, createConflictService(), loadWaiting(), stillDiffering(), fieldsOf(), isExists() (+32 more)

### Community 69 - "Community 69"
Cohesion: 0.07
Nodes (40): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+32 more)

### Community 70 - "Community 70"
Cohesion: 0.07
Nodes (39): cylinderOf(), cylinderText(), diverOf(), diverText(), finite(), kept, LEAD_MATERIALS, LEAD_WATERS (+31 more)

### Community 71 - "Community 71"
Cohesion: 0.07
Nodes (37): @modelcontextprotocol/server@^2.3.1, aiAccessLog, aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, AiAccessService, KeyRow (+29 more)

### Community 72 - "Community 72"
Cohesion: 0.06
Nodes (34): SUIT_UNDERGARMENTS, DiverCertifications, replyDiverError, STATUS, CertificationSummary, DiverDetailsView, DiverDetailsViewSchema, DiverSource (+26 more)

### Community 73 - "Community 73"
Cohesion: 0.07
Nodes (36): DUE_STATES, diversOf(), SiteRow, Cursor, cursorAt(), DateOnly(), Detail, diversAsked() (+28 more)

### Community 74 - "Community 74"
Cohesion: 0.08
Nodes (36): BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, Conditions, GasMix, ASCENT_RATE_M_PER_MIN (+28 more)

### Community 75 - "Community 75"
Cohesion: 0.09
Nodes (36): AIR, CNS_HALF_LIFE_MINUTES, cnsDecayed(), DEFAULT_CONDITIONS, N2_REPLAY_WINDOW_HOURS, N2_STALE_AFTER_HOURS, N2Loadings, N2LoadingsBase (+28 more)

### Community 76 - "Community 76"
Cohesion: 0.06
Nodes (30): headers, ADR-0035, diveRow(), rowOf(), ADR-0026, ADR-0048, aiAccessReady(), askMcp() (+22 more)

### Community 77 - "Community 77"
Cohesion: 0.05
Nodes (36): AREA_OF, areaOf(), areas, capture(), findings, headers, ADR-0020, ADR-0021 (+28 more)

### Community 78 - "Community 78"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 79 - "Community 79"
Cohesion: 0.13
Nodes (8): FakeCache, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore, UserScopedStore

### Community 80 - "Community 80"
Cohesion: 0.07
Nodes (38): SUIT_TYPES, DiveRow, GROUPS, ownDive(), participantsOf(), recordingsOf(), seriesOf(), minutes() (+30 more)

### Community 81 - "Community 81"
Cohesion: 0.07
Nodes (32): sampleCount(), rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject(), MAX_CARD_IMAGE_BYTES (+24 more)

### Community 82 - "Community 82"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 83 - "Community 83"
Cohesion: 0.11
Nodes (32): BACKUP_SKIPPED_DIRS, BackupDecryptionKey, openArchiveStreamingWith(), assertPgTool(), execFileAsync, pgCommand(), pgErrorDetail(), pgFailureMessage() (+24 more)

### Community 84 - "Community 84"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 85 - "Community 85"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 86 - "Community 86"
Cohesion: 0.11
Nodes (34): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+26 more)

### Community 87 - "Community 87"
Cohesion: 0.09
Nodes (23): dive, importJob, original, ImportService, ADR-0004, ADR-0044, noAppends, race() (+15 more)

### Community 88 - "Community 88"
Cohesion: 0.12
Nodes (35): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+27 more)

### Community 89 - "Community 89"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 91 - "Community 91"
Cohesion: 0.09
Nodes (31): MATCHING_WINDOWS, PurgeService, ConflictService, DecisionRouteDeps, ConnectionRouteDeps, Direction, KeepSignedIn, KeyExpiresOn (+23 more)

### Community 92 - "Community 92"
Cohesion: 0.11
Nodes (28): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+20 more)

### Community 93 - "Community 93"
Cohesion: 0.09
Nodes (31): bottomTime(), AscentPressure, DiverPlan, GasPlan, GasPlanDiver, GasPlanInput, GasPlanLevel, GasPlanLimit (+23 more)

### Community 94 - "Community 94"
Cohesion: 0.09
Nodes (28): createFitAdapter(), date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser() (+20 more)

### Community 95 - "Community 95"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 96 - "Community 96"
Cohesion: 0.07
Nodes (27): Cylinder, DiveView, RevisionView, ADR-0045, upload(), Candidate, Check, Outcome (+19 more)

### Community 97 - "Community 97"
Cohesion: 0.13
Nodes (30): importsQuery(), Logbook(), ImportContext, ImportFilesButton(), ImportHistory(), ImportList(), ImportNotices(), ImportPanel() (+22 more)

### Community 98 - "Community 98"
Cohesion: 0.14
Nodes (18): AvatarCircle(), DiveRow(), Error(), Fact(), Filters(), Loading(), LogbookList(), LogbookRoute() (+10 more)

### Community 99 - "Community 99"
Cohesion: 0.11
Nodes (9): ApiClient, DiveRepository, neighboursPath(), samplesPath(), onlyParam(), decodeList(), ProviderDirectory, providerDirectory() (+1 more)

### Community 100 - "Community 100"
Cohesion: 0.12
Nodes (4): DiveStore, toModel(), DiveSite, Recording

### Community 101 - "Community 101"
Cohesion: 0.09
Nodes (30): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+22 more)

### Community 102 - "Community 102"
Cohesion: 0.11
Nodes (26): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), typeOf() (+18 more)

### Community 103 - "Community 103"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 104 - "Community 104"
Cohesion: 0.10
Nodes (29): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), divesAtSite() (+21 more)

### Community 105 - "Community 105"
Cohesion: 0.13
Nodes (28): open(), createConnectionService(), checkAutomation(), checkFollowNumbers(), expiryOf(), onTurn(), ownConnection(), signInAt() (+20 more)

### Community 106 - "Community 106"
Cohesion: 0.10
Nodes (24): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearPushRuns(), clearQueue() (+16 more)

### Community 107 - "Community 107"
Cohesion: 0.07
Nodes (23): account, accountRelations, apikey, rateLimit, sessionRelations, ADR-0011, ADR-0012, ADR-0035 (+15 more)

### Community 108 - "Community 108"
Cohesion: 0.12
Nodes (24): samplesQuery(), FindingLane(), useAssessment(), DepthProfile(), NO_POINTS, SurfaceBreakLane(), ADR-0036, ADR-0063 (+16 more)

### Community 110 - "Community 110"
Cohesion: 0.11
Nodes (25): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DueInput, DueSchedule (+17 more)

### Community 111 - "Community 111"
Cohesion: 0.09
Nodes (18): ProblemCode, ResponseState, whenResponseGone(), StoredBlob, PlacedArchive, ANSWER, looksLikeRecipientPrivateKey(), PlacedArchive (+10 more)

### Community 112 - "Community 112"
Cohesion: 0.10
Nodes (26): globalSetup(), ADR-0023, accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks() (+18 more)

### Community 113 - "Community 113"
Cohesion: 0.11
Nodes (7): ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport

### Community 114 - "Community 114"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 115 - "Community 115"
Cohesion: 0.11
Nodes (24): AssessmentService, AssessmentView, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView() (+16 more)

### Community 116 - "Community 116"
Cohesion: 0.09
Nodes (21): DEFAULT_PPO2, GasQuery, GasPlanQuery, PLAN_RULES, PlanRuleName, ruleOf(), Sac, ADR-0028 (+13 more)

### Community 117 - "Community 117"
Cohesion: 0.16
Nodes (24): DiveOrigin, REFERENCE_PREFIX, SsiLogbook, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef() (+16 more)

### Community 118 - "Community 118"
Cohesion: 0.11
Nodes (20): BACKUP_RUN_TASK, backupTask(), ADR-0069, CERTIFICATION_IMAGE_TASK, EXPIRE_WAITING_IMPORTS_TASK, PROCESS_IMPORT_TASK, PUSH_RUN_STEP_TASK, AUTOMATION_TICK_TASK (+12 more)

### Community 119 - "Community 119"
Cohesion: 0.22
Nodes (22): BottomTime, bottomTime(), ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres() (+14 more)

### Community 120 - "Community 120"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 121 - "Community 121"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 122 - "Community 122"
Cohesion: 0.11
Nodes (22): CENTRE_SOURCE_NAME, CENTRE_SOURCES, CentreService, CentreView, CentreViewSchema, Row, Source, toCentreView() (+14 more)

### Community 123 - "Community 123"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 124 - "Community 124"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 125 - "Community 125"
Cohesion: 0.15
Nodes (19): cnsDecayed(), Estimate, isoUtc(), LoadingBasis, LoadingDive, N2Loadings, N2UnavailableReason, no_profile (+11 more)

### Community 126 - "Community 126"
Cohesion: 0.11
Nodes (16): ndl(), ProfileAssessment, Finding, LIMITS, dayNumber(), deepDays(), noFlyAfter(), SERIES_RULES (+8 more)

### Community 127 - "Community 127"
Cohesion: 0.13
Nodes (17): providerGoneDive, compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051, candidatesOf() (+9 more)

### Community 128 - "Community 128"
Cohesion: 0.14
Nodes (18): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+10 more)

### Community 129 - "Community 129"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 130 - "Community 130"
Cohesion: 0.16
Nodes (20): AssessmentError, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), requireManagedDive() (+12 more)

### Community 131 - "Community 131"
Cohesion: 0.12
Nodes (14): LoadingsView, CustomTypeOptions, i18next, initI18n(), resources, ADR-0014, LOADING_SOURCES, loadingNumber() (+6 more)

### Community 132 - "Community 132"
Cohesion: 0.28
Nodes (19): ambientPressureBar(), bestMixPercent(), Conditions, depthOfPressure(), eadMetres(), endMetres(), GasMix, isHypoxic() (+11 more)

### Community 133 - "Community 133"
Cohesion: 0.14
Nodes (18): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+10 more)

### Community 134 - "Community 134"
Cohesion: 0.16
Nodes (16): errorMessage(), formatOf(), createImportService(), cancelImport(), createImport(), endWaiting(), expireWaiting(), openUpload() (+8 more)

### Community 135 - "Community 135"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 136 - "Community 136"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 137 - "Community 137"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 138 - "Community 138"
Cohesion: 0.13
Nodes (17): siteImportStatus, SiteImportRouteDeps, StartBody, STATUS, ADR-0021, SiteImportError, SiteImportService, Area (+9 more)

### Community 139 - "Community 139"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 140 - "Community 140"
Cohesion: 0.13
Nodes (18): defineTool(), Id(), Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), getDiverLoadings (+10 more)

### Community 141 - "Community 141"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 142 - "Community 142"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 143 - "Community 143"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 144 - "Community 144"
Cohesion: 0.11
Nodes (13): BACKUP_EXTENSION, BackupEncryption, BackupPart, BackupLog, BackupRunRow, BackupSettingsInput, BackupSettingsValues, DEFAULT_BACKUP_SETTINGS (+5 more)

### Community 145 - "Community 145"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 146 - "Community 146"
Cohesion: 0.14
Nodes (14): replyCertificationError, STATUS, CertificationService, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams (+6 more)

### Community 147 - "Community 147"
Cohesion: 0.16
Nodes (13): BOM, Json, num(), obj(), positive(), scaled(), suuntoFitKey(), SuuntoJsonAdapter (+5 more)

### Community 148 - "Community 148"
Cohesion: 0.22
Nodes (15): AssessmentView, AssessmentState, clock(), counts(), differing(), Finding, formatRate(), hidden() (+7 more)

### Community 149 - "Community 149"
Cohesion: 0.18
Nodes (11): planningSac(), PlanningSacDive, PlanningSacResult, PlanningSacSource, default, logbook, compressibility(), Cubic (+3 more)

### Community 150 - "Community 150"
Cohesion: 0.15
Nodes (13): Sac, SAC_MISSING, SacMissing, sacOfDive(), SacValues, sacValues(), ADR-0033, ADR-0045 (+5 more)

### Community 151 - "Community 151"
Cohesion: 0.12
Nodes (16): ADR-0013, ADR-0026, ADR-0028, ADR-0030, ADR-0031, ADR-0034, ADR-0035, ADR-0036 (+8 more)

### Community 152 - "Community 152"
Cohesion: 0.17
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 153 - "Community 153"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 154 - "Community 154"
Cohesion: 0.17
Nodes (15): BottomTime, BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen, BottomTimeLimits (+7 more)

### Community 155 - "Community 155"
Cohesion: 0.22
Nodes (13): AppLockProbe, Arguments, credentialOf(), FILE_OPTIONS, fileText(), finishedArguments(), parseArguments(), RestoreCliDeps (+5 more)

### Community 156 - "Community 156"
Cohesion: 0.15
Nodes (13): AssessmentRefresh, ADR-0036, ADR-0053, CandidateRouteDeps, CandidateView, Decided, DiveBrief, OffsetSource (+5 more)

### Community 157 - "Community 157"
Cohesion: 0.17
Nodes (14): AiAccessScope, inputProblem(), loggable(), MAX_RESULT_CHARS, minute(), pgCode(), runTool(), startTimes() (+6 more)

### Community 158 - "Community 158"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 159 - "Community 159"
Cohesion: 0.26
Nodes (14): PlanStretch, temperatureIn(), DepthPlot(), LINE_DASHES, plotFont(), PressureStrip(), RIGHT_AXIS, token() (+6 more)

### Community 160 - "Community 160"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 161 - "Community 161"
Cohesion: 0.20
Nodes (13): dayNumber(), enteredDecoFromNdl(), minNdlSeconds(), NoFly, noFlyAfter(), NoFlyReason, decompression, several (+5 more)

### Community 162 - "Community 162"
Cohesion: 0.26
Nodes (14): jsRound(), powInt(), roundTo(), Sac, SacCylinder, SacDive, SacMissing, cylinder_incomplete (+6 more)

### Community 163 - "Community 163"
Cohesion: 0.13
Nodes (10): EGYPT, ExternalId, HOUSE_REEF, Method, Position, Revision, Site, SiteImport (+2 more)

### Community 164 - "Community 164"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 165 - "Community 165"
Cohesion: 0.18
Nodes (11): addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, relations(), TimelineBar, TimelineBarGeometry, TimelineGapGeometry, TimelineGeometry (+3 more)

### Community 166 - "Community 166"
Cohesion: 0.20
Nodes (10): fileTreeChecksum(), isRecipient(), createBackupService(), checkSettings(), due(), enqueueScheduled(), readSettings(), saveSettings() (+2 more)

### Community 167 - "Community 167"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 168 - "Community 168"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 169 - "Community 169"
Cohesion: 0.17
Nodes (11): FindingValues, RuleId, NoFly, ASSESSMENT_NOTE, FINDING_TEXT, n(), NO_FLY_TEXT, Text (+3 more)

### Community 170 - "Community 170"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 172 - "Community 172"
Cohesion: 0.17
Nodes (5): BodyWeight, Cylinder, Dive, Diver, RecordingSeries

### Community 173 - "Community 173"
Cohesion: 0.20
Nodes (8): ADR-0063, ADR-0040, ADR-0047, diveNumber(), expireSsiTokens(), headers, openFilters(), signInSsi()

### Community 174 - "Community 174"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 175 - "Community 175"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 176 - "Community 176"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 177 - "Community 177"
Cohesion: 0.25
Nodes (7): Profile, SKETCH_HEIGHT, SKETCH_WIDTH, sketchPaths(), sketchScale(), ADR-0041, ADR-0041

### Community 178 - "Community 178"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 179 - "Community 179"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 180 - "Community 180"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 182 - "Community 182"
Cohesion: 0.29
Nodes (9): BackupArchiveError, checkScryptBounds(), drained(), parseHeader(), readUnpackedArchive(), sha256File(), unpackTarStream(), verifyManifest() (+1 more)

### Community 183 - "Community 183"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 184 - "Community 184"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 185 - "Community 185"
Cohesion: 0.28
Nodes (7): ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, s(), ADR-0035

### Community 187 - "Community 187"
Cohesion: 0.25
Nodes (8): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, MODULE_IMPORTS, moduleRules, target()

### Community 188 - "Community 188"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 189 - "Community 189"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 190 - "Community 190"
Cohesion: 0.36
Nodes (6): NOAA_ROWS, otu(), oxygenLimitMinutes(), ADR-0032, evaluateOxygen(), ADR-0032

### Community 191 - "Community 191"
Cohesion: 0.25
Nodes (7): AIR, expectWithinReference(), GF100_FRESH, GF100_SALT, GF85_FRESH, ADR-0032, ADR-0046

### Community 192 - "Community 192"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 193 - "Community 193"
Cohesion: 0.33
Nodes (4): Sketch, SKETCH_POINTS, ADR-0041, ADR-0041

### Community 194 - "Community 194"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 195 - "Community 195"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 196 - "Community 196"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 198 - "Community 198"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 199 - "Community 199"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 202 - "Community 202"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 203 - "Community 203"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 204 - "Community 204"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1237 isolated node(s):** `Off`, `Reserve`, `RockBottom`, `ADR-0031`, `ADR-0032` (+1232 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3297 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **59 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `@tanstack/react-query` connect `Community 4` to `Community 0`, `Community 1`, `Community 97`, `Community 101`, `Community 7`, `Community 8`, `Community 137`, `Community 10`, `Community 44`, `Community 108`, `Community 78`, `Community 18`, `Community 21`, `Community 25`, `Community 26`?**
  _High betweenness centrality (0.027) - this node is a cross-community bridge._
- **What connects `Off`, `Reserve`, `RockBottom` to the rest of the system?**
  _1237 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.024246031746031745 - nodes in this community are weakly interconnected._
- **Why does `drizzle-orm` connect `Community 2` to `Community 130`, `Community 3`, `Community 5`, `Community 6`, `Community 9`, `Community 12`, `Community 14`, `Community 15`, `Community 144`, `Community 17`, `Community 19`, `Community 22`, `Community 23`, `Community 24`, `Community 28`, `Community 157`, `Community 30`, `Community 29`, `Community 31`, `Community 33`, `Community 39`, `Community 47`, `Community 49`, `Community 51`, `Community 52`, `Community 56`, `Community 58`, `Community 60`, `Community 62`, `Community 63`, `Community 67`, `Community 68`, `Community 71`, `Community 87`, `Community 96`, `Community 104`, `Community 107`, `Community 127`?**
  _High betweenness centrality (0.027) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.0502283105022831 - nodes in this community are weakly interconnected._
- **Why does `unwrap()` connect `Community 1` to `Community 0`, `Community 97`, `Community 4`, `Community 7`, `Community 8`, `Community 10`, `Community 44`, `Community 18`, `Community 21`, `Community 25`, `Community 26`?**
  _High betweenness centrality (0.025) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.036662749706227966 - nodes in this community are weakly interconnected._