package com.coreone.back.modules.folder.controller;

import com.coreone.back.common.util.AuthUtil;
import com.coreone.back.modules.folder.controller.dto.CreateFolderDTO;
import com.coreone.back.modules.folder.service.FolderService;
import com.coreone.back.modules.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
