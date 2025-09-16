package com.coreone.back.repository;

import com.coreone.back.domain.TaskMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskMembersRepository extends JpaRepository<TaskMember, UUID> {
}
