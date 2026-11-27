ALTER TABLE "user_entitlement" RENAME COLUMN "paddle_customer_id" TO "billing_customer_id";--> statement-breakpoint
ALTER TABLE "user_entitlement" RENAME COLUMN "paddle_subscription_id" TO "billing_subscription_id";--> statement-breakpoint
ALTER INDEX "user_entitlement_paddle_customer_uq" RENAME TO "user_entitlement_billing_customer_uq";--> statement-breakpoint
ALTER INDEX "user_entitlement_paddle_subscription_uq" RENAME TO "user_entitlement_billing_subscription_uq";
