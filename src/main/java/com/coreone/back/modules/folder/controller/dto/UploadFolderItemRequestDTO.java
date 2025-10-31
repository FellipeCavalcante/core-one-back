package com.coreone.back.modules.folder.controller.dto;

import org.springframework.web.multipart.MultipartFile;

public record UploadFolderItemRequestDTO(
        MultipartFile file
) { }
