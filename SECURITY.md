# Security policy

Dive Hub is self-hosted software that stores personal data (dive logs, names, locations), so we
take reports seriously and appreciate responsible disclosure.

## Reporting a vulnerability

Please **do not open a public issue** for a security problem. Report it privately through
GitHub's private vulnerability reporting:

<https://github.com/Fahrner-Labs/dive_hub/security/advisories/new>

Include, as far as you can:

- the Dive Hub version (or image tag) and how it is deployed (Docker Compose, reverse proxy);
- the steps to reproduce, or a proof of concept;
- the impact you see, and any suggested fix.

## What to expect

- We aim to acknowledge a report within **five business days** and to keep you informed while we
  investigate.
- We will confirm the issue, prepare a fix and credit you in the release notes unless you prefer
  to stay anonymous.
- Please give us time to release a fix before publishing details, and keep the report private
  until then.
- We do not run a paid bounty programme.

## Supported versions

Security fixes go into the latest release. Use an exact `vX.Y.Z` image tag and upgrade regularly
([self-hosting guide](docs/self-hosting.md#upgrades)); older tags do not receive fixes.

## Out of scope

- Misconfiguration of your own deployment (missing TLS, exposed ports, weak passwords, an
  unprotected setup token).
- Vulnerabilities in third-party services Dive Hub talks to (SSI, Garmin, Overpass, Wikidata) —
  report those upstream, though we are glad to hear if Dive Hub's use of them is unsafe.
- Vulnerabilities in the base images and dependencies — report them upstream; tell us if Dive Hub
  needs to pin or upgrade.
- Denial of service from your own authenticated account, or access you deliberately granted.
