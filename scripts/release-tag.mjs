#! /usr/bin/env node
// Validates a release tag and prints what the release workflow needs (ADR 0068, FAH-243).
// Stable: `vX.Y.Z`. Prerelease: `vX.Y.Z-rcN` with N >= 1 and no leading zeros. For a prerelease,
// `version`, `baseVersion`, `major` and `minor` describe the base version `vX.Y.Z`.
const FORM = 'vX.Y.Z or vX.Y.Z-rcN';
const GRAMMAR = /^v([0-9]+)\.([0-9]+)\.([0-9]+)(?:-rc([1-9][0-9]*))?$/;

const tag = process.argv[2] ?? '';
const match = GRAMMAR.exec(tag);
if (!match) {
  console.error(`::error::'${tag}' is not a release tag of the form ${FORM}`);
  process.exit(1);
}

const [, major, minor, patch, candidate] = match;
const baseVersion = `${major}.${minor}.${patch}`;
const values = {
  tag,
  version: baseVersion,
  baseVersion,
  major,
  minor,
  prerelease: candidate === undefined ? 'false' : 'true',
};

for (const [key, value] of Object.entries(values)) console.log(`${key}=${value}`);
