package com.marekk.microlending.api.application

import com.marekk.microlending.BaseSpringBootITSpec
import com.marekk.microlending.domain.application.LoanApplicationCommand
import com.marekk.microlending.domain.application.LoanApplicationFacade
import com.marekk.microlending.domain.customer.CustomerExamples
import com.marekk.microlending.domain.customer.CustomerFacade
import com.marekk.microlending.domain.exception.Exceptions
import javaslang.control.Either
import org.apache.http.HttpStatus
import org.springframework.beans.factory.annotation.Autowired

import static RequestExamples.toJson
import static com.jayway.restassured.RestAssured.given
import static com.marekk.microlending.Requests.customerHeader
import static com.marekk.microlending.api.Specification.API_CONTENT_TYPE
import static com.marekk.microlending.api.Specification.ROOT

class LoanApplicationControllerITSpec extends BaseSpringBootITSpec {
    private static String CUSTOMER_ID = CustomerExamples.MAREK_SNAPSHOT.identityNo
    public static final String APPLICATION_PATH = ROOT + "/loans/applications"
    @Autowired
    CustomerFacade customerFacade
    @Autowired
    LoanApplicationFacade applicationFacade

    @Override
    void setup() {
        customerFacade.retrieve(CUSTOMER_ID) >> CustomerExamples.MAREK_SNAPSHOT
        customerFacade.retrieve(_ as String) >> { throw Exceptions.notFound().get() }
        applicationFacade.process(RequestExamples.MAREK_LOAN_APPLICATION_FACTORY) >> Either.right('ok')
        applicationFacade.process(_ as LoanApplicationCommand) >> Either.left('some errors')
    }

    def "should return status 400 when required customer header is missing"() {
        expect:
            given()
                .contentType(API_CONTENT_TYPE)
                .body(toJson(RequestExamples.APPLICATION_CORRECT_REQUEST))
            .when()
                .post(APPLICATION_PATH)
            .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
    }

    def "should return status 404 when customer from header does not exist"() {
        expect:
            given()
                .contentType(API_CONTENT_TYPE)
                .header(customerHeader("notExistingCustomer"))
                .body(toJson(RequestExamples.APPLICATION_CORRECT_REQUEST))
            .when()
                .post(APPLICATION_PATH)
            .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
    }

    def "should return loanId when application is approved"() {
        expect:
            given()
                .contentType(API_CONTENT_TYPE)
                .header(customerHeader(CUSTOMER_ID))
                .body(toJson(RequestExamples.APPLICATION_CORRECT_REQUEST))
            .when()
                .post(APPLICATION_PATH)
            .then()
                .statusCode(HttpStatus.SC_CREATED)
    }

    def "should return error status when application is rejected"() {
        given:
            String rejectedRequest = toJson(new LoanApplicationCreateRequest(45, 10))
        expect:
            given()
                .contentType(API_CONTENT_TYPE)
                .header(customerHeader(CUSTOMER_ID))
                .body(rejectedRequest)
            .when()
                .post(APPLICATION_PATH)
            .then()
                .statusCode(HttpStatus.SC_PRECONDITION_FAILED)

    }
}
