package com.razororpay.razorpayClone.payment.config;


import com.razororpay.razorpayClone.common.enums.PaymentMethod;
import com.razororpay.razorpayClone.payment.processer.PaymentProcessor;
import com.razororpay.razorpayClone.payment.processer.strategy.CardPaymentProcessor;
import com.razororpay.razorpayClone.payment.processer.strategy.NetBankingPaymentProcessor;
import com.razororpay.razorpayClone.payment.processer.strategy.UpiPaymentProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class PaymentProcessorConfig {
    private final PaymentProcessor paymentProcessor;
    @Bean
    public Map<PaymentMethod , PaymentProcessor> paymentMethodPaymentProcessorMap(){
        return Map.of(
                PaymentMethod.CARD , new CardPaymentProcessor(),
                PaymentMethod.NETBANKING , new NetBankingPaymentProcessor(),
                PaymentMethod.UPI, new UpiPaymentProcessor()
        );
    }
}
