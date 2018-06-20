package com.marekk.microlending.domain.loan.command;

import lombok.experimental.FieldDefaults;

import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(makeFinal = true, level = PRIVATE)
class InMemoryLoanRepository implements LoanCreatorRepository {

    private static final InMemoryLoanRepository instance = new InMemoryLoanRepository();

    static InMemoryLoanRepository getInstance() {
        return instance;
    }

    ConcurrentHashMap<String, LoanEntity> map = new ConcurrentHashMap<>();

    private InMemoryLoanRepository() {
    }

    @Override
    public LoanEntity save(LoanEntity entity) {
        requireNonNull(entity);
        map.put(entity.getUuid(), entity);
        return entity;
    }

}
