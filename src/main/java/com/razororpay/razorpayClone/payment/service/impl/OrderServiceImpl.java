package com.razororpay.razorpayClone.payment.service.impl;

import com.razororpay.razorpayClone.common.enums.OrderStatus;
import com.razororpay.razorpayClone.common.exception.BusinessRuleViolationException;
import com.razororpay.razorpayClone.common.exception.DuplicateResourceException;
import com.razororpay.razorpayClone.common.exception.ResourceNotFoundException;
import com.razororpay.razorpayClone.payment.dto.request.CreateOrderRequest;
import com.razororpay.razorpayClone.payment.dto.response.OrderResponse;
import com.razororpay.razorpayClone.payment.dto.response.PaymentResponse;
import com.razororpay.razorpayClone.payment.entity.OrderRecord;
import com.razororpay.razorpayClone.payment.entity.Payment;
import com.razororpay.razorpayClone.payment.mapper.OrderMapper;
import com.razororpay.razorpayClone.payment.mapper.PaymentMapper;
import com.razororpay.razorpayClone.payment.repository.OrderRepository;
import com.razororpay.razorpayClone.payment.repository.PaymentRepository;
import com.razororpay.razorpayClone.payment.service.OrderService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.razororpay.razorpayClone.common.constant.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final PaymentMapper paymentMapper;

    @Value("${payment.order.default-order-expiry-minutes:30}")
    private int defaultOrderExpiryMinutes;

    @Override
    public OrderResponse create(UUID merchantId, CreateOrderRequest request) {
        if(request.receipt() != null && !orderRepository.existsByMerchantIdAndReceipt(merchantId, request.receipt())){
            throw new DuplicateResourceException(ORDER_RECEIPT_DUPLICATE.name(),  "Order with receipt already exists: " + request.receipt());
        }

        OrderRecord order = OrderRecord.builder()
                .receipt(request.receipt())
                .amount(request.amount())
                .notes(request.notes())
                .merchantId(merchantId)
                .orderStatus(OrderStatus.CREATED)
                .expiresAt(request.expiresAt() != null ? request.expiresAt() :
                        LocalDateTime.now().plusMinutes(defaultOrderExpiryMinutes))
                .build();

        order = orderRepository.save(order);
/*
*
* TODO:        publish kafka event about order creation
*
*/
        return orderMapper.toResponse(order);
    }


    public OrderResponse getById(UUID merchantId, UUID orderId){
        OrderRecord order = validateOrderExist(orderId,merchantId);
        return orderMapper.toResponse(order);
    }


    @Transactional
    public OrderResponse cancelOrder(UUID orderId, UUID merchantId){
        OrderRecord order = validateOrderExist(orderId,merchantId);


        if(Set.of(OrderStatus.CANCELLED , OrderStatus.PAID).contains(order.getOrderStatus())){
            throw new BusinessRuleViolationException(ORDER_CANNOT_CANCEL.name(), "Order cannot be cancel with status "+ order.getOrderStatus());
        }

        order.setOrderStatus(OrderStatus.CANCELLED);

        return orderMapper.toResponse(order);
    }

    public List<PaymentResponse> lisOfOrderForPayment(UUID orderId, UUID merchantId){
        OrderRecord order = validateOrderExist(orderId,merchantId);

        List<Payment> payment = paymentRepository.findByOrder_Id(order);

        return paymentMapper.toResponseList(payment);
    }



//    private method

    private OrderRecord validateOrderExist (UUID merchantId, UUID orderId){
        return   orderRepository.findByIdAndMerchantId(orderId,merchantId)
                .orElseThrow(() -> new ResourceNotFoundException(ORDER_NOT_FOUND.name(),orderId));
    }
}