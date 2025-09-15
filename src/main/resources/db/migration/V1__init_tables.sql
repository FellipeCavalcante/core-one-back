-- users types ADMIN, MANAGER, CLIENT, WORKER

CREATE TABLE sector
(
    id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL
);

CREATE TABLE sub_sector
(
    id        UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name      VARCHAR(100) NOT NULL,
    sector_id UUID         NOT NULL,
    CONSTRAINT fk_sub_sector_sector FOREIGN KEY (sector_id) REFERENCES sector (id) ON DELETE CASCADE
);

CREATE TABLE users
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name         VARCHAR(100)        NOT NULL,
    password     VARCHAR(100)        NOT NULL,
    email        VARCHAR(100) UNIQUE NOT NULL,
    type         VARCHAR(10)         NOT NULL,
    sub_sector_id UUID,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP,
    CONSTRAINT fk_user_sub_sector FOREIGN KEY (sub_sector_id) REFERENCES sub_sector (id) ON DELETE SET NULL
);
