package com.coreone.back.dto.subSector;

import lombok.Data;

import java.util.UUID;

@Data
public class GetTaskResponseBySubSector {

    private UUID id;
    private String title;
    private String description;
    private String status;
}
