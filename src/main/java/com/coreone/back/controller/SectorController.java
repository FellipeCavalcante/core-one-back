package com.coreone.back.controller;

import com.coreone.back.dto.sector.CreateSectorRequestDTO;
import com.coreone.back.dto.sector.CreateSectorResponseDTO;
import com.coreone.back.dto.sector.GetSectorResponse;
import com.coreone.back.service.SectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/sector")
@RequiredArgsConstructor
public class SectorController {

    private final SectorService service;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<CreateSectorResponseDTO> create(@RequestBody CreateSectorRequestDTO request) {

        var response = service.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/by-enterprise/{id}")
    public ResponseEntity<List<GetSectorResponse>> getByEnterprise(@PathVariable UUID id) {
        var response = service.listAllEnterpriseSectors(id);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        var response = service.delete(id);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
