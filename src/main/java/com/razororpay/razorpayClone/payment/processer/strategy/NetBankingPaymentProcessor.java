package com.razororpay.razorpayClone.payment.processer.strategy;

import com.razororpay.razorpayClone.common.util.RandomizerUtil;
import com.razororpay.razorpayClone.payment.processer.PaymentProcessor;
import com.razororpay.razorpayClone.payment.processer.dto.PaymentProcessorRequest;
import com.razororpay.razorpayClone.payment.processer.dto.PaymentProcessorResponse;
import org.springframework.stereotype.Component;

@Component
public class NetBankingPaymentProcessor implements PaymentProcessor {
    final String BANK_ERROR_FAIL = "Axis";
    @Override
    public PaymentProcessorResponse charge(PaymentProcessorRequest request) {

        String bank = request.methodDetails() != null ?request.methodDetails().get("bank").toString(): null;


        if(BANK_ERROR_FAIL.equals(bank)){
            return new PaymentProcessorResponse.Failure("BANK_REJECT","Net banking processor rejected by BANK");
        }

        String processRef = "NBK_PROCESSER_"+ RandomizerUtil.randomBase64(16);
        String redirectBankRef = "https://axisbank.com";

        return new PaymentProcessorResponse.Success(processRef,redirectBankRef);
    }
}
