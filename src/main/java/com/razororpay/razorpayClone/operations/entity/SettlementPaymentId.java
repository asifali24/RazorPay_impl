package com.razororpay.razorpayClone.operations.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class SettlementPaymentId implements Serializable {

    private UUID settlementId;
    private UUID paymentId;

    public SettlementPaymentId() {}

    public SettlementPaymentId(UUID settlementId, UUID paymentId) {
        this.settlementId = settlementId;
        this.paymentId = paymentId;
    }

    public UUID getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(UUID settlementId) {
        this.settlementId = settlementId;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SettlementPaymentId that = (SettlementPaymentId) o;
        return Objects.equals(settlementId, that.settlementId) &&
               Objects.equals(paymentId, that.paymentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(settlementId, paymentId);
    }
}
