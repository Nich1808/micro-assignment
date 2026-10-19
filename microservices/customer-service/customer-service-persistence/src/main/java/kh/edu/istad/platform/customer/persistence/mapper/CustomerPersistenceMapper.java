package kh.edu.istad.platform.customer.persistence.mapper;

import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.persistence.document.CustomerDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerPersistenceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customerId", expression = "java(customer.getId() != null ? customer.getId().value().toString() : null)")
    @Mapping(target = "email", expression = "java(customer.getEmail() != null ? customer.getEmail().value() : null)")
    @Mapping(target = "phoneNumber", expression = "java(customer.getPhoneNumber() != null ? customer.getPhoneNumber().value() : null)")
    CustomerDocument toDocument(Customer customer);

    @Mapping(target = "id", expression = "java(document.getCustomerId() != null ? new kh.edu.istad.common.domain.valueobject.CustomerId(java.util.UUID.fromString(document.getCustomerId())) : null)")
    @Mapping(target = "email", expression = "java(new kh.edu.istad.platform.customer.domain.valueobject.Email(document.getEmail()))") // 👈 កែ package ឱ្យត្រូវ
    @Mapping(target = "phoneNumber", expression = "java(new kh.edu.istad.platform.customer.domain.valueobject.PhoneNumber(document.getPhoneNumber()))")
    Customer toDomain(CustomerDocument document);
}