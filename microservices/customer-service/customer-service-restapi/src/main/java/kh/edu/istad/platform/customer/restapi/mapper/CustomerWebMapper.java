package kh.edu.istad.platform.customer.restapi.mapper;

import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerCommand;
import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerResult;
import kh.edu.istad.platform.customer.domain.dto.UpdateCustomerCommand;
import kh.edu.istad.platform.customer.domain.dto.UpdateCustomerResult;
import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.valueobject.Email;
import kh.edu.istad.platform.customer.domain.valueobject.PhoneNumber;
import kh.edu.istad.platform.customer.restapi.dto.CustomerInitiateRequest;
import kh.edu.istad.platform.customer.restapi.dto.CustomerInitiateResponse;
import kh.edu.istad.platform.customer.restapi.dto.CustomerUpdateRequest;
import kh.edu.istad.platform.customer.restapi.dto.CustomerUpdateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomerWebMapper {

    InitiateCustomerCommand toCommand(CustomerInitiateRequest request);

    @Mapping(target = "customerId", source = "id.value")
    CustomerInitiateResponse toResponse(Customer customer);

    CustomerInitiateResponse toResponse(InitiateCustomerResult result);

    CustomerUpdateResponse toResponse(UpdateCustomerResult result);

    @Mapping(target = "customerId", source = "id")
    UpdateCustomerCommand toCommand(UUID id, CustomerUpdateRequest request);

    default String map(Email email) {
        return email != null ? email.value() : null;
    }

    default String map(PhoneNumber phoneNumber) {
        return phoneNumber != null ? phoneNumber.value() : null;
    }
}