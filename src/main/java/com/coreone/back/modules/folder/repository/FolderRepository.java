package com.coreone.back.modules.folder.repository;

import com.coreone.back.modules.folder.domain.Folder;
import com.coreone.back.modules.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FolderRepository extends CrudRepository<Folder, UUID> {
    Page<Folder> findAllByUser(User user, Pageable pageable);
}
