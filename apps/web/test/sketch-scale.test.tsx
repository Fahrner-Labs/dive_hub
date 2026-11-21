// A decision card's sketch scale (FAH-231): the caption names the scale only when a sketch is drawn, so an empty slot
// for a Dive whose Recording is not there yet has no scale beneath it.
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { render, screen } from '@testing-library/react';
import { describe, expect, it } from 'vitest';
import type { DiveSummary, Me } from '../src/api.ts';
import { SketchScale } from '../src/dives/ProfileSketch.tsx';

const renderScale = (profile: DiveSummary['profile']) => {
  const client = new QueryClient({ defaultOptions: { queries: { retry: false } } });
  client.setQueryData(['me'], { preferences: { units: 'metric' } } as unknown as Me);
  render(
    <QueryClientProvider client={client}>
      <SketchScale profile={profile} scaleM={40} />
    </QueryClientProvider>,
  );
};

describe('the sketch scale', () => {
  it('names the scale of the sketch that is drawn', () => {
    renderScale({ depthsM: [0, 18, 0], spanSeconds: 1800, ascentBands: [] });
    expect(screen.getByText('0–40 m')).toBeTruthy();
  });

  it('is not there under an empty sketch slot', () => {
    renderScale(null);
    expect(screen.queryByText(/^0–/)).toBeNull();
  });
});
