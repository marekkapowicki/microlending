package com.marekk.microlending.domain.loan.command

import com.marekk.microlending.domain.loan.query.LoanSnapshot
import spock.lang.Specification
import spock.lang.Subject

import static com.marekk.microlending.domain.application.LoanApplicationAssembler.assembler

class LoanProcessorSpec extends Specification {

    static final BigDecimal INITIAL_FACTOR = 2

    LoanCreatorRepository loanCreatorRepository = InMemoryLoanRepository.instance

    @Subject
    LoanProcessorFacade loanProcessor = LoanProcessorFactory.inMemoryDatabaseProcessor(INITIAL_FACTOR, loanCreatorRepository)

    def "should store populated loan"() {

        given:
            LoanApplication application = assembler().asLoanApplication()
        when:
            LoanSnapshot loan = loanProcessor.acceptApplication(application)
        then:
            loan.loanId

    }
}
