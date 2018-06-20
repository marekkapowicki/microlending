package com.marekk.microlending.domain.application;

import com.marekk.microlending.domain.application.validation.ApplicationValidationRule;
import com.marekk.microlending.domain.loan.command.LoanProcessorFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class LoanApplicationConfiguration {

    @Bean
    LoanApplicationFacade loanApplicationFacade(List<ApplicationValidationRule> validationRules,
                                                LoanProcessorFacade loanProcessorFacade) {
        final LoanApplicationValidator loanApplicationValidator = loanApplicationValidator(validationRules);
        return new LoanApplicationFacade(loanApplicationValidator, loanProcessorFacade);
    }


    private LoanApplicationValidator loanApplicationValidator(List<ApplicationValidationRule> validationRules) {
        return new LoanApplicationValidator(validationRules);
    }
}
