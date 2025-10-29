package com.coreone.back.modules.payment.controller;

import com.coreone.back.common.util.AuthUtil;
import com.coreone.back.modules.payment.controller.dto.AddCreditCardRequestDTO;
import com.coreone.back.modules.payment.controller.dto.GetCardResponseDTO;
import com.coreone.back.modules.payment.domain.CreditCard;
import com.coreone.back.modules.payment.service.CreditCardService;
import com.coreone.back.modules.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/my-cards")
    public ResponseEntity<List<GetCardResponseDTO>> listMyCreditCards() {
        User user = authUtil.getAuthenticatedUser();

        var response = service.getMyCreditCards(user);

        return ResponseEntity.ok(response);
    }
}
