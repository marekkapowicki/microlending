package com.marekk.microlending;

import com.marekk.microlending.domain.exception.MicrolendingException;

import java.util.function.Supplier;

public final class Preconditions {
    private Preconditions() {
    }

    public static void checkArgument(boolean expression, Supplier<MicrolendingException> exceptionSupplier) {
        if (!expression) {
            throw exceptionSupplier.get();
        }
    }
}
