#! /usr/bin/env node
// Validates an Android release tag and derives the version the app reports (ADR 0077 D4/D5).
// Tags: `android-vX.Y.Z` or `android-vX.Y.Z-rcN`, pushed on main. `versionName` is the tag without `android-v`;
// `versionCode` is major*100000 + minor*1000 + patch*10 + (rc ? rcN : 9), so rc -> final rises monotonically.
import { resolve } from 'node:path';
import { fileURLToPath } from 'node:url';

const FORM = 'android-vX.Y.Z or android-vX.Y.Z-rcN';
const GRAMMAR = /^android-v([0-9]+)\.([0-9]+)\.([0-9]+)(?:-rc([1-9][0-9]*))?$/;

/** The tag's parts, or null when it is not an Android release tag. */
export function parseAndroidTag(tag) {
  const match = GRAMMAR.exec(tag);
  if (!match) return null;
  const [, major, minor, patch, candidate] = match;
  return {
    tag,
    versionName: tag.slice('android-v'.length),
    major: Number(major),
    minor: Number(minor),
    patch: Number(patch),
    rc: candidate === undefined ? null : Number(candidate),
  };
}

/** The versionCode ADR 0077 D5 derives from a parsed tag. */
export function androidVersionCode({ major, minor, patch, rc }) {
  // Each component has one decimal digit in the formula (minor three), and 9 is the final's sentinel.
  if (minor > 99 || patch > 9 || (rc !== null && rc > 8)) {
    throw new RangeError('versionCode components must keep minor <= 99, patch <= 9 and rc <= 8 (ADR 0077 D5)');
  }
  return major * 100000 + minor * 1000 + patch * 10 + (rc === null ? 9 : rc);
}

function main() {
  const tag = process.argv[2] ?? '';
  const parsed = parseAndroidTag(tag);
  if (!parsed) {
    console.error(`::error::'${tag}' is not an Android release tag of the form ${FORM}`);
    process.exit(1);
  }
  let versionCode;
  try {
    versionCode = androidVersionCode(parsed);
  } catch (error) {
    console.error(`::error::${error.message}`);
    process.exit(1);
  }
  const values = {
    tag: parsed.tag,
    versionName: parsed.versionName,
    versionCode,
    prerelease: parsed.rc === null ? 'false' : 'true',
  };
  for (const [key, value] of Object.entries(values)) console.log(`${key}=${value}`);
}

if (process.argv[1] && resolve(process.argv[1]) === fileURLToPath(import.meta.url)) main();
