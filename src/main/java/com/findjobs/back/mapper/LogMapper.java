package com.findjobs.back.mapper;

import com.findjobs.back.domain.Log;
import com.findjobs.back.dto.logs.GetLogResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LogMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "user.email", target = "userEmail")
    GetLogResponse toGetLogResponse(Log log);
}