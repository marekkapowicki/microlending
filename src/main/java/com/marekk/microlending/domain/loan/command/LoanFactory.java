package com.marekk.microlending.domain.loan.command;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(makeFinal = true, level = PRIVATE)
@AllArgsConstructor(access = PACKAGE)
@Slf4j
class LoanFactory {
    BigDecimal initialFactor;

    LoanEntity create(LoanApplication loanApplication) {
        log.info("creating loan from application={}", loanApplication);
        return new LoanEntity(
                loanApplication.getCreationDate(),
                loanApplication.getAmount(),
                initialFactor,
                loanApplication.calculateDueDate(),
                loanApplication.getCustomer());
    }
}
