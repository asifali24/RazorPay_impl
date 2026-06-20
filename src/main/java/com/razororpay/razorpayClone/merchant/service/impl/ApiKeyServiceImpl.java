package com.razororpay.razorpayClone.merchant.service.impl;

import com.razororpay.razorpayClone.common.exception.ResourceNotFoundException;
import com.razororpay.razorpayClone.merchant.dto.request.CreateApiKeyRequest;
import com.razororpay.razorpayClone.merchant.dto.response.ApiKeyCreateResponse;
import com.razororpay.razorpayClone.merchant.dto.response.ApiKeyResponse;
import com.razororpay.razorpayClone.merchant.entity.ApiKey;
import com.razororpay.razorpayClone.merchant.entity.Merchant;
import com.razororpay.razorpayClone.merchant.repository.ApiKeyRepository;
import com.razororpay.razorpayClone.merchant.repository.MerchantRepository;
import com.razororpay.razorpayClone.merchant.service.ApiKeyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiKeyServiceImpl implements ApiKeyService {
    private final ApiKeyRepository apiKeyRepository;
    private final MerchantRepository merchantRepository;
    @Override
    @Transactional
    public ApiKeyCreateResponse createApiKey(CreateApiKeyRequest request, UUID merchantId) {
        Merchant isMerchantExist = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant",merchantId));

        String keyId = "rzp_"+request.environment().name().toLowerCase()+"big_random_string";
        String rawSecret = "big_random_secret"; // TODO: replace with cryptographic random hex
//        a-z,A-Z,0-9,-,_
//        a-z,0-9

        ApiKey apiKey = ApiKey.builder()
                .merchant(isMerchantExist)
                .keyId(keyId)
                .keySecretHash(rawSecret) // TODO: encode with BcryptPasswordEncoder
                .environment(request.environment())
                .build();

        apiKey = apiKeyRepository.save(apiKey);

        return new ApiKeyCreateResponse(apiKey.getId(),keyId,rawSecret,request.environment());
    }



    @Override
    public List<ApiKeyResponse> getMerchantApiKeyList(UUID merchantId) {
        merchantRepository.findById(merchantId).orElseThrow(() -> new ResourceNotFoundException("Merchant",merchantId));

        return apiKeyRepository.findByMerchant_id(merchantId)
                .stream()
                .map((ele) -> new ApiKeyResponse(
                        ele.getId(),
                        ele.getKeyId(),
                        ele.getEnvironment(),
                        ele.isEnabled(),
                        ele.getLastUsedAt(),
                        null
                )).toList();
    }
}
