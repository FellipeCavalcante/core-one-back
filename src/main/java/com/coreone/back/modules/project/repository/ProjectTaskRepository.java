package com.coreone.back.modules.project.repository;

import com.coreone.back.modules.project.domain.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectTaskRepository extends JpaRepository<ProjectTask, UUID> {
}
