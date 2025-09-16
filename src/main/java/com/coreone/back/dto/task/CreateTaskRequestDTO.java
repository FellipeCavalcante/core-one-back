package com.coreone.back.dto.task;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CreateTaskRequestDTO {
    private UUID creatorId;
    private String title;
    private String description;
    private List<UUID> memberIds;
    private List<UUID> subSectorIds;
}
