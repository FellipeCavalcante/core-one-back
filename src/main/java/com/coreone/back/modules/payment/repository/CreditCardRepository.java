package com.coreone.back.modules.payment.repository;

import com.coreone.back.modules.payment.domain.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CreditCardRepository extends JpaRepository<CreditCard, UUID> {
}
