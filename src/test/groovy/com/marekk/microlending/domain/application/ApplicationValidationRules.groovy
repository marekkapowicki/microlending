package com.marekk.microlending.domain.application

import com.marekk.microlending.domain.application.validation.ApplicationValidationRule

class ApplicationValidationRules {
    public static final ApplicationValidationRule always_pass = { it -> true };
    public static final ApplicationValidationRule always_fail = { it -> false };
}
