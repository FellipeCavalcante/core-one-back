package com.coreone.back.modules.project.dto;

import lombok.Data;

@Data
public class UpdateProjectRequest {
    private String name;
    private String description;
    private String status;
}
