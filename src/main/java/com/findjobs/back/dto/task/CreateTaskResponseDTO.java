package com.findjobs.back.dto.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskResponseDTO {
    private UUID id;
    private UUID creatorId;
    private String title;
    private String description;
    private List<UUID> memberIds;
    private List<UUID> subSectorIds;
}
