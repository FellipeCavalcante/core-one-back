package com.coreone.back.modules.enterprise.dto;

import lombok.Data;

@Data
public class EnterpriseRequestCreateDTO {
    private String message;
    private String type; // "INVITE" or "JOIN_REQUEST"
}