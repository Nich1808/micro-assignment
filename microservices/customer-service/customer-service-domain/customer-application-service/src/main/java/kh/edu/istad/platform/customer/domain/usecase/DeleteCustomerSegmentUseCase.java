package kh.edu.istad.platform.customer.domain.usecase;

import kh.edu.istad.common.domain.valueobject.SegmentId;
import kh.edu.istad.platform.customer.domain.exception.CustomerSegmentNotFoundException;
import kh.edu.istad.platform.customer.domain.port.out.CustomerSegmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteCustomerSegmentUseCase {

    private final CustomerSegmentRepository customerSegmentRepository;

    public Mono<Void> execute(UUID id) {
        SegmentId segmentId = new SegmentId(id);
        return customerSegmentRepository.findById(segmentId)
                .switchIfEmpty(Mono.error(new CustomerSegmentNotFoundException(id)))
                .flatMap(segment -> customerSegmentRepository.deleteById(segmentId));
    }
}