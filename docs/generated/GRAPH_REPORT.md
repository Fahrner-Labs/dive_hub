# Graph Report - dive_hub  (2026-11-18)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 7696 nodes · 23580 edges · 201 communities (184 shown, 17 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 229 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `25c7c495`
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
- Community 178
- Community 179
- Community 180
- Community 181
- Community 182
- Community 183
- Community 185
- Community 186
- Community 187
- Community 188
- Community 189
- Community 190
- Community 193

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 204 edges
2. `Button()` - 198 edges
3. `useErrorText()` - 169 edges
4. `useDisplay()` - 161 edges
5. `Muted()` - 139 edges
6. `drizzle-orm` - 139 edges
7. `unwrap()` - 138 edges
8. `announce()` - 106 edges
9. `createTestDatabase()` - 103 edges
10. `TestDatabase` - 102 edges

## Surprising Connections (you probably didn't know these)
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `askedAt()` --calls--> `DiveError`  [EXTRACTED]
  apps/server/src/dives/continuation/append-service.ts → apps/server/src/dives/dive-service.ts
- `EquipmentListFilter` --references--> `EquipmentCategory`  [EXTRACTED]
  apps/server/src/equipment/read.ts → apps/server/src/vocabulary.ts

## Import Cycles
- None detected.

## Communities (201 total, 17 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.04
Nodes (181): DisplaySettings(), DeleteUserDialog(), SiteDataPanel(), UserRow(), Accesses(), AiAccessSetting(), bodyWeightsQuery(), certificationsQuery() (+173 more)

### Community 1 - "Community 1"
Cohesion: 0.04
Nodes (106): account, user, cylinder, invitation, original, passwordReset, ADR-0013, ADR-0011 (+98 more)

### Community 2 - "Community 2"
Cohesion: 0.03
Nodes (134): buddiesForQuery(), BuddyView, ConflictChoices, ConflictEntryView, ConflictFieldView, ConflictsOfConnection, fetchBuddies(), GoneDivesOfConnection (+126 more)

### Community 3 - "Community 3"
Cohesion: 0.03
Nodes (112): LogbookPage, PAGE_SIZE, ExternalIdView, siteQuery(), CodeFigure(), DiveCodes(), showDiveCodes(), ADR-0043 (+104 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (117): LEAD_PLACEMENTS, WEIGHTING_FELT, downsampleMinMax(), sacOfDive(), waterMismatch(), DiveCertifications, AppendDeps, AppendOutcome (+109 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (111): Queryable, continuationAppend, diveAssessment, diverManagement, diveSite, equipmentItem, logbookCheckAnswer, logbookCheckDiveAnswer (+103 more)

### Community 6 - "Community 6"
Cohesion: 0.02
Nodes (107): buddySuggestion, jointDive, parseHint(), Assessment, FindingView, Row, ADR-0036, upload() (+99 more)

### Community 7 - "Community 7"
Cohesion: 0.04
Nodes (121): diversQuery(), cylinderCatalogueQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery() (+113 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (91): createServer(), createAuth(), loadAuthSecret(), acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS (+83 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (95): connection, dive, diveFinding, diveFingerprint, diveLease, importJob, providerConflict, providerLease (+87 more)

### Community 10 - "Community 10"
Cohesion: 0.04
Nodes (113): OVERRIDABLE_FIELDS, OverridableField, recordingEvent, sampleSeries, renumber(), renumberStatement(), ADR-0048, LogbookCheckService (+105 more)

### Community 11 - "Community 11"
Cohesion: 0.04
Nodes (107): PushLeftOut, externalDiver(), mergeExternalDiver(), participations(), waitingOf(), open(), AutomationSettings, createConnectionService() (+99 more)

### Community 12 - "Community 12"
Cohesion: 0.04
Nodes (108): Me, AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView (+100 more)

### Community 13 - "Community 13"
Cohesion: 0.04
Nodes (108): ComputerChoice, DiveImportMode, DiveKind, importOriginal, ProviderImportPlan, RecordingSummary, UtcOffsetSource, defined() (+100 more)

### Community 14 - "Community 14"
Cohesion: 0.05
Nodes (98): ChangePassword(), SecuritySettings(), Sessions(), useDescribeAgent(), AdminSettings(), Invitations(), Invite(), LinkToPassOn() (+90 more)

### Community 15 - "Community 15"
Cohesion: 0.03
Nodes (79): createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities(), providerError() (+71 more)

### Community 16 - "Community 16"
Cohesion: 0.03
Nodes (80): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreExternalId, CentreSource, live, ADR-0029, ADR-0043, typedId() (+72 more)

### Community 17 - "Community 17"
Cohesion: 0.04
Nodes (84): DiveValues, OverridableField, revisionsQuery(), RevisionView, UtcOffsetSource, CylinderFields(), CylinderList(), MATERIALS (+76 more)

### Community 18 - "Community 18"
Cohesion: 0.04
Nodes (60): AuthOptions, auth, BackupServiceDeps, createServices(), ServiceParts, Services, ADR-0027, ADR-0030 (+52 more)

### Community 19 - "Community 19"
Cohesion: 0.02
Nodes (83): actorType, aiAccessOutcome, backupRunKind, backupRunStatus, candidateResolution, centreSource, CERTIFICATION_IMAGE_STATES, CERTIFICATION_VERIFICATIONS (+75 more)

### Community 20 - "Community 20"
Cohesion: 0.04
Nodes (78): AiAccessScope, seesPositions(), VerifiedAccess, diversOf(), SiteRow, checkedByRunTool, createMcpServer(), INSTRUCTIONS (+70 more)

### Community 21 - "Community 21"
Cohesion: 0.05
Nodes (64): ImportArea, looksLikeZip(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy() (+56 more)

### Community 22 - "Community 22"
Cohesion: 0.05
Nodes (69): DIVE_IMPORT_MODES, DIVE_KINDS, PARTICIPANT_ROLES, UTC_OFFSET_SOURCES, DiveSummaryFields, SummaryRow, ErrorAnswer, providerProblem() (+61 more)

### Community 23 - "Community 23"
Cohesion: 0.04
Nodes (70): Position, running(), SiteImportArea, siteImportsQuery(), SiteImportView, siteRevisionsQuery(), SiteRevisionView, ADR-0020 (+62 more)

### Community 24 - "Community 24"
Cohesion: 0.07
Nodes (74): allButDive(), continuationCandidateQuery(), deletedDivesOn(), deletedDivesQuery(), diveQuery(), DiveView, mergeCandidatesQuery(), nearbyDivesQuery() (+66 more)

### Community 25 - "Community 25"
Cohesion: 0.05
Nodes (64): accountListEntry, diver, diverExternalId, tidyName(), DiverDetails, Caller, foldName(), nameDiffers() (+56 more)

### Community 26 - "Community 26"
Cohesion: 0.05
Nodes (64): authHandler(), authHeaders(), forwardCookies(), sessionIdOf(), AttemptLimit, clientKey(), limitAttempts(), ADR-0061 (+56 more)

### Community 27 - "Community 27"
Cohesion: 0.05
Nodes (65): BackupRouteDeps, BACKUP_RUN_TASK, BackupService, backupTask(), ADR-0069, CertificationService, PurgeService, EXPIRE_WAITING_IMPORTS_TASK (+57 more)

### Community 28 - "Community 28"
Cohesion: 0.05
Nodes (67): ConnectionRow, WaitingPushOptions, audienceOfConnection(), audienceOfDive(), audienceOfDiver(), Hint, HINT_KINDS, HintAudience (+59 more)

### Community 29 - "Community 29"
Cohesion: 0.05
Nodes (58): divesQuery(), importQuery(), importsQuery(), ImportView, ADR-0030, uploadFile(), ApiError, DETAILS (+50 more)

### Community 30 - "Community 30"
Cohesion: 0.04
Nodes (69): SUIT_TYPES, buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf() (+61 more)

### Community 31 - "Community 31"
Cohesion: 0.07
Nodes (58): dataIdsOf(), settleSiteVisibility(), outgoingSite(), findOrMake(), SiteForSend, siteForSend(), ADR-0057, createSitePickService() (+50 more)

### Community 32 - "Community 32"
Cohesion: 0.07
Nodes (65): diverBodyWeight, lead, LeadPlacement, managedDiverIds(), tieToJoined(), cylindersOf(), mergeProviderCylinder(), providerCylinderOf() (+57 more)

### Community 33 - "Community 33"
Cohesion: 0.05
Nodes (54): diveSiteExternalId, providerSiteData, createSsiClient(), Card, DEEP, OWD, Report, Summary (+46 more)

### Community 34 - "Community 34"
Cohesion: 0.06
Nodes (56): NoFly, getDiverLoadings, BottomTimeQuery, DEFAULT_GF_HIGH, diverLogbook(), planBottomTime(), residualSeedOf(), Source (+48 more)

### Community 35 - "Community 35"
Cohesion: 0.05
Nodes (59): CylinderMaterial, SuitType, SuitUndergarment, WeightingFelt, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, Suit (+51 more)

### Community 36 - "Community 36"
Cohesion: 0.07
Nodes (55): SsiRecord, contextOf(), asNumber(), asText(), buildCreateRecord(), buildDeleteRecord(), buildUpdateRecord(), compareReadBack() (+47 more)

### Community 37 - "Community 37"
Cohesion: 0.04
Nodes (45): ParticipantRole, Direction, DiveExport, DiveListing, FIND_BY, FindBy, ImportedDive, Operation (+37 more)

### Community 38 - "Community 38"
Cohesion: 0.07
Nodes (60): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+52 more)

### Community 39 - "Community 39"
Cohesion: 0.09
Nodes (57): buildApp(), assessmentRoutes(), AI_ACCESS_NAME_MAX, isAdmin(), requireAdmin(), requireUser(), userOf(), backupRoutes() (+49 more)

### Community 40 - "Community 40"
Cohesion: 0.06
Nodes (52): EMPTY_SITE_IMPORT_COUNTS, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, ProviderAdapter (+44 more)

### Community 41 - "Community 41"
Cohesion: 0.06
Nodes (52): Delivered, DIVE_FIELDS, ProviderError, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord() (+44 more)

### Community 42 - "Community 42"
Cohesion: 0.05
Nodes (51): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, cancelWaitingImports() (+43 more)

### Community 43 - "Community 43"
Cohesion: 0.07
Nodes (51): BottomTimeInput, Conditions, GasMix, GasPlanInput, surfacePressureBar(), AIR, cnsDecayed(), DEFAULT_CONDITIONS (+43 more)

### Community 44 - "Community 44"
Cohesion: 0.08
Nodes (44): BottomTime, bottomTime(), Limit, ADR-0032, ADR-0033, ADR-0071, ambientPressureBar(), bestMixPercent() (+36 more)

### Community 45 - "Community 45"
Cohesion: 0.08
Nodes (49): BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32Encode(), bech32HrpExpand(), bech32Polymod() (+41 more)

### Community 46 - "Community 46"
Cohesion: 0.06
Nodes (34): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+26 more)

### Community 47 - "Community 47"
Cohesion: 0.07
Nodes (39): ImportErrorCode, ImportFound, errorMessage(), ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS, eachFile() (+31 more)

### Community 48 - "Community 48"
Cohesion: 0.06
Nodes (38): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+30 more)

### Community 49 - "Community 49"
Cohesion: 0.06
Nodes (9): DiveHubApp(), ErrorText(), InstanceForm(), Loading(), PasswordField(), PrimaryButton(), SignedIn(), SignInForm() (+1 more)

### Community 50 - "Community 50"
Cohesion: 0.06
Nodes (38): adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, SessionUser, ADR-0011, ADR-0012 (+30 more)

### Community 51 - "Community 51"
Cohesion: 0.05
Nodes (36): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError (+28 more)

### Community 52 - "Community 52"
Cohesion: 0.07
Nodes (37): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+29 more)

### Community 53 - "Community 53"
Cohesion: 0.08
Nodes (39): Leave, asBool(), DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+31 more)

### Community 54 - "Community 54"
Cohesion: 0.05
Nodes (42): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+34 more)

### Community 55 - "Community 55"
Cohesion: 0.06
Nodes (42): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, EquipmentService, RecordInput, ScheduleInput (+34 more)

### Community 56 - "Community 56"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 57 - "Community 57"
Cohesion: 0.06
Nodes (37): AssessmentService, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView(), ADR-0036 (+29 more)

### Community 58 - "Community 58"
Cohesion: 0.06
Nodes (40): Change(), Changes(), historyRoute, Made(), REVISION_CAUSES, PositionSchema, AdoptBody, Country (+32 more)

### Community 59 - "Community 59"
Cohesion: 0.07
Nodes (35): sampleCount(), SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject() (+27 more)

### Community 60 - "Community 60"
Cohesion: 0.07
Nodes (41): cylinderOf(), cylinderText(), diverOf(), diverText(), finite(), kept, LEAD_MATERIALS, LEAD_WATERS (+33 more)

### Community 61 - "Community 61"
Cohesion: 0.06
Nodes (35): CYLINDER_MATERIALS, SUIT_UNDERGARMENTS, DiverCertifications, replyDiverError, STATUS, CertificationSummary, DiverDetailsView, DiverDetailsViewSchema (+27 more)

### Community 62 - "Community 62"
Cohesion: 0.11
Nodes (40): DiveGearItemView, DiveGearView, EquipmentDetailView, EquipmentDueView, equipmentItemQuery(), EquipmentItemView, equipmentOfDiverQuery(), EquipmentRecordView (+32 more)

### Community 63 - "Community 63"
Cohesion: 0.08
Nodes (37): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+29 more)

### Community 64 - "Community 64"
Cohesion: 0.08
Nodes (6): AppContainer, KeystoreTokenStore, PreferencesInstanceUrlStore, UserCacheStore, SessionRepository, InstanceUrlStore

### Community 65 - "Community 65"
Cohesion: 0.07
Nodes (36): siteImport, siteImportStatus, DIVE_ONLY_PATTERN, PROBLEM_CODES, ProblemSchema, storedProblemCode(), ADR-0014, ADR-0027 (+28 more)

### Community 66 - "Community 66"
Cohesion: 0.07
Nodes (34): ADR-0025, WaterMismatch, CIRCUIT, circuitFromFit, DECO_MODEL, decoModelFromFit, DIVE_ALERT, DIVE_MODE (+26 more)

### Community 67 - "Community 67"
Cohesion: 0.10
Nodes (32): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0051 (+24 more)

### Community 68 - "Community 68"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 69 - "Community 69"
Cohesion: 0.11
Nodes (33): BACKUP_SKIPPED_DIRS, BackupDecryptionKey, encryptionKeyFingerprint(), openArchiveStreamingWith(), assertPgTool(), execFileAsync, pgCommand(), pgErrorDetail() (+25 more)

### Community 70 - "Community 70"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 71 - "Community 71"
Cohesion: 0.11
Nodes (35): massIn(), Dive, draftOfLead(), draftOfWeighting(), emptyLead(), fromLastDive(), hasThickness(), kg() (+27 more)

### Community 72 - "Community 72"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 73 - "Community 73"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 74 - "Community 74"
Cohesion: 0.08
Nodes (9): UiState, SignInEmailRequest, UserView, ErrorCodes, InstanceUrl, Loading, SessionState, SignedIn (+1 more)

### Community 75 - "Community 75"
Cohesion: 0.12
Nodes (35): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+27 more)

### Community 76 - "Community 76"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 77 - "Community 77"
Cohesion: 0.16
Nodes (6): FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore

### Community 78 - "Community 78"
Cohesion: 0.12
Nodes (31): AscentBand, ascentRate(), ceiling(), descentRate(), judgedParts(), lastMetres(), ppo2(), Profile (+23 more)

### Community 79 - "Community 79"
Cohesion: 0.09
Nodes (32): ImportOutcome, ensureItemForDevice(), recordingKeySource(), deletedEarlier(), fullerCopyHere(), IsManaged, knownRecording(), Outcome (+24 more)

### Community 80 - "Community 80"
Cohesion: 0.11
Nodes (27): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+19 more)

### Community 81 - "Community 81"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 82 - "Community 82"
Cohesion: 0.07
Nodes (25): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+17 more)

### Community 83 - "Community 83"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 84 - "Community 84"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 85 - "Community 85"
Cohesion: 0.11
Nodes (26): connectionsQuery(), useFocusBackWhenClosed(), countryCodes(), countryOptions(), holdLeave(), mayLeave(), useLeaveGuard(), typedSiteId() (+18 more)

### Community 86 - "Community 86"
Cohesion: 0.09
Nodes (27): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+19 more)

### Community 87 - "Community 87"
Cohesion: 0.11
Nodes (4): ApiClient, ApiErrorBody, DiveHubException, NoAnswerException

### Community 88 - "Community 88"
Cohesion: 0.11
Nodes (24): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+16 more)

### Community 89 - "Community 89"
Cohesion: 0.09
Nodes (23): computerEventOf(), ADR-0036, computerEventFromFit(), COMPUTER_EVENT, computerEventFromSuunto(), DECO_MODEL, decoModelFromSuunto, DIVE_MODE (+15 more)

### Community 90 - "Community 90"
Cohesion: 0.14
Nodes (26): DiveOrigin, REFERENCE_PREFIX, Series, SsiLogbook, aboveZero(), cylinderOf(), deviceOf(), deviceOfRef() (+18 more)

### Community 91 - "Community 91"
Cohesion: 0.10
Nodes (22): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearQueue(), conflictAtSsi() (+14 more)

### Community 92 - "Community 92"
Cohesion: 0.11
Nodes (24): EVIDENCE, Evidence, FindingValues, LIMITS, RULE_IDS, RuleId, RULES, SEVERITIES (+16 more)

### Community 93 - "Community 93"
Cohesion: 0.11
Nodes (24): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+16 more)

### Community 94 - "Community 94"
Cohesion: 0.11
Nodes (19): SsiClient, NumberChecks, TwowayChecks, apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks() (+11 more)

### Community 95 - "Community 95"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 96 - "Community 96"
Cohesion: 0.13
Nodes (24): CentreError, createCentreService(), holder(), liveSite(), lock(), setExternal(), displayNameOf(), Field (+16 more)

### Community 97 - "Community 97"
Cohesion: 0.09
Nodes (21): Cylinder, DiveView, RevisionView, ADR-0045, upload(), FIT_EPOCH_MS, makeSuuntoAppFit(), makeSuuntoJson() (+13 more)

### Community 98 - "Community 98"
Cohesion: 0.09
Nodes (23): @modelcontextprotocol/server@^2.3.1, aiAccessLog, aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, KeyRow, LOG_DAYS (+15 more)

### Community 99 - "Community 99"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 100 - "Community 100"
Cohesion: 0.12
Nodes (22): equipmentUse, DiveGear, DueEntry, GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow (+14 more)

### Community 101 - "Community 101"
Cohesion: 0.12
Nodes (20): providerGoneDive, siteRef(), compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051 (+12 more)

### Community 102 - "Community 102"
Cohesion: 0.15
Nodes (21): Answer, CatalogueCylinder, CylinderInput, cylindersOfDrafts(), CylinderView, draftOfCylinder(), emptyDraft(), fromCatalogue() (+13 more)

### Community 103 - "Community 103"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 104 - "Community 104"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 105 - "Community 105"
Cohesion: 0.11
Nodes (3): MainActivity, MainViewModel, DiveHubTheme()

### Community 106 - "Community 106"
Cohesion: 0.09
Nodes (20): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+12 more)

### Community 107 - "Community 107"
Cohesion: 0.12
Nodes (21): CENTRE_SOURCES, CentreService, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+13 more)

### Community 108 - "Community 108"
Cohesion: 0.11
Nodes (14): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+6 more)

### Community 109 - "Community 109"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 110 - "Community 110"
Cohesion: 0.09
Nodes (23): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+15 more)

### Community 111 - "Community 111"
Cohesion: 0.14
Nodes (18): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+10 more)

### Community 112 - "Community 112"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 113 - "Community 113"
Cohesion: 0.15
Nodes (18): qr@0.7.2, cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043 (+10 more)

### Community 114 - "Community 114"
Cohesion: 0.12
Nodes (21): Blocked, classify(), kindOf(), KINDS, LEFT_OUT_KEY, LEFT_OUT_KEYS, LeftOutReason, numberOnlyPlan() (+13 more)

### Community 115 - "Community 115"
Cohesion: 0.12
Nodes (14): LoadingsView, CustomTypeOptions, i18next, initI18n(), resources, ADR-0014, LOADING_SOURCES, loadingNumber() (+6 more)

### Community 116 - "Community 116"
Cohesion: 0.18
Nodes (20): className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), typeOf(), identifier() (+12 more)

### Community 117 - "Community 117"
Cohesion: 0.17
Nodes (15): hashPassword(), createInvitationService(), EmailTakenError, InvitationRow, InvitationStatus, ADR-0012, createPasswordResetService(), find() (+7 more)

### Community 118 - "Community 118"
Cohesion: 0.14
Nodes (18): DAY_MS, MAX_OFFSET_MS, inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), placeLocalTime(), Position (+10 more)

### Community 119 - "Community 119"
Cohesion: 0.16
Nodes (16): DecisionTimelineProps, GapKind, ADR-0063, addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, laneFitsCapsule(), relations() (+8 more)

### Community 120 - "Community 120"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 122 - "Community 122"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 123 - "Community 123"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 124 - "Community 124"
Cohesion: 0.19
Nodes (16): equipmentDueQuery(), EquipmentDueNotice(), ADR-0034, dismissDue(), dismissedDueDigest(), dueCounts(), dueDigest(), ADR-0034 (+8 more)

### Community 125 - "Community 125"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 126 - "Community 126"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 127 - "Community 127"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 128 - "Community 128"
Cohesion: 0.11
Nodes (13): BACKUP_EXTENSION, BackupEncryption, BackupPart, BackupLog, BackupRunRow, BackupSettingsInput, BackupSettingsValues, DEFAULT_BACKUP_SETTINGS (+5 more)

### Community 129 - "Community 129"
Cohesion: 0.11
Nodes (15): accountRelations, apikey, rateLimit, sessionRelations, ADR-0011, ADR-0012, ADR-0035, twoFactor (+7 more)

### Community 130 - "Community 130"
Cohesion: 0.20
Nodes (16): AssessmentView, AssessmentState, clock(), counts(), differing(), Finding, formatRate(), hidden() (+8 more)

### Community 131 - "Community 131"
Cohesion: 0.21
Nodes (16): samplesQuery(), FindingLane(), useAssessment(), DepthProfile(), NO_POINTS, SurfaceBreakLane(), ADR-0036, ADR-0063 (+8 more)

### Community 132 - "Community 132"
Cohesion: 0.16
Nodes (16): BACKUP_FORMAT_VERSION, BackupManifest, buildManifest(), decryptFile(), decryptFileWith(), decryptStreamWith(), encryptFile(), packContainer() (+8 more)

### Community 133 - "Community 133"
Cohesion: 0.12
Nodes (16): ADR-0013, ADR-0026, ADR-0028, ADR-0030, ADR-0031, ADR-0034, ADR-0035, ADR-0036 (+8 more)

### Community 134 - "Community 134"
Cohesion: 0.22
Nodes (13): AppLockProbe, Arguments, credentialOf(), FILE_OPTIONS, fileText(), finishedArguments(), parseArguments(), RestoreCliDeps (+5 more)

### Community 135 - "Community 135"
Cohesion: 0.15
Nodes (14): certification, PROBLEMS, createUserAdminService(), keepAnAdmin(), lock(), deleteUserData(), ADR-0013, ADR-0024 (+6 more)

### Community 136 - "Community 136"
Cohesion: 0.12
Nodes (15): pushRunItem, PushRunLeftOut, RemoteDive, FailureOutcome, Claimed, EMPTY_LEFT_OUT, ItemRow, MAX_IDS (+7 more)

### Community 137 - "Community 137"
Cohesion: 0.13
Nodes (14): SUGGESTION_STATUSES, DiveProfileView, DiveProfileViewSchema, ADR-0041, DiverRef, Offset, OverlapView, SiteRef (+6 more)

### Community 138 - "Community 138"
Cohesion: 0.18
Nodes (13): failureOutcome(), numberPlanOf(), orderItems(), createPushRunService(), analyse(), finish(), numbersHere(), ownConnection() (+5 more)

### Community 139 - "Community 139"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 140 - "Community 140"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 141 - "Community 141"
Cohesion: 0.17
Nodes (11): execFileAsync, fileTreeChecksum(), tarDirectory(), createBackupService(), checkSettings(), due(), enqueueScheduled(), readSettings() (+3 more)

### Community 142 - "Community 142"
Cohesion: 0.14
Nodes (7): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, headers, uniqueWord()

### Community 143 - "Community 143"
Cohesion: 0.29
Nodes (13): temperatureIn(), DepthPlot(), LINE_DASHES, plotFont(), PressureStrip(), RIGHT_AXIS, token(), ADR-0045 (+5 more)

### Community 144 - "Community 144"
Cohesion: 0.15
Nodes (12): EquipmentError, ADR-0034, ADR-0053, checkCost(), checkDates(), checkWindow(), checkResets(), checkSchedule() (+4 more)

### Community 145 - "Community 145"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 146 - "Community 146"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 147 - "Community 147"
Cohesion: 0.26
Nodes (3): ApiMeGet200Response, ApiMeGet200ResponseOwnDiver, ApiMeGet200ResponsePreferences

### Community 148 - "Community 148"
Cohesion: 0.18
Nodes (11): ProfileAssessment, Finding, dayNumber(), deepDays(), noFlyAfter(), SERIES_RULES, SeriesContext, SeriesDive (+3 more)

### Community 149 - "Community 149"
Cohesion: 0.19
Nodes (12): serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, equipmentList(), EquipmentListFilter, EquipmentRecordRead, EquipmentScheduleRead (+4 more)

### Community 150 - "Community 150"
Cohesion: 0.28
Nodes (13): createEquipmentService(), assemble(), detailOf(), lastCylinderOf(), managedItem(), recordsOf(), schedulesFor(), schedulesOf() (+5 more)

### Community 151 - "Community 151"
Cohesion: 0.21
Nodes (9): createFitAdapter(), adapter, crossCheck(), decodeOfficial(), privateDir, ownersParts(), parse(), toPart() (+1 more)

### Community 152 - "Community 152"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 153 - "Community 153"
Cohesion: 0.31
Nodes (12): assessmentQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), FindingItem(), FixedNote(), rowId() (+4 more)

### Community 154 - "Community 154"
Cohesion: 0.18
Nodes (7): ascentBandsOf(), assessProfile(), ndl(), cleanDepth(), CLEAN, samples, ADR-0036

### Community 155 - "Community 155"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 156 - "Community 156"
Cohesion: 0.20
Nodes (10): DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN, NO_RECORDING, notAtProvider(), ADR-0017 (+2 more)

### Community 157 - "Community 157"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 158 - "Community 158"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 159 - "Community 159"
Cohesion: 0.20
Nodes (8): Series, ChannelSeries, RecordedGas, Tank, TiedCylinder, ADR-0045, readings, ADR-0045

### Community 160 - "Community 160"
Cohesion: 0.25
Nodes (7): Profile, SKETCH_HEIGHT, SKETCH_WIDTH, sketchPaths(), sketchScale(), ADR-0041, ADR-0041

### Community 161 - "Community 161"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 162 - "Community 162"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 163 - "Community 163"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 164 - "Community 164"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 165 - "Community 165"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 166 - "Community 166"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 167 - "Community 167"
Cohesion: 0.28
Nodes (7): ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, s(), ADR-0035

### Community 169 - "Community 169"
Cohesion: 0.25
Nodes (8): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, MODULE_IMPORTS, moduleRules, target()

### Community 170 - "Community 170"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 171 - "Community 171"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 172 - "Community 172"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 174 - "Community 174"
Cohesion: 0.29
Nodes (6): itemIds(), ADR-0026, ADR-0034, Usage, UsageDive, UsageRow

### Community 175 - "Community 175"
Cohesion: 0.29
Nodes (4): PlanOptions, PlanStatus, options(), ADR-0052

### Community 176 - "Community 176"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 177 - "Community 177"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 178 - "Community 178"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 179 - "Community 179"
Cohesion: 0.40
Nodes (4): label(), CompareSubject, facts, subjects

### Community 180 - "Community 180"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 181 - "Community 181"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 182 - "Community 182"
Cohesion: 0.40
Nodes (4): TAGS, ADR-0023, { stdout, stderr }, total

### Community 185 - "Community 185"
Cohesion: 0.67
Nodes (3): planSketch(), shape(), ADR-0033

### Community 187 - "Community 187"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 188 - "Community 188"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 189 - "Community 189"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1186 isolated node(s):** `ADR-0028`, `ADR-0043`, `ADR-0050`, `ADR-0071`, `ADR-0031` (+1181 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3152 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **17 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 9` to `Community 128`, `Community 129`, `Community 1`, `Community 4`, `Community 5`, `Community 6`, `Community 135`, `Community 8`, `Community 136`, `Community 10`, `Community 11`, `Community 13`, `Community 15`, `Community 16`, `Community 18`, `Community 19`, `Community 20`, `Community 149`, `Community 25`, `Community 27`, `Community 156`, `Community 28`, `Community 30`, `Community 31`, `Community 32`, `Community 33`, `Community 34`, `Community 174`, `Community 47`, `Community 51`, `Community 55`, `Community 79`, `Community 86`, `Community 88`, `Community 96`, `Community 97`, `Community 98`, `Community 100`, `Community 101`, `Community 110`, `Community 117`, `Community 118`?**
  _High betweenness centrality (0.037) - this node is a cross-community bridge._
- **What connects `ADR-0028`, `ADR-0043`, `ADR-0050` to the rest of the system?**
  _1186 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.043700921816319564 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 0` to `Community 2`, `Community 131`, `Community 3`, `Community 68`, `Community 7`, `Community 12`, `Community 14`, `Community 17`, `Community 84`, `Community 85`, `Community 23`, `Community 24`, `Community 153`, `Community 122`, `Community 124`, `Community 29`, `Community 62`?**
  _High betweenness centrality (0.029) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.03520390379923318 - nodes in this community are weakly interconnected._
- **Why does `@playwright/test` connect `Community 46` to `Community 67`, `Community 99`, `Community 42`, `Community 139`, `Community 142`, `Community 48`, `Community 84`, `Community 54`, `Community 152`, `Community 91`, `Community 157`, `Community 158`?**
  _High betweenness centrality (0.020) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.026628352490421454 - nodes in this community are weakly interconnected._