CREATE TABLE "client_diagnostic" (
	"id" uuid PRIMARY KEY DEFAULT uuidv7() NOT NULL,
	"user_id" uuid NOT NULL,
	"client_name" text NOT NULL,
	"client_version" text NOT NULL,
	"captured_at" timestamp with time zone NOT NULL,
	"occurred_at" timestamp with time zone NOT NULL,
	"level" text NOT NULL,
	"code" text,
	"message" text NOT NULL,
	"stack" text,
	"context_json" jsonb,
	"created_at" timestamp with time zone DEFAULT now() NOT NULL,
	CONSTRAINT "client_diagnostic_level_ck" CHECK ("client_diagnostic"."level" in ('error', 'warn'))
);
--> statement-breakpoint
CREATE TABLE "client_diagnostics_settings" (
	"id" boolean PRIMARY KEY DEFAULT true NOT NULL,
	"enabled" boolean DEFAULT false NOT NULL,
	"updated_at" timestamp with time zone DEFAULT now() NOT NULL,
	CONSTRAINT "client_diagnostics_settings_one_row_ck" CHECK ("client_diagnostics_settings"."id")
);
--> statement-breakpoint
ALTER TABLE "client_diagnostic" ADD CONSTRAINT "client_diagnostic_user_id_user_id_fk" FOREIGN KEY ("user_id") REFERENCES "public"."user"("id") ON DELETE cascade ON UPDATE no action;--> statement-breakpoint
CREATE INDEX "client_diagnostic_created_idx" ON "client_diagnostic" USING btree ("created_at" desc);