# Graph Report - dive_hub  (2026-11-08)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 6348 nodes · 19923 edges · 157 communities (154 shown, 3 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 198 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `977579e6`
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
- `Removal` --references--> `ProviderServiceError`  [EXTRACTED]
  apps/server/src/providers/push/push-service.ts → apps/server/src/providers/registry.ts

## Import Cycles
- None detected.

## Communities (157 total, 3 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.05
Nodes (169): ChangePassword(), DisplaySettings(), SecuritySettings(), Sessions(), useDescribeAgent(), AdminSettings(), DeleteUserDialog(), Invitations() (+161 more)

### Community 1 - "Community 1"
Cohesion: 0.03
Nodes (120): ExternalIdView, isUnauthorized(), Me, running(), SiteImportArea, siteImportsQuery(), SiteImportView, siteQuery() (+112 more)

### Community 2 - "Community 2"
Cohesion: 0.04
Nodes (114): allButDive(), ApiError, continuationCandidateQuery(), deletedDivesQuery(), DeletedDiveView, diveNeighboursQuery(), diveProvidersQuery(), diveQuery() (+106 more)

### Community 3 - "Community 3"
Cohesion: 0.03
Nodes (106): Auth, CentreService, CentreRouteDeps, UTC_OFFSET_SOURCES, DiverService, DiverRouteDeps, downsampleMinMax(), AppendDeps (+98 more)

### Community 4 - "Community 4"
Cohesion: 0.04
Nodes (87): account, session, user, accountListEntry, cylinder, diver, revision, sampleSeries (+79 more)

### Community 5 - "Community 5"
Cohesion: 0.04
Nodes (95): OVERRIDABLE_FIELDS, OverridableField, renumber(), renumberStatement(), ADR-0048, AppendRow, channelsOf(), createContinuationService() (+87 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (79): createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities(), providerError() (+71 more)

### Community 7 - "Community 7"
Cohesion: 0.04
Nodes (80): adminRole, AI_ACCESS_NAME_MAX, AI_ACCESS_PREFIX, AI_ACCESS_RATE, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, SessionUser, ADR-0011 (+72 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (71): connection, diveLease, importJob, providerConflict, push, pushRun, waitingPush, kept() (+63 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (90): AnyLogbookCheck, buddiesQuery(), BuddyView, ConflictChoices, ConflictEntryView, ConflictFieldView, ConflictsOfConnection, ContinuationCandidateView (+82 more)

### Community 10 - "Community 10"
Cohesion: 0.06
Nodes (79): diverExternalId, diveSite, siteImport, dataIdsOf(), hubSideOf(), settleSiteVisibility(), outgoingSite(), findOrMake() (+71 more)

### Community 11 - "Community 11"
Cohesion: 0.02
Nodes (85): StoredConflictField, actorType, aiAccessOutcome, candidateResolution, centreSource, certification, CERTIFICATION_IMAGE_STATES, CERTIFICATION_VERIFICATIONS (+77 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (65): ParticipantRole, PushLeftOut, ProviderCylinder, Capabilities, Direction, DiveExport, DiveListing, DiveOrigin (+57 more)

### Community 13 - "Community 13"
Cohesion: 0.07
Nodes (70): api, buddiesForQuery(), centreQuery(), centresQuery(), CentreView, ConnectionView, DiverDetailsView, keys (+62 more)

### Community 14 - "Community 14"
Cohesion: 0.05
Nodes (70): continuationAppend, diveAssessment, lead, LeadPlacement, managedDiverIds(), managesDiveDiver(), ADR-0016, providerCylinderOf() (+62 more)

### Community 15 - "Community 15"
Cohesion: 0.04
Nodes (58): recordingEvent, PROBLEMS, Assessment, FindingView, Row, ADR-0036, upload(), Check (+50 more)

### Community 16 - "Community 16"
Cohesion: 0.05
Nodes (59): diveSiteExternalId, createSsiClient(), Dive, Status, ADR-0038, ADR-0043, Card, DEEP (+51 more)

### Community 17 - "Community 17"
Cohesion: 0.05
Nodes (59): errorMessage(), looksLikeZip(), placeFile(), placeFiles(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites() (+51 more)

### Community 18 - "Community 18"
Cohesion: 0.07
Nodes (70): candidatesQuery(), CandidateView, DiveCheckView, diversQuery(), goneDivesQuery(), isDiveCheck(), isPairCheck(), logbookChecksQuery() (+62 more)

### Community 19 - "Community 19"
Cohesion: 0.05
Nodes (52): graphile-worker@^0.18.0, createServer(), createAuth(), loadAuthSecret(), workerDeps(), Config, loadConfig(), ADR-0011 (+44 more)

### Community 20 - "Community 20"
Cohesion: 0.05
Nodes (69): assessmentQuery(), DiveValues, OverridableField, Position, revisionsQuery(), RevisionView, SiteRevisionView, UtcOffsetSource (+61 more)

### Community 21 - "Community 21"
Cohesion: 0.05
Nodes (53): AuthOptions, auth, createServices(), ServiceParts, Services, ADR-0027, ADR-0030, ADR-0061 (+45 more)

### Community 22 - "Community 22"
Cohesion: 0.04
Nodes (60): CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043, CreateBody (+52 more)

### Community 23 - "Community 23"
Cohesion: 0.06
Nodes (63): CertificationService, Copies, DeleteProblemSchema, ErrorAnswer, Problem, ProblemSchema, providerProblem(), ADR-0014 (+55 more)

### Community 24 - "Community 24"
Cohesion: 0.04
Nodes (64): pushRunItem, PushRunLeftOut, DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN, NO_RECORDING (+56 more)

### Community 25 - "Community 25"
Cohesion: 0.06
Nodes (64): Queryable, logbookCheckAnswer, logbookCheckDiveAnswer, keepApart(), keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration() (+56 more)

### Community 26 - "Community 26"
Cohesion: 0.06
Nodes (59): ImportArea, ImportedValues, SiteImportCounts, ADR-0021, ADR-0025, ADR-0053, ProviderAdapter, RemoteSite (+51 more)

### Community 27 - "Community 27"
Cohesion: 0.05
Nodes (59): CylinderMaterial, SuitType, SuitUndergarment, WeightingFelt, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, Suit (+51 more)

### Community 28 - "Community 28"
Cohesion: 0.06
Nodes (55): tidyName(), DiverDetails, Caller, locked(), ADR-0057, foldName(), nameDiffers(), Buddy (+47 more)

### Community 29 - "Community 29"
Cohesion: 0.07
Nodes (54): SsiRecord, contextOf(), asNumber(), buildCreateRecord(), buildDeleteRecord(), buildUpdateRecord(), compareReadBack(), DiveForSsi (+46 more)

### Community 30 - "Community 30"
Cohesion: 0.07
Nodes (58): waitingOf(), close(), currentRemote(), PushRow, ADR-0024, Choice, withRemembered(), Delivered (+50 more)

### Community 31 - "Community 31"
Cohesion: 0.06
Nodes (53): DIVE_FIELDS, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open() (+45 more)

### Community 32 - "Community 32"
Cohesion: 0.07
Nodes (49): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ambientPressureBar(), bestMixPercent() (+41 more)

### Community 33 - "Community 33"
Cohesion: 0.07
Nodes (56): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+48 more)

### Community 34 - "Community 34"
Cohesion: 0.05
Nodes (52): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, ADR-0040, AiAccess, AssessedFinding (+44 more)

### Community 35 - "Community 35"
Cohesion: 0.06
Nodes (54): ProblemCode, providerSitesQuery(), providerStatusQuery(), typedSiteId(), useFieldNames(), useProviderText(), waitsForUser(), ListChange (+46 more)

### Community 36 - "Community 36"
Cohesion: 0.07
Nodes (52): ConnectionRow, WaitingPushOptions, audienceOfConnection(), audienceOfDive(), audienceOfDiver(), ProviderLayer, ADR-0027, ADR-0029 (+44 more)

### Community 37 - "Community 37"
Cohesion: 0.07
Nodes (49): divesQuery(), LogbookPage, PAGE_SIZE, Buddies(), COUNT_OF, DiveList(), DiveRow(), MonthHead() (+41 more)

### Community 38 - "Community 38"
Cohesion: 0.07
Nodes (51): ComputerChoice, ProviderImportPlan, providerSiteData, kindOfMode(), deviceDiver(), closeFields(), entryOf(), openFields() (+43 more)

### Community 39 - "Community 39"
Cohesion: 0.07
Nodes (46): externalDiver(), mergeExternalDiver(), participations(), open(), AutomationSettings, createConnectionService(), checkAutomation(), checkFollowNumbers() (+38 more)

### Community 40 - "Community 40"
Cohesion: 0.07
Nodes (49): LeadEstimate, LeadHint(), orUndefined(), ADR-0025, ADR-0031, BOTTOM_TIME_RULES, cylinderOf(), cylinderText() (+41 more)

### Community 41 - "Community 41"
Cohesion: 0.12
Nodes (48): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, assessmentRoutes(), authHandler() (+40 more)

### Community 42 - "Community 42"
Cohesion: 0.05
Nodes (48): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+40 more)

### Community 43 - "Community 43"
Cohesion: 0.07
Nodes (38): ImportErrorCode, ImportFound, importOriginal, ImportOutcome, ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS (+30 more)

### Community 44 - "Community 44"
Cohesion: 0.08
Nodes (38): sacOfDive(), BottomTimeQuery, DEFAULT_GF_HIGH, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033 (+30 more)

### Community 45 - "Community 45"
Cohesion: 0.08
Nodes (40): asBool(), DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining(), ssiDiveType() (+32 more)

### Community 46 - "Community 46"
Cohesion: 0.05
Nodes (39): DiveView, RevisionView, ADR-0015, ADR-0025, ADR-0048, upload(), DiveView, LeadEntry (+31 more)

### Community 47 - "Community 47"
Cohesion: 0.07
Nodes (32): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+24 more)

### Community 48 - "Community 48"
Cohesion: 0.09
Nodes (32): showDiveCodes(), ADR-0043, ButtonProps, ADR-0018, RadioGroupProps, Switch(), ADR-0018, IconName (+24 more)

### Community 49 - "Community 49"
Cohesion: 0.06
Nodes (43): LEAD_PLACEMENTS, SUIT_TYPES, SUIT_UNDERGARMENTS, WEIGHTING_FELT, ProfileSummary, round1(), Series, summariseProfile() (+35 more)

### Community 50 - "Community 50"
Cohesion: 0.07
Nodes (41): AiAccessScope, diversOf(), SiteRow, Cursor, cursorAt(), DateOnly(), Detail, diversAsked() (+33 more)

### Community 51 - "Community 51"
Cohesion: 0.06
Nodes (36): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+28 more)

### Community 52 - "Community 52"
Cohesion: 0.08
Nodes (36): date(), FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position(), positive() (+28 more)

### Community 53 - "Community 53"
Cohesion: 0.10
Nodes (39): cylinderCatalogueQuery(), CylinderFields(), MATERIALS, ADR-0045, useCatalogueName(), useCylinderProblem(), DiveEditForm(), Draft (+31 more)

### Community 54 - "Community 54"
Cohesion: 0.10
Nodes (36): samplesQuery(), FindingLane(), useAssessment(), DepthProfile(), NO_POINTS, SurfaceBreakLane(), ADR-0036, ADR-0063 (+28 more)

### Community 55 - "Community 55"
Cohesion: 0.07
Nodes (37): CYLINDER_MATERIALS, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053, CandidateRouteDeps, CandidateView, Decided (+29 more)

### Community 56 - "Community 56"
Cohesion: 0.07
Nodes (36): device, diverBodyWeight, participant, DiverError, ADR-0016, ADR-0053, ACCOUNT_PATTERN, accountKey() (+28 more)

### Community 57 - "Community 57"
Cohesion: 0.07
Nodes (39): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+31 more)

### Community 58 - "Community 58"
Cohesion: 0.10
Nodes (40): pushRunActive(), pushRunBody(), PushRunChoices, PushRunDetail, PushRunPreview, pushRunPreviewQuery(), pushRunQuery(), pushRunsQuery() (+32 more)

### Community 59 - "Community 59"
Cohesion: 0.09
Nodes (37): Answer, massIn(), Dive, draftOfLead(), draftOfWeighting(), emptyLead(), fromLastDive(), hasThickness() (+29 more)

### Community 60 - "Community 60"
Cohesion: 0.05
Nodes (41): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+33 more)

### Community 61 - "Community 61"
Cohesion: 0.09
Nodes (39): buildJoined(), partInput(), partsOfJoined(), rebuildJoined(), RecordingRow, ADR-0063, writeJoined(), reviseDive() (+31 more)

### Community 62 - "Community 62"
Cohesion: 0.06
Nodes (33): DiverCertifications, replyDiverError, STATUS, CertificationSummary, DiverDetailsView, DiverDetailsViewSchema, DiverSource, DiverView (+25 more)

### Community 63 - "Community 63"
Cohesion: 0.07
Nodes (33): sampleCount(), rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject(), MAX_CARD_IMAGE_BYTES (+25 more)

### Community 64 - "Community 64"
Cohesion: 0.08
Nodes (35): @modelcontextprotocol/server@^2.3.1, aiAccessLog, aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, AiAccessService, KeyRow (+27 more)

### Community 65 - "Community 65"
Cohesion: 0.06
Nodes (36): Change(), Changes(), PositionSchema, AdoptBody, Country, CreateBody, Description, EditBody (+28 more)

### Community 66 - "Community 66"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 67 - "Community 67"
Cohesion: 0.11
Nodes (35): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+27 more)

### Community 68 - "Community 68"
Cohesion: 0.09
Nodes (28): ADR-0051, Status, ADR-0056, ADR-0047, clearPushRuns(), clearQueue(), conflictAtSsi(), connectSsi() (+20 more)

### Community 69 - "Community 69"
Cohesion: 0.06
Nodes (34): areaOf(), areas, capture(), findings, headers, ADR-0020, ADR-0021, ADR-0022 (+26 more)

### Community 70 - "Community 70"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 71 - "Community 71"
Cohesion: 0.08
Nodes (31): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+23 more)

### Community 72 - "Community 72"
Cohesion: 0.09
Nodes (32): DiveImportMode, DiveKind, Placement, ConflictEntry, ConflictField, createConflictService(), loadWaiting(), stillDiffering() (+24 more)

### Community 73 - "Community 73"
Cohesion: 0.13
Nodes (34): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+26 more)

### Community 74 - "Community 74"
Cohesion: 0.09
Nodes (33): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+25 more)

### Community 75 - "Community 75"
Cohesion: 0.12
Nodes (32): importsQuery(), ImportView, uploadFile(), Logbook(), ImportContext, ImportFilesButton(), ImportHistory(), ImportList() (+24 more)

### Community 76 - "Community 76"
Cohesion: 0.06
Nodes (34): build, depends_on, environment, image, ports, restart, volumes, condition (+26 more)

### Community 77 - "Community 77"
Cohesion: 0.06
Nodes (34): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+26 more)

### Community 78 - "Community 78"
Cohesion: 0.12
Nodes (26): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+18 more)

### Community 79 - "Community 79"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 80 - "Community 80"
Cohesion: 0.09
Nodes (27): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+19 more)

### Community 81 - "Community 81"
Cohesion: 0.13
Nodes (29): bottomTimeRule(), defined(), leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas, toolsGasQuery(), toolsPlanQuery() (+21 more)

### Community 82 - "Community 82"
Cohesion: 0.11
Nodes (28): ToolsPlan, ToolsPlanDiver, PlanDiver, PlanLevel, planSketch(), PlanStretch, ToolsRule, DEFAULT_CYLINDER (+20 more)

### Community 83 - "Community 83"
Cohesion: 0.09
Nodes (27): devDependencies, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2, @testing-library/react@^16.3.3 (+19 more)

### Community 84 - "Community 84"
Cohesion: 0.09
Nodes (21): Tx, AppendUndo, ADR-0020, ADR-0056, ADR-0066, withRevisionHints(), ChangeListener, REVISION_CAUSES (+13 more)

### Community 85 - "Community 85"
Cohesion: 0.11
Nodes (19): SsiClient, ClearChecks, TwowayChecks, apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks() (+11 more)

### Community 86 - "Community 86"
Cohesion: 0.09
Nodes (22): Cylinder, DiveView, RevisionView, ADR-0045, upload(), Candidate, Check, Outcome (+14 more)

### Community 87 - "Community 87"
Cohesion: 0.10
Nodes (22): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+14 more)

### Community 88 - "Community 88"
Cohesion: 0.08
Nodes (24): AssessmentService, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView(), ADR-0036 (+16 more)

### Community 89 - "Community 89"
Cohesion: 0.10
Nodes (23): ENGINE_VERSION, EVIDENCE, Evidence, FindingValues, RULES, SEVERITIES, Severity, SourceId (+15 more)

### Community 90 - "Community 90"
Cohesion: 0.16
Nodes (24): Series, SsiLogbook, aboveZero(), cylinderOf(), deviceOf(), deviceOfRef(), MATERIAL_OF_TANK_TYPE, nonZero() (+16 more)

### Community 91 - "Community 91"
Cohesion: 0.13
Nodes (22): diveWith(), ADR-0028, ADR-0043, forget(), headers, ADR-0043, headers, Site (+14 more)

### Community 92 - "Community 92"
Cohesion: 0.13
Nodes (22): byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf(), main() (+14 more)

### Community 93 - "Community 93"
Cohesion: 0.12
Nodes (21): RecordingSummary, UtcOffsetSource, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput (+13 more)

### Community 94 - "Community 94"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 95 - "Community 95"
Cohesion: 0.08
Nodes (19): accountRelations, apikey, rateLimit, sessionRelations, ADR-0011, ADR-0035, userRelations, verification (+11 more)

### Community 96 - "Community 96"
Cohesion: 0.15
Nodes (20): durationFormat(), DurationFormatter, formatDuration(), formatGasRate(), formatMass(), formatPressure(), formatPressureRate(), formatTemperature() (+12 more)

### Community 97 - "Community 97"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 98 - "Community 98"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 99 - "Community 99"
Cohesion: 0.09
Nodes (20): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+12 more)

### Community 100 - "Community 100"
Cohesion: 0.14
Nodes (19): createFileFormats(), createFitAdapter(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf() (+11 more)

### Community 101 - "Community 101"
Cohesion: 0.15
Nodes (22): baseDiveOf(), providerChangesOf(), comparableOf(), hasTolerance(), isEmpty(), near(), planSend(), sameAtProvider() (+14 more)

### Community 102 - "Community 102"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 103 - "Community 103"
Cohesion: 0.14
Nodes (21): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+13 more)

### Community 104 - "Community 104"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 105 - "Community 105"
Cohesion: 0.09
Nodes (17): headers, ADR-0035, aiAccessReady(), askMcp(), createAiAccess(), putAside(), headers, ADR-0018 (+9 more)

### Community 106 - "Community 106"
Cohesion: 0.13
Nodes (21): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+13 more)

### Community 107 - "Community 107"
Cohesion: 0.12
Nodes (15): TAGS, killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023 (+7 more)

### Community 108 - "Community 108"
Cohesion: 0.13
Nodes (17): providerGoneDive, compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051, candidatesOf() (+9 more)

### Community 109 - "Community 109"
Cohesion: 0.14
Nodes (18): DAY_MS, MAX_OFFSET_MS, inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), placeLocalTime(), Position (+10 more)

### Community 110 - "Community 110"
Cohesion: 0.14
Nodes (16): EXPIRE_WAITING_IMPORTS_TASK, PROCESS_IMPORT_TASK, PUSH_RUN_STEP_TASK, WAITING_PUSHES_TASK, AUTOMATION_TICK_TASK, IMPORT_DIVE_SITES_TASK, idOf(), processImportTask() (+8 more)

### Community 111 - "Community 111"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 112 - "Community 112"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 113 - "Community 113"
Cohesion: 0.20
Nodes (17): AssessmentView, AssessmentState, bandSeconds(), clock(), counts(), differing(), Finding, formatRate() (+9 more)

### Community 114 - "Community 114"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 115 - "Community 115"
Cohesion: 0.11
Nodes (19): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, @internationalized/date@^3.12.4, lucide-react@^1.51.0, react@^19.3.0 (+11 more)

### Community 116 - "Community 116"
Cohesion: 0.14
Nodes (17): defineTool(), Id(), Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), HINT (+9 more)

### Community 117 - "Community 117"
Cohesion: 0.14
Nodes (15): PROBLEM_CODES, SiteImportRouteDeps, StartBody, STATUS, ADR-0021, SiteImportError, SiteImportService, Area (+7 more)

### Community 118 - "Community 118"
Cohesion: 0.11
Nodes (14): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+6 more)

### Community 119 - "Community 119"
Cohesion: 0.18
Nodes (13): failedTitlesSince(), git(), failedTestsFromReport(), lastStatuses(), localTime(), appendRun(), browserStats(), logPath() (+5 more)

### Community 120 - "Community 120"
Cohesion: 0.13
Nodes (15): CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationExpiry, CertificationInput, CertificationView, EXPIRING_SOON_DAYS, ImportReport, ImportResult (+7 more)

### Community 121 - "Community 121"
Cohesion: 0.15
Nodes (10): HINT_CHANNEL, createListener(), connect(), lost(), LiveListener, LiveLog, RETRY_MS, ADR-0066 (+2 more)

### Community 122 - "Community 122"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 123 - "Community 123"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 124 - "Community 124"
Cohesion: 0.16
Nodes (11): replyCertificationError, STATUS, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, CertificationRouteDeps (+3 more)

### Community 125 - "Community 125"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 126 - "Community 126"
Cohesion: 0.16
Nodes (8): i18next@^26.4.2, CustomTypeOptions, i18next, initI18n(), resources, ADR-0014, ADR-0014, i18next

### Community 127 - "Community 127"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 128 - "Community 128"
Cohesion: 0.15
Nodes (8): ndl(), ProfileAssessment, Finding, RULE_IDS, SOURCES, CLEAN, samples, ADR-0036

### Community 129 - "Community 129"
Cohesion: 0.15
Nodes (9): Sac, SAC_MISSING, SacMissing, ADR-0033, ADR-0045, Cylinder, dive, ADR-0033 (+1 more)

### Community 130 - "Community 130"
Cohesion: 0.26
Nodes (7): DiveSummary, Profile, SKETCH_HEIGHT, SKETCH_WIDTH, sketchPaths(), ADR-0041, ADR-0041

### Community 131 - "Community 131"
Cohesion: 0.22
Nodes (9): dayNumber(), deepDays(), NoFly, noFlyAfter(), SERIES_RULES, SeriesContext, SeriesFinding, SeriesRuleId (+1 more)

### Community 132 - "Community 132"
Cohesion: 0.18
Nodes (5): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053

### Community 133 - "Community 133"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 134 - "Community 134"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 135 - "Community 135"
Cohesion: 0.27
Nodes (8): CertificationError, createCertificationService(), cleaned(), requireManagedDiver(), rowOrThrow(), expiryOf(), isCalendarDate(), isoDay()

### Community 137 - "Community 137"
Cohesion: 0.22
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 138 - "Community 138"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 139 - "Community 139"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 141 - "Community 141"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 142 - "Community 142"
Cohesion: 0.36
Nodes (6): ADR-0025, WaterMismatch, waterMismatch(), WATER_DENSITY, WaterType, ADR-0025

### Community 143 - "Community 143"
Cohesion: 0.25
Nodes (7): Hint, HINT_KINDS, HintAudience, HintKind, kinds, ADR-0020, ADR-0066

### Community 144 - "Community 144"
Cohesion: 0.29
Nodes (5): parseHint(), createHub(), HEARTBEAT_MS, MAX_STREAMS_PER_USER, ADR-0066

### Community 145 - "Community 145"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 146 - "Community 146"
Cohesion: 0.33
Nodes (4): Sketch, SKETCH_POINTS, ADR-0041, ADR-0041

### Community 147 - "Community 147"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 148 - "Community 148"
Cohesion: 0.33
Nodes (4): pollUntilDue(), ADR-0056, now, ADR-0056

### Community 149 - "Community 149"
Cohesion: 0.33
Nodes (6): ADR-0023, ADR-0053, ADR-0066, MODULE_IMPORTS, moduleRules, target()

### Community 150 - "Community 150"
Cohesion: 0.40
Nodes (5): stubDive(), ADR-0015, ADR-0023, ADR-0025, guardedRoute()

### Community 151 - "Community 151"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 152 - "Community 152"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 153 - "Community 153"
Cohesion: 0.40
Nodes (4): CertificationSummary, NO_CERTIFICATIONS, ADR-0050, ADR-0053

### Community 154 - "Community 154"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 155 - "Community 155"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

## Knowledge Gaps
- **1029 isolated node(s):** `ADR-0012`, `ADR-0013`, `ADR-0030`, `ADR-0035`, `ADR-0031` (+1024 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 2562 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **3 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 4` to `Community 5`, `Community 6`, `Community 7`, `Community 8`, `Community 10`, `Community 11`, `Community 14`, `Community 143`, `Community 15`, `Community 16`, `Community 19`, `Community 21`, `Community 22`, `Community 24`, `Community 25`, `Community 28`, `Community 30`, `Community 36`, `Community 38`, `Community 39`, `Community 41`, `Community 42`, `Community 43`, `Community 44`, `Community 50`, `Community 56`, `Community 61`, `Community 64`, `Community 71`, `Community 72`, `Community 74`, `Community 95`, `Community 103`, `Community 108`, `Community 109`, `Community 120`?**
  _High betweenness centrality (0.036) - this node is a cross-community bridge._
- **What connects `ADR-0012`, `ADR-0013`, `ADR-0030` to the rest of the system?**
  _1029 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.04680446661914944 - nodes in this community are weakly interconnected._
- **Why does `Button()` connect `Community 0` to `Community 1`, `Community 2`, `Community 35`, `Community 37`, `Community 40`, `Community 9`, `Community 75`, `Community 13`, `Community 48`, `Community 18`, `Community 82`, `Community 20`, `Community 53`, `Community 58`, `Community 59`?**
  _High betweenness centrality (0.019) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.02784327952113187 - nodes in this community are weakly interconnected._
- **Why does `@playwright/test` connect `Community 47` to `Community 34`, `Community 68`, `Community 133`, `Community 69`, `Community 105`, `Community 137`, `Community 83`, `Community 150`, `Community 87`, `Community 122`, `Community 91`, `Community 94`?**
  _High betweenness centrality (0.017) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.04222397062680304 - nodes in this community are weakly interconnected._