#!/usr/bin/env node
// The Android lane of `pnpm check` (ADR 0073): the committed API models must match the OpenAPI document, then the
// shared JVM tests and the debug APK build. CI installs a JDK and the Android SDK; a laptop needs both too.
import { spawnSync } from 'node:child_process';
import { existsSync } from 'node:fs';
import { dirname, join, resolve } from 'node:path';
import { fileURLToPath } from 'node:url';

const ROOT = resolve(dirname(fileURLToPath(import.meta.url)), '..');
const APP = join(ROOT, 'apps/android');

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
const java = process.env.JAVA_HOME ? join(process.env.JAVA_HOME, 'bin', 'java') : 'java';
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
const build = spawnSync(gradlew, ['--no-daemon', '--console=plain', ':shared:jvmTest', ':app:assembleDebug'], {
  cwd: APP,
  stdio: 'inherit',
  env: { ...process.env, ANDROID_HOME: sdk, ANDROID_SDK_ROOT: sdk },
});
if (build.error) {
  console.error(`android: could not start Gradle: ${build.error.message}`);
  process.exit(1);
}
process.exit(build.status ?? 1);
