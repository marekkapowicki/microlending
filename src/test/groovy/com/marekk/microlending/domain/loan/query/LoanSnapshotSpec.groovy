package com.marekk.microlending.domain.loan.query

import spock.lang.Specification

import static com.marekk.microlending.domain.customer.CustomerExamples.MAREK_SNAPSHOT
import static com.marekk.microlending.domain.loan.LoanExamples.SAMPLE_DETAILS

class LoanSnapshotSpec extends Specification {
    def "create loan without extensions using factory method"() {
        when:
            LoanSnapshot loan = LoanSnapshot.from('id', SAMPLE_DETAILS, MAREK_SNAPSHOT)
        then:
            with(loan) {
                loanId == 'id'
                details == SAMPLE_DETAILS
                customer == MAREK_SNAPSHOT
                !extensions
                finalDueDate == SAMPLE_DETAILS.dueDate
            }
    }
}
