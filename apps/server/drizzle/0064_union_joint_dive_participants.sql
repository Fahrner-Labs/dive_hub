-- Joint dives linked before FAH-410 copied no Participants (accept branch B) and never propagated later adds. This
-- one-time union gives every member of every existing Joint dive the people on the other members — the other members'
-- own Divers as buddies and their Participants with their roles — excluding the receiving Dive's own Diver. A member that
-- already lists a person keeps its own role, and the missing-Participant key makes a re-run a no-op. Adds only: no
-- Participant is removed, and no suggestion or live hint is raised, exactly as the link-time union does (ADR 0082).
INSERT INTO "participant" ("dive_id", "diver_id", "role")
SELECT DISTINCT ON ("member"."id", "person"."diver_id")
  "member"."id", "person"."diver_id", "person"."role"
FROM "dive" AS "member"
JOIN "dive" AS "other"
  ON "other"."joint_dive_id" = "member"."joint_dive_id"
  AND "other"."id" <> "member"."id"
  AND "other"."deleted_at" IS NULL
CROSS JOIN LATERAL (
  -- The other member's own Diver comes first, as in the link-time union.
  SELECT "other"."diver_id" AS "diver_id", 'buddy'::"participant_role" AS "role", 0 AS "rank"
  UNION ALL
  SELECT "participant"."diver_id", "participant"."role", 1 AS "rank"
  FROM "participant" WHERE "participant"."dive_id" = "other"."id"
) AS "person"
WHERE "member"."joint_dive_id" IS NOT NULL
  AND "member"."deleted_at" IS NULL
  AND "person"."diver_id" <> "member"."diver_id"
-- The first source wins a role: members in id order, each member's own Diver before its Participants.
ORDER BY "member"."id", "person"."diver_id", "other"."id", "person"."rank"
ON CONFLICT DO NOTHING;
