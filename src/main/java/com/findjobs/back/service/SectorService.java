package com.findjobs.back.service;

import com.findjobs.back.domain.Sector;
import com.findjobs.back.errors.sectorlAlreadyExistsException;
import com.findjobs.back.repository.SectorRepository;
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
