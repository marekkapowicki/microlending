package com.marekk.microlending.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
/**
 * Returns by endpoint as a result
 * of create operation
 */
public class IdResponse {
    String id;

    public static IdResponse of(String id) {
        return new IdResponse(id);
    }
}
