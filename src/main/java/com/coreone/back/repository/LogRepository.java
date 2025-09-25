package com.coreone.back.repository;

import com.coreone.back.domain.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface LogRepository extends JpaRepository<Log, UUID> {
    @Query("SELECT l FROM Log l JOIN FETCH l.user ORDER BY l.createdAt DESC")
    Page<Log> findAllWithUser(Pageable pageable);
}
