package com.coreone.back.modules.sector.controller;

import com.coreone.back.modules.sector.dto.UpdateSectorRequest;
import com.coreone.back.modules.user.domain.User;
import com.coreone.back.modules.sector.dto.CreateSectorRequestDTO;
import com.coreone.back.modules.sector.dto.CreateSectorResponseDTO;
import com.coreone.back.modules.sector.dto.GetSectorResponse;
import com.coreone.back.modules.sector.service.SectorService;
import com.coreone.back.common.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/sector")
@RequiredArgsConstructor
public class SectorController {

    private final SectorService service;
    private final AuthUtil authUtil;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<CreateSectorResponseDTO> create(@RequestBody CreateSectorRequestDTO request) {
        User user = authUtil.getAuthenticatedUser();

        var response = service.save(request, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/by-enterprise")
    public ResponseEntity<Page<GetSectorResponse>> getByEnterprise(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "20") int size) {
        User user = authUtil.getAuthenticatedUser();

        var response = service.listAllEnterpriseSectors(user, page, size);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("update/{sectorId}")
    public ResponseEntity<Void> updateSector(@PathVariable UUID sectorId, @RequestBody UpdateSectorRequest request) {
        User user = authUtil.getAuthenticatedUser();

        service.update(user, sectorId, request);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        User user = authUtil.getAuthenticatedUser();

        var response = service.delete(id, user);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
