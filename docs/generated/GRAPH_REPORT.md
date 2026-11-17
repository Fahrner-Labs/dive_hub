# Graph Report - dive_hub  (2026-11-17)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 7317 nodes · 22821 edges · 178 communities (173 shown, 5 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 223 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `aa6f68d8`
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
6. `unwrap()` - 138 edges
7. `drizzle-orm` - 137 edges
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
- `Removal` --references--> `ProviderServiceError`  [EXTRACTED]
  apps/server/src/providers/push/push-service.ts → apps/server/src/providers/registry.ts
- `askedAt()` --calls--> `DiveError`  [EXTRACTED]
  apps/server/src/dives/continuation/append-service.ts → apps/server/src/dives/dive-service.ts

## Import Cycles
- None detected.

## Communities (178 total, 5 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.03
Nodes (176): DiveValues, OverridableField, cylinderCatalogueQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime (+168 more)

### Community 1 - "Community 1"
Cohesion: 0.03
Nodes (156): continuationAppend, diverBodyWeight, diverManagement, lead, LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, participant (+148 more)

### Community 2 - "Community 2"
Cohesion: 0.06
Nodes (160): DeleteUserDialog(), Invite(), SiteDataPanel(), UserRow(), CreateAccess(), certificationsQuery(), diverDetailsQuery(), diverSearchQuery() (+152 more)

### Community 3 - "Community 3"
Cohesion: 0.03
Nodes (153): ComputerChoice, DiveImportMode, DiveKind, importOriginal, ProviderImportPlan, providerSiteData, ProviderCylinder, providerCylinderOf() (+145 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (146): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+138 more)

### Community 5 - "Community 5"
Cohesion: 0.04
Nodes (111): user, device, diver, ADR-0013, ADR-0011, dumpToolAvailable(), execFileAsync, ADR-0069 (+103 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (148): BuddyView, ConflictChoices, ConflictFieldView, ConnectionView, DiveImportMode, DiveImportPreview, diveImportPreviewQuery(), DiveKind (+140 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (135): allButDive(), AnyLogbookCheck, CandidateView, continuationCandidateQuery(), ContinuationCandidateView, deletedDivesOn(), DeletedDivesPageView, deletedDivesQuery() (+127 more)

### Community 8 - "Community 8"
Cohesion: 0.04
Nodes (129): ChangePassword(), DisplaySettings(), SecuritySettings(), Sessions(), useDescribeAgent(), AdminSettings(), Invitations(), LinkToPassOn() (+121 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (106): CertificationAccount, RemoteCertification, ADR-0050, ADR-0053, ParticipantRole, tidyName(), DiverDetails, foldName() (+98 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (111): Auth, CentreService, CentreRouteDeps, LEAD_PLACEMENTS, UTC_OFFSET_SOURCES, WEIGHTING_FELT, DiverService, DiverRouteDeps (+103 more)

### Community 11 - "Community 11"
Cohesion: 0.02
Nodes (95): buddySuggestion, parseHint(), Accepted, Candidate, Check, DiveView, Outcome, RevisionView (+87 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (89): setupRestoreArchivesQuery(), BackupRunView, BackupSettingsView, SiteDataView, ADR-0030, DiveGearItemView, DiveGearView, EquipmentCategory (+81 more)

### Community 13 - "Community 13"
Cohesion: 0.05
Nodes (98): Me, AcceptedBuddySuggestionView, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery(), BuddySuggestionView, candidatesQuery() (+90 more)

### Community 14 - "Community 14"
Cohesion: 0.04
Nodes (77): equipmentDueQuery(), EquipmentDueNotice(), ADR-0034, avatarTone(), initials(), dismissDue(), dismissedDueDigest(), dueCounts() (+69 more)

### Community 15 - "Community 15"
Cohesion: 0.04
Nodes (77): auth, BackupServiceDeps, createServices(), ServiceParts, ADR-0027, ADR-0030, ADR-0061, ADR-0063 (+69 more)

### Community 16 - "Community 16"
Cohesion: 0.03
Nodes (77): connection, createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities() (+69 more)

### Community 17 - "Community 17"
Cohesion: 0.04
Nodes (75): createServer(), createAuth(), loadAuthSecret(), acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS (+67 more)

### Community 18 - "Community 18"
Cohesion: 0.03
Nodes (81): equipmentUse, pushRun, managesDiveDiver(), Participant, participantsOfDives(), asksFor(), DIVE_ONLY, DiveListFilter (+73 more)

### Community 19 - "Community 19"
Cohesion: 0.02
Nodes (89): StoredConflictField, accountListEntry, actorType, aiAccessOutcome, backupRunKind, backupRunStatus, candidateResolution, centreSource (+81 more)

### Community 20 - "Community 20"
Cohesion: 0.04
Nodes (78): adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, AuthOptions, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, ADR-0011, ADR-0012 (+70 more)

### Community 21 - "Community 21"
Cohesion: 0.04
Nodes (80): CertificationService, DIVE_IMPORT_MODES, AssessmentRefresh, ADR-0036, ADR-0053, ErrorAnswer, PROBLEM_CODES, ProblemSchema (+72 more)

### Community 22 - "Community 22"
Cohesion: 0.04
Nodes (82): Queryable, logbookCheckAnswer, logbookCheckDiveAnswer, keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration(), CheckedDive (+74 more)

### Community 23 - "Community 23"
Cohesion: 0.06
Nodes (73): open(), close(), createConnectionService(), checkAutomation(), checkFollowNumbers(), expiryOf(), onTurn(), ownConnection() (+65 more)

### Community 24 - "Community 24"
Cohesion: 0.04
Nodes (68): Tx, diveCentre, jointDive, AppendUndo, overlapWindow(), recordingKeySource(), deletedEarlier(), fullerCopyHere() (+60 more)

### Community 25 - "Community 25"
Cohesion: 0.05
Nodes (63): diveSiteExternalId, createSsiAdapter(), SSI_CAPABILITIES, createSsiClient(), ssiSiteRow(), Fetch, Card, DEEP (+55 more)

### Community 26 - "Community 26"
Cohesion: 0.08
Nodes (65): buildApp(), assessmentRoutes(), AI_ACCESS_NAME_MAX, SessionUser, authHandler(), authHeaders(), fastify, FastifyRequest (+57 more)

### Community 27 - "Community 27"
Cohesion: 0.04
Nodes (59): typedId(), verificationCodeOf(), DiverError, ADR-0016, ADR-0053, ACCOUNT_PATTERN, accountKey(), createDiverService() (+51 more)

### Community 28 - "Community 28"
Cohesion: 0.06
Nodes (49): cylinder, dive, duplicateCandidate, importJob, original, providerConflict, push, recording (+41 more)

### Community 29 - "Community 29"
Cohesion: 0.04
Nodes (47): diveLease, providerLease, pushRunItem, kept(), LEASE_MS, MAX_WAIT_MS, POLL_MS, Delivered (+39 more)

### Community 30 - "Community 30"
Cohesion: 0.06
Nodes (48): SsiClient, SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), buildUpdateRecord(), DiveForSsi, emptyRecord() (+40 more)

### Community 31 - "Community 31"
Cohesion: 0.06
Nodes (58): createCertificationService(), ConnectionRow, WaitingPushOptions, waitingOf(), audienceOfConnection(), audienceOfDive(), audienceOfDiver(), emitHint() (+50 more)

### Community 32 - "Community 32"
Cohesion: 0.05
Nodes (53): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, CylinderInput, answer(), baseFrom(), cylindersAtReserve() (+45 more)

### Community 33 - "Community 33"
Cohesion: 0.07
Nodes (57): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+49 more)

### Community 34 - "Community 34"
Cohesion: 0.05
Nodes (52): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, cancelWaitingImports() (+44 more)

### Community 35 - "Community 35"
Cohesion: 0.07
Nodes (53): divesQuery(), LogbookPage, PAGE_SIZE, siteQuery(), Buddies(), COUNT_OF, DiveList(), DiveRow() (+45 more)

### Community 36 - "Community 36"
Cohesion: 0.06
Nodes (48): bodyWeightsQuery(), CertificationView, DeviceView, DiverDetailsView, diverLoadingsQuery(), diversQuery(), DiverView, ExternalDiverView (+40 more)

### Community 37 - "Community 37"
Cohesion: 0.06
Nodes (40): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+32 more)

### Community 38 - "Community 38"
Cohesion: 0.06
Nodes (34): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+26 more)

### Community 39 - "Community 39"
Cohesion: 0.08
Nodes (44): NoFly, residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows(), DiveRow, endMsOf(), loadingDivesForReplay() (+36 more)

### Community 40 - "Community 40"
Cohesion: 0.08
Nodes (37): sacOfDive(), BottomTimeQuery, DEFAULT_GF_HIGH, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033 (+29 more)

### Community 41 - "Community 41"
Cohesion: 0.09
Nodes (43): BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32HrpExpand(), bech32Polymod(), checkScryptBounds() (+35 more)

### Community 42 - "Community 42"
Cohesion: 0.08
Nodes (42): DUE_STATES, SiteRow, Cursor, cursorAt(), DateOnly(), defineTool(), Detail, diversAsked() (+34 more)

### Community 43 - "Community 43"
Cohesion: 0.06
Nodes (35): DiveExportAction, buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open(), saveAndReadBack() (+27 more)

### Community 44 - "Community 44"
Cohesion: 0.08
Nodes (40): Leave, Series, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+32 more)

### Community 45 - "Community 45"
Cohesion: 0.06
Nodes (41): @modelcontextprotocol/server@^2.3.1, aiAccessLog, aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, AiAccessScope, KeyRow (+33 more)

### Community 46 - "Community 46"
Cohesion: 0.05
Nodes (37): account, accountRelations, apikey, rateLimit, session, sessionRelations, ADR-0011, ADR-0012 (+29 more)

### Community 47 - "Community 47"
Cohesion: 0.06
Nodes (42): ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, seriesOf(), minute(), minutes() (+34 more)

### Community 48 - "Community 48"
Cohesion: 0.06
Nodes (41): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, Services, EquipmentService, RecordInput (+33 more)

### Community 49 - "Community 49"
Cohesion: 0.06
Nodes (38): CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043, CreateBody (+30 more)

### Community 50 - "Community 50"
Cohesion: 0.06
Nodes (36): CYLINDER_MATERIALS, SUIT_TYPES, SUIT_UNDERGARMENTS, DiverCertifications, replyDiverError, STATUS, CertificationSummary, DiverDetailsView (+28 more)

### Community 51 - "Community 51"
Cohesion: 0.05
Nodes (42): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+34 more)

### Community 52 - "Community 52"
Cohesion: 0.08
Nodes (34): ImportErrorCode, ImportFound, ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS, eachFile(), readEntry() (+26 more)

### Community 53 - "Community 53"
Cohesion: 0.07
Nodes (38): Change(), Changes(), historyRoute, Made(), PositionSchema, AdoptBody, Country, CreateBody (+30 more)

### Community 54 - "Community 54"
Cohesion: 0.15
Nodes (40): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+32 more)

### Community 55 - "Community 55"
Cohesion: 0.08
Nodes (38): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+30 more)

### Community 56 - "Community 56"
Cohesion: 0.07
Nodes (36): PARTICIPANT_ROLES, DIVE_ONLY_PATTERN, ADR-0053, ADR-0056, WaitingPushViewSchema, storedProblemCode(), oneOf(), refTo() (+28 more)

### Community 57 - "Community 57"
Cohesion: 0.08
Nodes (34): date(), FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position(), positive() (+26 more)

### Community 58 - "Community 58"
Cohesion: 0.07
Nodes (33): SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject(), MAX_CARD_IMAGE_BYTES (+25 more)

### Community 59 - "Community 59"
Cohesion: 0.08
Nodes (39): CylinderMaterial, cylinderOf(), cylinderText(), diverOf(), diverText(), finite(), kept, LEAD_MATERIALS (+31 more)

### Community 60 - "Community 60"
Cohesion: 0.05
Nodes (37): AREA_OF, areaOf(), areas, capture(), findings, headers, ADR-0020, ADR-0021 (+29 more)

### Community 61 - "Community 61"
Cohesion: 0.07
Nodes (33): ADR-0025, WaterMismatch, CIRCUIT, circuitFromFit, DECO_MODEL, decoModelFromFit, DIVE_ALERT, DIVE_MODE (+25 more)

### Community 62 - "Community 62"
Cohesion: 0.11
Nodes (35): massIn(), Dive, draftOfLead(), draftOfWeighting(), emptyLead(), fromLastDive(), hasThickness(), kg() (+27 more)

### Community 63 - "Community 63"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 64 - "Community 64"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 65 - "Community 65"
Cohesion: 0.11
Nodes (34): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+26 more)

### Community 66 - "Community 66"
Cohesion: 0.11
Nodes (30): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0040 (+22 more)

### Community 67 - "Community 67"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 68 - "Community 68"
Cohesion: 0.07
Nodes (30): diveAssessment, recordingEvent, Assessment, FindingView, Row, ADR-0036, upload(), Check (+22 more)

### Community 69 - "Community 69"
Cohesion: 0.09
Nodes (33): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+25 more)

### Community 70 - "Community 70"
Cohesion: 0.07
Nodes (30): CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError, CertificationExpiry, CertificationInput, CertificationView, cleaned(), requireManagedDiver() (+22 more)

### Community 71 - "Community 71"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 72 - "Community 72"
Cohesion: 0.13
Nodes (31): importsQuery(), Logbook(), ImportContext, ImportFilesButton(), ImportList(), ImportNotices(), ImportPanel(), ImportProvider() (+23 more)

### Community 73 - "Community 73"
Cohesion: 0.12
Nodes (27): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+19 more)

### Community 74 - "Community 74"
Cohesion: 0.14
Nodes (32): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findNewId(), Format (+24 more)

### Community 75 - "Community 75"
Cohesion: 0.09
Nodes (28): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+20 more)

### Community 76 - "Community 76"
Cohesion: 0.09
Nodes (25): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+17 more)

### Community 77 - "Community 77"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 78 - "Community 78"
Cohesion: 0.12
Nodes (25): BACKUP_SKIPPED_DIRS, BackupDecryptionKey, encryptionKeyFingerprint(), pgCommand(), pgFailureMessage(), redactPassword(), createRestoreService(), restore() (+17 more)

### Community 79 - "Community 79"
Cohesion: 0.11
Nodes (27): Conditions, GasMix, GasPlanInput, TissuesAfterDivesInput, DESCENT_RATE_M_PER_MIN, NdlInput, ndlMinutes(), ADR-0032 (+19 more)

### Community 80 - "Community 80"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 81 - "Community 81"
Cohesion: 0.10
Nodes (22): RecordingSummary, UtcOffsetSource, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput (+14 more)

### Community 82 - "Community 82"
Cohesion: 0.10
Nodes (27): applyHints(), LiveArrivals, Page, useLiveArrivals(), bump(), changedListeners, ChannelMessage, healthListeners (+19 more)

### Community 83 - "Community 83"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 84 - "Community 84"
Cohesion: 0.10
Nodes (25): ENGINE_VERSION, EVIDENCE, Evidence, FindingValues, LIMITS, RuleId, SEVERITIES, Severity (+17 more)

### Community 85 - "Community 85"
Cohesion: 0.11
Nodes (26): ScheduleDue, DiveGear, DueEntry, ensureItemForDevice(), GearItem, ItemDetailView, ItemEdit, ItemInput (+18 more)

### Community 86 - "Community 86"
Cohesion: 0.09
Nodes (23): computerEventOf(), ADR-0036, computerEventFromFit(), COMPUTER_EVENT, computerEventFromSuunto(), DECO_MODEL, decoModelFromSuunto, DIVE_MODE (+15 more)

### Community 87 - "Community 87"
Cohesion: 0.10
Nodes (22): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearQueue(), conflictAtSsi() (+14 more)

### Community 88 - "Community 88"
Cohesion: 0.09
Nodes (18): TAGS, killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023 (+10 more)

### Community 89 - "Community 89"
Cohesion: 0.10
Nodes (22): BACKUP_RUN_TASK, backupTask(), ADR-0069, PurgeService, EXPIRE_WAITING_IMPORTS_TASK, PROCESS_IMPORT_TASK, SCHEDULED_FETCH_TASK, PUSH_RUN_STEP_TASK (+14 more)

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
Cohesion: 0.16
Nodes (24): DiveOrigin, REFERENCE_PREFIX, SsiLogbook, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef() (+16 more)

### Community 94 - "Community 94"
Cohesion: 0.08
Nodes (20): headers, ADR-0035, aiAccessReady(), askMcp(), createAiAccess(), putAside(), headers, ADR-0016 (+12 more)

### Community 95 - "Community 95"
Cohesion: 0.13
Nodes (22): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DueInput, DueSchedule (+14 more)

### Community 96 - "Community 96"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 97 - "Community 97"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 98 - "Community 98"
Cohesion: 0.13
Nodes (23): managementOf(), EquipmentError, ADR-0034, ADR-0053, checkCost(), checkDates(), checkWindow(), createEquipmentService() (+15 more)

### Community 99 - "Community 99"
Cohesion: 0.15
Nodes (21): Answer, CatalogueCylinder, CylinderInput, cylindersOfDrafts(), CylinderView, draftOfCylinder(), emptyDraft(), fromCatalogue() (+13 more)

### Community 100 - "Community 100"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 101 - "Community 101"
Cohesion: 0.16
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 102 - "Community 102"
Cohesion: 0.09
Nodes (20): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+12 more)

### Community 103 - "Community 103"
Cohesion: 0.14
Nodes (19): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), parseXml() (+11 more)

### Community 104 - "Community 104"
Cohesion: 0.09
Nodes (23): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+15 more)

### Community 105 - "Community 105"
Cohesion: 0.13
Nodes (18): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, GasRule (+10 more)

### Community 106 - "Community 106"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 107 - "Community 107"
Cohesion: 0.13
Nodes (19): equipmentItem, serviceRecord, serviceRecordSchedule, serviceSchedule, equipmentList(), EquipmentListFilter, EquipmentRecordRead, today() (+11 more)

### Community 108 - "Community 108"
Cohesion: 0.24
Nodes (17): ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres(), isHypoxic(), metresPerBar() (+9 more)

### Community 109 - "Community 109"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 110 - "Community 110"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 111 - "Community 111"
Cohesion: 0.11
Nodes (18): AssessmentService, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView(), ADR-0036 (+10 more)

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
Cohesion: 0.21
Nodes (16): AssessmentView, AssessmentState, bandSeconds(), clock(), counts(), differing(), Finding, formatRate() (+8 more)

### Community 118 - "Community 118"
Cohesion: 0.12
Nodes (16): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+8 more)

### Community 119 - "Community 119"
Cohesion: 0.15
Nodes (12): createPoliteHttp(), Fetch, Init, PoliteHttpOptions, PROJECT_URL, RETRY_STATUSES, ADR-0021, userAgent() (+4 more)

### Community 120 - "Community 120"
Cohesion: 0.14
Nodes (15): SiteImportRouteDeps, StartBody, STATUS, ADR-0021, SiteImportError, SiteImportService, Area, Counts (+7 more)

### Community 121 - "Community 121"
Cohesion: 0.22
Nodes (15): samplesQuery(), FindingLane(), useAssessment(), useWhen(), DepthProfile(), NO_POINTS, SurfaceBreakLane(), ADR-0036 (+7 more)

### Community 122 - "Community 122"
Cohesion: 0.20
Nodes (14): AppLockProbe, BackupManifest, Arguments, credentialOf(), FILE_OPTIONS, fileText(), finishedArguments(), parseArguments() (+6 more)

### Community 123 - "Community 123"
Cohesion: 0.16
Nodes (16): BACKUP_FORMAT_VERSION, bech32Encode(), convertBits(), decryptFile(), decryptFileWith(), encodeRecipient(), encodeRecipientPrivateKey(), fileTreeChecksum() (+8 more)

### Community 124 - "Community 124"
Cohesion: 0.12
Nodes (16): ADR-0013, ADR-0026, ADR-0028, ADR-0030, ADR-0031, ADR-0034, ADR-0035, ADR-0036 (+8 more)

### Community 125 - "Community 125"
Cohesion: 0.15
Nodes (12): replyCertificationError, STATUS, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, CertificationRouteDeps (+4 more)

### Community 126 - "Community 126"
Cohesion: 0.16
Nodes (12): overpassQuery(), PoliteHttp, Binding, DEFAULT_WIKIDATA_SPARQL_URL, parseWikidata(), point(), ADR-0021, value() (+4 more)

### Community 127 - "Community 127"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 128 - "Community 128"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 129 - "Community 129"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 130 - "Community 130"
Cohesion: 0.20
Nodes (11): DIVE_KINDS, ImportView, ImportViewSchema, toImportView(), ADR-0030, ImportRouteDeps, importRoutes(), ADR-0044 (+3 more)

### Community 131 - "Community 131"
Cohesion: 0.20
Nodes (13): createSsiSiteSource(), DEFAULT_SSI_SITES_URL, createSiteSources(), ADR-0021, ADR-0027, createOverpassSource(), DEFAULT_OVERPASS_URL, Element (+5 more)

### Community 132 - "Community 132"
Cohesion: 0.13
Nodes (10): EGYPT, ExternalId, HOUSE_REEF, Method, Position, Revision, Site, SiteImport (+2 more)

### Community 133 - "Community 133"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 134 - "Community 134"
Cohesion: 0.18
Nodes (11): addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, relations(), TimelineBar, TimelineBarGeometry, TimelineGapGeometry, TimelineGeometry (+3 more)

### Community 135 - "Community 135"
Cohesion: 0.29
Nodes (13): temperatureIn(), DepthPlot(), LINE_DASHES, plotFont(), PressureStrip(), RIGHT_AXIS, token(), ADR-0045 (+5 more)

### Community 136 - "Community 136"
Cohesion: 0.20
Nodes (12): flag(), idText(), isObject(), numberOrNull(), readSsiSite(), SsiSiteValues, visibilityFor(), WATER_OF_BOW (+4 more)

### Community 137 - "Community 137"
Cohesion: 0.16
Nodes (7): CustomTypeOptions, i18next, initI18n(), resources, ADR-0014, ADR-0014, none

### Community 138 - "Community 138"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 139 - "Community 139"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 140 - "Community 140"
Cohesion: 0.21
Nodes (9): tarDirectory(), createBackupService(), checkSettings(), due(), enqueueScheduled(), readSettings(), saveSettings(), sweepRetention() (+1 more)

### Community 141 - "Community 141"
Cohesion: 0.15
Nodes (9): Sac, SAC_MISSING, SacMissing, ADR-0033, ADR-0045, Cylinder, dive, ADR-0033 (+1 more)

### Community 142 - "Community 142"
Cohesion: 0.27
Nodes (12): asNumber(), asText(), compareReadBack(), describe(), isObject(), keyDifferences(), pause(), recording() (+4 more)

### Community 143 - "Community 143"
Cohesion: 0.17
Nodes (6): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, uniqueWord()

### Community 144 - "Community 144"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 145 - "Community 145"
Cohesion: 0.20
Nodes (9): Series, ChannelSeries, RecordedGas, Tank, tanksOf(), TiedCylinder, ADR-0045, readings (+1 more)

### Community 146 - "Community 146"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 147 - "Community 147"
Cohesion: 0.31
Nodes (10): looksLikeZip(), coordinate(), parseSsiSites(), sitesJson(), truthy(), ADR-0024, unavailable(), alpha2Of() (+2 more)

### Community 148 - "Community 148"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 149 - "Community 149"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 150 - "Community 150"
Cohesion: 0.25
Nodes (7): Profile, SKETCH_HEIGHT, SKETCH_WIDTH, sketchPaths(), sketchScale(), ADR-0041, ADR-0041

### Community 151 - "Community 151"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 152 - "Community 152"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 153 - "Community 153"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 154 - "Community 154"
Cohesion: 0.22
Nodes (9): getDiverLoadings, HINT, Input, LoadingsInput, planGasAndBottomTime, ADR-0032, ADR-0035, Used() (+1 more)

### Community 155 - "Community 155"
Cohesion: 0.22
Nodes (9): Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), NOTICE, planning(), PLAN_RULES (+1 more)

### Community 156 - "Community 156"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 157 - "Community 157"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 158 - "Community 158"
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
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 163 - "Community 163"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 164 - "Community 164"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 165 - "Community 165"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 167 - "Community 167"
Cohesion: 0.40
Nodes (4): offer, osm, ssi, wikidata

### Community 168 - "Community 168"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 169 - "Community 169"
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
- **1165 isolated node(s):** `ADR-0031`, `ADR-0032`, `ADR-0033`, `ADR-0031`, `ADR-0034` (+1160 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 2978 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **5 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `@tanstack/react-query` connect `Community 12` to `Community 0`, `Community 2`, `Community 35`, `Community 36`, `Community 6`, `Community 7`, `Community 8`, `Community 72`, `Community 13`, `Community 110`, `Community 14`, `Community 80`, `Community 82`, `Community 121`?**
  _High betweenness centrality (0.030) - this node is a cross-community bridge._
- **What connects `ADR-0031`, `ADR-0032`, `ADR-0033` to the rest of the system?**
  _1165 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.0252779046748896 - nodes in this community are weakly interconnected._
- **Why does `drizzle-orm` connect `Community 5` to `Community 1`, `Community 3`, `Community 4`, `Community 9`, `Community 11`, `Community 15`, `Community 16`, `Community 17`, `Community 18`, `Community 19`, `Community 20`, `Community 22`, `Community 23`, `Community 24`, `Community 25`, `Community 27`, `Community 28`, `Community 29`, `Community 31`, `Community 37`, `Community 39`, `Community 40`, `Community 42`, `Community 45`, `Community 46`, `Community 48`, `Community 52`, `Community 56`, `Community 68`, `Community 69`, `Community 70`, `Community 83`, `Community 85`, `Community 104`, `Community 107`, `Community 115`?**
  _High betweenness centrality (0.030) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.02799466768234622 - nodes in this community are weakly interconnected._
- **Why does `react` connect `Community 14` to `Community 0`, `Community 2`, `Community 35`, `Community 36`, `Community 6`, `Community 7`, `Community 8`, `Community 72`, `Community 135`, `Community 12`, `Community 13`, `Community 110`, `Community 80`, `Community 82`, `Community 121`, `Community 62`?**
  _High betweenness centrality (0.024) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.05623376623376623 - nodes in this community are weakly interconnected._