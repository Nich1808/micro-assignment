package kh.edu.istad.platform.customer.domain.usecase;

import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerCommand;
import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerResult;
import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.port.out.CustomerRepository;
import kh.edu.istad.platform.customer.domain.service.CustomerDomainService;
import kh.edu.istad.platform.customer.domain.valueobject.Email;
import kh.edu.istad.platform.customer.domain.valueobject.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InitiateCustomerUseCase {

    private final CustomerDomainService customerDomainService;
    private final CustomerRepository customerRepository;

    public InitiateCustomerResult execute(InitiateCustomerCommand command){
        log.info("Initiate customer usecase: {}", command);

        // ១. បង្កើត Customer Entity ពី Command
        Customer customer = Customer.builder()
                .username(command.username())
                .familyName(command.familyName())
                .givenName(command.givenName())
                .email(new Email(command.email()))           // ឬ Email.of(command.email())
                .phoneNumber(new PhoneNumber(command.phoneNumber())) // ឬ PhoneNumber.of(command.phoneNumber())
                .build();

        // ២. Business Logic (បង្កើត ID និង Status ACTIVE)
        customer.initiateCustomer();

        // ៣. រក្សាទុកក្នុង Database (ប្រើ .block() ព្រោះ Controller ត្រូវការលទ្ធផលផ្ទាល់)
        Customer savedCustomer = customerRepository.save(customer).block();

        if (savedCustomer == null) {
            throw new RuntimeException("Failed to save customer");
        }

        // ៤. បង្កើតនិងត្រឡប់ Result ទៅកាន់ Controller
        return new InitiateCustomerResult(
                savedCustomer.getId().value(),
                savedCustomer.getUsername(),
                savedCustomer.getFamilyName(),
                savedCustomer.getGivenName(),
                savedCustomer.getEmail(),
                savedCustomer.getPhoneNumber(),
                savedCustomer.getStatus()
        );
    }
}