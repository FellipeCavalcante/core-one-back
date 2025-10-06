package com.coreone.back.modules.auth.mapper;

import com.coreone.back.modules.user.domain.User;
import com.coreone.back.modules.user.dto.CreateUserRequestDTO;
import com.coreone.back.modules.user.dto.CreateUserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthMapper {

    // DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(new java.sql.Timestamp(System.currentTimeMillis()))")
    @Mapping(target = "updatedAt", expression = "java(new java.sql.Timestamp(System.currentTimeMillis()))")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "enterprise", ignore = true)
    @Mapping(target = "subSector", ignore = true)
    @Mapping(target = "taskMemberships", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toUser(CreateUserRequestDTO dto);

    // Entity -> DTO
    CreateUserResponseDTO toCreateResponse(User user);
}