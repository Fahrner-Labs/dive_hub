CREATE TYPE "public"."change_event_kind" AS ENUM('changed', 'deleted');--> statement-breakpoint
CREATE TABLE "change_event" (
	"seq" bigserial PRIMARY KEY NOT NULL,
	"xid" "xid8" DEFAULT pg_current_xact_id() NOT NULL,
	"entity_type" text NOT NULL,
	"entity_id" uuid NOT NULL,
	"kind" "change_event_kind" NOT NULL,
	"at" timestamp with time zone DEFAULT now() NOT NULL
);
--> statement-breakpoint
DROP INDEX "certification_card_uq";--> statement-breakpoint
ALTER TABLE "diver" ADD COLUMN "version" integer DEFAULT 1 NOT NULL;--> statement-breakpoint
CREATE INDEX "change_event_at_idx" ON "change_event" USING btree ("at");--> statement-breakpoint
CREATE UNIQUE INDEX "certification_card_uq" ON "certification" USING btree ("diver_id","source","external_id") WHERE "certification"."external_id" is not null and "certification"."deleted_at" is null;