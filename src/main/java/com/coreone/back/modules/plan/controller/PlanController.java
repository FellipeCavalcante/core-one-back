package com.coreone.back.modules.plan.controller;

import com.coreone.back.modules.plan.controller.dto.GetPlanResponseDTO;
import com.coreone.back.modules.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/plan")
@RequiredArgsConstructor
public class PlanController {
    private final PlanService service;

    @GetMapping
    public ResponseEntity<List<GetPlanResponseDTO>> listAllPlans() {
        var response =  service.listAll();

        return ResponseEntity.ok(response);
    }
}
