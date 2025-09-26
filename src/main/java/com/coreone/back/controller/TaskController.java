package com.coreone.back.controller;

import com.coreone.back.domain.User;
import com.coreone.back.dto.task.CreateTaskRequestDTO;
import com.coreone.back.dto.task.CreateTaskResponseDTO;
import com.coreone.back.dto.task.GetTaskResponse;
import com.coreone.back.mapper.TaskMapper;
import com.coreone.back.repository.UserRepository;
import com.coreone.back.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;
    private final UserRepository userRepository;
    private final TaskMapper mapper;


    @PostMapping("/create")
    public ResponseEntity<CreateTaskResponseDTO> createTask(@RequestBody CreateTaskRequestDTO request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not Found!"));

        request.setCreatorId(user.getId());

        var taskSaved = service.createTask(request);

        var response = mapper.toCreateTaskResponseDTO(taskSaved, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<GetTaskResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        var tasks = service.findAll(page, size);

        Page<GetTaskResponse> response = tasks.map(mapper::toGetTaskResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetTaskResponse> searchById(@PathVariable UUID id) {
        var tasks = service.getById(id);

        var response = mapper.toGetTaskResponse(tasks);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        var response = service.delete(id);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<GetTaskResponse> changeStatus(@PathVariable UUID id, @RequestParam String status) {
        var response = service.changeStatus(id, status);

        return ResponseEntity.ok(response);
    }
}
