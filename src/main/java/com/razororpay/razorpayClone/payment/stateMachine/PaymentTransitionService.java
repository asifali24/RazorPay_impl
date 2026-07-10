package com.razororpay.razorpayClone.payment.stateMachine;


import com.razororpay.razorpayClone.common.enums.PaymentActor;
import com.razororpay.razorpayClone.common.enums.PaymentEvent;
import com.razororpay.razorpayClone.common.enums.PaymentStatus;
import com.razororpay.razorpayClone.payment.entity.Payment;
import com.razororpay.razorpayClone.payment.entity.PaymentTransitionLog;
import com.razororpay.razorpayClone.payment.repository.PaymentTransitionLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentTransitionService {
    private final PaymentTransitionLogRepository paymentTransitionLogRepository;
    private final PaymentStateMachine paymentStateMachine;

    public PaymentStatus apply(Payment payment, PaymentEvent event) {
        PaymentStatus next = paymentStateMachine.transition(payment.getStatus(), event);
        payment.setStatus(next);
        PaymentTransitionLog log = PaymentTransitionLog.builder()
                .payment(payment)
                .fromStatus(payment.getStatus())
                .event(event)
                .toStatus(next)
                .actor(PaymentActor.SYSTEM) //TODO: fetch merchant context to identify actor
                .occurredAt(LocalDateTime.now())
                .build();

        paymentTransitionLogRepository.save(log);
        return next;
    }
}
