package com.marekk.microlending.domain.customer;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PUBLIC;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of", access = PUBLIC)
@EqualsAndHashCode
@Getter
public final class CustomerRegisterCommand {
    String firstName;
    String lastName;
    String email;

    CustomerEntity toEntity() {
        return new CustomerEntity(firstName, lastName, email);
    }

}
