package com.coreone.back.modules.plan.controller.dto;

import java.util.UUID;

public record SelectPlanRequestDTO(
        UUID planId,
        UUID creditCardId
) {
}
