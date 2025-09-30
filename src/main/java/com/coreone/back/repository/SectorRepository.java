package com.coreone.back.repository;

import com.coreone.back.domain.Sector;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SectorRepository extends JpaRepository<Sector, UUID> {
    Optional<Sector> findByName(String name);


    Page<Sector> findAllByEnterpriseId(UUID enterpriseId, Pageable pageable);
}
