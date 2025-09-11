package com.findjobs.back.mapper;

import com.findjobs.back.domain.User;
import com.findjobs.back.dto.users.CreateUserRequestDTO;
import com.findjobs.back.dto.users.CreateUserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    // DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(new java.sql.Timestamp(System.currentTimeMillis()))")
    @Mapping(target = "updatedAt", expression = "java(new java.sql.Timestamp(System.currentTimeMillis()))")
    @Mapping(target = "jobs", ignore = true)
    @Mapping(target = "proposals", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "type", ignore = true)
    User toUser(CreateUserRequestDTO dto);

    // Entity -> DTO
    CreateUserResponseDTO toCreateResponse(User user);
}
