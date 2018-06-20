package com.marekk.microlending.domain.loan.command

import com.marekk.microlending.BaseITSpec
import com.marekk.microlending.domain.customer.CustomerExamples
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import spock.lang.Subject

import java.time.LocalDate

@DataJpaTest
@ContextConfiguration(classes = LoanCreatorConfiguration)
@Sql(scripts = '/insert_customer.sql', executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class LoanCreatorRepositoryITSpec extends BaseITSpec {

    @Autowired
    @Subject
    private LoanCreatorRepository loanCreatorRepository

    def "created loan should be stored in database"() {
        given:
            LocalDate now = LocalDate.now()
            LoanEntity entity = new LoanEntity(now, 100, 1.6, now.plusDays(1), CustomerExamples.MAREK_SNAPSHOT)
        when:
            String loanId = loanCreatorRepository.save(entity).uuid
        then:
            loanId
    }

}
