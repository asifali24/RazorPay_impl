package com.razororpay.razorpayClone.merchant.repository;

import com.razororpay.razorpayClone.merchant.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}