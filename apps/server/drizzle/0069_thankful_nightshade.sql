CREATE TABLE "user_entitlement" (
	"user_id" uuid PRIMARY KEY NOT NULL,
	"plan" text NOT NULL,
	"status" text NOT NULL,
	"paddle_customer_id" text,
	"paddle_subscription_id" text,
	"current_period_end" timestamp with time zone,
	"updated_at" timestamp with time zone DEFAULT now() NOT NULL
);
--> statement-breakpoint
ALTER TABLE "user_entitlement" ADD CONSTRAINT "user_entitlement_user_id_user_id_fk" FOREIGN KEY ("user_id") REFERENCES "public"."user"("id") ON DELETE cascade ON UPDATE no action;--> statement-breakpoint
CREATE UNIQUE INDEX "user_entitlement_paddle_customer_uq" ON "user_entitlement" USING btree ("paddle_customer_id");--> statement-breakpoint
CREATE UNIQUE INDEX "user_entitlement_paddle_subscription_uq" ON "user_entitlement" USING btree ("paddle_subscription_id");