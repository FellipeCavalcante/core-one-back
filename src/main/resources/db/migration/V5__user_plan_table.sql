CREATE TABLE user_plan
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id    UUID NOT NULL,
    plan_id    UUID NOT NULL,
    start_date TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    end_date   TIMESTAMP,
    status     VARCHAR(20)      DEFAULT 'ACTIVE',
    is_current BOOLEAN          DEFAULT true,
    created_at TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_plan_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_plan_plan FOREIGN KEY (plan_id) REFERENCES plans (id) ON DELETE CASCADE
);

CREATE INDEX idx_user_plan_current ON user_plan (user_id, is_current) WHERE is_current = true;