# Graph Report - dive_hub  (2026-11-17)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 7306 nodes · 22769 edges · 179 communities (174 shown, 5 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 223 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `61bddbd3`
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
- Community 178

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
- `askedAt()` --calls--> `DiveError`  [EXTRACTED]
  apps/server/src/dives/continuation/append-service.ts → apps/server/src/dives/dive-service.ts

## Import Cycles
- None detected.

## Communities (179 total, 5 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (210): Tx, continuationAppend, equipmentItem, LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, participant, recording (+202 more)

### Community 1 - "Community 1"
Cohesion: 0.02
Nodes (202): ComputerChoice, DiveImportMode, DiveKind, ImportErrorCode, ImportFound, importJob, importOriginal, ImportOutcome (+194 more)

### Community 2 - "Community 2"
Cohesion: 0.02
Nodes (154): createCertificationService(), ConnectionRow, providerGoneDive, pushRunItem, PushRunLeftOut, WaitingPushOptions, managedDiverIds(), audienceOfConnection() (+146 more)

### Community 3 - "Community 3"
Cohesion: 0.05
Nodes (158): DisplaySettings(), Sessions(), AdminSettings(), DeleteUserDialog(), Invitations(), Invite(), LinkToPassOn(), ProviderSiteData() (+150 more)

### Community 4 - "Community 4"
Cohesion: 0.04
Nodes (106): user, diver, revision, ADR-0013, ADR-0011, dumpToolAvailable(), execFileAsync, ADR-0069 (+98 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (138): ImportArea, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, diverExternalId (+130 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (111): LEAD_PLACEMENTS, UTC_OFFSET_SOURCES, WEIGHTING_FELT, downsampleMinMax(), sacOfDive(), AppendDeps, AppendOutcome, AppendService (+103 more)

### Community 7 - "Community 7"
Cohesion: 0.04
Nodes (105): AcceptedBuddySuggestionView, BuddySuggestionOverlapView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView, candidatesQuery(), deletedDivesTotal(), DiveSummary (+97 more)

### Community 8 - "Community 8"
Cohesion: 0.04
Nodes (106): allButDive(), AnyLogbookCheck, CandidateView, continuationCandidateQuery(), ContinuationCandidateView, deletedDivesOn(), DeletedDivesPageView, deletedDivesQuery() (+98 more)

### Community 9 - "Community 9"
Cohesion: 0.04
Nodes (71): BackupManifest, buildManifest(), decryptFile(), decryptFileWith(), encryptFile(), execFileAsync, openArchive(), openArchiveWith() (+63 more)

### Community 10 - "Community 10"
Cohesion: 0.04
Nodes (98): diversQuery(), divesQuery(), connectionsQuery(), ExternalIdView, Position, siteQuery(), COUNT_OF, DiveList() (+90 more)

### Community 11 - "Community 11"
Cohesion: 0.04
Nodes (95): SecuritySettings(), importsQuery(), ImportView, running(), siteImportsQuery(), App(), BackupsPage, CentrePage (+87 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (76): connection, createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities() (+68 more)

### Community 13 - "Community 13"
Cohesion: 0.03
Nodes (76): diveLease, providerConflict, providerLease, push, pushRun, waitingPush, kept(), LEASE_MS (+68 more)

### Community 14 - "Community 14"
Cohesion: 0.04
Nodes (89): devicesQuery(), assessmentQuery(), diveNeighboursQuery(), DiveValues, OverridableField, revisionsQuery(), RevisionView, UtcOffsetSource (+81 more)

### Community 15 - "Community 15"
Cohesion: 0.05
Nodes (91): DiveView, cylinderCatalogueQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery() (+83 more)

### Community 16 - "Community 16"
Cohesion: 0.02
Nodes (91): actorType, aiAccessOutcome, backupRunKind, backupRunStatus, candidateResolution, centreSource, CERTIFICATION_IMAGE_STATES, CERTIFICATION_VERIFICATIONS (+83 more)

### Community 17 - "Community 17"
Cohesion: 0.04
Nodes (79): CertificationService, CYLINDER_MATERIALS, DIVE_IMPORT_MODES, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053, ErrorAnswer (+71 more)

### Community 18 - "Community 18"
Cohesion: 0.05
Nodes (80): ConnectionView, providerStatusQuery(), ProviderView, choice(), ConnectBody, ConnectForm(), Connections(), CreateKeyLink() (+72 more)

### Community 19 - "Community 19"
Cohesion: 0.05
Nodes (67): CHANNELS, isRule(), NOT_COVERED, read(), ADR-0036, noFlyAfter(), seriesDiveOf(), Queryable (+59 more)

### Community 20 - "Community 20"
Cohesion: 0.03
Nodes (65): diveFinding, Assessment, FindingView, Row, ADR-0036, upload(), Check, Deleted (+57 more)

### Community 21 - "Community 21"
Cohesion: 0.04
Nodes (69): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+61 more)

### Community 22 - "Community 22"
Cohesion: 0.05
Nodes (71): diveAssessment, diveCentre, diveCentreSite, jointDive, logbookCheckAnswer, logbookCheckDiveAnswer, agreeInDepthAndDuration(), CheckedDive (+63 more)

### Community 23 - "Community 23"
Cohesion: 0.04
Nodes (58): CertificationView, DeviceView, DiverDetailsView, diverLoadingsQuery(), ExternalDiverView, FoundDiverView, LoadingsView, ReadCodeView (+50 more)

### Community 24 - "Community 24"
Cohesion: 0.06
Nodes (63): Me, BuddySuggestionPreviewView, keys, api, centreQuery(), centresQuery(), CentreView, SiteImportArea (+55 more)

### Community 25 - "Community 25"
Cohesion: 0.05
Nodes (57): equipmentDueQuery(), Buddies(), EquipmentDueNotice(), ADR-0034, avatarTone(), initials(), dismissDue(), dismissedDueDigest() (+49 more)

### Community 26 - "Community 26"
Cohesion: 0.08
Nodes (65): buildApp(), AssessmentService, AssessmentView, AssessmentRouteDeps, assessmentRoutes(), AssessmentViewSchema, FindingView, Rule (+57 more)

### Community 27 - "Community 27"
Cohesion: 0.04
Nodes (51): ParticipantRole, ProviderImportPlan, ActionContext, Capabilities, Direction, DiveExport, DiveListing, FIND_BY (+43 more)

### Community 28 - "Community 28"
Cohesion: 0.03
Nodes (60): buddySuggestion, parseHint(), Accepted, Candidate, Check, DiveView, Outcome, RevisionView (+52 more)

### Community 29 - "Community 29"
Cohesion: 0.05
Nodes (59): CylinderMaterial, SuitType, SuitUndergarment, WeightingFelt, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, Suit (+51 more)

### Community 30 - "Community 30"
Cohesion: 0.06
Nodes (47): auth, BackupServiceDeps, createServices(), ServiceParts, ADR-0027, ADR-0030, ADR-0061, ADR-0063 (+39 more)

### Community 31 - "Community 31"
Cohesion: 0.06
Nodes (61): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+53 more)

### Community 32 - "Community 32"
Cohesion: 0.04
Nodes (58): SUGGESTION_STATUSES, DiveProfileView, DiveProfileViewSchema, ADR-0041, Change(), Changes(), historyRoute, Made() (+50 more)

### Community 33 - "Community 33"
Cohesion: 0.05
Nodes (52): diveSiteExternalId, createSsiClient(), Card, DEEP, OWD, Report, Summary, ADR-0050 (+44 more)

### Community 34 - "Community 34"
Cohesion: 0.08
Nodes (53): ChangePassword(), useDescribeAgent(), AiAccessLogEntry, aiAccessLogQuery(), aiAccessQuery(), AiAccessView, authClient, invitationLinkQuery() (+45 more)

### Community 35 - "Community 35"
Cohesion: 0.05
Nodes (59): importQuery(), buddiesForQuery(), buddiesQuery(), BuddyView, DiveImportMode, DiveImportPreview, diveImportPreviewQuery(), DiveKind (+51 more)

### Community 36 - "Community 36"
Cohesion: 0.06
Nodes (44): SsiClient, SsiRecord, contextOf(), buildDeleteRecord(), DiveForSsi, writableRecord(), FakeSsi, BuddySaved (+36 more)

### Community 37 - "Community 37"
Cohesion: 0.06
Nodes (50): sessionIdOf(), invitation, userRole, AttemptLimit, clientKey(), limitAttempts(), TOKEN_ATTEMPTS, ADR-0061 (+42 more)

### Community 38 - "Community 38"
Cohesion: 0.06
Nodes (51): DIVE_FIELDS, ProviderError, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf() (+43 more)

### Community 39 - "Community 39"
Cohesion: 0.05
Nodes (52): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, cancelWaitingImports() (+44 more)

### Community 40 - "Community 40"
Cohesion: 0.07
Nodes (49): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+41 more)

### Community 41 - "Community 41"
Cohesion: 0.05
Nodes (51): PARTICIPANT_ROLES, DIVE_ONLY_PATTERN, PROBLEM_CODES, ProblemSchema, storedProblemCode(), ADR-0014, ADR-0027, oneOf() (+43 more)

### Community 42 - "Community 42"
Cohesion: 0.06
Nodes (52): AiAccessScope, seesPositions(), VerifiedAccess, diversOf(), SiteRow, checkedByRunTool, createMcpServer(), INSTRUCTIONS (+44 more)

### Community 43 - "Community 43"
Cohesion: 0.05
Nodes (49): DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN, NO_RECORDING, notAtProvider(), ADR-0017 (+41 more)

### Community 44 - "Community 44"
Cohesion: 0.08
Nodes (49): Leave, diveModeOfSsi(), keepTraining(), ssiDiveType(), ssiInteger(), ADR-0057, asNumber(), asText() (+41 more)

### Community 45 - "Community 45"
Cohesion: 0.08
Nodes (47): accountListEntry, tidyName(), DiverDetails, Caller, foldName(), nameDiffers(), Buddy, createBuddyService() (+39 more)

### Community 46 - "Community 46"
Cohesion: 0.06
Nodes (34): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+26 more)

### Community 47 - "Community 47"
Cohesion: 0.07
Nodes (38): createSsiSiteSource(), DEFAULT_SSI_SITES_URL, createSiteSources(), ADR-0021, ADR-0027, createOverpassSource(), DEFAULT_OVERPASS_URL, Element (+30 more)

### Community 48 - "Community 48"
Cohesion: 0.08
Nodes (44): BACKUP_FORMAT_VERSION, BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32HrpExpand(), bech32Polymod() (+36 more)

### Community 49 - "Community 49"
Cohesion: 0.09
Nodes (37): samplesQuery(), FindingLane(), useAssessment(), useWhen(), DepthProfile(), NO_POINTS, SurfaceBreakLane(), ADR-0036 (+29 more)

### Community 50 - "Community 50"
Cohesion: 0.06
Nodes (41): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, Services, EquipmentService, RecordInput (+33 more)

### Community 51 - "Community 51"
Cohesion: 0.06
Nodes (34): computerEventOf(), ADR-0036, computerEventFromFit(), BOM, Json, num(), obj(), positive() (+26 more)

### Community 52 - "Community 52"
Cohesion: 0.06
Nodes (41): ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, seriesOf(), minute(), minutes() (+33 more)

### Community 53 - "Community 53"
Cohesion: 0.07
Nodes (35): sampleCount(), SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject() (+27 more)

### Community 54 - "Community 54"
Cohesion: 0.08
Nodes (40): cylinderOf(), cylinderText(), diverOf(), diverText(), finite(), kept, LEAD_MATERIALS, LEAD_WATERS (+32 more)

### Community 55 - "Community 55"
Cohesion: 0.05
Nodes (42): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+34 more)

### Community 56 - "Community 56"
Cohesion: 0.07
Nodes (39): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+31 more)

### Community 57 - "Community 57"
Cohesion: 0.09
Nodes (34): createServer(), loadAuthSecret(), acquireAppLock(), workerDeps(), Config, loadConfig(), ADR-0011, ADR-0021 (+26 more)

### Community 58 - "Community 58"
Cohesion: 0.06
Nodes (35): SUIT_TYPES, SUIT_UNDERGARMENTS, DiverCertifications, replyDiverError, STATUS, CertificationSummary, DiverDetailsView, DiverDetailsViewSchema (+27 more)

### Community 59 - "Community 59"
Cohesion: 0.06
Nodes (31): replyCertificationError, STATUS, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, CertificationRouteDeps (+23 more)

### Community 60 - "Community 60"
Cohesion: 0.05
Nodes (37): AREA_OF, areaOf(), areas, capture(), findings, headers, ADR-0020, ADR-0021 (+29 more)

### Community 61 - "Community 61"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 62 - "Community 62"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 63 - "Community 63"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 64 - "Community 64"
Cohesion: 0.11
Nodes (32): DiveOrigin, REFERENCE_PREFIX, Series, SsiLogbook, asBool(), DIVE_ROW, SENT_KEYS, SsiValueType (+24 more)

### Community 65 - "Community 65"
Cohesion: 0.11
Nodes (30): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0040 (+22 more)

### Community 66 - "Community 66"
Cohesion: 0.11
Nodes (36): ToolsPlan, ToolsPlanDiver, PlanDiver, PlanLevel, PlanStretch, replaceToolsAddress(), ToolsRule, depthFromDisplay() (+28 more)

### Community 67 - "Community 67"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 68 - "Community 68"
Cohesion: 0.08
Nodes (34): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+26 more)

### Community 69 - "Community 69"
Cohesion: 0.13
Nodes (34): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+26 more)

### Community 70 - "Community 70"
Cohesion: 0.09
Nodes (29): RecordingSummary, RecordingFacts, date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num() (+21 more)

### Community 71 - "Community 71"
Cohesion: 0.07
Nodes (30): ADR-0025, WaterMismatch, waterMismatch(), DueCount, DueState, Item, ListInput, Record (+22 more)

### Community 72 - "Community 72"
Cohesion: 0.11
Nodes (27): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+19 more)

### Community 73 - "Community 73"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 74 - "Community 74"
Cohesion: 0.06
Nodes (23): CustomTypeOptions, i18next, initI18n(), resources, ADR-0014, StrayOverride, ADR-0014, none (+15 more)

### Community 75 - "Community 75"
Cohesion: 0.08
Nodes (29): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+21 more)

### Community 76 - "Community 76"
Cohesion: 0.11
Nodes (30): massIn(), Dive, draftOfLead(), draftOfWeighting(), emptyLead(), fromLastDive(), kg(), lacks() (+22 more)

### Community 77 - "Community 77"
Cohesion: 0.09
Nodes (28): ENGINE_VERSION, EVIDENCE, Evidence, FindingValues, RULE_IDS, RuleId, RULES, SEVERITIES (+20 more)

### Community 78 - "Community 78"
Cohesion: 0.08
Nodes (28): adminRole, AI_ACCESS_NAME_MAX, AI_ACCESS_PREFIX, AI_ACCESS_RATE, AuthOptions, createAuth(), PUBLIC_AUTH_PATHS, ADR-0011 (+20 more)

### Community 79 - "Community 79"
Cohesion: 0.09
Nodes (25): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+17 more)

### Community 80 - "Community 80"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 81 - "Community 81"
Cohesion: 0.09
Nodes (26): Auth, CENTRE_SOURCES, CentreService, CentreView, CentreViewSchema, Row, Source, toCentreView() (+18 more)

### Community 82 - "Community 82"
Cohesion: 0.12
Nodes (25): BACKUP_SKIPPED_DIRS, BackupDecryptionKey, encryptionKeyFingerprint(), pgCommand(), pgFailureMessage(), redactPassword(), createRestoreService(), restore() (+17 more)

### Community 83 - "Community 83"
Cohesion: 0.07
Nodes (23): account, accountRelations, apikey, rateLimit, sessionRelations, ADR-0011, ADR-0012, ADR-0035 (+15 more)

### Community 84 - "Community 84"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 85 - "Community 85"
Cohesion: 0.10
Nodes (27): applyHints(), LiveArrivals, Page, useLiveArrivals(), bump(), changedListeners, ChannelMessage, healthListeners (+19 more)

### Community 86 - "Community 86"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 87 - "Community 87"
Cohesion: 0.10
Nodes (22): createFileFormats(), FileFormat, looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf() (+14 more)

### Community 88 - "Community 88"
Cohesion: 0.10
Nodes (22): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearQueue(), conflictAtSsi() (+14 more)

### Community 89 - "Community 89"
Cohesion: 0.13
Nodes (22): errorMessage(), looksLikeZip(), formatOf(), createImportService(), cancelImport(), createImport(), endWaiting(), expireWaiting() (+14 more)

### Community 90 - "Community 90"
Cohesion: 0.10
Nodes (17): ProblemCode, ResponseState, whenResponseGone(), StoredBlob, PlacedArchive, ANSWER, looksLikeRecipientPrivateKey(), PlacedArchive (+9 more)

### Community 91 - "Community 91"
Cohesion: 0.09
Nodes (22): Cylinder, DiveView, RevisionView, ADR-0045, upload(), Candidate, Check, Outcome (+14 more)

### Community 92 - "Community 92"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 93 - "Community 93"
Cohesion: 0.11
Nodes (24): equipmentUse, DiveGear, ensureItemForDevice(), GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow (+16 more)

### Community 94 - "Community 94"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 95 - "Community 95"
Cohesion: 0.08
Nodes (20): headers, ADR-0035, aiAccessReady(), askMcp(), createAiAccess(), putAside(), headers, ADR-0016 (+12 more)

### Community 96 - "Community 96"
Cohesion: 0.13
Nodes (22): durationFormat(), DurationFormatter, formatClock(), formatDuration(), formatGasRate(), formatMass(), formatPressure(), formatPressureRate() (+14 more)

### Community 97 - "Community 97"
Cohesion: 0.09
Nodes (23): @modelcontextprotocol/server@^2.3.1, aiAccessLog, aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, KeyRow, LOG_DAYS (+15 more)

### Community 98 - "Community 98"
Cohesion: 0.14
Nodes (22): surfacePressureBar(), ndlMinutes(), ADR-0032, ADR-0046, schreinerStep(), surfaceSaturatedTissues(), ADR-0071, WATER_VAPOUR_BAR (+14 more)

### Community 99 - "Community 99"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 100 - "Community 100"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 101 - "Community 101"
Cohesion: 0.13
Nodes (20): APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), AppLockProbe, probeAppLock() (+12 more)

### Community 102 - "Community 102"
Cohesion: 0.11
Nodes (23): AIR, CNS_HALF_LIFE_MINUTES, DEFAULT_CONDITIONS, LoadingDive, N2_REPLAY_WINDOW_HOURS, N2_STALE_AFTER_HOURS, N2Loadings, N2LoadingsBase (+15 more)

### Community 103 - "Community 103"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 104 - "Community 104"
Cohesion: 0.11
Nodes (14): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+6 more)

### Community 105 - "Community 105"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 106 - "Community 106"
Cohesion: 0.09
Nodes (23): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+15 more)

### Community 107 - "Community 107"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 108 - "Community 108"
Cohesion: 0.15
Nodes (18): qr@0.7.2, cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043 (+10 more)

### Community 109 - "Community 109"
Cohesion: 0.12
Nodes (19): defineTool(), Id(), Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), getDiverLoadings (+11 more)

### Community 110 - "Community 110"
Cohesion: 0.14
Nodes (16): BottomTime, bottomTime(), Limit, ADR-0032, ADR-0033, ADR-0071, MAX_DEPTH_M, NOAA_ROWS (+8 more)

### Community 111 - "Community 111"
Cohesion: 0.22
Nodes (17): ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres(), isHypoxic(), metresPerBar() (+9 more)

### Community 112 - "Community 112"
Cohesion: 0.16
Nodes (16): DecisionTimelineProps, GapKind, ADR-0063, addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, laneFitsCapsule(), relations() (+8 more)

### Community 113 - "Community 113"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 114 - "Community 114"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 115 - "Community 115"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 116 - "Community 116"
Cohesion: 0.20
Nodes (17): AssessmentView, AssessmentState, bandSeconds(), clock(), counts(), differing(), Finding, formatRate() (+9 more)

### Community 117 - "Community 117"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 118 - "Community 118"
Cohesion: 0.11
Nodes (13): BACKUP_EXTENSION, BackupEncryption, BackupPart, BackupLog, BackupRunRow, BackupSettingsInput, BackupSettingsValues, DEFAULT_BACKUP_SETTINGS (+5 more)

### Community 119 - "Community 119"
Cohesion: 0.22
Nodes (16): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), appendRun(), browserStats() (+8 more)

### Community 120 - "Community 120"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 121 - "Community 121"
Cohesion: 0.16
Nodes (14): AssessmentError, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), requireManagedDive(), same() (+6 more)

### Community 122 - "Community 122"
Cohesion: 0.12
Nodes (16): BACKUP_RUN_TASK, backupTask(), ADR-0069, CERTIFICATION_IMAGE_TASK, EXPIRE_WAITING_IMPORTS_TASK, PROCESS_IMPORT_TASK, SCHEDULED_FETCH_TASK, PUSH_RUN_STEP_TASK (+8 more)

### Community 123 - "Community 123"
Cohesion: 0.12
Nodes (16): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+8 more)

### Community 124 - "Community 124"
Cohesion: 0.14
Nodes (16): CERTIFICATION_IMAGE_ATTEMPTS, CertificationExpiry, CertificationInput, CertificationView, EXPIRING_SOON_DAYS, expiryOf(), ImportReport, ImportResult (+8 more)

### Community 125 - "Community 125"
Cohesion: 0.15
Nodes (14): CIRCUIT, circuitFromFit, DECO_MODEL, decoModelFromFit, DIVE_ALERT, DIVE_MODE, diveModeFromFit, ADR-0015 (+6 more)

### Community 126 - "Community 126"
Cohesion: 0.12
Nodes (16): ADR-0013, ADR-0026, ADR-0028, ADR-0030, ADR-0031, ADR-0034, ADR-0035, ADR-0036 (+8 more)

### Community 127 - "Community 127"
Cohesion: 0.15
Nodes (14): certification, PROBLEMS, createUserAdminService(), keepAnAdmin(), lock(), deleteUserData(), ADR-0013, ADR-0024 (+6 more)

### Community 128 - "Community 128"
Cohesion: 0.21
Nodes (13): defined(), Joined, joinedSummary(), joinParts(), median(), PartInput, round3(), ADR-0063 (+5 more)

### Community 129 - "Community 129"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 130 - "Community 130"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 131 - "Community 131"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 132 - "Community 132"
Cohesion: 0.16
Nodes (14): serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, DueEntry, ScheduleView, equipmentList(), EquipmentListFilter (+6 more)

### Community 133 - "Community 133"
Cohesion: 0.13
Nodes (10): EGYPT, ExternalId, HOUSE_REEF, Method, Position, Revision, Site, SiteImport (+2 more)

### Community 134 - "Community 134"
Cohesion: 0.19
Nodes (10): fileTreeChecksum(), tarDirectory(), createBackupService(), checkSettings(), due(), enqueueScheduled(), readSettings(), saveSettings() (+2 more)

### Community 135 - "Community 135"
Cohesion: 0.29
Nodes (14): managementOf(), createEquipmentService(), assemble(), detailOf(), lastCylinderOf(), managedItem(), managedRecord(), managedSchedule() (+6 more)

### Community 136 - "Community 136"
Cohesion: 0.22
Nodes (11): fromOsm(), fromSsi(), fromWd(), HOUSE_REEF, osm(), site(), ssi(), ADR-0021 (+3 more)

### Community 137 - "Community 137"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 138 - "Community 138"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 139 - "Community 139"
Cohesion: 0.15
Nodes (9): Sac, SAC_MISSING, SacMissing, ADR-0033, ADR-0045, Cylinder, dive, ADR-0033 (+1 more)

### Community 140 - "Community 140"
Cohesion: 0.17
Nodes (6): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, uniqueWord()

### Community 141 - "Community 141"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 142 - "Community 142"
Cohesion: 0.18
Nodes (10): EquipmentError, ADR-0034, ADR-0053, checkCost(), checkDates(), checkWindow(), checkResets(), checkSchedule() (+2 more)

### Community 143 - "Community 143"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 144 - "Community 144"
Cohesion: 0.18
Nodes (5): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053

### Community 145 - "Community 145"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 146 - "Community 146"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

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
Nodes (8): wallClockMs(), AQABA, ATTERSEE, EILAT, HAUSREEF, local(), MID_ATLANTIC, ADR-0030

### Community 151 - "Community 151"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 152 - "Community 152"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 153 - "Community 153"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 155 - "Community 155"
Cohesion: 0.36
Nodes (5): idOf(), processImportTask(), startWorker(), TaskLog, quiet

### Community 156 - "Community 156"
Cohesion: 0.25
Nodes (8): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, MODULE_IMPORTS, moduleRules, target()

### Community 157 - "Community 157"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 158 - "Community 158"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 159 - "Community 159"
Cohesion: 0.36
Nodes (8): bech32Encode(), convertBits(), encodeRecipient(), encodeRecipientPrivateKey(), generateRecipientKeyPair(), jwkOf(), rawPrivateOf(), rawPublicOf()

### Community 160 - "Community 160"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 161 - "Community 161"
Cohesion: 0.29
Nodes (6): itemIds(), ADR-0026, ADR-0034, Usage, UsageDive, UsageRow

### Community 162 - "Community 162"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 163 - "Community 163"
Cohesion: 0.33
Nodes (5): CertificationError, cleaned(), requireManagedDiver(), rowOrThrow(), isCalendarDate()

### Community 164 - "Community 164"
Cohesion: 0.47
Nodes (6): BottomTimeInput, Conditions, GasMix, GasPlanInput, TissuesAfterDivesInput, NdlInput

### Community 165 - "Community 165"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 166 - "Community 166"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 167 - "Community 167"
Cohesion: 0.40
Nodes (4): label(), CompareSubject, facts, subjects

### Community 168 - "Community 168"
Cohesion: 0.40
Nodes (4): CertificationSummary, NO_CERTIFICATIONS, ADR-0050, ADR-0053

### Community 169 - "Community 169"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 170 - "Community 170"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 172 - "Community 172"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 173 - "Community 173"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 174 - "Community 174"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

### Community 176 - "Community 176"
Cohesion: 0.67
Nodes (3): draftOf(), localStart(), round1()

## Knowledge Gaps
- **1162 isolated node(s):** `ADR-0048`, `ADR-0016`, `ADR-0038`, `ADR-0038`, `ADR-0026` (+1157 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 2973 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **5 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 4` to `Community 0`, `Community 1`, `Community 2`, `Community 132`, `Community 5`, `Community 6`, `Community 9`, `Community 12`, `Community 13`, `Community 16`, `Community 19`, `Community 20`, `Community 21`, `Community 22`, `Community 28`, `Community 30`, `Community 161`, `Community 33`, `Community 37`, `Community 42`, `Community 43`, `Community 45`, `Community 50`, `Community 57`, `Community 68`, `Community 83`, `Community 86`, `Community 93`, `Community 97`, `Community 106`, `Community 118`, `Community 124`, `Community 127`?**
  _High betweenness centrality (0.038) - this node is a cross-community bridge._
- **What connects `ADR-0048`, `ADR-0016`, `ADR-0038` to the rest of the system?**
  _1162 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.020144578313253014 - nodes in this community are weakly interconnected._
- **Why does `react` connect `Community 24` to `Community 3`, `Community 7`, `Community 8`, `Community 10`, `Community 11`, `Community 14`, `Community 15`, `Community 18`, `Community 23`, `Community 25`, `Community 34`, `Community 35`, `Community 49`, `Community 66`, `Community 76`, `Community 84`, `Community 85`, `Community 108`, `Community 112`, `Community 114`?**
  _High betweenness centrality (0.028) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.023417535922248753 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 24` to `Community 34`, `Community 3`, `Community 35`, `Community 66`, `Community 7`, `Community 8`, `Community 10`, `Community 11`, `Community 14`, `Community 15`, `Community 49`, `Community 114`, `Community 18`, `Community 84`, `Community 85`, `Community 23`, `Community 25`?**
  _High betweenness centrality (0.023) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.02373869224697954 - nodes in this community are weakly interconnected._