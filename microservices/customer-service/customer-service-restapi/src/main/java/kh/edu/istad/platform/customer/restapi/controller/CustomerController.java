package kh.edu.istad.platform.customer.restapi.controller;

import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerResult;
import kh.edu.istad.platform.customer.domain.dto.UpdateCustomerCommand;
import kh.edu.istad.platform.customer.domain.dto.UpdateCustomerResult;
import kh.edu.istad.platform.customer.domain.event.CustomerDeactivatedEvent;
import kh.edu.istad.platform.customer.domain.exception.CustomerDomainException;
import kh.edu.istad.platform.customer.domain.exception.CustomerNotFoundException;
import kh.edu.istad.platform.customer.domain.usecase.DeactivateCustomerUseCase;
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

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    private final InitiateCustomerUseCase initiateCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final DeactivateCustomerUseCase deactivateCustomerUseCase;
    private final CustomerWebMapper customerWebMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerInitiateResponse createNewCustomer(
            @Valid @RequestBody CustomerInitiateRequest payload
    ) {
        log.info("Processing creation request for new customer...");
        InitiateCustomerResult outcome = initiateCustomerUseCase.execute(
                customerWebMapper.toCommand(payload)
        );
        return customerWebMapper.toResponse(outcome);
    }

    @PutMapping("/{id}")
    public CustomerUpdateResponse modifyCustomer(
            @PathVariable("id") UUID customerId,
            @Valid @RequestBody CustomerUpdateRequest updatePayload
    ) {
        log.info("Processing update request for customer ID: {}", customerId);
        UpdateCustomerResult outcome = updateCustomerUseCase.execute(
                new UpdateCustomerCommand(customerId, updatePayload.familyName(), updatePayload.givenName())
        );
        return customerWebMapper.toResponse(outcome);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleCustomerNotFound() {
        log.warn("Target customer could not be found in the system.");
    }

    @ExceptionHandler(CustomerDomainException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleCustomerDomainConflict() {
        log.error("A domain rule violation conflict occurred.");
    }

    @PutMapping("/{id}/deactivate")
    public CustomerDeactivatedEvent deactivateAccount(
            @PathVariable("id") UUID customerId
    ) {
        log.info("Processing deactivation request for customer ID: {}", customerId);
        return deactivateCustomerUseCase.execute(customerId);
    }
}