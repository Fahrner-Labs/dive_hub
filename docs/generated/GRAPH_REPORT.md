# Graph Report - dive_hub  (2026-11-13)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 7054 nodes · 21996 edges · 180 communities (175 shown, 5 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 221 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `495155c2`
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
1. `Notice()` - 200 edges
2. `unwrap()` - 196 edges
3. `Button()` - 194 edges
4. `useErrorText()` - 165 edges
5. `useDisplay()` - 153 edges
6. `Muted()` - 135 edges
7. `drizzle-orm` - 131 edges
8. `announce()` - 105 edges
9. `createTestDatabase()` - 99 edges
10. `TestDatabase` - 98 edges

## Surprising Connections (you probably didn't know these)
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `Removal` --references--> `ProviderServiceError`  [EXTRACTED]
  apps/server/src/providers/push/push-service.ts → apps/server/src/providers/registry.ts
- `EquipmentListFilter` --references--> `EquipmentCategory`  [EXTRACTED]
  apps/server/src/equipment/read.ts → apps/server/src/vocabulary.ts

## Import Cycles
- None detected.

## Communities (180 total, 5 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (210): buddySuggestion, connection, diveFinding, diveLease, providerConflict, push, revision, waitingPush (+202 more)

### Community 1 - "Community 1"
Cohesion: 0.05
Nodes (174): DisplaySettings(), AdminSettings(), DeleteUserDialog(), Invitations(), Invite(), LinkToPassOn(), ProviderSiteData(), SiteDataPanel() (+166 more)

### Community 2 - "Community 2"
Cohesion: 0.03
Nodes (127): createServer(), createAuth(), auth, hashPassword(), loadAuthSecret(), acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS (+119 more)

### Community 3 - "Community 3"
Cohesion: 0.04
Nodes (127): ImportedValues, diveSite, diveSiteExternalId, siteImport, siteSource, dataIdsOf(), hubSideOf(), settleSiteVisibility() (+119 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (123): api, ApiError, ConflictFieldView, ConnectionView, DiveImportMode, DiveImportPreview, diveImportPreviewQuery(), DiveKind (+115 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (106): divesQuery(), equipmentDueQuery(), EquipmentDueView, LogbookPage, Me, PAGE_SIZE, setupQuery(), App() (+98 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (97): AiAccessLogEntry, AiAccessView, AnyLogbookCheck, bottomTimeRule(), buddiesForQuery(), buddiesQuery(), BuddyView, defined() (+89 more)

### Community 7 - "Community 7"
Cohesion: 0.04
Nodes (80): accountRelations, apikey, rateLimit, sessionRelations, ADR-0011, ADR-0035, user, userRelations (+72 more)

### Community 8 - "Community 8"
Cohesion: 0.04
Nodes (83): ComputerChoice, DiveImportMode, DiveKind, ProviderCylinder, siteRef(), close(), closeFields(), ConflictEntry (+75 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (85): LEAD_PLACEMENTS, SUIT_TYPES, SUIT_UNDERGARMENTS, WEIGHTING_FELT, ADR-0025, WaterMismatch, waterMismatch(), withoutPressures() (+77 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (67): Check, Deleted, DiveView, Outcome, RecordingView, ADR-0063, Candidate, Deleted (+59 more)

### Community 11 - "Community 11"
Cohesion: 0.05
Nodes (77): ChangePassword(), SecuritySettings(), Sessions(), useDescribeAgent(), authClient, BackupRunView, backupSettingsQuery(), BackupSettingsView (+69 more)

### Community 12 - "Community 12"
Cohesion: 0.02
Nodes (83): SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, actorType, aiAccessOutcome, backupRunKind (+75 more)

### Community 13 - "Community 13"
Cohesion: 0.03
Nodes (63): ParticipantRole, PushLeftOut, Direction, DiveExport, DiveListing, ExportingProvider, FIND_BY, FindBy (+55 more)

### Community 14 - "Community 14"
Cohesion: 0.06
Nodes (80): candidatesQuery(), CandidateView, ConflictChoices, ConflictEntryView, ConflictsOfConnection, DiveCheckView, diversQuery(), goneDivesQuery() (+72 more)

### Community 15 - "Community 15"
Cohesion: 0.05
Nodes (75): CertificationService, DIVE_IMPORT_MODES, MATCHING_WINDOWS, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053, ErrorAnswer (+67 more)

### Community 16 - "Community 16"
Cohesion: 0.04
Nodes (55): createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities(), providerError() (+47 more)

### Community 17 - "Community 17"
Cohesion: 0.06
Nodes (73): allButDive(), continuationCandidateQuery(), ContinuationCandidateView, deletedDivesQuery(), DeletedDiveView, diveProvidersQuery(), diveQuery(), LogbookCheckView (+65 more)

### Community 18 - "Community 18"
Cohesion: 0.05
Nodes (60): sessionIdOf(), AttemptLimit, clientKey(), limitAttempts(), ADR-0061, Problem, ProblemCode, ResponseState (+52 more)

### Community 19 - "Community 19"
Cohesion: 0.06
Nodes (63): connectionsQuery(), cylinderCatalogueQuery(), LeadEstimate, CylinderFields(), MATERIALS, ADR-0034, ADR-0045, useCatalogueName() (+55 more)

### Community 20 - "Community 20"
Cohesion: 0.06
Nodes (61): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ambientPressureBar(), bestMixPercent() (+53 more)

### Community 21 - "Community 21"
Cohesion: 0.04
Nodes (63): pushRunItem, PushRunLeftOut, asksFor(), DIVE_ONLY, DIVE_ONLY_PATTERN, DiveListFilter, diveListFilter(), DiveOnly (+55 more)

### Community 22 - "Community 22"
Cohesion: 0.04
Nodes (59): AssessmentService, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView(), ADR-0036 (+51 more)

### Community 23 - "Community 23"
Cohesion: 0.04
Nodes (61): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, Services, CYLINDER_MATERIALS, PARTICIPANT_ROLES (+53 more)

### Community 24 - "Community 24"
Cohesion: 0.06
Nodes (47): cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043, CodeFigure() (+39 more)

### Community 25 - "Community 25"
Cohesion: 0.06
Nodes (56): ExternalIdView, siteQuery(), useAddressSearch(), countryCodes(), countryName(), countryOptions(), formatDistance(), formatPosition() (+48 more)

### Community 26 - "Community 26"
Cohesion: 0.08
Nodes (54): OVERRIDABLE_FIELDS, renumber(), renumberStatement(), ADR-0048, keepApart(), AppendRow, channelsOf(), createContinuationService() (+46 more)

### Community 27 - "Community 27"
Cohesion: 0.06
Nodes (49): looksLikeZip(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy(), ADR-0024 (+41 more)

### Community 28 - "Community 28"
Cohesion: 0.06
Nodes (49): DIVE_FIELDS, ProviderError, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf() (+41 more)

### Community 29 - "Community 29"
Cohesion: 0.07
Nodes (57): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+49 more)

### Community 30 - "Community 30"
Cohesion: 0.11
Nodes (54): buildApp(), assessmentRoutes(), SessionUser, authHandler(), authHeaders(), fastify, FastifyRequest, forwardCookies() (+46 more)

### Community 31 - "Community 31"
Cohesion: 0.07
Nodes (48): providerLease, tidyName(), DiverDetails, Caller, foldName(), nameDiffers(), Buddy, createBuddyService() (+40 more)

### Community 32 - "Community 32"
Cohesion: 0.07
Nodes (50): SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), buildUpdateRecord(), emptyRecord(), keysOf(), writableRecord() (+42 more)

### Community 33 - "Community 33"
Cohesion: 0.06
Nodes (45): UTC_OFFSET_SOURCES, LogbookCheckService, AppendDeps, AppendOutcome, AppendService, Side, ADR-0038, ADR-0063 (+37 more)

### Community 34 - "Community 34"
Cohesion: 0.05
Nodes (46): CENTRE_SOURCE_NAME, CENTRE_SOURCES, CentreService, CentreView, CentreViewSchema, Row, Source, toCentreView() (+38 more)

### Community 35 - "Community 35"
Cohesion: 0.06
Nodes (49): AiAccessScope, diversOf(), SiteRow, Cursor, cursorAt(), DateOnly(), Detail, diversAsked() (+41 more)

### Community 36 - "Community 36"
Cohesion: 0.06
Nodes (50): diveNeighboursQuery(), DiveValues, NeighboursView, OverridableField, RecordingSummary, revisionsQuery(), RevisionView, UtcOffsetSource (+42 more)

### Community 37 - "Community 37"
Cohesion: 0.07
Nodes (48): managedDiverIds(), CandidateError, CandidateRow, createCandidateService(), lockMine(), RecordingRow, ADR-0016, ADR-0038 (+40 more)

### Community 38 - "Community 38"
Cohesion: 0.07
Nodes (46): DiveField, Leave, asBool(), DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi() (+38 more)

### Community 39 - "Community 39"
Cohesion: 0.06
Nodes (40): diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0047, clearPushRuns(), clearQueue(), deletableDive() (+32 more)

### Community 40 - "Community 40"
Cohesion: 0.05
Nodes (49): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+41 more)

### Community 41 - "Community 41"
Cohesion: 0.08
Nodes (49): ConnectionRow, pushRun, WaitingPushOptions, audienceOfConnection(), PushRow, ADR-0024, blocking(), clearIfUnchanged() (+41 more)

### Community 42 - "Community 42"
Cohesion: 0.07
Nodes (39): ImportErrorCode, ImportFound, importOriginal, ImportOutcome, errorMessage(), ArchiveEntry, ArchiveLimitError, ArchiveLimits (+31 more)

### Community 43 - "Community 43"
Cohesion: 0.08
Nodes (48): BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32Encode(), bech32HrpExpand(), bech32Polymod() (+40 more)

### Community 44 - "Community 44"
Cohesion: 0.06
Nodes (44): headers, ADR-0030, Status, ADR-0056, AiAccess, AssessedFinding, cancelWaitingImports(), conflictAtSsi() (+36 more)

### Community 45 - "Community 45"
Cohesion: 0.06
Nodes (40): createDb(), createScratchDatabase(), checkClone(), createE2eDatabase(), E2eDatabase, ensureTemplate(), identifier(), migrateTemplate() (+32 more)

### Community 46 - "Community 46"
Cohesion: 0.07
Nodes (42): lead, providerSiteData, createSsiClient(), Card, DEEP, OWD, Report, Summary (+34 more)

### Community 47 - "Community 47"
Cohesion: 0.10
Nodes (46): waitingOf(), open(), createConnectionService(), checkAutomation(), checkFollowNumbers(), expiryOf(), onTurn(), ownConnection() (+38 more)

### Community 48 - "Community 48"
Cohesion: 0.06
Nodes (33): headers, ADR-0031, headers, open(), ownId(), ownRow(), sectionButton(), ADR-0050 (+25 more)

### Community 49 - "Community 49"
Cohesion: 0.09
Nodes (43): logbookCheckAnswer, logbookCheckDiveAnswer, keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration(), CheckedDive, DeletableDive (+35 more)

### Community 50 - "Community 50"
Cohesion: 0.07
Nodes (38): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+30 more)

### Community 51 - "Community 51"
Cohesion: 0.06
Nodes (41): siteImportStatus, PROBLEM_CODES, ProblemSchema, storedProblemCode(), ADR-0014, ADR-0027, oneOf(), PushView (+33 more)

### Community 52 - "Community 52"
Cohesion: 0.06
Nodes (42): downsampleMinMax(), ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, seriesOf(), minute() (+34 more)

### Community 53 - "Community 53"
Cohesion: 0.09
Nodes (34): sacOfDive(), BottomTimeQuery, DEFAULT_GF_HIGH, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033 (+26 more)

### Community 54 - "Community 54"
Cohesion: 0.05
Nodes (40): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+32 more)

### Community 55 - "Community 55"
Cohesion: 0.07
Nodes (41): BOTTOM_TIME_RULES, cylinderOf(), cylinderText(), diverOf(), diverText(), finite(), kept, LEAD_MATERIALS (+33 more)

### Community 56 - "Community 56"
Cohesion: 0.10
Nodes (35): samplesQuery(), DepthProfile(), NO_POINTS, SurfaceBreakLane(), ADR-0036, ADR-0063, bandSeconds(), stretch() (+27 more)

### Community 57 - "Community 57"
Cohesion: 0.05
Nodes (42): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+34 more)

### Community 58 - "Community 58"
Cohesion: 0.07
Nodes (39): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+31 more)

### Community 59 - "Community 59"
Cohesion: 0.07
Nodes (38): Change(), Changes(), historyRoute, Made(), PositionSchema, AdoptBody, Country, CreateBody (+30 more)

### Community 60 - "Community 60"
Cohesion: 0.06
Nodes (23): Hint, HINT_CHANNEL, HINT_KINDS, HintAudience, HintKind, kinds, ADR-0020, ADR-0066 (+15 more)

### Community 61 - "Community 61"
Cohesion: 0.07
Nodes (37): tieToJoined(), buildJoined(), partInput(), partsOfJoined(), RecordingRow, ADR-0063, writeJoined(), CylinderError (+29 more)

### Community 62 - "Community 62"
Cohesion: 0.07
Nodes (33): sampleCount(), SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject() (+25 more)

### Community 63 - "Community 63"
Cohesion: 0.06
Nodes (30): ImportArea, SiteImportRequest, call(), createSite(), Method, openStream(), Stream, ADR-0066 (+22 more)

### Community 64 - "Community 64"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 65 - "Community 65"
Cohesion: 0.10
Nodes (37): pushRunActive(), pushRunBody(), PushRunChoices, PushRunDetail, PushRunPreview, pushRunPreviewQuery(), pushRunQuery(), pushRunsQuery() (+29 more)

### Community 66 - "Community 66"
Cohesion: 0.11
Nodes (35): massIn(), Dive, draftOfLead(), draftOfWeighting(), emptyLead(), fromLastDive(), hasThickness(), kg() (+27 more)

### Community 67 - "Community 67"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 68 - "Community 68"
Cohesion: 0.08
Nodes (33): @modelcontextprotocol/server@^2.3.1, aiAccessLog, aiAccessSetting, AiAccessRouteDeps, AccessRefusal, AiAccessError, AiAccessService, KeyRow (+25 more)

### Community 69 - "Community 69"
Cohesion: 0.08
Nodes (24): Queryable, Tx, jointDive, ADR-0016, AppendUndo, OVERLAP_TOLERANCE_SECONDS, audienceOfDive(), audienceOfDiver() (+16 more)

### Community 70 - "Community 70"
Cohesion: 0.08
Nodes (31): DiverError, ADR-0016, ADR-0053, ACCOUNT_PATTERN, accountKey(), createDiverService(), applyDetails(), applyExternalId() (+23 more)

### Community 71 - "Community 71"
Cohesion: 0.08
Nodes (32): CandidateDive, DAY_MS, decideMatch(), depthsDisagree(), DuplicateReason, MatchDecision, MAX_OFFSET_MS, overlaps() (+24 more)

### Community 72 - "Community 72"
Cohesion: 0.12
Nodes (35): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+27 more)

### Community 73 - "Community 73"
Cohesion: 0.10
Nodes (28): diveWith(), ADR-0028, ADR-0043, headers, ADR-0043, forget(), headers, ADR-0043 (+20 more)

### Community 74 - "Community 74"
Cohesion: 0.11
Nodes (36): ToolsPlan, ToolsPlanDiver, PlanDiver, PlanLevel, PlanStretch, replaceToolsAddress(), ToolsRule, depthFromDisplay() (+28 more)

### Community 75 - "Community 75"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 76 - "Community 76"
Cohesion: 0.08
Nodes (30): CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite(), lock() (+22 more)

### Community 77 - "Community 77"
Cohesion: 0.08
Nodes (34): participantsOfDives(), buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf() (+26 more)

### Community 78 - "Community 78"
Cohesion: 0.06
Nodes (35): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+27 more)

### Community 79 - "Community 79"
Cohesion: 0.09
Nodes (27): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+19 more)

### Community 80 - "Community 80"
Cohesion: 0.08
Nodes (31): devDependencies, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2, @testing-library/react@^16.3.3 (+23 more)

### Community 81 - "Community 81"
Cohesion: 0.08
Nodes (26): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+18 more)

### Community 82 - "Community 82"
Cohesion: 0.14
Nodes (33): entryMatches(), kindOfMode(), deviceDiver(), currentRemote(), importsField(), createDiveImportService(), asRecording(), assess() (+25 more)

### Community 83 - "Community 83"
Cohesion: 0.08
Nodes (27): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+19 more)

### Community 84 - "Community 84"
Cohesion: 0.16
Nodes (29): assessmentQuery(), AssessmentView, AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState, FindingItem() (+21 more)

### Community 85 - "Community 85"
Cohesion: 0.07
Nodes (22): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, createScheduledFetch(), blocked(), due() (+14 more)

### Community 86 - "Community 86"
Cohesion: 0.08
Nodes (27): CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError, CertificationExpiry, CertificationInput, CertificationView, createCertificationService(), cleaned() (+19 more)

### Community 87 - "Community 87"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 88 - "Community 88"
Cohesion: 0.12
Nodes (28): AscentBand, ascentBandsOf(), assessProfile(), ceiling(), judgedParts(), lastMetres(), ppo2(), Profile (+20 more)

### Community 89 - "Community 89"
Cohesion: 0.12
Nodes (25): BackupDecryptionKey, encryptionKeyFingerprint(), openArchiveStreamingWith(), pgCommand(), pgFailureMessage(), redactPassword(), createRestoreService(), restore() (+17 more)

### Community 90 - "Community 90"
Cohesion: 0.15
Nodes (29): importsQuery(), ImportView, noAnswerError(), uploadFile(), Logbook(), ImportContext, ImportFilesButton(), ImportHistory() (+21 more)

### Community 91 - "Community 91"
Cohesion: 0.10
Nodes (25): ENGINE_VERSION, EVIDENCE, Evidence, FindingValues, RuleId, SEVERITIES, Severity, SourceId (+17 more)

### Community 92 - "Community 92"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 93 - "Community 93"
Cohesion: 0.11
Nodes (20): SsiClient, ClearChecks, NumberChecks, TwowayChecks, apneaDive(), DATASET_KEYS, datasetSummary(), findIn() (+12 more)

### Community 94 - "Community 94"
Cohesion: 0.10
Nodes (26): globalSetup(), ADR-0023, accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks() (+18 more)

### Community 95 - "Community 95"
Cohesion: 0.10
Nodes (26): OverridableField, CylinderInput, DiveEdit, DiveRow, isProviderCopy(), merge(), primaryOf(), ADR-0015 (+18 more)

### Community 96 - "Community 96"
Cohesion: 0.11
Nodes (25): ScheduleDue, DiveGear, DueEntry, GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow (+17 more)

### Community 97 - "Community 97"
Cohesion: 0.16
Nodes (25): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+17 more)

### Community 98 - "Community 98"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 99 - "Community 99"
Cohesion: 0.09
Nodes (18): continuationAppend, equipmentUse, managesDiveDiver(), Participant, createLogbookService(), deletedDives(), findManagedDive(), listDives() (+10 more)

### Community 100 - "Community 100"
Cohesion: 0.13
Nodes (25): managementOf(), EquipmentError, ADR-0034, ADR-0053, checkCost(), checkDates(), checkWindow(), createEquipmentService() (+17 more)

### Community 101 - "Community 101"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 102 - "Community 102"
Cohesion: 0.15
Nodes (24): DiveOrigin, Series, SsiLogbook, aboveZero(), cylinderOf(), deviceOf(), deviceOfRef(), MATERIAL_OF_TANK_TYPE (+16 more)

### Community 103 - "Community 103"
Cohesion: 0.08
Nodes (19): dumpToolAvailable(), execFileAsync, migrationsFolder, silent, ADR-0069, execFileAsync, migrationsFolder, restoreToolsAvailable() (+11 more)

### Community 104 - "Community 104"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 105 - "Community 105"
Cohesion: 0.12
Nodes (23): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+15 more)

### Community 106 - "Community 106"
Cohesion: 0.12
Nodes (19): backupTask(), ADR-0069, EXPIRE_WAITING_IMPORTS_TASK, PROCESS_IMPORT_TASK, SCHEDULED_FETCH_TASK, PUSH_RUN_STEP_TASK, waitingPushesKey(), AUTOMATION_TICK_TASK (+11 more)

### Community 107 - "Community 107"
Cohesion: 0.09
Nodes (14): i18next@^26.4.2, react-dom@^19.3.0, isUnauthorized(), CustomTypeOptions, i18next, initI18n(), resources, ADR-0014 (+6 more)

### Community 108 - "Community 108"
Cohesion: 0.15
Nodes (21): Answer, CatalogueCylinder, CylinderInput, cylindersOfDrafts(), CylinderView, draftOfCylinder(), emptyDraft(), fromCatalogue() (+13 more)

### Community 109 - "Community 109"
Cohesion: 0.15
Nodes (20): durationFormat(), DurationFormatter, formatDuration(), formatGasRate(), formatMass(), formatPressure(), formatPressureRate(), formatTemperature() (+12 more)

### Community 110 - "Community 110"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 111 - "Community 111"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 112 - "Community 112"
Cohesion: 0.16
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 113 - "Community 113"
Cohesion: 0.11
Nodes (22): typescript@7.0.2, typescript@7.0.2, ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2 (+14 more)

### Community 114 - "Community 114"
Cohesion: 0.15
Nodes (21): diverBodyWeight, LeadPlacement, partsOf(), DiveRow, LeadEntry, leadOf(), setLead(), setSuit() (+13 more)

### Community 115 - "Community 115"
Cohesion: 0.11
Nodes (15): TAGS, killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023 (+7 more)

### Community 116 - "Community 116"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 117 - "Community 117"
Cohesion: 0.13
Nodes (21): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+13 more)

### Community 118 - "Community 118"
Cohesion: 0.13
Nodes (17): providerGoneDive, compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051, candidatesOf() (+9 more)

### Community 119 - "Community 119"
Cohesion: 0.11
Nodes (21): ProviderImportPlan, SuitType, SuitUndergarment, WeightingFelt, Suit, WeightingFeedback, LeadEstimateAnswer, Confidence (+13 more)

### Community 120 - "Community 120"
Cohesion: 0.19
Nodes (22): mergeProviderCylinder(), participantsOf(), columnsOf(), mergeProviderLead(), createDiveChanges(), conflictFields(), contextOf(), fillBuddies() (+14 more)

### Community 121 - "Community 121"
Cohesion: 0.10
Nodes (15): BACKUP_EXTENSION, BACKUP_SKIPPED_DIRS, BackupEncryption, BackupPart, BACKUP_RUN_TASK, BackupLog, BackupRunRow, BackupSettingsInput (+7 more)

### Community 122 - "Community 122"
Cohesion: 0.14
Nodes (19): BACKUP_FORMAT_VERSION, buildManifest(), decryptFile(), decryptFileWith(), decryptStreamWith(), encryptFile(), encryptStream(), execFileAsync (+11 more)

### Community 123 - "Community 123"
Cohesion: 0.15
Nodes (18): RecordingSummary, UtcOffsetSource, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput (+10 more)

### Community 124 - "Community 124"
Cohesion: 0.10
Nodes (17): dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3, exports, name, private (+9 more)

### Community 125 - "Community 125"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 126 - "Community 126"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 127 - "Community 127"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 128 - "Community 128"
Cohesion: 0.12
Nodes (17): equipmentItem, serviceRecord, serviceRecordSchedule, serviceSchedule, equipmentList(), EquipmentListFilter, EquipmentRecordRead, today() (+9 more)

### Community 129 - "Community 129"
Cohesion: 0.14
Nodes (17): defineTool(), Id(), Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), HINT (+9 more)

### Community 130 - "Community 130"
Cohesion: 0.11
Nodes (15): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+7 more)

### Community 131 - "Community 131"
Cohesion: 0.14
Nodes (16): SUGGESTION_STATUSES, AcceptBody, STATUS, SuggestionRouteDeps, ADR-0070, SuggestionService, DiverRef, Offset (+8 more)

### Community 132 - "Community 132"
Cohesion: 0.12
Nodes (15): externalDiver(), mergeExternalDiver(), participations(), AutomationSettings, Credentials, FETCH_INTERVALS, FetchInterval, purpose() (+7 more)

### Community 133 - "Community 133"
Cohesion: 0.12
Nodes (17): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, @internationalized/date@^3.12.4, lucide-react@^1.51.0, react@^19.3.0 (+9 more)

### Community 134 - "Community 134"
Cohesion: 0.20
Nodes (14): AppLockProbe, BackupManifest, Arguments, credentialOf(), FILE_OPTIONS, fileText(), finishedArguments(), parseArguments() (+6 more)

### Community 135 - "Community 135"
Cohesion: 0.12
Nodes (15): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+7 more)

### Community 136 - "Community 136"
Cohesion: 0.16
Nodes (14): ADR-0036, ADR-0063, ADR-0038, ADR-0048, assessedDive(), diveNumber(), falseStart(), findFalseStart() (+6 more)

### Community 137 - "Community 137"
Cohesion: 0.17
Nodes (14): ascentRate(), descentRate(), worstOf(), dayNumber(), deepDays(), noFlyAfter(), reverseProfile(), SERIES_RULES (+6 more)

### Community 138 - "Community 138"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 139 - "Community 139"
Cohesion: 0.17
Nodes (10): fileTreeChecksum(), BackupError, createBackupService(), checkSettings(), due(), enqueueScheduled(), readSettings(), saveSettings() (+2 more)

### Community 140 - "Community 140"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 141 - "Community 141"
Cohesion: 0.20
Nodes (10): DIVE_KINDS, ImportView, ImportViewSchema, toImportView(), ADR-0030, ImportRouteDeps, ADR-0044, ADR-0056 (+2 more)

### Community 142 - "Community 142"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 143 - "Community 143"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 144 - "Community 144"
Cohesion: 0.15
Nodes (8): ndl(), ProfileAssessment, Finding, LIMITS, RULES, CLEAN, samples, ADR-0036

### Community 145 - "Community 145"
Cohesion: 0.17
Nodes (10): EstimateInput, AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031 (+2 more)

### Community 146 - "Community 146"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 147 - "Community 147"
Cohesion: 0.24
Nodes (8): DiveSummary, Profile, SKETCH_HEIGHT, SKETCH_WIDTH, sketchPaths(), ADR-0041, ProfileSketch(), ADR-0041

### Community 148 - "Community 148"
Cohesion: 0.21
Nodes (9): Config, loadConfig(), ADR-0011, ADR-0021, ADR-0024, ADR-0025, config, parseEncryptionKey() (+1 more)

### Community 149 - "Community 149"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 150 - "Community 150"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 151 - "Community 151"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 152 - "Community 152"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 153 - "Community 153"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

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
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 160 - "Community 160"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 161 - "Community 161"
Cohesion: 0.29
Nodes (5): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, EstimateCylinder

### Community 162 - "Community 162"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 163 - "Community 163"
Cohesion: 0.29
Nodes (7): ADR-0023, ADR-0053, ADR-0066, ADR-0070, MODULE_IMPORTS, moduleRules, target()

### Community 164 - "Community 164"
Cohesion: 0.33
Nodes (4): Sketch, SKETCH_POINTS, ADR-0041, ADR-0041

### Community 165 - "Community 165"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 166 - "Community 166"
Cohesion: 0.33
Nodes (4): pollUntilDue(), ADR-0056, now, ADR-0056

### Community 167 - "Community 167"
Cohesion: 0.40
Nodes (5): stubDive(), ADR-0015, ADR-0023, ADR-0025, guardedRoute()

### Community 168 - "Community 168"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 169 - "Community 169"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 170 - "Community 170"
Cohesion: 0.40
Nodes (4): IMPORTABLE, splitImportable(), ADR-0037, ADR-0037

### Community 171 - "Community 171"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 172 - "Community 172"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 174 - "Community 174"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 175 - "Community 175"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 177 - "Community 177"
Cohesion: 0.67
Nodes (3): draftOf(), localStart(), round1()

## Knowledge Gaps
- **1099 isolated node(s):** `ADR-0013`, `ADR-0036`, `ADR-0011`, `ADR-0069`, `ADR-0070` (+1094 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 2859 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **5 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 7` to `Community 128`, `Community 0`, `Community 2`, `Community 3`, `Community 132`, `Community 8`, `Community 10`, `Community 12`, `Community 15`, `Community 16`, `Community 21`, `Community 23`, `Community 26`, `Community 31`, `Community 35`, `Community 37`, `Community 40`, `Community 41`, `Community 42`, `Community 45`, `Community 46`, `Community 49`, `Community 53`, `Community 60`, `Community 61`, `Community 68`, `Community 69`, `Community 70`, `Community 71`, `Community 76`, `Community 77`, `Community 86`, `Community 95`, `Community 96`, `Community 99`, `Community 103`, `Community 105`, `Community 114`, `Community 118`, `Community 121`?**
  _High betweenness centrality (0.041) - this node is a cross-community bridge._
- **What connects `ADR-0013`, `ADR-0036`, `ADR-0011` to the rest of the system?**
  _1099 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.02097902097902098 - nodes in this community are weakly interconnected._
- **Why does `createSitePickService()` connect `Community 3` to `Community 85`, `Community 47`?**
  _High betweenness centrality (0.019) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.05353282512231198 - nodes in this community are weakly interconnected._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.025387957790192428 - nodes in this community are weakly interconnected._
- **Should `Community 3` be split into smaller, more focused modules?**
  _Cohesion score 0.03534675615212528 - nodes in this community are weakly interconnected._