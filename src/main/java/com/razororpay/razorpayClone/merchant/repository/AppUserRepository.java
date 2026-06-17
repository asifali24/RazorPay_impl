package com.razororpay.razorpayClone.merchant.repository;

import com.razororpay.razorpayClone.merchant.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
}