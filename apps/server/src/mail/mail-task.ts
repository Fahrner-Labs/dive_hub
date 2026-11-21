// The worker task for one queued e-mail: the service reads the User again, honours the opt-out and talks to the relay.
import type { MailService } from './mail-service.js';

export function mailTask(mail: Pick<MailService, 'deliver'>) {
  return async (payload: unknown) => {
    await mail.deliver(payload);
  };
}
