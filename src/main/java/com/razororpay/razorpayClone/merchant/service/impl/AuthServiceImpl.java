package com.razororpay.razorpayClone.merchant.service.impl;

import com.razororpay.razorpayClone.common.enums.MerchantStatus;
import com.razororpay.razorpayClone.common.enums.UserRole;
import com.razororpay.razorpayClone.common.exception.DuplicateResourceException;
import com.razororpay.razorpayClone.merchant.dto.request.MerchantSignupRequest;
import com.razororpay.razorpayClone.merchant.dto.response.MerchantSignupResponse;
import com.razororpay.razorpayClone.merchant.entity.AppUser;
import com.razororpay.razorpayClone.merchant.entity.Merchant;
import com.razororpay.razorpayClone.merchant.repository.AppUserRepository;
import com.razororpay.razorpayClone.merchant.repository.MerchantRepository;
import com.razororpay.razorpayClone.merchant.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final AppUserRepository appUserRepository;
    private final MerchantRepository merchantRepository;


    @Override
    @Transactional
    public MerchantSignupResponse signup(MerchantSignupRequest merchant) {
        if(merchantRepository.existsByEmail(merchant.email())){
            throw new DuplicateResourceException("DUPLICATE_MERCHANT_EMAIL" ,
                    "Merchant already exist with email "+ merchant.email());
        }

        Merchant newMerchant = Merchant.builder()
                .businessName(merchant.businessName())
                .name(merchant.name())
                .email(merchant.email())
                .status(MerchantStatus.PENDING_KYC)
                .businessType(merchant.businessType())
                .build();

        newMerchant = merchantRepository.save(newMerchant); // created before b/w we need merchant id

        AppUser owner = AppUser.builder()
                .merchant(newMerchant)
                .email(merchant.email())
                .passwordHash(merchant.password()) //TODO to be hashed
                .role(UserRole.OWNER)
                .build();

        owner = appUserRepository.save(owner);



        return new MerchantSignupResponse(
                newMerchant.getId() ,
                newMerchant.getName(),
                newMerchant.getEmail(),
                newMerchant.getBusinessName(),
                newMerchant.getBusinessType(),
                newMerchant.getStatus()
                );
    }
}
