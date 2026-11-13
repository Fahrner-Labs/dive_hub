CREATE TYPE "public"."suggestion_status" AS ENUM('pending', 'accepted', 'declined', 'cancelled');--> statement-breakpoint
CREATE TABLE "buddy_suggestion" (
	"id" uuid PRIMARY KEY DEFAULT uuidv7() NOT NULL,
	"dive_id" uuid NOT NULL,
	"diver_id" uuid NOT NULL,
	"status" "suggestion_status" DEFAULT 'pending' NOT NULL,
	"created_at" timestamp with time zone DEFAULT now() NOT NULL,
	"resolved_at" timestamp with time zone
);
--> statement-breakpoint
CREATE TABLE "joint_dive" (
	"id" uuid PRIMARY KEY DEFAULT uuidv7() NOT NULL,
	"created_at" timestamp with time zone DEFAULT now() NOT NULL
);
--> statement-breakpoint
ALTER TABLE "dive" ADD COLUMN "joint_dive_id" uuid;--> statement-breakpoint
ALTER TABLE "buddy_suggestion" ADD CONSTRAINT "buddy_suggestion_dive_id_dive_id_fk" FOREIGN KEY ("dive_id") REFERENCES "public"."dive"("id") ON DELETE cascade ON UPDATE no action;--> statement-breakpoint
ALTER TABLE "buddy_suggestion" ADD CONSTRAINT "buddy_suggestion_diver_id_diver_id_fk" FOREIGN KEY ("diver_id") REFERENCES "public"."diver"("id") ON DELETE no action ON UPDATE no action;--> statement-breakpoint
CREATE UNIQUE INDEX "buddy_suggestion_dive_diver_uq" ON "buddy_suggestion" USING btree ("dive_id","diver_id");--> statement-breakpoint
CREATE INDEX "buddy_suggestion_diver_idx" ON "buddy_suggestion" USING btree ("diver_id");--> statement-breakpoint
ALTER TABLE "dive" ADD CONSTRAINT "dive_joint_dive_id_joint_dive_id_fk" FOREIGN KEY ("joint_dive_id") REFERENCES "public"."joint_dive"("id") ON DELETE no action ON UPDATE no action;