package com.marekk.microlending.api.loan

import com.marekk.microlending.BaseSpringBootITSpec
import com.marekk.microlending.domain.customer.CustomerExamples
import com.marekk.microlending.domain.customer.CustomerFacade
import com.marekk.microlending.domain.loan.query.LoanFinderFacade
import com.marekk.microlending.domain.loan.query.LoanSnapshot
import org.apache.http.HttpStatus
import org.springframework.beans.factory.annotation.Autowired

import static com.jayway.restassured.RestAssured.given
import static com.marekk.microlending.Requests.customerHeader
import static com.marekk.microlending.api.Specification.API_CONTENT_TYPE
import static com.marekk.microlending.api.Specification.ROOT
import static com.marekk.microlending.domain.customer.CustomerExamples.toSnapshot
import static com.marekk.microlending.domain.exception.Exceptions.notFound

class LoanControllerITSpec extends BaseSpringBootITSpec {
    private static String MAREK_ID = "marekId"
    private static String OTHER_CUSTOMER_ID = "otherExistingCustomerId"
    private static final String MAREK_LOAN_ID = 'firstMarekLoan'
    @Autowired
    CustomerFacade customerFacade
    @Autowired
    LoanFinderFacade loanFacade

    @Override
    void setup() {
        customerFacade.retrieve(MAREK_ID) >> CustomerExamples.MAREK_SNAPSHOT
        customerFacade.retrieve(OTHER_CUSTOMER_ID) >> toSnapshot(CustomerExamples.generate())
        customerFacade.retrieve(_ as String) >> { throw notFound().get() }
        LoanSnapshot loanSnapshot = Mock(LoanSnapshot)

        loanFacade.retrieve(MAREK_LOAN_ID, MAREK_ID) >> loanSnapshot
    }

    def "should return status 400 during retrieving by id when required customer header is missing"() {
        expect:
            given()
                .contentType(API_CONTENT_TYPE)
                .pathParam('loanId', MAREK_LOAN_ID)
            .when()
                .get(ROOT + '/loans/{loanId}')
            .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
    }

    def "should return status 404 during retrieving by id when customer from header does not exist"() {
        expect:
            given()
                .contentType(API_CONTENT_TYPE)
                .header(customerHeader("notExistingCustomer"))
                .pathParam('loanId', MAREK_LOAN_ID)
            .when()
                .get(ROOT + '/loans/{loanId}')
            .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
    }

    def "should return status 404 during retrieving by id when loan does not belong to Marek"() {
        given:
            loanFacade.retrieve(MAREK_LOAN_ID, OTHER_CUSTOMER_ID) >> {
                throw notFound('loan does not exists for given customer').get()
            }
        expect:
            given()
                .contentType(API_CONTENT_TYPE)
                .header(customerHeader(OTHER_CUSTOMER_ID))
                .pathParam('loanId', MAREK_LOAN_ID)
            .when()
                .get(ROOT + '/loans/{loanId}')
            .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
    }

    def "should return loan belongs to Marek by id"() {
        expect:
            given()
                .contentType(API_CONTENT_TYPE)
                .header(customerHeader(MAREK_ID))
                .pathParam('loanId', MAREK_LOAN_ID)
            .when()
                .get(ROOT + '/loans/{loanId}')
            .then()
                .statusCode(HttpStatus.SC_OK)
    }
}

