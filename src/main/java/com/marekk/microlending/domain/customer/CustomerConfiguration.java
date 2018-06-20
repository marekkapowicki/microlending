package com.marekk.microlending.domain.customer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.marekk.microlending.domain.customer")
class CustomerConfiguration {

    @Bean
    CustomerFacade customerFacade(CustomerRepository customerRepository) {
        CustomerRegisterCommandValidator customerRegisterCommandValidator =
                new CustomerRegisterCommandValidator(customerRepository);
        return new CustomerFacade(customerRepository, customerRegisterCommandValidator);
    }

}
