package com.coreone.back.repository;

import com.coreone.back.domain.TaskSubSector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskSubSectorRepository extends JpaRepository<TaskSubSector, UUID> {
}
