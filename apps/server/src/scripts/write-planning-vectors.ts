// Writes the planning golden vectors (FAH-268) from the fixed case set in planning/test-vectors.ts.
// Regeneration is a deliberate, reviewed change: run it, review the diff, commit it. CI only reads the files.
import { writeFileSync } from 'node:fs';
import { fileURLToPath } from 'node:url';
import { VECTOR_KERNELS, vectorFile } from '../planning/test-vectors.js';

for (const kernel of VECTOR_KERNELS) {
  const out = fileURLToPath(new URL(`../../../../packages/test-vectors/planning/${kernel}.json`, import.meta.url));
  writeFileSync(out, `${JSON.stringify(vectorFile(kernel), null, 2)}\n`);
  console.log(`wrote ${out}`);
}
