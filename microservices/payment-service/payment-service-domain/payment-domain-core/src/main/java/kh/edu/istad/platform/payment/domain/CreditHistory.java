package kh.edu.istad.platform.payment.domain;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record CreditHistory(
        UUID creditEntryId,
        String action,
        Instant createdAt
) {
    public CreditHistory {
        Objects.requireNonNull(creditEntryId, "Credit entry identifier is required");

        if (action == null || action.trim().isEmpty()) {
            throw new IllegalArgumentException("Action description cannot be null or blank");
        }

        Objects.requireNonNull(createdAt, "Creation timestamp is required");
    }
}