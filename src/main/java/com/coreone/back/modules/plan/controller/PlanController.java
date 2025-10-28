package com.coreone.back.modules.plan.controller;

import com.coreone.back.common.util.AuthUtil;
import com.coreone.back.modules.plan.controller.dto.GetPlanResponseDTO;
import com.coreone.back.modules.plan.controller.dto.SelectPlanRequestDTO;
import com.coreone.back.modules.plan.service.PlanService;
import com.coreone.back.modules.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/plan")
@RequiredArgsConstructor
public class PlanController {
    private final PlanService service;
    private final AuthUtil authUtil;

    @GetMapping
    public ResponseEntity<List<GetPlanResponseDTO>> listAllPlans() {
        var response =  service.listAll();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/select")
    public ResponseEntity<Void> selectPlan(@RequestBody SelectPlanRequestDTO selectPlanRequestDTO) {
        User user = authUtil.getAuthenticatedUser();

        service.selectPlan(user, selectPlanRequestDTO);

        return ResponseEntity.ok().build();
    }
}
