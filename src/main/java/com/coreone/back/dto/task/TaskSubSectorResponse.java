package com.coreone.back.dto.task;

import lombok.Data;
import java.util.UUID;

@Data
public class TaskSubSectorResponse {
    private UUID id;
    private UUID subSectorId;
    private String subSectorName;
}
