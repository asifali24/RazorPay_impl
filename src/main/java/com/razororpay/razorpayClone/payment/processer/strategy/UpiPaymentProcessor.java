package com.razororpay.razorpayClone.payment.processer.strategy;

import com.razororpay.razorpayClone.common.util.RandomizerUtil;
import com.razororpay.razorpayClone.payment.processer.PaymentProcessor;
import com.razororpay.razorpayClone.payment.processer.dto.PaymentProcessorRequest;
import com.razororpay.razorpayClone.payment.processer.dto.PaymentProcessorResponse;

public class UpiPaymentProcessor implements PaymentProcessor {

    final String VPA_ERROR_FAIL = "Axis";

    @Override
    public PaymentProcessorResponse charge(PaymentProcessorRequest request) {

            String upi = request.methodDetails() != null ?request.methodDetails().get("upi").toString(): null;


            if(VPA_ERROR_FAIL.equals(upi)){
                return new PaymentProcessorResponse.Failure("VPA_REJECT","UPI banking processor rejected by BANK");
            }

            String processRef = "UPI_PROCESSER_"+ RandomizerUtil.randomBase64(16);
            String redirectBankRef = "NBK_PROCESSER_"+ RandomizerUtil.randomBase64(16);

            return new PaymentProcessorResponse.Success(processRef,redirectBankRef);

    }
}
