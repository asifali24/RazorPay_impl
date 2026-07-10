package com.razororpay.razorpayClone.payment.processer;

import com.razororpay.razorpayClone.common.enums.PaymentMethod;
import com.razororpay.razorpayClone.payment.processer.dto.PaymentProcessorRequest;
import com.razororpay.razorpayClone.payment.processer.dto.PaymentProcessorResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PaymentProcessorRouter {

    private Map<PaymentMethod , PaymentProcessor> paymentMethodPaymentProcessorMap;


    public PaymentProcessorResponse charge(PaymentProcessorRequest request) {
        PaymentProcessor processor = paymentMethodPaymentProcessorMap.get(request.method());

        if(processor == null){
            throw new IllegalArgumentException("No payment processor registered for method: "+request.method());
        }

        return processor.charge(request);
    }
}
