package com.marekk.microlending

import com.marekk.microlending.domain.application.LoanApplicationCommand
import com.marekk.microlending.domain.application.LoanApplicationFacade
import com.marekk.microlending.domain.customer.CustomerFacade
import com.marekk.microlending.domain.customer.CustomerRegisterCommand
import com.marekk.microlending.domain.customer.CustomerSnapshot
import com.marekk.microlending.domain.loan.command.extension.ExtensionFacade
import com.marekk.microlending.domain.loan.query.LoanFinderFacade
import com.marekk.microlending.domain.loan.query.LoanSnapshot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static java.net.InetAddress.localHost

@SpringBootTest
class MicrolendingApplicationSpec extends Specification {

    @Autowired
    private CustomerFacade customerFacade

    @Autowired
    private LoanApplicationFacade loanApplicationFacade

    @Autowired
    private ExtensionFacade extensionFacade

    @Autowired
    private LoanFinderFacade loanFinderFacade

    def 'happyFlow of main feature'() {
        given: 'new customer registers'
            String customerId = customerFacade.register(CustomerRegisterCommand.of('marek', 'kapowicki', 'mk@gmail.com'))
        and:
            CustomerSnapshot customer = customerFacade.retrieve(customerId)
        and: 'customer makes a loan application'
            String loanId = loanApplicationFacade.process(LoanApplicationCommand.of(customer, 100G as BigDecimal, 10, localHost)).right().get()
        and: 'loan ia taken'
            LoanSnapshot loan = loanFinderFacade.retrieve(loanId, customerId)
        and: 'customer extends the loan'
            extensionFacade.extend(loan)
        when: 'customer retrieve the loan by id'
            loan = loanFinderFacade.retrieve(loan.loanId, customer.identityNo)
        then: 'he can see taken loan with one extension'
            with(loan) {
                loanId
                extensions.size() == 1
            }
        and: 'the extension changes the dueDate of loan'
            loan.details.dueDate < loan.extensions[0].extDueDate


    }
}
