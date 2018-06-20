package com.marekk.microlending.domain.loan.query

import static com.marekk.microlending.domain.customer.CustomerExamples.MAREK_SNAPSHOT
import static java.time.LocalDate.now

class LoanViewExamples {
    static final LoanViewRow SAMPLE_ROW = new LoanViewRow("id", 100G as BigDecimal, 1.0, now().plusDays(2), now(), MAREK_SNAPSHOT, null)
}
