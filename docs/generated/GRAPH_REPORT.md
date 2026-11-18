# Graph Report - dive_hub  (2026-11-18)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 7380 nodes · 23019 edges · 178 communities (171 shown, 7 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 223 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `64ccd0e6`
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
6. `drizzle-orm` - 139 edges
7. `unwrap()` - 138 edges
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
- `EquipmentListFilter` --references--> `EquipmentCategory`  [EXTRACTED]
  apps/server/src/equipment/read.ts → apps/server/src/vocabulary.ts

## Import Cycles
- None detected.

## Communities (178 total, 7 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.04
Nodes (218): DisplaySettings(), DeleteUserDialog(), Invite(), SiteDataPanel(), UserRow(), CreateAccess(), bodyWeightsQuery(), certificationsQuery() (+210 more)

### Community 1 - "Community 1"
Cohesion: 0.03
Nodes (168): diversQuery(), cylinderCatalogueQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery() (+160 more)

### Community 2 - "Community 2"
Cohesion: 0.04
Nodes (108): user, dive, diver, duplicateCandidate, original, recording, revision, PURGE_AFTER_DAYS (+100 more)

### Community 3 - "Community 3"
Cohesion: 0.03
Nodes (145): AcceptedBuddySuggestionView, allButDive(), AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionView, CandidateView, continuationCandidateQuery(), ContinuationCandidateView (+137 more)

### Community 4 - "Community 4"
Cohesion: 0.02
Nodes (124): Auth, CentreService, CentreRouteDeps, LEAD_PLACEMENTS, UTC_OFFSET_SOURCES, WEIGHTING_FELT, DiverService, DiverRouteDeps (+116 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (104): dependencies, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, @internationalized/date@^3.12.4, lucide-react@^1.51.0, qr@0.7.2, react@^19.3.0, react-aria-components@^1.21.1 (+96 more)

### Community 6 - "Community 6"
Cohesion: 0.04
Nodes (107): Queryable, diverBodyWeight, lead, LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, renumber(), renumberStatement() (+99 more)

### Community 7 - "Community 7"
Cohesion: 0.04
Nodes (104): ChangePassword(), SecuritySettings(), Sessions(), useDescribeAgent(), AdminSettings(), Invitations(), LinkToPassOn(), ProviderSiteData() (+96 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (98): CertificationView, DiverDetailsView, ExternalDiverView, FoundDiverView, ReadCodeView, ADR-0028, ADR-0043, ADR-0050 (+90 more)

### Community 9 - "Community 9"
Cohesion: 0.04
Nodes (98): replyCertificationError, STATUS, CertificationService, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams (+90 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (80): createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities(), providerError() (+72 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (74): connection, cylinder, diveFingerprint, diveLease, importJob, ImportOutcome, providerConflict, push (+66 more)

### Community 12 - "Community 12"
Cohesion: 0.04
Nodes (93): i18next@^26.4.2, divesQuery(), pushRunActive(), pushRunBody(), PushRunChoices, PushRunPreview, pushRunPreviewQuery(), pushRunQuery() (+85 more)

### Community 13 - "Community 13"
Cohesion: 0.06
Nodes (85): Me, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery(), candidatesQuery(), deletedDivesQuery(), deletedDivesTotal(), DiveSummary (+77 more)

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (68): PROBLEMS, Assessment, FindingView, Row, ADR-0036, upload(), Hint, SuggestionView (+60 more)

### Community 15 - "Community 15"
Cohesion: 0.02
Nodes (86): actorType, aiAccessOutcome, backupRunKind, backupRunStatus, candidateResolution, centreSource, CERTIFICATION_IMAGE_STATES, CERTIFICATION_VERIFICATIONS (+78 more)

### Community 16 - "Community 16"
Cohesion: 0.04
Nodes (75): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+67 more)

### Community 17 - "Community 17"
Cohesion: 0.04
Nodes (68): sessionIdOf(), invitation, userRole, AttemptLimit, clientKey(), limitAttempts(), ADR-0061, Problem (+60 more)

### Community 18 - "Community 18"
Cohesion: 0.04
Nodes (79): DUE_STATES, AiAccessScope, seesPositions(), VerifiedAccess, diversOf(), SiteRow, checkedByRunTool, createMcpServer() (+71 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (75): SUIT_TYPES, ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, buddiesOf(), BuddySearch (+67 more)

### Community 20 - "Community 20"
Cohesion: 0.05
Nodes (58): ImportArea, errorMessage(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy() (+50 more)

### Community 21 - "Community 21"
Cohesion: 0.03
Nodes (67): buddySuggestion, jointDive, parseHint(), Accepted, Candidate, Check, DiveView, Outcome (+59 more)

### Community 22 - "Community 22"
Cohesion: 0.06
Nodes (68): diveAssessment, logbookCheckAnswer, logbookCheckDiveAnswer, agreeInDepthAndDuration(), CheckedDive, DeletableDive, findChecks(), followsSoon() (+60 more)

### Community 23 - "Community 23"
Cohesion: 0.06
Nodes (63): dataIdsOf(), settleSiteVisibility(), outgoingSite(), findOrMake(), SiteForSend, siteForSend(), ADR-0057, createSitePickService() (+55 more)

### Community 24 - "Community 24"
Cohesion: 0.05
Nodes (65): importsQuery(), isUnauthorized(), App(), BackupsPage, CentrePage, CentresPage, currentRoute(), DivePage (+57 more)

### Community 25 - "Community 25"
Cohesion: 0.04
Nodes (65): pushRunItem, PushRunLeftOut, asksFor(), DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN (+57 more)

### Community 26 - "Community 26"
Cohesion: 0.04
Nodes (50): ParticipantRole, ActionContext, Direction, DiveExport, DiveListing, FIND_BY, FindBy, Operation (+42 more)

### Community 27 - "Community 27"
Cohesion: 0.06
Nodes (61): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, ambientPressureBar() (+53 more)

### Community 28 - "Community 28"
Cohesion: 0.05
Nodes (51): AssessmentError, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings(), requireManagedDive() (+43 more)

### Community 29 - "Community 29"
Cohesion: 0.05
Nodes (55): diverExternalId, diveSiteExternalId, createSsiClient(), Card, DEEP, OWD, Report, Summary (+47 more)

### Community 30 - "Community 30"
Cohesion: 0.06
Nodes (63): ConnectionRow, WaitingPushOptions, createImport(), startImport(), audienceOfConnection(), audienceOfDive(), audienceOfDiver(), emitHint() (+55 more)

### Community 31 - "Community 31"
Cohesion: 0.05
Nodes (59): CylinderMaterial, SuitType, SuitUndergarment, WeightingFelt, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, Suit (+51 more)

### Community 32 - "Community 32"
Cohesion: 0.06
Nodes (62): ComputerChoice, DiveImportMode, DiveKind, importOriginal, UtcOffsetSource, siteRef(), PlacedTime, FileFormat (+54 more)

### Community 33 - "Community 33"
Cohesion: 0.05
Nodes (57): ExternalIdView, Position, running(), SiteImportArea, siteImportsQuery(), SiteImportView, siteRevisionsQuery(), SiteRevisionView (+49 more)

### Community 34 - "Community 34"
Cohesion: 0.06
Nodes (61): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+53 more)

### Community 35 - "Community 35"
Cohesion: 0.06
Nodes (42): openArchive(), openArchiveWith(), createDb(), findChangedMigrations(), migrateDatabase(), ADR-0004, createScratchDatabase(), execFileAsync (+34 more)

### Community 36 - "Community 36"
Cohesion: 0.07
Nodes (58): waitingOf(), close(), currentRemote(), PushRow, ADR-0024, Choice, isExporting(), referenceOf() (+50 more)

### Community 37 - "Community 37"
Cohesion: 0.06
Nodes (56): EMPTY_SITE_IMPORT_COUNTS, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, ProviderImportPlan (+48 more)

### Community 38 - "Community 38"
Cohesion: 0.07
Nodes (49): sacOfDive(), BottomTimeQuery, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033, ADR-0071 (+41 more)

### Community 39 - "Community 39"
Cohesion: 0.07
Nodes (59): sampleSeries, tieToJoined(), CylinderError, cylindersOf(), fillCylindersFromRecording(), mergeProviderCylinder(), providerCylinderOf(), sameSeries() (+51 more)

### Community 40 - "Community 40"
Cohesion: 0.07
Nodes (53): accountListEntry, providerLease, tidyName(), foldName(), nameDiffers(), Buddy, createBuddyService(), createFrom() (+45 more)

### Community 41 - "Community 41"
Cohesion: 0.07
Nodes (52): SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), buildUpdateRecord(), DiveForSsi, emptyRecord(), keysOf() (+44 more)

### Community 42 - "Community 42"
Cohesion: 0.04
Nodes (48): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError (+40 more)

### Community 43 - "Community 43"
Cohesion: 0.09
Nodes (54): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+46 more)

### Community 44 - "Community 44"
Cohesion: 0.05
Nodes (51): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, cancelWaitingImports() (+43 more)

### Community 45 - "Community 45"
Cohesion: 0.12
Nodes (49): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, assessmentRoutes(), authHandler() (+41 more)

### Community 46 - "Community 46"
Cohesion: 0.07
Nodes (47): Delivered, ProviderError, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf() (+39 more)

### Community 47 - "Community 47"
Cohesion: 0.07
Nodes (47): NoFly, noFlyAfter(), getDiverLoadings, DEFAULT_GF_HIGH, residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows() (+39 more)

### Community 48 - "Community 48"
Cohesion: 0.08
Nodes (49): BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32Encode(), bech32HrpExpand(), bech32Polymod() (+41 more)

### Community 49 - "Community 49"
Cohesion: 0.06
Nodes (34): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+26 more)

### Community 50 - "Community 50"
Cohesion: 0.06
Nodes (42): MATCHING_WINDOWS, ConflictService, DecisionRouteDeps, ConnectionRouteDeps, Direction, KeepSignedIn, KeyExpiresOn, ProviderView (+34 more)

### Community 51 - "Community 51"
Cohesion: 0.07
Nodes (44): Leave, asBool(), DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+36 more)

### Community 52 - "Community 52"
Cohesion: 0.07
Nodes (47): recordingEvent, RecordingSummary, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput (+39 more)

### Community 53 - "Community 53"
Cohesion: 0.06
Nodes (38): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+30 more)

### Community 54 - "Community 54"
Cohesion: 0.08
Nodes (41): participant, DiverError, ADR-0016, ADR-0053, ACCOUNT_PATTERN, accountKey(), createDiverService(), applyDetails() (+33 more)

### Community 55 - "Community 55"
Cohesion: 0.05
Nodes (36): applyHints(), LiveArrivals, Page, useLiveArrivals(), bump(), changedListeners, ChannelMessage, healthListeners (+28 more)

### Community 56 - "Community 56"
Cohesion: 0.05
Nodes (38): CYLINDER_MATERIALS, SUIT_UNDERGARMENTS, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053, replyDiverError (+30 more)

### Community 57 - "Community 57"
Cohesion: 0.06
Nodes (39): ADR-0025, WaterMismatch, waterMismatch(), computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit() (+31 more)

### Community 58 - "Community 58"
Cohesion: 0.06
Nodes (36): sampleCount(), SSI_CAPABILITIES, SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText() (+28 more)

### Community 59 - "Community 59"
Cohesion: 0.05
Nodes (42): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+34 more)

### Community 60 - "Community 60"
Cohesion: 0.07
Nodes (26): AuthOptions, auth, BackupServiceDeps, ServiceParts, Db, ProviderLog, out, services (+18 more)

### Community 61 - "Community 61"
Cohesion: 0.08
Nodes (41): ProviderCylinder, ConflictEntry, ConflictField, createConflictService(), loadWaiting(), stillDiffering(), fieldsOf(), isExists() (+33 more)

### Community 62 - "Community 62"
Cohesion: 0.06
Nodes (41): Change(), Changes(), historyRoute, Made(), REVISION_CAUSES, PositionSchema, AdoptBody, Country (+33 more)

### Community 63 - "Community 63"
Cohesion: 0.09
Nodes (41): devicesQuery(), DiveGearItemView, diveGearQuery(), DiveGearView, EquipmentCategory, EquipmentDetailView, EquipmentDueView, equipmentItemQuery() (+33 more)

### Community 64 - "Community 64"
Cohesion: 0.05
Nodes (42): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+34 more)

### Community 65 - "Community 65"
Cohesion: 0.07
Nodes (39): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+31 more)

### Community 66 - "Community 66"
Cohesion: 0.07
Nodes (35): AssessmentService, AssessmentView, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView() (+27 more)

### Community 67 - "Community 67"
Cohesion: 0.07
Nodes (33): CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043, CreateBody (+25 more)

### Community 68 - "Community 68"
Cohesion: 0.09
Nodes (33): createServices(), Services, ADR-0027, ADR-0030, ADR-0061, ADR-0063, ADR-0066, ADR-0070 (+25 more)

### Community 69 - "Community 69"
Cohesion: 0.06
Nodes (35): adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, SessionUser, ADR-0011, ADR-0012 (+27 more)

### Community 70 - "Community 70"
Cohesion: 0.08
Nodes (31): ImportErrorCode, ImportFound, ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS, eachFile(), looksLikeZip() (+23 more)

### Community 71 - "Community 71"
Cohesion: 0.10
Nodes (32): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0051 (+24 more)

### Community 72 - "Community 72"
Cohesion: 0.11
Nodes (33): BACKUP_SKIPPED_DIRS, BackupDecryptionKey, encryptionKeyFingerprint(), openArchiveStreamingWith(), assertPgTool(), execFileAsync, pgCommand(), pgErrorDetail() (+25 more)

### Community 73 - "Community 73"
Cohesion: 0.10
Nodes (31): bar(), celsius(), contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf(), firstOf() (+23 more)

### Community 74 - "Community 74"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 75 - "Community 75"
Cohesion: 0.08
Nodes (36): cylinderOf(), cylinderText(), diverOf(), diverText(), finite(), kept, LEAD_MATERIALS, LEAD_WATERS (+28 more)

### Community 76 - "Community 76"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 77 - "Community 77"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 78 - "Community 78"
Cohesion: 0.07
Nodes (35): EquipmentService, RecordInput, ScheduleInput, DiveGearItemViewSchema, DiveGearViewSchema, DueState, EquipmentDetailViewSchema, EquipmentDueViewSchema (+27 more)

### Community 79 - "Community 79"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 80 - "Community 80"
Cohesion: 0.06
Nodes (28): account, accountRelations, apikey, rateLimit, sessionRelations, ADR-0011, ADR-0012, ADR-0035 (+20 more)

### Community 81 - "Community 81"
Cohesion: 0.13
Nodes (34): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+26 more)

### Community 82 - "Community 82"
Cohesion: 0.08
Nodes (31): AI_ACCESS_NAME_MAX, aiAccessLog, aiAccessSetting, Access, AccessView, AiAccessRouteDeps, LogEntryView, Scope (+23 more)

### Community 83 - "Community 83"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 84 - "Community 84"
Cohesion: 0.07
Nodes (27): Cylinder, DiveView, RevisionView, ADR-0045, upload(), Candidate, Check, Outcome (+19 more)

### Community 85 - "Community 85"
Cohesion: 0.09
Nodes (29): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+21 more)

### Community 86 - "Community 86"
Cohesion: 0.08
Nodes (23): Tx, AppendUndo, ChangeListener, ADR-0015, ADR-0016, ADR-0020, ADR-0021, ADR-0022 (+15 more)

### Community 87 - "Community 87"
Cohesion: 0.07
Nodes (25): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+17 more)

### Community 88 - "Community 88"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 89 - "Community 89"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 90 - "Community 90"
Cohesion: 0.09
Nodes (22): BOM, Json, num(), obj(), positive(), scaled(), suuntoFitKey(), SuuntoJsonAdapter (+14 more)

### Community 91 - "Community 91"
Cohesion: 0.15
Nodes (26): open(), createConnectionService(), checkAutomation(), checkFollowNumbers(), expiryOf(), onTurn(), ownConnection(), signInAt() (+18 more)

### Community 92 - "Community 92"
Cohesion: 0.11
Nodes (20): SsiClient, ClearChecks, NumberChecks, TwowayChecks, apneaDive(), DATASET_KEYS, datasetSummary(), findIn() (+12 more)

### Community 93 - "Community 93"
Cohesion: 0.10
Nodes (22): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearQueue(), conflictAtSsi() (+14 more)

### Community 94 - "Community 94"
Cohesion: 0.07
Nodes (28): dependencies, better-auth@1.7.7, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1 (+20 more)

### Community 95 - "Community 95"
Cohesion: 0.15
Nodes (25): DiveOrigin, REFERENCE_PREFIX, Series, SsiLogbook, aboveZero(), cylinderOf(), deviceOf(), deviceOfRef() (+17 more)

### Community 96 - "Community 96"
Cohesion: 0.13
Nodes (26): massIn(), Dive, emptyLead(), hasThickness(), kg(), LastWeighting, LEAD_PLACEMENTS, LeadDraft (+18 more)

### Community 97 - "Community 97"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 98 - "Community 98"
Cohesion: 0.11
Nodes (23): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+15 more)

### Community 99 - "Community 99"
Cohesion: 0.12
Nodes (21): APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), AppLockProbe, probeAppLock() (+13 more)

### Community 100 - "Community 100"
Cohesion: 0.13
Nodes (22): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DueInput, DueSchedule (+14 more)

### Community 101 - "Community 101"
Cohesion: 0.11
Nodes (20): createFileFormats(), createFitAdapter(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, looksLikeUddf(), ownersParts() (+12 more)

### Community 102 - "Community 102"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 103 - "Community 103"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 104 - "Community 104"
Cohesion: 0.12
Nodes (21): createServer(), createAuth(), hashPassword(), loadAuthSecret(), acquireAppLock(), workerDeps(), Config, loadConfig() (+13 more)

### Community 105 - "Community 105"
Cohesion: 0.12
Nodes (22): equipmentUse, DiveGear, GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow, ItemView (+14 more)

### Community 106 - "Community 106"
Cohesion: 0.08
Nodes (24): devDependencies, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2, @testing-library/react@^16.3.3 (+16 more)

### Community 107 - "Community 107"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 108 - "Community 108"
Cohesion: 0.11
Nodes (14): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+6 more)

### Community 109 - "Community 109"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 110 - "Community 110"
Cohesion: 0.15
Nodes (22): EquipmentError, checkCost(), checkDates(), checkWindow(), createEquipmentService(), assemble(), checkResets(), checkSchedule() (+14 more)

### Community 111 - "Community 111"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 112 - "Community 112"
Cohesion: 0.13
Nodes (17): providerGoneDive, compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051, candidatesOf() (+9 more)

### Community 113 - "Community 113"
Cohesion: 0.12
Nodes (19): SUGGESTION_STATUSES, DiveProfileView, DiveProfileViewSchema, ADR-0041, AcceptBody, STATUS, SuggestionRouteDeps, ADR-0070 (+11 more)

### Community 114 - "Community 114"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 115 - "Community 115"
Cohesion: 0.15
Nodes (18): diveSite, providerSiteData, applyDecision(), AUTO_LINK_M, linkNearbySite(), RecordingPositions, SiteRef, ADR-0020 (+10 more)

### Community 116 - "Community 116"
Cohesion: 0.13
Nodes (18): siteImport, siteImportStatus, PROBLEM_CODES, SiteImportRouteDeps, StartBody, STATUS, ADR-0021, SiteImportService (+10 more)

### Community 117 - "Community 117"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 118 - "Community 118"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 119 - "Community 119"
Cohesion: 0.11
Nodes (13): BACKUP_EXTENSION, BackupEncryption, BackupPart, BackupLog, BackupRunRow, BackupSettingsInput, BackupSettingsValues, DEFAULT_BACKUP_SETTINGS (+5 more)

### Community 120 - "Community 120"
Cohesion: 0.16
Nodes (16): inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), placeLocalTime(), Position, ADR-0030, wallClockMs() (+8 more)

### Community 121 - "Community 121"
Cohesion: 0.22
Nodes (16): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), appendRun(), browserStats() (+8 more)

### Community 122 - "Community 122"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 123 - "Community 123"
Cohesion: 0.16
Nodes (16): BACKUP_FORMAT_VERSION, buildManifest(), decryptFile(), decryptFileWith(), decryptStreamWith(), encryptFile(), fileTreeChecksum(), packContainer() (+8 more)

### Community 124 - "Community 124"
Cohesion: 0.17
Nodes (13): createUddfAdapter(), parseXml(), decimal(), dive(), makeOceanicPlusUddf(), OCEANIC_DIVES, OceanicDive, site() (+5 more)

### Community 125 - "Community 125"
Cohesion: 0.12
Nodes (16): ADR-0013, ADR-0026, ADR-0028, ADR-0030, ADR-0031, ADR-0034, ADR-0035, ADR-0036 (+8 more)

### Community 126 - "Community 126"
Cohesion: 0.15
Nodes (14): session, certification, createUserAdminService(), keepAnAdmin(), lock(), deleteUserData(), ADR-0013, ADR-0024 (+6 more)

### Community 127 - "Community 127"
Cohesion: 0.15
Nodes (13): AssessmentRefresh, ADR-0036, ADR-0053, CandidateRouteDeps, CandidateView, Decided, DiveBrief, OffsetSource (+5 more)

### Community 128 - "Community 128"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 129 - "Community 129"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 130 - "Community 130"
Cohesion: 0.16
Nodes (14): serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, DueEntry, ScheduleView, equipmentList(), EquipmentListFilter (+6 more)

### Community 131 - "Community 131"
Cohesion: 0.14
Nodes (7): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, headers, uniqueWord()

### Community 132 - "Community 132"
Cohesion: 0.18
Nodes (11): addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, relations(), TimelineBar, TimelineBarGeometry, TimelineGapGeometry, TimelineGeometry (+3 more)

### Community 133 - "Community 133"
Cohesion: 0.19
Nodes (10): execFileAsync, tarDirectory(), createBackupService(), checkSettings(), due(), enqueueScheduled(), readSettings(), saveSettings() (+2 more)

### Community 134 - "Community 134"
Cohesion: 0.36
Nodes (4): decode(), localName(), Reader, XmlError

### Community 135 - "Community 135"
Cohesion: 0.22
Nodes (11): fromOsm(), fromSsi(), fromWd(), HOUSE_REEF, osm(), site(), ssi(), ADR-0021 (+3 more)

### Community 136 - "Community 136"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 137 - "Community 137"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 138 - "Community 138"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 139 - "Community 139"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 140 - "Community 140"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 141 - "Community 141"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 142 - "Community 142"
Cohesion: 0.20
Nodes (8): Series, ChannelSeries, RecordedGas, Tank, TiedCylinder, ADR-0045, readings, ADR-0045

### Community 143 - "Community 143"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 144 - "Community 144"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 145 - "Community 145"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 146 - "Community 146"
Cohesion: 0.29
Nodes (8): LoadingsView, LOADING_SOURCES, loadingNumber(), loadingPercent(), LoadingValues, showsLoadings(), ADR-0071, none

### Community 147 - "Community 147"
Cohesion: 0.22
Nodes (6): browserDetector(), Detector, DetectorClass, Picture, ADR-0043, ADR-0043

### Community 148 - "Community 148"
Cohesion: 0.24
Nodes (9): draftOfLead(), draftOfWeighting(), fromLastDive(), lacks(), shown(), WeightingValues, logged, nothing (+1 more)

### Community 149 - "Community 149"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 150 - "Community 150"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 151 - "Community 151"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 153 - "Community 153"
Cohesion: 0.28
Nodes (5): Profile, SKETCH_HEIGHT, SKETCH_WIDTH, ADR-0041, ADR-0041

### Community 154 - "Community 154"
Cohesion: 0.25
Nodes (8): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, MODULE_IMPORTS, moduleRules, target()

### Community 155 - "Community 155"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 156 - "Community 156"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 157 - "Community 157"
Cohesion: 0.29
Nodes (4): initI18n(), resources, ADR-0014, ADR-0014

### Community 158 - "Community 158"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 159 - "Community 159"
Cohesion: 0.29
Nodes (6): label(), axe-core@^4.13.0, CompareSubject, facts, subjects, axe-core

### Community 160 - "Community 160"
Cohesion: 0.29
Nodes (6): itemIds(), ADR-0026, ADR-0034, Usage, UsageDive, UsageRow

### Community 161 - "Community 161"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 162 - "Community 162"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 163 - "Community 163"
Cohesion: 0.47
Nodes (4): nearest(), perMinute(), depth, temperature

### Community 164 - "Community 164"
Cohesion: 0.33
Nodes (3): ApiClient, createApiClient(), ADR-0004

### Community 165 - "Community 165"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 166 - "Community 166"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 167 - "Community 167"
Cohesion: 0.67
Nodes (3): planSketch(), shape(), ADR-0033

### Community 168 - "Community 168"
Cohesion: 0.50
Nodes (4): ToolsParams, SuitType, SuitUndergarment, ListControl

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
- **1179 isolated node(s):** `ADR-0031`, `ADR-0043`, `ADR-0050`, `ADR-0024`, `ADR-0028` (+1174 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3010 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **7 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 2` to `Community 130`, `Community 4`, `Community 6`, `Community 10`, `Community 11`, `Community 14`, `Community 15`, `Community 17`, `Community 18`, `Community 19`, `Community 21`, `Community 22`, `Community 23`, `Community 25`, `Community 28`, `Community 29`, `Community 30`, `Community 160`, `Community 32`, `Community 35`, `Community 36`, `Community 37`, `Community 38`, `Community 39`, `Community 40`, `Community 42`, `Community 45`, `Community 47`, `Community 50`, `Community 52`, `Community 54`, `Community 60`, `Community 61`, `Community 68`, `Community 70`, `Community 80`, `Community 82`, `Community 84`, `Community 85`, `Community 89`, `Community 94`, `Community 105`, `Community 112`, `Community 115`, `Community 119`, `Community 120`, `Community 126`?**
  _High betweenness centrality (0.035) - this node is a cross-community bridge._
- **What connects `ADR-0031`, `ADR-0043`, `ADR-0050` to the rest of the system?**
  _1179 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.03852859135285914 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 7` to `Community 0`, `Community 1`, `Community 33`, `Community 3`, `Community 5`, `Community 8`, `Community 43`, `Community 12`, `Community 13`, `Community 55`, `Community 24`, `Community 63`?**
  _High betweenness centrality (0.025) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.029184207532142452 - nodes in this community are weakly interconnected._
- **Why does `Button()` connect `Community 0` to `Community 96`, `Community 1`, `Community 33`, `Community 3`, `Community 5`, `Community 7`, `Community 8`, `Community 43`, `Community 12`, `Community 13`, `Community 24`, `Community 63`?**
  _High betweenness centrality (0.021) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.04466046773739082 - nodes in this community are weakly interconnected._