package com.razororpay.razorpayClone.payment.processer;

import com.razororpay.razorpayClone.payment.processer.dto.PaymentProcessorRequest;
import com.razororpay.razorpayClone.payment.processer.dto.PaymentProcessorResponse;

public interface PaymentProcessor {
     PaymentProcessorResponse charge(PaymentProcessorRequest request);
}
