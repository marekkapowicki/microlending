package com.marekk.microlending.domain.loan.query;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.marekk.microlending.domain.loan.query")
class LoanFinderConfiguration {

    @Bean
    LoanFinderFacade loanFinderFacade(LoanFinderRepository loanFinderRepository) {
        return new LoanFinderFacade(loanFinderRepository, new LoanConverter());
    }
}
