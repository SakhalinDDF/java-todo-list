CREATE TABLE "public"."task" (
    "id" SERIAL8 NOT NULL,
    "user_id" INT8 NOT NULL,
    "status" VARCHAR(32) NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "created_at" TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY ("id"),
    FOREIGN KEY ("user_id") REFERENCES "public"."user" ("id") ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE INDEX "task_idx_status" ON "public"."task" USING BTREE ("status");
CREATE INDEX "task_idx_user_id" ON "public"."task" USING BTREE ("user_id");
CREATE INDEX "task_idx_created_at" ON "public"."task" USING BTREE ("created_at");
