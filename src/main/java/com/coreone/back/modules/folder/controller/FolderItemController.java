package com.coreone.back.modules.folder.controller;

import com.coreone.back.common.util.AuthUtil;
import com.coreone.back.modules.folder.controller.dto.UploadFolderItemRequestDTO;
import com.coreone.back.modules.folder.service.FolderItemService;
import com.coreone.back.modules.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/folder-item")
@RequiredArgsConstructor
public class FolderItemController {
    private final FolderItemService service;
    private final AuthUtil authUtil;

    @PostMapping("/{folderId}/upload")
    public ResponseEntity<Void> uploadFile(
            @PathVariable UUID folderId,
            @ModelAttribute UploadFolderItemRequestDTO dto
    ) {
        User user = authUtil.getAuthenticatedUser();

        service.uploadItem(user, folderId, dto);
        return ResponseEntity.ok().build();
    }
}
