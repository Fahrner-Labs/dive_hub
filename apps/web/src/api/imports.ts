import { queryOptions } from '@tanstack/react-query';
import { liveHealthy } from '../lib/live.ts';
import { keys } from './keys.ts';
import { api, noAnswerError, request, unwrap, type Ok } from './request.ts';

export type ImportView = Ok<'/api/imports'>[number];

export const importsQuery = () =>
  queryOptions({
    queryKey: keys.imports,
    queryFn: async () => request(api.GET('/api/imports')),
    // Poll while an Import is still being processed.
    refetchInterval: (q) => (liveHealthy() ? false : (q.state.data?.some((i) => i.status === 'pending' || i.status === 'processing') ? 1000 : false)),
  });

/** One Import, asked again every second while it runs (an import from a Provider, ADR 0030). */
export const importQuery = (id: string) => queryOptions({
  queryKey: keys.importOf(id),
  queryFn: async () => request(api.GET('/api/imports/{id}', { params: { path: { id } } })),
  refetchInterval: (q) => (liveHealthy() ? false : (q.state.data && (q.state.data.status === 'pending' || q.state.data.status === 'processing') ? 1000 : false)),
});

/** A multipart upload, which openapi-fetch's JSON body doesn't carry; its answer goes through unwrap like any other. */
export async function uploadFile(file: File) {
  const body = new FormData();
  body.append('file', file);
  const response = await fetch('/api/imports', { method: 'POST', body }).catch((error: unknown) => {
    throw noAnswerError(error);
  });
  const json: unknown = await response.json().catch(() => undefined);
  return unwrap({ data: json as ImportView, error: json, response });
}
