// A decision card's comparison (FAH-225): one column per candidate, and each value cell says which candidate it
// belongs to so a phone can stack the table without losing the column header. Its header row keeps a candidate's
// name and badge together, and the table and its scrolling wrapper are named (FAH-227). The wrapper is a group, not a
// region: two cards over one question would otherwise be two landmarks with the same name (FAH-231).
import { render, screen } from '@testing-library/react';
import axe from 'axe-core';
import { describe, expect, it } from 'vitest';
import { Comparison, type CompareFact, type CompareSubject } from '../src/ReviewRows.tsx';

const label = 'Where does this recording belong?';

const subjects: CompareSubject[] = [
  { key: 'recording', label: 'Recording', title: 'Recording', badge: <span>the max depth differs</span>, note: 'unknown device' },
  { key: 'dive', label: 'Dive 42', title: <a href="#/dives/42">Dive 42</a> },
];
const facts: CompareFact[] = [{ key: 'depth', label: 'Max depth', values: ['18 m', '20 m'] }];

describe('the comparison table', () => {
  it('marks a multi-candidate comparison and names every value cell for its column', () => {
    render(<Comparison label={label} subjects={subjects} facts={facts} />);
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
    render(<Comparison label={label} subjects={[subjects[0]!]} facts={facts} />);
    const table = screen.getByRole('table');
    expect(table.className).not.toContain('compare-multi');
    expect(screen.getByText('18 m').closest('td')!.dataset.subject).toBe('Recording');
  });

  it('keeps the table roles when CSS lays the rows out as blocks', () => {
    render(<Comparison label={label} subjects={subjects} facts={facts} />);
    expect(screen.getByRole('table')).toBeTruthy();
    expect(screen.getAllByRole('rowgroup')).toHaveLength(2);
    expect(screen.getAllByRole('row').length).toBeGreaterThanOrEqual(2);
  });

  it('puts a candidate name and badge in one flexible header row, the note beneath', () => {
    render(<Comparison label={label} subjects={subjects} facts={facts} />);
    const header = screen.getByRole('columnheader', { name: /^Recording/ });
    const head = header.querySelector('.compare-head');
    expect(head).not.toBeNull();
    expect(head!.querySelector('.compare-title')?.textContent).toBe('Recording');
    expect(head!.contains(screen.getByText('the max depth differs'))).toBe(true);
    const note = header.querySelector('.compare-note');
    expect(note?.textContent).toBe('unknown device');
    expect(head!.contains(note)).toBe(false);
  });

  it('names the table and its scrollable group', () => {
    const { container } = render(<Comparison label={label} subjects={subjects} facts={facts} />);
    const caption = container.querySelector('caption');
    expect(caption?.textContent).toBe(label);
    expect(caption?.className).toBe('visually-hidden');
    expect(screen.getByRole('table', { name: label })).toBeTruthy();
    const group = screen.getByRole('group', { name: label });
    expect(group).toBe(container.querySelector('.compare-wrap'));
    expect(group.tabIndex).toBe(0);
  });

  it('keeps two cards over one question from claiming the same landmark name', async () => {
    const { container } = render(
      <>
        <Comparison label={label} subjects={subjects} facts={facts} />
        <Comparison label={label} subjects={subjects} facts={facts} />
      </>,
    );
    const { violations } = await axe.run(container, { runOnly: { type: 'rule', values: ['landmark-unique'] } });
    expect(violations).toEqual([]);
    expect(screen.queryAllByRole('region', { name: label })).toHaveLength(0);
    expect(screen.getAllByRole('group', { name: label })).toHaveLength(2);
  });
});
