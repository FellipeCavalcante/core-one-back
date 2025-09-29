package com.coreone.back.service;

import com.coreone.back.domain.*;
import com.coreone.back.dto.task.GetTaskResponse;
import com.coreone.back.mapper.TaskMapper;
import com.coreone.back.repository.*;
import com.coreone.back.dto.task.CreateTaskRequestDTO;
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

    @Transactional
    public Task createTask(CreateTaskRequestDTO request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
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
        var task = taskRepository.findById(id).orElseThrow();
        return task;
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

        var taskSaved = mapper.toGetTaskResponse(task);

        return taskSaved;
    }
}
