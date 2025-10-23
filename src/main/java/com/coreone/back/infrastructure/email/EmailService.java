package com.coreone.back.infrastructure.email;

import java.util.Map;

public interface EmailService {
    void sendEmailHtml(String to, String subject, String templateName, Map<String, Object> variables);
}
