package com.coreone.back.modules.payment.controller.dto;

public record AddCreditCardRequestDTO(
        String brand,
        String lastDigits,
        String holderName,
        Integer expirationMonth,
        Integer expirationYear
) {
}
