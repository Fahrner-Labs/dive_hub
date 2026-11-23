CREATE TABLE "change_feed_floor" (
	"id" boolean PRIMARY KEY DEFAULT true NOT NULL,
	"xid" "xid8" DEFAULT '0'::xid8 NOT NULL,
	"seq" bigint DEFAULT 0 NOT NULL,
	"updated_at" timestamp with time zone DEFAULT now() NOT NULL
);
--> statement-breakpoint
INSERT INTO "change_feed_floor" ("id") VALUES (true);
--> statement-breakpoint
DROP INDEX "change_event_at_idx";--> statement-breakpoint
CREATE INDEX "change_event_xid_seq_idx" ON "change_event" USING btree ("xid","seq");