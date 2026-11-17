# Graph Report - dive_hub  (2026-11-17)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 7286 nodes · 22713 edges · 178 communities (172 shown, 6 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 222 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `62f90bc7`
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
2. `Button()` - 197 edges
3. `useErrorText()` - 169 edges
4. `useDisplay()` - 161 edges
5. `Muted()` - 139 edges
6. `unwrap()` - 138 edges
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
- `Removal` --references--> `ProviderServiceError`  [EXTRACTED]
  apps/server/src/providers/push/push-service.ts → apps/server/src/providers/registry.ts
- `EquipmentListFilter` --references--> `EquipmentCategory`  [EXTRACTED]
  apps/server/src/equipment/read.ts → apps/server/src/vocabulary.ts

## Import Cycles
- None detected.

## Communities (178 total, 6 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.05
Nodes (170): DisplaySettings(), AdminSettings(), DeleteUserDialog(), Invitations(), Invite(), LinkToPassOn(), ProviderSiteData(), SiteDataPanel() (+162 more)

### Community 1 - "Community 1"
Cohesion: 0.03
Nodes (146): diversQuery(), diveNeighboursQuery(), divesQuery(), PAGE_SIZE, ExternalIdView, siteQuery(), sitesQuery(), App() (+138 more)

### Community 2 - "Community 2"
Cohesion: 0.03
Nodes (103): createServer(), loadAuthSecret(), acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock (+95 more)

### Community 3 - "Community 3"
Cohesion: 0.03
Nodes (130): Queryable, diverManagement, LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, participant, renumber(), renumberStatement() (+122 more)

### Community 4 - "Community 4"
Cohesion: 0.04
Nodes (141): ComputerChoice, DiveImportMode, DiveKind, diverExternalId, diveSite, importOriginal, UtcOffsetSource, managedDiverIds() (+133 more)

### Community 5 - "Community 5"
Cohesion: 0.05
Nodes (99): account, user, cylinder, diver, diveSiteExternalId, lead, revision, ADR-0013 (+91 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (123): ImportArea, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, ProviderImportPlan (+115 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (111): BACKUP_RUN_TASK, backupTask(), ADR-0069, CYLINDER_MATERIALS, DIVE_IMPORT_MODES, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036 (+103 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (92): auth, BackupServiceDeps, createServices(), ServiceParts, ADR-0027, ADR-0030, ADR-0061, ADR-0063 (+84 more)

### Community 9 - "Community 9"
Cohesion: 0.04
Nodes (111): Me, AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView (+103 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (111): devicesQuery(), DiveValues, OverridableField, revisionsQuery(), RevisionView, UtcOffsetSource, cylinderCatalogueQuery(), ConflictChoices (+103 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (100): DiveGearItemView, diveGearQuery(), DiveGearView, EquipmentCategory, EquipmentDetailView, EquipmentDueView, EquipmentItemView, equipmentOfDiverQuery() (+92 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (104): CentreService, CentreRouteDeps, UTC_OFFSET_SOURCES, DiverService, downsampleMinMax(), LogbookCheckService, AppendDeps, AppendOutcome (+96 more)

### Community 13 - "Community 13"
Cohesion: 0.03
Nodes (90): buddySuggestion, parseHint(), Accepted, Candidate, Check, DiveView, Outcome, RevisionView (+82 more)

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (108): buddiesForQuery(), BuddyView, ConflictEntryView, ConflictsOfConnection, DiveImportMode, DiveImportPreview, DiveKind, fetchBuddies() (+100 more)

### Community 15 - "Community 15"
Cohesion: 0.02
Nodes (103): CHANNELS, NOT_COVERED, ADR-0036, actorType, aiAccessOutcome, backupRunKind, backupRunStatus, candidateResolution (+95 more)

### Community 16 - "Community 16"
Cohesion: 0.03
Nodes (81): createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities(), providerError() (+73 more)

### Community 17 - "Community 17"
Cohesion: 0.04
Nodes (94): jointDive, logbookCheckAnswer, logbookCheckDiveAnswer, agreeInDepthAndDuration(), CheckedDive, DeletableDive, findChecks(), followsSoon() (+86 more)

### Community 18 - "Community 18"
Cohesion: 0.03
Nodes (73): connection, diveLease, providerConflict, push, pushRun, waitingPush, kept(), ExportingProvider (+65 more)

### Community 19 - "Community 19"
Cohesion: 0.05
Nodes (89): allButDive(), continuationCandidateQuery(), deletedDivesQuery(), diveQuery(), DiveView, mergeCandidatesQuery(), nearbyDivesQuery(), restorePreviewQuery() (+81 more)

### Community 20 - "Community 20"
Cohesion: 0.06
Nodes (85): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, assessmentRoutes(), authHandler() (+77 more)

### Community 21 - "Community 21"
Cohesion: 0.04
Nodes (67): invitation, userRole, AttemptLimit, clientKey(), limitAttempts(), TOKEN_ATTEMPTS, ADR-0061, Problem (+59 more)

### Community 22 - "Community 22"
Cohesion: 0.04
Nodes (51): ParticipantRole, Direction, DIVE_FIELDS, DiveExport, DiveListing, DiveOrigin, FIND_BY, FindBy (+43 more)

### Community 23 - "Community 23"
Cohesion: 0.04
Nodes (63): pushRunItem, PushRunLeftOut, DIVE_ONLY, DIVE_ONLY_PATTERN, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN (+55 more)

### Community 24 - "Community 24"
Cohesion: 0.06
Nodes (52): equipmentDueQuery(), EquipmentDueNotice(), ADR-0034, avatarTone(), initials(), dismissDue(), dismissedDueDigest(), dueCounts() (+44 more)

### Community 25 - "Community 25"
Cohesion: 0.06
Nodes (61): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+53 more)

### Community 26 - "Community 26"
Cohesion: 0.06
Nodes (55): SsiRecord, contextOf(), asNumber(), asText(), buildCreateRecord(), buildDeleteRecord(), buildUpdateRecord(), compareReadBack() (+47 more)

### Community 27 - "Community 27"
Cohesion: 0.08
Nodes (55): ChangePassword(), SecuritySettings(), Sessions(), useDescribeAgent(), AiAccessLogEntry, aiAccessLogQuery(), aiAccessQuery(), AiAccessView (+47 more)

### Community 28 - "Community 28"
Cohesion: 0.06
Nodes (55): importQuery(), importsQuery(), ImportView, ADR-0030, uploadFile(), noAnswerError(), Logbook(), Connections() (+47 more)

### Community 29 - "Community 29"
Cohesion: 0.05
Nodes (55): CylinderMaterial, WeightingFelt, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, WeightingFeedback, answer(), baseFrom() (+47 more)

### Community 30 - "Community 30"
Cohesion: 0.05
Nodes (50): createSsiClient(), Card, DEEP, OWD, Report, Summary, ADR-0050, WRECK (+42 more)

### Community 31 - "Community 31"
Cohesion: 0.05
Nodes (54): AssessmentService, AssessmentView, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView() (+46 more)

### Community 32 - "Community 32"
Cohesion: 0.06
Nodes (58): ConnectionRow, WaitingPushOptions, audienceOfConnection(), audienceOfDive(), audienceOfDiver(), emitHint(), Hint, HINT_KINDS (+50 more)

### Community 33 - "Community 33"
Cohesion: 0.05
Nodes (52): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, cancelWaitingImports() (+44 more)

### Community 34 - "Community 34"
Cohesion: 0.07
Nodes (46): looksLikeZip(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy(), ADR-0024 (+38 more)

### Community 35 - "Community 35"
Cohesion: 0.06
Nodes (53): diversOf(), SiteRow, Cursor, cursorAt(), DateOnly(), defineTool(), Detail, diversAsked() (+45 more)

### Community 36 - "Community 36"
Cohesion: 0.07
Nodes (48): waitingOf(), close(), Choice, isExporting(), referenceOf(), Requirement, createPushService(), deleteThere() (+40 more)

### Community 37 - "Community 37"
Cohesion: 0.07
Nodes (48): Delivered, ProviderError, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf() (+40 more)

### Community 38 - "Community 38"
Cohesion: 0.06
Nodes (45): diverBodyWeight, DiverError, ADR-0016, ADR-0053, ACCOUNT_PATTERN, accountKey(), createDiverService(), applyDetails() (+37 more)

### Community 39 - "Community 39"
Cohesion: 0.05
Nodes (49): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+41 more)

### Community 40 - "Community 40"
Cohesion: 0.09
Nodes (45): accountListEntry, tidyName(), DiverDetails, Caller, foldName(), nameDiffers(), Buddy, createBuddyService() (+37 more)

### Community 41 - "Community 41"
Cohesion: 0.06
Nodes (34): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+26 more)

### Community 42 - "Community 42"
Cohesion: 0.06
Nodes (48): leadEstimateQuery(), LeadHint(), orUndefined(), ADR-0025, ADR-0031, CylinderDraft, CylinderMaterial, cylinderOf() (+40 more)

### Community 43 - "Community 43"
Cohesion: 0.08
Nodes (44): NoFly, residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows(), DiveRow, endMsOf(), loadingDivesForReplay() (+36 more)

### Community 44 - "Community 44"
Cohesion: 0.06
Nodes (45): LEAD_PLACEMENTS, SUIT_TYPES, SUIT_UNDERGARMENTS, WEIGHTING_FELT, ProfileSummary, round1(), Series, summariseProfile() (+37 more)

### Community 45 - "Community 45"
Cohesion: 0.06
Nodes (37): providerGoneDive, providerLease, AutomationSettings, Credentials, FETCH_INTERVALS, FetchInterval, purpose(), SignInFields (+29 more)

### Community 46 - "Community 46"
Cohesion: 0.08
Nodes (37): sacOfDive(), BottomTimeQuery, DEFAULT_GF_HIGH, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033 (+29 more)

### Community 47 - "Community 47"
Cohesion: 0.08
Nodes (41): Leave, Series, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+33 more)

### Community 48 - "Community 48"
Cohesion: 0.06
Nodes (35): CertificationView, DeviceView, DiverDetailsView, diverLoadingsQuery(), DiverView, ExternalDiverView, FoundDiverView, LoadingsView (+27 more)

### Community 49 - "Community 49"
Cohesion: 0.09
Nodes (43): BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32HrpExpand(), bech32Polymod(), checkScryptBounds() (+35 more)

### Community 50 - "Community 50"
Cohesion: 0.07
Nodes (41): @modelcontextprotocol/server@^2.3.1, aiAccessLog, aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, AiAccessScope, AiAccessService (+33 more)

### Community 51 - "Community 51"
Cohesion: 0.09
Nodes (37): samplesQuery(), FindingLane(), useAssessment(), DepthProfile(), NO_POINTS, SurfaceBreakLane(), ADR-0036, ADR-0063 (+29 more)

### Community 52 - "Community 52"
Cohesion: 0.07
Nodes (33): AssessmentError, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), requireManagedDive(), isRule() (+25 more)

### Community 53 - "Community 53"
Cohesion: 0.06
Nodes (38): CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043, CreateBody (+30 more)

### Community 54 - "Community 54"
Cohesion: 0.06
Nodes (39): Change(), Changes(), historyRoute, Made(), REVISION_CAUSES, PositionSchema, AdoptBody, Country (+31 more)

### Community 55 - "Community 55"
Cohesion: 0.05
Nodes (31): SourceSite, call(), createSite(), Method, openStream(), Stream, ADR-0066, ExternalId (+23 more)

### Community 56 - "Community 56"
Cohesion: 0.05
Nodes (42): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+34 more)

### Community 57 - "Community 57"
Cohesion: 0.07
Nodes (39): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+31 more)

### Community 58 - "Community 58"
Cohesion: 0.06
Nodes (32): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError (+24 more)

### Community 59 - "Community 59"
Cohesion: 0.10
Nodes (38): useCylinderProblem(), DiveEditForm(), Draft, draftOf(), FIELD_OF, localStart(), nanToNull(), round1() (+30 more)

### Community 60 - "Community 60"
Cohesion: 0.11
Nodes (40): keepToolsParams(), replaceToolsAddress(), RULES_WITH_BAR, Tool, TOOLS_RULES, TOOLS_WATERS, depthFromDisplay(), depthIn() (+32 more)

### Community 61 - "Community 61"
Cohesion: 0.07
Nodes (36): PARTICIPANT_ROLES, ADR-0053, ADR-0056, WaitingPushViewSchema, storedProblemCode(), oneOf(), refTo(), ConflictFieldView (+28 more)

### Community 62 - "Community 62"
Cohesion: 0.06
Nodes (34): DiverCertifications, replyDiverError, STATUS, CertificationSummary, DiverDetailsView, DiverDetailsViewSchema, DiverSource, DiverView (+26 more)

### Community 63 - "Community 63"
Cohesion: 0.07
Nodes (33): sampleCount(), SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject() (+25 more)

### Community 64 - "Community 64"
Cohesion: 0.07
Nodes (28): continuationAppend, device, equipmentItem, managesDiveDiver(), CandidateError, CandidateRow, createCandidateService(), lockMine() (+20 more)

### Community 65 - "Community 65"
Cohesion: 0.07
Nodes (32): createFitAdapter(), date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), position() (+24 more)

### Community 66 - "Community 66"
Cohesion: 0.05
Nodes (37): AREA_OF, areaOf(), areas, capture(), findings, headers, ADR-0020, ADR-0021 (+29 more)

### Community 67 - "Community 67"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 68 - "Community 68"
Cohesion: 0.10
Nodes (33): Answer, massFromDisplay(), massIn(), Dive, draftOfLead(), draftOfWeighting(), emptyLead(), hasThickness() (+25 more)

### Community 69 - "Community 69"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 70 - "Community 70"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 71 - "Community 71"
Cohesion: 0.11
Nodes (34): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+26 more)

### Community 72 - "Community 72"
Cohesion: 0.11
Nodes (30): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0040 (+22 more)

### Community 73 - "Community 73"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 74 - "Community 74"
Cohesion: 0.07
Nodes (27): Tx, AppendUndo, ADR-0020, ADR-0056, ADR-0066, ChangeListener, ADR-0015, ADR-0016 (+19 more)

### Community 75 - "Community 75"
Cohesion: 0.13
Nodes (34): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+26 more)

### Community 76 - "Community 76"
Cohesion: 0.09
Nodes (33): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+25 more)

### Community 77 - "Community 77"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 78 - "Community 78"
Cohesion: 0.07
Nodes (27): Cylinder, DiveView, RevisionView, ADR-0045, upload(), Candidate, Check, Outcome (+19 more)

### Community 79 - "Community 79"
Cohesion: 0.09
Nodes (29): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+21 more)

### Community 80 - "Community 80"
Cohesion: 0.09
Nodes (26): ImportErrorCode, ImportFound, errorMessage(), parseWithFitFileParser(), formatOf(), CHOICE_WAIT_DAYS, createImportService(), cancelImport() (+18 more)

### Community 81 - "Community 81"
Cohesion: 0.09
Nodes (28): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+20 more)

### Community 82 - "Community 82"
Cohesion: 0.09
Nodes (25): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+17 more)

### Community 83 - "Community 83"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 84 - "Community 84"
Cohesion: 0.12
Nodes (25): BACKUP_SKIPPED_DIRS, BackupDecryptionKey, encryptionKeyFingerprint(), pgCommand(), pgFailureMessage(), redactPassword(), createRestoreService(), restore() (+17 more)

### Community 85 - "Community 85"
Cohesion: 0.11
Nodes (27): Conditions, GasMix, GasPlanInput, TissuesAfterDivesInput, DESCENT_RATE_M_PER_MIN, NdlInput, ndlMinutes(), ADR-0032 (+19 more)

### Community 86 - "Community 86"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 87 - "Community 87"
Cohesion: 0.10
Nodes (26): ENGINE_VERSION, EVIDENCE, Evidence, FindingValues, RULE_IDS, RuleId, RULES, SEVERITIES (+18 more)

### Community 88 - "Community 88"
Cohesion: 0.14
Nodes (26): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+18 more)

### Community 89 - "Community 89"
Cohesion: 0.09
Nodes (23): computerEventOf(), ADR-0036, computerEventFromFit(), COMPUTER_EVENT, computerEventFromSuunto(), DECO_MODEL, decoModelFromSuunto, DIVE_MODE (+15 more)

### Community 90 - "Community 90"
Cohesion: 0.15
Nodes (26): open(), createConnectionService(), checkAutomation(), checkFollowNumbers(), expiryOf(), onTurn(), ownConnection(), signInAt() (+18 more)

### Community 91 - "Community 91"
Cohesion: 0.11
Nodes (20): SsiClient, ClearChecks, NumberChecks, TwowayChecks, apneaDive(), DATASET_KEYS, datasetSummary(), findIn() (+12 more)

### Community 92 - "Community 92"
Cohesion: 0.10
Nodes (22): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearQueue(), conflictAtSsi() (+14 more)

### Community 93 - "Community 93"
Cohesion: 0.11
Nodes (24): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+16 more)

### Community 94 - "Community 94"
Cohesion: 0.08
Nodes (23): Assessment, FindingView, Row, ADR-0036, upload(), Check, Deleted, DiveView (+15 more)

### Community 95 - "Community 95"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 96 - "Community 96"
Cohesion: 0.11
Nodes (21): BOM, Json, num(), obj(), positive(), scaled(), suuntoFitKey(), SuuntoJsonAdapter (+13 more)

### Community 97 - "Community 97"
Cohesion: 0.08
Nodes (20): headers, ADR-0035, aiAccessReady(), askMcp(), createAiAccess(), putAside(), headers, ADR-0016 (+12 more)

### Community 98 - "Community 98"
Cohesion: 0.12
Nodes (19): createFileFormats(), FileFormat, looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf() (+11 more)

### Community 99 - "Community 99"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 100 - "Community 100"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 101 - "Community 101"
Cohesion: 0.12
Nodes (22): equipmentUse, DiveGear, DueEntry, GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow (+14 more)

### Community 102 - "Community 102"
Cohesion: 0.18
Nodes (22): SsiLogbook, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef(), MATERIAL_OF_TANK_TYPE, nonZero() (+14 more)

### Community 103 - "Community 103"
Cohesion: 0.15
Nodes (20): durationFormat(), DurationFormatter, formatClock(), formatDuration(), formatGasRate(), formatMass(), formatPressure(), formatPressureRate() (+12 more)

### Community 104 - "Community 104"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 105 - "Community 105"
Cohesion: 0.09
Nodes (20): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+12 more)

### Community 106 - "Community 106"
Cohesion: 0.10
Nodes (21): ADR-0025, WaterMismatch, waterMismatch(), DECO_MODELS, DIVE_MODES, DIVER_SOURCES, EQUIPMENT_USES, GAS_CIRCUITS (+13 more)

### Community 107 - "Community 107"
Cohesion: 0.15
Nodes (21): assessmentQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), FindingItem(), FixedNote(), rowId() (+13 more)

### Community 108 - "Community 108"
Cohesion: 0.12
Nodes (22): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+14 more)

### Community 109 - "Community 109"
Cohesion: 0.11
Nodes (14): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+6 more)

### Community 110 - "Community 110"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 111 - "Community 111"
Cohesion: 0.13
Nodes (18): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, GasRule (+10 more)

### Community 112 - "Community 112"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 113 - "Community 113"
Cohesion: 0.15
Nodes (18): qr@0.7.2, cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043 (+10 more)

### Community 114 - "Community 114"
Cohesion: 0.24
Nodes (17): ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres(), isHypoxic(), metresPerBar() (+9 more)

### Community 115 - "Community 115"
Cohesion: 0.18
Nodes (19): comparableOf(), hasTolerance(), isEmpty(), near(), planSend(), sameAtProvider(), sameCylinder(), SendPlan (+11 more)

### Community 116 - "Community 116"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 117 - "Community 117"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 118 - "Community 118"
Cohesion: 0.13
Nodes (17): siteImportStatus, PROBLEM_CODES, SiteImportRouteDeps, StartBody, STATUS, ADR-0021, SiteImportError, SiteImportService (+9 more)

### Community 119 - "Community 119"
Cohesion: 0.15
Nodes (17): DAY_MS, inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), placeLocalTime(), Position, ADR-0030 (+9 more)

### Community 120 - "Community 120"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 121 - "Community 121"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 122 - "Community 122"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 123 - "Community 123"
Cohesion: 0.11
Nodes (13): BACKUP_EXTENSION, BackupEncryption, BackupPart, BackupLog, BackupRunRow, BackupSettingsInput, BackupSettingsValues, DEFAULT_BACKUP_SETTINGS (+5 more)

### Community 124 - "Community 124"
Cohesion: 0.21
Nodes (16): AssessmentView, AssessmentState, clock(), counts(), differing(), Finding, formatRate(), hidden() (+8 more)

### Community 125 - "Community 125"
Cohesion: 0.22
Nodes (16): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), appendRun(), browserStats() (+8 more)

### Community 126 - "Community 126"
Cohesion: 0.11
Nodes (16): accountRelations, apikey, rateLimit, sessionRelations, ADR-0011, ADR-0012, ADR-0035, twoFactor (+8 more)

### Community 127 - "Community 127"
Cohesion: 0.12
Nodes (16): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+8 more)

### Community 128 - "Community 128"
Cohesion: 0.20
Nodes (14): AppLockProbe, BackupManifest, Arguments, credentialOf(), FILE_OPTIONS, fileText(), finishedArguments(), parseArguments() (+6 more)

### Community 129 - "Community 129"
Cohesion: 0.12
Nodes (16): ADR-0013, ADR-0026, ADR-0028, ADR-0030, ADR-0031, ADR-0034, ADR-0035, ADR-0036 (+8 more)

### Community 130 - "Community 130"
Cohesion: 0.16
Nodes (12): replyCertificationError, STATUS, CertificationService, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams (+4 more)

### Community 131 - "Community 131"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 132 - "Community 132"
Cohesion: 0.16
Nodes (15): bottomTimeRule(), defined(), LeadEstimate, ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas, toolsGasQuery(), ToolsInput (+7 more)

### Community 133 - "Community 133"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 134 - "Community 134"
Cohesion: 0.18
Nodes (11): DIVE_KINDS, ImportView, ImportViewSchema, toImportView(), ADR-0030, ImportRouteDeps, ADR-0044, ADR-0056 (+3 more)

### Community 135 - "Community 135"
Cohesion: 0.18
Nodes (11): addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, relations(), TimelineBar, TimelineBarGeometry, TimelineGapGeometry, TimelineGeometry (+3 more)

### Community 136 - "Community 136"
Cohesion: 0.15
Nodes (12): EquipmentError, ADR-0034, ADR-0053, checkCost(), checkDates(), checkWindow(), checkResets(), checkSchedule() (+4 more)

### Community 137 - "Community 137"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 138 - "Community 138"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 139 - "Community 139"
Cohesion: 0.21
Nodes (9): tarDirectory(), createBackupService(), checkSettings(), due(), enqueueScheduled(), readSettings(), saveSettings(), sweepRetention() (+1 more)

### Community 140 - "Community 140"
Cohesion: 0.19
Nodes (12): serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, equipmentList(), EquipmentListFilter, EquipmentRecordRead, EquipmentScheduleRead (+4 more)

### Community 141 - "Community 141"
Cohesion: 0.15
Nodes (9): Sac, SAC_MISSING, SacMissing, ADR-0033, ADR-0045, Cylinder, dive, ADR-0033 (+1 more)

### Community 142 - "Community 142"
Cohesion: 0.28
Nodes (13): createEquipmentService(), assemble(), detailOf(), lastCylinderOf(), managedItem(), recordsOf(), schedulesFor(), schedulesOf() (+5 more)

### Community 143 - "Community 143"
Cohesion: 0.17
Nodes (6): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, uniqueWord()

### Community 144 - "Community 144"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 145 - "Community 145"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 146 - "Community 146"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 147 - "Community 147"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 148 - "Community 148"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 149 - "Community 149"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 150 - "Community 150"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 151 - "Community 151"
Cohesion: 0.27
Nodes (9): BACKUP_FORMAT_VERSION, buildManifest(), decryptFile(), decryptFileWith(), fileTreeChecksum(), manifestFor(), partOf(), sha256() (+1 more)

### Community 152 - "Community 152"
Cohesion: 0.22
Nodes (9): getDiverLoadings, HINT, Input, LoadingsInput, planGasAndBottomTime, ADR-0032, ADR-0035, Used() (+1 more)

### Community 153 - "Community 153"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 154 - "Community 154"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 155 - "Community 155"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 157 - "Community 157"
Cohesion: 0.25
Nodes (8): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, MODULE_IMPORTS, moduleRules, target()

### Community 158 - "Community 158"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 159 - "Community 159"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 160 - "Community 160"
Cohesion: 0.36
Nodes (8): bech32Encode(), convertBits(), encodeRecipient(), encodeRecipientPrivateKey(), generateRecipientKeyPair(), jwkOf(), rawPrivateOf(), rawPublicOf()

### Community 161 - "Community 161"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 162 - "Community 162"
Cohesion: 0.29
Nodes (6): itemIds(), ADR-0026, ADR-0034, Usage, UsageDive, UsageRow

### Community 163 - "Community 163"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 164 - "Community 164"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 165 - "Community 165"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 166 - "Community 166"
Cohesion: 0.40
Nodes (4): label(), CompareSubject, facts, subjects

### Community 167 - "Community 167"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 168 - "Community 168"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 170 - "Community 170"
Cohesion: 0.83
Nodes (4): createScheduledFetch(), blocked(), due(), minimumOf()

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
- **1161 isolated node(s):** `ADR-0012`, `ADR-0013`, `ADR-0030`, `ADR-0035`, `ADR-0031` (+1156 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 2967 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **6 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 5` to `Community 2`, `Community 3`, `Community 4`, `Community 6`, `Community 7`, `Community 8`, `Community 140`, `Community 13`, `Community 15`, `Community 16`, `Community 17`, `Community 18`, `Community 20`, `Community 21`, `Community 23`, `Community 30`, `Community 32`, `Community 162`, `Community 35`, `Community 36`, `Community 38`, `Community 39`, `Community 40`, `Community 43`, `Community 45`, `Community 46`, `Community 50`, `Community 55`, `Community 58`, `Community 61`, `Community 64`, `Community 74`, `Community 76`, `Community 78`, `Community 79`, `Community 80`, `Community 94`, `Community 101`, `Community 119`, `Community 123`, `Community 126`?**
  _High betweenness centrality (0.033) - this node is a cross-community bridge._
- **What connects `ADR-0012`, `ADR-0013`, `ADR-0030` to the rest of the system?**
  _1161 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.05141010575793185 - nodes in this community are weakly interconnected._
- **Why does `request()` connect `Community 27` to `Community 0`, `Community 1`, `Community 132`, `Community 9`, `Community 10`, `Community 11`, `Community 107`, `Community 42`, `Community 14`, `Community 48`, `Community 19`, `Community 51`, `Community 24`, `Community 28`?**
  _High betweenness centrality (0.027) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.029563932002956393 - nodes in this community are weakly interconnected._
- **Why does `react` connect `Community 11` to `Community 0`, `Community 1`, `Community 9`, `Community 10`, `Community 14`, `Community 19`, `Community 24`, `Community 27`, `Community 28`, `Community 48`, `Community 51`, `Community 59`, `Community 60`, `Community 68`, `Community 86`, `Community 107`, `Community 108`, `Community 113`, `Community 117`?**
  _High betweenness centrality (0.023) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.028809769521843824 - nodes in this community are weakly interconnected._