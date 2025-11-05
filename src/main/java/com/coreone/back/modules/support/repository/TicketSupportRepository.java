package com.coreone.back.modules.support.repository;

import com.coreone.back.modules.support.domain.TicketSupport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketSupportRepository extends JpaRepository<TicketSupport, UUID> {
}
