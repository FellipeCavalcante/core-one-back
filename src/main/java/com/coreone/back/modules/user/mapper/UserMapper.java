package com.coreone.back.modules.user.mapper;

import com.coreone.back.modules.user.domain.User;
import com.coreone.back.modules.user.dto.GetUserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "subSector", source = "subSector.id")
    GetUserResponse toGetUserResponse(User user);

}
