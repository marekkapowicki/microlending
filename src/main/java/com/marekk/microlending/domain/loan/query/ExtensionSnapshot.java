package com.marekk.microlending.domain.loan.query;

import com.marekk.microlending.domain.Snapshot;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static lombok.AccessLevel.MODULE;
import static lombok.AccessLevel.PRIVATE;

@Getter
@FieldDefaults(level = PRIVATE)
@AllArgsConstructor(access = MODULE)
@NoArgsConstructor(access = PRIVATE)
@Immutable
@ToString
@EqualsAndHashCode(of = "extCreationTime")
public class ExtensionSnapshot implements Snapshot {
    BigDecimal extInterestRate;
    LocalDate extDueDate;
    LocalDateTime extCreationTime;
}
