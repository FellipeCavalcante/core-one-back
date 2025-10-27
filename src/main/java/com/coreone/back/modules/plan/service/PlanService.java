package com.coreone.back.modules.plan.service;

import com.coreone.back.modules.plan.controller.dto.GetPlanResponseDTO;
import com.coreone.back.modules.plan.domain.Plan;
import com.coreone.back.modules.plan.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository repository;

    public List<GetPlanResponseDTO> listAll() {
        List<Plan> plans = repository.findAll();

        return plans.stream().map(plan -> new GetPlanResponseDTO(
                plan.getId(),
                plan.getName(),
                plan.getValue(),
                plan.getWorkstationQtd(),
                plan.getDescription()
        )).toList();
    }
}
