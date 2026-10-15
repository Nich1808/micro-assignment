package kh.edu.istad.platform.order.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class Order {

    public enum Status {
        PENDING, PAID, CANCELLED
    }

    private final UUID id;
    private final UUID customerId;
    private final UUID businessId;
    private final String deliveryAddress;
    private final List<OrderItem> items;
    private final BigDecimal total;
    private final Status status;
    private final Instant createdAt;

    public Order(UUID orderId, UUID customerId, UUID businessId, String deliveryAddress,
                 List<OrderItem> items, Status orderStatus, Instant createdAt) {

        this.id = Objects.requireNonNull(orderId, "Order identifier is mandatory");
        this.customerId = Objects.requireNonNull(customerId, "Customer identifier is required");
        this.businessId = Objects.requireNonNull(businessId, "Business identifier is required");

        if (deliveryAddress == null || deliveryAddress.trim().isEmpty()) {
            throw new IllegalArgumentException("Valid delivery address must be provided");
        }
        this.deliveryAddress = deliveryAddress.trim();

        if (items == null || items.isEmpty() || items.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Order must contain at least one valid item entry");
        }
        this.items = List.copyOf(items);

        this.total = this.items.stream()
                .map(OrderItem::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.status = Objects.requireNonNull(orderStatus, "Order status cannot be null");
        this.createdAt = Objects.requireNonNull(createdAt, "Creation timestamp is required");
    }

    public UUID id() {
        return id;
    }

    public UUID customerId() {
        return customerId;
    }

    public UUID businessId() {
        return businessId;
    }

    public String deliveryAddress() {
        return deliveryAddress;
    }

    public List<OrderItem> items() {
        return items;
    }

    public BigDecimal total() {
        return total;
    }

    public Status status() {
        return status;
    }

    public Instant createdAt() {
        return createdAt;
    }
}