package kh.edu.istad.platform.order.application;

import kh.edu.istad.platform.order.domain.Order;
import kh.edu.istad.platform.order.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateOrderUseCase {

    private final OrderRepository orderRepository;
    public Order execute(CreateOrderCommand payload) {
        log.info("Processing order creation command for customer ID: {}", payload != null ? payload.customerId() : "null");

        if (payload == null || payload.items() == null || payload.items().isEmpty()) {
            throw new IllegalArgumentException("Order items payload is mandatory and cannot be empty");
        }

        List<OrderItem> orderItemList = payload.items().stream().map(itemDto -> {
            if (itemDto == null) {
                throw new IllegalArgumentException("Encountered a null order item entry");
            }
            return new OrderItem(
                    itemDto.productId(),
                    itemDto.quantity(),
                    itemDto.unitPrice()
            );
        }).toList();

        Order newOrder = new Order(
                UUID.randomUUID(),
                payload.customerId(),
                payload.businessId(),
                payload.deliveryAddress(),
                orderItemList,
                Order.Status.PENDING,
                Instant.now()
        );

        return orderRepository.save(newOrder);
    }
}