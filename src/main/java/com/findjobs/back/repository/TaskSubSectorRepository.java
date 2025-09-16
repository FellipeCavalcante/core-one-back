package com.findjobs.back.repository;

import com.findjobs.back.domain.TaskSubSector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskSubSectorRepository extends JpaRepository<TaskSubSector, UUID> {
}
