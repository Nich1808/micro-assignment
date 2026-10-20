package kh.edu.istad.platform.customer.domain.port.out; // ឬដាក់ក្នុង domain port out package តាម project របស់អ្នក

import kh.edu.istad.platform.customer.domain.dto.CustomerFilterCriteria;
import kh.edu.istad.platform.customer.domain.dto.CustomerStatisticsResponse;
import kh.edu.istad.platform.customer.domain.entity.Customer;
import reactor.core.publisher.Flux;

public interface CustomerCustomRepository {
    Flux<Customer> findByCriteria(CustomerFilterCriteria criteria);
    Flux<CustomerStatisticsResponse> getCustomerStatistics();
}