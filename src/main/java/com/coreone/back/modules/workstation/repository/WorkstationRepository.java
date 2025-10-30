package com.coreone.back.modules.workstation.repository;

import com.coreone.back.modules.workstation.domain.Workstation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkstationRepository extends JpaRepository<Workstation, UUID> {
    int countByEnterpriseId(UUID enterpriseId);
}
