package com.coreone.back.modules.payment.domain;

import com.coreone.back.modules.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "credit_card")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(length = 50)
    private String brand; // Visa, MasterCard...

    @Column(name = "last_digits", length = 4, nullable = false)
    private String lastDigits;

    @Column(name = "holder_name", length = 100, nullable = false)
    private String holderName;

    @Column
    private String token; // Gateway token

    @Column(name = "expiration_mm", nullable = false)
    private Integer expirationMonth;

    @Column(name = "expiration_yy", nullable = false)
    private Integer expirationYear;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
