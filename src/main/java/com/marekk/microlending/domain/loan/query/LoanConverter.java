package com.marekk.microlending.domain.loan.query;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

import static com.marekk.microlending.Preconditions.checkArgument;
import static com.marekk.microlending.domain.exception.Exceptions.illegalState;
import static com.marekk.microlending.domain.exception.Exceptions.notFound;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;

public class LoanConverter implements Converter<List<LoanViewRow>, LoanSnapshot> {
    @Override
    public LoanSnapshot convert(List<LoanViewRow> rows) {
        checkArgument(!isEmpty(rows), notFound("loan is not found"));
        long loanNumbers = rows.stream()
                .map(LoanViewRow::getLoanId)
                .distinct()
                .count();
        checkArgument(loanNumbers == 1, illegalState("only one loanId is allowed. Found: " + loanNumbers));

        final List<ExtensionSnapshot> extensions = rows.stream()
                .map(LoanViewRow::getExtension)
                .filter(Objects::nonNull)
                .collect(toList());
        return rows.get(0).toSnapshot(extensions, new DueDateCalculator());
    }

    class DueDateCalculator implements BiFunction<LoanDetails, List<ExtensionSnapshot>, LocalDate> {
        @Override
        public LocalDate apply(LoanDetails details, List<ExtensionSnapshot> extensions) {

            return extensions.stream()
                    .map(ExtensionSnapshot::getExtDueDate)
                    .max(LocalDate::compareTo)
                    .orElseThrow(illegalState("wrong due date in extensions"));
        }
    }
}
