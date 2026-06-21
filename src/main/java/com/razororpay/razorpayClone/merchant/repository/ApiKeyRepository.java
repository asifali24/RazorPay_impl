package com.razororpay.razorpayClone.merchant.repository;

import com.razororpay.razorpayClone.merchant.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ApiKeyRepository extends JpaRepository<ApiKey, UUID> {

    List<ApiKey> findByMerchant_id(UUID merchantId);
    ApiKey findByIdAndMerchant_IdAndEnabled(UUID keyId, UUID merchantId,Boolean status);
}