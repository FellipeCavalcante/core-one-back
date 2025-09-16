package com.coreone.back.service;

import com.coreone.back.domain.SubSector;
import com.coreone.back.repository.SubSectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubSectorService {

    private final SubSectorRepository subSectorRepository;

    public Optional<SubSector> findById(UUID id) {
        return subSectorRepository.findById(id);
    }

    public SubSector save(SubSector subSector) {
        return subSectorRepository.save(subSector);
    }
}