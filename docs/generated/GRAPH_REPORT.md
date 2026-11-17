# Graph Report - dive_hub  (2026-11-17)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 7250 nodes · 22589 edges · 177 communities (170 shown, 7 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 222 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `47b0bf79`
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
- Community 176

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 204 edges
2. `unwrap()` - 201 edges
3. `Button()` - 197 edges
4. `useErrorText()` - 169 edges
5. `useDisplay()` - 161 edges
6. `Muted()` - 139 edges
7. `drizzle-orm` - 135 edges
8. `announce()` - 105 edges
9. `createTestDatabase()` - 101 edges
10. `TestDatabase` - 100 edges

## Surprising Connections (you probably didn't know these)
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `EquipmentListFilter` --references--> `EquipmentCategory`  [EXTRACTED]
  apps/server/src/equipment/read.ts → apps/server/src/vocabulary.ts
- `AuthOptions` --references--> `Db`  [EXTRACTED]
  apps/server/src/auth/auth.ts → apps/server/src/db/client.ts

## Import Cycles
- None detected.

## Communities (177 total, 7 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.04
Nodes (210): ChangePassword(), DisplaySettings(), SecuritySettings(), Sessions(), useDescribeAgent(), AdminSettings(), DeleteUserDialog(), Invitations() (+202 more)

### Community 1 - "Community 1"
Cohesion: 0.02
Nodes (145): equipmentDueQuery(), EquipmentDueView, ExternalIdView, setupQuery(), SiteImportArea, SiteImportView, App(), BackupsPage (+137 more)

### Community 2 - "Community 2"
Cohesion: 0.03
Nodes (168): allButDive(), continuationCandidateQuery(), deletedDivesQuery(), diveListPages, diveNeighboursQuery(), diveProvidersQuery(), diveQuery(), diversQuery() (+160 more)

### Community 3 - "Community 3"
Cohesion: 0.04
Nodes (105): user, accountListEntry, diver, revision, forget(), listAccountOf(), remember(), rememberedEntries() (+97 more)

### Community 4 - "Community 4"
Cohesion: 0.04
Nodes (124): continuationAppend, diverManagement, OVERRIDABLE_FIELDS, OverridableField, participant, recording, renumber(), renumberStatement() (+116 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (127): bottomTimeRule(), ConflictFieldView, defined(), leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas, toolsGasQuery() (+119 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (121): CYLINDER_MATERIALS, PARTICIPANT_ROLES, UTC_OFFSET_SOURCES, downsampleMinMax(), sacOfDive(), ADR-0025, WaterMismatch, waterMismatch() (+113 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (90): createServer(), loadAuthSecret(), BackupManifest, buildManifest(), decryptFile(), decryptFileWith(), encryptFile(), execFileAsync (+82 more)

### Community 8 - "Community 8"
Cohesion: 0.02
Nodes (101): ComputerChoice, DiveImportMode, DiveKind, duplicateCandidate, ImportErrorCode, ImportFound, importOriginal, ImportOutcome (+93 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (102): device, providerLease, DiverError, ADR-0016, ADR-0053, tidyName(), ACCOUNT_PATTERN, accountKey() (+94 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (105): divesQuery(), DiveSummary, LogbookPage, Me, PAGE_SIZE, pushRunActive(), pushRunBody(), PushRunChoices (+97 more)

### Community 11 - "Community 11"
Cohesion: 0.02
Nodes (106): CHANNELS, isRule(), NOT_COVERED, read(), ADR-0036, actorType, aiAccessOutcome, backupRunKind (+98 more)

### Community 12 - "Community 12"
Cohesion: 0.04
Nodes (100): cylinderCatalogueQuery(), LeadEstimate, CylinderFields(), MATERIALS, ADR-0034, ADR-0045, useCatalogueName(), useCylinderProblem() (+92 more)

### Community 13 - "Community 13"
Cohesion: 0.04
Nodes (81): auth, BackupServiceDeps, createServices(), ServiceParts, ADR-0027, ADR-0030, ADR-0061, ADR-0063 (+73 more)

### Community 14 - "Community 14"
Cohesion: 0.04
Nodes (94): CertificationService, CertificationRouteDeps, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053, Copies, DeleteProblemSchema (+86 more)

### Community 15 - "Community 15"
Cohesion: 0.05
Nodes (96): AcceptedBuddySuggestionView, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView, candidatesQuery(), CandidateView (+88 more)

### Community 16 - "Community 16"
Cohesion: 0.02
Nodes (101): AiAccessLogEntry, AiAccessView, AnyLogbookCheck, buddiesForQuery(), buddiesQuery(), BuddyView, ContinuationCandidateView, DeletedDiveView (+93 more)

### Community 17 - "Community 17"
Cohesion: 0.03
Nodes (79): diveSiteExternalId, ActionContext, DIVE_FIELDS, FIND_BY, OPERATIONS, OutgoingDive, SignInInput, SSI_CAPABILITIES (+71 more)

### Community 18 - "Community 18"
Cohesion: 0.03
Nodes (75): connection, createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities() (+67 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (74): replyCertificationError, STATUS, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, errors (+66 more)

### Community 20 - "Community 20"
Cohesion: 0.03
Nodes (65): apikey, Assessment, FindingView, Row, ADR-0036, upload(), Check, Deleted (+57 more)

### Community 21 - "Community 21"
Cohesion: 0.05
Nodes (72): diveAssessment, diveCentre, jointDive, logbookCheckAnswer, logbookCheckDiveAnswer, keepDive(), ADR-0038, agreeInDepthAndDuration() (+64 more)

### Community 22 - "Community 22"
Cohesion: 0.04
Nodes (59): diveLease, providerConflict, push, pushRun, waitingPush, kept(), record(), ExportingProvider (+51 more)

### Community 23 - "Community 23"
Cohesion: 0.07
Nodes (68): buildApp(), assessmentRoutes(), CLIENT_IP_HEADER, SessionUser, authHandler(), authHeaders(), fastify, FastifyRequest (+60 more)

### Community 24 - "Community 24"
Cohesion: 0.07
Nodes (58): api, ApiError, centresQuery(), CentreView, ConnectionView, keys, ProviderView, sitesQuery() (+50 more)

### Community 25 - "Community 25"
Cohesion: 0.06
Nodes (64): equipmentItem, managedDiverIds(), divesOf(), tieToJoined(), cylindersOf(), mergeProviderCylinder(), providerCylinderOf(), Participant (+56 more)

### Community 26 - "Community 26"
Cohesion: 0.05
Nodes (57): SsiClient, SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), buildUpdateRecord(), DiveForSsi, emptyRecord() (+49 more)

### Community 27 - "Community 27"
Cohesion: 0.05
Nodes (62): adminRole, AI_ACCESS_NAME_MAX, AI_ACCESS_PREFIX, AI_ACCESS_RATE, AuthOptions, createAuth(), ADR-0011, ADR-0012 (+54 more)

### Community 28 - "Community 28"
Cohesion: 0.03
Nodes (64): buddySuggestion, parseHint(), Accepted, Candidate, Check, DiveView, Outcome, RevisionView (+56 more)

### Community 29 - "Community 29"
Cohesion: 0.06
Nodes (62): diverExternalId, diveSite, siteImport, dataIdsOf(), ADR-0032, ADR-0033, settleSiteVisibility(), outgoingSite() (+54 more)

### Community 30 - "Community 30"
Cohesion: 0.04
Nodes (64): pushRunItem, PushRunLeftOut, DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN, NO_RECORDING (+56 more)

### Community 31 - "Community 31"
Cohesion: 0.05
Nodes (58): SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, ProviderImportPlan, providerSiteData, RemoteSite (+50 more)

### Community 32 - "Community 32"
Cohesion: 0.06
Nodes (61): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+53 more)

### Community 33 - "Community 33"
Cohesion: 0.07
Nodes (47): errorMessage(), looksLikeZip(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy() (+39 more)

### Community 34 - "Community 34"
Cohesion: 0.06
Nodes (51): WeightingFelt, WeightingFeedback, answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail() (+43 more)

### Community 35 - "Community 35"
Cohesion: 0.06
Nodes (50): diversOf(), SiteRow, Cursor, cursorAt(), DateOnly(), defineTool(), Detail, diversAsked() (+42 more)

### Community 36 - "Community 36"
Cohesion: 0.05
Nodes (50): ADR-0036, headers, ADR-0030, ADR-0038, ADR-0048, ADR-0062, AiAccess, assessedDive() (+42 more)

### Community 37 - "Community 37"
Cohesion: 0.08
Nodes (51): BACKUP_FORMAT_VERSION, BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32Encode(), bech32HrpExpand() (+43 more)

### Community 38 - "Community 38"
Cohesion: 0.07
Nodes (48): Delivered, ProviderError, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf() (+40 more)

### Community 39 - "Community 39"
Cohesion: 0.06
Nodes (47): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, PUBLIC_AUTH_PATHS, OpenApiDoc, withAuthEndpoints() (+39 more)

### Community 40 - "Community 40"
Cohesion: 0.07
Nodes (45): Leave, Series, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+37 more)

### Community 41 - "Community 41"
Cohesion: 0.06
Nodes (34): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+26 more)

### Community 42 - "Community 42"
Cohesion: 0.05
Nodes (45): AssessmentService, AssessmentView, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView() (+37 more)

### Community 43 - "Community 43"
Cohesion: 0.07
Nodes (45): NoFly, getDiverLoadings, DEFAULT_GF_HIGH, residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows(), DiveRow (+37 more)

### Community 44 - "Community 44"
Cohesion: 0.07
Nodes (45): CylinderMaterial, BOTTOM_TIME_RULES, cylinderOf(), cylinderText(), diverOf(), diverText(), finite(), kept (+37 more)

### Community 45 - "Community 45"
Cohesion: 0.07
Nodes (41): @modelcontextprotocol/server@^2.3.1, aiAccessLog, aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, AiAccessScope, AiAccessService (+33 more)

### Community 46 - "Community 46"
Cohesion: 0.09
Nodes (44): ConnectionRow, WaitingPushOptions, waitingOf(), audienceOfConnection(), clearIfUnchanged(), decide(), IMPORT_CAUSES, importedFrom() (+36 more)

### Community 47 - "Community 47"
Cohesion: 0.06
Nodes (43): LEAD_PLACEMENTS, WEIGHTING_FELT, ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, seriesOf() (+35 more)

### Community 48 - "Community 48"
Cohesion: 0.07
Nodes (36): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+28 more)

### Community 49 - "Community 49"
Cohesion: 0.07
Nodes (35): PushRow, ADR-0024, Choice, isExporting(), referenceOf(), Requirement, keepBuddies(), prepared() (+27 more)

### Community 50 - "Community 50"
Cohesion: 0.05
Nodes (42): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+34 more)

### Community 51 - "Community 51"
Cohesion: 0.07
Nodes (39): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+31 more)

### Community 52 - "Community 52"
Cohesion: 0.06
Nodes (35): SUIT_TYPES, SUIT_UNDERGARMENTS, DiverCertifications, replyDiverError, STATUS, CertificationSummary, DiverDetailsView, DiverDetailsViewSchema (+27 more)

### Community 53 - "Community 53"
Cohesion: 0.09
Nodes (33): BottomTimeQuery, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033, ADR-0071, DEFAULT_PPO2 (+25 more)

### Community 54 - "Community 54"
Cohesion: 0.10
Nodes (38): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+30 more)

### Community 55 - "Community 55"
Cohesion: 0.06
Nodes (37): Change(), Changes(), REVISION_CAUSES, PositionSchema, AdoptBody, Country, CreateBody, Description (+29 more)

### Community 56 - "Community 56"
Cohesion: 0.07
Nodes (32): sampleCount(), SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject() (+24 more)

### Community 57 - "Community 57"
Cohesion: 0.05
Nodes (36): AREA_OF, areaOf(), areas, capture(), findings, headers, ADR-0020, ADR-0021 (+28 more)

### Community 58 - "Community 58"
Cohesion: 0.06
Nodes (30): Queryable, Tx, AppendUndo, audienceOfDive(), Hint, HINT_KINDS, HintAudience, HintKind (+22 more)

### Community 59 - "Community 59"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 60 - "Community 60"
Cohesion: 0.08
Nodes (29): ENGINE_VERSION, EVIDENCE, Evidence, FindingValues, LIMITS, RULE_IDS, RuleId, RULES (+21 more)

### Community 61 - "Community 61"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 62 - "Community 62"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 63 - "Community 63"
Cohesion: 0.08
Nodes (31): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+23 more)

### Community 64 - "Community 64"
Cohesion: 0.09
Nodes (35): overlapWindow(), ensureItemForDevice(), recordingKeySource(), deletedEarlier(), fullerCopyHere(), IsManaged, knownRecording(), matchDecision() (+27 more)

### Community 65 - "Community 65"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 66 - "Community 66"
Cohesion: 0.13
Nodes (34): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+26 more)

### Community 67 - "Community 67"
Cohesion: 0.11
Nodes (28): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+20 more)

### Community 68 - "Community 68"
Cohesion: 0.09
Nodes (33): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+25 more)

### Community 69 - "Community 69"
Cohesion: 0.10
Nodes (21): cylinder, dive, importJob, original, ImportService, ADR-0004, ADR-0044, ADR-0048 (+13 more)

### Community 70 - "Community 70"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 71 - "Community 71"
Cohesion: 0.07
Nodes (27): Cylinder, DiveView, RevisionView, ADR-0045, upload(), Candidate, Check, Outcome (+19 more)

### Community 72 - "Community 72"
Cohesion: 0.08
Nodes (27): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+19 more)

### Community 73 - "Community 73"
Cohesion: 0.08
Nodes (28): CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError, CertificationExpiry, CertificationInput, CertificationView, createCertificationService(), cleaned() (+20 more)

### Community 74 - "Community 74"
Cohesion: 0.15
Nodes (32): siteRef(), kindOfMode(), deviceDiver(), closeFields(), entryOf(), openFields(), createDiveImportService(), asRecording() (+24 more)

### Community 75 - "Community 75"
Cohesion: 0.12
Nodes (26): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0051 (+18 more)

### Community 76 - "Community 76"
Cohesion: 0.09
Nodes (30): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+22 more)

### Community 77 - "Community 77"
Cohesion: 0.13
Nodes (29): importsQuery(), ImportView, ImportContext, ImportFilesButton(), ImportList(), ImportNotices(), ImportPanel(), ImportProvider() (+21 more)

### Community 78 - "Community 78"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 79 - "Community 79"
Cohesion: 0.12
Nodes (25): BACKUP_SKIPPED_DIRS, BackupDecryptionKey, encryptionKeyFingerprint(), pgCommand(), pgFailureMessage(), redactPassword(), createRestoreService(), restore() (+17 more)

### Community 80 - "Community 80"
Cohesion: 0.09
Nodes (27): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+19 more)

### Community 81 - "Community 81"
Cohesion: 0.12
Nodes (28): open(), createConnectionService(), checkAutomation(), checkFollowNumbers(), expiryOf(), onTurn(), ownConnection(), signInAt() (+20 more)

### Community 82 - "Community 82"
Cohesion: 0.11
Nodes (28): createConflictService(), loadWaiting(), stillDiffering(), fieldsOf(), isExists(), shownFields(), stillWaits(), baseDiveOf() (+20 more)

### Community 83 - "Community 83"
Cohesion: 0.09
Nodes (24): AssessmentError, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), requireManagedDive(), same() (+16 more)

### Community 84 - "Community 84"
Cohesion: 0.09
Nodes (23): BACKUP_EXTENSION, BackupEncryption, BackupPart, fileTreeChecksum(), isRecipient(), tarDirectory(), BackupRunRow, BackupSettingsInput (+15 more)

### Community 85 - "Community 85"
Cohesion: 0.10
Nodes (27): equipmentUse, ScheduleDue, DiveGear, DueEntry, GearItem, ItemDetailView, ItemEdit, ItemInput (+19 more)

### Community 86 - "Community 86"
Cohesion: 0.14
Nodes (25): HINT, Input, LoadingsInput, planGasAndBottomTime, ADR-0032, ADR-0035, Used(), BOTTOM_TIME_RULES (+17 more)

### Community 87 - "Community 87"
Cohesion: 0.10
Nodes (27): applyHints(), LiveArrivals, Page, useLiveArrivals(), bump(), changedListeners, ChannelMessage, healthListeners (+19 more)

### Community 88 - "Community 88"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 89 - "Community 89"
Cohesion: 0.08
Nodes (22): headers, ADR-0035, aiAccessReady(), askMcp(), createAiAccess(), goneAtSsi(), putAside(), sendToSsi() (+14 more)

### Community 90 - "Community 90"
Cohesion: 0.10
Nodes (26): globalSetup(), ADR-0023, accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks() (+18 more)

### Community 91 - "Community 91"
Cohesion: 0.07
Nodes (22): account, accountRelations, rateLimit, session, sessionRelations, ADR-0011, ADR-0012, ADR-0035 (+14 more)

### Community 92 - "Community 92"
Cohesion: 0.11
Nodes (23): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, Conditions (+15 more)

### Community 93 - "Community 93"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 94 - "Community 94"
Cohesion: 0.13
Nodes (24): diverBodyWeight, lead, LeadPlacement, SuitType, SuitUndergarment, DiveRow, LeadEntry, setLead() (+16 more)

### Community 95 - "Community 95"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 96 - "Community 96"
Cohesion: 0.11
Nodes (19): ProblemCode, ResponseState, whenResponseGone(), StoredBlob, PlacedArchive, ANSWER, looksLikeRecipientPrivateKey(), PlacedArchive (+11 more)

### Community 97 - "Community 97"
Cohesion: 0.11
Nodes (21): createFileFormats(), createFitAdapter(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf() (+13 more)

### Community 98 - "Community 98"
Cohesion: 0.14
Nodes (23): surfacePressureBar(), ndlMinutes(), ADR-0032, ADR-0046, advanceTissues(), schreinerStep(), surfaceSaturatedTissues(), ADR-0071 (+15 more)

### Community 99 - "Community 99"
Cohesion: 0.10
Nodes (20): ADR-0063, stubDive(), ADR-0015, ADR-0023, ADR-0025, ADR-0040, Status, ADR-0056 (+12 more)

### Community 100 - "Community 100"
Cohesion: 0.11
Nodes (20): diveRow(), rowOf(), ADR-0026, ADR-0048, clearQueue(), deletableDive(), diveRows(), fetchState() (+12 more)

### Community 101 - "Community 101"
Cohesion: 0.13
Nodes (21): acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), AppLockProbe (+13 more)

### Community 102 - "Community 102"
Cohesion: 0.11
Nodes (20): BackupRouteDeps, BACKUP_RUN_TASK, BackupService, backupTask(), ADR-0069, EXPIRE_WAITING_IMPORTS_TASK, PUSH_RUN_STEP_TASK, WAITING_PUSHES_TASK (+12 more)

### Community 103 - "Community 103"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 104 - "Community 104"
Cohesion: 0.15
Nodes (24): assessmentQuery(), certificationsQuery(), CertificationView, connectionsQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider() (+16 more)

### Community 105 - "Community 105"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 106 - "Community 106"
Cohesion: 0.18
Nodes (25): close(), currentRemote(), createPushService(), checkAll(), connectionOrThrow(), deleter(), deleteThere(), deletion() (+17 more)

### Community 107 - "Community 107"
Cohesion: 0.18
Nodes (22): SsiLogbook, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef(), MATERIAL_OF_TANK_TYPE, nonZero() (+14 more)

### Community 108 - "Community 108"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 109 - "Community 109"
Cohesion: 0.12
Nodes (21): CENTRE_SOURCES, CentreService, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+13 more)

### Community 110 - "Community 110"
Cohesion: 0.17
Nodes (22): uplot@^1.6.32, samplesQuery(), FindingLane(), useAssessment(), useWhen(), DepthProfile(), NO_POINTS, SurfaceBreakLane() (+14 more)

### Community 111 - "Community 111"
Cohesion: 0.11
Nodes (14): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+6 more)

### Community 112 - "Community 112"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 113 - "Community 113"
Cohesion: 0.09
Nodes (23): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+15 more)

### Community 114 - "Community 114"
Cohesion: 0.15
Nodes (22): managementOf(), EquipmentError, checkCost(), checkDates(), checkWindow(), createEquipmentService(), assemble(), checkResets() (+14 more)

### Community 115 - "Community 115"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 116 - "Community 116"
Cohesion: 0.14
Nodes (16): providerGoneDive, compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051, candidatesOf() (+8 more)

### Community 117 - "Community 117"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 118 - "Community 118"
Cohesion: 0.14
Nodes (18): serviceRecord, serviceRecordSchedule, serviceSchedule, equipmentList(), EquipmentListFilter, EquipmentRecordRead, today(), ADR-0034 (+10 more)

### Community 119 - "Community 119"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 120 - "Community 120"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 121 - "Community 121"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 122 - "Community 122"
Cohesion: 0.11
Nodes (19): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+11 more)

### Community 123 - "Community 123"
Cohesion: 0.13
Nodes (16): siteImportStatus, SiteImportRouteDeps, StartBody, STATUS, ADR-0021, SiteImportError, SiteImportService, Area (+8 more)

### Community 124 - "Community 124"
Cohesion: 0.21
Nodes (16): AssessmentView, AssessmentState, bandSeconds(), clock(), counts(), differing(), Finding, formatRate() (+8 more)

### Community 125 - "Community 125"
Cohesion: 0.22
Nodes (16): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), appendRun(), browserStats() (+8 more)

### Community 126 - "Community 126"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 127 - "Community 127"
Cohesion: 0.20
Nodes (15): formatOf(), createImportService(), cancelImport(), createImport(), endWaiting(), expireWaiting(), openUpload(), processFile() (+7 more)

### Community 128 - "Community 128"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 129 - "Community 129"
Cohesion: 0.27
Nodes (13): BackupRunView, backupSettingsQuery(), BackupSettingsView, backupsQuery(), Backups(), BackupsPage(), PROBLEM_TEXT, backupDownloadHref() (+5 more)

### Community 130 - "Community 130"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 131 - "Community 131"
Cohesion: 0.19
Nodes (11): decimal(), dive(), makeOceanicPlusUddf(), OCEANIC_DIVES, OceanicDive, site(), waypoint(), exported (+3 more)

### Community 132 - "Community 132"
Cohesion: 0.13
Nodes (10): EGYPT, ExternalId, HOUSE_REEF, Method, Position, Revision, Site, SiteImport (+2 more)

### Community 133 - "Community 133"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 134 - "Community 134"
Cohesion: 0.15
Nodes (8): CustomTypeOptions, i18next, initI18n(), resources, ADR-0014, LOADING_SOURCES, ADR-0014, none

### Community 135 - "Community 135"
Cohesion: 0.18
Nodes (11): addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, relations(), TimelineBar, TimelineBarGeometry, TimelineGapGeometry, TimelineGeometry (+3 more)

### Community 136 - "Community 136"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 137 - "Community 137"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 138 - "Community 138"
Cohesion: 0.15
Nodes (9): Sac, SAC_MISSING, SacMissing, ADR-0033, ADR-0045, Cylinder, dive, ADR-0033 (+1 more)

### Community 139 - "Community 139"
Cohesion: 0.17
Nodes (6): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, uniqueWord()

### Community 140 - "Community 140"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 141 - "Community 141"
Cohesion: 0.20
Nodes (9): Series, ChannelSeries, RecordedGas, Tank, tanksOf(), TiedCylinder, ADR-0045, readings (+1 more)

### Community 142 - "Community 142"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 143 - "Community 143"
Cohesion: 0.18
Nodes (5): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053

### Community 144 - "Community 144"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 145 - "Community 145"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 146 - "Community 146"
Cohesion: 0.20
Nodes (6): noAnswer, thrown(), ADR-0014, ApiClient, createApiClient(), ADR-0004

### Community 147 - "Community 147"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 148 - "Community 148"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

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
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 154 - "Community 154"
Cohesion: 0.25
Nodes (4): isUnauthorized(), LocalizedApp(), onError(), queryClient

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
Cohesion: 0.43
Nodes (5): nearest(), perMinute(), summarize(), depth, temperature

### Community 161 - "Community 161"
Cohesion: 0.33
Nodes (4): pollUntilDue(), ADR-0056, now, ADR-0056

### Community 162 - "Community 162"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 163 - "Community 163"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 164 - "Community 164"
Cohesion: 0.40
Nodes (4): label(), CompareSubject, facts, subjects

### Community 165 - "Community 165"
Cohesion: 0.40
Nodes (4): offer, osm, ssi, wikidata

### Community 166 - "Community 166"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 167 - "Community 167"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 170 - "Community 170"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 171 - "Community 171"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 172 - "Community 172"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1138 isolated node(s):** `ADR-0012`, `ADR-0013`, `ADR-0030`, `ADR-0035`, `ADR-0031` (+1133 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 2946 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **7 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 3` to `Community 4`, `Community 7`, `Community 8`, `Community 9`, `Community 11`, `Community 13`, `Community 14`, `Community 17`, `Community 18`, `Community 20`, `Community 21`, `Community 22`, `Community 25`, `Community 27`, `Community 28`, `Community 29`, `Community 30`, `Community 31`, `Community 35`, `Community 39`, `Community 43`, `Community 45`, `Community 46`, `Community 49`, `Community 53`, `Community 58`, `Community 63`, `Community 64`, `Community 68`, `Community 69`, `Community 71`, `Community 73`, `Community 84`, `Community 85`, `Community 88`, `Community 91`, `Community 94`, `Community 113`, `Community 116`, `Community 118`?**
  _High betweenness centrality (0.041) - this node is a cross-community bridge._
- **What connects `ADR-0012`, `ADR-0013`, `ADR-0030` to the rest of the system?**
  _1138 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.04405286343612335 - nodes in this community are weakly interconnected._
- **Why does `@playwright/test` connect `Community 41` to `Community 99`, `Community 36`, `Community 100`, `Community 103`, `Community 75`, `Community 139`, `Community 140`, `Community 76`, `Community 144`, `Community 145`, `Community 89`, `Community 90`, `Community 57`?**
  _High betweenness centrality (0.022) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.024471370871941078 - nodes in this community are weakly interconnected._
- **Why does `DiveExportAction` connect `Community 153` to `Community 8`, `Community 49`, `Community 38`?**
  _High betweenness centrality (0.020) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.02725598526703499 - nodes in this community are weakly interconnected._