package kh.edu.istad.platform.customer.restapi.controller;

import kh.edu.istad.platform.customer.domain.dto.UpdateSegmentCommand;
import kh.edu.istad.platform.customer.domain.usecase.CreateCustomerSegmentUseCase;
import kh.edu.istad.platform.customer.domain.usecase.DeleteCustomerSegmentUseCase;
import kh.edu.istad.platform.customer.domain.usecase.FindCustomerSegmentUseCase;
import kh.edu.istad.platform.customer.domain.usecase.UpdateCustomerSegmentUseCase;
import kh.edu.istad.platform.customer.restapi.dto.CustomerSegmentCreateRequest;
import kh.edu.istad.platform.customer.restapi.dto.CustomerSegmentResponse;
import kh.edu.istad.platform.customer.restapi.dto.CustomerSegmentUpdateRequest;
import kh.edu.istad.platform.customer.restapi.mapper.CustomerSegmentWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer-segments")
public class CustomerSegmentController {

    private final CreateCustomerSegmentUseCase createCustomerSegmentUseCase;
    private final FindCustomerSegmentUseCase findCustomerSegmentUseCase;
    private final UpdateCustomerSegmentUseCase updateCustomerSegmentUseCase;
    private final DeleteCustomerSegmentUseCase deleteCustomerSegmentUseCase;
    private final CustomerSegmentWebMapper customerSegmentWebMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<CustomerSegmentResponse> createSegment(
            @RequestBody @Valid CustomerSegmentCreateRequest request
    ) {
        return createCustomerSegmentUseCase.execute(
                customerSegmentWebMapper.toCommand(request)
        ).map(customerSegmentWebMapper::toResponse);
    }

    @GetMapping
    public Flux<CustomerSegmentResponse> getAllSegments() {
        return findCustomerSegmentUseCase.findAll()
                .map(customerSegmentWebMapper::toResponse);
    }

    @GetMapping("/{id}")
    public Mono<CustomerSegmentResponse> getSegmentById(@PathVariable("id") UUID id) {
        return findCustomerSegmentUseCase.findById(id)
                .map(customerSegmentWebMapper::toResponse);
    }

    @PutMapping("/{id}")
    public Mono<CustomerSegmentResponse> updateSegment(
            @PathVariable("id") UUID id,
            @RequestBody @Valid CustomerSegmentUpdateRequest request
    ) {
        UpdateSegmentCommand command = customerSegmentWebMapper.toCommand(id, request);
        return updateCustomerSegmentUseCase.execute(command)
                .map(customerSegmentWebMapper::toResponse);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public Mono<Void> deleteSegment(@PathVariable("id") UUID id) {
        return deleteCustomerSegmentUseCase.execute(id);
    }
}