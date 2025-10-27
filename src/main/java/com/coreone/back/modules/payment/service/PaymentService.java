package com.coreone.back.modules.payment.service;

import com.coreone.back.modules.payment.domain.Payment;
import com.coreone.back.modules.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository repository;

    public void save(Payment payment) {
        repository.save(payment);
    }
}
