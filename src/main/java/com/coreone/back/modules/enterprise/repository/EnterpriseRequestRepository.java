package com.coreone.back.modules.enterprise.repository;

import com.coreone.back.modules.enterprise.domain.EnterpriseRequest;
import com.coreone.back.modules.enterprise.domain.enums.EnterpriseRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EnterpriseRequestRepository extends JpaRepository<EnterpriseRequest, UUID> {

    Page<EnterpriseRequest> findAllByEnterprise_Id(UUID enterpriseId, Pageable pageable);
}
