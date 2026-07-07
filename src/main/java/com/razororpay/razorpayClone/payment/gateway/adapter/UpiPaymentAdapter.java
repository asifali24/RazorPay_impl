package com.razororpay.razorpayClone.payment.gateway.adapter;

import com.razororpay.razorpayClone.payment.gateway.PaymentAdapter;
import com.razororpay.razorpayClone.payment.gateway.dto.PaymentRequest;
import com.razororpay.razorpayClone.payment.gateway.dto.PaymentResult;
import org.springframework.stereotype.Component;

@Component
public class UpiPaymentAdapter implements PaymentAdapter {
    @Override
    public PaymentResult init(PaymentRequest paymentRequest) {
        return null;
    }
}
