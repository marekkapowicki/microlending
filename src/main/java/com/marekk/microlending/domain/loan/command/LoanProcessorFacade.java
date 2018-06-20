package com.marekk.microlending.domain.loan.command;

import com.marekk.microlending.domain.loan.query.LoanSnapshot;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@FieldDefaults(makeFinal = true, level = PRIVATE)
@AllArgsConstructor(access = PACKAGE)
public class LoanProcessorFacade {
    LoanFactory factory;
    LoanCreatorRepository repository;

    @Transactional
    public LoanSnapshot acceptApplication(LoanApplication loanApplication) {
        LoanEntity loan = factory.create(loanApplication);
        return repository.save(loan).toSnapshot();
    }

}
