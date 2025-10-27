package com.coreone.back.modules.plan.repository;

import com.coreone.back.modules.plan.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlanRepository extends JpaRepository<Plan, UUID> {
}
