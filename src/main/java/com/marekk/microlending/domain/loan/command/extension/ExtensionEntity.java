package com.marekk.microlending.domain.loan.command.extension;

import com.marekk.microlending.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

import static lombok.AccessLevel.PACKAGE;

@Entity
@Table(name = "loan_extension")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = PACKAGE)
class ExtensionEntity extends BaseEntity {

    int duration;
    @NotNull
    BigDecimal interestRate;
    @NotNull
    LocalDate dueDate;
    @NotNull
    String loanId;
}
