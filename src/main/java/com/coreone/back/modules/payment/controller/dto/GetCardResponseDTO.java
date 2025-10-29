package com.coreone.back.modules.payment.controller.dto;

import java.util.UUID;

public record GetCardResponseDTO(
        UUID id,
        String brand,
        String lastDigits,
        String holderName,
        String token,
        Integer expirationMonth,
        Integer expirationYear,
        UUID userId
) {
}
