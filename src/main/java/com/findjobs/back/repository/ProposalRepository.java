package com.findjobs.back.repository;

import com.findjobs.back.domain.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProposalRepository extends JpaRepository<Proposal, UUID> {
}
