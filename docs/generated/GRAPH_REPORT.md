# Graph Report - dive_hub  (2026-11-24)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 12612 nodes · 39014 edges · 408 communities (235 shown, 173 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 914 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `db273c1e`
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
- Community 239
- Community 240
- Community 241
- Community 242
- Community 243
- Community 244
- Community 245
- Community 246
- Community 247
- Community 248
- Community 249
- Community 250
- Community 251
- Community 252
- Community 253
- Community 254
- Community 255
- Community 256
- Community 257
- Community 258
- Community 259
- Community 260
- Community 262
- Community 263
- Community 264
- Community 265
- Community 266
- Community 267
- Community 268
- Community 269
- Community 274

## God Nodes (most connected - your core abstractions)
1. `Notice()` - 211 edges
2. `Button()` - 204 edges
3. `useErrorText()` - 176 edges
4. `useDisplay()` - 165 edges
5. `drizzle-orm` - 150 edges
6. `unwrap()` - 143 edges
7. `DiveStore` - 139 edges
8. `Muted()` - 139 edges
9. `createTestDatabase()` - 112 edges
10. `TestDatabase` - 111 edges

## Surprising Connections (you probably didn't know these)
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/server/package.json → package.json
- `typescript@7.0.2` --declares--> `typescript`  [EXTRACTED]
  apps/web/package.json → package.json
- `typescript@5.9.3` --declares--> `typescript`  [EXTRACTED]
  packages/api-client/package.json → package.json
- `Removal` --references--> `ProviderServiceError`  [EXTRACTED]
  apps/server/src/providers/push/push-service.ts → apps/server/src/providers/registry.ts
- `AuthOptions` --references--> `Db`  [EXTRACTED]
  apps/server/src/auth/auth.ts → apps/server/src/db/client.ts

## Import Cycles
- None detected.

## Communities (408 total, 173 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.02
Nodes (206): auth, hashPassword(), BackupServiceDeps, createServices(), ServiceParts, ADR-0027, ADR-0030, ADR-0061 (+198 more)

### Community 1 - "Community 1"
Cohesion: 0.04
Nodes (219): ChangePassword(), DisplaySettings(), Sessions(), ADR-0080, useDescribeAgent(), AdminSettings(), ClientDiagnosticsSetting(), DeleteUserDialog() (+211 more)

### Community 2 - "Community 2"
Cohesion: 0.02
Nodes (178): acquireAppLock(), APP_LOCK_DIR, APP_LOCK_HEARTBEAT_MS, APP_LOCK_NAME, APP_LOCK_STALE_MS, AppLock, appLockPath(), AppLockProbe (+170 more)

### Community 3 - "Community 3"
Cohesion: 0.03
Nodes (145): user, accountListEntry, changeEvent, dive, diveFinding, diver, revision, forget() (+137 more)

### Community 4 - "Community 4"
Cohesion: 0.02
Nodes (193): AiAccessLogEntry, AiAccessView, invitationLinkQuery(), InvitationView, passwordResetQuery(), sessionsQuery(), SessionView, setupRestoreArchivesQuery() (+185 more)

### Community 5 - "Community 5"
Cohesion: 0.02
Nodes (195): @internationalized/date@^3.12.4, cylinderCatalogueQuery(), bottomTimeRule(), defined(), LeadEstimate, leadEstimateQuery(), ToolsBottomTime, toolsBottomTimeQuery() (+187 more)

### Community 6 - "Community 6"
Cohesion: 0.02
Nodes (38): DiveRow(), Figures(), RowFacts(), EmptyState(), FactRow(), NoteText(), StackedNoteText(), Radius (+30 more)

### Community 7 - "Community 7"
Cohesion: 0.03
Nodes (104): Section(), cylinderLine(), cylinderPressures(), Cylinders(), Direction, Back, Left, Right (+96 more)

### Community 8 - "Community 8"
Cohesion: 0.02
Nodes (176): ComputerChoice, device, DiveImportMode, DiveKind, equipmentItem, ProviderImportPlan, providerSiteData, UtcOffsetSource (+168 more)

### Community 9 - "Community 9"
Cohesion: 0.02
Nodes (142): apikey, buddySuggestion, diveAssessment, jointDive, recordingEvent, parseHint(), Assessment, FindingView (+134 more)

### Community 10 - "Community 10"
Cohesion: 0.03
Nodes (147): label(), Me, AcceptedBuddySuggestionView, AnyLogbookCheck, BuddySuggestionOverlapView, BuddySuggestionPreviewView, buddySuggestionQuery(), buddySuggestionsQuery() (+139 more)

### Community 11 - "Community 11"
Cohesion: 0.02
Nodes (135): CertificationError, createCertificationService(), cleaned(), requireManagedDiver(), rowOrThrow(), isCalendarDate(), participant, providerGoneDive (+127 more)

### Community 12 - "Community 12"
Cohesion: 0.02
Nodes (151): downsampleMinMax(), SAC_MISSING, SacMissing, ADR-0033, ADR-0045, Cylinder, AiAccessRouteDeps, AccessRefusal (+143 more)

### Community 13 - "Community 13"
Cohesion: 0.03
Nodes (147): diversQuery(), diveNeighboursQuery(), diveQuery(), divesQuery(), DiveSummary, DiveValues, LogbookPage, OverridableField (+139 more)

### Community 14 - "Community 14"
Cohesion: 0.03
Nodes (138): EMPTY_SITE_IMPORT_COUNTS, ImportedValues, SiteImportCounts, StoredConflictField, ADR-0021, ADR-0025, ADR-0053, diverExternalId (+130 more)

### Community 15 - "Community 15"
Cohesion: 0.02
Nodes (135): Tx, continuationAppend, jointDiveDismissal, JointFillField, sampleSeries, defined(), Joined, joinedSummary() (+127 more)

### Community 16 - "Community 16"
Cohesion: 0.02
Nodes (132): lead, createSsiAdapter(), buddyListOf(), called(), confirmed(), keepRecord(), logbookOf(), open() (+124 more)

### Community 17 - "Community 17"
Cohesion: 0.02
Nodes (131): Auth, CentreService, CentreRouteDeps, LEAD_PLACEMENTS, UTC_OFFSET_SOURCES, WEIGHTING_FELT, DiverService, DiverRouteDeps (+123 more)

### Community 18 - "Community 18"
Cohesion: 0.04
Nodes (126): AppDeps, buildApp(), Server, ADR-0014, ADR-0035, ADR-0066, ADR-0076, assessmentRoutes() (+118 more)

### Community 19 - "Community 19"
Cohesion: 0.03
Nodes (18): AndroidDiveStore, DiveStore, toBoolean(), toLong(), toModel(), BodyWeight, Diver, DiveSite (+10 more)

### Community 20 - "Community 20"
Cohesion: 0.03
Nodes (100): dependencies, lucide-react@^1.51.0, qr@0.7.2, react@^19.3.0, react-aria-components@^1.21.1, react-i18next@^17.0.15, @tanstack/react-query@^5.104.0, uplot@^1.6.32 (+92 more)

### Community 21 - "Community 21"
Cohesion: 0.04
Nodes (17): AppContainer, MainActivity, DiveEditViewModel, followStoreWrites(), ScanErrors, SettingsViewModel, LoadingsUiState, LoadingsViewModel (+9 more)

### Community 22 - "Community 22"
Cohesion: 0.04
Nodes (117): ConnectionRow, diveFingerprint, WaitingPushOptions, waitingOf(), audienceOfConnection(), audienceOfDive(), Hint, HINT_KINDS (+109 more)

### Community 23 - "Community 23"
Cohesion: 0.02
Nodes (117): CENTRE_SOURCE_NAME, CentreDisplayNames, CentreError, CentreExternalId, CentreSource, createCentreService(), holder(), liveSite() (+109 more)

### Community 24 - "Community 24"
Cohesion: 0.05
Nodes (9): DiveHubTopBar(), Spacing, DiveHubTheme(), SectionTest, DiveHubTopBarTest, LoadingScreenshotTest, PlanningSacSource, default (+1 more)

### Community 25 - "Community 25"
Cohesion: 0.06
Nodes (77): Formats, formatSyncTime(), GasPlanRoute(), GasPlanScreen(), PlanAnswer(), PlanBody(), PlanDiverCard(), PlanDiverResult() (+69 more)

### Community 26 - "Community 26"
Cohesion: 0.06
Nodes (13): DiveDetailScreenshotTest, DiveViewCylindersItem, DiveViewCylindersItemGas, DiveViewFromRecording, DiveViewFromRecordingStartsAt, DiveViewLeadItem, DiveViewPosition, DiveViewRecordingsItemSummaryGasesItem (+5 more)

### Community 27 - "Community 27"
Cohesion: 0.04
Nodes (23): logbookDiveItemIndex(), LogbookScreenTest, DiveListView, DiveListViewCounts, DiveListViewDivesItem, DiveListViewDivesItemGasesItem, DiveListViewDivesItemParticipantsItem, DiveListViewDivesItemSite (+15 more)

### Community 28 - "Community 28"
Cohesion: 0.03
Nodes (89): connection, ArchiveEntry, ArchiveLimitError, ArchiveLimits, DEFAULT_LIMITS, eachFile(), readEntry(), ADR-0044 (+81 more)

### Community 29 - "Community 29"
Cohesion: 0.07
Nodes (8): RevisionFollowTest, LeadEstimateViewModelTest, DiveViewSuitDraftSource, numberOrNull(), Dive, refreshDetailsPage(), refreshDiveId(), refreshDiveStartsAt()

### Community 30 - "Community 30"
Cohesion: 0.03
Nodes (90): react-dom@^19.3.0, setupQuery(), ImportView, App(), BackupsPage, CentrePage, CentresPage, currentRoute() (+82 more)

### Community 31 - "Community 31"
Cohesion: 0.03
Nodes (85): replyCertificationError, STATUS, CertificationView, CertificationViewSchema, Day, ADR-0050, CardParams, CertificationRouteDeps (+77 more)

### Community 32 - "Community 32"
Cohesion: 0.04
Nodes (81): ImportArea, ImportErrorCode, ImportFound, importOriginal, ImportOutcome, errorMessage(), looksLikeZip(), FileFormat (+73 more)

### Community 33 - "Community 33"
Cohesion: 0.03
Nodes (74): cylinder, diveLease, providerConflict, push, pushRun, waitingPush, kept(), PURGE_AFTER_DAYS (+66 more)

### Community 34 - "Community 34"
Cohesion: 0.04
Nodes (48): GasPlanAnswer, ApiToolsGasPlanPost200Response, ApiToolsGasPlanPost200ResponseDiversItem, ApiToolsGasPlanPost200ResponseDiversItemRockBottomItem, ApiToolsGasPlanPost200ResponseDiversItemSacLpm, ApiToolsGasPlanPost200ResponseGas, ApiToolsGasPlanPost200ResponseRule, ApiToolsGasPlanPost200ResponseSegmentsItem (+40 more)

### Community 35 - "Community 35"
Cohesion: 0.04
Nodes (73): invitation, userRole, AttemptLimit, clientKey(), limitAttempts(), TOKEN_ATTEMPTS, ADR-0061, Problem (+65 more)

### Community 36 - "Community 36"
Cohesion: 0.04
Nodes (82): CertificationService, AssessmentRefresh, ADR-0036, ADR-0053, CandidateRouteDeps, CandidateView, Decided, DiveBrief (+74 more)

### Community 37 - "Community 37"
Cohesion: 0.06
Nodes (80): allButDive(), continuationCandidateQuery(), deletedDivesOn(), DiveView, restorePreviewQuery(), keys, diveProvidersQuery(), GoneDivesOfConnection (+72 more)

### Community 38 - "Community 38"
Cohesion: 0.06
Nodes (22): QuietButton(), ChangesList(), ConflictChoice(), ConflictEntry(), ConflictsRoute(), ConflictsScreen(), diveTitle(), FieldDecision() (+14 more)

### Community 39 - "Community 39"
Cohesion: 0.05
Nodes (7): DiveEditViewModelTest, CountingDispatcher, LogbookViewModelTest, ApiClient, ApiErrorBody, LogbookRepository, ApiClientTest

### Community 40 - "Community 40"
Cohesion: 0.04
Nodes (67): AssessmentError, AssessmentView, CHANNELS, createAssessmentService(), assessRows(), readSeries(), refreshDiver(), replaceFindings() (+59 more)

### Community 41 - "Community 41"
Cohesion: 0.04
Nodes (14): PreferencesInstanceUrlStore, SettingsDiagnosticsState, SettingsUiState, FakeDiagnostics, FakeInstances, SettingsRouteTest, SettingsViewModelTest, ReportProblemResult (+6 more)

### Community 42 - "Community 42"
Cohesion: 0.05
Nodes (66): SiteView, ADR-0014, ADR-0020, ADR-0027, ADR-0030, countryCodes(), countryName(), countryOptions() (+58 more)

### Community 43 - "Community 43"
Cohesion: 0.05
Nodes (26): DiveDetailViewModel, NeighboursRead, ProfileRead, LogbookUiState, LogbookViewModel, DiveRepository, neighboursPath(), countOf() (+18 more)

### Community 44 - "Community 44"
Cohesion: 0.04
Nodes (55): ParticipantRole, PushLeftOut, ActionContext, Capabilities, Direction, DiveExport, DiveListing, FIND_BY (+47 more)

### Community 45 - "Community 45"
Cohesion: 0.03
Nodes (69): ADR-0025, WaterMismatch, IfMatchHeaders, ifMatchVersion(), missingVersion(), ADR-0083, USED, usesIfMatch() (+61 more)

### Community 46 - "Community 46"
Cohesion: 0.07
Nodes (64): assessmentQuery(), AssessmentView, samplesQuery(), AsideItem(), AssessmentContext, AssessmentPanel(), AssessmentProvider(), AssessmentState (+56 more)

### Community 47 - "Community 47"
Cohesion: 0.06
Nodes (21): WriteDrain, Applied, Conflicted, Drained, Dropped, Empty, EntryOutcome, Failed (+13 more)

### Community 48 - "Community 48"
Cohesion: 0.04
Nodes (64): pushRunItem, PushRunLeftOut, DIVE_ONLY, DiveListFilter, diveListFilter(), DiveOnly, FINDINGS_SHOWN, NO_RECORDING (+56 more)

### Community 49 - "Community 49"
Cohesion: 0.05
Nodes (26): DiveDetailRoute(), DiveEditScreenRoute(), DiveHubApp(), DiveHubScreens, ErrorText(), Gate(), InstanceForm(), InstanceStatus() (+18 more)

### Community 50 - "Community 50"
Cohesion: 0.04
Nodes (63): sacOfDive(), sacValues(), BottomTimeInput, Conditions, GasMix, GasPlanInput, GasRule, LoadingDive (+55 more)

### Community 51 - "Community 51"
Cohesion: 0.06
Nodes (66): ANDROID_UI, AREAS, baseCandidates(), carryPassed(), changedSince(), chooseBase(), countingBase(), currentBranch() (+58 more)

### Community 52 - "Community 52"
Cohesion: 0.04
Nodes (53): graphile-worker@^0.18.0, AssessmentService, AssessmentRouteDeps, BackupRouteDeps, BACKUP_RUN_TASK, BackupService, backupTask(), ADR-0069 (+45 more)

### Community 53 - "Community 53"
Cohesion: 0.05
Nodes (42): captureDiagnostic(), captureError(), cleanContext(), clearDiagnostics(), CLIENT_NAME, CLIENT_VERSION, DiagnosticEntry, diagnosticFromError() (+34 more)

### Community 54 - "Community 54"
Cohesion: 0.07
Nodes (53): BottomTime, bottomTime(), Limit, ADR-0032, ADR-0033, ADR-0071, ambientPressureBar(), bestMixPercent() (+45 more)

### Community 55 - "Community 55"
Cohesion: 0.11
Nodes (7): DiveEditScreenshotTest, DiveEditDraft, DiveEditStart, DiveEditSuit, DiveEditWeighting, toDraft(), DiveEditDraftTest

### Community 56 - "Community 56"
Cohesion: 0.07
Nodes (15): ScanViewModelTest, ApiDiveCentresPostRequest, ApiDiveCentresPostRequestExternalIdsItem, PendingWrite, PendingWriteKind, Action, Patch, PendingWriteStatus (+7 more)

### Community 57 - "Community 57"
Cohesion: 0.05
Nodes (57): ConflictChoices, ConflictFieldView, showDiveCodes(), focusFirstIn(), LiveArrivals, Page, useLiveArrivals(), onLiveEvent() (+49 more)

### Community 58 - "Community 58"
Cohesion: 0.09
Nodes (30): ApiToolsBottomTimeGet200ResponseUsed, ApiToolsBottomTimeGet200ResponseUsedAltitudeM, ApiToolsBottomTimeGet200ResponseUsedGasRule, ApiToolsBottomTimeGet200ResponseUsedGasRuleValue, ApiToolsBottomTimeGet200ResponseUsedGfHigh, ApiToolsBottomTimeGet200ResponseUsedSacLpm, ApiToolsBottomTimeGet200ResponseUsedWater, BottomTimeCylinder (+22 more)

### Community 59 - "Community 59"
Cohesion: 0.07
Nodes (58): Answer, answer(), baseFrom(), BodyWeight, Confidence, high, low, medium (+50 more)

### Community 60 - "Community 60"
Cohesion: 0.12
Nodes (24): Body, BrokenBody, detailJson(), diveDetailsPage(), diveIdOf(), diveListItem(), divePage(), diverJson() (+16 more)

### Community 61 - "Community 61"
Cohesion: 0.10
Nodes (10): FakeCache, FakeCachedMe, FakeInstances, FakeQueue, FakeTokens, Harness, json(), SessionRepositoryTest (+2 more)

### Community 62 - "Community 62"
Cohesion: 0.06
Nodes (51): CylinderMaterial, CatalogueCylinder, CYLINDER_CATALOGUE, ADR-0031, answer(), baseFrom(), cylindersAtReserve(), CylinderTerm (+43 more)

### Community 63 - "Community 63"
Cohesion: 0.06
Nodes (36): headers, ADR-0031, headers, ADR-0043, headers, open(), ownId(), ownRow() (+28 more)

### Community 64 - "Community 64"
Cohesion: 0.06
Nodes (9): MainViewModel, Unreachable, IntHolder, MainViewModelPendingWarningTest, WarningCache, WarningCachedMe, WarningInstances, WarningQueue (+1 more)

### Community 65 - "Community 65"
Cohesion: 0.04
Nodes (44): account, accountRelations, rateLimit, session, sessionRelations, ADR-0011, ADR-0012, ADR-0035 (+36 more)

### Community 66 - "Community 66"
Cohesion: 0.05
Nodes (13): ApiClientDiagnosticsStatusGet200Response, ApiDiverBodyWeightsGet200ResponseItem, ApiDiversFromCodePostRequest, ApiDiversIdLeadEstimateGet200ResponseBasis, ApiDiversIdLeadEstimateGet200ResponseBasisDivesItem, ApiMeGet200ResponseOwnDiver, ApiToolsBottomTimeGet200ResponseResidualN2, ApiVerificationCodesReadPostRequest (+5 more)

### Community 67 - "Community 67"
Cohesion: 0.07
Nodes (17): ApiMeGet200Response, ApiMeGet200ResponsePreferences, UserView, InstanceUrl, HealthView, InstanceProbeResult, Loading, PendingChanges (+9 more)

### Community 68 - "Community 68"
Cohesion: 0.06
Nodes (45): getDiverLoadings, HINT, Input, LoadingsInput, planGasAndBottomTime, ADR-0032, ADR-0035, Used() (+37 more)

### Community 69 - "Community 69"
Cohesion: 0.11
Nodes (5): Complete, SessionEnded, Stopped, SyncEngine, SyncResult

### Community 70 - "Community 70"
Cohesion: 0.07
Nodes (44): ReportProblemSettings(), SecuritySettings(), aiAccessSettingQuery(), BackupRunView, backupSettingsQuery(), BackupSettingsView, backupsQuery(), SiteDataView (+36 more)

### Community 71 - "Community 71"
Cohesion: 0.08
Nodes (14): gasPlanAnswer(), GasPlanDiverInput, GasPlanDiverInputValues, GasPlanInputs, GasPlanLevelInput, GasPlanViewModel, number(), Answers (+6 more)

### Community 72 - "Community 72"
Cohesion: 0.06
Nodes (42): createServer(), createAuth(), loadAuthSecret(), workerDeps(), Config, loadConfig(), ADR-0011, ADR-0021 (+34 more)

### Community 73 - "Community 73"
Cohesion: 0.09
Nodes (49): Queryable, logbookCheckAnswer, logbookCheckDiveAnswer, keepApart(), keepDive(), keepRestored(), ADR-0038, agreeInDepthAndDuration() (+41 more)

### Community 74 - "Community 74"
Cohesion: 0.08
Nodes (20): InkButton(), ChangeBullet(), changeField(), ChangeLine(), ChooseRow(), Dismiss(), OutcomeText(), ReadResult() (+12 more)

### Community 75 - "Community 75"
Cohesion: 0.08
Nodes (42): SsiRecord, contextOf(), buildCreateRecord(), buildDeleteRecord(), buildUpdateRecord(), DiveForSsi, emptyRecord(), keysOf() (+34 more)

### Community 76 - "Community 76"
Cohesion: 0.09
Nodes (9): ScanUiState, ScanViewModel, ScanScreenshotTest, ScanResult, ScanStatus, Created, Existing, Failed (+1 more)

### Community 77 - "Community 77"
Cohesion: 0.08
Nodes (9): LeadCylinderInput, LeadInputs, PersonalizedScreenshotTest, ApiDiversIdLeadEstimateGet200ResponseMissingItem, ApiDiversIdLoadingsGet200Response, ApiDiversIdLoadingsGet200ResponseBasis, ApiDiversIdLoadingsGet200ResponseCns, ApiDiversIdLoadingsGet200ResponseN2 (+1 more)

### Community 78 - "Community 78"
Cohesion: 0.08
Nodes (44): noFlyAfter(), RecordingSummary, RecordingFacts, residualSeedOf(), depthSamples(), DiverDiveRows, diverDiveRows(), DiveRow (+36 more)

### Community 79 - "Community 79"
Cohesion: 0.06
Nodes (42): ADR-0036, ADR-0063, ADR-0038, ADR-0048, ADR-0062, AiAccess, assessedDive(), AssessedFinding (+34 more)

### Community 80 - "Community 80"
Cohesion: 0.10
Nodes (23): waterName(), BottomTime, bottomTime(), BottomTimeLimit, gas, ndl, oxygen, BottomTimeLimits (+15 more)

### Community 82 - "Community 82"
Cohesion: 0.15
Nodes (9): FeedEntry, RefreshData, refreshDetailJson(), refreshDiveListItem(), refreshDiverJson(), refreshRecordingJson(), refreshSiteJson(), resetRefreshData() (+1 more)

### Community 83 - "Community 83"
Cohesion: 0.05
Nodes (41): adminRole, AI_ACCESS_NAME_MAX, AI_ACCESS_PREFIX, AI_ACCESS_RATE, AuthOptions, CLIENT_IP_HEADER, PUBLIC_AUTH_PATHS, ADR-0011 (+33 more)

### Community 84 - "Community 84"
Cohesion: 0.05
Nodes (39): CYLINDER_MATERIALS, SUIT_TYPES, SUIT_UNDERGARMENTS, CertificationSummary, DiverCertifications, NO_CERTIFICATIONS, ADR-0050, ADR-0053 (+31 more)

### Community 85 - "Community 85"
Cohesion: 0.05
Nodes (42): headers, ADR-0035, AREA_OF, areaOf(), areas, capture(), findings, headers (+34 more)

### Community 86 - "Community 86"
Cohesion: 0.04
Nodes (44): env, runs-on, services, steps, timeout-minutes, concurrency, cancel-in-progress, group (+36 more)

### Community 87 - "Community 87"
Cohesion: 0.05
Nodes (35): CertificationAccount, CertificationProviders, RemoteCertification, ADR-0050, ADR-0053, CERTIFICATION_IMAGE_ATTEMPTS, CERTIFICATION_IMAGE_TASK, CertificationExpiry (+27 more)

### Community 89 - "Community 89"
Cohesion: 0.09
Nodes (36): EstimateDive, EstimateDiveCylinder, WeightingFeedback, WeightingFelt, right, too_heavy, too_light, dayNumber() (+28 more)

### Community 90 - "Community 90"
Cohesion: 0.09
Nodes (16): LogbookPaging, AscentBand, SketchCurve, SketchGeometry, sketchGeometry(), SketchPoint, SketchProfile, sketchScale() (+8 more)

### Community 91 - "Community 91"
Cohesion: 0.08
Nodes (37): args, browser, browserFailed(), browserListStep(), browserSteps(), byHand(), CACHE, code (+29 more)

### Community 92 - "Community 92"
Cohesion: 0.06
Nodes (7): KeystoreTokenStore, PreferencesCachedMeStore, UserCacheStore, FakeCachedMe, CachedMeStore, TokenStore, UserScopedStore

### Community 93 - "Community 93"
Cohesion: 0.08
Nodes (4): QrImageDecoder, LauncherIconTest, SplashTheme, SplashThemeTest

### Community 94 - "Community 94"
Cohesion: 0.06
Nodes (29): applyHints(), bump(), changedListeners, ChannelMessage, healthListeners, HINT_KINDS, hintedDives, HintKind (+21 more)

### Community 95 - "Community 95"
Cohesion: 0.05
Nodes (39): components, $defs, operations, paths, ADR-0014, ADR-0020, ADR-0022, ADR-0024 (+31 more)

### Community 96 - "Community 96"
Cohesion: 0.11
Nodes (23): AvatarCircle(), Error(), FilterAction(), Filters(), FilterSheet(), Loading(), LogbookDiveRow(), LogbookList() (+15 more)

### Community 97 - "Community 97"
Cohesion: 0.16
Nodes (7): toJson(), DiveMergeTest, withValue(), diveObject(), participant(), participants(), putDive()

### Community 98 - "Community 98"
Cohesion: 0.10
Nodes (31): DangerButton(), SecondaryButton(), dateText(), DestructiveActions(), DiveEditScreen(), EditChip(), EditForm(), editNumber() (+23 more)

### Community 99 - "Community 99"
Cohesion: 0.10
Nodes (33): SENT_KEYS, diveModeOfSsi(), keepTraining(), ssiDiveType(), ssiInteger(), ADR-0057, ComparedField, dataset() (+25 more)

### Community 100 - "Community 100"
Cohesion: 0.17
Nodes (37): withBuddies(), APP_KEYS, appRow(), byId(), changedKeys(), deleteTestEntries(), entryNamed(), flag() (+29 more)

### Community 101 - "Community 101"
Cohesion: 0.06
Nodes (37): concurrency, cancel-in-progress, group, if, runs-on, steps, timeout-minutes, jobs (+29 more)

### Community 102 - "Community 102"
Cohesion: 0.16
Nodes (14): Water(), DiveDetailText, conflictCylindersText(), conflictCylinderText(), conflictFeedbackText(), conflictFieldLabel(), conflictFieldValue(), conflictLeadText() (+6 more)

### Community 103 - "Community 103"
Cohesion: 0.11
Nodes (34): AscentBand, ascentBandsOf(), ascentRate(), assessProfile(), ceiling(), descentRate(), judgedParts(), lastMetres() (+26 more)

### Community 104 - "Community 104"
Cohesion: 0.05
Nodes (36): depends_on, environment, image, ports, restart, volumes, condition, environment (+28 more)

### Community 105 - "Community 105"
Cohesion: 0.11
Nodes (8): AppNavHost(), AppScreens, clearSavedTabStates(), isShowing(), leaveDiveForLogbook(), MainShell(), navigateToTab(), tabOf()

### Community 107 - "Community 107"
Cohesion: 0.08
Nodes (32): AIR, compressibility(), Cubic, HE, minusOne(), N2, O2, pressureForLitres() (+24 more)

### Community 108 - "Community 108"
Cohesion: 0.07
Nodes (28): headers, ADR-0030, ADR-0040, conflictForLena(), diveWithoutRecording(), editInSsi(), externalDiver(), importLena() (+20 more)

### Community 109 - "Community 109"
Cohesion: 0.08
Nodes (33): @dive-hub/api-client@workspace:*, @fontsource/poppins@^5.3.0, devDependencies, axe-core@^4.13.0, @axe-core/playwright@^4.13.0, jsdom@^30.1.3, @playwright/cli@^0.1.22, playwright-core@^1.63.0 (+25 more)

### Community 110 - "Community 110"
Cohesion: 0.08
Nodes (16): HINT_CHANNEL, createHub(), HEARTBEAT_MS, LiveHub, MAX_STREAMS_PER_USER, ADR-0066, createListener(), connect() (+8 more)

### Community 111 - "Community 111"
Cohesion: 0.13
Nodes (33): acceptedFormat(), Answer, Ask, dateTime(), deleteAndCheck(), describe(), findNewId(), Format (+25 more)

### Community 112 - "Community 112"
Cohesion: 0.10
Nodes (26): ADR-0051, Status, ADR-0056, ADR-0024, ADR-0043, clearQueue(), conflictAtSsi(), connectSsi() (+18 more)

### Community 113 - "Community 113"
Cohesion: 0.10
Nodes (25): Estimate, isoUtc(), LoadingBasis, LoadingDive, N2Loadings, N2UnavailableReason, no_profile, no_recent_dive (+17 more)

### Community 114 - "Community 114"
Cohesion: 0.13
Nodes (7): GateCachedMe, GateCall, GateClient, GateInstances, GateServer, GateTokens, WriteExitGateTest

### Community 115 - "Community 115"
Cohesion: 0.09
Nodes (8): ApiDiveSitesGet200Response, SiteView, SiteViewExternalIdsItem, SiteViewExternalIdsItemAttribution, SiteViewExternalIdsItemOffered, SiteViewExternalIdsItemOfferedPosition, SiteViewExternalIdsItemPrivateTo, SiteViewPosition

### Community 116 - "Community 116"
Cohesion: 0.12
Nodes (9): Connected, Hint, HintFrame, LiveEvent, LiveHint, SseLiveHints, LiveHintsTest, SseLiveHintsEngineTest (+1 more)

### Community 117 - "Community 117"
Cohesion: 0.09
Nodes (26): AL80, asEstimateInput(), EstimateDiveCylinder, evaluateLeadVectorCase(), LEAD_CASES, LEAD_VECTOR_KERNEL, leadInput(), leadVectorFile() (+18 more)

### Community 118 - "Community 118"
Cohesion: 0.06
Nodes (19): addedTotal, bands, bc, br, bt, carryTotal, files, fmt() (+11 more)

### Community 119 - "Community 119"
Cohesion: 0.24
Nodes (7): leadAnswer(), EstimateCylinder, PlannedConditions, Suit, currentLoadings(), leadEstimate(), PersonalizedToolsTest

### Community 121 - "Community 121"
Cohesion: 0.13
Nodes (8): InstanceProbeTest, json(), probe(), ProbeCache, ProbeCachedMe, ProbeInstances, probeRepository(), ProbeTokens

### Community 123 - "Community 123"
Cohesion: 0.10
Nodes (27): equipmentUse, ScheduleDue, DiveGear, DueEntry, ensureItemForDevice(), GearItem, ItemDetailView, ItemEdit (+19 more)

### Community 124 - "Community 124"
Cohesion: 0.07
Nodes (12): buddySite(), Dive, OTHER, PendingOffer, ADR-0012, ADR-0070, Dive, OTHER (+4 more)

### Community 125 - "Community 125"
Cohesion: 0.11
Nodes (4): FakeCache, FakeInstances, FakeTokens, MainViewModelTest

### Community 126 - "Community 126"
Cohesion: 0.09
Nodes (26): devDependencies, auth@1.7.7, drizzle-kit@^0.31.11, @garmin/fitsdk@^21.217.0, @modelcontextprotocol/client@^2.3.1, tsup@^8.5.1, tsx@^4.23.15, @types/node@^22.20.4 (+18 more)

### Community 127 - "Community 127"
Cohesion: 0.09
Nodes (23): computerEventOf(), ADR-0036, computerEventFromFit(), COMPUTER_EVENT, computerEventFromSuunto(), DECO_MODEL, decoModelFromSuunto, DIVE_MODE (+15 more)

### Community 128 - "Community 128"
Cohesion: 0.14
Nodes (25): bar(), celsius(), Context, contextOf(), declaredTankDataOf(), Dialect, DIVE_MODES, divesOf() (+17 more)

### Community 129 - "Community 129"
Cohesion: 0.10
Nodes (26): globalSetup(), ADR-0023, accept(), addCentre(), addCrowd(), dataFile(), importEgypt(), makeLinks() (+18 more)

### Community 130 - "Community 130"
Cohesion: 0.11
Nodes (7): ProviderSummaryData, ProviderSummaryDataBuddies, ProviderSummaryDataBuddiesImport, ProviderSummaryDataCertifications, ProviderSummaryDataCertificationsImport, ProviderSummaryDataDiveSites, ProviderSummaryDataDiveSitesImport

### Community 131 - "Community 131"
Cohesion: 0.16
Nodes (25): DiveOrigin, asBool(), DIVE_ROW, SsiValueType, ADR-0027, aboveZero(), cylinderOf(), deviceOf() (+17 more)

### Community 132 - "Community 132"
Cohesion: 0.13
Nodes (23): diveWith(), ADR-0028, ADR-0043, resetSource(), forget(), headers, ADR-0043, Site (+15 more)

### Community 133 - "Community 133"
Cohesion: 0.13
Nodes (23): REPO_MAP_STALE, byText(), checkRepoMap(), definitionsOf(), generateRepoMap(), isDeclaredName(), KIND_WEIGHT, localNamesOf() (+15 more)

### Community 136 - "Community 136"
Cohesion: 0.19
Nodes (10): Body, buddyAnswer(), centreAnswer(), centreView(), diverView(), Offline, ScanCall, ScanHarness (+2 more)

### Community 137 - "Community 137"
Cohesion: 0.07
Nodes (27): dependencies, better-auth@1.7.7, @better-auth/api-key@1.7.7, drizzle-orm@^0.45.3, fastify@^5.12.5, @fastify/multipart@^10.1.2, @fastify/static@^10.1.5, @fastify/swagger@^9.9.1 (+19 more)

### Community 138 - "Community 138"
Cohesion: 0.12
Nodes (23): addMonths(), CountDue, countReason(), dimensionState(), divesLeft(), divesReached(), DUE_STATES, DueInput (+15 more)

### Community 139 - "Community 139"
Cohesion: 0.11
Nodes (21): BOM, Json, num(), obj(), positive(), scaled(), suuntoFitKey(), SuuntoJsonAdapter (+13 more)

### Community 140 - "Community 140"
Cohesion: 0.18
Nodes (25): ADR-0060, className(), escape(), fileFor(), flattenSchema(), generateModels(), registerClass(), registerUnionClass() (+17 more)

### Community 141 - "Community 141"
Cohesion: 0.19
Nodes (9): DiveViewRecordingsItemSummary, Cylinder, summaryOf(), assembleToolSources(), epochMs(), recentDiveEnd(), sacOf(), summaryOf() (+1 more)

### Community 142 - "Community 142"
Cohesion: 0.09
Nodes (19): Cylinder, Dive, getDive(), headers, setCylinders(), ADR-0045, getDive(), headers (+11 more)

### Community 143 - "Community 143"
Cohesion: 0.12
Nodes (7): LeadAnswer, ApiDiversIdLeadEstimateGet200Response, ApiDiversIdLeadEstimateGet200ResponseAdjustmentsItem, ApiDiversIdLeadEstimateGet200ResponseLeftOut, ApiDiversIdLeadEstimateGet200ResponseRange, ApiDiversIdLeadEstimateGet200ResponseReserve, ApiDiversIdLeadEstimateGet200ResponseWeightCheck

### Community 144 - "Community 144"
Cohesion: 0.12
Nodes (6): ApiClientDiagnosticsPostRequest, ApiClientDiagnosticsPostRequestClient, ApiClientDiagnosticsPostRequestEntriesItem, DiagnosticHygiene, Diagnostics, Failed

### Community 145 - "Community 145"
Cohesion: 0.14
Nodes (23): LeadPlacement, SuitType, SuitUndergarment, WeightingFelt, DiveRow, LeadEntry, setLead(), setSuit() (+15 more)

### Community 146 - "Community 146"
Cohesion: 0.14
Nodes (23): input, ADR-0039, path, targets, baselineEntries(), baselineGrowth(), BASELINES, LINT_CONFIG (+15 more)

### Community 147 - "Community 147"
Cohesion: 0.08
Nodes (24): concurrency, cancel-in-progress, group, jobs, map, permissions, runs-on, steps (+16 more)

### Community 149 - "Community 149"
Cohesion: 0.21
Nodes (4): GasPlanScreenshotTest, GasPlanToolDiver, GasPlanToolInput, GasPlanToolLevel

### Community 151 - "Community 151"
Cohesion: 0.13
Nodes (6): ConnectionView, ConnectionViewDiveImport, ConnectionViewDiveNumbers, ConnectionViewLastFetch, ConnectionViewOffers, ConnectionViewWaiting

### Community 152 - "Community 152"
Cohesion: 0.13
Nodes (6): DeletedDivesPage, DeletedDivesPageDivesItem, DeletedDivesPageDivesItemSite, DeletedDivesPageDivesItemStillAtItem, DeletedDivesPageStillAtItem, WaitingPushView

### Community 153 - "Community 153"
Cohesion: 0.24
Nodes (6): Body, Call, Harness, Offline, Reply, WriteReplayerTest

### Community 154 - "Community 154"
Cohesion: 0.11
Nodes (21): CENTRE_SOURCES, CentreView, CentreViewSchema, Row, Source, toCentreView(), ADR-0043, CentreChanged (+13 more)

### Community 155 - "Community 155"
Cohesion: 0.17
Nodes (21): build(), byId(), callersOf(), dependentsOf(), fileNode(), git(), GRAPH, metaFor() (+13 more)

### Community 156 - "Community 156"
Cohesion: 0.15
Nodes (17): Body, Hold, Offline, refreshDeletedJson(), refreshDivePage(), refreshFeedPage(), RefreshHarness, refreshJson() (+9 more)

### Community 157 - "Community 157"
Cohesion: 0.23
Nodes (5): Body, ConflictCall, ConflictHarness, ConflictReply, WriteConflictResolutionTest

### Community 158 - "Community 158"
Cohesion: 0.14
Nodes (18): createFileFormats(), looksLikeFit(), createSuuntoJsonAdapter(), looksLikeSuuntoJson(), ADR-0037, createUddfAdapter(), looksLikeUddf(), decimal() (+10 more)

### Community 159 - "Community 159"
Cohesion: 0.09
Nodes (20): compilerOptions, types, extends, include, compilerOptions, allowImportingTsExtensions, jsx, lib (+12 more)

### Community 160 - "Community 160"
Cohesion: 0.18
Nodes (4): label(), SettingsDebugRoute(), SettingsDebugScreen(), SettingsDebugScreenTest

### Community 161 - "Community 161"
Cohesion: 0.14
Nodes (4): FakeCachedMe, FakeInstances, FakeTokens, SyncAuthTest

### Community 162 - "Community 162"
Cohesion: 0.19
Nodes (6): WriteQueueLineTest, WriteDropReason, Forbidden, Gone, NotFound, Refused

### Community 163 - "Community 163"
Cohesion: 0.16
Nodes (20): EquipmentError, checkCost(), checkDates(), checkWindow(), createEquipmentService(), assemble(), checkResets(), checkSchedule() (+12 more)

### Community 164 - "Community 164"
Cohesion: 0.13
Nodes (18): PROBLEM_CODES, SiteImportRouteDeps, StartBody, STATUS, ADR-0021, SiteImportError, SiteImportService, Area (+10 more)

### Community 165 - "Community 165"
Cohesion: 0.13
Nodes (19): ADR-0023, devDependencies, dependency-cruiser@18.7.0, eslint@10.13.0, @eslint/js@10.0.1, eslint-plugin-sonarjs@4.2.2, typescript@6.0.3, typescript-eslint@8.72.0 (+11 more)

### Community 166 - "Community 166"
Cohesion: 0.15
Nodes (5): DiagnosticEntry, DiagnosticLevel, ERROR, WARN, DiagnosticsBuffer

### Community 168 - "Community 168"
Cohesion: 0.15
Nodes (5): ApiToolsGasGet200Response, ApiToolsGasGet200ResponseAtDepth, ApiToolsGasGet200ResponseConditions, ApiToolsGasGet200ResponseGas, ApiToolsGasGet200ResponsePpO2Limits

### Community 169 - "Community 169"
Cohesion: 0.15
Nodes (5): ProviderSummaryDataDives, ProviderSummaryDataDivesExport, ProviderSummaryDataDivesExportNumbers, ProviderSummaryDataDivesExportRequirementsItem, ProviderSummaryDataDivesImport

### Community 170 - "Community 170"
Cohesion: 0.23
Nodes (9): decode(), ENTITIES, localName(), Open, parseXml(), Reader, ADR-0003, XmlElement (+1 more)

### Community 171 - "Community 171"
Cohesion: 0.10
Nodes (16): StrayOverride, english, flatten(), Tree, ADR-0014, ADR-0028, ADR-0029, ADR-0032 (+8 more)

### Community 172 - "Community 172"
Cohesion: 0.11
Nodes (15): ADR-0054, baselineSize(), cycleEdges(), days, hotspots(), lines, ADR-0039, ADR-0053 (+7 more)

### Community 173 - "Community 173"
Cohesion: 0.20
Nodes (17): git(), describeAttempt(), eachSpec(), failedTestsFromReport(), flakyTestsFromReport(), localTime(), REPORT, appendRun() (+9 more)

### Community 174 - "Community 174"
Cohesion: 0.12
Nodes (3): SyncWorker, WorkManagerPeriodicSync, PeriodicSyncScheduler

### Community 175 - "Community 175"
Cohesion: 0.19
Nodes (11): LeadInputTest, SuitType, drysuit, none, semidry, skin, wetsuit, SuitUndergarment (+3 more)

### Community 176 - "Community 176"
Cohesion: 0.19
Nodes (16): planningSac(), PlanningSacDive, powInt(), roundTo(), Sac, SacCylinder, SacDive, SacMissing (+8 more)

### Community 179 - "Community 179"
Cohesion: 0.15
Nodes (17): serviceRecord, serviceRecordSchedule, serviceSchedule, equipmentList(), EquipmentRecordRead, today(), ADR-0034, ADR-0035 (+9 more)

### Community 180 - "Community 180"
Cohesion: 0.16
Nodes (16): date(), FitAdapter, FitMessage, FitMessages, GARMIN_PRODUCTS, num(), parseWithFitFileParser(), position() (+8 more)

### Community 181 - "Community 181"
Cohesion: 0.19
Nodes (15): clientVersionGate(), ADR-0076, ClientVersionRegistry, MIN_CLIENT_VERSIONS, MinClientVersion, ADR-0076, clientRequestSerializer(), ADR-0076 (+7 more)

### Community 182 - "Community 182"
Cohesion: 0.13
Nodes (17): typescript@7.0.2, typescript@7.0.2, typescript, dependencies, openapi-fetch@^0.17.0, devDependencies, openapi-typescript@^7.13.0, typescript@5.9.3 (+9 more)

### Community 183 - "Community 183"
Cohesion: 0.14
Nodes (15): JOINT_FILL_FIELDS, OfferRouteDeps, STATUS, ADR-0070, OfferError, OfferService, Facts, Field (+7 more)

### Community 184 - "Community 184"
Cohesion: 0.12
Nodes (16): PlanningService, BottomTimeQuery, BottomTimeView, Depth, Fraction, GasPlanBody, GasPlanView, GasQuery (+8 more)

### Community 185 - "Community 185"
Cohesion: 0.15
Nodes (14): i18next@^26.4.2, diverLoadingsQuery(), LoadingsView, CurrentLoadings(), CustomTypeOptions, i18next, LOADING_SOURCES, loadingNumber() (+6 more)

### Community 187 - "Community 187"
Cohesion: 0.12
Nodes (8): SsiClient, deleteTestDive(), ClearChecks, NumberChecks, findDive(), runSiteVisibleFinish(), TwowayChecks, ValuesChecks

### Community 188 - "Community 188"
Cohesion: 0.23
Nodes (15): prepareEntry(), DENIED_KEY_PARTS, emptyRedactionCounters(), isDeniedKey(), maskString(), redactInner(), redactLogObject(), RedactOptions (+7 more)

### Community 189 - "Community 189"
Cohesion: 0.15
Nodes (10): androidVersionCode(), main(), ADR-0077, parseAndroidTag(), ADR-0077, rejected, script, ADR-0068 (+2 more)

### Community 190 - "Community 190"
Cohesion: 0.17
Nodes (11): killTree(), ADR-0023, runLanes(), runStep(), takeLock(), meeting(), ADR-0023, sleep() (+3 more)

### Community 193 - "Community 193"
Cohesion: 0.19
Nodes (4): CentreView, CentreViewExternalIdsItem, CentreViewSitesItem, CentreViewVerificationCode

### Community 194 - "Community 194"
Cohesion: 0.19
Nodes (4): DiveDetailsListView, DiveDetailsListViewCounts, DiveDetailsListViewMonthsItem, DiveDetailsListViewTotals

### Community 195 - "Community 195"
Cohesion: 0.19
Nodes (4): ProviderSummary, ProviderSummaryLimits, ProviderSummaryLimitsRun, ProviderSummarySignIn

### Community 196 - "Community 196"
Cohesion: 0.12
Nodes (13): clientDiagnostic, clientDiagnosticsSettings, ClientDiagnosticsEntryInput, ClientDiagnosticsEntryView, ClientDiagnosticsError, ClientDiagnosticsUpload, KEEP_NEWEST, MAX_UPLOAD_ENTRIES (+5 more)

### Community 197 - "Community 197"
Cohesion: 0.18
Nodes (11): storedList(), tryParseJson(), APP_PARAMS, createSsiTransport(), exchange(), isObject(), SSI_APP_USER_AGENT, SsiTransport (+3 more)

### Community 198 - "Community 198"
Cohesion: 0.21
Nodes (11): buildSupportBundle(), buildTarGz(), json(), ndjson(), octal(), SUPPORT_BUNDLE_FORMAT, SUPPORT_BUNDLE_FORMAT_VERSION, SupportBufferManifest (+3 more)

### Community 199 - "Community 199"
Cohesion: 0.24
Nodes (15): apneaDive(), DATASET_KEYS, datasetSummary(), findIn(), finishValuesChecks(), GAS, minutes(), NEW_KEYS (+7 more)

### Community 200 - "Community 200"
Cohesion: 0.12
Nodes (15): environment, healthcheck, image, ports, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER (+7 more)

### Community 201 - "Community 201"
Cohesion: 0.16
Nodes (12): CIRCUIT, circuitFromFit, DECO_MODEL, decoModelFromFit, DIVE_ALERT, DIVE_MODE, ADR-0015, WATER_TYPE (+4 more)

### Community 202 - "Community 202"
Cohesion: 0.13
Nodes (14): concurrency, cancel-in-progress, group, env, RELEASE_TAG, jobs, publish, name (+6 more)

### Community 204 - "Community 204"
Cohesion: 0.33
Nodes (4): ConflictField, DiveMerge, MergeOutcome, ParticipantsMergeOutcome

### Community 205 - "Community 205"
Cohesion: 0.24
Nodes (13): asNumber(), asText(), compareReadBack(), datasetLength(), describe(), isObject(), keyDifferences(), pause() (+5 more)

### Community 206 - "Community 206"
Cohesion: 0.20
Nodes (5): LogRingBuffer, RedactionCounters, SupportBundleInput, SupportBundleManifest, SupportServiceDeps

### Community 207 - "Community 207"
Cohesion: 0.14
Nodes (14): with, build-args, cache-from, cache-to, context, fetch-depth, load, password (+6 more)

### Community 208 - "Community 208"
Cohesion: 0.14
Nodes (13): compilerOptions, exactOptionalPropertyTypes, isolatedModules, lib, module, moduleResolution, noEmit, noUncheckedIndexedAccess (+5 more)

### Community 210 - "Community 210"
Cohesion: 0.18
Nodes (10): createItem(), daysFromNow(), Diver, headers, iso(), Item, made, ownDiver() (+2 more)

### Community 211 - "Community 211"
Cohesion: 0.32
Nodes (10): ConflictsRoute, DiveEditRoute, InstanceRoute, LogbookRoute, MainRoute, ScanRoute, SettingsDebugRoute, SettingsRoute (+2 more)

### Community 215 - "Community 215"
Cohesion: 0.26
Nodes (3): ApiToolsBottomTimeGet200Response, ApiToolsBottomTimeGet200ResponseGas, ApiToolsBottomTimeGet200ResponseLimits

### Community 216 - "Community 216"
Cohesion: 0.26
Nodes (3): DiverDetailsView, DiverDetailsViewAccountsItem, DiverDetailsViewCodesItem

### Community 217 - "Community 217"
Cohesion: 0.26
Nodes (3): DiverView, DiverViewCertifications, DiverViewCertificationsNewest

### Community 218 - "Community 218"
Cohesion: 0.26
Nodes (3): DiveViewVerificationCodesItem, DiveViewVerificationCodesItemCentre, DiveViewVerificationCodesItemDiver

### Community 220 - "Community 220"
Cohesion: 0.18
Nodes (10): LogBufferStats, SupportBundle, ClientDiagnosticsProvider, FailedBackup, FailedJob, HealthSection, iso(), JobsSection (+2 more)

### Community 221 - "Community 221"
Cohesion: 0.18
Nodes (11): ADR-0023, ADR-0053, ADR-0066, ADR-0070, ADR-0071, ADR-0076, ADR-0083, MODULE_IMPORTS (+3 more)

### Community 222 - "Community 222"
Cohesion: 0.17
Nodes (11): concurrency, cancel-in-progress, group, env, IMAGE, RELEASE_TAG, jobs, name (+3 more)

### Community 224 - "Community 224"
Cohesion: 0.25
Nodes (5): cameraShowing(), headers, ADR-0043, modules(), picture()

### Community 225 - "Community 225"
Cohesion: 0.22
Nodes (8): diveRow(), rowOf(), ADR-0026, ADR-0048, diveRows(), goToMore(), expectFirstDiveInView(), rowOf()

### Community 226 - "Community 226"
Cohesion: 0.20
Nodes (6): Dive, getDive(), headers, NOTHING, patchDive(), ADR-0031

### Community 227 - "Community 227"
Cohesion: 0.18
Nodes (11): verify, major, minor, prerelease, sha, tag, version, name (+3 more)

### Community 228 - "Community 228"
Cohesion: 0.18
Nodes (11): scripts, build, check, check:full, check:log, dev, hooks:install, lint (+3 more)

### Community 229 - "Community 229"
Cohesion: 0.29
Nodes (8): APP, lane(), main(), ADR-0073, parseReviewArgs(), review(), reviewGoldens(), ROOT

### Community 230 - "Community 230"
Cohesion: 0.53
Nodes (3): UiState, sessionDestination(), SessionDestinationTest

### Community 233 - "Community 233"
Cohesion: 0.22
Nodes (6): contrast(), css, darkRoot, lightRoot, luminance(), schemes

### Community 234 - "Community 234"
Cohesion: 0.20
Nodes (10): verify, prerelease, sha, tag, versionCode, versionName, name, outputs (+2 more)

### Community 235 - "Community 235"
Cohesion: 0.20
Nodes (10): with, cache, distribution, fetch-depth, if-no-files-found, java-version, name, path (+2 more)

### Community 236 - "Community 236"
Cohesion: 0.29
Nodes (10): steps, GH_TOKEN, steps, env, id, if, name, run (+2 more)

### Community 238 - "Community 238"
Cohesion: 0.22
Nodes (9): scripts, auth:generate, build, db:generate, dev, openapi, start, test (+1 more)

### Community 240 - "Community 240"
Cohesion: 0.31
Nodes (5): BufferedLog, createLogRingBuffer(), LOG_BUFFER_CAPACITY_BYTES, LOG_BUFFER_CAPACITY_ENTRIES, LogRingBufferOptions

### Community 241 - "Community 241"
Cohesion: 0.22
Nodes (9): ANDROID_RELEASE_KEY_ALIAS, ANDROID_RELEASE_KEY_PASSWORD, ANDROID_RELEASE_KEYSTORE_BASE64, ANDROID_RELEASE_KEYSTORE_PASSWORD, ANDROID_VERSION_CODE, ANDROID_VERSION_NAME, GH_REPO, GH_TOKEN (+1 more)

### Community 242 - "Community 242"
Cohesion: 0.22
Nodes (9): services, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, env, image, options, ports (+1 more)

### Community 243 - "Community 243"
Cohesion: 0.22
Nodes (8): allowBuilds, esbuild, peerDependencies, minimumReleaseAgeExclude, packageExtensions, dependency-cruiser, packages, typescript

### Community 245 - "Community 245"
Cohesion: 0.36
Nodes (6): css, files(), read(), src, tsx, withoutComments()

### Community 246 - "Community 246"
Cohesion: 0.39
Nodes (8): steps, steps, id, name, run, uses, working-directory, steps

### Community 249 - "Community 249"
Cohesion: 0.43
Nodes (5): createFitAdapter(), ownersParts(), parse(), toPart(), ADR-0063

### Community 250 - "Community 250"
Cohesion: 0.29
Nodes (7): scripts, build, dev, review:capture, test, test:e2e, typecheck

### Community 251 - "Community 251"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 252 - "Community 252"
Cohesion: 0.29
Nodes (7): version, on, workflow_dispatch, description, required, type, inputs

### Community 253 - "Community 253"
Cohesion: 0.40
Nodes (5): stubDive(), ADR-0015, ADR-0023, ADR-0025, guardedRoute()

### Community 254 - "Community 254"
Cohesion: 0.53
Nodes (3): mergeEdits(), Revision, same()

### Community 255 - "Community 255"
Cohesion: 0.40
Nodes (5): DivePhase, Dive, Error, Loading, Offline

### Community 256 - "Community 256"
Cohesion: 0.40
Nodes (5): LogbookPhase, Error, List, Loading, Offline

### Community 257 - "Community 257"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build

### Community 258 - "Community 258"
Cohesion: 0.40
Nodes (5): name, needs, runs-on, timeout-minutes, build-smoke

### Community 259 - "Community 259"
Cohesion: 0.40
Nodes (5): publish, name, needs, runs-on, timeout-minutes

### Community 263 - "Community 263"
Cohesion: 0.50
Nodes (4): SyncPhase, Details, Meta, Profiles

### Community 265 - "Community 265"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 266 - "Community 266"
Cohesion: 0.50
Nodes (3): interface, display_name, short_description

### Community 267 - "Community 267"
Cohesion: 0.50
Nodes (3): match, ADR-0068, values

## Knowledge Gaps
- **1365 isolated node(s):** `KeptServer`, `Requeued`, `Reserve`, `RockBottom`, `Sent` (+1360 more)
  These have ≤1 connection - possible missing edges. (Counts symbols only; 3970 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **173 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `DiveStore` connect `Community 19` to `Community 136`, `Community 141`, `Community 21`, `Community 153`, `Community 27`, `Community 156`, `Community 157`, `Community 29`, `Community 161`, `Community 39`, `Community 41`, `Community 47`, `Community 178`, `Community 56`, `Community 60`, `Community 191`, `Community 69`, `Community 71`, `Community 76`, `Community 82`, `Community 89`, `Community 97`, `Community 114`?**
  _High betweenness centrality (0.018) - this node is a cross-community bridge._
- **What connects `KeptServer`, `Requeued`, `Reserve` to the rest of the system?**
  _1365 weakly-connected nodes found - possible missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.019213947455735528 - nodes in this community are weakly interconnected._
- **Why does `DiveHubTheme()` connect `Community 24` to `Community 135`, `Community 148`, `Community 21`, `Community 149`, `Community 150`, `Community 26`, `Community 27`, `Community 160`, `Community 162`, `Community 38`, `Community 41`, `Community 49`, `Community 55`, `Community 186`, `Community 192`, `Community 203`, `Community 76`, `Community 77`, `Community 81`, `Community 213`, `Community 214`, `Community 120`, `Community 248`?**
  _High betweenness centrality (0.014) - this node is a cross-community bridge._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.04175309468049515 - nodes in this community are weakly interconnected._
- **Why does `@tanstack/react-query` connect `Community 4` to `Community 1`, `Community 5`, `Community 70`, `Community 37`, `Community 10`, `Community 42`, `Community 109`, `Community 46`, `Community 13`, `Community 20`, `Community 94`, `Community 30`, `Community 57`?**
  _High betweenness centrality (0.013) - this node is a cross-community bridge._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.021530282893487754 - nodes in this community are weakly interconnected._