package com.coreone.back.web.mapper;

import com.coreone.back.domain.entity.User;
import com.coreone.back.web.dto.user.CreateUserRequestDTO;
import com.coreone.back.web.dto.user.CreateUserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthMapper {

    // DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(new java.sql.Timestamp(System.currentTimeMillis()))")
    @Mapping(target = "updatedAt", expression = "java(new java.sql.Timestamp(System.currentTimeMillis()))")
    @Mapping(target = "enterprise", ignore = true)
    @Mapping(target = "subSector", ignore = true)
    @Mapping(target = "taskMemberships", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toUser(CreateUserRequestDTO dto);

    // Entity -> DTO
    CreateUserResponseDTO toCreateResponse(User user);
}
