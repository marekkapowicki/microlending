package com.marekk.microlending.api.customer

import com.marekk.microlending.domain.customer.CustomerRegisterCommand
import groovy.json.JsonBuilder
import io.codearte.jfairy.Fairy
import io.codearte.jfairy.producer.person.Person

class RequestExamples {
    static final CustomerRegisterRequest CUSTOMER_CORRECT_REQUEST = generate()

    static CustomerRegisterCommand toRegisterCommand(CustomerRegisterRequest request) {
        return request.toCommand()
    }

    static String toJson(CustomerRegisterRequest request) {
        CustomerRegisterCommand customer = request.toCommand()
        return new JsonBuilder(['firstName': customer.firstName, 'lastName': customer.lastName, 'email': customer.email]).toPrettyString()
    }

    static CustomerRegisterRequest generate() {
        Person person = Fairy.create().person()
        return new CustomerRegisterRequest(person.firstName, person.lastName, person.email)
    }


}
