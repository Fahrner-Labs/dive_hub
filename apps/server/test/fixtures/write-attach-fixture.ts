// Writes apps/web/e2e/fixtures/attach-computer.fit: a computer no other spec uses, at the seeded Dive's start and with
// a much greater depth, so importing it waits as a Duplicate candidate (a Recording that does not clearly belong to a
// Dive). Run: pnpm --filter @dive-hub/server exec tsx test/fixtures/write-attach-fixture.ts
import { writeFileSync } from 'node:fs';
import { fileURLToPath } from 'node:url';
import { makeSyntheticDive } from './synthetic-dive.js';

const out = fileURLToPath(new URL('../../../web/e2e/fixtures/attach-computer.fit', import.meta.url));
writeFileSync(out, makeSyntheticDive({
  serialNumber: 8803, start: new Date('2026-01-15T09:00:00Z'), durationSeconds: 30 * 60, maxDepthM: 32,
}));
console.log(`wrote ${out}`);
