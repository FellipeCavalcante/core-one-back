package com.coreone.back.dto.task;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class GetTaskResponse {

    private UUID id;
    private String title;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime finishedAt;

    private List<TaskMemberResponse> members;
    private List<TaskSubSectorResponse> subSectors;
}
