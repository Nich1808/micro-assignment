package kh.edu.istad.platform.order.restapi;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class OrderPaymentClient {

    private final RestClient.Builder restClientBuilder;

    public OrderPaymentClient(
            @LoadBalanced RestClient.Builder restClientBuilder) {
        this.restClientBuilder = restClientBuilder;
    }

    public PaymentResponse createPayment(
            UUID orderId,
            UUID customerId,
            BigDecimal amount) {

        return restClientBuilder
                .baseUrl("http://payment-service")
                .build()
                .post()
                .uri("/api/payments")
                .body(new PaymentRequest(orderId, customerId, amount))
                .retrieve()
                .body(PaymentResponse.class);
    }


    public record PaymentRequest(
            UUID orderId,
            UUID customerId,
            BigDecimal amount) {}

    public record PaymentResponse(
            UUID paymentId,
            UUID orderId,
            UUID customerId,
            BigDecimal amount,
            String status,
            java.time.Instant createdAt) {}
}