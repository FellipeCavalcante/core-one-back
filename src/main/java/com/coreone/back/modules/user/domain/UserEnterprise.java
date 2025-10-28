package com.coreone.back.modules.user.domain;

import com.coreone.back.modules.enterprise.domain.Enterprise;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "user_enterprise")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEnterprise {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "enterprise_id", nullable = false)
    private Enterprise enterprise;

    private String role = "MEMBER";

    @Column(name = "joined_at", updatable = false)
    private Timestamp joinedAt;

    @PrePersist
    protected void onCreate() {
        joinedAt = new Timestamp(System.currentTimeMillis());
    }
}
