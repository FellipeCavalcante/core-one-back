package com.coreone.back.repository;

import com.coreone.back.domain.SubSector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubSectorRepository extends JpaRepository<SubSector, UUID> {

}
