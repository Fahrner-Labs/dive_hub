# AGENTS.md

For AI coding agents (any tool).

## Rule #1: where knowledge lives

Work runs through Paperclip ([ADR 0065](docs/decisions/0065-work-runs-through-paperclip.md)).

- **This repository** holds durable product knowledge: spec, glossary, architecture ADRs, references.
- **Paperclip** holds work state: tasks, plans, discussion, review. Process and workflow rules live there too.
- **Agent memory** holds operational notes only, never product knowledge.
- Asked to "remember" something about the product: put it in `docs/` and say where.

## Where things are

Start at [docs/index.md](docs/index.md).

| Folder | Contents |
|---|---|
| `docs/spec/` | Product specification |
| `docs/glossary.md` | Domain language |
| `docs/decisions/` | ADRs (`NNNN-title.md`, copy `template.md`) |
| `docs/references/` | One file per external project/source |
| `docs/research/` | Dated research notes (`YYYY-MM-DD-topic.md`), sources linked |
| `docs/generated/` | CI-built navigation artefacts: `REPOMAP.md` (ranked symbol map; search it before grep-walking the tree), Graphify code graph and dependency-cruiser boundaries |

## Code navigation

`docs/generated/` holds three artefacts rebuilt on every merge to `main` ([ADR 0067](docs/decisions/0067-code-navigation-for-agents.md)):
`REPOMAP.md` (layer 1, [scripts/repomap.mjs](scripts/repomap.mjs)), `graph.json` (Graphify's symbol/call graph) and
`dependency-graph.json` (dependency-cruiser, [ADR 0053](docs/decisions/0053-module-boundaries.md)).
Review diffs leave them out. **The map and graph are hints; verify by reading the code.**

Start with `REPOMAP.md` for cheap per-module orientation, then drop to the code graph:

- Resolve the exact symbol first with grep (`rg -n 'lockManagedDive' apps packages`).
- Then ask with exact names: `graphify explain lockManagedDive --graph docs/generated/graph.json`,
  `graphify affected lockManagedDive --depth 2 --graph docs/generated/graph.json`, and
  `graphify path <from-file> <to-file> --graph docs/generated/graph.json`. An ambiguous name exits 3 with the
  candidates; `norm_label` in `graph.json` is the name Graphify stores.
- Do **not** use the natural-language `graphify query`: it was the weakest surface in the spike.
- Many commits stale? `graphify update .` refreshes `graphify-out/graph.json` in ~16 s; query that file. It cannot
  prune deleted or renamed files, so CI does a full rebuild.
- "What may import what": `dependency-graph.json` with the rules and `MODULE_IMPORTS` in `.dependency-cruiser.cjs`.

## Conventions

- Markdown with YAML frontmatter (`title`, `summary`, `status`, `date`); relative links, not `[[wikilinks]]`.
- ADRs only for decisions that are hard to reverse, surprising and a real trade-off; the discussion stays in the
  Paperclip issue and the ADR links to it. Don't re-litigate accepted ones without a new ADR.
- **Research**: new research goes into a Paperclip issue document; a note moves into `docs/research/` only when an ADR
  or spec cites it. Existing notes stay.
- **Checks** ([ADR 0023](docs/decisions/0023-faster-checks.md)): `pnpm check` while working and before a commit (only what changed
  since its last green run); no full check before a push: CI's `pnpm check:full` on the PR is the gate. The review
  capture (`REVIEW_AREAS`) only when UI changed. After a fix, `pnpm check` again (it can count from the failed
  browser run). `pnpm check --failed` reruns failed browser tests (green there renews the record); a change to e2e helpers or fixtures runs no browser tests:
  `pnpm check --tests <filter>` runs given ones and records them ([development.md](docs/development.md)); `pnpm check:log`
  sums the local run log. A PR whose CI fails after a green local check gets an entry in [check escapes](docs/agents/check-escapes.md).
  A docs-only commit needs no check. A new page or module gets its path in `scripts/check-lib.mjs`; a browser test an area tag.
- **Gates** ([research](docs/research/2026-10-22-agent-guardrails.md)): an edit hook lints each file; run `pnpm hooks:install`
  once per clone (pre-commit check). Fix what they report; never grow a baseline
  or raise a threshold. After a fix, `pnpm lint:baseline`.
  Module rules: [ADR 0053](docs/decisions/0053-module-boundaries.md). Before finishing a branch, run the `architecture-review` subagent.
- A new duty for API clients (something to show, ask, format or send) updates the [client contract](docs/spec/clients.md).
- The owner decides migrations (`apps/server/drizzle/**`), `**/db/schema.ts` and `docs/decisions/**`
  ([CODEOWNERS](.github/CODEOWNERS)); a task needing them without approval stops and hands back.
- Keep this file short.

## Tests

- Tests first.
- Tests run against a real PostgreSQL, on the test server only: `DIVEHUB_TEST_DATABASE_URL` (older name
  `TEST_DATABASE_URL`), else the local development default. Tests and browser tests never connect to `paperclip-db`,
  never reuse `DATABASE_URL`'s server, and create or drop databases nowhere else.
- At most one `vi.fn`, no `vi.mock`.
- Browser tests carry an area tag.
- `pnpm`, never `npm`.
- Iterate with `pnpm check --failed` / `--tests <filter>`; one full `pnpm check` after the last edit.

## Skills

Project skills live in `.claude/skills/` (pinned in `skills-lock.json`). Don't edit them.

- **Load the skills your task names**; read its override in [docs/skills.md](docs/skills.md#overrides) first: our rules win over the skill's.
- Role-specific skills live in the Paperclip company library.
- **Before new work** (area, design, technology), search for skills; install only vetted and user-approved ones.

## Working economically

- Search before reading; read parts, not whole files. `docs/index.md` and the glossary are searched, not read through.
- Keep long output out of the context: filter it or write it to a file.
- **Never saved on**: the checks before a commit or push, tests first, reading an ADR before changing it, reviewing UI
  screenshots, reading the code around an edit.
