package com.marekk.microlending.domain.customer;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import static com.marekk.microlending.domain.exception.Exceptions.notFound;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@FieldDefaults(makeFinal = true, level = PRIVATE)
@AllArgsConstructor
public class CustomerFacade {
    CustomerRepository repository;
    CustomerRegisterCommandValidator customerRegisterCommandValidator;

    @Transactional
    public String register(final CustomerRegisterCommand registerCommand) {
        log.info("new customer to register {}", registerCommand);
        customerRegisterCommandValidator.validate(registerCommand);
        return repository.save(registerCommand.toEntity()).getUuid();
    }

    @Transactional(readOnly = true)
    public CustomerSnapshot retrieve(final String id) {
        log.info("retrieve customer={} by id", id);
        return repository.findByUuid(id)
                .map(CustomerEntity::toSnapshot)
                .orElseThrow(notFound("customer not found"));
    }

    @Transactional(readOnly = true)
    public Page<CustomerSnapshot> retrieve(final Pageable pageable) {
        log.info("retrieve customers={}", pageable);
        return repository.findAll(pageable)
                .map(CustomerEntity::toSnapshot);
    }
}
