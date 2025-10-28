package com.coreone.back.modules.payment.service;

import com.coreone.back.common.errors.BadRequestException;
import com.coreone.back.common.errors.NotFoundException;
import com.coreone.back.common.errors.UnauthorizedException;
import com.coreone.back.modules.payment.domain.Payment;
import com.coreone.back.modules.payment.repository.PaymentRepository;
import com.coreone.back.modules.user.domain.User;
import com.coreone.back.modules.user.domain.UserPlan;
import com.coreone.back.modules.user.repository.UserPlanRepository;
import com.coreone.back.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository repository;
    private final UserService userService;
    private final UserPlanRepository userPlanRepository;

    public void save(Payment payment) {
        repository.save(payment);
    }

    public void payRequest(UUID paymentId, User user, String token) {
        var payment = getPayment(paymentId);

        if (payment.getStatus().equalsIgnoreCase("PAID")) {
            throw new BadRequestException("Payment is already paid");
        }

        if (!payment.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("Unauthorized");
        }

        boolean paymentApproved = mockPaymentGateway(payment, token);

        if (paymentApproved) {
            payment.setStatus("PAID");
            payment.setTransactionId(UUID.randomUUID().toString());
            payment.setExpiredAt(LocalDateTime.now().plusMonths(1));
            save(payment);

            UserPlan userPlan = new UserPlan();
            userPlan.setUser(user);
            userPlan.setPlan(payment.getPlan());
            userPlan.setStartDate(LocalDateTime.now());
            userPlan.setEndDate(LocalDateTime.now().plusMonths(1));
            userPlan.setStatus("ACTIVE");
            userPlanRepository.save(userPlan);

            userService.save(user);

        } else {
            payment.setStatus("FAILED");
            save(payment);
            throw new BadRequestException("Payment failed");
        }
    }

    public Payment getPayment(UUID paymentId) {
        return repository.findById(paymentId).orElseThrow(
                () -> new NotFoundException("Payment not found")
        );
    }

    private boolean mockPaymentGateway(Payment payment, String token) {
        return Math.random() > 0.05;
    }
}
