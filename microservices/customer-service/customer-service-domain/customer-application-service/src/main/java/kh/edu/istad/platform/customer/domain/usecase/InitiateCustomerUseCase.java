package kh.edu.istad.platform.customer.domain.usecase;

import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerCommand;
import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.port.out.CustomerRepository;
import kh.edu.istad.platform.customer.domain.service.CustomerDomainService;
import kh.edu.istad.platform.customer.domain.valueobject.Email;
import kh.edu.istad.platform.customer.domain.valueobject.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class InitiateCustomerUseCase {

    private final CustomerDomainService customerDomainService;
    private final CustomerRepository customerRepository;

    public Mono<Customer> execute(InitiateCustomerCommand command){
        log.info("Initiate customer usecase: {}", command);

        Customer customer = Customer.builder()
                .username(command.username())
                .familyName(command.familyName())
                .givenName(command.givenName())
                .email(new Email(command.email()))
                .phoneNumber(new PhoneNumber(command.phoneNumber()))
                .build();

        // ❌ លុបបន្ទាត់នេះចេញ៖ customer.initiateCustomer();

        // ✅ ទុកឱ្យ Domain Service ជាអ្នកจัดการជំនួស
        customerDomainService.initiateCustomer(customer);

        return customerRepository.save(customer);
    }
}