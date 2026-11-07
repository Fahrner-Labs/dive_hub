# Graph Report - 60c35ce8-111f-4f4f-8f36-1f0e151b50cd  (2026-11-07)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 6286 nodes · 19809 edges · 156 communities (146 shown, 10 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 197 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `34731d14`
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
- Community 152
- Community 153
- Community 154
- Community 155

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 181 edges
2. `Button()` - 178 edges
3. `unwrap()` - 175 edges
4. `useDisplay()` - 148 edges
5. `useErrorText()` - 148 edges
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

## Communities (156 total, 10 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.03
Nodes (177): bottomTimeRule(), cylinderCatalogueQuery(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas (+169 more)

### Community 1 - "Community 1"
Cohesion: 0.05
Nodes (167): ChangePassword(), DisplaySettings(), Sessions(), useDescribeAgent(), AdminSettings(), DeleteUserDialog(), Invitations(), Invite() (+159 more)

### Community 2 - "Community 2"
Cohesion: 0.02
Nodes (105): graphile-worker@^0.18.0, createServer(), loadAuthSecret(), workerDeps(), Config, loadConfig(), ADR-0011, ADR-0021 (+97 more)

### Community 3 - "Community 3"
Cohesion: 0.05
Nodes (96): user, accountListEntry, diver, revision, forget(), listAccountOf(), remember(), rememberedEntries() (+88 more)

### Community 4 - "Community 4"
Cohesion: 0.04
Nodes (121): ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, diverExternalId, diveSite (+113 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (110): divesQuery(), DiveSummary, ExternalIdView, isUnauthorized(), LogbookPage, SiteImportArea, SiteImportView, siteQuery() (+102 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (100): Auth, CentreService, CentreRouteDeps, DiverService, DiverRouteDeps, downsampleMinMax(), AppendDeps, AppendOutcome (+92 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (87): Assessment, FindingView, Row, ADR-0036, upload(), Check, Deleted, DiveView (+79 more)

### Community 8 - "Community 8"
Cohesion: 0.04
Nodes (90): CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, CertificationRouteDeps, errors, Fields (+82 more)

### Community 9 - "Community 9"
Cohesion: 0.04
Nodes (96): LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, participant, renumber(), renumberStatement(), ADR-0048, AppendRow (+88 more)

### Community 10 - "Community 10"
Cohesion: 0.02
Nodes (95): AiAccessLogEntry, AiAccessView, AnyLogbookCheck, BuddyView, ContinuationCandidateView, DETAILS, devicesQuery(), DeviceView (+87 more)

### Community 11 - "Community 11"
Cohesion: 0.05
Nodes (94): allButDive(), continuationCandidateQuery(), DeletedDiveView, diveNeighboursQuery(), diveProvidersQuery(), diveQuery(), DiveView, mergeCandidatesQuery() (+86 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (67): connection, diveLease, providerConflict, push, pushRun, waitingPush, kept(), record() (+59 more)

### Community 13 - "Community 13"
Cohesion: 0.04
Nodes (74): adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, AuthOptions, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, ADR-0011, ADR-0013 (+66 more)

### Community 14 - "Community 14"
Cohesion: 0.04
Nodes (72): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+64 more)

### Community 15 - "Community 15"
Cohesion: 0.05
Nodes (59): Me, CodeFigure(), DiveCodes(), showDiveCodes(), ADR-0043, DiverRow(), avatarTone(), initials() (+51 more)

### Community 16 - "Community 16"
Cohesion: 0.06
Nodes (75): candidatesQuery(), CandidateView, ConflictChoices, ConflictEntryView, ConflictsOfConnection, deletedDivesQuery(), DiveCheckView, diversQuery() (+67 more)

### Community 17 - "Community 17"
Cohesion: 0.03
Nodes (75): actorType, aiAccessOutcome, candidateResolution, centreSource, CERTIFICATION_IMAGE_STATES, CERTIFICATION_VERIFICATIONS, certificationImageState, certificationVerification (+67 more)

### Community 18 - "Community 18"
Cohesion: 0.08
Nodes (67): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, AssessmentService, AssessmentView (+59 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (63): diveSiteExternalId, lead, DIVE_FIELDS, SSI_CAPABILITIES, SsiCallLog, rowOf(), createSsiClient(), DEFAULT_CARD_IMAGE_BASE (+55 more)

### Community 20 - "Community 20"
Cohesion: 0.07
Nodes (61): api, ApiError, buddiesForQuery(), buddiesQuery(), centreQuery(), centresQuery(), CentreView, ConnectionView (+53 more)

### Community 21 - "Community 21"
Cohesion: 0.06
Nodes (66): Queryable, logbookCheckAnswer, logbookCheckDiveAnswer, recording, keepApart(), keepDive(), keepRestored(), ADR-0038 (+58 more)

### Community 22 - "Community 22"
Cohesion: 0.05
Nodes (69): ConflictFieldView, diveListPages, ProblemCode, providerSitesQuery(), providersQuery(), providerStatusQuery(), PushView, RequirementView (+61 more)

### Community 23 - "Community 23"
Cohesion: 0.05
Nodes (55): SsiClient, SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), DiveForSsi, emptyRecord(), writableRecord() (+47 more)

### Community 24 - "Community 24"
Cohesion: 0.04
Nodes (63): pushRunItem, PushRunLeftOut, DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN, NO_RECORDING (+55 more)

### Community 25 - "Community 25"
Cohesion: 0.04
Nodes (56): ServiceParts, Db, ComputerChoice, DiveImportMode, DiveKind, ImportErrorCode, ImportFound, importOriginal (+48 more)

### Community 26 - "Community 26"
Cohesion: 0.05
Nodes (57): AiAccessScope, seesPositions(), VerifiedAccess, diversOf(), checkedByRunTool, createMcpServer(), INSTRUCTIONS, MCP_SERVER_INFO (+49 more)

### Community 27 - "Community 27"
Cohesion: 0.05
Nodes (55): CylinderMaterial, WeightingFelt, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, WeightingFeedback, answer(), baseFrom() (+47 more)

### Community 28 - "Community 28"
Cohesion: 0.05
Nodes (44): createAuth(), auth, cylinder, duplicateCandidate, importJob, original, ImportService, out (+36 more)

### Community 29 - "Community 29"
Cohesion: 0.05
Nodes (47): createServices(), ADR-0027, ADR-0030, ADR-0061, ADR-0063, ADR-0066, Tx, diverManagement (+39 more)

### Community 30 - "Community 30"
Cohesion: 0.07
Nodes (47): ImportArea, errorMessage(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy() (+39 more)

### Community 31 - "Community 31"
Cohesion: 0.04
Nodes (44): ParticipantRole, ProviderImportPlan, PushLeftOut, Capabilities, Direction, DiveExport, DiveListing, FindBy (+36 more)

### Community 32 - "Community 32"
Cohesion: 0.07
Nodes (57): SecuritySettings(), pushRunActive(), pushRunBody(), PushRunChoices, PushRunDetail, PushRunPreview, pushRunPreviewQuery(), pushRunQuery() (+49 more)

### Community 33 - "Community 33"
Cohesion: 0.07
Nodes (57): ADR-0025, ADR-0027, ADR-0030, ADR-0031, ADR-0032, ADR-0035, ADR-0043, ADR-0048 (+49 more)

### Community 34 - "Community 34"
Cohesion: 0.08
Nodes (56): managedDiverIds(), tieToJoined(), CylinderError, cylindersOf(), mergeProviderCylinder(), providerCylinderOf(), sameSeries(), setCylinders() (+48 more)

### Community 35 - "Community 35"
Cohesion: 0.07
Nodes (54): waitingOf(), close(), currentRemote(), PushRow, ADR-0024, Choice, isExporting(), referenceOf() (+46 more)

### Community 36 - "Community 36"
Cohesion: 0.06
Nodes (49): replyCertificationError, STATUS, CertificationService, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053, CandidateRouteDeps (+41 more)

### Community 37 - "Community 37"
Cohesion: 0.07
Nodes (54): ConnectionRow, WaitingPushOptions, audienceOfConnection(), audienceOfDive(), audienceOfDiver(), Hint, HINT_KINDS, HintAudience (+46 more)

### Community 38 - "Community 38"
Cohesion: 0.06
Nodes (53): RecordingSummary, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput, round3() (+45 more)

### Community 39 - "Community 39"
Cohesion: 0.07
Nodes (49): Delivered, ProviderError, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf() (+41 more)

### Community 40 - "Community 40"
Cohesion: 0.05
Nodes (51): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+43 more)

### Community 41 - "Community 41"
Cohesion: 0.06
Nodes (41): AssessmentError, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), requireManagedDive() (+33 more)

### Community 42 - "Community 42"
Cohesion: 0.05
Nodes (49): LEAD_PLACEMENTS, SUIT_TYPES, SUIT_UNDERGARMENTS, WEIGHTING_FELT, ProfileSummary, round1(), Series, summariseProfile() (+41 more)

### Community 43 - "Community 43"
Cohesion: 0.09
Nodes (41): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ambientPressureBar(), bestMixPercent() (+33 more)

### Community 44 - "Community 44"
Cohesion: 0.06
Nodes (43): ADR-0036, ADR-0063, ADR-0038, ADR-0048, ADR-0062, AiAccess, assessedDive(), AssessedFinding (+35 more)

### Community 45 - "Community 45"
Cohesion: 0.08
Nodes (42): externalDiver(), mergeExternalDiver(), participations(), open(), AutomationSettings, createConnectionService(), checkAutomation(), checkFollowNumbers() (+34 more)

### Community 46 - "Community 46"
Cohesion: 0.09
Nodes (36): sacOfDive(), BottomTimeQuery, DEFAULT_GF_HIGH, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033 (+28 more)

### Community 47 - "Community 47"
Cohesion: 0.09
Nodes (41): Leave, SENT_KEYS, diveModeOfSsi(), keepTraining(), ssiDiveType(), ssiInteger(), ADR-0057, asNumber() (+33 more)

### Community 48 - "Community 48"
Cohesion: 0.07
Nodes (36): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+28 more)

### Community 49 - "Community 49"
Cohesion: 0.05
Nodes (32): SiteSourceAdapter, SourceSite, call(), createSite(), Method, openStream(), Stream, ADR-0066 (+24 more)

### Community 50 - "Community 50"
Cohesion: 0.08
Nodes (29): headers, ADR-0031, headers, ADR-0043, ADR-0016, created, headers, ADR-0066 (+21 more)

### Community 51 - "Community 51"
Cohesion: 0.05
Nodes (39): headers, ADR-0035, areaOf(), areas, capture(), findings, headers, ADR-0020 (+31 more)

### Community 52 - "Community 52"
Cohesion: 0.13
Nodes (38): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+30 more)

### Community 53 - "Community 53"
Cohesion: 0.06
Nodes (31): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CertificationError, CertificationExpiry (+23 more)

### Community 54 - "Community 54"
Cohesion: 0.07
Nodes (37): DIVE_ONLY_PATTERN, ErrorAnswer, PROBLEM_CODES, ProblemSchema, storedProblemCode(), ADR-0014, ADR-0027, oneOf() (+29 more)

### Community 55 - "Community 55"
Cohesion: 0.06
Nodes (39): Change(), Changes(), historyRoute, Made(), REVISION_CAUSES, PositionSchema, AdoptBody, Country (+31 more)

### Community 56 - "Community 56"
Cohesion: 0.08
Nodes (39): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), divesAtSite() (+31 more)

### Community 57 - "Community 57"
Cohesion: 0.05
Nodes (41): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+33 more)

### Community 58 - "Community 58"
Cohesion: 0.08
Nodes (39): CylinderMaterial, BOTTOM_TIME_RULES, cylinderOf(), cylinderText(), diverOf(), diverText(), finite(), kept (+31 more)

### Community 59 - "Community 59"
Cohesion: 0.09
Nodes (33): tidyName(), DiverDetails, Caller, locked(), foldName(), nameDiffers(), Buddy, createBuddyService() (+25 more)

### Community 60 - "Community 60"
Cohesion: 0.10
Nodes (39): siteRef(), kindOfMode(), deviceDiver(), closeFields(), createConflictService(), loadWaiting(), entryOf(), fieldsOf() (+31 more)

### Community 61 - "Community 61"
Cohesion: 0.07
Nodes (33): ADR-0025, WaterMismatch, waterMismatch(), CIRCUIT, circuitFromFit, DECO_MODEL, decoModelFromFit, DIVE_ALERT (+25 more)

### Community 62 - "Community 62"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 63 - "Community 63"
Cohesion: 0.06
Nodes (31): DiverCertifications, replyDiverError, STATUS, CertificationSummary, DiverDetailsView, DiverDetailsViewSchema, DiverSource, DiverView (+23 more)

### Community 64 - "Community 64"
Cohesion: 0.11
Nodes (35): massIn(), Dive, draftOfLead(), draftOfWeighting(), emptyLead(), fromLastDive(), hasThickness(), kg() (+27 more)

### Community 65 - "Community 65"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 66 - "Community 66"
Cohesion: 0.13
Nodes (34): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+26 more)

### Community 67 - "Community 67"
Cohesion: 0.09
Nodes (32): ADR-0039, args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE (+24 more)

### Community 68 - "Community 68"
Cohesion: 0.08
Nodes (31): AI_ACCESS_NAME_MAX, aiAccessLog, aiAccessSetting, Access, AccessView, AiAccessRouteDeps, LogEntryView, Scope (+23 more)

### Community 69 - "Community 69"
Cohesion: 0.11
Nodes (28): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+20 more)

### Community 70 - "Community 70"
Cohesion: 0.08
Nodes (17): HINT_CHANNEL, parseHint(), createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener() (+9 more)

### Community 71 - "Community 71"
Cohesion: 0.08
Nodes (25): ADR-0057, createScheduledFetch(), blocked(), due(), minimumOf(), OPENING_AFTER_MINUTES, SCHEDULED_FETCH_TASK, ADR-0056 (+17 more)

### Community 72 - "Community 72"
Cohesion: 0.11
Nodes (30): DiveOrigin, REFERENCE_PREFIX, Series, SsiLogbook, asBool(), DIVE_ROW, SsiValueType, ADR-0027 (+22 more)

### Community 73 - "Community 73"
Cohesion: 0.10
Nodes (28): diveWith(), ADR-0028, ADR-0043, forget(), headers, ADR-0043, ADR-0051, headers (+20 more)

### Community 74 - "Community 74"
Cohesion: 0.06
Nodes (34): build, depends_on, environment, image, ports, restart, volumes, condition (+26 more)

### Community 75 - "Community 75"
Cohesion: 0.06
Nodes (34): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+26 more)

### Community 76 - "Community 76"
Cohesion: 0.08
Nodes (30): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+22 more)

### Community 77 - "Community 77"
Cohesion: 0.07
Nodes (26): Cylinder, DiveView, RevisionView, ADR-0045, upload(), Candidate, Check, Outcome (+18 more)

### Community 78 - "Community 78"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 79 - "Community 79"
Cohesion: 0.09
Nodes (27): ENGINE_VERSION, EVIDENCE, Evidence, FindingValues, RULE_IDS, RuleId, RULES, SEVERITIES (+19 more)

### Community 80 - "Community 80"
Cohesion: 0.09
Nodes (25): headers, ADR-0030, ADR-0040, conflictForLena(), diveWithoutRecording(), editInSsi(), externalDiver(), importLena() (+17 more)

### Community 81 - "Community 81"
Cohesion: 0.15
Nodes (29): importsQuery(), ImportView, uploadFile(), Logbook(), ImportContext, ImportFilesButton(), ImportHistory(), ImportList() (+21 more)

### Community 82 - "Community 82"
Cohesion: 0.09
Nodes (23): computerEventOf(), ADR-0036, computerEventFromFit(), COMPUTER_EVENT, computerEventFromSuunto(), DECO_MODEL, decoModelFromSuunto, DIVE_MODE (+15 more)

### Community 83 - "Community 83"
Cohesion: 0.10
Nodes (26): globalSetup(), ADR-0023, accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks() (+18 more)

### Community 84 - "Community 84"
Cohesion: 0.09
Nodes (27): devDependencies, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2, @testing-library/react@^16.3.3 (+19 more)

### Community 85 - "Community 85"
Cohesion: 0.10
Nodes (26): applyHints(), LiveArrivals, Page, useLiveArrivals(), bump(), changedListeners, ChannelMessage, healthListeners (+18 more)

### Community 86 - "Community 86"
Cohesion: 0.14
Nodes (23): AscentBand, ascentBandsOf(), assessProfile(), ceiling(), ppo2(), Profile, PROFILE_RULES, ProfileInput (+15 more)

### Community 87 - "Community 87"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 88 - "Community 88"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 89 - "Community 89"
Cohesion: 0.15
Nodes (22): stillDiffering(), shownFields(), comparableOf(), hasTolerance(), isEmpty(), near(), planSend(), sameAtProvider() (+14 more)

### Community 90 - "Community 90"
Cohesion: 0.08
Nodes (18): i18next@^26.4.2, CustomTypeOptions, i18next, StrayOverride, english, flatten(), Tree, ADR-0014 (+10 more)

### Community 91 - "Community 91"
Cohesion: 0.17
Nodes (21): ADR-0053, ADR-0067, build(), byId(), callersOf(), dependentsOf(), fileNode(), git() (+13 more)

### Community 92 - "Community 92"
Cohesion: 0.11
Nodes (16): ndl(), ProfileAssessment, Finding, LIMITS, dayNumber(), deepDays(), noFlyAfter(), SERIES_RULES (+8 more)

### Community 93 - "Community 93"
Cohesion: 0.13
Nodes (18): CERTIFICATION_IMAGE_TASK, EXPIRE_WAITING_IMPORTS_TASK, PROCESS_IMPORT_TASK, PUSH_RUN_STEP_TASK, WAITING_PUSHES_TASK, waitingPushesKey(), AUTOMATION_TICK_TASK, IMPORT_DIVE_SITES_TASK (+10 more)

### Community 94 - "Community 94"
Cohesion: 0.14
Nodes (18): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+10 more)

### Community 95 - "Community 95"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 96 - "Community 96"
Cohesion: 0.16
Nodes (19): Answer, CatalogueCylinder, CylinderDraft, CylinderInput, cylindersOfDrafts(), CylinderView, draftOfCylinder(), emptyDraft() (+11 more)

### Community 97 - "Community 97"
Cohesion: 0.12
Nodes (15): TAGS, killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023 (+7 more)

### Community 98 - "Community 98"
Cohesion: 0.13
Nodes (17): providerGoneDive, compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051, candidatesOf() (+9 more)

### Community 99 - "Community 99"
Cohesion: 0.12
Nodes (18): CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043, CreateBody (+10 more)

### Community 100 - "Community 100"
Cohesion: 0.14
Nodes (18): DAY_MS, MAX_OFFSET_MS, inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), placeLocalTime(), Position (+10 more)

### Community 101 - "Community 101"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 102 - "Community 102"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 103 - "Community 103"
Cohesion: 0.13
Nodes (15): FIND_BY, OPERATIONS, SignInInput, CASES, ContractHarness, declared(), ELSEWHERE, later() (+7 more)

### Community 104 - "Community 104"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 105 - "Community 105"
Cohesion: 0.11
Nodes (19): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, @internationalized/date@^3.12.4, lucide-react@^1.51.0, react@^19.3.0 (+11 more)

### Community 106 - "Community 106"
Cohesion: 0.14
Nodes (14): nearest(), perMinute(), Series, summarize(), ChannelSeries, RecordedGas, Tank, tanksOf() (+6 more)

### Community 107 - "Community 107"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 108 - "Community 108"
Cohesion: 0.11
Nodes (16): account, accountRelations, apikey, rateLimit, session, sessionRelations, ADR-0011, ADR-0035 (+8 more)

### Community 109 - "Community 109"
Cohesion: 0.22
Nodes (14): looksLikeZip(), formatOf(), createImportService(), cancelImport(), createImport(), endWaiting(), expireWaiting(), openUpload() (+6 more)

### Community 110 - "Community 110"
Cohesion: 0.12
Nodes (15): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+7 more)

### Community 111 - "Community 111"
Cohesion: 0.17
Nodes (12): sampleCount(), storedList(), tryParseJson(), APP_PARAMS, createSsiTransport(), exchange(), isObject(), SSI_APP_USER_AGENT (+4 more)

### Community 112 - "Community 112"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 113 - "Community 113"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 114 - "Community 114"
Cohesion: 0.20
Nodes (12): fromOsm(), fromSsi(), fromWd(), HOUSE_REEF, osm(), plan(), site(), ssi() (+4 more)

### Community 115 - "Community 115"
Cohesion: 0.18
Nodes (10): ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, conflictAtSsi(), editAtSsi(), editElsewhere() (+2 more)

### Community 116 - "Community 116"
Cohesion: 0.20
Nodes (11): clearQueue(), fetchState(), leaveConflict(), queueEntry(), ssiConnectionOf(), automatic(), leave(), openConnections() (+3 more)

### Community 117 - "Community 117"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 118 - "Community 118"
Cohesion: 0.23
Nodes (10): git(), localTime(), appendRun(), browserStats(), logPath(), main(), ADR-0023, summarise() (+2 more)

### Community 119 - "Community 119"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 120 - "Community 120"
Cohesion: 0.15
Nodes (9): Sac, SAC_MISSING, SacMissing, ADR-0033, ADR-0045, Cylinder, dive, ADR-0033 (+1 more)

### Community 121 - "Community 121"
Cohesion: 0.26
Nodes (9): ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS, eachFile(), readEntry(), ADR-0044, walk() (+1 more)

### Community 122 - "Community 122"
Cohesion: 0.21
Nodes (9): createFitAdapter(), adapter, crossCheck(), decodeOfficial(), privateDir, ownersParts(), parse(), toPart() (+1 more)

### Community 123 - "Community 123"
Cohesion: 0.26
Nodes (8): Profile, SKETCH_HEIGHT, SKETCH_WIDTH, sketchPaths(), sketchScale(), ADR-0041, ProfileSketch(), ADR-0041

### Community 124 - "Community 124"
Cohesion: 0.25
Nodes (11): ascentRate(), descentRate(), judgedParts(), lastMetres(), safetyStop(), stopIn(), stopStability(), worstOf() (+3 more)

### Community 125 - "Community 125"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 126 - "Community 126"
Cohesion: 0.22
Nodes (8): diveRow(), rowOf(), ADR-0026, ADR-0048, diveRows(), goToMore(), expectFirstDiveInView(), rowOf()

### Community 127 - "Community 127"
Cohesion: 0.20
Nodes (6): noAnswer, thrown(), ADR-0014, ApiClient, createApiClient(), ADR-0004

### Community 128 - "Community 128"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

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
Cohesion: 0.28
Nodes (7): needsOsmAttribution(), siteOrigin(), ADR-0021, offer, osm, ssi, wikidata

### Community 134 - "Community 134"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 135 - "Community 135"
Cohesion: 0.25
Nodes (7): AIR, expectWithinReference(), GF100_FRESH, GF100_SALT, GF85_FRESH, ADR-0032, ADR-0046

### Community 136 - "Community 136"
Cohesion: 0.36
Nodes (6): headers, open(), ownId(), ownRow(), sectionButton(), ADR-0050

### Community 137 - "Community 137"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 138 - "Community 138"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 139 - "Community 139"
Cohesion: 0.33
Nodes (4): pollUntilDue(), ADR-0056, now, ADR-0056

### Community 140 - "Community 140"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 141 - "Community 141"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 142 - "Community 142"
Cohesion: 0.40
Nodes (4): IMPORTABLE, splitImportable(), ADR-0037, ADR-0037

### Community 144 - "Community 144"
Cohesion: 0.67
Nodes (3): planSketch(), shape(), ADR-0033

### Community 145 - "Community 145"
Cohesion: 0.50
Nodes (4): ToolsParams, SuitType, SuitUndergarment, ListControl

### Community 146 - "Community 146"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 147 - "Community 147"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

## Knowledge Gaps
- **1026 isolated node(s):** `ADR-0020`, `ADR-0025`, `ADR-0029`, `ADR-0057`, `ADR-0045` (+1021 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 2537 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **10 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `@playwright/test` connect `Community 50` to `Community 129`, `Community 136`, `Community 73`, `Community 44`, `Community 80`, `Community 51`, `Community 115`, `Community 83`, `Community 116`, `Community 87`, `Community 84`, `Community 125`, `Community 126`?**
  _High betweenness centrality (0.031) - this node is a cross-community bridge._
- **What connects `ADR-0020`, `ADR-0025`, `ADR-0029` to the rest of the system?**
  _1026 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.02656716417910448 - nodes in this community are weakly interconnected._
- **Why does `drizzle-orm` connect `Community 3` to `Community 2`, `Community 4`, `Community 7`, `Community 8`, `Community 9`, `Community 12`, `Community 13`, `Community 14`, `Community 17`, `Community 18`, `Community 19`, `Community 21`, `Community 24`, `Community 25`, `Community 26`, `Community 28`, `Community 29`, `Community 34`, `Community 35`, `Community 37`, `Community 38`, `Community 40`, `Community 41`, `Community 45`, `Community 46`, `Community 49`, `Community 53`, `Community 56`, `Community 59`, `Community 68`, `Community 71`, `Community 98`, `Community 100`, `Community 108`?**
  _High betweenness centrality (0.030) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.05414364640883978 - nodes in this community are weakly interconnected._
- **Why does `react` connect `Community 15` to `Community 0`, `Community 1`, `Community 32`, `Community 64`, `Community 5`, `Community 105`, `Community 10`, `Community 11`, `Community 16`, `Community 81`, `Community 84`, `Community 52`, `Community 20`, `Community 85`, `Community 22`, `Community 123`?**
  _High betweenness centrality (0.027) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.024820250172362848 - nodes in this community are weakly interconnected._