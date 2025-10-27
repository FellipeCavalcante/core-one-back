package com.coreone.back.modules.payment.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.coreone.back.modules.user.domain.User;
import com.coreone.back.modules.plan.domain.Plan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @ManyToOne
    @JoinColumn(name = "credit_card_id")
    private CreditCard creditCard;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal value;

    @Column(length = 10)
    private String currency = "BRL";

    @Column(length = 30)
    private String status = "PENDING"; // PENDING, PAID, FAILED, etc.

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;
}
