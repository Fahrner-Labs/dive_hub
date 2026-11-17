# Graph Report - dive_hub  (2026-11-17)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 7305 nodes · 22764 edges · 192 communities (186 shown, 6 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 223 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `1f020fbf`
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
1. `Notice()` - 204 edges
2. `Button()` - 198 edges
3. `useErrorText()` - 169 edges
4. `useDisplay()` - 161 edges
5. `Muted()` - 139 edges
6. `unwrap()` - 138 edges
7. `drizzle-orm` - 135 edges
8. `announce()` - 106 edges
9. `createTestDatabase()` - 101 edges
10. `TestDatabase` - 100 edges

## Surprising Connections (you probably didn't know these)
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `Removal` --references--> `ProviderServiceError`  [EXTRACTED]
  apps/server/src/providers/push/push-service.ts → apps/server/src/providers/registry.ts
- `Placement` --references--> `Actor`  [EXTRACTED]
  apps/server/src/imports/placement.ts → apps/server/src/revisions/revisions.ts

## Import Cycles
- None detected.

## Communities (192 total, 6 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.05
Nodes (179): DisplaySettings(), AdminSettings(), DeleteUserDialog(), Invitations(), Invite(), LinkToPassOn(), ProviderSiteData(), SiteDataPanel() (+171 more)

### Community 1 - "Community 1"
Cohesion: 0.04
Nodes (106): user, accountListEntry, dive, diver, diverExternalId, original, passwordReset, revision (+98 more)

### Community 2 - "Community 2"
Cohesion: 0.03
Nodes (130): allButDive(), AnyLogbookCheck, CandidateView, continuationCandidateQuery(), ContinuationCandidateView, deletedDivesOn(), DeletedDivesPageView, deletedDivesQuery() (+122 more)

### Community 3 - "Community 3"
Cohesion: 0.03
Nodes (127): assessmentQuery(), DiveValues, OverridableField, revisionsQuery(), RevisionView, UtcOffsetSource, cylinderCatalogueQuery(), AsideItem() (+119 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (111): divesQuery(), importsQuery(), ImportView, ADR-0030, uploadFile(), isUnauthorized(), noAnswerError(), SiteImportArea (+103 more)

### Community 5 - "Community 5"
Cohesion: 0.04
Nodes (112): OVERRIDABLE_FIELDS, OverridableField, recording, sampleSeries, renumber(), renumberStatement(), ADR-0048, AppendRow (+104 more)

### Community 6 - "Community 6"
Cohesion: 0.04
Nodes (96): Me, AcceptedBuddySuggestionView, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView, candidatesQuery() (+88 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (93): LEAD_PLACEMENTS, WEIGHTING_FELT, refreshAssessmentsAfterChange(), waterMismatch(), LogbookCheckService, AppendDeps, AppendOutcome, AppendService (+85 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (78): connection, createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities() (+70 more)

### Community 9 - "Community 9"
Cohesion: 0.05
Nodes (96): ComputerChoice, DiveImportMode, DiveKind, importOriginal, UtcOffsetSource, mergeProviderCylinder(), providerCylinderOf(), siteRef() (+88 more)

### Community 10 - "Community 10"
Cohesion: 0.02
Nodes (91): actorType, aiAccessOutcome, backupRunKind, backupRunStatus, candidateResolution, centreSource, CERTIFICATION_IMAGE_STATES, CERTIFICATION_VERIFICATIONS (+83 more)

### Community 11 - "Community 11"
Cohesion: 0.05
Nodes (74): equipmentDueQuery(), cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043 (+66 more)

### Community 12 - "Community 12"
Cohesion: 0.04
Nodes (68): BackupManifest, buildManifest(), decryptFile(), decryptFileWith(), encryptFile(), execFileAsync, openArchive(), openArchiveWith() (+60 more)

### Community 13 - "Community 13"
Cohesion: 0.04
Nodes (74): createServer(), AuthOptions, createAuth(), auth, hashPassword(), loadAuthSecret(), acquireAppLock(), createServices() (+66 more)

### Community 14 - "Community 14"
Cohesion: 0.04
Nodes (83): CertificationService, CertificationRouteDeps, CYLINDER_MATERIALS, DIVE_IMPORT_MODES, UTC_OFFSET_SOURCES, AssessmentRefresh, ADR-0036, ADR-0053 (+75 more)

### Community 15 - "Community 15"
Cohesion: 0.03
Nodes (70): Assessment, FindingView, Row, ADR-0036, upload(), Check, Deleted, DiveView (+62 more)

### Community 16 - "Community 16"
Cohesion: 0.04
Nodes (60): diveLease, providerConflict, push, pushRun, waitingPush, kept(), ExportingProvider, DELETE_QUIET_MS (+52 more)

### Community 17 - "Community 17"
Cohesion: 0.03
Nodes (59): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, ParticipantRole, ProviderCylinder, ActionContext (+51 more)

### Community 18 - "Community 18"
Cohesion: 0.05
Nodes (76): jointDive, logbookCheckAnswer, logbookCheckDiveAnswer, keepApart(), keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration() (+68 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (60): ServiceParts, ConnectionRow, importJob, AutomationSettings, Credentials, FETCH_INTERVALS, FetchInterval, purpose() (+52 more)

### Community 20 - "Community 20"
Cohesion: 0.04
Nodes (72): importQuery(), ExternalIdView, Position, running(), siteImportsQuery(), siteRevisionsQuery(), SiteRevisionView, sitesQuery() (+64 more)

### Community 21 - "Community 21"
Cohesion: 0.04
Nodes (79): buddiesForQuery(), buddiesQuery(), BuddyView, ConflictEntryView, ConflictsOfConnection, DiveImportMode, DiveImportPreview, diveImportPreviewQuery() (+71 more)

### Community 22 - "Community 22"
Cohesion: 0.06
Nodes (63): NoFly, Queryable, cylinder, RecordingSummary, ADR-0016, sacOfDive(), RecordingFacts, BottomTimeQuery (+55 more)

### Community 23 - "Community 23"
Cohesion: 0.05
Nodes (57): ImportArea, createSsiSiteSource(), DEFAULT_SSI_SITES_URL, createSiteSources(), ADR-0021, ADR-0027, createOverpassSource(), DEFAULT_OVERPASS_URL (+49 more)

### Community 24 - "Community 24"
Cohesion: 0.05
Nodes (60): adminRole, AI_ACCESS_NAME_MAX, AI_ACCESS_PREFIX, AI_ACCESS_RATE, Auth, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, ADR-0011 (+52 more)

### Community 25 - "Community 25"
Cohesion: 0.08
Nodes (62): buildApp(), AssessmentService, AssessmentRouteDeps, assessmentRoutes(), AssessmentViewSchema, FindingView, Rule, STATUS (+54 more)

### Community 26 - "Community 26"
Cohesion: 0.07
Nodes (58): diveSite, dataIdsOf(), settleSiteVisibility(), outgoingSite(), findOrMake(), SiteForSend, siteForSend(), ADR-0057 (+50 more)

### Community 27 - "Community 27"
Cohesion: 0.05
Nodes (63): AiAccessScope, seesPositions(), VerifiedAccess, diversOf(), SiteRow, checkedByRunTool, createMcpServer(), INSTRUCTIONS (+55 more)

### Community 28 - "Community 28"
Cohesion: 0.06
Nodes (61): diversQuery(), Buddies(), COUNT_OF, DiveList(), DiveRow(), MonthHead(), ShowOnly(), SiteFilter() (+53 more)

### Community 29 - "Community 29"
Cohesion: 0.05
Nodes (54): diveSiteExternalId, lead, providerSiteData, createSsiClient(), Dive, Status, ADR-0038, ADR-0043 (+46 more)

### Community 30 - "Community 30"
Cohesion: 0.08
Nodes (58): ChangePassword(), SecuritySettings(), Sessions(), useDescribeAgent(), AiAccessLogEntry, aiAccessLogQuery(), aiAccessQuery(), AiAccessView (+50 more)

### Community 31 - "Community 31"
Cohesion: 0.05
Nodes (59): CylinderMaterial, SuitType, SuitUndergarment, WeightingFelt, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, Suit (+51 more)

### Community 32 - "Community 32"
Cohesion: 0.06
Nodes (61): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+53 more)

### Community 33 - "Community 33"
Cohesion: 0.05
Nodes (43): BackupServiceDeps, duplicateCandidate, ImportErrorCode, ImportFound, ImportOutcome, recordingEvent, formatOf(), CHOICE_WAIT_DAYS (+35 more)

### Community 34 - "Community 34"
Cohesion: 0.05
Nodes (48): continuationAppend, device, diverBodyWeight, equipmentUse, LeadPlacement, managedDiverIds(), managesDiveDiver(), CandidateError (+40 more)

### Community 35 - "Community 35"
Cohesion: 0.05
Nodes (55): PARTICIPANT_ROLES, siteImport, siteImportStatus, ADR-0053, ADR-0056, WaitingPushViewSchema, PROBLEM_CODES, ProblemSchema (+47 more)

### Community 36 - "Community 36"
Cohesion: 0.06
Nodes (52): Delivered, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open() (+44 more)

### Community 37 - "Community 37"
Cohesion: 0.06
Nodes (44): SsiClient, SsiRecord, contextOf(), buildDeleteRecord(), buildUpdateRecord(), DiveForSsi, writableRecord(), FakeSsi (+36 more)

### Community 38 - "Community 38"
Cohesion: 0.06
Nodes (55): ConflictChoices, ConflictFieldView, providerStatusQuery(), RequirementView, useFocusBackWhenClosed(), Overrides, Paths, PluralBase (+47 more)

### Community 39 - "Community 39"
Cohesion: 0.06
Nodes (50): AscentBand, ascentBandsOf(), ascentRate(), ceiling(), descentRate(), judgedParts(), lastMetres(), ndl() (+42 more)

### Community 40 - "Community 40"
Cohesion: 0.04
Nodes (51): buddySuggestion, parseHint(), Accepted, Candidate, Check, DiveView, Outcome, RevisionView (+43 more)

### Community 41 - "Community 41"
Cohesion: 0.07
Nodes (54): waitingOf(), close(), currentRemote(), PushRow, ADR-0024, Choice, withRemembered(), isExporting() (+46 more)

### Community 42 - "Community 42"
Cohesion: 0.05
Nodes (52): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, cancelWaitingImports() (+44 more)

### Community 43 - "Community 43"
Cohesion: 0.05
Nodes (53): pushRunItem, PushRunLeftOut, DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN, NO_RECORDING (+45 more)

### Community 44 - "Community 44"
Cohesion: 0.07
Nodes (50): WaitingPushOptions, audienceOfConnection(), audienceOfDive(), audienceOfDiver(), Hint, HINT_KINDS, HintAudience, HintKind (+42 more)

### Community 45 - "Community 45"
Cohesion: 0.06
Nodes (34): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+26 more)

### Community 46 - "Community 46"
Cohesion: 0.07
Nodes (43): Leave, Series, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+35 more)

### Community 47 - "Community 47"
Cohesion: 0.09
Nodes (40): AssessmentView, samplesQuery(), AssessmentState, FindingLane(), useAssessment(), DepthProfile(), NO_POINTS, SurfaceBreakLane() (+32 more)

### Community 48 - "Community 48"
Cohesion: 0.08
Nodes (44): BACKUP_FORMAT_VERSION, BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32HrpExpand(), bech32Polymod() (+36 more)

### Community 49 - "Community 49"
Cohesion: 0.07
Nodes (39): participant, DiverError, ADR-0016, ADR-0053, ACCOUNT_PATTERN, accountKey(), createDiverService(), applyDetails() (+31 more)

### Community 50 - "Community 50"
Cohesion: 0.08
Nodes (42): LEAD_MATERIALS, LEAD_WATERS, massFromDisplay(), Dive, draftOfLead(), draftOfWeighting(), fromLastDive(), hasThickness() (+34 more)

### Community 51 - "Community 51"
Cohesion: 0.06
Nodes (40): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, EquipmentService, RecordInput, ScheduleInput (+32 more)

### Community 52 - "Community 52"
Cohesion: 0.06
Nodes (36): SUIT_TYPES, SUIT_UNDERGARMENTS, DiverCertifications, replyDiverError, STATUS, CertificationSummary, DiverDetailsView, DiverDetailsViewSchema (+28 more)

### Community 53 - "Community 53"
Cohesion: 0.10
Nodes (38): tidyName(), participantsOf(), foldName(), nameDiffers(), Buddy, createBuddyService(), createFrom(), linkEntry() (+30 more)

### Community 54 - "Community 54"
Cohesion: 0.05
Nodes (42): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+34 more)

### Community 55 - "Community 55"
Cohesion: 0.07
Nodes (39): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+31 more)

### Community 56 - "Community 56"
Cohesion: 0.09
Nodes (38): ConflictEntry, ConflictField, createConflictService(), loadWaiting(), stillDiffering(), fieldsOf(), isExists(), record() (+30 more)

### Community 57 - "Community 57"
Cohesion: 0.07
Nodes (37): Change(), Changes(), historyRoute, Made(), PositionSchema, AdoptBody, Country, CreateBody (+29 more)

### Community 58 - "Community 58"
Cohesion: 0.10
Nodes (39): bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas, toolsGasQuery() (+31 more)

### Community 59 - "Community 59"
Cohesion: 0.10
Nodes (34): open(), createConnectionService(), checkAutomation(), checkFollowNumbers(), expiryOf(), onTurn(), ownConnection(), signInAt() (+26 more)

### Community 60 - "Community 60"
Cohesion: 0.05
Nodes (37): AREA_OF, areaOf(), areas, capture(), findings, headers, ADR-0020, ADR-0021 (+29 more)

### Community 61 - "Community 61"
Cohesion: 0.08
Nodes (34): SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, Field, has(), inArea() (+26 more)

### Community 62 - "Community 62"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 63 - "Community 63"
Cohesion: 0.08
Nodes (32): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+24 more)

### Community 64 - "Community 64"
Cohesion: 0.12
Nodes (36): DEFAULT_SSI_URL, acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive() (+28 more)

### Community 65 - "Community 65"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 66 - "Community 66"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 67 - "Community 67"
Cohesion: 0.08
Nodes (30): sampleCount(), rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, idText(), isObject(), MAX_CARD_IMAGE_BYTES, PNG_MAGIC (+22 more)

### Community 68 - "Community 68"
Cohesion: 0.11
Nodes (30): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0040 (+22 more)

### Community 69 - "Community 69"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 70 - "Community 70"
Cohesion: 0.07
Nodes (27): Tx, AppendUndo, ADR-0020, ADR-0056, ADR-0066, withRevisionHints(), ChangeListener, REVISION_CAUSES (+19 more)

### Community 71 - "Community 71"
Cohesion: 0.07
Nodes (31): SUGGESTION_STATUSES, downsampleMinMax(), asksFor(), DIVE_ONLY_PATTERN, DiveCounts, DiveListQuery, DiveListView, DiveNeighboursView (+23 more)

### Community 72 - "Community 72"
Cohesion: 0.10
Nodes (33): CylinderMaterial, cylinderOf(), cylinderText(), diverOf(), diverText(), finite(), kept, LeadCylinder (+25 more)

### Community 73 - "Community 73"
Cohesion: 0.08
Nodes (30): aiAccessLog, aiAccessSetting, Access, AccessView, AiAccessRouteDeps, LogEntryView, Scope, STATUS (+22 more)

### Community 74 - "Community 74"
Cohesion: 0.08
Nodes (28): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+20 more)

### Community 75 - "Community 75"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 76 - "Community 76"
Cohesion: 0.09
Nodes (32): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+24 more)

### Community 77 - "Community 77"
Cohesion: 0.12
Nodes (27): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+19 more)

### Community 78 - "Community 78"
Cohesion: 0.09
Nodes (32): seriesOf(), minutes(), round(), Channel, CHANNEL_UNITS, CHANNELS, Computer, computerOf() (+24 more)

### Community 79 - "Community 79"
Cohesion: 0.07
Nodes (26): Cylinder, DiveView, RevisionView, ADR-0045, upload(), Candidate, Check, Outcome (+18 more)

### Community 80 - "Community 80"
Cohesion: 0.09
Nodes (28): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+20 more)

### Community 81 - "Community 81"
Cohesion: 0.09
Nodes (25): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+17 more)

### Community 82 - "Community 82"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 83 - "Community 83"
Cohesion: 0.12
Nodes (25): BACKUP_SKIPPED_DIRS, BackupDecryptionKey, encryptionKeyFingerprint(), pgCommand(), pgFailureMessage(), redactPassword(), createRestoreService(), restore() (+17 more)

### Community 84 - "Community 84"
Cohesion: 0.09
Nodes (27): CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError, CertificationExpiry, CertificationInput, CertificationView, createCertificationService(), cleaned() (+19 more)

### Community 85 - "Community 85"
Cohesion: 0.10
Nodes (28): ScheduleDue, DiveGear, DueEntry, ensureItemForDevice(), GearItem, ItemDetailView, ItemEdit, ItemInput (+20 more)

### Community 86 - "Community 86"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 87 - "Community 87"
Cohesion: 0.11
Nodes (25): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+17 more)

### Community 88 - "Community 88"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 89 - "Community 89"
Cohesion: 0.10
Nodes (27): BottomTimeInput, Conditions, GasMix, GasPlanInput, AIR, CNS_HALF_LIFE_MINUTES, DEFAULT_CONDITIONS, LoadingDive (+19 more)

### Community 90 - "Community 90"
Cohesion: 0.10
Nodes (22): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearQueue(), conflictAtSsi() (+14 more)

### Community 91 - "Community 91"
Cohesion: 0.10
Nodes (24): ENGINE_VERSION, EVIDENCE, Evidence, FindingValues, RULE_IDS, RuleId, SEVERITIES, Severity (+16 more)

### Community 92 - "Community 92"
Cohesion: 0.07
Nodes (22): account, accountRelations, apikey, rateLimit, sessionRelations, ADR-0011, ADR-0012, ADR-0035 (+14 more)

### Community 93 - "Community 93"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 94 - "Community 94"
Cohesion: 0.09
Nodes (24): ProviderImportPlan, ADR-0025, WaterMismatch, RemoteSite, SsiLogbookSite, SsiSiteValues, SiteInput, DECO_MODELS (+16 more)

### Community 95 - "Community 95"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 96 - "Community 96"
Cohesion: 0.11
Nodes (19): ProblemCode, ResponseState, whenResponseGone(), StoredBlob, PlacedArchive, ANSWER, looksLikeRecipientPrivateKey(), PlacedArchive (+11 more)

### Community 97 - "Community 97"
Cohesion: 0.12
Nodes (23): replayDive(), DESCENT_RATE_M_PER_MIN, ndlMinutes(), ADR-0032, ADR-0046, advanceTissues(), schreinerStep(), surfaceSaturatedTissues() (+15 more)

### Community 98 - "Community 98"
Cohesion: 0.16
Nodes (24): DiveOrigin, REFERENCE_PREFIX, SsiLogbook, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef() (+16 more)

### Community 99 - "Community 99"
Cohesion: 0.08
Nodes (20): headers, ADR-0035, aiAccessReady(), askMcp(), createAiAccess(), putAside(), headers, ADR-0016 (+12 more)

### Community 100 - "Community 100"
Cohesion: 0.08
Nodes (19): CustomTypeOptions, i18next, StrayOverride, none, english, flatten(), Tree, ADR-0014 (+11 more)

### Community 101 - "Community 101"
Cohesion: 0.14
Nodes (22): durationFormat(), DurationFormatter, formatClock(), formatDuration(), formatGasRate(), formatMass(), formatPressure(), formatPressureRate() (+14 more)

### Community 102 - "Community 102"
Cohesion: 0.11
Nodes (20): BACKUP_RUN_TASK, backupTask(), ADR-0069, EXPIRE_WAITING_IMPORTS_TASK, PROCESS_IMPORT_TASK, PUSH_RUN_STEP_TASK, WAITING_PUSHES_TASK, waitingPushesKey() (+12 more)

### Community 103 - "Community 103"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 104 - "Community 104"
Cohesion: 0.13
Nodes (24): PlanDiver, PlanLevel, planSketch(), PlanStretch, ToolsRule, DEFAULT_CYLINDER, DiverRows(), Editor() (+16 more)

### Community 105 - "Community 105"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 106 - "Community 106"
Cohesion: 0.08
Nodes (25): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+17 more)

### Community 107 - "Community 107"
Cohesion: 0.13
Nodes (20): APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), AppLockProbe, probeAppLock() (+12 more)

### Community 108 - "Community 108"
Cohesion: 0.12
Nodes (22): CENTRE_SOURCES, CentreService, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+14 more)

### Community 109 - "Community 109"
Cohesion: 0.12
Nodes (24): untieLostSeries(), recordingKeySource(), deletedEarlier(), fullerCopyHere(), IsManaged, knownRecording(), Outcome, Placement (+16 more)

### Community 110 - "Community 110"
Cohesion: 0.15
Nodes (21): Answer, CatalogueCylinder, CylinderInput, cylindersOfDrafts(), CylinderView, draftOfCylinder(), emptyDraft(), fromCatalogue() (+13 more)

### Community 111 - "Community 111"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 112 - "Community 112"
Cohesion: 0.12
Nodes (19): BOM, Json, num(), obj(), positive(), scaled(), time(), toRecording() (+11 more)

### Community 113 - "Community 113"
Cohesion: 0.12
Nodes (22): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+14 more)

### Community 114 - "Community 114"
Cohesion: 0.11
Nodes (14): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+6 more)

### Community 115 - "Community 115"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 116 - "Community 116"
Cohesion: 0.10
Nodes (18): DIVE_KINDS, CandidateRouteDeps, CandidateView, Decided, DiveBrief, OffsetSource, STATUS, ADR-0016 (+10 more)

### Community 117 - "Community 117"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 118 - "Community 118"
Cohesion: 0.16
Nodes (20): ImportedValues, SiteImportFinding, SiteImportProgress, ExistingSite, PlannedCreate, PlannedUpdate, columnsOf(), createSiteImportService() (+12 more)

### Community 119 - "Community 119"
Cohesion: 0.16
Nodes (21): managementOf(), EquipmentError, checkCost(), checkDates(), checkWindow(), createEquipmentService(), assemble(), checkResets() (+13 more)

### Community 120 - "Community 120"
Cohesion: 0.22
Nodes (18): ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres(), isHypoxic(), metresPerBar() (+10 more)

### Community 121 - "Community 121"
Cohesion: 0.10
Nodes (20): CertificationView, DeviceView, DiverDetailsView, diverLoadingsQuery(), externalDiversQuery(), ExternalDiverView, FoundDiverView, LoadingsView (+12 more)

### Community 122 - "Community 122"
Cohesion: 0.14
Nodes (17): createFileFormats(), createFitAdapter(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf() (+9 more)

### Community 123 - "Community 123"
Cohesion: 0.12
Nodes (19): defineTool(), Id(), Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), getDiverLoadings (+11 more)

### Community 124 - "Community 124"
Cohesion: 0.14
Nodes (16): BottomTime, bottomTime(), Limit, ADR-0032, ADR-0033, ADR-0071, MAX_DEPTH_M, NOAA_ROWS (+8 more)

### Community 125 - "Community 125"
Cohesion: 0.12
Nodes (19): DiveGearItemView, diveGearQuery(), DiveGearView, EquipmentCategory, EquipmentDetailView, EquipmentDueView, EquipmentItemView, equipmentOfDiverQuery() (+11 more)

### Community 126 - "Community 126"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 127 - "Community 127"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 128 - "Community 128"
Cohesion: 0.14
Nodes (18): equipmentItem, serviceRecord, serviceRecordSchedule, serviceSchedule, equipmentList(), EquipmentRecordRead, today(), ADR-0034 (+10 more)

### Community 129 - "Community 129"
Cohesion: 0.15
Nodes (17): DAY_MS, inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), placeLocalTime(), Position, ADR-0030 (+9 more)

### Community 130 - "Community 130"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 131 - "Community 131"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 132 - "Community 132"
Cohesion: 0.11
Nodes (13): BACKUP_EXTENSION, BackupEncryption, BackupPart, BackupLog, BackupRunRow, BackupSettingsInput, BackupSettingsValues, DEFAULT_BACKUP_SETTINGS (+5 more)

### Community 133 - "Community 133"
Cohesion: 0.22
Nodes (16): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), appendRun(), browserStats() (+8 more)

### Community 134 - "Community 134"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 135 - "Community 135"
Cohesion: 0.12
Nodes (16): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+8 more)

### Community 136 - "Community 136"
Cohesion: 0.22
Nodes (16): draftOf(), localStart(), round1(), temperatureIn(), DepthPlot(), LINE_DASHES, plotFont(), PressureStrip() (+8 more)

### Community 137 - "Community 137"
Cohesion: 0.14
Nodes (15): certification, PROBLEMS, createUserAdminService(), keepAnAdmin(), lock(), deleteUserData(), ADR-0013, ADR-0024 (+7 more)

### Community 138 - "Community 138"
Cohesion: 0.12
Nodes (16): ADR-0013, ADR-0026, ADR-0028, ADR-0030, ADR-0031, ADR-0034, ADR-0035, ADR-0036 (+8 more)

### Community 139 - "Community 139"
Cohesion: 0.23
Nodes (13): errorMessage(), looksLikeZip(), parseWithFitFileParser(), placeFile(), placeFiles(), coordinate(), parseSsiSites(), sitesJson() (+5 more)

### Community 140 - "Community 140"
Cohesion: 0.19
Nodes (14): date(), FitMessage, FitMessages, GARMIN_PRODUCTS, num(), position(), positive(), RECORD_CHANNELS (+6 more)

### Community 141 - "Community 141"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 142 - "Community 142"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 143 - "Community 143"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 144 - "Community 144"
Cohesion: 0.22
Nodes (12): defined(), Joined, joinedSummary(), joinParts(), median(), PartInput, round3(), ADR-0063 (+4 more)

### Community 145 - "Community 145"
Cohesion: 0.19
Nodes (11): decimal(), dive(), makeOceanicPlusUddf(), OCEANIC_DIVES, OceanicDive, site(), waypoint(), exported (+3 more)

### Community 146 - "Community 146"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 147 - "Community 147"
Cohesion: 0.18
Nodes (11): addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, relations(), TimelineBar, TimelineBarGeometry, TimelineGapGeometry, TimelineGeometry (+3 more)

### Community 148 - "Community 148"
Cohesion: 0.19
Nodes (10): fileTreeChecksum(), tarDirectory(), createBackupService(), checkSettings(), due(), enqueueScheduled(), readSettings(), saveSettings() (+2 more)

### Community 149 - "Community 149"
Cohesion: 0.18
Nodes (10): replyCertificationError, STATUS, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, errors (+2 more)

### Community 150 - "Community 150"
Cohesion: 0.24
Nodes (13): asNumber(), asText(), compareReadBack(), datasetLength(), describe(), isObject(), keyDifferences(), pause() (+5 more)

### Community 151 - "Community 151"
Cohesion: 0.22
Nodes (11): fromOsm(), fromSsi(), fromWd(), HOUSE_REEF, osm(), site(), ssi(), ADR-0021 (+3 more)

### Community 152 - "Community 152"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 153 - "Community 153"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 154 - "Community 154"
Cohesion: 0.15
Nodes (9): Sac, SAC_MISSING, SacMissing, ADR-0033, ADR-0045, Cylinder, dive, ADR-0033 (+1 more)

### Community 155 - "Community 155"
Cohesion: 0.17
Nodes (6): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, uniqueWord()

### Community 156 - "Community 156"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 157 - "Community 157"
Cohesion: 0.17
Nodes (10): Dive, HOUSE_REEF, LIGHTHOUSE, Method, Page, Position, Revision, Site (+2 more)

### Community 158 - "Community 158"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 159 - "Community 159"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 160 - "Community 160"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

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
Cohesion: 0.20
Nodes (5): FileFormat, FitAdapter, SuuntoJsonAdapter, UddfAdapter, ParsedRecording

### Community 165 - "Community 165"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 166 - "Community 166"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 167 - "Community 167"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 168 - "Community 168"
Cohesion: 0.28
Nodes (7): ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, s(), ADR-0035

### Community 170 - "Community 170"
Cohesion: 0.33
Nodes (8): AUTO_LINK_M, linkNearbySite(), recordingPosition(), RecordingPositions, SiteRef, ADR-0020, distanceSql(), nearSql()

### Community 171 - "Community 171"
Cohesion: 0.25
Nodes (7): IMPORT_SOURCES, SITE_SOURCES, SOURCE_INFO, SourceInfo, ADR-0021, IMPORT_SOURCES, ADR-0021

### Community 172 - "Community 172"
Cohesion: 0.22
Nodes (8): Numbered, SSI, Status, ADR-0024, ADR-0027, ADR-0056, upload(), Waiting

### Community 173 - "Community 173"
Cohesion: 0.25
Nodes (8): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, MODULE_IMPORTS, moduleRules, target()

### Community 174 - "Community 174"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 175 - "Community 175"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 176 - "Community 176"
Cohesion: 0.36
Nodes (8): bech32Encode(), convertBits(), encodeRecipient(), encodeRecipientPrivateKey(), generateRecipientKeyPair(), jwkOf(), rawPrivateOf(), rawPublicOf()

### Community 177 - "Community 177"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 178 - "Community 178"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 179 - "Community 179"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 180 - "Community 180"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 181 - "Community 181"
Cohesion: 0.40
Nodes (4): label(), CompareSubject, facts, subjects

### Community 182 - "Community 182"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 183 - "Community 183"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 185 - "Community 185"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 186 - "Community 186"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 187 - "Community 187"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1162 isolated node(s):** `ADR-0012`, `ADR-0013`, `ADR-0030`, `ADR-0035`, `ADR-0031` (+1157 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 2973 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **6 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 1` to `Community 128`, `Community 129`, `Community 132`, `Community 5`, `Community 8`, `Community 9`, `Community 10`, `Community 137`, `Community 12`, `Community 13`, `Community 15`, `Community 16`, `Community 18`, `Community 19`, `Community 22`, `Community 26`, `Community 27`, `Community 29`, `Community 33`, `Community 34`, `Community 35`, `Community 40`, `Community 41`, `Community 170`, `Community 43`, `Community 44`, `Community 172`, `Community 49`, `Community 51`, `Community 53`, `Community 56`, `Community 63`, `Community 73`, `Community 76`, `Community 79`, `Community 84`, `Community 85`, `Community 87`, `Community 88`, `Community 92`, `Community 106`, `Community 109`, `Community 118`?**
  _High betweenness centrality (0.041) - this node is a cross-community bridge._
- **What connects `ADR-0012`, `ADR-0013`, `ADR-0030` to the rest of the system?**
  _1162 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.051124002900652644 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 30` to `Community 0`, `Community 2`, `Community 3`, `Community 4`, `Community 6`, `Community 11`, `Community 20`, `Community 21`, `Community 28`, `Community 38`, `Community 47`, `Community 50`, `Community 58`, `Community 86`, `Community 104`, `Community 113`, `Community 121`, `Community 125`, `Community 127`?**
  _High betweenness centrality (0.029) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.04001572327044025 - nodes in this community are weakly interconnected._
- **Why does `@playwright/test` connect `Community 45` to `Community 160`, `Community 99`, `Community 68`, `Community 103`, `Community 42`, `Community 142`, `Community 60`, `Community 81`, `Community 86`, `Community 90`, `Community 155`, `Community 156`, `Community 159`?**
  _High betweenness centrality (0.022) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.03061575507395941 - nodes in this community are weakly interconnected._