package com.marekk.microlending.domain.loan.command;

import com.marekk.microlending.domain.BaseEntity;
import com.marekk.microlending.domain.customer.CustomerSnapshot;
import com.marekk.microlending.domain.loan.query.LoanDetails;
import com.marekk.microlending.domain.loan.query.LoanSnapshot;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "loan")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = PACKAGE)
@NoArgsConstructor(access = PRIVATE)
class LoanEntity extends BaseEntity {

    @NotNull
    LocalDate startDate;
    @NotNull
    BigDecimal amount;
    @NotNull
    BigDecimal interestRate;
    @NotNull
    LocalDate dueDate;

    @NotNull
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "identityNo", column = @Column(name = "customer_identity_no")),
            @AttributeOverride(name = "fullName", column = @Column(name = "customer_full_name")),
            @AttributeOverride(name = "email", column = @Column(name = "customer_email"))
    })
    /**
     * An entity is NOT in normal form
     * Customer data is duplicated
     */
            CustomerSnapshot customer;

    LoanSnapshot toSnapshot() {
        final LoanDetails details = LoanDetails.of(amount, interestRate, startDate, dueDate);
        return LoanSnapshot.from(getUuid(), details, customer);

    }

}
