package com.coreone.back.service;

import com.coreone.back.domain.Sector;
import com.coreone.back.dto.sector.CreateSectorRequestDTO;
import com.coreone.back.dto.sector.CreateSectorResponseDTO;
import com.coreone.back.errors.sectorlAlreadyExistsException;
import com.coreone.back.mapper.SectorMapper;
import com.coreone.back.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectorService {
    private final SectorRepository repository;
    private final SectorMapper mapper;
    private final EnterpriseService enterpriseService;

    public CreateSectorResponseDTO save(CreateSectorRequestDTO sector) {
        assertSectorDosNotExists(sector.getName());

        var enterprise = enterpriseService.findById(sector.getEnterprise());

        sector.setName(sector.getName().toUpperCase());

        var sectorMapped = mapper.toSector(sector);
        sectorMapped.setEnterprise(enterprise);

        var sectorSaved = repository.save(sectorMapped);

        return mapper.toCreateSectorResponseDTO(sectorSaved);
    }

    public void assertSectorDosNotExists(String name) {
        repository.findByName(name.toUpperCase())
                .ifPresent(existedSector -> {
                    throw new sectorlAlreadyExistsException("Sector " + name + " already exists");
                });
    }
}
