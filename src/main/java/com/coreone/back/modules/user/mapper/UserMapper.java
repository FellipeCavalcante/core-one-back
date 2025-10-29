package com.coreone.back.modules.user.mapper;

import com.coreone.back.modules.subSector.domain.SubSectorUser;
import com.coreone.back.modules.user.domain.User;
import com.coreone.back.modules.user.dto.GetUserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "subSectors", expression = "java(mapSubSectors(user.getSubSectorLinks()))")
    GetUserResponse toGetUserResponse(User user);

    default List<UUID> mapSubSectors(Set<SubSectorUser> links) {
        if (links == null) return List.of();
        return links.stream()
                .map(link -> link.getSubSector().getId())
                .toList();
    }

}
