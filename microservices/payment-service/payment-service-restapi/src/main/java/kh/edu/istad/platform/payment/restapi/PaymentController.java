package kh.edu.istad.platform.payment.restapi;

import kh.edu.istad.platform.payment.application.CreatePaymentCommand;
import kh.edu.istad.platform.payment.application.CreatePaymentUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final CreatePaymentUseCase createPaymentUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse processPaymentCreation(@Valid @RequestBody CreatePaymentRequest payload) {
        log.info("Handling payment submission for target order ID: {}", payload != null ? payload.orderId() : "null");

        var paymentEntity = createPaymentUseCase.execute(
                new CreatePaymentCommand(
                        payload.orderId(),
                        payload.customerId(),
                        payload.amount()
                )
        );

        return new PaymentResponse(
                paymentEntity.getId(),
                paymentEntity.getOrderId(),
                paymentEntity.getCustomerId(),
                paymentEntity.getAmount(),
                paymentEntity.getStatus().name(),
                paymentEntity.getCreatedAt()
        );
    }

    public record PaymentResponse(
            UUID paymentId,
            UUID orderId,
            UUID customerId,
            BigDecimal amount,
            String status,
            Instant createdAt
    ) {}
}