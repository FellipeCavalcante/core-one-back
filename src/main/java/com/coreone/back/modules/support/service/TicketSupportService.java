package com.coreone.back.modules.support.service;

import com.coreone.back.modules.support.domain.TicketSupport;
import com.coreone.back.modules.support.dto.CreateTicketSupportDTO;
import com.coreone.back.modules.support.repository.TicketSupportRepository;
import com.coreone.back.modules.user.domain.User;
import com.coreone.back.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TicketSupportService {
    private final TicketSupportRepository repository;
    private final EmailService emailService;

    public void create(CreateTicketSupportDTO dto, User user) {
        TicketSupport ts = new TicketSupport();
        ts.setTitle(dto.title());
        ts.setDescription(dto.description());
        ts.setStatus(dto.status());
        ts.setType(dto.type());
        ts.setUser(user);
        repository.save(ts);

        Map<String, Object> variables = Map.of(
                "name", user.getName(),
                "title", dto.title(),
                "type", dto.type()
        );

        emailService.sendEmailHtml(
                user.getEmail(),
                "Suporte do Cone",
                "ticket_support",
                variables
        );
    }
}
