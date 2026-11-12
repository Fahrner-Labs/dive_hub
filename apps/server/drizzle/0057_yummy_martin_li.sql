CREATE TABLE "backup_settings" (
	"id" boolean PRIMARY KEY DEFAULT true NOT NULL,
	"enabled" boolean DEFAULT false NOT NULL,
	"interval_hours" integer DEFAULT 24 NOT NULL,
	"retention_count" integer DEFAULT 7 NOT NULL,
	"recipient_public_key" text,
	"updated_at" timestamp with time zone DEFAULT now() NOT NULL,
	CONSTRAINT "backup_settings_one_row_ck" CHECK ("backup_settings"."id"),
	CONSTRAINT "backup_settings_interval_ck" CHECK ("backup_settings"."interval_hours" between 1 and 168),
	CONSTRAINT "backup_settings_retention_ck" CHECK ("backup_settings"."retention_count" between 1 and 365),
	CONSTRAINT "backup_settings_recipient_ck" CHECK (not "backup_settings"."enabled" or "backup_settings"."recipient_public_key" is not null)
);
