package com.coreone.back.modules.folder.repository;

import com.coreone.back.modules.folder.domain.FolderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FolderItemRepository extends JpaRepository<FolderItem, UUID> {
    List<FolderItem> findAlByFolderId(UUID folderId);
}
