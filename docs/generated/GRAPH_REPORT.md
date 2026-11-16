# Graph Report - dive_hub  (2026-11-16)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 7215 nodes · 22487 edges · 180 communities (175 shown, 5 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 222 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `dd3e4e3b`
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
1. `Notice()` - 203 edges
2. `unwrap()` - 200 edges
3. `Button()` - 197 edges
4. `useErrorText()` - 168 edges
5. `useDisplay()` - 159 edges
6. `Muted()` - 137 edges
7. `drizzle-orm` - 134 edges
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
- `Context` --references--> `Position`  [EXTRACTED]
  apps/server/src/imports/formats/uddf/uddf.ts → apps/server/src/vocabulary.ts

## Import Cycles
- None detected.

## Communities (180 total, 5 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.05
Nodes (173): ChangePassword(), DisplaySettings(), AdminSettings(), DeleteUserDialog(), Invitations(), Invite(), LinkToPassOn(), ProviderSiteData() (+165 more)

### Community 1 - "Community 1"
Cohesion: 0.04
Nodes (112): user, accountListEntry, diver, revision, forget(), listAccountOf(), remember(), rememberedEntries() (+104 more)

### Community 2 - "Community 2"
Cohesion: 0.04
Nodes (111): Queryable, LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, participant, recording, renumber(), renumberStatement() (+103 more)

### Community 3 - "Community 3"
Cohesion: 0.03
Nodes (87): equipmentDueQuery(), EquipmentDueView, isUnauthorized(), CodeFigure(), DiveCodes(), showDiveCodes(), ADR-0043, EquipmentDueNotice() (+79 more)

### Community 4 - "Community 4"
Cohesion: 0.04
Nodes (105): AcceptedBuddySuggestionView, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView, candidatesQuery(), CandidateView (+97 more)

### Community 5 - "Community 5"
Cohesion: 0.04
Nodes (102): assessmentQuery(), diveNeighboursQuery(), DiveValues, OverridableField, RequirementView, revisionsQuery(), RevisionView, UtcOffsetSource (+94 more)

### Community 6 - "Community 6"
Cohesion: 0.04
Nodes (99): divesQuery(), ExternalIdView, LogbookPage, PAGE_SIZE, App(), BackupsPage, CentrePage, CentresPage (+91 more)

### Community 7 - "Community 7"
Cohesion: 0.05
Nodes (94): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, AssessmentView, assessmentRoutes() (+86 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (77): diveLease, providerConflict, push, pushRun, waitingPush, kept(), record(), ExportingProvider (+69 more)

### Community 9 - "Community 9"
Cohesion: 0.04
Nodes (75): auth, BackupServiceDeps, createServices(), ServiceParts, ADR-0027, ADR-0030, ADR-0061, ADR-0063 (+67 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (78): connection, createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities() (+70 more)

### Community 11 - "Community 11"
Cohesion: 0.02
Nodes (95): AiAccessLogEntry, AiAccessView, AnyLogbookCheck, BuddyView, CertificationView, ContinuationCandidateView, DETAILS, DiveGearItemView (+87 more)

### Community 12 - "Community 12"
Cohesion: 0.04
Nodes (69): createServer(), loadAuthSecret(), acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock (+61 more)

### Community 13 - "Community 13"
Cohesion: 0.04
Nodes (93): ConflictFieldView, connectionsQuery(), ConnectionView, DiveImportMode, DiveImportPreview, diveImportPreviewQuery(), DiveKind, diveListPages (+85 more)

### Community 14 - "Community 14"
Cohesion: 0.04
Nodes (85): CertificationService, CYLINDER_MATERIALS, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053, ErrorAnswer, IdParams (+77 more)

### Community 15 - "Community 15"
Cohesion: 0.05
Nodes (82): diverExternalId, diveSite, dataIdsOf(), settleSiteVisibility(), previewSites(), ProviderAdapter, outgoingSite(), findOrMake() (+74 more)

### Community 16 - "Community 16"
Cohesion: 0.03
Nodes (70): Assessment, FindingView, Row, ADR-0036, upload(), Check, Deleted, DiveView (+62 more)

### Community 17 - "Community 17"
Cohesion: 0.04
Nodes (75): replyCertificationError, STATUS, CertificationView, Day, ADR-0050, CardParams, CertificationRouteDeps, errors (+67 more)

### Community 18 - "Community 18"
Cohesion: 0.04
Nodes (76): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+68 more)

### Community 19 - "Community 19"
Cohesion: 0.02
Nodes (85): actorType, aiAccessOutcome, backupRunKind, backupRunStatus, candidateResolution, centreSource, CERTIFICATION_IMAGE_STATES, CERTIFICATION_VERIFICATIONS (+77 more)

### Community 20 - "Community 20"
Cohesion: 0.04
Nodes (81): AiAccessScope, seesPositions(), VerifiedAccess, diversOf(), SiteRow, checkedByRunTool, createMcpServer(), INSTRUCTIONS (+73 more)

### Community 21 - "Community 21"
Cohesion: 0.05
Nodes (73): diveCentre, logbookCheckAnswer, logbookCheckDiveAnswer, agreeInDepthAndDuration(), CheckedDive, DeletableDive, findChecks(), followsSoon() (+65 more)

### Community 22 - "Community 22"
Cohesion: 0.04
Nodes (67): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+59 more)

### Community 23 - "Community 23"
Cohesion: 0.05
Nodes (65): adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, Auth, AuthOptions, CLIENT_IP_HEADER, createAuth(), PUBLIC_AUTH_PATHS (+57 more)

### Community 24 - "Community 24"
Cohesion: 0.05
Nodes (62): diveSiteExternalId, DIVE_FIELDS, createSsiAdapter(), SSI_CAPABILITIES, createSsiClient(), ssiSiteRow(), Fetch, Card (+54 more)

### Community 25 - "Community 25"
Cohesion: 0.04
Nodes (67): LEAD_PLACEMENTS, UTC_OFFSET_SOURCES, WEIGHTING_FELT, sacOfDive(), ADR-0025, WaterMismatch, waterMismatch(), withoutPressures() (+59 more)

### Community 26 - "Community 26"
Cohesion: 0.06
Nodes (63): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ambientPressureBar(), bestMixPercent() (+55 more)

### Community 27 - "Community 27"
Cohesion: 0.07
Nodes (67): allButDive(), continuationCandidateQuery(), DeletedDiveView, diveProvidersQuery(), diveQuery(), DiveView, mergeCandidatesQuery(), nearbyDivesQuery() (+59 more)

### Community 28 - "Community 28"
Cohesion: 0.06
Nodes (62): SecuritySettings(), BackupRunView, backupSettingsQuery(), BackupSettingsView, backupsQuery(), importsQuery(), ImportView, Logbook() (+54 more)

### Community 29 - "Community 29"
Cohesion: 0.05
Nodes (64): ComputerChoice, DiveImportMode, DiveKind, importOriginal, providerSiteData, ProviderCylinder, siteRef(), ConflictEntry (+56 more)

### Community 30 - "Community 30"
Cohesion: 0.06
Nodes (62): ConnectionRow, WaitingPushOptions, createImport(), startImport(), audienceOfConnection(), audienceOfDive(), audienceOfDiver(), emitHint() (+54 more)

### Community 31 - "Community 31"
Cohesion: 0.05
Nodes (59): CylinderMaterial, SuitType, SuitUndergarment, WeightingFelt, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, Suit (+51 more)

### Community 32 - "Community 32"
Cohesion: 0.06
Nodes (59): PushLeftOut, waitingOf(), close(), currentRemote(), Choice, Capabilities, isExporting(), referenceOf() (+51 more)

### Community 33 - "Community 33"
Cohesion: 0.05
Nodes (51): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+43 more)

### Community 34 - "Community 34"
Cohesion: 0.06
Nodes (55): ImportArea, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, siteImport (+47 more)

### Community 35 - "Community 35"
Cohesion: 0.04
Nodes (57): buddySuggestion, jointDive, Accepted, Candidate, Check, DiveView, Outcome, RevisionView (+49 more)

### Community 36 - "Community 36"
Cohesion: 0.07
Nodes (61): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), clearFailed(), clearPassed(), countingBase() (+53 more)

### Community 37 - "Community 37"
Cohesion: 0.09
Nodes (50): api, ApiError, buddiesForQuery(), buddiesQuery(), centreQuery(), centresQuery(), CentreView, diverSearchQuery() (+42 more)

### Community 38 - "Community 38"
Cohesion: 0.05
Nodes (53): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, cancelWaitingImports() (+45 more)

### Community 39 - "Community 39"
Cohesion: 0.06
Nodes (46): ImportErrorCode, ImportFound, ImportOutcome, errorMessage(), ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS (+38 more)

### Community 40 - "Community 40"
Cohesion: 0.06
Nodes (50): externalDiver(), mergeExternalDiver(), participations(), AutomationSettings, createConnectionService(), checkAutomation(), checkFollowNumbers(), expiryOf() (+42 more)

### Community 41 - "Community 41"
Cohesion: 0.05
Nodes (51): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+43 more)

### Community 42 - "Community 42"
Cohesion: 0.07
Nodes (44): providerLease, tidyName(), DiverDetails, Caller, foldName(), nameDiffers(), Buddy, createBuddyService() (+36 more)

### Community 43 - "Community 43"
Cohesion: 0.11
Nodes (48): withBuddies(), APP_KEYS, appRow(), BuddySaved, byId(), changedKeys(), deleteTestDive(), deleteTestEntries() (+40 more)

### Community 44 - "Community 44"
Cohesion: 0.05
Nodes (49): EquipmentService, RecordInput, ScheduleInput, DiveGearItemViewSchema, DiveGearViewSchema, DueState, EquipmentDetailViewSchema, EquipmentDueViewSchema (+41 more)

### Community 45 - "Community 45"
Cohesion: 0.07
Nodes (47): equipmentItem, buildJoined(), partInput(), partsOfJoined(), rebuildJoined(), RecordingRow, ADR-0063, writeJoined() (+39 more)

### Community 46 - "Community 46"
Cohesion: 0.07
Nodes (43): Series, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, keepTraining(), ssiDiveType(), ssiInteger() (+35 more)

### Community 47 - "Community 47"
Cohesion: 0.07
Nodes (38): createSsiSiteSource(), DEFAULT_SSI_SITES_URL, createSiteSources(), ADR-0021, ADR-0027, createOverpassSource(), DEFAULT_OVERPASS_URL, Element (+30 more)

### Community 48 - "Community 48"
Cohesion: 0.06
Nodes (42): Blocked, classify(), FailureOutcome, failureOutcome(), kindOf(), KINDS, LEFT_OUT_KEY, LEFT_OUT_KEYS (+34 more)

### Community 49 - "Community 49"
Cohesion: 0.07
Nodes (33): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+25 more)

### Community 50 - "Community 50"
Cohesion: 0.09
Nodes (44): cylinderCatalogueQuery(), CylinderFields(), MATERIALS, ADR-0034, ADR-0045, useCatalogueName(), useCylinderProblem(), DiveEditForm() (+36 more)

### Community 51 - "Community 51"
Cohesion: 0.09
Nodes (46): pushRunActive(), pushRunBody(), PushRunChoices, PushRunDetail, PushRunPreview, pushRunPreviewQuery(), pushRunQuery(), pushRunsQuery() (+38 more)

### Community 52 - "Community 52"
Cohesion: 0.10
Nodes (45): ParticipantRole, tieToJoined(), cylindersOf(), mergeProviderCylinder(), providerCylinderOf(), participantsOf(), columnsOf(), partsOf() (+37 more)

### Community 53 - "Community 53"
Cohesion: 0.05
Nodes (33): ProviderImportPlan, Direction, DiveExport, DiveField, DiveListing, DiveOrigin, FindBy, Leave (+25 more)

### Community 54 - "Community 54"
Cohesion: 0.08
Nodes (34): SsiClient, SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), emptyRecord(), writableRecord(), FakeSsi (+26 more)

### Community 55 - "Community 55"
Cohesion: 0.09
Nodes (42): BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32HrpExpand(), bech32Polymod(), checkScryptBounds() (+34 more)

### Community 56 - "Community 56"
Cohesion: 0.07
Nodes (41): noFlyAfter(), getDiverLoadings, DEFAULT_GF_HIGH, AIR, CNS_HALF_LIFE_MINUTES, cnsDecayed(), DEFAULT_CONDITIONS, LoadingBasis (+33 more)

### Community 57 - "Community 57"
Cohesion: 0.06
Nodes (36): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+28 more)

### Community 58 - "Community 58"
Cohesion: 0.09
Nodes (33): BottomTimeQuery, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033, DEFAULT_PPO2, floorTenth() (+25 more)

### Community 59 - "Community 59"
Cohesion: 0.05
Nodes (40): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+32 more)

### Community 60 - "Community 60"
Cohesion: 0.06
Nodes (36): SUIT_TYPES, SUIT_UNDERGARMENTS, DiverCertifications, replyDiverError, STATUS, DiverService, CertificationSummary, DiverDetailsView (+28 more)

### Community 61 - "Community 61"
Cohesion: 0.10
Nodes (35): samplesQuery(), FindingLane(), useAssessment(), DepthProfile(), NO_POINTS, SurfaceBreakLane(), ADR-0036, ADR-0063 (+27 more)

### Community 62 - "Community 62"
Cohesion: 0.05
Nodes (42): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+34 more)

### Community 63 - "Community 63"
Cohesion: 0.07
Nodes (29): AssessmentError, CHANNELS, requireManagedDive(), NOT_COVERED, ADR-0036, continuationAppend, diveAssessment, recordingEvent (+21 more)

### Community 64 - "Community 64"
Cohesion: 0.06
Nodes (32): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError (+24 more)

### Community 65 - "Community 65"
Cohesion: 0.06
Nodes (39): Change(), Changes(), historyRoute, Made(), REVISION_CAUSES, PositionSchema, AdoptBody, Country (+31 more)

### Community 66 - "Community 66"
Cohesion: 0.08
Nodes (34): date(), FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position(), positive() (+26 more)

### Community 67 - "Community 67"
Cohesion: 0.07
Nodes (38): ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, seriesOf(), minutes(), round() (+30 more)

### Community 68 - "Community 68"
Cohesion: 0.06
Nodes (29): Tx, device, ADR-0028, ADR-0043, ADR-0070, CandidateError, CandidateRow, createCandidateService() (+21 more)

### Community 69 - "Community 69"
Cohesion: 0.07
Nodes (32): SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject(), MAX_CARD_IMAGE_BYTES (+24 more)

### Community 70 - "Community 70"
Cohesion: 0.09
Nodes (35): Answer, massIn(), Dive, draftOfLead(), draftOfWeighting(), emptyLead(), hasThickness(), kg() (+27 more)

### Community 71 - "Community 71"
Cohesion: 0.10
Nodes (31): LogbookCheckService, AppendDeps, AppendOutcome, AppendService, Side, ADR-0038, ADR-0063, ContinuationService (+23 more)

### Community 72 - "Community 72"
Cohesion: 0.08
Nodes (35): Delivered, buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open(), saveAndReadBack() (+27 more)

### Community 73 - "Community 73"
Cohesion: 0.08
Nodes (35): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+27 more)

### Community 74 - "Community 74"
Cohesion: 0.10
Nodes (30): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0051 (+22 more)

### Community 75 - "Community 75"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 76 - "Community 76"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 77 - "Community 77"
Cohesion: 0.12
Nodes (35): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+27 more)

### Community 78 - "Community 78"
Cohesion: 0.09
Nodes (35): BOTTOM_TIME_RULES, cylinderOf(), cylinderText(), diverOf(), diverText(), finite(), kept, levelsOf() (+27 more)

### Community 79 - "Community 79"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 80 - "Community 80"
Cohesion: 0.08
Nodes (31): AI_ACCESS_NAME_MAX, aiAccessLog, aiAccessSetting, Access, AccessView, AiAccessRouteDeps, LogEntryView, Scope (+23 more)

### Community 81 - "Community 81"
Cohesion: 0.09
Nodes (33): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+25 more)

### Community 82 - "Community 82"
Cohesion: 0.07
Nodes (28): DELETE_QUIET_MS, Cylinder, DiveView, RevisionView, ADR-0045, upload(), Candidate, Deleted (+20 more)

### Community 83 - "Community 83"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 84 - "Community 84"
Cohesion: 0.07
Nodes (25): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+17 more)

### Community 85 - "Community 85"
Cohesion: 0.07
Nodes (22): CustomTypeOptions, i18next, initI18n(), resources, ADR-0014, StrayOverride, ADR-0014, english (+14 more)

### Community 86 - "Community 86"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 87 - "Community 87"
Cohesion: 0.09
Nodes (25): AssessmentService, AssessmentRouteDeps, BackupRouteDeps, BACKUP_RUN_TASK, BackupService, backupTask(), ADR-0069, EXPIRE_WAITING_IMPORTS_TASK (+17 more)

### Community 88 - "Community 88"
Cohesion: 0.12
Nodes (25): BACKUP_SKIPPED_DIRS, BackupDecryptionKey, encryptionKeyFingerprint(), pgCommand(), pgFailureMessage(), redactPassword(), createRestoreService(), restore() (+17 more)

### Community 89 - "Community 89"
Cohesion: 0.11
Nodes (19): cylinder, dive, duplicateCandidate, importJob, original, ImportService, ADR-0044, PreviewView (+11 more)

### Community 90 - "Community 90"
Cohesion: 0.15
Nodes (31): kindOfMode(), deviceDiver(), closeFields(), entryOf(), openFields(), importsField(), createDiveImportService(), asRecording() (+23 more)

### Community 91 - "Community 91"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 92 - "Community 92"
Cohesion: 0.11
Nodes (27): Sessions(), useDescribeAgent(), authClient, ProblemCode, sessionsQuery(), SessionView, focusFirstIn(), focusHeading() (+19 more)

### Community 93 - "Community 93"
Cohesion: 0.11
Nodes (29): ToolsPlan, ToolsPlanDiver, PlanDiver, PlanLevel, planSketch(), PlanStretch, ToolsRule, DEFAULT_CYLINDER (+21 more)

### Community 94 - "Community 94"
Cohesion: 0.07
Nodes (23): account, accountRelations, apikey, rateLimit, sessionRelations, ADR-0011, ADR-0012, ADR-0035 (+15 more)

### Community 95 - "Community 95"
Cohesion: 0.11
Nodes (22): RecordingSummary, UtcOffsetSource, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput (+14 more)

### Community 96 - "Community 96"
Cohesion: 0.16
Nodes (25): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+17 more)

### Community 97 - "Community 97"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 98 - "Community 98"
Cohesion: 0.11
Nodes (24): equipmentUse, DiveGear, ensureItemForDevice(), GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow (+16 more)

### Community 99 - "Community 99"
Cohesion: 0.09
Nodes (25): pushRunItem, PushRunLeftOut, asksFor(), DIVE_ONLY, DIVE_ONLY_PATTERN, DiveListFilter, diveListFilter(), DiveOnly (+17 more)

### Community 100 - "Community 100"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 101 - "Community 101"
Cohesion: 0.18
Nodes (23): SsiLogbook, asBool(), diveModeOfSsi(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef(), MATERIAL_OF_TANK_TYPE (+15 more)

### Community 102 - "Community 102"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 103 - "Community 103"
Cohesion: 0.17
Nodes (25): bottomTimeRule(), defined(), leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas, toolsGasQuery(), toolsPlanQuery() (+17 more)

### Community 104 - "Community 104"
Cohesion: 0.14
Nodes (21): DurationFormatter, formatClock(), formatDepth(), formatGasRate(), formatMass(), formatPressure(), formatPressureRate(), formatTemperature() (+13 more)

### Community 105 - "Community 105"
Cohesion: 0.11
Nodes (17): ProblemCode, ResponseState, whenResponseGone(), StoredBlob, PlacedArchive, ANSWER, looksLikeRecipientPrivateKey(), PlacedArchive (+9 more)

### Community 106 - "Community 106"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 107 - "Community 107"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 108 - "Community 108"
Cohesion: 0.16
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 109 - "Community 109"
Cohesion: 0.09
Nodes (20): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+12 more)

### Community 110 - "Community 110"
Cohesion: 0.12
Nodes (22): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+14 more)

### Community 111 - "Community 111"
Cohesion: 0.12
Nodes (20): CENTRE_SOURCES, CentreService, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+12 more)

### Community 112 - "Community 112"
Cohesion: 0.14
Nodes (18): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+10 more)

### Community 113 - "Community 113"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 114 - "Community 114"
Cohesion: 0.15
Nodes (18): qr@0.7.2, cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043 (+10 more)

### Community 115 - "Community 115"
Cohesion: 0.17
Nodes (19): AssessmentView, AssessmentState, bandSeconds(), clock(), counts(), differing(), Finding, formatRate() (+11 more)

### Community 116 - "Community 116"
Cohesion: 0.14
Nodes (16): providerGoneDive, compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051, candidatesOf() (+8 more)

### Community 117 - "Community 117"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 118 - "Community 118"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 119 - "Community 119"
Cohesion: 0.15
Nodes (19): BACKUP_FORMAT_VERSION, BackupManifest, buildManifest(), decryptFile(), decryptFileWith(), encryptFile(), encryptStream(), execFileAsync (+11 more)

### Community 120 - "Community 120"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 121 - "Community 121"
Cohesion: 0.15
Nodes (18): LeadEstimate, LeadHint(), orUndefined(), ADR-0025, ADR-0031, LEAD_MATERIALS, LEAD_WATERS, LeadCylinder (+10 more)

### Community 122 - "Community 122"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 123 - "Community 123"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 124 - "Community 124"
Cohesion: 0.11
Nodes (13): BACKUP_EXTENSION, BackupEncryption, BackupPart, BackupLog, BackupRunRow, BackupSettingsInput, BackupSettingsValues, DEFAULT_BACKUP_SETTINGS (+5 more)

### Community 125 - "Community 125"
Cohesion: 0.16
Nodes (16): inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), placeLocalTime(), Position, ADR-0030, wallClockMs() (+8 more)

### Community 126 - "Community 126"
Cohesion: 0.17
Nodes (15): createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), isRule(), read(), same() (+7 more)

### Community 127 - "Community 127"
Cohesion: 0.12
Nodes (16): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+8 more)

### Community 128 - "Community 128"
Cohesion: 0.16
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 129 - "Community 129"
Cohesion: 0.22
Nodes (13): AppLockProbe, Arguments, credentialOf(), FILE_OPTIONS, fileText(), finishedArguments(), parseArguments(), RestoreCliDeps (+5 more)

### Community 130 - "Community 130"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 131 - "Community 131"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 132 - "Community 132"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 133 - "Community 133"
Cohesion: 0.16
Nodes (14): serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, DueEntry, ScheduleView, equipmentList(), EquipmentListFilter (+6 more)

### Community 134 - "Community 134"
Cohesion: 0.26
Nodes (15): managementOf(), createEquipmentService(), assemble(), detailOf(), lastCylinderOf(), managedItem(), managedRecord(), managedSchedule() (+7 more)

### Community 135 - "Community 135"
Cohesion: 0.13
Nodes (10): EGYPT, ExternalId, HOUSE_REEF, Method, Position, Revision, Site, SiteImport (+2 more)

### Community 136 - "Community 136"
Cohesion: 0.18
Nodes (12): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearPushRuns(), conflictAtSsi() (+4 more)

### Community 137 - "Community 137"
Cohesion: 0.18
Nodes (11): addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, relations(), TimelineBar, TimelineBarGeometry, TimelineGapGeometry, TimelineGeometry (+3 more)

### Community 138 - "Community 138"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 139 - "Community 139"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 140 - "Community 140"
Cohesion: 0.21
Nodes (9): fileTreeChecksum(), createBackupService(), checkSettings(), due(), enqueueScheduled(), readSettings(), saveSettings(), sweepRetention() (+1 more)

### Community 141 - "Community 141"
Cohesion: 0.22
Nodes (11): flag(), idText(), isObject(), numberOrNull(), readSsiSite(), visibilityFor(), WATER_OF_BOW, ALPHA3_TO_ALPHA2 (+3 more)

### Community 142 - "Community 142"
Cohesion: 0.17
Nodes (6): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, uniqueWord()

### Community 143 - "Community 143"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 144 - "Community 144"
Cohesion: 0.18
Nodes (10): EquipmentError, ADR-0034, ADR-0053, checkCost(), checkDates(), checkWindow(), checkResets(), checkSchedule() (+2 more)

### Community 145 - "Community 145"
Cohesion: 0.30
Nodes (11): asText(), compareReadBack(), describe(), isObject(), keyDifferences(), pause(), recording(), runAnswerChecks() (+3 more)

### Community 146 - "Community 146"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 147 - "Community 147"
Cohesion: 0.20
Nodes (10): CandidateRouteDeps, CandidateView, Decided, DiveBrief, OffsetSource, STATUS, ADR-0016, ADR-0030 (+2 more)

### Community 148 - "Community 148"
Cohesion: 0.25
Nodes (8): ImportView, toImportView(), ImportRouteDeps, importRoutes(), ADR-0044, ADR-0056, withDecisions(), UploadTooLargeError

### Community 149 - "Community 149"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 150 - "Community 150"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 151 - "Community 151"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 152 - "Community 152"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 153 - "Community 153"
Cohesion: 0.27
Nodes (6): Profile, SKETCH_HEIGHT, SKETCH_WIDTH, sketchPaths(), ADR-0041, ADR-0041

### Community 154 - "Community 154"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 155 - "Community 155"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 156 - "Community 156"
Cohesion: 0.20
Nodes (10): verify, major, minor, sha, tag, version, name, outputs (+2 more)

### Community 157 - "Community 157"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 159 - "Community 159"
Cohesion: 0.25
Nodes (8): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, MODULE_IMPORTS, moduleRules, target()

### Community 160 - "Community 160"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 161 - "Community 161"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 162 - "Community 162"
Cohesion: 0.36
Nodes (8): bech32Encode(), convertBits(), encodeRecipient(), encodeRecipientPrivateKey(), generateRecipientKeyPair(), jwkOf(), rawPrivateOf(), rawPublicOf()

### Community 163 - "Community 163"
Cohesion: 0.25
Nodes (7): AIR, expectWithinReference(), GF100_FRESH, GF100_SALT, GF85_FRESH, ADR-0032, ADR-0046

### Community 164 - "Community 164"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 165 - "Community 165"
Cohesion: 0.29
Nodes (6): itemIds(), ADR-0026, ADR-0034, Usage, UsageDive, UsageRow

### Community 166 - "Community 166"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 167 - "Community 167"
Cohesion: 0.33
Nodes (4): pollUntilDue(), ADR-0056, now, ADR-0056

### Community 168 - "Community 168"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 169 - "Community 169"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 170 - "Community 170"
Cohesion: 0.40
Nodes (4): label(), CompareSubject, facts, subjects

### Community 171 - "Community 171"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 172 - "Community 172"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 173 - "Community 173"
Cohesion: 0.40
Nodes (4): TAGS, ADR-0023, { stdout, stderr }, total

### Community 175 - "Community 175"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 176 - "Community 176"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

## Knowledge Gaps
- **1129 isolated node(s):** `ADR-0012`, `ADR-0013`, `ADR-0030`, `ADR-0035`, `ADR-0031` (+1124 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 2929 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **5 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 1` to `Community 2`, `Community 133`, `Community 7`, `Community 8`, `Community 9`, `Community 10`, `Community 12`, `Community 14`, `Community 15`, `Community 16`, `Community 17`, `Community 19`, `Community 20`, `Community 21`, `Community 22`, `Community 23`, `Community 24`, `Community 29`, `Community 30`, `Community 32`, `Community 34`, `Community 35`, `Community 165`, `Community 39`, `Community 40`, `Community 41`, `Community 42`, `Community 45`, `Community 52`, `Community 56`, `Community 58`, `Community 63`, `Community 64`, `Community 68`, `Community 80`, `Community 81`, `Community 82`, `Community 89`, `Community 94`, `Community 98`, `Community 99`, `Community 116`, `Community 124`, `Community 125`?**
  _High betweenness centrality (0.040) - this node is a cross-community bridge._
- **What connects `ADR-0012`, `ADR-0013`, `ADR-0030` to the rest of the system?**
  _1129 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.05318843079753896 - nodes in this community are weakly interconnected._
- **Why does `@playwright/test` connect `Community 49` to `Community 33`, `Community 131`, `Community 102`, `Community 38`, `Community 136`, `Community 74`, `Community 91`, `Community 142`, `Community 143`, `Community 149`, `Community 150`, `Community 59`?**
  _High betweenness centrality (0.024) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.04113834883065652 - nodes in this community are weakly interconnected._
- **Why does `Button()` connect `Community 0` to `Community 3`, `Community 4`, `Community 5`, `Community 37`, `Community 6`, `Community 70`, `Community 11`, `Community 13`, `Community 92`, `Community 114`, `Community 50`, `Community 51`, `Community 121`, `Community 27`, `Community 28`, `Community 93`?**
  _High betweenness centrality (0.019) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.03949311023622047 - nodes in this community are weakly interconnected._