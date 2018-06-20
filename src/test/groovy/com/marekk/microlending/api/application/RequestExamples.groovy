package com.marekk.microlending.api.application

import com.marekk.microlending.domain.application.LoanApplicationCommand
import com.marekk.microlending.domain.customer.CustomerExamples
import groovy.json.JsonBuilder

class RequestExamples {
    static final LoanApplicationCreateRequest APPLICATION_CORRECT_REQUEST = new LoanApplicationCreateRequest(100, 10)
    static
    final LoanApplicationCommand MAREK_LOAN_APPLICATION_FACTORY = APPLICATION_CORRECT_REQUEST.toCommand(CustomerExamples.MAREK_SNAPSHOT, InetAddress.localHost)


    static String toJson(LoanApplicationCreateRequest request) {
        return new JsonBuilder(['amount': request.amount, 'dueDays': request.dueDays])
    }
}
