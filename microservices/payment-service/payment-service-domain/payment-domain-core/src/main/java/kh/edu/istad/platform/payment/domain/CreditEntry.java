package kh.edu.istad.platform.payment.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public record CreditEntry(
        UUID id,
        UUID customerId,
        BigDecimal amount,
        TransactionType type
) {
    public enum TransactionType {
        DEBIT,
        CREDIT
    }

    public CreditEntry {
        Objects.requireNonNull(id, "Credit entry identifier is required");
        Objects.requireNonNull(customerId, "Customer identifier is required");
        Objects.requireNonNull(type, "Transaction type cannot be null");

        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Credit entry amount must be a positive value");
        }
    }
}