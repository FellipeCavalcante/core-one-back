package com.coreone.back.modules.project.service;

import com.coreone.back.common.errors.NotFoundException;
import com.coreone.back.modules.project.domain.Project;
import com.coreone.back.modules.project.domain.ProjectSubSector;
import com.coreone.back.modules.project.repository.ProjectRepository;
import com.coreone.back.modules.project.repository.ProjectSubSectorRepository;
import com.coreone.back.modules.subSector.domain.SubSector;
import com.coreone.back.modules.subSector.repository.SubSectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectSubSectorService {

    private final ProjectRepository projectRepository;
    private final SubSectorRepository subSectorRepository;
    private final ProjectSubSectorRepository projectSubSectorRepository;

    @Transactional
    public void assign(UUID projectId, SubSector subSector) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found"));

        ProjectSubSector pss = new ProjectSubSector();
        pss.setProject(project);
        pss.setSubSector(subSector);
        projectSubSectorRepository.save(pss);
    }

    @Transactional
    public void assign(UUID projectId, UUID subSectorId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found"));

        SubSector subSector = subSectorRepository.findById(subSectorId)
                .orElseThrow(() -> new NotFoundException("SubSector not found"));

        ProjectSubSector pss = new ProjectSubSector();
        pss.setProject(project);
        pss.setSubSector(subSector);
        projectSubSectorRepository.save(pss);
    }
}