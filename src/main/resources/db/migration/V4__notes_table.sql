CREATE TABLE folders
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name          VARCHAR(100) NOT NULL,
    description   TEXT,
    is_public     BOOLEAN          DEFAULT FALSE,
    user_id       UUID,
    enterprise_id UUID,
    created_at    TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP,
    CONSTRAINT fk_folder_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_folder_enterprise FOREIGN KEY (enterprise_id) REFERENCES enterprise (id) ON DELETE SET NULL
);

CREATE TABLE notes
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title         VARCHAR(100) NOT NULL,
    content       TEXT         NOT NULL,
    is_public     BOOLEAN          DEFAULT FALSE,
    user_id       UUID         NOT NULL,
    enterprise_id UUID,
    folder_id     UUID,
    created_at    TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP,
    CONSTRAINT fk_note_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_note_enterprise FOREIGN KEY (enterprise_id) REFERENCES enterprise (id) ON DELETE SET NULL,
    CONSTRAINT fk_note_folder FOREIGN KEY (folder_id) REFERENCES folders (id) ON DELETE CASCADE
);

CREATE INDEX idx_notes_user ON notes (user_id);
CREATE INDEX idx_notes_public ON notes (is_public);