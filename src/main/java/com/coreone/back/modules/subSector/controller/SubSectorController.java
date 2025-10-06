package com.coreone.back.modules.subSector.controller;

import com.coreone.back.modules.user.domain.User;
import com.coreone.back.modules.subSector.dto.CreateSubSectorRequestDTO;
import com.coreone.back.modules.subSector.dto.CreateSubSectorResponseDTO;
import com.coreone.back.modules.subSector.dto.GetSubSectorResponse;
import com.coreone.back.modules.subSector.mapper.SubSectorMapper;
import com.coreone.back.modules.subSector.service.SubSectorService;
import com.coreone.back.common.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/subSector")
@RequiredArgsConstructor
public class SubSectorController {

    private final SubSectorService service;
    private final SubSectorMapper mapper;
    private final AuthUtil authUtil;

    @PostMapping("/create")
    public ResponseEntity<CreateSubSectorResponseDTO> createSubSector(@RequestBody CreateSubSectorRequestDTO request) {
        User user = authUtil.getAuthenticatedUser();

        var response = service.save(user.getId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/by-sector/{id}")
    public ResponseEntity<List<GetSubSectorResponse>> getBySector(@PathVariable UUID id) {
        var response = service.listAllSectorsSubSectors(id);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/add-user/{subSectorId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<String> addUser(@PathVariable UUID subSectorId, @RequestBody UUID userId) {
        var response = service.addUserToSubSector(subSectorId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/remove-user")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<String> removeUser(@PathVariable UUID subSectorId, @RequestBody UUID userId) {
        var response = service.removeUser(subSectorId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<String> deleteSubSector(@PathVariable UUID id) {
        var response = service.delete(id);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
