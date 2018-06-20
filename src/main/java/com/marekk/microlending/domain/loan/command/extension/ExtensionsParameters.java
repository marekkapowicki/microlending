package com.marekk.microlending.domain.loan.command.extension;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(makeFinal = true, level = PRIVATE)
@AllArgsConstructor
@Getter(PACKAGE)
final class ExtensionsParameters {
    int durationInDays;
    BigDecimal interestMultiplier;
}
