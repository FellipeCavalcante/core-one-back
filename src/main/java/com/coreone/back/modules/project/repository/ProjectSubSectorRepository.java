package com.coreone.back.modules.project.repository;

import com.coreone.back.modules.project.domain.ProjectSubSector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectSubSectorRepository extends JpaRepository<ProjectSubSector, UUID> {
}
