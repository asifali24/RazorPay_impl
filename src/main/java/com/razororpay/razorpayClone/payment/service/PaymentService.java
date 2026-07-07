package com.razororpay.razorpayClone.payment.service;

import com.razororpay.razorpayClone.payment.dto.request.PaymentInitRequest;
import com.razororpay.razorpayClone.payment.dto.response.PaymentResponse;
import jakarta.validation.Valid;

import java.util.UUID;

public interface PaymentService {
    PaymentResponse init(UUID merchantId, PaymentInitRequest paymentInitRequest);
}
