package com.marekk.microlending.domain.loan.query

import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month

import static com.marekk.microlending.domain.exception.Exceptions.illegalState
import static com.marekk.microlending.domain.exception.Exceptions.notFound
import static com.marekk.microlending.domain.loan.query.LoanViewRowAssembler.sample
import static com.marekk.microlending.domain.loan.query.LoanViewRowAssembler.sample

class LoanConverterSpec extends Specification {

    @Subject
    private LoanConverter converter = new LoanConverter()

    def 'throws exception for list: #emptyList'() {
        when:
            converter.convert(emptyList)
        then:
            RuntimeException ex = thrown()
            ex.class == notFound().get().class
        where:
            emptyList << [[], null]

    }

    def 'throws exception when different loans are mixed'() {
        given:
            List<LoanViewRow> rows = [sample('1').assemble(), sample('2').assemble()]
        when:
            converter.convert(rows)
        then:
            RuntimeException ex = thrown()
            ex.class == illegalState().get().class
            ex.message =~ 'Found: 2'
    }

    def 'sample conversion'() {
        given:
            List<LoanViewRow> rows = [sample('1').assemble()]
        when:
            LoanSnapshot loan = converter.convert(rows)
        then:
            with(loan) {
                loanId == '1'
                !extensions
            }
    }

    def 'sample conversion with one extension'() {
        given:
            LocalDate extensionDueDate = LocalDate.of(2018, Month.APRIL, 15)
            List<LoanViewRow> rows = [sample('1').extension(2, extensionDueDate).assemble()]
        when:
            LoanSnapshot loan = converter.convert(rows)
        then:
            with(loan) {
                loanId == '1'
                extensions[0].extDueDate == extensionDueDate
            }
    }

    def 'sample conversion with list of extension #extensionsToConvert'() {
        given:
           List<LoanViewRowAssembler> rows = extensionsToConvert.collect {
               sample('id').extension(it).assemble()}
        when:
            LoanSnapshot loan = converter.convert(rows)
        then:
            with(loan) {
                loanId == 'id'
                extensions.size() == extensionsToConvert.size()
            }
        where:
             extensionsToConvert << [[new ExtensionSnapshot(1, LocalDate.now(), LocalDateTime.now())],
                                     [new ExtensionSnapshot(1, LocalDate.now(), LocalDateTime.now()), new ExtensionSnapshot(2, LocalDate.now(), LocalDateTime.now()) ]]

    }
}
