package com.coreone.back.modules.payment.controller;

import com.coreone.back.common.util.AuthUtil;
import com.coreone.back.modules.payment.controller.dto.AddCreditCardRequestDTO;
import com.coreone.back.modules.payment.service.CreditCardService;
import com.coreone.back.modules.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/credit-card")
@RequiredArgsConstructor
public class CreditCardController {
    private final CreditCardService service;
    private final AuthUtil authUtil;

    @PostMapping("/add")
    public ResponseEntity<Void> addCreditCard(@RequestBody AddCreditCardRequestDTO requestDTO) {
        User user = authUtil.getAuthenticatedUser();

        service.addCreditCard(requestDTO, user);

        return ResponseEntity.noContent().build();
    }
}
