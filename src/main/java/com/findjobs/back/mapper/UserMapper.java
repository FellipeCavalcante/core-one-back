package com.findjobs.back.mapper;

import com.findjobs.back.domain.SubSector;
import com.findjobs.back.domain.User;
import com.findjobs.back.dto.users.CreateUserRequestDTO;
import com.findjobs.back.dto.users.CreateUserResponseDTO;
import com.findjobs.back.dto.users.GetUserResponse;
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
    @Mapping(target = "subSector", source = "subSector", qualifiedByName = "mapSubSector")
    User toUser(CreateUserRequestDTO dto);

    // Entity -> DTO
    @Mapping(target = "subSector", source = "subSector.id")
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
