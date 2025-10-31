package com.coreone.back.modules.folder.service;

import com.coreone.back.modules.folder.controller.dto.UploadFolderItemRequestDTO;
import com.coreone.back.modules.folder.domain.FolderItem;
import com.coreone.back.modules.folder.repository.FolderItemRepository;
import com.coreone.back.modules.user.domain.User;
import com.coreone.back.service.R2StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FolderItemService {
    private final FolderItemRepository repository;
    private final FolderService folderService;
    private final R2StorageService r2StorageService;

    public void uploadItem(User user, UUID folderId, UploadFolderItemRequestDTO dto) {
        var folder = folderService.findById(folderId);

        MultipartFile file = dto.file();

        String key = r2StorageService.uploadFile(file, folderId.toString());

        FolderItem item = new FolderItem();
        item.setFolderId(folder.getId());
        item.setUserId(user.getId());
        item.setFileName(file.getOriginalFilename());
        item.setStorageKey(key);
        item.setContentType(file.getContentType());
        item.setSizeBytes(file.getSize());
        item.setUrlPublic(buildPublicUrl(key));
        item.setCreatedAt(OffsetDateTime.now());

        repository.save(item);
    }

    /**
     * Generate public URL
     */
    private String buildPublicUrl(String key) {
        String baseUrl = System.getenv("R2_ACCOUNT_ID") != null
                ? "https://" + System.getenv("R2_ACCOUNT_ID") + ".r2.cloudflarestorage.com/"
                : "https://meu-bucket.r2.cloudflarestorage.com/";
        String bucket = System.getenv("R2_BUCKET") != null
                ? System.getenv("R2_BUCKET") + "/"
                : "";
        return baseUrl + bucket + key;
    }
}
