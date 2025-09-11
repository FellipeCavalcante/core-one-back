CREATE TYPE user_type AS ENUM ('worker', 'client', 'hybrid', 'admin', 'manager');
CREATE TYPE job_type AS ENUM ('freelancer', 'seasonal', 'temporary', 'effective');
CREATE TYPE job_local AS ENUM ('remote', 'hybrid', 'in-work');

CREATE TABLE users
(
    id         UUID PRIMARY KEY,
    name       VARCHAR(100)        NOT NULL,
    password   VARCHAR(100)        NOT NULL,
    email      VARCHAR(100) UNIQUE NOT NULL,
    type       VARCHAR(10)         NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE locate
(
    id        UUID PRIMARY KEY,
    cep       INT          NOT NULL,
    state     VARCHAR(30)  NOT NULL,
    city      VARCHAR(40)  NOT NULL,
    street    VARCHAR(100) NOT NULL,
    number    INT,
    reference VARCHAR(255)
);

CREATE TABLE job
(
    id          UUID PRIMARY KEY,
    title       VARCHAR(100) NOT NULL,
    description TEXT         NOT NULL,
    min_pay     DECIMAL,
    max_pay     DECIMAL,
    type        job_type     NOT NULL,
    local       job_local    NOT NULL,
    owner_id    UUID NOT NULL,
    id_locate   UUID,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_job_owner FOREIGN KEY (owner_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_job_locate FOREIGN KEY (id_locate) REFERENCES locate (id) ON DELETE SET NULL
);

CREATE TABLE proposal
(
    id         UUID PRIMARY KEY,
    value      DECIMAL,
    user_id    UUID NOT NULL,
    job_id     UUID NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_proposal_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_proposal_job FOREIGN KEY (job_id) REFERENCES job (id) ON DELETE CASCADE
);
