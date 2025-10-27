package com.coreone.back.modules.folder.controller;

import com.coreone.back.common.util.AuthUtil;
import com.coreone.back.modules.folder.controller.dto.CreateFolderDTO;
import com.coreone.back.modules.folder.controller.dto.GetFolderResponseDTO;
import com.coreone.back.modules.folder.controller.dto.UpdateFolderDTO;
import com.coreone.back.modules.folder.service.FolderService;
import com.coreone.back.modules.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/folder")
@RequiredArgsConstructor
public class FolderController {
    private final FolderService service;
    private final AuthUtil authUtil;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody CreateFolderDTO request) {
        User user = authUtil.getAuthenticatedUser();

        service.create(user, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/my-folders")
    public ResponseEntity<Page<GetFolderResponseDTO>> findAllByUser(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "20") int size) {
        var response = service.getMyFolders(page, size, authUtil.getAuthenticatedUser());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody UpdateFolderDTO request) {
        User user = authUtil.getAuthenticatedUser();

        service.update(user, id, request);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        User user = authUtil.getAuthenticatedUser();
        service.delete(user, id);

        return ResponseEntity.noContent().build();
    }
}
