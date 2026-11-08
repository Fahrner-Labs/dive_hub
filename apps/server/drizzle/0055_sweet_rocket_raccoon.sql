CREATE TYPE "public"."equipment_category" AS ENUM('regulator', 'bcd', 'wing', 'backplate', 'cylinder', 'drysuit', 'wetsuit', 'dive_computer', 'transmitter', 'light', 'camera', 'other');--> statement-breakpoint
CREATE TYPE "public"."equipment_status" AS ENUM('in_use', 'retired', 'lost', 'sold');--> statement-breakpoint
CREATE TYPE "public"."equipment_use_kind" AS ENUM('put_on', 'taken_off');--> statement-breakpoint
CREATE TABLE "equipment_item" (
	"id" uuid PRIMARY KEY DEFAULT uuidv7() NOT NULL,
	"diver_id" uuid NOT NULL,
	"category" "equipment_category" NOT NULL,
	"name" text NOT NULL,
	"maker" text,
	"model" text,
	"serial" text,
	"purchase_date" date,
	"notes" text,
	"status" "equipment_status" DEFAULT 'in_use' NOT NULL,
	"on_every_dive" boolean DEFAULT false NOT NULL,
	"in_use_from" date NOT NULL,
	"in_use_until" date,
	"version" integer DEFAULT 1 NOT NULL,
	"created_at" timestamp with time zone DEFAULT now() NOT NULL,
	"updated_at" timestamp with time zone DEFAULT now() NOT NULL,
	"deleted_at" timestamp with time zone,
	CONSTRAINT "equipment_item_window_ck" CHECK ("equipment_item"."in_use_until" is null or "equipment_item"."in_use_until" >= "equipment_item"."in_use_from")
);
--> statement-breakpoint
CREATE TABLE "equipment_use" (
	"dive_id" uuid NOT NULL,
	"item_id" uuid NOT NULL,
	"use" "equipment_use_kind" NOT NULL,
	CONSTRAINT "equipment_use_dive_id_item_id_pk" PRIMARY KEY("dive_id","item_id")
);
--> statement-breakpoint
CREATE TABLE "service_record" (
	"id" uuid PRIMARY KEY DEFAULT uuidv7() NOT NULL,
	"item_id" uuid NOT NULL,
	"date" date NOT NULL,
	"by_whom" text,
	"notes" text,
	"cost_amount" numeric(12, 2),
	"cost_currency" text,
	"version" integer DEFAULT 1 NOT NULL,
	"created_at" timestamp with time zone DEFAULT now() NOT NULL,
	"updated_at" timestamp with time zone DEFAULT now() NOT NULL,
	CONSTRAINT "service_record_cost_ck" CHECK (("service_record"."cost_amount" is null) = ("service_record"."cost_currency" is null) and ("service_record"."cost_amount" is null or "service_record"."cost_amount" >= 0) and ("service_record"."cost_currency" is null or "service_record"."cost_currency" ~ '^[A-Z]{3}$'))
);
--> statement-breakpoint
CREATE TABLE "service_record_schedule" (
	"record_id" uuid NOT NULL,
	"schedule_id" uuid NOT NULL,
	CONSTRAINT "service_record_schedule_record_id_schedule_id_pk" PRIMARY KEY("record_id","schedule_id")
);
--> statement-breakpoint
CREATE TABLE "service_schedule" (
	"id" uuid PRIMARY KEY DEFAULT uuidv7() NOT NULL,
	"item_id" uuid NOT NULL,
	"name" text NOT NULL,
	"interval_months" integer,
	"interval_dives" integer,
	"interval_hours" real,
	"start_date" date NOT NULL,
	"active" boolean DEFAULT true NOT NULL,
	"version" integer DEFAULT 1 NOT NULL,
	"created_at" timestamp with time zone DEFAULT now() NOT NULL,
	"updated_at" timestamp with time zone DEFAULT now() NOT NULL,
	CONSTRAINT "service_schedule_interval_ck" CHECK (("service_schedule"."interval_months" is not null or "service_schedule"."interval_dives" is not null or "service_schedule"."interval_hours" is not null)
        and coalesce("service_schedule"."interval_months", 1) > 0 and coalesce("service_schedule"."interval_dives", 1) > 0 and coalesce("service_schedule"."interval_hours", 1) > 0)
);
--> statement-breakpoint
ALTER TABLE "cylinder" ADD COLUMN "equipment_item_id" uuid;--> statement-breakpoint
ALTER TABLE "device" ADD COLUMN "equipment_item_id" uuid;--> statement-breakpoint
ALTER TABLE "equipment_item" ADD CONSTRAINT "equipment_item_diver_id_diver_id_fk" FOREIGN KEY ("diver_id") REFERENCES "public"."diver"("id") ON DELETE no action ON UPDATE no action;--> statement-breakpoint
ALTER TABLE "equipment_use" ADD CONSTRAINT "equipment_use_dive_id_dive_id_fk" FOREIGN KEY ("dive_id") REFERENCES "public"."dive"("id") ON DELETE cascade ON UPDATE no action;--> statement-breakpoint
ALTER TABLE "equipment_use" ADD CONSTRAINT "equipment_use_item_id_equipment_item_id_fk" FOREIGN KEY ("item_id") REFERENCES "public"."equipment_item"("id") ON DELETE no action ON UPDATE no action;--> statement-breakpoint
ALTER TABLE "service_record" ADD CONSTRAINT "service_record_item_id_equipment_item_id_fk" FOREIGN KEY ("item_id") REFERENCES "public"."equipment_item"("id") ON DELETE no action ON UPDATE no action;--> statement-breakpoint
ALTER TABLE "service_record_schedule" ADD CONSTRAINT "service_record_schedule_record_id_service_record_id_fk" FOREIGN KEY ("record_id") REFERENCES "public"."service_record"("id") ON DELETE cascade ON UPDATE no action;--> statement-breakpoint
ALTER TABLE "service_record_schedule" ADD CONSTRAINT "service_record_schedule_schedule_id_service_schedule_id_fk" FOREIGN KEY ("schedule_id") REFERENCES "public"."service_schedule"("id") ON DELETE cascade ON UPDATE no action;--> statement-breakpoint
ALTER TABLE "service_schedule" ADD CONSTRAINT "service_schedule_item_id_equipment_item_id_fk" FOREIGN KEY ("item_id") REFERENCES "public"."equipment_item"("id") ON DELETE no action ON UPDATE no action;--> statement-breakpoint
CREATE INDEX "equipment_item_diver_idx" ON "equipment_item" USING btree ("diver_id");--> statement-breakpoint
CREATE INDEX "equipment_use_item_idx" ON "equipment_use" USING btree ("item_id");--> statement-breakpoint
CREATE INDEX "service_record_item_idx" ON "service_record" USING btree ("item_id");--> statement-breakpoint
CREATE INDEX "service_record_schedule_schedule_idx" ON "service_record_schedule" USING btree ("schedule_id");--> statement-breakpoint
CREATE INDEX "service_schedule_item_idx" ON "service_schedule" USING btree ("item_id");--> statement-breakpoint
ALTER TABLE "cylinder" ADD CONSTRAINT "cylinder_equipment_item_id_equipment_item_id_fk" FOREIGN KEY ("equipment_item_id") REFERENCES "public"."equipment_item"("id") ON DELETE no action ON UPDATE no action;--> statement-breakpoint
ALTER TABLE "device" ADD CONSTRAINT "device_equipment_item_id_equipment_item_id_fk" FOREIGN KEY ("equipment_item_id") REFERENCES "public"."equipment_item"("id") ON DELETE no action ON UPDATE no action;--> statement-breakpoint
CREATE INDEX "cylinder_equipment_idx" ON "cylinder" USING btree ("equipment_item_id");--> statement-breakpoint
-- ADR 0034 D7: every existing Device gets one Equipment item, named after the Device (the Device table cannot tell a
-- transmitter from a computer, so every item starts as a dive computer; the User recategorizes it). Its Dives count
-- through the Device's Recordings, not as an every-Dive item. The item keeps the Device's id, so the link is direct.
-- Its in-use window starts at the earliest Dive the Device recorded (the local date of its earliest Recording), else today.
INSERT INTO "equipment_item" ("id", "diver_id", "category", "name", "status", "on_every_dive", "in_use_from", "created_at", "updated_at")
SELECT d."id", d."diver_id", 'dive_computer', trim(concat_ws(' ', d."manufacturer", d."product")), 'in_use', false,
       coalesce(
         (SELECT min(((r."starts_at" + make_interval(secs => coalesce(r."utc_offset_seconds", 0))) at time zone 'UTC')::date)
          FROM "recording" r
          WHERE r."device_id" = d."id" AND r."deleted_at" is null),
         current_date
       ),
       d."created_at", d."updated_at"
FROM "device" d;--> statement-breakpoint
UPDATE "device" SET "equipment_item_id" = "id";--> statement-breakpoint
ALTER TABLE "device" ALTER COLUMN "equipment_item_id" SET NOT NULL;