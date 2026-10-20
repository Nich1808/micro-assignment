package kh.edu.istad.platform.customer.domain.exception;

import java.util.UUID;

public class CustomerSegmentNotFoundException extends RuntimeException {
    public CustomerSegmentNotFoundException(UUID id) {
        super("Customer segment not found with id: " + id);
    }
}