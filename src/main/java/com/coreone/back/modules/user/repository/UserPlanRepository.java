package com.coreone.back.modules.user.repository;

import com.coreone.back.modules.user.domain.UserPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserPlanRepository extends JpaRepository<UserPlan, UUID> {
    @Query("SELECT up FROM UserPlan up WHERE up.user.id = :userId AND up.isCurrent = true")
    Optional<UserPlan> findCurrentPlan(@Param("userId") UUID userId);
}
