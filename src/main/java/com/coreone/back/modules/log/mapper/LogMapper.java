package com.coreone.back.modules.log.mapper;

import com.coreone.back.modules.log.domain.Log;
import com.coreone.back.modules.log.dto.GetLogResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LogMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "user.email", target = "userEmail")
    GetLogResponse toGetLogResponse(Log log);
}