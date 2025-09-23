package com.coreone.back.service;

import com.coreone.back.domain.SubSector;
import com.coreone.back.dto.subSector.GetSubSectorResponse;
import com.coreone.back.errors.NotFoundException;
import com.coreone.back.mapper.SubSectorMapper;
import com.coreone.back.repository.SubSectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubSectorService {

    private final SubSectorRepository repository;
    private final SubSectorMapper mapper;
    private final SectorService sectorService;
    private final UserService userService;

    public SubSector findById(UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("Sub Sector not found")
        );
    }

    public SubSector save(SubSector subSector) {
        return repository.save(subSector);
    }

    public List<GetSubSectorResponse> listAllSectorsSubSectors(UUID id) {
        var sector = sectorService.findById(id);

        var subSectors = repository.findAllBySectorId(sector.getId());

        List<GetSubSectorResponse> response = subSectors.stream()
                .map(mapper::toGetSubSectorResponse)
                .collect(Collectors.toList());

        return response;
    }

    public String addUserToSubSector(UUID subSectorId, UUID userId) {
        var subSector = findById(subSectorId);

        var user = userService.findById(userId);

        user.setSubSector(subSector);

        return "Usuário " + user.getUsername() + " cadastrado com sucesso!";
    }
}