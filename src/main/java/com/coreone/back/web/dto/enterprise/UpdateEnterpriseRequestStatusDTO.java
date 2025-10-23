package com.coreone.back.web.dto.enterprise;

import lombok.Data;

@Data
public class UpdateEnterpriseRequestStatusDTO {
    private String status; // "PENDING", "ACCEPTED", "REJECTED", "CANCELLED"
}