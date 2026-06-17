package com.razororpay.razorpayClone.merchant.service;

import com.razororpay.razorpayClone.merchant.dto.request.MerchantSignupRequest;
import com.razororpay.razorpayClone.merchant.dto.response.MerchantSignupResponse;


public interface AuthService {
    MerchantSignupResponse signup(MerchantSignupRequest merchant);
}
