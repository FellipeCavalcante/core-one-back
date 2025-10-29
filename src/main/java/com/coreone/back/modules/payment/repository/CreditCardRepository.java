package com.coreone.back.modules.payment.repository;

import com.coreone.back.modules.payment.domain.CreditCard;
import com.coreone.back.modules.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CreditCardRepository extends JpaRepository<CreditCard, UUID> {
     List<CreditCard> findAllByUser(User user);
}
