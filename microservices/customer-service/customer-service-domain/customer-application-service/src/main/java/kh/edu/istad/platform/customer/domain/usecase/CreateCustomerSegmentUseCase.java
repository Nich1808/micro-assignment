package kh.edu.istad.platform.customer.domain.usecase;

import kh.edu.istad.common.domain.valueobject.SegmentId;
import kh.edu.istad.platform.customer.domain.dto.CreateSegmentCommand;
import kh.edu.istad.platform.customer.domain.dto.CreateSegmentResult;
import kh.edu.istad.platform.customer.domain.entity.CustomerSegment;
import kh.edu.istad.platform.customer.domain.port.out.CustomerSegmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CreateCustomerSegmentUseCase {

    private final CustomerSegmentRepository customerSegmentRepository;

    public Mono<CreateSegmentResult> execute(CreateSegmentCommand command) {
        CustomerSegment segment = CustomerSegment.builder()
                .id(SegmentId.random())
                .name(command.name())
                .description(command.description())
                .build();

        return customerSegmentRepository.save(segment)
                .map(saved -> new CreateSegmentResult(
                        saved.getId().value(),
                        saved.getName(),
                        saved.getDescription()
                ));
    }
}