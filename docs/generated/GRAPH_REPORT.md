# Graph Report - dive_hub  (2026-11-18)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 7365 nodes · 23001 edges · 178 communities (170 shown, 8 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 223 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `9dd4a6a5`
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
- Community 177

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 204 edges
2. `Button()` - 198 edges
3. `useErrorText()` - 169 edges
4. `useDisplay()` - 161 edges
5. `Muted()` - 139 edges
6. `drizzle-orm` - 139 edges
7. `unwrap()` - 138 edges
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
- `SsiSiteValues` --references--> `SiteWaterType`  [EXTRACTED]
  apps/server/src/providers/ssi/ssi-site-record.ts → apps/server/src/vocabulary.ts
- `askedAt()` --calls--> `DiveError`  [EXTRACTED]
  apps/server/src/dives/continuation/append-service.ts → apps/server/src/dives/dive-service.ts

## Import Cycles
- None detected.

## Communities (178 total, 8 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (174): account, buddySuggestion, diveAssessment, parseHint(), ADR-0013, ADR-0011, dumpToolAvailable(), execFileAsync (+166 more)

### Community 1 - "Community 1"
Cohesion: 0.05
Nodes (184): ChangePassword(), DisplaySettings(), Sessions(), useDescribeAgent(), AdminSettings(), DeleteUserDialog(), Invitations(), Invite() (+176 more)

### Community 2 - "Community 2"
Cohesion: 0.03
Nodes (151): diversQuery(), cylinderCatalogueQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery() (+143 more)

### Community 3 - "Community 3"
Cohesion: 0.03
Nodes (152): OVERRIDABLE_FIELDS, OverridableField, participant, renumber(), renumberStatement(), ADR-0048, accountKey(), applyExternalId() (+144 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (155): ComputerChoice, DiveImportMode, DiveKind, importOriginal, ProviderImportPlan, providerSiteData, UtcOffsetSource, managedDiverIds() (+147 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (149): AcceptedBuddySuggestionView, allButDive(), AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionView, CandidateView, continuationCandidateQuery(), ContinuationCandidateView (+141 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (142): keys, buddiesForQuery(), buddiesQuery(), BuddyView, ConflictChoices, ConflictEntryView, ConflictFieldView, ConflictsOfConnection (+134 more)

### Community 7 - "Community 7"
Cohesion: 0.04
Nodes (117): EMPTY_SITE_IMPORT_COUNTS, ImportArea, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053 (+109 more)

### Community 8 - "Community 8"
Cohesion: 0.02
Nodes (117): accountRelations, apikey, rateLimit, session, sessionRelations, ADR-0011, ADR-0012, ADR-0035 (+109 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (111): LEAD_PLACEMENTS, UTC_OFFSET_SOURCES, WEIGHTING_FELT, sacOfDive(), DiveCertifications, AppendDeps, AppendOutcome, AppendService (+103 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (110): react-dom@^19.3.0, LoadingsView, divesQuery(), isUnauthorized(), App(), BackupsPage, CentrePage, CentresPage (+102 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (82): acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), probeAppLock() (+74 more)

### Community 12 - "Community 12"
Cohesion: 0.04
Nodes (101): Me, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery(), candidatesQuery(), deletedDivesTotal(), DiveSummary, isDiveCheck() (+93 more)

### Community 13 - "Community 13"
Cohesion: 0.04
Nodes (105): centreQuery(), centresQuery(), CentreView, ExternalIdView, Position, running(), SiteImportArea, siteImportsQuery() (+97 more)

### Community 14 - "Community 14"
Cohesion: 0.04
Nodes (81): AuthOptions, auth, BackupServiceDeps, createServices(), ServiceParts, Services, ADR-0027, ADR-0030 (+73 more)

### Community 15 - "Community 15"
Cohesion: 0.03
Nodes (80): connection, dive, diveLease, providerConflict, push, pushRun, revision, waitingPush (+72 more)

### Community 16 - "Community 16"
Cohesion: 0.04
Nodes (85): CertificationService, ConnectionRow, MATCHING_WINDOWS, providerGoneDive, AssessmentRefresh, ADR-0036, ADR-0053, IdParams (+77 more)

### Community 17 - "Community 17"
Cohesion: 0.05
Nodes (90): diveFingerprint, ParticipantRole, PushLeftOut, waitingOf(), open(), close(), createConnectionService(), checkAutomation() (+82 more)

### Community 18 - "Community 18"
Cohesion: 0.04
Nodes (84): BackupRunView, backupSettingsQuery(), BackupSettingsView, backupsQuery(), SiteDataView, ADR-0030, bodyWeightsQuery(), devicesQuery() (+76 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (78): sessionIdOf(), hashPassword(), invitation, passwordReset, userRole, AttemptLimit, clientKey(), limitAttempts() (+70 more)

### Community 20 - "Community 20"
Cohesion: 0.03
Nodes (72): createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities(), ADR-0062 (+64 more)

### Community 21 - "Community 21"
Cohesion: 0.04
Nodes (75): diveSiteExternalId, createSsiAdapter(), buddyListOf(), called(), logbookOf(), open(), saveBuddy(), providerReason() (+67 more)

### Community 22 - "Community 22"
Cohesion: 0.03
Nodes (78): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+70 more)

### Community 23 - "Community 23"
Cohesion: 0.05
Nodes (75): Queryable, logbookCheckAnswer, logbookCheckDiveAnswer, keepApart(), keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration() (+67 more)

### Community 24 - "Community 24"
Cohesion: 0.04
Nodes (72): DIVE_IMPORT_MODES, DIVE_KINDS, SUGGESTION_STATUSES, DiveProfileView, DiveProfileViewSchema, ADR-0041, Change(), Changes() (+64 more)

### Community 25 - "Community 25"
Cohesion: 0.06
Nodes (64): accountListEntry, diverExternalId, providerLease, tidyName(), DiverDetails, Caller, foldName(), nameDiffers() (+56 more)

### Community 26 - "Community 26"
Cohesion: 0.09
Nodes (63): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, assessmentRoutes(), AI_ACCESS_NAME_MAX (+55 more)

### Community 27 - "Community 27"
Cohesion: 0.05
Nodes (49): createServer(), createAuth(), loadAuthSecret(), workerDeps(), Config, loadConfig(), ADR-0011, ADR-0021 (+41 more)

### Community 28 - "Community 28"
Cohesion: 0.04
Nodes (59): AssessmentService, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView(), ADR-0036 (+51 more)

### Community 29 - "Community 29"
Cohesion: 0.04
Nodes (47): ActionContext, Direction, DiveExport, DiveListing, FIND_BY, FindBy, Operation, OPERATIONS (+39 more)

### Community 30 - "Community 30"
Cohesion: 0.04
Nodes (52): continuationAppend, device, diverBodyWeight, diverManagement, equipmentUse, lead, LeadPlacement, SuitType (+44 more)

### Community 31 - "Community 31"
Cohesion: 0.06
Nodes (50): errorMessage(), looksLikeZip(), placeFile(), placeFiles(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites() (+42 more)

### Community 32 - "Community 32"
Cohesion: 0.05
Nodes (58): diversOf(), SiteRow, Cursor, cursorAt(), DateOnly(), defineTool(), Detail, diversAsked() (+50 more)

### Community 33 - "Community 33"
Cohesion: 0.05
Nodes (57): PARTICIPANT_ROLES, DIVE_ONLY_PATTERN, ErrorAnswer, PROBLEM_CODES, ProblemCode, ProblemSchema, providerProblem(), storedProblemCode() (+49 more)

### Community 34 - "Community 34"
Cohesion: 0.06
Nodes (56): SecuritySettings(), pushRunActive(), pushRunBody(), PushRunChoices, PushRunPreview, pushRunPreviewQuery(), pushRunQuery(), pushRunsQuery() (+48 more)

### Community 35 - "Community 35"
Cohesion: 0.07
Nodes (57): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+49 more)

### Community 36 - "Community 36"
Cohesion: 0.06
Nodes (52): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, answer(), baseFrom(), cylindersAtReserve(), CylinderTerm (+44 more)

### Community 37 - "Community 37"
Cohesion: 0.07
Nodes (41): CodeFigure(), DiveCodes(), showDiveCodes(), ADR-0043, ButtonProps, ADR-0018, RadioGroupProps, Switch() (+33 more)

### Community 38 - "Community 38"
Cohesion: 0.05
Nodes (51): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, cancelWaitingImports() (+43 more)

### Community 39 - "Community 39"
Cohesion: 0.07
Nodes (53): WaitingPushOptions, audienceOfConnection(), audienceOfDive(), emitHint(), Hint, HINT_KINDS, HintAudience, HintKind (+45 more)

### Community 40 - "Community 40"
Cohesion: 0.07
Nodes (45): AscentBand, ascentRate(), ceiling(), descentRate(), judgedParts(), lastMetres(), ndl(), ppo2() (+37 more)

### Community 41 - "Community 41"
Cohesion: 0.08
Nodes (49): BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32Encode(), bech32HrpExpand(), bech32Polymod() (+41 more)

### Community 42 - "Community 42"
Cohesion: 0.06
Nodes (34): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+26 more)

### Community 43 - "Community 43"
Cohesion: 0.06
Nodes (38): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+30 more)

### Community 44 - "Community 44"
Cohesion: 0.08
Nodes (41): Leave, Series, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+33 more)

### Community 45 - "Community 45"
Cohesion: 0.08
Nodes (39): SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), emptyRecord(), writableRecord(), FakeSsi, BuddySaved (+31 more)

### Community 46 - "Community 46"
Cohesion: 0.07
Nodes (41): @modelcontextprotocol/server@^2.3.1, aiAccessLog, aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, AiAccessScope, AiAccessService (+33 more)

### Community 47 - "Community 47"
Cohesion: 0.06
Nodes (42): downsampleMinMax(), ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, seriesOf(), minute() (+34 more)

### Community 48 - "Community 48"
Cohesion: 0.05
Nodes (42): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+34 more)

### Community 49 - "Community 49"
Cohesion: 0.06
Nodes (31): Tx, jointDive, AppendUndo, ChangeListener, REVISION_CAUSES, ADR-0015, ADR-0016, ADR-0020 (+23 more)

### Community 50 - "Community 50"
Cohesion: 0.07
Nodes (36): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+28 more)

### Community 51 - "Community 51"
Cohesion: 0.08
Nodes (35): BottomTimeQuery, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033, ADR-0071, DEFAULT_PPO2 (+27 more)

### Community 52 - "Community 52"
Cohesion: 0.12
Nodes (40): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+32 more)

### Community 53 - "Community 53"
Cohesion: 0.06
Nodes (37): CYLINDER_MATERIALS, SUIT_TYPES, SUIT_UNDERGARMENTS, DiverCertifications, replyDiverError, STATUS, CertificationSummary, DiverDetailsView (+29 more)

### Community 54 - "Community 54"
Cohesion: 0.07
Nodes (41): cylinderOf(), cylinderText(), diverOf(), diverText(), finite(), kept, LEAD_MATERIALS, LEAD_WATERS (+33 more)

### Community 55 - "Community 55"
Cohesion: 0.05
Nodes (42): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+34 more)

### Community 56 - "Community 56"
Cohesion: 0.08
Nodes (38): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+30 more)

### Community 57 - "Community 57"
Cohesion: 0.07
Nodes (33): SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject(), MAX_CARD_IMAGE_BYTES (+25 more)

### Community 58 - "Community 58"
Cohesion: 0.06
Nodes (38): DUE_STATES, EquipmentService, RecordInput, ScheduleInput, DiveGearItemViewSchema, DiveGearViewSchema, DueState, EquipmentDetailViewSchema (+30 more)

### Community 59 - "Community 59"
Cohesion: 0.08
Nodes (30): createFileFormats(), FileFormat, createFitAdapter(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter() (+22 more)

### Community 60 - "Community 60"
Cohesion: 0.10
Nodes (32): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0051 (+24 more)

### Community 61 - "Community 61"
Cohesion: 0.11
Nodes (33): BACKUP_SKIPPED_DIRS, BackupDecryptionKey, encryptionKeyFingerprint(), openArchiveStreamingWith(), assertPgTool(), execFileAsync, pgCommand(), pgErrorDetail() (+25 more)

### Community 62 - "Community 62"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 63 - "Community 63"
Cohesion: 0.11
Nodes (35): massIn(), Dive, draftOfLead(), draftOfWeighting(), emptyLead(), fromLastDive(), hasThickness(), kg() (+27 more)

### Community 64 - "Community 64"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 65 - "Community 65"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 66 - "Community 66"
Cohesion: 0.06
Nodes (28): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationExpiry (+20 more)

### Community 67 - "Community 67"
Cohesion: 0.09
Nodes (33): equipmentItem, serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, DiveGear, DueEntry, GearItem (+25 more)

### Community 68 - "Community 68"
Cohesion: 0.12
Nodes (35): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+27 more)

### Community 69 - "Community 69"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 70 - "Community 70"
Cohesion: 0.09
Nodes (33): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+25 more)

### Community 71 - "Community 71"
Cohesion: 0.07
Nodes (27): Candidate, Check, Outcome, Preview, ADR-0030, ADR-0038, adapter, crossCheck() (+19 more)

### Community 72 - "Community 72"
Cohesion: 0.10
Nodes (29): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+21 more)

### Community 73 - "Community 73"
Cohesion: 0.08
Nodes (28): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+20 more)

### Community 74 - "Community 74"
Cohesion: 0.09
Nodes (30): Delivered, RemoteDive, confirmed(), keepRecord(), saveAndReadBack(), forSsi(), idOf(), numberOf() (+22 more)

### Community 75 - "Community 75"
Cohesion: 0.09
Nodes (28): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+20 more)

### Community 76 - "Community 76"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 77 - "Community 77"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 78 - "Community 78"
Cohesion: 0.14
Nodes (28): Logbook(), ImportContext, ImportFilesButton(), ImportList(), ImportNotices(), ImportPanel(), ImportProvider(), ImportRow() (+20 more)

### Community 79 - "Community 79"
Cohesion: 0.14
Nodes (26): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+18 more)

### Community 80 - "Community 80"
Cohesion: 0.09
Nodes (15): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+7 more)

### Community 81 - "Community 81"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 82 - "Community 82"
Cohesion: 0.10
Nodes (25): ENGINE_VERSION, EVIDENCE, Evidence, FindingValues, RULE_IDS, RuleId, SEVERITIES, Severity (+17 more)

### Community 83 - "Community 83"
Cohesion: 0.15
Nodes (26): wallClockMs(), DiveOrigin, REFERENCE_PREFIX, SsiLogbook, asBool(), aboveZero(), cylinderOf(), deviceOf() (+18 more)

### Community 84 - "Community 84"
Cohesion: 0.11
Nodes (20): SsiClient, ClearChecks, NumberChecks, TwowayChecks, apneaDive(), DATASET_KEYS, datasetSummary(), findIn() (+12 more)

### Community 85 - "Community 85"
Cohesion: 0.10
Nodes (22): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearQueue(), conflictAtSsi() (+14 more)

### Community 86 - "Community 86"
Cohesion: 0.09
Nodes (18): TAGS, killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023 (+10 more)

### Community 87 - "Community 87"
Cohesion: 0.13
Nodes (25): NoFly, RecordingSummary, RecordingFacts, DEFAULT_GF_HIGH, residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows() (+17 more)

### Community 88 - "Community 88"
Cohesion: 0.10
Nodes (26): Conditions, GasMix, GasPlanInput, AIR, DEFAULT_CONDITIONS, LoadingDive, N2_REPLAY_WINDOW_HOURS, N2_STALE_AFTER_HOURS (+18 more)

### Community 89 - "Community 89"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 90 - "Community 90"
Cohesion: 0.14
Nodes (23): surfacePressureBar(), ndlMinutes(), ADR-0032, ADR-0046, advanceTissues(), schreinerStep(), surfaceSaturatedTissues(), ADR-0071 (+15 more)

### Community 91 - "Community 91"
Cohesion: 0.13
Nodes (22): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DueInput, DueSchedule (+14 more)

### Community 92 - "Community 92"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 93 - "Community 93"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 94 - "Community 94"
Cohesion: 0.12
Nodes (19): BACKUP_RUN_TASK, backupTask(), ADR-0069, PurgeService, EXPIRE_WAITING_IMPORTS_TASK, PROCESS_IMPORT_TASK, PUSH_RUN_STEP_TASK, IMPORT_DIVE_SITES_TASK (+11 more)

### Community 95 - "Community 95"
Cohesion: 0.15
Nodes (21): Answer, CatalogueCylinder, CylinderInput, cylindersOfDrafts(), CylinderView, draftOfCylinder(), emptyDraft(), fromCatalogue() (+13 more)

### Community 96 - "Community 96"
Cohesion: 0.11
Nodes (23): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+15 more)

### Community 97 - "Community 97"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 98 - "Community 98"
Cohesion: 0.16
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 99 - "Community 99"
Cohesion: 0.09
Nodes (20): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+12 more)

### Community 100 - "Community 100"
Cohesion: 0.12
Nodes (21): CENTRE_SOURCES, CentreService, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+13 more)

### Community 101 - "Community 101"
Cohesion: 0.10
Nodes (21): ADR-0025, WaterMismatch, waterMismatch(), DECO_MODELS, DIVE_MODES, DIVER_SOURCES, GAS_CIRCUITS, IMPORT_SOURCES (+13 more)

### Community 102 - "Community 102"
Cohesion: 0.09
Nodes (23): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+15 more)

### Community 103 - "Community 103"
Cohesion: 0.13
Nodes (18): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, GasRule (+10 more)

### Community 104 - "Community 104"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 105 - "Community 105"
Cohesion: 0.15
Nodes (18): qr@0.7.2, cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043 (+10 more)

### Community 106 - "Community 106"
Cohesion: 0.11
Nodes (18): recordingEvent, Assessment, FindingView, Row, ADR-0036, upload(), Candidate, Deleted (+10 more)

### Community 107 - "Community 107"
Cohesion: 0.16
Nodes (21): managementOf(), EquipmentError, checkCost(), checkDates(), checkWindow(), createEquipmentService(), assemble(), checkResets() (+13 more)

### Community 108 - "Community 108"
Cohesion: 0.13
Nodes (21): Blocked, classify(), kindOf(), KINDS, LEFT_OUT_KEY, LEFT_OUT_KEYS, LeftOutReason, numberOnlyPlan() (+13 more)

### Community 109 - "Community 109"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 110 - "Community 110"
Cohesion: 0.13
Nodes (17): siteImportStatus, SiteImportRouteDeps, StartBody, STATUS, ADR-0021, SiteImportError, SiteImportService, Area (+9 more)

### Community 111 - "Community 111"
Cohesion: 0.24
Nodes (16): ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres(), isHypoxic(), metresPerBar() (+8 more)

### Community 112 - "Community 112"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

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
Cohesion: 0.24
Nodes (8): decode(), ENTITIES, localName(), Open, Reader, ADR-0003, XmlElement, XmlError

### Community 117 - "Community 117"
Cohesion: 0.11
Nodes (18): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+10 more)

### Community 118 - "Community 118"
Cohesion: 0.18
Nodes (15): asksFor(), failureOutcome(), numberPlanOf(), orderItems(), createPushRunService(), analyse(), numbersHere(), ownConnection() (+7 more)

### Community 119 - "Community 119"
Cohesion: 0.14
Nodes (13): nearest(), perMinute(), Series, summarize(), ChannelSeries, RecordedGas, Tank, TiedCylinder (+5 more)

### Community 120 - "Community 120"
Cohesion: 0.12
Nodes (16): ADR-0013, ADR-0026, ADR-0028, ADR-0030, ADR-0031, ADR-0034, ADR-0035, ADR-0036 (+8 more)

### Community 121 - "Community 121"
Cohesion: 0.22
Nodes (13): AppLockProbe, Arguments, credentialOf(), FILE_OPTIONS, fileText(), finishedArguments(), parseArguments(), RestoreCliDeps (+5 more)

### Community 122 - "Community 122"
Cohesion: 0.12
Nodes (15): pushRunItem, PushRunLeftOut, FailureOutcome, ScopedDive, Claimed, EMPTY_LEFT_OUT, ItemRow, MAX_IDS (+7 more)

### Community 123 - "Community 123"
Cohesion: 0.20
Nodes (13): formatOf(), createImportService(), cancelImport(), createImport(), endWaiting(), expireWaiting(), openUpload(), processFile() (+5 more)

### Community 124 - "Community 124"
Cohesion: 0.22
Nodes (15): asNumber(), asText(), buildUpdateRecord(), compareReadBack(), datasetLength(), keysOf(), describe(), isObject() (+7 more)

### Community 125 - "Community 125"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 126 - "Community 126"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 127 - "Community 127"
Cohesion: 0.16
Nodes (11): replyCertificationError, STATUS, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, CertificationRouteDeps (+3 more)

### Community 128 - "Community 128"
Cohesion: 0.22
Nodes (12): defined(), Joined, joinedSummary(), joinParts(), median(), PartInput, round3(), ADR-0063 (+4 more)

### Community 129 - "Community 129"
Cohesion: 0.13
Nodes (10): EGYPT, ExternalId, HOUSE_REEF, Method, Position, Revision, Site, SiteImport (+2 more)

### Community 130 - "Community 130"
Cohesion: 0.14
Nodes (7): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, headers, uniqueWord()

### Community 131 - "Community 131"
Cohesion: 0.18
Nodes (11): addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, relations(), TimelineBar, TimelineBarGeometry, TimelineGapGeometry, TimelineGeometry (+3 more)

### Community 132 - "Community 132"
Cohesion: 0.20
Nodes (12): flag(), idText(), isObject(), numberOrNull(), readSsiSite(), SsiSiteValues, visibilityFor(), WATER_OF_BOW (+4 more)

### Community 133 - "Community 133"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 134 - "Community 134"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 135 - "Community 135"
Cohesion: 0.21
Nodes (9): tarDirectory(), createBackupService(), checkSettings(), due(), enqueueScheduled(), readSettings(), saveSettings(), sweepRetention() (+1 more)

### Community 136 - "Community 136"
Cohesion: 0.15
Nodes (9): Sac, SAC_MISSING, SacMissing, ADR-0033, ADR-0045, Cylinder, dive, ADR-0033 (+1 more)

### Community 137 - "Community 137"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 138 - "Community 138"
Cohesion: 0.20
Nodes (10): BACKUP_FORMAT_VERSION, BackupManifest, decryptFile(), decryptFileWith(), decryptStreamWith(), fileTreeChecksum(), manifestFor(), partOf() (+2 more)

### Community 139 - "Community 139"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 140 - "Community 140"
Cohesion: 0.24
Nodes (9): CertificationError, createCertificationService(), cleaned(), requireManagedDiver(), rowOrThrow(), expiryOf(), isCalendarDate(), isoDay() (+1 more)

### Community 141 - "Community 141"
Cohesion: 0.22
Nodes (7): SourceSite, call(), createSite(), Method, openStream(), Stream, ADR-0066

### Community 142 - "Community 142"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 143 - "Community 143"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 144 - "Community 144"
Cohesion: 0.25
Nodes (7): Profile, SKETCH_HEIGHT, SKETCH_WIDTH, sketchPaths(), sketchScale(), ADR-0041, ADR-0041

### Community 145 - "Community 145"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 146 - "Community 146"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 147 - "Community 147"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 148 - "Community 148"
Cohesion: 0.22
Nodes (9): DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN, NO_RECORDING, notAtProvider(), ADR-0017 (+1 more)

### Community 149 - "Community 149"
Cohesion: 0.22
Nodes (8): inZone(), offsetIn(), AQABA, ATTERSEE, EILAT, HAUSREEF, MID_ATLANTIC, ADR-0030

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
Cohesion: 0.28
Nodes (8): itemIds(), ADR-0026, ADR-0034, Usage, UsageDive, usageMap(), usageOf(), UsageRow

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
Nodes (4): PlanOptions, PlanStatus, options(), ADR-0052

### Community 160 - "Community 160"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 161 - "Community 161"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 162 - "Community 162"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 163 - "Community 163"
Cohesion: 0.40
Nodes (4): label(), CompareSubject, facts, subjects

### Community 164 - "Community 164"
Cohesion: 0.40
Nodes (5): ensureItemForDevice(), scheduleView(), today(), trimName(), resolveDevice()

### Community 165 - "Community 165"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 166 - "Community 166"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 170 - "Community 170"
Cohesion: 0.67
Nodes (3): planSketch(), shape(), ADR-0033

### Community 172 - "Community 172"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 173 - "Community 173"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 174 - "Community 174"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1178 isolated node(s):** `ADR-0013`, `ADR-0011`, `ADR-0069`, `ADR-0070`, `ADR-0028` (+1173 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 2999 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **8 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 8` to `Community 0`, `Community 3`, `Community 4`, `Community 7`, `Community 9`, `Community 11`, `Community 14`, `Community 15`, `Community 16`, `Community 17`, `Community 19`, `Community 148`, `Community 21`, `Community 22`, `Community 23`, `Community 20`, `Community 153`, `Community 26`, `Community 27`, `Community 25`, `Community 30`, `Community 32`, `Community 39`, `Community 46`, `Community 49`, `Community 51`, `Community 66`, `Community 67`, `Community 70`, `Community 71`, `Community 72`, `Community 81`, `Community 87`, `Community 102`, `Community 106`, `Community 115`, `Community 122`?**
  _High betweenness centrality (0.050) - this node is a cross-community bridge._
- **What connects `ADR-0013`, `ADR-0011`, `ADR-0069` to the rest of the system?**
  _1178 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.021908526256352343 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 18` to `Community 96`, `Community 1`, `Community 2`, `Community 34`, `Community 5`, `Community 6`, `Community 10`, `Community 12`, `Community 77`, `Community 13`, `Community 78`, `Community 52`, `Community 117`?**
  _High betweenness centrality (0.029) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.045993269277666685 - nodes in this community are weakly interconnected._
- **Why does `Auth` connect `Community 28` to `Community 0`, `Community 100`, `Community 58`, `Community 9`, `Community 14`, `Community 46`, `Community 16`, `Community 110`, `Community 19`, `Community 53`, `Community 24`, `Community 26`, `Community 127`?**
  _High betweenness centrality (0.020) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.03281733746130031 - nodes in this community are weakly interconnected._