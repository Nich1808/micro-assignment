package kh.edu.istad.platform.customer.persistence.mapper;

import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.persistence.document.CustomerDocument;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerPersistenceMapper {

    @BeanMapping(builder = @Builder(disableBuilder = true))
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customerId", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "familyName", source = "familyName")
    @Mapping(target = "givenName", source = "givenName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    CustomerDocument toDocument(Customer customer);

    @Mapping(target = "id", source = "customerId")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "familyName", source = "familyName")
    @Mapping(target = "givenName", source = "givenName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "status", source = "status")
    Customer toDomain(CustomerDocument document);
}