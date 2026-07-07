package com.razororpay.razorpayClone.payment.service.impl;

import com.razororpay.razorpayClone.common.enums.OrderStatus;
import com.razororpay.razorpayClone.common.enums.PaymentMethod;
import com.razororpay.razorpayClone.common.enums.PaymentStatus;
import com.razororpay.razorpayClone.common.exception.BusinessRuleViolationException;
import com.razororpay.razorpayClone.common.exception.ResourceNotFoundException;
import com.razororpay.razorpayClone.payment.dto.request.PaymentInitRequest;
import com.razororpay.razorpayClone.payment.dto.response.PaymentResponse;
import com.razororpay.razorpayClone.payment.entity.OrderRecord;
import com.razororpay.razorpayClone.payment.entity.Payment;
import com.razororpay.razorpayClone.payment.gateway.PaymentAdapter;
import com.razororpay.razorpayClone.payment.gateway.PaymentGateWayRouter;
import com.razororpay.razorpayClone.payment.gateway.dto.PaymentRequest;
import com.razororpay.razorpayClone.payment.gateway.dto.PaymentResult;
import com.razororpay.razorpayClone.payment.mapper.PaymentMapper;
import com.razororpay.razorpayClone.payment.repository.OrderRepository;
import com.razororpay.razorpayClone.payment.repository.PaymentRepository;
import com.razororpay.razorpayClone.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentAdapter paymentAdapter;
    private final PaymentGateWayRouter paymentGateWayRouter;
    private PaymentMapper paymentMapper;
//    private Payment


    @Override
    @Transactional
    public PaymentResponse init(UUID merchantId, PaymentInitRequest paymentInitRequest) {

        OrderRecord order = orderRepository.findByIdAndMerchantId(paymentInitRequest.orderId(), merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", paymentInitRequest.orderId()));

        if(!Set.of(OrderStatus.CREATED , OrderStatus.ATTEMPTED).contains(order.getOrderStatus())){
            throw new BusinessRuleViolationException("ORDER_NOT_PAYABLE",
                    "Order cannot accept payment in status: "+order.getOrderStatus());
        }

        order.setOrderStatus(OrderStatus.ATTEMPTED);
        order.setAttempts(order.getAttempts()+1);

        Payment payment = Payment.builder()
                .order(order)
                .merchantId(merchantId)
                .amount(order.getAmount())
                .status(PaymentStatus.CREATED)
                .method(paymentInitRequest.method())
                .methodDetails(paymentInitRequest.methodDetails())
                .build();
        payment = paymentRepository.save(payment);


        PaymentRequest paymentRequest = new PaymentRequest(payment.getId(),
                paymentInitRequest.orderId(), merchantId,
                order.getAmount(), paymentInitRequest.method(),
                paymentInitRequest.methodDetails());

        PaymentResult paymentResult = paymentGateWayRouter.init(paymentRequest);

        if(paymentResult instanceof PaymentResult.Pending(String registrationRef)){
            payment.setProcessorReference(registrationRef);
        }else if(paymentResult instanceof PaymentResult.Failure(String errorCode, String errorDescription)){
            payment.setStatus(PaymentStatus.FAILED);
            payment.setErrorCode(errorCode);
            payment.setErrorDescription(errorDescription);
        }


        payment = paymentRepository.save(payment);
        orderRepository.save(order);
        return paymentMapper.toResponse(payment);
    }
}
