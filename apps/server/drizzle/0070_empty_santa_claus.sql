CREATE TYPE "public"."consent_kind" AS ENUM('terms', 'privacy');--> statement-breakpoint
CREATE TABLE "user_consent" (
	"user_id" uuid NOT NULL,
	"kind" "consent_kind" NOT NULL,
	"version" text NOT NULL,
	"accepted_at" timestamp with time zone DEFAULT now() NOT NULL,
	CONSTRAINT "user_consent_user_id_kind_version_pk" PRIMARY KEY("user_id","kind","version"),
	CONSTRAINT "user_consent_version_ck" CHECK (length("user_consent"."version") between 1 and 100)
);
--> statement-breakpoint
ALTER TABLE "user_consent" ADD CONSTRAINT "user_consent_user_id_user_id_fk" FOREIGN KEY ("user_id") REFERENCES "public"."user"("id") ON DELETE cascade ON UPDATE no action;