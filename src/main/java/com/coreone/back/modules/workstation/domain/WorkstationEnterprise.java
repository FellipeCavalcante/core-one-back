package com.coreone.back.modules.workstation.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import com.coreone.back.modules.enterprise.domain.Enterprise;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "workstation_enterprise")
@Getter
@Setter
public class WorkstationEnterprise {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "workstation_id", nullable = false)
    private Workstation workstation;

    @ManyToOne
    @JoinColumn(name = "enterprise_id", nullable = false)
    private Enterprise enterprise;

    @Column(name = "activated_at")
    private LocalDateTime activatedAt = LocalDateTime.now();
}
