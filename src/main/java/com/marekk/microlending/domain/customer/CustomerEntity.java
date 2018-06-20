package com.marekk.microlending.domain.customer;

import com.marekk.microlending.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.StringJoiner;

@Entity
@Table(name = "customer")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class CustomerEntity extends BaseEntity {
    @NotNull
    String firstName;
    @NotNull
    String lastName;
    @NotNull
    String email;

    CustomerEntity(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    CustomerSnapshot toSnapshot() {
        return new CustomerSnapshot(getUuid(), fullName(), email);
    }

    private String fullName() {
        return new StringJoiner(" ")
                .add(firstName)
                .add(lastName).toString();
    }
}
