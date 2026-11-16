# Graph Report - dive_hub  (2026-11-16)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 7223 nodes · 22530 edges · 190 communities (183 shown, 7 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 222 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `563e4c8b`
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
- Community 189

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 203 edges
2. `unwrap()` - 200 edges
3. `Button()` - 197 edges
4. `useErrorText()` - 168 edges
5. `useDisplay()` - 159 edges
6. `Muted()` - 137 edges
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
- `SsiLogbookSite` --references--> `SiteWaterType`  [EXTRACTED]
  apps/server/src/providers/ssi/ssi-client.ts → apps/server/src/vocabulary.ts
- `EquipmentListFilter` --references--> `EquipmentCategory`  [EXTRACTED]
  apps/server/src/equipment/read.ts → apps/server/src/vocabulary.ts

## Import Cycles
- None detected.

## Communities (190 total, 7 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.03
Nodes (178): bottomTimeRule(), cylinderCatalogueQuery(), defined(), leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas, toolsGasQuery() (+170 more)

### Community 1 - "Community 1"
Cohesion: 0.04
Nodes (137): user, diver, diverExternalId, diveSiteExternalId, revision, createSsiClient(), ADR-0013, ADR-0011 (+129 more)

### Community 2 - "Community 2"
Cohesion: 0.02
Nodes (164): diveNeighboursQuery(), diversQuery(), DiveValues, ExternalIdView, LogbookPage, OverridableField, PAGE_SIZE, revisionsQuery() (+156 more)

### Community 3 - "Community 3"
Cohesion: 0.05
Nodes (169): DisplaySettings(), DeleteUserDialog(), Invite(), LinkToPassOn(), SiteDataPanel(), UserRow(), AiAccessSetting(), CreateAccess() (+161 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (96): equipmentDueQuery(), EquipmentDueView, isUnauthorized(), running(), setupQuery(), SiteImportArea, siteImportsQuery(), SiteImportView (+88 more)

### Community 5 - "Community 5"
Cohesion: 0.02
Nodes (90): AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, BuddySuggestionView, BuddyView, ContinuationCandidateView, DETAILS (+82 more)

### Community 6 - "Community 6"
Cohesion: 0.05
Nodes (90): buddySuggestionsQuery(), candidatesQuery(), CandidateView, ConflictChoices, ConflictEntryView, ConflictFieldView, ConflictsOfConnection, deletedDivesQuery() (+82 more)

### Community 7 - "Community 7"
Cohesion: 0.04
Nodes (77): SessionUser, authHandler(), authHeaders(), fastify, FastifyRequest, forwardCookies(), publicPaths, sessionIdOf() (+69 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (71): Assessment, FindingView, Row, ADR-0036, upload(), Hint, SuggestionView, ADR-0028 (+63 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (71): connection, createGarminAdapter(), called(), tokenOf(), withToken(), DEFAULT_PAUSE_MS, GARMIN_KEY_PAGE, garminCapabilities() (+63 more)

### Community 10 - "Community 10"
Cohesion: 0.02
Nodes (89): StoredConflictField, accountListEntry, actorType, aiAccessOutcome, backupRunKind, backupRunStatus, candidateResolution, centreSource (+81 more)

### Community 11 - "Community 11"
Cohesion: 0.04
Nodes (76): ServiceParts, ConnectionRow, WaitingPushOptions, audienceOfConnection(), audienceOfDive(), audienceOfDiver(), Hint, HINT_KINDS (+68 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (63): loadAuthSecret(), diveLease, providerConflict, providerLease, push, pushRun, waitingPush, kept() (+55 more)

### Community 13 - "Community 13"
Cohesion: 0.06
Nodes (79): allButDive(), continuationCandidateQuery(), DeletedDiveView, diveProvidersQuery(), diveQuery(), DiveView, mergeCandidatesQuery(), nearbyDivesQuery() (+71 more)

### Community 14 - "Community 14"
Cohesion: 0.05
Nodes (73): CertificationService, DIVE_IMPORT_MODES, AssessmentRefresh, ADR-0036, ADR-0053, ErrorAnswer, providerProblem(), AccountRouteDeps (+65 more)

### Community 15 - "Community 15"
Cohesion: 0.05
Nodes (64): SsiClient, SsiRecord, contextOf(), asNumber(), buildCreateRecord(), buildDeleteRecord(), buildUpdateRecord(), compareReadBack() (+56 more)

### Community 16 - "Community 16"
Cohesion: 0.05
Nodes (57): dive, duplicateCandidate, ImportErrorCode, ImportFound, importJob, importOriginal, ImportOutcome, original (+49 more)

### Community 17 - "Community 17"
Cohesion: 0.07
Nodes (73): renumber(), managedDiverIds(), AppendRow, channelsOf(), createContinuationService(), takeIn(), cylindersBack(), DiveRow (+65 more)

### Community 18 - "Community 18"
Cohesion: 0.04
Nodes (72): sacOfDive(), ADR-0025, WaterMismatch, waterMismatch(), withoutPressures(), Copies, DeleteProblemSchema, CylinderInput (+64 more)

### Community 19 - "Community 19"
Cohesion: 0.05
Nodes (69): recording, sampleSeries, buildJoined(), partInput(), partsOfJoined(), rebuildJoined(), RecordingRow, ADR-0063 (+61 more)

### Community 20 - "Community 20"
Cohesion: 0.04
Nodes (49): ProviderCylinder, ConflictView, ActionContext, Direction, DiveExport, DiveListing, DiveOrigin, FIND_BY (+41 more)

### Community 21 - "Community 21"
Cohesion: 0.07
Nodes (59): dataIdsOf(), settleSiteVisibility(), outgoingSite(), findOrMake(), SiteForSend, siteForSend(), ADR-0057, createSitePickService() (+51 more)

### Community 22 - "Community 22"
Cohesion: 0.08
Nodes (63): buildApp(), assessmentRoutes(), AI_ACCESS_NAME_MAX, isAdmin(), requireAdmin(), requireUser(), userOf(), BackupRouteDeps (+55 more)

### Community 23 - "Community 23"
Cohesion: 0.05
Nodes (59): CylinderMaterial, SuitType, SuitUndergarment, WeightingFelt, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, Suit (+51 more)

### Community 24 - "Community 24"
Cohesion: 0.05
Nodes (59): pushRunItem, PushRunLeftOut, diveListFilter(), notAtProvider(), createImport(), startImport(), emitHint(), Blocked (+51 more)

### Community 25 - "Community 25"
Cohesion: 0.05
Nodes (51): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+43 more)

### Community 26 - "Community 26"
Cohesion: 0.07
Nodes (57): waitingOf(), close(), currentRemote(), PushRow, ADR-0024, isExporting(), referenceOf(), keepBuddies() (+49 more)

### Community 27 - "Community 27"
Cohesion: 0.06
Nodes (53): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+45 more)

### Community 28 - "Community 28"
Cohesion: 0.06
Nodes (52): AuthOptions, hashPassword(), createServices(), Services, ADR-0027, ADR-0030, ADR-0061, ADR-0063 (+44 more)

### Community 29 - "Community 29"
Cohesion: 0.05
Nodes (46): BackupManifest, buildManifest(), decryptFile(), decryptFileWith(), encryptFile(), execFileAsync, openArchive(), openArchiveWith() (+38 more)

### Community 30 - "Community 30"
Cohesion: 0.06
Nodes (58): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+50 more)

### Community 31 - "Community 31"
Cohesion: 0.05
Nodes (47): UTC_OFFSET_SOURCES, downsampleMinMax(), AppendDeps, AppendService, ContinuationService, createDeletionService(), DeletionDeps, DeletionService (+39 more)

### Community 32 - "Community 32"
Cohesion: 0.07
Nodes (46): errorMessage(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy(), ADR-0024 (+38 more)

### Community 33 - "Community 33"
Cohesion: 0.04
Nodes (51): DiveView, RevisionView, ADR-0015, ADR-0025, ADR-0048, upload(), DiveView, LeadEntry (+43 more)

### Community 34 - "Community 34"
Cohesion: 0.05
Nodes (53): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, cancelWaitingImports() (+45 more)

### Community 35 - "Community 35"
Cohesion: 0.07
Nodes (58): assessmentQuery(), pushRunActive(), pushRunBody(), PushRunChoices, PushRunDetail, PushRunPreview, pushRunPreviewQuery(), pushRunQuery() (+50 more)

### Community 36 - "Community 36"
Cohesion: 0.06
Nodes (54): diversOf(), SiteRow, Cursor, cursorAt(), DateOnly(), defineTool(), Detail, diversAsked() (+46 more)

### Community 37 - "Community 37"
Cohesion: 0.07
Nodes (56): ComputerChoice, DiveImportMode, DiveKind, ProviderImportPlan, entryMatches(), kindOfMode(), deviceDiver(), closeFields() (+48 more)

### Community 38 - "Community 38"
Cohesion: 0.05
Nodes (51): PARTICIPANT_ROLES, siteImportStatus, PROBLEM_CODES, ProblemCode, ProblemSchema, storedProblemCode(), ADR-0014, ADR-0027 (+43 more)

### Community 39 - "Community 39"
Cohesion: 0.06
Nodes (47): externalDiver(), mergeExternalDiver(), participations(), open(), automationOffers(), AutomationSettings, createConnectionService(), checkAutomation() (+39 more)

### Community 40 - "Community 40"
Cohesion: 0.07
Nodes (48): ImportArea, ImportedValues, SiteImportCounts, ADR-0021, ADR-0025, ADR-0053, siteImport, ExistingSite (+40 more)

### Community 41 - "Community 41"
Cohesion: 0.05
Nodes (48): DIVE_KINDS, Change(), Changes(), historyRoute, IdParams, Made(), ImportView, ImportViewSchema (+40 more)

### Community 42 - "Community 42"
Cohesion: 0.08
Nodes (51): BACKUP_FORMAT_VERSION, BackupArchiveError, BackupEncryptionMetadata, BECH32_GENERATOR, bech32Checksum(), bech32Decode(), bech32Encode(), bech32HrpExpand() (+43 more)

### Community 43 - "Community 43"
Cohesion: 0.08
Nodes (50): logbookCheckAnswer, logbookCheckDiveAnswer, keepApart(), keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration(), CheckedDive (+42 more)

### Community 44 - "Community 44"
Cohesion: 0.05
Nodes (45): AssessmentService, AssessmentRouteDeps, AssessmentViewSchema, FindingView, Rule, STATUS, toView(), ADR-0036 (+37 more)

### Community 45 - "Community 45"
Cohesion: 0.10
Nodes (45): ChangePassword(), SecuritySettings(), Sessions(), useDescribeAgent(), authClient, BackupRunView, backupSettingsQuery(), BackupSettingsView (+37 more)

### Community 46 - "Community 46"
Cohesion: 0.05
Nodes (49): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+41 more)

### Community 47 - "Community 47"
Cohesion: 0.06
Nodes (49): diveListPages, providerStatusQuery(), ProviderStatusView, PushView, UnmetView, focusFirstIn(), diveTargets(), useFieldNames() (+41 more)

### Community 48 - "Community 48"
Cohesion: 0.07
Nodes (45): OverridableField, ParticipantRole, CandidateError, CandidateRow, createCandidateService(), lockMine(), RecordingRow, ADR-0016 (+37 more)

### Community 49 - "Community 49"
Cohesion: 0.07
Nodes (36): Tx, renumberStatement(), ADR-0048, AppendUndo, alignedForMatching(), CandidateDive, DAY_MS, decideMatch() (+28 more)

### Community 50 - "Community 50"
Cohesion: 0.07
Nodes (38): createServer(), createAuth(), workerDeps(), Config, loadConfig(), ADR-0011, ADR-0021, ADR-0024 (+30 more)

### Community 51 - "Community 51"
Cohesion: 0.07
Nodes (44): NoFly, RecordingSummary, RecordingFacts, getDiverLoadings, DEFAULT_GF_HIGH, residualSeedOf(), depthSamples(), DiverDiveRows (+36 more)

### Community 52 - "Community 52"
Cohesion: 0.07
Nodes (42): Leave, Series, DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+34 more)

### Community 53 - "Community 53"
Cohesion: 0.07
Nodes (33): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+25 more)

### Community 54 - "Community 54"
Cohesion: 0.08
Nodes (44): siteRef(), ConflictEntry, ConflictField, createConflictService(), loadWaiting(), stillDiffering(), fieldsOf(), isExists() (+36 more)

### Community 55 - "Community 55"
Cohesion: 0.06
Nodes (38): sampleCount(), SSI_CAPABILITIES, SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText() (+30 more)

### Community 56 - "Community 56"
Cohesion: 0.06
Nodes (42): @modelcontextprotocol/server@^2.3.1, aiAccessLog, aiAccessSetting, AccessRefusal, AI_ACCESS_SCOPES, AiAccessError, AiAccessScope, AiAccessService (+34 more)

### Community 57 - "Community 57"
Cohesion: 0.05
Nodes (30): BackupServiceDeps, CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK (+22 more)

### Community 58 - "Community 58"
Cohesion: 0.10
Nodes (41): tidyName(), foldName(), nameDiffers(), Buddy, createBuddyService(), createFrom(), linkEntry(), listAccount() (+33 more)

### Community 59 - "Community 59"
Cohesion: 0.05
Nodes (38): CYLINDER_MATERIALS, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053, replyDiverError, STATUS (+30 more)

### Community 60 - "Community 60"
Cohesion: 0.06
Nodes (42): LEAD_PLACEMENTS, SUIT_TYPES, SUIT_UNDERGARMENTS, WEIGHTING_FELT, ProfileSummary, round1(), Series, summariseProfile() (+34 more)

### Community 61 - "Community 61"
Cohesion: 0.09
Nodes (40): AdminSettings(), Invitations(), ProviderSiteData(), ADR-0012, ADR-0013, ADR-0030, Users(), Accesses() (+32 more)

### Community 62 - "Community 62"
Cohesion: 0.07
Nodes (37): device, participant, DiverError, ADR-0016, ADR-0053, ACCOUNT_PATTERN, accountKey(), createDiverService() (+29 more)

### Community 63 - "Community 63"
Cohesion: 0.09
Nodes (34): BottomTimeQuery, diverLogbook(), planBottomTime(), Source, ADR-0032, ADR-0033, ADR-0071, DEFAULT_PPO2 (+26 more)

### Community 64 - "Community 64"
Cohesion: 0.05
Nodes (40): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+32 more)

### Community 65 - "Community 65"
Cohesion: 0.07
Nodes (40): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+32 more)

### Community 66 - "Community 66"
Cohesion: 0.06
Nodes (40): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, EquipmentService, RecordInput, ScheduleInput (+32 more)

### Community 67 - "Community 67"
Cohesion: 0.05
Nodes (42): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+34 more)

### Community 68 - "Community 68"
Cohesion: 0.11
Nodes (38): devicesQuery(), diveGearQuery(), DiverView, EquipmentCategory, equipmentItemQuery(), equipmentOfDiverQuery(), DeviceRow(), Devices() (+30 more)

### Community 69 - "Community 69"
Cohesion: 0.08
Nodes (27): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+19 more)

### Community 70 - "Community 70"
Cohesion: 0.10
Nodes (35): createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open(), saveAndReadBack() (+27 more)

### Community 71 - "Community 71"
Cohesion: 0.08
Nodes (32): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+24 more)

### Community 72 - "Community 72"
Cohesion: 0.10
Nodes (30): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0051 (+22 more)

### Community 73 - "Community 73"
Cohesion: 0.11
Nodes (35): divesQuery(), importsQuery(), ImportView, uploadFile(), Logbook(), LogbookHeader(), ImportContext, ImportFilesButton() (+27 more)

### Community 74 - "Community 74"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 75 - "Community 75"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 76 - "Community 76"
Cohesion: 0.09
Nodes (27): auth, out, services, createSecretBox(), ADR-0024, SiteSourceAdapter, IMPORT_SOURCES, SITE_SOURCES (+19 more)

### Community 77 - "Community 77"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 78 - "Community 78"
Cohesion: 0.06
Nodes (28): buddySuggestion, jointDive, parseHint(), Accepted, Candidate, Check, DiveView, Outcome (+20 more)

### Community 79 - "Community 79"
Cohesion: 0.09
Nodes (33): buddiesOf(), BuddySearch, count(), DIVE_COLUMNS, DIVE_FROM, DiveFilter, diveOf(), DiveRow (+25 more)

### Community 80 - "Community 80"
Cohesion: 0.19
Nodes (34): APP_KEYS, appRow(), byId(), changedKeys(), deleteTestEntries(), entryNamed(), flag(), FLAGS (+26 more)

### Community 81 - "Community 81"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 82 - "Community 82"
Cohesion: 0.12
Nodes (27): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+19 more)

### Community 83 - "Community 83"
Cohesion: 0.14
Nodes (32): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findNewId(), Format (+24 more)

### Community 84 - "Community 84"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 85 - "Community 85"
Cohesion: 0.12
Nodes (25): BACKUP_SKIPPED_DIRS, BackupDecryptionKey, encryptionKeyFingerprint(), pgCommand(), pgFailureMessage(), redactPassword(), createRestoreService(), restore() (+17 more)

### Community 86 - "Community 86"
Cohesion: 0.07
Nodes (26): account, accountRelations, apikey, rateLimit, sessionRelations, ADR-0011, ADR-0012, ADR-0035 (+18 more)

### Community 87 - "Community 87"
Cohesion: 0.09
Nodes (27): BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, Conditions, GasMix, DiverPlan (+19 more)

### Community 88 - "Community 88"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 89 - "Community 89"
Cohesion: 0.08
Nodes (30): CylinderMaterial, BOTTOM_TIME_RULES, cylinderText(), diverText(), kept, LEAD_MATERIALS, LEAD_WATERS, LeadCylinder (+22 more)

### Community 90 - "Community 90"
Cohesion: 0.13
Nodes (29): massIn(), Dive, draftOfLead(), draftOfWeighting(), emptyLead(), fromLastDive(), hasThickness(), kg() (+21 more)

### Community 91 - "Community 91"
Cohesion: 0.09
Nodes (23): BACKUP_EXTENSION, BackupEncryption, BackupPart, fileTreeChecksum(), isRecipient(), tarDirectory(), BackupRunRow, BackupSettingsInput (+15 more)

### Community 92 - "Community 92"
Cohesion: 0.13
Nodes (26): surfacePressureBar(), offGasAtSurface(), replayDive(), tissuesAfterDives(), ndlMinutes(), ADR-0032, ADR-0046, advanceTissues() (+18 more)

### Community 93 - "Community 93"
Cohesion: 0.10
Nodes (27): applyHints(), LiveArrivals, Page, useLiveArrivals(), bump(), changedListeners, ChannelMessage, healthListeners (+19 more)

### Community 94 - "Community 94"
Cohesion: 0.09
Nodes (23): computerEventOf(), ADR-0036, computerEventFromFit(), COMPUTER_EVENT, computerEventFromSuunto(), DECO_MODEL, decoModelFromSuunto, DIVE_MODE (+15 more)

### Community 95 - "Community 95"
Cohesion: 0.15
Nodes (23): BottomTime, bottomTime(), ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres() (+15 more)

### Community 96 - "Community 96"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 97 - "Community 97"
Cohesion: 0.10
Nodes (23): ENGINE_VERSION, EVIDENCE, Evidence, FindingValues, RuleId, SEVERITIES, Severity, SourceId (+15 more)

### Community 98 - "Community 98"
Cohesion: 0.09
Nodes (24): continuationAppend, equipmentItem, AppendOutcome, Side, ADR-0038, ADR-0063, Deletion, Participant (+16 more)

### Community 99 - "Community 99"
Cohesion: 0.11
Nodes (24): equipmentUse, DiveGear, ensureItemForDevice(), GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow (+16 more)

### Community 100 - "Community 100"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 101 - "Community 101"
Cohesion: 0.09
Nodes (21): Cylinder, DiveView, RevisionView, ADR-0045, upload(), FIT_EPOCH_MS, makeSuuntoAppFit(), makeSuuntoJson() (+13 more)

### Community 102 - "Community 102"
Cohesion: 0.13
Nodes (21): acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), AppLockProbe (+13 more)

### Community 103 - "Community 103"
Cohesion: 0.09
Nodes (20): Queryable, diveSite, OVERRIDABLE_FIELDS, ADR-0016, byProvider(), DiveRow, FILLABLE, moveLinks() (+12 more)

### Community 104 - "Community 104"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 105 - "Community 105"
Cohesion: 0.12
Nodes (19): BACKUP_RUN_TASK, backupTask(), ADR-0069, EXPIRE_WAITING_IMPORTS_TASK, PROCESS_IMPORT_TASK, PUSH_RUN_STEP_TASK, WAITING_PUSHES_TASK, AUTOMATION_TICK_TASK (+11 more)

### Community 106 - "Community 106"
Cohesion: 0.14
Nodes (22): diverBodyWeight, lead, LeadPlacement, untieRecording(), createDiveService(), DiveRow, LeadEntry, setLead() (+14 more)

### Community 107 - "Community 107"
Cohesion: 0.18
Nodes (22): SsiLogbook, asBool(), aboveZero(), cylinderOf(), deviceOf(), deviceOfRef(), MATERIAL_OF_TANK_TYPE, nonZero() (+14 more)

### Community 108 - "Community 108"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 109 - "Community 109"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 110 - "Community 110"
Cohesion: 0.12
Nodes (21): CENTRE_SOURCES, CentreService, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+13 more)

### Community 111 - "Community 111"
Cohesion: 0.13
Nodes (21): date(), FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position(), positive() (+13 more)

### Community 112 - "Community 112"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 113 - "Community 113"
Cohesion: 0.14
Nodes (18): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+10 more)

### Community 114 - "Community 114"
Cohesion: 0.13
Nodes (17): ConnectionService, compareGone(), GONE_WARNING_MIN, GoneCandidate, GoneComparison, goneWarning(), ADR-0051, candidatesOf() (+9 more)

### Community 115 - "Community 115"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 116 - "Community 116"
Cohesion: 0.15
Nodes (18): qr@0.7.2, cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043 (+10 more)

### Community 117 - "Community 117"
Cohesion: 0.18
Nodes (19): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), lastStatuses(), localTime(), passedToRecord() (+11 more)

### Community 118 - "Community 118"
Cohesion: 0.16
Nodes (16): DecisionTimelineProps, GapKind, ADR-0063, addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, laneFitsCapsule(), relations() (+8 more)

### Community 119 - "Community 119"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 120 - "Community 120"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 121 - "Community 121"
Cohesion: 0.14
Nodes (17): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+9 more)

### Community 122 - "Community 122"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 123 - "Community 123"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 124 - "Community 124"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 125 - "Community 125"
Cohesion: 0.17
Nodes (16): UtcOffsetSource, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput, round3() (+8 more)

### Community 126 - "Community 126"
Cohesion: 0.16
Nodes (16): inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), placeLocalTime(), Position, ADR-0030, wallClockMs() (+8 more)

### Community 127 - "Community 127"
Cohesion: 0.15
Nodes (17): RemoteSite, flag(), idText(), isObject(), numberOrNull(), readSsiSite(), ssiSiteRow(), SsiSiteValues (+9 more)

### Community 128 - "Community 128"
Cohesion: 0.21
Nodes (16): AssessmentView, AssessmentState, bandSeconds(), clock(), counts(), differing(), Finding, formatRate() (+8 more)

### Community 129 - "Community 129"
Cohesion: 0.15
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 130 - "Community 130"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 131 - "Community 131"
Cohesion: 0.12
Nodes (16): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+8 more)

### Community 132 - "Community 132"
Cohesion: 0.22
Nodes (15): samplesQuery(), FindingLane(), useAssessment(), DepthProfile(), NO_POINTS, SurfaceBreakLane(), ADR-0036, ADR-0063 (+7 more)

### Community 133 - "Community 133"
Cohesion: 0.17
Nodes (13): BOM, Json, num(), obj(), positive(), scaled(), suuntoFitKey(), time() (+5 more)

### Community 134 - "Community 134"
Cohesion: 0.13
Nodes (14): SUGGESTION_STATUSES, DiveProfileView, DiveProfileViewSchema, ADR-0041, DiverRef, Offset, OverlapView, SiteRef (+6 more)

### Community 135 - "Community 135"
Cohesion: 0.16
Nodes (13): CIRCUIT, circuitFromFit, DECO_MODEL, decoModelFromFit, DIVE_ALERT, DIVE_MODE, diveModeFromFit, ADR-0015 (+5 more)

### Community 136 - "Community 136"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 137 - "Community 137"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 138 - "Community 138"
Cohesion: 0.26
Nodes (14): ToolsPlan, temperatureIn(), DepthPlot(), LINE_DASHES, plotFont(), PressureStrip(), RIGHT_AXIS, token() (+6 more)

### Community 139 - "Community 139"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 140 - "Community 140"
Cohesion: 0.16
Nodes (14): serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, DueEntry, ScheduleView, equipmentList(), EquipmentListFilter (+6 more)

### Community 141 - "Community 141"
Cohesion: 0.26
Nodes (15): managementOf(), createEquipmentService(), assemble(), detailOf(), lastCylinderOf(), managedItem(), managedRecord(), managedSchedule() (+7 more)

### Community 142 - "Community 142"
Cohesion: 0.13
Nodes (10): EGYPT, ExternalId, HOUSE_REEF, Method, Position, Revision, Site, SiteImport (+2 more)

### Community 143 - "Community 143"
Cohesion: 0.18
Nodes (12): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearPushRuns(), conflictAtSsi() (+4 more)

### Community 144 - "Community 144"
Cohesion: 0.22
Nodes (11): fromOsm(), fromSsi(), fromWd(), HOUSE_REEF, osm(), site(), ssi(), ADR-0021 (+3 more)

### Community 145 - "Community 145"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 146 - "Community 146"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 147 - "Community 147"
Cohesion: 0.15
Nodes (9): Sac, SAC_MISSING, SacMissing, ADR-0033, ADR-0045, Cylinder, dive, ADR-0033 (+1 more)

### Community 148 - "Community 148"
Cohesion: 0.17
Nodes (6): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, uniqueWord()

### Community 149 - "Community 149"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 150 - "Community 150"
Cohesion: 0.18
Nodes (10): EquipmentError, ADR-0034, ADR-0053, checkCost(), checkDates(), checkWindow(), checkResets(), checkSchedule() (+2 more)

### Community 151 - "Community 151"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 152 - "Community 152"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 153 - "Community 153"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 154 - "Community 154"
Cohesion: 0.20
Nodes (6): noAnswer, thrown(), ADR-0014, ApiClient, createApiClient(), ADR-0004

### Community 155 - "Community 155"
Cohesion: 0.20
Nodes (8): Series, ChannelSeries, RecordedGas, Tank, TiedCylinder, ADR-0045, readings, ADR-0045

### Community 156 - "Community 156"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 157 - "Community 157"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 158 - "Community 158"
Cohesion: 0.20
Nodes (5): FileFormat, FitAdapter, SuuntoJsonAdapter, UddfAdapter, ParsedRecording

### Community 159 - "Community 159"
Cohesion: 0.22
Nodes (9): HINT, Input, LoadingsInput, planGasAndBottomTime, ADR-0032, ADR-0035, Used(), BOTTOM_TIME_RULES (+1 more)

### Community 160 - "Community 160"
Cohesion: 0.29
Nodes (9): MATCH_WITHIN_M, matchingSite(), SiteOutlook, siteOutlook(), ADR-0025, ADR-0030, ADR-0057, distanceSql() (+1 more)

### Community 161 - "Community 161"
Cohesion: 0.31
Nodes (9): cylinderOf(), diverOf(), finite(), levelsOf(), oneOf(), pickDefined(), toolsParams(), of() (+1 more)

### Community 162 - "Community 162"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 163 - "Community 163"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 164 - "Community 164"
Cohesion: 0.20
Nodes (10): verify, major, minor, sha, tag, version, name, outputs (+2 more)

### Community 165 - "Community 165"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 167 - "Community 167"
Cohesion: 0.25
Nodes (8): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, MODULE_IMPORTS, moduleRules, target()

### Community 168 - "Community 168"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 169 - "Community 169"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 170 - "Community 170"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 171 - "Community 171"
Cohesion: 0.29
Nodes (6): itemIds(), ADR-0026, ADR-0034, Usage, UsageDive, UsageRow

### Community 172 - "Community 172"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 173 - "Community 173"
Cohesion: 0.33
Nodes (4): pollUntilDue(), ADR-0056, now, ADR-0056

### Community 174 - "Community 174"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 175 - "Community 175"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 176 - "Community 176"
Cohesion: 0.40
Nodes (4): label(), CompareSubject, facts, subjects

### Community 177 - "Community 177"
Cohesion: 0.40
Nodes (4): WeightingValues, logged, nothing, ADR-0031

### Community 178 - "Community 178"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 179 - "Community 179"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 182 - "Community 182"
Cohesion: 0.67
Nodes (3): planSketch(), shape(), ADR-0033

### Community 183 - "Community 183"
Cohesion: 0.50
Nodes (4): ToolsParams, SuitType, SuitUndergarment, ListControl

### Community 184 - "Community 184"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 185 - "Community 185"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

## Knowledge Gaps
- **1132 isolated node(s):** `ADR-0034`, `ADR-0045`, `ADR-0031`, `ADR-0025`, `ADR-0031` (+1127 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 2931 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **7 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `drizzle-orm` connect `Community 1` to `Community 7`, `Community 8`, `Community 9`, `Community 10`, `Community 11`, `Community 140`, `Community 12`, `Community 16`, `Community 17`, `Community 19`, `Community 21`, `Community 24`, `Community 26`, `Community 28`, `Community 29`, `Community 31`, `Community 160`, `Community 33`, `Community 36`, `Community 37`, `Community 39`, `Community 40`, `Community 43`, `Community 171`, `Community 46`, `Community 48`, `Community 49`, `Community 50`, `Community 51`, `Community 54`, `Community 56`, `Community 57`, `Community 58`, `Community 62`, `Community 63`, `Community 66`, `Community 69`, `Community 71`, `Community 76`, `Community 78`, `Community 79`, `Community 86`, `Community 91`, `Community 98`, `Community 99`, `Community 101`, `Community 103`, `Community 106`, `Community 114`, `Community 126`?**
  _High betweenness centrality (0.043) - this node is a cross-community bridge._
- **What connects `ADR-0034`, `ADR-0045`, `ADR-0031` to the rest of the system?**
  _1132 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.02753422550376865 - nodes in this community are weakly interconnected._
- **Why does `Button()` connect `Community 3` to `Community 0`, `Community 2`, `Community 35`, `Community 68`, `Community 5`, `Community 6`, `Community 4`, `Community 73`, `Community 13`, `Community 45`, `Community 47`, `Community 116`, `Community 90`, `Community 61`?**
  _High betweenness centrality (0.028) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.03625815808556925 - nodes in this community are weakly interconnected._
- **Why does `@playwright/test` connect `Community 53` to `Community 64`, `Community 34`, `Community 153`, `Community 72`, `Community 104`, `Community 137`, `Community 143`, `Community 148`, `Community 149`, `Community 152`, `Community 25`, `Community 88`?**
  _High betweenness centrality (0.023) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.023757195185766616 - nodes in this community are weakly interconnected._