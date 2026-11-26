// The real Sources of a Site import, configured for this instance (ADR 0021, 0025). It sits beside main.ts because it
// wires SSI's site list from providers/ssi/ (ADR 0027) into sites/, which doesn't import providers/ itself.
import { DEFAULT_OVERPASS_URL, createOverpassSource } from './sites/import/overpass.js';
import { createPoliteHttp, type Fetch } from './sites/import/polite-http.js';
import type { SiteSourceAdapter } from './sites/import/site-source.js';
import { DEFAULT_SSI_SITES_URL, createSsiSiteSource } from './providers/ssi/ssi-sites.js';
import { DEFAULT_WIKIDATA_SPARQL_URL, createWikidataSource } from './sites/import/wikidata.js';
import type { ImportSource } from './sites/sources.js';

export function createSiteSources(options: {
  /** The Sources this instance enables (DIVEHUB_SITE_SOURCES, ADR 0087); the others are never constructed. */
  sources: readonly ImportSource[];
  overpassUrl?: string | undefined;
  wikidataUrl?: string | undefined;
  ssiSitesUrl?: string | undefined;
  /** The operator's e-mail address or URL, for the User-Agent (DIVEHUB_CONTACT). */
  contact?: string | undefined;
  /** Replaces the network (the browser tests replay recorded answers). */
  fetch?: Fetch;
}): Partial<Record<ImportSource, SiteSourceAdapter>> {
  if (options.sources.length === 0) throw new Error('At least one site source must be enabled (DIVEHUB_SITE_SOURCES)');
  const http = createPoliteHttp({ contact: options.contact, ...(options.fetch && { fetch: options.fetch }) });
  return {
    ...(options.sources.includes('osm') && { osm: createOverpassSource(http, options.overpassUrl ?? DEFAULT_OVERPASS_URL) }),
    ...(options.sources.includes('wikidata') && { wikidata: createWikidataSource(http, options.wikidataUrl ?? DEFAULT_WIKIDATA_SPARQL_URL) }),
    ...(options.sources.includes('ssi') && { ssi: createSsiSiteSource(http, options.ssiSitesUrl ?? DEFAULT_SSI_SITES_URL) }),
  };
}
