# Graph Report - dive_hub  (2026-11-21)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 11019 nodes · 33227 edges · 368 communities (222 shown, 146 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 626 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `321253f0`
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
- Community 190
- Community 191
- Community 192
- Community 193
- Community 194
- Community 195
- Community 196
- Community 197
- Community 198
- Community 199
- Community 200
- Community 201
- Community 202
- Community 203
- Community 204
- Community 205
- Community 206
- Community 207
- Community 208
- Community 209
- Community 210
- Community 211
- Community 212
- Community 213
- Community 214
- Community 215
- Community 216
- Community 217
- Community 218
- Community 219
- Community 220
- Community 221
- Community 222
- Community 223
- Community 224
- Community 225
- Community 226
- Community 227
- Community 228
- Community 229
- Community 230
- Community 231
- Community 232
- Community 233
- Community 234
- Community 235
- Community 236
- Community 237
- Community 238
- Community 240
- Community 241
- Community 242
- Community 243
- Community 244
- Community 245
- Community 248
- Community 250

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 209 edges
2. `Button()` - 202 edges
3. `useErrorText()` - 174 edges
4. `useDisplay()` - 163 edges
5. `drizzle-orm` - 143 edges
6. `unwrap()` - 141 edges
7. `Muted()` - 139 edges
8. `announce()` - 107 edges
9. `createTestDatabase()` - 106 edges
10. `TestDatabase` - 105 edges

## Surprising Connections (you probably didn't know these)
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `Context` --references--> `Position`  [EXTRACTED]
  apps/server/src/imports/formats/uddf/uddf.ts → apps/server/src/vocabulary.ts
- `EquipmentListFilter` --references--> `EquipmentCategory`  [EXTRACTED]
  apps/server/src/equipment/read.ts → apps/server/src/vocabulary.ts

## Import Cycles
- None detected.

## Communities (368 total, 146 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.05
Nodes (217): ChangePassword(), DisplaySettings(), ReportProblemSettings(), SecuritySettings(), Sessions(), ADR-0080, useDescribeAgent(), AdminSettings() (+209 more)

### Community 1 - "Community 1"
Cohesion: 0.03
Nodes (148): user, cylinder, dive, duplicateCandidate, importJob, original, push, recording (+140 more)

### Community 2 - "Community 2"
Cohesion: 0.02
Nodes (184): Queryable, Tx, continuationAppend, device, equipmentItem, LeadPlacement, OVERRIDABLE_FIELDS, OverridableField (+176 more)

### Community 3 - "Community 3"
Cohesion: 0.02
Nodes (172): ConnectionRow, diveFingerprint, providerGoneDive, providerLease, PushLeftOut, WaitingPushOptions, waitingOf(), open() (+164 more)

### Community 4 - "Community 4"
Cohesion: 0.03
Nodes (163): auth, ServiceParts, Db, EMPTY_SITE_IMPORT_COUNTS, ImportArea, ImportedValues, SiteImportCounts, StoredConflictField (+155 more)

### Community 5 - "Community 5"
Cohesion: 0.03
Nodes (144): BACKUP_EXTENSION, BACKUP_FORMAT_VERSION, BACKUP_SKIPPED_DIRS, BackupArchiveError, BackupDecryptionKey, BackupEncryption, BackupEncryptionMetadata, BackupManifest (+136 more)

### Community 6 - "Community 6"
Cohesion: 0.03
Nodes (159): bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery(), ToolsGas, toolsGasQuery() (+151 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (153): diversQuery(), DiveValues, DiveView, OverridableField, revisionsQuery(), RevisionView, UtcOffsetSource, cylinderCatalogueQuery() (+145 more)

### Community 8 - "Community 8"
Cohesion: 0.03
Nodes (140): label(), Me, AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery() (+132 more)

### Community 9 - "Community 9"
Cohesion: 0.02
Nodes (136): AiAccessLogEntry, aiAccessLogQuery(), aiAccessQuery(), AiAccessView, invitationLinkQuery(), invitationsQuery(), InvitationView, passwordResetQuery() (+128 more)

### Community 10 - "Community 10"
Cohesion: 0.02
Nodes (151): @modelcontextprotocol/server@^2.3.1, LEAD_PLACEMENTS, PARTICIPANT_ROLES, downsampleMinMax(), AiAccessRouteDeps, AccessRefusal, AiAccessScope, AiAccessService (+143 more)

### Community 11 - "Community 11"
Cohesion: 0.02
Nodes (122): buddySuggestion, diveFinding, parseHint(), Assessment, FindingView, Row, ADR-0036, upload() (+114 more)

### Community 12 - "Community 12"
Cohesion: 0.03
Nodes (146): ComputerChoice, DiveImportMode, DiveKind, ProviderImportPlan, providerSiteData, UtcOffsetSource, managedDiverIds(), tieToJoined() (+138 more)

### Community 13 - "Community 13"
Cohesion: 0.02
Nodes (132): BackupServiceDeps, actorType, aiAccessOutcome, backupRunKind, backupRunStatus, backupSettings, candidateResolution, centreSource (+124 more)

### Community 14 - "Community 14"
Cohesion: 0.02
Nodes (105): createServer(), createAuth(), loadAuthSecret(), workerDeps(), Config, loadConfig(), ADR-0011, ADR-0021 (+97 more)

### Community 15 - "Community 15"
Cohesion: 0.03
Nodes (129): divesQuery(), LogbookPage, PAGE_SIZE, pushRunActive(), pushRunBody(), PushRunChoices, PushRunPreview, pushRunPreviewQuery() (+121 more)

### Community 16 - "Community 16"
Cohesion: 0.02
Nodes (118): CentreService, CentreRouteDeps, WEIGHTING_FELT, DiverService, CertificationHint, CertificationHintExpiry, DiveCertifications, ADR-0050 (+110 more)

### Community 17 - "Community 17"
Cohesion: 0.03
Nodes (15): AppContainer, MainActivity, LogbookViewModel, followStoreWrites(), SettingsViewModel, LeadEstimateUiState, LeadEstimateViewModel, LoadingsUiState (+7 more)

### Community 18 - "Community 18"
Cohesion: 0.03
Nodes (106): buddiesForQuery(), buddiesQuery(), BuddyView, ConflictFieldView, ConnectionView, DiveImportMode, DiveImportPreview, diveImportPreviewQuery() (+98 more)

### Community 19 - "Community 19"
Cohesion: 0.04
Nodes (102): diveAssessment, logbookCheckAnswer, logbookCheckDiveAnswer, keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration(), CheckedDive (+94 more)

### Community 20 - "Community 20"
Cohesion: 0.04
Nodes (89): replyCertificationError, STATUS, CertificationView, Day, ADR-0050, CardParams, CertificationRouteDeps, errors (+81 more)

### Community 21 - "Community 21"
Cohesion: 0.06
Nodes (13): label(), SettingsDebugRoute(), SettingsDebugScreen(), Action(), ConnectionStatus(), Section(), SettingsRoute(), SettingsScreen() (+5 more)

### Community 22 - "Community 22"
Cohesion: 0.04
Nodes (93): accountListEntry, diver, diverExternalId, DiverError, ADR-0016, ADR-0053, tidyName(), ACCOUNT_PATTERN (+85 more)

### Community 23 - "Community 23"
Cohesion: 0.04
Nodes (75): sitesQuery(), CodeFigure(), DiveCodes(), showDiveCodes(), ADR-0043, initI18n(), avatarTone(), initials() (+67 more)

### Community 24 - "Community 24"
Cohesion: 0.05
Nodes (14): DiveStore, toBoolean(), toLong(), toModel(), BodyWeight, Cylinder, Diver, DiveSite (+6 more)

### Community 25 - "Community 25"
Cohesion: 0.04
Nodes (93): CertificationService, CYLINDER_MATERIALS, DIVE_IMPORT_MODES, UTC_OFFSET_SOURCES, AssessmentRefresh, refreshAssessmentsAfterChange(), ADR-0036, ADR-0053 (+85 more)

### Community 26 - "Community 26"
Cohesion: 0.04
Nodes (19): AndroidDiveStore, FakeDiagnostics, FakeInstances, SettingsViewModelTest, ApiClientDiagnosticsPostRequest, ApiClientDiagnosticsPostRequestClient, ApiClientDiagnosticsPostRequestEntriesItem, DiagnosticHygiene (+11 more)

### Community 27 - "Community 27"
Cohesion: 0.05
Nodes (88): allButDive(), continuationCandidateQuery(), ContinuationCandidateView, deletedDivesOn(), deletedDivesQuery(), diveNeighboursQuery(), diveQuery(), LogbookCheckView (+80 more)

### Community 28 - "Community 28"
Cohesion: 0.04
Nodes (48): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+40 more)

### Community 29 - "Community 29"
Cohesion: 0.06
Nodes (74): Formats, GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult(), PlanInputs() (+66 more)

### Community 30 - "Community 30"
Cohesion: 0.06
Nodes (80): buildApp(), assessmentRoutes(), AssessmentViewSchema, FindingView, Rule, STATUS, toView(), ADR-0036 (+72 more)

### Community 31 - "Community 31"
Cohesion: 0.04
Nodes (77): adminRole, AI_ACCESS_PREFIX, AI_ACCESS_RATE, Auth, AuthOptions, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, ADR-0011 (+69 more)

### Community 32 - "Community 32"
Cohesion: 0.06
Nodes (14): LogbookScreenshotTest, DiveListViewDivesItem, DiveListViewDivesItemGasesItem, DiveListViewDivesItemParticipantsItem, DiveListViewDivesItemSite, DiveListViewMonthsItem, DiveProfileView, localMonthOf() (+6 more)

### Community 33 - "Community 33"
Cohesion: 0.06
Nodes (34): Direction, Back, Left, Right, DirectionIcon(), DiveBody(), DiveContent(), DiveDetailRoute() (+26 more)

### Community 34 - "Community 34"
Cohesion: 0.04
Nodes (67): CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite(), lock() (+59 more)

### Community 35 - "Community 35"
Cohesion: 0.08
Nodes (8): DiveDetailScreenshotTest, DiveViewFromRecording, DiveViewFromRecordingStartsAt, DiveViewRecordingsItemSummaryGasesItem, DiveViewWaterMismatch, NeighboursView, NeighboursViewNext, NeighboursViewPrevious

### Community 36 - "Community 36"
Cohesion: 0.04
Nodes (19): ApiClientDiagnosticsStatusGet200Response, ApiDiveSitesGet200Response, ApiMeGet200ResponseOwnDiver, ApiToolsBottomTimeGet200ResponseResidualN2, ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue (+11 more)

### Community 37 - "Community 37"
Cohesion: 0.08
Nodes (4): FakeCache, RevisionFollowTest, LeadEstimateViewModelTest, Dive

### Community 38 - "Community 38"
Cohesion: 0.05
Nodes (28): DiveDetailUiState, DiveDetailViewModel, DiveRepository, neighboursPath(), countOf(), FilterChip, filterChips(), LogbookFilter (+20 more)

### Community 39 - "Community 39"
Cohesion: 0.04
Nodes (55): diveLease, providerConflict, pushRun, kept(), providerError(), ExportingProvider, ProviderError, DELETE_QUIET_MS (+47 more)

### Community 40 - "Community 40"
Cohesion: 0.05
Nodes (63): NoFly, getDiverLoadings, HINT, Input, LoadingsInput, planGasAndBottomTime, ADR-0032, ADR-0035 (+55 more)

### Community 41 - "Community 41"
Cohesion: 0.05
Nodes (24): Checking, MainViewModel, Reachability, Reachable, UiState, Unknown, Unreachable, sessionDestination() (+16 more)

### Community 42 - "Community 42"
Cohesion: 0.08
Nodes (29): LiveHints, SyncRefresher, Body, Hold, Offline, RefreshData, refreshDeletedJson(), refreshDetailJson() (+21 more)

### Community 43 - "Community 43"
Cohesion: 0.05
Nodes (60): diveSiteExternalId, createSsiAdapter(), SSI_CAPABILITIES, createSsiClient(), ssiSiteRow(), Fetch, Card, DEEP (+52 more)

### Community 44 - "Community 44"
Cohesion: 0.04
Nodes (65): BottomTime, bottomTime(), BottomTimeInput, Limit, ADR-0032, ADR-0033, ADR-0071, GasMix (+57 more)

### Community 45 - "Community 45"
Cohesion: 0.04
Nodes (51): ParticipantRole, Direction, DIVE_FIELDS, DiveExport, DiveListing, DiveOrigin, FIND_BY, FindBy (+43 more)

### Community 46 - "Community 46"
Cohesion: 0.04
Nodes (63): pushRunItem, PushRunLeftOut, DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN, NO_RECORDING (+55 more)

### Community 47 - "Community 47"
Cohesion: 0.04
Nodes (59): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationError (+51 more)

### Community 48 - "Community 48"
Cohesion: 0.06
Nodes (64): ANDROID_UI, AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch() (+56 more)

### Community 49 - "Community 49"
Cohesion: 0.08
Nodes (13): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), GasPlanInputTest (+5 more)

### Community 50 - "Community 50"
Cohesion: 0.11
Nodes (25): ApiToolsBottomTimeGet200ResponseGas, Body, BrokenBody, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage() (+17 more)

### Community 51 - "Community 51"
Cohesion: 0.07
Nodes (27): ProfileChart(), DiveViewRecordingsItem, DiveViewRecordingsItemDevice, DiveViewRecordingsItemSummarySurfaceBreaksItem, RecordingSamplesView, RecordingSamplesViewSeriesItem, computerRecordings(), depthSeries() (+19 more)

### Community 52 - "Community 52"
Cohesion: 0.09
Nodes (48): BottomTime, BottomTimeCylinder, BottomTimeInput, BottomTimeLimit, gas, ndl, oxygen, BottomTimeLimits (+40 more)

### Community 53 - "Community 53"
Cohesion: 0.05
Nodes (55): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+47 more)

### Community 54 - "Community 54"
Cohesion: 0.10
Nodes (10): DiveRoute, InstanceRoute, LogbookRoute, MainRoute, SettingsDebugRoute, SettingsRoute, SignInRoute, ToolsRoute (+2 more)

### Community 55 - "Community 55"
Cohesion: 0.06
Nodes (46): SsiClient, SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), DiveForSsi, emptyRecord(), writableRecord() (+38 more)

### Community 56 - "Community 56"
Cohesion: 0.08
Nodes (51): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+43 more)

### Community 57 - "Community 57"
Cohesion: 0.06
Nodes (13): DiveListView, DiveListViewCounts, DiveListViewTotals, DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewRecordingsItemSummary, DiveViewValues, DiveViewValuesStartsAt (+5 more)

### Community 58 - "Community 58"
Cohesion: 0.07
Nodes (59): Answer, answer(), baseFrom(), BodyWeight, Confidence, high, low, medium (+51 more)

### Community 59 - "Community 59"
Cohesion: 0.04
Nodes (55): Change(), Changes(), historyRoute, Made(), REVISION_CAUSES, PositionSchema, AdoptBody, Country (+47 more)

### Community 60 - "Community 60"
Cohesion: 0.07
Nodes (4): DiveDetailViewModelTest, GatedDispatcher, CountingDispatcher, LogbookViewModelTest

### Community 61 - "Community 61"
Cohesion: 0.07
Nodes (47): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+39 more)

### Community 62 - "Community 62"
Cohesion: 0.06
Nodes (41): createDb(), appliedMigrationHead(), findChangedMigrations(), migrateDatabase(), ADR-0004, ArchiveEntry, ArchiveLimitError, ArchiveLimits (+33 more)

### Community 63 - "Community 63"
Cohesion: 0.06
Nodes (51): ADR-0036, ADR-0063, headers, ADR-0030, ADR-0038, ADR-0048, ADR-0051, AiAccess (+43 more)

### Community 64 - "Community 64"
Cohesion: 0.06
Nodes (46): RequirementView, ExternalIdView, useFocusBackWhenClosed(), holdLeave(), mayLeave(), useLeaveGuard(), needsOsmAttribution(), siteOrigin() (+38 more)

### Community 65 - "Community 65"
Cohesion: 0.07
Nodes (10): ApiClient, ApiErrorBody, Connected, Hint, HintFrame, LiveEvent, LiveHint, SseLiveHints (+2 more)

### Community 66 - "Community 66"
Cohesion: 0.05
Nodes (49): AppDeps, Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, CertificationViewSchema, Services (+41 more)

### Community 67 - "Community 67"
Cohesion: 0.07
Nodes (30): headers, ADR-0031, headers, ADR-0043, ADR-0080, ADR-0016, ADR-0071, created (+22 more)

### Community 68 - "Community 68"
Cohesion: 0.11
Nodes (9): Complete, SessionEnded, Stopped, SyncEngine, SyncPhase, Details, Meta, Profiles (+1 more)

### Community 69 - "Community 69"
Cohesion: 0.07
Nodes (44): AIR, cnsDecayed(), DEFAULT_CONDITIONS, N2_REPLAY_WINDOW_HOURS, N2_STALE_AFTER_HOURS, N2Loadings, N2LoadingsBase, N2UnavailableReason (+36 more)

### Community 70 - "Community 70"
Cohesion: 0.07
Nodes (37): createSsiSiteSource(), DEFAULT_SSI_SITES_URL, createSiteSources(), ADR-0021, ADR-0027, createOverpassSource(), DEFAULT_OVERPASS_URL, Element (+29 more)

### Community 71 - "Community 71"
Cohesion: 0.05
Nodes (39): SUIT_TYPES, SUIT_UNDERGARMENTS, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053, replyDiverError (+31 more)

### Community 72 - "Community 72"
Cohesion: 0.06
Nodes (35): Delivered, DiveExportAction, buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open() (+27 more)

### Community 73 - "Community 73"
Cohesion: 0.11
Nodes (8): FakeCache, FakeInstances, FakeTokens, Harness, json(), SessionRepositoryTest, TokenStore, UserScopedStore

### Community 74 - "Community 74"
Cohesion: 0.07
Nodes (36): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+28 more)

### Community 75 - "Community 75"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 76 - "Community 76"
Cohesion: 0.08
Nodes (13): AppNavHost(), AppScreens, clearSavedTabStates(), isShowing(), leaveDiveForLogbook(), MainShell(), navigateToTab(), tabOf() (+5 more)

### Community 78 - "Community 78"
Cohesion: 0.09
Nodes (35): CylinderMaterial, aluminium, carbon, steel, EstimateDiveCylinder, LoadingDive, dayNumber(), enteredDecoFromNdl() (+27 more)

### Community 79 - "Community 79"
Cohesion: 0.05
Nodes (39): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+31 more)

### Community 80 - "Community 80"
Cohesion: 0.10
Nodes (21): ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseN2, ApiDiversIdLoadingsGet200ResponseNoFly, ApiDiversIdLoadingsGet200ResponseNoFlySource, ApiDiversIdLoadingsGet200ResponseOtu, cnsDecayed(), Estimate (+13 more)

### Community 81 - "Community 81"
Cohesion: 0.09
Nodes (37): SENT_KEYS, diveModeOfSsi(), keepTraining(), ssiDiveType(), ssiInteger(), ADR-0057, asNumber(), buildUpdateRecord() (+29 more)

### Community 82 - "Community 82"
Cohesion: 0.06
Nodes (31): diveRow(), rowOf(), ADR-0026, ADR-0048, ADR-0062, cancelWaitingImports(), connectGarmin(), diveRows() (+23 more)

### Community 83 - "Community 83"
Cohesion: 0.08
Nodes (38): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+30 more)

### Community 84 - "Community 84"
Cohesion: 0.08
Nodes (29): diveLocalTime(), offsetId(), timeZoneNote(), TimeZoneOrigin, DEVICE, NEARBY, POSITION, UNKNOWN (+21 more)

### Community 85 - "Community 85"
Cohesion: 0.10
Nodes (20): AvatarCircle(), DiveRow(), Error(), Fact(), Filters(), Loading(), LogbookList(), LogbookRoute() (+12 more)

### Community 86 - "Community 86"
Cohesion: 0.08
Nodes (13): drawBand(), ProfileSketch(), successColor(), AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint (+5 more)

### Community 87 - "Community 87"
Cohesion: 0.07
Nodes (36): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+28 more)

### Community 88 - "Community 88"
Cohesion: 0.07
Nodes (33): SsiCallLog, rowOf(), SsiBuddyRow, DEFAULT_CARD_IMAGE_BASE, DEFAULT_SSI_URL, idText(), isObject(), MAX_CARD_IMAGE_BYTES (+25 more)

### Community 89 - "Community 89"
Cohesion: 0.08
Nodes (26): captureDiagnostic(), captureError(), cleanContext(), clearDiagnostics(), CLIENT_NAME, CLIENT_VERSION, DiagnosticEntry, diagnosticFromError() (+18 more)

### Community 90 - "Community 90"
Cohesion: 0.09
Nodes (19): DiveHubApp(), DiveHubScreens, ErrorText(), Gate(), InstanceForm(), InstanceStatus(), LoadingScreen(), PasswordField() (+11 more)

### Community 91 - "Community 91"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 92 - "Community 92"
Cohesion: 0.13
Nodes (13): bottomTime(), otu(), oxygenLimitMinutes(), planningSac(), PlanningSacDive, PlanningSacResult, PlanningSacSource, default (+5 more)

### Community 93 - "Community 93"
Cohesion: 0.17
Nodes (38): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestDive(), deleteTestEntries(), entryNamed() (+30 more)

### Community 94 - "Community 94"
Cohesion: 0.08
Nodes (30): diverLoadingsQuery(), LoadingsView, siteImportsQuery(), CurrentLoadings(), CustomTypeOptions, i18next, resources, ADR-0014 (+22 more)

### Community 95 - "Community 95"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 96 - "Community 96"
Cohesion: 0.05
Nodes (37): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+29 more)

### Community 97 - "Community 97"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 98 - "Community 98"
Cohesion: 0.13
Nodes (6): Missing(), LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200ResponseMissingItem, ApiDiversIdLoadingsGet200ResponseCns

### Community 99 - "Community 99"
Cohesion: 0.13
Nodes (34): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findDive(), findNewId() (+26 more)

### Community 100 - "Community 100"
Cohesion: 0.11
Nodes (29): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, ADR-0040 (+21 more)

### Community 101 - "Community 101"
Cohesion: 0.09
Nodes (5): SettingsRouteTest, SettingsRepository, InstanceUrlStore, FakeInstances, SettingsRepositoryTest

### Community 102 - "Community 102"
Cohesion: 0.11
Nodes (28): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+20 more)

### Community 103 - "Community 103"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 104 - "Community 104"
Cohesion: 0.12
Nodes (29): Series, SsiLogbook, asBool(), DIVE_ROW, SsiValueType, ADR-0027, aboveZero(), cylinderOf() (+21 more)

### Community 105 - "Community 105"
Cohesion: 0.08
Nodes (27): AssessmentService, AssessmentRouteDeps, BackupRouteDeps, BACKUP_RUN_TASK, BackupService, backupTask(), ADR-0069, PurgeService (+19 more)

### Community 106 - "Community 106"
Cohesion: 0.13
Nodes (30): importsQuery(), ImportContext, ImportFilesButton(), ImportHistory(), ImportList(), ImportNotices(), ImportPanel(), ImportProvider() (+22 more)

### Community 107 - "Community 107"
Cohesion: 0.09
Nodes (30): devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0, @playwright/test@^1.63.0, @testing-library/dom@^10.4.2 (+22 more)

### Community 108 - "Community 108"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 109 - "Community 109"
Cohesion: 0.07
Nodes (24): account, accountRelations, apikey, rateLimit, session, sessionRelations, ADR-0011, ADR-0012 (+16 more)

### Community 110 - "Community 110"
Cohesion: 0.10
Nodes (23): stubDive(), ADR-0015, ADR-0023, ADR-0025, Status, ADR-0056, clearPushRuns(), clearQueue() (+15 more)

### Community 111 - "Community 111"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 112 - "Community 112"
Cohesion: 0.13
Nodes (23): errorMessage(), looksLikeZip(), formatOf(), createImportService(), cancelImport(), createImport(), endWaiting(), expireWaiting() (+15 more)

### Community 113 - "Community 113"
Cohesion: 0.09
Nodes (23): computerEventOf(), ADR-0036, computerEventFromFit(), COMPUTER_EVENT, computerEventFromSuunto(), DECO_MODEL, decoModelFromSuunto, DIVE_MODE (+15 more)

### Community 114 - "Community 114"
Cohesion: 0.13
Nodes (28): answer(), baseFrom(), cylindersAtReserve(), CylinderTerm, densityRatio(), detail(), diveWeight(), EMPTY_BUOYANCY_POINTS (+20 more)

### Community 115 - "Community 115"
Cohesion: 0.08
Nodes (23): Cylinder, DiveView, RevisionView, ADR-0045, upload(), Candidate, Check, Outcome (+15 more)

### Community 116 - "Community 116"
Cohesion: 0.10
Nodes (26): globalSetup(), ADR-0023, accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks() (+18 more)

### Community 117 - "Community 117"
Cohesion: 0.26
Nodes (6): leadAnswer(), EstimateCylinder, PlannedConditions, Suit, leadEstimate(), PersonalizedToolsTest

### Community 118 - "Community 118"
Cohesion: 0.11
Nodes (7): ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport

### Community 119 - "Community 119"
Cohesion: 0.11
Nodes (24): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+16 more)

### Community 120 - "Community 120"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 122 - "Community 122"
Cohesion: 0.16
Nodes (7): InstanceProbeTest, json(), probe(), ProbeCache, ProbeInstances, probeRepository(), ProbeTokens

### Community 123 - "Community 123"
Cohesion: 0.11
Nodes (19): ProblemCode, ResponseState, whenResponseGone(), StoredBlob, PlacedArchive, ANSWER, looksLikeRecipientPrivateKey(), PlacedArchive (+11 more)

### Community 124 - "Community 124"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 125 - "Community 125"
Cohesion: 0.09
Nodes (4): DiveHubApplication, SyncWorker, WorkManagerPeriodicSync, PeriodicSyncScheduler

### Community 126 - "Community 126"
Cohesion: 0.13
Nodes (3): FakeInstances, FakeTokens, MainViewModelTest

### Community 127 - "Community 127"
Cohesion: 0.13
Nodes (21): acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), AppLockProbe (+13 more)

### Community 128 - "Community 128"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 129 - "Community 129"
Cohesion: 0.14
Nodes (24): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+16 more)

### Community 130 - "Community 130"
Cohesion: 0.11
Nodes (3): KeystoreTokenStore, PreferencesInstanceUrlStore, UserCacheStore

### Community 131 - "Community 131"
Cohesion: 0.12
Nodes (7): LeadAnswer, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseLeftOut, ApiDiversIdLeadEstimateGet200ResponseRange, ApiDiversIdLeadEstimateGet200ResponseReserve, ApiDiversIdLeadEstimateGet200ResponseWeightCheck

### Community 133 - "Community 133"
Cohesion: 0.10
Nodes (22): AI_ACCESS_NAME_MAX, aiAccessLog, aiAccessSetting, Access, AccessView, LogEntryView, Scope, STATUS (+14 more)

### Community 134 - "Community 134"
Cohesion: 0.12
Nodes (22): equipmentUse, DiveGear, DueEntry, GearItem, ItemDetailView, ItemEdit, ItemInput, ItemRow (+14 more)

### Community 135 - "Community 135"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 136 - "Community 136"
Cohesion: 0.13
Nodes (6): ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting

### Community 137 - "Community 137"
Cohesion: 0.13
Nodes (6): DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, WaitingPushView

### Community 138 - "Community 138"
Cohesion: 0.16
Nodes (19): PROBLEM_CODES, ADR-0076, ClientVersionRegistry, MIN_CLIENT_VERSIONS, MinClientVersion, ADR-0076, clientRequestSerializer(), ADR-0076 (+11 more)

### Community 139 - "Community 139"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 142 - "Community 142"
Cohesion: 0.09
Nodes (23): dependencies, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1, @fastify/type-provider-typebox@^6.1.0 (+15 more)

### Community 143 - "Community 143"
Cohesion: 0.14
Nodes (18): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+10 more)

### Community 144 - "Community 144"
Cohesion: 0.24
Nodes (19): ambientPressureBar(), bestMixPercent(), DENSITY, depthOfPressure(), eadMetres(), endMetres(), isHypoxic(), metresPerBar() (+11 more)

### Community 145 - "Community 145"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 146 - "Community 146"
Cohesion: 0.15
Nodes (18): qr@0.7.2, cameraAtStart(), cameraFailure(), cameras(), CameraScan(), CameraState, FINAL, ADR-0043 (+10 more)

### Community 148 - "Community 148"
Cohesion: 0.12
Nodes (19): CENTRE_SOURCE_NAME, CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043 (+11 more)

### Community 149 - "Community 149"
Cohesion: 0.18
Nodes (16): ApiToolsBottomTimeGet200ResponseLimits, powInt(), roundTo(), Sac, SacCylinder, SacDive, SacMissing, cylinder_incomplete (+8 more)

### Community 151 - "Community 151"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 152 - "Community 152"
Cohesion: 0.15
Nodes (5): ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits

### Community 153 - "Community 153"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 154 - "Community 154"
Cohesion: 0.10
Nodes (20): better-auth@1.7.7, dependencies, better-auth@1.7.7, @dive-hub/api-client@workspace:*, @fontsource/poppins@^5.3.0, i18next@^26.4.2, @internationalized/date@^3.12.4, lucide-react@^1.51.0 (+12 more)

### Community 155 - "Community 155"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 156 - "Community 156"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 157 - "Community 157"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 158 - "Community 158"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 159 - "Community 159"
Cohesion: 0.19
Nodes (11): LeadInputTest, SuitType, drysuit, none, semidry, skin, wetsuit, SuitUndergarment (+3 more)

### Community 160 - "Community 160"
Cohesion: 0.29
Nodes (5): assembleToolSources(), epochMs(), recentDiveEnd(), summaryOf(), ToolSourcesStoreTest

### Community 161 - "Community 161"
Cohesion: 0.16
Nodes (16): inZone(), NEARBY_DAYS, nearbyOffset(), offsetIn(), placeLocalTime(), Position, ADR-0030, wallClockMs() (+8 more)

### Community 162 - "Community 162"
Cohesion: 0.18
Nodes (3): FakeInstances, FakeTokens, SyncAuthTest

### Community 163 - "Community 163"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 164 - "Community 164"
Cohesion: 0.12
Nodes (16): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+8 more)

### Community 165 - "Community 165"
Cohesion: 0.15
Nodes (13): Sac, SAC_MISSING, SacMissing, sacOfDive(), SacValues, sacValues(), ADR-0033, ADR-0045 (+5 more)

### Community 166 - "Community 166"
Cohesion: 0.23
Nodes (15): prepareEntry(), DENIED_KEY_PARTS, emptyRedactionCounters(), isDeniedKey(), maskString(), redactInner(), redactLogObject(), RedactOptions (+7 more)

### Community 167 - "Community 167"
Cohesion: 0.15
Nodes (10): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, ADR-0068 (+2 more)

### Community 168 - "Community 168"
Cohesion: 0.17
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 170 - "Community 170"
Cohesion: 0.19
Nodes (4): DiveDetailsListView, DiveDetailsListViewCounts, DiveDetailsListViewMonthsItem, DiveDetailsListViewTotals

### Community 171 - "Community 171"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 172 - "Community 172"
Cohesion: 0.12
Nodes (13): clientDiagnostic, clientDiagnosticsSettings, ClientDiagnosticsEntryInput, ClientDiagnosticsEntryView, ClientDiagnosticsError, ClientDiagnosticsUpload, KEEP_NEWEST, MAX_UPLOAD_ENTRIES (+5 more)

### Community 173 - "Community 173"
Cohesion: 0.16
Nodes (13): CIRCUIT, circuitFromFit, DECO_MODEL, decoModelFromFit, DIVE_ALERT, DIVE_MODE, diveModeFromFit, ADR-0015 (+5 more)

### Community 174 - "Community 174"
Cohesion: 0.14
Nodes (13): AL80, EstimateDiveCylinder, LEAD_CASES, leadInput(), LeadVectorInput, planOf(), STEEL_12, ADR-0031 (+5 more)

### Community 175 - "Community 175"
Cohesion: 0.19
Nodes (13): asEstimateInput(), evaluateLeadVectorCase(), LEAD_VECTOR_KERNEL, leadVectorFile(), VECTOR_KERNELS, VectorFile, vectorFile(), assertMatch() (+5 more)

### Community 176 - "Community 176"
Cohesion: 0.21
Nodes (11): buildSupportBundle(), buildTarGz(), json(), ndjson(), octal(), SUPPORT_BUNDLE_FORMAT, SUPPORT_BUNDLE_FORMAT_VERSION, SupportBufferManifest (+3 more)

### Community 177 - "Community 177"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 178 - "Community 178"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 181 - "Community 181"
Cohesion: 0.25
Nodes (11): supportBundlePreviewQuery(), backupDownloadHref(), formatBytes(), isBackupActive(), isBackupDownloadable(), ScheduleProblem, scheduleProblem(), SUPPORT_BUNDLE_DOWNLOAD_HREF (+3 more)

### Community 182 - "Community 182"
Cohesion: 0.13
Nodes (14): concurrency, cancel-in-progress, group, env, RELEASE_TAG, jobs, publish, name (+6 more)

### Community 185 - "Community 185"
Cohesion: 0.15
Nodes (12): EquipmentError, ADR-0034, ADR-0053, checkCost(), checkDates(), checkWindow(), checkResets(), checkSchedule() (+4 more)

### Community 186 - "Community 186"
Cohesion: 0.20
Nodes (12): flag(), idText(), isObject(), numberOrNull(), readSsiSite(), SsiSiteValues, visibilityFor(), WATER_OF_BOW (+4 more)

### Community 187 - "Community 187"
Cohesion: 0.20
Nodes (5): LogRingBuffer, RedactionCounters, SupportBundleInput, SupportBundleManifest, SupportServiceDeps

### Community 188 - "Community 188"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 189 - "Community 189"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 191 - "Community 191"
Cohesion: 0.19
Nodes (12): serviceRecord, serviceRecordSchedule, serviceSchedule, ScheduleDue, equipmentList(), EquipmentListFilter, EquipmentRecordRead, EquipmentScheduleRead (+4 more)

### Community 192 - "Community 192"
Cohesion: 0.28
Nodes (13): createEquipmentService(), assemble(), detailOf(), lastCylinderOf(), managedItem(), recordsOf(), schedulesFor(), schedulesOf() (+5 more)

### Community 193 - "Community 193"
Cohesion: 0.21
Nodes (9): createFitAdapter(), adapter, crossCheck(), decodeOfficial(), privateDir, ownersParts(), parse(), toPart() (+1 more)

### Community 194 - "Community 194"
Cohesion: 0.17
Nodes (6): buddySite(), Dive, OTHER, ADR-0012, ADR-0070, uniqueWord()

### Community 195 - "Community 195"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 196 - "Community 196"
Cohesion: 0.26
Nodes (3): formatSyncTime(), StalenessLabel(), SyncTimeFormatTest

### Community 198 - "Community 198"
Cohesion: 0.26
Nodes (3): DiverView, DiverViewCertifications, DiverViewCertificationsNewest

### Community 199 - "Community 199"
Cohesion: 0.26
Nodes (3): DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 200 - "Community 200"
Cohesion: 0.30
Nodes (11): asText(), compareReadBack(), describe(), isObject(), keyDifferences(), pause(), recording(), runAnswerChecks() (+3 more)

### Community 201 - "Community 201"
Cohesion: 0.18
Nodes (10): LogBufferStats, SupportBundle, ClientDiagnosticsProvider, FailedBackup, FailedJob, HealthSection, iso(), JobsSection (+2 more)

### Community 202 - "Community 202"
Cohesion: 0.18
Nodes (9): AL80, input(), inSameConditions(), SAME_CONDITIONS, STEEL_12, TODAY, ADR-0031, WETSUIT_5 (+1 more)

### Community 203 - "Community 203"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 204 - "Community 204"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 205 - "Community 205"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 206 - "Community 206"
Cohesion: 0.20
Nodes (10): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, ADR-0076, MODULE_IMPORTS, moduleRules (+2 more)

### Community 207 - "Community 207"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 208 - "Community 208"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 209 - "Community 209"
Cohesion: 0.20
Nodes (9): LeadEstimateAnswer, Confidence, EstimateAdjustment, EstimateBasis, EstimateInput, EstimateMissing, LeadEstimate, ADR-0025 (+1 more)

### Community 210 - "Community 210"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 211 - "Community 211"
Cohesion: 0.20
Nodes (10): verify, prerelease, sha, tag, versionCode, versionName, name, outputs (+2 more)

### Community 212 - "Community 212"
Cohesion: 0.20
Nodes (10): with, cache, distribution, fetch-depth, if-no-files-found, java-version, name, path (+2 more)

### Community 213 - "Community 213"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 215 - "Community 215"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 216 - "Community 216"
Cohesion: 0.28
Nodes (7): ProfileSummary, round1(), Series, summariseProfile(), ADR-0035, s(), ADR-0035

### Community 217 - "Community 217"
Cohesion: 0.31
Nodes (5): BufferedLog, createLogRingBuffer(), LOG_BUFFER_CAPACITY_BYTES, LOG_BUFFER_CAPACITY_ENTRIES, LogRingBufferOptions

### Community 218 - "Community 218"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 219 - "Community 219"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 220 - "Community 220"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 223 - "Community 223"
Cohesion: 0.29
Nodes (5): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, EstimateCylinder

### Community 224 - "Community 224"
Cohesion: 0.36
Nodes (6): headers, open(), ownId(), ownRow(), sectionButton(), ADR-0050

### Community 225 - "Community 225"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 226 - "Community 226"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 228 - "Community 228"
Cohesion: 0.29
Nodes (6): itemIds(), ADR-0026, ADR-0034, Usage, UsageDive, UsageRow

### Community 229 - "Community 229"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 230 - "Community 230"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 231 - "Community 231"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 233 - "Community 233"
Cohesion: 0.53
Nodes (4): argumentsText(), isLocal(), setupLines(), ADR-0035

### Community 234 - "Community 234"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 236 - "Community 236"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build

### Community 237 - "Community 237"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 238 - "Community 238"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 242 - "Community 242"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 243 - "Community 243"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 244 - "Community 244"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1303 isolated node(s):** `Sent`, `Reserve`, `RockBottom`, `Reachable`, `Unknown` (+1298 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3683 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **146 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `@tanstack/react-query` connect `Community 9` to `Community 0`, `Community 64`, `Community 6`, `Community 7`, `Community 8`, `Community 106`, `Community 107`, `Community 91`, `Community 15`, `Community 18`, `Community 181`, `Community 23`, `Community 56`, `Community 154`, `Community 27`, `Community 94`?**
  _High betweenness centrality (0.020) - this node is a cross-community bridge._
- **What connects `Sent`, `Reserve`, `RockBottom` to the rest of the system?**
  _1303 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.046627895112743595 - nodes in this community are weakly interconnected._
- **Why does `drizzle-orm` connect `Community 1` to `Community 2`, `Community 3`, `Community 4`, `Community 5`, `Community 134`, `Community 133`, `Community 10`, `Community 11`, `Community 12`, `Community 13`, `Community 142`, `Community 14`, `Community 16`, `Community 19`, `Community 20`, `Community 22`, `Community 31`, `Community 161`, `Community 34`, `Community 39`, `Community 40`, `Community 43`, `Community 172`, `Community 46`, `Community 47`, `Community 53`, `Community 62`, `Community 191`, `Community 66`, `Community 201`, `Community 228`, `Community 109`, `Community 111`?**
  _High betweenness centrality (0.015) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.03146509341199607 - nodes in this community are weakly interconnected._
- **Why does `react` connect `Community 23` to `Community 0`, `Community 64`, `Community 6`, `Community 7`, `Community 8`, `Community 9`, `Community 106`, `Community 107`, `Community 91`, `Community 15`, `Community 146`, `Community 18`, `Community 181`, `Community 56`, `Community 154`, `Community 27`, `Community 94`?**
  _High betweenness centrality (0.012) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.021474926253687316 - nodes in this community are weakly interconnected._