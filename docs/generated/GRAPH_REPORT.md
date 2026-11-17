# Graph Report - dive_hub  (2026-11-17)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 7339 nodes · 22886 edges · 173 communities (168 shown, 5 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 223 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `36a2c0ff`
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
- Community 172

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
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `Removal` --references--> `ProviderServiceError`  [EXTRACTED]
  apps/server/src/providers/push/push-service.ts → apps/server/src/providers/registry.ts
- `AuthOptions` --references--> `Db`  [EXTRACTED]
  apps/server/src/auth/auth.ts → apps/server/src/db/client.ts

## Import Cycles
- None detected.

## Communities (173 total, 5 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (215): user, buddySuggestion, dive, diveFinding, diver, ADR-0044, ADR-0013, Assessment (+207 more)

### Community 1 - "Community 1"
Cohesion: 0.05
Nodes (194): ChangePassword(), DisplaySettings(), SecuritySettings(), Sessions(), useDescribeAgent(), AdminSettings(), DeleteUserDialog(), Invitations() (+186 more)

### Community 2 - "Community 2"
Cohesion: 0.03
Nodes (155): assessmentQuery(), AssessmentView, samplesQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime (+147 more)

### Community 3 - "Community 3"
Cohesion: 0.02
Nodes (144): DIVE_FIELDS, DiveOrigin, OutgoingDive, REFERENCE_PREFIX, createSsiAdapter(), buddyListOf(), called(), confirmed() (+136 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (149): allButDive(), AnyLogbookCheck, CandidateView, continuationCandidateQuery(), ContinuationCandidateView, deletedDivesOn(), DeletedDivesPageView, deletedDivesQuery() (+141 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (135): AiAccessLogEntry, aiAccessLogQuery(), aiAccessQuery(), AiAccessView, invitationsQuery(), InvitationView, SessionView, setupQuery() (+127 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (125): BackupRunView, BackupSettingsView, backupsQuery(), devicesQuery(), diversQuery(), isUnauthorized(), ExternalIdView, Position (+117 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (114): Series, SsiClient, SsiRecord, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, keepTraining() (+106 more)

### Community 8 - "Community 8"
Cohesion: 0.04
Nodes (117): LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, renumber(), renumberStatement(), ADR-0048, AppendRow, channelsOf() (+109 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (88): createServer(), createAuth(), loadAuthSecret(), acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS (+80 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (108): CentreService, CentreRouteDeps, LEAD_PLACEMENTS, UTC_OFFSET_SOURCES, DiverService, downsampleMinMax(), DiveCertifications, LogbookCheckService (+100 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (84): connection, providerLease, createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE (+76 more)

### Community 12 - "Community 12"
Cohesion: 0.04
Nodes (92): adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, AuthOptions, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, SessionUser, ADR-0011 (+84 more)

### Community 13 - "Community 13"
Cohesion: 0.05
Nodes (90): diverExternalId, diveSite, siteSource, providerCylinderOf(), leadTotalKg(), SharedText(), ToolContext, dataIdsOf() (+82 more)

### Community 14 - "Community 14"
Cohesion: 0.02
Nodes (95): accountListEntry, actorType, aiAccessOutcome, backupRunKind, backupRunStatus, candidateResolution, centreSource, CERTIFICATION_IMAGE_STATES (+87 more)

### Community 15 - "Community 15"
Cohesion: 0.05
Nodes (89): Me, AcceptedBuddySuggestionView, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView, candidatesQuery() (+81 more)

### Community 16 - "Community 16"
Cohesion: 0.05
Nodes (86): Queryable, diveCentre, jointDive, logbookCheckAnswer, logbookCheckDiveAnswer, ADR-0016, keepApart(), keepDive() (+78 more)

### Community 17 - "Community 17"
Cohesion: 0.04
Nodes (80): replyCertificationError, STATUS, CertificationView, Day, ADR-0050, CardParams, CertificationRouteDeps, errors (+72 more)

### Community 18 - "Community 18"
Cohesion: 0.03
Nodes (70): auth, BackupServiceDeps, createServices(), ServiceParts, ADR-0027, ADR-0030, ADR-0061, ADR-0063 (+62 more)

### Community 19 - "Community 19"
Cohesion: 0.03
Nodes (70): diveLease, original, providerConflict, push, pushRun, recording, revision, waitingPush (+62 more)

### Community 20 - "Community 20"
Cohesion: 0.05
Nodes (68): equipmentDueQuery(), EquipmentDueView, EquipmentDueNotice(), ADR-0034, avatarTone(), initials(), dismissDue(), dismissedDueDigest() (+60 more)

### Community 21 - "Community 21"
Cohesion: 0.05
Nodes (78): CertificationService, CYLINDER_MATERIALS, DIVE_IMPORT_MODES, ErrorAnswer, PROBLEM_CODES, providerProblem(), toImportView(), withDecisions() (+70 more)

### Community 22 - "Community 22"
Cohesion: 0.05
Nodes (71): EMPTY_SITE_IMPORT_COUNTS, ImportArea, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053 (+63 more)

### Community 23 - "Community 23"
Cohesion: 0.04
Nodes (69): ConflictChoices, ConflictFieldView, providersQuery(), providerStatusQuery(), RequirementView, focusFirstIn(), useFocusBackWhenClosed(), holdLeave() (+61 more)

### Community 24 - "Community 24"
Cohesion: 0.04
Nodes (56): automationOffers(), AutomationSettings, ConnectionService, Credentials, FETCH_INTERVALS, FetchInterval, purpose(), SignInFields (+48 more)

### Community 25 - "Community 25"
Cohesion: 0.08
Nodes (74): DEFAULT_SSI_URL, APP_KEYS, appRow(), BuddySaved, byId(), changedKeys(), deleteTestDive(), deleteTestEntries() (+66 more)

### Community 26 - "Community 26"
Cohesion: 0.06
Nodes (66): waitingOf(), close(), currentRemote(), createDiveChanges(), baseDiveOf(), conflictFields(), fillBuddies(), fillSite() (+58 more)

### Community 27 - "Community 27"
Cohesion: 0.05
Nodes (66): AiAccessScope, seesPositions(), VerifiedAccess, diversOf(), checkedByRunTool, createMcpServer(), INSTRUCTIONS, MCP_SERVER_INFO (+58 more)

### Community 28 - "Community 28"
Cohesion: 0.06
Nodes (59): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, ambientPressureBar() (+51 more)

### Community 29 - "Community 29"
Cohesion: 0.05
Nodes (55): typedId(), diverBodyWeight, lead, participant, DiverError, ADR-0016, ADR-0053, ACCOUNT_PATTERN (+47 more)

### Community 30 - "Community 30"
Cohesion: 0.04
Nodes (43): ParticipantRole, ActionContext, Direction, DiveExport, DiveListing, FIND_BY, FindBy, Operation (+35 more)

### Community 31 - "Community 31"
Cohesion: 0.06
Nodes (55): LogbookPage, PAGE_SIZE, Buddies(), COUNT_OF, DiveList(), DiveRow(), MonthHead(), ShowOnly() (+47 more)

### Community 32 - "Community 32"
Cohesion: 0.07
Nodes (51): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+43 more)

### Community 33 - "Community 33"
Cohesion: 0.07
Nodes (57): ConnectionRow, WaitingPushOptions, audienceOfConnection(), audienceOfDive(), audienceOfDiver(), emitHint(), Hint, HINT_KINDS (+49 more)

### Community 34 - "Community 34"
Cohesion: 0.05
Nodes (53): pushRunItem, PushRunLeftOut, diveListFilter(), notAtProvider(), Blocked, classify(), FailureOutcome, failureOutcome() (+45 more)

### Community 35 - "Community 35"
Cohesion: 0.07
Nodes (57): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+49 more)

### Community 36 - "Community 36"
Cohesion: 0.07
Nodes (57): ComputerChoice, DiveImportMode, DiveKind, siteRef(), kindOfMode(), deviceDiver(), closeFields(), entryOf() (+49 more)

### Community 37 - "Community 37"
Cohesion: 0.09
Nodes (44): name, private, type, keys, centresQuery(), CentreView, sitesQuery(), SiteView (+36 more)

### Community 38 - "Community 38"
Cohesion: 0.04
Nodes (54): Change(), Changes(), historyRoute, Made(), REVISION_CAUSES, PositionSchema, AdoptBody, Country (+46 more)

### Community 39 - "Community 39"
Cohesion: 0.07
Nodes (45): looksLikeZip(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy(), ADR-0024 (+37 more)

### Community 40 - "Community 40"
Cohesion: 0.05
Nodes (51): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, cancelWaitingImports() (+43 more)

### Community 41 - "Community 41"
Cohesion: 0.07
Nodes (51): NoFly, noFlyAfter(), RecordingSummary, RecordingFacts, getDiverLoadings, DEFAULT_GF_HIGH, residualSeedOf(), depthSamples() (+43 more)

### Community 42 - "Community 42"
Cohesion: 0.08
Nodes (49): BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32Encode(), bech32HrpExpand(), bech32Polymod() (+41 more)

### Community 43 - "Community 43"
Cohesion: 0.06
Nodes (34): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+26 more)

### Community 44 - "Community 44"
Cohesion: 0.05
Nodes (37): cylinder, duplicateCandidate, importJob, recordingEvent, ImportService, noAppends, race(), waitingForLocks() (+29 more)

### Community 45 - "Community 45"
Cohesion: 0.06
Nodes (38): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+30 more)

### Community 46 - "Community 46"
Cohesion: 0.07
Nodes (45): cylinderCatalogueQuery(), DiveGearItemView, diveGearQuery(), DiveGearView, EquipmentCategory, EquipmentDetailView, equipmentItemQuery(), EquipmentItemView (+37 more)

### Community 47 - "Community 47"
Cohesion: 0.05
Nodes (46): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, CertificationViewSchema, Services, DiveProfileViewSchema (+38 more)

### Community 48 - "Community 48"
Cohesion: 0.10
Nodes (42): tidyName(), foldName(), nameDiffers(), Buddy, createBuddyService(), createFrom(), linkEntry(), listAccount() (+34 more)

### Community 49 - "Community 49"
Cohesion: 0.06
Nodes (35): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CertificationError, CertificationExpiry (+27 more)

### Community 50 - "Community 50"
Cohesion: 0.06
Nodes (38): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+30 more)

### Community 51 - "Community 51"
Cohesion: 0.05
Nodes (42): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+34 more)

### Community 52 - "Community 52"
Cohesion: 0.06
Nodes (42): WEIGHTING_FELT, ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, seriesOf(), minute() (+34 more)

### Community 53 - "Community 53"
Cohesion: 0.06
Nodes (36): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+28 more)

### Community 54 - "Community 54"
Cohesion: 0.09
Nodes (34): BottomTimeQuery, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033, ADR-0071, DEFAULT_PPO2 (+26 more)

### Community 55 - "Community 55"
Cohesion: 0.07
Nodes (42): CylinderMaterial, cylinderOf(), cylinderText(), diverOf(), diverText(), finite(), kept, LEAD_MATERIALS (+34 more)

### Community 56 - "Community 56"
Cohesion: 0.09
Nodes (39): ProviderCylinder, ConflictEntry, ConflictField, createConflictService(), loadWaiting(), stillDiffering(), fieldsOf(), isExists() (+31 more)

### Community 57 - "Community 57"
Cohesion: 0.05
Nodes (42): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+34 more)

### Community 58 - "Community 58"
Cohesion: 0.06
Nodes (35): SUIT_TYPES, SUIT_UNDERGARMENTS, DiverCertifications, replyDiverError, STATUS, CertificationSummary, DiverDetailsView, DiverDetailsViewSchema (+27 more)

### Community 59 - "Community 59"
Cohesion: 0.08
Nodes (39): buildJoined(), partInput(), partsOfJoined(), rebuildJoined(), RecordingRow, ADR-0063, writeJoined(), reviseDive() (+31 more)

### Community 60 - "Community 60"
Cohesion: 0.06
Nodes (30): LoadingsView, CustomTypeOptions, i18next, initI18n(), resources, ADR-0014, LOADING_SOURCES, loadingNumber() (+22 more)

### Community 61 - "Community 61"
Cohesion: 0.08
Nodes (38): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+30 more)

### Community 62 - "Community 62"
Cohesion: 0.16
Nodes (37): buildApp(), assessmentRoutes(), isAdmin(), requireAdmin(), requireUser(), userOf(), BackupRouteDeps, backupRoutes() (+29 more)

### Community 63 - "Community 63"
Cohesion: 0.11
Nodes (37): externalDiver(), mergeExternalDiver(), participations(), open(), createConnectionService(), checkAutomation(), checkFollowNumbers(), expiryOf() (+29 more)

### Community 64 - "Community 64"
Cohesion: 0.08
Nodes (33): date(), FitMessage, FitMessages, GARMIN_PRODUCTS, num(), position(), positive(), RECORD_CHANNELS (+25 more)

### Community 65 - "Community 65"
Cohesion: 0.10
Nodes (32): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0051 (+24 more)

### Community 66 - "Community 66"
Cohesion: 0.07
Nodes (27): continuationAppend, device, equipmentItem, managesDiveDiver(), CandidateError, CandidateRow, createCandidateService(), lockMine() (+19 more)

### Community 67 - "Community 67"
Cohesion: 0.11
Nodes (31): BACKUP_SKIPPED_DIRS, BackupDecryptionKey, openArchiveStreamingWith(), assertPgTool(), execFileAsync, pgCommand(), pgErrorDetail(), pgFailureMessage() (+23 more)

### Community 68 - "Community 68"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 69 - "Community 69"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 70 - "Community 70"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 71 - "Community 71"
Cohesion: 0.08
Nodes (31): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+23 more)

### Community 72 - "Community 72"
Cohesion: 0.08
Nodes (34): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+26 more)

### Community 73 - "Community 73"
Cohesion: 0.08
Nodes (28): AssessmentService, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView(), ADR-0036 (+20 more)

### Community 74 - "Community 74"
Cohesion: 0.08
Nodes (31): AI_ACCESS_NAME_MAX, aiAccessLog, aiAccessSetting, Access, AccessView, AiAccessRouteDeps, LogEntryView, Scope (+23 more)

### Community 75 - "Community 75"
Cohesion: 0.06
Nodes (29): account, accountRelations, apikey, rateLimit, session, sessionRelations, ADR-0011, ADR-0012 (+21 more)

### Community 76 - "Community 76"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 77 - "Community 77"
Cohesion: 0.09
Nodes (23): createFileFormats(), FileFormat, FitAdapter, looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), SuuntoJsonAdapter, ADR-0037 (+15 more)

### Community 78 - "Community 78"
Cohesion: 0.12
Nodes (27): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+19 more)

### Community 79 - "Community 79"
Cohesion: 0.11
Nodes (29): massIn(), Dive, draftOfLead(), draftOfWeighting(), emptyLead(), hasThickness(), LastWeighting, LEAD_PLACEMENTS (+21 more)

### Community 80 - "Community 80"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 81 - "Community 81"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 82 - "Community 82"
Cohesion: 0.10
Nodes (25): ENGINE_VERSION, EVIDENCE, Evidence, FindingValues, RULE_IDS, RuleId, SEVERITIES, Severity (+17 more)

### Community 83 - "Community 83"
Cohesion: 0.09
Nodes (21): Tx, AppendUndo, ADR-0020, ADR-0056, ADR-0066, withRevisionHints(), ChangeListener, ADR-0015 (+13 more)

### Community 84 - "Community 84"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 85 - "Community 85"
Cohesion: 0.10
Nodes (22): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearQueue(), conflictAtSsi() (+14 more)

### Community 86 - "Community 86"
Cohesion: 0.11
Nodes (23): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+15 more)

### Community 87 - "Community 87"
Cohesion: 0.10
Nodes (22): BACKUP_RUN_TASK, backupTask(), ADR-0069, CERTIFICATION_IMAGE_TASK, PurgeService, EXPIRE_WAITING_IMPORTS_TASK, PROCESS_IMPORT_TASK, PUSH_RUN_STEP_TASK (+14 more)

### Community 88 - "Community 88"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 89 - "Community 89"
Cohesion: 0.11
Nodes (24): equipmentUse, DiveGear, ensureItemForDevice(), GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow (+16 more)

### Community 90 - "Community 90"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 91 - "Community 91"
Cohesion: 0.16
Nodes (26): ImportView, Logbook(), ImportContext, ImportFilesButton(), ImportList(), ImportNotices(), ImportPanel(), ImportProvider() (+18 more)

### Community 92 - "Community 92"
Cohesion: 0.09
Nodes (22): diveAssessment, Check, Deleted, DiveView, Outcome, RecordingView, ADR-0063, Candidate (+14 more)

### Community 93 - "Community 93"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 94 - "Community 94"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 95 - "Community 95"
Cohesion: 0.08
Nodes (25): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+17 more)

### Community 96 - "Community 96"
Cohesion: 0.08
Nodes (24): devDependencies, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2, @testing-library/react@^16.3.3 (+16 more)

### Community 97 - "Community 97"
Cohesion: 0.11
Nodes (23): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+15 more)

### Community 98 - "Community 98"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 99 - "Community 99"
Cohesion: 0.09
Nodes (20): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+12 more)

### Community 100 - "Community 100"
Cohesion: 0.12
Nodes (16): ResponseState, whenResponseGone(), StoredBlob, PlacedArchive, ANSWER, looksLikeRecipientPrivateKey(), PlacedArchive, readParts() (+8 more)

### Community 101 - "Community 101"
Cohesion: 0.16
Nodes (22): useCylinderProblem(), DiveEditForm(), Draft, draftOf(), FIELD_OF, localStart(), nanToNull(), round1() (+14 more)

### Community 102 - "Community 102"
Cohesion: 0.11
Nodes (14): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+6 more)

### Community 103 - "Community 103"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 104 - "Community 104"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 105 - "Community 105"
Cohesion: 0.15
Nodes (18): qr@0.7.2, cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043 (+10 more)

### Community 106 - "Community 106"
Cohesion: 0.14
Nodes (18): Answer, CatalogueCylinder, CylinderInput, CylinderView, draftOfCylinder(), fromCatalogue(), fromItemLastUse(), LastCylinder (+10 more)

### Community 107 - "Community 107"
Cohesion: 0.12
Nodes (18): CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043, CreateBody (+10 more)

### Community 108 - "Community 108"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 109 - "Community 109"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 110 - "Community 110"
Cohesion: 0.16
Nodes (15): errorMessage(), parseWithFitFileParser(), formatOf(), createImportService(), cancelImport(), createImport(), endWaiting(), expireWaiting() (+7 more)

### Community 111 - "Community 111"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 112 - "Community 112"
Cohesion: 0.11
Nodes (18): BOTTOM_TIME_RULES, PLAN_RULES, PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody (+10 more)

### Community 113 - "Community 113"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 114 - "Community 114"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 115 - "Community 115"
Cohesion: 0.11
Nodes (13): BACKUP_EXTENSION, BackupEncryption, BackupPart, BackupLog, BackupRunRow, BackupSettingsInput, BackupSettingsValues, DEFAULT_BACKUP_SETTINGS (+5 more)

### Community 116 - "Community 116"
Cohesion: 0.17
Nodes (16): UtcOffsetSource, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput, round3() (+8 more)

### Community 117 - "Community 117"
Cohesion: 0.16
Nodes (16): inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), placeLocalTime(), Position, ADR-0030, wallClockMs() (+8 more)

### Community 118 - "Community 118"
Cohesion: 0.14
Nodes (16): SUGGESTION_STATUSES, AcceptBody, STATUS, SuggestionRouteDeps, ADR-0070, SuggestionService, DiverRef, Offset (+8 more)

### Community 119 - "Community 119"
Cohesion: 0.14
Nodes (16): SuitType, SuitUndergarment, WeightingFelt, Suit, WeightingFeedback, LeadEstimateAnswer, Confidence, EstimateAdjustment (+8 more)

### Community 120 - "Community 120"
Cohesion: 0.12
Nodes (16): ADR-0013, ADR-0026, ADR-0028, ADR-0030, ADR-0031, ADR-0034, ADR-0035, ADR-0036 (+8 more)

### Community 121 - "Community 121"
Cohesion: 0.15
Nodes (12): nearest(), perMinute(), Series, ChannelSeries, RecordedGas, Tank, TiedCylinder, ADR-0045 (+4 more)

### Community 122 - "Community 122"
Cohesion: 0.22
Nodes (13): AppLockProbe, Arguments, credentialOf(), FILE_OPTIONS, fileText(), finishedArguments(), parseArguments(), RestoreCliDeps (+5 more)

### Community 123 - "Community 123"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 124 - "Community 124"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 125 - "Community 125"
Cohesion: 0.17
Nodes (13): BACKUP_FORMAT_VERSION, BackupManifest, buildManifest(), decryptFile(), decryptFileWith(), decryptStreamWith(), encryptionKeyFingerprint(), fileTreeChecksum() (+5 more)

### Community 126 - "Community 126"
Cohesion: 0.16
Nodes (14): serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, DueEntry, ScheduleView, equipmentList(), EquipmentListFilter (+6 more)

### Community 127 - "Community 127"
Cohesion: 0.26
Nodes (15): managementOf(), createEquipmentService(), assemble(), detailOf(), lastCylinderOf(), managedItem(), managedRecord(), managedSchedule() (+7 more)

### Community 128 - "Community 128"
Cohesion: 0.13
Nodes (10): EGYPT, ExternalId, HOUSE_REEF, Method, Position, Revision, Site, SiteImport (+2 more)

### Community 129 - "Community 129"
Cohesion: 0.14
Nodes (7): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, headers, uniqueWord()

### Community 130 - "Community 130"
Cohesion: 0.18
Nodes (11): addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, relations(), TimelineBar, TimelineBarGeometry, TimelineGapGeometry, TimelineGeometry (+3 more)

### Community 131 - "Community 131"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 132 - "Community 132"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 133 - "Community 133"
Cohesion: 0.21
Nodes (9): tarDirectory(), createBackupService(), checkSettings(), due(), enqueueScheduled(), readSettings(), saveSettings(), sweepRetention() (+1 more)

### Community 134 - "Community 134"
Cohesion: 0.17
Nodes (10): EstimateInput, AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031 (+2 more)

### Community 135 - "Community 135"
Cohesion: 0.22
Nodes (11): flag(), idText(), isObject(), numberOrNull(), readSsiSite(), SsiSiteValues, WATER_OF_BOW, ALPHA3_TO_ALPHA2 (+3 more)

### Community 136 - "Community 136"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 137 - "Community 137"
Cohesion: 0.23
Nodes (10): ProviderImportPlan, ADR-0025, WaterMismatch, waterMismatch(), RemoteSite, SsiLogbookSite, SiteWaterType, WATER_DENSITY (+2 more)

### Community 138 - "Community 138"
Cohesion: 0.18
Nodes (10): EquipmentError, ADR-0034, ADR-0053, checkCost(), checkDates(), checkWindow(), checkResets(), checkSchedule() (+2 more)

### Community 139 - "Community 139"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 140 - "Community 140"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 141 - "Community 141"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 142 - "Community 142"
Cohesion: 0.25
Nodes (7): Profile, SKETCH_HEIGHT, SKETCH_WIDTH, sketchPaths(), sketchScale(), ADR-0041, ADR-0041

### Community 143 - "Community 143"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 144 - "Community 144"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 145 - "Community 145"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 146 - "Community 146"
Cohesion: 0.24
Nodes (9): LeadHint(), orUndefined(), ADR-0025, ADR-0031, CylinderDraft, LeadWater, massFromDisplay(), kg() (+1 more)

### Community 147 - "Community 147"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 148 - "Community 148"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 149 - "Community 149"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 151 - "Community 151"
Cohesion: 0.25
Nodes (8): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, MODULE_IMPORTS, moduleRules, target()

### Community 152 - "Community 152"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 153 - "Community 153"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 154 - "Community 154"
Cohesion: 0.29
Nodes (5): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, EstimateCylinder

### Community 155 - "Community 155"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 156 - "Community 156"
Cohesion: 0.29
Nodes (6): label(), axe-core@^4.13.0, CompareSubject, facts, subjects, axe-core

### Community 157 - "Community 157"
Cohesion: 0.29
Nodes (6): itemIds(), ADR-0026, ADR-0034, Usage, UsageDive, UsageRow

### Community 158 - "Community 158"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 159 - "Community 159"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 160 - "Community 160"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 161 - "Community 161"
Cohesion: 0.40
Nodes (4): IMPORTABLE, splitImportable(), ADR-0037, ADR-0037

### Community 162 - "Community 162"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 163 - "Community 163"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 164 - "Community 164"
Cohesion: 0.40
Nodes (4): TAGS, ADR-0023, { stdout, stderr }, total

### Community 165 - "Community 165"
Cohesion: 0.67
Nodes (3): planSketch(), shape(), ADR-0033

### Community 167 - "Community 167"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 168 - "Community 168"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 169 - "Community 169"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1170 isolated node(s):** `ADR-0044`, `ADR-0013`, `ADR-0036`, `ADR-0011`, `ADR-0069` (+1165 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 2989 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **5 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 0` to `Community 3`, `Community 8`, `Community 9`, `Community 11`, `Community 12`, `Community 13`, `Community 14`, `Community 16`, `Community 17`, `Community 18`, `Community 19`, `Community 22`, `Community 24`, `Community 26`, `Community 27`, `Community 29`, `Community 157`, `Community 33`, `Community 34`, `Community 36`, `Community 41`, `Community 44`, `Community 47`, `Community 48`, `Community 49`, `Community 54`, `Community 56`, `Community 59`, `Community 66`, `Community 71`, `Community 72`, `Community 74`, `Community 75`, `Community 81`, `Community 86`, `Community 89`, `Community 92`, `Community 95`, `Community 115`, `Community 117`, `Community 126`?**
  _High betweenness centrality (0.049) - this node is a cross-community bridge._
- **What connects `ADR-0044`, `ADR-0013`, `ADR-0036` to the rest of the system?**
  _1170 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.02209691167792276 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 37` to `Community 1`, `Community 2`, `Community 97`, `Community 4`, `Community 5`, `Community 6`, `Community 101`, `Community 109`, `Community 46`, `Community 15`, `Community 146`, `Community 20`, `Community 23`, `Community 91`, `Community 31`?**
  _High betweenness centrality (0.031) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.04944934403931377 - nodes in this community are weakly interconnected._
- **Why does `react` connect `Community 37` to `Community 1`, `Community 2`, `Community 97`, `Community 4`, `Community 101`, `Community 6`, `Community 5`, `Community 105`, `Community 109`, `Community 46`, `Community 15`, `Community 79`, `Community 20`, `Community 23`, `Community 91`, `Community 31`?**
  _High betweenness centrality (0.031) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.032423094810976015 - nodes in this community are weakly interconnected._