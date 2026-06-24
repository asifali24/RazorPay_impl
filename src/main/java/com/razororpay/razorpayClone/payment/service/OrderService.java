package com.razororpay.razorpayClone.payment.service;

import com.razororpay.razorpayClone.payment.dto.request.CreateOrderRequest;
import com.razororpay.razorpayClone.payment.dto.response.OrderResponse;
import jakarta.validation.Valid;

import java.util.UUID;

public interface OrderService {
    OrderResponse create(UUID merchantId, CreateOrderRequest request);
}
