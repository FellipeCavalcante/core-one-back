package com.coreone.back.dto.sector;

import com.coreone.back.domain.SubSector;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class GetSectorResponse {
    private UUID id;
    private String name;
    private List<SubSector> subSectors;
}
