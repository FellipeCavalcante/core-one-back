package com.coreone.back.mapper;

import com.coreone.back.domain.Enterprise;
import com.coreone.back.dto.enterprise.GetEnterpriseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EnterpriseMapper {

    GetEnterpriseResponse toGetEnterpriseResponse(Enterprise enterprise);
}
