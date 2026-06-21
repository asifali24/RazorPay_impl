package com.razororpay.razorpayClone.merchant.service;

import com.razororpay.razorpayClone.merchant.dto.request.CreateApiKeyRequest;
import com.razororpay.razorpayClone.merchant.dto.response.ApiKeyCreateResponse;
import com.razororpay.razorpayClone.merchant.dto.response.ApiKeyResponse;

import java.util.List;
import java.util.UUID;

public interface ApiKeyService {
    ApiKeyCreateResponse createApiKey(CreateApiKeyRequest request, UUID merchantId);

    List<ApiKeyResponse> getMerchantApiKeyList(UUID merchantId);

    void revokeApiKey(UUID merchantId, UUID keyId);

    ApiKeyCreateResponse rotateApiKey(UUID merchantI, UUID keyId);


}
