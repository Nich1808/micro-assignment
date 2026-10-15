package kh.edu.istad.platform.order.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public record OrderItem(
        UUID productId,
        int quantity,
        BigDecimal unitPrice
) {
    public OrderItem {
        Objects.requireNonNull(productId, "Product identifier cannot be null");
        Objects.requireNonNull(unitPrice, "Unit price cannot be null");

        if (quantity <= 0 || unitPrice.signum() <= 0) {
            throw new IllegalArgumentException("Item quantity and unit price must have positive values");
        }
    }

    public BigDecimal subtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}