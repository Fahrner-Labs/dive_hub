CREATE TYPE "public"."site_id_decision_choice" AS ENUM('move', 'keep');--> statement-breakpoint
CREATE TABLE "site_id_decision" (
	"site_id" uuid NOT NULL,
	"source" "site_source" NOT NULL,
	"account_id" uuid NOT NULL,
	"choice" "site_id_decision_choice" NOT NULL,
	"decided_by" uuid,
	"decided_at" timestamp with time zone DEFAULT now() NOT NULL,
	CONSTRAINT "site_id_decision_site_id_source_account_id_pk" PRIMARY KEY("site_id","source","account_id")
);
--> statement-breakpoint
ALTER TABLE "site_id_decision" ADD CONSTRAINT "site_id_decision_site_id_dive_site_id_fk" FOREIGN KEY ("site_id") REFERENCES "public"."dive_site"("id") ON DELETE cascade ON UPDATE no action;--> statement-breakpoint
ALTER TABLE "site_id_decision" ADD CONSTRAINT "site_id_decision_account_id_diver_external_id_id_fk" FOREIGN KEY ("account_id") REFERENCES "public"."diver_external_id"("id") ON DELETE cascade ON UPDATE no action;--> statement-breakpoint
ALTER TABLE "site_id_decision" ADD CONSTRAINT "site_id_decision_decided_by_user_id_fk" FOREIGN KEY ("decided_by") REFERENCES "public"."user"("id") ON DELETE set null ON UPDATE no action;--> statement-breakpoint
CREATE INDEX "site_id_decision_account_idx" ON "site_id_decision" USING btree ("account_id");