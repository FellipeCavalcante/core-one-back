package com.coreone.back.dto.subSector;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class GetSubSectorResponse {
    private UUID id;
    private String name;
    List<GetUserResponseBySubSector> users;
    List<GetTaskResponseBySubSector> tasks;
}
