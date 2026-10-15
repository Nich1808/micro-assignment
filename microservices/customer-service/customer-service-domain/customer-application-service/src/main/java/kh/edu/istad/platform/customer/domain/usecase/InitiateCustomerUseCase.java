package kh.edu.istad.platform.customer.domain.usecase;

import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerCommand;
import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InitiateCustomerUseCase {

    public InitiateCustomerResult execute(InitiateCustomerCommand command) {
        log.info("Initiating onboarding workflow for new customer entry...");

        return null;
    }
}