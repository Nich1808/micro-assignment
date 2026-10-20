package kh.edu.istad.platform.customer.domain.dto;

import kh.edu.istad.platform.customer.domain.valueobject.CustomerStatus;
import kh.edu.istad.platform.customer.domain.valueobject.Email;
import kh.edu.istad.platform.customer.domain.valueobject.PhoneNumber;

import java.util.UUID;

public record InitiateCustomerResult(
        UUID customerId,
        String username,
        String familyName,
        String givenName,
        Email email,
        PhoneNumber phoneNumber,
        CustomerStatus status
) {
}