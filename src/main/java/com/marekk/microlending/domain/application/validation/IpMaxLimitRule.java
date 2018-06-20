package com.marekk.microlending.domain.application.validation;

import com.marekk.microlending.domain.loan.command.LoanApplication;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.Ordered;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(makeFinal = true, level = PRIVATE)
@AllArgsConstructor(access = PACKAGE)
class IpMaxLimitRule implements ApplicationValidationRule {
    IpValidationService ipValidationService;
    int maxLimit;

    @Override
    public boolean acceptable(LoanApplication application) {
        return ipValidationService.getHitCount(application.getInetAddress()) <= maxLimit;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 100;
    }

    @Override
    public String toString() {
        return "max try from one ip > " + maxLimit;
    }
}
