package com.razororpay.razorpayClone.merchant.mapper;


import com.razororpay.razorpayClone.merchant.dto.response.ApiKeyResponse;
import com.razororpay.razorpayClone.merchant.entity.ApiKey;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApiKeyMapper {

    List<ApiKeyResponse> toListOFApiKeyResponse(List<ApiKey> apikey);

}
