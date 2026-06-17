package com.razororpay.razorpayClone.merchant.repository;

import com.razororpay.razorpayClone.merchant.entity.Merchant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MerchantRepository extends JpaRepository<Merchant, UUID> {
    boolean existsByEmail(@NotNull(message = "Email cant be empty") @Email(message = "Email is invalid") String email);
}