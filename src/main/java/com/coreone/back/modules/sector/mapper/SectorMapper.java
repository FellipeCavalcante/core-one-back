package com.coreone.back.modules.sector.mapper;

import com.coreone.back.modules.enterprise.domain.Enterprise;
import com.coreone.back.modules.sector.domain.Sector;
import com.coreone.back.modules.subSector.domain.SubSector;
import com.coreone.back.modules.sector.dto.CreateSectorRequestDTO;
import com.coreone.back.modules.sector.dto.CreateSectorResponseDTO;
import com.coreone.back.modules.sector.dto.GetSectorResponse;
import com.coreone.back.modules.sector.dto.GetSubSectorResponseBySector;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SectorMapper {

    // DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subSectors", ignore = true)
    @Mapping(target = "workstation", ignore = true)
    Sector toSector(CreateSectorRequestDTO dto);

    // Entity -> DTO
    CreateSectorResponseDTO toCreateSectorResponseDTO(Sector sector);

    GetSectorResponse toGetSectorResponse(Sector sector);

    GetSubSectorResponseBySector toGetSubSectorResponse(SubSector subSector);

    default Enterprise map(UUID enterpriseId) {
        if (enterpriseId == null) {
            return null;
        }
        Enterprise enterprise = new Enterprise();
        enterprise.setId(enterpriseId);
        return enterprise;
    }
}