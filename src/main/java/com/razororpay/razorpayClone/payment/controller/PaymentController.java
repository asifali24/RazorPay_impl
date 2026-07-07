package com.razororpay.razorpayClone.payment.controller;


import com.razororpay.razorpayClone.payment.dto.request.PaymentInitRequest;
import com.razororpay.razorpayClone.payment.dto.response.PaymentResponse;
import com.razororpay.razorpayClone.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;


    UUID merchantId = UUID.fromString("1f6caca9-cab9-40cd-a491-a714146e5a55"); //TODO: replace it with MerchantContext

    public ResponseEntity<PaymentResponse> init(@Valid @RequestBody PaymentInitRequest paymentInitRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.init(merchantId,paymentInitRequest));
    }
}
