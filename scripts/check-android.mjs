#!/usr/bin/env node
// The Android lane of `pnpm check` (ADR 0073): the committed API models must match the OpenAPI document, then the
// shared JVM tests, the debug APK build and the Compose screenshot tests. CI installs a JDK and the Android SDK; a
// laptop needs both too. `--review` runs no build: it collects the committed Roborazzi goldens for the design
// critique gate (FAH-359, docs/development.md).
import { spawnSync } from 'node:child_process';
import { copyFileSync, existsSync, mkdirSync, readdirSync } from 'node:fs';
import { dirname, join, resolve } from 'node:path';
import { fileURLToPath } from 'node:url';

const ROOT = resolve(dirname(fileURLToPath(import.meta.url)), '..');
const APP = join(ROOT, 'apps/android');
const GOLDENS = 'apps/android/app/src/test/screenshots';
const REVIEW_OUTPUT = 'apps/android/review-output';

/**
 * The screenshots for the design critique gate: the committed goldens, which a green `verifyRoborazziDebug` proves
 * equal to the current UI — so the critique never re-records them. Copies the PNGs whose name contains `filter`
 * (all without one) into `review-output/`. Returns `{ outDir, copied }`, repo-relative.
 */
export function reviewGoldens({ root = ROOT, filter } = {}) {
  const dir = join(root, GOLDENS);
  const names = existsSync(dir) ? readdirSync(dir).filter((name) => name.endsWith('.png') && (!filter || name.includes(filter))).sort() : [];
  if (names.length > 0) mkdirSync(join(root, REVIEW_OUTPUT), { recursive: true });
  for (const name of names) copyFileSync(join(dir, name), join(root, REVIEW_OUTPUT, name));
  return { outDir: REVIEW_OUTPUT, copied: names.map((name) => `${REVIEW_OUTPUT}/${name}`) };
}

function review(args) {
  const filter = args[0] === '--filter' ? args[1] : undefined;
  if (args[0] === '--filter' && filter === undefined) {
    console.error('android: --filter needs a part of the file name');
    process.exit(1);
  }
  if (args[0] !== undefined && args[0] !== '--filter') {
    console.error(`android: unknown --review argument ${args[0]} (the only one is --filter <part of the file name>)`);
    process.exit(1);
  }
  const { outDir, copied } = reviewGoldens({ filter });
  if (copied.length === 0) {
    console.log(`android: no goldens${filter ? ` matching "${filter}"` : ''} in ${GOLDENS}`);
    return;
  }
  console.log([`android: ${copied.length} screenshot(s) for the design critique in ${outDir}:`, ...copied.map((path) => `  ${path}`), 'Attach them to the Design Consultant review (docs/development.md: Android UI review).'].join('\n'));
}

function lane() {
  const generated = spawnSync(process.execPath, ['scripts/generate-android-api.mjs', '--check'], {
    cwd: ROOT,
    stdio: 'inherit',
  });
  if (generated.status !== 0) process.exit(generated.status ?? 1);

  const sdk = process.env.ANDROID_HOME || process.env.ANDROID_SDK_ROOT;
  if (!sdk || !existsSync(sdk)) {
    console.error('android: set ANDROID_HOME (or ANDROID_SDK_ROOT) to an Android SDK with platform 37 and build-tools 37.0.0');
    process.exit(1);
  }
  const javaBinary = process.platform === 'win32' ? 'java.exe' : 'java';
  const java = process.env.JAVA_HOME ? join(process.env.JAVA_HOME, 'bin', javaBinary) : javaBinary;
  if (!existsSync(java) && process.env.JAVA_HOME) {
    console.error(`android: JAVA_HOME points at no JDK (${process.env.JAVA_HOME})`);
    process.exit(1);
  }
  const javaVersion = spawnSync(java, ['-version'], { stdio: 'ignore' });
  if (javaVersion.error) {
    console.error('android: no java on PATH and no JAVA_HOME; install a JDK 17 or newer');
    process.exit(1);
  }

  const gradlew = process.platform === 'win32' ? 'gradlew.bat' : './gradlew';
  const build = spawnSync(
    gradlew,
    ['--no-daemon', '--console=plain', ':shared:jvmTest', ':app:assembleDebug', ':app:verifyRoborazziDebug'],
    {
      cwd: APP,
      stdio: 'inherit',
      env: { ...process.env, ANDROID_HOME: sdk, ANDROID_SDK_ROOT: sdk },
      shell: process.platform === 'win32',
    },
  );
  if (build.error) {
    console.error(`android: could not start Gradle: ${build.error.message}`);
    process.exit(1);
  }
  process.exit(build.status ?? 1);
}

function main(args) {
  if (args[0] === '--review') return review(args.slice(1));
  if (args.length > 0) {
    console.error(`android: unknown argument ${args[0]} (the only one is --review)`);
    process.exit(1);
  }
  lane();
}

if (process.argv[1] && resolve(process.argv[1]) === fileURLToPath(import.meta.url)) main(process.argv.slice(2));
