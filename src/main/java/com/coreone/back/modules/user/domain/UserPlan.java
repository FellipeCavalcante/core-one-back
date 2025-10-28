package com.coreone.back.modules.user.domain;

import com.coreone.back.modules.plan.domain.Plan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_plan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    private String status;

    @Column(name = "is_current")
    private Boolean isCurrent = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}