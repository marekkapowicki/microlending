package com.marekk.microlending.domain.application;

import com.marekk.microlending.domain.application.validation.ApplicationValidationRule;
import com.marekk.microlending.domain.loan.command.LoanApplication;
import com.marekk.microlending.domain.loan.command.LoanProcessorFacade;
import javaslang.control.Either;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Slf4j
@FieldDefaults(makeFinal = true, level = PRIVATE)
@AllArgsConstructor
public class LoanApplicationFacade {

    LoanApplicationValidator loanApplicationValidator;
    LoanProcessorFacade loanProcessorFacade;

    @Transactional(propagation = REQUIRES_NEW)
    public Either<String, String> process(final LoanApplicationCommand applicationCommand) {
        final LoanApplication application = applicationCommand.toLoanApplication();
        log.info("processing loan application={}", application);
        Optional<ApplicationValidationRule> failingRule = loanApplicationValidator.validate(application);
        return failingRule
                .map(it -> Either.<String, String>left(it.toString()))
                .orElseGet(() -> Either.right(loanProcessorFacade.acceptApplication(application).getLoanId()));
    }

}
