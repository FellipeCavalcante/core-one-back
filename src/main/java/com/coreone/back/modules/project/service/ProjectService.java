package com.coreone.back.modules.project.service;

import com.coreone.back.modules.project.domain.Project;
import com.coreone.back.modules.project.domain.ProjectMember;
import com.coreone.back.modules.project.domain.ProjectSubSector;
import com.coreone.back.modules.project.dto.CreateProjectRequestDTO;
import com.coreone.back.modules.project.dto.ProjectResponseDTO;
import com.coreone.back.modules.project.mapper.ProjectMapper;
import com.coreone.back.modules.project.repository.ProjectRepository;
import com.coreone.back.modules.subSector.service.SubSectorService;
import com.coreone.back.modules.user.domain.User;
import com.coreone.back.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository repository;
    private final ProjectMapper mapper;
    private final UserService  userService;
    private final SubSectorService subSectorService;

    public ProjectResponseDTO save(User user, CreateProjectRequestDTO dto) {
        var project = mapper.toDomain(dto);
        project.setEnterprise(user.getEnterprise());

        if (dto.getSubSectors() != null) {
            dto.getSubSectors().forEach(subSectorId -> {
                var subSector = subSectorService.findById(subSectorId);
                project.getSubSectors().add(new ProjectSubSector(null, project, subSector));
            });
        }

        if (dto.getMembers() != null) {
            dto.getMembers().forEach(userId -> {
                var memberUser = userService.findById(userId);
                project.getMembers().add(new ProjectMember(null, project, memberUser));
            });
        }

        repository.save(project);

        var response = new ProjectResponseDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getStatus()
        );

        return response;
    }
}
