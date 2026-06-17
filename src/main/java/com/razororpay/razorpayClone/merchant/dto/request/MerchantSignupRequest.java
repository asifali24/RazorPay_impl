package com.razororpay.razorpayClone.merchant.dto.request;

import com.razororpay.razorpayClone.common.enums.BusinessType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record MerchantSignupRequest(
        @NotNull(message = "Name cant be empty")
        @Size(min = 3,max = 25,message = "Name cant be more than 24 character and less than 3 character")
        String name,

        @NotNull(message = "Email cant be empty")
        @Email(message = "Email is invalid")
        String email,

        @NotNull(message = "Password cant be empty")
        @Size(min = 8,max = 50,message = "Password cant be more than 50 character and less than 8 character")
        String password,

        @NotNull(message = "BusinessName cant be empty")
        @Size(min = 3,max = 50,message = "BusinessName cant be more than 50 character and less than 3 character")
        String businessName,

        BusinessType businessType
) {
}
