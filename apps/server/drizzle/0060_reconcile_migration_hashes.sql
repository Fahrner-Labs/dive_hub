-- FAH-221: a one-time repair for databases that applied an earlier draft of 0011 and 0038, as a dev server can
-- when it starts before a migration's hand-written part is finished. Drizzle stores the SHA-256 of an applied file
-- and the server reports a mismatch at start ("Migration files changed after this database applied them"); a
-- backup preserves the stale hash. The released files' objects exist in those databases and nothing has to run
-- again, so this updates only the two stored hashes to the released files' hashes. A database that already
-- matches changes nothing, and a fresh one is unaffected.
--
-- The hashes are the SHA-256 of the files as released; apps/server/test/migrations.test.ts checks both against
-- the files, and released migrations are never edited (docs/development.md).
UPDATE "drizzle"."__drizzle_migrations" SET "hash" = 'b9b0619530bb5708f2d808699ec6e72992018693e9d5038859755452602a7385'
  WHERE "created_at" = 1791142289544 AND "hash" <> 'b9b0619530bb5708f2d808699ec6e72992018693e9d5038859755452602a7385';--> statement-breakpoint
UPDATE "drizzle"."__drizzle_migrations" SET "hash" = '59e8cc44ff60f2227fcc9e39a3cd150071d7ee1f951582bd7dc75a4c8aa16aff'
  WHERE "created_at" = 1791810961292 AND "hash" <> '59e8cc44ff60f2227fcc9e39a3cd150071d7ee1f951582bd7dc75a4c8aa16aff';
