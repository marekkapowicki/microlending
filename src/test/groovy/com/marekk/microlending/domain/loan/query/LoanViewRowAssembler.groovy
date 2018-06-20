package com.marekk.microlending.domain.loan.query

import com.marekk.microlending.domain.customer.CustomerSnapshot

import java.time.LocalDate
import java.time.LocalDateTime

import static com.marekk.microlending.domain.customer.CustomerExamples.MAREK_SNAPSHOT
import static com.marekk.microlending.domain.loan.LoanExamples.SAMPLE_DETAILS
import static java.time.LocalDateTime.now
import static java.time.LocalDateTime.now

class LoanViewRowAssembler {

    private String loanId
    private LoanDetails details = SAMPLE_DETAILS
    private CustomerSnapshot customer = MAREK_SNAPSHOT
    private ExtensionSnapshot extension

    static LoanViewRowAssembler sample(String id) {
        return new LoanViewRowAssembler(loanId: id)
    }

    LoanViewRowAssembler extension(BigDecimal interestRate, LocalDate dueDate) {
        return extension(new ExtensionSnapshot(interestRate, dueDate, now()))
    }

    LoanViewRowAssembler extension(ExtensionSnapshot extension) {
        this.extension = extension
        return this
    }



    LoanViewRow assemble() {
        return new LoanViewRow(loanId, details, customer, extension)
    }
}
