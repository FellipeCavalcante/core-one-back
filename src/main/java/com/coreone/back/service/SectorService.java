package com.coreone.back.service;

import com.coreone.back.domain.Sector;
import com.coreone.back.errors.sectorlAlreadyExistsException;
import com.coreone.back.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectorService {
    private final SectorRepository repository;

    public Sector save(Sector sector) {
        assertSectorDosNotExists(sector.getName());
        sector.setName(sector.getName().toUpperCase());
        return repository.save(sector);
    }

    public void assertSectorDosNotExists(String name) {
        repository.findByName(name.toUpperCase())
                .ifPresent(existedSector -> {
                    throw new sectorlAlreadyExistsException("Sector " + name + " already exists");
                });
    }
}
