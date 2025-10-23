package com.coreone.back.web.dto.enterprise;

import lombok.Data;

@Data
public class EnterpriseRequestCreateDTO {
    private String message;
    private String type; // "INVITE" or "JOIN_REQUEST"
}