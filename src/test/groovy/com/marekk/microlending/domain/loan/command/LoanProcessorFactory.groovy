package com.marekk.microlending.domain.loan.command

import lombok.NoArgsConstructor

import static lombok.AccessLevel.PRIVATE

@NoArgsConstructor(access = PRIVATE)
class LoanProcessorFactory {

    static LoanProcessorFacade inMemoryDatabaseProcessor(BigDecimal initialFactor, LoanCreatorRepository loanRepository) {
        return new LoanProcessorFacade(new LoanFactory(initialFactor), loanRepository)
    }
}
