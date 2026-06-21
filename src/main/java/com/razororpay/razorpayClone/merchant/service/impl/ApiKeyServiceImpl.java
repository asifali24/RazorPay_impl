package com.razororpay.razorpayClone.merchant.service.impl;

import com.razororpay.razorpayClone.common.exception.ResourceNotFoundException;
import com.razororpay.razorpayClone.common.util.RandomizerUtil;
import com.razororpay.razorpayClone.merchant.dto.request.CreateApiKeyRequest;
import com.razororpay.razorpayClone.merchant.dto.response.ApiKeyCreateResponse;
import com.razororpay.razorpayClone.merchant.dto.response.ApiKeyResponse;
import com.razororpay.razorpayClone.merchant.entity.ApiKey;
import com.razororpay.razorpayClone.merchant.entity.Merchant;
import com.razororpay.razorpayClone.merchant.mapper.ApiKeyMapper;
import com.razororpay.razorpayClone.merchant.repository.ApiKeyRepository;
import com.razororpay.razorpayClone.merchant.repository.MerchantRepository;
import com.razororpay.razorpayClone.merchant.service.ApiKeyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiKeyServiceImpl implements ApiKeyService {
    private final ApiKeyRepository apiKeyRepository;
    private final MerchantRepository merchantRepository;
    private final ApiKeyMapper apiKeyMapper;

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

//        return apiKeyRepository.findByMerchant_id(merchantId)
//                .stream()
//                .map((ele) -> new ApiKeyResponse(
//                        ele.getId(),
//                        ele.getKeyId(),
//                        ele.getEnvironment(),
//                        ele.isEnabled(),
//                        ele.getLastUsedAt(),
//                        null
//                )).toList();

        return apiKeyMapper.toListOFApiKeyResponse(apiKeyRepository.findByMerchant_id(merchantId));
    }

    @Override
    @Transactional
    public void revokeApiKey(UUID merchantId, UUID keyId) {
        ApiKey isApiKey =isApiKeyExist(merchantId,keyId,true);
        isApiKey.setEnabled(false);
    }

    @Override
    @Transactional
    public ApiKeyCreateResponse rotateApiKey(UUID merchantId, UUID keyId) {
        ApiKey apiKey = isApiKeyExist(merchantId,keyId,true);
        System.out.println(apiKey);
        String newRawSecret = RandomizerUtil.randomBase64(40);
        apiKey.setPreviousKeySecretHash(apiKey.getKeySecretHash());
        apiKey.setKeySecretHash(newRawSecret);  // TODO: encode with BcryptPasswordEncoder
        apiKey.setRotatedAt(LocalDateTime.now());
        apiKey.setGracePeriodExpiresAt(LocalDateTime.now().plusHours(24));
        apiKey = apiKeyRepository.save(apiKey);

        return new ApiKeyCreateResponse(apiKey.getId(), apiKey.getKeyId(),
                newRawSecret, apiKey.getEnvironment());
    }


/*
*
* local method
*
*/

private ApiKey isApiKeyExist(UUID merchantId, UUID keyId,Boolean status) {
    ApiKey isApiKey = apiKeyRepository.findByIdAndMerchant_IdAndEnabled(keyId,merchantId,status);
    if(isApiKey ==null) throw new ResourceNotFoundException("Apikey not found",keyId);
    return isApiKey;
}

}
