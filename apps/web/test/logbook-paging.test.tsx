// Logbook paging (FAH-260): page 2 renders its rows. The live-arrivals hook is page-1 only; its reset past page 1 must
// not make every render schedule another one.
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { render, screen } from '@testing-library/react';
import { beforeEach, describe, expect, it } from 'vitest';
import { keys, PAGE_SIZE, type DiveSummary, type LogbookPage } from '../src/api.ts';
import { DiveList } from '../src/DiveList.tsx';

const dive = (number: number): DiveSummary => ({
  id: `dive-${number}`,
  diverId: 'diver-1',
  number,
  startsAt: `2026-04-${String((number % 28) + 1).padStart(2, '0')}T08:00:00Z`,
  utcOffsetSeconds: 3600,
  utcOffsetSource: 'device',
  durationSeconds: 2_400,
  maxDepthM: 20,
  avgDepthM: 15,
  site: { id: 'site-1', name: 'Hausreef' },
  findings: 0,
  recordings: 1,
  fromProvider: null,
  gases: [],
  surfaceIntervalSeconds: null,
  participants: [],
  waiting: [],
  profile: null,
});

const page2: LogbookPage = {
  dives: Array.from({ length: PAGE_SIZE }, (_, i) => dive(PAGE_SIZE + i + 1)),
  total: PAGE_SIZE * 2,
  counts: { noRecording: 0, noSite: 0, withFindings: 0, notAtProvider: 0, changedSinceSent: 0 },
  totals: { dives: PAGE_SIZE * 2, durationSeconds: 2_400 * PAGE_SIZE * 2, deepestM: 20, lastDiveAt: null },
  months: [],
};

const renderPage2 = () => {
  // The phone layout's media queries are read while rendering; jsdom has none.
  window.matchMedia = (query: string) => ({
    matches: false, media: query, onchange: null,
    addEventListener: () => {}, removeEventListener: () => {}, addListener: () => {}, removeListener: () => {}, dispatchEvent: () => false,
  });
  const client = new QueryClient({ defaultOptions: { queries: { retry: false } } });
  client.setQueryData([...keys.dives, { page: 2 }], page2);
  client.setQueryData(keys.divers, []);
  client.setQueryData(keys.providers, []);
  client.setQueryData(keys.connections, []);
  client.setQueryData(keys.me, { preferences: { units: 'metric' } });
  render(
    <QueryClientProvider client={client}>
      <DiveList params={{ page: 2 }} />
    </QueryClientProvider>,
  );
};

describe('the logbook past page 1', () => {
  beforeEach(() => { location.hash = ''; });

  it('renders page 2 without looping through the live-arrivals reset', () => {
    renderPage2();
    expect(screen.getAllByRole('listitem')).toHaveLength(PAGE_SIZE);
    expect(screen.getByText('26–50 of 50 dives')).toBeTruthy();
  });
});
