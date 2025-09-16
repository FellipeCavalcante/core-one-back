package com.coreone.back.mapper;

import com.coreone.back.domain.Sector;
import com.coreone.back.dto.sector.CreateSectorRequestDTO;
import com.coreone.back.dto.sector.CreateSectorResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SectorMapper {

    // DTO -> Entity
    @Mapping(target = "id", ignore = true)
    Sector toSector(CreateSectorRequestDTO dto);

    // Entity -> DTO
    CreateSectorResponseDTO toCreateSectorResponseDTO(Sector sector);
}
