package com.marekk.microlending.domain.loan.command.extension;

import com.marekk.microlending.domain.loan.query.LoanSnapshot;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@FieldDefaults(makeFinal = true, level = PRIVATE)
@AllArgsConstructor
public class ExtensionFacade {
    ExtensionFactory extensionFactory;
    ExtensionCreatorRepository repository;

    @Transactional
    public void extend(LoanSnapshot loan) {
        log.info("extend the loan {}", loan);
        ExtensionEntity extension = extensionFactory.create(loan);
        repository.save(extension);
    }
}
