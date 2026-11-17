# Graph Report - dive_hub  (2026-11-17)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 7328 nodes · 22865 edges · 180 communities (173 shown, 7 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 223 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `67c714b5`
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
- Community 179

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 204 edges
2. `Button()` - 198 edges
3. `useErrorText()` - 169 edges
4. `useDisplay()` - 161 edges
5. `Muted()` - 139 edges
6. `unwrap()` - 138 edges
7. `drizzle-orm` - 137 edges
8. `announce()` - 106 edges
9. `createTestDatabase()` - 102 edges
10. `TestDatabase` - 101 edges

## Surprising Connections (you probably didn't know these)
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `Removal` --references--> `ProviderServiceError`  [EXTRACTED]
  apps/server/src/providers/push/push-service.ts → apps/server/src/providers/registry.ts
- `askedAt()` --calls--> `DiveError`  [EXTRACTED]
  apps/server/src/dives/continuation/append-service.ts → apps/server/src/dives/dive-service.ts

## Import Cycles
- None detected.

## Communities (180 total, 7 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (183): Queryable, Tx, LeadPlacement, OverridableField, recordingEvent, renumber(), renumberStatement(), ADR-0048 (+175 more)

### Community 1 - "Community 1"
Cohesion: 0.05
Nodes (175): DisplaySettings(), AdminSettings(), DeleteUserDialog(), Invitations(), Invite(), LinkToPassOn(), ProviderSiteData(), SiteDataPanel() (+167 more)

### Community 2 - "Community 2"
Cohesion: 0.03
Nodes (138): ChangePassword(), Sessions(), useDescribeAgent(), AiAccessLogEntry, AiAccessView, authClient, invitationLinkQuery(), InvitationView (+130 more)

### Community 3 - "Community 3"
Cohesion: 0.04
Nodes (142): uplot@^1.6.32, assessmentQuery(), AssessmentView, samplesQuery(), ToolsPlan, ToolsPlanDiver, AsideItem(), AssessmentContext (+134 more)

### Community 4 - "Community 4"
Cohesion: 0.04
Nodes (104): user, revision, ADR-0013, ADR-0011, dumpToolAvailable(), execFileAsync, ADR-0069, List (+96 more)

### Community 5 - "Community 5"
Cohesion: 0.02
Nodes (120): apikey, buddySuggestion, diveAssessment, parseHint(), Assessment, FindingView, Row, ADR-0036 (+112 more)

### Community 6 - "Community 6"
Cohesion: 0.04
Nodes (126): Me, AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView (+118 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (112): SecuritySettings(), devicesQuery(), equipmentDueQuery(), ExternalIdView, Connections(), DeviceRow(), Devices(), ADR-0016 (+104 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (107): LEAD_PLACEMENTS, OVERRIDABLE_FIELDS, UTC_OFFSET_SOURCES, WEIGHTING_FELT, downsampleMinMax(), sacOfDive(), LogbookCheckService, AppendDeps (+99 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (106): ComputerChoice, DiveImportMode, DiveKind, importOriginal, providerSiteData, ProviderCylinder, close(), ConflictEntry (+98 more)

### Community 10 - "Community 10"
Cohesion: 0.04
Nodes (103): cylinderCatalogueQuery(), connectionsQuery(), CylinderFields(), MATERIALS, ADR-0034, ADR-0045, useCatalogueName(), useCylinderProblem() (+95 more)

### Community 11 - "Community 11"
Cohesion: 0.04
Nodes (98): diveCentre, jointDive, logbookCheckAnswer, logbookCheckDiveAnswer, keepApart(), keepDive(), keepRestored(), ADR-0038 (+90 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (86): managedDiverIds(), open(), AutomationSettings, createConnectionService(), checkAutomation(), checkFollowNumbers(), expiryOf(), onTurn() (+78 more)

### Community 13 - "Community 13"
Cohesion: 0.04
Nodes (75): createServer(), createAuth(), loadAuthSecret(), acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS (+67 more)

### Community 14 - "Community 14"
Cohesion: 0.04
Nodes (95): AssessmentService, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView(), ADR-0036 (+87 more)

### Community 15 - "Community 15"
Cohesion: 0.05
Nodes (91): diverExternalId, diveSite, ProviderImportPlan, dataIdsOf(), settleSiteVisibility(), keyLease(), RemoteSite, Series (+83 more)

### Community 16 - "Community 16"
Cohesion: 0.03
Nodes (79): account, accountRelations, rateLimit, session, sessionRelations, ADR-0011, ADR-0012, ADR-0035 (+71 more)

### Community 17 - "Community 17"
Cohesion: 0.03
Nodes (76): connection, createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities() (+68 more)

### Community 18 - "Community 18"
Cohesion: 0.03
Nodes (66): CertificationAccount, RemoteCertification, ADR-0050, ADR-0053, ParticipantRole, ActionContext, Capabilities, Delivered (+58 more)

### Community 19 - "Community 19"
Cohesion: 0.05
Nodes (92): createCertificationService(), ConnectionRow, WaitingPushOptions, waitingOf(), audienceOfConnection(), audienceOfDive(), audienceOfDiver(), emitHint() (+84 more)

### Community 20 - "Community 20"
Cohesion: 0.02
Nodes (91): accountListEntry, actorType, aiAccessOutcome, backupRunKind, backupRunStatus, candidateResolution, centreSource, CERTIFICATION_IMAGE_STATES (+83 more)

### Community 21 - "Community 21"
Cohesion: 0.04
Nodes (89): divesQuery(), LogbookPage, PAGE_SIZE, importsQuery(), ImportView, App(), BackupsPage, CentrePage (+81 more)

### Community 22 - "Community 22"
Cohesion: 0.04
Nodes (79): adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, Auth, AuthOptions, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, ADR-0011 (+71 more)

### Community 23 - "Community 23"
Cohesion: 0.05
Nodes (88): diversQuery(), allButDive(), continuationCandidateQuery(), ContinuationCandidateView, deletedDivesOn(), deletedDivesQuery(), diveQuery(), mergeCandidatesQuery() (+80 more)

### Community 24 - "Community 24"
Cohesion: 0.03
Nodes (74): continuationAppend, equipmentUse, pushRun, managesDiveDiver(), Participant, participantsOfDives(), asksFor(), DIVE_ONLY (+66 more)

### Community 25 - "Community 25"
Cohesion: 0.04
Nodes (70): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+62 more)

### Community 26 - "Community 26"
Cohesion: 0.05
Nodes (72): @modelcontextprotocol/server@^2.3.1, DUE_STATES, AccessRefusal, AiAccessScope, AiAccessService, seesPositions(), VerifiedAccess, mcpEndpoint() (+64 more)

### Community 27 - "Community 27"
Cohesion: 0.04
Nodes (61): dive, importJob, ImportOutcome, providerConflict, push, waitingPush, kept(), PURGE_AFTER_DAYS (+53 more)

### Community 28 - "Community 28"
Cohesion: 0.05
Nodes (60): EMPTY_SITE_IMPORT_COUNTS, ImportArea, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053 (+52 more)

### Community 29 - "Community 29"
Cohesion: 0.05
Nodes (69): BuddyView, LeftOutView, providerSitesQuery(), pushRunActive(), pushRunBody(), PushRunChoices, PushRunDetail, PushRunPreview (+61 more)

### Community 30 - "Community 30"
Cohesion: 0.05
Nodes (54): diver, lead, participant, createSsiClient(), Sent, View, Card, DEEP (+46 more)

### Community 31 - "Community 31"
Cohesion: 0.07
Nodes (58): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, ambientPressureBar() (+50 more)

### Community 32 - "Community 32"
Cohesion: 0.08
Nodes (61): buildApp(), assessmentRoutes(), AI_ACCESS_NAME_MAX, SessionUser, authHandler(), authHeaders(), fastify, FastifyRequest (+53 more)

### Community 33 - "Community 33"
Cohesion: 0.05
Nodes (54): RecordingSummary, UtcOffsetSource, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput (+46 more)

### Community 34 - "Community 34"
Cohesion: 0.06
Nodes (61): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+53 more)

### Community 35 - "Community 35"
Cohesion: 0.06
Nodes (46): auth, BackupServiceDeps, createServices(), ServiceParts, ADR-0027, ADR-0030, ADR-0061, ADR-0063 (+38 more)

### Community 36 - "Community 36"
Cohesion: 0.07
Nodes (65): tieToJoined(), cylindersOf(), mergeProviderCylinder(), providerCylinderOf(), participantsOf(), siteRef(), columnsOf(), entryMatches() (+57 more)

### Community 37 - "Community 37"
Cohesion: 0.05
Nodes (53): DiveValues, DiveView, OverridableField, revisionsQuery(), RevisionView, UtcOffsetSource, CylinderList(), useCylinderText() (+45 more)

### Community 38 - "Community 38"
Cohesion: 0.07
Nodes (48): errorMessage(), looksLikeZip(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy() (+40 more)

### Community 39 - "Community 39"
Cohesion: 0.05
Nodes (52): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, cancelWaitingImports() (+44 more)

### Community 40 - "Community 40"
Cohesion: 0.05
Nodes (51): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, CertificationViewSchema, ADR-0050, Services (+43 more)

### Community 41 - "Community 41"
Cohesion: 0.06
Nodes (53): ConflictChoices, ConflictFieldView, providerStatusQuery(), focusFirstIn(), useFocusBackWhenClosed(), SyncedField, useFieldNames(), waitsForUser() (+45 more)

### Community 42 - "Community 42"
Cohesion: 0.08
Nodes (45): SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), DiveForSsi, emptyRecord(), writableRecord(), BuddySaved (+37 more)

### Community 43 - "Community 43"
Cohesion: 0.07
Nodes (45): DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining(), ssiDiveType(), ssiInteger() (+37 more)

### Community 44 - "Community 44"
Cohesion: 0.06
Nodes (34): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+26 more)

### Community 45 - "Community 45"
Cohesion: 0.07
Nodes (46): NoFly, residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows(), DiveRow, endMsOf(), loadingDivesForReplay() (+38 more)

### Community 46 - "Community 46"
Cohesion: 0.08
Nodes (48): BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32Encode(), bech32HrpExpand(), bech32Polymod() (+40 more)

### Community 47 - "Community 47"
Cohesion: 0.09
Nodes (43): tidyName(), DiverDetails, foldName(), nameDiffers(), Buddy, createBuddyService(), createFrom(), linkEntry() (+35 more)

### Community 48 - "Community 48"
Cohesion: 0.06
Nodes (37): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+29 more)

### Community 49 - "Community 49"
Cohesion: 0.08
Nodes (36): BottomTimeQuery, DEFAULT_GF_HIGH, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033, ADR-0071 (+28 more)

### Community 50 - "Community 50"
Cohesion: 0.09
Nodes (43): DiveGearItemView, diveGearQuery(), DiveGearView, EquipmentCategory, EquipmentDetailView, EquipmentDueView, equipmentItemQuery(), EquipmentItemView (+35 more)

### Community 51 - "Community 51"
Cohesion: 0.06
Nodes (42): SUIT_TYPES, ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, seriesOf(), minute() (+34 more)

### Community 52 - "Community 52"
Cohesion: 0.06
Nodes (37): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+29 more)

### Community 53 - "Community 53"
Cohesion: 0.05
Nodes (42): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+34 more)

### Community 54 - "Community 54"
Cohesion: 0.07
Nodes (39): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+31 more)

### Community 55 - "Community 55"
Cohesion: 0.06
Nodes (35): SUIT_UNDERGARMENTS, DiverCertifications, replyDiverError, STATUS, DiverService, CertificationSummary, DiverDetailsView, DiverDetailsViewSchema (+27 more)

### Community 56 - "Community 56"
Cohesion: 0.07
Nodes (38): Change(), Changes(), historyRoute, Made(), PositionSchema, AdoptBody, Country, CreateBody (+30 more)

### Community 57 - "Community 57"
Cohesion: 0.05
Nodes (37): AREA_OF, areaOf(), areas, capture(), findings, headers, ADR-0020, ADR-0021 (+29 more)

### Community 58 - "Community 58"
Cohesion: 0.11
Nodes (33): BACKUP_SKIPPED_DIRS, BackupDecryptionKey, encryptionKeyFingerprint(), openArchiveStreamingWith(), assertPgTool(), execFileAsync, pgCommand(), pgErrorDetail() (+25 more)

### Community 59 - "Community 59"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 60 - "Community 60"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 61 - "Community 61"
Cohesion: 0.11
Nodes (34): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+26 more)

### Community 62 - "Community 62"
Cohesion: 0.08
Nodes (33): PARTICIPANT_ROLES, DIVE_ONLY_PATTERN, storedProblemCode(), oneOf(), ConflictFieldView, DiveProviderStatus, DiveProviderStatusSchema, LeftOut (+25 more)

### Community 63 - "Community 63"
Cohesion: 0.11
Nodes (30): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0040 (+22 more)

### Community 64 - "Community 64"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 65 - "Community 65"
Cohesion: 0.07
Nodes (24): diveLease, providerLease, pushRunItem, LEASE_MS, MAX_WAIT_MS, POLL_MS, createFakeHandover(), FakeHandover (+16 more)

### Community 66 - "Community 66"
Cohesion: 0.09
Nodes (33): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+25 more)

### Community 67 - "Community 67"
Cohesion: 0.08
Nodes (30): accountNumber(), accountOnlyRow(), BUDDY_KEYS, FLAGS, newEntryRow(), rowOf(), SsiBuddyRow, withAccountRow() (+22 more)

### Community 68 - "Community 68"
Cohesion: 0.19
Nodes (34): APP_KEYS, appRow(), byId(), changedKeys(), deleteTestEntries(), entryNamed(), flag(), FLAGS (+26 more)

### Community 69 - "Community 69"
Cohesion: 0.11
Nodes (31): createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open(), saveAndReadBack() (+23 more)

### Community 70 - "Community 70"
Cohesion: 0.14
Nodes (32): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findNewId(), Format (+24 more)

### Community 71 - "Community 71"
Cohesion: 0.13
Nodes (26): bar(), celsius(), contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf(), firstOf() (+18 more)

### Community 72 - "Community 72"
Cohesion: 0.09
Nodes (25): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+17 more)

### Community 73 - "Community 73"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 74 - "Community 74"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 75 - "Community 75"
Cohesion: 0.10
Nodes (27): applyHints(), LiveArrivals, Page, useLiveArrivals(), bump(), changedListeners, ChannelMessage, healthListeners (+19 more)

### Community 76 - "Community 76"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 77 - "Community 77"
Cohesion: 0.10
Nodes (25): ENGINE_VERSION, EVIDENCE, Evidence, FindingValues, RULE_IDS, RuleId, RULES, SEVERITIES (+17 more)

### Community 78 - "Community 78"
Cohesion: 0.11
Nodes (26): ScheduleDue, DiveGear, DueEntry, ensureItemForDevice(), GearItem, ItemDetailView, ItemEdit, ItemInput (+18 more)

### Community 79 - "Community 79"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 80 - "Community 80"
Cohesion: 0.10
Nodes (22): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearQueue(), conflictAtSsi() (+14 more)

### Community 81 - "Community 81"
Cohesion: 0.11
Nodes (20): diverLoadingsQuery(), DiverView, CurrentLoadings(), ADR-0036, ADR-0048, ADR-0071, CustomTypeOptions, i18next (+12 more)

### Community 82 - "Community 82"
Cohesion: 0.12
Nodes (23): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+15 more)

### Community 83 - "Community 83"
Cohesion: 0.10
Nodes (22): BACKUP_RUN_TASK, backupTask(), ADR-0069, PurgeService, EXPIRE_WAITING_IMPORTS_TASK, PROCESS_IMPORT_TASK, SCHEDULED_FETCH_TASK, PUSH_RUN_STEP_TASK (+14 more)

### Community 84 - "Community 84"
Cohesion: 0.10
Nodes (17): ProblemCode, ResponseState, whenResponseGone(), StoredBlob, PlacedArchive, ANSWER, looksLikeRecipientPrivateKey(), PlacedArchive (+9 more)

### Community 85 - "Community 85"
Cohesion: 0.09
Nodes (27): cylinderText(), diverText(), kept, LEAD_MATERIALS, LEAD_WATERS, LeadWater, levelsText(), NUMBERS (+19 more)

### Community 86 - "Community 86"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 87 - "Community 87"
Cohesion: 0.08
Nodes (20): headers, ADR-0035, aiAccessReady(), askMcp(), createAiAccess(), putAside(), headers, ADR-0016 (+12 more)

### Community 88 - "Community 88"
Cohesion: 0.13
Nodes (22): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DueInput, DueSchedule (+14 more)

### Community 89 - "Community 89"
Cohesion: 0.12
Nodes (19): createFileFormats(), FileFormat, looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf() (+11 more)

### Community 90 - "Community 90"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 91 - "Community 91"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 92 - "Community 92"
Cohesion: 0.10
Nodes (22): ADR-0025, WaterMismatch, waterMismatch(), DECO_MODELS, DIVE_MODES, DIVER_SOURCES, EQUIPMENT_USES, EquipmentUse (+14 more)

### Community 93 - "Community 93"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 94 - "Community 94"
Cohesion: 0.11
Nodes (22): typescript@7.0.2, typescript@7.0.2, ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2 (+14 more)

### Community 95 - "Community 95"
Cohesion: 0.12
Nodes (22): BACKUP_FORMAT_VERSION, BackupManifest, buildManifest(), decryptFile(), decryptFileWith(), decryptStreamWith(), encryptFile(), encryptStream() (+14 more)

### Community 96 - "Community 96"
Cohesion: 0.12
Nodes (21): CENTRE_SOURCES, CentreService, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+13 more)

### Community 97 - "Community 97"
Cohesion: 0.11
Nodes (14): SsiClient, describe(), isObject(), keyDifferences(), pause(), recording(), runAnswerChecks(), sampleCount() (+6 more)

### Community 98 - "Community 98"
Cohesion: 0.11
Nodes (14): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+6 more)

### Community 99 - "Community 99"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 100 - "Community 100"
Cohesion: 0.09
Nodes (23): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+15 more)

### Community 101 - "Community 101"
Cohesion: 0.11
Nodes (16): ndl(), ProfileAssessment, Finding, LIMITS, dayNumber(), deepDays(), noFlyAfter(), SERIES_RULES (+8 more)

### Community 102 - "Community 102"
Cohesion: 0.10
Nodes (21): CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationExpiry, CertificationInput, CertificationView, EXPIRING_SOON_DAYS, expiryOf(), ImportReport (+13 more)

### Community 103 - "Community 103"
Cohesion: 0.11
Nodes (11): createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect(), lost() (+3 more)

### Community 104 - "Community 104"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 105 - "Community 105"
Cohesion: 0.15
Nodes (18): qr@0.7.2, cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043 (+10 more)

### Community 106 - "Community 106"
Cohesion: 0.12
Nodes (19): SUGGESTION_STATUSES, DiveProfileView, DiveProfileViewSchema, ADR-0041, AcceptBody, STATUS, SuggestionRouteDeps, ADR-0070 (+11 more)

### Community 107 - "Community 107"
Cohesion: 0.16
Nodes (21): managementOf(), EquipmentError, checkCost(), checkDates(), checkWindow(), createEquipmentService(), assemble(), checkResets() (+13 more)

### Community 108 - "Community 108"
Cohesion: 0.13
Nodes (19): diverBodyWeight, SuitType, SuitUndergarment, WeightingFelt, Suit, WeightingFeedback, DiveReference, LeadEstimateAnswer (+11 more)

### Community 109 - "Community 109"
Cohesion: 0.23
Nodes (20): DiveOrigin, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef(), MATERIAL_OF_TANK_TYPE, nonZero() (+12 more)

### Community 110 - "Community 110"
Cohesion: 0.18
Nodes (19): CatalogueCylinder, CylinderInput, cylindersOfDrafts(), CylinderView, draftOfCylinder(), fromCatalogue(), fromItemLastUse(), fromLastDive() (+11 more)

### Community 111 - "Community 111"
Cohesion: 0.14
Nodes (18): equipmentItem, serviceRecord, serviceRecordSchedule, serviceSchedule, equipmentList(), EquipmentRecordRead, today(), ADR-0034 (+10 more)

### Community 112 - "Community 112"
Cohesion: 0.13
Nodes (18): siteImport, siteImportStatus, PROBLEM_CODES, SiteImportRouteDeps, StartBody, STATUS, ADR-0021, SiteImportService (+10 more)

### Community 113 - "Community 113"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 114 - "Community 114"
Cohesion: 0.13
Nodes (18): defineTool(), Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), getDiverLoadings, HINT (+10 more)

### Community 115 - "Community 115"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 116 - "Community 116"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 117 - "Community 117"
Cohesion: 0.11
Nodes (19): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+11 more)

### Community 118 - "Community 118"
Cohesion: 0.11
Nodes (13): BACKUP_EXTENSION, BackupEncryption, BackupPart, BackupLog, BackupRunRow, BackupSettingsInput, BackupSettingsValues, DEFAULT_BACKUP_SETTINGS (+5 more)

### Community 119 - "Community 119"
Cohesion: 0.11
Nodes (12): Hint, HINT_CHANNEL, HINT_KINDS, HintAudience, HintKind, kinds, ADR-0020, ADR-0066 (+4 more)

### Community 120 - "Community 120"
Cohesion: 0.14
Nodes (14): nearest(), perMinute(), Series, summarize(), ChannelSeries, RecordedGas, Tank, tanksOf() (+6 more)

### Community 121 - "Community 121"
Cohesion: 0.22
Nodes (16): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), appendRun(), browserStats() (+8 more)

### Community 122 - "Community 122"
Cohesion: 0.12
Nodes (16): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+8 more)

### Community 123 - "Community 123"
Cohesion: 0.17
Nodes (12): sampleCount(), storedList(), tryParseJson(), APP_PARAMS, createSsiTransport(), exchange(), isObject(), SSI_APP_USER_AGENT (+4 more)

### Community 124 - "Community 124"
Cohesion: 0.12
Nodes (16): ADR-0013, ADR-0026, ADR-0028, ADR-0030, ADR-0031, ADR-0034, ADR-0035, ADR-0036 (+8 more)

### Community 125 - "Community 125"
Cohesion: 0.22
Nodes (13): AppLockProbe, Arguments, credentialOf(), FILE_OPTIONS, fileText(), finishedArguments(), parseArguments(), RestoreCliDeps (+5 more)

### Community 126 - "Community 126"
Cohesion: 0.15
Nodes (14): aiAccessLog, aiAccessSetting, AI_ACCESS_SCOPES, AiAccessError, KeyRow, LOG_DAYS, LOG_PAGE_MAX, LoggedCall (+6 more)

### Community 127 - "Community 127"
Cohesion: 0.14
Nodes (12): diveSiteExternalId, ids(), call(), createSite(), Method, Stream, ADR-0066, ExternalId (+4 more)

### Community 128 - "Community 128"
Cohesion: 0.20
Nodes (13): formatOf(), createImportService(), cancelImport(), createImport(), endWaiting(), expireWaiting(), openUpload(), processFile() (+5 more)

### Community 129 - "Community 129"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 130 - "Community 130"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 131 - "Community 131"
Cohesion: 0.17
Nodes (12): addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, laneFitsCapsule(), relations(), TimelineBar, TimelineBarGeometry, TimelineGapGeometry (+4 more)

### Community 132 - "Community 132"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 133 - "Community 133"
Cohesion: 0.15
Nodes (14): dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3, exports, name, private (+6 more)

### Community 134 - "Community 134"
Cohesion: 0.20
Nodes (10): DIVE_KINDS, ImportView, ImportViewSchema, toImportView(), ADR-0030, ImportRouteDeps, ADR-0044, ADR-0056 (+2 more)

### Community 135 - "Community 135"
Cohesion: 0.22
Nodes (11): fromOsm(), fromSsi(), fromWd(), HOUSE_REEF, osm(), site(), ssi(), ADR-0021 (+3 more)

### Community 136 - "Community 136"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 137 - "Community 137"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 138 - "Community 138"
Cohesion: 0.17
Nodes (10): EstimateInput, AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031 (+2 more)

### Community 139 - "Community 139"
Cohesion: 0.17
Nodes (6): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, uniqueWord()

### Community 140 - "Community 140"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 141 - "Community 141"
Cohesion: 0.23
Nodes (8): createBackupService(), checkSettings(), due(), enqueueScheduled(), readSettings(), saveSettings(), sweepRetention(), isUniqueViolation()

### Community 142 - "Community 142"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 143 - "Community 143"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 144 - "Community 144"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 145 - "Community 145"
Cohesion: 0.25
Nodes (7): Profile, SKETCH_HEIGHT, SKETCH_WIDTH, sketchPaths(), sketchScale(), ADR-0041, ADR-0041

### Community 146 - "Community 146"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 147 - "Community 147"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 148 - "Community 148"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 149 - "Community 149"
Cohesion: 0.31
Nodes (9): cylinderOf(), diverOf(), finite(), levelsOf(), oneOf(), pickDefined(), toolsParams(), of() (+1 more)

### Community 150 - "Community 150"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 151 - "Community 151"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 152 - "Community 152"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 153 - "Community 153"
Cohesion: 0.25
Nodes (6): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, CylinderInput, EstimateCylinder

### Community 154 - "Community 154"
Cohesion: 0.22
Nodes (4): Answer, ApiClient, createApiClient(), ADR-0004

### Community 155 - "Community 155"
Cohesion: 0.25
Nodes (8): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, MODULE_IMPORTS, moduleRules, target()

### Community 156 - "Community 156"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 157 - "Community 157"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 158 - "Community 158"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 159 - "Community 159"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 160 - "Community 160"
Cohesion: 0.33
Nodes (5): CertificationError, cleaned(), requireManagedDiver(), rowOrThrow(), isCalendarDate()

### Community 161 - "Community 161"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 162 - "Community 162"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 163 - "Community 163"
Cohesion: 0.40
Nodes (4): IMPORTABLE, splitImportable(), ADR-0037, ADR-0037

### Community 165 - "Community 165"
Cohesion: 0.40
Nodes (4): label(), CompareSubject, facts, subjects

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
Cohesion: 0.67
Nodes (3): planSketch(), shape(), ADR-0033

### Community 171 - "Community 171"
Cohesion: 0.50
Nodes (4): ToolsParams, SuitType, SuitUndergarment, ListControl

### Community 173 - "Community 173"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 174 - "Community 174"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 175 - "Community 175"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1165 isolated node(s):** `ADR-0048`, `ADR-0016`, `ADR-0016`, `ADR-0038`, `ADR-0026` (+1160 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 2981 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **7 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 4` to `Community 0`, `Community 5`, `Community 9`, `Community 11`, `Community 12`, `Community 13`, `Community 15`, `Community 16`, `Community 17`, `Community 19`, `Community 20`, `Community 22`, `Community 24`, `Community 25`, `Community 26`, `Community 27`, `Community 28`, `Community 30`, `Community 35`, `Community 40`, `Community 45`, `Community 47`, `Community 49`, `Community 65`, `Community 66`, `Community 76`, `Community 78`, `Community 82`, `Community 100`, `Community 102`, `Community 108`, `Community 111`, `Community 118`, `Community 119`, `Community 126`, `Community 127`?**
  _High betweenness centrality (0.037) - this node is a cross-community bridge._
- **What connects `ADR-0048`, `ADR-0016`, `ADR-0016` to the rest of the system?**
  _1165 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.023038469897848295 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 2` to `Community 1`, `Community 3`, `Community 37`, `Community 6`, `Community 7`, `Community 41`, `Community 74`, `Community 10`, `Community 75`, `Community 81`, `Community 50`, `Community 117`, `Community 21`, `Community 23`, `Community 29`?**
  _High betweenness centrality (0.036) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.04752841660058155 - nodes in this community are weakly interconnected._
- **Why does `@playwright/test` connect `Community 44` to `Community 130`, `Community 39`, `Community 72`, `Community 74`, `Community 139`, `Community 140`, `Community 143`, `Community 80`, `Community 144`, `Community 87`, `Community 57`, `Community 90`, `Community 63`?**
  _High betweenness centrality (0.030) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.026768103691180614 - nodes in this community are weakly interconnected._