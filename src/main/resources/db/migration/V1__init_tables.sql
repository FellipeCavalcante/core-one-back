CREATE
EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE plans
(
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name            VARCHAR(100)   NOT NULL,
    value           NUMERIC(10, 2) NOT NULL,
    workstation_qtd INTEGER        NOT NULL -- 1, 2, 3, 4
);

CREATE TABLE workstation
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name       VARCHAR(100) NOT NULL,
    created_at TIMESTAMP        DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE enterprise
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(100) NOT NULL,
    description TEXT         NOT NULL,
    created_at  TIMESTAMP        DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE workstation_enterprise
(
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    workstation_id UUID NOT NULL,
    enterprise_id  UUID NOT NULL,
    activated_at   TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_workstation_enterprise_workstation FOREIGN KEY (workstation_id) REFERENCES workstation (id) ON DELETE CASCADE,
    CONSTRAINT fk_workstation_enterprise_enterprise FOREIGN KEY (enterprise_id) REFERENCES enterprise (id) ON DELETE CASCADE,
    CONSTRAINT uq_workstation_enterprise UNIQUE (workstation_id, enterprise_id)
);

CREATE TABLE sector
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name          VARCHAR(100) NOT NULL,
    enterprise_id UUID         NOT NULL,
    CONSTRAINT fk_sector_enterprise FOREIGN KEY (enterprise_id) REFERENCES enterprise (id) ON DELETE CASCADE
);

CREATE TABLE sub_sector
(
    id        UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name      VARCHAR(100) NOT NULL,
    sector_id UUID         NOT NULL,
    CONSTRAINT fk_sub_sector_sector FOREIGN KEY (sector_id) REFERENCES sector (id) ON DELETE CASCADE
);

-- users types ADMIN, MANAGER, CLIENT, WORKER

CREATE TABLE users
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name          VARCHAR(100)        NOT NULL,
    password      VARCHAR(100)        NOT NULL,
    email         VARCHAR(100) UNIQUE NOT NULL,
    type          VARCHAR(10),
    enterprise_id UUID,
    sub_sector_id UUID,
    created_at    TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP,
    CONSTRAINT fk_user_enterprise FOREIGN KEY (enterprise_id) REFERENCES enterprise (id) ON DELETE SET NULL,
    CONSTRAINT fk_user_sub_sector FOREIGN KEY (sub_sector_id) REFERENCES sub_sector (id) ON DELETE SET NULL
);

CREATE TABLE credit_card
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    brand         VARCHAR(50),           -- Visa, MasterCard, etc.
    last_digits   VARCHAR(4)   NOT NULL, -- Ex: '1234'
    holder_name   VARCHAR(100) NOT NULL,
    token         TEXT,                  -- token gateway
    expiration_mm INTEGER      NOT NULL,
    expiration_yy INTEGER      NOT NULL,
    user_id       UUID         NOT NULL,
    created_at    TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_credit_card FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE payment
(
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id        UUID           NOT NULL,
    plan_id        UUID           NOT NULL,
    credit_card_id UUID,
    value          NUMERIC(10, 2) NOT NULL,
    currency       VARCHAR(10)      DEFAULT 'BRL',
    status         VARCHAR(30)      DEFAULT 'PENDING', -- PENDING, PAID, FAILED, CANCELED
    transaction_id VARCHAR(255),                       -- gateway
    created_at     TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    expired_at     TIMESTAMP,
    CONSTRAINT fk_payment_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_payment_plan FOREIGN KEY (plan_id) REFERENCES plans (id) ON DELETE CASCADE,
    CONSTRAINT fk_payment_card FOREIGN KEY (credit_card_id) REFERENCES credit_card (id) ON DELETE SET NULL
);

CREATE TABLE tasks
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title         VARCHAR(255) NOT NULL,
    description   TEXT         NOT NULL,
    status        VARCHAR(50)  NOT NULL,
    enterprise_id UUID,
    created_at    TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP,
    finished_at   TIMESTAMP,
    CONSTRAINT fk_user_enterprise FOREIGN KEY (enterprise_id) REFERENCES enterprise (id) ON DELETE SET NULL
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
