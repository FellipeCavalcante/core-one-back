package com.findjobs.back.mapper;

import com.findjobs.back.domain.SubSector;
import com.findjobs.back.dto.subSector.CreateSubSectorRequestDTO;
import com.findjobs.back.dto.subSector.CreateSubSectorResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubSectorMapper {

    // DTO -> Entity
    SubSector toSubSector(CreateSubSectorRequestDTO dto);

    // Entity -> DTO
    CreateSubSectorResponseDTO toCreateSubSectorResponseDTO(SubSector subSector);
}
