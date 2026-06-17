package com.razororpay.razorpayClone.merchant.controller;


import com.razororpay.razorpayClone.merchant.dto.request.MerchantSignupRequest;
import com.razororpay.razorpayClone.merchant.dto.response.MerchantSignupResponse;
import com.razororpay.razorpayClone.merchant.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MerchantSignupResponse> signup(@RequestBody @Valid MerchantSignupRequest merchant){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(merchant));
    }
}
