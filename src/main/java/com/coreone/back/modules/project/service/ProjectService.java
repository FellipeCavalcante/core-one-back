package com.coreone.back.modules.project.service;

import com.coreone.back.common.errors.NotFoundException;
import com.coreone.back.common.errors.UnauthorizedException;
import com.coreone.back.modules.project.domain.Project;
import com.coreone.back.modules.project.domain.ProjectMember;
import com.coreone.back.modules.project.domain.ProjectSubSector;
import com.coreone.back.modules.project.dto.CreateProjectRequestDTO;
import com.coreone.back.modules.project.dto.ProjectResponseDTO;
import com.coreone.back.modules.project.dto.UpdateProjectRequest;
import com.coreone.back.modules.project.mapper.ProjectMapper;
import com.coreone.back.modules.project.repository.ProjectMemberRepository;
import com.coreone.back.modules.project.repository.ProjectRepository;
import com.coreone.back.modules.subSector.service.SubSectorService;
import com.coreone.back.modules.user.domain.User;
import com.coreone.back.modules.user.service.UserService;
import com.coreone.back.modules.workstation.service.WorkstationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository repository;
    private final ProjectMapper mapper;
    private final UserService userService;
    private final SubSectorService subSectorService;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectSubSectorService projectSubSectorService;
    private final WorkstationService workstationService;

    public ProjectResponseDTO save(User user, CreateProjectRequestDTO dto, UUID workstationId) {
        var workstation = workstationService.getWorkstationById(workstationId);

        var project = mapper.toDomain(dto);

        project.setWorkstation(workstation);

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

        return new ProjectResponseDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getStatus()
        );
    }

    public String addUser(User user, UUID projectId, UUID userId) {
        var userToAdd = userService.findById(userId);
        var project = findById(projectId);

        var pm = new ProjectMember();
        pm.setProject(project);
        pm.setUser(userToAdd);

        projectMemberRepository.save(pm);

        return "User added successfully";
    }

    public void update(User user, UUID id, UpdateProjectRequest request) {
        var project = findById(id);

        if (request.getName() != null) {
            project.setName(request.getName());
        }
        if (request.getDescription() != null) {
            project.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            project.setStatus(request.getStatus());
        }

        project.setUpdatedAt(LocalDateTime.now());

        repository.save(project);
    }

    public Project findById(UUID projectId) {
        return repository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found"));
    }

    public void deleteProject(User requestingUser, UUID id) {
        var project = findById(id);

        repository.delete(project);
    }

    public void addSubSectorToProject(UUID projectId, UUID subSectorId) {
        projectSubSectorService.assign(projectId, subSectorId);
    }
}
