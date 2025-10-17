package com.coreone.back.modules.sector.service;

import com.coreone.back.common.errors.NotFoundException;
import com.coreone.back.common.errors.UnauthorizedException;
import com.coreone.back.common.errors.sectorlAlreadyExistsException;
import com.coreone.back.modules.enterprise.service.EnterpriseService;
import com.coreone.back.modules.sector.domain.Sector;
import com.coreone.back.modules.sector.dto.CreateSectorRequestDTO;
import com.coreone.back.modules.sector.dto.CreateSectorResponseDTO;
import com.coreone.back.modules.sector.dto.GetSectorResponse;
import com.coreone.back.modules.sector.dto.UpdateSectorRequest;
import com.coreone.back.modules.sector.mapper.SectorMapper;
import com.coreone.back.modules.sector.repository.SectorRepository;
import com.coreone.back.modules.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SectorService {
    private final SectorRepository repository;
    private final SectorMapper mapper;
    private final EnterpriseService enterpriseService;

    public CreateSectorResponseDTO save(CreateSectorRequestDTO sector, User user) {
        assertSectorDosNotExists(sector.getName());

        var enterprise = enterpriseService.findById(user.getEnterprise().getId());

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

    public Page<GetSectorResponse> listAllEnterpriseSectors(User user, int page, int size) {

        var enterprise = enterpriseService.findById(user.getEnterprise().getId());

        Pageable pageable = PageRequest.of(page, size);

        var sectors = repository.findAllByEnterpriseId(enterprise.getId(), pageable);

        return sectors.map(mapper::toGetSectorResponse);
    }

    public GetSectorResponse getSectorById(UUID id) {
        var sector = findById(id);

        return mapper.toGetSectorResponse(sector);
    }

    public Sector findById(UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("Sector not found!")
        );
    }

    public void update(User user, UUID sectorId, UpdateSectorRequest request) {
        var sector = findById(sectorId);

        if (!user.getEnterprise().getId().equals(sector.getEnterprise().getId())) {
            throw new UnauthorizedException("Unauthorized Access");
        }

        if (request.getName() != null) {
            sector.setName(request.getName());
        }

        repository.save(sector);
    }

    public String delete(UUID id, User user) {

        if (!user.getEnterprise().getId().equals(user.getId())) {
            throw new UnauthorizedException("Unauthorized request");
        }

        var sector = findById(id);

        repository.delete(sector);

        return "Sector " + id + " deleted";
    }
}
