package com.coreone.back.web.mapper;

import com.coreone.back.domain.entity.Enterprise;
import com.coreone.back.web.dto.enterprise.GetEnterpriseResponse;
import com.coreone.back.web.dto.enterprise.UpdateEnterpriseResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EnterpriseMapper {

    GetEnterpriseResponse toGetEnterpriseResponse(Enterprise enterprise);

    UpdateEnterpriseResponseDTO toUpdateResponse(Enterprise enterprise);
}
