package com.coreone.back.modules.task.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class TaskMemberResponse {
    private UUID id;
    private UUID userId;
    private String userName;
}
