package kh.edu.istad.platform.customer.persistence.mapper;

import kh.edu.istad.common.domain.valueobject.CustomerId;
import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.valueobject.Email;
import kh.edu.istad.platform.customer.domain.valueobject.PhoneNumber;
import kh.edu.istad.platform.customer.persistence.document.CustomerDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomerPersistenceMapper {

    @Mapping(target = "customerId", source = "id.value")
    @Mapping(target = "email", source = "email.value")
    @Mapping(target = "phoneNumber", source = "phoneNumber.value")
    CustomerDocument toDocument(Customer customer);

    @Mapping(target = "id", expression = "java(new CustomerId(UUID.fromString(document.customerId())))")
    @Mapping(target = "email", expression = "java(new Email(document.email()))")
    @Mapping(target = "phoneNumber", expression = "java(new PhoneNumber(document.phoneNumber()))")
    Customer toDomain(CustomerDocument document);
}