package com.coreone.back.modules.folder.repository;

import com.coreone.back.modules.folder.domain.Folder;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FolderRepository extends CrudRepository<Folder, UUID> {
}
