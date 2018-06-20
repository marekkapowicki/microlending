package com.marekk.microlending.domain.loan.query

import com.marekk.microlending.domain.loan.LoanExamples
import spock.lang.Specification
import spock.lang.Subject

import static com.marekk.microlending.domain.loan.query.LoanViewExamples.SAMPLE_ROW


class LoanFinderFacadeSpec extends Specification {

    private final LoanFinderRepository loanFinderRepository = Mock() {
        findByLoanIdAndCustomerIdentityNo(LOAN_ID, CUSTOMER_ID) >> Optional.of(SAMPLE_ROW)
        findByLoanIdAndCustomerIdentityNo(_, _) >> Optional.empty()
        findAllByCustomerIdentityNo(CUSTOMER_ID) >> [SAMPLE_ROW]
        findAllByCustomerIdentityNo(_) >> []
    }

    private final LoanConverter loanConverter = Mock() {
        convert([SAMPLE_ROW]) >> LoanExamples.SAMPLE
    }
    @Subject
    private final LoanFinderFacade facade = new LoanFinderFacade(loanFinderRepository, loanConverter)

}
