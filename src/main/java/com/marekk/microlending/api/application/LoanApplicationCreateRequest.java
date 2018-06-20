package com.marekk.microlending.api.application;

import com.marekk.microlending.api.CreateResourceRequest;
import com.marekk.microlending.domain.application.LoanApplicationCommand;
import com.marekk.microlending.domain.customer.CustomerSnapshot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.net.InetAddress;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(makeFinal = true, level = PRIVATE)
@AllArgsConstructor
@Getter(PACKAGE)
@ToString
class LoanApplicationCreateRequest implements CreateResourceRequest {

    @NotNull
    @Min(1)
    BigDecimal amount;

    @Min(1)
    int dueDays;

    LoanApplicationCommand toCommand(CustomerSnapshot customer, InetAddress inetAddress) {
        return LoanApplicationCommand.of(customer, amount, dueDays, inetAddress);
    }

}
