// Buddy suggestions on the Review page (ADR 0070): the accept dialog offers each overlapping Dive to link, and
// creating a new Dive, including when there is no overlap at all.
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { describe, expect, it, vi } from 'vitest';
import { SuggestionAcceptDialog } from '../src/BuddySuggestions.tsx';
import type { BuddySuggestionPreviewView, Me } from '../src/api.ts';

const overlap = (id: string, startsAt: string): BuddySuggestionPreviewView['overlaps'][number] => ({
  id, startsAt, utcOffsetSeconds: 3600, durationSeconds: 30 * 60, maxDepthM: 16,
  site: { id: 'site-1', name: 'Hausreef' },
});

const preview = (overlaps: BuddySuggestionPreviewView['overlaps']): BuddySuggestionPreviewView => ({
  id: 'suggestion-1',
  status: 'pending',
  createdAt: '2026-04-02T07:00:00Z',
  resolvedAt: null,
  diver: { id: 'diver-1', name: 'Anna' },
  dive: {
    id: 'dive-1',
    startsAt: '2026-04-02T08:00:00Z',
    utcOffsetSeconds: 3600,
    durationSeconds: 30 * 60,
    maxDepthM: 18,
    avgDepthM: 14,
    site: { id: 'site-1', name: 'Hausreef' },
    owner: { id: 'diver-owner', name: 'Tim' },
    centres: [],
    participants: [{ diverId: 'diver-owner', name: 'Tim', role: 'buddy' }],
  },
  overlaps,
});

const renderDialog = (props: Partial<Parameters<typeof SuggestionAcceptDialog>[0]> = {}) => {
  const client = new QueryClient({ defaultOptions: { queries: { retry: false } } });
  client.setQueryData(['me'], { preferences: { units: 'metric' } } as unknown as Me);
  const onAccept = vi.fn();
  render(
    <QueryClientProvider client={client}>
      <SuggestionAcceptDialog preview={preview([])} onAccept={onAccept} onClose={() => {}} {...props} />
    </QueryClientProvider>,
  );
  return { onAccept };
};

describe('the suggestion accept dialog', () => {
  it('says that a new Dive is made when no Dive of the Diver overlaps', async () => {
    const user = userEvent.setup();
    const { onAccept } = renderDialog();
    expect(screen.getByText(/no dive at this time/i)).toBeTruthy();
    expect(screen.queryByRole('button', { name: /link to your dive/i })).toBeNull();
    // The null error of a fresh mutation is not shown as a failure.
    expect(screen.queryByRole('alert')).toBeNull();
    await user.click(screen.getByRole('button', { name: 'Create a new dive' }));
    expect(onAccept).toHaveBeenCalledWith();
  });

  it('offers the one overlapping Dive to link, and creating a new one besides', async () => {
    const user = userEvent.setup();
    const { onAccept } = renderDialog({ preview: preview([overlap('dive-9', '2026-04-02T08:15:00Z')]) });
    await user.click(screen.getByRole('button', { name: /link to your dive/i }));
    expect(onAccept).toHaveBeenCalledWith('dive-9');
    expect(screen.getByRole('button', { name: 'Create a new dive' })).toBeTruthy();
  });

  it('offers every overlapping Dive, each named by its own time', () => {
    renderDialog({ preview: preview([overlap('dive-9', '2026-04-02T08:15:00Z'), overlap('dive-10', '2026-04-02T09:30:00Z')]) });
    expect(screen.getAllByRole('button', { name: /link to your dive/i })).toHaveLength(2);
  });

  it('stays open with the error when accepting fails', () => {
    renderDialog({ preview: preview([]), error: new Error('boom') });
    expect(screen.getByRole('alert').textContent).toContain('Something went wrong. Try again.');
    expect(screen.getByRole('dialog')).toBeTruthy();
  });
});
