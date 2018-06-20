package com.marekk.microlending.domain.application;

import com.marekk.microlending.domain.customer.CustomerSnapshot;
import com.marekk.microlending.domain.loan.command.LoanApplication;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(makeFinal = true, level = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(of = {"customer", "amount", "dueDays"})
public class LoanApplicationCommand {
    Clock clock;
    CustomerSnapshot customer;
    BigDecimal amount;
    int dueDays;
    InetAddress ipAddress;

    public static LoanApplicationCommand of(CustomerSnapshot customer, BigDecimal amount,
                                            int dueDays, InetAddress inetAddress) {
        final Clock systemClock = Clock.system(ZoneId.systemDefault());
        return new LoanApplicationCommand(systemClock, customer, amount, dueDays, inetAddress);
    }

    LoanApplication toLoanApplication() {
        return DefaultLoanApplication.of(customer, amount, dueDays, Instant.now(clock), ipAddress);
    }
}
