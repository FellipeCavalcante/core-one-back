CREATE TABLE project
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name          VARCHAR     NOT NULL,
    description   TEXT        NOT NULL,
    status        VARCHAR(30) NOT NULL,
    enterprise_id UUID,
    created_at    TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP,
    CONSTRAINT fk_project_enterprise FOREIGN KEY (enterprise_id) REFERENCES enterprise (id) ON DELETE SET NULL
);

CREATE TABLE project_sub_sector
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    project_id    UUID NOT NULL,
    sub_sector_id UUID NOT NULL,
    CONSTRAINT fk_project_sub_sector_project FOREIGN KEY (project_id) REFERENCES project (id) ON DELETE CASCADE,
    CONSTRAINT fk_project_sub_sector_sub_sector FOREIGN KEY (sub_sector_id) REFERENCES sub_sector (id) ON DELETE CASCADE,
    CONSTRAINT uq_project_sub_sector UNIQUE (project_id, sub_sector_id)
);

CREATE TABLE project_member
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    project_id UUID NOT NULL,
    user_id    UUID NOT NULL,
    CONSTRAINT fk_project_member_project FOREIGN KEY (project_id) REFERENCES project (id) ON DELETE CASCADE,
    CONSTRAINT fk_project_member_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT uq_project_members UNIQUE (project_id, user_id)
);

CREATE TABLE project_task
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    project_id UUID NOT NULL,
    task_id    UUID NOT NULL,
    CONSTRAINT fk_project_task_project FOREIGN KEY (project_id) REFERENCES project (id) ON DELETE CASCADE,
    CONSTRAINT fk_project_task_task FOREIGN KEY (task_id) REFERENCES tasks (id) ON DELETE CASCADE,
    CONSTRAINT uq_project_tasks UNIQUE (project_id, task_id)
);
