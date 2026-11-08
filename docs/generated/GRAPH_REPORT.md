# Graph Report - dive_hub  (2026-11-08)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 6354 nodes · 19935 edges · 149 communities (145 shown, 4 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 198 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `d766e14f`
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
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `Removal` --references--> `ProviderServiceError`  [EXTRACTED]
  apps/server/src/providers/push/push-service.ts → apps/server/src/providers/registry.ts

## Import Cycles
- None detected.

## Communities (149 total, 4 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.04
Nodes (204): DisplaySettings(), AdminSettings(), DeleteUserDialog(), Invitations(), Invite(), LinkToPassOn(), ProviderSiteData(), SiteDataPanel() (+196 more)

### Community 1 - "Community 1"
Cohesion: 0.03
Nodes (129): user, cylinder, diver, diveSiteExternalId, revision, createSsiClient(), ADR-0013, ADR-0011 (+121 more)

### Community 2 - "Community 2"
Cohesion: 0.03
Nodes (161): ComputerChoice, DiveImportMode, DiveKind, importOriginal, ProviderImportPlan, providerSiteData, PushLeftOut, ProviderCylinder (+153 more)

### Community 3 - "Community 3"
Cohesion: 0.04
Nodes (142): allButDive(), api, ApiError, candidatesQuery(), CandidateView, continuationCandidateQuery(), deletedDivesQuery(), DeletedDiveView (+134 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (125): ServiceParts, Db, ConnectionRow, providerGoneDive, WaitingPushOptions, audienceOfConnection(), audienceOfDiver(), open() (+117 more)

### Community 5 - "Community 5"
Cohesion: 0.04
Nodes (134): ImportedValues, diveSite, renumber(), createContinuationService(), takeIn(), takeCylinders(), applyToDive(), attachRecording() (+126 more)

### Community 6 - "Community 6"
Cohesion: 0.02
Nodes (103): Assessment, FindingView, Row, ADR-0036, upload(), halves(), Check, Deleted (+95 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (109): UTC_OFFSET_SOURCES, downsampleMinMax(), ADR-0025, WaterMismatch, waterMismatch(), AppendDeps, AppendOutcome, AppendService (+101 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (82): connection, push, createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE (+74 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (89): Queryable, continuationAppend, device, diverBodyWeight, lead, LeadPlacement, OverridableField, sampleSeries (+81 more)

### Community 10 - "Community 10"
Cohesion: 0.04
Nodes (96): assessmentQuery(), AssessmentView, devicesQuery(), DeviceView, diveNeighboursQuery(), DiveValues, NeighboursView, OverridableField (+88 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (90): Tx, recordingEvent, renumberStatement(), ADR-0048, AppendRow, channelsOf(), cylindersBack(), DiveRow (+82 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (87): AnyLogbookCheck, bottomTimeRule(), BuddyView, ConflictEntryView, ConflictsOfConnection, connectionsQuery(), ContinuationCandidateView, defined() (+79 more)

### Community 13 - "Community 13"
Cohesion: 0.05
Nodes (87): ChangePassword(), SecuritySettings(), Sessions(), useDescribeAgent(), authClient, invitationLinkQuery(), Me, passwordResetQuery() (+79 more)

### Community 14 - "Community 14"
Cohesion: 0.06
Nodes (83): buildApp(), assessmentRoutes(), authHandler(), authHeaders(), fastify, forwardCookies(), isAdmin(), publicPaths (+75 more)

### Community 15 - "Community 15"
Cohesion: 0.04
Nodes (64): createServer(), createAuth(), loadAuthSecret(), workerDeps(), Config, loadConfig(), ADR-0011, ADR-0021 (+56 more)

### Community 16 - "Community 16"
Cohesion: 0.04
Nodes (84): logbookCheckAnswer, logbookCheckDiveAnswer, OVERRIDABLE_FIELDS, recording, keepApart(), keepDive(), keepRestored(), ADR-0038 (+76 more)

### Community 17 - "Community 17"
Cohesion: 0.02
Nodes (85): StoredConflictField, actorType, aiAccessOutcome, candidateResolution, centreSource, certification, CERTIFICATION_IMAGE_STATES, CERTIFICATION_VERIFICATIONS (+77 more)

### Community 18 - "Community 18"
Cohesion: 0.05
Nodes (60): CodeFigure(), DiveCodes(), showDiveCodes(), ADR-0043, avatarTone(), initials(), NavSection, navSection() (+52 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (69): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+61 more)

### Community 20 - "Community 20"
Cohesion: 0.05
Nodes (69): CYLINDER_MATERIALS, PARTICIPANT_ROLES, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053, DIVE_ONLY_PATTERN, ErrorAnswer, Problem (+61 more)

### Community 21 - "Community 21"
Cohesion: 0.06
Nodes (63): DiveField, Leave, Series, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi() (+55 more)

### Community 22 - "Community 22"
Cohesion: 0.04
Nodes (60): ExternalIdView, Position, siteRevisionsQuery(), SiteRevisionView, SiteView, holdLeave(), mayLeave(), useLeaveGuard() (+52 more)

### Community 23 - "Community 23"
Cohesion: 0.06
Nodes (57): divesQuery(), DiveSummary, LogbookPage, PAGE_SIZE, Buddies(), COUNT_OF, DiveList(), DiveRow() (+49 more)

### Community 24 - "Community 24"
Cohesion: 0.06
Nodes (56): AscentBand, ascentRate(), ceiling(), descentRate(), judgedParts(), lastMetres(), ndl(), ppo2() (+48 more)

### Community 25 - "Community 25"
Cohesion: 0.05
Nodes (59): CylinderMaterial, SuitType, SuitUndergarment, WeightingFelt, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, Suit (+51 more)

### Community 26 - "Community 26"
Cohesion: 0.04
Nodes (46): Direction, DIVE_FIELDS, DiveExport, DiveListing, DiveOrigin, FIND_BY, FindBy, Operation (+38 more)

### Community 27 - "Community 27"
Cohesion: 0.05
Nodes (60): ConflictChoices, ConflictFieldView, diveListPages, ProblemCode, PushView, RequirementView, UnmetView, focusFirstIn() (+52 more)

### Community 28 - "Community 28"
Cohesion: 0.04
Nodes (48): graphile-worker@^0.18.0, importJob, providerConflict, waitingPush, kept(), record(), DELETE_QUIET_MS, QUIET_MS (+40 more)

### Community 29 - "Community 29"
Cohesion: 0.05
Nodes (43): auth, createServices(), ADR-0027, ADR-0030, ADR-0061, ADR-0063, ADR-0066, diverManagement (+35 more)

### Community 30 - "Community 30"
Cohesion: 0.06
Nodes (51): ImportArea, SiteImportCounts, ADR-0021, ADR-0025, ADR-0053, distanceM(), Field, has() (+43 more)

### Community 31 - "Community 31"
Cohesion: 0.07
Nodes (49): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ambientPressureBar(), bestMixPercent() (+41 more)

### Community 32 - "Community 32"
Cohesion: 0.07
Nodes (56): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+48 more)

### Community 33 - "Community 33"
Cohesion: 0.05
Nodes (51): replyCertificationError, STATUS, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, CertificationRouteDeps (+43 more)

### Community 34 - "Community 34"
Cohesion: 0.05
Nodes (52): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, ADR-0040, AiAccess, AssessedFinding (+44 more)

### Community 35 - "Community 35"
Cohesion: 0.04
Nodes (51): AssessmentService, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView(), ADR-0036 (+43 more)

### Community 36 - "Community 36"
Cohesion: 0.07
Nodes (50): Delivered, ProviderError, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf() (+42 more)

### Community 37 - "Community 37"
Cohesion: 0.06
Nodes (52): LeadEstimate, LeadHint(), orUndefined(), ADR-0025, ADR-0031, BOTTOM_TIME_RULES, cylinderOf(), cylinderText() (+44 more)

### Community 38 - "Community 38"
Cohesion: 0.08
Nodes (49): accountListEntry, diverExternalId, ParticipantRole, tidyName(), DiverDetails, Caller, foldName(), nameDiffers() (+41 more)

### Community 39 - "Community 39"
Cohesion: 0.08
Nodes (48): CertificationService, DIVE_IMPORT_MODES, AssessmentRefresh, IdParams, AccountRouteDeps, BuddyView, linkErrors, ADR-0050 (+40 more)

### Community 40 - "Community 40"
Cohesion: 0.05
Nodes (48): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+40 more)

### Community 41 - "Community 41"
Cohesion: 0.08
Nodes (38): sacOfDive(), BottomTimeQuery, DEFAULT_GF_HIGH, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033 (+30 more)

### Community 42 - "Community 42"
Cohesion: 0.10
Nodes (43): cylinderCatalogueQuery(), CylinderFields(), MATERIALS, ADR-0045, useCatalogueName(), useCylinderProblem(), DiveEditForm(), Draft (+35 more)

### Community 43 - "Community 43"
Cohesion: 0.07
Nodes (37): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), position(), positive() (+29 more)

### Community 44 - "Community 44"
Cohesion: 0.07
Nodes (32): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+24 more)

### Community 45 - "Community 45"
Cohesion: 0.06
Nodes (43): LEAD_PLACEMENTS, SUIT_TYPES, SUIT_UNDERGARMENTS, WEIGHTING_FELT, ProfileSummary, round1(), Series, summariseProfile() (+35 more)

### Community 46 - "Community 46"
Cohesion: 0.06
Nodes (37): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+29 more)

### Community 47 - "Community 47"
Cohesion: 0.07
Nodes (41): AiAccessScope, diversOf(), SiteRow, Cursor, cursorAt(), DateOnly(), Detail, diversAsked() (+33 more)

### Community 48 - "Community 48"
Cohesion: 0.06
Nodes (36): SSI_CAPABILITIES, SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject() (+28 more)

### Community 49 - "Community 49"
Cohesion: 0.06
Nodes (37): adminRole, AI_ACCESS_NAME_MAX, AI_ACCESS_PREFIX, AI_ACCESS_RATE, AuthOptions, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, SessionUser (+29 more)

### Community 50 - "Community 50"
Cohesion: 0.09
Nodes (42): providersQuery(), pushRunActive(), pushRunBody(), PushRunChoices, PushRunDetail, PushRunPreview, pushRunPreviewQuery(), pushRunQuery() (+34 more)

### Community 51 - "Community 51"
Cohesion: 0.10
Nodes (36): samplesQuery(), FindingLane(), useAssessment(), DepthProfile(), NO_POINTS, SurfaceBreakLane(), ADR-0036, ADR-0063 (+28 more)

### Community 52 - "Community 52"
Cohesion: 0.08
Nodes (34): ImportErrorCode, ImportFound, ImportOutcome, errorMessage(), looksLikeZip(), FileFormat, parseWithFitFileParser(), formatOf() (+26 more)

### Community 53 - "Community 53"
Cohesion: 0.07
Nodes (39): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+31 more)

### Community 54 - "Community 54"
Cohesion: 0.09
Nodes (37): Answer, massIn(), Dive, draftOfLead(), draftOfWeighting(), emptyLead(), fromLastDive(), hasThickness() (+29 more)

### Community 55 - "Community 55"
Cohesion: 0.05
Nodes (41): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+33 more)

### Community 56 - "Community 56"
Cohesion: 0.08
Nodes (35): @modelcontextprotocol/server@^2.3.1, aiAccessLog, aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, AiAccessService, KeyRow (+27 more)

### Community 57 - "Community 57"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 58 - "Community 58"
Cohesion: 0.09
Nodes (28): ADR-0051, Status, ADR-0056, ADR-0047, clearPushRuns(), clearQueue(), conflictAtSsi(), connectSsi() (+20 more)

### Community 59 - "Community 59"
Cohesion: 0.06
Nodes (34): areaOf(), areas, capture(), findings, headers, ADR-0020, ADR-0021, ADR-0022 (+26 more)

### Community 60 - "Community 60"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 61 - "Community 61"
Cohesion: 0.07
Nodes (31): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, Services, DiverCertifications, DiverService (+23 more)

### Community 62 - "Community 62"
Cohesion: 0.12
Nodes (35): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+27 more)

### Community 63 - "Community 63"
Cohesion: 0.11
Nodes (36): ToolsPlan, ToolsPlanDiver, PlanDiver, PlanLevel, PlanStretch, replaceToolsAddress(), ToolsRule, depthFromDisplay() (+28 more)

### Community 64 - "Community 64"
Cohesion: 0.09
Nodes (30): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+22 more)

### Community 65 - "Community 65"
Cohesion: 0.09
Nodes (33): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+25 more)

### Community 66 - "Community 66"
Cohesion: 0.12
Nodes (32): importsQuery(), ImportView, uploadFile(), Logbook(), ImportContext, ImportFilesButton(), ImportHistory(), ImportList() (+24 more)

### Community 67 - "Community 67"
Cohesion: 0.06
Nodes (34): build, depends_on, environment, image, ports, restart, volumes, condition (+26 more)

### Community 68 - "Community 68"
Cohesion: 0.06
Nodes (34): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+26 more)

### Community 69 - "Community 69"
Cohesion: 0.12
Nodes (27): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+19 more)

### Community 70 - "Community 70"
Cohesion: 0.07
Nodes (28): FindingValues, RuleId, NoFly, ASSESSMENT_NOTE, FINDING_TEXT, n(), NO_FLY_TEXT, Text (+20 more)

### Community 71 - "Community 71"
Cohesion: 0.07
Nodes (22): i18next@^26.4.2, CustomTypeOptions, i18next, initI18n(), resources, ADR-0014, StrayOverride, ADR-0014 (+14 more)

### Community 72 - "Community 72"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 73 - "Community 73"
Cohesion: 0.09
Nodes (27): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+19 more)

### Community 74 - "Community 74"
Cohesion: 0.09
Nodes (17): diveLease, pushRun, ExportingProvider, createFakeHandover(), FakeHandover, ADR-0027, ADR-0027, ADR-0027 (+9 more)

### Community 75 - "Community 75"
Cohesion: 0.11
Nodes (22): SsiRecord, contextOf(), FakeSsi, BuddySaved, access(), client, out, pause (+14 more)

### Community 76 - "Community 76"
Cohesion: 0.09
Nodes (27): devDependencies, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2, @testing-library/react@^16.3.3 (+19 more)

### Community 77 - "Community 77"
Cohesion: 0.10
Nodes (22): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+14 more)

### Community 78 - "Community 78"
Cohesion: 0.08
Nodes (21): account, accountRelations, apikey, rateLimit, session, sessionRelations, ADR-0011, ADR-0035 (+13 more)

### Community 79 - "Community 79"
Cohesion: 0.13
Nodes (22): diveWith(), ADR-0028, ADR-0043, forget(), headers, ADR-0043, headers, Site (+14 more)

### Community 80 - "Community 80"
Cohesion: 0.13
Nodes (22): byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf(), main() (+14 more)

### Community 81 - "Community 81"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 82 - "Community 82"
Cohesion: 0.10
Nodes (16): SsiClient, ClearChecks, clearDive(), findIn(), finishClearChecks(), GAS, isSet(), KEYS (+8 more)

### Community 83 - "Community 83"
Cohesion: 0.18
Nodes (22): SsiLogbook, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef(), MATERIAL_OF_TANK_TYPE, nonZero() (+14 more)

### Community 84 - "Community 84"
Cohesion: 0.14
Nodes (20): durationFormat(), DurationFormatter, formatDuration(), formatGasRate(), formatMass(), formatPressure(), formatPressureRate(), formatTemperature() (+12 more)

### Community 85 - "Community 85"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 86 - "Community 86"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 87 - "Community 87"
Cohesion: 0.11
Nodes (22): typescript@7.0.2, typescript@7.0.2, ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2 (+14 more)

### Community 88 - "Community 88"
Cohesion: 0.12
Nodes (19): CERTIFICATION_IMAGE_TASK, EXPIRE_WAITING_IMPORTS_TASK, PROCESS_IMPORT_TASK, SCHEDULED_FETCH_TASK, PUSH_RUN_STEP_TASK, WAITING_PUSHES_TASK, waitingPushesKey(), AUTOMATION_TICK_TASK (+11 more)

### Community 89 - "Community 89"
Cohesion: 0.11
Nodes (12): parseHint(), createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+4 more)

### Community 90 - "Community 90"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 91 - "Community 91"
Cohesion: 0.12
Nodes (20): CENTRE_SOURCES, CentreService, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+12 more)

### Community 92 - "Community 92"
Cohesion: 0.14
Nodes (18): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+10 more)

### Community 93 - "Community 93"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 94 - "Community 94"
Cohesion: 0.09
Nodes (17): headers, ADR-0035, aiAccessReady(), askMcp(), createAiAccess(), putAside(), headers, ADR-0018 (+9 more)

### Community 95 - "Community 95"
Cohesion: 0.15
Nodes (18): qr@0.7.2, cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043 (+10 more)

### Community 96 - "Community 96"
Cohesion: 0.13
Nodes (21): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+13 more)

### Community 97 - "Community 97"
Cohesion: 0.12
Nodes (15): TAGS, killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023 (+7 more)

### Community 98 - "Community 98"
Cohesion: 0.12
Nodes (21): Blocked, classify(), FailureOutcome, kindOf(), KINDS, LEFT_OUT_KEY, LEFT_OUT_KEYS, LeftOutReason (+13 more)

### Community 99 - "Community 99"
Cohesion: 0.11
Nodes (18): CERTIFICATION_IMAGE_ATTEMPTS, CertificationExpiry, CertificationInput, CertificationView, EXPIRING_SOON_DAYS, ImportReport, ImportResult, LIMIT (+10 more)

### Community 100 - "Community 100"
Cohesion: 0.15
Nodes (18): RecordingSummary, UtcOffsetSource, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput (+10 more)

### Community 101 - "Community 101"
Cohesion: 0.12
Nodes (16): Cylinder, DiveView, RevisionView, ADR-0045, upload(), FIT_EPOCH_MS, makeSuuntoAppFit(), makeSuuntoJson() (+8 more)

### Community 102 - "Community 102"
Cohesion: 0.10
Nodes (17): dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3, exports, name, private (+9 more)

### Community 103 - "Community 103"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 104 - "Community 104"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 105 - "Community 105"
Cohesion: 0.11
Nodes (19): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, @internationalized/date@^3.12.4, lucide-react@^1.51.0, react@^19.3.0 (+11 more)

### Community 106 - "Community 106"
Cohesion: 0.11
Nodes (12): Hint, HINT_CHANNEL, HINT_KINDS, HintAudience, HintKind, kinds, ADR-0020, ADR-0066 (+4 more)

### Community 107 - "Community 107"
Cohesion: 0.14
Nodes (17): defineTool(), Id(), Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), HINT (+9 more)

### Community 108 - "Community 108"
Cohesion: 0.23
Nodes (15): useWhen(), bandSeconds(), clock(), counts(), differing(), Finding, formatRate(), hidden() (+7 more)

### Community 109 - "Community 109"
Cohesion: 0.18
Nodes (13): failedTitlesSince(), git(), failedTestsFromReport(), lastStatuses(), localTime(), appendRun(), browserStats(), logPath() (+5 more)

### Community 110 - "Community 110"
Cohesion: 0.18
Nodes (14): failureOutcome(), numberPlanOf(), orderItems(), createPushRunService(), analyse(), needSignedIn(), numbersHere(), ownConnection() (+6 more)

### Community 111 - "Community 111"
Cohesion: 0.15
Nodes (13): DEFAULT_OVERPASS_URL, Element, overpassQuery(), parseMaxDepth(), parseOverpass(), text(), ADR-0021, PoliteHttp (+5 more)

### Community 112 - "Community 112"
Cohesion: 0.12
Nodes (15): pushRunItem, PushRunLeftOut, ProblemCode, RemoteDive, ScopedDive, Claimed, ItemRow, MAX_IDS (+7 more)

### Community 113 - "Community 113"
Cohesion: 0.16
Nodes (11): createPoliteHttp(), Fetch, Init, PoliteHttpOptions, PROJECT_URL, RETRY_STATUSES, ADR-0021, userAgent() (+3 more)

### Community 114 - "Community 114"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 115 - "Community 115"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 116 - "Community 116"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 117 - "Community 117"
Cohesion: 0.20
Nodes (11): DIVE_KINDS, ImportView, ImportViewSchema, toImportView(), ADR-0030, ImportRouteDeps, importRoutes(), ADR-0044 (+3 more)

### Community 118 - "Community 118"
Cohesion: 0.15
Nodes (13): replyDiverError, STATUS, LeadService, Amount, EstimateQuery, EstimateView, LeadRouteDeps, PlannedCylinders (+5 more)

### Community 119 - "Community 119"
Cohesion: 0.13
Nodes (10): EGYPT, ExternalId, HOUSE_REEF, Method, Position, Revision, Site, SiteImport (+2 more)

### Community 120 - "Community 120"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 121 - "Community 121"
Cohesion: 0.15
Nodes (9): Sac, SAC_MISSING, SacMissing, ADR-0033, ADR-0045, Cylinder, dive, ADR-0033 (+1 more)

### Community 122 - "Community 122"
Cohesion: 0.18
Nodes (5): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053

### Community 123 - "Community 123"
Cohesion: 0.20
Nodes (10): DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN, NO_RECORDING, notAtProvider(), ADR-0017 (+2 more)

### Community 124 - "Community 124"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 125 - "Community 125"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 126 - "Community 126"
Cohesion: 0.27
Nodes (8): CertificationError, createCertificationService(), cleaned(), requireManagedDiver(), rowOrThrow(), expiryOf(), isCalendarDate(), isoDay()

### Community 127 - "Community 127"
Cohesion: 0.22
Nodes (8): wallClockMs(), AQABA, ATTERSEE, EILAT, HAUSREEF, local(), MID_ATLANTIC, ADR-0030

### Community 128 - "Community 128"
Cohesion: 0.20
Nodes (9): Numbered, SSI, Status, ADR-0024, ADR-0027, ADR-0056, upload(), Waiting (+1 more)

### Community 129 - "Community 129"
Cohesion: 0.22
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 130 - "Community 130"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 131 - "Community 131"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 133 - "Community 133"
Cohesion: 0.39
Nodes (8): coordinate(), parseSsiSites(), sitesJson(), truthy(), ADR-0024, unavailable(), alpha2Of(), inBox()

### Community 134 - "Community 134"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 135 - "Community 135"
Cohesion: 0.36
Nodes (7): createSsiSiteSource(), DEFAULT_SSI_SITES_URL, createSiteSources(), ADR-0021, ADR-0027, createOverpassSource(), createWikidataSource()

### Community 136 - "Community 136"
Cohesion: 0.36
Nodes (6): SiteSourceError, Binding, parseWikidata(), point(), ADR-0021, value()

### Community 137 - "Community 137"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 138 - "Community 138"
Cohesion: 0.29
Nodes (4): PlanOptions, PlanStatus, options(), ADR-0052

### Community 139 - "Community 139"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 140 - "Community 140"
Cohesion: 0.33
Nodes (6): ADR-0023, ADR-0053, ADR-0066, MODULE_IMPORTS, moduleRules, target()

### Community 141 - "Community 141"
Cohesion: 0.40
Nodes (5): stubDive(), ADR-0015, ADR-0023, ADR-0025, guardedRoute()

### Community 142 - "Community 142"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 143 - "Community 143"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 145 - "Community 145"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 146 - "Community 146"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

## Knowledge Gaps
- **1029 isolated node(s):** `ADR-0012`, `ADR-0013`, `ADR-0030`, `ADR-0035`, `ADR-0031` (+1024 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 2564 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **4 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 1` to `Community 128`, `Community 2`, `Community 4`, `Community 5`, `Community 6`, `Community 7`, `Community 8`, `Community 9`, `Community 11`, `Community 14`, `Community 15`, `Community 16`, `Community 17`, `Community 19`, `Community 28`, `Community 29`, `Community 38`, `Community 40`, `Community 41`, `Community 47`, `Community 49`, `Community 52`, `Community 56`, `Community 61`, `Community 64`, `Community 65`, `Community 74`, `Community 78`, `Community 99`, `Community 106`, `Community 112`, `Community 123`?**
  _High betweenness centrality (0.032) - this node is a cross-community bridge._
- **What connects `ADR-0012`, `ADR-0013`, `ADR-0030` to the rest of the system?**
  _1029 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.042556538839724684 - nodes in this community are weakly interconnected._
- **Why does `react` connect `Community 18` to `Community 0`, `Community 3`, `Community 10`, `Community 12`, `Community 13`, `Community 22`, `Community 23`, `Community 27`, `Community 37`, `Community 42`, `Community 50`, `Community 51`, `Community 54`, `Community 63`, `Community 66`, `Community 76`, `Community 95`, `Community 96`, `Community 105`?**
  _High betweenness centrality (0.018) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.03427280145628021 - nodes in this community are weakly interconnected._
- **Why does `createBuddyService()` connect `Community 38` to `Community 4`, `Community 5`?**
  _High betweenness centrality (0.016) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.027792207792207792 - nodes in this community are weakly interconnected._