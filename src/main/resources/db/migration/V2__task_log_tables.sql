CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE tasks
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title       VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL,
    status      VARCHAR(50)  NOT NULL,
    created_at  TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP,
    finished_at TIMESTAMP
);

CREATE TABLE task_sub_sector
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    task_id       UUID NOT NULL,
    sub_sector_id UUID NOT NULL,
    CONSTRAINT fk_task_sub_sector_task FOREIGN KEY (task_id) REFERENCES tasks (id) ON DELETE CASCADE,
    CONSTRAINT fk_task_sub_sector_sub_sector FOREIGN KEY (sub_sector_id) REFERENCES sub_sector (id) ON DELETE CASCADE,
    CONSTRAINT uq_task_sub_sector UNIQUE (task_id, sub_sector_id)
);

CREATE TABLE task_members
(
    id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    task_id UUID NOT NULL,
    user_id UUID NOT NULL,
    CONSTRAINT fk_task_member_task FOREIGN KEY (task_id) REFERENCES tasks (id) ON DELETE CASCADE,
    CONSTRAINT fk_task_member_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT uq_task_members UNIQUE (task_id, user_id)
);

CREATE TABLE logs
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id    UUID,
    action     VARCHAR(100) NOT NULL, -- (CREATE_TASK, UPDATE_TASK_STATUS, LOGIN...)
    entity     VARCHAR(50)  NOT NULL, -- (TASK, USER, SECTOR...)
    entity_id  UUID,
    new_value  TEXT,
    created_at TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_log_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- Índices
CREATE INDEX idx_task_status ON tasks (status);
CREATE INDEX idx_logs_user_action ON logs (user_id, action);
CREATE INDEX idx_task_members_user ON task_members (user_id);
CREATE INDEX idx_task_sub_sector_sub ON task_sub_sector (sub_sector_id);