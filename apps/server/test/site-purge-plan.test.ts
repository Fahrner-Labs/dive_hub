// Classifying a Source purge (FAH-528): after one Source's External IDs are gone, which import-created sites may
// be deleted, which a Dive still names (reported, never deleted), and which stay for another Source or a User.
import { describe, expect, it } from 'vitest';
import { classifyPurge, type PurgeSite } from '../src/sites/import/site-purge-plan.js';

const site = (id: string, overrides: Partial<PurgeSite> = {}): PurgeSite => ({
  id,
  name: id,
  createdBy: null,
  mergedInto: null,
  dives: 0,
  externalIds: [],
  ...overrides,
});

const ssi = (providesData = true) => ({ source: 'ssi' as const, providesData });
const osm = (providesData = true) => ({ source: 'osm' as const, providesData });
const wikidata = (providesData = true) => ({ source: 'wikidata' as const, providesData });

describe('classifying a Source purge', () => {
  it('deletes an import-created site the Source left with no data and no Dive', () => {
    const result = classifyPurge('ssi', [site('gone', { externalIds: [] })]);
    expect(result).toEqual({ deleteIds: ['gone'], referenced: [] });
  });

  it('reports an import-created site a Dive still names, with how many Dives', () => {
    const result = classifyPurge('ssi', [
      site('used', { name: 'House Reef', dives: 2 }),
      site('unused'),
    ]);
    expect(result.deleteIds).toEqual(['unused']);
    expect(result.referenced).toEqual([{ id: 'used', name: 'House Reef', dives: 2 }]);
  });

  it('leaves a site another Source still provides data for, and reports it not either', () => {
    const result = classifyPurge('ssi', [
      site('both', { externalIds: [osm(true)], dives: 0 }),
      site('only-a-reference', { externalIds: [osm(false)] }),
    ]);
    expect(result.deleteIds).toEqual(['only-a-reference']);
    expect(result.referenced).toEqual([]);
  });

  it('never deletes a site a User made, even when no Dive names it and no data is left', () => {
    const result = classifyPurge('ssi', [
      site('mine', { createdBy: 'user-1' }),
      site('mine-used', { createdBy: 'user-1', dives: 3 }),
    ]);
    expect(result.deleteIds).toEqual([]);
    expect(result.referenced).toEqual([]);
  });

  it('deletes a merge tombstone before its target, so the pointer is gone when the target goes', () => {
    const result = classifyPurge('ssi', [
      site('target'),
      site('tombstone', { mergedInto: 'target' }),
    ]);
    expect(result.deleteIds).toEqual(['tombstone', 'target']);
  });

  it('leaves a merge target while a tombstone outside the purge still points at it', () => {
    const result = classifyPurge('ssi', [
      site('target'),
      site('tombstone', { createdBy: 'user-1', mergedInto: 'target' }),
    ]);
    expect(result.deleteIds).toEqual([]);
  });

  it('mixes all three outcomes in one run', () => {
    const result = classifyPurge('ssi', [
      site('unreferenced', { externalIds: [wikidata(false)] }),
      site('referenced', { name: 'Blue Hole', dives: 1, externalIds: [ssi(false)] }),
      site('from-wikidata', { externalIds: [wikidata(true)] }),
      site('by-a-user', { createdBy: 'user-1' }),
    ]);
    expect(result.deleteIds).toEqual(['unreferenced']);
    expect(result.referenced).toEqual([{ id: 'referenced', name: 'Blue Hole', dives: 1 }]);
  });
});
