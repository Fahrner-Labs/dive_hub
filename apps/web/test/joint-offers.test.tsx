// Joint-dive fill offers on the web (FAH-412): the Review card and the Dive page's inline prompt show only the offered
// shared facts; accepting copies them and refreshes the offers and the Dives, declining drops the card.
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { afterEach, describe, expect, it, vi } from 'vitest';
import type { ReactNode } from 'react';
import { api, keys, type JointOfferView, type Me } from '../src/api.ts';
import { DiveJointOffers, JointOffers } from '../src/JointOffers.tsx';

const offer = (fields: JointOfferView['fields'] = ['siteId', 'maxDepthM']): JointOfferView => ({
  dive: {
    id: 'dive-1', startsAt: '2026-06-01T09:00:00Z', utcOffsetSeconds: 3600, durationSeconds: 30 * 60,
    maxDepthM: null, avgDepthM: null, site: null,
  },
  source: {
    diveId: 'dive-2', diver: { id: 'diver-2', name: 'Anna' },
    startsAt: '2026-06-01T09:05:00Z', utcOffsetSeconds: 3600, durationSeconds: 30 * 60,
    maxDepthM: 30, avgDepthM: 18, site: { id: 'site-1', name: 'Hausreef' },
  },
  fields,
});

/** An answer for the `api` client, which only reads `response.ok` and `data`. */
const answer = <T,>(data: T) => ({ data, response: new Response(null, { status: 200 }) });

function makeClient() {
  const client = new QueryClient({ defaultOptions: { queries: { retry: false, staleTime: Infinity } } });
  client.setQueryData(keys.me, { preferences: { units: 'metric' } } as unknown as Me);
  return client;
}

function renderWith(ui: ReactNode, client = makeClient()) {
  render(<QueryClientProvider client={client}>{ui}</QueryClientProvider>);
  return client;
}

afterEach(() => {
  vi.restoreAllMocks();
});

describe('a Joint-dive fill offer', () => {
  it('shows only the fields it can fill, with the buddy\'s values beside the empty ones', () => {
    const client = makeClient();
    client.setQueryData(keys.jointOffers, [offer()]);
    renderWith(<JointOffers />, client);
    expect(screen.getByRole('heading', { name: 'Fill from your buddy\'s dive' })).toBeTruthy();
    expect(screen.getByRole('heading', { name: /from anna/i })).toBeTruthy();
    expect(screen.getByRole('rowheader', { name: 'Site' })).toBeTruthy();
    expect(screen.getByText('Hausreef')).toBeTruthy();
    expect(screen.getByRole('rowheader', { name: 'Max depth' })).toBeTruthy();
    expect(screen.getByText('30 m')).toBeTruthy();
    // The average depth is not offered, so it has no row.
    expect(screen.queryByRole('rowheader', { name: 'Average depth' })).toBeNull();
  });

  it('accepts every offered field from the Dive page and refreshes the offers and the Dives', async () => {
    const user = userEvent.setup();
    const post = vi.spyOn(api, 'POST').mockResolvedValue(answer({}) as never);
    vi.spyOn(api, 'GET').mockResolvedValue(answer([offer()]) as never);
    const client = makeClient();
    client.setQueryData(keys.jointOffersOf('dive-1'), [offer()]);
    const invalidate = vi.spyOn(client, 'invalidateQueries');
    renderWith(<DiveJointOffers diveId="dive-1" />, client);

    await user.click(screen.getByRole('button', { name: /^Accept:/ }));
    await waitFor(() => expect(post).toHaveBeenCalledWith('/api/joint-dives/offers/accept', {
      body: { diveId: 'dive-1', fields: ['siteId', 'maxDepthM'] },
    }));
    await waitFor(() => expect(invalidate).toHaveBeenCalledWith({ queryKey: keys.jointOffers }));
    expect(invalidate).toHaveBeenCalledWith({ queryKey: keys.dives });
  });

  it('declines the offered fields and the card disappears', async () => {
    const user = userEvent.setup();
    const post = vi.spyOn(api, 'POST').mockResolvedValue(answer({ diveId: 'dive-1', fields: ['siteId', 'maxDepthM'] }) as never);
    // The list is read again after the decline: the fields are gone.
    vi.spyOn(api, 'GET').mockResolvedValue(answer([]) as never);
    const client = makeClient();
    client.setQueryData(keys.jointOffers, [offer()]);
    renderWith(<JointOffers />, client);

    await user.click(screen.getByRole('button', { name: /^Decline:/ }));
    await waitFor(() => expect(screen.queryByRole('heading', { name: /from anna/i })).toBeNull());
    expect(post).toHaveBeenCalledWith('/api/joint-dives/offers/decline', {
      body: { diveId: 'dive-1', fields: ['siteId', 'maxDepthM'] },
    });
  });

  it('renders nothing on both surfaces when no offer waits', () => {
    const client = makeClient();
    client.setQueryData(keys.jointOffers, []);
    client.setQueryData(keys.jointOffersOf('dive-1'), []);
    renderWith(
      <>
        <JointOffers />
        <DiveJointOffers diveId="dive-1" />
      </>,
      client,
    );
    expect(document.body.textContent).toBe('');
  });
});
