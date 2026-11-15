// A decision card's comparison (FAH-225): one column per candidate, and each value cell says which candidate it
// belongs to so a phone can stack the table without losing the column header.
import { render, screen } from '@testing-library/react';
import { describe, expect, it } from 'vitest';
import { Comparison, type CompareFact, type CompareSubject } from '../src/ReviewRows.tsx';

const subjects: CompareSubject[] = [
  { key: 'recording', label: 'Recording', title: 'Recording', badge: <span>the max depth differs</span> },
  { key: 'dive', label: 'Dive 42', title: <a href="#/dives/42">Dive 42</a> },
];
const facts: CompareFact[] = [{ key: 'depth', label: 'Max depth', values: ['18 m', '20 m'] }];

describe('the comparison table', () => {
  it('marks a multi-candidate comparison and names every value cell for its column', () => {
    render(<Comparison subjects={subjects} facts={facts} />);
    const table = screen.getByRole('table');
    expect(table.className).toContain('compare-multi');
    expect(screen.getByRole('columnheader', { name: /^Recording/ })).toBeTruthy();
    expect(screen.getByRole('rowheader', { name: 'Max depth' })).toBeTruthy();
    const values = screen.getAllByRole('cell').filter((cell) => cell.dataset.subject);
    expect(values.map((cell) => [cell.dataset.subject, cell.textContent])).toEqual([
      ['Recording', '18 m'],
      ['Dive 42', '20 m'],
    ]);
  });

  it('keeps a single-candidate comparison plain: no multi marker', () => {
    render(<Comparison subjects={[subjects[0]!]} facts={facts} />);
    const table = screen.getByRole('table');
    expect(table.className).not.toContain('compare-multi');
    expect(screen.getByText('18 m').closest('td')!.dataset.subject).toBe('Recording');
  });

  it('keeps the table roles when CSS lays the rows out as blocks', () => {
    render(<Comparison subjects={subjects} facts={facts} />);
    expect(screen.getByRole('table')).toBeTruthy();
    expect(screen.getAllByRole('rowgroup')).toHaveLength(2);
    expect(screen.getAllByRole('row').length).toBeGreaterThanOrEqual(2);
  });
});
