package com.marekk.microlending.domain.customer

import com.marekk.microlending.BaseITSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Subject

import static com.marekk.microlending.api.customer.RequestExamples.CUSTOMER_CORRECT_REQUEST
import static com.marekk.microlending.api.customer.RequestExamples.toRegisterCommand
import static com.marekk.microlending.domain.exception.Exceptions.conflicted

@DataJpaTest
@ContextConfiguration(classes = CustomerConfiguration)
class CustomerFacadeITSpec extends BaseITSpec {

    private static final CustomerRegisterCommand SAVED_CUSTOMER = toRegisterCommand(CUSTOMER_CORRECT_REQUEST)
    private static String CUSTOMER_ID
    @Autowired
    @Subject
    CustomerFacade customerFacade

    void setup() {
        CUSTOMER_ID = customerFacade.register(SAVED_CUSTOMER)
    }

    def "should find existed customer by Id"() {
        when:
            CustomerSnapshot retrieve = customerFacade.retrieve(CUSTOMER_ID)
        then:
            retrieve.with {
                identityNo == CUSTOMER_ID
                email == SAVED_CUSTOMER.email
                fullName == "${SAVED_CUSTOMER.firstName} ${SAVED_CUSTOMER.lastName}".toString()
            }
    }

    def "should retrieve page with customers"() {
        when:
            Page<CustomerSnapshot> page = customerFacade.retrieve(new PageRequest(0, 5))
        then:
            page.totalElements == 1
            page.hasContent()

    }

    def "should throw exception during registration customer wuth existing email"() {
        when:
            customerFacade.register(SAVED_CUSTOMER)
        then:
            thrown(conflicted().get().class)

    }
}
