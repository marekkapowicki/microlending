package com.marekk.microlending.domain.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MicrolendingException extends RuntimeException {
    MicrolendingException(String message) {
        super(message);
    }
}
