CREATE TABLE "dive_fingerprint" (
	"dive_id" uuid NOT NULL,
	"provider" text NOT NULL,
	"dive_version" integer NOT NULL,
	"fingerprint" text NOT NULL,
	"updated_at" timestamp with time zone DEFAULT now() NOT NULL,
	CONSTRAINT "dive_fingerprint_dive_id_provider_pk" PRIMARY KEY("dive_id","provider")
);
--> statement-breakpoint
ALTER TABLE "dive_fingerprint" ADD CONSTRAINT "dive_fingerprint_dive_id_dive_id_fk" FOREIGN KEY ("dive_id") REFERENCES "public"."dive"("id") ON DELETE cascade ON UPDATE no action;