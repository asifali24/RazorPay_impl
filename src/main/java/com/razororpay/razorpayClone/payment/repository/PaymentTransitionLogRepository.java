package com.razororpay.razorpayClone.payment.repository;

import com.razororpay.razorpayClone.payment.entity.PaymentTransitionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentTransitionLogRepository extends JpaRepository<PaymentTransitionLog, UUID> {
}