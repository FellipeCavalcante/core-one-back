CREATE TABLE ticket_support
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title         VARCHAR NOT NULL,
    description   TEXT    NOT NULL,
    status        VARCHAR NOT NULL,
    type          VARCHAR NOT NULL,
    user_id       UUID,
    enterprise_id UUID,
    created_at    TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_ticket_support_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_ticket_support_enterprise FOREIGN KEY (enterprise_id) REFERENCES enterprise (id) ON DELETE CASCADE
);