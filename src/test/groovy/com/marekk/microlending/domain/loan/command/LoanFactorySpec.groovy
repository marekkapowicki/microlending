package com.marekk.microlending.domain.loan.command

import spock.lang.Specification
import spock.lang.Subject

import static com.marekk.microlending.domain.application.LoanApplicationAssembler.assembler

class LoanFactorySpec extends Specification {

    @Subject
    LoanFactory loanFactory = new LoanFactory(2G)

    def 'build proper loan'() {
        given:
            LoanApplication loanApplication = assembler().asLoanApplication()
        when:
            LoanEntity createdLoan = loanFactory.create(loanApplication)
        then:
            with(createdLoan.toSnapshot()) {
                loanId
                details
                finalDueDate == loanApplication.creationDate.plusDays(loanApplication.dueDays)
                retrieveInterestRate() == 2.0
            }
    }

}

