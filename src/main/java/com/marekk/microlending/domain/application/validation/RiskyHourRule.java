package com.marekk.microlending.domain.application.validation;

import com.google.common.collect.Range;
import com.marekk.microlending.domain.loan.command.LoanApplication;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.Ordered;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalTime;

import static java.time.ZoneId.systemDefault;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(makeFinal = true, level = PRIVATE)
@AllArgsConstructor(access = PACKAGE)
class RiskyHourRule implements ApplicationValidationRule {
    BigDecimal maxAmount;
    Range<LocalTime> riskyTimeRange;

    @Override
    public boolean acceptable(LoanApplication application) {
        return !withRiskyAmount(application.getAmount()) || !withinRiskyHour(application.getCreationTime());
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 10;
    }

    @Override
    public String toString() {
        return "risky time " + riskyTimeRange + "for amount = " + maxAmount;
    }

    private boolean withinRiskyHour(Instant creationTime) {
        return riskyTimeRange.contains(LocalTime.from(creationTime.atZone(systemDefault())));
    }

    private boolean withRiskyAmount(BigDecimal amount) {
        return amount.compareTo(maxAmount) >= 0;
    }
}
