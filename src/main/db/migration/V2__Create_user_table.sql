CREATE TABLE "public"."user" (
    "id" SERIAL8 NOT NULL,
    "status" VARCHAR(32) NOT NULL,
    "login" VARCHAR(255) NOT NULL,
    "auth_token" VARCHAR(36) NOT NULL,
    "created_at" TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY ("id")
);

CREATE INDEX "user_idx_status" ON "public"."user" USING BTREE ("status");
CREATE UNIQUE INDEX "user_idx_login" ON "public"."user" USING BTREE ("login");
CREATE UNIQUE INDEX "user_idx_auth_token" ON "public"."user" USING BTREE ("auth_token");
