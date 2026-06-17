package com.razororpay.razorpayClone.merchant.service;

import com.razororpay.razorpayClone.merchant.dto.request.CreateApiKeyRequest;
import com.razororpay.razorpayClone.merchant.dto.response.ApiKeyCreateResponse;

import java.util.UUID;

public interface ApiKeyService {
    ApiKeyCreateResponse createApiKey(CreateApiKeyRequest request, UUID merchantId);
}
