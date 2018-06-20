package com.marekk.microlending.domain.loan.command;

import com.marekk.microlending.domain.customer.CustomerSnapshot;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.time.Instant;
import java.time.LocalDate;

public interface LoanApplication {
    InetAddress getInetAddress();

    CustomerSnapshot getCustomer();

    BigDecimal getAmount();

    Instant getCreationTime();

    LocalDate getCreationDate();

    LocalDate calculateDueDate();
}
