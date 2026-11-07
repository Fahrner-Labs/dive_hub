// What divers needs of a Diver's certification cards (ADR 0050), and nothing more: the summaries on the Divers list,
// and the pictures of a deleted Diver. divers owns this port and certifications implements it; app.ts passes the real
// service in, so divers doesn't import certifications (ADR 0053's seam for that pair).

/** What the Divers list shows of a Diver's cards. `newest` is the first card of the list: an expired card only when all are. */
export interface CertificationSummary {
  count: number;
  newest: { agency: string; level: string } | null;
  expired: number;
  expiringSoon: number;
}

export const NO_CERTIFICATIONS: CertificationSummary = { count: 0, newest: null, expired: 0, expiringSoon: 0 };

/** The certification capability the diver routes use. */
export interface DiverCertifications {
  /** The summaries of several Divers' cards, by Diver id (the caller passes the User's own Divers). */
  summaries(diverIds: string[]): Promise<Map<string, CertificationSummary>>;
  /** The stored picture keys of a Diver's cards, to release when it is deleted. */
  imageKeysOf(diverId: string): Promise<string[]>;
  releaseKeys(keys: string[]): Promise<void>;
}
