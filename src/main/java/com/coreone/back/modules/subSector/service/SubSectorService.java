package com.coreone.back.modules.subSector.service;

import com.coreone.back.common.errors.NotFoundException;
import com.coreone.back.common.errors.UnauthorizedException;
import com.coreone.back.modules.project.service.ProjectSubSectorService;
import com.coreone.back.modules.sector.service.SectorService;
import com.coreone.back.modules.subSector.domain.SubSector;
import com.coreone.back.modules.subSector.domain.SubSectorUser;
import com.coreone.back.modules.subSector.dto.CreateSubSectorRequestDTO;
import com.coreone.back.modules.subSector.dto.CreateSubSectorResponseDTO;
import com.coreone.back.modules.subSector.dto.GetSubSectorResponse;
import com.coreone.back.modules.subSector.dto.UpdateSubSectorRequest;
import com.coreone.back.modules.subSector.mapper.SubSectorMapper;
import com.coreone.back.modules.subSector.repository.SubSectorRepository;
import com.coreone.back.modules.subSector.repository.SubSectorUserRepository;
import com.coreone.back.modules.user.domain.User;
import com.coreone.back.modules.user.domain.enums.UserType;
import com.coreone.back.modules.user.repository.UserEnterpriseRepository;
import com.coreone.back.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubSectorService {

    private final SubSectorRepository repository;
    private final SubSectorMapper mapper;
    private final SectorService sectorService;
    private final UserService userService;
    private final ProjectSubSectorService projectSubSectorService;
    private final UserEnterpriseRepository userEnterpriseRepository;
    private final SubSectorUserRepository subSectorUserRepository;

    public SubSector findById(UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("Sub Sector not found")
        );
    }

    @Transactional
    public CreateSubSectorResponseDTO save(User user, CreateSubSectorRequestDTO request) {
        var sector = sectorService.findById(request.getSector());

        if (user.getType() != UserType.ADMIN) {

            boolean belongsToEnterprise = userEnterpriseRepository.existsByUserIdAndEnterpriseId(
                    user.getId(),
                    sector.getWorkstation().getEnterprise().getId()
            );

            if (!belongsToEnterprise) {
                throw new UnauthorizedException("Unauthorized Access");
            }
        }

        var subSector = mapper.toSubSector(request);

        var subSectorSaved = repository.save(subSector);

        if (request.getProjectId() != null) {
            projectSubSectorService.assign(request.getProjectId(), subSectorSaved);
        }

        return mapper.toCreateSubSectorResponseDTO(subSectorSaved);
    }

    public List<GetSubSectorResponse> listAllSectorsSubSectors(UUID id) {
        var sector = sectorService.findById(id);

        var subSectors = repository.findAllBySectorId(sector.getId());

        return subSectors.stream()
                .map(mapper::toGetSubSectorResponse)
                .collect(Collectors.toList());
    }

    public String addUserToSubSector(UUID subSectorId, UUID userId) {
        var subSector = findById(subSectorId);

        var user = userService.findById(userId);

        SubSectorUser ssu = new SubSectorUser();
        ssu.setSubSector(subSector);
        ssu.setUser(user);

        subSectorUserRepository.save(ssu);

        return "User " + user.getUsername() + " added to sub sector!";
    }

    public void update(UUID id, UpdateSubSectorRequest request) {
        var subSector = findById(id);

        if (request.getName() != null) {
            subSector.setName(request.getName());
        }

        repository.save(subSector);
    }

    public String delete(UUID id) {
        var subSector = findById(id);

        repository.deleteById(subSector.getId());

        return "SubSector " + subSector.getId() + " deleted!";
    }

    public String removeUser(UUID subSectorId, UUID userId) {
        var subSector = findById(subSectorId);
        var user = userService.findById(userId);

        subSectorUserRepository.removeSubSectorUserByUser(user);

        return "User removed";
    }
}