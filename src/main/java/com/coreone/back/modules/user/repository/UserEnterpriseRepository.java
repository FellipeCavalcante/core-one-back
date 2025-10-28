package com.coreone.back.modules.user.repository;

import com.coreone.back.modules.user.domain.User;
import com.coreone.back.modules.user.domain.UserEnterprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserEnterpriseRepository extends JpaRepository<UserEnterprise, UUID> {
    UserEnterprise findByUser(User user);

    int countByUserId(UUID userId);

    boolean existsByUserIdAndEnterpriseId(UUID id, UUID id1);
}
