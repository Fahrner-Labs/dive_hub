# Graph Report - dive_hub  (2026-11-09)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 6683 nodes · 20820 edges · 172 communities (169 shown, 3 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 205 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `be198aaa`
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

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 193 edges
2. `Button()` - 189 edges
3. `unwrap()` - 188 edges
4. `useErrorText()` - 158 edges
5. `useDisplay()` - 151 edges
6. `Muted()` - 129 edges
7. `drizzle-orm` - 124 edges
8. `announce()` - 102 edges
9. `createTestDatabase()` - 90 edges
10. `TestDatabase` - 89 edges

## Surprising Connections (you probably didn't know these)
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `askedAt()` --calls--> `DiveError`  [EXTRACTED]
  apps/server/src/dives/continuation/append-service.ts → apps/server/src/dives/dive-service.ts
- `Placement` --references--> `Actor`  [EXTRACTED]
  apps/server/src/imports/placement.ts → apps/server/src/revisions/revisions.ts

## Import Cycles
- None detected.

## Communities (172 total, 3 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.05
Nodes (183): ChangePassword(), DisplaySettings(), Sessions(), useDescribeAgent(), AdminSettings(), DeleteUserDialog(), Invitations(), Invite() (+175 more)

### Community 1 - "Community 1"
Cohesion: 0.04
Nodes (124): user, diver, diverExternalId, revision, listAccountOf(), remember(), createSsiClient(), ADR-0013 (+116 more)

### Community 2 - "Community 2"
Cohesion: 0.03
Nodes (152): bodyWeightsQuery(), bottomTimeRule(), ConflictFieldView, cylinderCatalogueQuery(), defined(), leadEstimateQuery(), toolsBottomTimeQuery(), toolsGasQuery() (+144 more)

### Community 3 - "Community 3"
Cohesion: 0.04
Nodes (129): api, ApiError, centreQuery(), centresQuery(), CentreView, DiverDetailsView, diverSearchQuery(), DiverView (+121 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (126): SecuritySettings(), devicesQuery(), DeviceView, divesQuery(), equipmentDueQuery(), EquipmentDueView, importsQuery(), ImportView (+118 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (123): continuationAppend, LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, sampleSeries, renumber(), renumberStatement(), ADR-0048 (+115 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (111): CentreService, CentreRouteDeps, UTC_OFFSET_SOURCES, DiverService, downsampleMinMax(), Sac, SAC_MISSING, SacMissing (+103 more)

### Community 7 - "Community 7"
Cohesion: 0.04
Nodes (103): diveSite, providerCylinderOf(), dataIdsOf(), hubSideOf(), settleSiteVisibility(), ADR-0030, ADR-0045, ADR-0053 (+95 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (101): diveListPages, diveNeighboursQuery(), DiveValues, DiveView, NeighboursView, OverridableField, providerStatusQuery(), PushView (+93 more)

### Community 9 - "Community 9"
Cohesion: 0.04
Nodes (81): adminRole, AI_ACCESS_NAME_MAX, AI_ACCESS_PREFIX, AI_ACCESS_RATE, Auth, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, ADR-0011 (+73 more)

### Community 10 - "Community 10"
Cohesion: 0.04
Nodes (68): connection, diveLease, importJob, providerConflict, push, pushRun, waitingPush, kept() (+60 more)

### Community 11 - "Community 11"
Cohesion: 0.05
Nodes (85): LogbookPage, PAGE_SIZE, ProblemCode, pushRunActive(), pushRunBody(), PushRunChoices, PushRunDetail, PushRunPreview (+77 more)

### Community 12 - "Community 12"
Cohesion: 0.05
Nodes (83): allButDive(), continuationCandidateQuery(), ContinuationCandidateView, deletedDivesQuery(), DeletedDiveView, diveProvidersQuery(), diveQuery(), LogbookCheckView (+75 more)

### Community 13 - "Community 13"
Cohesion: 0.05
Nodes (74): replyCertificationError, STATUS, CertificationService, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams (+66 more)

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (80): AiAccessLogEntry, AiAccessView, AnyLogbookCheck, BuddyView, DETAILS, DiveGearItemView, DiveGearView, DiveImportMode (+72 more)

### Community 15 - "Community 15"
Cohesion: 0.05
Nodes (81): ComputerChoice, DiveImportMode, DiveKind, providerSiteData, mergeProviderCylinder(), participantsOf(), siteRef(), columnsOf() (+73 more)

### Community 16 - "Community 16"
Cohesion: 0.04
Nodes (72): PARTICIPANT_ROLES, ADR-0053, ADR-0056, WaitingPushViewSchema, ErrorAnswer, ProblemSchema, providerProblem(), storedProblemCode() (+64 more)

### Community 17 - "Community 17"
Cohesion: 0.03
Nodes (64): recordingEvent, Assessment, FindingView, Row, ADR-0036, upload(), Check, Deleted (+56 more)

### Community 18 - "Community 18"
Cohesion: 0.06
Nodes (73): candidatesQuery(), CandidateView, ConflictChoices, ConflictEntryView, ConflictsOfConnection, DiveCheckView, diversQuery(), GoneDivesOfConnection (+65 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (66): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+58 more)

### Community 20 - "Community 20"
Cohesion: 0.05
Nodes (70): Queryable, equipmentItem, equipmentUse, serviceRecord, serviceRecordSchedule, serviceSchedule, managementOf(), ScheduleDue (+62 more)

### Community 21 - "Community 21"
Cohesion: 0.03
Nodes (74): actorType, aiAccessOutcome, candidateResolution, centreSource, certificationImageState, certificationVerification, connectionState, cylinderMaterial (+66 more)

### Community 22 - "Community 22"
Cohesion: 0.04
Nodes (68): pushRunItem, PushRunLeftOut, DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN, NO_RECORDING (+60 more)

### Community 23 - "Community 23"
Cohesion: 0.06
Nodes (64): logbookCheckAnswer, logbookCheckDiveAnswer, keepApart(), keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration(), CheckedDive (+56 more)

### Community 24 - "Community 24"
Cohesion: 0.05
Nodes (57): computerEventOf(), ADR-0036, date(), FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser() (+49 more)

### Community 25 - "Community 25"
Cohesion: 0.04
Nodes (60): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, ADR-0025, WaterMismatch, waterMismatch() (+52 more)

### Community 26 - "Community 26"
Cohesion: 0.07
Nodes (42): cylinder, dive, duplicateCandidate, original, recording, ImportService, createLocalBlobStore(), StoredBlob (+34 more)

### Community 27 - "Community 27"
Cohesion: 0.04
Nodes (51): graphile-worker@^0.18.0, createServer(), createAuth(), loadAuthSecret(), workerDeps(), Config, loadConfig(), ADR-0011 (+43 more)

### Community 28 - "Community 28"
Cohesion: 0.06
Nodes (50): ImportArea, errorMessage(), looksLikeZip(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson() (+42 more)

### Community 29 - "Community 29"
Cohesion: 0.07
Nodes (53): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ambientPressureBar(), bestMixPercent() (+45 more)

### Community 30 - "Community 30"
Cohesion: 0.07
Nodes (60): browserFailed(), AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), clearFailed(), clearPassed() (+52 more)

### Community 31 - "Community 31"
Cohesion: 0.06
Nodes (51): accountListEntry, providerLease, tidyName(), DiverDetails, Caller, foldName(), nameDiffers(), Buddy (+43 more)

### Community 32 - "Community 32"
Cohesion: 0.06
Nodes (52): Delivered, DIVE_FIELDS, ProviderError, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord() (+44 more)

### Community 33 - "Community 33"
Cohesion: 0.11
Nodes (50): buildApp(), AssessmentService, AssessmentRouteDeps, assessmentRoutes(), AssessmentViewSchema, FindingView, Rule, STATUS (+42 more)

### Community 34 - "Community 34"
Cohesion: 0.05
Nodes (44): AuthOptions, createServices(), ServiceParts, Services, ADR-0027, ADR-0030, ADR-0061, ADR-0063 (+36 more)

### Community 35 - "Community 35"
Cohesion: 0.06
Nodes (36): createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities(), providerError() (+28 more)

### Community 36 - "Community 36"
Cohesion: 0.09
Nodes (49): withBuddies(), APP_KEYS, appRow(), BuddySaved, byId(), changedKeys(), deleteTestEntries(), entryNamed() (+41 more)

### Community 37 - "Community 37"
Cohesion: 0.04
Nodes (44): DiveView, RevisionView, ADR-0015, ADR-0025, ADR-0048, upload(), DiveView, LeadEntry (+36 more)

### Community 38 - "Community 38"
Cohesion: 0.05
Nodes (48): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+40 more)

### Community 39 - "Community 39"
Cohesion: 0.05
Nodes (39): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CertificationError, CertificationExpiry (+31 more)

### Community 40 - "Community 40"
Cohesion: 0.10
Nodes (48): waitingOf(), open(), createConnectionService(), checkAutomation(), checkFollowNumbers(), expiryOf(), onTurn(), ownConnection() (+40 more)

### Community 41 - "Community 41"
Cohesion: 0.08
Nodes (47): ConnectionRow, WaitingPushOptions, audienceOfConnection(), PushRow, ADR-0024, blocking(), clearIfUnchanged(), decide() (+39 more)

### Community 42 - "Community 42"
Cohesion: 0.07
Nodes (45): DUE_STATES, diversOf(), SiteRow, Cursor, cursorAt(), DateOnly(), defineTool(), Detail (+37 more)

### Community 43 - "Community 43"
Cohesion: 0.05
Nodes (36): diveSiteExternalId, ImportedField, SiteSourceAdapter, SourceSite, ADR-0021, ADR-0025, call(), createSite() (+28 more)

### Community 44 - "Community 44"
Cohesion: 0.06
Nodes (45): ADR-0038, ADR-0048, globalSetup(), ADR-0023, AiAccess, AssessedFinding, Connection, DatedDive (+37 more)

### Community 45 - "Community 45"
Cohesion: 0.08
Nodes (41): Leave, asBool(), DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+33 more)

### Community 46 - "Community 46"
Cohesion: 0.06
Nodes (42): @modelcontextprotocol/server@^2.3.1, aiAccessLog, aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, AiAccessScope, AiAccessService (+34 more)

### Community 47 - "Community 47"
Cohesion: 0.05
Nodes (31): Direction, DiveExport, DiveListing, FindBy, Operation, OutgoingDive, ProviderDive, ProviderErrorReason (+23 more)

### Community 48 - "Community 48"
Cohesion: 0.08
Nodes (35): SsiClient, buildCreateRecord(), buildDeleteRecord(), DiveForSsi, emptyRecord(), writableRecord(), describe(), isObject() (+27 more)

### Community 49 - "Community 49"
Cohesion: 0.07
Nodes (37): countryCodes(), countryName(), countryOptions(), formatDistance(), Hemispheres, NOT_COUNTRIES, Position, ADR-0020 (+29 more)

### Community 50 - "Community 50"
Cohesion: 0.07
Nodes (32): diverManagement, managedDiverIds(), managesDiveDiver(), ADR-0016, CandidateError, CandidateRow, createCandidateService(), lockMine() (+24 more)

### Community 51 - "Community 51"
Cohesion: 0.09
Nodes (35): BottomTimeQuery, DEFAULT_GF_HIGH, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033, DEFAULT_PPO2 (+27 more)

### Community 52 - "Community 52"
Cohesion: 0.12
Nodes (40): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+32 more)

### Community 53 - "Community 53"
Cohesion: 0.08
Nodes (38): ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, Context, ExistingSite (+30 more)

### Community 54 - "Community 54"
Cohesion: 0.07
Nodes (35): ImportErrorCode, ImportFound, importOriginal, ImportOutcome, ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS (+27 more)

### Community 55 - "Community 55"
Cohesion: 0.06
Nodes (36): CYLINDER_MATERIALS, SUIT_TYPES, SUIT_UNDERGARMENTS, DiverCertifications, replyDiverError, STATUS, CertificationSummary, DiverDetailsView (+28 more)

### Community 56 - "Community 56"
Cohesion: 0.05
Nodes (39): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+31 more)

### Community 57 - "Community 57"
Cohesion: 0.06
Nodes (34): externalDiver(), mergeExternalDiver(), participations(), AutomationSettings, Credentials, FETCH_INTERVALS, FetchInterval, purpose() (+26 more)

### Community 58 - "Community 58"
Cohesion: 0.07
Nodes (34): headers, ADR-0031, headers, ADR-0030, ADR-0040, conflictForLena(), connectGarmin(), diveWithoutRecording() (+26 more)

### Community 59 - "Community 59"
Cohesion: 0.08
Nodes (40): CylinderMaterial, BOTTOM_TIME_RULES, cylinderOf(), cylinderText(), diverOf(), diverText(), finite(), kept (+32 more)

### Community 60 - "Community 60"
Cohesion: 0.05
Nodes (41): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+33 more)

### Community 61 - "Community 61"
Cohesion: 0.07
Nodes (37): Change(), Changes(), historyRoute, Made(), REVISION_CAUSES, PositionSchema, AdoptBody, Country (+29 more)

### Community 62 - "Community 62"
Cohesion: 0.07
Nodes (32): SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject(), MAX_CARD_IMAGE_BYTES (+24 more)

### Community 63 - "Community 63"
Cohesion: 0.07
Nodes (36): args, browser, browserListStep(), browserSteps(), byHand(), CACHE, code, dry (+28 more)

### Community 64 - "Community 64"
Cohesion: 0.11
Nodes (35): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+27 more)

### Community 65 - "Community 65"
Cohesion: 0.11
Nodes (35): massIn(), Dive, draftOfLead(), draftOfWeighting(), emptyLead(), fromLastDive(), hasThickness(), kg() (+27 more)

### Community 66 - "Community 66"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 67 - "Community 67"
Cohesion: 0.08
Nodes (35): LEAD_PLACEMENTS, WEIGHTING_FELT, ProfileSummary, seriesOf(), minutes(), round(), Channel, CHANNEL_UNITS (+27 more)

### Community 68 - "Community 68"
Cohesion: 0.06
Nodes (35): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+27 more)

### Community 69 - "Community 69"
Cohesion: 0.08
Nodes (17): HINT_CHANNEL, parseHint(), createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener() (+9 more)

### Community 70 - "Community 70"
Cohesion: 0.09
Nodes (33): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+25 more)

### Community 71 - "Community 71"
Cohesion: 0.07
Nodes (25): headers, ADR-0043, ADR-0016, diveRow(), rowOf(), ADR-0026, ADR-0048, created (+17 more)

### Community 72 - "Community 72"
Cohesion: 0.10
Nodes (20): auth, out, services, createSecretBox(), ADR-0024, BlobStore, services(), services() (+12 more)

### Community 73 - "Community 73"
Cohesion: 0.08
Nodes (31): devDependencies, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2, @testing-library/react@^16.3.3 (+23 more)

### Community 74 - "Community 74"
Cohesion: 0.06
Nodes (33): depends_on, environment, image, ports, restart, volumes, condition, environment (+25 more)

### Community 75 - "Community 75"
Cohesion: 0.16
Nodes (32): createSsiTransport(), acceptedFormat(), Answer, dateTime(), deleteAndCheck(), describe(), findNewId(), Format (+24 more)

### Community 76 - "Community 76"
Cohesion: 0.10
Nodes (25): ADR-0051, Status, ADR-0056, clearPushRuns(), clearQueue(), conflictAtSsi(), connectSsi(), deletableDive() (+17 more)

### Community 77 - "Community 77"
Cohesion: 0.13
Nodes (26): bar(), celsius(), contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf(), firstOf() (+18 more)

### Community 78 - "Community 78"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 79 - "Community 79"
Cohesion: 0.07
Nodes (21): CustomTypeOptions, i18next, initI18n(), resources, ADR-0014, StrayOverride, ADR-0014, english (+13 more)

### Community 80 - "Community 80"
Cohesion: 0.08
Nodes (26): CylinderMaterial, ProviderImportPlan, SuitType, SuitUndergarment, WeightingFelt, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031 (+18 more)

### Community 81 - "Community 81"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 82 - "Community 82"
Cohesion: 0.14
Nodes (26): DiveOrigin, REFERENCE_PREFIX, Series, SsiLogbook, aboveZero(), cylinderOf(), deviceOf(), deviceOfRef() (+18 more)

### Community 83 - "Community 83"
Cohesion: 0.10
Nodes (26): applyHints(), LiveArrivals, Page, useLiveArrivals(), bump(), changedListeners, ChannelMessage, healthListeners (+18 more)

### Community 84 - "Community 84"
Cohesion: 0.11
Nodes (24): EVIDENCE, Evidence, FindingValues, RULE_IDS, RuleId, RULES, SEVERITIES, SourceId (+16 more)

### Community 85 - "Community 85"
Cohesion: 0.11
Nodes (22): RecordingSummary, UtcOffsetSource, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput (+14 more)

### Community 86 - "Community 86"
Cohesion: 0.10
Nodes (27): overlapWindow(), ensureItemForDevice(), trimName(), recordingKeySource(), deletedEarlier(), fullerCopyHere(), IsManaged, knownRecording() (+19 more)

### Community 87 - "Community 87"
Cohesion: 0.09
Nodes (22): Cylinder, DiveView, RevisionView, ADR-0045, upload(), Candidate, Check, Outcome (+14 more)

### Community 88 - "Community 88"
Cohesion: 0.11
Nodes (17): ADR-0036, ADR-0063, ADR-0062, ADR-0042, ADR-0047, ADR-0021, assessedDive(), cancelWaitingImports() (+9 more)

### Community 89 - "Community 89"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 90 - "Community 90"
Cohesion: 0.09
Nodes (20): Tx, AppendUndo, audienceOfDive(), ADR-0020, ADR-0056, ADR-0066, withRevisionHints(), ChangeListener (+12 more)

### Community 91 - "Community 91"
Cohesion: 0.14
Nodes (24): ProviderCylinder, ConflictView, Comparable, hasTolerance(), isEmpty(), near(), planSend(), sameAtProvider() (+16 more)

### Community 92 - "Community 92"
Cohesion: 0.13
Nodes (22): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DueInput, DueSchedule (+14 more)

### Community 93 - "Community 93"
Cohesion: 0.14
Nodes (21): diveWith(), ADR-0028, ADR-0043, forget(), headers, ADR-0043, headers, Site (+13 more)

### Community 94 - "Community 94"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 95 - "Community 95"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 96 - "Community 96"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 97 - "Community 97"
Cohesion: 0.11
Nodes (22): typescript@7.0.2, typescript@7.0.2, ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2 (+14 more)

### Community 98 - "Community 98"
Cohesion: 0.13
Nodes (22): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+14 more)

### Community 99 - "Community 99"
Cohesion: 0.12
Nodes (19): CERTIFICATION_IMAGE_TASK, EXPIRE_WAITING_IMPORTS_TASK, PROCESS_IMPORT_TASK, SCHEDULED_FETCH_TASK, PUSH_RUN_STEP_TASK, WAITING_PUSHES_TASK, waitingPushesKey(), AUTOMATION_TICK_TASK (+11 more)

### Community 100 - "Community 100"
Cohesion: 0.11
Nodes (15): TAGS, killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023 (+7 more)

### Community 101 - "Community 101"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 102 - "Community 102"
Cohesion: 0.14
Nodes (18): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+10 more)

### Community 103 - "Community 103"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 104 - "Community 104"
Cohesion: 0.11
Nodes (15): ndl(), ProfileAssessment, Finding, LIMITS, dayNumber(), deepDays(), noFlyAfter(), SERIES_RULES (+7 more)

### Community 105 - "Community 105"
Cohesion: 0.10
Nodes (19): account, accountRelations, apikey, rateLimit, session, sessionRelations, ADR-0011, ADR-0035 (+11 more)

### Community 106 - "Community 106"
Cohesion: 0.13
Nodes (17): providerGoneDive, compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051, candidatesOf() (+9 more)

### Community 107 - "Community 107"
Cohesion: 0.12
Nodes (19): siteImport, siteImportStatus, PROBLEM_CODES, SiteImportRouteDeps, StartBody, STATUS, ADR-0021, SiteImportError (+11 more)

### Community 108 - "Community 108"
Cohesion: 0.15
Nodes (18): Answer, CatalogueCylinder, CylinderInput, CylinderView, draftOfCylinder(), fromCatalogue(), fromItemLastUse(), fromLastDive() (+10 more)

### Community 109 - "Community 109"
Cohesion: 0.12
Nodes (18): CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043, CreateBody (+10 more)

### Community 110 - "Community 110"
Cohesion: 0.12
Nodes (16): ActionContext, FIND_BY, OPERATIONS, SignInInput, CASES, ContractHarness, declared(), ELSEWHERE (+8 more)

### Community 111 - "Community 111"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 112 - "Community 112"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 113 - "Community 113"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 114 - "Community 114"
Cohesion: 0.13
Nodes (16): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+8 more)

### Community 115 - "Community 115"
Cohesion: 0.11
Nodes (16): Choice, referenceOf(), Here, NumbersFor, PushValues, SendOptions, SendResult, signedOut() (+8 more)

### Community 116 - "Community 116"
Cohesion: 0.19
Nodes (18): asNumber(), asText(), compareReadBack(), apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks() (+10 more)

### Community 117 - "Community 117"
Cohesion: 0.14
Nodes (16): Id(), Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), HINT, Input (+8 more)

### Community 118 - "Community 118"
Cohesion: 0.14
Nodes (13): nearest(), perMinute(), Series, summarize(), ChannelSeries, RecordedGas, Tank, TiedCylinder (+5 more)

### Community 119 - "Community 119"
Cohesion: 0.12
Nodes (15): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+7 more)

### Community 120 - "Community 120"
Cohesion: 0.20
Nodes (12): failedTitlesSince(), git(), failedTestsFromReport(), localTime(), appendRun(), browserStats(), logPath(), main() (+4 more)

### Community 121 - "Community 121"
Cohesion: 0.16
Nodes (13): CIRCUIT, circuitFromFit, DECO_MODEL, decoModelFromFit, DIVE_ALERT, DIVE_MODE, diveModeFromFit, ADR-0015 (+5 more)

### Community 122 - "Community 122"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 123 - "Community 123"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 124 - "Community 124"
Cohesion: 0.22
Nodes (13): ParticipantRole, OutgoingParticipant, prepared(), fingerprintNow(), ADR-0024, ADR-0030, ADR-0053, forProvider() (+5 more)

### Community 125 - "Community 125"
Cohesion: 0.24
Nodes (12): SsiRecord, contextOf(), FakeSsi, findIn(), finishTwoway(), GAS, sendTwoway(), show() (+4 more)

### Community 126 - "Community 126"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 127 - "Community 127"
Cohesion: 0.15
Nodes (14): dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3, exports, name, private (+6 more)

### Community 128 - "Community 128"
Cohesion: 0.20
Nodes (10): DIVE_KINDS, ImportView, ImportViewSchema, toImportView(), ADR-0030, ImportRouteDeps, ADR-0044, ADR-0056 (+2 more)

### Community 129 - "Community 129"
Cohesion: 0.22
Nodes (11): fromOsm(), fromSsi(), fromWd(), HOUSE_REEF, osm(), site(), ssi(), ADR-0021 (+3 more)

### Community 130 - "Community 130"
Cohesion: 0.23
Nodes (9): DiveSummary, Profile, SKETCH_HEIGHT, SKETCH_WIDTH, sketchPaths(), sketchScale(), ADR-0041, ProfileSketch() (+1 more)

### Community 131 - "Community 131"
Cohesion: 0.16
Nodes (9): NetworkError, noAnswer, noAnswerError(), uploadFile(), thrown(), ADR-0014, ApiClient, createApiClient() (+1 more)

### Community 132 - "Community 132"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 133 - "Community 133"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 134 - "Community 134"
Cohesion: 0.17
Nodes (10): EstimateInput, AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031 (+2 more)

### Community 135 - "Community 135"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 136 - "Community 136"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 137 - "Community 137"
Cohesion: 0.18
Nodes (9): Buddy, KAI, MIA, NAMELESS, SAM, Status, ADR-0029, ADR-0043 (+1 more)

### Community 138 - "Community 138"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 139 - "Community 139"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 140 - "Community 140"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 141 - "Community 141"
Cohesion: 0.29
Nodes (9): DAY_MS, inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), placeLocalTime(), Position, ADR-0030 (+1 more)

### Community 142 - "Community 142"
Cohesion: 0.22
Nodes (8): wallClockMs(), AQABA, ATTERSEE, EILAT, HAUSREEF, local(), MID_ATLANTIC, ADR-0030

### Community 143 - "Community 143"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 144 - "Community 144"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 145 - "Community 145"
Cohesion: 0.20
Nodes (10): verify, major, minor, sha, tag, version, name, outputs (+2 more)

### Community 146 - "Community 146"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 148 - "Community 148"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 149 - "Community 149"
Cohesion: 0.22
Nodes (9): version, on, push, workflow_dispatch, tags, description, required, type (+1 more)

### Community 150 - "Community 150"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 151 - "Community 151"
Cohesion: 0.32
Nodes (6): round1(), Series, summariseProfile(), ADR-0035, s(), ADR-0035

### Community 152 - "Community 152"
Cohesion: 0.25
Nodes (7): createConflictService(), loadWaiting(), stillDiffering(), fieldsOf(), isExists(), shownFields(), stillWaits()

### Community 153 - "Community 153"
Cohesion: 0.25
Nodes (7): AIR, expectWithinReference(), GF100_FRESH, GF100_SALT, GF85_FRESH, ADR-0032, ADR-0046

### Community 154 - "Community 154"
Cohesion: 0.36
Nodes (6): headers, open(), ownId(), ownRow(), sectionButton(), ADR-0050

### Community 155 - "Community 155"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 156 - "Community 156"
Cohesion: 0.33
Nodes (4): Sketch, SKETCH_POINTS, ADR-0041, ADR-0041

### Community 157 - "Community 157"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 158 - "Community 158"
Cohesion: 0.33
Nodes (4): pollUntilDue(), ADR-0056, now, ADR-0056

### Community 159 - "Community 159"
Cohesion: 0.33
Nodes (6): ADR-0023, ADR-0053, ADR-0066, MODULE_IMPORTS, moduleRules, target()

### Community 160 - "Community 160"
Cohesion: 0.40
Nodes (5): stubDive(), ADR-0015, ADR-0023, ADR-0025, guardedRoute()

### Community 161 - "Community 161"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 162 - "Community 162"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 163 - "Community 163"
Cohesion: 0.40
Nodes (4): IMPORTABLE, splitImportable(), ADR-0037, ADR-0037

### Community 164 - "Community 164"
Cohesion: 0.40
Nodes (4): offer, osm, ssi, wikidata

### Community 165 - "Community 165"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 166 - "Community 166"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 167 - "Community 167"
Cohesion: 0.67
Nodes (3): planSketch(), shape(), ADR-0033

### Community 168 - "Community 168"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 169 - "Community 169"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

## Knowledge Gaps
- **1067 isolated node(s):** `ADR-0012`, `ADR-0013`, `ADR-0030`, `ADR-0035`, `ADR-0050` (+1062 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 2719 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **3 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 1` to `Community 5`, `Community 7`, `Community 9`, `Community 10`, `Community 137`, `Community 141`, `Community 13`, `Community 15`, `Community 16`, `Community 17`, `Community 19`, `Community 20`, `Community 21`, `Community 22`, `Community 23`, `Community 25`, `Community 26`, `Community 27`, `Community 31`, `Community 34`, `Community 35`, `Community 37`, `Community 38`, `Community 39`, `Community 41`, `Community 42`, `Community 43`, `Community 46`, `Community 50`, `Community 51`, `Community 54`, `Community 57`, `Community 70`, `Community 72`, `Community 86`, `Community 98`, `Community 105`, `Community 106`, `Community 115`?**
  _High betweenness centrality (0.041) - this node is a cross-community bridge._
- **What connects `ADR-0012`, `ADR-0013`, `ADR-0030` to the rest of the system?**
  _1067 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.04609965813736662 - nodes in this community are weakly interconnected._
- **Why does `typescript` connect `Community 97` to `Community 73`, `Community 38`, `Community 127`?**
  _High betweenness centrality (0.029) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.03929236499068901 - nodes in this community are weakly interconnected._
- **Why does `FakeGarmin` connect `Community 35` to `Community 1`, `Community 26`?**
  _High betweenness centrality (0.029) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.033136094674556214 - nodes in this community are weakly interconnected._