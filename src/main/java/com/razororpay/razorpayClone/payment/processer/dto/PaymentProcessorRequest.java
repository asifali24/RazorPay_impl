package com.razororpay.razorpayClone.payment.processer.dto;

import com.razororpay.razorpayClone.common.entity.Money;
import com.razororpay.razorpayClone.common.enums.PaymentMethod;

import java.util.Map;
import java.util.UUID;

public record PaymentProcessorRequest(

        UUID processingId,
        UUID paymentId,
        PaymentMethod method,
        Money amount,
        String pan,
        String expiry,
        Map<String, Object> methodDetails
) {

}
