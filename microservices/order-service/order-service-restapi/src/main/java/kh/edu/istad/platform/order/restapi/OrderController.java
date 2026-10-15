package kh.edu.istad.platform.order.restapi;

import jakarta.validation.Valid;
import kh.edu.istad.platform.order.application.CreateOrderCommand;
import kh.edu.istad.platform.order.application.CreateOrderUseCase;
import kh.edu.istad.platform.order.domain.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest payload) {
        log.info("Received request to process order creation for customer: {}", payload.customerId());

        var commandItems = payload.items().stream()
                .map(item -> new CreateOrderCommand.Item(
                        item.productId(),
                        item.quantity(),
                        item.unitPrice()
                ))
                .toList();

        CreateOrderCommand orderCommand = new CreateOrderCommand(
                payload.customerId(),
                payload.businessId(),
                payload.deliveryAddress(),
                commandItems
        );

        Order createdOrder = createOrderUseCase.execute(orderCommand);

        return new OrderResponse(
                createdOrder.id(),
                createdOrder.customerId(),
                createdOrder.businessId(),
                createdOrder.total(),
                createdOrder.status().name(),
                createdOrder.createdAt()
        );
    }

    public record OrderResponse(
            UUID orderId,
            UUID customerId,
            UUID businessId,
            BigDecimal total,
            String status,
            Instant createdAt
    ) {}
}