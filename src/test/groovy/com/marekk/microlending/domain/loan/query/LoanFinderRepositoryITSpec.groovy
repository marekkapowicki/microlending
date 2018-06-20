package com.marekk.microlending.domain.loan.query

import com.marekk.microlending.BaseITSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import spock.lang.Subject

import java.time.LocalDate
import java.time.Month

@DataJpaTest
@ContextConfiguration(classes = LoanFinderConfiguration)
class LoanFinderRepositoryITSpec extends BaseITSpec {

    @Autowired
    @Subject
    private LoanFinderRepository loanFinderRepository

    @Sql(scripts = ['/insert_customer.sql', '/insert_loan.sql'], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    def "the empty list is returned for not existing loan"() {
        when:
            List<LoanViewRow> rows = loanFinderRepository.findByLoanIdAndCustomerIdentityNo('666', '1')
        then:
            rows.isEmpty()
    }

    @Sql(scripts = ['/insert_customer.sql', '/insert_loan.sql'], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    def "find preinserted loan by id"() {
        when:
            List<LoanViewRow> rows = loanFinderRepository.findByLoanIdAndCustomerIdentityNo('123', '1')
        then:
            rows.size() == 1
        and:
            LoanViewRow loanRow = rows.get(0)
            with(loanRow) {
                loanId
                details
                customer
                !extension
            }
        and:
            with(loanRow.details) {
                amount == 100
                interestRate == 1.2
                creationDate == LocalDate.of(2017, Month.FEBRUARY, 01)
                dueDate == LocalDate.of(2017, Month.FEBRUARY, 15)
            }
    }

    @Sql(scripts = ['/insert_customer.sql', '/insert_loan.sql', '/insert_extensions.sql'], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    def "find preinserted loan with extensions by id"() {
        when:
            List<LoanViewRow> rows = loanFinderRepository.findByLoanIdAndCustomerIdentityNo('123', '1')
        then:
            rows.size() == 2
            rows.each {
                it.loanId
                it.customer
                it.extension
            }

    }
}
