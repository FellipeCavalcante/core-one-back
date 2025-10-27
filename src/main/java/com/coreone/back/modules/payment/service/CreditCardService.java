package com.coreone.back.modules.payment.service;

import com.coreone.back.modules.payment.controller.dto.AddCreditCardRequestDTO;
import com.coreone.back.modules.payment.domain.CreditCard;
import com.coreone.back.modules.payment.repository.CreditCardRepository;
import com.coreone.back.modules.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditCardService {
    private final CreditCardRepository repository;

    public void addCreditCard(AddCreditCardRequestDTO requestDTO, User user) {
        CreditCard creditCard = new CreditCard();
        creditCard.setBrand(requestDTO.brand());
        creditCard.setLastDigits(requestDTO.lastDigits());
        creditCard.setHolderName(requestDTO.holderName());
        creditCard.setToken("c96b1f0a-7255-47dd-adee-6ff920ecce36"); // TO DO
        creditCard.setExpirationMonth(requestDTO.expirationMonth());
        creditCard.setExpirationYear(requestDTO.expirationYear());
        creditCard.setUser(user);

        repository.save(creditCard);
    }
}
