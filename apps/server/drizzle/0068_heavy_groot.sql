CREATE TABLE "site_purge" (
	"id" uuid PRIMARY KEY DEFAULT uuidv7() NOT NULL,
	"started_by" uuid,
	"source" "site_source" NOT NULL,
	"status" "site_import_status" DEFAULT 'queued' NOT NULL,
	"progress" jsonb DEFAULT '{"step":"waiting","done":0,"total":0}'::jsonb NOT NULL,
	"result" jsonb,
	"error_code" text,
	"error_detail" text,
	"created_at" timestamp with time zone DEFAULT now() NOT NULL,
	"finished_at" timestamp with time zone
);
--> statement-breakpoint
ALTER TABLE "site_purge" ADD CONSTRAINT "site_purge_started_by_user_id_fk" FOREIGN KEY ("started_by") REFERENCES "public"."user"("id") ON DELETE set null ON UPDATE no action;--> statement-breakpoint
CREATE UNIQUE INDEX "site_purge_one_active_uq" ON "site_purge" USING btree ((true)) WHERE "site_purge"."status" in ('queued', 'running');--> statement-breakpoint
CREATE INDEX "site_purge_created_idx" ON "site_purge" USING btree ("created_at");