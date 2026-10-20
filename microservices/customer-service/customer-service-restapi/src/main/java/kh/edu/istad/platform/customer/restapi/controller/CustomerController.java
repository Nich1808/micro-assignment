package kh.edu.istad.platform.customer.restapi.controller;

import kh.edu.istad.platform.customer.domain.dto.CustomerFilterCriteria;
import kh.edu.istad.platform.customer.domain.dto.UpdateCustomerCommand;
import kh.edu.istad.platform.customer.domain.usecase.DeactivateCustomerUseCase;
import kh.edu.istad.platform.customer.domain.usecase.FindCustomerUseCase;
import kh.edu.istad.platform.customer.domain.usecase.InitiateCustomerUseCase;
import kh.edu.istad.platform.customer.domain.usecase.UpdateCustomerUseCase;
import kh.edu.istad.platform.customer.restapi.dto.CustomerInitiateRequest;
import kh.edu.istad.platform.customer.restapi.dto.CustomerInitiateResponse;
import kh.edu.istad.platform.customer.restapi.dto.CustomerUpdateRequest;
import kh.edu.istad.platform.customer.restapi.dto.CustomerUpdateResponse;
import kh.edu.istad.platform.customer.restapi.mapper.CustomerWebMapper;
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
@RequestMapping("/api/customers")
public class CustomerController {

    private final InitiateCustomerUseCase initiateCustomerUseCase;
    private final FindCustomerUseCase findCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final DeactivateCustomerUseCase deactivateCustomerUseCase;
    private final CustomerWebMapper customerWebMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<CustomerInitiateResponse> initiateCustomer(
            @RequestBody @Valid CustomerInitiateRequest customerInitiateRequest
    ){
        return initiateCustomerUseCase.execute(
                customerWebMapper.toCommand(customerInitiateRequest)
        ).map(customerWebMapper::toResponse);
    }

    @GetMapping
    public Flux<CustomerInitiateResponse> getAllCustomers() {
        return findCustomerUseCase.findAll()
                .map(customerWebMapper::toResponse);
    }

    // 🚀 Dynamic Filtering Endpoint ថ្មីដែលបានបន្ថែម
    @GetMapping("/filter")
    public Flux<CustomerInitiateResponse> filterCustomers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String segmentId
    ) {
        CustomerFilterCriteria criteria = CustomerFilterCriteria.builder()
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .status(status)
                .segmentId(segmentId)
                .build();

        return findCustomerUseCase.findByCriteria(criteria)
                .map(customerWebMapper::toResponse);
    }

    @GetMapping("/{id}")
    public Mono<CustomerInitiateResponse> getCustomerById(@PathVariable("id") String id) {
        return findCustomerUseCase.findById(id)
                .map(customerWebMapper::toResponse);
    }

    @PutMapping("/{id}")
    public Mono<CustomerUpdateResponse> updateCustomer(
            @PathVariable("id") String id,
            @RequestBody @Valid CustomerUpdateRequest request
    ) {
        UpdateCustomerCommand command = customerWebMapper.toCommand(UUID.fromString(id), request);

        return updateCustomerUseCase.execute(command)
                .map(customerWebMapper::toResponse);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public Mono<Void> deactivateCustomer(@PathVariable("id") String id) {
        return deactivateCustomerUseCase.execute(UUID.fromString(id))
                .then();
    }
}