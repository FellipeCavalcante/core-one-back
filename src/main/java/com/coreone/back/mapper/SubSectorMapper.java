package com.coreone.back.mapper;

import com.coreone.back.domain.Sector;
import com.coreone.back.domain.SubSector;
import com.coreone.back.dto.subSector.CreateSubSectorRequestDTO;
import com.coreone.back.dto.subSector.CreateSubSectorResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubSectorMapper {

    // DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sector", source = "sector")
    SubSector toSubSector(CreateSubSectorRequestDTO dto);

    // Entity -> DTO
    @Mapping(target = "sector", source = "sector.id")
    CreateSubSectorResponseDTO toCreateSubSectorResponseDTO(SubSector subSector);

    default Sector map(UUID id) {
        if (id == null) return null;
        Sector sector = new Sector();
        sector.setId(id);
        return sector;
    }

    default UUID map(Sector sector) {
        if (sector == null) return null;
        return sector.getId();
    }
}

