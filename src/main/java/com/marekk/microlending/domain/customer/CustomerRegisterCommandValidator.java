package com.marekk.microlending.domain.customer;

import com.marekk.microlending.domain.exception.Exceptions;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@FieldDefaults(makeFinal = true, level = PRIVATE)
@AllArgsConstructor
class CustomerRegisterCommandValidator {

    CustomerRepository customerRepository;

    void validate(CustomerRegisterCommand customerRegisterCommand) {
        log.info("validating customerRegister={}", customerRegisterCommand);
        if (customerRepository.countByEmail(customerRegisterCommand.getEmail()) > 0) {
            throw Exceptions.conflicted("email already exists").get();
        }
    }
}
