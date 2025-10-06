package com.coreone.back.modules.task.repository;

import com.coreone.back.modules.task.domain.TaskSubSector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskSubSectorRepository extends JpaRepository<TaskSubSector, UUID> {
}
