package com.coreone.back.modules.workstation.service;

import com.coreone.back.common.errors.BadRequestException;
import com.coreone.back.common.errors.NotFoundException;
import com.coreone.back.modules.user.domain.User;
import com.coreone.back.modules.user.domain.UserEnterprise;
import com.coreone.back.modules.user.repository.UserEnterpriseRepository;
import com.coreone.back.modules.user.repository.UserPlanRepository;
import com.coreone.back.modules.workstation.controller.dto.CreateWorkstationRequestDTO;
import com.coreone.back.modules.workstation.controller.dto.CreateWorkstationResponseDTO;
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
    private final UserPlanRepository userPlanRepository;

    public CreateWorkstationResponseDTO createWorkstation(CreateWorkstationRequestDTO requestDTO, User user) {
        verifyWorkstations(user);

        UserEnterprise userEnterprise = userEnterpriseRepository.findByUser(user);

        var workstation = new Workstation();
        workstation.setName(requestDTO.name());
        workstation.setEnterprise(userEnterprise.getEnterprise());

        var workstationSaved = repository.save(workstation);

        return  new CreateWorkstationResponseDTO(
              workstationSaved.getId(),
                workstation.getName()
        );
    }

    private void verifyWorkstations(User user) {
        var currentUserPlan = userPlanRepository.findCurrentByUserId(user.getId())
                .orElseThrow(() -> new BadRequestException("User does not have an active plan"));

        if (!"ACTIVE".equals(currentUserPlan.getStatus())) {
            throw new BadRequestException("User does not have an active plan");
        }

        int maxWorkstations = currentUserPlan.getPlan().getWorkstationQtd();

        var userEnterprise = userEnterpriseRepository.findByUser(user);

        int currentWorkstations = repository.countByEnterpriseId(userEnterprise.getEnterprise().getId());

        if (currentWorkstations >= maxWorkstations) {
            throw new BadRequestException("You have reached the maximum number of workstations for your plan");
        }
    }

    public void delete(UUID id) {
        var workstation = getWorkstationById(id);

        repository.delete(workstation);
    }

    public Workstation getWorkstationById(UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("Workstation id " + id + " not found")
        );
    }
}
