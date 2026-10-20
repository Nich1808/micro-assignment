package kh.edu.istad.platform.customer.domain.port.out;

import kh.edu.istad.common.domain.valueobject.SegmentId;
import kh.edu.istad.platform.customer.domain.entity.CustomerSegment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerSegmentRepository {
    Mono<CustomerSegment> save(CustomerSegment customerSegment);
    Mono<CustomerSegment> findById(SegmentId id);
    Flux<CustomerSegment> findAll();
    Mono<Void> deleteById(SegmentId id);
}