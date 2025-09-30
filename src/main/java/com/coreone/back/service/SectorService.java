package com.coreone.back.service;

import com.coreone.back.domain.Sector;
import com.coreone.back.dto.sector.CreateSectorRequestDTO;
import com.coreone.back.dto.sector.CreateSectorResponseDTO;
import com.coreone.back.dto.sector.GetSectorResponse;
import com.coreone.back.errors.NotFoundException;
import com.coreone.back.errors.UnauthorizedException;
import com.coreone.back.errors.sectorlAlreadyExistsException;
import com.coreone.back.mapper.SectorMapper;
import com.coreone.back.repository.SectorRepository;
import com.coreone.back.repository.UserRepository;
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
    private final UserRepository userRepository;

    public CreateSectorResponseDTO save(CreateSectorRequestDTO sector, UUID userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

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

    public Page<GetSectorResponse> listAllEnterpriseSectors(UUID id, int page, int size) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        var enterprise = enterpriseService.findById(user.getEnterprise().getId());

        Pageable pageable = PageRequest.of(page, size);

        var sectors = repository.findAllByEnterpriseId(enterprise.getId(), pageable);

        Page<GetSectorResponse> response = sectors.map(mapper::toGetSectorResponse);

        return response;
    }

    public Sector findById(UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("Sector not found!")
        );
    }

    public String delete(UUID id, UUID userId) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!user.getEnterprise().getId().equals(userId)) {
            throw new UnauthorizedException("Unauthorized request");
        }

        var sector = findById(id);

        repository.delete(sector);

        return "Sector " + id + " deleted";
    }
}
