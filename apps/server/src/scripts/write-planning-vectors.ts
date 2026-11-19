// Writes the planning golden vectors (FAH-268) from the fixed case set in planning/test-vectors.ts, and the lead
// estimate's (FAH-272) from lead/test-vectors.ts. Regeneration is a deliberate, reviewed change: run it, review
// the diff, commit it. CI only reads the files.
import { writeFileSync } from 'node:fs';
import { fileURLToPath } from 'node:url';
import { LEAD_VECTOR_KERNEL, leadVectorFile } from '../lead/test-vectors.js';
import { VECTOR_KERNELS, vectorFile } from '../planning/test-vectors.js';

const write = (kernel: string, file: ReturnType<typeof vectorFile>) => {
  const out = fileURLToPath(new URL(`../../../../packages/test-vectors/planning/${kernel}.json`, import.meta.url));
  writeFileSync(out, `${JSON.stringify(file, null, 2)}\n`);
  console.log(`wrote ${out}`);
};

for (const kernel of VECTOR_KERNELS) write(kernel, vectorFile(kernel));
write(LEAD_VECTOR_KERNEL, leadVectorFile());
