package com.marekk.microlending.domain.application

import com.marekk.microlending.domain.application.validation.ApplicationValidationRule
import spock.lang.Specification

import static com.marekk.microlending.domain.application.LoanApplicationAssembler.assembler

class LoanApplicationValidatorSpec extends Specification {
    def 'rules list can not be empty'() {
        when:
            new LoanApplicationValidator(rules)
        then:
            thrown(IllegalArgumentException)
        where:
            rules << [null, []]

    }

    def 'should return failing rule during validation'() {
        given:
            LoanApplicationValidator validator = new LoanApplicationValidator(rules)
        when:
            Optional<ApplicationValidationRule> result = validator.validate(assembler().asLoanApplication())
        then:
            result.isPresent()
        where:
            rules << [[ApplicationValidationRules.always_fail], [ApplicationValidationRules.always_pass, ApplicationValidationRules.always_pass, ApplicationValidationRules.always_fail]]
    }

    def 'should return empty result where all rules pass'() {
        given:
            LoanApplicationValidator validator = new LoanApplicationValidator(rules)
        when:
            Optional<ApplicationValidationRule> result = validator.validate(assembler().asLoanApplication())
        then:
            !result.isPresent()
        where:
            rules << [[ApplicationValidationRules.always_pass], [ApplicationValidationRules.always_pass, ApplicationValidationRules.always_pass]]
    }
}
