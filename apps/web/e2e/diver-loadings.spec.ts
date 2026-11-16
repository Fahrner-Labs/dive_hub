// A Diver's current loadings (ADR 0071) on their page: residual N₂, CNS and OTU beside DAN's no-fly time, with the
// Dives the estimate rests on and the sources. The server computes them; here the page in each state, so the seeded
// logbook stays untouched and a zero is never shown as a missing estimate or the other way round.
import { expect, test, type APIRequestContext } from '@playwright/test';
import { expectGoodPage, setPreferences } from './support.ts';

const ownDiverId = async (request: APIRequestContext) =>
  ((await (await request.get('/api/divers')).json()) as { id: string }[])[0]!.id;

test.beforeEach(async ({ request }) => {
  await setPreferences(request, { language: null, units: null });
});

test('the current loadings, their basis and what they are not', { tag: ['@divers'] }, async ({ page, request }) => {
  const id = await ownDiverId(request);
  await page.route('**/api/divers/*/loadings', (route) => route.fulfill({
    json: {
      computedAt: '2026-11-16T09:00:00Z',
      basis: { windowHours: 72, divesUsed: 2, divesSkippedNoProfile: 1, lastDiveEndedAt: '2026-11-16T08:00:00Z' },
      n2: { worstCompartmentPercent: 42.3, gfHigh: 85 },
      cns: { percentNow: 12.5, percentAtLastDiveEnd: 25, halfLifeMinutes: 90 },
      otu: { last24Hours: 34.2 },
      noFly: {
        hours: 18, reason: 'several', until: '2026-11-17T02:00:00Z',
        source: {
          title: 'DAN 2002: guidelines for flying after diving',
          url: 'https://dan.org/health-medicine/health-resource/health-safety-guidelines/guidelines-for-flying-after-diving/',
        },
      },
    },
  }));
  await page.goto(`/#/divers/${id}`);
  const section = page.locator('section.panel', { has: page.getByRole('heading', { name: 'Current loadings' }) });
  await expect(section.getByRole('heading', { name: 'Current loadings', level: 2 })).toBeVisible();
  // What each number is, in the Diver's GF and language; the CNS value keeps both ends of the same clock.
  await expect(section).toContainText('Residual nitrogen');
  await expect(section).toContainText('42.3% of the limit at GF 85');
  await expect(section).toContainText('Oxygen clock (CNS)');
  await expect(section).toContainText('12.5% now · 25% at the end of the last dive');
  await expect(section).toContainText('OTU, last 24 hours');
  await expect(section).toContainText('34.2');
  // The no-fly time is DAN's guideline, with its source; the provenance names the Dives used and left out.
  await expect(section).toContainText('Flying after the last dive');
  await expect(section).toContainText(/Not before .+ \(18 hours after several dives or days of diving\)\./);
  await expect(section.getByRole('link', { name: 'DAN 2002: guidelines for flying after diving' })).toHaveAttribute('href', /dan\.org/);
  await expect(section).toContainText(/From 2 logged dives in the last 72 hours; the last ended /);
  await expect(section).toContainText('1 dive without a depth profile contributes nothing.');
  // An estimate from the Diver's own logbook, never advice, with the sources the rules rest on.
  await expect(section).toContainText('An estimate from the Diver’s own logged dives, not advice');
  await expect(section.getByRole('link', { name: /ADR 0071/ })).toHaveAttribute('href', /0071-current-loadings-estimate\.md$/);
  await expect(section.getByRole('link', { name: 'Shearwater: the CNS oxygen clock' })).toHaveAttribute('href', /shearwater\.com/);
  await expect(section.getByRole('link', { name: 'Garmin Descent: surface interval' })).toHaveAttribute('href', /garmin\.com/);
  await expectGoodPage(page, 'Erika');
});

test('a returned zero is a value; with every estimate missing the section is not there', { tag: ['@divers'] }, async ({ page, request }) => {
  const id = await ownDiverId(request);
  await page.route('**/api/divers/*/loadings', (route) => route.fulfill({
    json: {
      computedAt: '2026-11-16T09:00:00Z',
      basis: { windowHours: 72, divesUsed: 1, divesSkippedNoProfile: 0, lastDiveEndedAt: '2026-11-16T08:00:00Z' },
      n2: { worstCompartmentPercent: 0, gfHigh: 85 },
      cns: { percentNow: 0, percentAtLastDiveEnd: 0, halfLifeMinutes: 90 },
      otu: { last24Hours: 0 },
      noFly: null,
    },
  }));
  await page.goto(`/#/divers/${id}`);
  const section = page.locator('section.panel', { has: page.getByRole('heading', { name: 'Current loadings' }) });
  await expect(section).toContainText('0% of the limit at GF 85');
  await expect(section).toContainText('0% now · 0% at the end of the last dive');
  await expect(section).toContainText('OTU, last 24 hours');
  await expect(section.locator('dd', { hasText: /^0$/ })).toBeVisible();
  await expectGoodPage(page, 'Erika');

  // Nothing to say: the section is gone, not a row of zeros.
  await page.unroute('**/api/divers/*/loadings');
  await page.route('**/api/divers/*/loadings', (route) => route.fulfill({
    json: {
      computedAt: '2026-11-16T09:00:00Z',
      basis: { windowHours: 72, divesUsed: 0, divesSkippedNoProfile: 0, lastDiveEndedAt: null },
      n2: null, cns: null, otu: null, noFly: null,
    },
  }));
  await page.reload();
  await expect(page.getByRole('heading', { name: 'Current loadings' })).toHaveCount(0);
  await expectGoodPage(page, 'Erika');
});
