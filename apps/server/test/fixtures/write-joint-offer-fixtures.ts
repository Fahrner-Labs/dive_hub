// Writes the two dives of the fill-offer browser test (apps/web/e2e/joint-dive-offers.spec.ts): a source Imported by
// one User and a Dive overlapping it by 20 s Imported by another, on computers no other spec uses. Both are in a
// Joint dive after the suggestion is accepted by linking (ADR 0070), so a shared fact the source has and the target
// lacks can be offered.
// Run: pnpm --filter @dive-hub/server exec tsx test/fixtures/write-joint-offer-fixtures.ts
import { writeFileSync } from 'node:fs';
import { fileURLToPath } from 'node:url';
import { makeSyntheticDive } from './synthetic-dive.js';

const out = (name: string) => fileURLToPath(new URL(`../../../web/e2e/fixtures/${name}`, import.meta.url));

writeFileSync(out('joint-source.fit'), makeSyntheticDive({
  serialNumber: 9301,
  start: new Date('2026-06-10T09:00:00Z'),
  durationSeconds: 40 * 60,
  maxDepthM: 24.5,
  diveNumber: 88,
}));
writeFileSync(out('joint-target.fit'), makeSyntheticDive({
  serialNumber: 9302,
  start: new Date('2026-06-10T09:00:20Z'),
  durationSeconds: 40 * 60,
  maxDepthM: 24,
  diveNumber: 88,
}));
// The decline test needs a Joint dive with no other member but the copy made from the source: its own Dive and time.
writeFileSync(out('joint-decline-source.fit'), makeSyntheticDive({
  serialNumber: 9303,
  start: new Date('2026-06-11T09:00:00Z'),
  durationSeconds: 40 * 60,
  maxDepthM: 21.5,
  diveNumber: 89,
}));
console.log('wrote joint-source.fit, joint-target.fit and joint-decline-source.fit');
