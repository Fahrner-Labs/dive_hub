// Writes apps/web/e2e/fixtures/buddy-computer.fit: a synthetic dive from a computer no other spec uses, at the
// seeded Dive's start (2026-01-15T09:00Z) so a second User's Dive overlaps it. The buddy-suggestion browser tests
// import it as that User before accepting by linking instead of creating (ADR 0070).
// Run: pnpm --filter @dive-hub/server exec tsx test/fixtures/write-buddy-fixture.ts
import { writeFileSync } from 'node:fs';
import { fileURLToPath } from 'node:url';
import { makeSyntheticDive } from './synthetic-dive.js';

const out = fileURLToPath(new URL('../../../web/e2e/fixtures/buddy-computer.fit', import.meta.url));
writeFileSync(out, makeSyntheticDive({
  serialNumber: 9101,
  start: new Date('2026-01-15T09:00:00Z'),
  durationSeconds: 35 * 60,
  maxDepthM: 22.5,
  diveNumber: 7,
}));
console.log(`wrote ${out}`);
