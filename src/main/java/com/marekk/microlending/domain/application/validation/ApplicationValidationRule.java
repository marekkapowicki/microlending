package com.marekk.microlending.domain.application.validation;

import com.marekk.microlending.domain.loan.command.LoanApplication;
import org.springframework.core.Ordered;

@FunctionalInterface
public interface ApplicationValidationRule extends Ordered {
    boolean acceptable(LoanApplication application);

    @Override
    default int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
