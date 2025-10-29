package com.coreone.back.modules.plan.service;

import com.coreone.back.common.errors.BadRequestException;
import com.coreone.back.common.errors.NotFoundException;
import com.coreone.back.common.errors.UnauthorizedException;
import com.coreone.back.modules.payment.domain.Payment;
import com.coreone.back.modules.payment.service.CreditCardService;
import com.coreone.back.modules.payment.service.PaymentService;
import com.coreone.back.modules.plan.controller.dto.GetPlanResponseDTO;
import com.coreone.back.modules.plan.controller.dto.SelectPlanRequestDTO;
import com.coreone.back.modules.plan.domain.Plan;
import com.coreone.back.modules.plan.repository.PlanRepository;
import com.coreone.back.modules.user.domain.User;
import com.coreone.back.modules.user.domain.UserPlan;
import com.coreone.back.modules.user.repository.UserPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository repository;
    private final CreditCardService creditCardService;
    private final UserPlanRepository userPlanRepository;
    private final PaymentService paymentService;

    public List<GetPlanResponseDTO> listAll() {
        List<Plan> plans = repository.findAll();

        return plans.stream().map(plan -> new GetPlanResponseDTO(
                plan.getId(),
                plan.getName(),
                plan.getValue(),
                plan.getWorkstationQtd(),
                plan.getDescription()
        )).toList();
    }

    @Transactional
    public void selectPlan(User user, SelectPlanRequestDTO request) {
        var plan = getById(request.planId());
        var creditCard = creditCardService.getCreditCardById(request.creditCardId());

        if (!creditCard.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("Unauthorized");
        }

        if (userPlanRepository.findCurrentPlan(user.getId()).isPresent()) {
            throw new BadRequestException("User has current plan");
        }

        UserPlan userPlan = new UserPlan();
        userPlan.setPlan(plan);
        userPlan.setUser(user);
        userPlan.setStatus("PENDING");
        userPlan.setStartDate(LocalDateTime.now());
        userPlan.setEndDate(LocalDateTime.now().plusMonths(1));
        userPlanRepository.save(userPlan);

        Payment payment = new Payment();
        payment.setUser(user);
        payment.setPlan(plan);
        payment.setCreditCard(creditCard);
        payment.setValue(plan.getValue());
        payment.setTransactionId(UUID.randomUUID().toString()); // token
        paymentService.save(payment);
    }


    public Plan getById(UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("Plan not found with id: " + id)
        );
    }
}
