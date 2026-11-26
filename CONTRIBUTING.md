# Contributing

Thanks for helping. Dive Hub is source-available under the [Business Source License 1.1](LICENSE), and
contributions are accepted under that same license. Read [AGENTS.md](AGENTS.md) and start at
[docs/index.md](docs/index.md) for how the repository is organised.

## Developer Certificate of Origin (DCO)

We use the **DCO, not a CLA**. Every commit you author must carry a `Signed-off-by:` line certifying that you
wrote the change or have the right to submit it under the project's license. Add it with:

```sh
git commit -s
```

The line looks like:

```text
Signed-off-by: Your Name <you@example.com>
```

The [Developer Certificate of Origin 1.1](https://developercertificate.org/) is the certification. The
`dco-check` GitHub Action checks pull requests from outside contributors; a missing sign-off blocks the pull
request.

## Pull requests

- One task is one branch and one pull request ([ADR 0054](docs/decisions/0054-unattended-implementation.md)).
  The owner reviews and merges every pull request; never merge or deploy yourself.
- Tests first. The tests need PostgreSQL (`compose.dev.yaml`); see [development.md](docs/development.md).
- Run `pnpm check` while working and before a commit; CI runs `pnpm check:full` on every pull request. Fix what
  hooks and lint report; never grow a baseline or raise a threshold.
- Production dependencies must be permissively licensed: the license scan in `pnpm check` fails otherwise
  ([ADR 0086](docs/decisions/0086-busl-1-1-with-apache-2-0-change-license.md)). Garmin's FIT SDK must never ship.
- Changes to `docs/decisions/**`, `LICENSE`, the database migrations or `**/db/schema.ts` need the owner's
  explicit approval ([CODEOWNERS](.github/CODEOWNERS)).
