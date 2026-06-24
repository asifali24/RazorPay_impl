package com.razororpay.razorpayClone.payment.controller;


import com.razororpay.razorpayClone.payment.dto.request.CreateOrderRequest;
import com.razororpay.razorpayClone.payment.dto.response.OrderResponse;
import com.razororpay.razorpayClone.payment.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    UUID merchantId = UUID.fromString("1f6caca9-cab9-40cd-a491-a714146e5a55"); //TODO: replace it with MerchantContext


    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(merchantId,request));
    }

}
