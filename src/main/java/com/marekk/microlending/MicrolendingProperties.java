package com.marekk.microlending;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * External configuration properties for our application
 * Unfortunately it must be mutable and contains setters/getters
 */
@ConfigurationProperties
@Validated
@Getter
@Setter
public class MicrolendingProperties {
    @NotNull
    @Valid
    private Loan loan;
    @NotNull
    @Valid
    private Extension extension;

    @Setter
    @Getter
    public static class Loan {
        @Range(min = 0)
        BigDecimal maxAmount;
        @NotNull
        @Pattern(regexp = "\\d{1,2}-\\d{1,2}")
        String riskyHours;
        @Range(min = 0)
        int maxPerIp = 3;
        @Range(min = 0)
        BigDecimal initialFactor;
    }

    @Setter
    @Getter
    public static class Extension {
        @Range(min = 1)
        int plusDays;
        @NotNull
        BigDecimal interestMultiplier;
    }
}


