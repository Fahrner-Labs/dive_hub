// The mail seam (accepted hosted plan §6.1): shared infrastructure at the same tier as storage/ and secrets/
// (ADR 0053 T0), so any domain module may send through it and no module edges are added. A provider takes one plain-text
// message. Resend is today's implementation; Postmark is the documented fallback, another provider behind this interface.
export interface MailMessage {
  to: string;
  subject: string;
  text: string;
}

/** `sendMail` is the whole contract: a refusal throws, so the caller can log it and decide what the User sees. */
export interface MailProvider {
  sendMail(message: MailMessage): Promise<void>;
}

/** What became of an attempted send, without throwing at every call site that must keep working either way. */
export type MailDelivery = { sent: true } | { sent: false; error: string };

/** Try one send; a failure becomes a value the route can log, never a silently dropped message. */
export async function attemptDelivery(send: () => Promise<void>): Promise<MailDelivery> {
  try {
    await send();
    return { sent: true };
  } catch (error) {
    return { sent: false, error: error instanceof Error ? error.message : String(error) };
  }
}
