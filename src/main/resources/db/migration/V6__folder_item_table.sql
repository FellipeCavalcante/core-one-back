CREATE TABLE folder_item
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    folder_id    UUID NOT NULL,
    user_id      UUID,
    file_name    VARCHAR(255) NOT NULL,
    storage_key  VARCHAR(512) NOT NULL UNIQUE, -- R2 key
    content_type VARCHAR(128),
    size_bytes   BIGINT,
    url_public   TEXT, -- if public; or NULL for presigned URL
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP,

    CONSTRAINT fk_folder_item_folder FOREIGN KEY (folder_id) REFERENCES folders (id) ON DELETE CASCADE,
    CONSTRAINT fk_folder_item_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL
);

CREATE INDEX idx_folder_item_folder_id ON folder_item(folder_id);
CREATE INDEX idx_folder_item_storage_key ON folder_item(storage_key);
