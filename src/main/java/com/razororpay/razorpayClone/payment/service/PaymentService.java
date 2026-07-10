package com.razororpay.razorpayClone.payment.service;

import com.razororpay.razorpayClone.payment.dto.request.PaymentInitRequest;
import com.razororpay.razorpayClone.payment.dto.response.PaymentResponse;

import java.util.UUID;

public interface PaymentService {
    PaymentResponse init(UUID merchantId, PaymentInitRequest paymentInitRequest);

    PaymentResponse capture(UUID merchantId, UUID paymentId);
}
