import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { MutationCache, QueryCache, QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { I18nProvider } from 'react-aria-components';
import { useTranslation } from 'react-i18next';
import { App } from './App.tsx';
import { isUnauthorized, keys, shouldRetry } from './api.ts';
import { initI18n } from './i18n/index.ts';
import { installDiagnostics } from './lib/diagnostics.ts';
import { installStaleChunkReload } from './lib/lazy-reload.ts';
import './design/tokens.css';
import './design/base.css';
import './ui/ui.css';
import './pages.css';

initI18n(navigator.languages);
// Capture errors from the moment the app starts, so a failure before the first render is reportable too (FAH-292).
installDiagnostics();
// A deploy can leave an open tab asking for lazy chunks that no longer exist (FAH-447): recover once.
installStaleChunkReload();

// A 401 from any call means the session ended (expired or signed out elsewhere): show sign-in.
const onError = (error: unknown) => {
  if (isUnauthorized(error)) queryClient.setQueryData(keys.me, null);
};
const queryClient: QueryClient = new QueryClient({
  queryCache: new QueryCache({ onError }),
  mutationCache: new MutationCache({ onError }),
  defaultOptions: {
    queries: { staleTime: 30_000, retry: shouldRetry },
  },
});

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <QueryClientProvider client={queryClient}>
      <LocalizedApp />
    </QueryClientProvider>
  </StrictMode>,
);

/** React Aria's number, date and select components follow the UI language, not the browser's. */
function LocalizedApp() {
  const { i18n } = useTranslation();
  return (
    <I18nProvider locale={i18n.language}>
      <App />
    </I18nProvider>
  );
}
