package com.marekk.microlending.api.loan

import com.marekk.microlending.BaseSpringBootITSpec
import com.marekk.microlending.domain.loan.LoanExamples
import com.marekk.microlending.domain.loan.command.extension.ExtensionFacade
import com.marekk.microlending.domain.loan.query.LoanFinderFacade
import org.apache.http.HttpStatus
import org.springframework.beans.factory.annotation.Autowired

import static com.jayway.restassured.RestAssured.given
import static com.marekk.microlending.Requests.customerHeader
import static com.marekk.microlending.api.Specification.API_CONTENT_TYPE
import static com.marekk.microlending.api.Specification.ROOT

class ExtensionControllerITSpec extends BaseSpringBootITSpec {

    private static String MAREK_ID = "marekId"
    private static final String MAREK_LOAN_ID = 'firstMarekLoan'
    public static final String LOANS_PATH = ROOT + "/loans/"

    @Autowired
    LoanFinderFacade loanFacade

    @Autowired
    ExtensionFacade extensionFacade


    @Override
    void setup() {
        loanFacade.retrieve(MAREK_LOAN_ID, MAREK_ID) >> LoanExamples.SAMPLE
    }

    def 'should return 200 during extending existing loan'() {
        when:
            given()
                    .contentType(API_CONTENT_TYPE)
                    .header(customerHeader(MAREK_ID))
                    .pathParam('loanId', MAREK_LOAN_ID)
                    .when()
                    .post(LOANS_PATH + '{loanId}/extension')
                    .then()
                    .statusCode(HttpStatus.SC_OK)
        then:
            1 * extensionFacade.extend(LoanExamples.SAMPLE)

    }

    def 'should return 404 during extending not existing loan'() {
        expect:
            given()
                    .contentType(API_CONTENT_TYPE)
                    .header(customerHeader('other client'))
                    .pathParam('loanId', MAREK_LOAN_ID)
                    .when()
                    .post(LOANS_PATH + '{loanId}/extension')
                    .then()
                    .statusCode(HttpStatus.SC_OK)

    }
}
