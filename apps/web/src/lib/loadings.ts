// What the Diver page shows of a Diver's current loadings (ADR 0071), and the words around it: an estimate from
// the Diver's own logged dives, never advice, with the sources the rules rest on. The server computes the numbers
// on request; nothing here stores or changes them.
import type { LoadingsView } from '../api.ts';

/** The estimates a view can carry; a missing one stays `null`, and a returned zero is a real zero. */
export type LoadingValues = Pick<LoadingsView, 'n2' | 'cns' | 'otu' | 'noFly'>;

/**
 * Whether the section has anything to say. It is hidden when every estimate is missing (no usable Dive in the
 * window, no Recording with CNS or OTU), and shown for a real zero.
 */
export function showsLoadings(v: LoadingValues): boolean {
  return v.n2 !== null || v.cns !== null || v.otu !== null || v.noFly !== null;
}

/** A percent as the server sends it (42.3 is 42.3 %), in the User's language. */
export function loadingPercent(value: number, locale: string): string {
  return new Intl.NumberFormat(locale, { style: 'percent', maximumFractionDigits: 1 }).format(value / 100);
}

/** A count such as OTU, in the User's language. */
export function loadingNumber(value: number, locale: string): string {
  return new Intl.NumberFormat(locale, { maximumFractionDigits: 1 }).format(value);
}

/**
 * The sources the client contract asks a client to link beside these numbers (ADR 0071): the rules, the CNS
 * half-life and the OTU expiry. Their titles stay in English, as the assessment's sources do.
 */
export const LOADING_SOURCES = {
  rules: {
    title: 'ADR 0071: current loadings from the Diver’s logged dives',
    url: 'https://github.com/Fahrner-Labs/dive_hub/blob/main/docs/decisions/0071-current-loadings-estimate.md',
  },
  cns: {
    title: 'Shearwater: the CNS oxygen clock',
    url: 'https://shearwater.com/blogs/community/shearwater-and-the-cns-oxygen-clock',
  },
  otu: {
    title: 'Garmin Descent: surface interval',
    url: 'https://www8.garmin.com/manuals/webhelp/GUID-D6BA1847-E20D-4241-B08C-DF0D8B052B28/EN-US/GUID-8179D8C5-35C9-450E-9E61-49180CD82701.html',
  },
} as const;
