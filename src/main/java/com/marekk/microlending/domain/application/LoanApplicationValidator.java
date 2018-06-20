package com.marekk.microlending.domain.application;


import com.google.common.collect.ImmutableList;
import com.marekk.microlending.domain.application.validation.ApplicationValidationRule;
import com.marekk.microlending.domain.loan.command.LoanApplication;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@FieldDefaults(makeFinal = true, level = PRIVATE)
class LoanApplicationValidator {
    List<ApplicationValidationRule> rules;

    LoanApplicationValidator(List<ApplicationValidationRule> rules) {
        checkArgument(rules != null && !rules.isEmpty(), "validation rules are null");
        this.rules = ImmutableList.copyOf(rules);
    }

    Optional<ApplicationValidationRule> validate(LoanApplication application) {
        checkArgument(application != null, "application is null");
        log.trace("Validating {} against {}", application, rules);
        return rules
                .stream()
                .filter(it -> !it.acceptable(application))
                .findAny();
    }
}
