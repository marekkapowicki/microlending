package com.marekk.microlending.domain.customer

import io.codearte.jfairy.Fairy
import io.codearte.jfairy.producer.person.Person

class CustomerExamples {

    static final CustomerSnapshot MAREK_SNAPSHOT = new CustomerSnapshot('1', 'marek kapowicki', 'mk@gmail.com')

    static CustomerSnapshot toSnapshot(CustomerEntity entity) {
        return entity.toSnapshot()
    }

    static CustomerEntity generate() {
        Person person = Fairy.create().person()
        return new CustomerEntity(person.firstName, person.lastName, person.email)
    }

}
