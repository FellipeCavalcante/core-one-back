package com.coreone.back.service;

import com.coreone.back.domain.Enterprise;
import com.coreone.back.domain.enums.UserType;
import com.coreone.back.dto.enterprise.CreateEnterpriseRequestDTO;
import com.coreone.back.dto.enterprise.CreateEnterpriseResponseDTO;
import com.coreone.back.repository.EnterpriseRepository;
import com.coreone.back.repository.UserRepository;
import com.coreone.back.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnterpriseService {

    private final EnterpriseRepository repository;
    private final UserRepository userRepository;
    private final LogService logService;
    private final JwtService jwtService;

    public CreateEnterpriseResponseDTO save(CreateEnterpriseRequestDTO request) {
        var owner = userRepository.findById(request.getCreatorId())
                .orElseThrow(() -> new RuntimeException("User not found"));

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

    public List<Enterprise> getAll() {
        return repository.findAll();
    }
}
