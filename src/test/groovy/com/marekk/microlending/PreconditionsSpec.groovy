package com.marekk.microlending

import spock.lang.Specification

import static com.marekk.microlending.domain.exception.Exceptions.notFound

class PreconditionsSpec extends Specification {
    def 'throw the expected exception'() {
        when:
            Preconditions.checkArgument(false, notFound())
        then:
            RuntimeException ex = thrown()
            ex.class == notFound().get().class
    }

    def 'non exception is thrown ehere precondition is met'() {
        when:
            Preconditions.checkArgument(true, notFound())
        then:
            noExceptionThrown()
    }
}
