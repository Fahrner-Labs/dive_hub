// Browser tests (ADR 0015): the built web client against the real server and a fresh database.
//   pnpm --filter @dive-hub/web test:e2e
// Uses an installed browser (Edge by default; PLAYWRIGHT_CHANNEL=chrome for Chrome), so no download.
import { randomUUID } from 'node:crypto';
import { defineConfig } from '@playwright/test';
import {
  E2E_BASE_URL, E2E_BILLING_PORT, E2E_FIRST_PORT, E2E_SERVERS, E2E_SESSION, E2E_SHARED_BASE_URL, E2E_SHARED_PORT, serverUrl,
} from './e2e/support.ts';

/** One id for all the e2e servers, so the migrated template runs its migrations once for this run. */
const E2E_RUN_ID = process.env.E2E_RUN_ID ?? randomUUID();

export default defineConfig({
  testDir: 'e2e',
  // One server and database per worker (ADR 0023). A spec file runs in one worker, its tests in order;
  // ui-quality.spec.ts spreads its tests over all workers.
  fullyParallel: false,
  workers: E2E_SERVERS,
  // One retry, only in CI (`E2E_RETRIES=1` in .github/workflows/check.yml): a test that passes on it is flaky and is
  // named in the job summary (ADR 0023, amendment 2026-11-12). Locally there is none, so a flake shows.
  retries: Number(process.env.E2E_RETRIES ?? 0),
  reporter: [['list'], ['json', { outputFile: 'test-results/report.json' }]],
  // Pages answer more slowly while other workers run (ADR 0023).
  expect: { timeout: 10_000 },
  outputDir: 'test-results',
  globalSetup: './e2e/global-setup.ts',
  use: {
    // Evaluated in each worker too, so these point at the worker's own server.
    baseURL: E2E_BASE_URL,
    channel: process.env.PLAYWRIGHT_CHANNEL ?? 'msedge',
    locale: 'en-GB',
    storageState: E2E_SESSION,
    // Recording a trace for every test cost about 13 % (ADR 0023). To see why a test fails, run it again
    // with --trace=on and open the trace (pnpm exec playwright show-trace).
    trace: 'off',
  },
  projects: [
    { name: 'e2e', testMatch: '**/*.spec.ts' },
    // Review material (e2e/review-material.capture.ts); only with --project=review.
    { name: 'review', testMatch: '**/*.capture.ts' },
  ],
  webServer: [
    ...Array.from({ length: E2E_SERVERS }, (_, slot) => ({
      command: 'npx tsx test/e2e-server.ts',
      cwd: '../server',
      url: `${serverUrl(slot)}/api/health/ready`,
      env: { E2E_PORT: String(E2E_FIRST_PORT + slot), E2E_DB: `divehub_e2e_${slot}`, E2E_RUN_ID },
      reuseExistingServer: false,
      timeout: 120_000,
    })),
    // One server with billing on (hosted-DiveHub plan §4.4), for the paywall's browser tests; the others stay without it.
    {
      command: 'npx tsx test/e2e-server.ts',
      cwd: '../server',
      url: `http://localhost:${E2E_BILLING_PORT}/api/health/ready`,
      env: { E2E_PORT: String(E2E_BILLING_PORT), E2E_DB: 'divehub_e2e_billing', E2E_RUN_ID, E2E_BILLING: '1' },
      reuseExistingServer: false,
      timeout: 120_000,
    },
    // One server with DIVEHUB_DIVER_VISIBILITY=shared (ADR 0088), for diver-visibility.spec.ts.
    {
      command: 'npx tsx test/e2e-server.ts',
      cwd: '../server',
      url: `${E2E_SHARED_BASE_URL}/api/health/ready`,
      env: { E2E_PORT: String(E2E_SHARED_PORT), E2E_DB: 'divehub_e2e_shared', E2E_RUN_ID, E2E_VISIBILITY: 'shared' },
      reuseExistingServer: false,
      timeout: 120_000,
    },
  ],
});
