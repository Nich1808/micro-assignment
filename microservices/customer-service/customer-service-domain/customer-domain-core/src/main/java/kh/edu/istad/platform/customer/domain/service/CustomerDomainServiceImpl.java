package kh.edu.istad.platform.customer.domain.service;

import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.event.CustomerDeactivatedEvent;
import kh.edu.istad.platform.customer.domain.event.CustomerInitiatedEvent;
import kh.edu.istad.platform.customer.domain.event.CustomerUpdatedEvent;

import java.time.ZoneId;
import java.time.ZonedDateTime;


public class CustomerDomainServiceImpl implements CustomerDomainService {

    private static final ZoneId DEFAULT_TIMEZONE = ZoneId.of("UTC");

    @Override
    public CustomerInitiatedEvent initiateCustomer(Customer targetCustomer) {
        targetCustomer.initiateCustomer();
        ZonedDateTime timestamp = ZonedDateTime.now(DEFAULT_TIMEZONE);
        return new CustomerInitiatedEvent(targetCustomer, timestamp);
    }

    @Override
    public CustomerUpdatedEvent updateCustomer(Customer targetCustomer, String familyName, String givenName) {
        targetCustomer.updateCustomer(familyName, givenName);
        ZonedDateTime timestamp = ZonedDateTime.now(DEFAULT_TIMEZONE);
        return new CustomerUpdatedEvent(targetCustomer, timestamp);
    }

    @Override
    public CustomerDeactivatedEvent deactivateCustomer(Customer targetCustomer) {
        targetCustomer.deactivateCustomer();
        ZonedDateTime timestamp = ZonedDateTime.now(DEFAULT_TIMEZONE);
        return new CustomerDeactivatedEvent(targetCustomer, timestamp);
    }
}