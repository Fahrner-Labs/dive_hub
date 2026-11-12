# Graph Report - dive_hub  (2026-11-12)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 6703 nodes · 20887 edges · 175 communities (170 shown, 5 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 206 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `84526663`
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

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 193 edges
2. `Button()` - 189 edges
3. `unwrap()` - 188 edges
4. `useErrorText()` - 158 edges
5. `useDisplay()` - 151 edges
6. `Muted()` - 129 edges
7. `drizzle-orm` - 125 edges
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
- `EquipmentListFilter` --references--> `EquipmentCategory`  [EXTRACTED]
  apps/server/src/equipment/read.ts → apps/server/src/vocabulary.ts

## Import Cycles
- None detected.

## Communities (175 total, 5 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.03
Nodes (147): user, diveSiteExternalId, revision, createSsiClient(), ADR-0013, ADR-0011, Sent, View (+139 more)

### Community 1 - "Community 1"
Cohesion: 0.05
Nodes (184): DisplaySettings(), AdminSettings(), DeleteUserDialog(), Invitations(), Invite(), LinkToPassOn(), ProviderSiteData(), SiteDataPanel() (+176 more)

### Community 2 - "Community 2"
Cohesion: 0.03
Nodes (102): createServer(), createAuth(), loadAuthSecret(), workerDeps(), Config, loadConfig(), ADR-0011, ADR-0021 (+94 more)

### Community 3 - "Community 3"
Cohesion: 0.04
Nodes (116): allButDive(), ApiError, continuationCandidateQuery(), deletedDivesQuery(), DeletedDiveView, diveProvidersQuery(), diveQuery(), DiveValues (+108 more)

### Community 4 - "Community 4"
Cohesion: 0.02
Nodes (98): diveFinding, recordingEvent, Assessment, FindingView, Row, ADR-0036, upload(), Check (+90 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (103): Auth, CentreService, CentreRouteDeps, UTC_OFFSET_SOURCES, DiverService, DiverRouteDeps, downsampleMinMax(), waterMismatch() (+95 more)

### Community 6 - "Community 6"
Cohesion: 0.04
Nodes (83): name, private, type, equipmentDueQuery(), EquipmentDueView, Me, EquipmentDueNotice(), ADR-0034 (+75 more)

### Community 7 - "Community 7"
Cohesion: 0.04
Nodes (101): LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, ParticipantRole, renumber(), renumberStatement(), ADR-0048, AppendRow (+93 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (84): connection, diveLease, providerConflict, providerLease, push, pushRun, waitingPush, kept() (+76 more)

### Community 9 - "Community 9"
Cohesion: 0.04
Nodes (97): api, ConflictFieldView, ConnectionView, DiveImportMode, DiveImportPreview, diveImportPreviewQuery(), DiveKind, diveTimesQuery() (+89 more)

### Community 10 - "Community 10"
Cohesion: 0.05
Nodes (91): ConflictChoices, ConflictEntryView, ConflictsOfConnection, cylinderCatalogueQuery(), LeadEstimate, CylinderFields(), CylinderList(), MATERIALS (+83 more)

### Community 11 - "Community 11"
Cohesion: 0.04
Nodes (87): ChangePassword(), SecuritySettings(), Sessions(), useDescribeAgent(), authClient, invitationLinkQuery(), isUnauthorized(), passwordResetQuery() (+79 more)

### Community 12 - "Community 12"
Cohesion: 0.04
Nodes (86): CertificationService, CYLINDER_MATERIALS, MATCHING_WINDOWS, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053, Copies (+78 more)

### Community 13 - "Community 13"
Cohesion: 0.04
Nodes (93): divesQuery(), LogbookPage, PAGE_SIZE, ProblemCode, pushRunActive(), pushRunBody(), PushRunChoices, PushRunDetail (+85 more)

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (83): AnyLogbookCheck, buddiesForQuery(), buddiesQuery(), BuddyView, certificationsQuery(), CertificationView, ContinuationCandidateView, DETAILS (+75 more)

### Community 15 - "Community 15"
Cohesion: 0.02
Nodes (78): StoredConflictField, actorType, aiAccessOutcome, candidateResolution, centreSource, CERTIFICATION_IMAGE_STATES, CERTIFICATION_VERIFICATIONS, certificationImageState (+70 more)

### Community 16 - "Community 16"
Cohesion: 0.08
Nodes (69): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, AssessmentService, AssessmentRouteDeps (+61 more)

### Community 17 - "Community 17"
Cohesion: 0.04
Nodes (68): pushRunItem, PushRunLeftOut, asksFor(), DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN (+60 more)

### Community 18 - "Community 18"
Cohesion: 0.06
Nodes (62): siteImport, ProviderAdapter, findOrMake(), ADR-0057, createSitePickService(), ADR-0057, distanceM(), matchingSite() (+54 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (51): PushLeftOut, ActionContext, Capabilities, Direction, DiveExport, DiveListing, FIND_BY, FindBy (+43 more)

### Community 20 - "Community 20"
Cohesion: 0.05
Nodes (59): adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, ADR-0011, ADR-0013, ADR-0035 (+51 more)

### Community 21 - "Community 21"
Cohesion: 0.08
Nodes (65): candidatesQuery(), CandidateView, DiveCheckView, diversQuery(), goneDivesQuery(), isDiveCheck(), isPairCheck(), logbookChecksQuery() (+57 more)

### Community 22 - "Community 22"
Cohesion: 0.05
Nodes (59): CylinderMaterial, SuitType, SuitUndergarment, WeightingFelt, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, Suit (+51 more)

### Community 23 - "Community 23"
Cohesion: 0.04
Nodes (54): Tx, continuationAppend, providerSiteData, sampleSeries, buildJoined(), partInput(), RecordingRow, ADR-0063 (+46 more)

### Community 24 - "Community 24"
Cohesion: 0.06
Nodes (48): SsiClient, SsiRecord, contextOf(), buildDeleteRecord(), buildUpdateRecord(), DiveForSsi, keysOf(), writableRecord() (+40 more)

### Community 25 - "Community 25"
Cohesion: 0.07
Nodes (54): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ambientPressureBar(), bestMixPercent() (+46 more)

### Community 26 - "Community 26"
Cohesion: 0.07
Nodes (43): AuthOptions, auth, createServices(), ServiceParts, Services, ADR-0027, ADR-0030, ADR-0061 (+35 more)

### Community 27 - "Community 27"
Cohesion: 0.05
Nodes (54): DIVE_IMPORT_MODES, PARTICIPANT_ROLES, DIVE_ONLY_PATTERN, ADR-0053, ADR-0056, WaitingPushViewSchema, storedProblemCode(), oneOf() (+46 more)

### Community 28 - "Community 28"
Cohesion: 0.07
Nodes (47): errorMessage(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy(), ADR-0024 (+39 more)

### Community 29 - "Community 29"
Cohesion: 0.05
Nodes (53): connectionsQuery(), ExternalIdView, Position, SiteRevisionView, SiteView, typedSiteId(), needsOsmAttribution(), siteOrigin() (+45 more)

### Community 30 - "Community 30"
Cohesion: 0.07
Nodes (56): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+48 more)

### Community 31 - "Community 31"
Cohesion: 0.07
Nodes (55): ConnectionRow, WaitingPushOptions, audienceOfConnection(), audienceOfDive(), audienceOfDiver(), Hint, HINT_KINDS, HintAudience (+47 more)

### Community 32 - "Community 32"
Cohesion: 0.09
Nodes (57): managedDiverIds(), cylindersOf(), mergeProviderCylinder(), providerCylinderOf(), participantsOf(), columnsOf(), partsOf(), leadOf() (+49 more)

### Community 33 - "Community 33"
Cohesion: 0.06
Nodes (53): AiAccessScope, diversOf(), SiteRow, Cursor, cursorAt(), DateOnly(), Detail, diversAsked() (+45 more)

### Community 34 - "Community 34"
Cohesion: 0.04
Nodes (53): ADR-0025, WaterMismatch, EquipmentService, RecordInput, ScheduleInput, DiveGearItemViewSchema, DiveGearViewSchema, DueState (+45 more)

### Community 35 - "Community 35"
Cohesion: 0.09
Nodes (49): Queryable, logbookCheckAnswer, logbookCheckDiveAnswer, ADR-0016, keepApart(), keepDive(), keepRestored(), ADR-0038 (+41 more)

### Community 36 - "Community 36"
Cohesion: 0.07
Nodes (49): Delivered, ProviderError, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf() (+41 more)

### Community 37 - "Community 37"
Cohesion: 0.06
Nodes (51): ADR-0038, ADR-0048, globalSetup(), ADR-0023, AiAccess, AssessedFinding, conflictForLena(), Connection (+43 more)

### Community 38 - "Community 38"
Cohesion: 0.09
Nodes (47): accountListEntry, diver, diverExternalId, tidyName(), DiverDetails, Caller, foldName(), nameDiffers() (+39 more)

### Community 39 - "Community 39"
Cohesion: 0.06
Nodes (36): diveModeFromFit, createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities() (+28 more)

### Community 40 - "Community 40"
Cohesion: 0.06
Nodes (44): diverBodyWeight, diverManagement, diveSite, lead, participant, DiverError, ADR-0016, ADR-0053 (+36 more)

### Community 41 - "Community 41"
Cohesion: 0.07
Nodes (44): ImportedValues, SiteImportCounts, ADR-0021, ADR-0025, ADR-0053, RemoteSite, ExistingSite, Field (+36 more)

### Community 42 - "Community 42"
Cohesion: 0.07
Nodes (45): ConflictField, stillDiffering(), shownFields(), StoredConflictField, ShownValue, Choice, hasTolerance(), isEmpty() (+37 more)

### Community 43 - "Community 43"
Cohesion: 0.11
Nodes (43): assessmentQuery(), AssessmentView, samplesQuery(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState, FindingItem() (+35 more)

### Community 44 - "Community 44"
Cohesion: 0.06
Nodes (43): ComputerChoice, DiveImportMode, DiveKind, ProviderImportPlan, UtcOffsetSource, ProviderCylinder, DAY_MS, MAX_OFFSET_MS (+35 more)

### Community 45 - "Community 45"
Cohesion: 0.08
Nodes (42): Leave, Series, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+34 more)

### Community 46 - "Community 46"
Cohesion: 0.09
Nodes (36): BottomTimeQuery, DEFAULT_GF_HIGH, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033, DEFAULT_PPO2 (+28 more)

### Community 47 - "Community 47"
Cohesion: 0.06
Nodes (37): sampleCount(), SSI_CAPABILITIES, SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText() (+29 more)

### Community 48 - "Community 48"
Cohesion: 0.07
Nodes (30): ADR-0036, ADR-0016, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0062 (+22 more)

### Community 49 - "Community 49"
Cohesion: 0.06
Nodes (43): LEAD_PLACEMENTS, SUIT_TYPES, SUIT_UNDERGARMENTS, WEIGHTING_FELT, ProfileSummary, round1(), Series, summariseProfile() (+35 more)

### Community 50 - "Community 50"
Cohesion: 0.07
Nodes (36): ImportErrorCode, ImportFound, importOriginal, ImportOutcome, ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS (+28 more)

### Community 51 - "Community 51"
Cohesion: 0.07
Nodes (42): alignedForMatching(), CandidateDive, decideMatch(), depthsDisagree(), DuplicateReason, MatchDecision, OVERLAP_TOLERANCE_SECONDS, overlaps() (+34 more)

### Community 52 - "Community 52"
Cohesion: 0.08
Nodes (40): open(), automationOffers(), AutomationSettings, createConnectionService(), checkAutomation(), checkFollowNumbers(), expiryOf(), onTurn() (+32 more)

### Community 53 - "Community 53"
Cohesion: 0.05
Nodes (40): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+32 more)

### Community 54 - "Community 54"
Cohesion: 0.06
Nodes (39): Change(), Changes(), historyRoute, Made(), REVISION_CAUSES, PositionSchema, AdoptBody, Country (+31 more)

### Community 55 - "Community 55"
Cohesion: 0.05
Nodes (42): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+34 more)

### Community 56 - "Community 56"
Cohesion: 0.07
Nodes (39): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+31 more)

### Community 57 - "Community 57"
Cohesion: 0.07
Nodes (37): @modelcontextprotocol/server@^2.3.1, aiAccessLog, aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, AiAccessService, KeyRow (+29 more)

### Community 58 - "Community 58"
Cohesion: 0.06
Nodes (34): DiverCertifications, replyDiverError, STATUS, CertificationSummary, DiverDetailsView, DiverDetailsViewSchema, DiverSource, DiverView (+26 more)

### Community 59 - "Community 59"
Cohesion: 0.11
Nodes (40): siteRef(), kindOfMode(), deviceDiver(), closeFields(), createConflictService(), loadWaiting(), entryOf(), fieldsOf() (+32 more)

### Community 60 - "Community 60"
Cohesion: 0.08
Nodes (34): date(), FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position(), positive() (+26 more)

### Community 61 - "Community 61"
Cohesion: 0.07
Nodes (26): device, equipmentItem, managesDiveDiver(), CandidateError, CandidateRow, createCandidateService(), lockMine(), RecordingRow (+18 more)

### Community 62 - "Community 62"
Cohesion: 0.17
Nodes (37): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestEntries(), entryNamed(), flag() (+29 more)

### Community 63 - "Community 63"
Cohesion: 0.11
Nodes (35): massIn(), Dive, draftOfLead(), draftOfWeighting(), emptyLead(), fromLastDive(), hasThickness(), kg() (+27 more)

### Community 64 - "Community 64"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 65 - "Community 65"
Cohesion: 0.09
Nodes (35): CylinderMaterial, BOTTOM_TIME_RULES, cylinderOf(), cylinderText(), diverOf(), diverText(), finite(), kept (+27 more)

### Community 66 - "Community 66"
Cohesion: 0.08
Nodes (30): CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite(), lock() (+22 more)

### Community 67 - "Community 67"
Cohesion: 0.07
Nodes (32): defineTool(), Id(), Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), HINT (+24 more)

### Community 68 - "Community 68"
Cohesion: 0.06
Nodes (35): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+27 more)

### Community 69 - "Community 69"
Cohesion: 0.11
Nodes (32): AscentBand, ascentRate(), ceiling(), descentRate(), judgedParts(), lastMetres(), ppo2(), Profile (+24 more)

### Community 70 - "Community 70"
Cohesion: 0.08
Nodes (17): HINT_CHANNEL, parseHint(), createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener() (+9 more)

### Community 71 - "Community 71"
Cohesion: 0.09
Nodes (33): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+25 more)

### Community 72 - "Community 72"
Cohesion: 0.10
Nodes (28): diveWith(), ADR-0028, ADR-0043, stubDive(), ADR-0015, ADR-0023, ADR-0025, forget() (+20 more)

### Community 73 - "Community 73"
Cohesion: 0.12
Nodes (32): importsQuery(), ImportView, Logbook(), ImportContext, ImportFilesButton(), ImportHistory(), ImportList(), ImportNotices() (+24 more)

### Community 74 - "Community 74"
Cohesion: 0.06
Nodes (33): depends_on, environment, image, ports, restart, volumes, condition, environment (+25 more)

### Community 75 - "Community 75"
Cohesion: 0.08
Nodes (26): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+18 more)

### Community 76 - "Community 76"
Cohesion: 0.14
Nodes (32): waitingOf(), close(), isExporting(), createPushService(), checkAll(), deleter(), deleteThere(), deletion() (+24 more)

### Community 77 - "Community 77"
Cohesion: 0.14
Nodes (32): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findNewId(), Format (+24 more)

### Community 78 - "Community 78"
Cohesion: 0.08
Nodes (27): CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError, CertificationExpiry, CertificationInput, CertificationView, createCertificationService(), cleaned() (+19 more)

### Community 79 - "Community 79"
Cohesion: 0.13
Nodes (31): bottomTimeRule(), defined(), ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas, toolsGasQuery(), toolsPlanQuery(), keepToolsParams() (+23 more)

### Community 80 - "Community 80"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 81 - "Community 81"
Cohesion: 0.07
Nodes (21): CustomTypeOptions, i18next, initI18n(), resources, ADR-0014, StrayOverride, ADR-0014, english (+13 more)

### Community 82 - "Community 82"
Cohesion: 0.09
Nodes (27): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+19 more)

### Community 83 - "Community 83"
Cohesion: 0.09
Nodes (23): computerEventOf(), ADR-0036, computerEventFromFit(), COMPUTER_EVENT, computerEventFromSuunto(), DECO_MODEL, decoModelFromSuunto, DIVE_MODE (+15 more)

### Community 84 - "Community 84"
Cohesion: 0.10
Nodes (26): applyHints(), LiveArrivals, Page, useLiveArrivals(), bump(), changedListeners, ChannelMessage, healthListeners (+18 more)

### Community 85 - "Community 85"
Cohesion: 0.11
Nodes (25): equipmentUse, ScheduleDue, DiveGear, DueEntry, GearItem, ItemDetailView, ItemEdit, ItemInput (+17 more)

### Community 86 - "Community 86"
Cohesion: 0.16
Nodes (25): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+17 more)

### Community 87 - "Community 87"
Cohesion: 0.11
Nodes (26): ToolsPlan, ToolsPlanDiver, PlanDiver, PlanLevel, planSketch(), PlanStretch, ToolsRule, DEFAULT_CYLINDER (+18 more)

### Community 88 - "Community 88"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 89 - "Community 89"
Cohesion: 0.11
Nodes (23): EVIDENCE, Evidence, FindingValues, RULE_IDS, RuleId, RULES, SEVERITIES, SourceId (+15 more)

### Community 90 - "Community 90"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 91 - "Community 91"
Cohesion: 0.16
Nodes (24): DiveOrigin, REFERENCE_PREFIX, SsiLogbook, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef() (+16 more)

### Community 92 - "Community 92"
Cohesion: 0.09
Nodes (22): cancelWaitingImports(), connectGarmin(), expectAccessible(), expectGoodPage(), goneAtSsi(), leaveGarmin(), putAside(), sendToSsi() (+14 more)

### Community 93 - "Community 93"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 94 - "Community 94"
Cohesion: 0.10
Nodes (18): createScheduledFetch(), blocked(), due(), minimumOf(), createProviderLayer(), ProviderLayer, ADR-0027, ADR-0029 (+10 more)

### Community 95 - "Community 95"
Cohesion: 0.15
Nodes (21): Answer, CatalogueCylinder, CylinderInput, cylindersOfDrafts(), CylinderView, draftOfCylinder(), emptyDraft(), fromCatalogue() (+13 more)

### Community 96 - "Community 96"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 97 - "Community 97"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 98 - "Community 98"
Cohesion: 0.16
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 99 - "Community 99"
Cohesion: 0.09
Nodes (18): account, accountRelations, apikey, rateLimit, session, sessionRelations, ADR-0011, ADR-0035 (+10 more)

### Community 100 - "Community 100"
Cohesion: 0.09
Nodes (23): devDependencies, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2, @testing-library/react@^16.3.3, @testing-library/user-event@^14.6.8 (+15 more)

### Community 101 - "Community 101"
Cohesion: 0.11
Nodes (15): TAGS, killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023 (+7 more)

### Community 102 - "Community 102"
Cohesion: 0.09
Nodes (23): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+15 more)

### Community 103 - "Community 103"
Cohesion: 0.14
Nodes (21): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+13 more)

### Community 104 - "Community 104"
Cohesion: 0.10
Nodes (18): DIVE_KINDS, CandidateRouteDeps, CandidateView, Decided, DiveBrief, OffsetSource, STATUS, ADR-0016 (+10 more)

### Community 105 - "Community 105"
Cohesion: 0.15
Nodes (22): managementOf(), EquipmentError, checkCost(), checkDates(), checkWindow(), createEquipmentService(), assemble(), checkResets() (+14 more)

### Community 106 - "Community 106"
Cohesion: 0.14
Nodes (18): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+10 more)

### Community 107 - "Community 107"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 108 - "Community 108"
Cohesion: 0.15
Nodes (18): qr@0.7.2, cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043 (+10 more)

### Community 109 - "Community 109"
Cohesion: 0.12
Nodes (19): CENTRE_SOURCE_NAME, CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+11 more)

### Community 110 - "Community 110"
Cohesion: 0.13
Nodes (17): providerGoneDive, compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051, candidatesOf() (+9 more)

### Community 111 - "Community 111"
Cohesion: 0.13
Nodes (17): EXPIRE_WAITING_IMPORTS_TASK, PROCESS_IMPORT_TASK, SCHEDULED_FETCH_TASK, PUSH_RUN_STEP_TASK, WAITING_PUSHES_TASK, AUTOMATION_TICK_TASK, IMPORT_DIVE_SITES_TASK, idOf() (+9 more)

### Community 112 - "Community 112"
Cohesion: 0.12
Nodes (16): Cylinder, DiveView, RevisionView, ADR-0045, upload(), FIT_EPOCH_MS, makeSuuntoAppFit(), makeSuuntoJson() (+8 more)

### Community 113 - "Community 113"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 114 - "Community 114"
Cohesion: 0.14
Nodes (18): serviceRecord, serviceRecordSchedule, serviceSchedule, equipmentList(), EquipmentListFilter, EquipmentRecordRead, today(), ADR-0034 (+10 more)

### Community 115 - "Community 115"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 116 - "Community 116"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 117 - "Community 117"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 118 - "Community 118"
Cohesion: 0.11
Nodes (19): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+11 more)

### Community 119 - "Community 119"
Cohesion: 0.11
Nodes (13): ImportArea, SiteImportRequest, openStream(), EGYPT, ExternalId, HOUSE_REEF, Method, Position (+5 more)

### Community 120 - "Community 120"
Cohesion: 0.12
Nodes (15): certification, PROBLEMS, createUserAdminService(), keepAnAdmin(), lock(), deleteUserData(), ADR-0013, ADR-0024 (+7 more)

### Community 121 - "Community 121"
Cohesion: 0.13
Nodes (14): headers, ADR-0031, headers, ADR-0043, headers, ADR-0030, ADR-0040, externalDiver() (+6 more)

### Community 122 - "Community 122"
Cohesion: 0.14
Nodes (11): headers, open(), ownId(), ownRow(), sectionButton(), ADR-0050, cameraShowing(), headers (+3 more)

### Community 123 - "Community 123"
Cohesion: 0.15
Nodes (14): headers, ADR-0025, clearQueue(), fetchState(), leaveConflict(), leaveSsi(), queueEntry(), ssiConnectionOf() (+6 more)

### Community 124 - "Community 124"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 125 - "Community 125"
Cohesion: 0.18
Nodes (15): RecordingSummary, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput, round3() (+7 more)

### Community 126 - "Community 126"
Cohesion: 0.22
Nodes (16): draftOf(), localStart(), round1(), temperatureIn(), DepthPlot(), LINE_DASHES, plotFont(), PressureStrip() (+8 more)

### Community 127 - "Community 127"
Cohesion: 0.22
Nodes (16): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+8 more)

### Community 128 - "Community 128"
Cohesion: 0.15
Nodes (12): replyCertificationError, STATUS, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, CertificationRouteDeps (+4 more)

### Community 129 - "Community 129"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 130 - "Community 130"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 131 - "Community 131"
Cohesion: 0.16
Nodes (12): CIRCUIT, circuitFromFit, DECO_MODEL, decoModelFromFit, DIVE_ALERT, DIVE_MODE, ADR-0015, WATER_TYPE (+4 more)

### Community 132 - "Community 132"
Cohesion: 0.18
Nodes (12): created, headers, importFixture(), ADR-0066, uploadFixture(), Status, ADR-0056, clearPushRuns() (+4 more)

### Community 133 - "Community 133"
Cohesion: 0.24
Nodes (13): asNumber(), asText(), compareReadBack(), datasetLength(), describe(), isObject(), keyDifferences(), pause() (+5 more)

### Community 134 - "Community 134"
Cohesion: 0.22
Nodes (11): fromOsm(), fromSsi(), fromWd(), HOUSE_REEF, osm(), site(), ssi(), ADR-0021 (+3 more)

### Community 135 - "Community 135"
Cohesion: 0.23
Nodes (9): DiveSummary, Profile, SKETCH_HEIGHT, SKETCH_WIDTH, sketchPaths(), sketchScale(), ADR-0041, ProfileSketch() (+1 more)

### Community 136 - "Community 136"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 137 - "Community 137"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 138 - "Community 138"
Cohesion: 0.17
Nodes (8): ascentBandsOf(), assessProfile(), ndl(), LIMITS, cleanDepth(), CLEAN, samples, ADR-0036

### Community 139 - "Community 139"
Cohesion: 0.18
Nodes (11): ProfileAssessment, Finding, dayNumber(), deepDays(), noFlyAfter(), SERIES_RULES, SeriesContext, SeriesDive (+3 more)

### Community 140 - "Community 140"
Cohesion: 0.29
Nodes (10): hashPassword(), passwordReset, createInvitationService(), createPasswordResetService(), find(), ADR-0013, createSetupService(), ADR-0012 (+2 more)

### Community 141 - "Community 141"
Cohesion: 0.17
Nodes (8): FitAdapter, SuuntoJsonAdapter, UddfAdapter, ParsedRecording, adapter, crossCheck(), decodeOfficial(), privateDir

### Community 142 - "Community 142"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 143 - "Community 143"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 144 - "Community 144"
Cohesion: 0.18
Nodes (5): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053

### Community 145 - "Community 145"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 146 - "Community 146"
Cohesion: 0.20
Nodes (6): noAnswer, thrown(), ADR-0014, ApiClient, createApiClient(), ADR-0004

### Community 147 - "Community 147"
Cohesion: 0.20
Nodes (8): Series, ChannelSeries, RecordedGas, Tank, TiedCylinder, ADR-0045, readings, ADR-0045

### Community 148 - "Community 148"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 149 - "Community 149"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 150 - "Community 150"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 151 - "Community 151"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 152 - "Community 152"
Cohesion: 0.20
Nodes (10): verify, major, minor, sha, tag, version, name, outputs (+2 more)

### Community 153 - "Community 153"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 155 - "Community 155"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 156 - "Community 156"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 157 - "Community 157"
Cohesion: 0.25
Nodes (7): AIR, expectWithinReference(), GF100_FRESH, GF100_SALT, GF85_FRESH, ADR-0032, ADR-0046

### Community 158 - "Community 158"
Cohesion: 0.32
Nodes (7): leadEstimateQuery(), LeadHint(), orUndefined(), ADR-0025, ADR-0031, CylinderDraft, LeadWater

### Community 159 - "Community 159"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 160 - "Community 160"
Cohesion: 0.33
Nodes (4): Sketch, SKETCH_POINTS, ADR-0041, ADR-0041

### Community 161 - "Community 161"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 162 - "Community 162"
Cohesion: 0.33
Nodes (4): pollUntilDue(), ADR-0056, now, ADR-0056

### Community 163 - "Community 163"
Cohesion: 0.33
Nodes (6): ADR-0023, ADR-0053, ADR-0066, MODULE_IMPORTS, moduleRules, target()

### Community 164 - "Community 164"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 165 - "Community 165"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 166 - "Community 166"
Cohesion: 0.40
Nodes (4): offer, osm, ssi, wikidata

### Community 167 - "Community 167"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 168 - "Community 168"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 170 - "Community 170"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 171 - "Community 171"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

## Knowledge Gaps
- **1069 isolated node(s):** `ADR-0013`, `ADR-0011`, `ADR-0040`, `ADR-0043`, `ADR-0017` (+1064 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 2723 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **5 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 0` to `Community 2`, `Community 4`, `Community 7`, `Community 8`, `Community 12`, `Community 140`, `Community 15`, `Community 16`, `Community 17`, `Community 18`, `Community 20`, `Community 23`, `Community 26`, `Community 27`, `Community 31`, `Community 32`, `Community 33`, `Community 35`, `Community 38`, `Community 39`, `Community 40`, `Community 42`, `Community 44`, `Community 46`, `Community 50`, `Community 51`, `Community 52`, `Community 57`, `Community 61`, `Community 66`, `Community 71`, `Community 78`, `Community 82`, `Community 85`, `Community 99`, `Community 102`, `Community 103`, `Community 110`, `Community 114`, `Community 120`?**
  _High betweenness centrality (0.048) - this node is a cross-community bridge._
- **What connects `ADR-0013`, `ADR-0011`, `ADR-0040` to the rest of the system?**
  _1069 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.03225064409274936 - nodes in this community are weakly interconnected._
- **Why does `@playwright/test` connect `Community 48` to `Community 129`, `Community 132`, `Community 37`, `Community 6`, `Community 100`, `Community 72`, `Community 142`, `Community 145`, `Community 53`, `Community 121`, `Community 122`, `Community 123`, `Community 92`, `Community 93`?**
  _High betweenness centrality (0.024) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.05194072706763062 - nodes in this community are weakly interconnected._
- **Why does `react` connect `Community 6` to `Community 1`, `Community 3`, `Community 135`, `Community 9`, `Community 10`, `Community 11`, `Community 13`, `Community 14`, `Community 21`, `Community 29`, `Community 43`, `Community 63`, `Community 73`, `Community 79`, `Community 84`, `Community 87`, `Community 108`, `Community 118`, `Community 126`?**
  _High betweenness centrality (0.020) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.029331779331779332 - nodes in this community are weakly interconnected._