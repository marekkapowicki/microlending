package com.marekk.microlending.domain.loan.query;

import com.marekk.microlending.domain.customer.CustomerSnapshot;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Immutable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.function.BiFunction;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.util.CollectionUtils.isEmpty;

@Getter
@FieldDefaults(level = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
@Entity
@Immutable
@Table(name = "loan_snapshot")
@EqualsAndHashCode
@AllArgsConstructor(access = PRIVATE)
class LoanViewRow {
    @Id
    String loanId;
    @NotNull
    @Embedded
    LoanDetails details;
    @NotNull
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "identityNo", column = @Column(name = "customer_identity_no")),
            @AttributeOverride(name = "fullName", column = @Column(name = "customer_full_name")),
            @AttributeOverride(name = "email", column = @Column(name = "customer_email"))
    })
    CustomerSnapshot customer;

    @Embedded
    ExtensionSnapshot extension;

    LoanSnapshot toSnapshot(List<ExtensionSnapshot> extensions,
                            BiFunction<LoanDetails, List<ExtensionSnapshot>, LocalDate> finalDateCalculator) {
        return isEmpty(extensions) ? LoanSnapshot.from(loanId, details, customer) :
                LoanSnapshot.from(loanId, details, customer, extensions, finalDateCalculator);
    }

}
