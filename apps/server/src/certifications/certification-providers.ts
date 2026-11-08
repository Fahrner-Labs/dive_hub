// What certifications needs of the Providers and their Connections (ADR 0050), and nothing more: the User's own
// Connection with its Provider's card reader, a card's picture, and whether a failure is the Provider refusing.
// certifications owns this port and the card shape; the provider layer implements it and compose wires the real
// registry and Connections in, so certifications doesn't import providers (ADR 0053's seam for that pair).

/** One certification card in the account (ADR 0050): the agency's own record, as typed values. */
export interface RemoteCertification {
  /** The Provider's ID of the card (SSI: `ccard_uid`); the key of a re-import. */
  remoteId: string;
  agency: string;
  /** The course or level as the Provider names it. */
  level: string;
  courseId: string | null;
  category: string | null;
  issuedOn: string | null;
  expiresOn: string | null;
  divesAtIssue: number | null;
  instructorName: string | null;
  instructorNumber: string | null;
  centreName: string | null;
  centreNumber: string | null;
  centreCity: string | null;
  centreCountry: string | null;
  /** Where the card's picture is (public, no sign-in); null when the Provider has none. Only an adapter makes it. */
  imageUrl: string | null;
}

/** The User's own Connection, with its Provider's card reader. */
export interface CertificationAccount {
  /** The Provider's id (the card's `source`). */
  readonly provider: string;
  readonly diverId: string;
  /** The account's cards, read live, inside the Connection's access. */
  cards(): Promise<RemoteCertification[]>;
}

/** The Provider and Connection lookups certifications needs; the provider layer implements it. */
export interface CertificationProviders {
  /**
   * The User's own Connection, with its Provider's card reader. Throws the Provider refusal `provider_unsupported`
   * for a Provider this instance has no card reader for.
   */
  onConnection(userId: string, connectionId: string): Promise<CertificationAccount>;
  /** Whether this instance's Provider reads cards at all. */
  supports(provider: string): boolean;
  /** A card's picture from the URL a read gave: the bytes and their media type (no sign-in). */
  image(provider: string, url: string): Promise<{ bytes: Uint8Array; mediaType: string }>;
  /** Whether a failure is the Provider refusing, not a bug; only such a failure ends a picture fetch `failed`. */
  isProviderRefusal(error: unknown): boolean;
}
