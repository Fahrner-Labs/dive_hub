---
title: Testing strategies - fast checks with enough certainty
summary: How others choose which tests to run (Meta's and Develocity's predictive selection, Microsoft's and Datadog's test impact analysis, Launchable), what research says about dropping tests by coverage, the pyramid and the trophy, flaky tests, and what Playwright and vitest offer, set against our own numbers (ui-quality.spec.ts is 60 % of browser worker time, a test averages 7.3 s, mostly setup through the API and navigation). Ranked for Dive Hub - measure and shrink the slow tests first, move checks out of the browser, then a coverage map run as an observation experiment; machine-learning selection doesn't fit. Input for backlog 0004's design.
status: open
date: 2026-10-26
---

# Testing strategies: fast checks with enough certainty

The research that backlog 0004 asks for before its design. Web
research through the `researcher` subagent on 2026-10-26; our own numbers from
[ADR 0023](../decisions/0023-faster-checks.md#amendment-2026-10-24-a-faster-pnpm-check-and-ci-as-the-only-full-check).
Marks: [S] seen in the source, [I] inferred, [NV] not verified (often: only a search summary or a secondary source was
read).

## Our starting point (ADR 0023, measured 2026-10-24)
- `pnpm check`'s browser step: 1136 s wall on 2 workers, 2044 s of worker time, 280 test runs from ~205 tests. The 280
  are variants (`ui-quality.spec.ts` runs every page in several variants), not retries.
- `ui-quality.spec.ts` is 1231 s of the 2044 s. In its English variant axe takes 246 s of 526 s (2.4 s per page check);
  the slowest tests run axe 3-6 times.
- A test averages 7.3 s (3.1 s on 2026-10-08), mostly setup through the API and navigation.
- 4 workers were slower (1307 s, 14 timeouts); 2 workers showed no contention.
- Locally the check already runs only what changed since the last green run, browser tests by hand-kept area tags; CI
  runs everything and is the gate.

*Measured again on 2026-10-26* (design):
the same local plan took 334 s wall (229 tests, 2.6 s a test, ui-quality 42 % of worker time, axe at every check +33 s
on ui-quality), 4 workers 284 s without timeouts. The 7.3 s did not reproduce; the ranking below was written before.

*Measured again on 2026-11-09* (Playwright's JSON report, 2 workers, the check's English plan of 130 tests):

| axe | Wall | Worker time | Mean per test |
|---|---|---|---|
| off (measurement only) | 152 s | 251 s | 1.9 s |
| once per test, on its final state | 231 s | 344 s | 2.6 s |
| at every page state | 278 s | 395 s | 3.0 s |

So axe is 27 % of the English variant's worker time when it runs once per test, and 36 % at every state. The German,
dark, phone variant (72 tests) went from 222 s to 280 s worker time for every state (+57 s). Running axe once per test
everywhere, not only in `pnpm check`, saves about 108 s of worker time across the two axe variants (16 %) and about 7
axe scans per variant. What is given up: an axe fault on an intermediate state (a dialog closed again, a page navigated
away from) is only found with `E2E_AXE=every`; the other page rules still run at every state.

So most of the time sits in a few places (one spec file, axe, per-test setup), not spread evenly. That shapes the
ranking at the end.

## 1. Test impact analysis and predictive selection
- **Meta, predictive test selection** (2018): a gradient-boosted model trained on past changes and results runs about a
  third of the tests that depend on the change and still catches over 99.9 % of regressions; selection by build
  dependencies alone ran about a quarter of all tests per mobile change. Flaky results spoil the training data. [S]
  [Meta](https://engineering.fb.com/2018/11/21/developer-tools/predictive-test-selection/). Needs large history: doesn't
  fit.
- **Develocity (Gradle)**: the same idea as a product, JVM builds only; vendor claims 35-70 % time saved. [S]
  [docs](https://docs.gradle.com/develocity/predictive-test-selection),
  [2023.4](https://gradle.com/blog/develocity-2023.4-boosts-toolchain-reliability-and-test-performance-scalability/).
  Not applicable.
- **Microsoft TIA (Azure Pipelines)**: per-test dependency maps; runs impacted, previously failing and new tests. Changes
  it can't reason about (HTML, CSS, unknown file types) run everything; optional full run every N builds; it advises
  validating a selection by running the subset and then the full suite and comparing. [S]
  [docs](https://learn.microsoft.com/en-us/azure/devops/pipelines/test/test-impact-analysis). The product doesn't fit;
  its safety rules do.
- **Datadog TIA**: per-test coverage; a test is skipped only if it passed on a commit where the files it covers are the
  same as now; tests marked unskippable always run. No skip rates published; Playwright support [NV]. [S]
  [docs](https://docs.datadoghq.com/tests/test_impact_analysis/how_it_works/). This is the exact rule our coverage idea
  would implement, next to the green records we already keep.
- **CloudBees Smart Tests (was Launchable)**: hosted ML subsetting by a target (confidence, time or share of duration);
  recommends starting in observation mode, measuring what a subset would have missed. [S]
  [docs](https://docs.cloudbees.com/docs/cloudbees-smart-tests/latest/send-data-to-smart-tests/subset/choose-a-subset-optimization-target).
  The observation mode is worth copying.
- **Open source for JavaScript**: no maintained per-test impact analysis for Playwright found [NV]; the pieces are
  Playwright's `--only-changed`, vitest's `--changed`/`related`/`recordDependencies` and monocart-coverage-reports.

## 2. Selection by coverage and dropping redundant tests
- Studies on shrinking a suite by coverage disagree: Rothermel et al. (1998) found reduced suites could find far fewer
  faults, Wong et al. found little loss. Numbers not read [NV]; a later reduction paper:
  [Gyori et al. FSE 2014](https://mir.cs.illinois.edu/gyori/pubs/fse14reduction.pdf) (not opened).
- Why coverage subsets are weak evidence [I]: coverage says code ran, not what was asserted; the same lines run under
  different data and states (browser tests drive the same client code through many server states); branches that depend
  on data look the same.
- A smallest covering set is a set-cover problem; the greedy approximation is the standard answer and fine at our size
  [I].
- Consequence: a "test A lies inside test B" report is a prompt for the owner's review, as backlog 0004 already says;
  never an automatic drop.

## 3. What belongs in browser tests
- Google, "Just Say No to More End-to-End Tests" (2015): keep a pyramid shape; end-to-end tests give slow feedback, are
  flaky and hard to debug. The often-quoted 70/20/10 split comes from a comment, not the post [S, partial].
  [Google Testing Blog](https://testing.googleblog.com/2015/04/just-say-no-to-more-end-to-end-tests.html)
- Kent C. Dodds' testing trophy: most weight on integration tests; "the more your tests resemble the way your software
  is used, the more confidence they can give you" [S via summary].
  [post](https://kentcdodds.com/blog/the-testing-trophy-and-testing-classifications)
- Playwright's best practices: test what the user sees, control the backend's data, mock third parties, log in once in a
  setup project and reuse the storage state. [S] [docs](https://playwright.dev/docs/best-practices)
- For us [I]: browser tests for what only a browser shows (routing, rendering, focus and events, a few whole journeys);
  validation, permissions, data rules and API contracts as server tests (`app.inject()` against a real database,
  milliseconds each); client logic in vitest. Page checks that don't need a real server (one `h1`, widths, axe) are
  candidates for a lighter setup.

## 4. Selection by changed files and imports
- vitest `--changed [since]` and `vitest related <files>` follow the module graph, static imports only. [S]
  `experimental.recordDependencies` records the modules each test file actually loaded and uses that record for both; a
  file without a record always runs. [S] [vitest CLI](https://vitest.dev/guide/cli)
- Nx `affected` and Turborepo `--affected` work per project or package [S via summary]
  ([Nx](https://nx.dev/docs/features/ci-features/affected.md), [Turborepo](https://turborepo.dev/docs/reference/query)):
  too coarse for a repository with two apps.
- Limits [I]: an import graph misses configuration, migrations, environment variables and the server serving the built
  client; those stay "run more" triggers, as in Microsoft's TIA and in our check script today.

## 5. Flaky tests
- Playwright counts a test as flaky when it failed and then passed on a retry; retries are off by default;
  `--fail-on-flaky-tests` exists; traces on the first retry are the recommended setting. [S]
  [retries](https://playwright.dev/docs/test-retries), [CLI](https://playwright.dev/docs/test-cli)
- Google (2016): about 1.5 % of test runs flaky, about 16 % of tests with some flakiness; reruns only for tests marked
  flaky [S via summary]. [post](https://testing.googleblog.com/2016/05/flaky-tests-at-google-and-how-we.html)
- Atlassian: detection and quarantine out of the gate while still tracked; flakiness caused up to 21 % of master build
  failures in Jira's frontend [S via summary].
  [post](https://www.atlassian.com/blog/atlassian-engineering/taming-test-flakiness-how-we-built-a-scalable-tool-to-detect-and-manage-flaky-tests)
- Meta: selection learnt from flaky results is wrong, so flakiness is handled first.
- ADR 0023 rejected retries because they would hide flaky tests. A middle way others use [I]: a retry in CI only,
  with `--fail-on-flaky-tests` or a count in the log, so a flaky test is still reported.

## 6. Playwright
- `--only-changed [ref]`: test files changed against the ref (Git), and since 1.46 test files that import a changed file
  [S; the import part via the release notes summary,
  [1.46](https://github.com/microsoft/playwright/releases/tag/v1.46.0)]. App code that the browser tests drive isn't
  imported by them, so it can't select by app changes [I].
- `--last-failed` reruns failures; `--shard x/y` with `fullyParallel` balances per test, merged through blob reports
  [S] ([sharding](https://playwright.dev/docs/test-sharding)). Sharding helps CI, not the laptop.
- Workers default to half the logical cores; each is a process with its own browser [S]
  ([parallel](https://playwright.dev/docs/test-parallel)). Why 4 workers were slower for us is not settled: CPU, the
  one e2e server per worker, or the database [I].
- Coverage: `page.coverage` (Chromium only) records the client's V8 coverage, reset on each navigation by default [S]
  ([API](https://playwright.dev/docs/api/class-coverage));
  [monocart-coverage-reports](https://github.com/cenfun/monocart-coverage-reports) turns it, and a Node server's
  `NODE_V8_COVERAGE` or `v8.takeCoverage()`, into source-mapped reports and keeps raw data for merging [S]. Per-test
  output isn't documented; per-test server coverage needs the server to serve one test at a time (our servers are per
  worker, so a worker's server serves one test at a time already [I]) and a way to separate one test's counts from the
  next ([NV]: how V8 counters reset).

## 7. vitest
- `--changed`, `related`, `recordDependencies`: section 4. `--shard`, `--project`; pool `forks` by default;
  `--no-isolate`; `vitest doctor` measures pools and checks `isolate: false` by running the suite twice shuffled. [S]
  (page for 5.0.5)
- Coverage `v8` or `istanbul`; `coverage.changed` limits it to changed files; since 3.2 V8 coverage is remapped through
  the AST and matches Istanbul [S] ([coverage](https://vitest.dev/guide/coverage)). Coverage per test isn't
  documented [NV]; per test file through `recordDependencies` is probably enough for the server tests [I].

## 8. General speed levers for browser tests
- Log in once (setup project and `storageState`), mock third parties, Chromium only, traces on the first retry. [S]
  (best practices)
- Data through the API or the database, the UI only for the step under test [I]. Our tests already set up through the
  API; the average of 7.3 s says to measure which part of setup costs most.
- A database per worker cloned from a migrated template (`CREATE DATABASE ... TEMPLATE`; fails while anyone is connected
  to the template) [S] ([PostgreSQL](https://www.postgresql.org/docs/current/manage-ag-templatedbs.html)), or `TRUNCATE`
  of the used tables; a rollback per test doesn't work while the server holds its own connections [I].
- Web-first assertions instead of fixed waits; the built client instead of a dev server [I, no benchmark found].

## Ranked for Dive Hub
Effort against gain, with our numbers above in mind [I]:

1. **Profile and shrink the slow part first.** `ui-quality.spec.ts` and its axe scans are 60 % of browser worker time:
   fewer axe passes per test (once per page and state, not after every step), fewer variants in `pnpm check`, and a
   look at where the 7.3 s per test go (setup, navigation, waits). Low effort, largest gain.
2. **Move checks out of the browser.** Rules and API behaviour as server tests, client logic in vitest; keep browser
   tests for what only a browser shows. Ongoing, test by test.
3. **Cheaper setup** where profiling points: login state reuse, setup through the API or the database, a per-worker
   database from a template. May also explain the worker limit.
4. **Flaky tests made visible**: count them per run in CI (a retry there only if ADR 0023's reason is kept: reported,
   not hidden). Any selection relies on it.
5. **A generated map instead of hand-kept area tags**: `recordDependencies` for the server tests; for browser tests,
   per-test coverage of the client (`page.coverage`) and of the worker's server. Keeps today's "unknown file runs more"
   rules and a full run in CI.
6. **Selection by coverage as an observation run first**: compute what the selection would have run, compare it with
   CI's result and the [check escapes](../agents/check-escapes.md) log, and switch it on when it would not have missed
   anything over a period (Microsoft's validation and Launchable's observation mode).
7. **The "test inside another test" report** once 5 exists: candidates for the owner, never automatic drops.
8. **CI sharding** only if CI time becomes the problem.

Not for us: machine-learning selection (Meta, Develocity, Smart Tests) and hosted services (Datadog): too little
history, and cost.

## Moved out of the browser (2026-11-10)

Part 2 of the ranking: the browser cases whose assertions were API shapes, status codes or server rules, and the
server test (`app.inject()` against PostgreSQL) that now owns each check. The browser cases keep what only a
browser shows (notices, focus, dialogs, a rendered page); their setup calls stay.

| Browser case | Check moved | Server test |
|---|---|---|
| `ai-access.spec.ts`: a User creates and uses a key, an admin switches AI access off | MCP answers and their status/`error_description`; a key stops at once when revoked or switched off | `mcp-endpoint.test.ts` |
| `provider-sites.spec.ts`: the picker and the site page | The picker's `{ externalId, private }` answer, the stored private external ID, and another User never seeing it | `site-pick.test.ts`, `private-site-ids.test.ts` |
| `provider-conflicts.spec.ts`: a choice and a run's conflict | The notes taken from SSI, and the pushes a choice records | `ssi-target.test.ts`, `push-runs.test.ts`, `provider-conflicts.test.ts` |
| `logbook.spec.ts`: a row's sketch | The list row's profile: 48 depths for a Recording, `null` without one | `assessment.test.ts` |
| `oceanic-plus-import.spec.ts`: the app's zip | The import outcome, and the `+00:02` offset read as two hours | `oceanic-plus-import.test.ts` |
| `suunto-import.spec.ts`: the Suunto JSON export | The import outcome (`created`/`unchanged`) | `suunto-import.test.ts` |
| `dive-continuation.spec.ts`: appending from Review | The later Dive answering 404 after the append | `dive-continuation.test.ts` |
| `waiting-pushes.spec.ts`: a manual send, restoring an automatic delete | The queue entry cleared by a manual send, and a restore cancelling the delete | `waiting-pushes.test.ts`, `dive-deletion.test.ts` |
| `gone-dives.spec.ts`: cancelling the delete dialog | Cancelling sends nothing is a client rule; the page now says the Dive is still there | the browser case |

`ai-access.spec.ts` still calls the MCP endpoint to fill "What was read", and the import specs still read the
Dive they show; those are setup, not checks. This is the ongoing part of item 2: later cases move as they are
found, and a check already covered by a server test is deleted rather than duplicated.

## Template databases (2026-11-11)

Item 3 of the ranking, the database part of setup. The browser tests now build one migrated template database per
migration state and clone the per-worker databases from it (`CREATE DATABASE ... TEMPLATE ...`) instead of running
migrations for every e2e server. The template's name carries a key over the migration files' hashes, a format version
and Graphile Worker's version: a new or changed migration builds a new template, and each clone is checked against the
migration files before use, so an old template cannot hide a migration. An unchanged template is reused by later runs;
its migrations are checked once against it for each run, not once per worker.

Measured on the agent machine's PostgreSQL 17 (the repository needs 18; the two workers are the browser tests'
default), first run on a fresh test server: creating a fresh database and migrating it took 602 and 632 ms; building
the template once took 629 ms and cloning it 113 and 102 ms. So the two-worker setup saved about 0.4 s per run here,
and more workers or a slower server save proportionally more. The rest of the per-worker setup (seeding the User and
Dive, `e2e/prepare.ts`) is unchanged; a prepared template would save that too, but would bring back the hidden
dependencies between tests that serial preparation had.

## Flaky tests counted in CI (2026-11-12)

Item 4 of the ranking. CI reported nothing about flakes: a flaky test either reddened the run or, when it passed
again, left no trace, while [check escapes](../agents/check-escapes.md) and the flake fixes FAH-53–FAH-56 kept
showing what they cost.

- **One retry in CI only** (`E2E_RETRIES=1` in `.github/workflows/check.yml`); locally there is none, so a flake still
  shows. Playwright marks a test `flaky` when it failed and then passed on the retry, and its JSON report already
  carries that status and the earlier attempts.
- **Counted in the job summary.** `node scripts/check-log.mjs --flaky` reads `apps/web/test-results/report.json` and
  appends a "Flaky browser tests" table: the case, the attempt that passed, and the first line of each earlier
  failure. A run with no flaky case says so; a run without a report (an earlier step failed) says so too.
- **Not hidden, but no longer blocking.** The run stays green when a case passes on its retry; the table is the
  count. `--fail-on-flaky-tests` is not used, and the failed-browser-tests summary still lists only the cases that
  failed every attempt (from the same report).
- This note records the mechanism; the rate itself is read from the job summaries of the PRs and pushes that follow.
  For comparison, Google reported about 1.5 % of test runs flaky in 2016 (section 5).

## Not found
How `--only-changed` traces imports exactly; published missed-failure rates for coverage-based selection of browser
tests; the cost of V8 coverage in Playwright; the figures in the minimisation papers; per-test coverage in vitest;
whether Datadog supports Playwright.
