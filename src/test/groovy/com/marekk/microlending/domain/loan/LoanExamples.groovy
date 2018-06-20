package com.marekk.microlending.domain.loan

import com.marekk.microlending.domain.loan.query.LoanDetails
import com.marekk.microlending.domain.loan.query.LoanSnapshot

import static com.marekk.microlending.domain.customer.CustomerExamples.MAREK_SNAPSHOT
import static java.time.LocalDate.now

class LoanExamples {
    static final LoanDetails SAMPLE_DETAILS = LoanDetails.of(100g, 1g, now(), now().plusDays(2))
    static final LoanSnapshot SAMPLE = LoanSnapshot.from("id", SAMPLE_DETAILS, MAREK_SNAPSHOT);

}
