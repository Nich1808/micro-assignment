package kh.edu.istad.platform.customer.bean;

import kh.edu.istad.platform.customer.domain.service.CustomerDomainService;
import kh.edu.istad.platform.customer.domain.service.CustomerDomainServiceImpl;
import kh.edu.istad.platform.customer.domain.usecase.DeactivateCustomerUseCase;
import kh.edu.istad.platform.customer.domain.usecase.InitiateCustomerUseCase;
import kh.edu.istad.platform.customer.domain.usecase.UpdateCustomerUseCase;
import kh.edu.istad.platform.customer.domain.port.out.CustomerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public CustomerDomainService customerDomainService() {
        return new CustomerDomainServiceImpl();
    }

    @Bean
    public InitiateCustomerUseCase initiateCustomerUseCase(
            CustomerDomainService customerDomainService,
            CustomerRepository customerRepository) {
        return new InitiateCustomerUseCase(customerDomainService, customerRepository);
    }

    @Bean
    public UpdateCustomerUseCase updateCustomerUseCase(CustomerRepository customerRepository, CustomerDomainService customerDomainService) {
        return new UpdateCustomerUseCase(customerRepository, customerDomainService);
    }

    @Bean
    public DeactivateCustomerUseCase deactivateCustomerUseCase(CustomerRepository customerRepository, CustomerDomainService customerDomainService) {
        return new DeactivateCustomerUseCase(customerRepository, customerDomainService);
    }

}