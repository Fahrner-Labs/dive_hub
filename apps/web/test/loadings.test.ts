import { describe, expect, it } from 'vitest';
import de from '../src/i18n/locales/de.json' with { type: 'json' };
import en from '../src/i18n/locales/en.json' with { type: 'json' };
import { loadingNumber, loadingPercent, LOADING_SOURCES, showsLoadings } from '../src/lib/loadings.ts';

const none = { n2: null, cns: null, otu: null, noFly: null };

describe('when the current loadings show', () => {
  it('stays hidden when every estimate is missing', () => {
    expect(showsLoadings(none)).toBe(false);
  });

  it('shows for each estimate on its own', () => {
    expect(showsLoadings({ ...none, n2: { worstCompartmentPercent: 42.3, gfHigh: 85 } })).toBe(true);
    expect(showsLoadings({ ...none, cns: { percentNow: 12.5, percentAtLastDiveEnd: 25, halfLifeMinutes: 90 } })).toBe(true);
    expect(showsLoadings({ ...none, otu: { last24Hours: 34.2 } })).toBe(true);
    expect(showsLoadings({
      ...none,
      noFly: {
        hours: 18, reason: 'several', until: '2026-11-17T02:00:00Z',
        source: { title: 'DAN 2002: guidelines for flying after diving', url: 'https://dan.org/health-medicine/' },
      },
    })).toBe(true);
  });

  it('takes a returned zero for a value, never for a missing estimate', () => {
    expect(showsLoadings({
      n2: { worstCompartmentPercent: 0, gfHigh: 85 },
      cns: { percentNow: 0, percentAtLastDiveEnd: 0, halfLifeMinutes: 90 },
      otu: { last24Hours: 0 },
      noFly: null,
    })).toBe(true);
  });
});

describe('the numbers the section shows', () => {
  it('formats a percent in the User’s language, with a real zero kept', () => {
    expect(loadingPercent(42.34, 'en')).toBe('42.3%');
    expect(loadingPercent(0, 'en')).toBe('0%');
    expect(loadingPercent(42.34, 'de').replace(/\u00a0|\u202f/g, ' ')).toBe('42,3 %');
  });

  it('formats an OTU count in the User’s language', () => {
    expect(loadingNumber(34.25, 'en')).toBe('34.3');
    expect(loadingNumber(0, 'en')).toBe('0');
    expect(loadingNumber(34.25, 'de')).toBe('34,3');
  });
});

describe('the sources the section links', () => {
  it('names each source, and every link is a web address', () => {
    for (const source of Object.values(LOADING_SOURCES)) {
      expect(source.title).toBeTruthy();
      expect(source.url).toMatch(/^https:\/\//);
    }
  });
});

describe('the texts the section needs', () => {
  const keys = [
    'title', 'n2Label', 'n2Value', 'cnsLabel', 'cnsValue', 'cnsNote', 'otuLabel', 'otuValue',
    'noFlyLabel', 'noFly.single', 'noFly.several', 'noFly.decompression',
    'basis_one', 'basis_other', 'basisNone', 'basisNoDive', 'skipped_one', 'skipped_other', 'note', 'sources',
  ];

  it.each([['en', en], ['de', de]])('has all of them in %s', (_name, locale) => {
    const texts = locale.loadings as unknown as Record<string, unknown>;
    for (const key of keys) {
      const value = key.split('.').reduce<unknown>((node, part) => (node && typeof node === 'object' ? (node as Record<string, unknown>)[part] : undefined), texts);
      expect(value, key).toBeTypeOf('string');
      expect((value as string).length, key).toBeGreaterThan(0);
    }
  });
});
