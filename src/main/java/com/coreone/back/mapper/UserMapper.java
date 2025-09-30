package com.coreone.back.mapper;

import com.coreone.back.domain.SubSector;
import com.coreone.back.domain.User;
import com.coreone.back.dto.users.CreateUserRequestDTO;
import com.coreone.back.dto.users.CreateUserResponseDTO;
import com.coreone.back.dto.users.GetUserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

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

    @Mapping(target = "subSector", source = "subSector.id")
    GetUserResponse toGetUserResponse(User user);

    @Named("mapSubSector")
    default SubSector mapSubSector(UUID subSectorId) {
        if (subSectorId == null) {
            return null;
        }
        SubSector subSector = new SubSector();
        subSector.setId(subSectorId);
        return subSector;
    }
}
