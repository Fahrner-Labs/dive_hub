# Graph Report - dive_hub  (2026-11-13)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 7090 nodes · 22115 edges · 178 communities (174 shown, 4 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 221 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `56e915ba`
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
- Community 177

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 203 edges
2. `unwrap()` - 200 edges
3. `Button()` - 197 edges
4. `useErrorText()` - 168 edges
5. `useDisplay()` - 156 edges
6. `Muted()` - 137 edges
7. `drizzle-orm` - 131 edges
8. `announce()` - 105 edges
9. `createTestDatabase()` - 99 edges
10. `TestDatabase` - 98 edges

## Surprising Connections (you probably didn't know these)
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `Placement` --references--> `Actor`  [EXTRACTED]
  apps/server/src/imports/placement.ts → apps/server/src/revisions/revisions.ts
- `AuthOptions` --references--> `Db`  [EXTRACTED]
  apps/server/src/auth/auth.ts → apps/server/src/db/client.ts

## Import Cycles
- None detected.

## Communities (178 total, 4 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (157): ParticipantRole, waitingOf(), open(), close(), ConflictEntry, ConflictField, createConflictService(), loadWaiting() (+149 more)

### Community 1 - "Community 1"
Cohesion: 0.05
Nodes (163): ChangePassword(), DeleteUserDialog(), Invite(), LinkToPassOn(), SiteDataPanel(), UserRow(), CreateAccess(), bodyWeightsQuery() (+155 more)

### Community 2 - "Community 2"
Cohesion: 0.04
Nodes (105): user, diver, diveSiteExternalId, revision, ADR-0013, ADR-0011, dumpToolAvailable(), execFileAsync (+97 more)

### Community 3 - "Community 3"
Cohesion: 0.03
Nodes (134): Queryable, CylinderMaterial, diverManagement, OVERRIDABLE_FIELDS, OverridableField, participant, recording, renumber() (+126 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (99): createServer(), createAuth(), loadAuthSecret(), acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS (+91 more)

### Community 5 - "Community 5"
Cohesion: 0.02
Nodes (126): CHANNELS, NOT_COVERED, ADR-0036, actorType, aiAccessOutcome, backupRunKind, backupRunStatus, candidateResolution (+118 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (113): cylinderCatalogueQuery(), DiveImportMode, DiveImportPreview, diveImportPreviewQuery(), DiveKind, diveTimesQuery(), DiveValues, LeadEstimate (+105 more)

### Community 7 - "Community 7"
Cohesion: 0.04
Nodes (119): ComputerChoice, DiveImportMode, DiveKind, importOriginal, providerSiteData, UtcOffsetSource, managedDiverIds(), tieToJoined() (+111 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (111): ImportOutcome, jointDive, logbookCheckAnswer, logbookCheckDiveAnswer, agreeInDepthAndDuration(), CheckedDive, DeletableDive, findChecks() (+103 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (106): LEAD_PLACEMENTS, UTC_OFFSET_SOURCES, WEIGHTING_FELT, downsampleMinMax(), waterMismatch(), LogbookCheckService, AppendDeps, AppendOutcome (+98 more)

### Community 10 - "Community 10"
Cohesion: 0.02
Nodes (90): buddySuggestion, parseHint(), Accepted, Candidate, Check, DiveView, Outcome, RevisionView (+82 more)

### Community 11 - "Community 11"
Cohesion: 0.04
Nodes (92): Tx, diveSite, siteImport, AppendUndo, Context, findOrMake(), ADR-0057, createSitePickService() (+84 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (77): createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities(), ADR-0062 (+69 more)

### Community 13 - "Community 13"
Cohesion: 0.04
Nodes (74): equipmentDueQuery(), EquipmentDueView, EquipmentDueNotice(), ADR-0034, avatarTone(), initials(), dismissDue(), dismissedDueDigest() (+66 more)

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (73): connection, diveLease, providerConflict, push, pushRun, waitingPush, kept(), record() (+65 more)

### Community 15 - "Community 15"
Cohesion: 0.03
Nodes (83): ExternalIdView, isUnauthorized(), Position, SiteImportArea, SiteImportView, siteRevisionsQuery(), SiteRevisionView, App() (+75 more)

### Community 16 - "Community 16"
Cohesion: 0.04
Nodes (82): CertificationService, CYLINDER_MATERIALS, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053, Copies, DeleteProblemSchema (+74 more)

### Community 17 - "Community 17"
Cohesion: 0.04
Nodes (75): ServiceParts, Db, providerLease, tidyName(), DiverDetails, Caller, foldName(), nameDiffers() (+67 more)

### Community 18 - "Community 18"
Cohesion: 0.03
Nodes (85): AcceptedBuddySuggestionView, AnyLogbookCheck, BackupRunView, backupSettingsQuery(), BackupSettingsView, backupsQuery(), buddiesQuery(), BuddySuggestionOverlapView (+77 more)

### Community 19 - "Community 19"
Cohesion: 0.06
Nodes (83): buddySuggestionQuery(), buddySuggestionsQuery(), candidatesQuery(), CandidateView, ConflictChoices, ConflictEntryView, ConflictsOfConnection, GoneDivesOfConnection (+75 more)

### Community 20 - "Community 20"
Cohesion: 0.05
Nodes (85): diveNeighboursQuery(), diversQuery(), divesQuery(), NeighboursView, PAGE_SIZE, RecordingSummary, siteQuery(), Buddies() (+77 more)

### Community 21 - "Community 21"
Cohesion: 0.04
Nodes (83): buddiesForQuery(), ConflictFieldView, ProblemCode, providerSitesQuery(), providersQuery(), providerStatusQuery(), ProviderView, RequirementView (+75 more)

### Community 22 - "Community 22"
Cohesion: 0.06
Nodes (73): allButDive(), ApiError, continuationCandidateQuery(), deletedDivesQuery(), DeletedDiveView, diveProvidersQuery(), diveQuery(), DiveView (+65 more)

### Community 23 - "Community 23"
Cohesion: 0.03
Nodes (67): AssessmentService, AssessmentView, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView() (+59 more)

### Community 24 - "Community 24"
Cohesion: 0.04
Nodes (64): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+56 more)

### Community 25 - "Community 25"
Cohesion: 0.07
Nodes (61): api, BuddyView, centreQuery(), centresQuery(), CentreView, ConnectionView, diverDetailsQuery(), DiverDetailsView (+53 more)

### Community 26 - "Community 26"
Cohesion: 0.05
Nodes (59): sessionIdOf(), hashPassword(), invitation, passwordReset, userRole, AttemptLimit, clientKey(), limitAttempts() (+51 more)

### Community 27 - "Community 27"
Cohesion: 0.06
Nodes (57): DIVE_FIELDS, OutgoingDive, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf() (+49 more)

### Community 28 - "Community 28"
Cohesion: 0.07
Nodes (61): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), clearFailed(), clearPassed(), countingBase() (+53 more)

### Community 29 - "Community 29"
Cohesion: 0.07
Nodes (53): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ambientPressureBar(), bestMixPercent() (+45 more)

### Community 30 - "Community 30"
Cohesion: 0.10
Nodes (56): buildApp(), assessmentRoutes(), SessionUser, authHandler(), authHeaders(), fastify, FastifyRequest, forwardCookies() (+48 more)

### Community 31 - "Community 31"
Cohesion: 0.05
Nodes (55): AiAccessScope, diversOf(), SiteRow, Cursor, cursorAt(), DateOnly(), Detail, diversAsked() (+47 more)

### Community 32 - "Community 32"
Cohesion: 0.07
Nodes (53): DisplaySettings(), SecuritySettings(), Sessions(), useDescribeAgent(), AdminSettings(), Invitations(), ProviderSiteData(), ADR-0012 (+45 more)

### Community 33 - "Community 33"
Cohesion: 0.06
Nodes (55): ConnectionRow, WaitingPushOptions, audienceOfConnection(), audienceOfDive(), audienceOfDiver(), Hint, HINT_KINDS, HintAudience (+47 more)

### Community 34 - "Community 34"
Cohesion: 0.05
Nodes (49): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, Services, DIVE_IMPORT_MODES, ADR-0053 (+41 more)

### Community 35 - "Community 35"
Cohesion: 0.06
Nodes (48): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+40 more)

### Community 36 - "Community 36"
Cohesion: 0.08
Nodes (44): SsiRecord, buildCreateRecord(), buildDeleteRecord(), buildUpdateRecord(), DiveForSsi, emptyRecord(), keysOf(), writableRecord() (+36 more)

### Community 37 - "Community 37"
Cohesion: 0.06
Nodes (44): CENTRE_SOURCES, CentreService, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+36 more)

### Community 38 - "Community 38"
Cohesion: 0.07
Nodes (46): managementOf(), EquipmentError, ADR-0034, ADR-0053, checkCost(), checkDates(), checkWindow(), createEquipmentService() (+38 more)

### Community 39 - "Community 39"
Cohesion: 0.07
Nodes (45): Series, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining(), ssiDiveType() (+37 more)

### Community 40 - "Community 40"
Cohesion: 0.07
Nodes (45): serviceRecord, serviceRecordSchedule, serviceSchedule, addMonths(), CountDue, countReason(), dimensionState(), divesLeft() (+37 more)

### Community 41 - "Community 41"
Cohesion: 0.07
Nodes (37): createSsiSiteSource(), DEFAULT_SSI_SITES_URL, createSiteSources(), ADR-0021, ADR-0027, createOverpassSource(), DEFAULT_OVERPASS_URL, Element (+29 more)

### Community 42 - "Community 42"
Cohesion: 0.08
Nodes (37): BottomTimeQuery, DEFAULT_GF_HIGH, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033, DEFAULT_PPO2 (+29 more)

### Community 43 - "Community 43"
Cohesion: 0.09
Nodes (43): BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32HrpExpand(), bech32Polymod(), checkScryptBounds() (+35 more)

### Community 44 - "Community 44"
Cohesion: 0.06
Nodes (42): ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, connectGarmin(), Connection, DatedDive (+34 more)

### Community 45 - "Community 45"
Cohesion: 0.05
Nodes (42): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+34 more)

### Community 46 - "Community 46"
Cohesion: 0.06
Nodes (38): ADR-0025, WaterMismatch, computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL (+30 more)

### Community 47 - "Community 47"
Cohesion: 0.08
Nodes (28): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+20 more)

### Community 48 - "Community 48"
Cohesion: 0.07
Nodes (37): device, DiverError, ADR-0016, ADR-0053, ACCOUNT_PATTERN, accountKey(), createDiverService(), applyDetails() (+29 more)

### Community 49 - "Community 49"
Cohesion: 0.06
Nodes (40): Change(), Changes(), historyRoute, Made(), REVISION_CAUSES, PositionSchema, AdoptBody, Country (+32 more)

### Community 50 - "Community 50"
Cohesion: 0.05
Nodes (42): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+34 more)

### Community 51 - "Community 51"
Cohesion: 0.05
Nodes (32): account, accountRelations, apikey, rateLimit, session, sessionRelations, ADR-0011, ADR-0035 (+24 more)

### Community 52 - "Community 52"
Cohesion: 0.10
Nodes (39): pushRunActive(), pushRunBody(), PushRunChoices, PushRunDetail, PushRunPreview, pushRunPreviewQuery(), pushRunQuery(), pushRunsQuery() (+31 more)

### Community 53 - "Community 53"
Cohesion: 0.10
Nodes (33): samplesQuery(), DepthProfile(), NO_POINTS, SurfaceBreakLane(), ADR-0036, ADR-0063, nearest(), perMinute() (+25 more)

### Community 54 - "Community 54"
Cohesion: 0.06
Nodes (31): replyCertificationError, STATUS, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, CertificationRouteDeps (+23 more)

### Community 55 - "Community 55"
Cohesion: 0.07
Nodes (37): ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, seriesOf(), minutes(), Channel (+29 more)

### Community 56 - "Community 56"
Cohesion: 0.08
Nodes (35): @modelcontextprotocol/server@^2.3.1, aiAccessLog, aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, AiAccessService, KeyRow (+27 more)

### Community 57 - "Community 57"
Cohesion: 0.08
Nodes (35): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+27 more)

### Community 58 - "Community 58"
Cohesion: 0.07
Nodes (32): cylinder, Card, DEEP, OWD, Report, Summary, ADR-0050, WRECK (+24 more)

### Community 59 - "Community 59"
Cohesion: 0.09
Nodes (29): ImportErrorCode, ImportFound, errorMessage(), FileFormat, formatOf(), CHOICE_WAIT_DAYS, createImportService(), cancelImport() (+21 more)

### Community 60 - "Community 60"
Cohesion: 0.10
Nodes (30): bar(), celsius(), contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf(), firstOf() (+22 more)

### Community 61 - "Community 61"
Cohesion: 0.09
Nodes (36): CylinderMaterial, BOTTOM_TIME_RULES, cylinderOf(), cylinderText(), diverOf(), diverText(), finite(), kept (+28 more)

### Community 62 - "Community 62"
Cohesion: 0.11
Nodes (35): massIn(), Dive, draftOfLead(), draftOfWeighting(), emptyLead(), fromLastDive(), hasThickness(), kg() (+27 more)

### Community 63 - "Community 63"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 64 - "Community 64"
Cohesion: 0.09
Nodes (22): auth, BackupServiceDeps, out, services, createSecretBox(), ADR-0024, SiteSourceAdapter, BlobStore (+14 more)

### Community 65 - "Community 65"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 66 - "Community 66"
Cohesion: 0.08
Nodes (34): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+26 more)

### Community 67 - "Community 67"
Cohesion: 0.08
Nodes (30): SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject(), MAX_CARD_IMAGE_BYTES (+22 more)

### Community 68 - "Community 68"
Cohesion: 0.19
Nodes (35): APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed(), flag() (+27 more)

### Community 69 - "Community 69"
Cohesion: 0.13
Nodes (35): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+27 more)

### Community 70 - "Community 70"
Cohesion: 0.15
Nodes (32): assessmentQuery(), AssessmentView, AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState, FindingItem() (+24 more)

### Community 71 - "Community 71"
Cohesion: 0.12
Nodes (33): importsQuery(), ImportView, noAnswerError(), uploadFile(), Logbook(), ImportContext, ImportFilesButton(), ImportHistory() (+25 more)

### Community 72 - "Community 72"
Cohesion: 0.06
Nodes (35): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+27 more)

### Community 73 - "Community 73"
Cohesion: 0.11
Nodes (28): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0051 (+20 more)

### Community 74 - "Community 74"
Cohesion: 0.09
Nodes (30): PARTICIPANT_ROLES, storedProblemCode(), oneOf(), DiveProviderStatus, DiveProviderStatusSchema, LeftOut, PushView, pushView() (+22 more)

### Community 75 - "Community 75"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 76 - "Community 76"
Cohesion: 0.08
Nodes (26): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+18 more)

### Community 77 - "Community 77"
Cohesion: 0.07
Nodes (26): Cylinder, DiveView, RevisionView, ADR-0045, upload(), Candidate, Check, Outcome (+18 more)

### Community 78 - "Community 78"
Cohesion: 0.09
Nodes (25): headers, ADR-0030, ADR-0040, conflictForLena(), diveWithoutRecording(), externalDiver(), importLena(), leaveLena() (+17 more)

### Community 79 - "Community 79"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 80 - "Community 80"
Cohesion: 0.12
Nodes (28): AscentBand, ascentBandsOf(), assessProfile(), ceiling(), judgedParts(), lastMetres(), ppo2(), Profile (+20 more)

### Community 81 - "Community 81"
Cohesion: 0.12
Nodes (25): BACKUP_SKIPPED_DIRS, BackupDecryptionKey, encryptionKeyFingerprint(), pgCommand(), pgFailureMessage(), redactPassword(), createRestoreService(), restore() (+17 more)

### Community 82 - "Community 82"
Cohesion: 0.09
Nodes (26): CERTIFICATION_IMAGE_ATTEMPTS, CertificationError, CertificationExpiry, CertificationInput, CertificationView, createCertificationService(), cleaned(), requireManagedDiver() (+18 more)

### Community 83 - "Community 83"
Cohesion: 0.07
Nodes (21): CustomTypeOptions, i18next, initI18n(), resources, ADR-0014, StrayOverride, ADR-0014, english (+13 more)

### Community 84 - "Community 84"
Cohesion: 0.10
Nodes (26): ENGINE_VERSION, EVIDENCE, Evidence, FindingValues, RULE_IDS, RuleId, RULES, SEVERITIES (+18 more)

### Community 85 - "Community 85"
Cohesion: 0.09
Nodes (26): Blocked, classify(), kindOf(), KINDS, LEFT_OUT_KEY, LEFT_OUT_KEYS, LeftOutReason, numberOnlyPlan() (+18 more)

### Community 86 - "Community 86"
Cohesion: 0.15
Nodes (25): SsiLogbook, asBool(), aboveZero(), contextOf(), cylinderOf(), deviceOf(), deviceOfRef(), MATERIAL_OF_TANK_TYPE (+17 more)

### Community 87 - "Community 87"
Cohesion: 0.09
Nodes (28): devDependencies, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2, @testing-library/react@^16.3.3 (+20 more)

### Community 88 - "Community 88"
Cohesion: 0.14
Nodes (29): bottomTimeRule(), defined(), leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas, toolsGasQuery(), toolsPlanQuery() (+21 more)

### Community 89 - "Community 89"
Cohesion: 0.11
Nodes (28): ToolsPlan, ToolsPlanDiver, PlanDiver, PlanLevel, planSketch(), PlanStretch, ToolsRule, DEFAULT_CYLINDER (+20 more)

### Community 90 - "Community 90"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 91 - "Community 91"
Cohesion: 0.08
Nodes (24): diveFinding, Assessment, FindingView, Row, ADR-0036, upload(), Check, Deleted (+16 more)

### Community 92 - "Community 92"
Cohesion: 0.11
Nodes (25): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+17 more)

### Community 93 - "Community 93"
Cohesion: 0.10
Nodes (23): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+15 more)

### Community 94 - "Community 94"
Cohesion: 0.10
Nodes (21): BOM, Json, num(), obj(), positive(), scaled(), SuuntoJsonAdapter, time() (+13 more)

### Community 95 - "Community 95"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 96 - "Community 96"
Cohesion: 0.11
Nodes (23): SiteImportCounts, Field, has(), inArea(), LatitudeBands, MATCH_WITHIN_M, merged(), NEAR_WITHIN_M (+15 more)

### Community 97 - "Community 97"
Cohesion: 0.12
Nodes (23): accountListEntry, diverExternalId, forget(), listAccountOf(), remember(), rememberedEntries(), rememberedFor(), resolvedRows() (+15 more)

### Community 98 - "Community 98"
Cohesion: 0.11
Nodes (20): BACKUP_RUN_TASK, backupTask(), ADR-0069, CERTIFICATION_IMAGE_TASK, EXPIRE_WAITING_IMPORTS_TASK, PROCESS_IMPORT_TASK, PUSH_RUN_STEP_TASK, WAITING_PUSHES_TASK (+12 more)

### Community 99 - "Community 99"
Cohesion: 0.11
Nodes (20): createFileFormats(), createFitAdapter(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, looksLikeUddf(), ownersParts() (+12 more)

### Community 100 - "Community 100"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 101 - "Community 101"
Cohesion: 0.09
Nodes (23): pushRunItem, PushRunLeftOut, DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN, NO_RECORDING (+15 more)

### Community 102 - "Community 102"
Cohesion: 0.11
Nodes (17): ProblemCode, ResponseState, whenResponseGone(), StoredBlob, PlacedArchive, ANSWER, looksLikeRecipientPrivateKey(), PlacedArchive (+9 more)

### Community 103 - "Community 103"
Cohesion: 0.15
Nodes (21): Answer, CatalogueCylinder, CylinderInput, cylindersOfDrafts(), CylinderView, draftOfCylinder(), emptyDraft(), fromCatalogue() (+13 more)

### Community 104 - "Community 104"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 105 - "Community 105"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 106 - "Community 106"
Cohesion: 0.16
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 107 - "Community 107"
Cohesion: 0.09
Nodes (20): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+12 more)

### Community 108 - "Community 108"
Cohesion: 0.11
Nodes (14): SsiClient, describe(), isObject(), keyDifferences(), pause(), recording(), runAnswerChecks(), sampleCount() (+6 more)

### Community 109 - "Community 109"
Cohesion: 0.12
Nodes (22): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+14 more)

### Community 110 - "Community 110"
Cohesion: 0.09
Nodes (23): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+15 more)

### Community 111 - "Community 111"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 112 - "Community 112"
Cohesion: 0.15
Nodes (18): durationFormat(), DurationFormatter, formatDuration(), formatGasRate(), formatMass(), formatPressure(), formatPressureRate(), formatTemperature() (+10 more)

### Community 113 - "Community 113"
Cohesion: 0.13
Nodes (17): AssessmentError, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), requireManagedDive(), isRule() (+9 more)

### Community 114 - "Community 114"
Cohesion: 0.14
Nodes (16): providerGoneDive, compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051, candidatesOf() (+8 more)

### Community 115 - "Community 115"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 116 - "Community 116"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 117 - "Community 117"
Cohesion: 0.13
Nodes (17): siteImportStatus, SiteImportRouteDeps, StartBody, STATUS, ADR-0021, SiteImportError, SiteImportService, Area (+9 more)

### Community 118 - "Community 118"
Cohesion: 0.13
Nodes (15): FIND_BY, OPERATIONS, SignInInput, CASES, ContractHarness, declared(), ELSEWHERE, later() (+7 more)

### Community 119 - "Community 119"
Cohesion: 0.13
Nodes (16): stubDive(), ADR-0015, ADR-0023, ADR-0025, created, headers, importFixture(), ADR-0066 (+8 more)

### Community 120 - "Community 120"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 121 - "Community 121"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 122 - "Community 122"
Cohesion: 0.11
Nodes (13): BACKUP_EXTENSION, BackupEncryption, BackupPart, BackupLog, BackupRunRow, BackupSettingsInput, BackupSettingsValues, DEFAULT_BACKUP_SETTINGS (+5 more)

### Community 123 - "Community 123"
Cohesion: 0.14
Nodes (17): ImportArea, ImportedValues, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, ExistingSite, PlannedCreate (+9 more)

### Community 124 - "Community 124"
Cohesion: 0.14
Nodes (17): defineTool(), Id(), Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), HINT (+9 more)

### Community 125 - "Community 125"
Cohesion: 0.18
Nodes (15): failureOutcome(), numberPlanOf(), orderItems(), createPushRunService(), analyse(), finish(), numbersHere(), ownConnection() (+7 more)

### Community 126 - "Community 126"
Cohesion: 0.16
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 127 - "Community 127"
Cohesion: 0.15
Nodes (15): ascentRate(), descentRate(), worstOf(), LIMITS, dayNumber(), deepDays(), noFlyAfter(), reverseProfile() (+7 more)

### Community 128 - "Community 128"
Cohesion: 0.20
Nodes (14): AppLockProbe, BackupManifest, Arguments, credentialOf(), FILE_OPTIONS, fileText(), finishedArguments(), parseArguments() (+6 more)

### Community 129 - "Community 129"
Cohesion: 0.18
Nodes (14): siteSource, looksLikeZip(), coordinate(), parseSsiSites(), sitesJson(), truthy(), ADR-0024, unavailable() (+6 more)

### Community 130 - "Community 130"
Cohesion: 0.17
Nodes (13): createUddfAdapter(), parseXml(), decimal(), dive(), makeOceanicPlusUddf(), OCEANIC_DIVES, OceanicDive, site() (+5 more)

### Community 131 - "Community 131"
Cohesion: 0.14
Nodes (14): SUIT_TYPES, SUIT_UNDERGARMENTS, replyDiverError, STATUS, LeadService, Amount, EstimateQuery, EstimateView (+6 more)

### Community 132 - "Community 132"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 133 - "Community 133"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 134 - "Community 134"
Cohesion: 0.26
Nodes (14): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), GAS, minutes(), NEW_KEYS, parse() (+6 more)

### Community 135 - "Community 135"
Cohesion: 0.13
Nodes (10): EGYPT, ExternalId, HOUSE_REEF, Method, Position, Revision, Site, SiteImport (+2 more)

### Community 136 - "Community 136"
Cohesion: 0.20
Nodes (11): clearQueue(), fetchState(), leaveConflict(), queueEntry(), ssiConnectionOf(), automatic(), leave(), openConnections() (+3 more)

### Community 137 - "Community 137"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 138 - "Community 138"
Cohesion: 0.36
Nodes (4): decode(), localName(), Reader, XmlError

### Community 139 - "Community 139"
Cohesion: 0.22
Nodes (11): fromOsm(), fromSsi(), fromWd(), HOUSE_REEF, osm(), site(), ssi(), ADR-0021 (+3 more)

### Community 140 - "Community 140"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 141 - "Community 141"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 142 - "Community 142"
Cohesion: 0.21
Nodes (9): tarDirectory(), createBackupService(), checkSettings(), due(), enqueueScheduled(), readSettings(), saveSettings(), sweepRetention() (+1 more)

### Community 143 - "Community 143"
Cohesion: 0.17
Nodes (6): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, uniqueWord()

### Community 144 - "Community 144"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 145 - "Community 145"
Cohesion: 0.17
Nodes (7): ndl(), ProfileAssessment, Finding, SeriesDive, CLEAN, samples, ADR-0036

### Community 146 - "Community 146"
Cohesion: 0.26
Nodes (11): RecordingSummary, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput, round3() (+3 more)

### Community 147 - "Community 147"
Cohesion: 0.26
Nodes (7): DiveSummary, Profile, SKETCH_HEIGHT, SKETCH_WIDTH, sketchPaths(), ADR-0041, ADR-0041

### Community 148 - "Community 148"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 149 - "Community 149"
Cohesion: 0.18
Nodes (5): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053

### Community 150 - "Community 150"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 151 - "Community 151"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 152 - "Community 152"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 153 - "Community 153"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 154 - "Community 154"
Cohesion: 0.27
Nodes (9): BACKUP_FORMAT_VERSION, buildManifest(), decryptFile(), decryptFileWith(), fileTreeChecksum(), manifestFor(), partOf(), sha256() (+1 more)

### Community 155 - "Community 155"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 156 - "Community 156"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 157 - "Community 157"
Cohesion: 0.20
Nodes (10): verify, major, minor, sha, tag, version, name, outputs (+2 more)

### Community 158 - "Community 158"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 160 - "Community 160"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 161 - "Community 161"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 162 - "Community 162"
Cohesion: 0.36
Nodes (8): bech32Encode(), convertBits(), encodeRecipient(), encodeRecipientPrivateKey(), generateRecipientKeyPair(), jwkOf(), rawPrivateOf(), rawPublicOf()

### Community 163 - "Community 163"
Cohesion: 0.25
Nodes (7): AIR, expectWithinReference(), GF100_FRESH, GF100_SALT, GF85_FRESH, ADR-0032, ADR-0046

### Community 164 - "Community 164"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 165 - "Community 165"
Cohesion: 0.29
Nodes (7): ADR-0023, ADR-0053, ADR-0066, ADR-0070, MODULE_IMPORTS, moduleRules, target()

### Community 166 - "Community 166"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 167 - "Community 167"
Cohesion: 0.33
Nodes (4): pollUntilDue(), ADR-0056, now, ADR-0056

### Community 168 - "Community 168"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 169 - "Community 169"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 170 - "Community 170"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 171 - "Community 171"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 172 - "Community 172"
Cohesion: 0.40
Nodes (4): TAGS, ADR-0023, { stdout, stderr }, total

### Community 173 - "Community 173"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 174 - "Community 174"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

## Knowledge Gaps
- **1108 isolated node(s):** `ADR-0056`, `ADR-0024`, `ADR-0030`, `ADR-0056`, `ADR-0025` (+1103 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 2878 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **4 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 2` to `Community 0`, `Community 3`, `Community 4`, `Community 5`, `Community 7`, `Community 8`, `Community 10`, `Community 11`, `Community 12`, `Community 14`, `Community 17`, `Community 24`, `Community 26`, `Community 31`, `Community 33`, `Community 34`, `Community 38`, `Community 40`, `Community 42`, `Community 48`, `Community 51`, `Community 56`, `Community 58`, `Community 59`, `Community 64`, `Community 66`, `Community 82`, `Community 90`, `Community 91`, `Community 97`, `Community 101`, `Community 110`, `Community 114`, `Community 122`?**
  _High betweenness centrality (0.026) - this node is a cross-community bridge._
- **What connects `ADR-0056`, `ADR-0024`, `ADR-0030` to the rest of the system?**
  _1108 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.02258504949368529 - nodes in this community are weakly interconnected._
- **Why does `@playwright/test` connect `Community 47` to `Community 100`, `Community 132`, `Community 136`, `Community 73`, `Community 44`, `Community 45`, `Community 78`, `Community 143`, `Community 144`, `Community 150`, `Community 119`, `Community 151`, `Community 87`, `Community 93`?**
  _High betweenness centrality (0.025) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.052282430213464695 - nodes in this community are weakly interconnected._
- **Why does `react` connect `Community 13` to `Community 1`, `Community 6`, `Community 15`, `Community 18`, `Community 19`, `Community 20`, `Community 21`, `Community 22`, `Community 147`, `Community 25`, `Community 32`, `Community 52`, `Community 53`, `Community 62`, `Community 70`, `Community 71`, `Community 87`, `Community 88`, `Community 89`, `Community 109`, `Community 116`?**
  _High betweenness centrality (0.018) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.044705160647189636 - nodes in this community are weakly interconnected._