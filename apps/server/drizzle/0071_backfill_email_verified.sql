-- Users created by the setup token or an invitation before registration could open were vouched for by an admin and
-- have no verification mail to click; mark them verified so opening registration cannot lock them out.
UPDATE "user" SET "email_verified" = true WHERE "email_verified" = false;
