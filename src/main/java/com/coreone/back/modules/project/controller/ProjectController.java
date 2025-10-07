package com.coreone.back.modules.project.controller;

import com.coreone.back.common.util.AuthUtil;
import com.coreone.back.modules.project.domain.Project;
import com.coreone.back.modules.project.dto.CreateProjectRequestDTO;
import com.coreone.back.modules.project.dto.ProjectResponseDTO;
import com.coreone.back.modules.project.service.ProjectService;
import com.coreone.back.modules.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService service;
    private final AuthUtil authUtil;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<ProjectResponseDTO> create(@RequestBody CreateProjectRequestDTO request) {
        User user = authUtil.getAuthenticatedUser();

        var project = service.save(user, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }
}
