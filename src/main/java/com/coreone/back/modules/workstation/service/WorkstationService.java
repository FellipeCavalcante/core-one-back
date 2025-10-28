package com.coreone.back.modules.workstation.service;

import com.coreone.back.common.errors.BadRequestException;
import com.coreone.back.common.errors.NotFoundException;
import com.coreone.back.modules.user.domain.User;
import com.coreone.back.modules.user.domain.UserEnterprise;
import com.coreone.back.modules.user.repository.UserEnterpriseRepository;
import com.coreone.back.modules.workstation.controller.dto.CreateWorkstationRequestDTO;
import com.coreone.back.modules.workstation.domain.Workstation;
import com.coreone.back.modules.workstation.repository.WorkstationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkstationService {
    private final WorkstationRepository repository;
    private final UserEnterpriseRepository  userEnterpriseRepository;

    public Workstation createWorkstation(CreateWorkstationRequestDTO requestDTO, User user) {
        verifyWorkstations(user);

        UserEnterprise userEnterprise = userEnterpriseRepository.findByUser(user);

        var workstation = new Workstation();
        workstation.setName(requestDTO.name());
        workstation.setEnterprise(userEnterprise.getEnterprise());

        return repository.save(workstation);
    }

    public void verifyWorkstations(User user) {
        var currentUserPlan = user.getCurrentUserPlan();

        if (currentUserPlan == null || !"ACTIVE".equals(currentUserPlan.getStatus())) {
            throw new BadRequestException("User does not have an active plan");
        }

        int maxWorkstations = currentUserPlan.getPlan().getWorkstationQtd();

        int currentWorkstations = userEnterpriseRepository.countByUserId(user.getId());

        if (currentWorkstations >= maxWorkstations) {
            throw new BadRequestException("You have reached the maximum number of workstations for your plan");
        }
    }

    public Workstation getWorkstationById(UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("Workstation id " + id + " not found")
        );
    }
}
