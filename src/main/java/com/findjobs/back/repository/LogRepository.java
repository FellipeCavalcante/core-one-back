package com.findjobs.back.repository;

import com.findjobs.back.domain.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface LogRepository extends JpaRepository<Log, UUID> {
    @Query("SELECT l FROM Log l JOIN FETCH l.user ORDER BY l.createdAt DESC")
    List<Log> findAllWithUser();
}
