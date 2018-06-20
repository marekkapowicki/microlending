package com.marekk.microlending.domain.loan.command.extension

import com.marekk.microlending.BaseITSpec
import com.marekk.microlending.domain.loan.LoanExamples
import com.marekk.microlending.infrastructure.Profiles
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Subject

@ActiveProfiles(Profiles.TEST)
@ContextConfiguration(classes = ExtensionConfiguration)
class ExtensionFacadeITSpec extends BaseITSpec {

    @Subject
    @Autowired
    ExtensionFacade extensionFacade;

    def "save extension without exception"() {
        when:
            extensionFacade.extend(LoanExamples.SAMPLE)
        then:
            noExceptionThrown()

    }
}
