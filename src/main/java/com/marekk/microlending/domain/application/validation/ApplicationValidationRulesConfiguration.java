package com.marekk.microlending.domain.application.validation;

import com.google.common.collect.Range;
import com.marekk.microlending.MicrolendingProperties;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.math.BigDecimal;
import java.time.LocalTime;

import static com.google.common.collect.Range.closed;
import static java.time.LocalTime.of;
import static lombok.AccessLevel.PRIVATE;

@Configuration
@EnableCaching
@EnableScheduling
@EnableConfigurationProperties(MicrolendingProperties.class)
public class ApplicationValidationRulesConfiguration {

    @Bean
    CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("ip");
    }

    @Bean
    IpValidationRepository ipValidationRepository() {
        return new IpValidationCacheRepository();
    }

    @Bean
    IpValidationService ipValidationService(IpValidationRepository ipValidationRepository) {
        return new IpValidationService(ipValidationRepository);
    }

    @Bean
    LoanRestrictions loanRestrictions(MicrolendingProperties microlendingProperties) {
        final MicrolendingProperties.Loan loanProperties = microlendingProperties.getLoan();
        return new LoanRestrictions(loanProperties.getMaxPerIp(),
                loanProperties.getRiskyHours(), loanProperties.getMaxAmount());
    }

    @Bean
    ApplicationValidationRule riskyHour(LoanRestrictions loanRestrictions) {

        return new RiskyHourRule(loanRestrictions.maxAmount, loanRestrictions.getRiskyTimeRange());
    }

    @Bean
    ApplicationValidationRule ipMaxNumber(IpValidationService ipValidationService,
                                          LoanRestrictions loanRestrictions) {
        return new IpMaxLimitRule(ipValidationService, loanRestrictions.maxPerIp);
    }

    @FieldDefaults(makeFinal = true, level = PRIVATE)
    @AllArgsConstructor
    private static final class LoanRestrictions {
        int maxPerIp;
        String riskyHours;
        BigDecimal maxAmount;

        private Range<LocalTime> getRiskyTimeRange() {
            final String[] hours = riskyHours.split("-");
            final int startHour = Integer.parseInt(hours[0]);
            final int endHour = Integer.parseInt(hours[1]);
            return closed(of(startHour, 0), of(endHour, 0));
        }
    }
}
