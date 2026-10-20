package kh.edu.istad.platform.customer.domain.usecase;

import kh.edu.istad.common.domain.valueobject.SegmentId;
import kh.edu.istad.platform.customer.domain.entity.CustomerSegment;
import kh.edu.istad.platform.customer.domain.exception.CustomerSegmentNotFoundException;
import kh.edu.istad.platform.customer.domain.port.out.CustomerSegmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindCustomerSegmentUseCase {

    private final CustomerSegmentRepository customerSegmentRepository;

    public Mono<CustomerSegment> findById(UUID id) {
        return customerSegmentRepository.findById(new SegmentId(id))
                .switchIfEmpty(Mono.error(new CustomerSegmentNotFoundException(id)));
    }

    public Flux<CustomerSegment> findAll() {
        return customerSegmentRepository.findAll();
    }
}