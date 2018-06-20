package com.marekk.microlending.domain.exception;

import lombok.NoArgsConstructor;

import java.util.function.Supplier;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class Exceptions {
    public static Supplier<MicrolendingException> notFound() {
        return ResourceNotFound::new;
    }

    public static Supplier<MicrolendingException> notFound(String message) {
        return () -> new ResourceNotFound(message);
    }

    public static Supplier<MicrolendingException> illegalState() {
        return IllegalState::new;
    }

    public static Supplier<MicrolendingException> illegalState(String message) {
        return () -> new IllegalState(message);
    }

    public static Supplier<MicrolendingException> conflicted() {
        return ResourceConflicted::new;
    }

    public static Supplier<MicrolendingException> conflicted(String message) {
        return () -> new ResourceConflicted(message);
    }
}
