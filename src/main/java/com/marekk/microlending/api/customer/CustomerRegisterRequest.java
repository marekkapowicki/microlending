package com.marekk.microlending.api.customer;

import com.marekk.microlending.api.CreateResourceRequest;
import com.marekk.microlending.domain.customer.CustomerRegisterCommand;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(makeFinal = true, level = PRIVATE)
@AllArgsConstructor
@ToString
class CustomerRegisterRequest implements CreateResourceRequest {
    @NotNull
    String firstName;
    @NotNull
    String lastName;
    @NotNull
    @Email
    String email;

    CustomerRegisterCommand toCommand() {
        return CustomerRegisterCommand.of(firstName, lastName, email);
    }
}
