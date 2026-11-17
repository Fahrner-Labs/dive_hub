// What dives needs of a Diver's certification cards (ADR 0050), and nothing more: the expiry hint beside a Dive's
// Participants. Only a User who manages the Participant sees it; everyone else gets no hint at all. dives owns this
// port and certifications implements it; app.ts passes the real service in, so dives doesn't import certifications
// (ADR 0053's seam for that pair).

/** A card's state as the hint says it: `soon` within 90 days, `expired` after the day. */
export type CertificationHintExpiry = 'soon' | 'expired';

/** The most urgent expired or expiring card of one Participant. */
export interface CertificationHint {
  agency: string;
  level: string;
  expiry: CertificationHintExpiry;
}

/** The certification capability the Dive routes use. */
export interface DiveCertifications {
  /**
   * The most urgent expired or expiring card of each of these Divers, by Diver id, for the User named. A Diver the
   * User does not manage is left out, as is one whose cards are all valid, have no date, or have none.
   */
  hintsFor(userId: string, diverIds: string[]): Promise<Map<string, CertificationHint>>;
}
