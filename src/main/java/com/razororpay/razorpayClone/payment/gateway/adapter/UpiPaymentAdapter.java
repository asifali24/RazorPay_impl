package com.razororpay.razorpayClone.payment.gateway.adapter;

import com.razororpay.razorpayClone.payment.gateway.PaymentAdapter;
import com.razororpay.razorpayClone.payment.gateway.dto.PaymentRequest;
import com.razororpay.razorpayClone.payment.gateway.dto.PaymentResult;
import com.razororpay.razorpayClone.payment.processer.PaymentProcessorRouter;
import com.razororpay.razorpayClone.payment.processer.dto.PaymentProcessorRequest;
import com.razororpay.razorpayClone.payment.processer.dto.PaymentProcessorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class UpiPaymentAdapter implements PaymentAdapter {
    private final PaymentProcessorRouter paymentProcessorRouter;

    @Override
    public PaymentResult init(PaymentRequest paymentRequest) {
        log.info("Initiate payment with UPI banking Adapter ,paymentId: {}",  paymentRequest.paymentId());
        try{
            PaymentProcessorRequest paymentProcessorRequest = PaymentProcessorRequest.nonCard(
                    paymentRequest.paymentId(),paymentRequest.method(),paymentRequest.amount(),paymentRequest.methodDetails()
            );

            PaymentProcessorResponse paymentProcessorResponse = paymentProcessorRouter.charge(paymentProcessorRequest);

//        if(paymentProcessorResponse instanceof  PaymentProcessorResponse.Success success){
//            return new PaymentResult.Success(success.bankReference());
//        }else if(paymentProcessorResponse instanceof  PaymentProcessorResponse.Failure failure){
//            return new PaymentResult.Failure(failure.errorCode(),failure.errorDescription());
//        }else if (paymentProcessorResponse instanceof  PaymentProcessorResponse.Pending pending){
//            return new PaymentResult.Pending(pending.processorReference());
//        }

            return switch (paymentProcessorResponse) {

                case PaymentProcessorResponse.Success success ->
                        new PaymentResult.Success(success.bankReference());

                case PaymentProcessorResponse.Failure failure ->
                        new PaymentResult.Failure(
                                failure.errorCode(),
                                failure.errorDescription()
                        );

                case PaymentProcessorResponse.Pending pending ->
                        new PaymentResult.Pending(pending.processorReference());
            };
        }catch(Exception e){
            log.warn("UPI failed, paymentId: {}", paymentRequest.paymentId());
            return new PaymentResult.Failure("VPA_FAILED", e.getMessage());
        }
    }

    @Override
    public PaymentResult capture(UUID paymentId) {
        return new PaymentResult.Success("UPI_REF_"+paymentId);
    }
}
