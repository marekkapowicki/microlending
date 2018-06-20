package com.marekk.microlending.domain.loan.command;

import org.springframework.data.repository.Repository;

interface LoanCreatorRepository extends Repository<LoanEntity, Long> {
    LoanEntity save(LoanEntity loan);
}
