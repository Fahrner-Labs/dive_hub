import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useEffect, useRef, useState, type FormEvent } from 'react';
import { Trans, useTranslation } from 'react-i18next';
import { api, keys, siteImportsQuery, sitePurgesQuery, siteSourcesQuery, unwrap, type SiteImportArea, type SiteImportView, type SitePurgeView } from './api.ts';
import { LANGUAGES, pickLanguage } from './i18n/languages.ts';
import { announce } from './lib/announce.ts';
import { useDisplay, useErrorText } from './lib/display.ts';
import { countryOptions } from './lib/geo.ts';
import { usePageTitle } from './lib/page.ts';
import { settingsHref } from './lib/settings.ts';
import { Badge, Button, Checkbox, ConfirmButton, Form, Icon, Muted, Notice, NumberField, PageHeader, Panel, RadioGroup, Select } from './ui/index.ts';

type Source = SiteImportView['sources'][number];
type AreaKind = SiteImportArea['kind'];

const NO_COUNTRY = 'none';
const ODBL_URL = 'https://opendatacommons.org/licenses/odbl/1-0/';
/** Every Source's label, in the order the form shows them; the record forces a new Source to get one. */
const SOURCE_LABEL = {
  wikidata: 'siteImport.sourceWikidata', osm: 'siteImport.sourceOsm', ssi: 'siteImport.sourceSsi',
} as const satisfies Record<Source, string>;
const SOURCE_ORDER = Object.keys(SOURCE_LABEL) as Source[];
const running = (i: SiteImportView) => i.status === 'queued' || i.status === 'running';
/** Findings shown per kind; a worldwide SSI run can report thousands. */
const FINDINGS_SHOWN = 50;

/**
 * An admin imports Dive sites (ADR 0021, 0025): Wikidata (CC0), OpenStreetMap (ODbL, whose obligations the admin
 * confirms first) and SSI (no licence: the admin confirms the explanation and the risk), for a country, a box or
 * everywhere, optionally only filling sites already here. The import runs on the server; this page follows it.
 */
export function SiteImportPage() {
  const { t } = useTranslation();
  usePageTitle(t('siteImport.title'));
  return (
    <>
      <PageHeader title={t('siteImport.title')} lead={t('siteImport.intro')} back={{ href: settingsHref('admin'), label: t('settings.section.admin') }} />
      <StartImport />
      <PurgeSource />
      <LatestImports />
      <LatestPurges />
    </>
  );
}

function StartImport() {
  const { t, i18n } = useTranslation();
  const errorText = useErrorText();
  const display = useDisplay();
  const queryClient = useQueryClient();
  const imports = useQuery(siteImportsQuery());
  // Driven by the server (ADR 0087): an instance may construct fewer Sources than the vocabulary names.
  const enabledSources = useQuery(siteSourcesQuery());
  const busy = imports.data?.some(running) ?? false;
  const [sources, setSources] = useState<Record<Source, boolean>>({ osm: true, wikidata: true, ssi: false });
  const offered = SOURCE_ORDER.filter((source) => enabledSources.data?.includes(source));
  const chosen = offered.filter((source) => sources[source]);
  const [kind, setKind] = useState<AreaKind>('country');
  const [country, setCountry] = useState<string | null>(null);
  const [box, setBox] = useState({ south: Number.NaN, west: Number.NaN, north: Number.NaN, east: Number.NaN });
  const [language, setLanguage] = useState<string>(pickLanguage(i18n.language, []));
  const [odbl, setOdbl] = useState(false);
  const [ssiConfirmed, setSsiConfirmed] = useState(false);
  const [onlyFill, setOnlyFill] = useState(false);
  // Says what is missing; set on submit only, so nothing moves under a pointer while fields commit.
  const [problem, setProblem] = useState<string | null>(null);

  const start = useMutation({
    mutationFn: async (body: { sources: Source[]; area: SiteImportArea; language: string; confirmOdbl: boolean; confirmSsi: boolean; createSites: boolean }) =>
      unwrap(await api.POST('/api/admin/site-imports', { body })),
    onSuccess: async () => {
      announce(t('siteImport.started'));
      await queryClient.invalidateQueries({ queryKey: keys.siteImports });
    },
  });

  const submit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const area: SiteImportArea | null = kind === 'world' ? { kind }
      : kind === 'country' ? (country ? { kind, country } : null)
      : Object.values(box).some(Number.isNaN) || box.south >= box.north ? null : { kind, ...box };
    const missing = chosen.length === 0 ? t('siteImport.needSource')
      : !area ? (kind === 'country' ? t('siteImport.needCountry') : t('siteImport.needBox'))
      : chosen.includes('osm') && !odbl ? t('siteImport.needOdbl')
      : chosen.includes('ssi') && !ssiConfirmed ? t('siteImport.needSsi') : null;
    setProblem(missing);
    if (missing || !area) return;
    start.mutate({
      sources: chosen, area, language, confirmOdbl: chosen.includes('osm') && odbl, confirmSsi: chosen.includes('ssi') && ssiConfirmed, createSites: !onlyFill,
    });
  };

  return (
    <Panel title={t('siteImport.new')}>
      <Form className="form" onSubmit={submit}>
        <fieldset className="field-set">
          <legend className="field-label">{t('siteImport.sources')}</legend>
          {offered.map((source) => (
            <Checkbox key={source} isSelected={sources[source]} onChange={(v) => setSources((s) => ({ ...s, [source]: v }))}>
              {t(SOURCE_LABEL[source])}
            </Checkbox>
          ))}
        </fieldset>
        {enabledSources.error && <Notice tone="danger">{errorText(enabledSources.error)}</Notice>}
        {chosen.includes('osm') && (
          // Standing terms that wait for the admin's confirmation: tinted, not a Notice (which announces itself).
          <div className="terms">
            <p><strong>{t('siteImport.odblTitle')}</strong></p>
            <p>{t('siteImport.odblCredit')}</p>
            <p>{t('siteImport.odblShareAlike')}</p>
            <p>
              <Trans
                i18nKey="siteImport.odblMore"
                components={{ license: <a href={ODBL_URL} target="_blank" rel="noopener noreferrer" className="external-link" /> }}
              />
            </p>
            <Checkbox isSelected={odbl} onChange={setOdbl}>{t('siteImport.odblConfirm')}</Checkbox>
          </div>
        )}
        {chosen.includes('ssi') && (
          // No licence to accept: the admin confirms they understood and take the decision (ADR 0025).
          <div className="terms">
            <p><strong>{t('siteImport.ssiTitle')}</strong></p>
            <p>{t('siteImport.ssiNoConditions')}</p>
            <p>{t('siteImport.ssiDatabase')}</p>
            <p>{t('siteImport.ssiRisk')}</p>
            <p>{t('siteImport.ssiTaken')}</p>
            <Checkbox isSelected={ssiConfirmed} onChange={setSsiConfirmed}>{t('siteImport.ssiConfirm')}</Checkbox>
          </div>
        )}
        <RadioGroup
          label={t('siteImport.area')}
          value={kind}
          onChange={(v) => setKind(v as AreaKind)}
          options={[
            { value: 'country', label: t('siteImport.areaCountry') },
            { value: 'box', label: t('siteImport.areaBoxOption') },
            { value: 'world', label: t('siteImport.areaWorld') },
          ]}
        />
        {kind === 'country' && (
          <Select
            label={t('sites.country')}
            value={country ?? NO_COUNTRY}
            onChange={(v) => setCountry(!v || v === NO_COUNTRY ? null : v)}
            options={[{ id: NO_COUNTRY, label: t('siteImport.chooseCountry') }, ...countryOptions(display.locale)]}
          />
        )}
        {kind === 'box' && (
          <>
            <Muted>{t('siteImport.boxHint')}</Muted>
            <div className="form-grid">
              <NumberField label={t('siteImport.south')} value={box.south} onChange={(v) => setBox((b) => ({ ...b, south: v }))} minValue={-90} maxValue={90} formatOptions={{ maximumFractionDigits: 6 }} />
              <NumberField label={t('siteImport.north')} value={box.north} onChange={(v) => setBox((b) => ({ ...b, north: v }))} minValue={-90} maxValue={90} formatOptions={{ maximumFractionDigits: 6 }} />
              <NumberField label={t('siteImport.west')} value={box.west} onChange={(v) => setBox((b) => ({ ...b, west: v }))} minValue={-180} maxValue={180} formatOptions={{ maximumFractionDigits: 6 }} />
              <NumberField label={t('siteImport.east')} value={box.east} onChange={(v) => setBox((b) => ({ ...b, east: v }))} minValue={-180} maxValue={180} formatOptions={{ maximumFractionDigits: 6 }} />
            </div>
          </>
        )}
        {kind === 'world' && <Muted>{t('siteImport.worldHint')}</Muted>}
        <div className="field-block">
          <Checkbox isSelected={onlyFill} onChange={setOnlyFill}>{t('siteImport.onlyFill')}</Checkbox>
          <Muted>{t('siteImport.onlyFillHint')}</Muted>
        </div>
        <Select
          label={t('siteImport.language')}
          description={t('siteImport.languageHint')}
          value={language}
          onChange={(v) => setLanguage(v || 'en')}
          options={Object.entries(LANGUAGES).map(([id, label]) => ({ id, label }))}
        />
        {problem && <Notice tone="danger">{problem}</Notice>}
        {start.error && <Notice tone="danger">{errorText(start.error)}</Notice>}
        {busy && <Muted>{t('siteImport.busy')}</Muted>}
        <div className="form-actions">
          <Button type="submit" variant="primary" icon="siteImport" isPending={start.isPending} isDisabled={busy || !enabledSources.data}>{t('siteImport.start')}</Button>
        </div>
      </Form>
    </Panel>
  );
}

/**
 * The pre-agreed remedy if a Source objects to its site data (FAH-528): remove every External ID of one Source and
 * the sites it leaves without data or a Dive. Sites a Dive still names stay and are reported; the admin resolves
 * them and runs it again. The dialog says what cannot be undone.
 */
function PurgeSource() {
  const { t } = useTranslation();
  const errorText = useErrorText();
  const queryClient = useQueryClient();
  // The server drives the offered Sources (DIVEHUB_SITE_SOURCES, ADR 0087): never keep a full list here.
  const sources = useQuery(siteSourcesQuery());
  const offered = sources.data ?? [];
  const [chosen, setChosen] = useState<Source>('ssi');
  const source = offered.includes(chosen) ? chosen : offered[0];
  const name = source ? t(`siteImport.sourceName.${source}`) : '';
  const purge = useMutation({
    mutationFn: async (remove: Source) => unwrap(await api.POST('/api/admin/site-imports/purge', { body: { source: remove } })),
    onSuccess: async () => {
      await Promise.all([
        queryClient.invalidateQueries({ queryKey: keys.sitePurges }),
        queryClient.invalidateQueries({ queryKey: keys.sites }),
      ]);
    },
  });

  return (
    <Panel title={t('siteImport.purge.title')}>
      <p>{t('siteImport.purge.intro')}</p>
      {sources.error && <Notice tone="danger">{errorText(sources.error)}</Notice>}
      {sources.data && (
        <Select
          label={t('siteImport.purge.source')}
          description={t('siteImport.purge.sourceHint')}
          value={source ?? null}
          onChange={(v) => setChosen((v ?? offered[0] ?? 'ssi') as Source)}
          options={offered.map((s) => ({ id: s, label: t(`siteImport.sourceName.${s}`) }))}
        />
      )}
      <Muted>{t('siteImport.purge.what')}</Muted>
      {purge.error && <Notice tone="danger">{errorText(purge.error)}</Notice>}
      <div className="form-actions">
        <ConfirmButton
          variant="danger"
          icon="delete"
          title={t('siteImport.purge.confirmTitle', { source: name })}
          body={t('siteImport.purge.confirmBody', { source: name })}
          confirmLabel={t('siteImport.purge.confirm')}
          onConfirm={async () => { if (source) await purge.mutateAsync(source); }}
          onDone={() => announce(t('siteImport.purge.started'))}
          isPending={purge.isPending}
          isDisabled={!source}
        >
          {t('siteImport.purge.start')}
        </ConfirmButton>
      </div>
    </Panel>
  );
}

function LatestImports() {
  const { t } = useTranslation();
  const errorText = useErrorText();
  const imports = useQuery(siteImportsQuery());
  // Says when a run finishes, for screen readers (the badge changes silently).
  const statuses = useRef(new Map<string, SiteImportView['status']>());
  useEffect(() => {
    for (const i of imports.data ?? []) {
      const before = statuses.current.get(i.id);
      if (before && before !== i.status && !running(i)) announce(i.status === 'done' ? t('siteImport.finished') : t('siteImport.failedNow'));
      statuses.current.set(i.id, i.status);
    }
  }, [imports.data, t]);

  return (
    <Panel title={t('siteImport.latest')}>
      {imports.error && <Notice tone="danger">{errorText(imports.error)}</Notice>}
      {imports.data?.length === 0 && <Muted>{t('siteImport.none')}</Muted>}
      {imports.data && imports.data.length > 0 && (
        <ul className="site-imports">{imports.data.map((i) => <ImportItem key={i.id} item={i} />)}</ul>
      )}
    </Panel>
  );
}

const STATUS_TONE = { queued: 'neutral', running: 'neutral', done: 'success', failed: 'danger' } as const;
const COUNTED = ['created', 'updated', 'unchanged', 'kept', 'linked', 'offered', 'skippedNoName', 'skippedDeleted', 'skippedNew', 'gone'] as const;

function ImportItem({ item: i }: { item: SiteImportView }) {
  const { t } = useTranslation();
  const display = useDisplay();
  const area = i.area.kind === 'country' ? display.country(i.area.country)
    : i.area.kind === 'box' ? t('siteImport.areaBox', {
      southWest: display.position({ latitude: i.area.south, longitude: i.area.west }),
      northEast: display.position({ latitude: i.area.north, longitude: i.area.east }),
    })
    : t('siteImport.areaWorld');
  const sources = i.sources.map((s) => t(`siteImport.sourceName.${s}`)).join(', ');
  const progress = i.status !== 'running' ? null
    : i.progress.step === 'saving' ? t('siteImport.saving', { done: i.progress.done, total: i.progress.total })
    : i.progress.step === 'waiting' ? null : t('siteImport.fetching', { source: t(`siteImport.sourceName.${i.progress.step}`) });
  const counts = i.counts && COUNTED.filter((k) => i.counts![k] > 0).map((k) => t(`siteImport.count.${k}`, { count: i.counts![k] }));
  const near = i.findings.flatMap((f) => (f.kind === 'near' ? [f] : []));
  const offers = i.findings.flatMap((f) => (f.kind === 'offer' ? [f] : []));

  return (
    <li>
      <div className="site-import-head">
        <strong>{sources} · {area}</strong>
        <Badge tone={STATUS_TONE[i.status]}>{t(`siteImport.status.${i.status}`)}</Badge>
      </div>
      <p className="meta">{display.dateTime(i.createdAt)}{!i.createSites && ` · ${t('siteImport.onlyFilled')}`}</p>
      {progress && <p>{progress}</p>}
      {counts && <p>{counts.length > 0 ? counts.join(', ') : t('siteImport.nothingFound')}</p>}
      {i.failureCode && <p className="import-error">{t(`errors.${i.failureCode}`)}</p>}
      {near.length > 0 && (
        <>
          <p>{t('siteImport.nearIntro', { count: near.length })}</p>
          <ul className="site-import-findings">
            {near.slice(0, FINDINGS_SHOWN).map((f) => (
              <li key={f.siteId}>
                <a href={`#/sites/${f.siteId}`}>{f.name}</a>
                {' '}{t('siteImport.nearOf', { distance: display.distance(f.distanceM) })}{' '}
                <a href={`#/sites/${f.nearSiteId}`}>{f.nearName}</a>
              </li>
            ))}
          </ul>
          {near.length > FINDINGS_SHOWN && <p className="meta">{t('siteImport.moreFindings', { count: near.length - FINDINGS_SHOWN })}</p>}
        </>
      )}
      {offers.length > 0 && (
        <>
          <p>{t('siteImport.offerIntro', { count: offers.length })}</p>
          <ul className="site-import-findings">
            {offers.slice(0, FINDINGS_SHOWN).map((f) => (
              <li key={`${f.siteId}-${f.source}`}>
                <a href={`#/sites/${f.siteId}`}>{f.name}</a> ({t(`siteImport.sourceName.${f.source}`)})
              </li>
            ))}
          </ul>
          {offers.length > FINDINGS_SHOWN && <p className="meta">{t('siteImport.moreFindings', { count: offers.length - FINDINGS_SHOWN })}</p>}
        </>
      )}
    </li>
  );
}

function LatestPurges() {
  const { t } = useTranslation();
  const errorText = useErrorText();
  const purges = useQuery(sitePurgesQuery());
  return (
    <Panel title={t('siteImport.purge.latest')}>
      {purges.error && <Notice tone="danger">{errorText(purges.error)}</Notice>}
      {purges.data?.length === 0 && <Muted>{t('siteImport.purge.none')}</Muted>}
      {purges.data && purges.data.length > 0 && (
        <ul className="site-imports">{purges.data.map((p) => <PurgeItem key={p.id} item={p} />)}</ul>
      )}
    </Panel>
  );
}

function PurgeItem({ item: p }: { item: SitePurgeView }) {
  const { t } = useTranslation();
  const display = useDisplay();
  const name = t(`siteImport.sourceName.${p.source}`);
  const progress = p.status !== 'running' ? null
    : p.progress.step === 'ids' ? t('siteImport.purge.progressIds', { source: name })
    : p.progress.step === 'sites' ? t('siteImport.purge.progressSites', { done: p.progress.done, total: p.progress.total })
    : null;
  const reported = p.result?.referencedSites ?? [];

  return (
    <li>
      <div className="site-import-head">
        <strong>{t('siteImport.purge.heading', { source: name })}</strong>
        <Badge tone={STATUS_TONE[p.status]}>{t(`siteImport.status.${p.status}`)}</Badge>
      </div>
      <p className="meta">{display.dateTime(p.createdAt)}</p>
      {progress && <p>{progress}</p>}
      {p.result && <p>{t('siteImport.purge.result', { ids: p.result.sourceIdsDeleted, sites: p.result.sitesDeleted })}</p>}
      {p.failureCode && <p className="import-error">{t(`errors.${p.failureCode}`)}</p>}
      {reported.length > 0 && (
        <>
          <p>{t('siteImport.purge.reportedIntro', { count: reported.length })}</p>
          <ul className="site-import-findings">
            {reported.slice(0, FINDINGS_SHOWN).map((s) => (
              <li key={s.id}>
                <a href={`#/sites/${s.id}`}>{s.name}</a>
                {' '}{t('siteImport.purge.reportedDives', { count: s.dives })}
              </li>
            ))}
          </ul>
          {reported.length > FINDINGS_SHOWN && <p className="meta">{t('siteImport.moreFindings', { count: reported.length - FINDINGS_SHOWN })}</p>}
        </>
      )}
    </li>
  );
}
