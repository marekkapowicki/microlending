package com.marekk.microlending;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaAuditing
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EnableConfigurationProperties(MicrolendingProperties.class)
public class MicrolendingApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicrolendingApplication.class, args);
    }
}
