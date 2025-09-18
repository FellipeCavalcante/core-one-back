package com.coreone.back.repository;

import com.coreone.back.domain.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnterpriseRepository extends JpaRepository<Enterprise, UUID> {
}
