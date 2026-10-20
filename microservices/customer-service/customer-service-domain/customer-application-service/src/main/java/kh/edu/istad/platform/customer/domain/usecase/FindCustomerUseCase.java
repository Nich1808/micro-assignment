package kh.edu.istad.platform.customer.domain.usecase;

import kh.edu.istad.common.domain.valueobject.CustomerId;
import kh.edu.istad.platform.customer.domain.dto.CustomerFilterCriteria;
import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.exception.CustomerDomainException;
import kh.edu.istad.platform.customer.domain.port.out.CustomerCustomRepository;
import kh.edu.istad.platform.customer.domain.port.out.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindCustomerUseCase {

    private final CustomerRepository customerRepository;
    private final CustomerCustomRepository customerCustomRepository; // 🚀 បន្ថែម Custom Repository

    public Flux<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Mono<Customer> findById(String id) {
        CustomerId customerId = new CustomerId(UUID.fromString(id));
        return customerRepository.findById(customerId)
                .switchIfEmpty(Mono.error(new CustomerDomainException("Customer not found with id: " + id)));
    }


    public Flux<Customer> findByCriteria(CustomerFilterCriteria criteria) {
        return customerCustomRepository.findByCriteria(criteria);
    }
}