package com.marekk.microlending

import com.jayway.restassured.RestAssured
import com.marekk.microlending.domain.application.LoanApplicationFacade
import com.marekk.microlending.domain.customer.CustomerFacade
import com.marekk.microlending.domain.loan.command.extension.ExtensionFacade
import com.marekk.microlending.domain.loan.query.LoanFinderFacade
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(classes = Config, webEnvironment = RANDOM_PORT)
abstract class BaseSpringBootITSpec extends Specification {

    @Value('${local.server.port}')
    int serverPort;

    void setup() {
        RestAssured.port = serverPort;
    }

    @TestConfiguration
    static class Config {
        private DetachedMockFactory factory = new DetachedMockFactory()

        @Bean
        CustomerFacade customerFacade() {
            return factory.Stub(CustomerFacade)
        }

        @Bean
        LoanApplicationFacade loanApplicationFacade() {
            return factory.Stub(LoanApplicationFacade)
        }

        @Bean
        LoanFinderFacade loanFinderFacade() {
            return factory.Stub(LoanFinderFacade)
        }

        @Bean
        ExtensionFacade loanExtensionFacade() {
            return factory.Mock(ExtensionFacade)
        }
    }
}
