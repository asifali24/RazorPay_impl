package com.razororpay.razorpayClone.payment.gateway;

import com.razororpay.razorpayClone.payment.gateway.dto.PaymentRequest;
import com.razororpay.razorpayClone.payment.gateway.dto.PaymentResult;

import java.util.UUID;

public interface PaymentAdapter {
    PaymentResult init(PaymentRequest paymentRequest);

    PaymentResult capture(UUID paymentId);
}
