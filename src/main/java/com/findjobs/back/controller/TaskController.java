package com.findjobs.back.controller;

import com.findjobs.back.domain.User;
import com.findjobs.back.dto.task.CreateTaskRequestDTO;
import com.findjobs.back.dto.task.CreateTaskResponseDTO;
import com.findjobs.back.mapper.TaskMapper;
import com.findjobs.back.repository.UserRepository;
import com.findjobs.back.service.TaskService;
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
