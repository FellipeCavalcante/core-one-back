package com.coreone.back.modules.payment.repository;

import com.coreone.back.modules.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
