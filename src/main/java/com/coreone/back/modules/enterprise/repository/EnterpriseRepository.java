package com.coreone.back.modules.enterprise.repository;

import com.coreone.back.modules.enterprise.domain.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnterpriseRepository extends JpaRepository<Enterprise, UUID> {
}
