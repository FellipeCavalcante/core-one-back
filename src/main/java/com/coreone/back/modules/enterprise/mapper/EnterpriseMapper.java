package com.coreone.back.modules.enterprise.mapper;

import com.coreone.back.modules.enterprise.domain.Enterprise;
import com.coreone.back.modules.enterprise.dto.GetEnterpriseResponse;
import com.coreone.back.modules.enterprise.dto.UpdateEnterpriseResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EnterpriseMapper {

    GetEnterpriseResponse toGetEnterpriseResponse(Enterprise enterprise);

    UpdateEnterpriseResponseDTO toUpdateResponse(Enterprise enterprise);
}
