package com.marekk.microlending.domain.application

import com.marekk.microlending.domain.loan.command.LoanProcessorFacade
import javaslang.control.Either
import spock.lang.Specification

import static com.marekk.microlending.domain.application.LoanApplicationAssembler.assembler
import static com.marekk.microlending.domain.loan.LoanExamples.SAMPLE

class LoanApplicationFacadeSpec extends Specification {
    LoanProcessorFacade loanProcessorFacade = Mock() {
        acceptApplication(_ as DefaultLoanApplication) >> SAMPLE
    }

    def "return created loanId for valid application"() {
        given:
            LoanApplicationValidator alwaysPassValidator = new LoanApplicationValidator([ApplicationValidationRules.always_pass])
            LoanApplicationFacade facade = new LoanApplicationFacade(alwaysPassValidator, loanProcessorFacade)
        when:
            Either<String, String> result = facade.process(assembler().asLoanApplicationCommand())
        then:
            result.right
            result.right().get() == SAMPLE.loanId
    }

    def "return broken rule for invalid application"() {
        given:
            LoanApplicationValidator alwaysFailedValidator = new LoanApplicationValidator([ApplicationValidationRules.always_fail])
            LoanApplicationFacade facade = new LoanApplicationFacade(alwaysFailedValidator, loanProcessorFacade)
        when:
            Either<String, String> result = facade.process(assembler().asLoanApplicationCommand())
        then:
            result.left

    }
}
