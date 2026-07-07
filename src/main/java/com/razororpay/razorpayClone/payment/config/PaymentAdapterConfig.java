package com.razororpay.razorpayClone.payment.config;


import com.razororpay.razorpayClone.common.enums.PaymentMethod;
import com.razororpay.razorpayClone.payment.gateway.PaymentAdapter;
//import com.razororpay.razorpayClone.payment.gateway.adapter.CardPaymentAdapter;
import com.razororpay.razorpayClone.payment.gateway.adapter.CardPaymentAdapter;
import com.razororpay.razorpayClone.payment.gateway.adapter.NetBankingPaymentAdapter;
import com.razororpay.razorpayClone.payment.gateway.adapter.UpiPaymentAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class PaymentAdapterConfig {

    private final CardPaymentAdapter cardPaymentAdapter;
    private final UpiPaymentAdapter upiPaymentAdapter;
    private final NetBankingPaymentAdapter netBankingPaymentAdapter;

    @Bean
    public Map<PaymentMethod, PaymentAdapter> paymentAdaptorMap(){
        return Map.of(
                PaymentMethod.CARD , cardPaymentAdapter,
                PaymentMethod.NETBANKING , netBankingPaymentAdapter,
                PaymentMethod.UPI, upiPaymentAdapter
        );
    }
}
