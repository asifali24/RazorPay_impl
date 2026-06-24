package com.razororpay.razorpayClone.payment.mapper;

import com.razororpay.razorpayClone.payment.entity.OrderRecord;
import com.razororpay.razorpayClone.payment.dto.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    OrderResponse toResponse(OrderRecord orderRecord);
}
