package com.coreone.back.modules.enterprise.service;

import com.coreone.back.modules.enterprise.domain.Enterprise;
import com.coreone.back.modules.user.domain.enums.UserType;
import com.coreone.back.modules.enterprise.dto.CreateEnterpriseRequestDTO;
import com.coreone.back.modules.enterprise.dto.CreateEnterpriseResponseDTO;
import com.coreone.back.modules.enterprise.dto.UpdateEnterpriseRequestDTO;
import com.coreone.back.modules.enterprise.dto.UpdateEnterpriseResponseDTO;
import com.coreone.back.common.errors.ConflictException;
import com.coreone.back.common.errors.NotFoundException;
import com.coreone.back.common.errors.UnauthorizedException;
import com.coreone.back.modules.enterprise.mapper.EnterpriseMapper;
import com.coreone.back.modules.enterprise.repository.EnterpriseRepository;
import com.coreone.back.modules.user.repository.UserRepository;
import com.coreone.back.security.JwtService;
import com.coreone.back.modules.log.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnterpriseService {

    private final EnterpriseRepository repository;
    private final EnterpriseMapper mapper;
    private final UserRepository userRepository;
    private final LogService logService;
    private final JwtService jwtService;

    public CreateEnterpriseResponseDTO save(CreateEnterpriseRequestDTO request) {
        var owner = userRepository.findById(request.getCreatorId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (owner.getEnterprise() != null) {
            throw new ConflictException("User has been created enterprise");
        }

        Enterprise enterprise = new Enterprise();
        enterprise.setName(request.getName());
        enterprise.setDescription(request.getDescription());
        enterprise.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        enterprise.getUsers().add(owner);

        owner.setEnterprise(enterprise);
        owner.setType(UserType.ADMIN);

        enterprise = repository.save(enterprise);
        userRepository.save(owner);

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(owner.getEmail())
                .password(owner.getPassword())
                .authorities(owner.getAuthorities())
                .build();

        String newToken = jwtService.generateToken(userDetails);

        var newValue = "title: CREATE ENTERPRISE " + request.getName() + '\n' +
                "Enterprise description: " + request.getDescription() + ".";
        logService.create(owner, "CREATE_ENTERPRISE", "ENTERPRISE", enterprise.getId(), newValue);

        CreateEnterpriseResponseDTO response = new CreateEnterpriseResponseDTO();
        response.setId(enterprise.getId());
        response.setCreatorId(owner.getId());
        response.setName(enterprise.getName());
        response.setDescription(enterprise.getDescription());
        response.setToken(newToken);

        return response;
    }

    public Page<Enterprise> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

    public UpdateEnterpriseResponseDTO update(UpdateEnterpriseRequestDTO request, UUID userId) {
        var enterprise = findById(request.getId());

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!user.getEnterprise().getId().equals(enterprise.getId())) {
            throw new UnauthorizedException("Unauthorized request");
        }

        enterprise.setName(request.getName());
        enterprise.setDescription(request.getDescription());

        var enterpriseUpdated = repository.save(enterprise);

        return mapper.toUpdateResponse(enterpriseUpdated);
    }

    public Enterprise findById(UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("Enterprise not found")
        );
    }
}
