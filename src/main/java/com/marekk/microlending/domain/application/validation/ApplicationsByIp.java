package com.marekk.microlending.domain.application.validation;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicInteger;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(makeFinal = true, level = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(of = "inetAddress")
@ToString
@Getter
class ApplicationsByIp {
    InetAddress inetAddress;
    AtomicInteger counter;

    static ApplicationsByIp firstApplication(InetAddress inetAddress) {
        return new ApplicationsByIp(inetAddress, new AtomicInteger(0));
    }

    ApplicationsByIp withIncrementCounter() {
        return new ApplicationsByIp(inetAddress, new AtomicInteger(counter.incrementAndGet()));
    }
}
