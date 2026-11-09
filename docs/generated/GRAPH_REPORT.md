# Graph Report - dive_hub  (2026-11-09)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 6685 nodes · 20821 edges · 172 communities (169 shown, 3 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 205 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `083864ce`
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
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `Removal` --references--> `ProviderServiceError`  [EXTRACTED]
  apps/server/src/providers/push/push-service.ts → apps/server/src/providers/registry.ts
- `SsiLogbookSite` --references--> `SiteWaterType`  [EXTRACTED]
  apps/server/src/providers/ssi/ssi-client.ts → apps/server/src/vocabulary.ts

## Import Cycles
- None detected.

## Communities (172 total, 3 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.03
Nodes (151): user, diver, diveSiteExternalId, revision, ADR-0013, upload(), ADR-0011, Sent (+143 more)

### Community 1 - "Community 1"
Cohesion: 0.05
Nodes (177): AdminSettings(), DeleteUserDialog(), Invitations(), Invite(), LinkToPassOn(), ProviderSiteData(), SiteDataPanel(), ADR-0012 (+169 more)

### Community 2 - "Community 2"
Cohesion: 0.04
Nodes (106): diverBodyWeight, diveSite, lead, LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, renumber(), renumberStatement() (+98 more)

### Community 3 - "Community 3"
Cohesion: 0.04
Nodes (109): diveNeighboursQuery(), diversQuery(), divesQuery(), ExternalIdView, NeighboursView, PAGE_SIZE, RecordingSummary, siteQuery() (+101 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (100): PARTICIPANT_ROLES, UTC_OFFSET_SOURCES, downsampleMinMax(), sacOfDive(), waterMismatch(), AppendDeps, AppendOutcome, AppendService (+92 more)

### Community 5 - "Community 5"
Cohesion: 0.04
Nodes (92): DisplaySettings(), importsQuery(), ImportView, isUnauthorized(), Me, setupQuery(), App(), CentrePage (+84 more)

### Community 6 - "Community 6"
Cohesion: 0.04
Nodes (90): connection, ConnectionRow, importJob, ImportOutcome, providerConflict, push, waitingPush, WaitingPushOptions (+82 more)

### Community 7 - "Community 7"
Cohesion: 0.02
Nodes (96): AiAccessLogEntry, AiAccessView, AnyLogbookCheck, bottomTimeRule(), buddiesForQuery(), buddiesQuery(), BuddyView, defined() (+88 more)

### Community 8 - "Community 8"
Cohesion: 0.04
Nodes (75): bodyWeightsQuery(), connectionsQuery(), LeadEstimate, BodyWeightDialog(), today(), ADR-0031, holdLeave(), mayLeave() (+67 more)

### Community 9 - "Community 9"
Cohesion: 0.02
Nodes (88): account, accountRelations, apikey, rateLimit, sessionRelations, ADR-0011, ADR-0035, userRelations (+80 more)

### Community 10 - "Community 10"
Cohesion: 0.04
Nodes (66): ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS, eachFile(), readEntry(), ADR-0044, walk() (+58 more)

### Community 11 - "Community 11"
Cohesion: 0.04
Nodes (82): ConflictChoices, ConflictEntryView, ConflictFieldView, ConflictsOfConnection, devicesQuery(), DeviceView, revisionsQuery(), RevisionView (+74 more)

### Community 12 - "Community 12"
Cohesion: 0.04
Nodes (69): AuthOptions, auth, hashPassword(), createServices(), Services, ADR-0027, ADR-0030, ADR-0061 (+61 more)

### Community 13 - "Community 13"
Cohesion: 0.03
Nodes (71): createServer(), createAuth(), loadAuthSecret(), CERTIFICATION_IMAGE_TASK, workerDeps(), Config, loadConfig(), ADR-0011 (+63 more)

### Community 14 - "Community 14"
Cohesion: 0.05
Nodes (78): allButDive(), continuationCandidateQuery(), ContinuationCandidateView, deletedDivesQuery(), DeletedDiveView, diveProvidersQuery(), diveQuery(), LogbookCheckView (+70 more)

### Community 15 - "Community 15"
Cohesion: 0.05
Nodes (74): CertificationService, CYLINDER_MATERIALS, DIVE_IMPORT_MODES, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053, OptionalText() (+66 more)

### Community 16 - "Community 16"
Cohesion: 0.05
Nodes (72): Queryable, equipmentItem, serviceRecord, serviceRecordSchedule, serviceSchedule, requireManagedDiver(), managementOf(), ADR-0016 (+64 more)

### Community 17 - "Community 17"
Cohesion: 0.04
Nodes (54): diveLease, providerLease, pushRun, kept(), OPENING_AFTER_MINUTES, ADR-0056, Clock, LEASE_MS (+46 more)

### Community 18 - "Community 18"
Cohesion: 0.07
Nodes (62): api, ApiError, centreQuery(), centresQuery(), CentreView, ConnectionView, diverDetailsQuery(), DiverDetailsView (+54 more)

### Community 19 - "Community 19"
Cohesion: 0.07
Nodes (69): candidatesQuery(), CandidateView, DiveCheckView, GoneDivesOfConnection, goneDivesQuery(), GoneDiveView, isDiveCheck(), isPairCheck() (+61 more)

### Community 20 - "Community 20"
Cohesion: 0.05
Nodes (61): adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, Auth, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, ADR-0011, ADR-0013 (+53 more)

### Community 21 - "Community 21"
Cohesion: 0.06
Nodes (66): logbookCheckAnswer, logbookCheckDiveAnswer, keepApart(), keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration(), CheckedDive (+58 more)

### Community 22 - "Community 22"
Cohesion: 0.03
Nodes (48): CertificationAccount, RemoteCertification, ADR-0050, ADR-0053, Direction, DiveExport, DiveListing, FIND_BY (+40 more)

### Community 23 - "Community 23"
Cohesion: 0.04
Nodes (50): recordingEvent, Assessment, FindingView, Row, ADR-0036, Candidate, Deleted, DiveView (+42 more)

### Community 24 - "Community 24"
Cohesion: 0.04
Nodes (57): createSsiClient(), Card, DEEP, OWD, Report, Summary, ADR-0050, WRECK (+49 more)

### Community 25 - "Community 25"
Cohesion: 0.07
Nodes (55): siteImport, dataIdsOf(), settleSiteVisibility(), keyLease(), outgoingSite(), findOrMake(), SiteForSend, siteForSend() (+47 more)

### Community 26 - "Community 26"
Cohesion: 0.05
Nodes (60): AiAccessScope, seesPositions(), VerifiedAccess, diversOf(), SiteRow, checkedByRunTool, createMcpServer(), INSTRUCTIONS (+52 more)

### Community 27 - "Community 27"
Cohesion: 0.09
Nodes (58): buildApp(), assessmentRoutes(), AI_ACCESS_NAME_MAX, SessionUser, authHandler(), authHeaders(), fastify, FastifyRequest (+50 more)

### Community 28 - "Community 28"
Cohesion: 0.06
Nodes (54): siteImportStatus, DIVE_ONLY_PATTERN, ADR-0053, ADR-0056, ErrorAnswer, PROBLEM_CODES, ProblemSchema, providerProblem() (+46 more)

### Community 29 - "Community 29"
Cohesion: 0.05
Nodes (56): continuationAppend, CylinderMaterial, sampleSeries, buildJoined(), partInput(), partsOfJoined(), rebuildJoined(), RecordingRow (+48 more)

### Community 30 - "Community 30"
Cohesion: 0.06
Nodes (46): SsiClient, SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), DiveForSsi, emptyRecord(), writableRecord() (+38 more)

### Community 31 - "Community 31"
Cohesion: 0.07
Nodes (47): errorMessage(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy(), ADR-0024 (+39 more)

### Community 32 - "Community 32"
Cohesion: 0.06
Nodes (55): SuitType, SuitUndergarment, WeightingFelt, Suit, WeightingFeedback, answer(), baseFrom(), cylindersAtReserve() (+47 more)

### Community 33 - "Community 33"
Cohesion: 0.06
Nodes (51): Delivered, DIVE_FIELDS, ProviderError, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord() (+43 more)

### Community 34 - "Community 34"
Cohesion: 0.07
Nodes (49): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+41 more)

### Community 35 - "Community 35"
Cohesion: 0.07
Nodes (55): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+47 more)

### Community 36 - "Community 36"
Cohesion: 0.05
Nodes (42): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+34 more)

### Community 37 - "Community 37"
Cohesion: 0.07
Nodes (38): cylinder, dive, duplicateCandidate, ImportErrorCode, ImportFound, importOriginal, original, recording (+30 more)

### Community 38 - "Community 38"
Cohesion: 0.06
Nodes (45): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, Conditions, GasMix (+37 more)

### Community 39 - "Community 39"
Cohesion: 0.07
Nodes (46): asBool(), DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining(), ssiDiveType() (+38 more)

### Community 40 - "Community 40"
Cohesion: 0.09
Nodes (45): accountListEntry, tidyName(), DiverDetails, Caller, foldName(), nameDiffers(), Buddy, createBuddyService() (+37 more)

### Community 41 - "Community 41"
Cohesion: 0.06
Nodes (43): date(), FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position(), positive() (+35 more)

### Community 42 - "Community 42"
Cohesion: 0.09
Nodes (49): ParticipantRole, managedDiverIds(), cylindersOf(), mergeProviderCylinder(), ProviderCylinder, providerCylinderOf(), participantsOf(), columnsOf() (+41 more)

### Community 43 - "Community 43"
Cohesion: 0.06
Nodes (47): orUndefined(), ADR-0025, ADR-0031, CylinderDraft, CylinderMaterial, BOTTOM_TIME_RULES, cylinderOf(), cylinderText() (+39 more)

### Community 44 - "Community 44"
Cohesion: 0.06
Nodes (43): PushLeftOut, close(), Choice, SendPlan, ImportedDive, isExporting(), Leave, referenceOf() (+35 more)

### Community 45 - "Community 45"
Cohesion: 0.05
Nodes (46): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, WaitingPushViewSchema, EquipmentService, RecordInput (+38 more)

### Community 46 - "Community 46"
Cohesion: 0.06
Nodes (45): ADR-0038, ADR-0048, globalSetup(), ADR-0023, AiAccess, AssessedFinding, Connection, DatedDive (+37 more)

### Community 47 - "Community 47"
Cohesion: 0.09
Nodes (43): cylinderCatalogueQuery(), CylinderFields(), MATERIALS, ADR-0034, ADR-0045, useCatalogueName(), useCylinderProblem(), DiveEditForm() (+35 more)

### Community 48 - "Community 48"
Cohesion: 0.11
Nodes (41): waitingOf(), open(), createConnectionService(), checkAutomation(), checkFollowNumbers(), expiryOf(), onTurn(), ownConnection() (+33 more)

### Community 49 - "Community 49"
Cohesion: 0.10
Nodes (36): samplesQuery(), FindingLane(), useAssessment(), DepthProfile(), NO_POINTS, SurfaceBreakLane(), ADR-0036, ADR-0063 (+28 more)

### Community 50 - "Community 50"
Cohesion: 0.07
Nodes (36): device, diverExternalId, participant, DiverError, ADR-0016, ADR-0053, ACCOUNT_PATTERN, accountKey() (+28 more)

### Community 51 - "Community 51"
Cohesion: 0.06
Nodes (36): SUIT_TYPES, SUIT_UNDERGARMENTS, DiverCertifications, replyDiverError, STATUS, DiverService, CertificationSummary, DiverDetailsView (+28 more)

### Community 52 - "Community 52"
Cohesion: 0.05
Nodes (39): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+31 more)

### Community 53 - "Community 53"
Cohesion: 0.09
Nodes (41): providersQuery(), pushRunActive(), pushRunBody(), PushRunChoices, PushRunDetail, PushRunPreview, pushRunPreviewQuery(), pushRunQuery() (+33 more)

### Community 54 - "Community 54"
Cohesion: 0.07
Nodes (34): sampleCount(), SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject() (+26 more)

### Community 55 - "Community 55"
Cohesion: 0.07
Nodes (34): headers, ADR-0031, headers, ADR-0030, ADR-0040, conflictForLena(), connectGarmin(), diveWithoutRecording() (+26 more)

### Community 56 - "Community 56"
Cohesion: 0.05
Nodes (41): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+33 more)

### Community 57 - "Community 57"
Cohesion: 0.13
Nodes (35): ChangePassword(), SecuritySettings(), Sessions(), useDescribeAgent(), authClient, DiverView, externalDiversQuery(), ExternalDiverView (+27 more)

### Community 58 - "Community 58"
Cohesion: 0.08
Nodes (37): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+29 more)

### Community 59 - "Community 59"
Cohesion: 0.06
Nodes (37): Change(), Changes(), historyRoute, Made(), REVISION_CAUSES, PositionSchema, AdoptBody, Country (+29 more)

### Community 60 - "Community 60"
Cohesion: 0.10
Nodes (30): equipmentDueQuery(), EquipmentDueView, EquipmentDueNotice(), ADR-0034, avatarTone(), initials(), dismissDue(), dismissedDueDigest() (+22 more)

### Community 61 - "Community 61"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 62 - "Community 62"
Cohesion: 0.06
Nodes (32): AssessmentService, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView(), ADR-0036 (+24 more)

### Community 63 - "Community 63"
Cohesion: 0.08
Nodes (32): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+24 more)

### Community 64 - "Community 64"
Cohesion: 0.07
Nodes (37): LEAD_PLACEMENTS, WEIGHTING_FELT, ProfileSummary, seriesOf(), minute(), minutes(), round(), startTimes() (+29 more)

### Community 65 - "Community 65"
Cohesion: 0.11
Nodes (37): ToolsPlan, ToolsPlanDiver, PlanDiver, PlanLevel, PlanStretch, replaceToolsAddress(), ToolsRule, depthFromDisplay() (+29 more)

### Community 66 - "Community 66"
Cohesion: 0.10
Nodes (33): Answer, massIn(), Dive, draftOfLead(), draftOfWeighting(), emptyLead(), hasThickness(), kg() (+25 more)

### Community 67 - "Community 67"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 68 - "Community 68"
Cohesion: 0.07
Nodes (27): Tx, CandidateError, CandidateRow, createCandidateService(), lockMine(), RecordingRow, ADR-0016, ADR-0038 (+19 more)

### Community 69 - "Community 69"
Cohesion: 0.10
Nodes (29): BottomTimeQuery, DEFAULT_GF_HIGH, diverLogbook(), Source, ADR-0032, ADR-0033, DEFAULT_PPO2, GasQuery (+21 more)

### Community 70 - "Community 70"
Cohesion: 0.07
Nodes (27): CertificationProviders, CERTIFICATION_IMAGE_ATTEMPTS, CertificationError, CertificationExpiry, CertificationInput, CertificationView, createCertificationService(), cleaned() (+19 more)

### Community 71 - "Community 71"
Cohesion: 0.08
Nodes (28): ServiceParts, ImportArea, ImportedValues, ADR-0021, ADR-0025, ADR-0053, PlannedCreate, PlannedExternalId (+20 more)

### Community 72 - "Community 72"
Cohesion: 0.13
Nodes (34): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+26 more)

### Community 73 - "Community 73"
Cohesion: 0.06
Nodes (35): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+27 more)

### Community 74 - "Community 74"
Cohesion: 0.09
Nodes (27): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+19 more)

### Community 75 - "Community 75"
Cohesion: 0.08
Nodes (17): HINT_CHANNEL, parseHint(), createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener() (+9 more)

### Community 76 - "Community 76"
Cohesion: 0.09
Nodes (33): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+25 more)

### Community 77 - "Community 77"
Cohesion: 0.07
Nodes (25): headers, ADR-0043, ADR-0016, diveRow(), rowOf(), ADR-0026, ADR-0048, created (+17 more)

### Community 78 - "Community 78"
Cohesion: 0.06
Nodes (33): depends_on, environment, image, ports, restart, volumes, condition, environment (+25 more)

### Community 79 - "Community 79"
Cohesion: 0.11
Nodes (29): SiteImportCounts, distanceM(), ExistingSite, Field, has(), inArea(), LatitudeBands, MatchableSite (+21 more)

### Community 80 - "Community 80"
Cohesion: 0.14
Nodes (33): siteRef(), kindOfMode(), deviceDiver(), closeFields(), entryOf(), openFields(), importsField(), createDiveImportService() (+25 more)

### Community 81 - "Community 81"
Cohesion: 0.12
Nodes (27): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+19 more)

### Community 82 - "Community 82"
Cohesion: 0.10
Nodes (25): ADR-0051, Status, ADR-0056, clearPushRuns(), clearQueue(), conflictAtSsi(), connectSsi(), deletableDive() (+17 more)

### Community 83 - "Community 83"
Cohesion: 0.09
Nodes (28): ENGINE_VERSION, EVIDENCE, Evidence, FindingValues, LIMITS, RULE_IDS, RuleId, RULES (+20 more)

### Community 84 - "Community 84"
Cohesion: 0.09
Nodes (24): RecordingSummary, UtcOffsetSource, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput (+16 more)

### Community 85 - "Community 85"
Cohesion: 0.07
Nodes (25): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+17 more)

### Community 86 - "Community 86"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 87 - "Community 87"
Cohesion: 0.07
Nodes (21): CustomTypeOptions, i18next, initI18n(), resources, ADR-0014, StrayOverride, ADR-0014, english (+13 more)

### Community 88 - "Community 88"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 89 - "Community 89"
Cohesion: 0.09
Nodes (25): ProblemCode, Blocked, classify(), kindOf(), KINDS, LEFT_OUT_KEY, LEFT_OUT_KEYS, LeftOutReason (+17 more)

### Community 90 - "Community 90"
Cohesion: 0.09
Nodes (27): devDependencies, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2, @testing-library/react@^16.3.3 (+19 more)

### Community 91 - "Community 91"
Cohesion: 0.11
Nodes (17): ADR-0036, ADR-0063, ADR-0062, ADR-0042, ADR-0047, ADR-0021, assessedDive(), cancelWaitingImports() (+9 more)

### Community 92 - "Community 92"
Cohesion: 0.15
Nodes (27): equipmentItemQuery(), EquipmentItemView, EquipmentScheduleDue, EquipmentScheduleView, EquipmentStatus, CATEGORIES, categoryName(), DiverEquipment() (+19 more)

### Community 93 - "Community 93"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 94 - "Community 94"
Cohesion: 0.09
Nodes (24): ComputerChoice, DiveImportMode, DiveKind, ProviderImportPlan, providerSiteData, ProviderContext, Assessment, ComputerView (+16 more)

### Community 95 - "Community 95"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 96 - "Community 96"
Cohesion: 0.15
Nodes (24): DiveOrigin, Series, SsiLogbook, aboveZero(), cylinderOf(), deviceOf(), deviceOfRef(), MATERIAL_OF_TANK_TYPE (+16 more)

### Community 97 - "Community 97"
Cohesion: 0.09
Nodes (23): @modelcontextprotocol/server@^2.3.1, aiAccessLog, aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, KeyRow, LOG_DAYS (+15 more)

### Community 98 - "Community 98"
Cohesion: 0.09
Nodes (24): pushRunItem, PushRunLeftOut, DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN, NO_RECORDING (+16 more)

### Community 99 - "Community 99"
Cohesion: 0.14
Nodes (21): diveWith(), ADR-0028, ADR-0043, forget(), headers, ADR-0043, headers, Site (+13 more)

### Community 100 - "Community 100"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 101 - "Community 101"
Cohesion: 0.10
Nodes (22): ADR-0025, WaterMismatch, RemoteSite, ProviderSite, SiteInput, DECO_MODELS, DIVE_MODES, DIVER_SOURCES (+14 more)

### Community 102 - "Community 102"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 103 - "Community 103"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 104 - "Community 104"
Cohesion: 0.09
Nodes (20): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+12 more)

### Community 105 - "Community 105"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 106 - "Community 106"
Cohesion: 0.09
Nodes (23): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+15 more)

### Community 107 - "Community 107"
Cohesion: 0.12
Nodes (20): CENTRE_SOURCES, CentreService, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+12 more)

### Community 108 - "Community 108"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 109 - "Community 109"
Cohesion: 0.15
Nodes (18): qr@0.7.2, cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043 (+10 more)

### Community 110 - "Community 110"
Cohesion: 0.13
Nodes (21): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+13 more)

### Community 111 - "Community 111"
Cohesion: 0.12
Nodes (15): TAGS, killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023 (+7 more)

### Community 112 - "Community 112"
Cohesion: 0.13
Nodes (17): providerGoneDive, compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051, candidatesOf() (+9 more)

### Community 113 - "Community 113"
Cohesion: 0.13
Nodes (18): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+10 more)

### Community 114 - "Community 114"
Cohesion: 0.18
Nodes (20): comparableOf(), hasTolerance(), isEmpty(), near(), planSend(), sameAtProvider(), sameCylinder(), text() (+12 more)

### Community 115 - "Community 115"
Cohesion: 0.16
Nodes (17): durationFormat(), DurationFormatter, formatDuration(), formatGasRate(), formatMass(), formatPressure(), formatPressureRate(), formatTemperature() (+9 more)

### Community 116 - "Community 116"
Cohesion: 0.24
Nodes (17): ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres(), isHypoxic(), metresPerBar() (+9 more)

### Community 117 - "Community 117"
Cohesion: 0.19
Nodes (18): AssessmentView, AssessmentState, useWhen(), bandSeconds(), clock(), counts(), differing(), Finding (+10 more)

### Community 118 - "Community 118"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 119 - "Community 119"
Cohesion: 0.15
Nodes (16): failedTitlesSince(), git(), eachSpec(), failedTestsFromReport(), lastStatuses(), localTime(), passedToRecord(), reportedTests() (+8 more)

### Community 120 - "Community 120"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 121 - "Community 121"
Cohesion: 0.15
Nodes (17): MAX_OFFSET_MS, inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), placeLocalTime(), Position, ADR-0030 (+9 more)

### Community 122 - "Community 122"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 123 - "Community 123"
Cohesion: 0.14
Nodes (18): defineTool(), Id(), Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), HINT (+10 more)

### Community 124 - "Community 124"
Cohesion: 0.19
Nodes (18): columnsOf(), createSiteImportService(), create(), loadExisting(), update(), same(), SiteImportError, ADR-0021 (+10 more)

### Community 125 - "Community 125"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 126 - "Community 126"
Cohesion: 0.19
Nodes (16): looksLikeZip(), formatOf(), createImportService(), cancelImport(), createImport(), endWaiting(), expireWaiting(), openUpload() (+8 more)

### Community 127 - "Community 127"
Cohesion: 0.12
Nodes (15): externalDiver(), mergeExternalDiver(), participations(), AutomationSettings, Credentials, FETCH_INTERVALS, FetchInterval, purpose() (+7 more)

### Community 128 - "Community 128"
Cohesion: 0.18
Nodes (15): asksFor(), failureOutcome(), numberPlanOf(), orderItems(), createPushRunService(), analyse(), numbersHere(), ownConnection() (+7 more)

### Community 129 - "Community 129"
Cohesion: 0.12
Nodes (15): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+7 more)

### Community 130 - "Community 130"
Cohesion: 0.13
Nodes (14): createScheduledFetch(), blocked(), due(), minimumOf(), ProviderLayer, ADR-0027, ADR-0029, ADR-0030 (+6 more)

### Community 131 - "Community 131"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 132 - "Community 132"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 133 - "Community 133"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 134 - "Community 134"
Cohesion: 0.20
Nodes (12): fromOsm(), fromSsi(), fromWd(), HOUSE_REEF, osm(), plan(), site(), ssi() (+4 more)

### Community 135 - "Community 135"
Cohesion: 0.20
Nodes (10): DIVE_KINDS, ImportView, ImportViewSchema, toImportView(), ADR-0030, ImportRouteDeps, ADR-0044, ADR-0056 (+2 more)

### Community 136 - "Community 136"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 137 - "Community 137"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 138 - "Community 138"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 139 - "Community 139"
Cohesion: 0.24
Nodes (8): DiveSummary, Profile, SKETCH_HEIGHT, SKETCH_WIDTH, sketchPaths(), ADR-0041, ProfileSketch(), ADR-0041

### Community 140 - "Community 140"
Cohesion: 0.17
Nodes (10): Dive, HOUSE_REEF, LIGHTHOUSE, Method, Page, Position, Revision, Site (+2 more)

### Community 141 - "Community 141"
Cohesion: 0.21
Nodes (11): ProblemCode, HELD_REASONS, HeldReason, ICON, isHeldReason(), minutesUntil(), ADR-0056, useNow() (+3 more)

### Community 142 - "Community 142"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 143 - "Community 143"
Cohesion: 0.20
Nodes (10): audienceOfConnection(), audienceOfDiver(), Hint, HINT_KINDS, HintAudience, HintKind, kinds, ADR-0020 (+2 more)

### Community 144 - "Community 144"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 145 - "Community 145"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 146 - "Community 146"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 147 - "Community 147"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 148 - "Community 148"
Cohesion: 0.36
Nodes (9): describe(), isObject(), keyDifferences(), pause(), recording(), runAnswerChecks(), sampleCount(), testDive() (+1 more)

### Community 149 - "Community 149"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 150 - "Community 150"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 151 - "Community 151"
Cohesion: 0.20
Nodes (10): verify, major, minor, sha, tag, version, name, outputs (+2 more)

### Community 152 - "Community 152"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 154 - "Community 154"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 155 - "Community 155"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 156 - "Community 156"
Cohesion: 0.32
Nodes (6): round1(), Series, summariseProfile(), ADR-0035, s(), ADR-0035

### Community 157 - "Community 157"
Cohesion: 0.29
Nodes (6): IMPORT_SOURCES, SOURCE_INFO, SourceInfo, ADR-0021, IMPORT_SOURCES, ADR-0021

### Community 158 - "Community 158"
Cohesion: 0.36
Nodes (6): headers, open(), ownId(), ownRow(), sectionButton(), ADR-0050

### Community 159 - "Community 159"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 160 - "Community 160"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 161 - "Community 161"
Cohesion: 0.33
Nodes (4): pollUntilDue(), ADR-0056, now, ADR-0056

### Community 162 - "Community 162"
Cohesion: 0.33
Nodes (6): ADR-0023, ADR-0053, ADR-0066, MODULE_IMPORTS, moduleRules, target()

### Community 163 - "Community 163"
Cohesion: 0.40
Nodes (5): stubDive(), ADR-0015, ADR-0023, ADR-0025, guardedRoute()

### Community 164 - "Community 164"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 165 - "Community 165"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 166 - "Community 166"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 167 - "Community 167"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 168 - "Community 168"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 169 - "Community 169"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

## Knowledge Gaps
- **1067 isolated node(s):** `ADR-0013`, `ADR-0011`, `ADR-0040`, `ADR-0063`, `ADR-0043` (+1062 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 2721 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **3 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 17` to `Community 0`, `Community 2`, `Community 6`, `Community 9`, `Community 10`, `Community 12`, `Community 13`, `Community 143`, `Community 16`, `Community 20`, `Community 21`, `Community 23`, `Community 24`, `Community 25`, `Community 26`, `Community 28`, `Community 29`, `Community 36`, `Community 37`, `Community 40`, `Community 42`, `Community 44`, `Community 45`, `Community 50`, `Community 63`, `Community 68`, `Community 69`, `Community 70`, `Community 76`, `Community 88`, `Community 94`, `Community 97`, `Community 98`, `Community 106`, `Community 112`, `Community 121`, `Community 124`, `Community 127`?**
  _High betweenness centrality (0.045) - this node is a cross-community bridge._
- **What connects `ADR-0013`, `ADR-0011`, `ADR-0040` to the rest of the system?**
  _1067 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.030162104703693862 - nodes in this community are weakly interconnected._
- **Why does `createSsiAdapter()` connect `Community 33` to `Community 96`, `Community 0`, `Community 39`, `Community 12`, `Community 13`, `Community 17`, `Community 24`, `Community 61`, `Community 30`?**
  _High betweenness centrality (0.029) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.04518213866039953 - nodes in this community are weakly interconnected._
- **Why does `Button()` connect `Community 1` to `Community 65`, `Community 66`, `Community 3`, `Community 5`, `Community 7`, `Community 8`, `Community 11`, `Community 109`, `Community 14`, `Community 47`, `Community 92`, `Community 141`, `Community 18`, `Community 19`, `Community 53`, `Community 57`, `Community 60`?**
  _High betweenness centrality (0.024) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.039019103102560626 - nodes in this community are weakly interconnected._