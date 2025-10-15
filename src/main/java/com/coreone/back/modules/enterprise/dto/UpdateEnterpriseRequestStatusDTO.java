package com.coreone.back.modules.enterprise.dto;

import lombok.Data;

@Data
public class UpdateEnterpriseRequestStatusDTO {
    private String status; // "PENDING", "ACCEPTED", "REJECTED", "CANCELLED"
}