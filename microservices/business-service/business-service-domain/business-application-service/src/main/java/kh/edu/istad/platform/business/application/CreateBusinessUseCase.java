package kh.edu.istad.platform.business.application;

import kh.edu.istad.platform.business.domain.Business;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateBusinessUseCase {

    private final BusinessRepository businessRepository;

    public Mono<Business> execute(CreateBusinessCommand payload) {
        log.info("Executing business registration orchestration workflow...");

        return Mono.defer(() -> {
            Business businessAggregate = new Business(
                    UUID.randomUUID(),
                    payload.name(),
                    payload.email(),
                    payload.address(),
                    Business.Status.ACTIVE,
                    Instant.now()
            );
            return businessRepository.save(businessAggregate);
        });
    }
}