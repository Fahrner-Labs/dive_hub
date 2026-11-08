# Graph Report - dive_hub  (2026-11-08)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 6347 nodes · 19917 edges · 154 communities (150 shown, 4 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 198 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `fb0f43a0`
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

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 181 edges
2. `Button()` - 178 edges
3. `unwrap()` - 175 edges
4. `useErrorText()` - 148 edges
5. `useDisplay()` - 148 edges
6. `drizzle-orm` - 118 edges
7. `Muted()` - 117 edges
8. `announce()` - 95 edges
9. `createTestDatabase()` - 87 edges
10. `TestDatabase` - 86 edges

## Surprising Connections (you probably didn't know these)
- `previousFile` --indirect_call--> `today()`  [INFERRED]
  scripts/drift-report.mjs → apps/web/src/BodyWeight.tsx
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `askedAt()` --calls--> `DiveError`  [EXTRACTED]
  apps/server/src/dives/continuation/append-service.ts → apps/server/src/dives/dive-service.ts

## Import Cycles
- None detected.

## Communities (154 total, 4 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.03
Nodes (163): Tx, continuationAppend, diverManagement, LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, participant, sampleSeries (+155 more)

### Community 1 - "Community 1"
Cohesion: 0.06
Nodes (171): ChangePassword(), DisplaySettings(), SecuritySettings(), Sessions(), useDescribeAgent(), AdminSettings(), DeleteUserDialog(), Invitations() (+163 more)

### Community 2 - "Community 2"
Cohesion: 0.02
Nodes (154): AiAccessLogEntry, AiAccessView, AnyLogbookCheck, api, ApiError, buddiesForQuery(), buddiesQuery(), BuddyView (+146 more)

### Community 3 - "Community 3"
Cohesion: 0.03
Nodes (148): allButDive(), candidatesQuery(), CandidateView, ConflictChoices, ConflictEntryView, ConflictsOfConnection, continuationCandidateQuery(), deletedDivesQuery() (+140 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (136): ImportArea, ImportedValues, SiteImportCounts, ADR-0021, ADR-0025, ADR-0053, diveSite, siteImport (+128 more)

### Community 5 - "Community 5"
Cohesion: 0.04
Nodes (107): user, diver, diveSiteExternalId, revision, listAccountOf(), createSsiClient(), ADR-0013, ADR-0011 (+99 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (139): ConnectionRow, WaitingPushOptions, waitingOf(), audienceOfConnection(), audienceOfDiver(), open(), createConnectionService(), checkAutomation() (+131 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (138): bottomTimeRule(), cylinderCatalogueQuery(), defined(), DiveView, LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery() (+130 more)

### Community 8 - "Community 8"
Cohesion: 0.04
Nodes (138): ComputerChoice, DiveImportMode, DiveKind, importOriginal, ProviderImportPlan, providerSiteData, managedDiverIds(), mergeProviderCylinder() (+130 more)

### Community 9 - "Community 9"
Cohesion: 0.04
Nodes (79): Me, ParticipantRole, ParticipantView, cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState (+71 more)

### Community 10 - "Community 10"
Cohesion: 0.04
Nodes (101): Queryable, logbookCheckAnswer, logbookCheckDiveAnswer, recording, recordingEvent, keepApart(), keepDive(), keepRestored() (+93 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (89): accountListEntry, diverExternalId, ADR-0016, ADR-0053, tidyName(), ACCOUNT_PATTERN, DETAIL_FIELDS, DETAIL_PATTERN (+81 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (85): Cylinder, DiveView, RevisionView, ADR-0045, upload(), DiveView, RevisionView, ADR-0015 (+77 more)

### Community 13 - "Community 13"
Cohesion: 0.04
Nodes (69): createServer(), createAuth(), auth, loadAuthSecret(), workerDeps(), Config, loadConfig(), ADR-0011 (+61 more)

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (89): PARTICIPANT_ROLES, downsampleMinMax(), AppendDeps, AppendOutcome, AppendService, Side, ADR-0038, ADR-0063 (+81 more)

### Community 15 - "Community 15"
Cohesion: 0.04
Nodes (92): divesQuery(), DiveSummary, LogbookPage, PAGE_SIZE, pushRunActive(), pushRunBody(), PushRunChoices, PushRunDetail (+84 more)

### Community 16 - "Community 16"
Cohesion: 0.04
Nodes (70): connection, diveLease, importJob, ImportOutcome, providerConflict, push, pushRun, waitingPush (+62 more)

### Community 17 - "Community 17"
Cohesion: 0.03
Nodes (67): AuthOptions, createServices(), ServiceParts, Services, ADR-0027, ADR-0030, ADR-0061, ADR-0063 (+59 more)

### Community 18 - "Community 18"
Cohesion: 0.05
Nodes (71): ADR-0050, DIVE_IMPORT_MODES, DIVE_KINDS, DiveSummaryFields, SummaryRow, ADR-0053, ADR-0056, WaitingPushView (+63 more)

### Community 19 - "Community 19"
Cohesion: 0.02
Nodes (81): StoredConflictField, actorType, aiAccessOutcome, candidateResolution, centreSource, CERTIFICATION_IMAGE_STATES, CERTIFICATION_VERIFICATIONS, certificationImageState (+73 more)

### Community 20 - "Community 20"
Cohesion: 0.05
Nodes (69): adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, ADR-0011, ADR-0013, ADR-0035 (+61 more)

### Community 21 - "Community 21"
Cohesion: 0.05
Nodes (57): SsiClient, SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), buildUpdateRecord(), DiveForSsi, emptyRecord() (+49 more)

### Community 22 - "Community 22"
Cohesion: 0.05
Nodes (50): createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities(), providerError() (+42 more)

### Community 23 - "Community 23"
Cohesion: 0.05
Nodes (66): assessmentQuery(), diveNeighboursQuery(), DiveValues, NeighboursView, OverridableField, RecordingSummary, revisionsQuery(), RevisionView (+58 more)

### Community 24 - "Community 24"
Cohesion: 0.08
Nodes (65): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, assessmentRoutes(), AssessmentViewSchema (+57 more)

### Community 25 - "Community 25"
Cohesion: 0.05
Nodes (61): AssessmentService, AssessmentRouteDeps, Auth, replyCertificationError, STATUS, CertificationService, CertificationView, Day (+53 more)

### Community 26 - "Community 26"
Cohesion: 0.06
Nodes (62): devicesQuery(), DeviceView, setupQuery(), SiteImportArea, SiteImportView, App(), CentrePage, CentresPage (+54 more)

### Community 27 - "Community 27"
Cohesion: 0.05
Nodes (57): AiAccessScope, seesPositions(), VerifiedAccess, diversOf(), checkedByRunTool, createMcpServer(), INSTRUCTIONS, MCP_SERVER_INFO (+49 more)

### Community 28 - "Community 28"
Cohesion: 0.06
Nodes (49): looksLikeZip(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy(), ADR-0024 (+41 more)

### Community 29 - "Community 29"
Cohesion: 0.06
Nodes (52): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, answer(), baseFrom(), cylindersAtReserve(), CylinderTerm (+44 more)

### Community 30 - "Community 30"
Cohesion: 0.07
Nodes (56): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+48 more)

### Community 31 - "Community 31"
Cohesion: 0.07
Nodes (49): Delivered, DIVE_FIELDS, ProviderError, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord() (+41 more)

### Community 32 - "Community 32"
Cohesion: 0.06
Nodes (54): ConflictFieldView, diveListPages, ProblemCode, providerSitesQuery(), providersQuery(), ProviderStatusView, PushView, RequirementView (+46 more)

### Community 33 - "Community 33"
Cohesion: 0.04
Nodes (39): ParticipantRole, PushLeftOut, Capabilities, Direction, DiveExport, DiveListing, FindBy, ImportedDive (+31 more)

### Community 34 - "Community 34"
Cohesion: 0.06
Nodes (33): headers, ADR-0031, headers, ADR-0043, cameraShowing(), headers, ADR-0043, ADR-0016 (+25 more)

### Community 35 - "Community 35"
Cohesion: 0.07
Nodes (46): Leave, Series, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+38 more)

### Community 36 - "Community 36"
Cohesion: 0.05
Nodes (49): LEAD_PLACEMENTS, SUIT_TYPES, SUIT_UNDERGARMENTS, WEIGHTING_FELT, ProfileSummary, round1(), Series, summariseProfile() (+41 more)

### Community 37 - "Community 37"
Cohesion: 0.09
Nodes (41): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ambientPressureBar(), bestMixPercent() (+33 more)

### Community 38 - "Community 38"
Cohesion: 0.07
Nodes (38): ImportErrorCode, ImportFound, errorMessage(), ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS, eachFile() (+30 more)

### Community 39 - "Community 39"
Cohesion: 0.09
Nodes (36): sacOfDive(), BottomTimeQuery, DEFAULT_GF_HIGH, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033 (+28 more)

### Community 40 - "Community 40"
Cohesion: 0.07
Nodes (44): CylinderMaterial, BOTTOM_TIME_RULES, cylinderOf(), cylinderText(), diverOf(), diverText(), finite(), kept (+36 more)

### Community 41 - "Community 41"
Cohesion: 0.05
Nodes (41): headers, ADR-0035, areaOf(), areas, capture(), findings, headers, ADR-0020 (+33 more)

### Community 42 - "Community 42"
Cohesion: 0.06
Nodes (39): ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, connectGarmin(), Connection, DatedDive (+31 more)

### Community 43 - "Community 43"
Cohesion: 0.07
Nodes (39): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+31 more)

### Community 44 - "Community 44"
Cohesion: 0.06
Nodes (35): CYLINDER_MATERIALS, DiverCertifications, replyDiverError, STATUS, DiverService, CertificationSummary, DiverDetailsView, DiverDetailsViewSchema (+27 more)

### Community 45 - "Community 45"
Cohesion: 0.08
Nodes (39): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), divesAtSite() (+31 more)

### Community 46 - "Community 46"
Cohesion: 0.07
Nodes (34): sampleCount(), SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject() (+26 more)

### Community 47 - "Community 47"
Cohesion: 0.05
Nodes (41): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+33 more)

### Community 48 - "Community 48"
Cohesion: 0.06
Nodes (37): Change(), Changes(), historyRoute, Made(), REVISION_CAUSES, PositionSchema, AdoptBody, Country (+29 more)

### Community 49 - "Community 49"
Cohesion: 0.08
Nodes (32): date(), FitMessage, FitMessages, GARMIN_PRODUCTS, num(), position(), positive(), RECORD_CHANNELS (+24 more)

### Community 50 - "Community 50"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 51 - "Community 51"
Cohesion: 0.07
Nodes (32): ADR-0025, WaterMismatch, waterMismatch(), CIRCUIT, circuitFromFit, DECO_MODEL, decoModelFromFit, DIVE_ALERT (+24 more)

### Community 52 - "Community 52"
Cohesion: 0.11
Nodes (35): massIn(), Dive, draftOfLead(), draftOfWeighting(), emptyLead(), fromLastDive(), hasThickness(), kg() (+27 more)

### Community 53 - "Community 53"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 54 - "Community 54"
Cohesion: 0.11
Nodes (34): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+26 more)

### Community 55 - "Community 55"
Cohesion: 0.08
Nodes (31): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+23 more)

### Community 56 - "Community 56"
Cohesion: 0.08
Nodes (27): RecordingSummary, UtcOffsetSource, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput (+19 more)

### Community 57 - "Community 57"
Cohesion: 0.08
Nodes (32): DIVE_ONLY_PATTERN, storedProblemCode(), oneOf(), PushView, ACTIONS, errors, ItemView, OUTCOMES (+24 more)

### Community 58 - "Community 58"
Cohesion: 0.13
Nodes (34): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+26 more)

### Community 59 - "Community 59"
Cohesion: 0.08
Nodes (31): AI_ACCESS_NAME_MAX, aiAccessLog, aiAccessSetting, Access, AccessView, AiAccessRouteDeps, LogEntryView, Scope (+23 more)

### Community 60 - "Community 60"
Cohesion: 0.10
Nodes (28): diveWith(), ADR-0028, ADR-0043, forget(), headers, ADR-0043, ADR-0051, headers (+20 more)

### Community 61 - "Community 61"
Cohesion: 0.06
Nodes (34): build, depends_on, environment, image, ports, restart, volumes, condition (+26 more)

### Community 62 - "Community 62"
Cohesion: 0.06
Nodes (34): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+26 more)

### Community 63 - "Community 63"
Cohesion: 0.08
Nodes (30): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+22 more)

### Community 64 - "Community 64"
Cohesion: 0.09
Nodes (25): headers, ADR-0030, ADR-0040, conflictForLena(), diveWithoutRecording(), editInSsi(), externalDiver(), importLena() (+17 more)

### Community 65 - "Community 65"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 66 - "Community 66"
Cohesion: 0.07
Nodes (27): admin, auth, { db, pool }, divers, E2E_USER, fakeSsi, garminDive(), here() (+19 more)

### Community 67 - "Community 67"
Cohesion: 0.12
Nodes (25): samplesQuery(), FindingLane(), useAssessment(), useWhen(), DepthProfile(), NO_POINTS, SurfaceBreakLane(), ADR-0036 (+17 more)

### Community 68 - "Community 68"
Cohesion: 0.15
Nodes (29): importsQuery(), ImportView, uploadFile(), Logbook(), ImportContext, ImportFilesButton(), ImportHistory(), ImportList() (+21 more)

### Community 69 - "Community 69"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 70 - "Community 70"
Cohesion: 0.09
Nodes (23): computerEventOf(), ADR-0036, computerEventFromFit(), COMPUTER_EVENT, computerEventFromSuunto(), DECO_MODEL, decoModelFromSuunto, DIVE_MODE (+15 more)

### Community 71 - "Community 71"
Cohesion: 0.15
Nodes (26): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+18 more)

### Community 72 - "Community 72"
Cohesion: 0.09
Nodes (27): devDependencies, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2, @testing-library/react@^16.3.3 (+19 more)

### Community 73 - "Community 73"
Cohesion: 0.10
Nodes (26): applyHints(), LiveArrivals, Page, useLiveArrivals(), bump(), changedListeners, ChannelMessage, healthListeners (+18 more)

### Community 74 - "Community 74"
Cohesion: 0.08
Nodes (18): SiteSourceAdapter, call(), createSite(), Method, openStream(), Stream, ADR-0066, EGYPT (+10 more)

### Community 75 - "Community 75"
Cohesion: 0.08
Nodes (23): Assessment, FindingView, Row, ADR-0036, upload(), Check, Deleted, DiveView (+15 more)

### Community 76 - "Community 76"
Cohesion: 0.09
Nodes (22): Candidate, Check, Outcome, Preview, ADR-0030, ADR-0038, FIT_EPOCH_MS, makeSuuntoAppFit() (+14 more)

### Community 77 - "Community 77"
Cohesion: 0.11
Nodes (23): EVIDENCE, Evidence, FindingValues, RULE_IDS, SEVERITIES, Severity, SourceId, SOURCES (+15 more)

### Community 78 - "Community 78"
Cohesion: 0.16
Nodes (24): DiveOrigin, REFERENCE_PREFIX, SsiLogbook, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef() (+16 more)

### Community 79 - "Community 79"
Cohesion: 0.11
Nodes (22): ADR-0036, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048, assessedDive() (+14 more)

### Community 80 - "Community 80"
Cohesion: 0.13
Nodes (22): byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf(), main() (+14 more)

### Community 81 - "Community 81"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 82 - "Community 82"
Cohesion: 0.08
Nodes (25): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+17 more)

### Community 83 - "Community 83"
Cohesion: 0.10
Nodes (18): ndl(), ProfileAssessment, Finding, LIMITS, RULES, dayNumber(), deepDays(), NoFly (+10 more)

### Community 84 - "Community 84"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 85 - "Community 85"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 86 - "Community 86"
Cohesion: 0.13
Nodes (22): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+14 more)

### Community 87 - "Community 87"
Cohesion: 0.12
Nodes (19): CERTIFICATION_IMAGE_TASK, EXPIRE_WAITING_IMPORTS_TASK, PROCESS_IMPORT_TASK, SCHEDULED_FETCH_TASK, PUSH_RUN_STEP_TASK, WAITING_PUSHES_TASK, waitingPushesKey(), AUTOMATION_TICK_TASK (+11 more)

### Community 88 - "Community 88"
Cohesion: 0.11
Nodes (12): parseHint(), createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+4 more)

### Community 89 - "Community 89"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 90 - "Community 90"
Cohesion: 0.12
Nodes (20): CENTRE_SOURCES, CentreService, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+12 more)

### Community 91 - "Community 91"
Cohesion: 0.14
Nodes (18): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+10 more)

### Community 92 - "Community 92"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 93 - "Community 93"
Cohesion: 0.12
Nodes (15): TAGS, killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023 (+7 more)

### Community 94 - "Community 94"
Cohesion: 0.12
Nodes (21): Blocked, classify(), FailureOutcome, kindOf(), KINDS, LEFT_OUT_KEY, LEFT_OUT_KEYS, LeftOutReason (+13 more)

### Community 95 - "Community 95"
Cohesion: 0.17
Nodes (18): Answer, CatalogueCylinder, CylinderInput, cylindersOfDrafts(), CylinderView, draftOfCylinder(), emptyDraft(), fromCatalogue() (+10 more)

### Community 96 - "Community 96"
Cohesion: 0.11
Nodes (18): CERTIFICATION_IMAGE_ATTEMPTS, CertificationExpiry, CertificationInput, CertificationView, EXPIRING_SOON_DAYS, ImportReport, ImportResult, LIMIT (+10 more)

### Community 97 - "Community 97"
Cohesion: 0.14
Nodes (16): providerGoneDive, compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051, candidatesOf() (+8 more)

### Community 98 - "Community 98"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 99 - "Community 99"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 100 - "Community 100"
Cohesion: 0.13
Nodes (15): FIND_BY, OPERATIONS, SignInInput, CASES, ContractHarness, declared(), ELSEWHERE, later() (+7 more)

### Community 101 - "Community 101"
Cohesion: 0.19
Nodes (17): AssessmentView, AssessmentState, clock(), counts(), differing(), Finding, formatRate(), hidden() (+9 more)

### Community 102 - "Community 102"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 103 - "Community 103"
Cohesion: 0.11
Nodes (19): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, @internationalized/date@^3.12.4, lucide-react@^1.51.0, react@^19.3.0 (+11 more)

### Community 104 - "Community 104"
Cohesion: 0.16
Nodes (16): inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), placeLocalTime(), Position, ADR-0030, wallClockMs() (+8 more)

### Community 105 - "Community 105"
Cohesion: 0.11
Nodes (12): Hint, HINT_CHANNEL, HINT_KINDS, HintAudience, HintKind, kinds, ADR-0020, ADR-0066 (+4 more)

### Community 106 - "Community 106"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 107 - "Community 107"
Cohesion: 0.11
Nodes (16): account, accountRelations, apikey, rateLimit, session, sessionRelations, ADR-0011, ADR-0035 (+8 more)

### Community 108 - "Community 108"
Cohesion: 0.17
Nodes (15): failureOutcome(), numberPlanOf(), orderItems(), createPushRunService(), analyse(), finish(), needSignedIn(), numbersHere() (+7 more)

### Community 109 - "Community 109"
Cohesion: 0.11
Nodes (14): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+6 more)

### Community 110 - "Community 110"
Cohesion: 0.18
Nodes (13): failedTitlesSince(), git(), failedTestsFromReport(), lastStatuses(), localTime(), appendRun(), browserStats(), logPath() (+5 more)

### Community 111 - "Community 111"
Cohesion: 0.12
Nodes (15): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+7 more)

### Community 112 - "Community 112"
Cohesion: 0.12
Nodes (15): pushRunItem, PushRunLeftOut, ProblemCode, RemoteDive, ScopedDive, Claimed, ItemRow, MAX_IDS (+7 more)

### Community 113 - "Community 113"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 114 - "Community 114"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 115 - "Community 115"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 116 - "Community 116"
Cohesion: 0.18
Nodes (10): ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, conflictAtSsi(), editAtSsi(), editElsewhere() (+2 more)

### Community 117 - "Community 117"
Cohesion: 0.20
Nodes (11): clearQueue(), fetchState(), leaveConflict(), queueEntry(), ssiConnectionOf(), automatic(), leave(), openConnections() (+3 more)

### Community 118 - "Community 118"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 119 - "Community 119"
Cohesion: 0.28
Nodes (13): ToolsPlan, DepthPlot(), LINE_DASHES, plotFont(), PressureStrip(), RIGHT_AXIS, token(), ADR-0045 (+5 more)

### Community 120 - "Community 120"
Cohesion: 0.16
Nodes (8): i18next@^26.4.2, CustomTypeOptions, i18next, initI18n(), resources, ADR-0014, ADR-0014, i18next

### Community 121 - "Community 121"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 122 - "Community 122"
Cohesion: 0.15
Nodes (9): Sac, SAC_MISSING, SacMissing, ADR-0033, ADR-0045, Cylinder, dive, ADR-0033 (+1 more)

### Community 123 - "Community 123"
Cohesion: 0.26
Nodes (8): Profile, SKETCH_HEIGHT, SKETCH_WIDTH, sketchPaths(), sketchScale(), ADR-0041, ProfileSketch(), ADR-0041

### Community 124 - "Community 124"
Cohesion: 0.18
Nodes (5): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053

### Community 125 - "Community 125"
Cohesion: 0.20
Nodes (10): DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN, NO_RECORDING, notAtProvider(), ADR-0017 (+2 more)

### Community 126 - "Community 126"
Cohesion: 0.29
Nodes (9): Doc, isObjectSchema(), mediaSchemas(), placesOf(), placesOfOperation(), Schema, sharedInlineSchemas(), sizable() (+1 more)

### Community 127 - "Community 127"
Cohesion: 0.18
Nodes (9): Buddy, KAI, MIA, NAMELESS, SAM, Status, ADR-0029, ADR-0043 (+1 more)

### Community 128 - "Community 128"
Cohesion: 0.20
Nodes (6): noAnswer, thrown(), ADR-0014, ApiClient, createApiClient(), ADR-0004

### Community 129 - "Community 129"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 130 - "Community 130"
Cohesion: 0.27
Nodes (8): CertificationError, createCertificationService(), cleaned(), requireManagedDiver(), rowOrThrow(), expiryOf(), isCalendarDate(), isoDay()

### Community 131 - "Community 131"
Cohesion: 0.22
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 132 - "Community 132"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 133 - "Community 133"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 135 - "Community 135"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 136 - "Community 136"
Cohesion: 0.25
Nodes (7): AIR, expectWithinReference(), GF100_FRESH, GF100_SALT, GF85_FRESH, ADR-0032, ADR-0046

### Community 137 - "Community 137"
Cohesion: 0.36
Nodes (6): headers, open(), ownId(), ownRow(), sectionButton(), ADR-0050

### Community 138 - "Community 138"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 139 - "Community 139"
Cohesion: 0.33
Nodes (4): Sketch, SKETCH_POINTS, ADR-0041, ADR-0041

### Community 140 - "Community 140"
Cohesion: 0.29
Nodes (4): PlanOptions, PlanStatus, options(), ADR-0052

### Community 141 - "Community 141"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 142 - "Community 142"
Cohesion: 0.33
Nodes (6): ADR-0023, ADR-0053, ADR-0066, MODULE_IMPORTS, moduleRules, target()

### Community 143 - "Community 143"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 144 - "Community 144"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 145 - "Community 145"
Cohesion: 0.40
Nodes (4): IMPORTABLE, splitImportable(), ADR-0037, ADR-0037

### Community 146 - "Community 146"
Cohesion: 0.33
Nodes (3): ADR-0056, now, ADR-0056

### Community 147 - "Community 147"
Cohesion: 0.40
Nodes (4): offer, osm, ssi, wikidata

### Community 149 - "Community 149"
Cohesion: 0.67
Nodes (3): planSketch(), shape(), ADR-0033

### Community 150 - "Community 150"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 151 - "Community 151"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

## Knowledge Gaps
- **1029 isolated node(s):** `ADR-0048`, `ADR-0016`, `ADR-0026`, `ADR-0038`, `ADR-0056` (+1024 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 2563 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **4 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `react` connect `Community 9` to `Community 32`, `Community 1`, `Community 2`, `Community 3`, `Community 67`, `Community 68`, `Community 103`, `Community 72`, `Community 7`, `Community 73`, `Community 15`, `Community 119`, `Community 52`, `Community 23`, `Community 26`, `Community 123`?**
  _High betweenness centrality (0.038) - this node is a cross-community bridge._
- **What connects `ADR-0048`, `ADR-0016`, `ADR-0026` to the rest of the system?**
  _1029 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.025953773322194375 - nodes in this community are weakly interconnected._
- **Why does `drizzle-orm` connect `Community 5` to `Community 0`, `Community 4`, `Community 6`, `Community 8`, `Community 10`, `Community 11`, `Community 12`, `Community 13`, `Community 16`, `Community 17`, `Community 18`, `Community 19`, `Community 20`, `Community 22`, `Community 24`, `Community 27`, `Community 38`, `Community 39`, `Community 45`, `Community 55`, `Community 59`, `Community 66`, `Community 69`, `Community 75`, `Community 82`, `Community 86`, `Community 96`, `Community 97`, `Community 104`, `Community 105`, `Community 107`, `Community 112`, `Community 125`, `Community 127`?**
  _High betweenness centrality (0.033) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.05651243949116289 - nodes in this community are weakly interconnected._
- **Why does `createDiveImportService()` connect `Community 8` to `Community 0`, `Community 97`, `Community 4`, `Community 38`, `Community 6`, `Community 10`, `Community 16`, `Community 91`?**
  _High betweenness centrality (0.033) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.018784893267651887 - nodes in this community are weakly interconnected._