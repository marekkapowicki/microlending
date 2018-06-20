package com.marekk.microlending.domain.loan.query;

import com.marekk.microlending.domain.Snapshot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.LocalDate;

import static lombok.AccessLevel.MODULE;
import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = MODULE)
@Immutable
@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(staticName = "of")
public class LoanDetails implements Snapshot {
    BigDecimal amount;
    BigDecimal interestRate;
    LocalDate creationDate;
    LocalDate dueDate;

}
