import { Trans, useTranslation } from 'react-i18next';
import { usePageTitle } from './lib/page.ts';
import { Panel } from './ui/index.ts';

/** The sections in order; their texts live under `legal.<page>.sections.<key>` with a title and a body. */
const SECTIONS = {
  privacy: [
    { key: 'controller', title: 'legal.privacy.sections.controller.title', body: 'legal.privacy.sections.controller.body' },
    { key: 'data', title: 'legal.privacy.sections.data.title', body: 'legal.privacy.sections.data.body' },
    { key: 'purposes', title: 'legal.privacy.sections.purposes.title', body: 'legal.privacy.sections.purposes.body' },
    { key: 'processors', title: 'legal.privacy.sections.processors.title', body: 'legal.privacy.sections.processors.body' },
    { key: 'retention', title: 'legal.privacy.sections.retention.title', body: 'legal.privacy.sections.retention.body' },
    { key: 'rights', title: 'legal.privacy.sections.rights.title', body: 'legal.privacy.sections.rights.body' },
    { key: 'cookies', title: 'legal.privacy.sections.cookies.title', body: 'legal.privacy.sections.cookies.body' },
    { key: 'changes', title: 'legal.privacy.sections.changes.title', body: 'legal.privacy.sections.changes.body' },
  ],
  terms: [
    { key: 'scope', title: 'legal.terms.sections.scope.title', body: 'legal.terms.sections.scope.body' },
    { key: 'account', title: 'legal.terms.sections.account.title', body: 'legal.terms.sections.account.body' },
    { key: 'acceptableUse', title: 'legal.terms.sections.acceptableUse.title', body: 'legal.terms.sections.acceptableUse.body' },
    { key: 'content', title: 'legal.terms.sections.content.title', body: 'legal.terms.sections.content.body' },
    { key: 'paid', title: 'legal.terms.sections.paid.title', body: 'legal.terms.sections.paid.body' },
    { key: 'availability', title: 'legal.terms.sections.availability.title', body: 'legal.terms.sections.availability.body' },
    { key: 'liability', title: 'legal.terms.sections.liability.title', body: 'legal.terms.sections.liability.body' },
    { key: 'changes', title: 'legal.terms.sections.changes.title', body: 'legal.terms.sections.changes.body' },
    { key: 'contact', title: 'legal.terms.sections.contact.title', body: 'legal.terms.sections.contact.body' },
  ],
} as const;

export type LegalPage = keyof typeof SECTIONS;

/**
 * Static legal page shells (hosted plan §10.4): the structure and placeholders of the Privacy Policy and Terms of
 * Service. The operator fills the final wording; nothing here is legal advice.
 */
export function Legal({ page }: { page: LegalPage }) {
  const { t } = useTranslation();
  usePageTitle(t(`legal.${page}.title`));
  return (
    <Panel title={t(`legal.${page}.title`)} level={1}>
      <p className="muted">{t('legal.draft')}</p>
      <p className="muted">{t(`legal.${page}.updated`)}</p>
      {SECTIONS[page].map((section) => (
        <section key={section.key} className="legal-section">
          <h2>{t(section.title)}</h2>
          <p>{t(section.body)}</p>
          {section.key === 'processors' && (
            <ul>
              <li><Trans i18nKey="legal.privacy.processors.hosting" components={{ strong: <strong /> }} /></li>
              <li><Trans i18nKey="legal.privacy.processors.payments" components={{ strong: <strong /> }} /></li>
              <li><Trans i18nKey="legal.privacy.processors.email" components={{ strong: <strong /> }} /></li>
            </ul>
          )}
        </section>
      ))}
    </Panel>
  );
}
