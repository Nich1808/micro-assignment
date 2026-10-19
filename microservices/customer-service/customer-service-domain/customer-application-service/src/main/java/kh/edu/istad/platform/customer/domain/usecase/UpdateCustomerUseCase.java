package kh.edu.istad.platform.customer.domain.usecase;

import kh.edu.istad.common.domain.valueobject.CustomerId;
import kh.edu.istad.platform.customer.domain.dto.UpdateCustomerCommand;
import kh.edu.istad.platform.customer.domain.dto.UpdateCustomerResult;
import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.exception.CustomerNotFoundException;
import kh.edu.istad.platform.customer.domain.port.out.CustomerRepository;
import kh.edu.istad.platform.customer.domain.service.CustomerDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UpdateCustomerUseCase {

    private final CustomerRepository customerRepository;
    private final CustomerDomainService customerDomainService;

    public Mono<UpdateCustomerResult> execute(UpdateCustomerCommand command) {
        return customerRepository.findById(new CustomerId(command.customerId()))
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(command.customerId())))
                .flatMap(customer -> {
                    customerDomainService.updateCustomer(customer, command.familyName(), command.givenName());
                    return customerRepository.save(customer);
                })
                .map(customer -> new UpdateCustomerResult(
                        customer.getId().value(),
                        customer.getUsername(),
                        customer.getFamilyName(),
                        customer.getGivenName(),
                        customer.getEmail().value(),
                        customer.getPhoneNumber().value()
                ));
    }
}