package com.findjobs.back.service;

import com.findjobs.back.domain.SubSector;
import com.findjobs.back.repository.SubSectorRepository;
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