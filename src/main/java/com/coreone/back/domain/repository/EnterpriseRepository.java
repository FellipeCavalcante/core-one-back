package com.coreone.back.domain.repository;

import com.coreone.back.domain.entity.Enterprise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface EnterpriseRepository {
    Optional<Enterprise> findById(UUID id);
    Enterprise save(Enterprise enterprise);
    Page<Enterprise> findAll(Pageable pageable);
}
