package com.marekk.microlending.domain.loan.command.extension;

import com.marekk.microlending.domain.loan.query.LoanSnapshot;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(makeFinal = true, level = PRIVATE)
@AllArgsConstructor
class ExtensionFactory {
    ExtensionsParameters extensionsParameters;

    ExtensionEntity create(LoanSnapshot loan) {
        int durationInDays = extensionsParameters.getDurationInDays();
        return new ExtensionEntity(durationInDays, calculateInterest(loan),
                calculateDueDate(loan, durationInDays), loan.getLoanId());
    }

    private LocalDate calculateDueDate(LoanSnapshot loan, int daysToAdd) {
        return loan.getFinalDueDate().plusDays(daysToAdd);
    }

    private BigDecimal calculateInterest(LoanSnapshot loan) {
        return extensionsParameters.getInterestMultiplier().multiply(loan.retrieveInterestRate());
    }
}
