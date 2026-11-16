// The Review page: its part is in the address, and a decision's comparison decides per row whether the candidates agree.
import { describe, expect, it } from 'vitest';
import { reviewHref, reviewTab, valuesAgree } from '../src/lib/review.ts';

describe('review address', () => {
  it('leaves the first part out', () => {
    expect(reviewHref('decide')).toBe('#/review');
    expect(reviewHref('imports')).toBe('#/review?tab=imports');
    expect(reviewHref('deleted')).toBe('#/review?tab=deleted');
  });

  it('reads the part back and falls back to the first one', () => {
    expect(reviewTab(new URLSearchParams('tab=decided'))).toBe('decided');
    expect(reviewTab(new URLSearchParams('tab=nonsense'))).toBe('decide');
    expect(reviewTab(new URLSearchParams(''))).toBe('decide');
  });
});

describe('one comparison row', () => {
  it('agrees when every candidate has the same formatted text', () => {
    expect(valuesAgree(['16.4 m', '16.4 m'])).toBe(true);
    expect(valuesAgree(['45 min', '45 min', '45 min'])).toBe(true);
    expect(valuesAgree([undefined, undefined])).toBe(true);
  });

  it('differs when the text is not equal, missing vs present included', () => {
    expect(valuesAgree(['16.4 m', '16.1 m'])).toBe(false);
    expect(valuesAgree(['45 min', undefined])).toBe(false);
    expect(valuesAgree([undefined, '45 min', '45 min'])).toBe(false);
  });
});
