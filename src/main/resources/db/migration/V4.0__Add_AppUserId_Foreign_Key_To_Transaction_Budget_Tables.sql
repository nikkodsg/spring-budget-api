ALTER TABLE "transaction" ADD COLUMN app_user_id BIGINT;

ALTER TABLE "transaction"
ADD CONSTRAINT transaction_app_user_id_fkey
FOREIGN KEY (app_user_id)
REFERENCES app_user(id) ON DELETE CASCADE;

ALTER TABLE "budget" ADD COLUMN app_user_id BIGINT;

ALTER TABLE "budget"
ADD CONSTRAINT transaction_app_user_id_fkey
FOREIGN KEY (app_user_id)
REFERENCES app_user(id) ON DELETE CASCADE;