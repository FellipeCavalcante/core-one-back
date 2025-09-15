package com.findjobs.back.mapper;

import com.findjobs.back.domain.Sector;
import com.findjobs.back.dto.sector.CreateSectorRequestDTO;
import com.findjobs.back.dto.sector.CreateSectorResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SectorMapper {

    // DTO -> Entity
    Sector toSector(CreateSectorRequestDTO dto);

    // Entity -> DTO
    CreateSectorResponseDTO toCreateSectorResponseDTO(Sector sector);
}
