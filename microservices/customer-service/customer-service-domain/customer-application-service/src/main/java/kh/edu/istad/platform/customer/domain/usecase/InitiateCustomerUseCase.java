package kh.edu.istad.platform.customer.domain.usecase;

import kh.edu.istad.common.domain.valueobject.CustomerId;
import kh.edu.istad.platform.customer.domain.valueobject.Email;
import kh.edu.istad.platform.customer.domain.valueobject.PhoneNumber; // សូមផ្ទៀងផ្ទាត់ package របស់ PhoneNumber
import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerCommand;
import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerResult;
import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.port.out.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class InitiateCustomerUseCase {

    private final CustomerRepository customerRepository;

    public Mono<InitiateCustomerResult> execute(InitiateCustomerCommand command) {
        log.info("Initiating onboarding workflow for new customer entry...");

        Customer customer = Customer.builder()
                .id(new CustomerId(UUID.randomUUID()))
                .username(command.username())
                .familyName(command.familyName())
                .givenName(command.givenName())
                .email(new Email(command.email()))
                .phoneNumber(new PhoneNumber(command.phoneNumber())) // Wrap ទៅជា PhoneNumber Object
                .build();

        return customerRepository.save(customer)
                .map(savedCustomer -> new InitiateCustomerResult(
                        savedCustomer.getId().value(),
                        savedCustomer.getUsername(),
                        savedCustomer.getFamilyName(),
                        savedCustomer.getGivenName(),
                        savedCustomer.getEmail().value(),
                        savedCustomer.getPhoneNumber().value() // Unwrap យកតម្លៃ String មកវិញ (.value() ឬ .toString())
                ));
    }
}