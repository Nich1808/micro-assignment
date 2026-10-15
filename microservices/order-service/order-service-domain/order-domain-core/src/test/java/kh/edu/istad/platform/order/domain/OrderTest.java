package kh.edu.istad.platform.order.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderTest {

    @Test
    @DisplayName("Should successfully calculate aggregate total and reject orders with empty item collections")
    void testOrderTotalCalculationAndEmptyItemValidation() {
        // Arrange
        UUID productId = UUID.randomUUID();
        int requestedQuantity = 3;
        BigDecimal itemUnitPrice = new BigDecimal("2.50");

        OrderItem sampleItem = new OrderItem(productId, requestedQuantity, itemUnitPrice);

        UUID orderIdentifier = UUID.randomUUID();
        UUID customerIdentifier = UUID.randomUUID();
        UUID businessIdentifier = UUID.randomUUID();
        String shippingDestination = "Phnom Penh";
        Instant timestamp = Instant.now();

        // Act
        Order activeOrder = new Order(
                orderIdentifier,
                customerIdentifier,
                businessIdentifier,
                shippingDestination,
                List.of(sampleItem),
                Order.Status.PENDING,
                timestamp
        );

        // Assert - Total computation verification
        assertEquals(new BigDecimal("7.50"), activeOrder.total());

        // Assert - Empty items validation exception
        assertThrows(IllegalArgumentException.class, () ->
                new Order(
                        orderIdentifier,
                        customerIdentifier,
                        businessIdentifier,
                        shippingDestination,
                        List.of(),
                        Order.Status.PENDING,
                        timestamp
                )
        );
    }
}