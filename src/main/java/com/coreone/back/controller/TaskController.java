package com.coreone.back.controller;

import com.coreone.back.domain.User;
import com.coreone.back.dto.task.CreateTaskRequestDTO;
import com.coreone.back.dto.task.CreateTaskResponseDTO;
import com.coreone.back.mapper.TaskMapper;
import com.coreone.back.repository.UserRepository;
import com.coreone.back.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;
    private final TaskMapper mapper;


    @PostMapping("/create")
    public ResponseEntity<CreateTaskResponseDTO> createTask(@RequestBody CreateTaskRequestDTO request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not Found!"));

        request.setCreatorId(user.getId());

        var taskSaved = taskService.createTask(request);

        var response = mapper.toCreateTaskResponseDTO(taskSaved, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
