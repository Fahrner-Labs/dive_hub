# Graph Report - dive_hub  (2026-11-15)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 7091 nodes · 22122 edges · 192 communities (187 shown, 5 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 221 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `e8242cf3`
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
- Community 184
- Community 185
- Community 186
- Community 187
- Community 188
- Community 189
- Community 191

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 203 edges
2. `unwrap()` - 200 edges
3. `Button()` - 197 edges
4. `useErrorText()` - 168 edges
5. `useDisplay()` - 156 edges
6. `Muted()` - 137 edges
7. `drizzle-orm` - 131 edges
8. `announce()` - 105 edges
9. `createTestDatabase()` - 99 edges
10. `TestDatabase` - 98 edges

## Surprising Connections (you probably didn't know these)
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `Removal` --references--> `ProviderServiceError`  [EXTRACTED]
  apps/server/src/providers/push/push-service.ts → apps/server/src/providers/registry.ts
- `Context` --references--> `Position`  [EXTRACTED]
  apps/server/src/imports/formats/uddf/uddf.ts → apps/server/src/vocabulary.ts

## Import Cycles
- None detected.

## Communities (192 total, 5 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (145): buddySuggestion, jointDive, original, parseHint(), ADR-0013, Assessment, FindingView, Row (+137 more)

### Community 1 - "Community 1"
Cohesion: 0.04
Nodes (147): AcceptedBuddySuggestionView, allButDive(), ApiError, BuddySuggestionOverlapView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView, candidatesQuery() (+139 more)

### Community 2 - "Community 2"
Cohesion: 0.03
Nodes (139): assessmentQuery(), bodyWeightsQuery(), cylinderCatalogueQuery(), diveNeighboursQuery(), DiveValues, LeadEstimate, NeighboursView, OverridableField (+131 more)

### Community 3 - "Community 3"
Cohesion: 0.06
Nodes (131): DisplaySettings(), AdminSettings(), DeleteUserDialog(), Invitations(), Invite(), LinkToPassOn(), ProviderSiteData(), SiteDataPanel() (+123 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (136): buddiesQuery(), BuddyView, certificationsQuery(), CertificationView, ConflictChoices, ConflictEntryView, ConflictFieldView, ConflictsOfConnection (+128 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (113): diveSiteExternalId, DIVE_FIELDS, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf() (+105 more)

### Community 6 - "Community 6"
Cohesion: 0.04
Nodes (110): ChangePassword(), SecuritySettings(), Sessions(), useDescribeAgent(), authClient, BackupRunView, backupSettingsQuery(), BackupSettingsView (+102 more)

### Community 7 - "Community 7"
Cohesion: 0.04
Nodes (93): ExternalIdView, DiveCodes(), showDiveCodes(), ADR-0043, avatarTone(), initials(), subscribeNarrow, subscribeRoomy (+85 more)

### Community 8 - "Community 8"
Cohesion: 0.04
Nodes (87): user, accountListEntry, cylinder, dive, diver, revision, forget(), forgetEntry() (+79 more)

### Community 9 - "Community 9"
Cohesion: 0.04
Nodes (95): CertificationService, CYLINDER_MATERIALS, DIVE_IMPORT_MODES, PARTICIPANT_ROLES, CodePart(), AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036 (+87 more)

### Community 10 - "Community 10"
Cohesion: 0.04
Nodes (96): divesQuery(), LogbookPage, pushRunActive(), PushRunChoices, PushRunDetail, PushRunPreview, pushRunQuery(), pushRunsQuery() (+88 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (93): AnyLogbookCheck, BuddySuggestionPreviewView, ContinuationCandidateView, DeletedDiveView, DETAILS, DiveCheckView, DiveGearItemView, DiveGearView (+85 more)

### Community 12 - "Community 12"
Cohesion: 0.05
Nodes (80): diverExternalId, diveSite, dataIdsOf(), settleSiteVisibility(), outgoingSite(), findOrMake(), SiteForSend, siteForSend() (+72 more)

### Community 13 - "Community 13"
Cohesion: 0.04
Nodes (63): connection, diveLease, providerConflict, push, pushRun, waitingPush, kept(), record() (+55 more)

### Community 14 - "Community 14"
Cohesion: 0.04
Nodes (61): AuthOptions, auth, BackupLog, BackupServiceDeps, createServices(), ServiceParts, Services, ADR-0027 (+53 more)

### Community 15 - "Community 15"
Cohesion: 0.05
Nodes (70): authHeaders(), forwardCookies(), sessionIdOf(), invitation, userRole, AttemptLimit, clientKey(), limitAttempts() (+62 more)

### Community 16 - "Community 16"
Cohesion: 0.02
Nodes (81): actorType, aiAccessOutcome, backupRun, backupRunKind, backupRunStatus, backupSettings, candidateResolution, centreSource (+73 more)

### Community 17 - "Community 17"
Cohesion: 0.03
Nodes (77): UTC_OFFSET_SOURCES, ADR-0025, WaterMismatch, waterMismatch(), withoutPressures(), Copies, DeleteProblemSchema, DiveSummaryFields (+69 more)

### Community 18 - "Community 18"
Cohesion: 0.07
Nodes (71): renumber(), renumberStatement(), ADR-0048, AppendRow, channelsOf(), createContinuationService(), takeIn(), cylindersBack() (+63 more)

### Community 19 - "Community 19"
Cohesion: 0.05
Nodes (51): importJob, createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities() (+43 more)

### Community 20 - "Community 20"
Cohesion: 0.05
Nodes (67): ComputerChoice, DiveImportMode, DiveKind, providerSiteData, UtcOffsetSource, ProviderCylinder, PlacedTime, close() (+59 more)

### Community 21 - "Community 21"
Cohesion: 0.04
Nodes (64): pushRunItem, PushRunLeftOut, asksFor(), DIVE_ONLY, DIVE_ONLY_PATTERN, DiveListFilter, diveListFilter(), DiveOnly (+56 more)

### Community 22 - "Community 22"
Cohesion: 0.06
Nodes (60): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ambientPressureBar(), bestMixPercent() (+52 more)

### Community 23 - "Community 23"
Cohesion: 0.05
Nodes (59): CylinderMaterial, SuitType, SuitUndergarment, WeightingFelt, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, Suit (+51 more)

### Community 24 - "Community 24"
Cohesion: 0.07
Nodes (61): BACKUP_FORMAT_VERSION, BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32Encode(), bech32HrpExpand() (+53 more)

### Community 25 - "Community 25"
Cohesion: 0.06
Nodes (61): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+53 more)

### Community 26 - "Community 26"
Cohesion: 0.06
Nodes (51): Auth, CentreService, CentreRouteDeps, downsampleMinMax(), AppendDeps, AppendOutcome, AppendService, Side (+43 more)

### Community 27 - "Community 27"
Cohesion: 0.10
Nodes (59): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, assessmentRoutes(), AI_ACCESS_NAME_MAX (+51 more)

### Community 28 - "Community 28"
Cohesion: 0.05
Nodes (54): OVERRIDABLE_FIELDS, OverridableField, CylinderError, CylinderInput, fillCylindersFromRecording(), sameSeries(), setCylinders(), ADR-0031 (+46 more)

### Community 29 - "Community 29"
Cohesion: 0.06
Nodes (45): BACKUP_EXTENSION, BACKUP_SKIPPED_DIRS, BackupDecryptionKey, BackupEncryption, BackupPart, encryptionKeyFingerprint(), fileTreeChecksum(), BackupError (+37 more)

### Community 30 - "Community 30"
Cohesion: 0.05
Nodes (36): duplicateCandidate, ImportService, listenToChanges(), ADR-0004, UploadTooLargeError, FIT_EPOCH_MS, makeSuuntoAppFit(), shape() (+28 more)

### Community 31 - "Community 31"
Cohesion: 0.07
Nodes (43): SsiClient, SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), DiveForSsi, emptyRecord(), writableRecord() (+35 more)

### Community 32 - "Community 32"
Cohesion: 0.07
Nodes (44): HINT, Input, planGasAndBottomTime, ADR-0032, ADR-0035, Used(), BOTTOM_TIME_RULES, BottomTimeQuery (+36 more)

### Community 33 - "Community 33"
Cohesion: 0.06
Nodes (45): ImportErrorCode, ImportFound, importOriginal, ImportOutcome, ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS (+37 more)

### Community 34 - "Community 34"
Cohesion: 0.05
Nodes (42): AssessmentError, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), requireManagedDive() (+34 more)

### Community 35 - "Community 35"
Cohesion: 0.08
Nodes (51): WaitingPushOptions, audienceOfConnection(), audienceOfDive(), audienceOfDiver(), currentRemote(), PushRow, ADR-0024, queueKept() (+43 more)

### Community 36 - "Community 36"
Cohesion: 0.06
Nodes (50): AiAccessScope, diversOf(), SiteRow, Cursor, cursorAt(), DateOnly(), Detail, diversAsked() (+42 more)

### Community 37 - "Community 37"
Cohesion: 0.08
Nodes (50): logbookCheckAnswer, logbookCheckDiveAnswer, recording, keepApart(), keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration() (+42 more)

### Community 38 - "Community 38"
Cohesion: 0.05
Nodes (46): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+38 more)

### Community 39 - "Community 39"
Cohesion: 0.07
Nodes (40): createServer(), createAuth(), loadAuthSecret(), acquireAppLock(), workerDeps(), Config, loadConfig(), ADR-0011 (+32 more)

### Community 40 - "Community 40"
Cohesion: 0.04
Nodes (34): CertificationAccount, RemoteCertification, ADR-0050, ADR-0053, ProviderImportPlan, Direction, DiveExport, DiveListing (+26 more)

### Community 41 - "Community 41"
Cohesion: 0.06
Nodes (40): ConnectionRow, providerGoneDive, providerLease, AutomationSettings, Credentials, FETCH_INTERVALS, FetchInterval, purpose() (+32 more)

### Community 42 - "Community 42"
Cohesion: 0.07
Nodes (48): buildJoined(), partInput(), partsOfJoined(), rebuildJoined(), RecordingRow, ADR-0063, writeJoined(), reviseDive() (+40 more)

### Community 43 - "Community 43"
Cohesion: 0.09
Nodes (48): waitingOf(), open(), createConnectionService(), checkAutomation(), checkFollowNumbers(), expiryOf(), onTurn(), ownConnection() (+40 more)

### Community 44 - "Community 44"
Cohesion: 0.06
Nodes (40): Queryable, device, diverBodyWeight, lead, LeadPlacement, ParticipantRole, managedDiverIds(), ADR-0016 (+32 more)

### Community 45 - "Community 45"
Cohesion: 0.08
Nodes (42): DiveField, Leave, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+34 more)

### Community 46 - "Community 46"
Cohesion: 0.07
Nodes (41): diverManagement, participant, DiverError, ADR-0016, ADR-0053, ACCOUNT_PATTERN, accountKey(), createDiverService() (+33 more)

### Community 47 - "Community 47"
Cohesion: 0.06
Nodes (45): LEAD_PLACEMENTS, SUIT_TYPES, SUIT_UNDERGARMENTS, WEIGHTING_FELT, ProfileSummary, round1(), Series, summariseProfile() (+37 more)

### Community 48 - "Community 48"
Cohesion: 0.07
Nodes (39): alignedForMatching(), CandidateDive, DAY_MS, decideMatch(), depthsDisagree(), DuplicateReason, MatchDecision, MAX_OFFSET_MS (+31 more)

### Community 49 - "Community 49"
Cohesion: 0.06
Nodes (42): ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, connectGarmin(), Connection, DatedDive (+34 more)

### Community 50 - "Community 50"
Cohesion: 0.05
Nodes (42): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+34 more)

### Community 51 - "Community 51"
Cohesion: 0.07
Nodes (34): APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), AppLockProbe, probeAppLock() (+26 more)

### Community 52 - "Community 52"
Cohesion: 0.08
Nodes (28): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+20 more)

### Community 53 - "Community 53"
Cohesion: 0.07
Nodes (39): siteImportStatus, PROBLEM_CODES, ProblemSchema, storedProblemCode(), ADR-0014, ADR-0027, oneOf(), refTo() (+31 more)

### Community 54 - "Community 54"
Cohesion: 0.08
Nodes (41): orUndefined(), ADR-0025, ADR-0031, CylinderDraft, CylinderMaterial, BOTTOM_TIME_RULES, cylinderOf(), cylinderText() (+33 more)

### Community 55 - "Community 55"
Cohesion: 0.05
Nodes (42): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+34 more)

### Community 56 - "Community 56"
Cohesion: 0.14
Nodes (40): withBuddies(), APP_KEYS, appRow(), BuddySaved, byId(), changedKeys(), deleteTestDive(), deleteTestEntries() (+32 more)

### Community 57 - "Community 57"
Cohesion: 0.07
Nodes (33): PushLeftOut, Choice, Capabilities, isExporting(), Requirement, prepared(), Here, NumbersFor (+25 more)

### Community 58 - "Community 58"
Cohesion: 0.06
Nodes (34): DiverCertifications, replyDiverError, STATUS, DiverService, CertificationSummary, DiverDetailsView, DiverDetailsViewSchema, DiverSource (+26 more)

### Community 59 - "Community 59"
Cohesion: 0.08
Nodes (37): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+29 more)

### Community 60 - "Community 60"
Cohesion: 0.08
Nodes (35): @modelcontextprotocol/server@^2.3.1, aiAccessLog, aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, AiAccessService, KeyRow (+27 more)

### Community 61 - "Community 61"
Cohesion: 0.13
Nodes (36): DEFAULT_SSI_URL, acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive() (+28 more)

### Community 62 - "Community 62"
Cohesion: 0.07
Nodes (35): REVISION_CAUSES, PositionSchema, AdoptBody, Country, CreateBody, Description, EditBody, errors (+27 more)

### Community 63 - "Community 63"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 64 - "Community 64"
Cohesion: 0.10
Nodes (35): diveGearQuery(), EquipmentCategory, equipmentItemQuery(), EquipmentItemView, equipmentOfDiverQuery(), EquipmentScheduleDue, EquipmentScheduleView, EquipmentStatus (+27 more)

### Community 65 - "Community 65"
Cohesion: 0.11
Nodes (34): importsQuery(), ImportView, noAnswerError(), uploadFile(), Logbook(), ImportContext, ImportFilesButton(), ImportHistory() (+26 more)

### Community 66 - "Community 66"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 67 - "Community 67"
Cohesion: 0.07
Nodes (33): fit-file-parser@^6.1.2, geo-tz@8.1.9, yauzl@^3.4.0, devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1 (+25 more)

### Community 68 - "Community 68"
Cohesion: 0.08
Nodes (30): CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite(), lock() (+22 more)

### Community 69 - "Community 69"
Cohesion: 0.06
Nodes (35): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+27 more)

### Community 70 - "Community 70"
Cohesion: 0.07
Nodes (33): EquipmentService, RecordInput, ScheduleInput, DiveGearItemViewSchema, DiveGearViewSchema, DueState, EquipmentDetailViewSchema, EquipmentDueViewSchema (+25 more)

### Community 71 - "Community 71"
Cohesion: 0.09
Nodes (33): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+25 more)

### Community 72 - "Community 72"
Cohesion: 0.11
Nodes (28): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0051 (+20 more)

### Community 73 - "Community 73"
Cohesion: 0.08
Nodes (26): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+18 more)

### Community 74 - "Community 74"
Cohesion: 0.12
Nodes (27): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+19 more)

### Community 75 - "Community 75"
Cohesion: 0.13
Nodes (32): bottomTimeRule(), defined(), leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas, toolsGasQuery(), toolsPlanQuery() (+24 more)

### Community 76 - "Community 76"
Cohesion: 0.09
Nodes (25): headers, ADR-0030, ADR-0040, conflictForLena(), diveWithoutRecording(), externalDiver(), importLena(), leaveLena() (+17 more)

### Community 77 - "Community 77"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 78 - "Community 78"
Cohesion: 0.09
Nodes (27): ENGINE_VERSION, EVIDENCE, Evidence, FindingValues, RULE_IDS, RuleId, RULES, SEVERITIES (+19 more)

### Community 79 - "Community 79"
Cohesion: 0.14
Nodes (31): siteRef(), kindOfMode(), deviceDiver(), baseDiveOf(), providerChangesOf(), importsField(), createDiveImportService(), asRecording() (+23 more)

### Community 80 - "Community 80"
Cohesion: 0.12
Nodes (25): samplesQuery(), FindingLane(), useAssessment(), DepthProfile(), NO_POINTS, SurfaceBreakLane(), ADR-0036, ADR-0063 (+17 more)

### Community 81 - "Community 81"
Cohesion: 0.07
Nodes (21): CustomTypeOptions, i18next, initI18n(), resources, ADR-0014, StrayOverride, ADR-0014, english (+13 more)

### Community 82 - "Community 82"
Cohesion: 0.07
Nodes (24): GarminClient, auth, baseDbUrl, { db, pool }, divers, E2E_USER, fakeSsi, garminDive() (+16 more)

### Community 83 - "Community 83"
Cohesion: 0.10
Nodes (23): createSsiSiteSource(), DEFAULT_SSI_SITES_URL, createSiteSources(), ADR-0021, ADR-0027, createOverpassSource(), DEFAULT_OVERPASS_URL, createPoliteHttp() (+15 more)

### Community 84 - "Community 84"
Cohesion: 0.09
Nodes (28): devDependencies, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2, @testing-library/react@^16.3.3 (+20 more)

### Community 85 - "Community 85"
Cohesion: 0.10
Nodes (23): graphile-worker@^0.18.0, BACKUP_RUN_TASK, BackupService, backupTask(), ADR-0069, EXPIRE_WAITING_IMPORTS_TASK, PROCESS_IMPORT_TASK, SCHEDULED_FETCH_TASK (+15 more)

### Community 86 - "Community 86"
Cohesion: 0.07
Nodes (22): account, accountRelations, apikey, rateLimit, session, sessionRelations, ADR-0011, ADR-0035 (+14 more)

### Community 87 - "Community 87"
Cohesion: 0.09
Nodes (23): computerEventOf(), ADR-0036, computerEventFromFit(), COMPUTER_EVENT, computerEventFromSuunto(), DECO_MODEL, decoModelFromSuunto, DIVE_MODE (+15 more)

### Community 88 - "Community 88"
Cohesion: 0.10
Nodes (23): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+15 more)

### Community 89 - "Community 89"
Cohesion: 0.09
Nodes (20): Tx, AppendUndo, ADR-0020, ADR-0056, ADR-0066, ChangeListener, ADR-0015, ADR-0016 (+12 more)

### Community 90 - "Community 90"
Cohesion: 0.10
Nodes (22): RequirementView, holdLeave(), mayLeave(), useLeaveGuard(), Draft, draftOf(), positionOf(), privateIdOf() (+14 more)

### Community 91 - "Community 91"
Cohesion: 0.11
Nodes (26): ToolsPlan, ToolsPlanDiver, PlanDiver, PlanLevel, planSketch(), PlanStretch, ToolsRule, DEFAULT_CYLINDER (+18 more)

### Community 92 - "Community 92"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 93 - "Community 93"
Cohesion: 0.09
Nodes (19): CertificationProviders, createScheduledFetch(), blocked(), due(), minimumOf(), ProviderLayer, ADR-0027, ADR-0029 (+11 more)

### Community 94 - "Community 94"
Cohesion: 0.11
Nodes (24): equipmentUse, DiveGear, ensureItemForDevice(), GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow (+16 more)

### Community 95 - "Community 95"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 96 - "Community 96"
Cohesion: 0.08
Nodes (24): defineTool(), Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), NOTICE, PLAN_RULES (+16 more)

### Community 97 - "Community 97"
Cohesion: 0.14
Nodes (23): AscentBand, ascentBandsOf(), assessProfile(), ceiling(), ppo2(), Profile, PROFILE_RULES, ProfileInput (+15 more)

### Community 98 - "Community 98"
Cohesion: 0.12
Nodes (22): SiteImportCounts, has(), inArea(), LatitudeBands, merged(), NEAR_WITHIN_M, normalise(), normalised (+14 more)

### Community 99 - "Community 99"
Cohesion: 0.12
Nodes (23): date(), FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position(), positive() (+15 more)

### Community 100 - "Community 100"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 101 - "Community 101"
Cohesion: 0.10
Nodes (22): Answer, Dive, draftOfLead(), draftOfWeighting(), kg(), LastWeighting, LEAD_PLACEMENTS, LeadDraft (+14 more)

### Community 102 - "Community 102"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 103 - "Community 103"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 104 - "Community 104"
Cohesion: 0.09
Nodes (20): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+12 more)

### Community 105 - "Community 105"
Cohesion: 0.14
Nodes (20): tidyName(), DiverDetails, foldName(), nameDiffers(), Buddy, createBuddyService(), createFrom(), linkEntry() (+12 more)

### Community 106 - "Community 106"
Cohesion: 0.12
Nodes (18): FIND_BY, OPERATIONS, OutgoingDive, SignInInput, CASES, ContractHarness, declared(), ELSEWHERE (+10 more)

### Community 107 - "Community 107"
Cohesion: 0.13
Nodes (19): Element, overpassQuery(), parseMaxDepth(), parseOverpass(), text(), ADR-0021, PoliteHttp, inBox() (+11 more)

### Community 108 - "Community 108"
Cohesion: 0.10
Nodes (20): Check, Deleted, DiveView, Outcome, RecordingView, ADR-0063, Candidate, Deleted (+12 more)

### Community 109 - "Community 109"
Cohesion: 0.12
Nodes (22): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+14 more)

### Community 110 - "Community 110"
Cohesion: 0.15
Nodes (19): DurationFormatter, formatDepth(), formatGasRate(), formatMass(), formatPressure(), formatPressureRate(), formatTemperature(), formatThickness() (+11 more)

### Community 111 - "Community 111"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 112 - "Community 112"
Cohesion: 0.11
Nodes (16): ndl(), ProfileAssessment, Finding, LIMITS, dayNumber(), deepDays(), noFlyAfter(), SERIES_RULES (+8 more)

### Community 113 - "Community 113"
Cohesion: 0.14
Nodes (18): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+10 more)

### Community 114 - "Community 114"
Cohesion: 0.20
Nodes (22): DiveOrigin, REFERENCE_PREFIX, Series, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef() (+14 more)

### Community 115 - "Community 115"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 116 - "Community 116"
Cohesion: 0.17
Nodes (21): CatalogueCylinder, CylinderInput, cylindersOfDrafts(), CylinderView, draftOfCylinder(), emptyDraft(), fromCatalogue(), fromItemLastUse() (+13 more)

### Community 117 - "Community 117"
Cohesion: 0.16
Nodes (18): failedTitlesSince(), git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT (+10 more)

### Community 118 - "Community 118"
Cohesion: 0.12
Nodes (19): CENTRE_SOURCE_NAME, CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+11 more)

### Community 119 - "Community 119"
Cohesion: 0.10
Nodes (21): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+13 more)

### Community 120 - "Community 120"
Cohesion: 0.11
Nodes (17): DIVE_KINDS, CandidateRouteDeps, CandidateView, Decided, DiveBrief, OffsetSource, STATUS, ADR-0016 (+9 more)

### Community 121 - "Community 121"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 122 - "Community 122"
Cohesion: 0.15
Nodes (17): ImportArea, ImportedValues, siteImport, SiteImportFinding, SiteImportProgress, ExistingSite, Field, PlannedCreate (+9 more)

### Community 123 - "Community 123"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 124 - "Community 124"
Cohesion: 0.13
Nodes (16): stubDive(), ADR-0015, ADR-0023, ADR-0025, created, headers, importFixture(), ADR-0066 (+8 more)

### Community 125 - "Community 125"
Cohesion: 0.19
Nodes (17): AssessmentView, AssessmentState, clock(), counts(), differing(), Finding, formatRate(), hidden() (+9 more)

### Community 126 - "Community 126"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 127 - "Community 127"
Cohesion: 0.13
Nodes (12): openArchive(), dumpToolAvailable(), execFileAsync, migrationsFolder, silent, ADR-0069, archiveWithBrokenDatabase(), execFileAsync (+4 more)

### Community 128 - "Community 128"
Cohesion: 0.18
Nodes (15): RecordingSummary, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput, round3() (+7 more)

### Community 129 - "Community 129"
Cohesion: 0.14
Nodes (16): SUGGESTION_STATUSES, AcceptBody, STATUS, SuggestionRouteDeps, ADR-0070, SuggestionService, DiverRef, Offset (+8 more)

### Community 130 - "Community 130"
Cohesion: 0.16
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 131 - "Community 131"
Cohesion: 0.14
Nodes (13): replyCertificationError, STATUS, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, CertificationRouteDeps (+5 more)

### Community 132 - "Community 132"
Cohesion: 0.13
Nodes (15): CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationExpiry, CertificationInput, CertificationView, EXPIRING_SOON_DAYS, ImportReport, ImportResult (+7 more)

### Community 133 - "Community 133"
Cohesion: 0.20
Nodes (13): errorMessage(), looksLikeZip(), coordinate(), parseSsiSites(), sitesJson(), truthy(), ADR-0024, unavailable() (+5 more)

### Community 134 - "Community 134"
Cohesion: 0.17
Nodes (12): sampleCount(), storedList(), tryParseJson(), APP_PARAMS, createSsiTransport(), exchange(), isObject(), SSI_APP_USER_AGENT (+4 more)

### Community 135 - "Community 135"
Cohesion: 0.23
Nodes (15): draftOf(), localStart(), round1(), temperatureIn(), DepthPlot(), LINE_DASHES, plotFont(), PressureStrip() (+7 more)

### Community 136 - "Community 136"
Cohesion: 0.23
Nodes (12): hashPassword(), passwordReset, createInvitationService(), createPasswordResetService(), find(), ADR-0013, createSetupService(), isMissingRelation() (+4 more)

### Community 137 - "Community 137"
Cohesion: 0.13
Nodes (9): FileFormat, FitAdapter, SuuntoJsonAdapter, UddfAdapter, ParsedRecording, adapter, crossCheck(), decodeOfficial() (+1 more)

### Community 138 - "Community 138"
Cohesion: 0.16
Nodes (13): CIRCUIT, circuitFromFit, DECO_MODEL, decoModelFromFit, DIVE_ALERT, DIVE_MODE, diveModeFromFit, ADR-0015 (+5 more)

### Community 139 - "Community 139"
Cohesion: 0.18
Nodes (12): BOM, Json, num(), obj(), positive(), scaled(), suuntoFitKey(), time() (+4 more)

### Community 140 - "Community 140"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 141 - "Community 141"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 142 - "Community 142"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 143 - "Community 143"
Cohesion: 0.16
Nodes (14): serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, DueEntry, ScheduleView, equipmentList(), EquipmentListFilter (+6 more)

### Community 144 - "Community 144"
Cohesion: 0.14
Nodes (11): Hint, HINT_CHANNEL, HINT_KINDS, HintAudience, HintKind, kinds, ADR-0020, ADR-0066 (+3 more)

### Community 145 - "Community 145"
Cohesion: 0.13
Nodes (10): EGYPT, ExternalId, HOUSE_REEF, Method, Position, Revision, Site, SiteImport (+2 more)

### Community 146 - "Community 146"
Cohesion: 0.20
Nodes (11): clearQueue(), fetchState(), leaveConflict(), queueEntry(), ssiConnectionOf(), automatic(), leave(), openConnections() (+3 more)

### Community 147 - "Community 147"
Cohesion: 0.15
Nodes (12): EquipmentError, ADR-0034, ADR-0053, checkCost(), checkDates(), checkWindow(), checkResets(), checkSchedule() (+4 more)

### Community 148 - "Community 148"
Cohesion: 0.22
Nodes (11): fromOsm(), fromSsi(), fromWd(), HOUSE_REEF, osm(), site(), ssi(), ADR-0021 (+3 more)

### Community 149 - "Community 149"
Cohesion: 0.23
Nodes (9): DiveSummary, Profile, SKETCH_HEIGHT, SKETCH_WIDTH, sketchPaths(), sketchScale(), ADR-0041, ProfileSketch() (+1 more)

### Community 150 - "Community 150"
Cohesion: 0.21
Nodes (10): browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile(), ADR-0043 (+2 more)

### Community 151 - "Community 151"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 152 - "Community 152"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 153 - "Community 153"
Cohesion: 0.27
Nodes (12): asText(), compareReadBack(), datasetLength(), describe(), isObject(), keyDifferences(), pause(), recording() (+4 more)

### Community 154 - "Community 154"
Cohesion: 0.17
Nodes (6): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, uniqueWord()

### Community 155 - "Community 155"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 156 - "Community 156"
Cohesion: 0.32
Nodes (12): createEquipmentService(), assemble(), detailOf(), lastCylinderOf(), managedItem(), recordsOf(), schedulesFor(), schedulesOf() (+4 more)

### Community 157 - "Community 157"
Cohesion: 0.23
Nodes (9): createHub(), HEARTBEAT_MS, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect(), lost(), createLive() (+1 more)

### Community 158 - "Community 158"
Cohesion: 0.17
Nodes (10): Dive, HOUSE_REEF, LIGHTHOUSE, Method, Page, Position, Revision, Site (+2 more)

### Community 159 - "Community 159"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 160 - "Community 160"
Cohesion: 0.25
Nodes (11): ascentRate(), descentRate(), judgedParts(), lastMetres(), safetyStop(), stopIn(), stopStability(), worstOf() (+3 more)

### Community 162 - "Community 162"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 163 - "Community 163"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 164 - "Community 164"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 165 - "Community 165"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 166 - "Community 166"
Cohesion: 0.27
Nodes (8): CertificationError, createCertificationService(), cleaned(), requireManagedDiver(), rowOrThrow(), expiryOf(), isCalendarDate(), isoDay()

### Community 167 - "Community 167"
Cohesion: 0.22
Nodes (8): wallClockMs(), AQABA, ATTERSEE, EILAT, HAUSREEF, local(), MID_ATLANTIC, ADR-0030

### Community 168 - "Community 168"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 169 - "Community 169"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 170 - "Community 170"
Cohesion: 0.20
Nodes (10): verify, major, minor, sha, tag, version, name, outputs (+2 more)

### Community 171 - "Community 171"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 173 - "Community 173"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 174 - "Community 174"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 175 - "Community 175"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 176 - "Community 176"
Cohesion: 0.29
Nodes (7): ADR-0023, ADR-0053, ADR-0066, ADR-0070, MODULE_IMPORTS, moduleRules, target()

### Community 177 - "Community 177"
Cohesion: 0.29
Nodes (6): itemIds(), ADR-0026, ADR-0034, Usage, UsageDive, UsageRow

### Community 178 - "Community 178"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 179 - "Community 179"
Cohesion: 0.33
Nodes (4): pollUntilDue(), ADR-0056, now, ADR-0056

### Community 180 - "Community 180"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 181 - "Community 181"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 182 - "Community 182"
Cohesion: 0.40
Nodes (4): StoredConflictField, ADR-0021, ADR-0025, ADR-0053

### Community 183 - "Community 183"
Cohesion: 0.40
Nodes (4): CertificationSummary, NO_CERTIFICATIONS, ADR-0050, ADR-0053

### Community 184 - "Community 184"
Cohesion: 0.40
Nodes (4): offer, osm, ssi, wikidata

### Community 185 - "Community 185"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 186 - "Community 186"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 188 - "Community 188"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 189 - "Community 189"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

## Knowledge Gaps
- **1108 isolated node(s):** `ADR-0013`, `ADR-0036`, `ADR-0069`, `ADR-0070`, `ADR-0028` (+1103 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 2879 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **5 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 8` to `Community 0`, `Community 132`, `Community 5`, `Community 136`, `Community 9`, `Community 12`, `Community 13`, `Community 14`, `Community 143`, `Community 16`, `Community 144`, `Community 18`, `Community 15`, `Community 20`, `Community 21`, `Community 19`, `Community 26`, `Community 27`, `Community 28`, `Community 29`, `Community 30`, `Community 32`, `Community 33`, `Community 34`, `Community 35`, `Community 36`, `Community 37`, `Community 38`, `Community 39`, `Community 41`, `Community 42`, `Community 44`, `Community 46`, `Community 48`, `Community 177`, `Community 57`, `Community 60`, `Community 67`, `Community 68`, `Community 71`, `Community 82`, `Community 86`, `Community 94`, `Community 105`, `Community 108`, `Community 122`?**
  _High betweenness centrality (0.029) - this node is a cross-community bridge._
- **What connects `ADR-0013`, `ADR-0036`, `ADR-0069` to the rest of the system?**
  _1108 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.021976672805402088 - nodes in this community are weakly interconnected._
- **Why does `react` connect `Community 7` to `Community 64`, `Community 1`, `Community 2`, `Community 3`, `Community 4`, `Community 65`, `Community 6`, `Community 135`, `Community 10`, `Community 11`, `Community 75`, `Community 109`, `Community 80`, `Community 84`, `Community 149`, `Community 119`, `Community 90`, `Community 91`?**
  _High betweenness centrality (0.015) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.035597970158297354 - nodes in this community are weakly interconnected._
- **Why does `Button()` connect `Community 4` to `Community 64`, `Community 1`, `Community 2`, `Community 3`, `Community 65`, `Community 6`, `Community 7`, `Community 10`, `Community 11`, `Community 90`, `Community 91`?**
  _High betweenness centrality (0.013) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.030917469627147046 - nodes in this community are weakly interconnected._