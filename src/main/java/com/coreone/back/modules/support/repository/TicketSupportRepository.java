package com.coreone.back.modules.support.repository;

import com.coreone.back.modules.support.domain.TicketSupport;
import com.coreone.back.modules.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TicketSupportRepository extends JpaRepository<TicketSupport, UUID> {
    List<TicketSupport> findByUser(User user);
}
