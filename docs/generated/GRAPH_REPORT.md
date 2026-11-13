# Graph Report - dive_hub  (2026-11-13)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 7024 nodes · 21903 edges · 177 communities (174 shown, 3 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 221 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `3f39cb98`
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
1. `Notice()` - 200 edges
2. `unwrap()` - 196 edges
3. `Button()` - 194 edges
4. `useErrorText()` - 165 edges
5. `useDisplay()` - 153 edges
6. `Muted()` - 135 edges
7. `drizzle-orm` - 129 edges
8. `announce()` - 105 edges
9. `createTestDatabase()` - 97 edges
10. `TestDatabase` - 96 edges

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

## Communities (177 total, 3 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.03
Nodes (150): account, migrateDatabase(), ADR-0004, buddySuggestion, ADR-0013, ADR-0011, dumpToolAvailable(), execFileAsync (+142 more)

### Community 1 - "Community 1"
Cohesion: 0.05
Nodes (169): api, bodyWeightsQuery(), buddiesForQuery(), centreQuery(), centresQuery(), CentreView, certificationsQuery(), CertificationView (+161 more)

### Community 2 - "Community 2"
Cohesion: 0.02
Nodes (112): AuthOptions, createServices(), ServiceParts, Services, ADR-0027, ADR-0030, ADR-0061, ADR-0063 (+104 more)

### Community 3 - "Community 3"
Cohesion: 0.05
Nodes (112): ChangePassword(), DisplaySettings(), SecuritySettings(), Sessions(), useDescribeAgent(), AdminSettings(), DeleteUserDialog(), Invitations() (+104 more)

### Community 4 - "Community 4"
Cohesion: 0.04
Nodes (112): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+104 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (106): buddiesQuery(), BuddyView, ConflictChoices, ConflictFieldView, ConnectionView, DiveImportMode, DiveImportPreview, diveImportPreviewQuery() (+98 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (108): AiAccessLogEntry, AiAccessView, AnyLogbookCheck, CandidateView, ContinuationCandidateView, DETAILS, DiveCheckView, DiveGearItemView (+100 more)

### Community 7 - "Community 7"
Cohesion: 0.04
Nodes (96): divesQuery(), isUnauthorized(), LogbookPage, PAGE_SIZE, ProblemCode, setupQuery(), App(), BackupsPage (+88 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (79): connection, diveLease, importJob, ImportOutcome, providerConflict, providerLease, push, pushRun (+71 more)

### Community 9 - "Community 9"
Cohesion: 0.02
Nodes (92): actorType, aiAccessOutcome, backupRun, backupRunKind, backupRunStatus, backupSettings, candidateResolution, centreSource (+84 more)

### Community 10 - "Community 10"
Cohesion: 0.04
Nodes (73): equipmentDueQuery(), EquipmentDueView, Me, cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState (+65 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (71): createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities(), providerError() (+63 more)

### Community 12 - "Community 12"
Cohesion: 0.04
Nodes (75): hashPassword(), invitation, passwordReset, userRole, AttemptLimit, clientKey(), limitAttempts(), ADR-0061 (+67 more)

### Community 13 - "Community 13"
Cohesion: 0.05
Nodes (81): CertificationService, DIVE_IMPORT_MODES, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053, ErrorAnswer, ProblemCode (+73 more)

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (81): CYLINDER_MATERIALS, PARTICIPANT_ROLES, ADR-0053, ADR-0056, WaitingPushViewSchema, storedProblemCode(), Change(), Changes() (+73 more)

### Community 15 - "Community 15"
Cohesion: 0.05
Nodes (81): LeadPlacement, OVERRIDABLE_FIELDS, OverridableField, renumber(), renumberStatement(), ADR-0048, accountKey(), createDiverService() (+73 more)

### Community 16 - "Community 16"
Cohesion: 0.07
Nodes (76): candidatesQuery(), ConflictEntryView, ConflictsOfConnection, diversQuery(), GoneDivesOfConnection, goneDivesQuery(), GoneDiveView, isDiveCheck() (+68 more)

### Community 17 - "Community 17"
Cohesion: 0.05
Nodes (74): BACKUP_FORMAT_VERSION, BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32Encode(), bech32HrpExpand() (+66 more)

### Community 18 - "Community 18"
Cohesion: 0.05
Nodes (78): diveSite, ParticipantRole, providerSiteData, managedDiverIds(), mergeProviderCylinder(), providerCylinderOf(), participantsOf(), columnsOf() (+70 more)

### Community 19 - "Community 19"
Cohesion: 0.05
Nodes (77): ComputerChoice, DiveImportMode, DiveKind, UtcOffsetSource, ProviderCylinder, siteRef(), entryMatches(), PlacedTime (+69 more)

### Community 20 - "Community 20"
Cohesion: 0.05
Nodes (58): diveSiteExternalId, createSsiClient(), Sent, View, Card, DEEP, OWD, Report (+50 more)

### Community 21 - "Community 21"
Cohesion: 0.03
Nodes (54): ProviderImportPlan, Direction, DiveExport, DiveExportAction, DiveListing, DiveOrigin, FIND_BY, FindBy (+46 more)

### Community 22 - "Community 22"
Cohesion: 0.04
Nodes (74): LEAD_PLACEMENTS, WEIGHTING_FELT, sacOfDive(), ADR-0025, WaterMismatch, waterMismatch(), withoutPressures(), Copies (+66 more)

### Community 23 - "Community 23"
Cohesion: 0.07
Nodes (72): allButDive(), ApiError, continuationCandidateQuery(), deletedDivesQuery(), DeletedDiveView, diveProvidersQuery(), diveQuery(), DiveView (+64 more)

### Community 24 - "Community 24"
Cohesion: 0.05
Nodes (71): assessmentQuery(), devicesQuery(), DeviceView, diveNeighboursQuery(), DiveValues, NeighboursView, OverridableField, RecordingSummary (+63 more)

### Community 25 - "Community 25"
Cohesion: 0.04
Nodes (65): ExternalIdView, Position, siteRevisionsQuery(), SiteRevisionView, useAddressSearch(), ADR-0014, ADR-0020, ADR-0027 (+57 more)

### Community 26 - "Community 26"
Cohesion: 0.05
Nodes (68): equipmentItem, serviceRecord, serviceRecordSchedule, serviceSchedule, requireManagedDiver(), managementOf(), ScheduleDue, EquipmentError (+60 more)

### Community 27 - "Community 27"
Cohesion: 0.04
Nodes (63): pushRunItem, PushRunLeftOut, DIVE_ONLY, DIVE_ONLY_PATTERN, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN (+55 more)

### Community 28 - "Community 28"
Cohesion: 0.05
Nodes (64): CandidateError, createCandidateService(), lockMine(), rebuildJoined(), reviseDive(), attachRecording(), createDiveFromRecording(), DiveError (+56 more)

### Community 29 - "Community 29"
Cohesion: 0.04
Nodes (68): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+60 more)

### Community 30 - "Community 30"
Cohesion: 0.05
Nodes (50): AppLockProbe, BACKUP_EXTENSION, BACKUP_SKIPPED_DIRS, BackupDecryptionKey, BackupEncryption, BackupManifest, BackupPart, BackupLog (+42 more)

### Community 31 - "Community 31"
Cohesion: 0.05
Nodes (59): CylinderMaterial, SuitType, SuitUndergarment, WeightingFelt, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, Suit (+51 more)

### Community 32 - "Community 32"
Cohesion: 0.06
Nodes (60): AiAccessScope, seesPositions(), diversOf(), SiteRow, checkedByRunTool, createMcpServer(), INSTRUCTIONS, MCP_SERVER_INFO (+52 more)

### Community 33 - "Community 33"
Cohesion: 0.05
Nodes (46): createServer(), createAuth(), loadAuthSecret(), acquireAppLock(), workerDeps(), Config, loadConfig(), ADR-0011 (+38 more)

### Community 34 - "Community 34"
Cohesion: 0.04
Nodes (56): AssessmentService, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView(), ADR-0036 (+48 more)

### Community 35 - "Community 35"
Cohesion: 0.06
Nodes (55): accountListEntry, diver, tidyName(), DiverDetails, Caller, foldName(), nameDiffers(), Buddy (+47 more)

### Community 36 - "Community 36"
Cohesion: 0.06
Nodes (52): Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), HINT, Input, NOTICE (+44 more)

### Community 37 - "Community 37"
Cohesion: 0.07
Nodes (53): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ambientPressureBar(), bestMixPercent() (+45 more)

### Community 38 - "Community 38"
Cohesion: 0.06
Nodes (56): Leave, Series, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+48 more)

### Community 39 - "Community 39"
Cohesion: 0.10
Nodes (56): buildApp(), assessmentRoutes(), AI_ACCESS_NAME_MAX, SessionUser, authHandler(), authHeaders(), fastify, FastifyRequest (+48 more)

### Community 40 - "Community 40"
Cohesion: 0.05
Nodes (54): continuationAppend, recordingEvent, RecordingSummary, sampleSeries, AppendRow, channelsOf(), cylindersBack(), DiveRow (+46 more)

### Community 41 - "Community 41"
Cohesion: 0.05
Nodes (47): ImportErrorCode, ImportFound, importOriginal, ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS, eachFile() (+39 more)

### Community 42 - "Community 42"
Cohesion: 0.06
Nodes (47): UTC_OFFSET_SOURCES, downsampleMinMax(), AppendDeps, AppendOutcome, AppendService, Side, ADR-0038, ADR-0063 (+39 more)

### Community 43 - "Community 43"
Cohesion: 0.06
Nodes (51): DIVE_FIELDS, ProviderError, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf() (+43 more)

### Community 44 - "Community 44"
Cohesion: 0.05
Nodes (44): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+36 more)

### Community 45 - "Community 45"
Cohesion: 0.05
Nodes (51): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, cancelWaitingImports() (+43 more)

### Community 46 - "Community 46"
Cohesion: 0.11
Nodes (49): withBuddies(), APP_KEYS, appRow(), BuddySaved, byId(), changedKeys(), deleteTestDive(), deleteTestEntries() (+41 more)

### Community 47 - "Community 47"
Cohesion: 0.09
Nodes (48): Queryable, logbookCheckAnswer, logbookCheckDiveAnswer, keepApart(), keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration() (+40 more)

### Community 48 - "Community 48"
Cohesion: 0.06
Nodes (35): APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), probeAppLock(), encryptFile() (+27 more)

### Community 49 - "Community 49"
Cohesion: 0.08
Nodes (48): LeadEstimate, LEAD_MATERIALS, LEAD_WATERS, massFromDisplay(), massIn(), Dive, draftOfLead(), draftOfWeighting() (+40 more)

### Community 50 - "Community 50"
Cohesion: 0.05
Nodes (43): CENTRE_SOURCES, CentreService, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+35 more)

### Community 51 - "Community 51"
Cohesion: 0.09
Nodes (49): ConnectionRow, WaitingPushOptions, waitingOf(), audienceOfConnection(), currentRemote(), createProviderLayer(), remoteState(), statusAt() (+41 more)

### Community 52 - "Community 52"
Cohesion: 0.07
Nodes (34): dive, duplicateCandidate, original, recording, ImportService, createLocalBlobStore(), ADR-0044, Neighbours (+26 more)

### Community 53 - "Community 53"
Cohesion: 0.08
Nodes (41): AssessmentView, samplesQuery(), AssessmentState, FindingLane(), useAssessment(), useWhen(), DepthProfile(), NO_POINTS (+33 more)

### Community 54 - "Community 54"
Cohesion: 0.08
Nodes (37): SsiClient, buildCreateRecord(), buildDeleteRecord(), emptyRecord(), writableRecord(), ClearChecks, clearDive(), findIn() (+29 more)

### Community 55 - "Community 55"
Cohesion: 0.07
Nodes (43): LeadHint(), orUndefined(), ADR-0025, ADR-0031, CylinderDraft, CylinderMaterial, BOTTOM_TIME_RULES, cylinderOf() (+35 more)

### Community 56 - "Community 56"
Cohesion: 0.06
Nodes (36): sampleCount(), SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject() (+28 more)

### Community 57 - "Community 57"
Cohesion: 0.09
Nodes (39): cylinderCatalogueQuery(), CylinderFields(), useCatalogueName(), useCylinderProblem(), DiveEditForm(), Draft, draftOf(), FIELD_OF (+31 more)

### Community 58 - "Community 58"
Cohesion: 0.07
Nodes (38): PushLeftOut, close(), PushRow, ADR-0024, Choice, isExporting(), referenceOf(), exchange() (+30 more)

### Community 59 - "Community 59"
Cohesion: 0.06
Nodes (40): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, EquipmentService, RecordInput, ScheduleInput (+32 more)

### Community 60 - "Community 60"
Cohesion: 0.05
Nodes (39): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+31 more)

### Community 61 - "Community 61"
Cohesion: 0.05
Nodes (42): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+34 more)

### Community 62 - "Community 62"
Cohesion: 0.11
Nodes (33): diverExternalId, settleSiteVisibility(), keyLease(), findOrMake(), SiteForSend, siteForSend(), ADR-0057, createSitePickService() (+25 more)

### Community 63 - "Community 63"
Cohesion: 0.08
Nodes (23): auth, BackupServiceDeps, out, services, createSecretBox(), ADR-0024, BlobStore, ADR-0004 (+15 more)

### Community 64 - "Community 64"
Cohesion: 0.13
Nodes (36): open(), createConnectionService(), checkAutomation(), checkFollowNumbers(), expiryOf(), onTurn(), ownConnection(), signInAt() (+28 more)

### Community 65 - "Community 65"
Cohesion: 0.08
Nodes (25): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0040, ADR-0042 (+17 more)

### Community 66 - "Community 66"
Cohesion: 0.08
Nodes (33): SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, Context, distanceM(), Field (+25 more)

### Community 67 - "Community 67"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 68 - "Community 68"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 69 - "Community 69"
Cohesion: 0.13
Nodes (34): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+26 more)

### Community 70 - "Community 70"
Cohesion: 0.12
Nodes (33): importsQuery(), ImportView, noAnswerError(), uploadFile(), Logbook(), ImportContext, ImportFilesButton(), ImportHistory() (+25 more)

### Community 71 - "Community 71"
Cohesion: 0.06
Nodes (35): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+27 more)

### Community 72 - "Community 72"
Cohesion: 0.12
Nodes (26): bar(), celsius(), contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf(), firstOf() (+18 more)

### Community 73 - "Community 73"
Cohesion: 0.07
Nodes (25): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+17 more)

### Community 74 - "Community 74"
Cohesion: 0.11
Nodes (25): ADR-0051, Status, ADR-0056, clearPushRuns(), clearQueue(), conflictAtSsi(), connectSsi(), deletableDive() (+17 more)

### Community 75 - "Community 75"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 76 - "Community 76"
Cohesion: 0.10
Nodes (27): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+19 more)

### Community 77 - "Community 77"
Cohesion: 0.13
Nodes (28): comparableOf(), hasTolerance(), isEmpty(), near(), planSend(), sameAtProvider(), sameCylinder(), text() (+20 more)

### Community 78 - "Community 78"
Cohesion: 0.07
Nodes (21): CustomTypeOptions, i18next, initI18n(), resources, ADR-0014, StrayOverride, ADR-0014, english (+13 more)

### Community 79 - "Community 79"
Cohesion: 0.09
Nodes (27): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+19 more)

### Community 80 - "Community 80"
Cohesion: 0.09
Nodes (27): @modelcontextprotocol/server@^2.3.1, aiAccessLog, aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, AiAccessService, KeyRow (+19 more)

### Community 81 - "Community 81"
Cohesion: 0.11
Nodes (24): ImportArea, DEFAULT_OVERPASS_URL, Element, overpassQuery(), parseMaxDepth(), parseOverpass(), text(), ADR-0021 (+16 more)

### Community 82 - "Community 82"
Cohesion: 0.08
Nodes (22): SiteSourceAdapter, SourceSite, call(), createSite(), Method, openStream(), Stream, ADR-0066 (+14 more)

### Community 83 - "Community 83"
Cohesion: 0.09
Nodes (22): headers, ADR-0031, headers, ADR-0043, ADR-0016, created, headers, importFixture() (+14 more)

### Community 84 - "Community 84"
Cohesion: 0.14
Nodes (29): bottomTimeRule(), defined(), leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas, toolsGasQuery(), toolsPlanQuery() (+21 more)

### Community 85 - "Community 85"
Cohesion: 0.10
Nodes (25): EVIDENCE, Evidence, FindingValues, RULE_IDS, RuleId, RULES, SEVERITIES, Severity (+17 more)

### Community 86 - "Community 86"
Cohesion: 0.09
Nodes (22): BOM, Json, num(), obj(), positive(), scaled(), suuntoFitKey(), SuuntoJsonAdapter (+14 more)

### Community 87 - "Community 87"
Cohesion: 0.09
Nodes (23): ADR-0038, ADR-0048, falseStart(), findFalseStart(), keepFalseStart(), livedAt(), mergeablePair(), mergePair() (+15 more)

### Community 88 - "Community 88"
Cohesion: 0.09
Nodes (27): devDependencies, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2, @testing-library/react@^16.3.3 (+19 more)

### Community 89 - "Community 89"
Cohesion: 0.11
Nodes (24): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+16 more)

### Community 90 - "Community 90"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 91 - "Community 91"
Cohesion: 0.11
Nodes (21): BACKUP_RUN_TASK, backupTask(), ADR-0069, EXPIRE_WAITING_IMPORTS_TASK, PROCESS_IMPORT_TASK, SCHEDULED_FETCH_TASK, PUSH_RUN_STEP_TASK, WAITING_PUSHES_TASK (+13 more)

### Community 92 - "Community 92"
Cohesion: 0.14
Nodes (24): ImportedValues, SiteImportFinding, SiteImportProgress, ExistingSite, PlannedCreate, PlannedExternalId, PlannedUpdate, columnsOf() (+16 more)

### Community 93 - "Community 93"
Cohesion: 0.17
Nodes (24): wallClockMs(), SsiLogbook, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef(), MATERIAL_OF_TANK_TYPE (+16 more)

### Community 94 - "Community 94"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 95 - "Community 95"
Cohesion: 0.11
Nodes (21): createSsiSiteSource(), DEFAULT_SSI_SITES_URL, createSiteSources(), ADR-0021, ADR-0027, createOverpassSource(), createPoliteHttp(), Fetch (+13 more)

### Community 96 - "Community 96"
Cohesion: 0.14
Nodes (23): AscentBand, ascentBandsOf(), assessProfile(), ceiling(), ppo2(), Profile, PROFILE_RULES, ProfileInput (+15 more)

### Community 97 - "Community 97"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 98 - "Community 98"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 99 - "Community 99"
Cohesion: 0.13
Nodes (20): diveWith(), ADR-0028, ADR-0043, forget(), headers, ADR-0043, Site, ADR-0057 (+12 more)

### Community 100 - "Community 100"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 101 - "Community 101"
Cohesion: 0.15
Nodes (23): ToolsPlanDiver, PlanDiver, PlanLevel, ToolsRule, DEFAULT_CYLINDER, DiverResult(), DiverRows(), Editor() (+15 more)

### Community 102 - "Community 102"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 103 - "Community 103"
Cohesion: 0.09
Nodes (23): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+15 more)

### Community 104 - "Community 104"
Cohesion: 0.11
Nodes (16): ndl(), ProfileAssessment, Finding, LIMITS, dayNumber(), deepDays(), noFlyAfter(), SERIES_RULES (+8 more)

### Community 105 - "Community 105"
Cohesion: 0.10
Nodes (19): Assessment, FindingView, Row, ADR-0036, upload(), Candidate, Deleted, DiveView (+11 more)

### Community 106 - "Community 106"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 107 - "Community 107"
Cohesion: 0.13
Nodes (21): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+13 more)

### Community 108 - "Community 108"
Cohesion: 0.16
Nodes (18): durationFormat(), DurationFormatter, formatDuration(), formatGasRate(), formatMass(), formatPressure(), formatPressureRate(), formatTemperature() (+10 more)

### Community 109 - "Community 109"
Cohesion: 0.10
Nodes (18): accountRelations, apikey, rateLimit, session, sessionRelations, ADR-0011, ADR-0035, user (+10 more)

### Community 110 - "Community 110"
Cohesion: 0.13
Nodes (17): providerGoneDive, compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051, candidatesOf() (+9 more)

### Community 111 - "Community 111"
Cohesion: 0.13
Nodes (18): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+10 more)

### Community 112 - "Community 112"
Cohesion: 0.10
Nodes (16): createScheduledFetch(), blocked(), due(), minimumOf(), ProviderLayer, ADR-0027, ADR-0029, ADR-0030 (+8 more)

### Community 113 - "Community 113"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 114 - "Community 114"
Cohesion: 0.14
Nodes (16): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), fit (+8 more)

### Community 115 - "Community 115"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 116 - "Community 116"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 117 - "Community 117"
Cohesion: 0.11
Nodes (19): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+11 more)

### Community 118 - "Community 118"
Cohesion: 0.13
Nodes (17): siteImport, siteImportStatus, PROBLEM_CODES, SiteImportRouteDeps, StartBody, STATUS, ADR-0021, SiteImportService (+9 more)

### Community 119 - "Community 119"
Cohesion: 0.15
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 120 - "Community 120"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 121 - "Community 121"
Cohesion: 0.13
Nodes (15): CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationExpiry, CertificationInput, CertificationView, EXPIRING_SOON_DAYS, ImportReport, ImportResult (+7 more)

### Community 122 - "Community 122"
Cohesion: 0.18
Nodes (14): siteSource, looksLikeZip(), coordinate(), parseSsiSites(), sitesJson(), truthy(), ADR-0024, unavailable() (+6 more)

### Community 123 - "Community 123"
Cohesion: 0.15
Nodes (10): HINT_CHANNEL, createListener(), connect(), lost(), LiveListener, LiveLog, RETRY_MS, ADR-0066 (+2 more)

### Community 124 - "Community 124"
Cohesion: 0.15
Nodes (14): certification, PROBLEMS, createUserAdminService(), keepAnAdmin(), lock(), deleteUserData(), ADR-0013, ADR-0024 (+6 more)

### Community 125 - "Community 125"
Cohesion: 0.14
Nodes (14): SUIT_TYPES, SUIT_UNDERGARMENTS, replyDiverError, STATUS, LeadService, Amount, EstimateQuery, EstimateView (+6 more)

### Community 126 - "Community 126"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 127 - "Community 127"
Cohesion: 0.25
Nodes (14): uplot@^1.6.32, ToolsPlan, DepthPlot(), LINE_DASHES, plotFont(), PressureStrip(), RIGHT_AXIS, token() (+6 more)

### Community 128 - "Community 128"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 129 - "Community 129"
Cohesion: 0.16
Nodes (11): replyCertificationError, STATUS, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, CertificationRouteDeps (+3 more)

### Community 130 - "Community 130"
Cohesion: 0.17
Nodes (12): SUGGESTION_STATUSES, SuggestionRouteDeps, ADR-0070, SuggestionError, SuggestionService, DiverRef, SiteRef, Status (+4 more)

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
Cohesion: 0.22
Nodes (10): useFocusBackWhenClosed(), holdLeave(), mayLeave(), useLeaveGuard(), revoke(), NoteForm(), SITES, Sites() (+2 more)

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
Cohesion: 0.21
Nodes (9): createFitAdapter(), adapter, crossCheck(), decodeOfficial(), privateDir, ownersParts(), parse(), toPart() (+1 more)

### Community 139 - "Community 139"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 140 - "Community 140"
Cohesion: 0.26
Nodes (7): DiveSummary, Profile, SKETCH_HEIGHT, SKETCH_WIDTH, sketchPaths(), ADR-0041, ADR-0041

### Community 141 - "Community 141"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 142 - "Community 142"
Cohesion: 0.25
Nodes (11): ascentRate(), descentRate(), judgedParts(), lastMetres(), safetyStop(), stopIn(), stopStability(), worstOf() (+3 more)

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
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 147 - "Community 147"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 148 - "Community 148"
Cohesion: 0.27
Nodes (8): CertificationError, createCertificationService(), cleaned(), requireManagedDiver(), rowOrThrow(), expiryOf(), isCalendarDate(), isoDay()

### Community 149 - "Community 149"
Cohesion: 0.27
Nodes (7): DIVE_KINDS, ImportView, ImportViewSchema, ADR-0030, ImportRouteDeps, ADR-0044, ADR-0056

### Community 151 - "Community 151"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 152 - "Community 152"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 153 - "Community 153"
Cohesion: 0.20
Nodes (10): verify, major, minor, sha, tag, version, name, outputs (+2 more)

### Community 154 - "Community 154"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 155 - "Community 155"
Cohesion: 0.28
Nodes (7): ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, s(), ADR-0035

### Community 156 - "Community 156"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 157 - "Community 157"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 158 - "Community 158"
Cohesion: 0.25
Nodes (7): Hint, HINT_KINDS, HintAudience, HintKind, kinds, ADR-0020, ADR-0066

### Community 159 - "Community 159"
Cohesion: 0.29
Nodes (5): parseHint(), createHub(), HEARTBEAT_MS, MAX_STREAMS_PER_USER, ADR-0066

### Community 160 - "Community 160"
Cohesion: 0.25
Nodes (7): AIR, expectWithinReference(), GF100_FRESH, GF100_SALT, GF85_FRESH, ADR-0032, ADR-0046

### Community 161 - "Community 161"
Cohesion: 0.36
Nodes (6): headers, open(), ownId(), ownRow(), sectionButton(), ADR-0050

### Community 162 - "Community 162"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 163 - "Community 163"
Cohesion: 0.29
Nodes (7): ADR-0023, ADR-0053, ADR-0066, ADR-0070, MODULE_IMPORTS, moduleRules, target()

### Community 164 - "Community 164"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 165 - "Community 165"
Cohesion: 0.33
Nodes (4): pollUntilDue(), ADR-0056, now, ADR-0056

### Community 166 - "Community 166"
Cohesion: 0.40
Nodes (5): stubDive(), ADR-0015, ADR-0023, ADR-0025, guardedRoute()

### Community 167 - "Community 167"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 168 - "Community 168"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 169 - "Community 169"
Cohesion: 0.33
Nodes (3): ApiClient, createApiClient(), ADR-0004

### Community 170 - "Community 170"
Cohesion: 0.40
Nodes (4): CertificationSummary, NO_CERTIFICATIONS, ADR-0050, ADR-0053

### Community 171 - "Community 171"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 172 - "Community 172"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 173 - "Community 173"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 174 - "Community 174"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

## Knowledge Gaps
- **1092 isolated node(s):** `ADR-0004`, `ADR-0013`, `ADR-0011`, `ADR-0069`, `ADR-0070` (+1087 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 2839 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **3 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 8` to `Community 0`, `Community 2`, `Community 9`, `Community 11`, `Community 12`, `Community 13`, `Community 14`, `Community 15`, `Community 18`, `Community 19`, `Community 20`, `Community 26`, `Community 27`, `Community 28`, `Community 29`, `Community 30`, `Community 158`, `Community 32`, `Community 33`, `Community 35`, `Community 36`, `Community 40`, `Community 41`, `Community 44`, `Community 47`, `Community 51`, `Community 52`, `Community 58`, `Community 59`, `Community 62`, `Community 63`, `Community 76`, `Community 79`, `Community 80`, `Community 82`, `Community 92`, `Community 103`, `Community 105`, `Community 109`, `Community 110`, `Community 121`, `Community 124`?**
  _High betweenness centrality (0.040) - this node is a cross-community bridge._
- **What connects `ADR-0004`, `ADR-0013`, `ADR-0011` to the rest of the system?**
  _1092 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.025734734277447845 - nodes in this community are weakly interconnected._
- **Why does `react` connect `Community 10` to `Community 1`, `Community 3`, `Community 5`, `Community 134`, `Community 7`, `Community 6`, `Community 140`, `Community 16`, `Community 23`, `Community 24`, `Community 25`, `Community 49`, `Community 53`, `Community 57`, `Community 70`, `Community 84`, `Community 88`, `Community 101`, `Community 107`, `Community 117`, `Community 127`?**
  _High betweenness centrality (0.026) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.046043003489812 - nodes in this community are weakly interconnected._
- **Why does `typescript` connect `Community 120` to `Community 88`, `Community 113`, `Community 79`?**
  _High betweenness centrality (0.019) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.023807274444969297 - nodes in this community are weakly interconnected._