CREATE TABLE "joint_dive_dismissal" (
	"id" uuid PRIMARY KEY DEFAULT uuidv7() NOT NULL,
	"joint_dive_id" uuid NOT NULL,
	"dive_id" uuid NOT NULL,
	"field" text NOT NULL,
	"created_at" timestamp with time zone DEFAULT now() NOT NULL
);
--> statement-breakpoint
ALTER TABLE "joint_dive_dismissal" ADD CONSTRAINT "joint_dive_dismissal_joint_dive_id_joint_dive_id_fk" FOREIGN KEY ("joint_dive_id") REFERENCES "public"."joint_dive"("id") ON DELETE cascade ON UPDATE no action;--> statement-breakpoint
ALTER TABLE "joint_dive_dismissal" ADD CONSTRAINT "joint_dive_dismissal_dive_id_dive_id_fk" FOREIGN KEY ("dive_id") REFERENCES "public"."dive"("id") ON DELETE cascade ON UPDATE no action;--> statement-breakpoint
CREATE UNIQUE INDEX "joint_dive_dismissal_uq" ON "joint_dive_dismissal" USING btree ("joint_dive_id","dive_id","field");