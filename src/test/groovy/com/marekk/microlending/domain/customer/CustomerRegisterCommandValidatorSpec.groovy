package com.marekk.microlending.domain.customer

import com.marekk.microlending.domain.exception.Exceptions
import spock.lang.Specification
import spock.lang.Subject

import static com.marekk.microlending.api.customer.RequestExamples.*

class CustomerRegisterCommandValidatorSpec extends Specification {

    private CustomerRepository customerRepository = Mock()
    @Subject
    private CustomerRegisterCommandValidator validator = new CustomerRegisterCommandValidator(customerRepository)

    def "throw exception where email exists"() {
        setup:
            CustomerRegisterCommand registerCommand = toRegisterCommand(generate())
            customerRepository.countByEmail(registerCommand.email) >> 1
        when:
            validator.validate(registerCommand)
        then:
            RuntimeException ex = thrown()
            ex.class == Exceptions.conflicted().get().class
    }

    def "no exception thrown where email is unique"() {
        when:
            validator.validate(toRegisterCommand(CUSTOMER_CORRECT_REQUEST))
        then:
            noExceptionThrown()
    }
}
