package com.coreone.back.controller;

import com.coreone.back.dto.subSector.CreateSubSectorRequestDTO;
import com.coreone.back.dto.subSector.CreateSubSectorResponseDTO;
import com.coreone.back.mapper.SubSectorMapper;
import com.coreone.back.service.SubSectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/subSector")
@RequiredArgsConstructor
public class SubSectorController {

    private final SubSectorService service;
    private final SubSectorMapper mapper;

    @PostMapping("/create")
    public ResponseEntity<CreateSubSectorResponseDTO> createSubSector(@RequestBody CreateSubSectorRequestDTO request) {
        var subSector = mapper.toSubSector(request);
        var subSectorSaved = service.save(subSector);

        var response = mapper.toCreateSubSectorResponseDTO(subSectorSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
