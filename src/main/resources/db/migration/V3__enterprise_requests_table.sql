CREATE TABLE enterprise_request
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    message       VARCHAR(255),
    status        VARCHAR(50)                                NOT NULL, -- PENDING, ACCEPTED, REJECTED, CANCELLED
    type          VARCHAR(50)                                NOT NULL, -- INVITE, JOIN_REQUEST
    user_id       UUID                                       NOT NULL,
    enterprise_id UUID                                       NOT NULL,
    created_at    TIMESTAMP        DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at    TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_request_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_request_enterprise FOREIGN KEY (enterprise_id) REFERENCES enterprise (id) ON DELETE CASCADE
);