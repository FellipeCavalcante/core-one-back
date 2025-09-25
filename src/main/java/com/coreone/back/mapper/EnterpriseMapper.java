package com.coreone.back.mapper;

import com.coreone.back.domain.Enterprise;
import com.coreone.back.dto.enterprise.GetEnterpriseResponse;
import com.coreone.back.dto.enterprise.UpdateEnterpriseResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EnterpriseMapper {

    GetEnterpriseResponse toGetEnterpriseResponse(Enterprise enterprise);

    UpdateEnterpriseResponseDTO toUpdateResponse(Enterprise enterprise);
}
