package com.marekk.microlending.domain.application.validation

import spock.lang.Specification
import spock.lang.Subject

import static com.marekk.microlending.domain.application.LoanApplicationAssembler.assembler

class IpMaxLimitRuleSpec extends Specification {

    private final IpValidationService validationService = Mock()

    @Subject
    private final IpMaxLimitRule rule = new IpMaxLimitRule(validationService, 3)

    def 'return #expectedResult for #ipHitCounter applications from one ip'() {
        given:
            InetAddress someAddress = InetAddress.localHost
            validationService.getHitCount(someAddress) >> ipHitCounter

        when:
            boolean result = rule.acceptable(assembler().withInetAddress(someAddress).asLoanApplication())
        then:
            result == expectedResult
        where:
            ipHitCounter || expectedResult
            0            || true
            1            || true
            2            || true
            3            || true
            4            || false
            5            || false

    }

}
