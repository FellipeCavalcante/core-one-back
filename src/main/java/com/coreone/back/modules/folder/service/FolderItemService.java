package com.coreone.back.modules.folder.service;

import com.coreone.back.common.errors.NotFoundException;
import com.coreone.back.common.errors.UnauthorizedException;
import com.coreone.back.modules.folder.controller.dto.GetFolderItemResponseDTO;
import com.coreone.back.modules.folder.controller.dto.UploadFolderItemRequestDTO;
import com.coreone.back.modules.folder.domain.FolderItem;
import com.coreone.back.modules.folder.repository.FolderItemRepository;
import com.coreone.back.modules.user.domain.User;
import com.coreone.back.service.S3StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FolderItemService {
    private final FolderItemRepository repository;
    private final FolderService folderService;
    private final S3StorageService s3StorageService;

    /**
     *
     * @param user
     * @param folderId
     * @param dto
     * upload item to bucket
     */
    public void uploadItem(User user, UUID folderId, UploadFolderItemRequestDTO dto) {
        var folder = folderService.findById(folderId);

        MultipartFile file = dto.file();

        String key = s3StorageService.uploadFile(file, folderId.toString());

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
     *  Delete item
     */
    public void deleteItem(User user, UUID itemId) {
        var item = findById(itemId);

        if (!item.getUserId().equals(user.getId())) {
            throw new UnauthorizedException("Unauthorized");
        }

        s3StorageService.deleteFile(item.getStorageKey());

        repository.delete(item);
    }

    /**
     *
     * @param folderId
     * @return list of items
     */
    public List<GetFolderItemResponseDTO> folderItems(UUID folderId) {
        var folder = folderService.findById(folderId);

        List<FolderItem> items = repository.findAlByFolderId(folder.getId());

        return items.stream().map(item -> new GetFolderItemResponseDTO(
                item.getId(),
                item.getUserId(),
                item.getFileName(),
                item.getStorageKey(),
                item.getContentType(),
                item.getUrlPublic(),
                item.getCreatedAt()
        )).toList();
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

    private FolderItem findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Item not found")
                );
    }
}
