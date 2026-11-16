# Graph Report - dive_hub  (2026-11-16)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 7150 nodes · 22299 edges · 182 communities (178 shown, 4 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 222 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `866fcc26`
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
- Community 181

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 203 edges
2. `unwrap()` - 200 edges
3. `Button()` - 197 edges
4. `useErrorText()` - 168 edges
5. `useDisplay()` - 159 edges
6. `Muted()` - 137 edges
7. `drizzle-orm` - 133 edges
8. `announce()` - 105 edges
9. `createTestDatabase()` - 100 edges
10. `TestDatabase` - 99 edges

## Surprising Connections (you probably didn't know these)
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `Removal` --references--> `ProviderServiceError`  [EXTRACTED]
  apps/server/src/providers/push/push-service.ts → apps/server/src/providers/registry.ts
- `AuthOptions` --references--> `Db`  [EXTRACTED]
  apps/server/src/auth/auth.ts → apps/server/src/db/client.ts

## Import Cycles
- None detected.

## Communities (182 total, 4 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.06
Nodes (157): DisplaySettings(), AdminSettings(), DeleteUserDialog(), Invitations(), Invite(), LinkToPassOn(), ProviderSiteData(), SiteDataPanel() (+149 more)

### Community 1 - "Community 1"
Cohesion: 0.04
Nodes (110): user, accountListEntry, diver, diverExternalId, revision, forget(), listAccountOf(), remember() (+102 more)

### Community 2 - "Community 2"
Cohesion: 0.03
Nodes (125): ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, SiteImportFinding, SiteImportProgress (+117 more)

### Community 3 - "Community 3"
Cohesion: 0.03
Nodes (119): ConflictChoices, ConflictEntryView, ConflictsOfConnection, cylinderCatalogueQuery(), devicesQuery(), DeviceView, diversQuery(), DiverView (+111 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (113): ChangePassword(), SecuritySettings(), Sessions(), useDescribeAgent(), authClient, BackupRunView, backupSettingsQuery(), BackupSettingsView (+105 more)

### Community 5 - "Community 5"
Cohesion: 0.02
Nodes (106): apikey, buddySuggestion, parseHint(), Accepted, Candidate, Check, DiveView, Outcome (+98 more)

### Community 6 - "Community 6"
Cohesion: 0.02
Nodes (111): AiAccessLogEntry, AiAccessView, AnyLogbookCheck, bottomTimeRule(), BuddyView, ContinuationCandidateView, DETAILS, DiveGearItemView (+103 more)

### Community 7 - "Community 7"
Cohesion: 0.04
Nodes (106): api, ApiError, ConflictFieldView, ConnectionView, DiveImportMode, DiveImportPreview, DiveKind, diveListPages (+98 more)

### Community 8 - "Community 8"
Cohesion: 0.05
Nodes (89): Queryable, OVERRIDABLE_FIELDS, OverridableField, renumber(), renumberStatement(), ADR-0048, keepApart(), askedAt() (+81 more)

### Community 9 - "Community 9"
Cohesion: 0.05
Nodes (88): AcceptedBuddySuggestionView, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView, candidatesQuery(), CandidateView (+80 more)

### Community 10 - "Community 10"
Cohesion: 0.04
Nodes (78): adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, AuthOptions, CLIENT_IP_HEADER, createAuth(), PUBLIC_AUTH_PATHS, ADR-0011 (+70 more)

### Community 11 - "Community 11"
Cohesion: 0.02
Nodes (87): actorType, aiAccessOutcome, backupRun, backupRunKind, backupRunStatus, backupSettings, candidateResolution, centreSource (+79 more)

### Community 12 - "Community 12"
Cohesion: 0.04
Nodes (81): ExternalIdView, Position, siteRevisionsQuery(), SiteRevisionView, sitesQuery(), SiteView, DiveDetails(), DiverPage() (+73 more)

### Community 13 - "Community 13"
Cohesion: 0.03
Nodes (68): diveLease, providerConflict, push, pushRun, waitingPush, kept(), record(), Delivered (+60 more)

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (83): CYLINDER_MATERIALS, LEAD_PLACEMENTS, SUIT_TYPES, SUIT_UNDERGARMENTS, WEIGHTING_FELT, ADR-0025, WaterMismatch, waterMismatch() (+75 more)

### Community 15 - "Community 15"
Cohesion: 0.03
Nodes (63): ProviderImportPlan, PushLeftOut, DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities(), ADR-0062, ActionContext, Capabilities (+55 more)

### Community 16 - "Community 16"
Cohesion: 0.05
Nodes (64): auth, BackupServiceDeps, createServices(), ServiceParts, ADR-0027, ADR-0030, ADR-0061, ADR-0063 (+56 more)

### Community 17 - "Community 17"
Cohesion: 0.05
Nodes (63): equipmentDueQuery(), EquipmentDueView, CodeFigure(), DiveCodes(), showDiveCodes(), ADR-0043, EquipmentDueNotice(), ADR-0034 (+55 more)

### Community 18 - "Community 18"
Cohesion: 0.06
Nodes (79): allButDive(), continuationCandidateQuery(), deletedDivesQuery(), DeletedDiveView, diveProvidersQuery(), diveQuery(), DiveView, goneDivesQuery() (+71 more)

### Community 19 - "Community 19"
Cohesion: 0.05
Nodes (54): dive, duplicateCandidate, importJob, ImportOutcome, original, recording, ArchiveEntry, ArchiveLimitError (+46 more)

### Community 20 - "Community 20"
Cohesion: 0.04
Nodes (64): connection, createGarminClient(), bodyOf(), call(), DEFAULT_GARMIN_URL, fitOf(), GarminActivity, GarminClient (+56 more)

### Community 21 - "Community 21"
Cohesion: 0.05
Nodes (74): CertificationService, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053, ErrorAnswer, providerProblem(), IdParams (+66 more)

### Community 22 - "Community 22"
Cohesion: 0.05
Nodes (75): diveAssessment, jointDive, logbookCheckAnswer, logbookCheckDiveAnswer, keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration() (+67 more)

### Community 23 - "Community 23"
Cohesion: 0.05
Nodes (73): diveNeighboursQuery(), divesQuery(), LogbookPage, NeighboursView, PAGE_SIZE, RecordingSummary, siteQuery(), Buddies() (+65 more)

### Community 24 - "Community 24"
Cohesion: 0.08
Nodes (69): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, assessmentRoutes(), AI_ACCESS_NAME_MAX (+61 more)

### Community 25 - "Community 25"
Cohesion: 0.04
Nodes (63): pushRunItem, PushRunLeftOut, asksFor(), DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN (+55 more)

### Community 26 - "Community 26"
Cohesion: 0.04
Nodes (52): ConnectionRow, providerGoneDive, providerLease, AutomationSettings, Credentials, FETCH_INTERVALS, FetchInterval, purpose() (+44 more)

### Community 27 - "Community 27"
Cohesion: 0.05
Nodes (51): ImportArea, createSsiSiteSource(), DEFAULT_SSI_SITES_URL, createSiteSources(), ADR-0021, ADR-0027, createOverpassSource(), DEFAULT_OVERPASS_URL (+43 more)

### Community 28 - "Community 28"
Cohesion: 0.05
Nodes (59): CylinderMaterial, SuitType, SuitUndergarment, WeightingFelt, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, Suit (+51 more)

### Community 29 - "Community 29"
Cohesion: 0.06
Nodes (52): diveSiteExternalId, createSsiClient(), Card, DEEP, OWD, Report, Summary, ADR-0050 (+44 more)

### Community 30 - "Community 30"
Cohesion: 0.07
Nodes (63): ParticipantRole, sampleSeries, managedDiverIds(), CylinderError, cylindersOf(), mergeProviderCylinder(), ProviderCylinder, providerCylinderOf() (+55 more)

### Community 31 - "Community 31"
Cohesion: 0.05
Nodes (51): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+43 more)

### Community 32 - "Community 32"
Cohesion: 0.05
Nodes (48): AssessmentError, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), requireManagedDive() (+40 more)

### Community 33 - "Community 33"
Cohesion: 0.07
Nodes (61): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), clearFailed(), clearPassed(), countingBase() (+53 more)

### Community 34 - "Community 34"
Cohesion: 0.05
Nodes (49): siteRef(), ConflictEntry, ConflictField, createConflictService(), loadWaiting(), stillDiffering(), fieldsOf(), isExists() (+41 more)

### Community 35 - "Community 35"
Cohesion: 0.05
Nodes (56): AiAccessRouteDeps, AccessRefusal, AiAccessScope, AiAccessService, seesPositions(), VerifiedAccess, mcpEndpoint(), McpEndpointDeps (+48 more)

### Community 36 - "Community 36"
Cohesion: 0.05
Nodes (53): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, cancelWaitingImports() (+45 more)

### Community 37 - "Community 37"
Cohesion: 0.06
Nodes (51): createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open(), saveAndReadBack() (+43 more)

### Community 38 - "Community 38"
Cohesion: 0.07
Nodes (53): ComputerChoice, DiveImportMode, DiveKind, providerSiteData, kindOfMode(), deviceDiver(), closeFields(), entryOf() (+45 more)

### Community 39 - "Community 39"
Cohesion: 0.07
Nodes (46): SsiRecord, asNumber(), asText(), buildCreateRecord(), buildDeleteRecord(), buildUpdateRecord(), compareReadBack(), datasetLength() (+38 more)

### Community 40 - "Community 40"
Cohesion: 0.05
Nodes (51): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+43 more)

### Community 41 - "Community 41"
Cohesion: 0.09
Nodes (50): open(), close(), createConnectionService(), checkAutomation(), checkFollowNumbers(), expiryOf(), onTurn(), ownConnection() (+42 more)

### Community 42 - "Community 42"
Cohesion: 0.08
Nodes (50): WaitingPushOptions, waitingOf(), audienceOfConnection(), currentRemote(), PushRow, ADR-0024, remoteState(), statusAt() (+42 more)

### Community 43 - "Community 43"
Cohesion: 0.08
Nodes (49): BackupArchiveError, BackupDecryptionKey, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32Encode(), bech32HrpExpand() (+41 more)

### Community 44 - "Community 44"
Cohesion: 0.06
Nodes (44): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, Conditions, GasMix (+36 more)

### Community 45 - "Community 45"
Cohesion: 0.07
Nodes (39): createServer(), loadAuthSecret(), acquireAppLock(), workerDeps(), Config, loadConfig(), ADR-0011, ADR-0021 (+31 more)

### Community 46 - "Community 46"
Cohesion: 0.07
Nodes (33): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+25 more)

### Community 47 - "Community 47"
Cohesion: 0.08
Nodes (45): recordingEvent, buildJoined(), partInput(), partsOfJoined(), rebuildJoined(), RecordingRow, ADR-0063, writeJoined() (+37 more)

### Community 48 - "Community 48"
Cohesion: 0.07
Nodes (38): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+30 more)

### Community 49 - "Community 49"
Cohesion: 0.06
Nodes (39): DIVE_IMPORT_MODES, DIVE_KINDS, UTC_OFFSET_SOURCES, DiveSummaryFields, diveSummaryOf(), SummaryRow, DiveCounts, DiveListQuery (+31 more)

### Community 50 - "Community 50"
Cohesion: 0.06
Nodes (40): PARTICIPANT_ROLES, DIVE_ONLY_PATTERN, ADR-0053, ADR-0056, WaitingPushView, WaitingPushViewSchema, ProblemSchema, storedProblemCode() (+32 more)

### Community 51 - "Community 51"
Cohesion: 0.06
Nodes (44): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+36 more)

### Community 52 - "Community 52"
Cohesion: 0.08
Nodes (40): Series, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining(), ssiDiveType() (+32 more)

### Community 53 - "Community 53"
Cohesion: 0.05
Nodes (41): AssessmentService, AssessmentView, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView() (+33 more)

### Community 54 - "Community 54"
Cohesion: 0.07
Nodes (38): participant, DiverError, ADR-0016, ADR-0053, ACCOUNT_PATTERN, accountKey(), createDiverService(), applyDetails() (+30 more)

### Community 55 - "Community 55"
Cohesion: 0.06
Nodes (36): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+28 more)

### Community 56 - "Community 56"
Cohesion: 0.05
Nodes (40): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+32 more)

### Community 57 - "Community 57"
Cohesion: 0.10
Nodes (36): samplesQuery(), FindingLane(), useAssessment(), DepthProfile(), NO_POINTS, SurfaceBreakLane(), ADR-0036, ADR-0063 (+28 more)

### Community 58 - "Community 58"
Cohesion: 0.09
Nodes (38): useCylinderProblem(), DiveEditForm(), Draft, draftOf(), FIELD_OF, localStart(), nanToNull(), round1() (+30 more)

### Community 59 - "Community 59"
Cohesion: 0.05
Nodes (42): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+34 more)

### Community 60 - "Community 60"
Cohesion: 0.07
Nodes (37): Change(), Changes(), historyRoute, Made(), REVISION_CAUSES, PositionSchema, AdoptBody, Country (+29 more)

### Community 61 - "Community 61"
Cohesion: 0.06
Nodes (32): Cylinder, DiveView, RevisionView, ADR-0045, upload(), Candidate, Check, Outcome (+24 more)

### Community 62 - "Community 62"
Cohesion: 0.09
Nodes (35): tidyName(), DiverDetails, Caller, foldName(), nameDiffers(), Buddy, createBuddyService(), createFrom() (+27 more)

### Community 63 - "Community 63"
Cohesion: 0.06
Nodes (31): DiverCertifications, replyDiverError, STATUS, CertificationSummary, DiverDetailsView, DiverDetailsViewSchema, DiverSource, DiverView (+23 more)

### Community 64 - "Community 64"
Cohesion: 0.10
Nodes (31): LogbookCheckService, AppendDeps, AppendOutcome, AppendService, Side, ADR-0038, ADR-0063, ContinuationService (+23 more)

### Community 65 - "Community 65"
Cohesion: 0.09
Nodes (31): BottomTimeQuery, DEFAULT_GF_HIGH, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033, DEFAULT_PPO2 (+23 more)

### Community 66 - "Community 66"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 67 - "Community 67"
Cohesion: 0.08
Nodes (35): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+27 more)

### Community 68 - "Community 68"
Cohesion: 0.08
Nodes (31): sampleCount(), rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject(), MAX_CARD_IMAGE_BYTES (+23 more)

### Community 69 - "Community 69"
Cohesion: 0.10
Nodes (30): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0051 (+22 more)

### Community 70 - "Community 70"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 71 - "Community 71"
Cohesion: 0.08
Nodes (31): CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite(), lock() (+23 more)

### Community 72 - "Community 72"
Cohesion: 0.08
Nodes (35): downsampleMinMax(), seriesOf(), minute(), minutes(), round(), startTimes(), Channel, CHANNEL_UNITS (+27 more)

### Community 73 - "Community 73"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 74 - "Community 74"
Cohesion: 0.05
Nodes (36): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+28 more)

### Community 75 - "Community 75"
Cohesion: 0.13
Nodes (34): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+26 more)

### Community 76 - "Community 76"
Cohesion: 0.12
Nodes (33): importsQuery(), ImportView, uploadFile(), Logbook(), ImportContext, ImportFilesButton(), ImportHistory(), ImportList() (+25 more)

### Community 77 - "Community 77"
Cohesion: 0.10
Nodes (33): cylinderOf(), cylinderText(), diverOf(), diverText(), finite(), kept, LEAD_MATERIALS, LEAD_WATERS (+25 more)

### Community 78 - "Community 78"
Cohesion: 0.11
Nodes (32): massIn(), Dive, draftOfLead(), draftOfWeighting(), emptyLead(), hasThickness(), kg(), LastWeighting (+24 more)

### Community 79 - "Community 79"
Cohesion: 0.08
Nodes (26): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+18 more)

### Community 80 - "Community 80"
Cohesion: 0.08
Nodes (22): BACKUP_EXTENSION, BACKUP_SKIPPED_DIRS, BackupEncryption, BackupPart, execFileAsync, openArchive(), openArchiveWith(), packContainer() (+14 more)

### Community 81 - "Community 81"
Cohesion: 0.07
Nodes (22): CustomTypeOptions, i18next, initI18n(), resources, ADR-0014, StrayOverride, ADR-0014, english (+14 more)

### Community 82 - "Community 82"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 83 - "Community 83"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 84 - "Community 84"
Cohesion: 0.09
Nodes (26): ENGINE_VERSION, EVIDENCE, Evidence, FindingValues, RULE_IDS, RuleId, RULES, Severity (+18 more)

### Community 85 - "Community 85"
Cohesion: 0.13
Nodes (24): encryptionKeyFingerprint(), openArchiveStreamingWith(), pgCommand(), pgFailureMessage(), redactPassword(), createRestoreService(), restore(), decryptionKeyOf() (+16 more)

### Community 86 - "Community 86"
Cohesion: 0.09
Nodes (22): Tx, AppendUndo, audienceOfDive(), ADR-0020, ADR-0056, ADR-0066, withRevisionHints(), ChangeListener (+14 more)

### Community 87 - "Community 87"
Cohesion: 0.13
Nodes (24): errorMessage(), looksLikeZip(), formatOf(), createImportService(), cancelImport(), createImport(), endWaiting(), expireWaiting() (+16 more)

### Community 88 - "Community 88"
Cohesion: 0.14
Nodes (27): assessmentQuery(), certificationsQuery(), CertificationView, connectionsQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider() (+19 more)

### Community 89 - "Community 89"
Cohesion: 0.15
Nodes (29): defined(), leadEstimateQuery(), toolsBottomTimeQuery(), toolsGasQuery(), toolsPlanQuery(), keepToolsParams(), replaceToolsAddress(), RULES_WITH_BAR (+21 more)

### Community 90 - "Community 90"
Cohesion: 0.11
Nodes (28): ToolsPlan, PlanDiver, PlanLevel, planSketch(), PlanStretch, ToolsRule, DEFAULT_CYLINDER, DiverResult() (+20 more)

### Community 91 - "Community 91"
Cohesion: 0.07
Nodes (23): account, accountRelations, rateLimit, session, sessionRelations, ADR-0011, ADR-0012, ADR-0035 (+15 more)

### Community 92 - "Community 92"
Cohesion: 0.09
Nodes (24): equipmentUse, EquipmentError, ADR-0034, ADR-0053, checkCost(), checkDates(), checkWindow(), checkResets() (+16 more)

### Community 93 - "Community 93"
Cohesion: 0.11
Nodes (20): SsiClient, ClearChecks, NumberChecks, TwowayChecks, apneaDive(), DATASET_KEYS, datasetSummary(), findIn() (+12 more)

### Community 94 - "Community 94"
Cohesion: 0.08
Nodes (23): Assessment, FindingView, Row, ADR-0036, upload(), Check, Deleted, DiveView (+15 more)

### Community 95 - "Community 95"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 96 - "Community 96"
Cohesion: 0.11
Nodes (21): BACKUP_RUN_TASK, backupTask(), ADR-0069, CERTIFICATION_IMAGE_TASK, EXPIRE_WAITING_IMPORTS_TASK, PROCESS_IMPORT_TASK, SCHEDULED_FETCH_TASK, PUSH_RUN_STEP_TASK (+13 more)

### Community 97 - "Community 97"
Cohesion: 0.10
Nodes (18): ProblemCode, ResponseState, whenResponseGone(), StoredBlob, PlacedArchive, ANSWER, looksLikeRecipientPrivateKey(), PlacedArchive (+10 more)

### Community 98 - "Community 98"
Cohesion: 0.17
Nodes (24): bar(), celsius(), contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf(), firstOf() (+16 more)

### Community 99 - "Community 99"
Cohesion: 0.16
Nodes (24): DiveOrigin, REFERENCE_PREFIX, SsiLogbook, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef() (+16 more)

### Community 100 - "Community 100"
Cohesion: 0.14
Nodes (23): AscentBand, ascentBandsOf(), assessProfile(), ceiling(), ppo2(), Profile, PROFILE_RULES, ProfileInput (+15 more)

### Community 101 - "Community 101"
Cohesion: 0.12
Nodes (21): APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), AppLockProbe, probeAppLock() (+13 more)

### Community 102 - "Community 102"
Cohesion: 0.13
Nodes (22): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DueInput, DueSchedule (+14 more)

### Community 103 - "Community 103"
Cohesion: 0.12
Nodes (19): createFileFormats(), FileFormat, looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf() (+11 more)

### Community 104 - "Community 104"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 105 - "Community 105"
Cohesion: 0.11
Nodes (22): CENTRE_SOURCE_NAME, CENTRE_SOURCES, CentreService, CentreView, CentreViewSchema, Row, Source, toCentreView() (+14 more)

### Community 106 - "Community 106"
Cohesion: 0.08
Nodes (24): EquipmentService, RecordInput, ScheduleInput, EquipmentDetailViewSchema, ServiceRecordViewSchema, ServiceScheduleViewSchema, CostCurrency, DateString (+16 more)

### Community 107 - "Community 107"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 108 - "Community 108"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 109 - "Community 109"
Cohesion: 0.16
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 110 - "Community 110"
Cohesion: 0.11
Nodes (22): typescript@7.0.2, typescript@7.0.2, ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2 (+14 more)

### Community 111 - "Community 111"
Cohesion: 0.14
Nodes (23): managementOf(), createEquipmentService(), assemble(), checkSchedule(), detailOf(), insertSchedule(), lastCylinderOf(), managedItem() (+15 more)

### Community 112 - "Community 112"
Cohesion: 0.11
Nodes (21): defineTool(), diversAsked(), Id(), offsetOf(), ToolError, Input, planGasPlanTool, ADR-0033 (+13 more)

### Community 113 - "Community 113"
Cohesion: 0.12
Nodes (22): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+14 more)

### Community 114 - "Community 114"
Cohesion: 0.11
Nodes (16): ndl(), ProfileAssessment, Finding, LIMITS, dayNumber(), deepDays(), noFlyAfter(), SERIES_RULES (+8 more)

### Community 115 - "Community 115"
Cohesion: 0.15
Nodes (20): diverBodyWeight, diveSite, lead, LeadPlacement, DiveRow, LeadEntry, setLead(), setSuit() (+12 more)

### Community 116 - "Community 116"
Cohesion: 0.13
Nodes (22): serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, DueEntry, GearItem, ItemDetailView, ItemEdit (+14 more)

### Community 117 - "Community 117"
Cohesion: 0.11
Nodes (11): createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect(), lost() (+3 more)

### Community 118 - "Community 118"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 119 - "Community 119"
Cohesion: 0.15
Nodes (18): qr@0.7.2, cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043 (+10 more)

### Community 120 - "Community 120"
Cohesion: 0.14
Nodes (19): RecordingSummary, UtcOffsetSource, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput (+11 more)

### Community 121 - "Community 121"
Cohesion: 0.12
Nodes (19): siteImport, siteImportStatus, PROBLEM_CODES, SiteImportRouteDeps, StartBody, STATUS, ADR-0021, SiteImportError (+11 more)

### Community 122 - "Community 122"
Cohesion: 0.24
Nodes (17): ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres(), isHypoxic(), metresPerBar() (+9 more)

### Community 123 - "Community 123"
Cohesion: 0.10
Nodes (17): dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3, exports, name, private (+9 more)

### Community 124 - "Community 124"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 125 - "Community 125"
Cohesion: 0.12
Nodes (18): CERTIFICATION_IMAGE_ATTEMPTS, CertificationExpiry, CertificationInput, CertificationView, EXPIRING_SOON_DAYS, ImportReport, ImportResult, LIMIT (+10 more)

### Community 126 - "Community 126"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 127 - "Community 127"
Cohesion: 0.17
Nodes (18): comparableOf(), planSend(), DIVE_FIELDS, exchange(), exportsField(), contextOf(), findIn(), finishTwoway() (+10 more)

### Community 128 - "Community 128"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 129 - "Community 129"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 130 - "Community 130"
Cohesion: 0.16
Nodes (16): inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), placeLocalTime(), Position, ADR-0030, wallClockMs() (+8 more)

### Community 131 - "Community 131"
Cohesion: 0.11
Nodes (12): Hint, HINT_CHANNEL, HINT_KINDS, HintAudience, HintKind, kinds, ADR-0020, ADR-0066 (+4 more)

### Community 132 - "Community 132"
Cohesion: 0.21
Nodes (16): AssessmentView, AssessmentState, bandSeconds(), clock(), counts(), differing(), Finding, formatRate() (+8 more)

### Community 133 - "Community 133"
Cohesion: 0.16
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 134 - "Community 134"
Cohesion: 0.14
Nodes (13): replyCertificationError, STATUS, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, CertificationRouteDeps (+5 more)

### Community 135 - "Community 135"
Cohesion: 0.12
Nodes (15): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+7 more)

### Community 136 - "Community 136"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 137 - "Community 137"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 138 - "Community 138"
Cohesion: 0.17
Nodes (13): BACKUP_FORMAT_VERSION, buildManifest(), decryptFile(), decryptFileWith(), decryptStreamWith(), encryptFile(), encryptStream(), manifestFor() (+5 more)

### Community 139 - "Community 139"
Cohesion: 0.17
Nodes (10): fileTreeChecksum(), BackupError, createBackupService(), checkSettings(), due(), enqueueScheduled(), readSettings(), saveSettings() (+2 more)

### Community 140 - "Community 140"
Cohesion: 0.16
Nodes (13): aiAccessLog, aiAccessSetting, AiAccessError, KeyRow, LOG_DAYS, LOG_PAGE_MAX, LoggedCall, permissionOf() (+5 more)

### Community 141 - "Community 141"
Cohesion: 0.18
Nodes (12): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearPushRuns(), conflictAtSsi() (+4 more)

### Community 142 - "Community 142"
Cohesion: 0.18
Nodes (11): addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, relations(), TimelineBar, TimelineBarGeometry, TimelineGapGeometry, TimelineGeometry (+3 more)

### Community 143 - "Community 143"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 144 - "Community 144"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 145 - "Community 145"
Cohesion: 0.15
Nodes (12): DUE_STATES, DiveGearItemViewSchema, DiveGearViewSchema, DueState, EquipmentDueViewSchema, EquipmentItemViewSchema, EquipmentUsageView, EquipmentUseInput (+4 more)

### Community 146 - "Community 146"
Cohesion: 0.17
Nodes (6): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, uniqueWord()

### Community 147 - "Community 147"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 148 - "Community 148"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 149 - "Community 149"
Cohesion: 0.25
Nodes (11): ascentRate(), descentRate(), judgedParts(), lastMetres(), safetyStop(), stopIn(), stopStability(), worstOf() (+3 more)

### Community 150 - "Community 150"
Cohesion: 0.18
Nodes (5): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053

### Community 151 - "Community 151"
Cohesion: 0.24
Nodes (9): CertificationError, createCertificationService(), cleaned(), requireManagedDiver(), rowOrThrow(), expiryOf(), isCalendarDate(), isoDay() (+1 more)

### Community 152 - "Community 152"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 153 - "Community 153"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 154 - "Community 154"
Cohesion: 0.25
Nodes (7): Profile, SKETCH_HEIGHT, SKETCH_WIDTH, sketchPaths(), sketchScale(), ADR-0041, ADR-0041

### Community 155 - "Community 155"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 156 - "Community 156"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 157 - "Community 157"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 158 - "Community 158"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 159 - "Community 159"
Cohesion: 0.20
Nodes (10): verify, major, minor, sha, tag, version, name, outputs (+2 more)

### Community 160 - "Community 160"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 161 - "Community 161"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 162 - "Community 162"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 163 - "Community 163"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 164 - "Community 164"
Cohesion: 0.29
Nodes (7): ADR-0023, ADR-0053, ADR-0066, ADR-0070, MODULE_IMPORTS, moduleRules, target()

### Community 165 - "Community 165"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 166 - "Community 166"
Cohesion: 0.33
Nodes (4): pollUntilDue(), ADR-0056, now, ADR-0056

### Community 167 - "Community 167"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 168 - "Community 168"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 169 - "Community 169"
Cohesion: 0.40
Nodes (4): label(), CompareSubject, facts, subjects

### Community 170 - "Community 170"
Cohesion: 0.40
Nodes (4): offer, osm, ssi, wikidata

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
Cohesion: 0.83
Nodes (4): createScheduledFetch(), blocked(), due(), minimumOf()

### Community 176 - "Community 176"
Cohesion: 0.67
Nodes (4): called(), tokenOf(), withToken(), providerError()

### Community 177 - "Community 177"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 178 - "Community 178"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

## Knowledge Gaps
- **1118 isolated node(s):** `ADR-0012`, `ADR-0013`, `ADR-0030`, `ADR-0035`, `ADR-0043` (+1113 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 2902 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **4 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 1` to `Community 130`, `Community 131`, `Community 2`, `Community 5`, `Community 8`, `Community 10`, `Community 11`, `Community 140`, `Community 13`, `Community 16`, `Community 19`, `Community 20`, `Community 21`, `Community 22`, `Community 24`, `Community 25`, `Community 26`, `Community 29`, `Community 30`, `Community 32`, `Community 34`, `Community 35`, `Community 38`, `Community 40`, `Community 42`, `Community 45`, `Community 47`, `Community 50`, `Community 51`, `Community 54`, `Community 61`, `Community 62`, `Community 65`, `Community 71`, `Community 80`, `Community 91`, `Community 92`, `Community 94`, `Community 111`, `Community 115`, `Community 116`, `Community 125`?**
  _High betweenness centrality (0.030) - this node is a cross-community bridge._
- **What connects `ADR-0012`, `ADR-0013`, `ADR-0030` to the rest of the system?**
  _1118 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.055969369996519316 - nodes in this community are weakly interconnected._
- **Why does `react` connect `Community 17` to `Community 0`, `Community 3`, `Community 4`, `Community 6`, `Community 7`, `Community 9`, `Community 12`, `Community 18`, `Community 23`, `Community 57`, `Community 58`, `Community 76`, `Community 78`, `Community 83`, `Community 88`, `Community 89`, `Community 90`, `Community 113`, `Community 119`, `Community 124`?**
  _High betweenness centrality (0.030) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.03900628645004923 - nodes in this community are weakly interconnected._
- **Why does `@playwright/test` connect `Community 46` to `Community 36`, `Community 69`, `Community 104`, `Community 136`, `Community 141`, `Community 146`, `Community 147`, `Community 83`, `Community 56`, `Community 153`, `Community 152`, `Community 31`?**
  _High betweenness centrality (0.019) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.031609195402298854 - nodes in this community are weakly interconnected._