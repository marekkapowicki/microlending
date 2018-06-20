package com.marekk.microlending.infrastructure;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Uuids {
    private static final Pattern pattern = Pattern.compile("-");

    public static String uuid() {
        return pattern.matcher(UUID.randomUUID().toString()).replaceAll("");
    }
}
