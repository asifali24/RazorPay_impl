package com.razororpay.razorpayClone.merchant.mapper;


import com.razororpay.razorpayClone.merchant.dto.response.MerchantSignupResponse;
import com.razororpay.razorpayClone.merchant.entity.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthMapper {
    MerchantSignupResponse toSignupResponse(Merchant newMerchant);
}
