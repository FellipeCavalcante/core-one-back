package com.coreone.back.modules.task.service;

import com.coreone.back.modules.log.service.LogService;
import com.coreone.back.modules.project.domain.ProjectTask;
import com.coreone.back.modules.project.repository.ProjectTaskRepository;
import com.coreone.back.modules.project.service.ProjectService;
import com.coreone.back.modules.subSector.domain.SubSector;
import com.coreone.back.modules.subSector.repository.SubSectorRepository;
import com.coreone.back.modules.task.domain.Task;
import com.coreone.back.modules.task.domain.TaskMember;
import com.coreone.back.modules.task.domain.TaskSubSector;
import com.coreone.back.modules.task.dto.CreateTaskRequestDTO;
import com.coreone.back.modules.task.dto.GetTaskResponse;
import com.coreone.back.modules.task.mapper.TaskMapper;
import com.coreone.back.modules.task.repository.TaskMembersRepository;
import com.coreone.back.modules.task.repository.TaskRepository;
import com.coreone.back.modules.task.repository.TaskSubSectorRepository;
import com.coreone.back.modules.user.domain.User;
import com.coreone.back.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper mapper;
    private final UserRepository userRepository;
    private final SubSectorRepository subSectorRepository;
    private final LogService logService;
    private final TaskMembersRepository taskMembersRepository;
    private final TaskSubSectorRepository taskSubSectorRepository;
    private final ProjectTaskRepository projectTaskRepository;
    private final ProjectService projectService;

    @Transactional
    public Task createTask(User user, CreateTaskRequestDTO request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setEnterprise(user.getEnterprise());
        task.setStatus("PENDING");

        Task savedTask = taskRepository.save(task);

        if (request.getMemberIds() != null && !request.getMemberIds().isEmpty()) {
            List<User> members = userRepository.findAllById(request.getMemberIds());
            for (User member : members) {
                TaskMember tm = new TaskMember();
                tm.setTask(savedTask);
                tm.setUser(member);
                taskMembersRepository.save(tm);
                savedTask.getMembers().add(tm);
            }
        }

        if (request.getSubSectorIds() != null && !request.getSubSectorIds().isEmpty()) {
            List<SubSector> subSectors = subSectorRepository.findAllById(request.getSubSectorIds());
            for (SubSector subSector : subSectors) {
                TaskSubSector tss = new TaskSubSector();
                tss.setTask(savedTask);
                tss.setSubSector(subSector);
                taskSubSectorRepository.save(tss);
                savedTask.getSubSectors().add(tss);
            }
        }

        if (request.getProjectId() != null) {
            var project = projectService.findById(request.getProjectId());
            ProjectTask ps = new ProjectTask();
            ps.setTask(savedTask);
            ps.setProject(project);
            projectTaskRepository.save(ps);
        }

        var taskCreatedBy = userRepository.findById(request.getCreatorId()).orElseThrow();
        var newValue = "title: " + request.getTitle() + " description: " + request.getDescription() + ".";
        logService.create(taskCreatedBy, "CREATE_TASK", "TASK", savedTask.getId(), newValue);

        return savedTask;
    }

    public Page<Task> findAll(User requestingUser, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskRepository.findAllByEnterpriseIdFromSubSectors(requestingUser.getEnterprise().getId(), pageable);
    }

    public Task getById(UUID id) {
        return taskRepository.findById(id).orElseThrow();
    }

    public String delete(UUID id) {
        var task = getById(id);

        taskRepository.delete(task);

        return "Deleted task: " + task.getTitle();
    }

    public GetTaskResponse changeStatus(UUID id, String status) {
        var task = getById(id);

        status = status.toUpperCase();

        if (task.getStatus().equals(status)) {
            throw new RuntimeException("Task status is already set to " + status);
        }

        task.setStatus(status);

        taskRepository.save(task);

        return mapper.toGetTaskResponse(task);
    }
}
