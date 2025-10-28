package com.coreone.back.modules.user.repository;

import com.coreone.back.modules.user.domain.UserPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserPlanRepository extends JpaRepository<UserPlan, UUID> {
}
