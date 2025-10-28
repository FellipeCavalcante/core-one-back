package com.coreone.back.modules.payment.controller;

import com.coreone.back.common.util.AuthUtil;
import com.coreone.back.modules.payment.service.PaymentService;
import com.coreone.back.modules.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService service;
    private final AuthUtil authUtil;

    @PostMapping("/{paymentId}/pay")
    public ResponseEntity<?> pay(@PathVariable UUID paymentId,
                                 @RequestHeader("Authorization") String token) {

        User user = authUtil.getAuthenticatedUser();

        service.payRequest(paymentId, user, token);

        return ResponseEntity.ok("Payment processed successfully!");
    }
}
