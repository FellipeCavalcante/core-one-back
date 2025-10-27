package com.coreone.back.modules.note.controller;

import com.coreone.back.common.util.AuthUtil;
import com.coreone.back.modules.note.controller.dto.CreateNoteRequestDTO;
import com.coreone.back.modules.note.service.NoteService;
import com.coreone.back.modules.user.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/note")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService service;
    private final AuthUtil authUtil;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@Valid @RequestBody CreateNoteRequestDTO requestDTO) {
        User user = authUtil.getAuthenticatedUser();

        service.save(user, requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
