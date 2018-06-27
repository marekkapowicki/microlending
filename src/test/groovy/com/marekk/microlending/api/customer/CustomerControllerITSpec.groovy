package com.marekk.microlending.api.customer

import com.marekk.microlending.BaseSpringBootITSpec
import com.marekk.microlending.domain.customer.CustomerExamples
import com.marekk.microlending.domain.customer.CustomerFacade
import org.apache.http.HttpStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

import static RequestExamples.toJson
import static com.google.common.collect.Lists.newArrayList
import static com.jayway.restassured.RestAssured.given
import static com.jayway.restassured.RestAssured.when
import static com.marekk.microlending.api.Specification.API_CONTENT_TYPE
import static com.marekk.microlending.api.Specification.ROOT
import static com.marekk.microlending.domain.exception.Exceptions.conflicted
import static com.marekk.microlending.domain.exception.Exceptions.notFound
import static org.apache.http.HttpStatus.SC_NOT_FOUND
import static org.hamcrest.Matchers.notNullValue
import static org.hamcrest.core.IsEqual.equalTo

class CustomerControllerITSpec extends BaseSpringBootITSpec {

    @Autowired
    CustomerFacade customerFacade

    def 'should receive all customers'() {
        given:
            customerFacade.retrieve(_ as Pageable) >> new PageImpl<>(newArrayList(CustomerExamples.MAREK_SNAPSHOT))
        expect:
            when()
                .get(ROOT + "/customers")
            .then()
                .body("numberOfElements", equalTo(1))
                .statusCode(HttpStatus.SC_OK)
    }

    def 'should receive customer details when customer exists'() {
        given:
            String existingId = "someId"
            customerFacade.retrieve(existingId) >> CustomerExamples.MAREK_SNAPSHOT
        expect:
            given()
                .pathParam("customerId", existingId)
            .when()
                .get(ROOT + "/customers/{customerId}")
            .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(API_CONTENT_TYPE)
                .body("identityNo", notNullValue()).and()
                .body("fullName", notNullValue()).and()
                .body("email", notNullValue())
    }

    def 'should return status 404 during retrieving by id when customer does not exists'() {
        given:
            String wrongId = "wrongId"
            customerFacade.retrieve(wrongId) >> { throw notFound().get() }
        expect:
            given()
                .pathParam("customerId", wrongId)
            .when()
                .get(ROOT + "/customers/{customerId}")
            .then()
                .statusCode(SC_NOT_FOUND)
                .contentType(API_CONTENT_TYPE)
    }

    def 'should return status 409 during registration when email exists'() {
        given:
            customerFacade.register(RequestExamples.CUSTOMER_CORRECT_REQUEST.toCommand()) >> {
                throw conflicted().get()
            }
        expect:
            given()
                .contentType(API_CONTENT_TYPE)
                .body(toJson(RequestExamples.CUSTOMER_CORRECT_REQUEST))
            .when()
                .post(ROOT + "/customers")
            .then()
                .statusCode(HttpStatus.SC_CONFLICT)
    }

    def 'should return id during customer registration'() {
        given:
            String createdId = "createdId"
            customerFacade.register(RequestExamples.CUSTOMER_CORRECT_REQUEST.toCommand()) >> createdId
        expect:
            given()
                .contentType(API_CONTENT_TYPE)
                .body(toJson(RequestExamples.CUSTOMER_CORRECT_REQUEST))
            .when()
                .post(ROOT + "/customers")
            .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("id", equalTo(createdId))
    }
}
