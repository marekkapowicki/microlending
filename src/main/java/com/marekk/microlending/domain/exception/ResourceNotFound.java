package com.marekk.microlending.domain.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such resource")
@NoArgsConstructor
class ResourceNotFound extends MicrolendingException {
    ResourceNotFound(String message) {
        super(message);
    }
}
