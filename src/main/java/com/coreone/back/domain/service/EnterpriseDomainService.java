package com.coreone.back.domain.service;

import com.coreone.back.common.errors.NotFoundException;
import com.coreone.back.domain.entity.Enterprise;
import com.coreone.back.domain.repository.EnterpriseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnterpriseDomainService {

    private final EnterpriseRepository repository;

    public Enterprise save(Enterprise enterprise) {
        return repository.save(enterprise);
    }

    public Enterprise findById(UUID id){
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Enterprise not found"));
    }

    public Page<Enterprise> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
