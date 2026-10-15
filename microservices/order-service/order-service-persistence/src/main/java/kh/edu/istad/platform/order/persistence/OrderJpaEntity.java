package kh.edu.istad.platform.order.persistence;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import kh.edu.istad.platform.order.domain.Order;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderJpaEntity {

    @Id
    @Column(name = "order_id", nullable = false)
    private UUID id;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "business_id", nullable = false)
    private UUID businessId;

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @Column(name = "total", nullable = false, precision = 19, scale = 4)
    private BigDecimal total;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    @OrderColumn(name = "line_number")
    private List<OrderItemJpaValue> items = new ArrayList<>();

    public static OrderJpaEntity from(Order domainModel) {
        OrderJpaEntity persistenceModel = new OrderJpaEntity();
        persistenceModel.id = domainModel.id();
        persistenceModel.customerId = domainModel.customerId();
        persistenceModel.businessId = domainModel.businessId();
        persistenceModel.deliveryAddress = domainModel.deliveryAddress();
        persistenceModel.total = domainModel.total();
        persistenceModel.status = domainModel.status().name();
        persistenceModel.createdAt = domainModel.createdAt();

        persistenceModel.items = domainModel.items().stream()
                .map(item -> new OrderItemJpaValue(
                        item.productId(),
                        item.quantity(),
                        item.unitPrice()
                ))
                .toList();

        return persistenceModel;
    }
}