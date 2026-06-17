package com.razororpay.razorpayClone.merchant.dto.response;

import com.razororpay.razorpayClone.common.enums.Environment;

import java.util.UUID;

public record ApiKeyCreateResponse(
        UUID id,
        String keyId,
        String keySecret,
        Environment environment
) {
}
