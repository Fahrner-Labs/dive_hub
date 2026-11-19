// The Waypoint mark: the diver-down flag with the stripe knocked out and the hub bead on it.
import { render } from '@testing-library/react';
import { describe, expect, it } from 'vitest';
import { BrandMark } from '../src/ui/Layout.tsx';

describe('BrandMark', () => {
  it('draws the square mark at the asked size and takes the brand fill', () => {
    const { container } = render(<BrandMark size={32} />);
    const svg = container.querySelector('svg')!;
    expect(svg.getAttribute('viewBox')).toBe('0 0 256 256');
    expect(svg.getAttribute('width')).toBe('32');
    expect(svg.getAttribute('height')).toBe('32');
    expect(svg.getAttribute('fill')).toBe('var(--color-brand-flag)');
    expect(svg.getAttribute('aria-hidden')).toBe('true');
    expect(svg.getAttribute('focusable')).toBe('false');
  });

  it('knocks the stripe out of the flag and threads the bead on it', () => {
    const { container } = render(<BrandMark />);
    const path = container.querySelector('path')!;
    expect(path.getAttribute('fill-rule')).toBe('evenodd');
    expect(path.getAttribute('d')).toContain('M 32 56');
    expect(container.querySelector('circle')!.getAttribute('r')).toBe('38');
  });
});
