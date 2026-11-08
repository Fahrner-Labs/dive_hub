# Graph Report - dive_hub  (2026-11-08)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 6288 nodes · 19816 edges · 150 communities (146 shown, 4 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 197 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `428d4c74`
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

## Communities (150 total, 4 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.04
Nodes (150): allButDive(), api, ApiError, candidatesQuery(), CandidateView, ConflictChoices, ConflictEntryView, ConflictsOfConnection (+142 more)

### Community 1 - "Community 1"
Cohesion: 0.03
Nodes (136): LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, renumber(), managedDiverIds(), AppendRow, channelsOf(), createContinuationService() (+128 more)

### Community 2 - "Community 2"
Cohesion: 0.06
Nodes (138): ChangePassword(), DisplaySettings(), SecuritySettings(), Sessions(), useDescribeAgent(), AdminSettings(), DeleteUserDialog(), Invitations() (+130 more)

### Community 3 - "Community 3"
Cohesion: 0.05
Nodes (90): user, accountListEntry, diver, revision, forget(), listAccountOf(), remember(), ADR-0057 (+82 more)

### Community 4 - "Community 4"
Cohesion: 0.04
Nodes (112): bottomTimeRule(), cylinderCatalogueQuery(), defined(), LeadEstimate, ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas, toolsGasQuery() (+104 more)

### Community 5 - "Community 5"
Cohesion: 0.04
Nodes (106): divesQuery(), LogbookPage, ProblemCode, pushRunActive(), pushRunBody(), PushRunChoices, PushRunDetail, PushRunPreview (+98 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (98): UTC_OFFSET_SOURCES, downsampleMinMax(), AppendDeps, AppendOutcome, AppendService, Side, ADR-0038, ADR-0063 (+90 more)

### Community 7 - "Community 7"
Cohesion: 0.04
Nodes (102): buddiesQuery(), ConflictFieldView, ConnectionView, DiveImportMode, DiveImportPreview, diveImportPreviewQuery(), DiveKind, diveListPages (+94 more)

### Community 8 - "Community 8"
Cohesion: 0.02
Nodes (100): AiAccessLogEntry, AiAccessView, AnyLogbookCheck, buddiesForQuery(), BuddyView, ContinuationCandidateView, DeletedDiveView, DETAILS (+92 more)

### Community 9 - "Community 9"
Cohesion: 0.04
Nodes (93): importsQuery(), ImportView, isUnauthorized(), Me, setupQuery(), SiteImportArea, SiteImportView, uploadFile() (+85 more)

### Community 10 - "Community 10"
Cohesion: 0.04
Nodes (90): CertificationService, CYLINDER_MATERIALS, DIVE_IMPORT_MODES, MATCHING_WINDOWS, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053 (+82 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (71): createServer(), createAuth(), loadAuthSecret(), workerDeps(), Config, loadConfig(), ADR-0011, ADR-0021 (+63 more)

### Community 12 - "Community 12"
Cohesion: 0.04
Nodes (78): fastify@^5.12.5, adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, AuthOptions, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, SessionUser (+70 more)

### Community 13 - "Community 13"
Cohesion: 0.05
Nodes (64): CodeFigure(), DiveCodes(), showDiveCodes(), ADR-0043, avatarTone(), initials(), holdLeave(), mayLeave() (+56 more)

### Community 14 - "Community 14"
Cohesion: 0.02
Nodes (82): actorType, aiAccessOutcome, candidateResolution, centreSource, CERTIFICATION_IMAGE_STATES, CERTIFICATION_VERIFICATIONS, certificationImageState, certificationVerification (+74 more)

### Community 15 - "Community 15"
Cohesion: 0.06
Nodes (71): diverExternalId, diveSite, siteSource, dataIdsOf(), settleSiteVisibility(), DiveRow, LIST_SOURCES, outgoingSite() (+63 more)

### Community 16 - "Community 16"
Cohesion: 0.05
Nodes (74): ImportArea, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, siteImport (+66 more)

### Community 17 - "Community 17"
Cohesion: 0.04
Nodes (62): halves(), Check, Deleted, DiveView, Outcome, RecordingView, ADR-0063, Candidate (+54 more)

### Community 18 - "Community 18"
Cohesion: 0.04
Nodes (68): connection, dive, diveFinding, importJob, providerConflict, push, waitingPush, kept() (+60 more)

### Community 19 - "Community 19"
Cohesion: 0.05
Nodes (53): createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities(), providerError() (+45 more)

### Community 20 - "Community 20"
Cohesion: 0.06
Nodes (66): logbookCheckAnswer, logbookCheckDiveAnswer, recording, keepApart(), keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration() (+58 more)

### Community 21 - "Community 21"
Cohesion: 0.05
Nodes (62): tidyName(), DiverDetails, externalDiver(), mergeExternalDiver(), participations(), Caller, locked(), ADR-0057 (+54 more)

### Community 22 - "Community 22"
Cohesion: 0.04
Nodes (63): pushRunItem, PushRunLeftOut, DIVE_ONLY, DIVE_ONLY_PATTERN, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN (+55 more)

### Community 23 - "Community 23"
Cohesion: 0.05
Nodes (48): auth, createServices(), ServiceParts, ADR-0027, ADR-0030, ADR-0061, ADR-0063, ADR-0066 (+40 more)

### Community 24 - "Community 24"
Cohesion: 0.05
Nodes (54): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+46 more)

### Community 25 - "Community 25"
Cohesion: 0.06
Nodes (56): Db, Queryable, ComputerChoice, DiveImportMode, DiveKind, importOriginal, ImportOutcome, providerSiteData (+48 more)

### Community 26 - "Community 26"
Cohesion: 0.05
Nodes (56): PARTICIPANT_ROLES, siteImportStatus, ADR-0053, ADR-0056, WaitingPushViewSchema, PROBLEM_CODES, ProblemSchema, storedProblemCode() (+48 more)

### Community 27 - "Community 27"
Cohesion: 0.04
Nodes (44): Direction, DiveExport, DiveListing, FIND_BY, FindBy, Operation, OPERATIONS, OutgoingDive (+36 more)

### Community 28 - "Community 28"
Cohesion: 0.09
Nodes (58): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, AssessmentService, AssessmentView (+50 more)

### Community 29 - "Community 29"
Cohesion: 0.05
Nodes (55): CylinderMaterial, WeightingFelt, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, WeightingFeedback, answer(), baseFrom() (+47 more)

### Community 30 - "Community 30"
Cohesion: 0.05
Nodes (51): diveSiteExternalId, lead, createSsiClient(), Card, DEEP, OWD, Report, Summary (+43 more)

### Community 31 - "Community 31"
Cohesion: 0.07
Nodes (47): errorMessage(), looksLikeZip(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy() (+39 more)

### Community 32 - "Community 32"
Cohesion: 0.05
Nodes (54): ConnectionRow, WaitingPushOptions, createConflictService(), loadWaiting(), stillDiffering(), fieldsOf(), isExists(), shownFields() (+46 more)

### Community 33 - "Community 33"
Cohesion: 0.06
Nodes (52): Delivered, DIVE_FIELDS, ProviderError, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord() (+44 more)

### Community 34 - "Community 34"
Cohesion: 0.06
Nodes (53): diveNeighboursQuery(), diveQuery(), DiveValues, NeighboursView, OverridableField, PAGE_SIZE, RecordingSummary, revisionsQuery() (+45 more)

### Community 35 - "Community 35"
Cohesion: 0.07
Nodes (49): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ambientPressureBar(), bestMixPercent() (+41 more)

### Community 36 - "Community 36"
Cohesion: 0.05
Nodes (51): replyCertificationError, STATUS, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, CertificationRouteDeps (+43 more)

### Community 37 - "Community 37"
Cohesion: 0.06
Nodes (53): defined(), Joined, joinedSummary(), joinParts(), median(), round3(), ADR-0063, buildJoined() (+45 more)

### Community 38 - "Community 38"
Cohesion: 0.05
Nodes (50): ADR-0038, ADR-0048, ADR-0062, globalSetup(), ADR-0023, AiAccess, assessedDive(), AssessedFinding (+42 more)

### Community 39 - "Community 39"
Cohesion: 0.05
Nodes (42): AssessmentError, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), requireManagedDive() (+34 more)

### Community 40 - "Community 40"
Cohesion: 0.06
Nodes (51): leadEstimateQuery(), LeadHint(), orUndefined(), ADR-0025, ADR-0031, CylinderDraft, CylinderMaterial, BOTTOM_TIME_RULES (+43 more)

### Community 41 - "Community 41"
Cohesion: 0.09
Nodes (51): waitingOf(), open(), close(), createConnectionService(), checkAutomation(), checkFollowNumbers(), expiryOf(), onTurn() (+43 more)

### Community 42 - "Community 42"
Cohesion: 0.08
Nodes (47): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+39 more)

### Community 43 - "Community 43"
Cohesion: 0.08
Nodes (41): SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), buildUpdateRecord(), emptyRecord(), keysOf(), writableRecord() (+33 more)

### Community 44 - "Community 44"
Cohesion: 0.08
Nodes (38): sacOfDive(), BottomTimeQuery, DEFAULT_GF_HIGH, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033 (+30 more)

### Community 45 - "Community 45"
Cohesion: 0.07
Nodes (42): Leave, Series, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+34 more)

### Community 46 - "Community 46"
Cohesion: 0.07
Nodes (31): ADR-0036, headers, ADR-0031, headers, ADR-0043, ADR-0016, ADR-0063, ADR-0051 (+23 more)

### Community 47 - "Community 47"
Cohesion: 0.10
Nodes (39): centreQuery(), centresQuery(), CentreView, sitesQuery(), SiteView, AddSiteDialog(), CentrePage(), CentresPage() (+31 more)

### Community 48 - "Community 48"
Cohesion: 0.06
Nodes (43): LEAD_PLACEMENTS, SUIT_TYPES, SUIT_UNDERGARMENTS, WEIGHTING_FELT, ProfileSummary, round1(), Series, summariseProfile() (+35 more)

### Community 49 - "Community 49"
Cohesion: 0.07
Nodes (41): AiAccessScope, diversOf(), SiteRow, Cursor, cursorAt(), DateOnly(), Detail, diversAsked() (+33 more)

### Community 50 - "Community 50"
Cohesion: 0.09
Nodes (38): samplesQuery(), ToolsPlan, FindingLane(), useAssessment(), DepthProfile(), NO_POINTS, SurfaceBreakLane(), ADR-0036 (+30 more)

### Community 51 - "Community 51"
Cohesion: 0.06
Nodes (28): diveLease, pushRun, ExportingProvider, DiveView, RevisionView, ADR-0015, ADR-0025, ADR-0048 (+20 more)

### Community 52 - "Community 52"
Cohesion: 0.05
Nodes (41): headers, ADR-0035, areaOf(), areas, capture(), findings, headers, ADR-0020 (+33 more)

### Community 53 - "Community 53"
Cohesion: 0.07
Nodes (35): yauzl@^3.4.0, ImportErrorCode, ImportFound, ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS, eachFile() (+27 more)

### Community 54 - "Community 54"
Cohesion: 0.06
Nodes (37): ADR-0025, WaterMismatch, waterMismatch(), computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit() (+29 more)

### Community 55 - "Community 55"
Cohesion: 0.07
Nodes (36): device, diverBodyWeight, participant, DiverError, ADR-0016, ADR-0053, ACCOUNT_PATTERN, accountKey() (+28 more)

### Community 56 - "Community 56"
Cohesion: 0.06
Nodes (31): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CertificationError, CertificationExpiry (+23 more)

### Community 57 - "Community 57"
Cohesion: 0.05
Nodes (41): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+33 more)

### Community 58 - "Community 58"
Cohesion: 0.07
Nodes (33): sampleCount(), SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject() (+25 more)

### Community 59 - "Community 59"
Cohesion: 0.08
Nodes (37): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+29 more)

### Community 60 - "Community 60"
Cohesion: 0.08
Nodes (35): @modelcontextprotocol/server@^2.3.1, aiAccessLog, aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, AiAccessService, KeyRow (+27 more)

### Community 61 - "Community 61"
Cohesion: 0.06
Nodes (32): DiverCertifications, replyDiverError, STATUS, CertificationSummary, DiverDetailsView, DiverDetailsViewSchema, DiverSource, DiverView (+24 more)

### Community 62 - "Community 62"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 63 - "Community 63"
Cohesion: 0.10
Nodes (35): Answer, massFromDisplay(), massIn(), Dive, draftOfLead(), draftOfWeighting(), emptyLead(), hasThickness() (+27 more)

### Community 64 - "Community 64"
Cohesion: 0.08
Nodes (32): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+24 more)

### Community 65 - "Community 65"
Cohesion: 0.08
Nodes (32): PushLeftOut, PushRow, ADR-0024, Choice, Capabilities, isExporting(), referenceOf(), prepared() (+24 more)

### Community 66 - "Community 66"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 67 - "Community 67"
Cohesion: 0.07
Nodes (27): Tx, CandidateError, CandidateRow, createCandidateService(), lockMine(), RecordingRow, ADR-0016, ADR-0038 (+19 more)

### Community 68 - "Community 68"
Cohesion: 0.12
Nodes (35): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+27 more)

### Community 69 - "Community 69"
Cohesion: 0.12
Nodes (32): useCylinderProblem(), DiveEditForm(), Draft, draftOf(), FIELD_OF, localStart(), nanToNull(), round1() (+24 more)

### Community 70 - "Community 70"
Cohesion: 0.13
Nodes (35): kindOfMode(), deviceDiver(), closeFields(), entryOf(), openFields(), currentRemote(), baseDiveOf(), providerChangesOf() (+27 more)

### Community 71 - "Community 71"
Cohesion: 0.08
Nodes (17): HINT_CHANNEL, parseHint(), createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener() (+9 more)

### Community 72 - "Community 72"
Cohesion: 0.09
Nodes (33): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+25 more)

### Community 73 - "Community 73"
Cohesion: 0.06
Nodes (34): build, depends_on, environment, image, ports, restart, volumes, condition (+26 more)

### Community 74 - "Community 74"
Cohesion: 0.06
Nodes (34): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+26 more)

### Community 75 - "Community 75"
Cohesion: 0.08
Nodes (31): fit-file-parser@^6.1.2, geo-tz@8.1.9, devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1 (+23 more)

### Community 76 - "Community 76"
Cohesion: 0.11
Nodes (27): diveWith(), ADR-0028, ADR-0043, forget(), headers, ADR-0043, headers, Site (+19 more)

### Community 77 - "Community 77"
Cohesion: 0.07
Nodes (25): account, accountRelations, apikey, rateLimit, session, sessionRelations, ADR-0011, ADR-0035 (+17 more)

### Community 78 - "Community 78"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 79 - "Community 79"
Cohesion: 0.09
Nodes (27): ENGINE_VERSION, EVIDENCE, Evidence, FindingValues, RULE_IDS, RuleId, RULES, SEVERITIES (+19 more)

### Community 80 - "Community 80"
Cohesion: 0.09
Nodes (27): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+19 more)

### Community 81 - "Community 81"
Cohesion: 0.09
Nodes (25): headers, ADR-0030, ADR-0040, conflictForLena(), diveWithoutRecording(), editInSsi(), externalDiver(), importLena() (+17 more)

### Community 82 - "Community 82"
Cohesion: 0.10
Nodes (25): Auth, CENTRE_SOURCES, CentreService, CentreView, CentreViewSchema, Row, Source, toCentreView() (+17 more)

### Community 83 - "Community 83"
Cohesion: 0.14
Nodes (26): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+18 more)

### Community 84 - "Community 84"
Cohesion: 0.11
Nodes (20): SsiClient, ClearChecks, NumberChecks, TwowayChecks, apneaDive(), DATASET_KEYS, datasetSummary(), findIn() (+12 more)

### Community 85 - "Community 85"
Cohesion: 0.09
Nodes (27): devDependencies, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2, @testing-library/react@^16.3.3 (+19 more)

### Community 86 - "Community 86"
Cohesion: 0.07
Nodes (27): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0, graphile-worker@^0.18.0 (+19 more)

### Community 87 - "Community 87"
Cohesion: 0.09
Nodes (22): Cylinder, DiveView, RevisionView, ADR-0045, upload(), Candidate, Check, Outcome (+14 more)

### Community 88 - "Community 88"
Cohesion: 0.18
Nodes (24): assessmentQuery(), AssessmentView, AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState, FixedNote(), rowId() (+16 more)

### Community 89 - "Community 89"
Cohesion: 0.13
Nodes (23): durationFormat(), DurationFormatter, formatDepth(), formatDuration(), formatGasRate(), formatMass(), formatPressure(), formatPressureRate() (+15 more)

### Community 90 - "Community 90"
Cohesion: 0.16
Nodes (24): DiveOrigin, REFERENCE_PREFIX, SsiLogbook, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef() (+16 more)

### Community 91 - "Community 91"
Cohesion: 0.11
Nodes (20): diveRow(), rowOf(), ADR-0026, ADR-0048, clearQueue(), deletableDive(), diveRows(), fetchState() (+12 more)

### Community 92 - "Community 92"
Cohesion: 0.14
Nodes (23): AscentBand, ascentBandsOf(), assessProfile(), ceiling(), ppo2(), Profile, PROFILE_RULES, ProfileInput (+15 more)

### Community 93 - "Community 93"
Cohesion: 0.12
Nodes (19): createFileFormats(), FileFormat, looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf() (+11 more)

### Community 94 - "Community 94"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 95 - "Community 95"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 96 - "Community 96"
Cohesion: 0.14
Nodes (24): audienceOfConnection(), audienceOfDive(), audienceOfDiver(), emitHint(), Hint, HINT_KINDS, HintAudience, HintKind (+16 more)

### Community 97 - "Community 97"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 98 - "Community 98"
Cohesion: 0.11
Nodes (16): ndl(), ProfileAssessment, Finding, LIMITS, dayNumber(), deepDays(), noFlyAfter(), SERIES_RULES (+8 more)

### Community 99 - "Community 99"
Cohesion: 0.12
Nodes (18): CERTIFICATION_IMAGE_TASK, EXPIRE_WAITING_IMPORTS_TASK, PROCESS_IMPORT_TASK, SCHEDULED_FETCH_TASK, PUSH_RUN_STEP_TASK, WAITING_PUSHES_TASK, AUTOMATION_TICK_TASK, IMPORT_DIVE_SITES_TASK (+10 more)

### Community 100 - "Community 100"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 101 - "Community 101"
Cohesion: 0.13
Nodes (21): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+13 more)

### Community 102 - "Community 102"
Cohesion: 0.12
Nodes (15): TAGS, killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023 (+7 more)

### Community 103 - "Community 103"
Cohesion: 0.14
Nodes (16): providerGoneDive, compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051, candidatesOf() (+8 more)

### Community 104 - "Community 104"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 105 - "Community 105"
Cohesion: 0.15
Nodes (16): failedTitlesSince(), git(), eachSpec(), failedTestsFromReport(), lastStatuses(), localTime(), passedToRecord(), reportedTests() (+8 more)

### Community 106 - "Community 106"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 107 - "Community 107"
Cohesion: 0.18
Nodes (18): hasTolerance(), isEmpty(), near(), planSend(), sameAtProvider(), sameCylinder(), SendPlan, text() (+10 more)

### Community 108 - "Community 108"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 109 - "Community 109"
Cohesion: 0.11
Nodes (19): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, @internationalized/date@^3.12.4, lucide-react@^1.51.0, react@^19.3.0 (+11 more)

### Community 110 - "Community 110"
Cohesion: 0.16
Nodes (16): inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), placeLocalTime(), Position, ADR-0030, wallClockMs() (+8 more)

### Community 111 - "Community 111"
Cohesion: 0.14
Nodes (17): defineTool(), Id(), Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), HINT (+9 more)

### Community 112 - "Community 112"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 113 - "Community 113"
Cohesion: 0.11
Nodes (14): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+6 more)

### Community 114 - "Community 114"
Cohesion: 0.12
Nodes (15): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+7 more)

### Community 115 - "Community 115"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 116 - "Community 116"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 117 - "Community 117"
Cohesion: 0.13
Nodes (10): EGYPT, ExternalId, HOUSE_REEF, Method, Position, Revision, Site, SiteImport (+2 more)

### Community 118 - "Community 118"
Cohesion: 0.18
Nodes (10): ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, conflictAtSsi(), editAtSsi(), editElsewhere() (+2 more)

### Community 119 - "Community 119"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 120 - "Community 120"
Cohesion: 0.20
Nodes (10): DIVE_KINDS, ImportView, ImportViewSchema, toImportView(), ADR-0030, ImportRouteDeps, ADR-0044, ADR-0056 (+2 more)

### Community 121 - "Community 121"
Cohesion: 0.24
Nodes (13): asNumber(), asText(), compareReadBack(), datasetLength(), describe(), isObject(), keyDifferences(), pause() (+5 more)

### Community 122 - "Community 122"
Cohesion: 0.16
Nodes (8): i18next@^26.4.2, CustomTypeOptions, i18next, initI18n(), resources, ADR-0014, ADR-0014, i18next

### Community 123 - "Community 123"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 124 - "Community 124"
Cohesion: 0.15
Nodes (9): Sac, SAC_MISSING, SacMissing, ADR-0033, ADR-0045, Cylinder, dive, ADR-0033 (+1 more)

### Community 125 - "Community 125"
Cohesion: 0.21
Nodes (9): createFitAdapter(), adapter, crossCheck(), decodeOfficial(), privateDir, ownersParts(), parse(), toPart() (+1 more)

### Community 126 - "Community 126"
Cohesion: 0.17
Nodes (10): Dive, HOUSE_REEF, LIGHTHOUSE, Method, Page, Position, Revision, Site (+2 more)

### Community 127 - "Community 127"
Cohesion: 0.26
Nodes (7): DiveSummary, Profile, SKETCH_HEIGHT, SKETCH_WIDTH, sketchPaths(), ADR-0041, ADR-0041

### Community 128 - "Community 128"
Cohesion: 0.25
Nodes (11): ascentRate(), descentRate(), judgedParts(), lastMetres(), safetyStop(), stopIn(), stopStability(), worstOf() (+3 more)

### Community 129 - "Community 129"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 130 - "Community 130"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

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
Cohesion: 0.33
Nodes (6): ADR-0023, ADR-0053, ADR-0066, MODULE_IMPORTS, moduleRules, target()

### Community 141 - "Community 141"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 142 - "Community 142"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 143 - "Community 143"
Cohesion: 0.33
Nodes (3): ApiClient, createApiClient(), ADR-0004

### Community 144 - "Community 144"
Cohesion: 0.40
Nodes (4): offer, osm, ssi, wikidata

### Community 146 - "Community 146"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 147 - "Community 147"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

## Knowledge Gaps
- **1026 isolated node(s):** `ADR-0038`, `ADR-0063`, `ADR-0031`, `ADR-0016`, `ADR-0026` (+1021 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 2537 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **4 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 3` to `Community 1`, `Community 10`, `Community 11`, `Community 12`, `Community 14`, `Community 15`, `Community 16`, `Community 17`, `Community 18`, `Community 19`, `Community 20`, `Community 21`, `Community 22`, `Community 23`, `Community 25`, `Community 26`, `Community 28`, `Community 30`, `Community 32`, `Community 37`, `Community 39`, `Community 44`, `Community 49`, `Community 51`, `Community 53`, `Community 55`, `Community 56`, `Community 60`, `Community 64`, `Community 65`, `Community 67`, `Community 72`, `Community 75`, `Community 77`, `Community 86`, `Community 96`, `Community 103`, `Community 110`?**
  _High betweenness centrality (0.034) - this node is a cross-community bridge._
- **What connects `ADR-0038`, `ADR-0063`, `ADR-0031` to the rest of the system?**
  _1026 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.035573400958016345 - nodes in this community are weakly interconnected._
- **Why does `react` connect `Community 13` to `Community 0`, `Community 2`, `Community 34`, `Community 4`, `Community 69`, `Community 5`, `Community 7`, `Community 101`, `Community 9`, `Community 8`, `Community 109`, `Community 47`, `Community 50`, `Community 85`, `Community 88`, `Community 63`, `Community 127`?**
  _High betweenness centrality (0.020) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.033983966538863715 - nodes in this community are weakly interconnected._
- **Why does `unwrap()` connect `Community 2` to `Community 0`, `Community 34`, `Community 4`, `Community 5`, `Community 69`, `Community 7`, `Community 8`, `Community 9`, `Community 40`, `Community 47`, `Community 50`, `Community 88`?**
  _High betweenness centrality (0.017) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.06269534840963413 - nodes in this community are weakly interconnected._