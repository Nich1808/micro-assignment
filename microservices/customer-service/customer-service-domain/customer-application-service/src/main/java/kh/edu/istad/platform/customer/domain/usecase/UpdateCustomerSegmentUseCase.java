package kh.edu.istad.platform.customer.domain.usecase;

import kh.edu.istad.common.domain.valueobject.SegmentId;
import kh.edu.istad.platform.customer.domain.dto.UpdateSegmentCommand;
import kh.edu.istad.platform.customer.domain.dto.UpdateSegmentResult;
import kh.edu.istad.platform.customer.domain.exception.CustomerSegmentNotFoundException;
import kh.edu.istad.platform.customer.domain.port.out.CustomerSegmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UpdateCustomerSegmentUseCase {

    private final CustomerSegmentRepository customerSegmentRepository;

    public Mono<UpdateSegmentResult> execute(UpdateSegmentCommand command) {
        return customerSegmentRepository.findById(new SegmentId(command.segmentId()))
                .switchIfEmpty(Mono.error(new CustomerSegmentNotFoundException(command.segmentId())))
                .flatMap(segment -> {
                    segment.update(command.name(), command.description());
                    return customerSegmentRepository.save(segment);
                })
                .map(saved -> new UpdateSegmentResult(
                        saved.getId().value(),
                        saved.getName(),
                        saved.getDescription()
                ));
    }
}