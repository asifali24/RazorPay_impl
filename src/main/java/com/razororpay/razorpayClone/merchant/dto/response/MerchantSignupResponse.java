package com.razororpay.razorpayClone.merchant.dto.response;

import com.razororpay.razorpayClone.common.enums.BusinessType;
import com.razororpay.razorpayClone.common.enums.MerchantStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record MerchantSignupResponse(
        UUID id,
        String name,
        String email,
        String businessName,
        BusinessType businessType,
        MerchantStatus merchantStatus
) {
}
