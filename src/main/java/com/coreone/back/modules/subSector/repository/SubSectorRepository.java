package com.coreone.back.modules.subSector.repository;

import com.coreone.back.modules.subSector.domain.SubSector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubSectorRepository extends JpaRepository<SubSector, UUID> {

    List<SubSector> findAllBySectorId(UUID sectorId);
}
