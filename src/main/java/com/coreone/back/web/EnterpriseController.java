package com.coreone.back.web;

import com.coreone.back.application.EnterpriseApplicationService;
import com.coreone.back.common.util.AuthUtil;
import com.coreone.back.domain.entity.User;
import com.coreone.back.web.dto.enterprise.CreateEnterpriseRequestDTO;
import com.coreone.back.web.dto.enterprise.CreateEnterpriseResponseDTO;
import com.coreone.back.web.dto.enterprise.GetEnterpriseResponse;
import com.coreone.back.web.mapper.EnterpriseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/enterprises")
@RequiredArgsConstructor
public class EnterpriseController {

    private final EnterpriseApplicationService enterpriseApplicationService;
    private final AuthUtil authUtil;
    private final EnterpriseMapper mapper;

    @PostMapping
    public ResponseEntity<CreateEnterpriseResponseDTO> create(
            @RequestBody CreateEnterpriseRequestDTO request) {
        User owner = authUtil.getAuthenticatedUser();

         request.setCreatorId(owner.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(enterpriseApplicationService.createEnterprise(request));
    }

    @GetMapping
    public ResponseEntity<Page<GetEnterpriseResponse>> getAll(Pageable pageable) {

        var response = mapper.toGetEnterpriseResponse();

        return ResponseEntity.ok(enterpriseApplicationService.getAllEnterprises(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(enterpriseApplicationService.getEnterpriseById(id));
    }
}