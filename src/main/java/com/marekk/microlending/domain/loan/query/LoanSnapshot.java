package com.marekk.microlending.domain.loan.query;

import com.marekk.microlending.domain.customer.CustomerSnapshot;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.BiFunction;

import static com.marekk.microlending.Preconditions.checkArgument;
import static com.marekk.microlending.domain.exception.Exceptions.illegalState;
import static java.util.Collections.unmodifiableList;
import static org.springframework.util.CollectionUtils.isEmpty;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
public class LoanSnapshot {

    public static LoanSnapshot from(String loanId, LoanDetails details, CustomerSnapshot customer) {
        return new LoanSnapshot(loanId, details, customer, null, details.dueDate);
    }

    static LoanSnapshot from(String loanId, LoanDetails details, CustomerSnapshot customer, List<ExtensionSnapshot> extensions,
                             BiFunction<LoanDetails, List<ExtensionSnapshot>, LocalDate> finalDateCalculator) {
        checkArgument(details != null, illegalState("details are null"));
        checkArgument(customer != null, illegalState("customer is null"));
        checkArgument(!isEmpty(extensions), illegalState("extensions are null"));
        return new LoanSnapshot(loanId, details, customer, unmodifiableList(extensions), finalDateCalculator.apply(details, extensions));
    }

    String loanId;
    LoanDetails details;
    CustomerSnapshot customer;
    List<ExtensionSnapshot> extensions;
    LocalDate finalDueDate;


    public BigDecimal retrieveInterestRate() {
        return details.interestRate;
    }
}
