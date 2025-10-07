package com.coreone.back.modules.project.repository;

import com.coreone.back.modules.project.domain.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, UUID> {
}
