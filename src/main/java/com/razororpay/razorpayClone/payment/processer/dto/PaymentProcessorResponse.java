package com.razororpay.razorpayClone.payment.processer.dto;

public sealed interface PaymentProcessorResponse permits
        PaymentProcessorResponse.Pending,
        PaymentProcessorResponse.Success,
        PaymentProcessorResponse.Failure
{

    record Pending() implements  PaymentProcessorResponse{};

    record Success() implements  PaymentProcessorResponse{};

    record Failure() implements  PaymentProcessorResponse{};
}
