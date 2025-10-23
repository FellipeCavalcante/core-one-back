package com.coreone.back.infrastructure.persistence;

import com.coreone.back.domain.entity.Enterprise;
import com.coreone.back.domain.repository.EnterpriseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaEnterpriseRepository extends JpaRepository<Enterprise, UUID>, EnterpriseRepository {
}
