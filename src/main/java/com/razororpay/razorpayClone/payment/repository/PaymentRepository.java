package com.razororpay.razorpayClone.payment.repository;

import com.razororpay.razorpayClone.payment.entity.OrderRecord;
import com.razororpay.razorpayClone.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    List<Payment> findByOrder_Id(OrderRecord order);

    Optional<Payment> findByIdAndMerchantId(UUID paymentId, UUID merchantId);
}