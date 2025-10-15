package com.coreone.back.modules.enterprise.mapper;

import com.coreone.back.modules.enterprise.domain.EnterpriseRequest;
import com.coreone.back.modules.enterprise.dto.EnterpriseRequestResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EnterpriseRequestMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "enterprise.id", target = "enterpriseId")
    EnterpriseRequestResponseDTO toResponse(EnterpriseRequest entity);
}
