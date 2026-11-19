# Graph Report - dive_hub  (2026-11-19)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 9348 nodes · 28217 edges · 271 communities (190 shown, 81 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 457 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `fe294db2`
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
- Community 192
- Community 193
- Community 194
- Community 195
- Community 196
- Community 197
- Community 200

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 204 edges
2. `Button()` - 198 edges
3. `useErrorText()` - 169 edges
4. `useDisplay()` - 161 edges
5. `Muted()` - 139 edges
6. `drizzle-orm` - 139 edges
7. `unwrap()` - 138 edges
8. `announce()` - 106 edges
9. `createTestDatabase()` - 103 edges
10. `TestDatabase` - 102 edges

## Surprising Connections (you probably didn't know these)
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `askedAt()` --calls--> `DiveError`  [EXTRACTED]
  apps/server/src/dives/continuation/append-service.ts → apps/server/src/dives/dive-service.ts
- `Placement` --references--> `Actor`  [EXTRACTED]
  apps/server/src/imports/placement.ts → apps/server/src/revisions/revisions.ts

## Import Cycles
- None detected.

## Communities (271 total, 81 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (205): ComputerChoice, DiveImportMode, DiveKind, importOriginal, UtcOffsetSource, mergeProviderCylinder(), ProviderCylinder, providerCylinderOf() (+197 more)

### Community 1 - "Community 1"
Cohesion: 0.02
Nodes (196): Queryable, continuationAppend, diverManagement, equipmentItem, lead, LeadPlacement, OVERRIDABLE_FIELDS, OverridableField (+188 more)

### Community 2 - "Community 2"
Cohesion: 0.02
Nodes (168): BACKUP_EXTENSION, BACKUP_FORMAT_VERSION, BACKUP_SKIPPED_DIRS, BackupArchiveError, BackupDecryptionKey, BackupEncryption, BackupEncryptionMetadata, BackupManifest (+160 more)

### Community 3 - "Community 3"
Cohesion: 0.04
Nodes (126): user, cylinder, dive, diveFinding, duplicateCandidate, original, recording, revision (+118 more)

### Community 4 - "Community 4"
Cohesion: 0.05
Nodes (171): AdminSettings(), DeleteUserDialog(), Invitations(), Invite(), LinkToPassOn(), ProviderSiteData(), SiteDataPanel(), ADR-0012 (+163 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (157): bottomTimeRule(), defined(), LeadEstimate, ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas, toolsGasQuery(), ToolsInput (+149 more)

### Community 6 - "Community 6"
Cohesion: 0.02
Nodes (158): @modelcontextprotocol/server@^2.3.1, AI_ACCESS_NAME_MAX, aiAccessLog, aiAccessSetting, WEIGHTING_FELT, downsampleMinMax(), DUE_STATES, Access (+150 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (147): EMPTY_SITE_IMPORT_COUNTS, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, diverExternalId (+139 more)

### Community 8 - "Community 8"
Cohesion: 0.02
Nodes (142): LEAD_PLACEMENTS, UTC_OFFSET_SOURCES, ADR-0025, WaterMismatch, waterMismatch(), DiveCertifications, AppendDeps, AppendOutcome (+134 more)

### Community 9 - "Community 9"
Cohesion: 0.03
Nodes (145): diveNeighboursQuery(), diveQuery(), divesQuery(), DiveValues, LogbookPage, OverridableField, PAGE_SIZE, revisionsQuery() (+137 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (127): label(), Me, AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery() (+119 more)

### Community 11 - "Community 11"
Cohesion: 0.03
Nodes (112): AccessLog(), AiAccess(), CreatedAccess(), LabelledCopy(), ADR-0035, AiAccessLogEntry, aiAccessLogQuery(), aiAccessQuery() (+104 more)

### Community 12 - "Community 12"
Cohesion: 0.02
Nodes (110): apikey, buddySuggestion, parseHint(), upload(), Accepted, Candidate, Check, DiveView (+102 more)

### Community 13 - "Community 13"
Cohesion: 0.03
Nodes (124): buddiesForQuery(), BuddyView, ConflictChoices, ConflictEntryView, ConflictFieldView, ConflictsOfConnection, DiveImportMode, DiveImportPreview (+116 more)

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (107): adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, Auth, AuthOptions, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, SessionUser (+99 more)

### Community 15 - "Community 15"
Cohesion: 0.03
Nodes (114): pushRunItem, PushRunLeftOut, diveListFilter(), notAtProvider(), waitingOf(), createConnectionService(), checkAutomation(), checkFollowNumbers() (+106 more)

### Community 16 - "Community 16"
Cohesion: 0.03
Nodes (101): isAdmin(), replyCertificationError, STATUS, CertificationService, CertificationView, CertificationViewSchema, Day, ADR-0050 (+93 more)

### Community 17 - "Community 17"
Cohesion: 0.04
Nodes (103): DiveView, cylinderCatalogueQuery(), leadEstimateQuery(), CylinderFields(), CylinderList(), MATERIALS, ADR-0034, ADR-0045 (+95 more)

### Community 18 - "Community 18"
Cohesion: 0.04
Nodes (103): importQuery(), importsQuery(), ImportView, ADR-0030, uploadFile(), noAnswerError(), running(), siteImportsQuery() (+95 more)

### Community 19 - "Community 19"
Cohesion: 0.05
Nodes (102): ChangePassword(), DisplaySettings(), SecuritySettings(), Sessions(), useDescribeAgent(), authClient, backupSettingsQuery(), backupsQuery() (+94 more)

### Community 20 - "Community 20"
Cohesion: 0.03
Nodes (82): ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS, eachFile(), readEntry(), ADR-0044, walk() (+74 more)

### Community 21 - "Community 21"
Cohesion: 0.03
Nodes (80): connection, diveLease, providerConflict, push, pushRun, waitingPush, kept(), LEASE_MS (+72 more)

### Community 22 - "Community 22"
Cohesion: 0.04
Nodes (94): diveAssessment, logbookCheckAnswer, logbookCheckDiveAnswer, recordingEvent, agreeInDepthAndDuration(), CheckedDive, DeletableDive, findChecks() (+86 more)

### Community 23 - "Community 23"
Cohesion: 0.04
Nodes (73): ServiceParts, Tx, ConnectionRow, WaitingPushOptions, AppendUndo, AutomationSettings, Credentials, FETCH_INTERVALS (+65 more)

### Community 24 - "Community 24"
Cohesion: 0.02
Nodes (90): accountListEntry, actorType, aiAccessOutcome, backupRun, backupRunKind, backupRunStatus, backupSettings, candidateResolution (+82 more)

### Community 25 - "Community 25"
Cohesion: 0.05
Nodes (84): buildApp(), assessmentRoutes(), requireAdmin(), requireUser(), userOf(), BackupRouteDeps, backupRoutes(), BackupRunView (+76 more)

### Community 26 - "Community 26"
Cohesion: 0.04
Nodes (47): ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem, ApiToolsGasPlanPost200ResponseUsed (+39 more)

### Community 27 - "Community 27"
Cohesion: 0.04
Nodes (66): createServer(), createAuth(), auth, loadAuthSecret(), acquireAppLock(), BackupServiceDeps, createServices(), Services (+58 more)

### Community 28 - "Community 28"
Cohesion: 0.04
Nodes (76): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+68 more)

### Community 29 - "Community 29"
Cohesion: 0.04
Nodes (72): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+64 more)

### Community 30 - "Community 30"
Cohesion: 0.05
Nodes (59): equipmentDueQuery(), EquipmentDueView, showDiveCodes(), ADR-0043, EquipmentDueNotice(), ADR-0034, avatarTone(), initials() (+51 more)

### Community 31 - "Community 31"
Cohesion: 0.04
Nodes (20): ApiDiveSitesGet200Response, ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting, DiverView (+12 more)

### Community 33 - "Community 33"
Cohesion: 0.05
Nodes (46): DiveBody(), DiveDetailRoute(), DiveDetailScreen(), errorText(), FactRow(), Facts(), Loading(), NeighbourButton() (+38 more)

### Community 34 - "Community 34"
Cohesion: 0.06
Nodes (76): Answer, answer(), baseFrom(), BodyWeight, Confidence, high, low, medium (+68 more)

### Community 35 - "Community 35"
Cohesion: 0.05
Nodes (58): remember(), SSI_CAPABILITIES, createSsiClient(), Fetch, Card, DEEP, OWD, Report (+50 more)

### Community 36 - "Community 36"
Cohesion: 0.08
Nodes (53): BottomTime, bottomTime(), BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen (+45 more)

### Community 37 - "Community 37"
Cohesion: 0.05
Nodes (58): AssessmentError, AssessmentService, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver() (+50 more)

### Community 38 - "Community 38"
Cohesion: 0.06
Nodes (26): DiveDetailUiState, DiveDetailViewModel, LogbookViewModel, ApiDivesGet200ResponseCounts, NeighboursView, NeighboursViewPrevious, DiveRepository, neighboursPath() (+18 more)

### Community 39 - "Community 39"
Cohesion: 0.06
Nodes (53): defineTool(), Id(), Input, planGasPlanTool, ADR-0033, ADR-0035, Used(), getDiverLoadings (+45 more)

### Community 40 - "Community 40"
Cohesion: 0.05
Nodes (56): ImportErrorCode, ImportFound, importJob, ImportOutcome, looksLikeZip(), FileFormat, formatOf(), CHOICE_WAIT_DAYS (+48 more)

### Community 41 - "Community 41"
Cohesion: 0.04
Nodes (46): ParticipantRole, ProviderImportPlan, ActionContext, Capabilities, Direction, DiveExport, DiveListing, FIND_BY (+38 more)

### Community 42 - "Community 42"
Cohesion: 0.07
Nodes (60): AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch(), e2eServerLoads() (+52 more)

### Community 43 - "Community 43"
Cohesion: 0.05
Nodes (52): CENTRE_SOURCES, CentreService, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+44 more)

### Community 44 - "Community 44"
Cohesion: 0.06
Nodes (45): SsiClient, SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), emptyRecord(), writableRecord(), FakeSsi (+37 more)

### Community 45 - "Community 45"
Cohesion: 0.08
Nodes (52): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+44 more)

### Community 46 - "Community 46"
Cohesion: 0.07
Nodes (46): errorMessage(), coordinate(), createSsiSiteSource(), DEFAULT_SSI_SITES_URL, parseSsiSites(), sitesJson(), truthy(), ADR-0024 (+38 more)

### Community 47 - "Community 47"
Cohesion: 0.06
Nodes (52): centreQuery(), centresQuery(), CentreView, Position, SiteImportArea, SiteImportView, siteRevisionsQuery(), SiteRevisionView (+44 more)

### Community 48 - "Community 48"
Cohesion: 0.07
Nodes (15): LogbookScreenshotTest, ApiDivesGet200Response, ApiDivesGet200ResponseDivesItem, ApiDivesGet200ResponseDivesItemGasesItem, ApiDivesGet200ResponseDivesItemParticipantsItem, ApiDivesGet200ResponseDivesItemSite, ApiDivesGet200ResponseMonthsItem, ApiDivesGet200ResponseTotals (+7 more)

### Community 49 - "Community 49"
Cohesion: 0.06
Nodes (7): AndroidDiveStore, DiveStore, toBoolean(), toLong(), toModel(), DiveSite, RecordingSeries

### Community 50 - "Community 50"
Cohesion: 0.09
Nodes (45): Formats, dateTime(), GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult() (+37 more)

### Community 51 - "Community 51"
Cohesion: 0.07
Nodes (49): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+41 more)

### Community 52 - "Community 52"
Cohesion: 0.08
Nodes (10): DiveDetailScreenshotTest, DiveViewFromRecording, DiveViewFromRecordingStartsAt, DiveViewRecordingsItemDevice, DiveViewRecordingsItemSummary, DiveViewRecordingsItemSummaryGasesItem, DiveViewValues, DiveViewValuesStartsAt (+2 more)

### Community 53 - "Community 53"
Cohesion: 0.05
Nodes (51): headers, ADR-0030, ADR-0062, globalSetup(), ADR-0023, AiAccess, AssessedFinding, cancelWaitingImports() (+43 more)

### Community 54 - "Community 54"
Cohesion: 0.08
Nodes (25): Profile(), ProfileChart(), ProfileTable(), DiveViewRecordingsItem, DiveViewRecordingsItemSummarySurfaceBreaksItem, RecordingSamplesView, RecordingSamplesViewSeriesItem, computerRecordings() (+17 more)

### Community 55 - "Community 55"
Cohesion: 0.06
Nodes (18): AvatarCircle(), drawBand(), ProfileSketch(), initials(), mixName(), LogbookPaging, AscentBand, SketchCurve (+10 more)

### Community 56 - "Community 56"
Cohesion: 0.09
Nodes (6): DiveHubTheme(), GasPlanScreenshotTest, ToolsScreenshotTest, GasPlanToolDiver, GasPlanToolInput, GasPlanToolLevel

### Community 57 - "Community 57"
Cohesion: 0.06
Nodes (49): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, DiveProfileView, DiveProfileViewSchema, ADR-0041 (+41 more)

### Community 58 - "Community 58"
Cohesion: 0.06
Nodes (9): AppContainer, DiveHubApplication, MainActivity, MainViewModel, DiveHubException, NoAnswerException, InstanceUrl, decodeList() (+1 more)

### Community 59 - "Community 59"
Cohesion: 0.07
Nodes (48): NoFly, noFlyAfter(), DEFAULT_GF_HIGH, residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows(), DiveRow (+40 more)

### Community 60 - "Community 60"
Cohesion: 0.07
Nodes (47): Delivered, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open() (+39 more)

### Community 61 - "Community 61"
Cohesion: 0.06
Nodes (34): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+26 more)

### Community 62 - "Community 62"
Cohesion: 0.08
Nodes (44): Leave, asBool(), DIVE_ROW, SENT_KEYS, SsiValueType, ADR-0027, diveModeOfSsi(), keepTraining() (+36 more)

### Community 63 - "Community 63"
Cohesion: 0.10
Nodes (42): ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres(), isHypoxic(), metresPerBar() (+34 more)

### Community 64 - "Community 64"
Cohesion: 0.06
Nodes (38): ADR-0036, ADR-0063, diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0038, ADR-0048 (+30 more)

### Community 65 - "Community 65"
Cohesion: 0.10
Nodes (6): PlanningSacSource, default, logbook, json(), LogbookRepositoryTest, RepositoryHarness

### Community 66 - "Community 66"
Cohesion: 0.11
Nodes (23): Sac, SacCylinder, SacDive, SacMissing, cylinder_incomplete, no_average_depth, too_short, sacOfDive() (+15 more)

### Community 67 - "Community 67"
Cohesion: 0.05
Nodes (42): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+34 more)

### Community 69 - "Community 69"
Cohesion: 0.09
Nodes (40): tidyName(), DiverDetails, Caller, foldName(), nameDiffers(), Buddy, createBuddyService(), createFrom() (+32 more)

### Community 70 - "Community 70"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 71 - "Community 71"
Cohesion: 0.06
Nodes (39): Change(), Changes(), historyRoute, Made(), REVISION_CAUSES, PositionSchema, AdoptBody, Country (+31 more)

### Community 72 - "Community 72"
Cohesion: 0.15
Nodes (19): Body, diveListItem(), divePage(), diverJson(), Harness, json(), manyDives(), manySites() (+11 more)

### Community 73 - "Community 73"
Cohesion: 0.09
Nodes (34): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+26 more)

### Community 74 - "Community 74"
Cohesion: 0.07
Nodes (34): sampleCount(), SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject() (+26 more)

### Community 75 - "Community 75"
Cohesion: 0.06
Nodes (30): LoadingsView, CustomTypeOptions, i18next, initI18n(), resources, ADR-0014, LOADING_SOURCES, loadingNumber() (+22 more)

### Community 76 - "Community 76"
Cohesion: 0.07
Nodes (6): KeystoreTokenStore, PreferencesInstanceUrlStore, UserCacheStore, InstanceUrlStore, TokenStore, UserScopedStore

### Community 77 - "Community 77"
Cohesion: 0.12
Nodes (11): GasPlanAnswer, gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number() (+3 more)

### Community 78 - "Community 78"
Cohesion: 0.10
Nodes (32): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0051 (+24 more)

### Community 79 - "Community 79"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 80 - "Community 80"
Cohesion: 0.08
Nodes (36): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+28 more)

### Community 81 - "Community 81"
Cohesion: 0.09
Nodes (11): UiState, ApiMeGet200Response, ApiMeGet200ResponseOwnDiver, ApiMeGet200ResponsePreferences, SignInEmailRequest, UserView, Loading, SessionRepository (+3 more)

### Community 82 - "Community 82"
Cohesion: 0.09
Nodes (10): Answers, number(), ToolsInputs, ToolsUiState, ToolsViewModel, ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions (+2 more)

### Community 83 - "Community 83"
Cohesion: 0.13
Nodes (8): FakeCache, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore, UserScopedStore

### Community 84 - "Community 84"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 85 - "Community 85"
Cohesion: 0.08
Nodes (9): DiveView, DiveViewLeadItem, DiveViewParticipantsItem, DiveViewParticipantsItemCertificationHint, DiveViewPosition, DiveViewSac, DiveViewSite, DiveViewSuit (+1 more)

### Community 86 - "Community 86"
Cohesion: 0.08
Nodes (30): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+22 more)

### Community 87 - "Community 87"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 88 - "Community 88"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 89 - "Community 89"
Cohesion: 0.08
Nodes (33): BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, Conditions, GasMix, ASCENT_RATE_M_PER_MIN (+25 more)

### Community 90 - "Community 90"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 91 - "Community 91"
Cohesion: 0.11
Nodes (17): DiveRow(), Error(), Fact(), Filters(), Loading(), LogbookList(), LogbookRoute(), LogbookScreen() (+9 more)

### Community 92 - "Community 92"
Cohesion: 0.13
Nodes (34): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+26 more)

### Community 93 - "Community 93"
Cohesion: 0.09
Nodes (27): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+19 more)

### Community 94 - "Community 94"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 95 - "Community 95"
Cohesion: 0.18
Nodes (3): SyncCheckpoint, SyncState, DiveStoreTest

### Community 96 - "Community 96"
Cohesion: 0.09
Nodes (29): PARTICIPANT_ROLES, storedProblemCode(), DateTime, DiveProviderStatus, DiveProviderStatusSchema, LeftOut, PushView, pushView() (+21 more)

### Community 97 - "Community 97"
Cohesion: 0.08
Nodes (27): computerEventOf(), ADR-0036, CIRCUIT, circuitFromFit, computerEventFromFit(), DECO_MODEL, decoModelFromFit, DIVE_ALERT (+19 more)

### Community 98 - "Community 98"
Cohesion: 0.10
Nodes (26): holdLeave(), mayLeave(), useLeaveGuard(), typedSiteId(), Draft, draftOf(), ExternalIdField(), positionOf() (+18 more)

### Community 99 - "Community 99"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 100 - "Community 100"
Cohesion: 0.12
Nodes (10): ApiDiverBodyWeightsGet200ResponseItem, Complete, SessionEnded, Stopped, SyncEngine, SyncPhase, Details, Meta (+2 more)

### Community 101 - "Community 101"
Cohesion: 0.09
Nodes (29): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+21 more)

### Community 102 - "Community 102"
Cohesion: 0.12
Nodes (24): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+16 more)

### Community 103 - "Community 103"
Cohesion: 0.18
Nodes (7): planningSac(), PlanningSacDive, PlanningSacResult, BottomTimeToolInput, GasNumbersInput, ToolDiverSource, ToolsServiceTest

### Community 104 - "Community 104"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 105 - "Community 105"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 106 - "Community 106"
Cohesion: 0.14
Nodes (26): DiveOrigin, REFERENCE_PREFIX, Series, SsiLogbook, aboveZero(), cylinderOf(), deviceOf(), deviceOfRef() (+18 more)

### Community 107 - "Community 107"
Cohesion: 0.10
Nodes (22): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearQueue(), conflictAtSsi() (+14 more)

### Community 108 - "Community 108"
Cohesion: 0.11
Nodes (7): ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh, ApiToolsBottomTimeGet200ResponseUsedSacLpm, ApiToolsBottomTimeGet200ResponseUsedWater

### Community 109 - "Community 109"
Cohesion: 0.11
Nodes (7): ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport

### Community 110 - "Community 110"
Cohesion: 0.08
Nodes (23): Check, Deleted, DiveView, Outcome, RecordingView, ADR-0063, Candidate, Deleted (+15 more)

### Community 111 - "Community 111"
Cohesion: 0.09
Nodes (22): Cylinder, DiveView, RevisionView, ADR-0045, upload(), Candidate, Check, Outcome (+14 more)

### Community 112 - "Community 112"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 113 - "Community 113"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 114 - "Community 114"
Cohesion: 0.11
Nodes (3): ApiClient, ApiErrorBody, DiveRepositoryTest

### Community 115 - "Community 115"
Cohesion: 0.13
Nodes (22): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DueInput, DueSchedule (+14 more)

### Community 116 - "Community 116"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 117 - "Community 117"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 118 - "Community 118"
Cohesion: 0.13
Nodes (20): APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), AppLockProbe, probeAppLock() (+12 more)

### Community 119 - "Community 119"
Cohesion: 0.11
Nodes (17): ProblemCode, ResponseState, whenResponseGone(), StoredBlob, PlacedArchive, ANSWER, looksLikeRecipientPrivateKey(), PlacedArchive (+9 more)

### Community 120 - "Community 120"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 121 - "Community 121"
Cohesion: 0.09
Nodes (23): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+15 more)

### Community 122 - "Community 122"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 123 - "Community 123"
Cohesion: 0.10
Nodes (17): account, accountRelations, rateLimit, session, sessionRelations, ADR-0011, ADR-0012, ADR-0035 (+9 more)

### Community 124 - "Community 124"
Cohesion: 0.17
Nodes (20): EquipmentError, checkCost(), checkWindow(), createEquipmentService(), assemble(), checkResets(), checkSchedule(), detailOf() (+12 more)

### Community 125 - "Community 125"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 126 - "Community 126"
Cohesion: 0.15
Nodes (5): SiteViewExternalIdsItem, SiteViewExternalIdsItemAttribution, SiteViewExternalIdsItemOffered, SiteViewExternalIdsItemOfferedPosition, SiteViewExternalIdsItemPrivateTo

### Community 127 - "Community 127"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource-variable/ibm-plex-sans@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 128 - "Community 128"
Cohesion: 0.11
Nodes (18): BACKUP_RUN_TASK, backupTask(), ADR-0069, CERTIFICATION_IMAGE_TASK, PurgeService, EXPIRE_WAITING_IMPORTS_TASK, PROCESS_IMPORT_TASK, SCHEDULED_FETCH_TASK (+10 more)

### Community 129 - "Community 129"
Cohesion: 0.12
Nodes (17): equipmentUse, checkDates(), DiveGear, ensureItemForDevice(), isCalendarDate(), ItemRow, LastCylinder, RecordRow (+9 more)

### Community 130 - "Community 130"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 131 - "Community 131"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 132 - "Community 132"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 133 - "Community 133"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 134 - "Community 134"
Cohesion: 0.16
Nodes (14): ImportArea, SiteImportRequest, openStream(), fromOsm(), fromSsi(), fromWd(), HOUSE_REEF, osm() (+6 more)

### Community 135 - "Community 135"
Cohesion: 0.18
Nodes (15): RecordingSummary, defined(), Joined, joinedSummary(), joinParts(), median(), PartInput, round3() (+7 more)

### Community 136 - "Community 136"
Cohesion: 0.14
Nodes (16): SUGGESTION_STATUSES, AcceptBody, STATUS, SuggestionRouteDeps, ADR-0070, SuggestionService, DiverRef, Offset (+8 more)

### Community 137 - "Community 137"
Cohesion: 0.12
Nodes (16): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+8 more)

### Community 138 - "Community 138"
Cohesion: 0.20
Nodes (3): FakeInstances, FakeTokens, SyncAuthTest

### Community 139 - "Community 139"
Cohesion: 0.14
Nodes (16): CERTIFICATION_IMAGE_ATTEMPTS, CertificationExpiry, CertificationInput, CertificationView, EXPIRING_SOON_DAYS, expiryOf(), ImportReport, ImportResult (+8 more)

### Community 140 - "Community 140"
Cohesion: 0.14
Nodes (14): AL80, EstimateDiveCylinder, LEAD_CASES, leadInput(), LeadVectorInput, planOf(), STEEL_12, ADR-0031 (+6 more)

### Community 141 - "Community 141"
Cohesion: 0.17
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 142 - "Community 142"
Cohesion: 0.19
Nodes (4): ApiToolsBottomTimeGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseLimits, ApiToolsBottomTimeGet200ResponseResidualN2

### Community 143 - "Community 143"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 144 - "Community 144"
Cohesion: 0.23
Nodes (15): cnsDecayed(), Estimate, isoUtc(), LoadingBasis, LoadingDive, N2Loadings, N2UnavailableReason, no_profile (+7 more)

### Community 145 - "Community 145"
Cohesion: 0.15
Nodes (15): SuitType, SuitUndergarment, WeightingFelt, Suit, WeightingFeedback, LeadEstimateAnswer, LeadVectorDive, Confidence (+7 more)

### Community 146 - "Community 146"
Cohesion: 0.19
Nodes (13): asEstimateInput(), evaluateLeadVectorCase(), LEAD_VECTOR_KERNEL, leadVectorFile(), VECTOR_KERNELS, VectorFile, vectorFile(), assertMatch() (+5 more)

### Community 147 - "Community 147"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 148 - "Community 148"
Cohesion: 0.19
Nodes (15): accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks(), offerSsiData(), PreparedData (+7 more)

### Community 149 - "Community 149"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 150 - "Community 150"
Cohesion: 0.20
Nodes (13): dayNumber(), enteredDecoFromNdl(), minNdlSeconds(), NoFly, noFlyAfter(), NoFlyReason, decompression, several (+5 more)

### Community 151 - "Community 151"
Cohesion: 0.13
Nodes (10): EGYPT, ExternalId, HOUSE_REEF, Method, Position, Revision, Site, SiteImport (+2 more)

### Community 152 - "Community 152"
Cohesion: 0.14
Nodes (7): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, headers, uniqueWord()

### Community 153 - "Community 153"
Cohesion: 0.19
Nodes (11): qr@0.7.2, browserDetector(), Detector, DetectorClass, Picture, pictureOf(), readQr(), readQrFromFile() (+3 more)

### Community 154 - "Community 154"
Cohesion: 0.18
Nodes (11): addGap(), CAPSULE_MIN_PERCENT, CAPSULE_MIN_SHARE, relations(), TimelineBar, TimelineBarGeometry, TimelineGapGeometry, TimelineGeometry (+3 more)

### Community 155 - "Community 155"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 156 - "Community 156"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 157 - "Community 157"
Cohesion: 0.19
Nodes (12): serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, ScheduleView, equipmentList(), EquipmentRecordRead, EquipmentScheduleRead (+4 more)

### Community 158 - "Community 158"
Cohesion: 0.17
Nodes (10): EstimateInput, AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031 (+2 more)

### Community 159 - "Community 159"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 160 - "Community 160"
Cohesion: 0.26
Nodes (3): DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewCylindersItemSeries

### Community 161 - "Community 161"
Cohesion: 0.30
Nodes (11): asText(), compareReadBack(), describe(), isObject(), keyDifferences(), pause(), recording(), runAnswerChecks() (+3 more)

### Community 162 - "Community 162"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 163 - "Community 163"
Cohesion: 0.18
Nodes (5): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053

### Community 164 - "Community 164"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 165 - "Community 165"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 166 - "Community 166"
Cohesion: 0.18
Nodes (11): version, on, push, release, workflow_dispatch, tags, types, description (+3 more)

### Community 167 - "Community 167"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 168 - "Community 168"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 169 - "Community 169"
Cohesion: 0.29
Nodes (10): DueEntry, GearItem, ItemDetailView, ItemEdit, ItemInput, ItemView, EquipmentItemRead, EquipmentListFilter (+2 more)

### Community 170 - "Community 170"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 171 - "Community 171"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 172 - "Community 172"
Cohesion: 0.47
Nodes (8): DiveHubApp(), ErrorText(), InstanceForm(), Loading(), PasswordField(), PrimaryButton(), SignInForm(), Spinner()

### Community 173 - "Community 173"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 175 - "Community 175"
Cohesion: 0.36
Nodes (5): idOf(), processImportTask(), startWorker(), TaskLog, quiet

### Community 176 - "Community 176"
Cohesion: 0.25
Nodes (8): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, MODULE_IMPORTS, moduleRules, target()

### Community 177 - "Community 177"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 178 - "Community 178"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 179 - "Community 179"
Cohesion: 0.29
Nodes (5): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, EstimateCylinder

### Community 180 - "Community 180"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 181 - "Community 181"
Cohesion: 0.29
Nodes (6): itemIds(), ADR-0026, ADR-0034, Usage, UsageDive, UsageRow

### Community 182 - "Community 182"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 183 - "Community 183"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 184 - "Community 184"
Cohesion: 0.40
Nodes (4): CertificationError, cleaned(), rowOrThrow(), isCalendarDate()

### Community 185 - "Community 185"
Cohesion: 0.40
Nodes (4): CertificationSummary, NO_CERTIFICATIONS, ADR-0050, ADR-0053

### Community 186 - "Community 186"
Cohesion: 0.40
Nodes (4): CertificationHint, CertificationHintExpiry, ADR-0050, ADR-0053

### Community 187 - "Community 187"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 188 - "Community 188"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 189 - "Community 189"
Cohesion: 0.40
Nodes (4): TAGS, ADR-0023, { stdout, stderr }, total

### Community 193 - "Community 193"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 194 - "Community 194"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 195 - "Community 195"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1263 isolated node(s):** `Reserve`, `RockBottom`, `Off`, `SuitThickness`, `ADR-0030` (+1258 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3353 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **81 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `@tanstack/react-query` connect `Community 11` to `Community 98`, `Community 4`, `Community 101`, `Community 5`, `Community 9`, `Community 10`, `Community 13`, `Community 45`, `Community 47`, `Community 79`, `Community 17`, `Community 18`, `Community 19`, `Community 30`, `Community 127`?**
  _High betweenness centrality (0.029) - this node is a cross-community bridge._
- **What connects `Reserve`, `RockBottom`, `Off` to the rest of the system?**
  _1263 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.0219435736677116 - nodes in this community are weakly interconnected._
- **Why does `drizzle-orm` connect `Community 3` to `Community 0`, `Community 1`, `Community 2`, `Community 129`, `Community 6`, `Community 7`, `Community 8`, `Community 139`, `Community 12`, `Community 14`, `Community 15`, `Community 20`, `Community 21`, `Community 22`, `Community 23`, `Community 24`, `Community 27`, `Community 157`, `Community 29`, `Community 35`, `Community 37`, `Community 39`, `Community 40`, `Community 181`, `Community 57`, `Community 59`, `Community 69`, `Community 104`, `Community 110`, `Community 121`, `Community 123`?**
  _High betweenness centrality (0.028) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.022209674383587428 - nodes in this community are weakly interconnected._
- **Why does `Button()` connect `Community 4` to `Community 98`, `Community 5`, `Community 9`, `Community 10`, `Community 11`, `Community 45`, `Community 13`, `Community 47`, `Community 17`, `Community 18`, `Community 19`, `Community 30`?**
  _High betweenness centrality (0.016) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.022700119474313024 - nodes in this community are weakly interconnected._