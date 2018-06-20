package com.marekk.microlending.domain.customer;

import com.marekk.microlending.domain.Snapshot;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.MODULE;
import static lombok.AccessLevel.PRIVATE;

@Getter
@FieldDefaults(level = PRIVATE)
@AllArgsConstructor(access = MODULE)
@NoArgsConstructor(access = PRIVATE)
@ToString
@EqualsAndHashCode
public final class CustomerSnapshot implements Snapshot {
    String identityNo;
    String fullName;
    String email;

}
