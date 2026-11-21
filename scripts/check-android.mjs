#!/usr/bin/env node
// The Android lane of `pnpm check` (ADR 0073): the committed API models must match the OpenAPI document, then the
// shared JVM tests, the debug APK build and the Compose screenshot tests. CI installs a JDK and the Android SDK; a
// laptop needs both too. `--review` runs no build: it collects the committed Roborazzi goldens for the design
// critique gate (FAH-359, docs/development.md).
import { spawnSync } from 'node:child_process';
import { copyFileSync, existsSync, mkdirSync, readdirSync, rmSync } from 'node:fs';
import { dirname, join, resolve } from 'node:path';
import { fileURLToPath } from 'node:url';

const ROOT = resolve(dirname(fileURLToPath(import.meta.url)), '..');
const APP = join(ROOT, 'apps/android');
const GOLDENS = 'apps/android/app/src/test/screenshots';
const REVIEW_OUTPUT = 'apps/android/review-output';

/**
 * The screenshots for the design critique gate: the committed goldens, which a green `verifyRoborazziDebug` proves
 * equal to the current UI for the states the screenshot tests capture — so the critique never re-records them.
 * Copies the PNGs whose name contains `filter` (all without one) into `review-output/`, which is cleared first: a
 * stale screenshot must never be critiqued as current. Returns `{ outDir, copied }`, repo-relative.
 */
export function reviewGoldens({ root = ROOT, filter } = {}) {
  const dir = join(root, GOLDENS);
  const names = existsSync(dir) ? readdirSync(dir).filter((name) => name.endsWith('.png') && (!filter || name.includes(filter))).sort() : [];
  rmSync(join(root, REVIEW_OUTPUT), { recursive: true, force: true });
  if (names.length > 0) mkdirSync(join(root, REVIEW_OUTPUT), { recursive: true });
  for (const name of names) copyFileSync(join(dir, name), join(root, REVIEW_OUTPUT, name));
  return { outDir: REVIEW_OUTPUT, copied: names.map((name) => `${REVIEW_OUTPUT}/${name}`) };
}

/**
 * The `--review` arguments: nothing, or `--filter <part of the file name>`. Anything else — an unknown argument, a
 * missing, empty or `--`-prefixed filter value, a leftover — is an error, so a typo never silently reviews the wrong set.
 */
export function parseReviewArgs(args) {
  const [first, ...rest] = args;
  if (first === undefined) return { filter: undefined };
  if (first !== '--filter') return { error: `unknown --review argument ${first} (the only one is --filter <part of the file name>)` };
  const [value, ...leftover] = rest;
  if (value === undefined || value.startsWith('--') || value.trim() === '') return { error: `--filter needs a part of the file name${value ? `, not ${value}` : ''}` };
  if (leftover.length > 0) return { error: `unknown --review argument ${leftover[0]} (the only one is --filter <part of the file name>)` };
  return { filter: value };
}

function review(args) {
  const parsed = parseReviewArgs(args);
  if (parsed.error) {
    console.error(`android: ${parsed.error}`);
    process.exit(1);
  }
  const { outDir, copied } = reviewGoldens({ filter: parsed.filter });
  if (copied.length === 0) {
    // A filter that matches nothing is a typo; no goldens at all is a broken checkout (the docs order --review
    // after a green Android lane). Neither may hand an empty bundle to the critique.
    console.error(`android: no goldens${parsed.filter ? ` matching "${parsed.filter}"` : ''} in ${GOLDENS}`);
    process.exit(1);
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
