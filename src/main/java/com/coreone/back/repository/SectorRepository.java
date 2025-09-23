package com.coreone.back.repository;

import com.coreone.back.domain.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SectorRepository extends JpaRepository<Sector, UUID> {
    Optional<Sector> findByName(String name);

    List<Sector> findAllByEnterpriseId(UUID enterpriseId);
}
