package kh.edu.istad.platform.payment.persistence;

import kh.edu.istad.platform.payment.application.PaymentRepository;
import kh.edu.istad.platform.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PaymentRepositoryAdapter implements PaymentRepository {

    private final PaymentMongoRepository paymentMongoRepository;

    @Override
    public Payment save(Payment payment) {
        log.info("Saving payment aggregate with identifier: {} for order: {}", payment.id(), payment.orderId());

        PaymentDocument paymentDocument = PaymentDocument.from(payment);
        PaymentDocument savedDocument = paymentMongoRepository.save(paymentDocument);

        return savedDocument.toDomain();
    }
}