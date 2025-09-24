package com.coreone.back.service;

import com.coreone.back.domain.Sector;
import com.coreone.back.dto.sector.CreateSectorRequestDTO;
import com.coreone.back.dto.sector.CreateSectorResponseDTO;
import com.coreone.back.dto.sector.GetSectorResponse;
import com.coreone.back.errors.NotFoundException;
import com.coreone.back.errors.sectorlAlreadyExistsException;
import com.coreone.back.mapper.SectorMapper;
import com.coreone.back.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public List<GetSectorResponse> listAllEnterpriseSectors(UUID id) {
        var enterprise = enterpriseService.findById(id);

        var sectors = repository.findAllByEnterpriseId(enterprise.getId());

        List<GetSectorResponse> response = sectors.stream()
                .map(mapper::toGetSectorResponse)
                .collect(Collectors.toList());

        return response;
    }

    public Sector findById(UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("Sector not found!")
        );
    }

    public String delete(UUID id) {
        var sector = findById(id);

        repository.delete(sector);

        return "Sector " + id + " deleted";
    }
}
