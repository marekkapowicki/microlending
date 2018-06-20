package com.marekk.microlending.domain.application

import com.marekk.microlending.domain.customer.CustomerSnapshot
import com.marekk.microlending.domain.loan.command.LoanApplication

import java.time.Clock
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

import static com.marekk.microlending.domain.customer.CustomerExamples.MAREK_SNAPSHOT
import static java.time.LocalDate.now

class LoanApplicationAssembler {
    CustomerSnapshot customer = MAREK_SNAPSHOT
    BigDecimal amount = 1_000
    int dueDays = 14
    Clock creationClock = Clock.systemDefaultZone()
    InetAddress inetAddress = InetAddress.localHost

    static LoanApplicationAssembler assembler() {
        return new LoanApplicationAssembler()
    }

    private LoanApplicationAssembler() {
    }

    LoanApplicationAssembler withCustomer(CustomerSnapshot customerSnapshot) {
        this.customer = customerSnapshot
        return this
    }

    LoanApplicationAssembler withAmount(BigDecimal amount) {
        this.amount = amount
        return this
    }

    LoanApplicationAssembler withDueDays(int dueDays) {
        this.dueDays = dueDays
        return this
    }

    LoanApplicationAssembler withInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress
        return this
    }

    LoanApplicationAssembler withCreationTime(LocalTime creationTime) {
        ZoneId zoneId = ZoneId.systemDefault()
        ZonedDateTime dt = ZonedDateTime.of(now(), creationTime, zoneId)
        Clock clock = Clock.fixed(dt.toInstant(), zoneId)
        return withCreationTime(clock)
    }

    LoanApplicationAssembler withCreationTime(Clock creationClock) {
        this.creationClock = creationClock
        return this
    }

    LoanApplication asLoanApplication() {
        asLoanApplicationCommand().toLoanApplication()
    }

    LoanApplicationCommand asLoanApplicationCommand() {
        return new LoanApplicationCommand(creationClock, customer, amount, dueDays, inetAddress)
    }
}
