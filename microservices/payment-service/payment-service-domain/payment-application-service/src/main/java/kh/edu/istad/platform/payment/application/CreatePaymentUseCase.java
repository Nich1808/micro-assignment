package kh.edu.istad.platform.payment.application;

import kh.edu.istad.platform.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreatePaymentUseCase {

    private final PaymentRepository paymentRepository;

    public Payment execute(CreatePaymentCommand paymentCommand) {
        if (paymentCommand == null) {
            throw new IllegalArgumentException("Payment execution command cannot be null");
        }

        log.info("Processing payment creation for order: {} and customer: {}",
                paymentCommand.orderId(), paymentCommand.customerId());

        Payment newPayment = Payment.create(
                paymentCommand.orderId(),
                paymentCommand.customerId(),
                paymentCommand.amount()
        );

        return paymentRepository.save(newPayment);
    }
}