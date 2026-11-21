// The restore path's intro (FAH-207): the locale's <code> markup must render as code elements, not raw text —
// t() alone would print the tags, so the intro goes through Trans like the setup intro and the success notice do.
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { render, screen } from '@testing-library/react';
import { describe, expect, it } from 'vitest';
import { keys } from '../src/api.ts';
import { SetupRestore } from '../src/account/SetupRestore.tsx';

const renderRestore = () => {
  const client = new QueryClient({ defaultOptions: { queries: { retry: false } } });
  client.setQueryData(keys.setupRestoreArchives, { archives: [] });
  render(
    <QueryClientProvider client={client}>
      <SetupRestore />
    </QueryClientProvider>,
  );
};

describe('the restore intro', () => {
  it('renders the archive name and the restore folder as code elements', () => {
    renderRestore();
    expect(screen.getByText('.divehub-backup').tagName).toBe('CODE');
    expect(screen.getByText('/data/restore/').tagName).toBe('CODE');
    expect(screen.queryByText((content) => content.includes('<code>'))).toBeNull();
  });
});
