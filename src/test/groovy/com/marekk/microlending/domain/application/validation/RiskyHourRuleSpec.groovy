package com.marekk.microlending.domain.application.validation

import com.google.common.collect.Range
import com.marekk.microlending.domain.loan.command.LoanApplication
import spock.lang.Specification

import java.time.LocalTime

import static com.marekk.microlending.domain.application.LoanApplicationAssembler.assembler

class RiskyHourRuleSpec extends Specification {

    def "for amount #amount at #time should validate with result: #expectedResult"() {
        Range<LocalTime> riskyTime_0_6Range = Range.closed(LocalTime.of(0, 0), LocalTime.of(6, 0))
        given:
            final RiskyHourRule rule = new RiskyHourRule(maxAmount, riskyTime_0_6Range)
            final LoanApplication application = assembler()
                    .withAmount(amount)
                    .withCreationTime(time).asLoanApplication()
        when:
            boolean result = rule.acceptable(application)
        then:
            result == expectedResult
        where:
            time                 | amount | maxAmount || expectedResult
            LocalTime.of(14, 30) | 3      | 10        || true
            LocalTime.of(14, 30) | 30     | 10        || true
            LocalTime.of(4, 30)  | 3      | 10        || true
            LocalTime.of(4, 30)  | 30     | 10        || false
            LocalTime.of(4, 30)  | 10     | 10        || false
            LocalTime.of(0, 00)  | 10     | 10        || false
            LocalTime.of(6, 00)  | 10     | 10        || false
            LocalTime.of(0, 00)  | 3      | 10        || true
            LocalTime.of(6, 00)  | 3      | 10        || true


    }
}
