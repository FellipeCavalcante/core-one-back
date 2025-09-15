package com.findjobs.back.repository;

import com.findjobs.back.domain.SubSector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubSectorRepository extends JpaRepository<SubSector, UUID> {

}
