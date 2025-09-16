package com.coreone.back.controller;

import com.coreone.back.dto.sector.CreateSectorRequestDTO;
import com.coreone.back.dto.sector.CreateSectorResponseDTO;
import com.coreone.back.mapper.SectorMapper;
import com.coreone.back.service.SectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/sector")
@RequiredArgsConstructor
public class SectorController {

    private final SectorService sectorService;
    private final SectorMapper sectorMapper;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<CreateSectorResponseDTO> create(@RequestBody CreateSectorRequestDTO request) {
        var sector = sectorMapper.toSector(request);
        var sectorSaved = sectorService.save(sector);

        var response = sectorMapper.toCreateSectorResponseDTO(sectorSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
