package com.marekk.microlending.domain.loan.query

import com.marekk.microlending.domain.exception.Exceptions
import org.junit.Ignore
import spock.lang.Specification
import spock.lang.Subject

import static com.marekk.microlending.domain.loan.query.LoanViewExamples.SAMPLE_ROW

@Ignore
class LoanFinderSpec extends Specification {

    private static final String LOAN_ID = 'customer_loan'
    private static final String CUSTOMER_ID = "existing_customer"


    private final LoanFinderRepository loanFinderRepository = Mock() {
        findByLoanIdAndCustomerIdentityNo(LOAN_ID, CUSTOMER_ID) >> Optional.of(SAMPLE_ROW)
        findByLoanIdAndCustomerIdentityNo(_, _) >> Optional.empty()
        findAllByCustomerIdentityNo(CUSTOMER_ID) >> [SAMPLE_ROW]
        findAllByCustomerIdentityNo(_) >> []
    }

    @Subject
    private final LoanFinderFacade loanFinderFacade = new LoanFinderFacade(loanFinderRepository)

    def "return single loan by id for existing customer"() {
        when:
            LoanSnapshot loan = loanFinderFacade.retrieve(LOAN_ID, CUSTOMER_ID)
        then:
            loan.with {
                loanId
                details.amount
                details.interestRate
                details.creationDate
                details.dueDate
            }
    }

    def "throw exception during retrieving loan for not existing customer"() {
        when:
            loanFinderFacade.retrieve(LOAN_ID, 'wrong_id')
        then:
            RuntimeException ex = thrown()
            ex.class == Exceptions.notFound().get().class

    }

    def "return all loans for existing customer"() {
        when:
            List<LoanSnapshot> loans = loanFinderFacade.retrieveAll(CUSTOMER_ID)
        then:
            loans
    }


    def "return no loans for not existing customer"() {
        when:
            List<LoanSnapshot> loans = loanFinderFacade.retrieveAll('wrong_id')
        then:
            !loans
    }

}
