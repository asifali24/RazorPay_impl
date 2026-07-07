package com.razororpay.razorpayClone.payment.gateway;


import com.razororpay.razorpayClone.common.enums.PaymentMethod;
import com.razororpay.razorpayClone.payment.gateway.dto.PaymentRequest;
import com.razororpay.razorpayClone.payment.gateway.dto.PaymentResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PaymentGateWayRouter {

    private final Map<PaymentMethod , PaymentAdapter> paymentMethodPaymentAdapterMap;

    public PaymentResult init(PaymentRequest paymentRequest){
        PaymentAdapter adapter = paymentMethodPaymentAdapterMap.get(paymentRequest.method());

        if (adapter == null) {
            throw new IllegalArgumentException("No payment adapter registered for method: "+paymentRequest.method());
        }

        return adapter.init(paymentRequest);
    }
}
