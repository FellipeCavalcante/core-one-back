package com.coreone.back.modules.sector.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class GetSectorResponse {
    private UUID id;
    private String name;
    private List<GetSubSectorResponseBySector> subSectors;
}
