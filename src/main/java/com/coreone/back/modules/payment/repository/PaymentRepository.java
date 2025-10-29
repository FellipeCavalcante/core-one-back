package com.coreone.back.modules.payment.repository;

import com.coreone.back.modules.payment.domain.Payment;
import com.coreone.back.modules.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findAllByUser(User user);
}
