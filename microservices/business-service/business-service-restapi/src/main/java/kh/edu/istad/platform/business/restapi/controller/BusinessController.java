package kh.edu.istad.platform.business.restapi.controller;

import kh.edu.istad.platform.business.application.CreateBusinessCommand;
import kh.edu.istad.platform.business.application.CreateBusinessUseCase;
import jakarta.validation.Valid;
import kh.edu.istad.platform.business.restapi.dto.CreateBusinessRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/businesses")
@RequiredArgsConstructor
public class BusinessController {

    private final CreateBusinessUseCase createBusinessUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BusinessResponse> registerNewBusiness(@Valid @RequestBody CreateBusinessRequest payload) {
        log.info("Processing reactive payload for new business registration...");

        CreateBusinessCommand command = new CreateBusinessCommand(
                payload.name(),
                payload.email(),
                payload.address()
        );

        return createBusinessUseCase.execute(command)
                .map(entity -> new BusinessResponse(
                        entity.id(),
                        entity.name(),
                        entity.email(),
                        entity.address(),
                        entity.status().name(),
                        entity.createdAt()
                ));
    }

    public record BusinessResponse(
            UUID businessId,
            String name,
            String email,
            String address,
            String status,
            Instant createdAt
    ) {}
}