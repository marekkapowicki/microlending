package com.marekk.microlending.infrastructure;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.servlet.ServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IpAddressProvider {

    public static InetAddress provide(ServletRequest servletRequest) {
        try {
            return InetAddress.getByName(servletRequest.getRemoteAddr());
        } catch (UnknownHostException e) {
            throw new IllegalStateException(e);
        }
    }
}
