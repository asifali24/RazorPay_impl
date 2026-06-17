package com.razororpay.razorpayClone.merchant.dto.request;

import com.razororpay.razorpayClone.common.enums.Environment;

public record CreateApiKeyRequest(
        Environment environment
) {
}
