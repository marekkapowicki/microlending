package com.marekk.microlending.domain.application;

import com.marekk.microlending.domain.customer.CustomerSnapshot;
import com.marekk.microlending.domain.loan.command.LoanApplication;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.time.Instant;
import java.time.LocalDate;

import static java.time.ZoneId.systemDefault;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(makeFinal = true, level = PRIVATE)
@AllArgsConstructor(staticName = "of", access = PACKAGE)
@EqualsAndHashCode(of = {"customer", "amount", "dueDays"})
@Getter
@ToString
class DefaultLoanApplication implements LoanApplication {
    CustomerSnapshot customer;
    BigDecimal amount;
    int dueDays;
    Instant creationTime;
    InetAddress inetAddress;

    @Override
    public LocalDate getCreationDate() {
        return LocalDate.from(creationTime.atZone(systemDefault()));
    }


    @Override
    public LocalDate calculateDueDate() {
        return getCreationDate().plusDays(dueDays);
    }

}
