// Writes apps/web/e2e/fixtures/live-computer*.fit: synthetic dives from computers no other spec uses. The browser tests
// upload one while a logbook is open (ADR 0066): a scheduled fetch or import brings a new Dive, and the hint must make
// it appear without a reload (FAH-38).
//
// The dives start far in the future so the arriving Dive is always the newest row of the logbook, even though the
// e2e servers share one database per worker and earlier specs leave newer fixture dives there (the surface-break pair,
// the Garmin dive). The arrival test's tint only starts once the row is at least half visible, so a row pushed below
// the fold would never tint (FAH-262). A separate file for a retry keeps that attempt real: a retry runs in the same
// worker and database, and re-importing the same file is skipped as "unchanged", so it could never tint again.
// Run: pnpm --filter @dive-hub/server exec tsx test/fixtures/write-live-fixtures.ts
import { writeFileSync } from 'node:fs';
import { fileURLToPath } from 'node:url';
import { makeSyntheticDive } from './synthetic-dive.js';

const write = (name: string, options: Parameters<typeof makeSyntheticDive>[0]) => {
  const out = fileURLToPath(new URL(`../../../web/e2e/fixtures/${name}`, import.meta.url));
  writeFileSync(out, makeSyntheticDive(options));
  console.log(`wrote ${out}`);
};

write('live-computer.fit', { serialNumber: 8801, start: new Date('2099-04-10T09:00:00Z'), diveNumber: 60 });
write('live-computer-2.fit', { serialNumber: 8802, start: new Date('2099-04-11T09:00:00Z'), diveNumber: 61 });
write('live-computer-3.fit', { serialNumber: 8804, start: new Date('2099-04-12T09:00:00Z'), diveNumber: 62 });
