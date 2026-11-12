CREATE TYPE "public"."backup_run_kind" AS ENUM('manual', 'scheduled');--> statement-breakpoint
CREATE TYPE "public"."backup_run_status" AS ENUM('queued', 'running', 'ready', 'failed');--> statement-breakpoint
CREATE TABLE "backup_runs" (
	"id" uuid PRIMARY KEY DEFAULT uuidv7() NOT NULL,
	"kind" "backup_run_kind" NOT NULL,
	"status" "backup_run_status" DEFAULT 'queued' NOT NULL,
	"bytes" bigint,
	"sha256" text,
	"error" text,
	"created_by" uuid,
	"created_at" timestamp with time zone DEFAULT now() NOT NULL,
	"updated_at" timestamp with time zone DEFAULT now() NOT NULL,
	"started_at" timestamp with time zone,
	"finished_at" timestamp with time zone,
	CONSTRAINT "backup_run_bytes_ck" CHECK ("backup_runs"."bytes" is null or "backup_runs"."bytes" >= 0)
);
--> statement-breakpoint
ALTER TABLE "backup_runs" ADD CONSTRAINT "backup_runs_created_by_user_id_fk" FOREIGN KEY ("created_by") REFERENCES "public"."user"("id") ON DELETE set null ON UPDATE no action;--> statement-breakpoint
CREATE INDEX "backup_run_created_idx" ON "backup_runs" USING btree ("created_at");--> statement-breakpoint
CREATE UNIQUE INDEX "backup_run_in_flight_uq" ON "backup_runs" USING btree ((1)) WHERE "backup_runs"."status" in ('queued', 'running');