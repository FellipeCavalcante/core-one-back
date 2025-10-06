package com.coreone.back.modules.task.repository;

import com.coreone.back.modules.task.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    @Query("""
    SELECT DISTINCT t
    FROM Task t
    JOIN t.subSectors ts
    JOIN ts.subSector ss
    JOIN ss.sector s
    WHERE s.enterprise.id = :enterpriseId
""")
    Page<Task> findAllByEnterpriseIdFromSubSectors(UUID enterpriseId, Pageable pageable);

}
