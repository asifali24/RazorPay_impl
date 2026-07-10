package com.razororpay.razorpayClone.payment.gateway.adapter;

import com.razororpay.razorpayClone.payment.gateway.PaymentAdapter;
import com.razororpay.razorpayClone.payment.gateway.dto.PaymentRequest;
import com.razororpay.razorpayClone.payment.gateway.dto.PaymentResult;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class CardPaymentAdapter implements PaymentAdapter {
    @Override
    public PaymentResult init(PaymentRequest paymentRequest) {
        return null;
    }

    @Override
    public PaymentResult capture(UUID paymentId) {
        return null;
    }
}
