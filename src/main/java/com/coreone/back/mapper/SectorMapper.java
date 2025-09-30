package com.coreone.back.mapper;

import com.coreone.back.domain.Enterprise;
import com.coreone.back.domain.Sector;
import com.coreone.back.domain.SubSector;
import com.coreone.back.dto.sector.CreateSectorRequestDTO;
import com.coreone.back.dto.sector.CreateSectorResponseDTO;
import com.coreone.back.dto.sector.GetSectorResponse;
import com.coreone.back.dto.sector.GetSubSectorResponseBySector;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SectorMapper {

    // DTO -> Entity
    @Mapping(target = "id", ignore = true)
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