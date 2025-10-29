package com.coreone.back.modules.project.mapper;

import com.coreone.back.modules.project.domain.Project;
import com.coreone.back.modules.project.dto.CreateProjectRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "subSectors", ignore = true)
    @Mapping(target = "members", ignore = true)
    @Mapping(target = "workstation", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Project toDomain(CreateProjectRequestDTO dto);
}
