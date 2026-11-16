import { Fragment, type ReactNode } from 'react';
import { useTranslation } from 'react-i18next';
import { valuesAgree } from './lib/review.ts';

/** One thing a row says about a Dive or a Recording; `text` is what the User reads, and what is compared. */
export interface Fact {
  key: string;
  text: string | undefined;
}

/** The facts of a row on one line; those in `marked` differ from the other candidate (UI redesign 4.5). */
export function Facts({ facts, marked }: { facts: Fact[]; marked: Set<string> }) {
  const shown = facts.filter((f) => f.text);
  return (
    <p className="candidate-facts">
      {shown.map((f, i) => (
        <Fragment key={f.key}>
          {i > 0 && ' · '}
          {marked.has(f.key) ? <mark className="diff">{f.text}</mark> : f.text}
        </Fragment>
      ))}
    </p>
  );
}

/** A candidate of a decision as a row of its own: what it is, a badge for what to know about it, then its facts. */
export function CandidateRow({ title, badge, facts, marked }: { title: ReactNode; badge?: ReactNode; facts: Fact[]; marked: Set<string> }) {
  return (
    <li className="candidate">
      <div className="candidate-head">{title}{badge}</div>
      <Facts facts={facts} marked={marked} />
    </li>
  );
}

/** The candidates of one decision side by side; stacked on a phone. */
export function Candidates({ children }: { children: ReactNode }) {
  return <ul className="candidates">{children}</ul>;
}

/** One candidate column of a comparison: its title, a badge for what to know about it, and an optional note under it. */
export interface CompareSubject {
  key?: string;
  /** The column's plain name, for the stacked table on a phone where the column header is out of sight. */
  label: string;
  title: ReactNode;
  badge?: ReactNode;
  note?: ReactNode;
}

/** One fact row of a comparison: its label and one formatted value per candidate, in the columns' order. */
export interface CompareFact {
  key: string;
  label: ReactNode;
  values: (string | undefined)[];
}

/**
 * The candidates of a decision compared fact by fact, as a table (UI redesign 4.5): one column per candidate and one
 * labeled row per fact. Rows every candidate agrees on collapse to one muted value; differing rows are highlighted
 * whole, and a row appears only when at least one candidate has a value. Its wrapper is a named group, not a region:
 * two cards over one question would otherwise be two landmarks with the same name (FAH-231).
 */
export function Comparison({ label, subjects, facts }: { label: string; subjects: CompareSubject[]; facts: CompareFact[] }) {
  const { t } = useTranslation();
  const multi = subjects.length > 1;
  const shown = facts.filter((fact) => fact.values.some((value) => value));
  return (
    <div className="compare-wrap" role="group" aria-label={label} tabIndex={0}>
      {/* Explicit roles keep the table a table when its own width (not the window's) lays the rows out as blocks. */}
      <table className={multi ? 'compare compare-multi' : 'compare'} role="table">
        <caption className="visually-hidden">{label}</caption>
        <thead role="rowgroup">
          <tr role="row">
            <td className="compare-corner" role="cell" />
            {subjects.map((subject, i) => (
              <th key={subject.key ?? i} scope="col" role="columnheader">
                <span className="compare-head">
                  <span className="compare-title">{subject.title}</span>
                  {subject.badge}
                </span>
                {subject.note && <span className="compare-note">{subject.note}</span>}
              </th>
            ))}
          </tr>
        </thead>
        <tbody role="rowgroup">
          {shown.map((fact) => {
            const same = valuesAgree(fact.values);
            return (
              <tr key={fact.key} role="row" className={same ? undefined : 'compare-differs'}>
                <th scope="row" role="rowheader">{fact.label}</th>
                {same && multi ? (
                  <td className="compare-same" role="cell" colSpan={subjects.length}>
                    {t(subjects.length === 2 ? 'comparison.both' : 'comparison.all', { value: fact.values[0] })}
                  </td>
                ) : (
                  fact.values.map((value, i) => (
                    <td key={i} role="cell" data-subject={subjects[i]?.label} className={value ? undefined : 'compare-missing'}>{value ?? t('common.none')}</td>
                  ))
                )}
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
}
