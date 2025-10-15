package com.coreone.back.modules.enterprise.service;

import com.coreone.back.common.errors.BadRequestException;
import com.coreone.back.common.errors.NotFoundException;
import com.coreone.back.common.errors.UnauthorizedException;
import com.coreone.back.modules.enterprise.domain.Enterprise;
import com.coreone.back.modules.enterprise.domain.EnterpriseRequest;
import com.coreone.back.modules.enterprise.domain.enums.EnterpriseRequestStatus;
import com.coreone.back.modules.enterprise.domain.enums.EnterpriseRequestType;
import com.coreone.back.modules.enterprise.dto.EnterpriseRequestCreateDTO;
import com.coreone.back.modules.enterprise.dto.EnterpriseRequestResponseDTO;
import com.coreone.back.modules.enterprise.dto.UpdateEnterpriseRequestStatusDTO;
import com.coreone.back.modules.enterprise.mapper.EnterpriseRequestMapper;
import com.coreone.back.modules.enterprise.repository.EnterpriseRequestRepository;
import com.coreone.back.modules.enterprise.repository.EnterpriseRepository;
import com.coreone.back.modules.user.domain.User;
import com.coreone.back.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnterpriseRequestService {

    private final EnterpriseRequestRepository repository;
    private final EnterpriseRepository enterpriseRepository;
    private final EnterpriseRequestMapper mapper;
    private final UserService userService;

    @Transactional
    public EnterpriseRequestResponseDTO createJoinRequest(User user, UUID enterpriseId, EnterpriseRequestCreateDTO requestDTO) {
        Enterprise enterprise = enterpriseRepository.findById(enterpriseId)
                .orElseThrow(() -> new NotFoundException("Enterprise not found"));

        EnterpriseRequest er = new EnterpriseRequest();
        er.setUser(user);
        er.setEnterprise(enterprise);
        er.setStatus(EnterpriseRequestStatus.PENDING);
        er.setType(EnterpriseRequestType.JOIN_REQUEST);
        er.setMessage(requestDTO.getMessage());

        repository.save(er);
        return mapper.toResponse(er);
    }

    public EnterpriseRequestResponseDTO createInvite(User inviter, UUID userId, UUID enterpriseId, EnterpriseRequestCreateDTO requestDTO) {
        if (!inviter.getEnterprise().getId().equals(enterpriseId)) {
            throw new UnauthorizedException("Enterprise not found");
        }

        var invitedUser = userService.findById(userId);

        EnterpriseRequest er = new EnterpriseRequest();
        er.setUser(invitedUser);
        er.setEnterprise(invitedUser.getEnterprise());
        er.setStatus(EnterpriseRequestStatus.PENDING);
        er.setType(EnterpriseRequestType.INVITE);
        er.setMessage(requestDTO.getMessage());

        repository.save(er);
        return mapper.toResponse(er);
    }

    public Page<EnterpriseRequest> getRequestsByEnterprise(UUID enterpriseId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return repository.findAllByEnterprise_Id(enterpriseId, pageable);
    }

    public EnterpriseRequestResponseDTO updateStatus(UUID requestId, UpdateEnterpriseRequestStatusDTO requestStatusDTO, User user) {
        var request = findById(requestId);

        if (!user.getEnterprise().getId().equals(request.getEnterprise().getId()) &&
                !user.getId().equals(request.getUser().getId())) {
            throw new UnauthorizedException("Unauthorized request");
        }

        EnterpriseRequestStatus newStatus;
        try {
            newStatus = EnterpriseRequestStatus.valueOf(requestStatusDTO.getStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid status value");
        }

        if (request.getStatus() == newStatus || request.getStatus() != EnterpriseRequestStatus.PENDING) {
            throw new BadRequestException("Invalid status change request");
        }

        if (newStatus == EnterpriseRequestStatus.REJECTED) {
            request.setStatus(EnterpriseRequestStatus.REJECTED);
        } else if (newStatus == EnterpriseRequestStatus.CANCELLED) {
            request.setStatus(EnterpriseRequestStatus.CANCELLED);
        } else if (newStatus == EnterpriseRequestStatus.ACCEPTED &&
                user.getEnterprise().equals(request.getEnterprise())) {
            request.setStatus(EnterpriseRequestStatus.ACCEPTED);

            var userInvited = userService.findById(request.getUser().getId());
            userInvited.setEnterprise(request.getEnterprise());
            userService.save(userInvited);
        }

        repository.save(request);
        return mapper.toResponse(request);
    }

    public EnterpriseRequest findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Request not found"));
    }
}
