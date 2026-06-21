package com.razororpay.razorpayClone.merchant.controller;

import com.razororpay.razorpayClone.merchant.dto.request.CreateApiKeyRequest;
import com.razororpay.razorpayClone.merchant.dto.response.ApiKeyCreateResponse;
import com.razororpay.razorpayClone.merchant.dto.response.ApiKeyResponse;
import com.razororpay.razorpayClone.merchant.service.ApiKeyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/merchant/{merchantId}/api-keys")
@RequiredArgsConstructor
public class ApiKeyController {
    private final ApiKeyService apiKeyService;

    @PostMapping
    public ResponseEntity<ApiKeyCreateResponse> createApiKey(
            @PathVariable UUID merchantId ,@Valid @RequestBody CreateApiKeyRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(apiKeyService.createApiKey(request,merchantId));
    }

    @GetMapping()
    public ResponseEntity<List<ApiKeyResponse>> getMerchantApiKeyList(@PathVariable UUID merchantId){
        return ResponseEntity.ok(apiKeyService.getMerchantApiKeyList(merchantId));
    }

    @DeleteMapping("/{keyId}")
    public ResponseEntity<Void> revokeApiKey(@PathVariable UUID merchantId, @PathVariable UUID keyId ){
        apiKeyService.revokeApiKey(merchantId,keyId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{keyId}/rotate")
    public ResponseEntity<ApiKeyCreateResponse> rotateApiKey(@PathVariable UUID merchantId, @PathVariable UUID keyId ){
        return ResponseEntity.ok(apiKeyService.rotateApiKey(merchantId,keyId));
    }

}
