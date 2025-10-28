package com.coreone.back.modules.sector.service;

import com.coreone.back.common.errors.NotFoundException;
import com.coreone.back.common.errors.UnauthorizedException;
import com.coreone.back.common.errors.sectorlAlreadyExistsException;
import com.coreone.back.modules.sector.domain.Sector;
import com.coreone.back.modules.sector.dto.CreateSectorRequestDTO;
import com.coreone.back.modules.sector.dto.CreateSectorResponseDTO;
import com.coreone.back.modules.sector.dto.GetSectorResponse;
import com.coreone.back.modules.sector.dto.UpdateSectorRequest;
import com.coreone.back.modules.sector.mapper.SectorMapper;
import com.coreone.back.modules.sector.repository.SectorRepository;
import com.coreone.back.modules.user.domain.User;
import com.coreone.back.modules.user.domain.enums.UserType;
import com.coreone.back.modules.user.repository.UserEnterpriseRepository;
import com.coreone.back.modules.workstation.service.WorkstationService;
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
    private final WorkstationService workstationService;
    private final UserEnterpriseRepository userEnterpriseRepository;

    public CreateSectorResponseDTO save(CreateSectorRequestDTO sector, UUID workstationId) {
        assertSectorDosNotExists(sector.getName());

        var works = workstationService.getWorkstationById(workstationId);

        sector.setName(sector.getName().toUpperCase());

        var sectorMapped = mapper.toSector(sector);
        sectorMapped.setWorkstation(works);

        var sectorSaved = repository.save(sectorMapped);

        return mapper.toCreateSectorResponseDTO(sectorSaved);
    }

    public void assertSectorDosNotExists(String name) {
        repository.findByName(name.toUpperCase())
                .ifPresent(existedSector -> {
                    throw new sectorlAlreadyExistsException("Sector " + name + " already exists");
                });
    }

    public Page<GetSectorResponse> listAllWorkstationSectors(UUID workstationId, int page, int size) {

        var workstation = workstationService.getWorkstationById(workstationId);

        Pageable pageable = PageRequest.of(page, size);

        var sectors = repository.findAllByWorkstationId(workstation.getId(), pageable);

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

        if (user.getType() != UserType.ADMIN) {

            boolean belongsToEnterprise = userEnterpriseRepository.existsByUserIdAndEnterpriseId(
                    user.getId(),
                    sector.getWorkstation().getEnterprise().getId()
            );

            if (!belongsToEnterprise) {
                throw new UnauthorizedException("Unauthorized Access");
            }
        }

        if (request.getName() != null) {
            sector.setName(request.getName());
        }

        repository.save(sector);
    }

    public String delete(UUID id, User user) {
        var sector = findById(id);

        if (user.getType() != UserType.ADMIN) {

            boolean belongsToEnterprise = userEnterpriseRepository.existsByUserIdAndEnterpriseId(
                    user.getId(),
                    sector.getWorkstation().getEnterprise().getId()
            );

            if (!belongsToEnterprise) {
                throw new UnauthorizedException("Unauthorized request");
            }
        }

        repository.delete(sector);

        return "Sector " + id + " deleted";
    }
}
