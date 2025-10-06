package com.coreone.back.modules.task.repository;

import com.coreone.back.modules.task.domain.TaskMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskMembersRepository extends JpaRepository<TaskMember, UUID> {
}
