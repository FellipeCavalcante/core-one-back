package com.coreone.back.application;

import com.coreone.back.common.errors.ConflictException;
import com.coreone.back.domain.entity.Enterprise;
import com.coreone.back.domain.entity.enums.UserType;
import com.coreone.back.domain.service.EnterpriseDomainService;
import com.coreone.back.domain.service.UserDomainService;
import com.coreone.back.infrastructure.security.CustomUserDetails;
import com.coreone.back.infrastructure.security.JwtService;
import com.coreone.back.web.dto.enterprise.CreateEnterpriseRequestDTO;
import com.coreone.back.web.dto.enterprise.CreateEnterpriseResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnterpriseApplicationService {

    private final EnterpriseDomainService enterpriseDomainService;
    private final UserDomainService userDomainService;
    private final JwtService jwtService;

    public CreateEnterpriseResponseDTO createEnterprise(CreateEnterpriseRequestDTO request) {
        var owner = userDomainService.findById(request.getCreatorId());

        if (owner.getEnterprise() != null) {
            throw new ConflictException("User already has an enterprise");
        }

        Enterprise enterprise = Enterprise.builder()
                .name(request.getName())
                .description(request.getDescription())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();

        enterprise.getUsers().add(owner);
        owner.setEnterprise(enterprise);

        Enterprise savedEnterprise = enterpriseDomainService.save(enterprise);

        owner.setType(UserType.BOSS);
        CustomUserDetails customUserDetails = new CustomUserDetails(owner);
        String newToken = jwtService.generateToken(customUserDetails);


        return CreateEnterpriseResponseDTO.builder()
                .id(savedEnterprise.getId())
                .creatorId(owner.getId())
                .name(savedEnterprise.getName())
                .description(savedEnterprise.getDescription())
                .token(newToken)
                .build();
    }

    public Page<Enterprise> getAllEnterprises(Pageable pageable) {
        return enterpriseDomainService.findAll(pageable);
    }

    public Enterprise getEnterpriseById(UUID id) {
        return enterpriseDomainService.findById(id);
    }

}