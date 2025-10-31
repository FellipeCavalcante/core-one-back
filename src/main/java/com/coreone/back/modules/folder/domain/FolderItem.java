package com.coreone.back.modules.folder.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "folder_item")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FolderItem {

    @Id
    private UUID id = UUID.randomUUID();

    @Column(name = "folder_id", nullable = false)
    private UUID folderId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "storage_key", nullable = false, unique = true)
    private String storageKey;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "size_bytes")
    private Long sizeBytes;

    @Column(name = "url_public")
    private String urlPublic;

    @Column(name = "created_at")
    private OffsetDateTime createdAt = OffsetDateTime.now();
}
