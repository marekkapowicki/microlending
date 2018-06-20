package com.marekk.microlending.domain.loan.command;

import com.marekk.microlending.MicrolendingProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.math.BigDecimal;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories("com.marekk.microlending.domain.loan.command")
@EnableConfigurationProperties(MicrolendingProperties.class)
class LoanCreatorConfiguration {

    @Bean
    public LoanProcessorFacade loanProcessor(MicrolendingProperties microlendingProperties,
                                             LoanCreatorRepository loanCreatorRepository) {
        final BigDecimal initialFactor = microlendingProperties.getLoan().getInitialFactor();
        return new LoanProcessorFacade(new LoanFactory(initialFactor), loanCreatorRepository);
    }

}
