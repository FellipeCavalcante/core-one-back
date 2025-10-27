package com.coreone.back.modules.plan.controller.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record GetPlanResponseDTO(
        UUID id,
        String name,
        BigDecimal value,
        Integer workstation,
        String description
) {
}
